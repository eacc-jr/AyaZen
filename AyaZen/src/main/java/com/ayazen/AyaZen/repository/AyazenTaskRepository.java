package com.ayazen.AyaZen.repository;


import com.ayazen.AyaZen.entity.AyazenTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AyazenTaskRepository extends JpaRepository<AyazenTask, Long> {
}
