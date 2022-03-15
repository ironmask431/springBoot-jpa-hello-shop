package com.basic.test;

import com.basic.domain.Board;
import org.junit.Test;

public class LombokTests {

    //롬복 @RequiredArgsConstructor 테스트
    @Test
    public void testRequiredArgsConstructor(){
        Board board = new Board(1);
        System.out.println(board);
    }

    //롬복 @Getter, @Setter 테스트
    @Test
    public void testGetterSetter(){
        Board board = new Board(1);
        board.setTitle("테스트제목");
        System.out.println(board.getTitle());
    }
}
