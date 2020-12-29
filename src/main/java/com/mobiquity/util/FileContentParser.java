package com.mobiquity.util;

import static com.mobiquity.util.Contants.MAX_ALLOWED_ITEMS;
import static com.mobiquity.util.Contants.MAX_ALLOWED_WEIGHT_OR_COST;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.mobiquity.dto.Item;
import com.mobiquity.dto.ItemPackage;
import com.mobiquity.exception.APIException;

/**
 * @author Kiran Kumar Samala
 * 
 *   The class uses regular expression to parse the package information and converts to DTO. 
 */
public class FileContentParser {

	private static final String ITEM_REGEX = "(\\((?<index>\\d+),(?<weight>\\d+\\.\\d+),€(?<cost>\\d+)\\s*\\))+";
	private static final String LINE_REGEX = "(\\d+)\\s:\\s" + ITEM_REGEX;
	private Pattern linePattern = Pattern.compile(LINE_REGEX);
	private Pattern itemPattern = Pattern.compile(ITEM_REGEX);

	
	/**
	 *  Converts the raw package information to ItemPackage DTO
	 *  
	 * @param line
	 * @return
	 * @throws APIException
	 */
	public ItemPackage parseLines(String line) throws APIException {
		if (!isValidParameters(line)) {
			throw new APIException(String.format("Incorrect Parameters %s", line));
		}

		ItemPackage pack = transformRawInfoToDto(line);

		validatePackagesAndItems(pack);

		return pack;
	}

	/*
	 * Verifies if the string contains valid package information 
	 * @param line
	 * @return
	 */
	private boolean isValidParameters(String line) {
		return linePattern.matcher(line).find();
	}

	
	/**
	 * Transforms raw package in string formation to ItemPackage
	 * 
	 * @param line
	 * @return
	 */
	private ItemPackage transformRawInfoToDto(String line) {
		String[] packageInfo = line.split(":");

		int packageWeight = Integer.parseInt(packageInfo[0].trim());
		return ItemPackage.builder().packageWeight(packageWeight).items(createItems(packageInfo[1], packageWeight))
				.build();

	}

	/**
	 *  Transforms Raw Item in string format to Item. Uses Builder design pattern to create Items
	 * 
	 * @param rawItems
	 * @param packageWeight
	 * @return
	 */
	private List<Item> createItems(String rawItems, int packageWeight) {
		Matcher rawItemsMatcher = itemPattern.matcher(rawItems);

		List<Item> itemsList = new ArrayList<Item>();
		while (rawItemsMatcher.find()) {
			double itemWeight = Double.parseDouble(rawItemsMatcher.group("weight"));
			Item item = Item.builder().index(Integer.parseInt(rawItemsMatcher.group("index"))).weight(itemWeight)
					.cost(Integer.parseInt(rawItemsMatcher.group("cost"))).build();

			itemsList.add(item);
		}

		return itemsList;
	}

	
	/**
	 * Validation of package content and item content.
	 * Restriction
	 * 		1. Package overall weight should not be more than 100
	 * 		2. Package should not have more than 15 Items
	 * 		3. Each item weight in package should not be more than 100
	 * 		4. Each Item cost in package should not be more than 100
	 * 
	 * @param pack
	 */
	private void validatePackagesAndItems(ItemPackage pack) {

		if (pack.getPackageWeight() > MAX_ALLOWED_WEIGHT_OR_COST) {
			throw new APIException(" Maximun allowed package weight is : " + MAX_ALLOWED_WEIGHT_OR_COST);
		}

		if (pack.getItems().size() > MAX_ALLOWED_ITEMS) {
			throw new APIException(" Maximum allowed items per package is 15 ");
		}

		for (Item item : pack.getItems()) {
			if (item.getCost() > MAX_ALLOWED_WEIGHT_OR_COST) {
				throw new APIException(" Maximun allowed item cost is : " + MAX_ALLOWED_WEIGHT_OR_COST);
			}
			if (item.getWeight() > MAX_ALLOWED_WEIGHT_OR_COST) {
				throw new APIException(" Maximun allowed item weight is : " + MAX_ALLOWED_WEIGHT_OR_COST);
			}
		}
	}
}