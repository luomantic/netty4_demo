package test.protocal.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * <pre>
 * 串口数据包协议
 * +——-----——+——-----——+——----——+——-----——+——------——+——------——+——-----——+——----——+
 * |   包头  |  卡地址  |  卡类型 | 命令代码 |  报文长度 |  报文内容 |  校验码  |  包尾  |
 * +——-----——+——-----——+——----——+——-----——+——------——+——------——+——-----——+——----——+
 * </pre>
 *
 * 包头：    2 字节，0xeb 0x90
 * 地址：    1 字节，默认0xff, 485通信时为卡的485通信地址
 * 卡类型：  1 字节，默认0x13
 * 命令代码：1 字节，指令功能代码
 * 报文长度：2 字节，低字节在前，高字节在后，是报文内容的长度
 * 报文内容：n 自己，指令代码数据
 * 校验码：  1 字节，从包头到报文内容字段的和，然后与0xff异或
 * 包尾：   1 字节， 默认0xEF
 *
 * 串口请求对象
 * @author luomantic
 */
public class SerialRequest {
    /**
     * 命令代码
     */
    private byte command;

    /**
     * 报文内容 - 指令代码数据
     */
    private byte[] data;

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getDataLength(){
        if (null == data) {
            return 0;
        }
        // 长度转换为小端序列
        return int2short(data.length);
    }

    private short int2short (int i){
        byte[] bytes = new byte[4];
        bytes[0] = (byte) i;
        bytes[1] = (byte) (i << 8);
        bytes[2] = (byte) (i << 2*8);
        bytes[3] = (byte) (i << 3*8);
        return bytesToShort(bytes);
    }

    private short bytesToShort(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getShort();
    }
}
