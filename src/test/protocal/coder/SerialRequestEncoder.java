package test.protocal.coder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import test.protocal.model.ProtocolConstant;
import test.protocal.model.SerialRequest;

import static test.protocal.model.ProtocolConstant.sumCheck;

/**
 * 网络请求编码器
 *
 * @author luomantic
 */
public class SerialRequestEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) {
        SerialRequest request = (SerialRequest) msg;
        ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();

        // 串口协议头
        buffer.writeShort(ProtocolConstant.serial_head);
        // 卡地址
        buffer.writeByte(ProtocolConstant.card_address);
        // 卡类型
        buffer.writeByte(ProtocolConstant.card_type);
        // 命令号
        buffer.writeByte(request.getCommand());
        // 指令长度
        buffer.writeShort(request.getDataLength());

        // 报文内容
        if (null != request.getData()) {
            buffer.writeBytes(request.getData());
        }

        // 校验码
        buffer.writeByte(sumCheck(buffer.array(), buffer.writerIndex()));

        // 串口协议尾
        buffer.writeByte(ProtocolConstant.serial_end);
        return buffer;
    }

}
