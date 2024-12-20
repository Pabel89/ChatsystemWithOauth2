package chatsystem;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import chatsystem.models.*;
import chatsystem.services.ChatService;
import chatsystem.services.MessageService;
import chatsystem.services.UserService;
import chatsystem.assets.RandomAccessKeyGenerator;




@Controller
public class Chatcontroller {

    @Autowired
    ChatService cd;

    @Autowired
    MessageService md;

    @Autowired
    UserService us;

    private final OAuth2AuthorizedClientService authorizedClientService;
    public RandomAccessKeyGenerator rakg = new RandomAccessKeyGenerator();

    JSONParser parser = new JSONParser();

    public Chatcontroller(OAuth2AuthorizedClientService authorizedClientService) {
        this.authorizedClientService = authorizedClientService;
    }

 
    @GetMapping("/chatlist")
    public String Chats(@AuthenticationPrincipal OAuth2User principal, ModelMap map){
        String username = "";

        for (String key : principal.getAttributes().keySet()) {
            if(key.equals("login")){
               username = (String) principal.getAttributes().get(key);
            }else if(key.equals("nickname")){
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

    
    @MessageMapping("/chatmessages/{accesskey}") // Client sends to /app/hello
    @SendTo("/incomingchats/chatmessages/{accesskey}") // Broadcast to subscribers
    public String sendMessage(@DestinationVariable String accesskey, ChatMessage message) {
       System.out.println("accesskey sent from client: "+accesskey);
       boolean isUserInChat = false;
        if ("received".equals(message.getType())) {
            // Nachricht wurde empfangen, verarbeite sie
            System.out.println("Message will be processed");
            String newContent = message.getContent().replace("\\", "");
            try {
                // Parse the JSON string into a JSONObject
                JSONObject jsonObject = (JSONObject) parser.parse(newContent);
                String StrContent;
                // Access data from the parsed JSONObject
                String user = (String) jsonObject.get("user");
                long chatid = (long) jsonObject.get("Chat");
                StrContent = (String) jsonObject.get("message");
                if(cd.doesChatBelongToUserAndAccessKey(user, chatid,accesskey)){
                    isUserInChat = true;
                    
                    Message newMessage = new Message(StrContent, cd.getChatById(chatid),us.getFirstUserWithUsername(user));
                    Chat myChat = cd.getChatById(chatid);
                    myChat.addMessage(newMessage);
                    cd.save(myChat,newMessage);
                    
                    System.out.println("Message will be saved because User is in Chat");
                }
                
    
    
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println("new Content: "+newContent);
            message.setContent(newContent);
            message.setType("sent"); // Setze den Typ für die ausgehende Nachricht
        }
        return message.getContent();
    }
    


    @GetMapping("/chat/{chatId}")
    public String chatMessages(@AuthenticationPrincipal OAuth2User principal, ModelMap map, @PathVariable long chatId) {
        try {
            String username = (String) principal.getAttributes().get("login");
            String username2 = (String) principal.getAttributes().get("nickname");
            if(username == null && username2!= null){
                username = username2;
            }
            //messagingTemplate.convertAndSend("/topic/greetings", message);
            if (cd.doesChatBelongToUser(username, chatId)) {
                List<Message> messages = (List<Message>) md.findMessagesFromChat(chatId);
                Chat myChat = cd.getChatById(chatId);
                if(myChat != null){
                    map.addAttribute("chatnumber",chatId);
                    map.addAttribute("accesskey",myChat.getChatAccesskey());
                    map.addAttribute("username",username);
                    map.addAttribute("messages", messages);
                    
                    return "chatmessages"; // Assuming the template is named "chat.html"

                }else{
                    map.addAttribute("error","invalid");
                    return "chatroomError.html";
                }
            } else {
                map.addAttribute("error","not authorized");
                return "chatroomError.html"; // Handle unauthorized access
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
        String accesskey = rakg.generateRandomAccessKey(20);
        Chat mynewChat = new Chat(true,true);
        mynewChat.setChatAccesskey(accesskey);

         
        
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
