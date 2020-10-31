# 工具类说明(基于JDK11)


> Do you like sunshine?  Yes , I do.

## jdk版本说明
支持Jdk1.8及以上.

## maven引用
maven:  
```xml
<dependency>
  <groupId>red.lixiang.tools</groupId>
  <artifactId>sunshine-starter</artifactId>
  <version>1.0.4</version>
</dependency>
```

gradle:
```groovy
compile 'red.lixiang.tools:sunshine-starter:1.0.4'
```
如只需要jdk本身的工具,可只引用 `sunshine-base`
如只需一些第三方的某个工具只需引用 `sunshine-common`

如需某个工具类,可以直接复制源码,或者通过下方公众号和笔者联系

## sunshine-base
不依赖别的第三方(fastjson,gson除外)的工具包
- HttpTools
- IOTools
- OSTools
- HostTools
- ReflectTools
- AESTools
- RSATools
- XMLTools
- FileTools
- ListTools
- RandomTools
- StringTools
- SnowflakeGenerator

## sunshine-common
基于第三方的工具再次封装的工具包  
为了项目不臃肿,所有的包都是以`compileOnly`的方式引进来的,也就是说,你还需要自己在项目中添加依赖才可以使用里面的工具  
- aliOssTools
- ExcelTools
- KuberTools
- YamlTools
- MarkdownTools

## sunshine-starter
可用于spring-boot引用 
- RedisSpringTools
- ContextHolder  

引入starter之后,可以完成一个最简化的增删改查+Controller


欢迎关注公众号  
工具包使用过程中,有什么问题,可以在公众号留言,或点击菜单加笔者微信直接交流    
![java_subscribe](https://gitee.com/smeilknife/image1/raw/master/image/java_subscribe.jpg)
