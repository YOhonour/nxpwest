package nxp.west.infobase.nxpwest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@SpringBootApplication
@EnableSwagger2
@ServletComponentScan
public class NxpwestApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NxpwestApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        //传入SpringBoot应用的主程序       
        return application.sources(NxpwestApplication.class);
    }
}
