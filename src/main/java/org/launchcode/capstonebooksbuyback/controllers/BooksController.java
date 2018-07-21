package org.launchcode.capstonebooksbuyback.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by Sehar Sagarwala
 */


@Controller
@RequestMapping("book")
public class BooksController {

    static ArrayList<String> books = new ArrayList<>();
    //add RequestMapping to configure the route to this
    //@ResponseBody allows text to be sent directly from the
    // controller method
    //RequestPAth: /book
    @RequestMapping("")
    public String index(Model model) {

        //tell thymeleaf we want to return a a template.

        model.addAttribute("books",books);
        model.addAttribute("title", "My Books");
        return "book/index";
    }

    @RequestMapping(value="add", method=RequestMethod.GET)
    public String displayAddBookForm (Model model){
        model.addAttribute("title","Add Book");
        return "book/add";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddBookForm(@RequestParam String bookName){
        books.add(bookName);
        //leaving redirect empty, redirect to the same controller's index method.
        return "redirect:";

    }

}
