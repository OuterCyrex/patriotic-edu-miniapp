package cn.org.shelly.edu.model.po;

import cn.org.shelly.edu.model.resp.StopWordResp;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName(value ="stop_word")
@Data
public class StopWord implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 停词内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 停词类型(0：数字、1：符号、2：英文、3：中文、4：通用)
     */
    @TableField(value = "type")
    @Schema (description = "停词类型")
    private Integer type;

    /**
     * 停词状态(0：弃用、1：启用)
     */
    @TableField(value = "state")
    @Schema (description = "停词状态")
    private Integer state;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 最近更新时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;

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
     * 逻辑删除
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

  public static StopWord toEntity(StopWordResp req) {
    StopWord stopWord = new StopWord();
    stopWord.setContent(req.getContent());
    stopWord.setType(req.getType());
    stopWord.setState(req.getState());
    return stopWord;
  }
}
