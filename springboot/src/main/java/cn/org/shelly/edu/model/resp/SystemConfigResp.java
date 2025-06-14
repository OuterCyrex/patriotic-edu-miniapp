package cn.org.shelly.edu.model.resp;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Schema(description = "系统配置")
@Accessors(chain = true)
public class SystemConfigResp {
    private Integer id;
    @Schema(description = "每日答题数量，暂时未提供功能支持")
    private Integer dailyQuizCount;
    @Schema (description = "每日答对题数可获得的星星数，暂时未提供功能支持")
    private Integer starsPerCorrect;
    @Schema (description = "服务热线")
    private String hotlinePhone;
    @Schema (description = "心声最长字数")
    private Integer voiceMaxLength;
    @Schema (description = "用户默认头像")
    private String avatar;
}
