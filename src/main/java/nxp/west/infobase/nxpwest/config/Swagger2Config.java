package nxp.west.infobase.nxpwest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("nxp.west.infobase.nxpwest.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("NXPWest 接口文档")
                .description("登陆保持使用Sesion" +
                        "<br>注意：所有接口参数都可以使用mapJson提交，这个字符串是包装了参数的Map转Json串后使用UTF-8编码得到" +
                        "<br>比如map转的Json为：{\"name\":\"zdb\",\"age\":\"26\"}\n" +
                        "<br>UTF8编码后 %7B%22name%22%3A%22zdb%22%2C%22age%22%3A%2226%22%7D" +
                        "<br>提交方式为直接请求：http://172.0.0.1/8080/nxpwest/test?mapJson=%7B%22name%22%3A%22zdb%22%2C%22age%22%3A%2226%22%7D")
//                .termsOfServiceUrl("https://github.com/YOhonour")
                .contact("l")
                .version("1.0")
                .build();
    }
}
