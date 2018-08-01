package com.mz.mreal.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {
    @Query("select m from Meme m where m.id > 1")
    List<Meme> findThemMemes();

    @Transactional
    @Modifying
    @Query("update Meme m set m.upvotes=m.upvotes+1 where m.id=:id")
    void upvote(@Param("id") Long id);
}

