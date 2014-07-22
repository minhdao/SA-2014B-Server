import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 * Created by minh on 7/16/14.
 * Program starts here
 * Threads and sockets for servers are created here
 */
public class Server {

    private static ArrayList<Thread> game = new ArrayList<Thread>();
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        Server m = new Server();

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        final CardTable ct = (CardTable) context.getBean("cardTable");

        try {
            serverSocket = new ServerSocket(18888);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // server will run forever
        while (true){
            // server will only add 4 players into card table
            for (int i = 1; i <= 4;i++){
                try {
                    ct.addPlayer(new Player("Player-" + i , serverSocket.accept()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
