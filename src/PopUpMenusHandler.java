import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexander on 14.08.2015.
 */
public class PopUpMenusHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter pw = resp.getWriter();

        Integer n = 1;
        HttpSession hs = req.getSession(true);
        hs.setAttribute("s", n);

        String input = req.getParameter("inputfield");
        String[] content = null;

        content = DataBaseHandler.authors;

        if (input == "" || input == null) {}
        else {
            pw.println("<ul>");
            input = input.toLowerCase();
            String firstOption = "^" + input + ".*";
            String secondOption = ".*\\s" + input + ".*";

            int maxShownResults = 10;
            for (int i = 0; i < content.length; ++i) {
                if ((matches(firstOption, content[i].toLowerCase()) || matches(secondOption, content[i].toLowerCase())) && maxShownResults > 0) {
                    pw.println("<li class=\"dval1\">" + content[i] + "</li>");
                    --maxShownResults;
                }
            }
            pw.println("</ul>");
        }
    }

    public static boolean matches(String regex, String line) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(line);

        return m.matches();
    }
}
