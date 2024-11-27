package githubchat.services;

import githubchat.models.Chat;
import githubchat.models.Message;

import java.util.List;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import githubchat.repositories.ChatRepository;
import githubchat.repositories.MessageRepository;


@Service
public class ChatService {

    @Autowired
    ChatRepository cr;

    @Autowired
    MessageRepository mr;

    public Long save(Chat chat) {
        Long id = null;
     
        cr.save(chat);
        id = chat.getId();           

        return id;
    }

    public Long save(Chat chat, Message message) {
        Long id = null;
     
        cr.save(chat);
        mr.save(message);
        
        id = chat.getId();           

        return id;
    }

    public Collection<Chat> findChatsFromUser(String _username){
        Collection<Chat> c = cr.findChatsFromUser(_username);

        return c;
    }

    public boolean doesChatBelongToUser(String _username, long _id){
        boolean result = false;
        Collection<Chat> chats = cr.findChatsFromUser(_username);
        
        for (Chat chat : chats) {
            if(chat.getId() == _id){
                result = true;
                
            }
        }
        if(result){
            System.out.println("Chat belongs to user");
        }else{
            System.out.println("Chat belongs not to user");
        }

        return result;
    }


    
}
