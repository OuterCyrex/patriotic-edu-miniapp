package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName(value ="knowledge_question")
@Data
@Accessors(chain = true)
public class KnowledgeQuestion implements Serializable {
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

  @Schema(description = "创建时间")
  private Date gmtCreate;

  @Schema(description = "修改时间")
  private Date gmtModified;

  @Schema(description = "创建者")
  private String createBy;

  @Schema(description = "修改者")
  private String updateBy;

  @Schema(description = "逻辑删除（0=未删除，1=已删除）")
  private Integer isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}
