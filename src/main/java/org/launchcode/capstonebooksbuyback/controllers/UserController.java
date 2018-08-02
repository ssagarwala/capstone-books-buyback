package org.launchcode.capstonebooksbuyback.controllers;
import org.launchcode.capstonebooksbuyback.models.Book;
import org.launchcode.capstonebooksbuyback.models.User;
import org.launchcode.capstonebooksbuyback.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("user")

public class UserController {

    @Autowired
    private UserDao userDao;
/**********Added login**/

    @RequestMapping(value="login", method=RequestMethod.GET)
    public String login (Model model){
        model.addAttribute("title","Login ");
        return "user/login";
    }

    @RequestMapping(value="login", method=RequestMethod.POST)
    public String login(Model model, @RequestParam String userName, @RequestParam String password,HttpServletRequest request){

            Iterable<User> users = userDao.findAll();
            for(User user: users) {
                if (user.getUsername().equals(userName) &&
                        user.getPassword().equals(password)) {
                    model.addAttribute("title", "Welcome user"+user.getUsername());
                    HttpSession session = request.getSession();
                    session.setAttribute("username", user.getUsername());
                    session.setAttribute("id", user.getId());

                    /*return "redirect:/book/add/" +user.getId();*/
                    return "redirect:/book";
                }
            }
            return "redirect:user/add";

    }

    @RequestMapping(value="logout", method=RequestMethod.GET)
    public String logout(Model model,HttpServletRequest request){

        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("title","Successfully Logged out ");
        return "redirect:/user/login";

    }

    /********** end login **/
    @RequestMapping(value="add", method=RequestMethod.GET)
    public String displayAddUserForm (Model model){
        model.addAttribute("title","Register User");
        model.addAttribute(new User());
        return "user/add";
    }
    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddUserForm(@ModelAttribute @Valid User user, Errors error, String verify, Model model,HttpServletRequest request){

        if(error.hasErrors()){
            model.addAttribute("title","Register User");
            return "user/add";
        }

        if(verify.equals(user.getPassword())){
            userDao.save(user);
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("id", user.getId());
            /*return "redirect:/book/add/" +user.getId();*/
            return "redirect:/book/add";
        }
        /* if password and verify passwords are not same */
        else{
            model.addAttribute("username",user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("password","");
            model.addAttribute("verify","");
            return "user/add";
        }

    }
}
