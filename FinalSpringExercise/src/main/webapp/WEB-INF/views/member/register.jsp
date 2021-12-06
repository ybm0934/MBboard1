<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>:: YBM World Member Join ::</title>
    <link rel="stylesheet" href="${path }/css/member/register.css">
    <script src="${path }/lib/jquery-3.6.0.min.js"></script>
    <script>
    	$(document).ready(function(){
    		const regexp1 = /(\s*)/g; // 공백
    		const regexp2 = /\W|\s/g; // 공백 및 특수문자
    		
    		$('#idCheck').click(function(){
    			let userid = $('#userid').val();
    			userid = userid.search(regexp2);
    			
    			if($('#userid').val().length == 0) {
    				alert('아이디를 입력해주세요.');
    				$('#userid').focus();
    				
    				return false;
    			}else if(userid > -1) {
    				alert('사용할 수 없는 아이디입니다.');
    				$('#userid').val('');
    				$('#userid').focus();
    				
    				return false;
    			}else {
    				$.ajax({
    					url : 'idCheck.do',
    					type : 'POST',
    					dataType : 'HTML',
    					data : {
    						userid : $('#userid').val()
    					},
    					success : function(data) {
    						$('#idCheckDiv').html(data);
    					},
    					error : function(data) {
    						alert('ajax 통신 실패!');
    					}
    				});
    			}
    		});
    	});
    </script>
</head>
<body>
<div id="wrap">
    <div id="titleDiv">
        <h1>회원가입</h1>
    </div>
    <div id="joinDiv">
        <form name="joinForm" method="post" action="register.do">
        <table id="joinTable">
            <caption>회원가입 테이블</caption>
            <tr>
                <th>이름</th>
                <td>
                    <input type="text" name="name" class="textbox" placeholder="이름을 입력하세요." spellcheck="false" required>
                </td>
            </tr>
            <tr>
                <th>아이디</th>
                <td>
	                <div>
	                    <input type="text" name="userid" id="userid" class="textbox" placeholder="아이디를 입력하세요." spellcheck="false" required>
	                    <input type="button" id="idCheck" class="btn" value="중복확인">
	                </div>
	                <div id="idCheckDiv"></div>
                </td>
            </tr>
            <tr>
                <th>비밀번호</th>
                <td>
                    <input type="password" name="pwd" class="textbox" placeholder="비밀번호를 입력하세요." spellcheck="false" required>
                </td>
            </tr>
            <tr>
                <th>비밀번호 확인</th>
                <td>
                    <input type="password" name="pwdOk" class="textbox" placeholder="비밀번호를 한 번 더 입력하세요." spellcheck="false" required>
                </td>
            </tr>
            <tr>
                <th>이메일</th>
                <td>
                    <input type="text" name="email" class="textbox" placeholder="이메일을 입력하세요." spellcheck="false" required>
                </td>
            </tr>
        </table>
        <div id="regitBtnDiv">
            <input type="submit" id="regitBtn" class="btn" value="가입하기">
        </div>
        </form>
    </div>
</div>
</body>
</html>