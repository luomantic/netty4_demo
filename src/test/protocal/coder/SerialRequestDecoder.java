package test.protocal.coder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import static test.protocal.model.ProtocolConstant.base_serial_length;

/**
 * 网络请求解码器
 *
 * @author luomantic
 */
public class SerialRequestDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
        if (buffer.readableBytes() > base_serial_length) {
            return null;
        }

        // 数据包不完整，需要等待后面的数据
        return null;
    }

}
