<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

  <script src="/script/jquery.js"></script>
  <script src="/script/resultsJQuery.js"></script>
  <script src="/script/popUpScript.js"></script>
  <script src="/script/editPopUpScript.js"></script>
  <script src="/script/jquery-ui.js"></script>

  <link href="/css/resultsStyle.css" rel="stylesheet">
  <link href="/css/popUpStyle.css" rel="stylesheet">
  <link href="/css/rectanglePositionsStyle.css" rel="stylesheet">
  <link href="/css/editPopUpStyle.css" rel="stylesheet">
  <style>
    #b {
    margin: 0;
    height: 100%; /* Высота страницы */
    background: url(8.jpg); /* Параметры фона */
    background-size: cover; /* Фон занимает всю доступную площадь */
    background-attachment: fixed;
  }
  </style>
</head>
<body id="b">

<div id="top">
  <form class="input" name="queryInput" action="/s">
    <a class="btn" href="index.jsp">БQ</a><input id="input" class="shadow" name="input" autocomplete="off" type="text"><input class="button" value="Найти" type="submit">
  </form>


  <div id="results">
  </div>
</div>

<!------------------ Вывод результатов ------------------->
  <%
    PrintWriter pw = response.getWriter();
    String[] output = (String[]) session.getAttribute("output");

    if (output != null) {
  %>
<div id="output">

  <%for (int i = 0; i < output.length;) {%>
  <% if (i == 0) { %>
    <div id="authorOnShelf">
      <a id="a" class="a" id="shelf"><strong>Полка № <%=output[i]%></strong></a>
      <div class="hide" id="bookInner">
        <a id="showPopUp">Показать</a>
        <div id="book" <%if (output[i+3].equals("1")) {%> class="notInLibrary"<%}%>>

          <p id="author"><strong>Автор:</strong> <%=output[i+1]%></p>
          <p id="bookname"><strong>Оглавление:</strong> <%=output[i+2]%></p>
          <p id="isDeleted" class="hidden"><%=output[i+3]%></p>
          <p id="id_b" class="hidden"><%=output[i+4]%></p>
        </div>

  <% i = i + 5; }
  else if (output[i].equals(output[i-5])) {%>

        <br>
        <div id="book" <%if (output[i+3].equals("1")) {%> class="notInLibrary"<%}%>>
          <p id="author"><strong>Автор:</strong> <%=output[i+1]%></p>
          <p id="bookname"><strong>Оглавление:</strong> <%=output[i+2]%></p>
          <p id="isDeleted" class="hidden"><%=output[i+3]%></p>
          <p id="id_b" class="hidden"><%=output[i+4]%></p>
        </div>

<% i = i + 5; } else { %>
        </div>
      </div>
      <div id="authorOnShelf">
        <a id="a" class="a" id="shelf"><strong>Полка № <%=output[i]%></strong></a>
        <div class="hide" id="bookInner">
           <a id="showPopUp">Показать</a>
          <div id="book" <%if (output[i+3].equals("1")) {%> class="notInLibrary"<%}%>>
            <p id="author"><strong>Автор:</strong> <%=output[i+1]%></p>
            <p id="bookname"><strong>Оглавление:</strong> <%=output[i+2]%></p>
            <p id="isDeleted" class="hidden"><%=output[i+3]%></p>
            <p id="id_b" class="hidden"><%=output[i+4]%></p>
           </div>
<% i = i + 5; } %>
<%}%>
        </div>
      </div>

</div>
<%}%>
<!----------------------------------------------------------->

<div class="messagepop pop">
  <form method="GET" id="new_message" action="/AddRemoveModify">
    <p><label for="bookauthor">Автор</label><input type="text" size="50" autocomplete="on" name="bookauthor" id="bookauthor" /></p>
    <p><label for="bookname">Оглавление</label><input type="text" size="50" autocomplete="on" name="bookname" id="bookname" /></p>
    <p><label for="shelf">Номер полки</label><input type="text" size="50" autocomplete="on" name="shelf" id="shelf" /></p>
    <p><input type="submit" value="Добавить" name="commit" class="formAddBtn" /> <a id="formCancelBtn" class="close" href="/">Отмена</a></p>
  </form>
</div>
<a href="/contact" id="contact">Добавить новую книгу</a>




<div style="display: none;" id="popUp">
  <div id="bgImage">
    <div style="display: block;" id="rectangle">
    </div>
  </div>
</div>




<div id="edit">
  <form method="GET" id="new_message" action="/AddRemoveModify">
    <p><label for="bookauthor"><strong>Автор</strong></label><input type="text" size="50" autocomplete="on" name="bookauthor" id="bookauthor" readonly/></p>
    <p><label for="bookname"><strong>Оглавление</strong></label><input type="text" size="50" autocomplete="on" name="bookname" id="bookname" readonly/></p>
    <div id="idDiv"><p><label for="shelf">ID</label><input type="text" size="50" autocomplete="on" name="id_b" id="id" readonly/></p></div>
    <div id="isDeletedDiv"><p><label for="shelf">Deleted</label><input type="text" size="50" autocomplete="on" name="isDeleted" id="isDeleted" readonly/></p></div>
    <div id="shelfDiv"><p><label for="shelf">Номер полки</label><input type="text" size="50" autocomplete="on" name="shelf" id="shelf" /></p></div>

    <div id="newShelfDiv"><p><label for="shelf"><strong>Номер новой полки</strong></label><input type="text" size="25" autocomplete="on" name="shelf" id="newShelf" /> <a id="enter" class="myButtonDelete">Ok</a> </p></div>

    <a id="delete" class="myButtonDelete">Взять</a> <a id="modify" class="myButtonDelete">Переместить</a> <a id="editCancelBtn" class="myButtonCancel">Отмена</a>
  </form>
</div>

</body>

</html>
