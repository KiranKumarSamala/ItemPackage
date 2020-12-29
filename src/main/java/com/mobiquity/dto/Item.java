package com.mobiquity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Kiran Kumar Samala
 * 
 * Item is used to hold each Package Item
 */
@Getter
@Builder
@ToString
public final class Item implements Comparable<Item> {
	private int index;
	private double weight;
	private int cost;

	@Override
	public int compareTo(Item o) {
		return (int) (this.getWeight() - o.weight);
	}

}
