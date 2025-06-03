package cn.org.shelly.edu.service.impl;

import cn.org.shelly.edu.mapper.StopWordMapper;
import cn.org.shelly.edu.model.po.StopWord;
import cn.org.shelly.edu.model.resp.StopWordResp;
import cn.org.shelly.edu.service.StopWordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
public class StopWordServiceImpl extends ServiceImpl<StopWordMapper, StopWord>
    implements StopWordService {

  @Override
  public IPage<StopWordResp> selectStopWordList(Long pageNum, Long pageSize, String key) {
    return lambdaQuery()
      .select(StopWord::getId, StopWord::getContent, StopWord::getType, StopWord::getState)
      .like(StringUtils.isNotBlank(key), StopWord::getContent, key)
      .orderByDesc(StopWord::getGmtCreate)
      .page(new Page<>(pageNum, pageSize))
      .convert(StopWordResp::toResp);
  }

     @Override
     public Set<String> getStopWords() {
        return lambdaQuery()
                .select(StopWord::getContent)
                .eq(StopWord::getState, 1)
                .list()
                .stream()
                .map(StopWord::getContent)
                .collect(toSet());
    }
}




