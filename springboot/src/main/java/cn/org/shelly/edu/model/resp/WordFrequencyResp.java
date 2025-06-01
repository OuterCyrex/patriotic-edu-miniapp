package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.Word;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WordFrequencyResp {

    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 频率
     */
    @Schema(description = "频率")
    private Integer frequency;


    /**
     * 状态（0：未审核 1：已审核）
     */
    @Schema(description = "状态（0：停用 1：启用）")
    private Integer status;

    public static WordFrequencyResp toResp(Word wordFrequency){
        return new WordFrequencyResp()
                .setId(wordFrequency.getId())
                .setContent(wordFrequency.getWord())
                .setFrequency(wordFrequency.getCount())
                .setStatus(wordFrequency.getStatus());
    }
}
