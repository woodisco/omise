package com.kadai.omise.repository;

import com.kadai.omise.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
    会員Repository
*/
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /*
        照会処理 (email)
        @param String email
    */
    Optional<Member> findByEmail(String email);

    /*
        重複バリデーション処理 (email)
        @param String email
    */
    boolean existsByEmail(String email);
}
