package com.example.board.dto;

import com.example.board.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDTO toCommentDTO(Comment comment, Long boardId) {
        return CommentDTO.builder()
                .id(comment.getId())
                .commentWriter(comment.getCommentWriter())
                .commentContents(comment.getCommentContents())
                .commentCreatedTime(comment.getCreatedTime())
                .boardId(boardId)
                .build();
    }
}
