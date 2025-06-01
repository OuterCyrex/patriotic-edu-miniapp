package cn.org.shelly.edu.model.po;

import cn.org.shelly.edu.model.resp.WordFrequencyResp;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName(value ="word")
@Data
public class Word implements Serializable {
  /**
   *
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 词汇
   */
  @TableField(value = "word")
  @Schema(description = "词汇")
  private String word;

  /**
   * 出现次数
   */
  @TableField(value = "count")
  private Integer count;

  /**
   * 创建者
   */
  @TableField(value = "create_by")
  private String createBy;

  /**
   * 更新者
   */
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


  @TableField(value = "is_deleted")
  private Integer isDeleted;

  private Integer  status;

  @TableField(exist = false)
  @Serial
  private static final long serialVersionUID = 1L;

  public static Word toEntity(WordFrequencyResp req) {
    Word word = new Word();
    word.setWord(req.getContent());
    word.setCount(req.getFrequency());
    word.setStatus(req.getStatus());
    return word;
  }
}
