<!DOCTYPE html>
<sec:ifLoggedIn>
<html>
	<head>
		<meta name="layout" content="iframe" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
				
		<r:require modules="bootstrap"/>
	</head>
  
  	<body>
	  	<div id="wrap">
				
			<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
			<g:set var="strokePatient" value="${ (careActivityInstance?.imaging?.scan?.diagnosisCategory?.description == 'TIA' || careActivityInstance?.imaging?.scan?.diagnosisCategory?.description == 'Other') ? '0' : '1'}"/>
			<g:hiddenField name="strokePatient" value="${strokePatient}"></g:hiddenField>
			<div class="outerContainer">
					  
				<tmpl:/templates/navbar />									  
			
				<div class="container-fluid" >

				</div>
			</div>
				
			<div id="push"></div>
		</div>
		
		<tmpl:/templates/footer />
		

	</body>
</html>
</sec:ifLoggedIn><sec:ifNotLoggedIn>
<html>
	<head>
		<META http-equiv="refresh" content="0;URL=${resource(dir:'login',file:'login.gsp')}">
	</head>
</html>
</sec:ifNotLoggedIn>