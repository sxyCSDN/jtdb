package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUIData;
import com.jt.vo.SysResult;

import sl.org.objectweb.asm.tree.TryCatchBlockNode;

//EasyUI要求返回的数据都是json
@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 1.根据页面要求进行分页查询
	 * http://localhost:8091/item/query?page=1&rows=20
	 */
	@RequestMapping("/query")
	public EasyUIData findItemByPage
				(Integer page,Integer rows) {
		
	return itemService.findItemByPage(page,rows);
	}
	
	
	//实现商品新增
	@RequestMapping("/save")
	    public SysResult saveItem(Item item,String desc) {
			
		try {
			//同时入库2张表,完成商品/商品新增入库
			itemService.saveItem(item,desc);
			return SysResult.ok(); 
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	
		
	}
	
	/**
	 * 实现商品详情回显
	 * 如果调用报错了,必须返回201
	 * 如果调用成功  返回200,并且data
	 * 
	 * 方式1:try-catch
	 * 方式2:全局异常处理机制  复习!!! aop
	 * @param itemId
	 * @return
	 */
	
	@RequestMapping("/query/item/desc/{itemId}")
	public SysResult findItemDescById
	(@PathVariable Long itemId) {
		try {
			ItemDesc itemDesc = 
					itemService.findItemDescById(itemId);
			return SysResult.ok(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//实现商品更新操作
	    @RequestMapping("/update")
	    public SysResult updateById(Item item,String desc) {
	    	try {
				itemService.updateItem(item,desc);
				return SysResult.ok();
	    		
			} catch (Exception e) {
			     e.printStackTrace();
			     return SysResult.fail();
				
			}
	    }
	    @RequestMapping("/instock")
	    public SysResult instock(Long[] ids) {
			try {
				int status=2;//2代表下架
				itemService.updateStatus(ids,status);
				return SysResult.ok();
			} catch (Exception e) {
				e.printStackTrace();
				return SysResult.fail();
			}
		
	    	
	    }
	    
	   @RequestMapping("/reshelf")
	   
	public SysResult reshelf(Long[] ids) {
		   try {
			int status=1;//1代表上架
			itemService.updateStatus(ids, status);
			return SysResult.ok();
		
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
		   
	   }
	   @RequestMapping("/delete")
	   public SysResult delete(Long[] ids) {
		   try {
			   itemService.delete(ids);
			   return SysResult.ok();
			
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	   }
	 //实现文件上传 富文本编辑器进行文件上传
		

	   
	    
}

	          