一个EventLoopGroup 包含一个或多个EventLoop<br>
一个EventLoop在生命周期内只与一个Thread绑定<br>
一个channel在生命周期内只注册于一个EventLoop<br>
一个EventLoop可能会被分配给一个或多个Channel

## 消息写入方式
1、直接写入channel  消息从尾端流动
2、写入HandlerContext  消息从下一个Handler流动



## Netty的channel 怎么保证的线程安全
Netty 的 Channel 在多线程环境下可以安全地使用，这是因为 Netty 内部对 Channel 的线程安全性做了很好的保证。

在 Netty 中，每个 Channel 都有自己的 EventLoop，用于处理 Channel 上的事件。EventLoop 是一个单独的线程，负责处理 I/O 事件、执行任务和定时任务等。当一个 I/O 事件发生时，EventLoop 会调用 ChannelPipeline 中的处理器（ChannelHandler）来处理事件，这些处理器是串联在一起的，形成一个处理链。在处理链中，每个处理器都可以对事件进行处理，也可以将事件传递给下一个处理器，直到事件被处理完毕。

在 EventLoop 中，每个 Channel 的 I/O 事件都会由一个线程来处理，因此不会出现多个线程同时对同一个 Channel 进行操作的情况。此外，Netty 中的所有操作都是异步的，因此在操作完成前，当前线程不会被阻塞，可以继续处理其他的任务。

另外，Netty 中的 ChannelHandler 也可以是线程安全的。在处理器中，如果需要对共享状态进行操作，可以使用同步机制（如锁或原子操作）来保证线程安全性。

综上所述，Netty 的 Channel 是线程安全的，这得益于 Netty 内部对 Channel 的事件处理和操作都是由一个单独的 EventLoop 线程进行，避免了多个线程同时对同一个 Channel 进行操作的情况，并且 Netty 中的所有操作都是异步的，不会阻塞当前线程。此外，在处理器中使用同步机制来保证共享状态的线程安全性。


## ByteBuf
### 使用模式
- 堆缓冲区
  - 支撑数组为JVM堆缓冲
  - 适合有遗留数据处理的情况
- 直接缓冲区 
  - 优点：避免 缓冲区和中间缓冲区的频繁复制 
  - 缺点：分配与释放比较昂贵
- 复合缓冲区
  - Jdk的ByteBuffer完全缺失的特性
  - 实现 CompositeByteBuf

### ByteBuf内存池的实现
Netty提供了两种ByteBuf内存池的实现：PooledByteBufAllocator和UnpooledByteBufAllocator。
1. PooledByteBufAllocator
   - PooledByteBufAllocator是Netty提供的ByteBuf内存池实现之一。它通过池化ByteBuf对象的方式来提高内存使用效率。它的实现基于Jemalloc或TCMalloc等内存管理库，具有高效的内存分配和释放机制，可以避免频繁的内存分配和垃圾回收带来的性能损失。
   - PooledByteBufAllocator使用两个池来管理ByteBuf对象，一个是堆内存池，一个是直接内存池。在分配ByteBuf对象时，它会根据需求的大小和类型选择相应的池，如果池中有可用的对象，就会从池中获取，如果池中没有可用对象，则会进行扩容，扩容的大小是根据具体的算法和参数来决定的。使用PooledByteBufAllocator可以有效地减少内存碎片和GC开销，提高内存使用效率和程序性能。
2. UnpooledByteBufAllocator
   - UnpooledByteBufAllocator是Netty提供的非ByteBuf内存池实现。它使用的是普通的Java对象池技术，不会进行ByteBuf对象的池化管理。在分配ByteBuf对象时，它会直接从JVM堆或直接内存中分配，每次分配都会产生新的对象，不会重复利用已有的对象。UnpooledByteBufAllocator比PooledByteBufAllocator性能差，但是它具有更简单的实现和更低的内存消耗。

### 派生缓冲区
索引隔离，数据共享<br>
**真实复制，copy类型方法<br>**
#### 包含方法
- duplicate
- slice
- slice(int, int)
- Unpooled.unmodifiableBuffer(...)
- order(ByteOrder)
- readSlice(int)

### ByteBuf 分配
- 按需分配：ByteBufAllocator接口
- Unpooled 缓冲区
- ByteBufUtil工具类


***调用clear清除readIndex和writeIndex，但不会清除内容***

## ByteBuffer和ByteBuf在特性上有以下区别
1. 数据类型：ByteBuffer只能处理字节数据，而ByteBuf可以处理字节、字符、数字等各种数据类型。
2. 缓冲区容量：ByteBuffer的容量是固定的，一旦分配后不能动态扩展；而ByteBuf的容量是动态可扩展的。
3. 索引管理：ByteBuffer使用position、limit和capacity来管理缓冲区索引，这些索引需要手动管理；而ByteBuf使用读写索引来管理缓冲区，可以分离读写索引，方便读写不同的数据。
4. 缓冲区类型：ByteBuffer分为堆内存和直接内存，而ByteBuf可以分为堆内存和直接内存两种类型，并且可以自动进行内存池化，提高性能。
5. 引用计数：ByteBuf支持引用计数，可以自动管理缓冲区的释放，避免内存泄漏问题。
6. ByteBuf提供了更多的操作，如get/set操作、批量读写、查找操作、复制和切片等。
7. ByteBuf提供了对协议的支持，如HTTP、WebSocket、Redis等，可以快速处理协议数据。
8. ByteBuffer只有一个索引，需要调用flip来切换读写索引


## ChannelHandler 和 ChannelPipeline
### ChannelInboundHandlerAdapter
**channelRead 被重写是，需要显示的释放ByteBuf的计数器，调用ReferenceCountUtil.release()**

### SimpleChannelInboundHandler
**使用channelRead0 不需要显式的释放资源，会自动释放**
<br>
**自动释放资源会清空引用，避免存储引用操作**

### ChannelOutboundHandler

### ChannelHandler
被@sharable标记的实现类，可以添加到多个Pipeline中

### ChannelPipeline
***实现方式***
<br>
双向链表

### 传播路径
**Channel 或者 ChannelPipeline**
<br>
调用上述类内的方法，会沿着整个pipeline进行传播
<br>
<br>
**ChannelHandlerContext**
<br>
调用上述类内的方法，传播会从当前handler开始，并且只会调用到下一个能处理该事件的handler

#### **为什么会想要从 ChannelPipeline 中的某个特定点开始传播事件呢?**
- 减少事件经过它不感兴趣的ChannelHandler所带来的考校
- 为了避免将事件传经那些可能会对它感兴趣的ChannelHandler
<br>
**为何要共享同一个ChannelHandler 在多个ChannelPipeline中安装同一个ChannelHandler
的一个常见的原因是用于收集跨越多个 Channel 的统计信息。**


## EventLoop和线程模型
**一个EventLoop将由一个永远都不会改变的Thread驱动**
<br>


## 检测泄漏
-Dio.netty.leakDetectionLevel=ADVANCED
<br>
- DISABLED 禁用
- SIMPLE 1%采样
- ADVANCED 报告泄漏的任何位置
- PARANOID 每次对消息访问都采样，影响性能