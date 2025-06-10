package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@TableName(value ="defense_voice")
@Data
public class DefenseVoice implements Serializable {


  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 心声内容
   */
  @TableField(value = "content")
  @Schema(description = "心声内容")
  private String content;

  /**
   * 地区
   */
  @TableField(value = "region")
  @Schema(description = "地区")
  private String region;

  /**
   * 身份
   */
  @TableField(value = "identity")
  @Schema(description = "身份")
  private String identity;

  /**
   * 作者姓名
   */
  @TableField(value = "author_name")
  private String authorName;

  /**
   * 主题分类
   */
  @TableField(value = "theme")
  @Schema(description = "主题分类")
  private String theme;

  /**
   * 点赞数
   */
  @TableField(value = "likes_count")
  @Schema(description = "点赞数")
  private Integer likesCount;

  /**
   * 评论数
   */
  @TableField(value = "comments_count")
  @Schema(description = "评论数")
  private Integer commentsCount;

  /**
   * 是否精选：1是 0否
   */
  @TableField(value = "is_featured")
  @Schema(description = "是否精选：1是 0否")
  private Integer isFeatured;

  /**
   * 审核状态：0待审核 1已通过 2已拒绝
   */
  @TableField(value = "status")
  @Schema(description = "审核状态  0待审核 1已通过 2已拒绝")
  private Integer status;

  @TableField(value = "create_by")
  private String createBy;

  @TableField(value = "update_by")
  private String updateBy;

  /**
   * 创建时间
   */
  @TableField(value = "gmt_create")
  private Date gmtCreate;

  /**
   * 更新时间
   */
  @TableField(value = "gmt_modified")
  private Date gmtModified;

  /**
   * 逻辑删除
   */
  @TableField(value = "is_deleted")
  private Integer isDeleted;

  @TableField(value = "avatar")
  private String avatar;
}
