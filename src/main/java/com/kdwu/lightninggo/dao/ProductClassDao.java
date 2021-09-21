package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.ProductClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductClassDao extends JpaRepository<ProductClass, Integer> {
}
