<%@ include file="../header.jsp" %>
<%@page import="java.util.Map"%>

<%
    Boolean loaded = false;
    String graphName = request.getParameter("graph");
    String filename;
    Graph graph_ = null;
    if (graphName != null) {
	    %>
	    
	    <%
	    Interactor i = new Interactor();
	    filename = config_.rootDir + config_.dataPath + graphName;
	    graph_ = i.loadGraph(filename);
	    if (graph_ == null) {
	        out.print("Unable to load the graph " + filename );
	    } else {
	        loaded = true;
	    }
    }
    %>
    
<div class="content">

    <% if (!loaded)  {%>
	    <div class="fulltable">
	    <%@ include file="../table.jsp" %>
	    </div>
    <% } else { %>
	    <div class="shorttable nowidth">
	    <% tableSize = 2; %>
	    <%@ include file="../table.jsp" %>
	    </div>
    <% } %>  

    <div class="builder">
	    <div class="generator">
	    <% String success = request.getParameter("success");
	    String fail_      = request.getParameter("fail");
	    int fail          = 0;
	    
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
        }
	    %>

	       <% if (!loaded)  {%>
	       <h3>Build a graph</h3>
           <form action="<%= root %>/slave.jsp" method="post">
               <input type="hidden" name="action" value="build">
               <label class="l50">Name</label>
               <input type="text" name="name" class="i170" required="required"><br />
               <label class="l50">N = </label>
               <input type="text" class="i60" name="nvalue" required="required">
               <input type="submit" value="Build">
           </form>
	       
	       <% } else { %>
	       <h3 class="strong">Edit graph</h3>
	       <div class="editor-blocks">
	           <div class="form">
                   <form action="<%= root %>/slave.jsp" method="post">
                   <input type="hidden" name="action" value="insertNode">
                   <input type="hidden" name="name" value="<%= graphName %>">
                   <label>Insert node(s)</label>
                   <input type="number" name="nvalue" value="1" required="required" placeholder="Number of nodes">
                   <input type="submit" value="Insert node">
                   </form>
	           </div>
	           <div class="form">
		           <form action="<%= root %>/slave.jsp" method="post">
	               <input type="hidden" name="action" value="removeNode">
	               <input type="hidden" name="name" value="<%= graphName %>">
	               <label>Remove node</label>
	               <input type="number" name="nvalue" required="required" placeholder="Node id">
	               <input type="submit" value="Remove node">
	               </form>
	           </div>
	           <div class="form">
	               <form action="<%= root %>/slave.jsp" method="post">
	               <input type="hidden" name="action" value="insertEdge">
	               <input type="hidden" name="name" value="<%= graphName %>">
	               <label>Insert edge</label>
	               <input type="number" name="from" required="required" placeholder="From"><br />
	               <input type="number" name="to" required="required" placeholder="To"><br />
	               <input type="submit" value="Insert edge">
	               </form>
	           </div>
	           <div class="form">
	               <form action="<%= root %>/slave.jsp" method="post">
                   <input type="hidden" name="action" value="removeEdge">
                   <input type="hidden" name="name" value="<%= graphName %>">
                   <label>Remove edge</label>
                   <input type="number" name="from" required="required" placeholder="From"><br />
                   <input type="number" name="to" required="required" placeholder="To"><br />
                   <input type="submit" value="Remove edge">
                   </form>
	           </div>
	           <div class="clear"></div>
	       </div>
	       
	       
	       
		   <%--
		   
		   
		   <form action="<%= root %>/slave.jsp" method="post">
               <input type="hidden" name="action" value="insertEdge">
               <input type="hidden" name="name" value="<%= graphName %>">
               <label>From</label>
               <input type="number" name="from" required="required"><br />
               <label>To</label>
               <input type="number" name="to" required="required"><br />  
               <input type="submit" value="Insert edge">
           </form>
            --%>
           
           <% } %>
	    </div>
	    
    </div>
    
    <% if (loaded)  {%>
        <div class="graphVisual" id="graphVisual">
        <canvas id="viewport" width="1020" height="600"></canvas>
        </div>
    <% } %>
    
    
</div>

<% if (loaded) {%>
<script type="text/javascript">
var sys = arbor.ParticleSystem();
sys.parameters({gravity:true})
sys.renderer = Renderer("#viewport");
<% 
// compute max in/out degree
int max_i = 0, 
    max_o = 0;

for (int i = 1; i <= graph_.getNvertices(); i++) {
    if (max_i < graph_.getIndegree()[i])
        max_i = graph_.getIndegree()[i];
    if (max_o < graph_.getOutdegree()[i])
        max_o = graph_.getOutdegree()[i];
}

max_i ++;
max_o ++;

for (int i = 1; i <= graph_.getNvertices(); i++) { 
    int r = (0 + (255 / max_i * (graph_.getIndegree()[i] + 1)));
    int g = (255 - (255 / max_o * (graph_.getIndegree()[i] + 1)));
    int b = (255 - (255 / max_o * (graph_.getIndegree()[i] + 1)));
    out.print("sys.addNode('node_" + i + "', {color: 'rgb(" + r + "," + g + "," + b + ")', shape: 'dot', label: '" + i + "'});\n");
}

Edgenode p = new Edgenode();
for (int i = 1; i <= graph_.getNvertices(); i++) {
    p = graph_.getEdge(i);
    if (p == null) continue;
    while (p != null) {
        out.print("var edge_" + i +"_" + p.y + " = sys.addEdge('node_" + i + "', 'node_" + p.y + "', {type:\"arrow\", directed:true, color: '#333'} );\n");
        p = p.next;
    }
}
%>

</script>
<%} %>


<%@ include file="../footer.jsp" %>