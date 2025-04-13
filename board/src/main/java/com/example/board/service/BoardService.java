package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.Board;
import com.example.board.entity.BoardFile;
import com.example.board.repository.BoardFileRepository;
import com.example.board.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDTO boardDTO) throws IOException {

        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부파일 없음
            Board saveEntity = Board.toSaveEntity(boardDTO);
            boardRepository.save(saveEntity);
        } else{
            // 첨부파일 있음
            Board board = Board.toSaveFileEntity(boardDTO);
            Long savedId = boardRepository.save(board).getId();
            Board boardIdEntity = boardRepository.findById(savedId).get();

            for (MultipartFile boardFile: boardDTO.getBoardFile()){
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "C:/Users/rlarb/coding/UnivImg/" + storedFileName;

                boardFile.transferTo(new File(savePath));


                BoardFile boardFileEntity =  BoardFile.toBoardFileEntity(board, originalFileName, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
        }
    }

    public List<BoardDTO> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board : boardList) {
            boardDTOList.add(BoardDTO.toBoardDTO(board));
        }

        return  boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            return BoardDTO.toBoardDTO(board); // 이 시점에 Lazy 로딩된 boardFileList 접근 가능
        } else {
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        Board boardEntity = Board.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 3; // 한 페이지에 몇개 보여줄지
        Page<Board> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        return boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()) );

    }
}
