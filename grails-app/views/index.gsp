<html>
<head>
  	<title>CIMSS</title>
	<meta name="layout" content="reportTemplate" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<link rel='stylesheet' href="${resource(dir: 'css/jquery', file: 'jquery.dataTables.css')}" type="text/css"/>
	
	<r:require modules="bootstrap"/>
	<r:require modules="jquery-ui-dev"/>	

	<style type="text/css">
	 body {
 		background-color: rgb(15, 77, 146);
 		padding-top: 40px;
        padding-bottom: 40px;
	 }
	 .menu-panel {
	 	max-width: 300px;
	    padding: 19px 29px 29px;
	    margin: 0 auto 20px;
	    background-color: #fff;
	    border: 1px solid #e5e5e5;
	 }
	 .hero-unit {
	    max-width: 300px;
 		margin: 0 auto 20px;
 		background-color: rgb(15, 77, 146);
 		color: white;
 		font-family: 'Open Sans','Lucida Grande',Verdana,'Helvetica Neue',Helvetica,Arial,sans-serif;
 				
 	 } 	
 	 .hero-unit h1, .hero-unit p {
 	  font-weight: normal;
 	  text-align:center
 	 } 
 	 .nav-list > .active > a, .nav-list > .active > a:hover {
    	color: rgb(255, 255, 255);
    	text-shadow: 0px -1px 0px rgba(0, 0, 0, 0.2);
    	background-color: #5A99DE;
	}
	</style>
</head>
 <body> 

	<div class="container">
    	<div class="hero-unit">
    		<h1>cimss</h1>
    		<p>Stroke system</p>
    		<p>
    
    		</p>
    	</div>
    	<div class="menu-panel">			
				
    			
    		    <ul class="nav nav-list">
    		    
    				<li class="nav-header">CIMSS Options</li>
    				
    				<li class="active"><g:link controller="case" action=index">CIMSS</g:link></li>
    				
    				<li class="divider"></li>
    				
    				<li><g:link controller="therapistAdmin">Administer Therapists</g:link></li>
    				
    				<li class="divider"></li>
    				
    				<li><a href="${request.contextPath}/therapy/TherapyApp/index.html" >Perform Therapy Session</a></li>
    				
    			</ul>	
				
		
		</div>
    </div>
		
	
</body>
</html>							