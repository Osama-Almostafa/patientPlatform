package CommonGatewayInterface;

import java.io.*;

public class converting {
    private converting() {
    }

    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("/Users/osama/IdeaProjects/patientPlatform/" +
                "src/main/java/HTML/index.html"));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
            // or
            //  sb.append(line).append(System.getProperty("line.separator"));
        }
        String nohtml = sb.toString().replaceAll("\\<.*?>", "");
        System.out.println(nohtml);
    }
}