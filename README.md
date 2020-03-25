# gim

#### **一个简单易用，稳定高效的及时通讯框架（java）**

## 简介：

gim是基于高性能网络框架getty封装的，能简单上手，稳定高效的及时通讯框架

Getty：[https://github.com/gogym/getty]

gim是为了降低及时通讯的技术门槛，把更多的时间专注在业务上为目的而诞生的。

## gim的特点：

1、轻量级架构，核心代码仅几百行。

2、高效的协议，基于google protobuf协议，通讯效率高。

3、易用的接口，框架提供了常用的可直接使用的接口和回调，简洁高效。

4、易拓展设计，适配器模式让框架非常容易拓展不同的场景需要。

5、简洁的数据结构，框架携带的消息结构体非常简单，随时可以拓展所需字段。

6、强壮的拆包黏包，网络通讯最麻烦的一环，拆包黏包算一个，gim已经帮你做好了。

7、服务器集群，集群设计在任何系统上都算是复杂的一环，gim已经提供了简单健壮的集群设计。

8、心跳检测，断线重连，离线消息，自动重发，这些肯定不能少。

9、最好当然是易用性，gim启动最少只需2行代码。

10、最好当然是易用性，gim启动最少只需2行代码。

11、提供客户端使用的jar。兼容android

 ## 效果
 
 效果图是安卓上开发一个demo的效果。这部分源码不在工程中
 
  ![image](https://github.com/gogym/gim/blob/master/p1.png)
  ![image](https://github.com/gogym/gim/blob/master/p2.png)

 ## 简单使用 
 
  **Maven** 
 
 在项目的pom.xml的dependencies中加入以下内容:
 
 
 ```
    //服务器端
     <dependency>
       <groupId>com.gettyio</groupId>
       <artifactId>gim-java</artifactId>
       <version>1.0.1</version>
     </dependency>
     
     //客户端
     <dependency>
       <groupId>com.gettyio</groupId>
       <artifactId>gim-client</artifactId>
       <version>1.0.1</version>
     </dependency>
 ```
 
  **Gradle** 
 
 
 ```
 compile group: 'com.gettyio', name: 'gim-client', version: '1.0.1'
 ```
 
  **非Maven项目** 
 
 可直接到中央仓库下载jar包导入到工程中
 
 链接：https://mvnrepository.com/artifact/com.gettyio/getty-java  [点击跳转到中央仓库](https://mvnrepository.com/artifact/com.gettyio/getty-java)

 
 ## 更多详情与文档
 
 更多详情，请点击  **wiki文档** ：[跳转到wiki](https://gitee.com/kokjuis/gim/wikis/pages)
 
 ### 提供bug反馈或建议
 
 - [码云Gitee issue](https://gitee.com/kokjuis/gim/issues)
 - [Github issue](https://github.com/gogym/gim/issues)
 
 
 ### create by
 
  **gogym** 
 
  **email:189155278@qq.com** 
  
  ### getty、gim交流群1 ：708758323
