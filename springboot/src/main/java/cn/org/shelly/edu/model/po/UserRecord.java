package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName(value ="user_record")
@Data
@Accessors(chain = true)
public class UserRecord implements Serializable {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @TableField(value = "user_id")
  private Long userId;

  @TableField(value = "question_id")
  private Long questionId;

  /**
   * 题目类型：1知识题 2情景题
   */
  @TableField(value = "question_type")
  @Schema(description = "题目类型：1知识题 2情景题")
  private Integer questionType;

  /**
   * 用户答案：1-A 2-B 3-C 4-D
   */
  @TableField(value = "user_answer")
  @Schema(description = "用户答案：1-A 2-B 3-C 4-D")
  private Integer userAnswer;

  /**
   * 是否正确：1正确 0错误
   */
  @TableField(value = "is_correct")
  @Schema(description = "是否正确：1正确 0错误")
  private Integer isCorrect;

  /**
   * 是否核算
   */
  @TableField(value = "used")
  @Schema(description = "是否核算（0：未 1：已）")
  private Integer used;

  /**
   * 创建时间
   */
  @TableField(value = "gmt_create")
  private Date gmtCreate;

  @TableField(exist = false)
  @Serial
  private static final long serialVersionUID = 1L;
}
