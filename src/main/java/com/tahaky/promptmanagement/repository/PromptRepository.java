package com.tahaky.promptmanagement.repository;

import com.tahaky.promptmanagement.model.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {

    Optional<Prompt> findByNameAndActive(String name, Boolean active);

    List<Prompt> findByActive(Boolean active);

    List<Prompt> findByCategoryAndActive(String category, Boolean active);

    @Query("SELECT p FROM Prompt p WHERE p.name = :name ORDER BY p.version DESC")
    List<Prompt> findAllVersionsByName(String name);

    Optional<Prompt> findTopByNameOrderByVersionDesc(String name);

    Optional<Prompt> findFirstByActive(Boolean active);
}
