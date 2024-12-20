package chatsystem.services;

import java.util.List;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chatsystem.models.Chat;
import chatsystem.models.Message;
import chatsystem.repositories.ChatRepository;
import chatsystem.repositories.MessageRepository;


@Service
public class MessageService {

    @Autowired
    MessageRepository mr;

    public Long save(Message message) {
        Long id = null;
     
        mr.save(message);
        id = message.getId();           

        return id;
    }


    public Collection<Message> findMessagesFromChat(long _id_number){
        System.out.println("MessagService: findMessagesFromChat() executed");
        Collection<Message> m = null;
        Long _idNumberlong = _id_number;
        m = mr.findMessagesFromChat(_idNumberlong);
        if(m.size()>0){
            System.out.println("Messages for Chat found");
        }else{
            System.out.println("Messages for Chat not found");
        }

        return m;
    }


    
}
