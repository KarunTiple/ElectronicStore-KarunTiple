package com.bikkadit.elcetronicstore.repositories;

import com.bikkadit.elcetronicstore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
