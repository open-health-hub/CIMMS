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
					
					
					  <div id="loading"> 
					  	<img src="/stroke/images/ajax-loader.gif"></img>
					  	 
					  	<p>Loading data please wait .....</p>
					  	
					  </div>
					  					  
					  
					<tmpl:/templates/navbar />
									  
					<div class="container-fluid" >
	
							<div class="tab-content">
	
								<div class="tab-pane" id="reportTab"></div>
								<div class="tab-pane active" id="admission-tab">
									<g:render template="/dataStatus/dataSummary"></g:render>
									<g:render template="/admission/admission"  model="[hsi: careActivityInstance.hospitalStayId, careActivityInstance: careActivityInstance]"></g:render>
								</div>				
								<div class="tab-pane" id="admission-assessment-tab">
									<g:render template="/dataStatus/dataSummary"></g:render>
									<g:render template="/assessment/assessment" model="careActivityInstance: careActivityInstance]"></g:render>								
								</div>
								<div class="tab-pane" id="therapy-tab">
		                            <g:render template="/dataStatus/dataSummary"></g:render>
		                            <g:render template="/therapy/therapy"></g:render>
		                        </div>
								
									
								<div class="tab-pane" id="imaging-tab">
									<g:render template="/dataStatus/dataSummary"></g:render>
								</div>	
											
								<div class="tab-pane" id="treatments-tab">
								<g:render template="/dataStatus/dataSummary"></g:render>
		
								</div>	
								<div class="tab-pane" id="thrombolysis-tab">
									<g:render template="/dataStatus/dataSummary"></g:render>
									
								</div>	
								<div class="tab-pane" id="clinical-summary-tab">
		                            <g:render template="/dataStatus/dataSummary"></g:render>
		                        </div>
								
								
								<div class="tab-pane" id="discharge-tab">							
									<g:render template="/dataStatus/dataSummary"></g:render>
									<g:render template="/discharge/discharge"></g:render>
								</div>
							</div>
						</div>
					</div>	
							
				
		 <div id="push"></div>
		</div>
		
		<tmpl:/templates/footer />
		
		<g:render template="/templates/helpAbout"></g:render>	
		<g:render template="/dataStatus/dialogs"></g:render>	
	</body>
</html>
</sec:ifLoggedIn><sec:ifNotLoggedIn>
<html>
	<head>
		<META http-equiv="refresh" content="0;URL=${resource(dir:'login',file:'login.gsp')}">
	</head>
</html>
</sec:ifNotLoggedIn>