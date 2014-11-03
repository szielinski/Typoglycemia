import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Typoglycemia {

   private static final String PUNCTUATION = "&@#*.'\"[](){}\\/!?:,;- \t\r\n"; // System.lineSeparator();
   private static final String CORRECT_USAGE = "Incorrect usage. Start the program from the command line using: \'java Typoglycemia inputFileName.txt outputFileName.txt\'";
   private static Random rand = null;

   public Typoglycemia(String inFile, String outFile) {
      rand = new Random(System.currentTimeMillis());
      ProcessFile(inFile, outFile);
   }

   private String randomPermute(char[] a, int n) {
      for (int i = 1; i < n; i++) {
         int j = rand.nextInt(n - i) + i;
         char temp = a[i];
         a[i] = a[j];
         a[j] = temp;
      }
      return new String(a);
   }

   private void ProcessFile(String inFilename, String outFilename) {
      Scanner in = null;
      PrintWriter out = null;
      try {
         in = new Scanner(new FileReader(inFilename));
         out = new PrintWriter(outFilename);
         while (in.hasNextLine()) {
            String line = in.nextLine();
            StringTokenizer st = new StringTokenizer(line, PUNCTUATION, true);
            while (st.hasMoreTokens()) {
               String s = st.nextToken();
               if (s.length() > 3){ // ignore words too short for randomisation
                  s = randomPermute(s.toCharArray(), s.length() - 1);
               }
               out.print(s);
            }
            if (in.hasNextLine()){ // restore the newline char eaten by the scanner
               out.println();
            }
         }
      } catch (FileNotFoundException e) {
         System.err.printf("File(s): %s and %s not found.\n", inFilename,
               outFilename);
      } finally {
         in.close();
         out.close();
      }
      System.out.println("Finished successfully.");
   }

   public static void main(String[] args) {
      if (args.length < 2) {
         System.out.println(CORRECT_USAGE);
         return;
      }
      new Typoglycemia(args[0], args[1]);
   }
}
