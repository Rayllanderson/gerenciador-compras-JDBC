<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Suas Listas</title>
</head>
<body>
<a href="categorias.jsp">Voltar</a>
<h1>produtos</h1>
	<c:forEach items="${produtos}" var="prod">
	<table>
	<tr>
		<td>
			<p>${prod.nome}</p>
		</td>
	</tr>
	</table>
	</c:forEach>
	
	<script src="resource/javascript/esconderUrl.js"></script>
</body>
</html>