package cn.org.shelly.edu.service.impl;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.KnowledgeQuestionMapper;
import cn.org.shelly.edu.model.dto.QuestionResultDTO;
import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import cn.org.shelly.edu.model.po.UserRecord;
import cn.org.shelly.edu.model.req.KnowledgeQuestionReq;
import cn.org.shelly.edu.model.resp.KnowledgeQuestionResp;
import cn.org.shelly.edu.model.resp.KnowledgeResultResp;
import cn.org.shelly.edu.service.KnowledgeQuestionService;
import cn.org.shelly.edu.service.UserRecordService;
import cn.org.shelly.edu.utils.RedisUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    public KnowledgeResultResp submit(Long questionId, Integer answer) {
        KnowledgeQuestion  knowledgeQuestion = baseMapper.selectById(questionId);
        if (knowledgeQuestion == null) {
            throw new CustomException("题目不存在");
        }
        boolean isCorrect = answer.equals(knowledgeQuestion.getCorrectAnswer());
        UserRecord userRecord = new UserRecord()
                .setUserId(StpUtil.getLoginIdAsLong())
                .setQuestionId(questionId)
                .setQuestionType(1)
                .setUserAnswer(answer)
                .setIsCorrect(isCorrect ? 1 : 0)
                .setUsed(0)
                .setGmtCreate(new Date());
        userRecordService.save(userRecord);
        return KnowledgeResultResp.builder()
                .result(isCorrect)
                .explanation(knowledgeQuestion.getExplanation())
                .recordId(userRecord.getId())
                .build();
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
    private String buildKey(Long userId) {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd
        return String.format("quiz:daily:knowledge:%d:%s", userId, date);
    }
}




