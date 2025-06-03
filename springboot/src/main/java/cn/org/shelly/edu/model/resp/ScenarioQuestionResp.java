package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.ScenarioQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ScenarioQuestionResp {
    private Integer id;

    @Schema(description = "情景描述")
    private String scenario;
    @Schema(description = "问题")
    private String question;

    private String optionA;

    private String optionB;

    private String optionC;

    public static ScenarioQuestionResp toResp(ScenarioQuestion scenarioQuestion) {
         return new ScenarioQuestionResp()
                .setId(scenarioQuestion.getId())
                .setScenario(scenarioQuestion.getScenario())
                .setQuestion(scenarioQuestion.getQuestion())
                .setOptionA(scenarioQuestion.getOptionA())
                .setOptionB(scenarioQuestion.getOptionB())
                .setOptionC(scenarioQuestion.getOptionC());
    }
}
