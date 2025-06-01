package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.StopWord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StopWordResp {
    /**
     * id
     */
    @Schema(description = "id(添加时删除这一行)")
    private Long id;

    /**
     * 停词内容
     */
    private String content;

    /**
     * 停词类型(0：数字、1：符号、2：英文、3：中文、4：通用)
     */
    @Schema(description = "停词类型(0：数字、1：符号、2：英文、3：中文、4：通用)")
    private Integer type;

    /**
     * 停词状态(0：弃用、1：启用)
     */
    @Schema(description = "停词状态(0：弃用、1：启用)")
    private Integer state;

    public static StopWordResp toResp(StopWord stopWord){
        return new StopWordResp()
                .setId(stopWord.getId())
                .setContent(stopWord.getContent())
                .setType(stopWord.getType())
                .setState(stopWord.getState());
    }

}
