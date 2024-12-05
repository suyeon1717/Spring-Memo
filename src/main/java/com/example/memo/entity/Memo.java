package com.example.memo.entity;


import com.example.memo.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class Memo {

    private Long id;
    private String title;
    private String contents;

    public void update(MemoRequestDto requestDto) { // 매개변수: 요청 정보
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void updateTitle(MemoRequestDto requestDto) { // 매개변수: 요청 정보
        this.title = requestDto.getTitle();
    }
}

