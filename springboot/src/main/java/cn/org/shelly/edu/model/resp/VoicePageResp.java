package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.DefenseVoice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class VoicePageResp {

    private Long id;

    /**
     * 心声内容
     */
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
     * 审核状态：0待审核 1已通过 2已拒绝
     */
    @Schema(description = "审核状态  0待审核 1已通过 2已拒绝")
    private Integer status;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    private String avatar;

    public static VoicePageResp fromDefenseVoice(DefenseVoice defenseVoice) {
        VoicePageResp resp = new VoicePageResp();
        resp.setId(defenseVoice.getId());
        resp.setContent(defenseVoice.getContent());
        resp.setRegion(defenseVoice.getRegion());
        resp.setIdentity(defenseVoice.getIdentity());
        resp.setAuthorName(defenseVoice.getAuthorName());
        resp.setTheme(defenseVoice.getTheme());
        resp.setLikesCount(defenseVoice.getLikesCount());
        resp.setCommentsCount(defenseVoice.getCommentsCount());
        resp.setIsFeatured(defenseVoice.getIsFeatured());
        resp.setStatus(defenseVoice.getStatus());
        resp.setGmtCreate(defenseVoice.getGmtCreate());
        resp.setAvatar(defenseVoice.getAvatar());
        return resp;
    }
}
