package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.ScenarioQuestion;
import cn.org.shelly.edu.model.req.ScenarioQuestionReq;
import cn.org.shelly.edu.model.req.SubmitReq;
import cn.org.shelly.edu.model.resp.ScenarioQuestionResp;
import cn.org.shelly.edu.model.resp.ScenarioResultResp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Shelly6
* @description 针对表【scenario_question(情景问答表)】的数据库操作Service
* @createDate 2025-05-31 15:29:26
*/
public interface ScenarioQuestionService extends IService<ScenarioQuestion> {

    List<ScenarioQuestionResp> getScenarioQuiz();

    void addScenarioQuiz(ScenarioQuestionReq scenarioQuestionReq);

    void updateScenarioQuiz(ScenarioQuestionReq scenarioQuestionReq);

    ScenarioResultResp submit(List<SubmitReq> req);
}
