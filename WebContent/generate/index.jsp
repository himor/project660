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
    <h3>Please select a file</h3>
    
    
    </div>
</div>
<%@ include file="../footer.jsp" %>