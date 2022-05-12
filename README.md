# gim

#### **一个基于getty,简单易用，稳定高效的及时通讯框架**

#### A Getty based, easy to use, stable and efficient timely communication framework

## 简介：

gim是基于高性能网络框架getty封装的，轻量级，稳定高效的及时通讯框架

Gim is a lightweight, stable and efficient instant communication framework encapsulated by Getty, a high-performance network framework

Getty 地址（link）：[https://github.com/gogym/getty]

gim目的是降低及时通讯的技术门槛，把消息的分发交给gim，让更多的时间专注在业务上而诞生的。

Gim was born to lower the technical barriers to timely communication, deliver messages to GIM, and allow more time to focus on the business.


## 效果 The effect

![image](https://gitee.com/kokjuis/gim/raw/master/p1.png)

## gim的特点：

1、轻量级架构，核心代码仅几百行。

Lightweight architecture with only a few hundred lines of core code.

2、高效的协议，基于google protobuf。

Efficient protocol based on Google Protobuf.

3、框架提供了常用的接口和回调，简洁高效。

The framework provides a common interface and callback, concise and efficient.

4、易拓展设计，适配器模式让框架非常容易拓展不同的场景需要。

Easy to extend the design, the adapter pattern makes it very easy to extend the framework for different scenario needs.

5、强壮的拆包和黏包处理，网络通讯最麻烦的一环，gim已经处理好了。

Unpacking and sticky packet handling, the most troublesome part of network communication, GIM has been dealt with.

6、服务器集群，集群设计在任何系统上都算是复杂的一环，gim已经提供了基于redis的简单健壮的集群设计。

Server clustering, a complex design on any system, giM has provided a simple and robust cluster design based on redis.

7、gim提供了如：心跳检测，断线重连，离线监听，SSL支持，自动重发，消息ack机制等。

Gim provides such as: heartbeat detection, disconnected reconnection, offline monitoring, SSL support, automatic retransmission, message ACK mechanism, etc.

8、gim同时支持普通socket协议和websocket协议的消息互通。

Gim supports both normal socket protocol and WebSocket protocol for message exchange.

9、gim服务支持端口多开，可同时开启多个socket或websocket端口

The GIM service supports multiple ports, with multiple sockets or WebSocket ports open simultaneously

10、提供客户端使用的jar。兼容android平台的使用

Provide the JAR used by the client. Compatible with android platform


 ## 简单使用  Simple to use
 
  **Maven** 
 
 在项目的pom.xml的dependencies中加入依赖:
 
 Add dependencies to the list of projects that are on POM.XML:
 
 
 ```
    //服务器端
     <dependency>
       <groupId>com.gettyio</groupId>
       <artifactId>gim-java</artifactId>
       <version>1.1.5-beta</version>
     </dependency>
     
     //客户端
     <dependency>
       <groupId>com.gettyio</groupId>
       <artifactId>gim-client</artifactId>
       <version>1.1.5-beta</version>
     </dependency>
 ```
 
  **Gradle** 
 
 
 ```
 compile group: 'com.gettyio', name: 'gim-client', version: '1.1.5-beta'
 ```
 
  **非Maven项目** （No Maven） 
 
 可到中央仓库下载jar包导入到工程中
 
 You can go to the central warehouse to download the JAR package and import it into the project
 
 链接（link）：https://mvnrepository.com/artifact/com.gettyio/getty-java  [点击跳转到中央仓库(click here)](https://mvnrepository.com/artifact/com.gettyio/getty-java)

 
 ## 更多详情与文档 More details and documentation
 
 请点击(click here)  **wiki** ：[wiki](https://gitee.com/kokjuis/gim/wikis/pages)
 
 ### bug反馈或建议 Bug feedback or Suggestions
 
 - [码云Gitee issue](https://gitee.com/kokjuis/gim/issues)
 - [Github issue](https://github.com/gogym/gim/issues)
 
 
 ### create by
 
  **gogym** 
 
  **email:189155278@qq.com** 
  
  ### getty、gim交流群1 ：708758323
