package test.protocal.coder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import test.protocal.model.NetRequest;
import test.protocal.model.ProtocolConstant;

import static test.protocal.model.ProtocolConstant.base_net_length;

/**
 * 网络请求解码器
 *
 * @author luomantic
 */
public class NetRequestDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
        if (buffer.readableBytes() > base_net_length) {
            if (buffer.readableBytes() > 1024 * 8) { // 防止socket字节流攻击
                buffer.skipBytes(buffer.readableBytes());
            }

            int beginReader;

            while (true) {
                beginReader = buffer.readerIndex();

                buffer.markReaderIndex();
                if (buffer.readShort() == ProtocolConstant.net_head) {
                    break;
                }

                // 未读到包头，略过一个字节
                buffer.resetReaderIndex();
                buffer.readByte();

                if (buffer.readableBytes() < base_net_length) {
                    return null;
                }
            }

            // 卡号
            byte[] cardNum = buffer.readBytes(12).array();
            // 串口头
            short serialHead = buffer.readShort();
            // 卡地址
            byte cardAddress = buffer.readByte();
            // 卡类型
            byte cardType = buffer.readByte();

            // 命令代码
            byte command = buffer.readByte();
            // 报文长度
            short length = buffer.readShort();

            if (buffer.readableBytes() < length) {
                buffer.readerIndex(beginReader); // 还原读指针
            }

            byte[] data = new byte[length];
            buffer.readBytes(data);

            NetRequest request = new NetRequest();
            request.setCardNumber(cardNum);
            request.getSerialRequest().setCommand(command);
            request.getSerialRequest().setData(data);

            // 继续往下传递(sendUpStreamEvent)
            return request;
        }

        // 数据包不完整，需要等待后面的数据
        return null;
    }

}
