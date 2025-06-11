package cn.org.shelly.edu.service.impl;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.mapper.KnowledgeQuestionMapper;
import cn.org.shelly.edu.model.dto.QuestionResultDTO;
import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.po.UserRecord;
import cn.org.shelly.edu.model.req.KnowledgeQuestionReq;
import cn.org.shelly.edu.model.req.SubmitReq;
import cn.org.shelly.edu.model.resp.KnowledgeQuestionResp;
import cn.org.shelly.edu.model.resp.KnowledgeResultResp;
import cn.org.shelly.edu.model.resp.KnowledgeSingleResultResp;
import cn.org.shelly.edu.service.KnowledgeQuestionService;
import cn.org.shelly.edu.service.UserRecordService;
import cn.org.shelly.edu.service.UserService;
import cn.org.shelly.edu.utils.CommonUtil;
import cn.org.shelly.edu.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
* @author Shelly6
* @description 针对表【knowledge_question(国防知识题库表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class KnowledgeQuestionServiceImpl extends ServiceImpl<KnowledgeQuestionMapper, KnowledgeQuestion>
    implements KnowledgeQuestionService {
    private final UserRecordService userRecordService;
    private final RedisUtil redisUtil;
    private final UserService userService;
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Override
    public List<KnowledgeQuestionResp> getQuiz() {
        String key = buildKey(StpUtil.getLoginIdAsLong());
        List<Long> qs = redisUtil.getListOfKey(key, Long.class);
        List<KnowledgeQuestionResp> ret;
        if (qs == null || qs.isEmpty()) {
            var list = new java.util.ArrayList<>(baseMapper.selectList(
                            new QueryWrapper<KnowledgeQuestion>()
                                    .select("id", "question", "option_a",
                                            "option_b", "option_c", "option_d", "explanation",
                                            "difficulty", "correct_answer")
                                    .eq("status", 1)
                                    .eq("is_deleted", 0)
                                    .last("ORDER BY RAND() LIMIT 10")
                    ).stream()
                    .map(KnowledgeQuestionResp::toResp)
                    .toList());
            qs = list.stream().map(KnowledgeQuestionResp::getId).toList();
            list.sort(Comparator.comparing(KnowledgeQuestionResp::getId));
            redisUtil.set(key, qs);
            ret = list;
        }
        else{
            ret = lambdaQuery()
                    .in(KnowledgeQuestion::getId, qs)
                    .orderByAsc(KnowledgeQuestion::getId)
                    .select(KnowledgeQuestion::getId, KnowledgeQuestion::getQuestion, KnowledgeQuestion::getOptionA,
                            KnowledgeQuestion::getExplanation,
                            KnowledgeQuestion::getOptionB, KnowledgeQuestion::getOptionC, KnowledgeQuestion::getCorrectAnswer,
                            KnowledgeQuestion::getOptionD, KnowledgeQuestion::getDifficulty)
                    .list()
                    .stream()
                    .map(KnowledgeQuestionResp::toResp)
                    .toList();
        }
            this.fillFields(ret, qs);
        return ret;
    }

    private void fillFields(List<KnowledgeQuestionResp> ret, List<Long> qs) {
        if(ret == null || ret.isEmpty() ||  qs == null || qs.isEmpty()){
                return;
        }
        List<QuestionResultDTO> list = userRecordService.lambdaQuery()
                .select(UserRecord::getIsCorrect, UserRecord::getUserAnswer,
                        UserRecord::getQuestionId, UserRecord::getId)
                .eq(UserRecord::getQuestionType, 1)
                .in(UserRecord::getQuestionId, qs)
                .eq(UserRecord::getUserId, StpUtil.getLoginIdAsLong())
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
        Map<Long, QuestionResultDTO> resultMap = list.stream().collect(Collectors.toMap(QuestionResultDTO::getQuestionId, result -> result));
        for(KnowledgeQuestionResp question: ret){
            if(resultMap.containsKey(question.getId())){
                var result = resultMap.get(question.getId());
                question.setChoice(result.getUserAnswer());
                question.setDone(1);
                question.setCorrect(result.getResult());
                question.setRecordId(result.getRecordId());
            }
            else{
                question.setAnswer(null);
                question.setChoice(null);
                question.setExplanation(null);
            }
        }
    }

    @Override
    public void addKnowledgeQuiz(KnowledgeQuestionReq knowledgeQuestionReq) {
        KnowledgeQuestion knowledgeQuestion = KnowledgeQuestionReq.toEntity(knowledgeQuestionReq);
        save(knowledgeQuestion);
    }

    @Override
    public void updateScenarioQuiz(KnowledgeQuestionReq knowledgeQuestionReq) {
         KnowledgeQuestion knowledgeQuestion = KnowledgeQuestionReq.toEntity(knowledgeQuestionReq);
         updateById(knowledgeQuestion);
    }

    @Override
    public KnowledgeResultResp submit(List<SubmitReq> req) {
        List<Long> qs = req.stream().map(SubmitReq::getQuestionId).toList();
        Map<Long, KnowledgeQuestion> questionMap = lambdaQuery()
                .select(KnowledgeQuestion::getId, KnowledgeQuestion::getExplanation,
                        KnowledgeQuestion::getCorrectAnswer)
                .in(KnowledgeQuestion::getId, qs)
                .list()
                .stream()
                .collect(Collectors.toMap(KnowledgeQuestion::getId, question -> question));
        Long userId = StpUtil.getLoginIdAsLong();
        AtomicReference<Integer> ac = new AtomicReference<>(0);
        List<UserRecord> userRecords = req.stream()
                .map(r -> {
                    UserRecord userRecord = new UserRecord()
                            .setUserId(userId)
                            .setQuestionId(r.getQuestionId())
                            .setQuestionType(1)
                            .setUserAnswer(r.getAnswer());
                    int isCorrect = questionMap.get(r.getQuestionId()).getCorrectAnswer().equals(r.getAnswer()) ? 1 : 0;
                    if(isCorrect == 1){
                        ac.getAndSet(ac.get() + 1);
                    }
                    userRecord.setIsCorrect(isCorrect);
                    userRecord.setUsed(1);
                    return userRecord;
                })
                .toList();
        userRecordService.saveBatch(userRecords);
        var data = userRecords.stream()
                .map(record -> new KnowledgeSingleResultResp()
                        .setResult(record.getIsCorrect() == 1)
                        .setExplanation(questionMap.get(record.getQuestionId()).getExplanation())
                        .setQuestionId(record.getQuestionId())
                        .setRecordId(record.getId()))
                .toList();
        String comment = CommonUtil.getCommentByAcs(ac.get());
        int stars = CommonUtil.calculateStars(ac.get());
        CompletableFuture.runAsync(() -> userService.lambdaUpdate()
                .setSql("total_stars = total_stars + " + stars)
                .eq(User::getId, userId)
                .update(), threadPoolTaskExecutor);
        return new KnowledgeResultResp()
                .setList(data)
                .setAc(ac.get())
                .setWa(10 - ac.get())
                .setComment(comment)
                .setStars(stars);
    }

    private String buildKey(Long userId) {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        return String.format("quiz:daily:knowledge:%d:%s", userId, date);
    }

}




