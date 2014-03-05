<html>
	<head>
  	<title>The Calderdale And Huddersfield NHS Foundation Trust Stroke System</title>
		<meta name="layout" content="main" />
		
		<r:require modules="jquery-jdmenu"/>
		<r:layoutResources/>
	
		
	</head>
  <body>  	
  	<div Id="pageBody">
	  	<sec:ifNotLoggedIn>
				<h1>Welcome to the Calderdale And Huddersfield NHS Foundation Trust Stroke System:</h1>
				<h2>
					<p>Please sign in to proceed</p>
				</h2>
			</sec:ifNotLoggedIn>
			<br/>
			<sec:ifLoggedIn>
			</br>
				<h1>CIMMS Reporting</h1>
				<h2>
					<p>Select a menu option to continue</p>
				</h2>
			</sec:ifLoggedIn>
		</div>
	</body>
</html>