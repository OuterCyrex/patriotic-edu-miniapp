package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.DefenseVoice;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class VoiceFrontPageResp {

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
    @Schema(description = "学校")
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
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    public static VoiceFrontPageResp fromDefenseVoice(DefenseVoice defenseVoice) {
        VoiceFrontPageResp resp = new VoiceFrontPageResp();
        resp.setId(defenseVoice.getId());
        resp.setContent(defenseVoice.getContent());
        resp.setRegion(defenseVoice.getRegion());
        resp.setIdentity(defenseVoice.getIdentity());
        resp.setAuthorName(defenseVoice.getAuthorName());
        resp.setTheme(defenseVoice.getTheme());
        resp.setLikesCount(defenseVoice.getLikesCount());
        resp.setCommentsCount(defenseVoice.getCommentsCount());
        resp.setIsFeatured(defenseVoice.getIsFeatured());
        resp.setGmtCreate(defenseVoice.getGmtCreate());
        return resp;
    }
}
