package com.example.board.entity;

import com.example.board.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "board_table")
public class Board extends BaseEntity {
    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFile> boardFileList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    public static Board toSaveEntity(BoardDTO boardDTO) {
        return Board.builder()
                .boardWriter(boardDTO.getBoardWriter())
                .boardPass(boardDTO.getBoardPass())
                .boardTitle(boardDTO.getBoardTitle())
                .boardContents(boardDTO.getBoardContents())
                .boardHits(0)
                .fileAttached(0) // 파일 없음
                .build();
    }

    public static Board toSaveFileEntity(BoardDTO boardDTO){
        return Board.builder()
                .boardWriter(boardDTO.getBoardWriter())
                .boardPass(boardDTO.getBoardPass())
                .boardTitle(boardDTO.getBoardTitle())
                .boardContents(boardDTO.getBoardContents())
                .boardHits(0)
                .fileAttached(1) // 파일 있음
                .build();
    }

    public static Board toUpdateEntity(BoardDTO boardDTO) {
        return Board.builder()
                .id(boardDTO.getId())
                .boardWriter(boardDTO.getBoardWriter())
                .boardPass(boardDTO.getBoardPass())
                .boardTitle(boardDTO.getBoardTitle())
                .boardContents(boardDTO.getBoardContents())
                .boardHits(boardDTO.getBoardHits())
                .fileAttached(0) // 파일 없음
                .build();
    }
}
