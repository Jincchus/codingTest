package com.tenco.blog.board;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardSaveFormDto {
    private Integer no;
    private String title;
    private String content;
    private String author;
}
