/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey_generator;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Asif
 */
public class Survey_Generator {

    /**
     * @param args the command line arguments
     */
    public static ArrayList<ArrayList<String>> objectAttributeWithPossibleValues = new ArrayList<ArrayList<String>>();
    public static Map<String, Double> attributeValuesPosition = new HashMap<String, Double>();
    public static Map<String, Double> motivatingFactorsPosition = new HashMap<String, Double>();
    public static ArrayList<ArrayList<Double>> factorsAttributesRatingMatrix = new ArrayList<ArrayList<Double>>();
    public static Map<String, String> classVariable = new HashMap<>();
    public static ArrayList<User> usersList = new ArrayList<User>();
    public static Integer numberOfUsers = 6;
    
    public static void main(String[] args) {
//        OpeningForm opening = new OpeningForm();
//        opening.setVisible(true);
        try
        {
            fileOpenSpecification();
            fileOpenRating();
            createUsers();
            assignAttributes();
        } catch(Exception e) {
            
        }
    }
    
    public static void fileOpenSpecification() {
        int flag = 0;
        int i = -1;
        String key = null;
            
        try{
            Scanner in = new Scanner(new FileReader("specification.txt"));
            while (in.hasNext()) {
                String temp = in.next();
                if (temp.equalsIgnoreCase("type:")) {
                    flag = 0;                  
                    objectAttributeWithPossibleValues.add(new ArrayList<String>());
                    i++; 
                    continue;
                }
                if (temp.equalsIgnoreCase("value:")) {
                    flag = 1;
                    continue;
                }
                if (flag == 0) key = temp;
                if (flag == 1) classVariable.put(key, temp);
                flag = 2;
                objectAttributeWithPossibleValues.get(i).add(temp);
            }
        } catch(Exception e) {
            
        }
        System.out.println(classVariable);
        System.out.println(objectAttributeWithPossibleValues);
    }
    
    public static void fileOpenRating() {
        try {
            Scanner in = new Scanner(new FileReader("rating.txt"));
            int lineNo = 0;
            String temp = null;
            while (in.hasNext()) {
                temp = in.nextLine();
                  String[] elements = temp.split("\\s+");
                  if (lineNo == 0) {
                      
                      for (int j = 1; j < elements.length; j++) {
                        attributeValuesPosition.put(elements[j], (double)j-1);
                      }                  
                  }
                  else {
                      motivatingFactorsPosition.put(elements[0], (double)lineNo-1);
                      ArrayList<Double> tempArrayList = new ArrayList<>();
                      for (int j = 1; j < elements.length; j++) {
                          tempArrayList.add(Double.parseDouble(elements[j]));
                      }
                      factorsAttributesRatingMatrix.add(tempArrayList);
                  }              
                lineNo++;
            }
            System.out.println(attributeValuesPosition);
            System.out.println(motivatingFactorsPosition);
        }
        catch(Exception e) {
            
        }
    }
    
    public static void createUsers() {
        for (int i = 0; i < numberOfUsers; i++) {
            User user = new User(classVariable, motivatingFactorsPosition);
            usersList.add(user);
        }
        
//        for (int i = 0; i < usersList.size(); i++) {
//            System.out.println(usersList.get(i).attributes);
//            System.out.println(usersList.get(i).factors);
//        }
    }
    
    public static void assignAttributes() {
        for (int i = 0; i < objectAttributeWithPossibleValues.size(); i++) {
            
        }
    }
    
}
