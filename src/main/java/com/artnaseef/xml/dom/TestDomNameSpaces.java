package com.artnaseef.xml.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by art on 12/15/16.
 */
public class TestDomNameSpaces {

  private DocumentBuilderFactory docBuilderFactory;

  public static void main(String[] args) {
    new TestDomNameSpaces().instanceMain(args);
  }

  public void instanceMain(String[] args) {
    try {
      docBuilderFactory = DocumentBuilderFactory.newInstance();

      if (args.length >= 1) {
        for (String fileName : args) {
          System.out.println(fileName.replaceAll(".", "#"));
          System.out.println(fileName);
          System.out.println(fileName.replaceAll(".", "#"));

          this.processFile(fileName);

          System.out.println();
        }
      } else {
        this.processInputStream(System.in, true);
      }
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  private void processFile(String fileName)
      throws IOException, ParserConfigurationException, SAXException {

    this.processInputStream(new FileInputStream(fileName), false);

    System.out.println();

    this.processInputStream(new FileInputStream(fileName), true);
  }

  private void processInputStream(InputStream inputStream, boolean withNamespaces)
      throws IOException, ParserConfigurationException, SAXException {

    if (withNamespaces) {
      System.out.println("WITH NAMESPACES");
      System.out.println("================");

      docBuilderFactory.setNamespaceAware(true);
    } else {
      System.out.println("WITHOUT NAMESPACES");
      System.out.println("===================");

      docBuilderFactory.setNamespaceAware(false);
    }


    DocumentBuilder documentBuilder = docBuilderFactory.newDocumentBuilder();
    Document doc = documentBuilder.parse(inputStream);
    this.dumpElements(doc.getDocumentElement(), "");
  }

  private void dumpElements(Element ele, String prefix) {
    NodeList children = ele.getChildNodes();
    int cur = 0;

    while (cur < children.getLength()) {
      System.out.println(prefix + "tag-name=" + ele.getTagName() +
                         ", ns-uri=" + ele.getNamespaceURI() +
                         ", local-name=" + ele.getLocalName() +
                         ", node-name=" + ele.getNodeName()
      );

      Node childNode = children.item(cur);

      if (childNode instanceof Element) {
        this.dumpElements((Element) childNode, prefix + "  ");
      }

      cur++;
    }
  }

}
