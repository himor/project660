<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>
<%@ include file="setup.jsp" %>

<%
	String action = request.getParameter("action");
	if (action == null || action.equals(""))
		return;
	
	String path = Config.getInstance().rootDir;
	
	/**
	 * Load info
	 */
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
	
    /**
     * Generate
     */
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
        Pair pair           = new Pair(n, p);
        Generator generator = new Generator();
        
        generator.name = name;
        generator.pair = pair;
        generator.path = path;
        (new Thread(generator)).start();
        response.sendRedirect(Config.getInstance().rootUrl + "/generate/?success=1");
        return;
    }
    
    /**
     * Build
     */
     
     if (action.equals("build")) {
         String n_   = request.getParameter("nvalue");
         String name = request.getParameter("name");
         if (n_ == null || name == null) {
             response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=1");
             return;
         }
         int n = Integer.parseInt(n_);
         if (n <= 0) {
             response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=2");
             return;
         }
         Interactor i = new Interactor();
         if (i.checkFileList(path, name)) {
             response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=3");
             return;
         }
         Pair pair           = new Pair(n, 0);
         Generator generator = new Generator();
         
         generator.name = name;
         generator.pair = pair;
         generator.path = path;
         (new Thread(generator)).start();
         response.sendRedirect(Config.getInstance().rootUrl + "/builder/?success=1");
         return;
     }
    
     /**
      * insertEdge
      */
      if (action.equals("insertEdge")) {
          String from_ = request.getParameter("from");
          String to_   = request.getParameter("to");
          String name  = request.getParameter("name");
          if (from_ == null || to_ == null || name == null) {
              response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=1&graph=" + name);
              return;
          }
          int from = Integer.parseInt(from_);
          int to   = Integer.parseInt(to_);
          if (from <= 0 || to <= 0) {
              response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=2&graph=" + name);
              return;
          }
          Interactor i = new Interactor();
          Generator g  = new Generator();
          
          g.name = name;
          g.path = path;
          
          String filename = config_.rootDir + config_.dataPath + name;
          Graph graph_ = i.loadGraph(filename);
          if (graph_ == null) {
              response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=4&graph=" + name);
          }
          g.lockGraph(graph_);
          graph_.insert_edge(from, to, g.DIRECTED);
          g.saveGraph(graph_);
          
          response.sendRedirect(Config.getInstance().rootUrl + "/builder/?graph=" + name);
          return;
      }
     
      /**
       * insertNode
       */
       if (action.equals("insertNode")) {
           String name = request.getParameter("name");
           String n_   = request.getParameter("nvalue");
           if (name == null || n_ == null) {
               response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=1&graph=" + name);
               return;
           }
           
           int n = Integer.parseInt(n_);
           n     = n > 1 ? n : 1; 

           Interactor i = new Interactor();
           Generator g  = new Generator();
           
           g.name = name;
           g.path = path;
           
           String filename = config_.rootDir + config_.dataPath + name;
           Graph graph_ = i.loadGraph(filename);
           if (graph_ == null) {
               response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=4&graph=" + name);
           }
           g.lockGraph(graph_);
           graph_.addVertex(n);
           g.saveGraph(graph_);
           
           response.sendRedirect(Config.getInstance().rootUrl + "/builder/?graph=" + name);
           return;
       }
      
       /**
        * deleteGraph
        */
        if (action.equals("deleteGraph")) {
            String name  = request.getParameter("name");
            if (name == null) {
                response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=1&graph=" + name);
                return;
            }
            Interactor i = new Interactor();
            FileInfo fi  = new FileInfo();
            fi.setFilename(name);
            i.removeFromFileList(path, fi);
            
            response.sendRedirect(Config.getInstance().rootUrl + "/builder/");
            return;
        }
       
        /**
         * removeNode
         */
         if (action.equals("removeNode")) {
             String name = request.getParameter("name");
             String n_   = request.getParameter("nvalue");
             if (name == null || n_ == null) {
                 response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=1&graph=" + name);
                 return;
             }
             int n = Integer.parseInt(n_);
             if (n <= 0) {
                 response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=2&graph=" + name);
                 return;
             }
             
             Interactor i = new Interactor();
             Generator g  = new Generator();
             
             g.name = name;
             g.path = path;
             
             String filename = config_.rootDir + config_.dataPath + name;
             Graph graph_ = i.loadGraph(filename);
             if (graph_ == null) {
                 response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=4&graph=" + name);
             }
             g.lockGraph(graph_);
             graph_.removeVertex(n);
             g.saveGraph(graph_);
             
             response.sendRedirect(Config.getInstance().rootUrl + "/builder/?graph=" + name);
             return;
         }
        
         /**
          * removeEdge
          */
          if (action.equals("removeEdge")) {
              String from_ = request.getParameter("from");
              String to_   = request.getParameter("to");
              String name  = request.getParameter("name");
              if (from_ == null || to_ == null || name == null) {
                  response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=1&graph=" + name);
                  return;
              }
              int from = Integer.parseInt(from_);
              int to   = Integer.parseInt(to_);
              if (from <= 0 || to <= 0) {
                  response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=2&graph=" + name);
                  return;
              }
              Interactor i = new Interactor();
              Generator g  = new Generator();
              
              g.name = name;
              g.path = path;
              
              String filename = config_.rootDir + config_.dataPath + name;
              Graph graph_ = i.loadGraph(filename);
              if (graph_ == null) {
                  response.sendRedirect(Config.getInstance().rootUrl + "/builder/?fail=4&graph=" + name);
              }
              g.lockGraph(graph_);
              graph_.remove_edge(from, to, g.DIRECTED);
              g.saveGraph(graph_);
              
              response.sendRedirect(Config.getInstance().rootUrl + "/builder/?graph=" + name);
              return;
          }
	
	
%>