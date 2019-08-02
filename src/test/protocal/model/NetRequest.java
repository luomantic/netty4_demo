package test.protocal.model;

/**
 * <pre>
 * 网络数据包协议
 * +——----——+——-----——+——---------——+——----——+
 * |  协议头 |   卡号  |  串口协议数据 | 协议尾 |
 * +——----——+——-----——+——---------——+——----——+
 * </pre>
 *
 * 协议头：2  字节，0xAA 0xBB
 * 卡号：  12 字节，不足12位给末尾补0
 * 串口协议内容
 * 协议尾：2  字节，0xBB 0xAA
 *
 * 网络请求对象
 * @author luomantic
 */
public class NetRequest {

    private byte[] cardNumber; // 12字节

    private SerialRequest serialRequest;

    public byte[] getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(byte[] cardNumber) {
        this.cardNumber = cardNumber;
    }

    public SerialRequest getSerialRequest() {
        return serialRequest;
    }

    public void setSerialRequest(SerialRequest serialRequest) {
        this.serialRequest = serialRequest;
    }

}
