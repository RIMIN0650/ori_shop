package com.rimin.Ori_Shop.sales.repository;

import com.rimin.Ori_Shop.sales.domain.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales, Long> {
}
