package com.example.board.service;

import com.example.board.dto.CommentDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Comment;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {

        Optional<Board> boardRepositoryById = boardRepository.findById(commentDTO.getBoardId());
        if(boardRepositoryById.isPresent()){
            Board board = boardRepositoryById.get();
            Comment saveEntity = Comment.toSaveEntity(commentDTO, board);
            return commentRepository.save(saveEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        Board boardEntity = boardRepository.findById(boardId).get();
        List<Comment> commentList = commentRepository.findAllByBoardOrderByIdDesc(boardEntity);

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment: commentList){
            CommentDTO commentDTO = CommentDTO.toCommentDTO(comment, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
