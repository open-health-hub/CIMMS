<html>
	<head>
		<meta name="layout" content="iframe" />		
		<r:require modules="bootstrap"/>
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
				<g:set var="strokePatient" value="${ (careActivityInstance?.imaging?.scan?.diagnosisCategory?.description == 'TIA' || careActivityInstance?.imaging?.scan?.diagnosisCategory?.description == 'Other') ? '0' : '1'}"/>
				<g:hiddenField name="strokePatient" value="${strokePatient}"></g:hiddenField>
				<div class="outerContainer">
				
				
				  <div id="loading"> 
				  	<img src="/stroke/images/ajax-loader.gif"></img>
				  	 
				  	<p>Loading data please wait .....</p>
				  	
				  </div>
				  
				  <div style="position: absolute; right: 20px; top: 25px;" >
						<p><a id="helpAbout" style="text-decoration: none;">
						<img src="/stroke/images/skin/information.png"  alt="Help and Information"></img>&nbsp;More info ...</a>
						</p>
					</div>
				  
					<div id="tabsx" style="display:nonex">
				
						<ul >
							<li ><a href="#admission-tab">Onset/ Admission</a></li>
							<li ><a href="#admission-assessment-tab">Admission Assessment</a></li>						
						    <li ><a href="#therapy-tab"><p>Therapy</p></a></li>  
						    <li><a href="#imaging-tab">Imaging</a></li>							
							<li><a href="#thrombolysis-tab">Thrombolysis</a></li>
							<li><a href="#treatments-tab">Treatment</a></li>
							<li><a href="#clinical-summary-tab">Clinical Summary</a></li> 
							<li><a href="#discharge-tab">Discharge Plan</a></li>	
							<li><a href="#reportTab">Reports</a></li>
						</ul>
						

						<div id="reportTab"></div>
						<div id="admission-tab">
							<g:render template="/dataStatus/dataSummary"></g:render>
							<g:render template="/admission/admission"></g:render>
						</div>				
						<div id="admission-assessment-tab">
							<g:render template="/dataStatus/dataSummary"></g:render>
							<g:render template="/assessment/assessment"></g:render>
						</div>
						<div id="therapy-tab">
                            <g:render template="/dataStatus/dataSummary"></g:render>
                            <g:render template="/therapy/therapy"></g:render>
                        </div>
						
							
						<div id="imaging-tab">
							<g:render template="/dataStatus/dataSummary"></g:render>
						</div>	
									
						<div id="treatments-tab">
						<g:render template="/dataStatus/dataSummary"></g:render>

						</div>	
						<div id="thrombolysis-tab">
						<g:render template="/dataStatus/dataSummary"></g:render>
							
						</div>	
						<div id="clinical-summary-tab">
                            <g:render template="/dataStatus/dataSummary"></g:render>
                        </div>
						
						
						<div id="discharge-tab">							
						<g:render template="/dataStatus/dataSummary"></g:render>
						<g:render template="/discharge/discharge"></g:render>
						</div>
					</div>
				</div>	
						
			</sec:ifLoggedIn>
	</div>
	<div class="hr">
	</div>		
	<%-- div id="statusbar" style="width: 960px; margin: 0px auto; padding: 4px 10px;">
	&nbsp;
	</div--%>
	
	<g:render template="/templates/helpAbout"></g:render>	
	<g:render template="/dataStatus/dialogs"></g:render>	
	
		
		
		
		
	</body>
</html>