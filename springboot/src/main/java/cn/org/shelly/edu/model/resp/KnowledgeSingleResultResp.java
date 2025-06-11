package cn.org.shelly.edu.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class KnowledgeSingleResultResp {
    private Boolean result;
    private String explanation;
    @Schema(description = "记录id")
    private Long recordId;
    @Schema(description = "题目ID")
    private Long questionId;
}
