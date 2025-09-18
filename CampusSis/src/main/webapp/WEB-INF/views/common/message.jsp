<%@ page contentType="text/html; charset=UTF-8" %>
<%
  String msg = (String)request.getAttribute("msg");
  String url = (String)request.getAttribute("url");
%>
<script>
  alert('<%= msg == null ? "" : msg.replace("'", "\\'") %>');
  location.href='<%= url == null ? (request.getContextPath() + "/lecture/final") : url %>';
</script>