package com.mobiquity.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mobiquity.exception.APIException;

/**
 * @author Kiran Kumar Samala
 * 
 *   It reads the file information and converts to List of String
 */
public class PackageInfoReader {

	/**
	 * It reads the file information from the given absolute path. All the lines in the file is converted to List of String
	 * 
	 * @param filePath
	 * @return
	 * @throws APIException
	 */
	public List<String> readFile(String filePath) throws APIException {
		
		try (Stream<String> stream = Files.lines(Paths.get(filePath))) {

            return stream.collect(Collectors.toList());

        } catch (IOException e) {
           throw new APIException("Invalid file path : " + filePath);
        }
	}
}
