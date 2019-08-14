package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIFile;
@Controller
/*
 * 	文件上传步骤
 * 	1.获取文件名称
 *  2.准备文件上传路径
 *  3.判断文件夹是否存在
 *  4.实现文件上传
 *  
 * */

public class FileController {
   //实现文件上传的案例
	//如果上传完成 重定向到上传页面
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//获取的input标签中name属性的名称
		String name = fileImage.getName();
		//获取的是文件真实名称
				String fileName = fileImage.getOriginalFilename();
				System.out.println(name);
				System.out.println(fileName);
			
               File dirFile =new File("D:/1-JT/jt-upload");
		
        		if(!dirFile.exists()) {
        			//如果文件夹不存在,则创建文件夹
        			dirFile.mkdirs(); //创建多级目录
        			//dirFile.mkdir();  //创建一级目录
        		}
        		
        		//实现文件的上传
        		File file =new File("D:/1-JT/jt-upload/"+fileName);
        		fileImage.transferTo(file);

		
		
		//默认的转发才会执行视图解析器的代码
		return "redirect:/file.jsp";
		
	}
	@Autowired
	FileService fileService;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public EasyUIFile fileUpload(MultipartFile uploadFile) {
		
		return fileService.fileUpload(uploadFile);
	}
}
	
	
	

