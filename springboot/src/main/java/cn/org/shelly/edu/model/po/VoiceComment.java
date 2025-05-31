package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@TableName(value ="voice_comment")
@Data
public class VoiceComment implements Serializable {

  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  @TableField(value = "voice_id")
  private Long voiceId;

  @TableField(value = "user_id")
  private Long userId;

  /**
   * 评论内容
   */
  @TableField(value = "content")
  @Schema(description = "评论内容")
  private String content;

  /**
   * 父评论ID，0为顶级评论
   */
  @TableField(value = "parent_id")
  @Schema(description = "父评论ID，0为顶级评论")
  private Long parentId;

  /**
   * 点赞数
   */
  @TableField(value = "likes_count")
  @Schema(description = "点赞数")
  private Integer likesCount;

  /**
   * 状态：1显示 0隐藏
   */
  @TableField(value = "status")
  @Schema(description = "状态：1显示 0隐藏")
  private Integer status;

  @TableField(value = "create_by")
  private String createBy;

  @TableField(exist = false)
  @Serial
  private static final long serialVersionUID = 1L;
}
