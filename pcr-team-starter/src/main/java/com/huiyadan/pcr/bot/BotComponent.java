package com.huiyadan.pcr.bot;

import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author lihh
 */
@Slf4j
@Component
public class BotComponent {

    @Autowired
    private Bot bot;

    @Autowired
    private BotListener botListener;

    @PostConstruct
    public void init() {
        Thread t = new BotThread(bot, botListener);
        t.start();
    }
}
