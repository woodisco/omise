package com.kadai.omise.repository;

import com.kadai.omise.domain.Member;
import org.springframework.data.repository.CrudRepository;

// 会員Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    // 登録
    Member save(Member restaurant);
}
