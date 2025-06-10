package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class KnowledgeQuestionResp {
    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "题干内容")
    private String question;

    @Schema(description = "选项A")
    private String optionA;

    @Schema(description = "选项B")
    private String optionB;

    @Schema(description = "选项C")
    private String optionC;

    @Schema(description = "选项D")
    private String optionD;

    @Schema(description = "题目难度（1-简单，2-中等，3-困难）")
    private Integer difficulty;

    @Schema(description = "已做?")
    private Integer done;

    @Schema(description = "是否正确(当done字段为1时不为null)")
    private Boolean correct;

    @Schema(description = "你的选择(当done字段为1时不为null)")
    private Integer choice;

    @Schema(description = "正确答案(当done字段为1时不为null)")
    private Integer answer;

    @Schema(description = "答案解释(当done字段为1时不为null)")
    private String explanation;

    @Schema(description = "答题记录id(当done字段为1时不为null)")
    private Long recordId;


    public static KnowledgeQuestionResp toResp(KnowledgeQuestion knowledgeQuestion) {
        return new KnowledgeQuestionResp()
                .setId(knowledgeQuestion.getId())
                .setQuestion(knowledgeQuestion.getQuestion())
                .setOptionA(knowledgeQuestion.getOptionA())
                .setOptionB(knowledgeQuestion.getOptionB())
                .setOptionC(knowledgeQuestion.getOptionC())
                .setOptionD(knowledgeQuestion.getOptionD())
                .setAnswer(knowledgeQuestion.getCorrectAnswer())
                .setExplanation(knowledgeQuestion.getExplanation())
                .setDone(0)
                .setDifficulty(knowledgeQuestion.getDifficulty());
    }
}
