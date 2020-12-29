package com.mobiquity.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mobiquity.exception.APIException;

public class PackageInfoReaderTest {

	@Test
	void testFileRead() {
		
		List<String> packageInfo = new ArrayList<>();
		packageInfo.add("81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
		packageInfo.add("8 : (1,15.3,€34)");
		packageInfo.add(
				"75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)");
		packageInfo.add(
				"56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)");
		
		PackageInfoReader reader = new PackageInfoReader();
		try {
			List<String> packageInfoFromFile = reader.readFile(getClass().getClassLoader().getResource("packageInfo.txt").toURI().getPath());
			
			assertEquals(packageInfo.get(0), packageInfoFromFile.get(0));
			assertEquals(packageInfo.get(1), packageInfoFromFile.get(1));
			assertEquals(packageInfo.get(2), packageInfoFromFile.get(2));
			assertEquals(packageInfo.get(3), packageInfoFromFile.get(3));

		} catch (APIException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testInvalidFilePath() {
		PackageInfoReader reader = new PackageInfoReader();
		assertThrows(APIException.class,() -> reader.readFile("/user/invalidfile.txt"));
	}
	
}
