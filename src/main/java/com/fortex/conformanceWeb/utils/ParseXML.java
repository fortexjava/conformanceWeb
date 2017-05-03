package com.fortex.conformanceWeb.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

 
public class ParseXML {
	private Map<String, Map<Integer,FieldValidateModel>> modelsMap = new HashMap<String, Map<Integer,FieldValidateModel>>();
	private Map<Integer, Map<Integer, String>> mapping = new HashMap<Integer, Map<Integer, String>>();
	public ParseXML (String fileName) 
			throws ParserConfigurationException, SAXException, IOException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		ParseHandler handler = new ParseHandler();
		parser.parse(ParseXML.class.getResourceAsStream(fileName), handler);
		//return handler.modelsMap;
	}
	
	private class ParseHandler extends DefaultHandler {
		//Map<String, Map<Integer,FieldValidateModel>> modelsMap;
		//Map<String, Map<Integer, String>> mapping;
		Map<Integer, String> text;
		private Map<Integer, FieldValidateModel> fields;
		private Map<Integer, FieldValidateModel> groups;
		private FieldValidateModel field;
		
		@Override
		public void startDocument() throws SAXException {
			modelsMap = new TreeMap<String, Map<Integer,FieldValidateModel>>();
			mapping = new HashMap<Integer, Map<Integer, String>>();
		}
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			switch(qName) {
			case "messages":
				return;
			case "message":
				fields = new TreeMap<Integer, FieldValidateModel>();
				modelsMap.put(attributes.getValue("number"), fields);
				return;
			case "field":
				int number = Integer.parseInt(attributes.getValue("number"));
				FieldValidateModel model = new FieldValidateModel(number, 
						"Y".equals(attributes.getValue("required"))); 
				String defaultVal = attributes.getValue("defaultVal");
				if(defaultVal != null) {
					model.setDefaultVal(defaultVal);
				}
				if(groups == null){
					fields.put(number, model);
				}else {
					groups.put(number, model);
				}
				field = model;
				return;
			case "group":
				groups = new TreeMap<Integer, FieldValidateModel>();
				field.setGroups(groups);
				return;
			case "mapping":
				text = new HashMap<Integer, String>();
				mapping.put(Integer.parseInt(attributes.getValue("field")), text);
				return;
			case "text":
				text.put(Integer.parseInt(attributes.getValue("number")), attributes.getValue("value"));
				return;
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if("group".equals(qName)) {
				groups = null;
			}
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			super.characters(ch, start, length);
		}
	}
	
	public Map<String, Map<Integer,FieldValidateModel>> getModelsMap() {
		return this.modelsMap;
	}
	
	public Map<Integer, Map<Integer, String>> getMapping() {
		return this.mapping;
	}
}
