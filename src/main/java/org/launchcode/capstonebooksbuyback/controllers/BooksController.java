package org.launchcode.capstonebooksbuyback.controllers;
import org.launchcode.capstonebooksbuyback.models.Book;
import org.launchcode.capstonebooksbuyback.models.data.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Created by Sehar Sagarwala
 */

@Controller
@RequestMapping("book")
public class BooksController {

     @Autowired
     private BookDao bookDao;


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

    @RequestMapping(value="add", method=RequestMethod.GET)
    public String displayAddBookForm (Model model){
        model.addAttribute("title","Add Book");
        model.addAttribute(new Book());
        return "book/add";
    }

    @RequestMapping(value="add", method=RequestMethod.POST)
    public String processAddBookForm(@ModelAttribute @Valid Book book,Errors error,Model model){

        if(error.hasErrors()){
            model.addAttribute("title","Add Book");
            return "book/add";
        }

        bookDao.save(book);
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

}
