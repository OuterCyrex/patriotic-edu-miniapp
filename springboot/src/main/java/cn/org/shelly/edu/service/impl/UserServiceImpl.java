package cn.org.shelly.edu.service.impl;
import cn.dev33.satoken.stp.StpUtil;
import cn.org.shelly.edu.exception.CustomException;
import cn.org.shelly.edu.mapper.UserMapper;
import cn.org.shelly.edu.model.po.SystemConfig;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.req.LoginReq;
import cn.org.shelly.edu.model.resp.UserInfoResp;
import cn.org.shelly.edu.model.req.UserReq;
import cn.org.shelly.edu.service.SystemConfigService;
import cn.org.shelly.edu.service.UserService;
import cn.org.shelly.edu.utils.PasswordUtils;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
  private final SystemConfigService systemConfigService;

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
      User po = UserReq.toPo(param);
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
       if(!old.getUsername().equals(param.getUsername())){
          Long count = lambdaQuery()
            .eq(User::getUsername, param.getUsername())
            .count();
          if(count > 0){
             throw new CustomException("用户名已存在");
          }
      }
      User po = UserReq.toPo(param);
      po.setPassword(old.getPassword());
      updateById(po);
  }
}




