package githubchat;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import githubchat.models.*;
import githubchat.services.ChatService;
import githubchat.services.UserService;
import githubchat.services.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Collection;
import java.util.List;

@Controller
public class Chatcontroller {

    @Autowired
    ChatService cd;

    @Autowired
    MessageService md;

    @Autowired
    UserService us;

    private final OAuth2AuthorizedClientService authorizedClientService;

    public Chatcontroller(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

 
    @GetMapping("/chatlist")
    public String Chats(@AuthenticationPrincipal OAuth2User principal, ModelMap map){
        String username = "";

        for (String key : principal.getAttributes().keySet()) {
            if(key=="login"){
            username = (String) principal.getAttributes().get(key);
            }
        }

        List<Chat> chats = (List<Chat>) cd.findChatsFromUser(username);        
        map.addAttribute("chats", chats);
        return "chatlist";
    }

    @GetMapping("/testwebsocket")
    public String testwebsocket(@AuthenticationPrincipal OAuth2User principal, ModelMap map){
        
        return "testwebsocket";
    }

    @MessageMapping("/chatmessages") // Client sends to /app/hello
    @SendTo("/incomingchats/chatmessages") // Broadcast to subscribers
    public String sendMessage(String message) {
        return message;
    }

    @GetMapping("/chat/{chatId}")
    public String chatMessages(@AuthenticationPrincipal OAuth2User principal, ModelMap map, @PathVariable long chatId) {
        try {
            String username = (String) principal.getAttributes().get("login");
            //messagingTemplate.convertAndSend("/topic/greetings", message);
            if (cd.doesChatBelongToUser(username, chatId)) {
                List<Message> messages = (List<Message>) md.findMessagesFromChat(chatId);
                map.addAttribute("chatnumber",chatId);
                map.addAttribute("username",username);
                map.addAttribute("messages", messages);
                return "chatmessages"; // Assuming the template is named "chat.html"
            } else {
                return "unauthorized"; // Handle unauthorized access
            }
        } catch (DataAccessException | NullPointerException e) {
            System.out.println("Error fetching messages for chatId {} "+ e);
            map.addAttribute("errorMessage", "Failed to fetch messages. Please try again later.");
            return "error"; // Assuming an "error.html" template exists
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    @ResponseBody
    public String addChat(@RequestParam("selectedUsername") String targetUsername, @RequestParam("initialMessage") String initalMessage){
        OAuth2User ouser = (OAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("github", ouser.getName());
       System.out.println("Add Chat via Postrequest with: ");
       System.out.println(targetUsername);
       System.out.println(initalMessage);
      
        String user = "test";
        Chat mynewChat = new Chat(true,true);

         
        
        for (String key : ouser.getAttributes().keySet()) {
            if(key=="login"){
                user = (String) ouser.getAttributes().get(key);
            }
        }
        

         
        List<Chatuser> startUserInList = us.findByUsername(user);
        Chatuser conversationStarter = startUserInList.get(0);

        List<Chatuser> targetUserInList = us.findByUsername(targetUsername);
        Chatuser targetUser = targetUserInList.get(0);

        conversationStarter.addChat(mynewChat);
        targetUser.addChat(mynewChat);

        mynewChat.addUser(conversationStarter);
        mynewChat.addUser(targetUser);

        Message initMessage = new Message(initalMessage, mynewChat,conversationStarter);
        mynewChat.addMessage(initMessage);
        
        cd.save(mynewChat,initMessage);
        us.save(conversationStarter);
        us.save(targetUser);
        

        return "chataddedsuccessfull"; 
    }

    
}
