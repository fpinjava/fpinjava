package com.fpinjava.application.xml.version_2;

import com.fpinjava.common.List;
import com.fpinjava.common.Result;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadXmlFile {

  private final static String format = "First Name : %s\n" +
      "\tLast Name : %s\n" +
      "\tNick Name : %s\n" +
      "\tSalary : %s";

  public static void main(String[] args) {
    final Result<String> path = getXmlFilePath();
    final Result<String> rDoc = path.flatMap(ReadXmlFile::readFile2String);
    final Result<String> rRoot = getRootElementName();
    final Result<List<String>> result = rDoc.flatMap(doc -> rRoot
        .flatMap(rootElementName -> readDocument(rootElementName, doc))
        .map(list -> toStringList(list, format)));
    result.forEachOrException(ReadXmlFile::processList)
          .forEach(Throwable::printStackTrace);
  }

  private static Result<String> getXmlFilePath() {
    return Result.of("/media/SANSTITRE/fpinjava/fpinjava-parent/fpinjava-common/src/main/java/com/fpinjava/xml/file.xml");
  }

  private static Result<String> getRootElementName() {
    return Result.of("staff"); // Simulating a computation that may fail.
  }

  public static Result<String> readFile2String(String path) {
    try {
      return Result.success(new String(Files.readAllBytes(Paths.get(path)))); // <- throws SecurityException
    } catch (IOException e) {
      return Result.failure(String.format("IO error while reading file %s", path), e);
    } catch (Exception e) {
      return Result.failure(String.format("Unexpected error while reading file %s", path), e);
    }
  }

  private static Result<List<Element>> readDocument(String rootElementName, String stringDoc) {
    final SAXBuilder builder = new SAXBuilder();
    try {
      final Document document = builder.build(new StringReader(stringDoc));
      final Element rootElement = document.getRootElement();
      return Result.success(List.fromCollection(rootElement.getChildren(rootElementName)));
    } catch (IOException | JDOMException io) {
      return Result.failure(String.format("Invalid root element name '%s' or XML data %s", rootElementName, stringDoc), io);
    } catch (Exception e) {
      return Result.failure(String.format("Unexpected error while reading XML data %s", stringDoc), e);
    }
  }

  private static List<String> toStringList(List<Element> list, String format) {
    return list.map(e -> processElement(e, format));
  }

  private static String processElement(Element element, String format) {
    return String.format(format, element.getChildText("firstname"),
        element.getChildText("lastname"),
        element.getChildText("nickname"),
        element.getChildText("salary"));
  }

  private static <T> void processList(List<T> list) {
    list.forEach(System.out::println);
  }
}
