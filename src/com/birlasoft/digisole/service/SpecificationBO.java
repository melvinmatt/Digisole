package com.birlasoft.digisole.service;

import com.birlasoft.digisole.model.SpecificationVO;
 
/**
 * @author Melvin Mathew
 * 
 * Validates the specification details when admin user is trying to add a new product.
 * 
 * validateProdID(ProductVO): Validates the specification ID for a given input. The product initial 
 * 2 characters are always one among 'LS-', 'MS-', 'AS-'. Return a boolean value depending on 
 * the input.
 *
 */
public class SpecificationBO {

	/**
	 * @param specs
	 * @return
	 */
	public static boolean validateSpecID(SpecificationVO specs) {
		
		if(specs.getSpecID() == null) {
			return false;
		}
		
		if(specs.getSpecID().substring(0, 3).equalsIgnoreCase("LS-") || 
				specs.getSpecID().substring(0, 3).equalsIgnoreCase("MS-") ||
				specs.getSpecID().substring(0, 3).equalsIgnoreCase("AS-")) {
			
			return true;
		}
		
		return false;
		
	}
	
}
