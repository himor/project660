<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="project660.*" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>

<div class="filelist">
    <table class="table">
    <thead>
    <tr>
        <th>#</th>
        <th>Name</th>
        <th></th>
        <% if (tableSize == 1) { %>
        <th>Date</th>
        <% } %>
    </tr>
    </thead>
    <tbody>
    <%
    Interactor tableInteractor = new Interactor();
    int counter                = 0;
    boolean needUpdateTable    = false;
    
    Map<String, FileInfo> map = tableInteractor.getFileList(config_.rootDir);
    
    for (Map.Entry<String, FileInfo> entry : map.entrySet()) {
        %>
        <tr>
            <td><%=++counter%></td>
        <%
        if (entry.getValue().getLocked()) {
            needUpdateTable = true;
            %>
            <td><%= entry.getKey() %></td>
            <td></td>
            <%
        } else {
            %>
            <td><a href="javascript:loadInfo('<%=entry.getKey()%>');">
            <%=entry.getKey()%>
            </a></td>
            <td>
            <a href="<%=root %>/analyze/?graph=<%=entry.getKey()%>" title="analyze">
            <span class="glyphicon glyphicon-play"></span></a>
            <a href="<%=root %>/builder/?graph=<%=entry.getKey()%>" title="edit">
            <span class="glyphicon glyphicon-cog"></span></a>
            </td>
            <%
        }
        %>
        <% if (tableSize == 1) { %>
        <td><%= entry.getValue().getFormattedDate("MM/dd/yy HH:mm") %></td>
        <% } %>
        </tr>
        <%       
    }
    %>
    </table>
    </div>
    
    <%
    if (needUpdateTable) {
    %>
    <script>needUpdateTable = true;</script>
    <%
    }
    %>