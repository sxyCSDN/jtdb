package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUIFile {
	private Integer error=0;//如果没错返回，有错返回0;
	private String  url;
	private Integer width;
	private Integer height;
	 
	
}
