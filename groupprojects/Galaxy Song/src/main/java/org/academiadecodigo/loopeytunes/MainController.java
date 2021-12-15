package org.academiadecodigo.loopeytunes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")

public class MainController {

    @Autowired
    private TempDB tempDB;


    @RequestMapping(method = RequestMethod.GET, path = "/")
    public String index(Model model) {
        Dog dog = new Dog();
        dog.setHeight(1);
        model.addAttribute("dog", dog);
        return "index.html";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/style.css")
    public String style() {

        return "style.css";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/indexJS.js")
    public String js() {

        return "indexJS.js";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/doglist/style.css")
    public String style2() {

        return "style.css";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/doglist/ddoglisttest.js")
    public String doglistJs() {

        return "ddoglisttest.js";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/breedlistJS.js")
    public String breedjs() {

        return "breedlistJS.js";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/breed.css")
    public String breedcss() {

        return "breed.css";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/adoptingpageJS.js")
    public String adoptingjs() {

        return "adoptingpageJS.js";
    }


    @RequestMapping(method = RequestMethod.POST, path = "/breedList")
    public String getDogProperties(@ModelAttribute Dog dog) {

      tempDB.setDog(dog);
        return "breedlist.html";
    }


    @RequestMapping(method = RequestMethod.GET, path = "/doglist/{id}")
    public String doglist() {
        return "doglist.html";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/adoptingpage")
    public String adopting() {

        return "adoptingpage.html";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/doglist/doglist.css")
    public String breed() {

        return "doglist.css";
    }

    @Autowired
    public void setTempDB(TempDB tempDB) {
        this.tempDB = tempDB;
    }
}
