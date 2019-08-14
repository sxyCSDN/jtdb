package com.jt.service;

import java.util.List;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUIData;
import com.jt.vo.EasyUITree;

public interface ItemService {
	
	EasyUIData findItemByPage(Integer page, Integer rows);

	void saveItem(Item item, String desc);

	ItemDesc findItemDescById(Long itemId);

	void updateItem(Item item, String desc);

	void updateStatus(Long[] ids, int status);

	void delete(Long[] ids);
   //前台通过id获取商品的信息
	Item findItemById(Long itemId);

	
}
