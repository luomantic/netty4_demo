package test.protocal.model;

/**
 * 协议常量、工具方法
 *
 * @author luomantic
 */
public class ProtocolConstant {

    /**
     * 网络协议头
     */
    public static final short net_head = (short)0xaabb;

    /**
     * 串口协议头
     */
    public static final short serial_head = (short) 0xeb90;

    /**
     * 卡地址
     */
    public static final byte card_address = (byte) 0xff;

    /**
     * 卡类型
     */
    public static final byte card_type = 0x13;

    /**
     * 串口协议尾
     */
    public static final byte serial_end = (byte) 0xef;

    /**
     * 网络协议尾
     */
    public static final short net_end = (short) 0xbbaa;

    /**
     * 串口数据包基本长度
     */
    public static final int base_serial_length = 9;

    /**
     * 网络数据包基本长度
     */
    public static final int base_net_length = base_serial_length + 16;

    /**
     * 获取校验位
     * @param buf buffer
     * @param len 长度
     * @return 校验位2字节
     */
    public static short sumCheck(byte[] buf, int len) {
        int i;
        short sum = 0;
        for (i = 14; i < len; i++) {
            sum += buf[i];
        }
        sum = (short) (sum ^ 0xff);
        return sum;
    }

}
