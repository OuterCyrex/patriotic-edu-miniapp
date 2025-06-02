package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@TableName(value ="service_booking")
@Data
public class ServiceBooking implements Serializable {
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @Schema(description = "组织名称")
  private String organizationName;

  @Schema(description = "联系人")
  private String contactPerson;

  @Schema(description = "联系电话")
  private String contactPhone;

  @Schema(description = "联系邮箱")
  private String contactEmail;

  @Schema(description = "服务类型")
  private Integer serviceType;
  @Schema (description = "期望时间")

  private String preferredTime;

  @Schema(description = "参与人数")
  private Integer participantCount;

  @Schema (description = "场地地址")
  private String venueAddress;

  @Schema(description = "特殊要求")
  private String specialRequirements;

  @Schema(description = "匹配英雄")
  private String matchedHeroes;

  @Schema(description = "状态")
  private Integer status;

  @Schema(description = "管理员备注")
  private String adminNotes;

  @TableField(value = "create_by")
  private String createBy;

  @TableField(value = "update_by")
  private String updateBy;

  @Serial
  private static final long serialVersionUID = 1L;
}
