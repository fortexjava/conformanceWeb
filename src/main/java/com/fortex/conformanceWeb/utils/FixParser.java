/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb.utils;

import java.util.Map;

/**
 * @author Administrator
 *
 */
public class FixParser {
	private static ParseXML parser;
	public static void init(String fileName) throws Exception {
		parser = new ParseXML(fileName);
	}
	
	/**
	public static void main(String args[]) throws Exception{
		FixParser.init("/spec/FIX44_GENERATE_FILE.xml");
		Map<Integer, Map<Integer, String>> textMap = parser.getMapping();		
	}
	**/
}
