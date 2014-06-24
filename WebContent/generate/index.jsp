<%@ include file="../header.jsp" %>
<%@page import="java.util.Map"%>
<div class="content">
    <div class="filelist">
    <table>
    <thead>
    <tr>
    	<th>#</th>
    	<th>Name</th>
    	<th>Load</th>
   	</tr>
   	</thead>
   	<tbody>
    <%
    Interactor i = new Interactor();
    int counter  = 0;
    
    for (Map.Entry<String, FileInfo> entry : i.getFileList(config_.rootDir).entrySet()) {
        out.print("<tr><td>" + (++counter) + "</td>");
        
        if (entry.getValue().getLocked()) {
            out.print("<td>" + entry.getKey() + "</td>");
            out.print("<td>locked</td>");
        } else {            
	        out.print("<td><a href=\"javascript:loadInfo('" + entry.getKey() + "')\">");
	        out.print(entry.getKey());
	        out.println("</a></td>");
	        out.print("<td><a href=\"" + root + "/analyze/?graph=" + entry.getKey() + "\">...</a></td></tr>");
        }
        
    }
    
    %>
    </table>
    </div>
    <div class="info">
        <div class="infoData">
	       <h3>Please select a file...</h3>
	    </div>
	    <div class="generator">
	       <h3>Generate new graph</h3>
	       <form action="<%= root %>/slave.jsp" method="post">
		       <input type="hidden" name="action" value="generate">
		       <label>N = </label>
		       <input type="text" name="nvalue" required="required">
		       <br />
		       <label>P = </label>
		       <input type="text" name="pvalue" required="required">
		       <br />
		       <label>Name </label>
               <input type="text" name="name" required="required">
               <br />
               <input type="submit" value="Start">
		       <small>Generation of large graphs cat take hours...</small>
	       </form>
	       
	    </div>  
    </div>
</div>
<%@ include file="../footer.jsp" %>