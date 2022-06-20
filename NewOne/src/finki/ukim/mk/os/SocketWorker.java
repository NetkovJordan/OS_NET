package finki.ukim.mk.os;

import java.io.*;
import java.net.Socket;

public class SocketWorker extends Thread{
    String serverFolder;
    Socket socket;
    public SocketWorker(String serverFolder,Socket socket){
        this.serverFolder = serverFolder;
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try{
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            String firstCommand = dis.readUTF();
            if(firstCommand.equals("hello:203038")){
                dos.writeUTF("203038:hello");
            }
            String downloadCommand = dis.readUTF();
            if(downloadCommand.equals("203038:receive")){
                System.out.println("Server:Download command is received");
                Thread.sleep(2000);
                System.out.println("Server:File upload is starting...");
                Thread.sleep(2000);
               sendFileToClient(dos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void sendFileToClient(DataOutputStream dos) throws IOException {
        dos.writeUTF("203038:send:filename.txt");
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(String.format("%s%s",serverFolder,"dat.txt")));
            String line = null;
            while((line = reader.readLine())!=null){
                dos.writeUTF(line + "\n");
            }
            dos.writeUTF("203038:over");
        }finally {
            if(reader!=null){
                reader.close();
            }
        }
        System.out.println("Server:File upload finished!");
    }
}
