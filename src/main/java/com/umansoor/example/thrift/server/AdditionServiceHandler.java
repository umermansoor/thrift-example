package com.umansoor.example.thrift.server;

import org.apache.thrift.TException;

/**
 *
 * @author umermansoor
 */
public class AdditionServiceHandler implements AdditionService.Iface
{
    public int add(int n1, int n2) throws TException {
        return n1+n2;
    }
   
    
}
