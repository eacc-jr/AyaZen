package com.ayazen.AyaZen.repository;

import com.ayazen.AyaZen.entity.AyazenProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AyazenProjectRepository  extends  JpaRepository<AyazenProject, Long>{
}
