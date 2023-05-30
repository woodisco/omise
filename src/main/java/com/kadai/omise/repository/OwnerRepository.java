package com.kadai.omise.repository;

import com.kadai.omise.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/*
    オーナーRepository
*/
@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    /*
        照会処理 (email)
        @param String email
    */
    Optional<Owner> findByEmail(String email);

    /*
        重複バリデーション処理 (email)
        @param String email
    */
    boolean existsByEmail(String email);
}