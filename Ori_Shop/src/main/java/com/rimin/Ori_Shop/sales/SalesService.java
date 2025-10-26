package com.rimin.Ori_Shop.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService {

    private final SalesRepository salesRepository;

    
    // 모든 주문 목록 가져오기
    public List<Sales> getAllOrderList(){
        List<Sales> salesList = salesRepository.findAll();
        return salesList;
    }


}
