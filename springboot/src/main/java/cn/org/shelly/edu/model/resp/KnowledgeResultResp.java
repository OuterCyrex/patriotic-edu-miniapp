package cn.org.shelly.edu.model.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KnowledgeResultResp {
    private Boolean result;
    private String explanation;
    private Long recordId;
}
