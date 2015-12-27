package com.fpinjava.application.xml.version_5;


import com.fpinjava.common.Executable;
import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import org.jdom2.Element;

public class Test {

  private final static String format = "First Name : %s\n" +
      "\tLast Name : %s\n" +
      "\tEmail : %s\n" +
      "\tSalary : %s";

  private final static List<String> elementNames = List.list("firstname", "lastname", "email"); // <- "salary" is missing

  public static void main(String... args) {
    Executable program = ReadXmlFile.readXmlFile(Test::getXmlFilePath, Test::getRootElementName, Test::processElement, Test::processList);
    try {
      program.exec();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static Result<String> processElement(Element element) {
    try {
      return Result.of(String.format(format, elementNames.map(name -> getChildText(element, name)).toJavaList().toArray()));
    } catch (Exception e) {
      return Result.failure("Exception while formatting element. Probable cause is a missing element name in element list " + elementNames);
    }
  }

  private static String getChildText(Element element, String name) {
    String string = element.getChildText(name);
    return string != null ? string :  "Element " + name + " not found";
  }

  private static FilePath getXmlFilePath() {
    return FilePath.apply("file.xml"); // <- adjust path
  }

  private static ElementName getRootElementName() {
    return ElementName.apply("staff");
  }

  private static <T> void processList(List<T> list) {
    list.forEach(System.out::println);
  }
}
