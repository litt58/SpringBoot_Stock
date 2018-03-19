package com.jzli.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2018/3/15
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
public class PatternTest {
    public static void main(String[] args) {
        String line = "#name#,#content#";
        String pattern = "#(.*?)#";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        while (m.find()) {
            for(int i=0,n=m.groupCount();i<=n;i++){
                System.out.println(m.group(i));
            }
        }
    }
}
