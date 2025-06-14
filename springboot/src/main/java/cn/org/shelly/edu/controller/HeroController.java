package cn.org.shelly.edu.controller;

import cn.org.shelly.edu.common.PageInfo;
import cn.org.shelly.edu.common.Result;
import cn.org.shelly.edu.constants.CodeEnum;
import cn.org.shelly.edu.model.po.Hero;
import cn.org.shelly.edu.model.resp.HeroFrontPageResp;
import cn.org.shelly.edu.model.resp.HeroPageResp;
import cn.org.shelly.edu.model.resp.HeroResp;
import cn.org.shelly.edu.service.HeroService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 英雄控制器
 *  @author shelly
 */
@RequestMapping("/hero")
@RestController
@Tag(name = "英雄模块")
@RequiredArgsConstructor
public class HeroController {

    private final HeroService heroService;
    @GetMapping("/admin/{id}")
    @Operation(summary = "后台获取英雄信息")
    public Result<Hero> getHeroById(@PathVariable("id") Integer id){
      Hero hero = heroService.getById(id);
      if (hero == null || hero.getIsDeleted() == 1){
        return Result.fail(CodeEnum.DATA_NOT_EXIST);
      }
      return Result.success(hero);
    }
    @GetMapping("/{id}")
    @Operation(summary = "前台获取英雄信息")
    public Result<HeroResp> getHero(@PathVariable("id") Integer id){
      Hero hero = heroService.getById(id);
      if (hero == null || hero.getIsDeleted() == 1 || hero.getStatus() == 0){
        return Result.fail("英雄不存在");
      }
      return Result.success(HeroResp.fromHero(hero));
    }
    @PostMapping
    @Operation(summary = "添加英雄")
    public Result<Hero> addHero(@RequestBody Hero hero){
      heroService.save(hero);
      return Result.success(hero);
    }
    @PutMapping
    @Operation(summary = "修改英雄")
    public Result<Hero> updateHero(@RequestBody Hero hero){
      heroService.updateById(hero);
      return Result.success(hero);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "删除英雄")
    public Result<Hero> deleteHero(@PathVariable("id") Integer id){
      Hero hero = heroService.getById(id);
      if (hero == null || hero.getIsDeleted() == 1){
        return Result.fail("英雄不存在");
      }
      hero.setIsDeleted(1);
      heroService.updateById(hero);
      return Result.success(hero);
    }

    @GetMapping("/list")
    @Operation(summary = "前台获取英雄列表")
    public Result<PageInfo<HeroFrontPageResp>> getHeroList(
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "period", defaultValue = "1") Integer period,
      @RequestParam(required = false, defaultValue = "") String name

    ){
      var page = heroService.lambdaQuery()
        .eq(Hero::getPeriod, period)
        .like(StringUtils.isNotBlank(name),  Hero::getName, name)
        .orderByDesc(Hero::getSortOrder)
        .eq(Hero::getStatus, 1)
        .eq(Hero::getIsDeleted, 0)
        .page(new Page<>(pageNum, pageSize))
        .convert(HeroFrontPageResp::fromHero);
      return Result.page(page);
    }
    @GetMapping("/admin/list")
    @Operation(summary = "后台获取英雄列表")
    public Result<PageInfo<HeroPageResp>> getList(
      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
      @RequestParam(value = "period", defaultValue = "1") Integer period,
      @RequestParam(required = false, defaultValue = "") String name,
      @RequestParam(required = false, defaultValue = "-1") Integer status

    ){
      var page = heroService.lambdaQuery()
        .eq(Hero::getPeriod, period)
        .like(StringUtils.isNotBlank(name),  Hero::getName, name)
        .orderByDesc(Hero::getSortOrder)
        .eq(Hero::getIsDeleted, 0)
        .eq(status != -1, Hero::getStatus, status)
        .page(new Page<>(pageNum, pageSize))
        .convert(HeroPageResp::fromHero);
      return Result.page(page);
    }


}
