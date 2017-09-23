<%--
  Created by IntelliJ IDEA.
  User: elessar
  Date: 12.09.17 г.
  Time: 0:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  $END$
  <script>
    var sock = new WebSocket("ws://localhost:8080/alice");
    sock.onopen = function (onOpenEvent) {
        alert(onOpenEvent.type);
        sock.send("Hello,Server. I am a client!");
    };
    sock.onmessage = function (onMessageEvent) {
        alert(onMessageEvent.data);
    };
  </script>
  </body>
</html>
