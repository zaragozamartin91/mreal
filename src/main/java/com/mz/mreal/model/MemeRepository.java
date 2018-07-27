package com.mz.mreal.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {
}

