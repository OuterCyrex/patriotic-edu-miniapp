package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.Hero;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
public class HeroPageResp {
  @Schema(description = "主键ID")
  private Long id;

  @Schema(description = "英雄姓名")
  private String name;

  @Schema(description = "历史时期（1-新民主主义革命先驱，2-建设年代守护者，3-改革浪潮弄潮儿，4-强国先锋时代篇）")
  private Integer period;

  @Schema(description = "时期年份")
  private String periodYears;

  @Schema(description = "称号/职务")
  private String title;

  @Schema(description = "名言")
  private String famousQuote;

  @Schema(description = "事迹摘要")
  private String summary;

  @Schema(description = "英雄事迹详情")
  private String story;

  @Schema(description = "牺牲年份，可能为空")
  private String sacrificeYear;

  @Schema(description = "头像图片链接")
  private String avatarUrl;

  @Schema(description = "排序值")
  private Integer sortOrder;

  @Schema(description = "状态（1启用，0禁用）")
  private Integer status;

  @Schema(description = "创建时间")
  private Date gmtCreate;

  public static HeroPageResp fromHero(Hero hero) {
    HeroPageResp heroPageResp = new HeroPageResp();
    heroPageResp.setId(hero.getId());
    heroPageResp.setName(hero.getName());
    heroPageResp.setPeriod(hero.getPeriod());
    heroPageResp.setPeriodYears(hero.getPeriodYears());
    heroPageResp.setTitle(hero.getTitle());
    heroPageResp.setFamousQuote(hero.getFamousQuote());
    heroPageResp.setSummary(hero.getSummary());
    heroPageResp.setStory(hero.getStory());
    heroPageResp.setSacrificeYear(hero.getSacrificeYear());
    heroPageResp.setAvatarUrl(hero.getAvatarUrl());
    heroPageResp.setSortOrder(hero.getSortOrder());
    heroPageResp.setStatus(hero.getStatus());
    heroPageResp.setGmtCreate(hero.getGmtCreate());
    return heroPageResp;
  }
}
