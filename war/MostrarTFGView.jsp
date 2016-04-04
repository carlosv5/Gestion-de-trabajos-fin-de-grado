<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/main.css"/>
<title>Gestión de TFGs, bienvenido <c:out value="${user}"/></title>
</head>
<body>
<p> Sistema de gestión de TFGs</p>
	<c:if test="${user != null }">
		<h3><c:out value="${user}"/></h3>
	</c:if>
<c:if test="${not empty user and not empty tfg }">	
<div id="tituloTFGs">TFG activo de ALUMNO <c:out value="${user}"/></div>
	<div>
		<tr>
			<td><div>Título: <c:out value="${tfg.titulo}"/></div></td>
			<td><div>Autor: <c:out value="${tfg.autor}"/></div></td>
			<td><div>Resumen: <c:out value="${tfg.resumen}"/></div></td>
			<td><div>Tutor: <c:out value="${tfg.tutor}"/></div></td>		
			<td><div>Estado: <c:out value="${tfg.estado}"/></div></td>	
		</tr>
	</div>
	<div>
</c:if>

<c:if test="${not empty user and empty tfg and empty tfgs}">
Alumno:esta es una solicitud de TFG
	<form action="/nuevoTFG" method="post" acceptcharset="utf-8">
		<input type="text" name="titulo" id="titulo" maxLength="255" size="20"
			required placeholder="Titulo del TFG"/>
		<input type="text" name="resumen" id="resumen" masLength="255" required
			size="20" placeholder="Resumen del TFG"/>
		<input type="text" name="tutor" id="tutor" maxLength="255" required size="20"
			placeholder="Tutor del TFG"/>
		<input type="submit" value="Solicitud de TFG"/>
	</form>
</c:if>
</div>
<div>
<c:if test="${not empty user and not empty tfg and tfg.estado == 2}">
Formulario subida de memoria. <c:out value="${tfg.fichero}"/>
	<form action="<%=blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
		<input id="autor" name="autor" type="hidden" value="${tfg.autor}"/>
		<input type="file" name="file"/>
		<input type="submit" value="Subir memoria"/>
	</form>
</c:if>
</div>
<div>
<c:if test="${not empty user and not empty tfg and tfg.estado lt 2}">
	No hay memoria subida todavía.
</c:if>
<c:if test="${not empty user and not empty tfg and tfg.estado == 1}">
	<form action="/borrarSolicitud" method="post">
		<input id="autor" name="autor" type="hidden" value="${tfg.autor}"/>
		<input type="submit" value="Retirar solicitud de TFG"/>
	</form>
</c:if>
<c:if test="${not empty user and not empty tfg and tfg.estado gt 2}">
	<form action="/mostrarMemoria" method="get">
		<input id="autor" name="autor" type="hidden" value="${tfg.autor}"/>
		<input type="submit" value="Ver memoria"/>
	</form>
</c:if>
</div>

<c:if test="${not empty user and not empty tfgs}">	
<div id="tituloTFGs"> TFGs activos de PROFESOR <c:out value="${user}"/></div>
	<table>
		<tr>
			<th>Autor</th>
			<th>Título</th>
			<th>Resumen</th>
			<th>Tutor</th>
			<th>Secretario</th>
			<th>Estado</th>
			<th>Fichero</th>

		</tr>
		<c:forEach items="${tfgs}" var="tfgi">
			<tr>
				<td><c:out value="${tfgi.autor}"/></td>
				<td><c:out value="${tfgi.titulo}"/></td>
				<td><c:out value="${tfgi.resumen}"/></td>
				<td><c:out value="${tfgi.tutor}"/></td>	
				<c:choose>
					<c:when test="${tfgi.estado == 3}">
						<form action="/aceptarSecretario" method="post" acceptcharset="utf-8">
							<input type="hidden" name="autor" value="${tfgi.autor}"/>
							<td><input type="text" name="secretario" id="secretario" maxLength="255"
							required size="20" placeholder="Sugerir secretario"/></td>
							<td><input type="submit" value="Sugerir secretario"/></td>
						</form>
					</c:when>
					<c:when test ="${tfgi.estado ==1}">
						<form action="/aceptarTutor" method="get" acceptcharset="utf-8">
							<input type="hidden" name="autor" value="${tfgi.autor}"/>
							<td><c:out value="${tfgi.secretario}"/></td>
							<td><input type="submit" value="Aceptar ser tutor"/></td>
						</form>
					</c:when>
					<c:otherwise>
						<td><c:out value="${tfgi.secretario}"/></td>
						<td><c:out value="${tfgi.estado}"/></td>
					</c:otherwise>
				</c:choose>	
			</tr>
		</c:forEach>
	</table>
</c:if>	
	



<p>Puedes pulsar el siguiente enlace para hacer
<a href="<c:url value="${url}"/>"><c:out value="${urlLinktext}"/></a></p>
</body>
</html>