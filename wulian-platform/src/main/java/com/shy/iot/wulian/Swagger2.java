package com.shy.iot.wulian;

//swagger2的配置文件，在项目的启动类的同级文件建立

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {
//swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
  @Bean
  public Docket createRestApi() {
      ParameterBuilder tokenPar = new ParameterBuilder();
      ParameterBuilder tokenPar1= new ParameterBuilder();
      ParameterBuilder tokenPar2= new ParameterBuilder();
      ParameterBuilder tokenPar3= new ParameterBuilder();
      List<Parameter> pars = new ArrayList<Parameter>();
      tokenPar.name("acctName").description("账户名").defaultValue("").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
      tokenPar1.name("token").description("令牌").defaultValue("").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
      tokenPar2.name("estateId").description("楼盘ID").defaultValue("").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
      tokenPar3.name("employeeId").description("发布人ID").defaultValue("").modelRef(new ModelRef("string")).parameterType("header").required(false).build();

      pars.add(tokenPar.build());
      pars.add(tokenPar1.build());
      pars.add(tokenPar2.build());
      pars.add(tokenPar3.build());
      Docket docket = new Docket(DocumentationType.SWAGGER_2)
              .select()
              .apis(RequestHandlerSelectors.basePackage("com.wulian"))
              .paths(PathSelectors.ant("/**"))
              .build()
              .globalOperationParameters(pars)
              .apiInfo(apiInfo());
      return docket;
  }
  //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
  private ApiInfo apiInfo() {
      return new ApiInfoBuilder()
              //页面标题
              .title("物联接口测试 RESTful API")
              //创建人
              .contact(new Contact("Maxg", "", ""))
              //版本号
              .version("1.0")
              //描述
              .description("物联接口测试 RESTful API")
              .build();
  }


}