package com.rimin.Ori_Shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    
    
    // item 테이블 아이디 상위 3 항목 조회하여 가져오기
    public List<Item> findTop3Items() {
        List<Item> itemList = itemRepository.findTop3ByOrderById();
        return itemList;
    }
    
    
    
    
    
    // 신규 상품 등록
    public void saveItem(String itemName, Integer price) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        itemRepository.save(item);
    }

}
