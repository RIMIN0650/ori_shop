package com.rimin.Ori_Shop.item.repository;

import com.rimin.Ori_Shop.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findTop3ByOrderById();
}
