package com.mobiquity.packer;

import com.mobiquity.exception.APIException;
import com.mobiquity.service.PackingService;
import com.mobiquity.util.FileContentParser;
import com.mobiquity.util.PackageInfoReader;

/**
 * @author Kiran Kumar Samala
 * 
 * Packer is the entry class for calculating the packing information 
 */
public class Packer {
	
	
	/**
	 * 
	 * 
	 * Receives absolute path for of the file which contains package with item information. It calculate the optimal items which should go into each package
	 * 
	 * @param filePath
	 * @return
	 * @throws APIException
	 */
	public static String pack(String filePath) throws APIException {
		
		if(filePath == null) {
			throw new APIException("");
		}else {
			PackageInfoReader reader = new PackageInfoReader();
			FileContentParser parser = new FileContentParser();
			
			PackingService service = new PackingService(reader, parser);
			
			return service.getItemsPerPack(filePath);
		}
	}
}
