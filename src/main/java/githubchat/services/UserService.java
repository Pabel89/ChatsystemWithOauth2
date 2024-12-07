package githubchat.services;


import githubchat.models.Chatuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import githubchat.repositories.ChatuserRepository;

@Service
public class UserService {
   
    @Autowired
    ChatuserRepository cur;
    
    public List<Chatuser> findByUsername(String username){

        List<Chatuser> chatusers = cur.findByUsername(username);

        return chatusers;
    }

    public Chatuser getFirstUserWithUsername(String username){

        List<Chatuser> chatusers = cur.findByUsername(username);

        return chatusers.get(0);
    }

    public List<Chatuser> getAllChatusers() {
        // Call findAll and convert the result to a List (optional)
        Iterable<Chatuser> chatusers = cur.findAll();
        return (List<Chatuser>) chatusers;  // Cast to List if needed
      }

    public Long save(Chatuser user) {
        Long id = null;
     
           
            System.out.println("Pruefe ob User: "+user.getUsername()+" bereits hinterlegt ist");
           
            if(cur.findByUsername(user.getUsername()).size()==0){
                cur.save(user);
               

            }else{
                System.out.println("User ist schon hinterlegt, daher wird er nicht erneut gespeichert");
            }
        

        return id;
    }

    



}