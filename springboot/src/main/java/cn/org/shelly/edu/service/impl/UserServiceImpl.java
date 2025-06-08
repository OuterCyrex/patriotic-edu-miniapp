package cn.org.shelly.edu.service.impl;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.constants.CodeEnum;
import cn.org.shelly.edu.constants.RedisConstants;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.UserMapper;
import cn.org.shelly.edu.model.po.SystemConfig;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.req.LoginReq;
import cn.org.shelly.edu.model.resp.UserInfoResp;
import cn.org.shelly.edu.model.req.UserReq;
import cn.org.shelly.edu.service.SystemConfigService;
import cn.org.shelly.edu.service.UserService;
import cn.org.shelly.edu.utils.EmailUtils;
import cn.org.shelly.edu.utils.PasswordUtils;
import cn.org.shelly.edu.utils.RedisUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author Shelly6
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
@RequiredArgsConstructor
@SuppressWarnings("all")
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
  private final SystemConfigService systemConfigService;
  private final RedisUtil redisUtil;

    @Override
    public UserInfoResp login(LoginReq param) {
        User user = lambdaQuery()
          .eq(User::getUsername, param.getUsername())
          .one();
        if (user == null) {
          throw  new CustomException("用户不存在");
        }
        if (!PasswordUtils.match( param.getPassword(), user.getPassword())) {
          throw  new CustomException("密码错误");
        }
        StpUtil.login(user.getId());
        return new UserInfoResp()
              .setId(user.getId())
              .setUsername(user.getUsername())
              .setNickname(user.getNickname())
              .setPhone(user.getPhone())
              .setEmail(user.getEmail())
              .setAvatarUrl(user.getAvatarUrl())
              .setRegion(user.getRegion())
              .setType(user.getType())
              .setTotalStars(user.getTotalStars())
              .setToken(StpUtil.getTokenValue());
    }

  @Override
  public void add(UserReq param) {
      if(StringUtils.isBlank(param.getPassword()) || StringUtils.isBlank(param.getUsername())){
        throw new CustomException("用户名或密码不能为空");
      }
      Long count = lambdaQuery()
          .eq(User::getUsername, param.getUsername())
          .count();
      if(count > 0){
         throw new CustomException("用户已存在");
      }
      User po = UserReq.toAdminPo(param);
      po.setPassword(PasswordUtils.encrypt(param.getPassword()));
      if(StringUtils.isBlank(po.getAvatarUrl())){
          String avatarUrl = systemConfigService.lambdaQuery()
            .eq(SystemConfig::getId,1)
            .one()
            .getAvatar();
          if(StringUtils.isNotBlank(avatarUrl)){
             po.setAvatarUrl(avatarUrl);
          }
      }
      save(po);
  }

  @Override
  public void updateInfo(UserReq param) {
      User old = lambdaQuery()
          .eq(User::getId, param.getId())
          .one();
      if(old == null){
         throw new CustomException("用户不存在");
      }
      User po = UserReq.toUserPo(param);
      po.setUsername(old.getUsername());
      updateById(po);
  }

    @Override
    public void register(UserReq param) {
        String code = param.getCode();
        if(StringUtils.isBlank(code)){
          throw new CustomException("验证码不能为空");
        }
        if(StringUtils.isBlank(param.getUsername())){
            throw new CustomException("邮箱不能为空");
        }
        EmailUtils.isValidEmail(param.getUsername());
        String redisKeyPrefix = RedisConstants.VERIFICATION_CODE.getKey();
        // 从缓存中获取验证码
        String redisKey = redisKeyPrefix + param.getUsername();
        if (redisUtil.getObject(redisKey) == null || redisUtil.getTime(redisKey) == 0) {
            throw new CustomException(CodeEnum.CODE_EXPIRED);
        }
        // 验证验证码是否匹配
        if (!code.equals(redisUtil.getObject(redisKey).toString())) {
            throw new CustomException(CodeEnum.CODE_ERROR);
        }
        if(StringUtils.isBlank(param.getUsername())){
          throw new CustomException("密码不能为空");
        }
        Long count = lambdaQuery()
            .eq(User::getUsername, param.getUsername())
            .count();
        if(count > 0){
           throw new CustomException("邮箱已存在");
        }
        User po = UserReq.toUserPo(param);
        po.setType(0);
        po.setPassword(PasswordUtils.encrypt(param.getPassword()));

        if(StringUtils.isBlank(po.getAvatarUrl())){
            String avatarUrl = systemConfigService.lambdaQuery()
              .eq(SystemConfig::getId,1)
              .one()
              .getAvatar();
            if(StringUtils.isNotBlank(avatarUrl)){
               po.setAvatarUrl(avatarUrl);
            }
        }
        save(po);
    }
}




