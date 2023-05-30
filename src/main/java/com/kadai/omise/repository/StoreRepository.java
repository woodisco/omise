package com.kadai.omise.repository;

import com.kadai.omise.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    Page<Store> findByNameOrRouteOrCategory(String searchName, String searchRoute, String searchCategory, Pageable pageable);

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

    /*
        重複バリデーション処理 (name)
        @param String email
    */
    boolean existsByName(String name);

    @Query(value = "SELECT * FROM store s where s.owner_id = :owner_id", nativeQuery = true)
    List<Store> updateStoreList(@Param("owner_id") Long ownerId);
}