package com.example.board.entity;

import com.example.board.dto.CommentDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "comment_table")
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static Comment toSaveEntity(CommentDTO commentDTO, Board board) {
        return Comment.builder()
                .commentWriter(commentDTO.getCommentWriter())
                .commentContents(commentDTO.getCommentContents())
                .board(board)
                .build();
    }
}
