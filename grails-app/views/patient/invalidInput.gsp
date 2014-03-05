<html>
	<head>
		<meta name="layout" content="iframe" />				
	</head>
  <body>
  	<div id="pageBody">
	  	<sec:ifNotLoggedIn>
				<h1>Welcome to the Calderdale And Huddersfield NHS Foundation Trust Stroke System:</h1>
				<h2>
					<p>Please sign in to proceed</p>
				</h2>
			</sec:ifNotLoggedIn>
			<br/>
			<sec:ifLoggedIn>
				<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
				<div class="outerContainer">
					<div id="invalidData">				
						<p>Changing admission/discharge details would result in invalid stroke data</p>
						<br></br>
						<br></br>
						<p>Current data is for the patient with hospital number ${ patientInstance.hospitalNumber}</p>
						<br></br>
						<p>${dataDiscrepancy.before}</p>
						<br></br>
						<p>Provided data is</p>
						<br></br>
						<p>${dataDiscrepancy.after}</p>
						<br></br>
						<p>Changing the data would create the following errors :- </p>
						<br></br>
						<g:renderErrors bean="${careActivityInstance}" />
						<br></br>
						<strong> Erroneous or missing parameters</strong>
<ul>
  <g:each var="msg" in="${ errMsgs}">
    <li> ${msg}</li>
  </g:each>
</ul>
						<br></br>
						<p>Please contact your system administrator</p>
					</div>				
				</div>				
			</sec:ifLoggedIn>
	</div>
	
		
		
		
		
	</body>
</html>