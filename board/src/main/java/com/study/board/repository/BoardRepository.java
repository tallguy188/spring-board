package com.study.board.repository;


import com.study.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Integer> {

// generic 부분에는 entity와 pk의 자료형을 넣어주면 된다.



}
