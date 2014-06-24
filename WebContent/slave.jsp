<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>
<%@ include file="setup.jsp" %>

<%
	String action = request.getParameter("action");
	if (action == null || action.equals(""))
		return;
	
	String path = Config.getInstance().rootDir;
	
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
		return;
	}
	
	if (action.equals("generate")) {
        String n_   = request.getParameter("nvalue");
        String p_   = request.getParameter("pvalue");
        String name = request.getParameter("name");
        
        if (n_ == null || p_ == null || name == null) {
            response.sendRedirect(Config.getInstance().rootUrl + "/generate/?fail=1");
            return;
        }
        
        int n    = Integer.parseInt(n_);
        double p = Double.parseDouble(p_);
        
        if (n <= 0 || p <= 0) {
            response.sendRedirect(Config.getInstance().rootUrl + "/generate/?fail=2");
            return;
        }
        
        Interactor i = new Interactor();
        if (i.checkFileList(path, name)) {
            response.sendRedirect(Config.getInstance().rootUrl + "/generate/?fail=3");
            return;
        }
        
        Pair pair = new Pair(n, p);
        
        // START
        Generator generator = new Generator();
        
        generator.name = name;
        generator.pair = pair;
        generator.path = path;
        
        (new Thread(generator)).start();
        
        response.sendRedirect(Config.getInstance().rootUrl + "/generate/?success=1");
        return;
    }
	
	
%>