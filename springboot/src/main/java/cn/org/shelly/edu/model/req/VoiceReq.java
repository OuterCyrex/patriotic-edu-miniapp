package cn.org.shelly.edu.model.req;

import cn.org.shelly.edu.model.po.DefenseVoice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoiceReq {

  /**
   * 心声内容
   */
  @Schema(description = "心声内容")
  @NotNull
  private String content;

  /**
   * 地区
   */
  @Schema(description = "地区")
  @NotNull
  private String region;

  /**
   * 身份
   */
  @Schema(description = "身份(XX中学)")
  @NotNull
  private String identity;

  /**
   * 作者姓名
   */
  @NotNull
  private String authorName;

  /**
   * 主题分类
   */
  @Schema(description = "主题分类")
  private String theme;

  public DefenseVoice toDefenseVoice(VoiceReq req) {
    DefenseVoice defenseVoice = new DefenseVoice();
    defenseVoice.setContent(req.getContent());
    defenseVoice.setRegion(req.getRegion());
    defenseVoice.setIdentity(req.getIdentity());
    defenseVoice.setAuthorName(req.getAuthorName());
    defenseVoice.setTheme(req.getTheme());
    defenseVoice.setLikesCount(0);
    defenseVoice.setCommentsCount(0);
    defenseVoice.setIsFeatured(0);
    defenseVoice.setStatus(1);
    return defenseVoice;
  }

}
