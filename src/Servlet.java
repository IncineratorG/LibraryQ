import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexander on 12.08.2015.
 */
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        HttpSession hs = req.getSession();
        String[] output = null;

        String input = req.getParameter("input");
        if (input.matches("\\d")) {
            //String queryToDB = "select * from books where shelf = '" + input + "'";
            String queryToDB = "select * from books where shelf = '" + input + "'";
            output = DataBaseHandler.findInDataBase(queryToDB);
        }
        else {
            String queryToDB = "select * from books where bookauthor = '" + input + "'";
            output = DataBaseHandler.findInDataBase(queryToDB);
        }

        hs.setAttribute("output", output);
        resp.sendRedirect("/results.jsp");
    }

    public static void printResults(String[] output, PrintWriter pw) {
        String s1 = "<html>\n" +
                "<head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "  <script src=\"jquery.js\"></script>\n" +
                "  <script src=\"resultsJQuery.js\"></script>\n" + "<script src=\"popUpScript.js\"></script>" +
                "  <link href=\"resultsStyle.css\" rel=\"stylesheet\"/>\n" + "<link href=\"popUpStyle.css\" rel=\"stylesheet\"/>" +
                "  <link href=\"rectanglePositionsStyle.css\" rel=\"stylesheet\"/>\n" +
                "<style>#b {\n" +
                "    margin: 0; \n" +
                "    height: 100%; /* Высота страницы */\n" +
                "    background: url(8.jpg); /* Параметры фона */\n" +
                "    background-size: cover; /* Фон занимает всю доступную площадь */\n" +
                "    background-attachment: fixed;\n" +
                "}" +
                "</style></head>\n" +
                "<body id=\"b\">\n" +
                "\n" +
                "<div id=\"top\" >\n" +
                "  <form class=\"input\" name=\"queryInput\" action=\"/s\">\n" +
                "   <a class=\"btn\" href=\"index.jsp\">БQ</a><input type=\"text\" id=\"input\" class=\"shadow\" name=\"input\" autocomplete=\"off\" /><input type=\"submit\" class=\"button\" value=\"Найти\"/>\n" +
                "  </form>\n" +
                "\n" +
                "\n" +
                "  <div id=\"results\" >\n" +
                "  </div>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "<div id=\"output\">";

        String s2 = "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"messagepop pop\">\n" +
                "    <form method=\"post\" id=\"new_message\" action=\"/messages\">\n" +
                "        <p><label for=\"email\">Автор</label><input type=\"text\" size=\"50\" name=\"email\" id=\"email\" /></p>\n" +
                "        <p><label for=\"email\">Оглавление</label><input type=\"text\" size=\"50\" name=\"email\" id=\"email\" /></p>\n" +
                "        <p><label for=\"email\">Номер полки</label><input type=\"text\" size=\"50\" name=\"email\" id=\"email\" /></p>\n" +
                "        <p><input type=\"submit\" value=\"Добавить\" name=\"commit\" class=\"formAddBtn\" /> <a id=\"formCancelBtn\" class=\"close\" href=\"/\">Отмена</a></p>\n" +
                "    </form>\n" +
                "</div>\n" +
                "\n" +
                "<a href=\"/contact\" id=\"contact\">Добавить новую книгу</a>\n" +
                "\n" +
                "</body>\n" +
                "<div id=\"popUp\">\n" +
                "    <div id=\"bgImage\">\n" +
                "        <div id=\"rectangle\" class=\"rectangle\">\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>" +
                "\n" +
                "</html>";

        pw.println(s1);

        for (int i = 0; i < output.length;) {
            if (i == 0) {
                pw.print("<div id=\"book\">");
                pw.print("<a id=\"a\"><strong> Полка №: " + output[i] + "</strong></a>");
                pw.print("<div class=\"hide\" id=\"bookInner\">");
                pw.print("<a id=\"showPopUp\">Показать</a>");
                pw.print("<p ><strong>Автор:</strong> " + output[i+1] + "</p>");
                pw.print("<p ><strong>Оглавление:</strong> " + output[i+2] + "</p>");
                i = i + 3;
            }
            else if (output[i].equals(output[i-3])) {
                pw.print("<br>");
                pw.print("<p ><strong>Автор:</strong> " + output[i+1] + "</p>");
                pw.print("<p ><strong>Оглавление:</strong> " + output[i+2] + "</p>");
                i = i + 3;
            }
            else {
                pw.print("</div>");
                pw.print("</div>");
                pw.print("<div id=\"book\">");
                pw.print("<a id=\"a\" id=\"shelf\"><strong> Полка № " + output[i] + "</strong></a>");
                pw.print("<div class=\"hide\" id=\"bookInner\">>");
                pw.print("<a id=\"showPopUp\">Показать</a>");
                pw.print("<p ><strong>Автор:</strong> " + output[i+1] + "</p>");
                pw.print("<p ><strong>Оглавление:</strong> " + output[i+2] + "</p>");
                i = i + 3;
            }
        }
        pw.print("</div>");
        pw.print("</div>");

        pw.println(s2);
    }
}
