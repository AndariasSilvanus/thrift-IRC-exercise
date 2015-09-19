/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pat_irc;

import IRC_service.IRCService;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public static String nickname;
    public static ArrayList<String> channel_list;
    
    public IRCClient() {
        nickname = "";
        channel_list = new ArrayList<String>();
    }
    
    public static void main(String [] args) {
        try {
            nickname = "defaultnick";
            channel_list = new ArrayList<String>();
            
            // Open connection
            TTransport transport;
            transport = new TSocket("localhost", 9090);
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            IRCService.Client client = new IRCService.Client(protocol);
            
            
            Runnable main_thread = new Runnable() {
                @Override
                public void run() {
                    Scanner in = new Scanner(System.in);
                    String s;
                    while(true){
                        // Run mode
                        String mode = "", channel = "", msg = "";
                        Scanner input = new Scanner (System.in);
                        generateUname();
                        do {
                            mode = input.next();
                            if (!mode.equals("/EXIT")) {
                                if (mode.equals("/NICK")) { // Set nickname's client
                                    nickname = input.next();
                                }

                                else if (mode.equals("/JOIN")) { // Join channel X
                                    channel = input.next();
                                    channel_list.add(channel);
            //                        client.join_channel(channel);
                                }

                                else if (mode.equals("/LEAVE")) { // Leave channel X
                                    channel = input.next();
                                    channel_list.remove(channel);
                                }

                                else if (mode.charAt(0) == '@') { // Message channel X
                                    channel = mode.substring(1, mode.length()-1);
                                    msg = input.next();
                                }

                                else { // Message to all channel
                                    msg = mode + " " + input.nextLine();
                                    try {
                                        client.broadcast_send(msg, nickname);
        //                            client.broadcast_msg(msg);
                                    } catch (TException ex) {
                                        Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        } while (!mode.equals("/EXIT"));

                    }
                }
            };
            new Thread(main_thread).start();
        
            Runnable listen = new Runnable() {
                @Override
                public void run() {
                    while(true){
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        try {
                            if ((!(client.broadcast_recv().isEmpty())) || (!client.broadcast_recv().equals(""))) {
                                System.out.println(client.broadcast_recv());
                            }
                        } catch (TException ex) {
                            Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            new Thread(listen).start();
        // Close connection
//            transport.close();
        }
        catch (TException x) {
            x.printStackTrace();
        }
        
        
            
    }
    
     public static void generateUname(){
	String usernames[] = {"Ludger","Elle","Jude","Milla","Alvin","Rowen","Elize","Leia"};
	String uname;
        Random rand = new Random();
	
	uname = usernames[(int)(rand.nextInt(usernames.length))] + (int) rand.nextInt(50) + 1;
	System.out.println("Username: " + uname);
	
	nickname = uname;
    }
     
    private static void perform(IRCService.Client client) throws TException {
//        int product = client.multiply(3,5);
//        System.out.println("3*5=" + product);
    }
}
