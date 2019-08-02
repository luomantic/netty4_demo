package test2;

import test.protocal.serial.Serializer;

public class A extends Serializer {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void read() {
        this.text = readString();
    }

    @Override
    protected void write() {
        writeString(text);
    }
}
