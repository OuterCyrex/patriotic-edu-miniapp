package cn.org.shelly.edu.model.req;

import cn.org.shelly.edu.model.po.ScenarioQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ScenarioQuestionReq {
    private Long id;

    @Schema(description = "情景描述")
    private String scenario;
    @Schema(description = "问题")
    private String question;

    private String optionA;

    private String optionB;

    private String optionC;

    @Schema(description = "正确答案（1=A，2=B，3=C）")
    private Integer correctAnswer;

    @Schema(description = "法律依据")
    private String legalBasis;
    @Schema(description = "解决方案")
    private String solution;
    private Integer status;

    public static ScenarioQuestion toEntity(ScenarioQuestionReq scenarioQuestionReq) {
        ScenarioQuestion scenarioQuestion = new ScenarioQuestion();
        scenarioQuestion.setScenario(scenarioQuestionReq.getScenario())
                .setId(scenarioQuestion.getId())
                .setQuestion(scenarioQuestionReq.getQuestion())
                .setOptionA(scenarioQuestionReq.getOptionA())
                .setOptionB(scenarioQuestionReq.getOptionB())
                .setOptionC(scenarioQuestionReq.getOptionC())
                .setCorrectAnswer(scenarioQuestionReq.getCorrectAnswer())
                .setLegalBasis(scenarioQuestion.getLegalBasis())
                .setStatus(scenarioQuestion.getStatus())
                .setSolution(scenarioQuestion.getSolution());
        return scenarioQuestion;
    }
}
