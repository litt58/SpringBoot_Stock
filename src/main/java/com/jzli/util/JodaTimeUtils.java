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

    /**
     * 默认开始时间
     */
    public static final String DEFAULT_START = "20150101";
    /**
     * 默认结束时间
     */
    public static final String DEFAULT_END = "20190101";

    public static Date parseDate(String time) {
        Date parse = null;
        if (!ObjectUtils.isEmpty(time)) {
            DateTime dateTime = DateTime.parse(time, format);
            parse = dateTime.toDate();
        }
        return parse;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return
     */
    public static String getDateString(Date date) {
        String result = null;
        if (!ObjectUtils.isEmpty(date)) {
            DateTime dateTime = new DateTime(date);
            result = dateTime.toString(format);
        }
        return result;
    }
}
