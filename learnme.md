本次学习参考
    spring cloud实例Dome详细搭建 ： https://blog.csdn.net/qq_44575680/article/details/90290092
    实际使用的时候可以优化相关模块开发顺序。可能后续在文章中进行相关顺序调整。
一、创建工程spring-cloud-demo
    1.在pom.xml中设置父类工程为pom方式 : <packaging>pom</packaging>
    2.在pom.xml中配置 spring cloud 和 springboot 的统一版本
        <properties>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
            <java.version>1.8</java.version>
            <spring-cloud.version>Greenwich.SR5</spring-cloud.version>
            <springboot.version>2.1.6.RELEASE</springboot.version>
        </properties>
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>${spring-cloud.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-dependencies</artifactId>
                    <version>${springboot.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
        </dependencyManagement>
二、创建注册中心（eureka）模块：eureka-server
    1.在pom.xml中配置Eureka服务端的依赖
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <!--Eureka服务端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：EurekaServerApplication
        启动一个服务注册中心，只需要一个注解 @EnableEurekaServer
    3.创建配置文件：application.yml
        设置名称、端口、地址
        通过 eureka.client.fetch-registry:false 和 register-with-eureka:false 来表明自己是一个 Eureka Server
        说明：Eureka 是一个高可用的组件，它没有后端缓存，每一个实例注册之后需要向注册中心发送心跳（因此可以在内存中完成），
             在默认情况下 Erureka Server 也是一个 Eureka Client , 必须要指定一个 Server
    4.启动工程，进行本环节测试
        打开浏览器访问：http://127.0.0.1:7001 ， 查看 Eureka Server 界面   
三、创建服务提供者模块：server-provider
    1.在pom.xml中配置Eureka客户端的依赖
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <!--Eureka客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ServerProviderApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
    3.编写创建配置文件：application.yml 
        设置名称、端口、地址
        说明：需要指明 spring.application.name，这个很重要，这在以后的服务与服务之间相互调用一般都是根据这个 name
    4.编写创建暴露服务实现类：ServerProviderController， 实现简单测试逻辑
    5.启动工程，进行本环节测试
        01).查看是否成功注册服务： http://127.0.0.1:7001 查看
            发现一个服务已经注册在服务中了，服务名为 SERVER-PROVIDER , 端口为 8001
        02).测试服务实现类： http://127.0.0.1:8001/provider/hi 查看
            返回结果正确 ： Hello Eureka, i am from port: 8001
    6.启动多个实例方法：
        点开项目运行的配置编辑框 -> Edit Configurations -> 勾选Allow parallel run复选框 
        -> 修改 application.yml 中端口号 -> 点击绿色三角号运行标志启动 -> 运行标志右下角那里会出现数字2
四、创建服务消费者模块：server-consumer
    实际使用的时候可以直接按照【创建服务消费者（负载均衡）模块：server-consumer-feign】，因为测试学习，所以先进行了验证
    1.在pom.xml中配置 : web服务相关
    2.编写创建启动类：ServerConsumerApplication
    3.编写创建配置文件：application.yml
        设置名称、端口、地址
    4.编写创建消费接口实现类：ServerConsumerController
        通过 RestTemplate 调用 server-provider 中的 /provider/hi 方法（路径）
    5.启动工程，进行本环节测试
        打开浏览器访问：http://127.0.0.1:9001/consumer/hi ， 查看 
        返回结果正确 ： Hello Eureka, i am from port: 8001  
五、创建网关模块：zuul
    1.在pom.xml中配置Eureka客户端的依赖和网关的依赖
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <!--Eureka的客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <!--Zuul网关的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ZuulApplication
        增加 @EnableZuulProxy 注解开启 Zuul 功能
        通过 @EnableDiscoveryClient 注解能够让注册中心能够发现，扫描到该服务
    3.编写创建配置文件：application.yml | 包含测试
        实际使用的时候可以直接按照02)配置即可，因为测试学习，所以先用01)进行了验证
        01).初始配置zuul
        zuul:
          routes:
            #没有提示，自己取名称，值为服务的名称
            api-server-provider:
              # 以 /business 开头的请求都转发给 SERVER-PROVIDER 服务
              path: /business/**  #匹配指定的路径，资源匹配的路径才会拦截，转发
              serviceId: server-provider
        -
         测试路径：http://127.0.0.1:9527/server-provider/provider/hi 查看
            规则：http://127.0.0.1:9527/服务提供者名称(小写)/服务路径
         返回结果正确 ： Hello Eureka, i am from port: 8001    
        02).修改配置：屏蔽掉使用原来的网关地址访问资源
        zuul:
          routes:
            #没有提示，自己取名称，值为服务的名称
            api-server-provider:
              # 以 /business 开头的请求都转发给 SERVER-PROVIDER 服务
              path: /business/**  #匹配指定的路径，资源匹配的路径才会拦截，转发
              serviceId: server-provider
          #忽略直接使用服务名 '*' 忽略所有的服务
          ignored-services: '*'
          #统一给访问前缀
          prefix: /api
        -  
         测试路径1：http://127.0.0.1:9527/api/business/provider/hi , 查看
         返回结果正确 ： Hello Eureka, i am from port: 8001
         测试路径2：http://127.0.0.1:9527/server-provider/provider/hi 查看
         返回结果正确 ： Whitelabel Error Page 
六、创建服务消费者（负载均衡）模块：server-consumer-feign
    1.在pom.xml中配置Eureka客户端的依赖和Feign的依赖
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <!--Eureka的客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <!--Feign的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ServerConsumerFeignApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
        通过注解 @EnableEurekaClient 开启 Feign 功能
    3.编写创建配置文件：application.yml
        配置 eureka.client 
    4.在service文件夹下创建接口 : ConsumeFeignClient
        通过注解 @FeignClient("SERVER-PROVIDER") 要调用的服务名称
        实现对 server-provider 的调用
    5.编写创建消费接口实现类：ConsumeFeignController
        通过 ConsumeFeignClient 调用 实现
    6.启动工程，进行本环节测试
        打开浏览器访问：http://127.0.0.1:9002/consumer/feign/hi ， 查看 
        刷新2次，返回结果正确（负载均衡测试通过） ： 
            Hello Eureka, i am from port: 8001  
            Hello Eureka, i am from port: 8002
    -
     补充说明：Feign 是一个声明式的伪 HTTP 客户端，它使得写 HTTP 客户端变得更简单。
             使用 Feign，只需要创建一个接口并注解。它具有可插拔的注解特性，可使用 Feign 注解和 JAX-RS 注解。
             Feign 支持可插拔的编码器和解码器。Feign 默认集成了 Ribbon，并和 Eureka 结合，默认实现了负载均衡的效果。
七、创建服务消费者（负载均衡）模块：server-consumer-ribbon
    1.在pom.xml中配置Eureka客户端
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <!--Eureka客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
        </dependencies>
        -补充说明：按照官方的意思是需要加入以下依赖
           <dependency>
              <groupId>org.springframework.cloud</groupId>
              <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
           </dependency>
           但是其实是不需要的加入这个依赖的，在spring-cloud-starter-eureka依赖中就已经包含了Ribbon Starter
    2.编写创建启动类：ServerConsumerRibbonApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
        使用Ribbon : 为 RestTemplate 配置类添加 @LoadBalanced 注解即可
    3.编写创建配置文件：application.yml
        与之前总体配置保持一致，但是有特殊配置：
          禁止向eureka注册服务，因为它自己本身就是服务器
            eureka:
              client:
                registerWithEureka: false
    4.编写创建消费接口实现类：ConsumeRibbonController
        RestTemplate 方式调用： 资源路径 注意 [URL:端口] 换为 [服务名称]
    5.启动工程，进行本环节测试
            打开浏览器访问：http://127.0.0.1:9003/consumer/ribbon/hi ， 查看 
            刷新2次，返回结果正确（负载均衡测试通过） ： 
                Hello Eureka, i am from port: 8001  
                Hello Eureka, i am from port: 8002
     -
     补充说明：Ribbon 是一个负载均衡客户端，可以很好的控制 HTTP 和 TCP 的一些行为。
             在微服务架构中，业务都会被拆分成一个独立的服务，服务与服务的通讯是基于 HTTP RESTful 的。
             Spring Cloud 有两种服务调用方式，一种是 Ribbon + RestTemplate，另一种是 Feign 。
     其他说明:本章节为学习过程，实际使用的时候选择 Feign 比较好。
八、创建服务消费者（负载均衡、熔断降级）模块：server-consumer-feign-hystrix
    总体流程和 server-consumer-feign 保持一致，增加熔断机制
    1.在pom.xml中配置Eureka客户端的依赖和Feign的依赖 ， 与 server-consumer-feign 相同
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <!--Eureka的客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <!--Feign的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ServerConsumerFeignHystrixApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
        通过注解 @EnableFeignClients 开启 Feign 功能
    3.编写创建配置文件：application.yml
        配置 eureka.client 
        配置 开启熔断支持 (Feign 是自带熔断器的，但默认是关闭的。设置 true 开启熔断支持。)
            feign:
              hystrix:
                enabled: true
    4.在 service 文件夹下创建接口 : ConsumeFeignClient
        通过注解 @FeignClient(value = "SERVER-PROVIDER", fallback = ConsumeFeignFallback.class)
            实现对 server-provider 的调用 和 熔断机制处理
            value = "SERVER-PROVIDER" 为 要调用的服务名称
            fallback = ConsumeFeignFallback.class 为 熔断实现类
    5.在 fallback 文件夹下创建实现类 : ConsumeFeignFallback
        继承接口 ConsumeFeignClient , 请求失败或超时或异常则会触发熔断并返回一个固定结果
        完成该步骤后修改 ConsumeFeignClient 的 @FeignClient 注解，增加 fallback = ConsumeFeignFallback.class
     -说明：4 和 5 说明：4和5实现的顺序是交叉的，
        因为步骤5 ConsumeFeignFallback 需要继承 ConsumeFeignClient
        同时步骤4 fallback = ConsumeFeignFallback.class 需要步骤5 创建实现
    6.编写创建消费接口实现类：ConsumeFeignController
            通过 ConsumeFeignClient 调用 实现
    7.启动工程，进行本环节测试
        启动报错：to {GET /provider/hi}: There is already 'consumeFeignFallback' bean method
            查询是因为使用服务降级不要在接口 ConsumeFeignClient 使用 @RequestMapping , 直接在 @GetMapping 中补全路径
        重启成功，打开浏览器访问：http://127.0.0.1:9004/consumer/feign/hi ， 查看 
            刷新2次，返回结果正确（负载均衡测试通过） ： 
                Hello Eureka, i am from port: 8001  
                Hello Eureka, i am from port: 8002
            关闭 server-provider 后刷浏览器，返回结果正确（熔断降级测试通过） ： 
                请求失败了，请重试...
    8.补充说明：使用熔断降级，一定不要在服务消费者调用类上使用 @RequestMapping 注释，
                否则会报错误： There is already 'consumeFeignFallback' bean method
九、创建服务消费者（负载均衡、熔断降级(后备工厂方式)）模块：server-consumer-feign-hystrix-f
    总体流程和 server-consumer-feign-hystrix 保持一致，熔断降级使用[后备工厂]方式
    1.在pom.xml中配置Eureka客户端的依赖和Feign的依赖
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <!--Eureka的客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <!--Feign的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ServerConsumerFeignHystrixFApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
        通过注解 @EnableFeignClients 开启 Feign 功能
    3.编写创建配置文件：application.yml
        配置 eureka.client 
        配置 开启熔断支持 (Feign 是自带熔断器的，但默认是关闭的。设置 true 开启熔断支持。)
            feign:
              hystrix:
                enabled: true
    4.在 service 文件夹下创建接口 : ConsumeFeignClient
        通过注解 @FeignClient(value = "SERVER-PROVIDER", fallbackFactory = ConsumeFeignClientFactory.class)
            实现对 server-provider 的调用 和 熔断机制处理
            value = "SERVER-PROVIDER" 为 要调用的服务名称
            fallbackFactory = ConsumeFeignClientFactory.class 为 熔断实现后备工厂
    5.在 factory 文件夹下创建实现类 : ConsumeFeignClientFactory 后备工厂
        implements FallbackFactory<ConsumeFeignClient>
    6.编写创建消费接口实现类：ConsumeFeignController
        通过 ConsumeFeignClient 调用 实现
    7.启动工程，进行本环节测试
        打开浏览器访问：http://127.0.0.1:9005/consumer/feign/hi ， 查看 
            刷新2次，返回结果正确（负载均衡测试通过） ： 
                Hello Eureka, i am from port: 8001  
                Hello Eureka, i am from port: 8002
        打开浏览器访问：http://127.0.0.1:9005/consumer/feign/testNum/1 ， 查看 
            返回结果正确：test succeed, the num is: 1   
        打开浏览器访问：http://127.0.0.1:9005/consumer/feign/testNum/0 ， 查看 
            返回结果正确：请求失败了，请重试...!
     -实际使用的时候可以根据使用情况选择 server-consumer-feign-hystrix 或 server-consumer-feign-hystrix-f
十、修改网关模块：zuul 
    本次主要是将 网关地址访问资源 改成 comsumer (server-consumer-feign-hystrix-f)
    1.将 application.yml 备份成 application.yml.back_provider
        新建 application.yml 拷贝原有的 yml文件
    2.修改端口号为：9627
    3.修改配置
    zuul:
      routes:
        #没有提示，自己取名称，值为服务的名称
        api-server-consumer-feign-hystrix-f:
          # 以 /business 开头的请求都转发给 SERVER-PROVIDER 服务
          path: /business/**  #匹配指定的路径，资源匹配的路径才会拦截，转发
          serviceId: server-consumer-feign-hystrix-f
      #忽略直接使用服务名 *忽略所有的服务
      ignored-services: '*'
      #统一给访问前缀
      prefix: /api
    7.启动工程，进行本环节测试
        测试路径1：http://127.0.0.1:9627/api/business/consumer/feign/hi , 查看
        刷新2次，返回结果正确（负载均衡测试通过） ： 
            Hello Eureka, i am from port: 8001
            Hello Eureka, i am from port: 8002
        测试路径2：http://127.0.0.1:9627/server-consumer-feign-hystrix-f/consumer/feign/hi 查看
            返回结果正确 ： Whitelabel Error Page 
十一、创建配置中心仓库文件夹：config-repos
    1. 编写临时测试 server-provider-dev.yml 文件
        repos: repos version 1.0
        demo:
          message: Hello Spring Cloud Config
十二、创建配置中心模块：config
    1.在pom.xml中配置Eureka客户端的依赖和配置中心依赖支持
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <!--Eureka的客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <!--配置中心依赖支持-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-config-server</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ServerConsumerFeignHystrixFApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
        通过注解 @EnableConfigServer 启动配置中心服务
    3.编写创建配置文件：application.yml
        配置config仓库地址相关
        spring:
          cloud:
            config:
              label: master
              server:
                git:
                  uri: https://github.com/yuan563421580/spring-cloud-demo.git
                  search-paths: config-repos
                  username: 563421580@qq.com
                  password: 1411305034bo
    4.启动工程，进行本环节测试
        打开浏览器访问：http://127.0.0.1:8888/server-provider/dev , 查看
            返回结果正确（临时测试编写的文件） ： 
            propertySources: [
                {
                    name: "https://github.com/yuan563421580/spring-cloud-demo.git/config-repos/server-provider-dev.yml",
                    source: {
                        repos: "repos version 1.0",
                        demo.message: "Hello Spring Cloud Config"
                    }
                }
            ]
        修改后均正确访问并返回结果
            http://127.0.0.1:8888/server-provider/dev
            http://127.0.0.1:8888/server-consumer/dev
十三、创建服务提供者模块，使用配置中心：server-provider-config
    1.在pom.xml中配置Eureka客户端的依赖和配置中心支持
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <!--Eureka客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <!--配置中心支持-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-config</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ServerProviderConfigApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
    3.编写创建配置文件：bootstrap.yml
        -说明：bootstrap相对于application具有优先执行的顺序
        配置 eureka.client
        配置 配置中心 相关
            spring:
              cloud:
                config:
                  #配置服务器
                  uri: http://127.0.0.1:8888
                  #分支
                  label: master
                  #github上面名称
                  name: server-provider
                  #环境
                  profile: dev
                  #如果连不上config 尝试重连， 最大6次
                  #fail-fast: true 
    4.编写创建暴露服务实现类（拷贝原有方法）：ServerProviderController， 实现简单测试逻辑
    5.启动工程，进行本环节测试
        01).查看是否成功注册服务： http://127.0.0.1:7001 查看
            发现一个服务已经注册在服务中了，服务名为 SERVER-PROVIDER-CONFIG , 端口为 8011
        02).测试服务实现类： http://127.0.0.1:8011/provider/hi 查看
            返回结果正确 ： Hello Eureka, the provider with config, i am from port: 8011
十四、创建服务消费者（负载均衡、熔断降级）模块，使用配置中心：server-consumer-config
    说明：以 server-consumer-feign-hystrix 为基础新建模块改造
    1.在pom.xml中配置Eureka客户端的依赖、Feign的依赖和配置中心支持
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-test</artifactId>
            </dependency>
            <!--Eureka的客户端的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
            </dependency>
            <!--Feign的依赖-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-openfeign</artifactId>
            </dependency>
            <!--配置中心支持-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-config</artifactId>
            </dependency>
        </dependencies>
    2.编写创建启动类：ServerConsumerConfigApplication
        通过注解 @EnableEurekaClient 表明自己是一个 Eureka Client
        通过注解 @EnableFeignClients 开启 Feign 功能
    3.编写创建配置文件：bootstrap.yml
        -说明：bootstrap相对于application具有优先执行的顺序
        配置 eureka.client
        配置 配置中心 相关
            spring:
              cloud:
                config:
                  #配置服务器
                  uri: http://127.0.0.1:8888
                  #分支
                  label: master
                  #github上面名称
                  name: server-consumer
                  #环境
                  profile: dev
                  #如果连不上config 尝试重连， 最大6次
                  #fail-fast: true
    4.在 service 文件夹下创建接口 : ConsumeFeignClient
        通过注解 @FeignClient(value = "SERVER-PROVIDER", fallback = ConsumeFeignFallback.class)
            实现对 server-provider 的调用 和 熔断机制处理
            value = "SERVER-PROVIDER" 为 要调用的服务名称
            fallback = ConsumeFeignFallback.class 为 熔断实现类
    5.在 fallback 文件夹下创建实现类 : ConsumeFeignFallback
        继承接口 ConsumeFeignClient , 请求失败或超时或异常则会触发熔断并返回一个固定结果
        完成该步骤后修改 ConsumeFeignClient 的 @FeignClient 注解，增加 fallback = ConsumeFeignFallback.class
         -说明：4 和 5 说明：4和5实现的顺序是交叉的，
            因为步骤5 ConsumeFeignFallback 需要继承 ConsumeFeignClient
            同时步骤4 fallback = ConsumeFeignFallback.class 需要步骤5 创建实现
    6.编写创建消费接口实现类：ConsumeFeignController
         通过 ConsumeFeignClient 调用 实现
    7.启动工程，进行本环节测试
        启动报错：to {GET /provider/hi}: There is already 'consumeFeignFallback' bean method
            查询是因为使用服务降级不要在接口 ConsumeFeignClient 使用 @RequestMapping , 直接在 @GetMapping 中补全路径
        重启成功，打开浏览器访问：http://127.0.0.1:9114/consumer/feign/hi ， 查看 
            刷新2次，返回结果正确（负载均衡测试通过） ： 
                Hello Eureka, the provider with config, i am from port: 8011
                Hello Eureka, the provider with config, i am from port: 8012
            访问：http://127.0.0.1:9114/consumer/feign/testNum/0，返回结果正确（熔断降级测试通过） ： 
                请求失败了，请重试...!
            关闭 server-provider-config 后刷浏览器，返回结果正确（熔断降级测试通过） ： 
                请求失败了，请重试...!
    8.补充说明：使用熔断降级，一定不要在服务消费者调用类上使用 @RequestMapping 注释，
                否则会报错误： There is already 'consumeFeignFallback' bean method
----------------------------            
com.yuansb.spring.cloud.demo