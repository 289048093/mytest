# 环境变量配置相关

标签（空格分隔）： 配置 环境变量

把配置放在环境变量中可使代码和配置分离，重新部署时如果没有新的配置，打包后的代码不需要修改任何地方，也不需要根据不同的环境打包不同的代码，开发、测试、正式环境可共用一套代码，可有效避免出现配置错误的问题

--- 
目前支持框架：
##spring原生
**测试版本**：4.2.2.RELEASE

**支持情况**：支持环境变量配置各种bean,通过bean属性注入的配置都支持环境变量配置，比如：spring各种dateSource(spring-jdbc,spring-mybatis,spring-hibernate),spring-ldap,spring-security,spring-mongodb,spring-session,spring-data-redis...

**Demo**：
环境变量设置：
```bash
export MY_NAME=Zhao.Li
```

bean配置：
```xml
    <bean class="com.midea.BeanDemo">
        <property name="name" value="${MY_NAME}"/>
    </bean>
```


##Dubbo+Spring
测试版本：2.5.6
支持情况：支持环境变量配置Dubbo
```xml
    <!-- 使用注册中心暴露服务地址 -->
    <!--或者<dubbo:registry address="zookeeper://${MOA_ZK_CONNECT}"/>-->
    <dubbo:registry address="${MOA_DUBBO_REGESTRY_CONNECT}"/>
    <!-- 用dubbo协议端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="${MOA_DUBBO_PORT}"/>
```

##常见问题：

 - 必须在spring xml配置文件中加入如下代码（所有与spring集成的框架都需要加入这段代码，才能通过变量配置）:
```xml
    <!-- 加载配置属性 -->
    <context:property-placeholder/>
```
- 环境变量KEY必须符合shell变量命名规则，建议使用全大写，单词用“_”隔开，不能用"."等标点符号, 如：“MOA_DUBBO_ZK_CONNECT”
- 在java代码中获取环境变量：
```java
    System.getenv("MOA_ZK_CONNECT")
```
如果之前是通过properties文件获取配置，可做如下操作，以兼容之前的代码：
```java
public class PropertyUtils {
    private static final Properties PROPERTIES = new Properties();
    static{
        PROPERTIES.putAll(System.getenv());
    }
    // codes... ...
}
```
- 关于idea开发环境测试：

intellij ideax下设置环境变量(数据可在properties文件和idea环境变量编辑窗口相互拷贝粘贴)：

**tomcat:**

1.先打开配置界面

![step 1][1]

2.2.在Tomcat Server中选中要设置的tomcat应用，或者点击+添加，在startup/connection选项卡中，选中Run或者debug，在environment variables中设置相应的环境变量

![setp 2][2]

**java 应用：**

1.同tomcat1，打开配置界面

2.在application下选中要设置的应用，或者点击+号添加。在environment variables中设置环境变量

![step 2][3]



  [1]: https://raw.githubusercontent.com/289048093/mytest/master/1.png
  [2]: https://raw.githubusercontent.com/289048093/mytest/master/2.png
  [3]: https://raw.githubusercontent.com/289048093/mytest/master/3.png
