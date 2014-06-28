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
    
    String success = request.getParameter("success");
    String fail_    = request.getParameter("fail");
    int fail        = 0;
    
    if (fail_ != null)
        fail = Integer.parseInt(fail_);
    
    %>
    </table>
    </div>
    <div class="info">
        <div class="infoData">
            <% if (success != null) {
                out.print("<h3 class='success'>Generating the graph</h3>");
            } else if (fail == 1) {
                out.print("<h3 class='fail'>Unexpected error in parameters</h3>");
            } else if (fail == 2) {
                out.print("<h3 class='fail'>Parameters should not be less than zero</h3>");
            } else if (fail == 3) {
                out.print("<h3 class='fail'>Graph with this name already exists</h3>");
            } else {
            %>
	       <h3>Please select a file...</h3>
	       <% } %>
	    </div>
	    <div class="generator">
	       <h3>Generate a new graph</h3>
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
               <input type="submit" value="Start"><br/>
		       <small>Generating of large graphs can take hours...</small>
	       </form>
	       
	    </div>  
    </div>
</div>
<%@ include file="../footer.jsp" %>