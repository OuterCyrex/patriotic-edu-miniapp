package cn.org.shelly.edu.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentReq {
    @Schema(description = "心声id")
    @NotNull
    private Long id;
    @Schema(description = "内容")
    @NotNull
    private String content;
    @Schema(description = "评论类型：1心声 2评论")
    @NotNull
    private Integer type;
    @Schema(description = "回复评论的id（没有则为0）")
    private Long replyId;
}
