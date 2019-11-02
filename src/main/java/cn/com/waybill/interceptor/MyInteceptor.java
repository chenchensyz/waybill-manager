package cn.com.waybill.interceptor;

import cn.com.waybill.tools.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInteceptor implements WebMvcConfigurer {

    @Autowired
    private Environment env;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //web后台接口拦截器
        String webExcludePath = env.getProperty("interceptor.webExcludePath");
        String[] webExcludeArr = webExcludePath.split(",");
        registry.addInterceptor(new WebInterceptor())
                .addPathPatterns("/**").excludePathPatterns(webExcludeArr);
        String apiExcludePath = env.getProperty("interceptor.apiExcludePath");
        registry.addInterceptor(new ApiInterceptor())
                .addPathPatterns("/api/**").excludePathPatterns(apiExcludePath);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将所有/static/** 访问都映射到classpath:/static/ 目录下
        String fileRootPath = env.getProperty(CodeUtil.FILE_MODEL_PATH);
        System.out.println(fileRootPath);
        registry.addResourceHandler("/file/**").addResourceLocations("file:" + fileRootPath);
    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController("/admin", "/static/index.html");
//    }

}


