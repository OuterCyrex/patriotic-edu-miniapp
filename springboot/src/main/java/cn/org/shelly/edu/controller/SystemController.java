package cn.org.shelly.edu.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.common.PageInfo;
import cn.org.shelly.edu.common.Result;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.model.po.Announcement;
import cn.org.shelly.edu.model.po.SystemConfig;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.resp.SystemConfigResp;
import cn.org.shelly.edu.service.AnnouncementService;
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
}
