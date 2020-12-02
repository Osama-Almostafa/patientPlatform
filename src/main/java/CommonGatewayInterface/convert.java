package CommonGatewayInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class convert {

    private static BufferedReader in = null;
    private static String filename = "hallo";
    private static PrintWriter out = null;

    public static void main(String[] args) {

        //if (args.length > 0) filename = args[0];

        //else System.exit(0);

        try {
            in = new BufferedReader(new FileReader("/Users/osama/IdeaProjects/patientPlatform/" +
                    "src/main/java/HTML/index.html"));
            //in = new BufferedReader(new FileReader(filename));
            out = new PrintWriter(new FileWriter(filename + ".out"));
            String l = in.readLine();
            //giv os hele HTML filen
            while (l != null) {
                out.print("System.out.println(\"");
                for (int i = 0; i < l.length(); i++) {
                    char c = l.charAt(i);
                    if (c == '"') out.print("\\");
                    //hvis vores HTML laeste tegn i strengen er
                    out.print(c);
                }
                out.println("\");");
                l = in.readLine();
                System.out.println();
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
      