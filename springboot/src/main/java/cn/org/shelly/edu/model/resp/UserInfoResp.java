package cn.org.shelly.edu.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoResp {
  private Long id;
  @Schema(description = "邮箱")
  private String username;

  private String nickname;

  private String avatarUrl;
  @Schema(description = "地区")
  private String region;
  @Schema (description = "(用户类型(0：学生 1：管理员)")
  private Integer type;
  @Schema (description = "总星星数")
  private Integer totalStars;
  private String token;
}
