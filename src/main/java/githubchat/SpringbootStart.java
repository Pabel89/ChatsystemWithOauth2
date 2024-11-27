package githubchat;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.tomcat.util.buf.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.*;
import org.springframework.security.oauth2.core.user.*;

import githubchat.models.Chatuser;
import githubchat.services.UserService;


@SpringBootApplication
@RestController
public class SpringbootStart {

    @Autowired
    UserService us;
	
    @CrossOrigin
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        String username = "";
        UserService ud = new UserService();
        //System.out.println(principal.getAttributes());
        for (String key : principal.getAttributes().keySet()) {
            if(key=="login"){
            username = (String) principal.getAttributes().get(key);
            }
        }

        System.out.println("Username ist: "+username);
        if(!principal.getAttribute("name").equals("")||!principal.getAttribute("name").equals(null)){
                Chatuser cu = new Chatuser(username, principal.getAttribute("name"));
                us.save(cu);
            return  ("Eingeloggter Nutzer ist:"+(String) principal.getAttribute("name"));
        }else{
                Chatuser cu = new Chatuser(username, "");
                us.save(cu);
            return "Authentifizierter Nutzer hat keinen Namen angegeben. Der Username ist:"+username;
        }
    }

   
  


   

}