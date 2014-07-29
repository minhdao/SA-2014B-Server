import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        int turnNumber = 0;
        while (true){
            // server will only add 2 players into card table
//            for (int i = 0; i < 2;i++){
                try {
                    Socket socket = serverSocket.accept();
                    ct.addPlayer(new Player("Player-" + turnNumber , socket, turnNumber));
                    turnNumber++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            }
        }
    }
}
