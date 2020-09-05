package com.huiyadan.pcr.bot;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.Events;

/**
 * @author lihh
 */
@Slf4j
public class BotThread extends Thread {

    private Bot bot;

    private BotListener botListener;

    public BotThread(Bot bot, BotListener botListener) {
        this.bot = bot;
        this.botListener = botListener;
    }

    public void run() {
        bot.login();
        bot.getGroups().forEach(group -> log.info("{} {}", group.getId(), group.getName()));
        Events.registerEvents(bot, botListener);
        bot.join(); // 阻塞当前线程直到 bot 离线
    }
}
