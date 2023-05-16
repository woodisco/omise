package com.kadai.omise.repository;

import com.kadai.omise.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    /*
        お店の都市数取得
    */
    @Query(value = "SELECT COUNT(DISTINCT address2) AS count FROM store", nativeQuery = true)
    int findAllOfAddress();

    /*
        お店Listを取得 (検索機能処理)
        @param String searchName
        @param String searchRoute
        @param Pageable pageable
    */
    Page<Store> findByNameOrRoute(String searchName, String searchRoute, Pageable pageable);

    /*
        カテゴリ取得
    */
    @Query(value = "SELECT DISTINCT category FROM store", nativeQuery = true)
    List<String> findAllOfCategory();

    /*
        路線取得
    */
    @Query(value = "SELECT DISTINCT route FROM store", nativeQuery = true)
    List<String> findAllOfRoute();
}