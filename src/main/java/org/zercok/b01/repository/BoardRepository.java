package org.zercok.b01.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zercok.b01.domain.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    
    List<Board> findByTitle(String searchKeyword); // 목적 : 정확하게 조회하기


    List<Board> findByWriter(String searchKeyword); // 목적 : 포함 된 것 조회하기
    // 목적에 맞게 쓰기 위해 Containing 키워드 사용 사용법은 아래와 같다.

    List<Board> findByContentContaining(String searchKeyword); // Content 라는 항목을 Containing으로 검색한거다. 메서드 명으로 정의하는듯?



    List<Board> findByContentContainingAndWriterContaining(String contentSearch, String writerSearch); // Content 를 앤드로 검색했다.
    List<Board> findByContentContainingOrWriterContaining(String contentSearch, String writerSearch); // Content 를 오알로 검색했다.


    // 조건 검색 후 페이징 처리
    List<Board> findByContentContaining(String searchkeyord, Pageable pageable);

}

/* public interface BoardRepository extends JpaRepository<Board, Long>
상속 받고 테이블명은 보더, 주키는 롱 타입으로 선언 되어 있어서 이렇게 해야 된다. */

/* Spring Data JPA를 이용할 때는 JpaRepository 라는 인터페이스를
* 이용해 인터페이스 선언만으로 데이터베이스 관련 작업을 어느 정도 처리할 수 있다.
* ( 마치 MyBatis를 이용할 때 매퍼 인터페이스만을 선언하는 것과 유사 )
* 개발 단계에서 JpaRepository   */