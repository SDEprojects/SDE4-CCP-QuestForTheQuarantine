package com.codebusters.data;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class ChapterBuilder {
    private HashMap<String, ArrayList> story = new HashMap<>();

    ChapterBuilder () {
        story = readXMLFile("./quarantine_first_edition.xml");
    }

    ChapterBuilder(String storyXMLFile) {
        readXMLFile(storyXMLFile);
    }

    private HashMap<String, ArrayList> readXMLFile(String file){
        HashMap<String, ArrayList> story = new HashMap<>();
        try {
            File xmlFile = new File(file);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document storyDoc = dBuilder.parse(xmlFile);
            storyDoc.getDocumentElement().normalize();
            story = buildStoryMap(storyDoc);

        } catch (javax.xml.parsers.ParserConfigurationException parseE){
            System.out.println(parseE);
        } catch (java.io.IOException ioE){
            System.out.println(ioE);
        } catch (org.xml.sax.SAXException saxException){
            System.out.println(saxException);
        } catch (Exception e) {
            System.out.println(e);
        }
        return story;
    }

    private HashMap<String, ArrayList> buildStoryMap(Document storyDoc) {
        HashMap<String, ArrayList> story = new HashMap<>();
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = "/Workbook/Worksheet";
        try {
            NodeList worksheet = (NodeList) xpath.evaluate(expression, storyDoc, XPathConstants.NODESET);
            for(int i = 0; i < worksheet.getLength(); i++) {
                addTableToStory(worksheet.item(i), story);
            }
        } catch ( javax.xml.xpath.XPathExpressionException e) {
            System.out.println(e);
        }

        return story;
    }

    private HashMap<String, ArrayList> addTableToStory(Node table, HashMap<String, ArrayList> story) {
        String expression = "Table/Row";
        String tableName = table.getAttributes().getNamedItem("ss:Name").getNodeValue();
        ArrayList list = new ArrayList();

        XPath xpath = XPathFactory.newInstance().newXPath();
        try {
            NodeList rows = (NodeList) xpath.evaluate(expression, table, XPathConstants.NODESET);
            NodeList keys = (NodeList) xpath.evaluate("Cell", rows.item(0), XPathConstants.NODESET);
            // add column keys to a List
            ArrayList<String> columnKeys = new ArrayList<>();
            for(int i = 0; i < keys.getLength(); i++){
                String columnName = keys.item(i).getTextContent();
                columnKeys.add(columnName);
            }
            // add remaining rows

            for(Integer i = 1; i < rows.getLength(); i++) {
                HashMap<String, String> row = new HashMap<>();
                NodeList cells = (NodeList) xpath.evaluate("Cell", rows.item(i),  XPathConstants.NODESET);
                // add each cell in the row
                for(Integer j = 1; j < cells.getLength(); j++) {
                    row.put(columnKeys.get(j),cells.item(j).getTextContent());
                }
                list.add(row);
            }
        } catch (javax.xml.xpath.XPathExpressionException e) {
            System.out.println(e);
        }
        story.put(tableName, list);
        return story;
    }

    public HashMap<String, ArrayList> getStory() {
        return story;
    }
}
