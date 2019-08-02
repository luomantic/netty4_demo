package test.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import test.module.SimpleCommand;
import test.protocal.model.NetRequest;
import test.protocal.model.SerialRequest;

import java.nio.charset.StandardCharsets;

import static test.client.Client.getIpInfo;

public class HeartBeatClientHandler extends IdleStateAwareChannelHandler {

    HeartBeatClientHandler() {
        super();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        System.out.println("messageReceived");
        super.messageReceived(ctx, e);
    }

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
        System.out.println("channelIdle");
        if (e != null) {
            if (e.getState() == IdleState.WRITER_IDLE) {
                SimpleCommand simpleCommand = new SimpleCommand();
                simpleCommand.setCardNum("18122916W273");
                simpleCommand.setCmd(165);

                NetRequest request = new NetRequest();
                request.setCardNumber(simpleCommand.getCardNum().getBytes(StandardCharsets.UTF_8));
                SerialRequest serialRequest = new SerialRequest();
                serialRequest.setCommand((byte) simpleCommand.getCmd());
                request.setSerialRequest(serialRequest);
                ctx.getChannel().write(request);
            }
        }
        super.channelIdle(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);
        System.out.println("channelConnected");
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        System.out.println("channelDisconnected");
        if (ctx.getChannel() != null && !ctx.getChannel().isConnected()) {
            ctx.getChannel().connect(getIpInfo());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        System.out.println(e);
        super.exceptionCaught(ctx, e);
    }
}
