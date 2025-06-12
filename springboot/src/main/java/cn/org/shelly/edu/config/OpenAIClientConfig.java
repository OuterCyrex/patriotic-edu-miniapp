package cn.org.shelly.edu.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAI 配置
 * @author shelly
 */
@Configuration
public class OpenAIClientConfig {

    //------------------------------------基础配置-------------------------------------------------------
    @Bean(name = "chatClient")
    public ChatClient chatClient(OpenAiChatModel model, ToolCallbackProvider toolCallbackProvider, @Value("${spring.ai.openai.chat.options.model}") String modelName) {
        return ChatClient.builder(model)
                .defaultOptions(OpenAiChatOptions.builder().model(modelName).build())
                .defaultTools(toolCallbackProvider.getToolCallbacks())
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

}
