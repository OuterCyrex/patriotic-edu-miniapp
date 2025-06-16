package cn.org.shelly.edu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.common.PageInfo;
import cn.org.shelly.edu.common.Result;
import cn.org.shelly.edu.constants.CodeEnum;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.model.po.Announcement;
import cn.org.shelly.edu.model.po.Misc;
import cn.org.shelly.edu.model.po.SystemConfig;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.resp.SystemConfigResp;
import cn.org.shelly.edu.service.AnnouncementService;
import cn.org.shelly.edu.service.MiscService;
import cn.org.shelly.edu.service.SystemConfigService;
import cn.org.shelly.edu.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 系统控制器
 * @author shelly
 */
@RestController
@Tag(name = "系统模块")
@RequestMapping("/system")
@RequiredArgsConstructor
public class SystemController {
    private final AnnouncementService announcementService;
    private final SystemConfigService systemConfigService;
    private final UserService userService;
    private final MiscService miscService;
    @Operation(summary = "获取系统公告")
    @GetMapping("/{id}")
    public Result<Announcement> getSystemAnnouncement(@PathVariable("id") Long id) {
        return Result.success(announcementService.getById(id));
    }
    @Operation(summary = "获取系统公告列表")
    @GetMapping("/list")
    public Result<PageInfo<Announcement>> getSystemAnnouncementList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String key
    ) {
        return Result.page(announcementService.lambdaQuery()
                .like(StringUtils.isNotBlank(key), Announcement::getTitle, key)
                .page(new Page<>(pageNum, pageSize))
        );
    }
    @Operation(summary = "添加系统公告")
    @PostMapping
    public Result<?> addSystemAnnouncement(@RequestBody Announcement announcement) {
        return announcementService.save(announcement) ? Result.success() : Result.fail();
    }
    @Operation(summary = "更新系统公告")
    @PutMapping
    public Result<?> updateSystemAnnouncement(@RequestBody Announcement announcement) {
        return announcementService.updateById(announcement) ? Result.success() : Result.fail();
    }
    @Operation(summary = "删除系统公告")
    @DeleteMapping("/{id}")
    public Result<?> deleteSystemAnnouncement(@PathVariable("id") Long id) {
        return announcementService.removeById(id) ? Result.success() : Result.fail();
    }
    @GetMapping
    @Operation(summary = "获取系统配置")
    public Result<SystemConfigResp> getSystemConfig() {
        SystemConfig systemConfig = systemConfigService.getById(1L);
        return Result.success(SystemConfig.toResp(systemConfig));
    }
    @GetMapping("/update")
    @Operation(summary = "更新系统配置")
    public Result<?> updateSystemConfig() {
        Long uid = StpUtil.getLoginIdAsLong();
        User user = userService.getById(uid);
        if(user.getType() != 1){
            return Result.fail("您无操作权限！");
        }
        SystemConfig systemConfig = systemConfigService.getById(1L);
        systemConfig.setUpdateBy(user.getUsername());
        return systemConfigService.updateById(systemConfig) ? Result.success() : Result.fail();
    }

    @Operation(summary = "上传文件(oss)",description = "上传的文件最大不超过20M")
    @PostMapping("/upload")
    public Result<String> uploadByOss(@RequestParam("file") MultipartFile file) {
        if(file.getSize() > 20971520L) {
            throw new CustomException("上传文件不得超过20M",280);
        }
        try {
            return Result.success(systemConfigService.uploadTiny(file));
        } catch (IOException e) {
            throw new CustomException("上传失败");
        }
    }
    @GetMapping("/misc")
    @Operation(summary = "获取系统杂项")
    public Result<String> getMisc(@RequestParam String key) {
        key = StringUtils.trim(key);
        if (StringUtils.isBlank(key)) {
            return Result.fail("参数错误");
        }
        Misc misc = miscService.getById(key);
        System.out.println(misc);
        if (misc == null) {
            return Result.fail(CodeEnum.DATA_NOT_EXIST);
        }
        return Result.success(misc.getValue());
    }

    @PostMapping("/misc")
    @Operation(summary = "新增系统杂项")
    public Result<String> addMisc(@RequestBody Misc misc) {
        String key = StringUtils.trim(misc.getMiscKey());
        String value = StringUtils.trim(misc.getValue());
        if(!Misc.isJson(value)){
            return Result.fail("value 非 json");
        }
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return Result.fail("参数错误");
        }
        if (miscService.getById(key) != null) {
            return Result.fail("Key 已存在");
        }
        misc.setMiscKey(key);
        misc.setValue(value);
        miscService.save(misc);
        return Result.success("添加成功");
    }

    @PutMapping("/misc")
    @Operation(summary = "更新系统杂项")
    public Result<String> updateMisc(@RequestBody Misc misc) {
        String key = StringUtils.trim(misc.getMiscKey());
        String value = StringUtils.trim(misc.getValue());
        if(!Misc.isJson(value)){
            return Result.fail("value 非 json");
        }
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return Result.fail("参数错误");
        }
        if (miscService.getById(key) == null) {
            return Result.fail("Key 不存在");
        }
        misc.setMiscKey(key);
        misc.setValue(value);
        miscService.updateById(misc);
        return Result.success("更新成功");
    }

    @DeleteMapping("/misc")
    @Operation(summary = "删除系统杂项")
    public Result<String> deleteMisc(@RequestParam String key) {
        key = StringUtils.trim(key);
        if (StringUtils.isBlank(key)) {
            return Result.fail("参数错误");
        }
        if (miscService.getById(key) == null) {
            return Result.fail("Key 不存在");
        }
        miscService.removeById(key);
        return Result.success("删除成功");
    }
    @GetMapping("/misc/list")
    @Operation(summary = "获取系统杂项列表")
    public Result<PageInfo<Misc>> getMiscList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String key
    ) {
        return Result.page(miscService.lambdaQuery()
                .like(StringUtils.isNotBlank(key), Misc::getMiscKey, key)
                .page(new Page<>(pageNum, pageSize))
        );
    }


}
