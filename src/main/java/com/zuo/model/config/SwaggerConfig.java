package com.zuo.model.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 //添加swagger启用注解
//@Profile({"dev", "test"}) //方式一
//@ConditionalOnProperty(name = "swagger2.enable", havingValue = "true") //方式二
public class SwaggerConfig {

    //读取yml文件配置
    @Value("${swagger2.enable}")
    private boolean swagger2Enable;

    /**
     * .enable()  控制是否进行初始化
     * .select()  初始化并返回一个API选择构造器
     * .paths(PathSelectors.any())   设置路径筛选器
     * .apis(RequestHandlerSelectors.basePackage("com.xxx.xxx.xxx"))  添加路径选择条件
     * .build();    构建
     *
     * PathSelectors 类的方法：
     *  - Predicate<String> any():满足条件的路径，该断言总为true
     *  - Predicate<String> none():不满足条件的路径,该断言总为false
     *  - Predicate<String> regex(final String pathRegex):符合正则的路径
     *
     * RequestHandlerSelectors 类的方法：
     *  - Predicate<RequestHandler> any()：返回包含所有满足条件的请求处理器的断言，该断言总为true
     *  - Predicate<RequestHandler> none()：返回不满足条件的请求处理器的断言,该断言总为false
     *  - Predicate<RequestHandler> basePackage(final String basePackage)：返回一个断言(Predicate)，该断言包含所有匹配basePackage下所有类的请求路径的请求处理器
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swagger2Enable)  //方式三
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zuo.model.controller"))  // 注意修改此处的包名
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("项目模板集成系统")
                .description("API接口文档")
                .version("1.0.0")
                .build();
    }
}

