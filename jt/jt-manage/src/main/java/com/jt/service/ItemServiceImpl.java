package com.jt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.jt.mapper.ItemCatMapper;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.EasyUIData;
import com.jt.vo.EasyUITree;

import redis.clients.jedis.Jedis;
@Service
public class ItemServiceImpl implements ItemService {
	
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Autowired
	private ItemCatMapper itemCatMapper;

	
	/**根据页面要求分页查询数据
	      参数说明:  page: 当前查询的页面  rows:每页行数
	      分页sql语句:
	   		第一页:
	   		select * from tb_item limit 0,20;  19
	   		第二页:
	   		select * from tb_item limit 20,20
	   		第三页:
	   		select * from tb_item limit 40,20
	   		第n页:
	   		select * from tb_item limit (page-1)*rows,20
	 */
	@Override
	public EasyUIData findItemByPage(Integer page, Integer rows) {
		//1.查询商品列表记录总数  
		int total = itemMapper.selectCount(null);
		//2.根据分页查询数据
		int start = (page - 1) * rows;
		List<Item> itemList = 
				itemMapper.selectItemByPage(start,rows);
		return new EasyUIData(total, itemList);
	}

	@Override
	@Transactional	//控制事务
	public void saveItem(Item item,String desc) {
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//封装itemDesc实现入库操作
				ItemDesc itemDesc = new ItemDesc();
				itemDesc.setItemId(item.getId());
				itemDesc.setItemDesc(desc);
				itemDesc.setCreated(item.getCreated());
				itemDesc.setUpdated(item.getCreated());
				itemDescMapper.insert(itemDesc);
		
		
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
	   
		return itemDescMapper.selectById(itemId);
	}

	@Override
	public void updateItem(Item item, String desc) {
		item.setUpdated(new Date());
		itemMapper.updateById(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}
/**
 * 业务实现：
 *         要求批量实现数据修改ids 100，101，102，103
 *         sql实现:
 *         update tb_item set status =#{status} where id in (100,101...)
 */
	@Override
	public void updateStatus(Long[] ids, int status) {
				Item item =new Item();
				item.setStatus(status)
				    .setUpdated(new Date());
				
				//将数组转化为集合数据List
				List<Long> idList =Arrays.asList(ids);
				UpdateWrapper<Item> updateWrapper =
						new UpdateWrapper<Item>();
				updateWrapper.in("id", idList);
				itemMapper.update(item, updateWrapper);
			
				
		
	}

@Override
public void delete(Long[] ids) {
	    List<Long> list=Arrays.asList(ids);
	    itemMapper.deleteBatchIds(list);
	    
	 
	
}

@Override
//
public Item findItemById(Long itemId) {
	
	return itemMapper.selectById(itemId);
}






}

