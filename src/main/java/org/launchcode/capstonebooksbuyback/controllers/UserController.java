package org.launchcode.capstonebooksbuyback.controllers;
import org.launchcode.capstonebooksbuyback.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("user")

public class UserController {

    @RequestMapping(value="add", method=RequestMethod.GET)
    public String displayAddUserForm (Model model){
        model.addAttribute("title","Add User");
        return "user/add";
    }
    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddUserForm(Model model, @ModelAttribute User user, String verify){

        if(verify.equals(user.getPassword())){
            model.addAttribute("title","Welcome"+user.getUsername());
            return "user/index";
        }
        else{
            model.addAttribute("username",user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("password","");
            model.addAttribute("verify","");
            return "user/add";
        }

    }


}
