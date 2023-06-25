package com.moigae.application.component.host.service;

import com.moigae.application.component.host.domain.Board;
import com.moigae.application.component.host.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> getAllBoards() {
        List<Board> all = boardRepository.findAll();
        return all;
    }

//    public Board getBoardById(String id) {
//        return boardRepository.findById(id).orElse(null);
//    }

//    public void createBoard(BankAccountDto boardDto) {
//        Board board = Board.builder()
//                .date(boardDto.getDate())
//                .participantRange(boardDto.getParticipantRange())
//                .price(boardDto.getPrice())
//                .meetingTitle(boardDto.getMeetingTitle())
//                .meetingStatus(boardDto.getMeetingStatus())
//                .build();
//        Board ret = boardRepository.save(board);
//        boardDto.setId(String.valueOf(ret.getId()));
//    }

//    
//    public void updateBoard(BoardDto boardDto) {
//        boardRepository.save(boardDto);
//    }

    // 추가적인 메서드 구현 가능
}