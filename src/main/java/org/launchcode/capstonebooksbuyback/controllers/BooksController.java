package org.launchcode.capstonebooksbuyback.controllers;
import org.launchcode.capstonebooksbuyback.models.Book;
import org.launchcode.capstonebooksbuyback.models.User;
import org.launchcode.capstonebooksbuyback.models.data.BookDao;
import org.launchcode.capstonebooksbuyback.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

/**
 * Created by Sehar Sagarwala
 */

@Controller
@RequestMapping("book")
public class BooksController {

     @Autowired
     private BookDao bookDao;

     @Autowired
     private UserDao userDao;

    //add RequestMapping to configure the route to this
    //@ResponseBody allows text to be sent directly from the
    // controller method
    //RequestPAth: /book
    @RequestMapping("")
    public String index(Model model) {

        //tell thymeleaf we want to return a a template.

        model.addAttribute("books", bookDao.findAll());
        model.addAttribute("title", "My Books");
        return "book/index";
    }

    @RequestMapping(value="add/{userId}", method=RequestMethod.GET)
    public String displayAddBookForm (@PathVariable int userId,Model model){
        model.addAttribute("title","Add Book");
        Optional<User> user1 = userDao.findById(userId);
        if(user1.isPresent()){
            User user = user1.get();
            model.addAttribute(new Book());
            model.addAttribute("userId",user.getId());
            return "book/add";
        }
        return "redirect:";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddBookForm(@ModelAttribute @Valid Book book,Errors error,@RequestParam int userId, Model model){

        if(error.hasErrors()){
            model.addAttribute("title","Add Book");
            return "book/add";
        }
        //Integer userId1= Integer.parseInt(userId);
        Optional<User> user1 = userDao.findById(userId);
        if(user1.isPresent()) {
            User user = user1.get();
            book.setUser(user);
            bookDao.save(book);
        }
       //leaving redirect empty, redirect to the same controller's index method.
        return "redirect:";
    }

    @RequestMapping(value="remove", method=RequestMethod.GET)
    public String displayRemoveBookForm(Model model){
        model.addAttribute("books",bookDao.findAll());
        model.addAttribute("title","Remove Books");
        return "book/remove";
    }

    @RequestMapping(value="remove", method=RequestMethod.POST)
    public String processRemoveBookForm(@RequestParam int[] bookIds){
       
        for (int bookId : bookIds) {
             bookDao.deleteById(bookId);
        }
        //leaving redirect empty, redirect to the same controller's index method.

        return "redirect:";
    }


    @RequestMapping(value="edit/{bookId}", method=RequestMethod.GET)
    public String displayEditForm(@PathVariable int bookId,Model model){
        //Book book = BookData.getById(bookId);

        Optional<Book> book1 = bookDao.findById(bookId);
        if(book1.isPresent()) {
            Book book = book1.get();
            model.addAttribute("book", book );
            model.addAttribute("title", "Capstone Book Buyback");
            return "book/edit";
        }
        return "book/index";
    }

    @RequestMapping(value="edit", method=RequestMethod.POST)
    public String processEditForm(int bookId, String name, String author){
        Optional<Book> book1 = bookDao.findById(bookId);
        if(book1.isPresent()) {
            Book book = book1.get();
            book.setName(name);
            book.setAuthor(author);
            bookDao.save(book);
            return "redirect:";
        }
        return "book/index";
    }

    ///find book by user
    /**public String booksByUser(Model model, @RequestParam int id){

        Optional<User> user1 = userDao.findById(id);
        if(user1.isPresent()) {
            User user = user1.get();
            List<Book> books = user.;
            model.addAttribute("books", books);
            model.addAttribute("title", "books by user" + user.getUsername());
        }
        return "user/index";

    }**/



}
