package test.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;
import test.module.SimpleCommand;
import test.protocal.coder.NetRequestEncoder;
import test.protocal.model.NetRequest;
import test.protocal.model.SerialRequest;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) {
        ClientBootstrap bootstrap = new ClientBootstrap();

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();

        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();

        bootstrap.setFactory(new NioClientSocketChannelFactory(boss, worker));
        bootstrap.setPipelineFactory(() -> {
            ChannelPipeline pipeline = Channels.pipeline();
            pipeline.addLast("encoder", new NetRequestEncoder());
            //pipeline.addLast("decoder", new NetRequestDecoder());
            pipeline.addLast("idleTimeoutHandler", new IdleStateHandler(hashedWheelTimer, 30, 30, 60));
            pipeline.addLast("heartbeatHandler", new HeartBeatClientHandler());
            return pipeline;
        });

        ChannelFuture connect = bootstrap.connect(getIpInfo());
        Channel channel = connect.getChannel();

        System.out.println("client start.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入：");
            String cardNum = scanner.nextLine();  // 卡号 18122916W273
            int cmd = Integer.parseInt(scanner.nextLine()); // 命令号


            SimpleCommand simpleCommand = new SimpleCommand();
            simpleCommand.setCardNum(cardNum);
            simpleCommand.setCmd(cmd);

            NetRequest request = new NetRequest();
            request.setCardNumber(simpleCommand.getCardNum().getBytes(StandardCharsets.UTF_8));
            SerialRequest serialRequest = new SerialRequest();
            serialRequest.setCommand((byte) simpleCommand.getCmd());

            //byte[] data = getBytes(scanner);
            //serialRequest.setData(data);
            request.setSerialRequest(serialRequest);

            channel.write(request);
        }
    }

    public static InetSocketAddress getIpInfo() {
//        return new InetSocketAddress("192.168.4.1", 6666);
        return new InetSocketAddress("127.0.0.1", 8000);
    }

    private static byte[] getBytes(Scanner scanner) {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) 1;
        int input = Integer.parseInt(scanner.nextLine());
        byte light = (byte) ((byte) (input) & 0x000000ff);
        bytes[1] = light;
        return bytes;
    }
}
