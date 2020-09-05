package com.huiyadan.pcr.test.service;

import com.huiyadan.pcr.service.ArchiveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huiyadanli
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArchiveServiceTest {

    @Autowired
    private ArchiveService archiveService;

    @Test
    public void archiveFromDatabase() {
        archiveService.archiveFromDatabase();
    }

    @Test
    public void archiveFromBigfun() {
        archiveService.archiveFromBigfun();
    }
}
