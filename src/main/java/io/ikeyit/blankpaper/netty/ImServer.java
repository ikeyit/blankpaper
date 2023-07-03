package io.ikeyit.blankpaper.netty;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 *
 */
public class ImServer {

    private static final Logger log = LoggerFactory.getLogger(ImServer.class);

    private int port;

    public ImServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        log.info("Start server at port: {}", port);
        // 接受客户端链接的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("bossGroup"));
        // 链接成功后，将交给这个线程组处理。这里指定2个EventLoop，每一个EventLoop单线程处理
        EventLoopGroup workerGroup = new NioEventLoopGroup(2, new DefaultThreadFactory("workerGroup"));
        // 处理长任务的线程组。请求处理过长会阻塞其它请求的处理，这种场景下可以放到另外一个线程组里执行。
        EventLoopGroup processorGroup = new NioEventLoopGroup(3, new DefaultThreadFactory("processorGroup"));
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    .addLast(new StringDecoder(StandardCharsets.UTF_8))
                                    .addLast(new StringEncoder(StandardCharsets.UTF_8))
                                    .addLast(new ImHandler())
                                    .addLast(processorGroup, new ProcessorHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ImServer(8080).start();
    }
}
