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

    @Schema(description = "已做?")
    private Integer done;

    @Schema(description = "是否正确(当done字段为1时不为null)")
    private Boolean correct;

    @Schema(description = "你的选择(当done字段为1时不为null)")
    private Integer choice;

    @Schema(description = "正确答案(当done字段为1时不为null)")
    private Integer answer;

    @Schema(description = "法律依据(当done字段为1时不为null)")
    private String legalBasis;

    @Schema(description = "解决方案(当done字段为1时不为null)")
    private String solution;

    @Schema(description = "答题记录id(当done字段为1时不为null)")
    private Long recordId;
    public static ScenarioQuestionResp toResp(ScenarioQuestion scenarioQuestion) {
         return new ScenarioQuestionResp()
                .setId(scenarioQuestion.getId())
                 .setScenario(scenarioQuestion.getScenario())
                 .setQuestion(scenarioQuestion.getQuestion())
                 .setOptionA(scenarioQuestion.getOptionA())
                 .setOptionB(scenarioQuestion.getOptionB())
                 .setDone(0)
                 .setAnswer(scenarioQuestion.getCorrectAnswer())
                 .setLegalBasis(scenarioQuestion.getLegalBasis())
                 .setSolution(scenarioQuestion.getSolution())
                 .setOptionC(scenarioQuestion.getOptionC());
    }
}
