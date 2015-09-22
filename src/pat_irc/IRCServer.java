/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pat_irc;

import IRC_service.IRCService;
import IRC_service.Message;
import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer; 
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 *
 * @author Andarias Silvanus
 */
public class IRCServer {
    
    public static IRCHandler handler;
    public static IRCService.Processor processor;
    
    public static List<Message> msgList;
    public static List<String> channel_list;
    
    public IRCServer() {
        channel_list = new ArrayList<String>();
        msgList = new ArrayList<Message>();
    }
    
    public static void main(String [] args) {
        try {
            channel_list = new ArrayList<String>();
            msgList = new ArrayList<Message>();
            
            handler = new IRCHandler();
            processor = new IRCService.Processor(handler);
            
            // Thread for client
            Runnable runServer = new Runnable() {
                @Override
                public void run() {
                    run_server(processor);
                }
            };
            new Thread(runServer).start();
        }
        catch (Exception x) {
            x.printStackTrace();
        }
    }
    
    public static void run_server(IRCService.Processor processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);
            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            System.out.println("Starting IRC server...");
            server.serve();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
