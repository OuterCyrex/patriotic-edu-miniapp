package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.DefenseVoice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class VoiceDetailResp {

    private Long id;

    /**
     * 心声内容
     */
    @Schema(description = "心声内容")
    private String content;

    /**
     * 地区
     */
    @Schema(description = "地区")
    private String region;

    /**
     * 身份
     */
    @Schema(description = "身份")
    private String identity;

    /**
     * 作者姓名
     */
    private String authorName;

    /**
     * 主题分类
     */
    @Schema(description = "主题分类")
    private String theme;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    private Integer likesCount;

    /**
     * 评论数
     */
    @Schema(description = "评论数")
    private Integer commentsCount;

    /**
     * 是否精选：1是 0否
     */
    @Schema(description = "是否精选：1是 0否")
    private Integer isFeatured;
    /**
     * 创建时间
     */
    private Date gmtCreate;

    public static VoiceDetailResp fromDefenseVoice(DefenseVoice byId) {
        VoiceDetailResp resp = new VoiceDetailResp();
        resp.setId(byId.getId());
        resp.setContent(byId.getContent());
        resp.setRegion(byId.getRegion());
        resp.setIdentity(byId.getIdentity());
        resp.setAuthorName(byId.getAuthorName());
        resp.setTheme(byId.getTheme());
        resp.setLikesCount(byId.getLikesCount());
        resp.setCommentsCount(byId.getCommentsCount());
        resp.setIsFeatured(byId.getIsFeatured());
        resp.setGmtCreate(byId.getGmtCreate());
        return resp;
    }
}
