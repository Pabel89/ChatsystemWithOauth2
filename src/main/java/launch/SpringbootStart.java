package launch;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.*;
import org.springframework.security.oauth2.core.user.*;


@SpringBootApplication
@RestController
public class SpringbootStart {
	
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        
        if(!principal.getAttribute("name").equals("")||!principal.getAttribute("name").equals(null)){
            return  ("Eingeloggter Nutzer ist:"+(String) principal.getAttribute("name"));
        }else{
            return "Authentifizierter Nutzer hat kein Namen gesetzt in Github";
        }
    }

   
  


   

}