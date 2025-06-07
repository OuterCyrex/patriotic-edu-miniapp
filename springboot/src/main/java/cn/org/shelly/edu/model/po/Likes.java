package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


@TableName(value ="likes")
@Data
public class Likes implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "id(前端不用传)")
    private Long id;
    @Schema(description = "id(前端不用传)")
    private Long userId;
    @Schema(description = "点赞对象类型（1心声 2评论）")
    private Integer targetType;

    private Long targetId;

     @Serial
    private static final long serialVersionUID = 1L;
}
