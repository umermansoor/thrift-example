package com.umansoor.example.thrift.client;

import com.umansoor.example.thrift.server.AdditionService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.*;

/**
 *
 * @author umermansoor
 */
public class Client {
    
    private final String host;
    private final int port;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public Client (String h, int p) {
        host = h;
        port = p;
    }
    
    /**
     * 
     * @param n1
     * @param n2 
     */
    public void add(int n1, int n2) {
        TTransport transport = new TSocket(host, port);
        AdditionService.Client client;
        try {
            transport.open();
            logger.info("Connected to {} on {}", host, port);
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new AdditionService.Client(protocol);
            int result = client.add(n1, n2);
            logger.info("client.add({}, {}) = {}", n1, n2, result);
            transport.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
    }
}
