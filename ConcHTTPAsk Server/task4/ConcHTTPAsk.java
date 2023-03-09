import java.io.IOException;
import java.net.ServerSocket;

public class ConcHTTPAsk {
    public static void main(String[] args) {
        int portNumber;
        if(args.length < 1) portNumber = 8888;
        else portNumber = Integer.parseInt(args[0]);
        try {
            ServerSocket server = new ServerSocket(portNumber);
            while (true) {
                new MyRunnable(server.accept()).start();
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
}
