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
    <div class="filelist200">
    <table>
    <thead>
    <tr>
    	<th>#</th>
    	<th>Name</th>
   	</tr>
   	</thead>
   	<tbody>
    <%
    Interactor in = new Interactor();
    int counter  = 0;
    
    for (Map.Entry<String, FileInfo> entry : in.getFileList(config_.rootDir).entrySet()) {
        out.print("<tr><td>" + (++counter) + "</td>");
        
        if (entry.getValue().getLocked()) {
            out.print("<td>" + entry.getKey() + "[");
            out.print("locked]</td>");
        } else {            
	        out.print("<td><a href=\"" + root + "/builder/?graph=" + entry.getKey() + "\">");
	        out.print(entry.getKey());
	        out.println("</a></td>");
	        out.print("</tr>");
        }
        
    }
        
    %>
    </table>
    </div>
    <div class="builder">
	    <div class="generator">
	    <% String success = request.getParameter("success");
	    String fail_    = request.getParameter("fail");
	    int fail        = 0;
	    
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

	       <h3>Build a graph</h3>
	       <% if (!loaded)  {%>
	       
           <form action="<%= root %>/slave.jsp" method="post">
               <input type="hidden" name="action" value="build">
               <label>Name</label>
               <input type="text" name="name" required="required"><br />
               <label>N = </label>
               <input type="text" name="nvalue" required="required"><br />
               <input type="submit" value="Build">
           </form>
	       
	       <% } else { %>
	       <form action="<%= root %>/slave.jsp" method="post">
		       <input type="hidden" name="action" value="insertNode">
		       <input type="hidden" name="name" value="<%= graphName %>">
		       <input type="submit" value="Insert node">
		   </form>
		   
		   <form action="<%= root %>/slave.jsp" method="post">
               <input type="hidden" name="action" value="removeNode">
               <input type="hidden" name="name" value="<%= graphName %>">
                <label>Node</label>
               <input type="number" name="nvalue" required="required">
               <input type="submit" value="Remove node">
           </form>
		   
		   <form action="<%= root %>/slave.jsp" method="post">
               <input type="hidden" name="action" value="insertEdge">
               <input type="hidden" name="name" value="<%= graphName %>">
               <label>From</label>
               <input type="number" name="from" required="required"><br />
               <label>To</label>
               <input type="number" name="to" required="required"><br />  
               <input type="submit" value="Insert edge">
           </form>
           <% } %>
	    </div>
	    
	    <div class="graphVisual" id="graphVisual">
	    <canvas id="viewport" width="800" height="600"></canvas>
	    </div>
	    
    </div>
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
        out.print("sys.addEdge('node_" + i + "', 'node_" + p.y + "', {type:\"arrow\", directed:true, color: '#333'} );\n");
        p = p.next;
    }
}
%>

</script>
<%} %>


<%@ include file="../footer.jsp" %>