package net.vincent_clerc.network;

import net.vincent_clerc.utils.Callback;
import net.vincent_clerc.network.message_processor.InitialMessageProcessor;
import net.vincent_clerc.network.message_processor.MessageProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ConnectionHandler {

    public MessageProcessor messageProcessor;

    public ConnectionHandler(Callback playerConnectionCallback) {
        this.messageProcessor = new InitialMessageProcessor(playerConnectionCallback);
    }

    public String readMessage(SocketChannel channel) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int bytesRead;

        while ((bytesRead = channel.read(buffer)) > 0) {

            buffer.flip();

            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);

            byteArrayOutputStream.write(data);

            buffer.clear();

        }

        if (bytesRead == -1) {
            channel.close();
            return null;
        }

        String message = byteArrayOutputStream.toString();

//        System.out.println("Message from server : " + message);

        return message;

    }

    public void handleConnect(SelectionKey key) throws IOException {

        SocketChannel channel = (SocketChannel) key.channel();

        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }

        channel.configureBlocking(false);
        channel.register(key.selector(), SelectionKey.OP_WRITE);

    }

    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        String message = this.readMessage(channel);
        this.messageProcessor.process(message);
    }

    public void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.register(key.selector(), SelectionKey.OP_READ);
    }

}
