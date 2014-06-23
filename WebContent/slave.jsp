<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>
<%@ include file="setup.jsp" %>

<%
	String action = request.getParameter("action");
	if (action == null || action.equals(""))
		return;
	
	String path  = Config.getInstance().rootDir;
	
	if (action.equals("loadInfo")) {
		String filename = request.getParameter("filename");
		if (filename == null || filename.equals("")) {
			out.print("Filename is not defined");
			return;
		}
		
		Interactor i = new Interactor();
		Graph g      = i.loadGraph(path + Config.getInstance().dataPath + filename);
		
		if (g == null) {
			out.print("Cannot load graph");
			return;
		}
		
		out.print(g.print_graph());
		
		
	}
	
	
%>