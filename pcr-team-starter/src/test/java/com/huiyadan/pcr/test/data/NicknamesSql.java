package com.huiyadan.pcr.test.data;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lihh
 */
public class NicknamesSql {

    public static void main(String[] args) throws IOException {
        String path = "E:\\Programing\\Mine3\\pcr\\pcr-team\\db\\nicknames.csv";
        List<String> lines = FileUtils.readLines(new File(path), "UTF-8");
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] items = StringUtils.split(line, ",");
            System.out.println(String.format("INSERT INTO nicknames(\"id\", \"jp_name\", \"cn_name\", \"nicknames\") VALUES (%s, '%s', '%s', '%s');",
                    items[0], items[1], items[2], getNicknames(line)));
        }
    }


    private static String getNicknames(String line) {
        int i = line.indexOf(",");
        i = line.indexOf(",", i + 1);
        if (i > 0) {
            i = line.indexOf(",", i + 1);
            return line.substring(i + 1);
        } else {
            return "";
        }
    }

}
