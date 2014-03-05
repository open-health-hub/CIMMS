<html>
<head>
  	<title>CIMSS Therapy</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<link rel='stylesheet' href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}" type="text/css"/>
	<link rel='stylesheet' href="${resource(dir:'css', file:'cimss_admin.css')}" type="text/css"/> 
	
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>	

	<style>
		#clock {
			font-size: 52px;
			line-height: 56px; 
			color: #fff; 
			font-weight: bold; 
			background-color: rgb(15, 77, 146); 
			border: 10px solid #efefef;
			border-top:  10px solid #ddd;
			border-bottom: 10px solid #ddd;
			padding: 8px 12px; 
			text-shadow: 2px 2px #333;
			margin: 5px;
		}
		#clock-control {
			border: 0px solid #ccc;
			padding: 5px;
		}
	</style>
</head>
 <body >  	
  	
  	
  	
  	<tmpl:navBar/>
    
	<div class="container">
			<%--sec:ifLoggedIn --%>
			<div class="page-header">
    			<h3>Therapy <small> therapy timer</small></h3>
    		</div>			
			<g:if test="${flash.message}">
				<div class="alert alert-success">${flash.message}</div>
			</g:if>			
			<g:if test="${flash.error_message}">
				<div class="alert alert-error">
				${flash.error_message}
				</div>
			</g:if>
			
						
			<g:form action="therapyFinished">
				
				<div class="row">
					<div class="span6 clearfix">	
							
		                    <div id="clock">00:00:00</div>	                    	
		                    
		                    <input type="hidden" name="therapyDuration" id="therapyDuration" value="0"></input>
					
							<div id="clock-control" class="pull-right">
								<a href="#" class="btn btn-success" id="clockStartStop">Start</a>
							</div>						
					</div>				
					<div class="span6">
						<div class="control-group">
							<div class="controls">
								<button type="submit" class="btn btn-primary">Proceed</button>
							</div>
						</div>
					</div>
				</div>
			</g:form>
			

			<%-- /sec:ifLoggedIn --%>
		</div>
		
		<script type="text/javascript" src="${resource(dir:'js/application/therapy', file:'therapy_timer.js')}" >
		</script>
	</body>
</html>