package githubchat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import githubchat.models.Chatuser;
import githubchat.services.UserService;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SelectionController {

    @Autowired
    UserService us;

    @GetMapping("/selectchat")
    public String passParametersWithModelMap(ModelMap map) {
        List<Chatuser> users = us.getAllChatusers();
        map.addAttribute("users", users);
        return "chatselection";
    }
}

