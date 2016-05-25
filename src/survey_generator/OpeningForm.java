package survey_generator;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

public class OpeningForm extends javax.swing.JFrame {
    
    ArrayList<ArrayList<String>> specification = new ArrayList<ArrayList<String>>();
    Map<String, String> classVariable = new HashMap<>();
    ArrayList<ArrayList<Double>> attributeValuesRating = new ArrayList<ArrayList<Double>>();
    Map<String, Integer> attributeValues = new HashMap<String, Integer>();
    Map<String, Integer> points = new HashMap<String, Integer>();

    public OpeningForm() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setLabel("Load Specification File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Load Rating");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(241, Short.MAX_VALUE))
        );

        jButton1.getAccessibleContext().setAccessibleName("loadSpecficationFile");
        jButton2.getAccessibleContext().setAccessibleName("loadRating");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            int flag = 0;
            int i = -1;
            String key = null;
            Scanner in = new Scanner(new FileReader("specification.txt"));
            while (in.hasNext()) {
                String temp = in.next();
                if (temp.equalsIgnoreCase("type:")) {
                    flag = 0;
                    specification.add(new ArrayList<String>());
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
                specification.get(i).add(temp);
            }
            System.out.print(specification);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OpeningForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Scanner in = new Scanner(new FileReader("rating.txt"));
            int lineNo = 0;
            String temp = null;
            while (in.hasNext()) {
                temp = in.nextLine();
                  String[] elements = temp.split("\\s+");
                  if (lineNo == 0) {
                      
                      for (int j = 1; j < elements.length; j++) {
                        attributeValues.put(elements[j], j-1);
                      }                  
                  }
                  else {
                      points.put(elements[0], lineNo-1);
                      ArrayList<Double> tempArrayList = new ArrayList<>();
                      for (int j = 1; j < elements.length; j++) {
                          tempArrayList.add(Double.parseDouble(elements[j]));
                      }
                      attributeValuesRating.add(tempArrayList);
                  }              
                lineNo++;
            }
            System.out.println(attributeValues);
            System.out.println(points);
        }
        catch(Exception e) {
            
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OpeningForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpeningForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpeningForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpeningForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpeningForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
