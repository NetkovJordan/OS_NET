package finki.ukim.mk.os;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    ServerSocket serverSocket;
    int port;
    String serverFolder;
    public Server(int port,String serverFolder){
        this.port = port;
        this.serverFolder = serverFolder;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                SocketWorker socketWorker = new SocketWorker(serverFolder,socket);
                socketWorker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
