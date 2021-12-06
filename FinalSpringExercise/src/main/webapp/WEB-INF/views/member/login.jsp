<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>:: Welcome YBM World ::</title>
    <link rel="stylesheet" href="${path }/css/member/login.css">
</head>
<body oncontextmenu="return false" ondragstart="return false" onselectstart="return false">
<div id="wrap">
    <div class="intro">
        <div id="introInner1">
            <h1>Welcome YBM World!</h1>
            <p id="firp">Web Folder를 이용하여 소중한 사진, 자료를 보관하세요!</p>
            <p id="secp">국내 최저 가격으로 사진과 문서 자료를 한 곳에 안전하게 보관하고 작업할 수 있습니다.</p>
        </div>
        <div id="introInner2">
            <input type="button" id="regitBtn" value="시작하기" onclick="location.href='register.do'">
        </div>
    </div>
    <div class="intro">
        <div id="loginDiv">
            <form name="loginForm" method="post" action="login.do">
                <div class="textarea">
                    <img src="${path }/img/email.png" class="idimg">
                    <input type="text" name="id" class="textbox" placeholder="아이디를 입력하세요." spellcheck="false" maxlength="20" required>
                </div>
                <div class="textarea">
                    <img src="${path }/img/key.png" class="idimg">
                    <input type="password" name="pwd" class="textbox" placeholder="비밀번호를 입력하세요." spellcheck="false"  maxlength="20" required>
                </div>
                <div class="btnDiv">
                    <input type="submit" id="loginBtn" value="Login">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>