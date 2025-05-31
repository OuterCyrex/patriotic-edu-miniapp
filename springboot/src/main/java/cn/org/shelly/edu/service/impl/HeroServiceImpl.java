package cn.org.shelly.edu.service.impl;
import cn.org.shelly.edu.mapper.HeroMapper;
import cn.org.shelly.edu.model.po.Hero;
import cn.org.shelly.edu.service.HeroService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author Shelly6
* @description 针对表【hero(英雄人物表)】的数据库操作Service实现
* @createDate 2025-05-31 15:29:26
*/
@Service
public class HeroServiceImpl extends ServiceImpl<HeroMapper, Hero>
    implements HeroService {

}




