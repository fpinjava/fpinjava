package com.fpinjava.application.xml.version_1;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Try this with an empty file
 */
public class ReadXmlFile {

  /**
   * Not testable, throws exceptions.
   */
  public static void main(String[] args) {

    SAXBuilder builder = new SAXBuilder();
    File xmlFile = new File("/media/SANSTITRE/fpinjava/fpinjava-parent/fpinjava-common/src/main/java/com/fpinjava/xml2/version_0/empty.xml");

    try {

      Document document = (Document) builder.build(xmlFile);
      Element rootNode = document.getRootElement();
      List list = rootNode.getChildren("staff");

      for (int i = 0; i < list.size(); i++) {

        Element node = (Element) list.get(i);

        System.out.println("First Name : " + node.getChildText("firstname"));
        System.out.println("\tLast Name : " + node.getChildText("lastname"));
        System.out.println("\tNick Name : " + node.getChildText("nickname"));
        System.out.println("\tSalary : " + node.getChildText("salary"));
      }
    } catch (IOException io) {
      System.out.println(io.getMessage());
    } catch (JDOMException jdomex) {
      System.out.println(jdomex.getMessage());
    }
  }
}
