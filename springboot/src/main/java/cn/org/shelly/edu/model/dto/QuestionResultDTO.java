package cn.org.shelly.edu.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class QuestionResultDTO {
    private Boolean result;
    private Integer userAnswer;
    private Long questionId;
    private Long recordId;
}
