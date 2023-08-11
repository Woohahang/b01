package org.zercok.b01.repository;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zercok.b01.domain.Board;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


@Log4j2

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test // 테이블에 백개의 행 추가하기
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title..." + i)
                    .content("content..." + i)
                    .writer("user" + (i % 10))
                    .build();

            Board result = boardRepository.save(board);
            log.info("BNO: " + result.getBno());
        });
    }


    @Test
    public void testSelect() {

        // 보더레포시토리가 상속받고 있는 클래스가 있어서 참조할 수 있는 메서드가 있다.
        Optional<Board> result = boardRepository.findById(1L);
        log.info("\ntestSelect 메서드 작동\n" + result + "\n\n");

        Board board = boardRepository.findById(1L).get();
        log.info("\nfinById를 get 했다.\n" + board + "\n\n");

        log.info("\nBno 얻었다. \n" + board.getBno());


        Optional<Board> resultOver = boardRepository.findById(200000L);
        log.info("\n값이 없는 걸 조회했다. \n" + resultOver);

        Optional<Board> result2 = boardRepository.findById(50L);
        Board board1 = result2.orElseThrow();
        log.info("값이 있으면 반환, 없으면 에러 발생" + board1);

    }


    @Test // 하나 지정해서 삭제하기
    public void testDeleteOne() {
        boardRepository.deleteById(100L);
        long cnt = boardRepository.count();
        log.info("\n엔티티 갯수 : " + cnt);
    }

    @Test // 전부 다 삭제하기
    public void testDeleteAll() {
        boardRepository.deleteAll();
        long cnt = boardRepository.count();
        log.info("\n엔티티 갯수 : " + cnt);

    }


    @Test // 테이블에 한 행 추가하기
    public void insertTest() {
        Board board = Board.builder()
                .title("title Test...1")
                .content("content...1")
                .writer("user1")
                .build();


        boardRepository.save(board); // 위에 만든 테이블을 테이블에 넣고 저장
    }

    @Test
    public void updateTest() {


        // 이거 수정하고 싶으면 set 메서드를 써야한다. 근데 우린 보더를 DTO 개념이라 @Getter 만 적용했다.
        // 선생님은 보더 클래스에 @Setter 를 넣었지만 난 안 했다.
        // @Setter 를 넣었다면 문법은 다음과 같다.

        Board board101 = boardRepository.findById(101L).get();
//      board101.setTitle("title 수정함"); // 현재는 @Setter 를 사용하지 않아 setTitle 메서드가 없다.
        boardRepository.save(board101);

    }


    @Test // 페이징
    public void testPaging() {
        Pageable pageable = PageRequest.of(3, 10); // 3페이지 에서 사이즈는 10으로 // 참고로 0페이지 부터니까 헷갈 ㄴㄴ

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("\n페이징 기능을 사용해 0부터 10까지 조회를 했다.\n" + result.getTotalPages()); // 10이 나온다.
        log.info(result.getTotalElements()); // 전체 몇 행이 있는지
        log.info(result.getNumber()); // 현재 몇 페이지 인지
        log.info(result.getSize()); // 현재 사이즈가 몇개인지


        List<Board> boardsList = result.getContent();
        boardsList.forEach(ddddd -> log.info(ddddd));
        // 존내 편하다 대신 최신글이 위로 올라가야겠지?

    }


    @Test // 페이징 최신 순서로! ㄷㄷ 개편하다
    public void testPaging2() {
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending()); // 3페이지 에서 사이즈는 10으로 // 참고로 0페이지 부터니까 헷갈 ㄴㄴ

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("\n페이징 기능을 사용해 0부터 10까지 조회를 했다.\n" + result.getTotalPages()); // 10이 나온다.
        log.info(result.getTotalElements()); // 전체 몇 행이 있는지
        log.info(result.getNumber()); // 현재 몇 페이지 인지
        log.info(result.getSize()); // 현재 사이즈가 몇개인지


        List<Board> boardsList = result.getContent();
        boardsList.forEach(ddddd -> log.info(ddddd));
        // 존내 편하다 대신 최신글이 위로 올라가야겠지?

    }


    @Test // 정확하게 검색하기 !
    public void testSearch1() {

        // 보더레포지토리에서 타이틀로 검색하겠다 "title...52"
        // findByTitle 이 메서드는 내가 정의한거다. 어디에? BoardRepository 에다가
        List<Board> boardList = boardRepository.findByTitle("title...52");

        boardList.forEach(boooo -> log.info("\n검색했다\n" + boooo));

    }


    @Test // 포함된거 다 검색한다! Containing 포함된걸 검색한다. 라는 뜻
    public void testSearch2() {

        // Content 에 2가 포함된 행을 다 검색한다!
        List<Board> boardList = boardRepository.findByContentContaining("2");

        boardList.forEach(boooo -> log.info("\n검색했다\n" + boooo));

        log.info("\n검색 된 수 : " + boardList.size()); // 몇개 검색 됐지? 보는거

    }


    @Test // 엔드로 검색한다
    public void testSearch3() {


        List<Board> boardList = boardRepository.findByContentContainingAndWriterContaining("2", "1");

        boardList.forEach(boooo -> log.info("\n검색했다\n" + boooo));

        log.info("\n검색 된 수 : " + boardList.size()); // 몇개 검색 됐지? 보는거

    }

    @Test // 오알로 검색한다.
    public void testSearch4() {

        List<Board> boardList = boardRepository.findByContentContainingOrWriterContaining("2", "1");

        boardList.forEach(boooo -> log.info("\n검색했다\n" + boooo));

        log.info("\n검색 된 수 : " + boardList.size()); // 몇개 검색 됐지? 보는거

    }


    @Test // 검색 후 페이징 기능
    public void testSearch5() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending() );
        List<Board> boardList = boardRepository.findByContentContaining("2", pageable);

        boardList.forEach(board -> log.info("\n이거 된건가?"+board));
        log.info(boardList.size());

    }


}

