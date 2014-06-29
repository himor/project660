<%@ include file="../header.jsp" %>
<%@ page import="java.util.Map"%>
<div class="content">
    
    <div class="fulltable">
    <%@ include file="../table.jsp" %>
    </div>
    
    <div class="info">
        <div class="infoData">
            <% 
            String success = request.getParameter("success");
            String fail_   = request.getParameter("fail");
            int fail       = 0;
            
            if (fail_ != null)
                fail = Integer.parseInt(fail_);
            
            if (success != null) {
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
	       <h3 class="strong">Generate a new graph</h3>
	       <form action="<%= root %>/slave.jsp" method="post">
		       <input type="hidden" name="action" value="generate">
		       <label class="l50">N = </label>
		       <input type="text" class="i60" name="nvalue" id="gen_nvalue" required="required">
		       <label class="l00">P = </label>
		       <input type="text" class="i60" name="pvalue" id="gen_pvalue" required="required">
		       <br />
		       <label class="l50">Name </label>
               <input type="text" name="name" required="required" id="gen_name">
               <br /><input type="submit" value="Start"><br />
		       <small>Generating of large graphs can take hours...</small>
	       </form>
	       
	    </div>  
    </div>
</div>
<%@ include file="../footer.jsp" %>