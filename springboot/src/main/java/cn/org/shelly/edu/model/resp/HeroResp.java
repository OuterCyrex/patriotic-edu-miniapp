package cn.org.shelly.edu.model.resp;

import cn.org.shelly.edu.model.po.Hero;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 前台返回
 *  @author shelly
 */
@Data
public class HeroResp {
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

  public static  HeroResp fromHero(Hero hero) {
    HeroResp heroResp = new HeroResp();
    heroResp.setId(hero.getId());
    heroResp.setName(hero.getName());
    heroResp.setPeriod(hero.getPeriod());
    heroResp.setPeriodYears(hero.getPeriodYears());
    heroResp.setTitle(hero.getTitle());
    heroResp.setFamousQuote(hero.getFamousQuote());
    heroResp.setSummary(hero.getSummary());
    heroResp.setStory(hero.getStory());
    heroResp.setSacrificeYear(hero.getSacrificeYear());
    heroResp.setAvatarUrl(hero.getAvatarUrl());
    return heroResp;
  }
}
