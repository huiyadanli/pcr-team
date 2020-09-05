package com.huiyadan.pcr.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huiyadan.pcr.dao.model.DamageEntity;
import com.huiyadan.pcr.service.DayReportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.List;

/**
 * @author huiyadanli
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DayReportServiceTest {

    @Autowired
    private DayReportService dayReportService;

    @Test
    public void add() throws ParseException, JsonProcessingException {
        List<DamageEntity> list = dayReportService.add(DateUtils.parseDate("2020-08-29","yyyy-MM-dd"));
        ObjectMapper mapper = new ObjectMapper();
        log.info("{}",mapper.writeValueAsString(list));
    }
}
