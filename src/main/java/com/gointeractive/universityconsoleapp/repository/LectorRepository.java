package com.gointeractive.universityconsoleapp.repository;

import com.gointeractive.universityconsoleapp.entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {

    @Query("SELECT l FROM Lector l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Lector> searchLectorsByKeyword(@Param("keyword") String keyword);

}

