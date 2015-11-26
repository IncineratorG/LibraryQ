import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Alexander on 24.08.2015.
 */
public class AddRemoveModify extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter pw = resp.getWriter();

        String action = req.getParameter("action");
        if (action == null)
            action = "null";

        switch (action) {
            case "null": addNewBook(req, resp);
                break;

            case "delete": deleteBook(req, resp);
                break;

            case "modify": modifyBook(req, resp);
                break;
        }
    }

    public static void addNewBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookauthor = req.getParameter("bookauthor");
        String bookname = req.getParameter("bookname");
        String shelf = req.getParameter("shelf");

        DataBaseHandler.addNewBook(bookauthor, bookname, shelf, null);
        RequestDispatcher rq = req.getRequestDispatcher("index.jsp");
        rq.forward(req, resp);
    }

    public static void deleteBook(HttpServletRequest req, HttpServletResponse resp) {
        String id_b = req.getParameter("id");
        String isDeleted = req.getParameter("isDeleted");
        String query = null;

        //System.out.println(id_b);
        //System.out.println(isDeleted);

        switch (isDeleted) {
            case "0": query = "update books set deleted = " + "1" + " where id_b = " + id_b;
                break;

            case "1": query = "update books set deleted = " + "0" + " where id_b = " + id_b;
                break;
        }

        DataBaseHandler.executeQuery(query);
    }

    public static void modifyBook(HttpServletRequest req, HttpServletResponse resp) {
        String newShelf = req.getParameter("newShelf");
        String id_b = req.getParameter("id");

        System.out.println(id_b);
        System.out.println(newShelf);

        String query = "update books set shelf = " + newShelf + " where id_b = " + id_b;

        DataBaseHandler.executeQuery(query);
    }
}
