package test.module;

import test.protocal.serial.Serializer;

/**
 * 简单指令
 *
 * @author luomantic
 */
public class SimpleCommand extends Serializer {
    private String cardNum;
    private int cmd;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Override
    protected void read() {
        this.cardNum = readString();
        this.cmd = readInt();
    }

    @Override
    protected void write() {
        writeString(cardNum);
        writeInt(cmd);
    }

}
