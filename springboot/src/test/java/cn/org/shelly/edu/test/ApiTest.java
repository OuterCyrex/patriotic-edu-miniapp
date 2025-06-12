package cn.org.shelly.edu.test;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CountDownLatch;
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class ApiTest {
    @Resource
    private OpenAiChatModel openAiChatModel;

    @Resource
    private ChatClient chatClient;

    @Test
    public void test_stream1() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<ChatResponse> stream = chatClient.prompt(new Prompt(List.of(new SystemMessage("你是一个助手，请用中文回答问题"),
                        new UserMessage("1 + 1"))))
                .stream()
                .chatResponse();

        stream.subscribe(
                chatResponse -> {
                    AssistantMessage output = chatResponse.getResult().getOutput();
                    log.info("测试结果(stream): {}", JSON.toJSONString(output));
                },
                Throwable::printStackTrace,
                () -> {
                    countDownLatch.countDown();
                    log.info("测试结果(stream): done!");
                }
        );

        countDownLatch.await();
    }
    @Test
    public void test_stream2() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux<String> stream = chatClient.prompt(new Prompt(List.of(new SystemMessage("你是一个助手，请用中文回答问题"),
                        new UserMessage("1 + 1"))))
                .stream()
                .content();

        stream.subscribe(
                content -> {
                    log.info("测试结果(stream): {}", JSON.toJSONString(content));
                },
                Throwable::printStackTrace,
                () -> {
                    countDownLatch.countDown();
                    log.info("测试结果(stream): done!");
                }
        );

        countDownLatch.await();
    }
}
