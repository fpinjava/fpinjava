package com.fpinjava.makingjavafunctional.valuetypes;

import static com.fpinjava.common.CollectionUtilities.*;
import static com.fpinjava.makingjavafunctional.valuetypes.Price.*;
import static com.fpinjava.makingjavafunctional.valuetypes.Weight.*;

import java.util.List;

public class Store {

  public static void main(String[] args) {

    Product toothPaste = new Product("Tooth paste", price(1.5), weight(0.5));
    Product toothBrush = new Product("Tooth brush", price(3.5), weight(0.3));
    
    List<OrderLine> order = list(
        new OrderLine(toothPaste, 2), 
        new OrderLine(toothBrush, 3));
        
    Price price = foldLeft(order, Price.ZERO, Price.sum);
    Weight weight = foldLeft(order, Weight.ZERO, Weight.sum);
    
    System.out.println(String.format("Total price: %s", price));
    System.out.println(String.format("Total weight: %s", weight));

  }
}