package CommonGatewayInterface;

import java.util.StringTokenizer;

public class CGIget {
    public CGIget(){}

    private static void showHead() {
        System.out.println("Content-Type: text/html");
        System.out.println();
        System.out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">");
        System.out.println("<HTML>");
        System.out.println("<HEAD>");
        System.out.println("<TITLE>The CGIget application</TITLE>");
        System.out.println("<META http-equiv=\"content-type\" content=\"text/html; charset=iso-8859-1\">");
        System.out.println("<META http-equiv=\"Pragma\" content=\"no-cache\">");
        System.out.println("<META http-equiv=\"expires\" content=\"0\">");
        System.out.println("</HEAD>");
        System.out.println("<BODY>");
    }

    private static void showTail() {
        System.out.println("</BODY>\n</HTML>");
    }

    private static void showBody(StringTokenizer t) {

        System.out.println("Transferred fields:");
        System.out.println("<TABLE BORDER=\"1\">");
        String felt;
        while (t.hasMoreTokens()) {
            felt = t.nextToken();
            if (felt != null) {
                System.out.print("<TR><TD>");
                StringTokenizer tt = new StringTokenizer(felt, "=\n\r");
                String s = tt.nextToken();
                if (s != null) {
                    System.out.print(s);
                    s = tt.nextToken();
                    if (s != null)
                        System.out.print("</TD><TD>" + s);
                }
            }
            System.out.println("</TD></TR>");
        }
        System.out.println("</TABLE>");
    }

    public static void main(String[] args) {
        showHead();
        for (String s : args) {
            System.out.println("<p>" + s + "</p> <br>");
        }
        if (args.length > 0 && args[0] != null && args[0].length() > 0) {

            showBody(new StringTokenizer(args[0], "&\n\r"));
        } else
            System.out.println("<P>No data!</P>");
        showTail();
    }

}
