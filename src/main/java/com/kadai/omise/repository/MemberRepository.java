package com.kadai.omise.repository;

import com.kadai.omise.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
    会員Repository
*/
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    /*
        照会処理 (Email)
        @param String email
    */
    Optional<Member> findByEmail(String email);
}
