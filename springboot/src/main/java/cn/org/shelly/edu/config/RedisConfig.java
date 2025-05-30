package cn.org.shelly.edu.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Redis配置类
 * @author shelly
 */
@Configuration
public class RedisConfig implements CachingConfigurer {

    private static final String STANDARD_PATTERN = "yyyy/MM/dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy/MM/dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 使用Jackson2JsonRedisSerialize 替换默认序列化

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.registerLocalDateTime(objectMapper);

        // 构造器实现(新方式)
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        template.setConnectionFactory(connectionFactory);
        // key采用String的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        // value序列化方式采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 处理时间类型
     *
     * @param objectMapper 生成树
     */
    private void registerLocalDateTime(ObjectMapper objectMapper) {
        // 设置java.util.Date时间类的序列化以及反序列化的格式
        objectMapper.setDateFormat(new SimpleDateFormat(STANDARD_PATTERN));

        JavaTimeModule timeModule = new JavaTimeModule();
        // LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(STANDARD_PATTERN);
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        // LocalDate
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
        // LocalTime
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        objectMapper.registerModule(timeModule);

    }
}