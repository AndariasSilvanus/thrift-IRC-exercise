namespace java IRC_service

struct Message {
	1: string form,
	2: list<string> toChannel,
	3: string msg,
	4: i64 msg_time,
}

service IRCService {
	i32 join_channel (1: string channel),
	list<Message> msg_recv (),
	void broadcast_send (1: string msg, 2: string uname, 3: list<string> channelList),
	void msg_channel_send (1: string msg, 2: string channel, 3: string uname),	
}