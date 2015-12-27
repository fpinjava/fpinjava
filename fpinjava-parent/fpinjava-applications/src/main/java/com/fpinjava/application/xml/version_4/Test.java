package com.fpinjava.application.xml.version_4;


import com.fpinjava.common.Executable;
import com.fpinjava.common.List;
import org.jdom2.Element;

public class Test {

  private final static String format = "First Name : %s\n" +
      "\tLast Name : %s\n" +
      "\tEmail : %s\n" +
      "\tSalary : %s";

  private final static List<String> elementNames = List.list("firstname", "lastname", "email", "salary");

  public static void main(String... args) {
    Executable program = ReadXmlFile.readXmlFile(Test::getXmlFilePath, Test::getRootElementName, Test::processElement, Test::processList);
    program.exec();
  }

  private static String processElement(Element element) {
    return String.format(format, elementNames.map(element::getChildText).toJavaList().toArray());
  }

  private static FilePath getXmlFilePath() {
    return FilePath.apply("file.xml"); // <- adjust path
  }

  private static ElementName getRootElementName() {
    return ElementName.apply("staff");
  }

  private static void processList(List<String> list) {
    list.forEach(System.out::println);
  }
}
