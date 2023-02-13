package com.campers.camfp.repository.history;

import org.springframework.data.jpa.repository.JpaRepository;

import com.campers.camfp.entity.history.History;

public interface HistoryRepository extends JpaRepository<History, Long>{

}
