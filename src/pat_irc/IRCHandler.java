/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pat_irc;

import org.apache.thrift.TException;
import IRC_service.IRCService;

/**
 *
 * @author Andarias Silvanus
 */
public class IRCHandler implements IRCService.Iface{

    IRCServer server = new IRCServer();    

    @Override
    public void join_channel(String channel) throws TException {
        server.channel_list.add(channel);
    }

    @Override
    public String broadcast_recv() throws TException {
        
        String msg = IRCServer.bufmsg;
        //flush
//        IRCServer.bufmsg = "";
        
        //klo empty gimana? 
//        if(!(msg.isEmpty())){
//            return msg;
//        }
//        else{
//            return "";
//        }
        return msg;
    }

    @Override
    public void broadcast_send(String msg, String uname) throws TException {
        System.out.println(msg);
        IRCServer.bufmsg ="(" + uname + ") " + msg;
    }

    @Override
    public String msg_channel_recv(String channel) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void msg_channel_send(String channel, String msg) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}