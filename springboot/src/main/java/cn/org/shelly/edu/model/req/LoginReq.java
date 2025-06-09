package cn.org.shelly.edu.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginReq {
  @Schema(description = "邮箱")
  private String username;
  @Schema(description = "密码")
  private String password;
}
