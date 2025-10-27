package com.rimin.Ori_Shop.sales.repository;

import com.rimin.Ori_Shop.sales.domain.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Long> {

    public List<Sales> findAllByMemberId(Long memberId);

}
