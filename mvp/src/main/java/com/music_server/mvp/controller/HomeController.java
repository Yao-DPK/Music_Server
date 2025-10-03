package com.music_server.mvp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping(path = "/")
    public String index(){
        return "Hello Superman";
    }
    
    @GetMapping(path = "/path")
    public String index_path(){
        return "What about Superman??";
    }
}
