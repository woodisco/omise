package com.kadai.omise.repository;

import com.kadai.omise.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
    Member save(Member restaurant);
}
