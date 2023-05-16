package com.kadai.omise.repository;

import com.kadai.omise.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(value = "SELECT COUNT(DISTINCT address2) AS count FROM store", nativeQuery = true)
    int findAllOfAddress();

    Page<Store> findByName(String searchName, Pageable pageable);
}