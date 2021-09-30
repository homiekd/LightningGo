package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.ProductBrand;
import com.kdwu.lightninggo.model.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBrandDao extends JpaRepository<ProductBrand, Integer> {
    @Query(value = "SELECT pb FROM ProductBrand pb WHERE ( pb.code LIKE %:code% ) " +
            " AND ( pb.name LIKE %:name% ) " +
            " AND ( pb.description LIKE %:description% ) ")
    public Page<ProductBrand> findAllBySearchContext(@Param("code") String code,
                                                     @Param("name") String name,
                                                     @Param("description") String description,
                                                     Pageable pageable);
}
