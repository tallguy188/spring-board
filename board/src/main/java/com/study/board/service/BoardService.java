package com.study.board.service;


import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성
    public void write(Board board, MultipartFile file) throws Exception {   // html에서 넘어오는 name이랑 일치시켜야한다.
        // 저장경로지정
        String projectPath= System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 랜덤식별자생성
        UUID uuid = UUID.randomUUID();

        // 랜덤식별자 + 원래파일이름
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath,fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);

        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public List<Board> boardList() {

        return boardRepository.findAll();
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id ) {

        return boardRepository.findById(id).get(); // findbyid는 값을 optional로 받아옴.
    }

    // 게시글 삭제처리
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);

    }
}
