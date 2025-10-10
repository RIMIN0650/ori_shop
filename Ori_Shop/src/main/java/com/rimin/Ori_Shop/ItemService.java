package com.rimin.Ori_Shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String itemName, Integer price) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        itemRepository.save(item);
    }

}
