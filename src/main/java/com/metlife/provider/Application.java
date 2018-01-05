package com.metlife.provider;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Docket apis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("default")
                .select()
                .paths(Predicates.or(
                        PathSelectors.ant("/info"),
                        PathSelectors.ant("/health"),
                        PathSelectors.ant("/quotes"),
                        PathSelectors.ant("/greeting"),
                        PathSelectors.ant("/foo"),
                        PathSelectors.ant("/kube"),
                        PathSelectors.ant("/.well-known/acme-challenge/SYItYSrusYRhXRxPiLAVaY8qAdtPNom1UNKdaJ8XA_w"),
                        PathSelectors.ant("/toggle")
                ))
                .build();
    }

//    @Bean
//    public Docket infoApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("info")
//                .select()
//                .paths(PathSelectors.ant("/info"))
//                .build();
//    }
//    @Bean
//    public Docket healthApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("health")
//                .select()
//                .paths(PathSelectors.ant("/health"))
//                .build();
//    }
}
