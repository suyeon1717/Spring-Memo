package com.example.memo.controller;

import com.example.memo.dto.MemoRequestDto;
import com.example.memo.dto.MemoResponseDto;
import com.example.memo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/memos")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();

    // 메모 생성 기능
    @PostMapping
    public ResponseEntity<MemoResponseDto> createMemo(@RequestBody MemoRequestDto requestDto) {
        // ResponseEntity : 상태 코드 반환
        // @RequestBody : 클라이언트로부터 JSON파일을 요청받았을 때, 파라미터로 바로 바인딩


        // 식별자가 1씩 증가하도록 만듦
        Long memoId = memoList.isEmpty() ? 1 : Collections.max(memoList.keySet()) + 1; //memoList에 있는 키 값을 다 꺼내서 그 중에 최댓값 +1

        // 요청받은 데이터로 Memo 객체 생성
        Memo memo = new Memo(memoId, requestDto.getTitle(), requestDto.getContents());

        // Inmemory DB에 Memo 메모
        memoList.put(memoId, memo);

        return new ResponseEntity<>(new MemoResponseDto(memo), HttpStatus.CREATED);
    }

// 메모 전체 목록 조회 기능
    /*
    - 메모 전체 목록을 조회할 수 있다. (READ)
    - 여러개의 데이터를 배열 형태로 한번에 응답한다.
    - 데이터가 없는 경우 비어있는 배열 형태로 응답한다.
    - 응답 상태코드는 200 OK로 설정한다.
     */

    @GetMapping
    public ResponseEntity<List<MemoResponseDto>> findAllMemos(){
        // init List
        List<MemoResponseDto> responseList = new ArrayList<>();

        // HashMap<Memo> -> List<MemoResponseDto> 형태로
        for(Memo memo : memoList.values()) {
            MemoResponseDto responseDto = new MemoResponseDto(memo);
            responseList.add(responseDto);
        }

        // Map To List
//        responseList = memoList.values().stream().map(MemoResponseDto::new).toList();

        // 데이터가 없으면 204 No Content, 있으면 200 OK
        if (responseList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
        } else {
            return new ResponseEntity<>(responseList, HttpStatus.OK); // 200 OK
        }
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
