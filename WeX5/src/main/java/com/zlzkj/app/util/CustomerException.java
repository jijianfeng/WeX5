package com.zlzkj.app.util;

/**
 * 创 建 人：Liuyk 创建日期：2010-10-11  最后修改人：Liuyk 最后修改日期：2010-10-11
 */
public class CustomerException extends RuntimeException {

    private static final long serialVersionUID = -3491146776554220574L;
    
	public CustomerException() {
	}
	
	public CustomerException(String string) {
		super(string);
	}

}
