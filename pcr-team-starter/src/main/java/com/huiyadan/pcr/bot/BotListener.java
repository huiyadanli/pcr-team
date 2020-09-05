package com.huiyadan.pcr.bot;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.QuoteReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 机器人消息监听
 * @author huiyadanli
 */
@Component
public class BotListener extends SimpleListenerHost {

    @Value("${bot.group}")
    private Long qqGroupId;

    @EventHandler
    public ListeningStatus onGroupMessage(GroupMessageEvent event) {
        // 只对配置的群生效
        if (qqGroupId != event.getGroup().getId()) {
            return ListeningStatus.LISTENING;
        }
        String msgString = event.getMessage().contentToString();
        if (msgString.contains("reply")) {
            // 引用回复
            final QuoteReply quote = new QuoteReply(event.getSource());
            event.getGroup().sendMessage(quote.plus("引用回复"));

        }
        return ListeningStatus.LISTENING;
    }

    //处理在处理事件中发生的未捕获异常
    @Override
    public void handleException(CoroutineContext context, Throwable exception) {
        throw new RuntimeException("在事件处理中发生异常", exception);
    }
}
