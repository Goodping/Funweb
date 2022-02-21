<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/join.jsp</title>
<script type="text/javascript">

	var isCheckId = false;
	var isCheckPass = false;
	
	function checkDuplicateId() 
	{	
		isCheckId = true;
	}
	
	function checkConfirmPasswd(pass2) 
	{
		
		var span_pass = document.getElementById("span_pass");
	
		var pass = document.fr.pass.value;
	
		if(pass == pass2) {
			span_pass.innerHTML = " 비밀번호 일치";
			span_pass.style.color = "GREEN";
		
			isCheckPass = true;
		} 
		else
		{ 
			span_pass.innerHTML = " 비밀번호 불일치";
			span_pass.style.color = "RED";
		
			isCheckPass = false;
		}

	}

	function checkForm() 
	{
		if(!isCheckId) { 
    		alert("아이디 중복확인 필수!");
    		document.fr.btn.focus();
    		return false;
    	}
		else if(!isCheckPass)
		{ 
			alert("비밀번호를 확인하세요");
			document.fr.pass2.focus();
			return false;
		}
	
	}
	
	
</script>

<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"/>
		<!-- 헤더 들어가는곳 -->
		  
		<!-- 본문들어가는 곳 -->
		  <!-- 본문 메인 이미지 -->
		  <div id="sub_img_member"></div>
		  <!-- 왼쪽 메뉴 -->
		  <nav id="sub_menu">
		  	<ul>
		  		<li><a href="#">Join us</a></li>
		  		<li><a href="#">Privacy policy</a></li>
		  	</ul>
		  </nav>
		  <!-- 본문 내용 -->
		  <article>
		  	<h1>Join Us</h1>
		  	<form action="joinPro.jsp" method="post" id="join" name="fr" onsubmit="return checkForm()">
		  		<fieldset>
		  			<legend>Basic Info</legend>
		  			<label>User Id</label>
		  			<input type="text" name="id" class="id" id="id" required="required" readonly="readonly">
		  			<input type="button" value="dup. check" class="dup" id="btn" onclick="window.open('checkId.jsp','check','width=500,height=500')">
		  			<span id="idcheck"></span>
		  			<br>
		  			
		  			<label>Password</label>
		  			<input type="password" name="pass" id="pass" required="required"><br> 			
		  			
		  			<label>Retype Password</label>
		  			<input type="password" name="pass2" onblur="checkConfirmPasswd(this.value)" required="required">
		  			<span id="span_pass"></span>
		  			
		  			<br>
		  			
		  			
		  			<label>Name</label>
		  			<input type="text" name="name" id="name" required="required" ><br>
		  			
		  			<label>E-Mail</label>
		  			<input type="email" name="email" id="email" required="required"><br>
		  			
		  			<label>Mobile Phone Number</label>
		  			<input type="text" name="mobile" required="required" ><br>
		  		</fieldset>
		  		
		  		<fieldset>
		  			<legend>Optional</legend>
		  			<label>Address</label>
		  			<input type="text" name="address" ><br>
		  			<label>Phone Number</label>
		  			<input type="text" name="phone" ><br>

		  		</fieldset>
		  		<div class="clear"></div>
		  		<div id="buttons">
		  			<input type="submit" value="Submit" class="submit">
		  			<input type="reset" value="Cancel" class="cancel">
		  		</div>
		  	</form>
		  </article>
		  
		  
		<div class="clear"></div>  
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


