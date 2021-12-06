<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="<%=request.getContextPath() %>" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>:: YBM Web Folder ::</title>
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
    		
    		// 폴더 생성
    		$('#mkBtn').click(function(){
    			$('input[name=path]').val('${inPath}');
    			$('form[name=makeFolder]').submit();
    		});
    		
    		// 전체 선택
    		$('#allCheck').click(function(){
    			if($('#allCheck').prop('checked')) {
	    			$('.checkBox').prop('checked', true);
    			}else {
	    			$('.checkBox').prop('checked', false);
    			}
    		});
    		
    		// 체크박스 체크 시 이벤트
    		$('input[type=checkBox]').change(function(){
    			const ckLen = $('input[name=checkBox]').length;
    			const ckedLen = $('input[name=checkBox]:checked').length;
    			
    			// 전체 선택
    			if(ckLen == ckedLen) {
    				$('#allCheck').prop('checked', true);
    			}else {
    				$('#allCheck').prop('checked', false);
    			}
    			
    			// 체크 박스 목록 불러오기
    			$('.checkBox').each(function(){
	    			const id = $(this).attr('id');
	    			const no = id.substring('check'.length);
	    			
	    			// 체크 박스 선택 시 체크 박스 표시 및 배경 색 변경
	    			if($('#' + id).prop('checked') == true) {
	    				$('#checkDiv' + no).css('visibility', 'visible');
	    				$('#contentAttr' + no).css({'background-color':'#80c3fd', 'border-color':'#2f9dfc'});
	    			}else {
		    			$('#checkDiv' + no).css('visibility', 'hidden');
	    				$('#contentAttr' + no).css({'background-color':'white', 'border-color':'#80c3fd'});
	    			}
    			});
    			
    			// 체크 박스 선택 시 메뉴 변경
    			if(ckedLen > 0) {
    				$('form[name=fileUpload]').hide();
    				$('form[name=folderUpload]').hide();
    				$('form[name=fileDownload]').show();
    				$('form[name=makeFolder]').hide();
    				$('form[name=deleteFile]').show();
    				$('#statusDiv').show();
    				$('#statusSpn1').html(ckedLen);
    			}else {
    				$('form[name=fileDownload]').hide();
    				$('form[name=folderUpload]').show();
    				$('form[name=fileUpload]').show();
    				$('form[name=deleteFile]').hide();
    				$('form[name=makeFolder]').show();
    				$('#statusDiv').hide();
    				$('#statusSpn1').html(0);
    			}
    		});
    		
    		// 컨텐츠 div 목록 불러오기
    		$('.contentAttr').each(function(){
    			const id = $(this).attr('id');
    			const no = id.substring('contentAttr'.length);
    			
    			// 체크 박스 선택이 안 되어 있을 시에만 hover 이벤트 발생
	    		$('#' + id).hover(function(){
	    			if($('#check' + no).prop('checked') == false) {
		    			$('#' + id).css({'background-color':'#80c3fd', 'border-color':'#2f9dfc'});
		    			$('#checkDiv' + no).css('visibility', 'visible');
	    			}
	    		}, function(){
	    			if($('#check' + no).prop('checked') == false) {
		    			$('#' + id).css({'background-color':'white', 'border-color':'#80c3fd'});
		    			$('#checkDiv' + no).css('visibility', 'hidden');
	    			}
	    		});
    		});
    		
    		// 파일 삭제
    		$('#delBtn').click(function(){
    			if(confirm('정말 삭제하시겠습니까?')) {
					const arr = new Array();
					$('.checkBox:checked').each(function(){
						arr.push($(this).val());
					});
					
					const div = arr.join('>');
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
	    				const frm = $('<iframe src="fileDownload.do?path=' + encodeURIComponent($('.checkBox:checked').eq(i).val()) + '" style="display: none;" />');
	    				frm.appendTo('body');
					}, 300 * (i + 1));
    			});
				$('iframe').remove();
    		});
    	});
    	
    	// 개별 다운로드
   		function download(path) {
 			document.fileDownload.path.value = path;
 			document.fileDownload.submit();
   		}
    	
    	// 폴더 안으로 이동
    	function inFolder(type, path) {
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
    	
    	// 폴더명 변경 이미지 보이기
    	function editView(no) {
    		$(document).ready(function(){
	    		$('#editName' + no).css('display', 'inline');
    		});
    	}
    	
    	// 폴더명 변경 이미지 숨기기
    	function editClose(no) {
    		$(document).ready(function(){
   				$('#editName' + no).css('display', 'none');
    		});
    	}
    	
    	// 폴더명 밑줄 On
    	function editLineOn(no) {
    		$(document).ready(function(){
    			$('#fileName' + no).css('text-decoration', 'underline');
    		});
    	}
    	
    	// 폴더명 밑줄 Off
    	function editLineOff(no) {
    		$(document).ready(function(){
    			$('#fileName' + no).css('text-decoration', 'none');
    		});
    	}
    	
    	// 폴더명 변경 이벤트
    	function reName(no, name) {
    		$(document).ready(function(){
    			$('#boxOn' + no).css('display', 'inline');
    			$('#boxOff' + no).css('display', 'none');
    			const textOn = $('#boxOn' + no).children('input[type=text]');
    			textOn.select();
    			
    			$('input[name=name' + no + ']').on('keydown', function(e){
    				if(e.keyCode == 13) {
    					if(textOn.val().length == 0) {
    						alert('파일명을 입력하세요.');
    					}else {
	    					$('#nameForm' + no).children('input[name=editName]').val(textOn.val());
	    					$('#nameForm' + no).submit();
    					}
    				}else if(e.keyCode == 27) {
    					$('#boxOn' + no).css('display', 'none');
    	    			$('#boxOff' + no).css('display', 'inline');
    				}
    			});
    			
    			textOn.focusout(function(){
    				textOn.val(name);
    				$('#boxOn' + no).css('display', 'none');
	    			$('#boxOff' + no).css('display', 'inline');
    			});
    		});
    	}
    </script>
</head>
<body oncontextmenu="return false" ondragstart="return false" onselectstart="return false">
<div id="titleDiv">
	<a href="disk.do">Web Folder</a>
</div>
<div id="wrap">
	<div id="loginDiv">
		<a href="login.do">로그인</a>
		 | 
		<a href="register.do">회원 가입</a>
	</div>
    <div id="menuDiv">
    	<div class="menuItems">
		   	<span id="checkBoxSpn">
		       	<input type="checkBox" id="allCheck">
		   	</span>
    	</div>
    	<div class="menuItems">
		   	<form name="fileUpload" method="post" action="fileUpload.do" enctype="multipart/form-data">
		   		<label for="file" class="btn fileLabel">파일 올리기</label>
		   		<input type="file" multiple="multiple" name="files" id="file">
		   		<input type="hidden" name="path" value="${inPath }">
		   	</form>
		   	<form name="fileDownload" method="post" action="fileDownload.do">
		   		<input type="hidden" name="path">
		   		<label for="download" id="downBtn" class="btn fileLabel">내려받기</label>
		   		<input type="hidden" id="download">
		   	</form>
		</div>
		<div class="menuItems">
	    	<form name="makeFolder" method="post" action="makeFolder.do">
		    	<input type="hidden" name="path">
		        <input type="submit" id="mkBtn" class="btn" value="폴더 생성">
	    	</form>
	    	<form name="deleteFile" method="post" action="deleteFile.do">
		    	<input type="hidden" name="checked">
		        <input type="button" id="delBtn" class="btn" value="선택 삭제">
	    	</form>
    	</div>
		<div class="menuItems">
	    	<form name="enterFolder" method="post" action="disk.do">
		    	<input type="hidden" name="inPath">
	    	</form>
    	</div>
    </div>
    <div id="statusDiv">
    	<span id="statusSpn1"></span>
	    <span id="statusSpn2">개 항목 선택 됨</span>
    </div>
    <div id="pathDiv">
    	<a href="disk.do">HOME</a>
    	${parentTag }
    </div>
    <c:if test="${empty list }">
    <div id="contentDiv">
    	<div class="noDiv">
    		<span class="noSpn">파일이 없습니다.</span>
    	</div>
    </div>
    </c:if>
    <c:if test="${!empty list }">
    <div id="contentDiv">
    <c:forEach var="vo" items="${list }">
        <div class="content">
		    <c:set var="name" value="${vo.name }" />
		   	<c:set var="nameLeng" value="${fn:length(vo.name) }" />
		   	<c:if test="${nameLeng >= 23 }">
				<c:set var="name" value="${fn:substring(name, 0, 23) }···" />
			</c:if>
			<div class="contentAttr" title="${vo.name }" id="contentAttr${vo.no }">
	        	<div class="checkDiv" id="checkDiv${vo.no }">
	       			<input type="checkBox" name="checkBox" class="checkBox" id="check${vo.no }" value="${vo.path }">
	       		</div>
		        <div class="innerDiv" onclick="
	            	<c:if test="${vo.type == 'folder' }">inFolder('${vo.type }', '${vo.path }');</c:if>
			        <c:if test="${vo.type != 'folder' }">download('${vo.path}');</c:if>">
		        	<div class="fileImg">
		            	<img src="${path }/img/${vo.type }.png">
		            </div>
		        </div>
			</div>
			<div class="infoDiv">
	            <div class="fileName" id="fileName${vo.no }"
	            	onmouseenter="editView('${vo.no }');" onmouseleave="editClose('${vo.no }');">
	            	<div class="boxOn" id="boxOn${vo.no }">
	            		<input type="text" name="name${vo.no }" class="reNameBox" value="${vo.name }" spellcheck="false">
	            	</div>
	            	<div class="boxOff" id="boxOff${vo.no }">
		                <span class="nameSpn" title="${vo.name }">${name }</span>
		                <img src="${path }/img/pen.png" id="editName${vo.no }" onclick="reName('${vo.no}', '${vo.name }');"
		                	onmouseenter="editLineOn('${vo.no }');" onmouseleave="editLineOff('${vo.no }');" title="이름 변경">
	            	</div>
	            </div>
				<form name="editFileName" id="nameForm${vo.no }" method="post" action="editFileName.do">
					<input type="hidden" name="editName">
					<input type="hidden" name="path" value="${vo.path }">
					<input type="hidden" name="upPath" value="${vo.upPath }">
                </form>
	            <div class="fileSize">
	                <span class="fileSizeSpn">
	                <c:if test="${vo.type != 'folder' }">
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