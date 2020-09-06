package com.huiyadan.pcr.extend;

import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.Requests;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * 动漫图片获取
 *
 * @author huiyadanli
 */
@Slf4j
@Component
public class PictureFetcher {

    @Value("${extend.picture.path}")
    String folder;

    @Value("${extend.picture.apis}")
    String[] apis;

    public String getImageUrl() {
        if (apis == null || apis.length == 0) {
            return null;
        }

        return randomApi();
    }

    public byte[] getImageBytes() {
        if (apis == null || apis.length == 0) {
            return null;
        }

        String url = getImageUrl();
        log.info("随机图片地址: {}", url);
        return Requests.get(url).send().readToBytes();
    }

    public File getImageFile() {
        if (apis == null || apis.length == 0) {
            return null;
        }

        try {
            byte[] bytes = getImageBytes();
            File file = Paths.get(folder, String.valueOf(System.currentTimeMillis())).toFile();
            FileUtils.writeByteArrayToFile(file, bytes);
            return file;
        } catch (IOException e) {
            log.error("下载图片出错", e);
            return null;
        }
    }


    private String randomApi() {
        return apis[(int) (Math.random() * apis.length)];
    }
}
