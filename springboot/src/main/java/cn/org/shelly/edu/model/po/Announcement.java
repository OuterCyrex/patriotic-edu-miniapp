package cn.org.shelly.edu.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 平台公告表
 * @TableName announcement
 */
@TableName(value ="announcement")
@Data
public class Announcement implements Serializable {
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 公告简介
     */
    @TableField(value = "summary")
    private String summary;

    /**
     * 公告正文
     */
    @TableField(value = "content")
    private String content;

    /**
     * 封面图URL
     */
    @TableField(value = "cover_url")
    private String coverUrl;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified")
    private Date gmtModified;
    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}