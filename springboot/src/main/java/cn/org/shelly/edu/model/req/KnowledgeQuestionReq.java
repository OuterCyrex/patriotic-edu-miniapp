package cn.org.shelly.edu.model.req;

import cn.org.shelly.edu.model.po.KnowledgeQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class KnowledgeQuestionReq {
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

    @Schema(description = "正确答案（1=A，2=B，3=C，4=D）")
    private Integer correctAnswer;

    @Schema(description = "答案解析")
    private String explanation;

    @Schema(description = "题目难度（1-简单，2-中等，3-困难）")
    private Integer difficulty;

    @Schema(description = "题目分类")
    private String category;

    @Schema(description = "状态（1启用，0禁用）")
    private Integer status;

    public static KnowledgeQuestion toEntity(KnowledgeQuestionReq knowledgeQuestionReq) {
        KnowledgeQuestion knowledgeQuestion = new KnowledgeQuestion();
        knowledgeQuestion.setQuestion(knowledgeQuestionReq.getQuestion())
                .setOptionA(knowledgeQuestionReq.getOptionA())
                .setOptionB(knowledgeQuestionReq.getOptionB())
                .setOptionC(knowledgeQuestionReq.getOptionC())
                .setOptionD(knowledgeQuestionReq.getOptionD())
                .setCorrectAnswer(knowledgeQuestionReq.getCorrectAnswer())
                .setExplanation(knowledgeQuestionReq.getExplanation())
                .setDifficulty(knowledgeQuestionReq.getDifficulty())
                .setCategory(knowledgeQuestionReq.getCategory())
                .setStatus(knowledgeQuestionReq.getStatus());
        return knowledgeQuestion;
    }
}
