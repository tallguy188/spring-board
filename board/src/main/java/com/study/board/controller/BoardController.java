package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;  //autowired로 boardservice를 주입
    @GetMapping("/board/write")  //localhost:8090//board/write,   GetMapping은 경로를 설정
    public String boardWriteForm() {
        return "boardwrite";
    }


    @PostMapping("/board/writepro")
    public String boardWritePro(Board board , Model model, MultipartFile file) throws Exception{

      boardService.write(board,file);

      model.addAttribute("message","글작성이 완료되었습니다.");
        model.addAttribute("searchUrl","/board/list");



        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model,@PageableDefault(page = 0,size=10,sort="id",direction = Sort.Direction.DESC )
    Pageable pageable, String searchKeyword) {//데이터를 담아서 보이는 페이지로 보내주기 위해 model사용


        Page<Board> list = null;
        if(searchKeyword ==null) {
            list = boardService.boardList(pageable);


        }else {

            list = boardService.boardSearch(searchKeyword,pageable);


        }

        // 페이지 블럭 처리
        int nowPage = list.getPageable().getPageNumber() + 1; //pageable의 디폴트 값이 0부터 시작이라서 +1
        int startPage = Math.max(nowPage - 4,1);  // 현재페이지가 4보다 작을때를 대비
        int endPage = Math.min(nowPage + 5,list.getTotalPages()); //페이지가 토탈페이지수를 넘지 않도록 대비

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "boardlist";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id) { //localhost:8090/board/view?id=1

        model.addAttribute("board",boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id) {

        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id , Model model){


        model.addAttribute("board",boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board,Model model,MultipartFile file) throws Exception {

        Board boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardService.write(boardTemp, file);

        model.addAttribute("message","수정이 완료되었습니다");
        model.addAttribute("searchUrl","/board/list");

        return "message";
    }
}
