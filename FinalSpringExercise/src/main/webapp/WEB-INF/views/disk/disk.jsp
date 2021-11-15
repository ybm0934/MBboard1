<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Web Folder</title>
    <link rel="stylesheet" href="${path }/css/disk/disk.css">
    <script src="${path }/lib/jquery-3.6.0.min.js"></script>
    <script>
    	$(document).ready(function(){
    		$('#statusDiv').hide();
    		$('form[name=deleteFile]').hide();
    		$('form[name=fileDownload]').hide();
    		
    		// 파일 업로드
    		$('#file').change(function(){
    			if($(this).val() != '') {
    				$('form[name=fileUpload]').submit();
    			}
    		});
    		
    		// 전체 선택
    		$('#allCheck').click(function(){
    			if($('#allCheck').prop('checked')) {
	    			$('.checkBox').prop('checked', true);
    			}else {
	    			$('.checkBox').prop('checked', false);
    			}
    		});
    		
    		// 파일 선택 시 이벤트
    		$('input[type=checkBox]').change(function(){
    			const boxLen = $('input[name=checkBox]').length;
    			const checkLen = $('input[name=checkBox]:checked').length;
    			
    			if(boxLen != checkLen) {
    				$('#allCheck').prop('checked', false);
    			}else {
    				$('#allCheck').prop('checked', true);
    			}
    			
    			if(checkLen > 0) {
    				$('#statusDiv').show();
    				$('form[name=fileUpload]').hide();
    				$('form[name=fileDownload]').show();
    				$('form[name=makeFolder]').hide();
    				$('form[name=deleteFile]').show();
    				$('.checkDiv').css('visibility', 'visible');
    				$('#statusSpn1').html(checkLen);
    			}else {
    				$('form[name=fileDownload]').hide();
    				$('form[name=fileUpload]').show();
    				$('form[name=deleteFile]').hide();
    				$('form[name=makeFolder]').show();
    				$('#statusDiv').hide();
    				$('.checkDiv').css('visibility', 'hidden');
    				$('#statusSpn1').html(0);
    			}
    		});
    		
    		// 파일 삭제
    		$('#delBtn').click(function(){
    			if(confirm('정말 삭제하시겠습니까?')) {
					const arr = new Array();
					$('.checkBox:checked').each(function(){
						arr.push($(this).val());
					});
					
					const div = arr.join(':');
					$('input[name=checked]').val(div);
					if(arr.length != 0) {
						$('form[name=deleteFile]').submit();
					}
    			}
    		});
    		
    		// 선택 다운로드
    		$('#downBtn').click(function(){
    			$('.checkBox:checked').each(function(i){
					setTimeout(function() {
	    				const frm = $('<iframe src="fileDownload.do?fileName=' + $('.checkBox:checked').eq(i).val() + '" style="display: none;" />');
	    				frm.appendTo('body');
					}, 200 * (i + 1));
    			});
				$('iframe').remove();
    		});
    		
    		// 폴더 경로 디렉토리
    		let inPath = '${inPath }';
    		const parentPath = '${parentPath }';
    		const parentShortPath = '${parentShortPath }';
    		if(parentShortPath != '') {
	    		//const splitPath = parentShortPath.split('/');
	   			$.each(splitPath, function(index, item) {
	   				inPath = inPath.substring(0, inPath.lastIndexOf('/'));
	    			//const aTag = '<a href="#" onclick="upFolder(' + "'" + inPath + "'" + ');">' + item + '</a>';
	    			$('#pathDiv').append(' &gt; ' + aTag);
	    			if(index == splitPath.length - 2) return false;
	   			});
    		}
    	});
    	
    	// 폴더 안으로 이동
    	function inFolder(folderName, type, path, upPath) {
    		if(type == 'folder') {
    			document.enterFolder.inPath.value = path;
    			document.enterFolder.submit();
    		}
    	}
    	
    	// 부모 경로로 이동
    	function upFolder(upPath) {
    		document.enterFolder.inPath.value = upPath;
    		document.enterFolder.submit();
    	}
    	
    	// 개별 다운로드
   		function download(fileName, type) {
   			if(type == 'file') {
   				document.fileDownload.fileName.value = fileName;
   				document.fileDownload.submit();
   			}
   		}
    </script>
</head>
<body>
<div id="wrap">
    <div id="titleDiv">
        <h1>Web Folder</h1>
    </div>
    <div id="menuDiv">
    	<div class="menuItems">
		   	<span id="checkBoxSpn">
		       	<input type="checkBox" id="allCheck">
		   	</span>
    	</div>
    	<div class="menuItems">
		   	<form name="fileUpload" method="post" action="fileUpload.do" enctype="multipart/form-data">
		   		<input type="hidden" name="path" value="">
		   		<label for="file" id="fileLabel" class="btn">파일 올리기</label>
		   		<input type="file" multiple="multiple" name="files" id="file">
		   	</form>
		   	<form name="fileDownload" method="post" action="fileDownload.do">
		   		<input type="hidden" name="path" value="">
		   		<input type="hidden" name="fileName">
		   		<input type="button" id="downBtn" class="btn" value="내려받기">
		   	</form>
		</div>
		<div class="menuItems">
	    	<form name="makeFolder" method="post" action="makeFolder.do">	
		    	<input type="hidden" name="path" value="">
		        <input type="submit" id="mkBtn" class="btn" value="폴더 생성">
	    	</form>
	    	<form name="deleteFile" method="post" action="deleteFile.do">
		    	<input type="hidden" name="path" value="">
		    	<input type="hidden" name="checked">
		        <input type="button" id="delBtn" class="btn" value="선택 삭제">
	    	</form>
    	</div>
		<div class="menuItems">
	    	<form name="enterFolder" method="post" action="disk.do">
		    	<input type="text" name="inPath">
	    	</form>
    	</div>
    </div>
    <c:if test="${empty list }">
    <div id="contentDiv">
    	<div class="noDiv">
    		<span class="noSpn">파일이 없습니다.</span>
    	</div>
    </div>
    </c:if>
    <c:if test="${!empty list }">
    <div id="pathDiv">
    	<a href="disk.do">HOME</a>
    	${parentTag }
    </div>
    <div id="statusDiv">
    	<span id="statusSpn1"></span>
	    <span id="statusSpn2">개 항목 선택 됨</span>
    </div>
    <div id="contentDiv">
    <c:forEach var="vo" items="${list }">
        <div class="content">
		    <c:set var="name" value="${vo.name }" />
		   	<c:set var="nameLeng" value="${fn:length(vo.name) }" />
		   	<c:if test="${nameLeng >= 11 }">
				<c:set var="name" value="${fn:substring(name, 0, 11) }···" />
			</c:if>
        	<div class="checkDiv">
        		<input type="checkBox" name="checkBox" class="checkBox" value="${vo.name }">
        	</div>
	        <div class="innerDIv" title="${vo.name }" onclick="
		        <c:if test="${vo.type == 'folder' }">inFolder('${vo.name}', '${vo.type }', '${vo.path }');</c:if>
		        <c:if test="${vo.type == 'file' }">download('${vo.name}', '${vo.type }');</c:if>">
	        	<div class="fileImg">
	            	<img src="${path }/img/${vo.type }.png">
	            </div>
	            <div class="fileName">
	                <span class="nameSpn">${name }</span>
	            </div>
	            <div class="fileSize">
	                <span class="fileSizeSpn">
	                <c:if test="${vo.type == 'file'}">
	                	${vo.size }
	                	${vo.sizeType }
	                </c:if>
	                </span>
	            </div>
	        </div>
        </div>
    </c:forEach>
    </div>
    </c:if>
    <div id="pagingDiv">
        
    </div>
</div>
</body>
</html>