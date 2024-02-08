package com.tenco.blog.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;
    private String title;
    private String content;
    private String author;

    public void update(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
