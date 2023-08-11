package org.zercok.b01.domain;


import lombok.*;

import javax.persistence.*;



@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString



// 엔티티 객체란 쉽게 말해 PK(기본 키)를 가지는 자바의 객체다. 엔티티 객체는 고유의 식별을 위해 @ID를 써야한다.

@Entity // 데이터베이스에서 테이블을 만드는 번거로움이 없어진다! 이 어노테이션만 있으면 그으으으냥 보더라는 테이블 바로 만들어짐
// 1) 데이터베이스에서 알아서 결정하는 방식(위임) - MySQL/MariaDB GenerationType.IDENTITY
// 2) 데이터베이스 시퀀스 오브젝트 사용(ORACLE) - 오라클 쓰려면 시퀀스오브젝트 필요하고, @SequenceGenerator 필요

// 마리아 디비 쓸거면 1번, 오라클 쓸거면 2번 써야한다.

// 3) 키 생성용 테이블 사용 - @TableGeneratoer 필요
// 4) 방언(마리아DB 용 SQL)에 따라 지정, 기본 값
// 지금은 일번 쓴다
public class Board extends BaseEntity {

    @Id // 얘는 주키다! 이게 있어야 엔티티 에러가 안 난다!!
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 얘는 등록 될 때마다 1씩 증가하는 역할
    private Long bno; // 보더 넘버라는 의도로 만들었다.

    @Column(length = 500, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 2000, nullable = false)
    private String writer;

}

//starter-data-jpa 덕분에 @Entitiy 쓸 수 있다.


/* @Column(length = 500, nullable = false)
길이는 500자, 널은 안 된다. */