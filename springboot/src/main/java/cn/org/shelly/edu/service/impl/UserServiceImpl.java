package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.mapper.UserMapper;
import cn.org.shelly.edu.model.po.User;
import cn.org.shelly.edu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Shelly6
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




