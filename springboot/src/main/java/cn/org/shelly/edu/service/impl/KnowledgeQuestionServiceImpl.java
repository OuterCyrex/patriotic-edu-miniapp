package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.KnowledgeQuestionMapper;
import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import cn.org.shelly.edu.model.po.UserRecord;
import cn.org.shelly.edu.model.req.KnowledgeQuestionReq;
import cn.org.shelly.edu.model.resp.KnowledgeQuestionResp;
import cn.org.shelly.edu.model.resp.KnowledgeResultResp;
import cn.org.shelly.edu.service.KnowledgeQuestionService;
import cn.org.shelly.edu.service.UserRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author Shelly6
* @description 针对表【knowledge_question(国防知识题库表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@RequiredArgsConstructor
public class KnowledgeQuestionServiceImpl extends ServiceImpl<KnowledgeQuestionMapper, KnowledgeQuestion>
    implements KnowledgeQuestionService {
    private final UserRecordService userRecordService;

    @Override
    public List<KnowledgeQuestionResp> getQuiz() {
        return baseMapper.selectList(
                new QueryWrapper<KnowledgeQuestion>()
                        .select("id", "question", "option_a", "option_b", "option_c", "option_d", "difficulty")
                        .eq("status", 1)
                        .eq("is_deleted", 0)
                        .last("ORDER BY RAND() LIMIT 10")
        ).stream()
                .map(KnowledgeQuestionResp::toResp)
                .toList();
    }

    @Override
    public KnowledgeResultResp submit(Long questionId, Integer answer) {
        KnowledgeQuestion  knowledgeQuestion = baseMapper.selectById(questionId);
        if (knowledgeQuestion == null) {
            throw new CustomException("题目不存在");
        }
        boolean isCorrect = answer.equals(knowledgeQuestion.getCorrectAnswer());
        UserRecord userRecord = new UserRecord()
                .setUserId(1L)
                .setQuestionId(questionId)
                .setQuestionType(1)
                .setUserAnswer(answer)
                .setIsCorrect(isCorrect ? 1 : 0)
                .setStarsEarned(isCorrect ? 1 : 0)
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
}




