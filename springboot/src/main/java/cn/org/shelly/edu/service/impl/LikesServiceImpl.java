package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.mapper.LikesMapper;
import cn.org.shelly.edu.model.po.Likes;
import cn.org.shelly.edu.service.LikesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Shelly6
* @description 针对表【likes(点赞记录表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes>
    implements LikesService {

}




