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
import org.apache.thrift.TException;

/**
 *
 * @author Andarias Silvanus
 */
public class IRCHandler implements IRCService.Iface{

    @Override
    public void msg_channel_send(String msg, String channel, String uname) throws TException {
        List<String> channeList = new ArrayList<String>();
        channeList.add(channel);
        Message msg_temp = makeMessage(msg, channeList, uname);
        IRCServer.msgList.add(msg_temp);
    }
    
    private Message makeMessage(String msg, List<String> chaneList, String nickname) {
        Message msg_temp = new Message();
        msg_temp.setForm(nickname);
        msg_temp.setMsg(msg);
        msg_temp.setToChannel(chaneList);
        
        long tsTime = getSecondNow();
        msg_temp.setMsg_time(tsTime);
        
        return msg_temp;
    }

    @Override
    public void broadcast_send(String msg, String uname, List<String> channelList) throws TException {
        Message msg_temp = makeMessage(msg, channelList, uname);
        IRCServer.msgList.add(msg_temp);
    }
    
    private long getSecondNow(){
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp ts_now = new java.sql.Timestamp(today.getTime());
        long tsTime = ts_now.getTime();
        return tsTime;
    }

    @Override
    public List<Message> msg_recv() throws TException {
        List<Message> old_msg = IRCServer.msgList;
        List<Message> res_msg = new ArrayList<Message>();
        
        if (!old_msg.isEmpty()) {
            for (Message m : old_msg) {
                    res_msg.add(m);
            }
        }
        
        return res_msg;
    }

    @Override
    public int join_channel(String channel) throws TException {
        IRCServer.channel_list.add(channel);
        return 1;
    }
}