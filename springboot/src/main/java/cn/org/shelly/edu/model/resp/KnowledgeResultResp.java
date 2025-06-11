package cn.org.shelly.edu.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class KnowledgeResultResp {
    @Schema(description = "结果列表")
    private List<KnowledgeSingleResultResp> list;
    @Schema(description = "总通过数")
    private Integer ac;
    @Schema(description = "总未通过数")
    private Integer wa;
    @Schema(description = "总评")
    private String comment;
    @Schema(description = "总星数")
    private Integer stars;
}
