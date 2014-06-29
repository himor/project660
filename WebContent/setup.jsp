<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>

<%
	Config config_ = Config.getInstance();
	String root    = config_.rootUrl; 
	String url     = request.getRequestURI();
	int tableSize  = 1;
	config_.setRootDir("C:\\temp");
%>
