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

    public Chat getChatById(long _id){
        Chat myChat = null;
        myChat = cr.findChatById(_id);

        return myChat;
    }

    public boolean doesChatBelongToUserAndAccessKey(String _username, long _id, String _accesskey){
        boolean result = false;
        Collection<Chat> chats = cr.findChatsFromUser(_username);
        
        for (Chat chat : chats) {
            if(chat.getId() == _id && chat.getChatAccesskey().equals(_accesskey)){
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
