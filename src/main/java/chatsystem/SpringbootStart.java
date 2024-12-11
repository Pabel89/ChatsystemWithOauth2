package chatsystem;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import chatsystem.models.Chatuser;
import chatsystem.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.*;
import org.springframework.security.oauth2.core.user.*;


@SpringBootApplication
@RestController
public class SpringbootStart {

    @Autowired
    UserService us;
	
    @CrossOrigin
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        String username = "";
        Chatuser cu;
        UserService ud = new UserService();
        //System.out.println(principal.getAttributes());
        for (String key : principal.getAttributes().keySet()) {
            if(key=="login"){
            username = (String) principal.getAttributes().get(key);
            }
        }

        System.out.println("Username ist: "+username);
        if(!principal.getAttribute("name").equals("")||!principal.getAttribute("name").equals(null)){
            String input = principal.getAttribute("name").toString();
            char specificChar = '@';
            
            int index = input.indexOf(specificChar);
            
            if (index != -1) {
              String result = input.substring(0, index);
              cu = new Chatuser(result, principal.getAttribute("name"));
              us.save(cu);
              return  ("Eingeloggter Nutzer ist: "+ result);
            }else{
               cu = new Chatuser(username, principal.getAttribute("name"));
               us.save(cu);
               return  ("Eingeloggter Nutzer ist:"+(String) principal.getAttribute("name"));
            }
           
           
        }else{
                cu = new Chatuser(username, "");
                us.save(cu);
            return "Authentifizierter Nutzer hat keinen Namen angegeben. Der Username ist:"+username;
        }
    }

   
  


   

}