package cn.org.shelly.edu.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.common.Result;
import cn.org.shelly.edu.constants.CodeEnum;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.req.LoginReq;
import cn.org.shelly.edu.model.req.PasswordReq;
import cn.org.shelly.edu.model.resp.UserInfoResp;
import cn.org.shelly.edu.model.req.UserReq;
import cn.org.shelly.edu.service.UserService;
import cn.org.shelly.edu.utils.PasswordUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
/**
 * 用户控制器
 * @author shelly
 */
@RestController
@RequestMapping("/user")
@Tag( name = "用户管理")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;
    @SaIgnore
    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<UserInfoResp> login(@RequestBody LoginReq param){
      return Result.success(userService.login(param));
    }

    @PostMapping("/add")
     @Operation(summary = "添加管理员")
    public Result<?> add(@RequestBody UserReq param){
      userService.add(param);
      return Result.success();
    }

    @PutMapping("/update")
    @Operation(summary = "更新管理员信息")
    public Result<?> update(@RequestBody UserReq param){
      userService.updateInfo(param);
      return Result.success();
    }

    @PutMapping("/updatePassword")
    @Operation(summary = "修改密码")
    public Result<?> updatePassword(@RequestBody PasswordReq param){
      User user = userService.getById(StpUtil.getLoginIdAsString());
      if(!PasswordUtils.match(param.oldPassword(),user.getPassword())){
        return Result.fail(CodeEnum.PASSWORD_ERROR);
      }
      if(!param.confirmPassword().equals(param.newPassword())){
        return  Result.fail(CodeEnum.PASSWORD_NOT_MATCH);
      }
      user.setPassword(PasswordUtils.encrypt(param.newPassword()));
      return Result.isSuccess(userService.updateById(user));
    }

}
