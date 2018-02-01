package com.jzli.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * =======================================================
 *
 * @Company 产品技术部
 * @Date ：2017/7/17
 * @Author ：李金钊
 * @Version ：0.0.1
 * @Description ：
 * ========================================================
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //配置get方法中参数有.的读取问题
        configurer.setUseSuffixPatternMatch(false);
        super.configurePathMatch(configurer);
    }
}
