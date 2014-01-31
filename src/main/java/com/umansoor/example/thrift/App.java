package com.umansoor.example.thrift;
import com.umansoor.example.thrift.client.Client;
import com.umansoor.example.thrift.server.Server;
import org.slf4j.*;

/**
 * Hello world!
 *
 */
public class App 
{
    static final Logger logger = LoggerFactory.getLogger(App.class.getSimpleName());
    
    public static void main( String[] args )
    {
        int port = 9090;
        int n1 = 15;
        int n2 = 16;
        
        if (args.length == 3) {
            try {
                port = Integer.parseInt(args[0]);
                n1 = Integer.parseInt(args[1]);
                n2 = Integer.parseInt(args[2]);
            } catch (NumberFormatException nfe) {
                logger.error("Invalid arguments. {}", nfe);
                printUsage();
            }
        }
        
        logger.info("## Starting Server on port {} ##", port);
        final Server server = new Server(port);
        Thread serverThread = new Thread(new Runnable() {
           @Override public void run() {
               server.doStart();
           } 
        });
        
        serverThread.start();
        
        // Give Server enough time to start otherwise client will fail 
        // Note: BAD approach. In real app, please consider (e.g. could use CountDownLatch)
        try { serverThread.sleep(1000); } catch (InterruptedException ie) {}
        
        
        logger.info("## Starting Client to add: {} + {} ##", n1, n2);
        new Client("localhost", port).add(n1, n2);
        
        // Shut down the server
        server.doShutdown();
        // Await shutdown
        try { serverThread.join(); } catch (InterruptedException ie) {}
        
        logger.info("bye");

    }
    
    public static void printUsage() {
        System.out.println("java -jar thrift-example.jar [port] [first_number] [second_number]");
    }
}
