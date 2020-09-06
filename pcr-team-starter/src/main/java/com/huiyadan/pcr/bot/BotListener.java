package com.huiyadan.pcr.bot;

import com.huiyadan.pcr.extend.PictureFetcher;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.QuoteReply;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 机器人消息监听
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class BotListener extends SimpleListenerHost {

    @Value("${bot.group}")
    private Long qqGroupId;

    @Autowired
    private PictureFetcher pictureFetcher;

    @EventHandler
    public ListeningStatus onGroupMessage(GroupMessageEvent event) {
        // 只对配置的群生效
        if (qqGroupId != event.getGroup().getId()) {
            return ListeningStatus.LISTENING;
        }
        try {
            String msgString = event.getMessage().contentToString();
            if (msgString.contains("reply")) {
                // 引用回复
                final QuoteReply quote = new QuoteReply(event.getSource());
                event.getGroup().sendMessage(quote.plus("引用回复"));

            } else if (msgString.contains("来点图片")) {
                File file = null;
                try {
                    file = pictureFetcher.getImageFile();
                } catch (Exception e) {
                    log.error("图片下载失败", e);
                    event.getGroup().sendMessage("图片下载失败！");
                }
                if (file != null) {
                    final Image image = event.getGroup().uploadImage(file);
                    // 上传一个图片并得到 Image 类型的 Message
                    final String imageId = image.getImageId(); // 可以拿到 ID
                    final Image fromId = MessageUtils.newImage(imageId); // ID 转换得到 Image
                    event.getGroup().sendMessage(image); // 发送图片
                }
            }
        } catch (Exception e) {
            log.error("bot消息处理异常", e);
        }
        return ListeningStatus.LISTENING;
    }

    //处理在处理事件中发生的未捕获异常
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        throw new RuntimeException("在事件处理中发生异常", exception);
    }
}
