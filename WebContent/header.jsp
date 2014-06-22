<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>

<%
	String root = Config.rootUrl; 
	String url  = request.getRequestURI();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet' type='text/css'>
<link href='<%= root %>/css/main.css' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="<%= root %>/js/main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Generation and Properties of Large Directed Erd�s-Renyi Model Random Graphs</title>
</head>
<body>

<div class="header">
    <h2>Generation and Properties of Large Directed Erd�s-Renyi Model Random Graphs</h2>
</div>

<div class="menu">
    <ul>
	    <li><a <% out.print(url.contains("generate") ? "class='active'" : "");%> href="<%= root %>/generate/">Generate</a></li>
	    <li><a <% out.print(url.contains("analyze") ? "class='active'" : "");%> href="<%= root %>/analyze/">Analyze</a></li>
    </ul>
</div>