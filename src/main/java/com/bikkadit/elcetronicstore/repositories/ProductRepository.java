package com.bikkadit.elcetronicstore.repositories;

import com.bikkadit.elcetronicstore.entities.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Products, String> {


    Page<Products> findByTitleContaining(String keyword,Pageable pageable);

    Page<Products> findByLiveTrue(Pageable pageable);

    Page<Products> findByBrandContaining(String keyword,Pageable pageable);


}
