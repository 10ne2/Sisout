<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>

    alert("${message}");
    if(window.opener) {
        window.opener.parent.location.reload();
        window.close();
    }else{
        if(window.parent){
            window.parent.location.href="<%=(request.getContextPath().isEmpty() ? "/":request.getContextPath()) + "/login/index"%>";
        }else{
            window.location.href="<%=(request.getContextPath().isEmpty() ? "/login/index":request.getContextPath() + "/login/index")%>";
        }
    }

</script>
