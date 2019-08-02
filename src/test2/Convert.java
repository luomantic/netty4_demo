package test2;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Convert {

    public static void main(String[] args) {
        int id = 100;
        int age = 25;
        int length = 8;

        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer(ByteOrder.BIG_ENDIAN, length);
        channelBuffer.writeInt(id);
        channelBuffer.writeInt(age);

        System.out.println(Arrays.toString(channelBuffer.array()));

        test();
    }

    private static void test() {
        A a1 = new A();
        a1.setText("a");
        try {
            int length = a1.getText().getBytes().length;
            ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN, length);
            channelBuffer.writeBytes(a1.getText().getBytes("unicode"));

//            [-2, -1, 0, 97, 0, 115, 0, 100, 0, 102]
            System.out.println(Arrays.toString(a1.getText().getBytes("unicode")));
            System.out.println(Arrays.toString(channelBuffer.array()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
