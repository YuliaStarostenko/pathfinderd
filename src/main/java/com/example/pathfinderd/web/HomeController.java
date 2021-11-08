package com.example.pathfinderd.web;

import com.example.pathfinderd.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final PictureService pictureService;

    public HomeController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @GetMapping("/")
    public String index(Model model){
        model
                .addAttribute("picture", pictureService.findAllUrls());
        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
