package com.birlasoft.digisole.service;

import com.birlasoft.digisole.model.ProductVO;

/**
 * @author Melvin Mathew
 * 
 * Validates the product details when admin user is trying to add a new product.
 * 
 * validateProdID(ProductVO): Validates the product ID for a given input. The product initial 2
 * characters are always one among 'L-', 'M-', 'A-'. Return a boolean value depending on the input.
 * 
 * validateRating(ProductVO): Validates the rating of the product to be below 5. Return a boolean 
 * value depending on the input.
 *
 */
public class ProductBO {

	/**
	 * @param product
	 * @return
	 */
	public static boolean validateProdID(ProductVO product) {
		
		if(product.getProdID() == null) {
			return false;
		}
		
		if(product.getProdID().substring(0, 2).equalsIgnoreCase("L-") || 
				product.getProdID().substring(0, 2).equalsIgnoreCase("M-") ||
				product.getProdID().substring(0, 2).equalsIgnoreCase("A-")) {
			
			return true;
		}
		
		return false;
		
	}
	
	/**
	 * @param product
	 * @return
	 */
	public static boolean validateRating(ProductVO product) {
		
		if(product.getRating() == 0) {
			
			return false;
		}
		
		if(product.getRating() <= 5.0) {
			
			return true;
		}
		
		return false;
		
	}
	
}
