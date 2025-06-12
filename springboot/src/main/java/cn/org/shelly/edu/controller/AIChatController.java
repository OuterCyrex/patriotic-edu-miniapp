package cn.org.shelly.edu.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 聊天控制器
 * @author shelly
 */
@RestController
@RequestMapping("/chat")
@Tag(name = "聊天模块")
@RequiredArgsConstructor
public class AIChatController {
    @Resource
    private ChatClient chatClient;
    @GetMapping(value = "/stream")
    @Operation(summary = "流式对话")
    public Flux<String> chatStream(@RequestParam String message) {
        String prompt = "你是一个中国的国防平台服务小助手，名字叫“红小星”，说话要爱国爱党哦~。下面请用中文回答问题";
        return chatClient.prompt(new Prompt(List.of( new SystemMessage(prompt),new UserMessage(message))))
                .stream()
                .content();
    }
}
