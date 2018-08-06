package org.launchcode.capstonebooksbuyback.controllers;

import org.launchcode.capstonebooksbuyback.models.Book;
import org.launchcode.capstonebooksbuyback.models.Zip;
import org.launchcode.capstonebooksbuyback.models.data.ZipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("zip")
public class ZipController {

    @Autowired
    private ZipDao zipDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("zips", zipDao.findAll());
        model.addAttribute("title", "zipCodes");
        return "zip/index";
    }

    @RequestMapping(value = "", method=RequestMethod.POST)
    public String findBookByZip (Model model, @RequestParam int zipId){


        Optional<Zip> zip1 = zipDao.findById(zipId);
        if(zip1.isPresent()) {
            Zip zip = zip1.get();

            List<Book> books = zip.getBooks();

            model.addAttribute("books", books);
            model.addAttribute("zips", zipDao.findAll());
            model.addAttribute("title", "Books in zip   :" + zip.getZipNumber());
            return "zip/index";
        }
        else {
            Iterable<Zip> zips =zipDao.findAll();
            model.addAttribute("zips",zips);
            return "zip/index";
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title", "Add ZipCode");
        model.addAttribute(new Zip());
        return "zip/add";
    }
    @ RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,
                      @ModelAttribute @Valid Zip zip, Errors errors){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Add ZipCode");
            return "zip/add";
        }
        else {
            zipDao.save(zip);
            model.addAttribute("zipId", zip.getId());
            return "redirect:/user/add";
        }
    }
}
