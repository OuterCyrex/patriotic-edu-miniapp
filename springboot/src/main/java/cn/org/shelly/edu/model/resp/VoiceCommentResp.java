package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.VoiceComment;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Data
public class VoiceCommentResp {
    private Long id;

    @TableField(value = "voice_id")
    private Long voiceId;

    @TableField(value = "user_id")
    private Long userId;

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    private String content;

    /**
     * 父评论ID，0为顶级评论
     */
    @Schema(description = "父评论ID，0为顶级评论")
    private Long parentId;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    private Integer likesCount;

    private List<VoiceCommentResp> children;

    public static VoiceCommentResp toResp(VoiceComment voiceComment) {
        VoiceCommentResp resp = new VoiceCommentResp();
        resp.setId(voiceComment.getId());
        resp.setVoiceId(voiceComment.getVoiceId());
        resp.setUserId(voiceComment.getUserId());
        resp.setContent(voiceComment.getContent());
        resp.setParentId(voiceComment.getParentId());
        resp.setLikesCount(voiceComment.getLikesCount());
        return resp;
    }
}
