package com.example.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "board_file_table")
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private  String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private  Board board;

    public static BoardFile toBoardFileEntity(Board board, String originalFileName, String storedFileName){
        return BoardFile.builder()
                .originalFileName(originalFileName)
                .storedFileName(storedFileName)
                .board(board)
                .build();
    }
}
