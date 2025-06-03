package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import cn.org.shelly.edu.model.req.KnowledgeQuestionReq;
import cn.org.shelly.edu.model.resp.KnowledgeQuestionResp;
import cn.org.shelly.edu.model.resp.KnowledgeResultResp;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Shelly6
* @description 针对表【knowledge_question(国防知识题库表)】的数据库操作Service
* @createDate 2025-05-31 15:29:26
*/
public interface KnowledgeQuestionService extends IService<KnowledgeQuestion> {

    List<KnowledgeQuestionResp> getQuiz();

    KnowledgeResultResp submit(Long questionId, Integer answer);

    void addKnowledgeQuiz(KnowledgeQuestionReq knowledgeQuestionReq);

    void updateScenarioQuiz(KnowledgeQuestionReq knowledgeQuestionReq);
}
