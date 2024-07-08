package com.web.garimaElectrical.global;

import java.util.ArrayList;
import java.util.List;
import com.web.garimaElectrical.model.productModel;


public class globalVariable {
	
	public static List<productModel> cart;
	
	static {
		cart=new ArrayList<productModel>();
	}

}
