package org.board.project.configs;

import org.board.project.commons.interceptors.CommonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(FileUploadConfig.class)
@EnableJpaAuditing
@EnableScheduling
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private FileUploadConfig fileUploadConfig;

    @Autowired
    private CommonInterceptor commonInterceptor;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //controller 없이 연결하기
        registry.addViewController("/")
                .setViewName("front/main/index");

        registry.addViewController("/mypage")
                .setViewName("front/main/index");

        registry.addViewController("/admin")
                .setViewName("front/main/index");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**"); //모든 파일의 경로에서 적용됨.
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //System.out.println("fileUploadConfig : "+fileUploadConfig);
        //fileUploadConfig : FileUploadConfig(path=C:/uploads/, url=/uploads/)

                                                //업로드 경로를 포함한 모든 경로
        registry.addResourceHandler(fileUploadConfig.getUrl()+"**")
                .addResourceLocations("file:///"+fileUploadConfig.getPath());
    }


    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();

        ms.setDefaultEncoding("UTF-8");
        ms.addBasenames("messages.commons","messages.validations","messages.errors");

        return ms;
    }
}
