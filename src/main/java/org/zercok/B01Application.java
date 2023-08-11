package org.zercok;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Log4j2
@SpringBootApplication
@EnableJpaAuditing
public class B01Application {

    public static void main(String[] args) {
        SpringApplication.run(B01Application.class, args);
        log.info("메인 메서드 동작");
    }

}


/* @EnableJpaAuditing
목적 : @EntityListeners 를 활성화 시킨다. */

