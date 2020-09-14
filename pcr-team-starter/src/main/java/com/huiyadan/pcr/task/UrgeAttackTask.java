package com.huiyadan.pcr.task;

import com.huiyadan.pcr.executor.AttackTaskExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 群内催刀
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class UrgeAttackTask {

    @Autowired
    private AttackTaskExecutor attackTaskExecutor;

    @Scheduled(cron = "${task.urge.cron}")
    public void execute() {
        try {
            attackTaskExecutor.urge();
        } catch (Exception e) {
            log.error("自动催刀执行异常", e);
        }
    }

}
