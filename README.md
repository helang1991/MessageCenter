# MessageCenterDemo
在一些特定的Android车机中的系统中，需要APP进程之间的通信，但不是APP之间直接相互通信，而是由一个消息中心处理后，
再决定消息如何转发，简单地说有点类似于一个由服务器的聊天室</br>
当然方式有很多，先列举几种方式，看看优缺点<Br/>
## 1.广播(Broadcast)
优点，使用简单，解耦性高<Br/>
缺点，速度慢，有可能会延迟，丢消息（主要是有系统的广播队列，所有广播都在那个队列里，如果队列过长，那么你发的广播肯定会延迟）<Br/>
## 2.Socket 不建议使用，耦合性高，效率也一般般（两次拷贝）
## 3.共享内存 
优点：效率高，速度快<Br/>
缺点：安全性低<Br/>

## 当然还有很多方式，我只列举了这几种<Br/>
综上所述，上面三种方案均都有比较明显的缺点，因此，AIDL的优势就出来了<Br/>
## AIDL
基于binder，效率高；基于C/S架构，分层清晰，功能明确；有Linux的进程ID概念，更加安全等优点<Br/>
## 流程图
看下基本的流程图，app要通过一个Server进程（Center）来实现消息的消息的发送和接收<Br/>
这样的话，有一个控制中心就可以方便控制所有消息的管理，也方便后续的其他APP的集成<Br/>
我这里就写的比较简单了

![image](https://github.com/helang1991/MessageCenterDemo/blob/master/png/test.png)

## 效果图
App2通过Center向App1发送消息<Br/>
![image](https://github.com/helang1991/MessageCenterDemo/blob/master/png/app2.png)<Br/>
![image](https://github.com/helang1991/MessageCenterDemo/blob/master/png/app1.png)

## 总结
代码很简单，没有怎么封装，也没有使用什么设计模式，你可以根据自己的项目情况来实现<Br/>



