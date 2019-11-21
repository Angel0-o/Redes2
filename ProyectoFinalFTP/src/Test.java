
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Angel
 */
public class Test {
    public static void main(String args[]) { 
  
      // Creating an empty LinkedList 
      LinkedList<String> list = new LinkedList<String>(); 
  
      // Use add() method to add elements in the list 
      list.add("Geeks"); 
      list.add("for"); 
      list.add("Geeks"); 
      list.add("10"); 
      list.add("20"); 
  
      // Displaying the list 
      System.out.println("LinkedList:" + list); 
        
      // The first position of an element  
      // is returned 
      System.out.println("The first occurrence of Geeks is at index:"  + list.indexOf("Geeks")); 
      System.out.println("The first occurrence of Geeks is at index:"  + list.indexOf("Geeks")); 
      System.out.println("The first occurrence of 10 is at index: "   + list.indexOf("10")); 
  
   } 
}
