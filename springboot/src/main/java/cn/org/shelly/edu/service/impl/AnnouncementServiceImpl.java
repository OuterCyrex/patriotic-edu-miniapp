package cn.org.shelly.edu.service.impl;

import cn.org.shelly.edu.mapper.AnnouncementMapper;
import cn.org.shelly.edu.model.po.Announcement;
import cn.org.shelly.edu.service.AnnouncementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService {

}


