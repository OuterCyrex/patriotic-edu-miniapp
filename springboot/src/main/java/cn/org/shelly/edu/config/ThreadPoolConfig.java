package cn.org.shelly.edu.config;

import cn.org.shelly.edu.config.properties.ThreadPoolProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 * @author shelly
 */

@Configuration
@RequiredArgsConstructor
@EnableAsync
public class ThreadPoolConfig {

    private final ThreadPoolProperties threadPoolProperties;

    /**
     * 创建线程池
     * @return 线程池
     */
    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        executor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 最大可创建的线程数
        executor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        // 等待队列最大长度
        executor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        // 线程池维护线程所允许的空闲时间
        executor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}