package com.example.casinoserver.repositories;

import com.example.casinoserver.entities.GameLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameLogRepository extends JpaRepository<GameLog, Integer> {
}
