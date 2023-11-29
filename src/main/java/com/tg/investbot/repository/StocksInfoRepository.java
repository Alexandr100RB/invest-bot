package com.tg.investbot.repository;

import com.tg.investbot.model.StocksInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @since 29.11.2023
 */
@Repository
public interface StocksInfoRepository extends JpaRepository<StocksInfo, String> {
    List<StocksInfo> findAll();
}
