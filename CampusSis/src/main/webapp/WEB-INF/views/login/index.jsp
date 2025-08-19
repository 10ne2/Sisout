<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Login | CAMP_US</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', sans-serif;
            background-color: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            display: flex;
            width: 800px;
            height: 400px;
        }
        .logo-section {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .logo-section img {
            max-width: 100%;
            height: auto;
        }
        .login-section {
            flex: 1;
            padding: 40px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        .login-section h2 {
            font-size: 28px;
            color: #38B2AC;
            margin-bottom: 10px;
            text-align: center;
        }
        .login-section p {
            font-size: 14px;
            color: #888;
            margin-bottom: 20px;
            text-align: center;
        }
        .form-box {
            width: 330px;
        }
        .form-box input[type="text"],
        .form-box input[name="retUrl"],
        .form-box input[type="password"]
        {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-box input {
            border: 1px solid #ccc;
        }
        .forgot {
            display: flex;
            justify-content: space-between;
            font-size: 13px;
            margin-bottom: 15px;
        }
        .forgot a {
            color: #38B2AC;
            text-decoration: none;
        }
        .form-box button {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
            font-size: 14px;
            background-color: #38B2AC;
            color: white;
            border: none;
            cursor: pointer;
        }
        .form-box button:hover {
            background-color: #319795;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="logo-section">
        <img src="${ctx}/resources/images/campusLogo.png" alt="Campus Logo">
    </div>
    <div class="login-section">
        <h2>LOGIN</h2>
        <p>회원님의 아이디와 비밀번호를 정확히 입력해주세요.</p>
        <form method="post" action="${ctx}/login/index/post" class="form-box">
        	<input name="retUrl" value="${retUrl}" type="hidden"/>
            <input type="text" name="id" placeholder="ID" required value="19800101" />
            <input type="password" name="pwd" placeholder="PASSWORD" required value="19800101" />
            <div class="forgot">
                <span>비밀번호를 잊어버리셨나요?</span>
                <a href="login/find-password">비밀번호 찾기</a>
            </div>
            <button type="submit">로그인</button>
        </form>
    </div>
</div>
<c:if test="${not empty sessionScope.loginFailMsg}">
    <script>
        alert("${sessionScope.loginFailMsg}");
    </script>
    <c:remove var="loginFailMsg" scope="session" />
</c:if>
</body>
</html>
