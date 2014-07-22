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

    public static void main(String[] args) {
        Server m = new Server();

        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

        final CardTable ct = (CardTable) context.getBean("cardTable");

        try {
            ServerSocket ss = new ServerSocket(18888);
            for (int i = 0; ;i++){
                ct.addPlayer(new Player("name", ss.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
