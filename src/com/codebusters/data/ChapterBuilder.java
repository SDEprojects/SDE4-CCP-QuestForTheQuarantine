package com.codebusters.data;

import com.codebusters.game.*;
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
    private final HashMap<String, ArrayList<HashMap<String, String>>> story;
    private final ArrayList<Chapter> chapters = new ArrayList<>();

    public ChapterBuilder () {
        story = readXMLFile("./quarantine_first_edition.xml");
        buildChapters();
    }

    public ChapterBuilder(String storyXMLFile) {
        story = readXMLFile(storyXMLFile);
        buildChapters();
    }

    private HashMap<String, ArrayList<HashMap<String, String>>> readXMLFile(String file){
        HashMap<String, ArrayList<HashMap<String, String>>> story = new HashMap<>();
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

    private HashMap<String, ArrayList<HashMap<String, String>>> buildStoryMap(Document storyDoc) {
        HashMap<String, ArrayList<HashMap<String, String>>> story = new HashMap<>();
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

    private void addTableToStory(Node table, HashMap<String, ArrayList<HashMap<String, String>>> story) {
        String expression = "Table/Row";
        String tableName = table.getAttributes().getNamedItem("ss:Name").getNodeValue();
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

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

            for(int i = 1; i < rows.getLength(); i++) {
                HashMap<String, String> row = new HashMap<>();
                NodeList cells = (NodeList) xpath.evaluate("Cell", rows.item(i),  XPathConstants.NODESET);
                // add each cell in the row
                for(int j = 0; j < cells.getLength(); j++) {
                    row.put(columnKeys.get(j),cells.item(j).getTextContent());
                }
                list.add(row);
            }
        } catch (javax.xml.xpath.XPathExpressionException e) {
            System.out.println(e);
        }
        story.put(tableName, list);
    }

    private void buildChapters() {

       ArrayList<HashMap<String, String>> chs = story.get("Chapters");
       for(HashMap<String, String> entry : chs) {
           Chapter newChapter = new Chapter();
           newChapter.setChapterId(entry.get("chapterId"));
           newChapter.setChapterName(entry.get("chapterName"));
           newChapter.setSceneText(entry.get("sceneText"));
           addPaths(newChapter);
           chapters.add(newChapter);
       }
    }

    private void addPaths(Chapter ch) {
        ArrayList<HashMap<String, String>> paths = story.get("Paths");
        for(HashMap<String, String> path : paths) {
            if(path.get("chapterId") == null) continue;
            if(path.get("chapterId").equals(ch.getChapterId())) {
                parseItems(path);
                ch.setPaths(path);
            }
        }
    }

    private void parseItems(HashMap<String, String> path) {
        String gainItems = path.get("gainItems");
        String loseItems = path.get("loseItems");
        String requiredItems = path.get("requiredItems");
        path.put("gainItems", gainItems);
        path.put("loseItems", loseItems);
        path.put("requiredItems", requiredItems);
    }
    public HashMap<String, ArrayList<HashMap<String, String>>> getStory() {
        return story;
    }

    public ArrayList<Chapter> getChapters() {
        return chapters;
    }
}
