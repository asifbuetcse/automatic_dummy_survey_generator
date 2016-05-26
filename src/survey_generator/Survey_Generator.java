/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey_generator;

import java.io.FileReader;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.pmstation.spss.DataConstants;
import com.pmstation.spss.MissingValue;
import com.pmstation.spss.SPSSWriter;
import com.pmstation.spss.ValueLabels;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    public static Integer numberOfUsers = 30;
    public static List myList = new ArrayList();
    public static List factorsList = new ArrayList();
    public static List attributeList = new ArrayList();

    public static void main(String[] args) {
        try {
            fileOpenSpecification();
            fileOpenRating();
            createUsers();
            createAttributeList();
            assignAttributes();
            assignValues();
            writeInFiles();
            exportToSPSS();
            exportToSPSSindividual();
        } catch (Exception e) {
        }
    }

    public static void fileOpenSpecification() {
        int flag = 0;
        int i = -1;
        String key = null;
        try {
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
                if (flag == 0) {
                    key = temp;
                }
                if (flag == 1) {
                    classVariable.put(key, temp);
                }
                flag = 2;
                objectAttributeWithPossibleValues.get(i).add(temp);
            }
        } catch (Exception e) {

        }
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
                        attributeValuesPosition.put(elements[j], (double) j - 1);
                    }
                } else {
                    factorsList.add(elements[0]);
                    motivatingFactorsPosition.put(elements[0], (double) lineNo - 1);
                    ArrayList<Double> tempArrayList = new ArrayList<>();
                    for (int j = 1; j < elements.length; j++) {
                        tempArrayList.add(Double.parseDouble(elements[j]));
                    }
                    factorsAttributesRatingMatrix.add(tempArrayList);
                }
                lineNo++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void createUsers() {
        for (int i = 0; i < numberOfUsers; i++) {
            usersList.add(new User(classVariable, motivatingFactorsPosition));
        }
    }

    public static void createAttributeList() {
        for (int i = 0; i < objectAttributeWithPossibleValues.size(); i++) {
            ArrayList<String> tempArrayList = objectAttributeWithPossibleValues.get(i);
            String key = tempArrayList.get(0);
            attributeList.add(key);
        }

    }

    public static void assignAttributes() {
        for (int i = 0; i < numberOfUsers; i++) {
            myList.add(i);
        }
        for (int i = 0; i < objectAttributeWithPossibleValues.size(); i++) {
            Collections.shuffle(myList);
            ArrayList<String> tempArrayList = objectAttributeWithPossibleValues.get(i);
            String key = tempArrayList.get(0);
            int k = 1;
            for (int j = 0; j < myList.size(); j++) {
                usersList.get((int) myList.get(j)).attributes.put(key, tempArrayList.get(k));
                if (key.equalsIgnoreCase("gender")) {
                    k++;
                } else {
                    k += (Math.random() * tempArrayList.size());
                }
                if (k >= tempArrayList.size()) {
                    k = 1;
                }
            }
        }
    }

    public static void assignValues() {
        for (int i = 0; i < usersList.size(); i++) {
            User user = usersList.get(i);
            double attributePosition = 0;
            for (int j = 0; j < factorsList.size(); j++) {
                double sum = 0;
                int denominator = 0;
                String thisFactor = (String) factorsList.get(j);
                double factorPosition = motivatingFactorsPosition.get(thisFactor);
                for (Object attributeList1 : attributeList) {
                    String thisAttribute = user.attributes.get((String) attributeList1);
                    if (attributeValuesPosition.containsKey(thisAttribute)) {
                        attributePosition = attributeValuesPosition.get(thisAttribute);
                    } else {
                        continue;
                    }
                    double value = factorsAttributesRatingMatrix.get((int) factorPosition).get((int) attributePosition);
                    value = randomize(value);
                    sum += value;
                    denominator++;
                }
                sum /= denominator;

                if (sum < 4) {
                    sum = 4;
                }
                if (sum == 10) {
                    sum = 9;
                }
                user.factors.put(thisFactor, sum);
            }
        }
    }

    public static double randomize(double input) {
        double[] anArray = new double[10];
        double output = 0;
        for (int i = 0; i < 10; i++) {
            anArray[i] = 1.0;
        }
        anArray[(int) input] += 10;

        if ((int) input + 1 < 10) {
            anArray[(int) input + 1] += 5;
        }
        if ((int) input + 2 < 10) {
            anArray[(int) input + 2] += 2;
        }
        if ((int) input - 1 < 10) {
            anArray[(int) input - 1] += 5;
        }
        if ((int) input - 2 < 10) {
            anArray[(int) input - 2] += 2;
        }
        double sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += anArray[i];
        }
        for (int i = 0; i < 10; i++) {
            anArray[i] /= sum;
        }
        double rand = Math.random();
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += anArray[i];
            if (sum > rand) {
                return (double) i;
            }
        }
        return output;
    }

    public static void writeInFiles() {
        try {
            PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
            for (int i = 0; i < usersList.size(); i++) {
                User user = usersList.get(i);
                writer.println("User no: " + (i + 1));
                writer.println();
                for (int j = 0; j < attributeList.size(); j++) {
                    writer.println(attributeList.get(j) + " : " + user.attributes.get(attributeList.get(j)));
                }
                writer.println();
                for (int j = 0; j < factorsList.size(); j++) {
                    double point = user.factors.get(factorsList.get(j));
                    int inPoint = (int) point;
                    if (inPoint > 10) {
                        inPoint = 10;
                    }
                    writer.println(factorsList.get(j) + " : " + inPoint);
                }
                writer.println();
                writer.println();
            }
            writer.close();
        } catch (Exception e) {

        }
    }

    public static void exportToSPSS() throws FileNotFoundException, IOException {
        try {
            OutputStream out = new FileOutputStream("outs.sav");
            SPSSWriter outSPSS = new SPSSWriter(out, "utf-8");
            outSPSS.setCalculateNumberOfCases(false);
            outSPSS.addDictionarySection(-1);
            for (int i = 0; i < attributeList.size(); i++) {
                outSPSS.addStringVar(attributeList.get(i).toString(), 255, attributeList.get(i).toString());
            }
            for (int i = 0; i < factorsList.size(); i++) {
                outSPSS.addNumericVar(factorsList.get(i).toString(), 8, 2, factorsList.get(i).toString());
            }
            outSPSS.addDataSection();
            for (int i = 0; i < usersList.size(); i++) {
                User user = usersList.get(i);
                for (int j = 0; j < attributeList.size(); j++) {
                    outSPSS.addData(user.attributes.get(attributeList.get(j)));
                }
                for (int j = 0; j < factorsList.size(); j++) {
                    double point = user.factors.get(factorsList.get(j));
                    int inPoint = (int) point;
                    if (inPoint > 10) {
                        inPoint = 10;
                    }
                    outSPSS.addData((double) inPoint);
                }
            }
            outSPSS.addFinishSection();
            out.close();
        } catch (FileNotFoundException exOb) {
            System.out.println("FileNotFoundException (Demo.main): "
                    + exOb.getMessage());
            exOb.printStackTrace(System.out);
            return;
        } catch (IOException exOb) {
            System.out.println("IOException (Demo.main): " + exOb.getMessage());
            exOb.printStackTrace(System.out);
            return;
        }
    }

    public static void exportToSPSSindividual() {
        for (int i = 0; i < objectAttributeWithPossibleValues.size(); i++) {
            ArrayList<String> tempArrayList = objectAttributeWithPossibleValues.get(i);
            String value = null;
            for (int j = 1; j < tempArrayList.size(); j++) {
                value = tempArrayList.get(j);
                System.out.println(value);
                try {
                    exportToSPSSindividualHelper(value);
                } catch (IOException ex) {
                    Logger.getLogger(Survey_Generator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static void exportToSPSSindividualHelper(String value) throws FileNotFoundException, IOException {
        try {
            OutputStream out = new FileOutputStream(value + ".sav");
            SPSSWriter outSPSS = new SPSSWriter(out, "utf-8");
            outSPSS.setCalculateNumberOfCases(false);
            outSPSS.addDictionarySection(-1);
            for (int i = 0; i < attributeList.size(); i++) {
                outSPSS.addStringVar(attributeList.get(i).toString(), 255, attributeList.get(i).toString());
            }
            for (int i = 0; i < factorsList.size(); i++) {
                outSPSS.addNumericVar(factorsList.get(i).toString(), 8, 2, factorsList.get(i).toString());
            }
            outSPSS.addDataSection();
            for (int i = 0; i < usersList.size(); i++) {
                User user = usersList.get(i);
                if (!user.attributes.containsValue(value)) {
                    continue;
                }
                for (int j = 0; j < attributeList.size(); j++) {
                    outSPSS.addData(user.attributes.get(attributeList.get(j)));
                }
                for (int j = 0; j < factorsList.size(); j++) {
                    double point = user.factors.get(factorsList.get(j));
                    int inPoint = (int) point;
                    if (inPoint > 10) {
                        inPoint = 10;
                    }
                    outSPSS.addData((double) inPoint);
                }
            }
            outSPSS.addFinishSection();
            out.close();
        } catch (FileNotFoundException exOb) {
            System.out.println("FileNotFoundException (Demo.main): "
                    + exOb.getMessage());
            exOb.printStackTrace(System.out);
            return;
        } catch (IOException exOb) {
            System.out.println("IOException (Demo.main): " + exOb.getMessage());
            exOb.printStackTrace(System.out);
            return;
        }
    }

}
