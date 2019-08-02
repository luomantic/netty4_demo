package test.protocal.serial;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.nio.ByteOrder;

/**
 * buffer工厂
 *
 * @author luomantic
 */
public class BufferFactory {

    public static ByteOrder BYTE_ORDER = ByteOrder.BIG_ENDIAN;

    /**
     * 获取一个buffer
     * @return 动态分配内存的ByteBuffer
     */
    public static ChannelBuffer getBuffer() {
        return ChannelBuffers.dynamicBuffer();
    }

    /**
     * 将数据写入buffer
     * @param bytes 字节数组
     * @return 复制的ChannelBuffer对象
     */
    public static ChannelBuffer getBuffer(byte[] bytes) {
        return ChannelBuffers.copiedBuffer(bytes);
    }

}
