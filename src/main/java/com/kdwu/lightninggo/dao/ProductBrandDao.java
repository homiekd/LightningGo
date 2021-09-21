package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.ProductBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandDao extends JpaRepository<ProductBrand, Integer> {
}
