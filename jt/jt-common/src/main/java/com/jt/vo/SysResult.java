package com.jt.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult implements Serializable{
	      private Integer status;  //200表示业务成功 201表示
	      private String msg;   //返回服务器提示信息
	      private Object data; //表示服务器数据
	      /**
	       * 
	       */
	      	
	      		public static SysResult ok() {

	      			return new SysResult(200,"调用成功!",null);

	      		}

	      		public static SysResult ok(Object data) {

	      			return new SysResult(200,"调用成功!",data);
	      		}

	      		public static SysResult ok(String msg,Object data) {

	      			return new SysResult(200,msg,data);
	      		}

	      		public static SysResult fail() {

	      			return new SysResult(201, "业务调用失败", null);
	      		}

	      		public static SysResult fail(String msg) {

	      			return new SysResult(201, msg, null);
	      		}
	      	}