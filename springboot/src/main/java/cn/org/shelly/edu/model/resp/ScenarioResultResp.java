package cn.org.shelly.edu.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class ScenarioResultResp {
    @Schema(description = "法律依据")
    private String legalBasis;
    @Schema(description = "解决方案")
    private String solution;
    @Schema(description = "是否正确")
    private Boolean result;
    @Schema(description = "记录ID")
    private Long recordId;
}
