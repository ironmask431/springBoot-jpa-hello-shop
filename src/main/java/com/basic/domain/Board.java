package com.basic.domain;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class Board {
    private final int boardNo;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime regDate;
}
