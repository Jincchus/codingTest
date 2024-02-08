package com.tenco.blog.board;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void saveBoard(BoardSaveFormDto dto){

        Board board = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .build();

        boardRepository.save(board);
    }

    //public List<Board> getAllList(){
    //    return boardRepository.findAll();
    //}

    @Transactional
    public Page<Board> getPagingList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Optional<Board> getBoardByNo(Integer no){
        return boardRepository.findById(no);
    }

    public void deleteBoard(Integer no){
        boardRepository.deleteById(no);
    }

    @Transactional
    public Board updateBoard(int no ,BoardSaveFormDto dto ){

        Board board = boardRepository.findById(no).orElseThrow();

        board.update(
                dto.getTitle(),
                dto.getContent(),
                dto.getAuthor()
        );

        return this.boardRepository.save(board);
    }

}
