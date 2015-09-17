/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pat_irc;

import IRC_service.IRCService;
import java.util.Scanner;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * @author Andarias Silvanus
 */
public class IRCClient {
    public static void main(String [] args) {
        try {
            // Open connection
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            IRCService.Client client = new IRCService.Client(protocol);
            
            // Run mode
            String mode = "", nickname = "", channel = "", msg = "";
            Scanner input = new Scanner (System.in);
            do {
                mode = input.next();
                if (!mode.equals("/EXIT")) {
                    if (mode.equals("/NICK")) { // Set nickname's client
                        nickname = input.next();
                    }
                    else if (mode.equals("/JOIN")) { // Join channel X
                        channel = input.next();
                    }
                    else if (mode.equals("/LEAVE")) { // Leave channel X
                        channel = input.next();
                    }
                    else if (mode.charAt(0) == '@') { // Message channel X
                        channel = mode.substring(1, mode.length()-1);
                        msg = input.next();
                    }
                    else { // Message to all channel
                        msg = input.next();
                    }
//                    perform(client);
                }
            } while (!mode.equals("/EXIT"));
            
            // Close connection
                transport.close();
        }
        catch (TException x) {
            x.printStackTrace();
        }
    }
    private static void perform(IRCService.Client client) throws TException {
//        int product = client.multiply(3,5);
//        System.out.println("3*5=" + product);
    }
}
