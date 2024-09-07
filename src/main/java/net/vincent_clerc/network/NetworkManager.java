package net.vincent_clerc.network;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkManager {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private ExecutorService executorService;
    private Selector selector;

    private ConnectionHandler connectionHandler;

    public void initialize() {
        this.executorService = Executors.newSingleThreadExecutor(); // Create a single-threaded executor
        this.connectionHandler = new ConnectionHandler();
        this.connectToServer();
    }

    private void connectToServer() {

        executorService.submit(() -> {

            try {
                this.selector = Selector.open();
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false); // Mode non-bloquant
                socketChannel.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }

            while (true) {

                try {

                    selector.select();

                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                    while (keyIterator.hasNext()) {

                        SelectionKey key = keyIterator.next();

                        if (key.isConnectable()) {
                            this.connectionHandler.handleConnect(key);
                        } else if (key.isReadable()) {
                            this.connectionHandler.handleRead(key);
                        } else if (key.isWritable()) {
                            this.connectionHandler.handleWrite(key);
                        }

                        keyIterator.remove(); // Suppression de la clé traitée
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(0);
                }

            }

        });

    }

}
