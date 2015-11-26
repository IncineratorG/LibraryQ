<%@ page import="java.io.PrintWriter" %>
<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 05.08.2015
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <script src="/script/jquery.js"></script>
    <script src="/script/indexJQuery.js"></script>
    <script src="/script/jquery-ui.js"></script>

    <link href="/css/indexOverallStyle.css" rel="stylesheet"/>
    <link href="/css/indexDivTop.css" rel="stylesheet"/>
    <link href="/css/indexDivMessagePop.css" rel="stylesheet"/>

</head>
<body>

<!---------indexDivTop.css-------------------------------------->
<div id="top" >
    <form class="input" name="queryInput" action="/s">
        <a  class="btn" href="index.jsp">БQ</a><input type="text" id="input" class="shadow" name="input" autocomplete="off" autofocus/><input type="submit" class="btn1" value="Найти"/>
    </form>

    <div id="results" >
    </div>
</div>
<!--------------------------------------------------------------->

<!----------indexDivMessagePop.css------------------------------->
<div class="messagepop pop">
    <form method="GET" id="new_message" action="/AddRemoveModify">
        <p><label for="bookauthor">Автор</label><input type="text" size="50" autocomplete="on" name="bookauthor" id="bookauthor" /></p>
        <p><label for="bookname">Оглавление</label><input type="text" size="50" autocomplete="on" name="bookname" id="bookname" /></p>
        <p><label for="shelf">Номер полки</label><input type="text" size="50" autocomplete="on" name="shelf" id="shelf" /></p>
        <p><input type="submit" value="Добавить" name="commit" class="formAddBtn" /> <a id="formCancelBtn" class="close" href="/">Отмена</a></p>
    </form>
</div>
<a href="/contact" id="contact">Добавить новую книгу</a>
<!---------------------------------------------------------------->

</body>
</html>