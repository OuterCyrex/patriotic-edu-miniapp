package cn.org.shelly.edu.service;

import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.model.req.LoginReq;
import cn.org.shelly.edu.model.resp.UserInfoResp;
import cn.org.shelly.edu.model.req.UserReq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Shelly6
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-05-31 15:29:26
*/
public interface UserService extends IService<User> {

  UserInfoResp login(LoginReq param);

  void add(UserReq param);

  void updateInfo(UserReq param);
}
