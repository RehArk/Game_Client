package net.vincent_clerc.network;

import org.lwjgl.Sys;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class ConnectionHandler {


    public String readMessage(SocketChannel channel) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int bytesRead;

        while ((bytesRead = channel.read(buffer)) > 0) {
            buffer.flip();
            byteArrayOutputStream.write(buffer.array(), 0, bytesRead);
            buffer.clear();
        }

        if (bytesRead == -1) {
            channel.close();
            return null;
        }

        String message = byteArrayOutputStream.toString();

        System.out.println("Message from server : " + message);

        return message;

    }

    public void handleConnect(SelectionKey key) throws IOException {

        SocketChannel channel = (SocketChannel) key.channel();

        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }

        channel.configureBlocking(false);
        channel.register(key.selector(), SelectionKey.OP_WRITE);

        String message = this.readMessage(channel);

        // TODO : handle message
    }

    public void handleRead(SelectionKey key) throws IOException {
        // TODO : handle message
    }

    public void handleWrite(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.register(key.selector(), SelectionKey.OP_READ);
    }

}
