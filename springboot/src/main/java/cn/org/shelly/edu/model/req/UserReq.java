package cn.org.shelly.edu.model.req;

import cn.org.shelly.edu.model.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 添加管理员
 * @author shelly
 */
@Data
public class UserReq {
  private Long id;

  private String username;

  private String nickname;

  private String phone;

  private String email;

  private String avatarUrl;
  @Schema(description = "地区")
  private String region;
  @Schema (description = "总星星数")
  private Integer totalStars;

  private String password;

  public static User toPo(UserReq req) {
    User po = new User();
    po.setId(req.getId());
    po.setUsername(req.getUsername());
    po.setNickname(req.getNickname());
    po.setPhone(req.getPhone());
    po.setEmail(req.getEmail());
    po.setAvatarUrl(req.getAvatarUrl());
    po.setRegion(req.getRegion());
    po.setTotalStars(req.getTotalStars());
    po.setType(1);
    po.setStatus(1);
    return po;
  }
}
