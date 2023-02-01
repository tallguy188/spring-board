package com.study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {


    @GetMapping("/board/write")  //localhost:8090//board/write
    public String boardWriteForm() {
        return "boardwrite";
    }


    @PostMapping("/board/writepro")
    public String boardWritePro(String title, String content){

        System.out.println(title);
        System.out.println(content);


        return "";
    }
}
