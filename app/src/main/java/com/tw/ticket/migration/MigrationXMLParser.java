package com.tw.ticket.migration;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class MigrationXMLParser {
    private Document dom;
    private String[] migrationClasses;
    private Patch[] patches;

    public Patch[] loadAllPatchClasses() {
        parseXmlFile();
        parseDocument();
        patches = new Patch[migrationClasses.length];
        for (String className : migrationClasses) {
            Class c = null;
            try {
                c = Class.forName(className);
                Patch patch = (Patch) c.newInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return patches;
    }

    private void parseXmlFile() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse(new File("/app/resources/migrations.xml").getAbsolutePath());
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void parseDocument() {
        Element docEle = dom.getDocumentElement();
        NodeList nodeList = docEle.getElementsByTagName("patch");
        int nodeListLength = nodeList.getLength();
        if (nodeList != null && nodeListLength > 0) {
            migrationClasses = new String[nodeListLength];
            for (int i = 0; i < nodeListLength; i++) {
                Element element = (Element) nodeList.item(i);
                migrationClasses[i] = element.getNodeValue();
            }
        }
    }
}
