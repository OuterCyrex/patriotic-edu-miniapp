package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName(value ="scenario_question")
@Data
@Accessors(chain = true)
public class ScenarioQuestion implements Serializable {
  private Integer id;

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

  private Date gmtCreate;

  private Date gmtModified;

  private String createBy;

  private String updateBy;

  private Integer isDeleted;
  @Serial
  private static final long serialVersionUID = 1L;
}
