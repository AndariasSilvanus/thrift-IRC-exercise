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
    
    static String nickname;
    static List<String> channel_list;
    static long lastFetch;
    
    public static void main(String [] args) {
        try {
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
                    String mode = "", channel = "", msg = "";
                    generateUname();
                    Scanner input = new Scanner (System.in);
                    
                    // Run mode
                    do {
                        mode = input.next();

                        if (mode.equals("/NICK")) { // Set nickname's client
                            nickname = input.next();
                            System.out.println("Ganti nickname berhasil!");
                        }

                        else if (mode.equals("/JOIN")) { // Join channel X
                            channel = input.next();
                            try {
                                client.join_channel(channel);
                                if(!(channel_list.contains(channel))){
                                    channel_list.add(channel);
                                }
                                System.out.println("Join pada channel " + channel + " berhasil!");
                            } catch (TException ex) {
                                Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                        else if (mode.equals("/LEAVE")) { // Leave channel X
                            channel = input.next();
                            channel_list.remove(channel);
                            System.out.println("Leave pada channel " + channel + " berhasil!");
                        }

                        else if (mode.equals("/EXIT")) { // Exit 
                            System.out.println("Exiting program...");
                            System.exit(0);
                        }

                        else {
                            if (mode.charAt(0) == '@') { // Message channel X
                                channel = mode.substring(1, mode.length());
                                msg = input.nextLine();
                                try {
                                    client.msg_channel_send(msg, channel, nickname);
                                } catch (TException ex) {
                                    Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            else { // Message to all channel
                                msg = mode + " " + input.nextLine();
                                try {
                                    client.broadcast_send(msg, nickname, channel_list);
                                } catch (TException ex) {
                                    Logger.getLogger(IRCClient.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    } while (true);
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
                            List<Message> msg_list = client.msg_recv();
                            if (!msg_list.isEmpty()) {
                                String res = "";
                                for (Message m : msg_list) {
                                    
                                    if (m.getMsg_time() > lastFetch)
                                        for (String s : m.getToChannel()) {
                                            if (channel_list.contains(s)) {
                                                res = "[" + s + "] (" + m.getForm() + ") " + m.getMsg();
                                                System.out.println(res);
                                            }
                                        }
                                }
                            }
                            lastFetch = getSecondNow();
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
    
    private static long getSecondNow(){
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp ts_now = new java.sql.Timestamp(today.getTime());
        long tsTime = ts_now.getTime();
        return tsTime;
    }
    
     public static void generateUname(){
	String usernames[] = {"Ludger","Elle","Jude","Milla","Alvin","Rowen","Elize","Leia"};
	String uname;
        Random rand = new Random();
	
	uname = usernames[(int)(rand.nextInt(usernames.length))] + (int) rand.nextInt(50) + 1;
	System.out.println("Username: " + uname);
	
	nickname = uname;
    }
}

