package com.jzli.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/3/20
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class JodaTimeUtils {
    private static DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd");

    public static Date parseDate(String time) {
        Date parse = null;
        if (!ObjectUtils.isEmpty(time)) {
            DateTime dateTime = DateTime.parse(time, format);
            parse = dateTime.toDate();
        }
        return parse;
    }
}
