package com.mobiquity.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mobiquity.util.FileContentParser;
import com.mobiquity.util.PackageInfoReader;

@RunWith(JUnitPlatform.class)
@ExtendWith(MockitoExtension.class)
class PackingServiceTest {

	@Mock
	PackageInfoReader reader;
	FileContentParser parser = new FileContentParser();

	private final String testData1 = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
	private final String testData2 = "8 : (1,15.3,€34)";
	private final String testData3 = "75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)";
	private final String testData4 = "56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)";

	private final String testResult1 = "4";
	private final String testResult2 = "-";
	private final String testResult3 = "2,7";
	private final String testResult4 = "8,9";

	@Test
	void testGetItemsPerPack() {

		Mockito.when(reader.readFile("")).thenReturn(Arrays.asList(testData1));
		
		PackingService service = new PackingService(reader, parser);

		String result = service.getItemsPerPack("");
		assertEquals(testResult1, result);
	}

	@Test
	void testGetItemsPerPackItemWeightExceedPackageWeight() {

		Mockito.when(reader.readFile("")).thenReturn(Arrays.asList(testData2));
		
		PackingService service = new PackingService(reader, parser);

		String result = service.getItemsPerPack("");
		assertEquals(testResult2, result);
	}
	
	@Test
	void testGetItemsPerPackItemSelectLargerCostLesserWeight() {

		Mockito.when(reader.readFile("")).thenReturn(Arrays.asList(testData3));
		
		PackingService service = new PackingService(reader, parser);

		String result = service.getItemsPerPack("");
		assertEquals(testResult3, result);
	}
	
	@Test
	void testGetItemsPerPackMultiplPackages() {
				
		Mockito.when(reader.readFile("")).thenReturn(Arrays.asList(testData1, testData2, testData3, testData4));
		
		PackingService service = new PackingService(reader, parser);

		String result = service.getItemsPerPack("");
		
		String[] testResult = {testResult1, testResult2, testResult3, testResult4};
		assertEquals(String.join("\n",  testResult), result);
	}
	
}
