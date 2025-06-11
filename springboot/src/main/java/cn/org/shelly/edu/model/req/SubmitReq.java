package cn.org.shelly.edu.model.req;

import lombok.Data;

@Data
public class SubmitReq {
    private Long questionId;
    private Integer answer;
}
