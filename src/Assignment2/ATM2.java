/*Mohammed Safwat--October/29/2019--ICS340 Algorithms and Data Structures
 * Program implements a cash counting mechanism listing a set of bill Denominations.Using a graphical interface,
 * to ask user to upload a .txt file that has a set of numbers or change( in Bills: example 5 10 20).
 *  The program reads file containing the "Bills" and outputs numbers of ways listed  and time span the program
 *   calculated the results IN Nanoseconds. Finally, Program outputs two files for the two different forms (Recursive, Dynamic)
 *  which a user can observe the difference in time to dispense of bills in both forms. 
 *  
*/
package Assignment2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
		
public class ATM2 {
			
		    public static void main(String[] args) {
		        // 1. The program shall graphically prompt the user for a file.

		        // Create JFrame for Gui Elements
		        JFrame gui = new JFrame("Upload File");

		        // Set width and length and visibility of Gui JFrame
		        gui.setSize(300, 200);
		        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		        // Upload button to open upload dialog.
		        JButton uploadButton = new JButton("Upload File");

		        uploadButton.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                JFileChooser fileChooser = new JFileChooser();
		                int returnValue = fileChooser.showOpenDialog(null);
		                if (returnValue == JFileChooser.APPROVE_OPTION) {
		                    File selectedFile = fileChooser.getSelectedFile();

		                    // Read integers to list
		                    int[] denominationBills = null;
		                    ArrayList<Integer> changeValues = new ArrayList<>();
		                    Scanner scanner = null;
		                    // 2. Read the selected file which will contain 1 integer per line.
		                    try {
		                        scanner = new Scanner(selectedFile);
		                        String text = null;
		                        // Reading each line of file using Scanner class
		                        int lineNumber = 1;
		                        String[] tempSpaceSeperatedBills = null;

		                        while (scanner.hasNextLine()) {
		                            String line = scanner.nextLine();
		                            System.out.println("line " + lineNumber + " :" + line);
		                            if (lineNumber == 1) {
		                                // 2. The program shall read the selected file of which the first line will be a space separated list of the bill denomination sizes.
		                            	// 5 10 50 100
		                                tempSpaceSeperatedBills = line.replaceAll("\\s+$", "").split(" ");
		                            } else {
		                                //  3. The program shall then read each line in the rest of the file containing an integer
		                                // and output the number of different ways to produce that value using the denominations listed in the first line.
		                                changeValues.add(Integer.parseInt(line));
		                            }
		                            lineNumber++;
		                        }

		                        denominationBills = new int[tempSpaceSeperatedBills.length];
		                        for (int i = 0; i < tempSpaceSeperatedBills.length; i++) {
		                            denominationBills[i] = Integer.parseInt(tempSpaceSeperatedBills[i]);
		                        }
		                    } catch (FileNotFoundException ex) {
		                        // TODO Auto-generated catch block
		                        ex.printStackTrace();
		                    } finally {
		                        if (scanner != null) {
		                            scanner.close();
		                        }
		                    }
		                    // 5. The program shall implement 2 different forms of the algorithm: 1 recursive and 1 using dynamic programming.
		                    // 7. The program shall write the output to a file in the same directory as the input file.
		                    String fileDirectory = selectedFile.getParent();
		                    // Recurcive
		                    ArrayList<String> numberOfWays = new ArrayList<String>();
		                  
		                    for (Integer change : changeValues) {
		                    	long startTime = System.nanoTime();
		                    	String chgStr =  change + " - ";
		                    	chgStr = chgStr + String.valueOf(recursiveGetNumberOfWaysToDeriveChangeFromDenominations(denominationBills, denominationBills.length - 1, change)) + " - ";
			                    long endTime = System.nanoTime();
			                    long timeElapsed = endTime - startTime;
			                    chgStr = chgStr + String.valueOf(timeElapsed)+ "ns";
			                    numberOfWays.add(chgStr);
			                    System.out.println(timeElapsed);
		                    }

		                    // 4. The program shall indicate after that the number the number of milliseconds the program spent calculating the answer.
		     
		                    String fileName =  "output_recursive.txt";
		       
		     
		                    writeFile(numberOfWays, fileName, fileDirectory);
		                   
		                    //Dyanamic
		                    numberOfWays = new ArrayList<String>();
		                    for (Integer change : changeValues) {
		   	                    long startTime = System.nanoTime();
		                    	String chgStr =  change + " - ";
		                    	chgStr = chgStr + String.valueOf(dynamicGetNumberOfWaysToDeriveChangeFromDenominations(denominationBills, change)) + " - ";
			                    long endTime = System.nanoTime();
			                    long timeElapsed = endTime - startTime;
			                    chgStr = chgStr + String.valueOf(timeElapsed)+ "ns";
			                    numberOfWays.add(chgStr);
			                    System.out.println(timeElapsed);
		                    }
		                    fileName =  "output_dynamic.txt";
		                   
		                   
		                    writeFile(numberOfWays, fileName, fileDirectory);
		                }
		            }

		            private void writeFile(ArrayList<String> numberOfWays, String fileName, String fileDirectory) {
		                File file = new File(fileDirectory + "/ " + fileName);
		                FileWriter fw = null;
		                BufferedWriter bw = null;
		                try {
		                    if (!file.exists()) {
		                        file.createNewFile();
		                    }
		                    fw = new FileWriter(file);
		                    bw = new BufferedWriter(fw);
		                    for (int i = 0; i < numberOfWays.size(); i++) {
		                        // 5. write 1 integer per line in the output file.
		                        bw.write(numberOfWays.get(i));
		                        bw.newLine();
		                    }
		                    bw.flush();
		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                } finally {
		                    try {
		                        if (bw != null) {
		                            bw.close();
		                        }
		                        if (fw != null) {
		                            fw.close();
		                        }
		                    } catch (IOException ex) {
		                        ex.printStackTrace();
		                    }
		                }
		            }
		        });
		        // create a panel to add the button
		        JPanel panel = new JPanel();

		        // add upload button to the panel
		        panel.add(uploadButton);

		        // add panel to gui
		        gui.add(panel);

		        // Show gui to user
		        gui.setVisible(true);
		    }

		    // find the total number of ways to get change from unlimited supply of denominations in set currencyDenominationValues
		    private static int recursiveGetNumberOfWaysToDeriveChangeFromDenominations(int[] currencyDenominationValues,
		            int numberOfDenominations,
		            int change) {
		        // if change is 0, return 1
		        if (change == 0) {
		            return 1;
		        }

		        // change 0 if total become negative or no denominations left
		        if (change < 0 || numberOfDenominations < 0 ) {
		            return 0;
		        }

		        // Case 1. include currencyDenominationValues[n] in solution and recur
		        // with remaining change (change - currencyDenominationValues[numberOfDenominations]) with same number of denominations
		        int incl = recursiveGetNumberOfWaysToDeriveChangeFromDenominations(currencyDenominationValues, numberOfDenominations, change - currencyDenominationValues[numberOfDenominations]);

		        // Case 2. exclude current coin currencyDenominationValues[n] from solution and recur
		        // for remaining numberOfDenominations (numberOfDenominations - 1)
		        int excl = recursiveGetNumberOfWaysToDeriveChangeFromDenominations(currencyDenominationValues, numberOfDenominations - 1, change);

		        // return total ways by including or excluding current denomination
		        return incl + excl;
		    }

		    private static int dynamicGetNumberOfWaysToDeriveChangeFromDenominations(int[] currencyDenominationValues, int change) {
		        // Create the ways array to 1 plus the amount to stop overflow
		        int[] ways = new int[change + 1];

		        // Set the first way to 1 because its 0 and there is 1 way to make 0 with 0
		        // coins
		        ways[0] = 1;

		        // Go through all of the coins
		        for (int i = 0; i < currencyDenominationValues.length; i++) {

		            // Make a comparison to each index value
		            // of ways with the coin value.
		            for (int j = 0; j < ways.length; j++) {
		                if (currencyDenominationValues[i] <= j) {

		                    // Update the ways array
		                    ways[j] += ways[(int) (j - currencyDenominationValues[i])];
		                }
		            }
		        }

		        // return the value at the Nth position
		        // of the ways array.
		        return ways[change];
		    }

		}

		

