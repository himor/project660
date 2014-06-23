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
    Interactor i    = new Interactor();
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
    <!-- <canvas id="viewport" width="800" height="600"></canvas> -->
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
<%-- var sys = arbor.ParticleSystem(2600, 512, .5);
sys.parameters({gravity:true})
sys.renderer = Renderer("#viewport") ;
<% for (int i = 1; i <= graph_.getNvertices(); i++) { 
    out.print("sys.addNode('node_" + i + "', {color: 'grey', shape: 'dot', label: '" + i + "'});\n");
}

Edgenode p = new Edgenode();
for (int i = 1; i <= graph_.getNvertices(); i++) {
    p = graph_.getEdge(i);
    if (p == null) continue;
    while (p != null) {
        out.print("sys.addEdge('node_" + i + "', 'node_" + p.y + "', {type:\"arrow\", directed:true} );\n");
        p = p.next;
    }
}
%> --%>

function beginAddNodesLoop(graph){
    graph.beginUpdate();

    <% for (int i = 1; i <= graph_.getNvertices(); i++) { 
        out.print("graph.addNode(" + i + ");\n");
    }

    Edgenode p = new Edgenode();
    for (int i = 1; i <= graph_.getNvertices(); i++) {
        p = graph_.getEdge(i);
        if (p == null) continue;
        while (p != null) {
            out.print("graph.addLink(" + i + ", " + p.y + ");\n");
            p = p.next;
        }
    }
    %>
    
    graph.endUpdate();
}

var graph = Viva.Graph.graph();

var layout = Viva.Graph.Layout.forceDirected(graph, {

});

var graphics = Viva.Graph.View.svgGraphics();
graphics.node(function(node){
   return Viva.Graph.svg('rect')
      .attr('width', 15)
      .attr('height', 15)
      .attr('fill', node.data ? node.data : '#00a2e8');
});

var renderer = Viva.Graph.View.renderer(graph,
    {
        layout     : layout,
        graphics   : graphics,
        container  : document.getElementById('graphVisual'),
        renderLinks : true
    });

renderer.run(50);

beginAddNodesLoop(graph);

l = layout;
		

</script>
<%} %>
<%@ include file="../footer.jsp" %>