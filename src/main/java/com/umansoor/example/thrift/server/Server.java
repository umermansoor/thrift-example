package com.umansoor.example.thrift.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.*;

/**
 *
 * @author umermansoor
 */
public class Server {

    private final int port;
    private TServer server = null;
    private final AdditionService.Processor<AdditionServiceHandler> processor;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public Server(int p) {
        port = p;
        processor = new AdditionService.Processor(new AdditionServiceHandler());
    }

    public void doShutdown() {
        if (server != null) {
            server.stop();
        }
        server = null;
    }

    public void doStart() {
        try {
            TServerTransport serverTransport = new TServerSocket(port);
            //TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
            server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            logger.info("Server is going live");
            server.serve();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
