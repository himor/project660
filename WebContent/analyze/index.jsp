<%@ include file="../header.jsp" %>
<%@page import="java.util.Map"%>
<div class="content">
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
    %>
    <div class="graphData">
    <% out.print(graph_.print_graph(true)); %>
    </div>
    
    <div class="graphVisual" id="graphVisual">
    <div class="result">
    <%= i.getAnalisys(graph_) %>
    </div>
    <canvas id="viewport" width="800" height="600"></canvas>
    </div>
    <%        
    }   
    %>
    <% }
    if (graphName == null ) {
    %>
    <div class="filelist">
    <table>
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
    </tr>
    </thead>
    <tbody>
    <%
    Interactor i = new Interactor();
    int counter  = 0;
    for (Map.Entry<String, FileInfo> entry : i.getFileList(config_.rootDir).entrySet()) {
        if (entry.getValue().getLocked())
            continue;
        
        out.print("<tr><td>" + (++counter) + "</td>");
        out.print("<td><a href=\"" + root + "/analyze/?graph=" + entry.getKey() + "\">");
        out.print(entry.getKey());
        out.println("</a></td>");
    }
    %>
    </table>
    </div>
    <div class="info">
    <h3>Please select a file</h3>
    </div> 
    <%} %>
</div>
<% if (loaded) {%>
<script type="text/javascript">
var sys = arbor.ParticleSystem(2600, 512, .5);
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