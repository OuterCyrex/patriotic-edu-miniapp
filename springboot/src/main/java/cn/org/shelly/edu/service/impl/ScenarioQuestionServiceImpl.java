package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.ScenarioQuestionMapper;
import cn.org.shelly.edu.model.po.ScenarioQuestion;
import cn.org.shelly.edu.model.po.UserRecord;
import cn.org.shelly.edu.model.req.ScenarioQuestionReq;
import cn.org.shelly.edu.model.resp.ScenarioQuestionResp;
import cn.org.shelly.edu.model.resp.ScenarioResultResp;
import cn.org.shelly.edu.service.ScenarioQuestionService;
import cn.org.shelly.edu.service.UserRecordService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
* @author Shelly6
* @description 针对表【scenario_question(情景问答表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@RequiredArgsConstructor
public class ScenarioQuestionServiceImpl extends ServiceImpl<ScenarioQuestionMapper, ScenarioQuestion>
    implements ScenarioQuestionService {
    private final UserRecordService userRecordService;


    @Override
    public List<ScenarioQuestionResp> getScenarioQuiz() {
        return baseMapper.selectList(
                        new QueryWrapper<ScenarioQuestion>()
                                .select("id", "scenario", "question", "option_a", "option_b", "option_c")
                                .eq("status", 1)
                                .eq("is_deleted", 0)
                                .last("ORDER BY RAND() LIMIT 10")
                ).stream()
                .map(ScenarioQuestionResp::toResp)
                .toList();
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
                .setUserId(1L)
                .setQuestionId(questionId)
                .setQuestionType(2)
                .setUserAnswer(answer)
                .setIsCorrect(isCorrect ? 1 : 0)
                .setStarsEarned(isCorrect ? 1 : 0)
                .setGmtCreate(new Date());
        userRecordService.save(userRecord);
        return new ScenarioResultResp()
                .setResult(isCorrect)
                .setRecordId(userRecord.getId())
                .setLegalBasis(scenarioQuestion.getLegalBasis())
                .setSolution(scenarioQuestion.getSolution());
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
}




