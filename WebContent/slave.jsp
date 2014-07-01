<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>
<%@ include file="setup.jsp" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%
	String action = request.getParameter("action");
	if (action == null || action.equals(""))
		return;
	
	String path = Config.getInstance().rootDir;
	
	Map<String, String> map = new HashMap<String, String>();
	
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
       * insertNode - AJAX
       */
       if (action.equals("insertNode")) {
           String form[] = request.getParameterValues("form[]");
           response.setContentType("application/json");
           Interactor i = new Interactor();
           if (form == null || form.length != 3) {
               map.put("error", "1");
               out.print(i.getJson(map));
               return;
           } else {
               String name = form[1];
               String n_   = form[2];
               if (name == null || n_ == null) {
                   map.put("error", "1");
                   out.print(i.getJson(map));
                   return;
               }
               int n = Integer.parseInt(n_);
               n     = n > 1 ? n : 1;
               Generator g  = new Generator();
               g.name = name;
               g.path = path;
               String filename = config_.rootDir + config_.dataPath + name;
               Graph graph_ = i.loadGraph(filename);
               if (graph_ == null) {
                   map.put("error", "1");
                   out.print(i.getJson(map));
                   return;
               }
               g.lockGraph(graph_);
               graph_.addVertex(n);
               g.saveGraph(graph_);
               map.put("error", "0");
               map.put("count", "" + n);
               map.put("total", "" + graph_.getNvertices());
               out.print(i.getJson(map));
               return;
           }
       }
      
        /**
         * removeNode - AJAX
         */
         if (action.equals("removeNode")) {
             String form[] = request.getParameterValues("form[]");
             response.setContentType("application/json");
             Interactor i = new Interactor();
             if (form == null || form.length != 3) {
                 map.put("error", "1");
                 out.print(i.getJson(map));
                 return;
             } else {
                 String name = form[1];
                 String n_   = form[2];
                 if (name == null || n_ == null) {
                     map.put("error", "1");
                     out.print(i.getJson(map));
                     return;
                 }
                 int n        = Integer.parseInt(n_);
                 Generator g  = new Generator();
                 g.name = name;
                 g.path = path;
                 String filename = config_.rootDir + config_.dataPath + name;
                 Graph graph_ = i.loadGraph(filename);
                 if (graph_ == null) {
                     map.put("error", "1");
                     out.print(i.getJson(map));
                     return;
                 }
                 g.lockGraph(graph_);
                 graph_.removeVertex(n);
                 g.saveGraph(graph_);
                 map.put("error", "0");
                 map.put("id", "" + n);
                 out.print(i.getJson(map));
                 return;
             }
         }
         
         /**
          * insertEdge - AJAX
          */
         if (action.equals("insertEdge")) {
             String form[] = request.getParameterValues("form[]");
             response.setContentType("application/json");
             Interactor i = new Interactor();
             if (form == null || form.length != 4) {
                 map.put("error", "1");
                 out.print(i.getJson(map));
                 return;
             } else {
	             String name  = form[1];
	             String from_ = form[2];
	             String to_   = form[3];
	             if (from_ == null || to_ == null || name == null) {
	                 map.put("error", "1");
	                 out.print(i.getJson(map));
	                 return;
	             }
	             int from = Integer.parseInt(from_);
	             int to   = Integer.parseInt(to_);
	             if (from <= 0 || to <= 0) {
	                 map.put("error", "1");
                     out.print(i.getJson(map));
                     return;
	             }
	             Generator g  = new Generator();
	             g.name = name;
	             g.path = path;
	             String filename = config_.rootDir + config_.dataPath + name;
	             Graph graph_ = i.loadGraph(filename);
	             if (graph_ == null) {
	                 map.put("error", "1");
	                 out.print(i.getJson(map));
	                 return;
	             }
	             g.lockGraph(graph_);
	             graph_.insert_edge(from, to, g.DIRECTED);
	             g.saveGraph(graph_);
	             map.put("error", "0");
                 map.put("from", "" + from);
                 map.put("to", "" + to);
                 out.print(i.getJson(map));
                 return;
              }
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