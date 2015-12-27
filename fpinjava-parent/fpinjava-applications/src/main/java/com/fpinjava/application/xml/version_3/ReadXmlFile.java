package com.fpinjava.application.xml.version_3;

import com.fpinjava.common.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadXmlFile {

  public static Executable readXmlFile(Supplier<Result<String>> sPath,
                                       Supplier<Result<String>> sRootName,
                                       Tuple<String, List<String>> format,
                                       Effect<List<String>> e) {
    final Result<String> path = sPath.get();
    final Result<String> rDoc = path.flatMap(ReadXmlFile::readFile2String);
    final Result<String> rRoot =sRootName.get();
    final Result<List<String>> result = rDoc.flatMap(doc -> rRoot
        .flatMap(rootElementName -> readDocument(rootElementName, doc))
        .map(list -> toStringList(list, format)));
    return () -> result.forEachOrException(e)
          .forEach(Throwable::printStackTrace);
  }

  public static Result<String> readFile2String(String path) {
    try {
      return Result.success(new String(Files.readAllBytes(Paths.get(path))));
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

    private static List<String> toStringList(List<Element> list, Tuple<String, List<String>> format) {
      return list.map(e -> processElement(e, format));
    }

    private static String processElement(Element element, Tuple<String, List<String>> format) {
      String formatString = format._1;
      List<String> parameters = format._2.map(element::getChildText);
      return String.format(formatString, parameters.toJavaList().toArray());
    }

}
