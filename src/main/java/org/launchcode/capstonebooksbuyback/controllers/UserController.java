package org.launchcode.capstonebooksbuyback.controllers;
import org.launchcode.capstonebooksbuyback.models.User;
import org.launchcode.capstonebooksbuyback.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("user")

public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value="add", method=RequestMethod.GET)
    public String displayAddUserForm (Model model){
        model.addAttribute("title","Add User");
        model.addAttribute(new User());
        return "user/add";
    }
    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddUserForm(@ModelAttribute @Valid User user, Errors error, String verify, Model model){

        if(error.hasErrors()){
            model.addAttribute("title","Add User");
            return "user/add";
        }

        if(verify.equals(user.getPassword())){
            model.addAttribute("title","Welcome"+user.getUsername());
            userDao.save(user);
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
