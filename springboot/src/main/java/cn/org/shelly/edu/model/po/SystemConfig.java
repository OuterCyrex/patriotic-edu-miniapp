package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@TableName(value ="system_config")
@Data
public class SystemConfig implements Serializable {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  @Schema (description = "每日答题数量")
  private Integer dailyQuizCount;
  @Schema (description = "每日答对题数可获得的星星数")
  private Integer starsPerCorrect;
  @Schema (description = "服务热线")
  private String hotlinePhone;
  @Schema (description = "心声最长字数")
  private Integer voiceMaxLength;
  @TableField(value = "update_by")
  private String updateBy;
  private String avatar;
  @Serial
  private static final long serialVersionUID = 1L;
}
