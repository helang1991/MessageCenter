# MessageCenterDemo
在一些特定的Android车机中的系统中，需要APP进程之间的通信，方式有很多，先列举几种方式<Br/>
##1.广播(Broadcast)
优点，使用简单，解耦性高<Br/>
缺点，速度慢，有可能会延迟，丢消息<Br/>
##2.Socket 不建议使用，耦合性高，效率也一般般（两次拷贝）
##3.共享内存 
优点：效率高，速度快<Br/>
缺点：安全性低<Br/>

当然还有很多方式，我只列举了几种<Br/>

因此，采用基于Ibinder的AIDL是更好的方案，效率高，安全性高，综合性能能力最好<Br/>
看下基本的流程图，app要通过一个Server进程来实现消息的消息的发送和接收<Br/>
![image](https://github.com/helang1991/MessageCenterDemo/blob/master/MessageCenter.png)<Br/>
代码很简单，没有怎么封装，也没有使用什么设计模式，你可以根据自己的项目情况来实现<Br/>

