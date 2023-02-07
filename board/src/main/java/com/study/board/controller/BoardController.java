package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;  //autowired로 boardservice를 주입
    @GetMapping("/board/write")  //localhost:8090//board/write
    public String boardWriteForm() {
        return "boardwrite";
    }


    @PostMapping("/board/writepro")  // GetMapping은 경로를 설정
    public String boardWritePro(Board board){

      boardService.write(board);


        return "";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {   //데이터를 담아서 보이는 페이지로 보내주기 위해 model사용
        model.addAttribute("list",boardService.boardList());

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) { //localhost:8090/board/view?id=1

        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }
}
