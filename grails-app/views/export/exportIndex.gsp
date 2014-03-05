<html>
	<head>
  	<title>The Calderdale And Huddersfield NHS Foundation Trust Stroke System</title>
		<meta name="layout" content="main" />

		
	</head>
  <body>
  	<div id="pageBody">
	  	
			<sec:ifLoggedIn>
			</br>
				<h1>Exports :</h1>
				<h2>
					<p>This is a work in progress ....</p>
				</h2>
				
				<h1>SINAP</h1>
			
				<g:formRemote id="sinapExport" class="validatedForm" name="sinapExport"  
							asynchronous="false"  url="[controller:'export',action:'sinap']" 
							update="sinapExportResults"   >
						
				<g:actionSubmit  controller="export" action="sinap" value="Export Data" />
				
				<div id="sinapExportResults">
				
				</div>
				</g:formRemote>
				
				
				
			</sec:ifLoggedIn>
		</div>
	</body>
</html>