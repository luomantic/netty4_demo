package test.protocal.coder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import test.protocal.model.NetRequest;
import test.protocal.model.ProtocolConstant;

import java.util.Arrays;

import static test.protocal.model.ProtocolConstant.sumCheck;

/**
 * 网络请求编码器
 *
 * @author luomantic
 */
public class NetRequestEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) {
        NetRequest request = (NetRequest) msg;
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

        buffer.writeShort(ProtocolConstant.net_head);

        buffer.writeBytes(request.getCardNumber());

        // 串口协议头
        buffer.writeShort(ProtocolConstant.serial_head);
        // 卡地址
        buffer.writeByte(ProtocolConstant.card_address);
        // 卡类型
        buffer.writeByte(ProtocolConstant.card_type);
        // 命令号
        buffer.writeByte(request.getSerialRequest().getCommand());

        // 指令长度
        int dataLength = request.getSerialRequest().getDataLength();
        buffer.writeShort(dataLength);

        // 报文内容
        if (null != request.getSerialRequest().getData()) {
            buffer.writeBytes(request.getSerialRequest().getData());
        }

        // 校验码
        buffer.writeByte(sumCheck(buffer.array(), buffer.writerIndex()));

        // 串口协议尾
        buffer.writeByte(ProtocolConstant.serial_end);

        buffer.writeShort(ProtocolConstant.net_end);

        ChannelBuffer copy = buffer.copy(0, buffer.writerIndex());

        System.out.println(Arrays.toString(buffer.toByteBuffer().array()));
        System.out.println(Arrays.toString(copy.toByteBuffer().array()));

        return copy;
    }

}
