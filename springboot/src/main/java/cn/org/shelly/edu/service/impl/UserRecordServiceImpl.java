package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.mapper.UserRecordMapper;
import cn.org.shelly.edu.model.po.UserRecord;
import cn.org.shelly.edu.service.UserRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Shelly6
* @description 针对表【user_record(用户答题记录表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
public class UserRecordServiceImpl extends ServiceImpl<UserRecordMapper, UserRecord>
    implements UserRecordService {

}




