package com.mobiquity.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.mobiquity.dto.Item;
import com.mobiquity.dto.ItemPackage;
import com.mobiquity.exception.APIException;

public class FileContentParserTest {

	@Test
	void testParsedPackageAndItems() {
		FileContentParser parser = new FileContentParser();

		String line = "81 : (1,53.38,€45) (2,80.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		ItemPackage pack = parser.parseLines(line);

		assertEquals(81, pack.getPackageWeight());

		assertEquals(6, pack.getItems().size());

		Item item = pack.getItems().get(0);
		assertEquals(45, item.getCost());
		assertEquals(53.38, item.getWeight());
		assertEquals(1, item.getIndex());

	}

	@Test
	void testPackageWeightMoreThan100() {
		FileContentParser parser = new FileContentParser();
		String line = "101 : (1,53.38,€45) (2,80.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		assertThrows(APIException.class, () -> parser.parseLines(line));
	}

	@Test
	void testItemWeightOrCostMoreThan100() {
		FileContentParser parser = new FileContentParser();
		String line = "80 : (1,53.38,€101) (2,200.00,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		assertThrows(APIException.class, () -> parser.parseLines(line));
	}

	@Test
	void testMaximumAllowedItemsException() {
		FileContentParser parser = new FileContentParser();
		String line = "80 : (1,53.38,€101) (2,20.0,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48) (1,53.38,€101) (2,20.0,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48) (1,53.38,€101) (2,20.0,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		assertThrows(APIException.class, () -> parser.parseLines(line));

	}
	
	@Test
	void testInValidParametersException() {
		FileContentParser parser = new FileContentParser();
		String line = "80:(1,53.38,€101)2,20.0,€98) ((3,78.48,€3)) ";
		assertThrows(APIException.class, () -> parser.parseLines(line));

	}
}
