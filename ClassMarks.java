import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
/**
* Program that generates a mark table,
* given a list of students and a list of assignments.
*
* @author  Atri Sarker
* @version 1.0
* @since   2025-10-26
*/
public final class ClassMarks {
  /**
   * Variable for Max Mark.
   */
  public static final int MAX_MARK = 100;
  /**
   * Variable for Min Mark.
   */
  public static final int MIN_MARK = 0;
  /**
   * Variable for Median Mark.
   */
  public static final int MEDIAN_MARK = 75;
  /**
   * Variable for Standard Deviation in the marks.
   */
  public static final int STD_DEV_MARK = 10;
  /**
   * Private constructor to satisfy style checker.
   * @exception IllegalStateException for the utility class.
   * @see IllegalStateException
   */
  private ClassMarks() {
    // Prevents illegal states.
    throw new IllegalStateException("Utility class.");
  }

  /**
   * Function that generates a 2D array of students and
   * their marks for each assignment.
   * It will come with a header row for assignments and
   * a header column for student names.
   * @param studentsArr array of student names.
   * @param assignmentsArr array of assignment names.
   * @return 2D String[][] array of students and their marks.
   */
  public static String[][] generateMarks(final String[] studentsArr,
      final String[] assignmentsArr) {
    // Create a 2D array with an extra row and column for headers
    String[][] table =
    new String[studentsArr.length + 1][assignmentsArr.length + 1];
    // Create the header row
    String[] headerRow = new String[assignmentsArr.length + 1];
    headerRow[0] = "Students";
    // Loop through assignments to fill in the header row
    for (int assignmentNum = 0;
     assignmentNum < assignmentsArr.length; assignmentNum++) {
      headerRow[assignmentNum + 1] = assignmentsArr[assignmentNum];
    }
    // Set the header row in the marks table
    table[0] = headerRow;
    // Initialize Random object
    Random random = new Random();
    // Loop through row numbers
    for (int rowNum = 1; rowNum <= studentsArr.length; rowNum++) {
      // Set the student name in the first column
      table[rowNum][0] = studentsArr[rowNum - 1];
      // Create variable for row
      String[] row = new String[assignmentsArr.length + 1];
      // First column is the student name
      row[0] = studentsArr[rowNum - 1];
      // Loop through column numbers
      for (int colNum = 1; colNum <= assignmentsArr.length; colNum++) {
        // Generate random mark using Gaussian
        int randNum = (int)
        (MEDIAN_MARK + STD_DEV_MARK * random.nextGaussian());
        // Ensure the mark is within bounds
        if (randNum > MAX_MARK) {
          randNum = MAX_MARK;
        } else if (randNum < MIN_MARK) {
          randNum = MIN_MARK;
        }
        // Set the mark in the row
        row[colNum] = Integer.toString(randNum);
      }
      // Set the row in the marks table
      table[rowNum] = row;
    }
    // Return the table
    return table;
  }

  /**
   * Entrypoint of the program.
   * @param args UNUSED.
   */
  public static void main(final String[] args) {
    // First argument is the path to the students file.
    final String studentsFilePath = args[0];
    // Second argument is the path to the assignments file.
    final String assignmentsFilePath = args[1];
    // Third argument is the path to the output csv file.
    final String outputFilePath = args[2];
    // Print arguments
    System.out.println("Students file: " + studentsFilePath);
    System.out.println("Assignments file: " + assignmentsFilePath);
    System.out.println("Output file: " + outputFilePath);
    // Read data from both of the files and save them into arrays.
    try {
        // Access the students file and create a File object.
        File studentsFile = new File(studentsFilePath);
        // Scanner that will read the File Object.
        Scanner studentsFileReader = new Scanner(studentsFile);
        // Create list to store all the student names
        ArrayList<String> listOfStudents = new ArrayList<>();
        // Loop through all available lines
        while (studentsFileReader.hasNextLine()) {
          // Add the line to the list
          listOfStudents.add(studentsFileReader.nextLine());
        }
        // Access the assignments file and create a File object.
        File assignmentsFile = new File(assignmentsFilePath);
        // Scanner that will read the File Object.
        Scanner assignmentsFileReader = new Scanner(assignmentsFile);
        // Create list to store all the assignment names
        ArrayList<String> listOfAssignments = new ArrayList<>();
        // Loop through all available lines
        while (assignmentsFileReader.hasNextLine()) {
          // Add the line to the list
          listOfAssignments.add(assignmentsFileReader.nextLine());
        }
        // Close the File readers
        studentsFileReader.close();
        assignmentsFileReader.close();

        // Convert lists to arrays
        // Passing new String[0] to toArray()
        // because toArray() automatically makes a bigger one.
        String[] studentsArr = listOfStudents.toArray(new String[0]);
        String[] assignmentsArr = listOfAssignments.toArray(new String[0]);

        // Generate marks table
        String[][] marksTable = generateMarks(studentsArr, assignmentsArr);

        // Upload table to CSV file
        try {
          // Create a File object for the output file
          File outputFile = new File(outputFilePath);
          // Create a FileWriter object to write to the file
          java.io.FileWriter writer = new java.io.FileWriter(outputFile);
          // Loop through every row of the marks table
          for (String[] row : marksTable) {
            // Join the row elements with commas
            String rowString = String.join(",", row);
            // Write the row to the file
            writer.write(rowString + "\n");
          }
          // Close the writer
          writer.close();
          } catch (IOException error) {
          System.out.println(error);
        }
      } catch (IOException error) {
        System.out.println(error);
      }
      // Completion message
      System.out.println("DONE!");
  }
}
