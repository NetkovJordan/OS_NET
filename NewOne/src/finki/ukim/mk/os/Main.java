package finki.ukim.mk.os;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Server server = new Server(5000,"C:\\OS\\");
        server.start();
        Client client = new Client("localhost",5000,"C:\\DownloadFolder\\");
        client.start();
    }
}
