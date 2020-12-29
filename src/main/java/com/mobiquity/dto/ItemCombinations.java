package com.mobiquity.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Kiran Kumar Samala
 * 
 * ItemCombinations is used to hold list of unique item index , their total weight and total cost . Where the total weight is lesser tht or equal to package weight
 */
@Getter
@Setter
@ToString
public class ItemCombinations {

	/*
	 * hold list of unique item index for which the total weight is lesser than or equal to package weight 
	 */
	List<Integer> indexList;
	
	/*
	 *  holds the total weight of items , where is index information is available with @indexList
	 */
	double weight;
	
	/*
	 *  holds the total cost of items , where is index information is available with @indexList
	 */
	double cost;

}
