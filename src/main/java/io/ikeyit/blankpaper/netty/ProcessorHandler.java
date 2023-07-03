package io.ikeyit.blankpaper.netty;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理命令，需要一定的时间才能处理完。该handler将在EventLoop以外的线程执行。
 */
public class ProcessorHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger log = LoggerFactory.getLogger(ProcessorHandler.class);

    public ProcessorHandler() {
        log.info("I'm born!");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("Process command: {}", msg);
        if ("__bitcoin".equals(msg))  {
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            ctx.writeAndFlush("Coin is generated! Number: " + ThreadLocalRandom.current().nextLong() + "\r\n");
        } else {
            ctx.writeAndFlush("The command is invalid!\r\n");
        }
    }
}
