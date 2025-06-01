package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@TableName(value ="user")
@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private String avatarUrl;
    @Schema (description = "地区")
    private String region;
    @Schema (description = "(用户类型(0：学生 1：管理员)")
    private Integer type;

    @Schema (description = "总星星数")
    private Integer totalStars;

    private Integer status;

    private Date gmtCreate;

    private Date gmtModified;

    private String password;

    @Serial
    private static final long serialVersionUID = 1L;
}
