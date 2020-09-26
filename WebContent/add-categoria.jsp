<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Suas Listas</title>

<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

</head>
<body>
<h1>Listas</h1>
	
	<form action="categorias?acao=salvar" method="post">
		
		<input type="text" id="nomeLista" name="nomeLista" placeholder="Nome da Lista"/> 
		<br>
		<input type="text" id="orcamento" name="orcamento" placeholder="Or�amento"/> 
		<br>
		<input type="submit" value="Salvar"/> 
		<button onclick="window.location.replace('categorias.jsp'); return false;">Cancelar</button>
	</form>

	<script src="resource/javascript/apenasNumeros.js"></script>
</body>
</html>