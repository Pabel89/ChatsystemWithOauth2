package chatsystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import chatsystem.models.Chatuser;
import chatsystem.services.UserService;

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

