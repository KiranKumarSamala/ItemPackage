package com.mobiquity.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Kiran Kumar Samala
 * 
 * ItemPackage is used to hold each Package information
 */
@Getter
@Builder
@ToString
public class ItemPackage {

	private List<Item> items;
	private int packageWeight;

}
