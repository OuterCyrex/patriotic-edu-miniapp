package cn.org.shelly.edu.service.impl;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.ScenarioQuestionMapper;
import cn.org.shelly.edu.model.dto.QuestionResultDTO;
import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import cn.org.shelly.edu.model.po.ScenarioQuestion;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.po.UserRecord;
import cn.org.shelly.edu.model.req.ScenarioQuestionReq;
import cn.org.shelly.edu.model.req.SubmitReq;
import cn.org.shelly.edu.model.resp.*;
import cn.org.shelly.edu.service.ScenarioQuestionService;
import cn.org.shelly.edu.service.UserRecordService;
import cn.org.shelly.edu.service.UserService;
import cn.org.shelly.edu.utils.CommonUtil;
import cn.org.shelly.edu.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
* @author Shelly6
* @description 针对表【scenario_question(情景问答表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@RequiredArgsConstructor
@Slf4j
public class ScenarioQuestionServiceImpl extends ServiceImpl<ScenarioQuestionMapper, ScenarioQuestion>
    implements ScenarioQuestionService {
    private final UserRecordService userRecordService;
    private final RedisUtil redisUtil;
    private final UserService userService;
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Override
    public List<ScenarioQuestionResp> getScenarioQuiz() {
        String key = buildKey(StpUtil.getLoginIdAsLong());
        List<Integer> qs = redisUtil.getListOfKey(key, Integer.class);
        List<ScenarioQuestionResp> ret;
        if (qs == null || qs.isEmpty()) {
            var list = new java.util.ArrayList<>(baseMapper.selectList(
                            new QueryWrapper<ScenarioQuestion>()
                                    .select("id", "scenario", "question",
                                            "option_a", "option_b", "option_c",
                                            "legal_basis", "solution", "correct_answer")
                                    .eq("status", 1)
                                    .eq("is_deleted", 0)
                                    .last("ORDER BY RAND() LIMIT 10")
                    ).stream()
                    .map(ScenarioQuestionResp::toResp)
                    .toList());
            qs = list.stream().map(ScenarioQuestionResp::getId).toList();
            list.sort(Comparator.comparing(ScenarioQuestionResp::getId));
            redisUtil.set(key, qs);
            ret = list;
        }
        else{
            ret = lambdaQuery()
                    .in(ScenarioQuestion::getId, qs)
                    .orderByAsc(ScenarioQuestion::getId)
                    .select(ScenarioQuestion::getId, ScenarioQuestion::getQuestion, ScenarioQuestion::getSolution,
                            ScenarioQuestion::getCorrectAnswer, ScenarioQuestion::getLegalBasis,
                            ScenarioQuestion::getOptionA, ScenarioQuestion::getScenario,
                            ScenarioQuestion::getOptionB, ScenarioQuestion::getOptionC)
                    .list()
                    .stream()
                    .map(ScenarioQuestionResp::toResp)
                    .toList();
        }
        this.fillFields(ret, qs);
        return ret;
    }

    private void fillFields(List<ScenarioQuestionResp> ret, List<Integer> qs) {
        if(ret == null || ret.isEmpty() ||  qs == null || qs.isEmpty()){
            return;
        }
        List<QuestionResultDTO> list = userRecordService.lambdaQuery()
                .select(UserRecord::getIsCorrect, UserRecord::getUserAnswer,
                        UserRecord::getQuestionId, UserRecord::getId)
                .eq(UserRecord::getQuestionType, 2)
                .eq(UserRecord::getUserId, StpUtil.getLoginIdAsLong())
                .in(UserRecord::getQuestionId, qs)
                .ge(UserRecord::getGmtCreate, LocalDate.now().atStartOfDay())
                .list()
                .stream()
                .map(record -> new QuestionResultDTO()
                        .setResult(record.getIsCorrect() == 1)
                        .setQuestionId(record.getQuestionId())
                        .setRecordId(record.getId())
                        .setUserAnswer(record.getUserAnswer()))
                .toList();
        log.info("resultMap: {}", list);
        Map<Long, QuestionResultDTO> resultMap = list.stream()
                .collect(Collectors.toMap(QuestionResultDTO::getQuestionId, result -> result));
        for(ScenarioQuestionResp question: ret){
            if(resultMap.containsKey(question.getId().longValue())){
                var result = resultMap.get(question.getId().longValue());
                question.setChoice(result.getUserAnswer());
                question.setDone(1);
                question.setCorrect(result.getResult());
                question.setRecordId(result.getRecordId());
            }
            else{
                question.setAnswer(null);
                question.setChoice(null);
                question.setLegalBasis(null);
                question.setSolution(null);
            }
        }
    }

    @Override
    public void addScenarioQuiz(ScenarioQuestionReq scenarioQuestionReq) {
        ScenarioQuestion scenarioQuestion = ScenarioQuestionReq.toEntity(scenarioQuestionReq);
        save(scenarioQuestion);
    }

    @Override
    public void updateScenarioQuiz(ScenarioQuestionReq scenarioQuestionReq) {
         ScenarioQuestion scenarioQuestion = ScenarioQuestionReq.toEntity(scenarioQuestionReq);
         updateById(scenarioQuestion);
    }

    @Override
    public ScenarioResultResp submit(Long questionId, Integer answer) {
        ScenarioQuestion scenarioQuestion = baseMapper.selectOne(
                new LambdaQueryWrapper<ScenarioQuestion>()
                        .eq(ScenarioQuestion::getId, questionId)
                        .eq(ScenarioQuestion::getStatus, 1)
                        .eq(ScenarioQuestion::getIsDeleted, 0)
        );
        if(Objects.isNull(scenarioQuestion)){
            throw new CustomException("题目不存在");
        }
        boolean isCorrect = answer.equals(scenarioQuestion.getCorrectAnswer());
        UserRecord userRecord = new UserRecord()
                .setUserId(StpUtil.getLoginIdAsLong())
                .setQuestionId(questionId)
                .setQuestionType(2)
                .setUserAnswer(answer)
                .setIsCorrect(isCorrect ? 1 : 0)
                .setUsed(0)
                .setGmtCreate(new Date());
        userRecordService.save(userRecord);
        return new ScenarioResultResp()
                .setResult(isCorrect)
                .setUserAnswer(answer)
                .setAnswer(scenarioQuestion.getCorrectAnswer())
                .setRecordId(userRecord.getId())
                .setLegalBasis(scenarioQuestion.getLegalBasis())
                .setSolution(scenarioQuestion.getSolution());
    }

//    @Override
//    public ScenarioResultResp submitAll(List<SubmitReq> req) {
//        List<Long> qs = req.stream().map(SubmitReq::getQuestionId).toList();
//        Map<Integer, ScenarioQuestion> questionMap = this.lambdaQuery()
//                .select(ScenarioQuestion::getId, ScenarioQuestion::getSolution,
//                        ScenarioQuestion::getLegalBasis,
//                        ScenarioQuestion::getCorrectAnswer)
//                .in(ScenarioQuestion::getId, qs)
//                .list()
//                .stream()
//                .collect(Collectors.toMap(ScenarioQuestion::getId, question -> question));
//        Long userId = StpUtil.getLoginIdAsLong();
//        AtomicReference<Integer> ac = new AtomicReference<>(0);
//        List<UserRecord> userRecords = req.stream()
//                .map(r -> {
//                    UserRecord userRecord = new UserRecord()
//                            .setUserId(userId)
//                            .setQuestionId(r.getQuestionId())
//                            .setQuestionType(2)
//                            .setUserAnswer(r.getAnswer());
//                    int isCorrect = questionMap.get(r.getQuestionId().intValue()).getCorrectAnswer().equals(r.getAnswer()) ? 1 : 0;
//                    if(isCorrect == 1){
//                        ac.getAndSet(ac.get() + 1);
//                    }
//                    userRecord.setIsCorrect(isCorrect);
//                    userRecord.setUsed(1);
//                    return userRecord;
//                })
//                .toList();
//        userRecordService.saveBatch(userRecords);
//        var data = userRecords.stream()
//                .map(record -> new ScenarioSingleResultResp()
//                        .setResult(record.getIsCorrect() == 1)
//                        .setLegalBasis(questionMap.get(record.getQuestionId().intValue()).getLegalBasis())
//                        .setSolution(questionMap.get(record.getQuestionId().intValue()).getSolution())
//                        .setQuestionId(record.getQuestionId())
//                        .setRecordId(record.getId()))
//                .toList();
//        String comment = CommonUtil.getCommentByAcs(ac.get());
//        int stars = CommonUtil.calculateStars(ac.get());
//        CompletableFuture.runAsync(() -> userService.lambdaUpdate()
//                .setSql("total_stars = total_stars + " + stars)
//                .eq(User::getId, userId)
//                .update(), threadPoolTaskExecutor);
//        return new ScenarioResultResp()
//                .setList(data)
//                .setAc(ac.get())
//                .setWa(10 - ac.get())
//                .setComment(comment)
//                .setStars(stars);
//    }

    private String buildKey(Long userId) {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd
        return String.format("quiz:daily:scenario:%d:%s", userId, date);
    }
}




