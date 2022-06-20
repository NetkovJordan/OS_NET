package finki.ukim.mk.os;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{
    String serverAddress;
    int serverPort;
    String downloadFolder;

    public Client(String serverAddress,int serverPort,String downloadFolder){
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.downloadFolder = downloadFolder;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        DataOutputStream dos = null;
        try {
            Socket socket = new Socket(serverAddress,serverPort);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("hello:203038"); //ako ne rabote so DOS na ispit ce koristime BufferedWritter namesto dos.
            System.out.println(dis.readUTF());
            Thread.sleep(2000);
            getFilesFromServer(dis,dos);
            Thread.sleep(1000);
            getFileSize(downloadFolder);//napraviv so for za u slucaj ako imas povekje fajlovi u downloadFolder
            //inace sekako ako ti rabote siminjanje na folder so ti prakja server ce znajs kako se vika fileName.
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getFilesFromServer(DataInputStream dis,DataOutputStream dos) throws IOException {
        dos.writeUTF("203038:receive");
        String response = dis.readUTF();
        if(response.equals("203038:send:filename.txt")){
            System.out.println("Client:File download starting...");
            BufferedWriter writer = null;
            try{
                writer = new BufferedWriter(new FileWriter(String.format("%s%s",downloadFolder,"dat.txt")));
                while(!(response = dis.readUTF()).equals("203038:over")){
                    writer.write(response + "\n");
                }
            }finally {
                if(writer!=null){
                    writer.flush();
                    writer.close();
                }
            }
        }
        System.out.println("Client:File download finished!");

    }
    public void getFileSize(String downloadFolder){//
        File file = new File(downloadFolder);
        File [] files = file.listFiles();
        double size = 0;
        for(File f:files){
            if(f.isFile() && f.getName().equals("dat.txt")){
                size = f.length();
            }
        }
       System.out.println("Client:File size is " + size + " bytes");
    }

}
