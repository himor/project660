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
}
%>