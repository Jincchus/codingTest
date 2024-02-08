package com.tenco.blog.board;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

//    @GetMapping("/")
//    public String index(Model model) {
//        List<Board> boardList = boardService.getAllList();

//        if(boardList.isEmpty()){
//            model.addAttribute("boardList", null);
//        } else {
//            model.addAttribute("boardList", boardList);
//        }
//        return "/index";
//    }
    @GetMapping("/")
    public String index(Model model, @PageableDefault(size = 5, sort = "no", direction = Sort.Direction.DESC) Pageable pageable) {

        model.addAttribute("boardPageList", boardService.getPagingList(pageable));;
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());
        model.addAttribute("currentPage", pageable.get)
        model.addAttribute("check", boardService.getListCheck(pageable));

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(BoardSaveFormDto dto, Model model){

        if(dto.getTitle().length() > 20 ){
            // 추후에 붙이기 ㅎ AJAX로 수정
            String errorMsg = "제목의 길이는 20자를 넘을 수 없습니다";
            model.addAttribute("errorMsg", errorMsg);
            return "/board/saveForm";
        }
        if(dto.getContent().length() > 20 ){
            String errorMsg = "글 내용의 길이는 20자를 넘을 수 없습니다";
            model.addAttribute("errorMsg", errorMsg);
            return "/board/saveForm";
        }
        boardService.saveBoard(dto);
        return "redirect:/";
    }

    @GetMapping("/board/{no}/updateForm")
    public String updateForm(@PathVariable int no, Model model){
        Optional<Board> boardOptional = boardService.getBoardByNo(no);
        Board board = boardOptional.orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        model.addAttribute("board", board);

        System.out.println("boardupdate" + board.toString());

        return "/board/updateForm";
    }

    @PostMapping("/board/{no}/update")
    public String update(@PathVariable int no, BoardSaveFormDto dto){
        boardService.updateBoard(no, dto);
        return "redirect:/";
    }

    @PostMapping("/board/{no}/delete")
    public String delete(@PathVariable int no){
        boardService.deleteBoard(no);
        return "redirect:/";
    }

}
