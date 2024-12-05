package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모 생성 기능
    @PostMapping
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // @RequestBody : 클라이언트로부터 JSON파일을 요청받았을 때, 파라미터로 바로 바인딩


        // 식별자가 1씩 증가하도록 만듦
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1; //memoList에 있는 키 값을 다 꺼내서 그 중에 최댓값 +1

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, requestDto.getTitle(), requestDto.getContents());

        // Inmemory DB에 Memo 메모
        memoList.put(memoId, memo);

        return new MemoResponseDto(memo);
    }

    // 메모 조회 기능
    @GetMapping("/{id}")
    public MemoResponseDto findMemoById(@PathVariable Long id) {
        //@PathVariable 식별자를 파라미터로 바인딩할 때 사용

        Memo memo = memoList.get(id); //조회된 메모 객체

        return new MemoResponseDto(memo);
    }

    // 메모 수정 기능
    @PutMapping("/{id}")
    public MemoResponseDto updateMemoById(
            @PathVariable Long id,
            @RequestBody MemoRequestDto requestDto
    ) { // 실제 동작할 로직
        Memo memo = memoList.get(id);

        memo.update(requestDto);

        return new MemoResponseDto(memo);
    }

    // 메모 삭제 기능
    @DeleteMapping("/{id}")
    public void deleteMemo(
            @PathVariable Long id
    ) {
        memoList.remove(id);
    }

}
