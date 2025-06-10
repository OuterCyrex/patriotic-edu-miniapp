package cn.org.shelly.edu.model.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionResp {
    private Integer ac;
    private Integer wa;
    private String comment;
    private Integer stars;
    @Schema(description = "总时间（单位/s）")
    private Long time;
    public static int calculateStars(int ac) {
        if (ac >= 10) return 3;
        if (ac >= 8) return 2;
        if (ac >= 6) return 1;
        return 0;
    }
    public static String getCommentByAcs(int ac) {
        return switch (ac) {
            case 3 -> "🎖️ 太棒啦！你就是我们的国防小英雄，知识储备满满，祖国为你点赞！";
            case 2 -> "💪 很不错哦！离小卫士只差一步之遥，继续加油，为祖国积蓄更多能量！";
            case 1 -> "🌱 初心萌芽，未来可期！多练多看，你就是下一个国防小达人！";
            default -> "📚 知识需要补给啦！别灰心，重整旗鼓，为热爱祖国再出发！";
        };
    }


}
