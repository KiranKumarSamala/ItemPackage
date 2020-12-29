package com.mobiquity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.ItemCombinations;
import com.mobiquity.dto.ItemPackage;
import com.mobiquity.exception.APIException;
import com.mobiquity.util.FileContentParser;
import com.mobiquity.util.PackageInfoReader;

/**
 * @author Kiran Kumar Samala
 * 
 *  The core optimal packing logic is calculated in this class. Backtracking with memorization is used to calculate
 */
public class PackingService {

	private PackageInfoReader reader;
	private FileContentParser parser;

	public PackingService(PackageInfoReader reader, FileContentParser parser) {
		super();
		this.reader = reader;
		this.parser = parser;
	}

	public String getItemsPerPack(String filePath) throws APIException {

		return reader.readFile(filePath).stream().map(parser::parseLines).map(this::findOptimalSolution)
				.collect(Collectors.joining("\n"));

	}

	/**
	 * All the package items are sorted by weight in ascending order. The items with more than the package weight are filtered.
	 * This will ensure to reduce the number of iteration 
	 * 
	 * @param itemPackage
	 * @return
	 */
	private String findOptimalSolution(ItemPackage itemPackage) {
		List<ItemCombinations> resultList = new ArrayList<>();
		itemPackage.getItems().sort(Comparable::compareTo);

		// Filter items if weight is greater than package weight
		List<Item> filteredItemsList = itemPackage.getItems().stream().filter(item -> (item.getWeight() <= itemPackage.getPackageWeight()))
				.collect(Collectors.toList());

		findOptimalItemsPerPackage(filteredItemsList, itemPackage.getPackageWeight(), resultList, 0, null);
		return getResult(resultList);
	}

	
	/**
	 * It filters the item combinations with maximum cost. In case the cost is same for multiple combinations then it will filter the combination
	 * with lesser weight  
	 * 
	 * @param resultList
	 * @return
	 */
	private String getResult(List<ItemCombinations> resultList) {
		double maxCost = Integer.MIN_VALUE;
		double minWeight = Integer.MAX_VALUE;
		ItemCombinations res = null;
		for (ItemCombinations result : resultList) {
			if (result.getCost() > maxCost) {
				maxCost = result.getCost();
				minWeight = result.getWeight();
				res = result;
			} else if (result.getCost() == maxCost && result.getWeight() <= minWeight) {
				maxCost = result.getCost();
				minWeight = result.getWeight();
				res = result;
			}
		}

		if (res == null) {
			return "-";
		}

		return res.getIndexList().stream().sorted().map(String::valueOf).collect(Collectors.joining(","));
	}

	
	/**
	 * 
	 * This method uses backtracking to calculate the optimal item per package. Memorization is used to hold the information of each stage
	 * 
	 * @param items
	 * @param packageWeight
	 * @param resultList
	 * @param listIndex
	 * @param result
	 */
	private void findOptimalItemsPerPackage(List<Item> items, int packageWeight, List<ItemCombinations> resultList,
			int listIndex, ItemCombinations result) {

		for (int i = listIndex; i < items.size(); i++) {

			Item item = items.get(i);
			if (result == null) {
				result = createResult(item);
			} else {
				double cummulativeWeight = result.getWeight() + item.getWeight();
				if (cummulativeWeight <= packageWeight) {
					result.setWeight(cummulativeWeight);
					result.setCost(result.getCost() + item.getCost());
					result.getIndexList().add(item.getIndex());
				} else if (cummulativeWeight > packageWeight) {
					return;
				}
			}

			findOptimalItemsPerPackage(items, packageWeight, resultList, i + 1, result);

			resultList.add(copyResult(result));
			int indexOfIndex = result.getIndexList().indexOf((items.get(i).getIndex()));
			if (indexOfIndex >= 0) {

				result.getIndexList().remove(indexOfIndex);
				result.setWeight(result.getWeight() - items.get(i).getWeight());
				result.setCost(result.getCost() - items.get(i).getCost());
			}
		}
	}

	static ItemCombinations copyResult(ItemCombinations result) {
		ItemCombinations res = new ItemCombinations();
		res.setCost(result.getCost());
		res.setWeight(result.getWeight());
		res.setIndexList(new ArrayList<>());
		res.getIndexList().addAll(result.getIndexList());

		return res;
	}

	private ItemCombinations createResult(Item item) {
		ItemCombinations res = new ItemCombinations();
		res.setCost(item.getCost());
		res.setWeight(item.getWeight());
		res.setIndexList(new ArrayList<>());
		res.getIndexList().add(item.getIndex());

		return res;
	}
}
