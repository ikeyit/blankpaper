package io.ikeyit.blankpaper.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 */
public class ImHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger log = LoggerFactory.getLogger(ImHandler.class);
    public ImHandler() {
        log.info("I'm born!");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("Received {}", msg);
        if (msg.startsWith("__")) {
            ctx.fireChannelRead(msg);
        } else if ("bye".equals(msg)) {
            ctx.writeAndFlush("See you!\r\n").addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.writeAndFlush("Got your message: " + msg + "\r\n");
        }
    }
}
