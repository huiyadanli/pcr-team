package com.huiyadan.pcr.test.service;

import com.huiyadan.pcr.api.bigfun.team.ClanReportFetcher;
import com.huiyadan.pcr.api.bigfun.team.model.ClanViewer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author huiyadanli
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClanReportFetcherTest {

    @Autowired
    private ClanReportFetcher clanReportFetcher;

    @Test
    public void print() {
        List<ClanViewer> nicknameWithUid = clanReportFetcher.get();
        for (ClanViewer clanViewer : nicknameWithUid) {
            System.out.println(clanViewer.getViewer_id() + "\t" + clanViewer.getUsername());
        }
    }
}
