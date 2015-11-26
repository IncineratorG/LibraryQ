import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alexander on 05.08.2015.
 */
public class QueryExecutor extends HttpServlet {

    public static final String URL = "jdbc:oracle:thin:@//localhost:1521/xe";
    public static final String username = "Big";
    public static final String password = "Big";

   @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        registerDatabaseDriver();       // Регистрируем драйвер

        String[] dataBaseContent = readFromDatabase();      // Массив записей из базы данных

        String inputString = req.getParameter("input");     // Получаем введённую строку
        String fromJavascript = req.getParameter("fromJavascript");

        if (inputString == null) {

        }
        else {
            String[] matches = searchInDataBaseContent(inputString, dataBaseContent);    // Ищем введённую строку в базе данных

            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter pw = resp.getWriter();

            printResultsInWebPage(matches, pw);
        }

    }

    public static String[] readFromDatabase() {
        String queryToDataBase = "select filmname from films";
        List<String> stringList = new ArrayList<>();

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(queryToDataBase);

            int i = 0;
            while (resultSet.next()) {
                stringList.add(resultSet.getString("filmname"));
            }

        } catch (SQLException e) {
            System.err.println("Error getting results from DataBase");
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing the connection");
                }
            }
        }

        String[] dataBaseContent = new String[stringList.size()];
        for (int i = 0; i < dataBaseContent.length; ++i)
            dataBaseContent[i] = stringList.get(i);

        return dataBaseContent;
    }

    public static String[] searchInDataBaseContent(String inputString, String[] dataBaseContent) {
        String firstOption = "^" + inputString + ".*";
        String secondOption = ".*\\s" + inputString + ".*";
        List<String> matchingStringsList = new ArrayList<>();

        for (int i = 0; i < dataBaseContent.length; ++i) {
            if (matches(firstOption.toLowerCase(), dataBaseContent[i].toLowerCase()) || matches(secondOption.toLowerCase(), dataBaseContent[i].toLowerCase())) {
                matchingStringsList.add(dataBaseContent[i]);
            }
        }

        String[] matchingStrings = new String[matchingStringsList.size()];
        for (int i = 0; i < matchingStrings.length; ++i)
            matchingStrings[i] = matchingStringsList.get(i);

        return matchingStrings;
    }

    public static void printResultsInWebPage(String[] matches, PrintWriter pw) {
        if (matches.length == 0)
            pw.println("No match");
        else {

            for (int i = 0; i < matches.length; ++i) {
                pw.println("<tr><td>" + matches[i] + "</td></tr>");
            }
        }
    }

    public static boolean matches(String inputString, String stringInDataBase) {
        Pattern p = Pattern.compile(inputString);
        Matcher m = p.matcher(stringInDataBase);

        return m.matches();
    }










    public static String[] readFile() {
        String string;
        List<String> strings = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\DB.txt"), "UTF-8"));

            while ((string = reader.readLine()) != null) {
                strings.add(" " + string.toLowerCase());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] results = new String[strings.size()];
        for (int i = 0; i < results.length; ++i)
            results[i] = strings.get(i);

        return results;
    }

    private void executeSelectQuery(String[] requests, PrintWriter pw) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, username, password);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(requests[0]);

            boolean requestSuccessfull = false;
            if (resultSet.next()) {
                requestSuccessfull = true;
                createResultingPage(resultSet, pw, requestSuccessfull);
            }
            else {
                for (int i = 1; i < requests.length; ++i) {
                    resultSet = statement.executeQuery(requests[i]);

                    if (resultSet.next()) {
                        requestSuccessfull = true;
                        createResultingPage(resultSet, pw, requestSuccessfull);
                        break;
                    }
                }
            }

            if (!requestSuccessfull) {
                createResultingPage(resultSet, pw, requestSuccessfull);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//--------------------------- Создание страницы с результатами ------------------------------------------------------
    public static void createResultingPage(ResultSet resultSet, PrintWriter pw, boolean requestSuccessfull) {

        String htmlHeadMetaAndBodyOpenings = "<html>\n" + "<head>\n" + "<meta charset=\"utf-8\">\n" + "</head>\n" + "<body>\n";
        String htmlBodyContentWithTable = "<table border=\"1\">\n" + "<caption></caption>\n" + "<tr>\n" + "<th>Название</th>\n" + "<th>Расположение</th>\n" +
                "<th>Жанр</th>\n" + "<th>Длительность</th>\n" + "</tr>\n";
        String htmlTableClose = "</table>\n";
        String htmlBodyClose = "</body>\n" + "</html>";


        pw.println(htmlHeadMetaAndBodyOpenings);        // Создание заголовка страницы

        if (!requestSuccessfull) {
            pw.println("<p>Фильм не найден</p>");             // Если в базе ничкго не найдено
        }
        else {
            StringBuilder sb = new StringBuilder();
            try {
                sb.append(resultSet.getString("filmname") + "\n" + "columnSeparatorLine");     // Warning! Использование неименованных констант
                sb.append(resultSet.getString("lcation") + "\n" + "columnSeparatorLine");      // Warning! Использование неименованных констант
                sb.append(resultSet.getString("genre") + "\n" + "columnSeparatorLine");        // Warning! Использование неименованных констант
                sb.append(resultSet.getString("dration") + "\n" + "lineSeparatorLine");        // Warning! Использование неименованных констант

                while (resultSet.next()) {
                    sb.append(resultSet.getString("filmname") + "\n" + "columnSeparatorLine");     // Warning! Использование неименованных констант
                    sb.append(resultSet.getString("lcation") + "\n" + "columnSeparatorLine");      // Warning! Использование неименованных констант
                    sb.append(resultSet.getString("genre") + "\n" + "columnSeparatorLine");        // Warning! Использование неименованных констант
                    sb.append(resultSet.getString("dration") + "\n" + "lineSeparatorLine");        // Warning! Использование неименованных констант
                }

                pw.println(htmlBodyContentWithTable);

                String string = sb.toString();
                String[] strings = string.split("lineSeparatorLine");

                Arrays.sort(strings);

                sb = new StringBuilder();

                for (int i = 0; i < strings.length; ++i) {
                    sb.append(strings[i]);
                }

                string = sb.toString();
                strings = string.split("columnSeparatorLine");

                sb = new StringBuilder();

                for (int i = 0; i < strings.length; ++i) {
                    sb.append(strings[i]);
                }

                string = sb.toString();
                strings = string.split("\n");

                //++++++++++++++ Удаляем 'null' из вывода ++++++++++++++++++++++
                for (int i = 0; i < strings.length; ++i) {
                    if (strings[i].equals("null")) {
                        strings[i] = "Нет данных";
                    }
                }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                for (int i = 0; i < strings.length; ) {
                    pw.println("<tr><td>" + strings[i] + "</td><td>" + strings[i + 1]
                            + "</td><td>" + strings[i + 2] + "</td><td>" + strings[i + 3] + "</td></tr>\n");
                    i = i + 4;
                }

                pw.println(htmlTableClose);             // Закрываем таблицу


            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        pw.println(htmlBodyClose);          // Закрываем HTML документ
    }
//----------------------------------------------------------------------------------------------------------


//--------------------------- Создание запросов к базе данных -----------------------------------------------
    public static String[] RequestBuilder(String[] FilmNames) {
        String[] requests = new String[FilmNames.length];

        for (int i = 0; i < FilmNames.length; ++i) {
            requests[i] = "select * from films where upper(filmname) like upper('" + FilmNames[i] + "')";
        }

        return requests;
    }
//------------------------------------------------------------------------------------------------------------



//--------------------------------- Обработка введённой строки ------------------------------------------
    public static String[] SimpleWordHandler(String FilmName) {
        String[] str = genNewWords(FilmName);

        return str;
    }

    public static String[] genNewWords(String word) {
        List<String> newWords = new ArrayList<>();
        newWords.add(word);
        char[] string = word.toCharArray();

        String str = replaceCharInString(string, 'ё');
        newWords.add(str);
        string = str.toCharArray();

        str = replaceCharInString(string, 'о');
        newWords.add(str);
        string = str.toCharArray();

        str = replaceCharInString(string, 'а');
        newWords.add(str);
        string = str.toCharArray();

        str = replaceCharInString(string, 'н');
        newWords.add(str);
        string = str.toCharArray();

        str = replaceCharInString(string, 'с');
        newWords.add(str);
        string = str.toCharArray();

        str = replaceCharInString(string, 'е');
        newWords.add(str);
        string = str.toCharArray();

        str = replaceCharInString(string, 'ф');
        newWords.add(str);
        string = str.toCharArray();

        str = replaceCharInString(string, 'и');
        newWords.add(str);
        string = str.toCharArray();

        String[] finalArr = new String[newWords.size()];

        for (int i = 0; i < finalArr.length; ++i)
            finalArr[i] = newWords.get(i);

        return finalArr;
    }

    public static String replaceCharInString(char[] string, char charToReplace) {
        String finalString = "";

        for (int i = 1; i < string.length - 1; ++i) {
            if (string[i] == charToReplace) {
                string[i] = '%';
            }
        }

        for (char ch : string) {
            finalString = finalString + ch;
        }

        return finalString;
    }
//----------------------------------------------------------------------------------------------


//------------------ Регистрация драйвера для базы данных -------------------------------------
    public static void registerDatabaseDriver() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        Locale locale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.err.println("Cannot register a driver");
        }
    }
//---------------------------------------------------------------------------------------------
}
