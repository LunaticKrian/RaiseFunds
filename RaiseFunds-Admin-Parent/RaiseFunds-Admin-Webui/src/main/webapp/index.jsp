<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>搭建环境，测试页面</title>
</head>
<%--引入JQuery--%>
<script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
<base href="http://${pageContext.request.serverName }:${pageContext.request.serverPort }${pageContext.request.contextPath }/"/>
<body>
<h1>测试</h1>
<a href="${pageContext.request.contextPath}/test/index.html">测试</a>

<br>

</body>
</html>