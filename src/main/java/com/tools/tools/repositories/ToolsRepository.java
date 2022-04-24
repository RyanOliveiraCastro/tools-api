package com.tools.tools.repositories;

import com.tools.tools.model.Tools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ToolsRepository extends JpaRepository<Tools, Long> {

    boolean existsBytitle(String title);
}
