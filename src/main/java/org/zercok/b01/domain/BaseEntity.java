package org.zercok.b01.domain;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;



/* 데이터베이스의 거의 모든 테이블에는 데이터가 추가된 시간이나 수정된 시간 등이 칼럼으로 작성되므로 이를 쉽게 처리하고자 */
/* @MappedSuperClass를 이용해서 공통으로 사용되는 칼럼들을 지정하고 해당 클래스를 쉽게 처리 */


@MappedSuperclass
/* 상속받은 엔터티에 매핑 정보가 적용되는 클래스를 지정합니다. 매핑된 슈퍼클래스에는 별도의 테이블이 정의되어 있지 않습니다. */
@EntityListeners(value = {AuditingEntityListener.class})
abstract class BaseEntity {

    @CreatedDate 
    @Column(name = "reg_date", updatable = false) // 생성 시간 기록 업데이트 기록하지마라! 펄스
    private LocalDateTime regDate; // 시간까지 나오게 하기 위해 타입을 이걸로 함

    @LastModifiedDate
    @Column(name ="modd_ate") // 업데이트 기본값 트루다! 업데이트가 이루어지면 기록한다! 트루!
    private LocalDateTime modDate;


}

// 이 클래스를 왜 만들었냐? 최상위 보더 개념
// 여기 필드는 상속 받은 클래스에 @Entity 가 있으니 알아서 테이블에 있을거다.




/* @EntityListeners(value = {AuditingEntityListener.class})
목적 : 한 행이 추가 되면 날짜 시간을 기록해서 필드에 넣는다.
엔티티 또는 맵핑된 수퍼클래스에 사용할 콜백 리스너 클래스를 지정합니다. 이 주석은 엔티티 클래스 또는 매핑된 슈퍼클래스에 적용될 수 있습니다. */