<%@ include file="../header.jsp" %>
<%@page import="java.util.Map"%>
<div class="content">
    <div class="filelist">
    <ul>
    <%
    Interactor i = new Interactor();
    String path = request.getRealPath("/");
    
    for (Map.Entry<String, String> entry : i.getFiles(path).entrySet()) {
        
        out.print("<li>");
        out.print("<a href=\"javascript:load('" + entry.getKey() + "');\">");
        out.print(entry.getKey());
        out.println("</a>");
        out.print("</li>");
    }
    
    
    
    %>
    </ul>
    </div>
    <div class="info">
    
    
    
    </div>
</div>
<%@ include file="../footer.jsp" %>