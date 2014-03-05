<div class="pull-left sidebar" >
    
    <div class="sidebar-head">	    
		<h4>Therapy</h4>
    </div>

	<tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="therapy"/>

    
    <ul class="nav nav-list iconised-list">    
	    
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-inbox"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a href="#" id="occupational_therapy_link" onclick="$('#tabs').tabs('select',2); $('#therapyAccordion').accordion('activate',0) ;">Occupational Therapy</a>
	    	</span>
	    </li>
	    
	    	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-comment"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a href="#" onclick="$('#tabs').tabs('select',1); $('#therapyAccordion').accordion('activate',1) ;">Speech and Language Therapy</a>
	    	</span>
	    </li>
	    	    
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-resize-small"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" onclick="$('#tabs').tabs('select',1); $('#therapyAccordion').accordion('activate',2) ;">Physiotherapy</a>
		    </span>
	    </li>
	    
		<li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-map-marker"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" onclick="$('#tabs').tabs('select',1); $('#therapyAccordion').accordion('activate',3) ;">MDT Rehabilitation Goals</a>
		    </span>
	    </li>	    

		<li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-cog"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" onclick="$('#tabs').tabs('select',1); $('#therapyAccordion').accordion('activate',4) ;">Post Stroke Assessments</a>
		    </span>
	    </li>	    

		<li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-book"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" onclick="$('#tabs').tabs('select',1); $('#therapyAccordion').accordion('activate',5) ;">Therapy Summary</a>
		    </span>
	    </li>	    

    </ul>
</div>
<div id="therapyAccordion" class="data-entry-narrow">
    <h3><a href="#">Occupational Therapy</a></h3>
    <div id='occupational-therapy-section'></div>
    <h3><a href="#">Speech and Language Therapy</a></h3>
    <div id='speech-and-language-therapy-section'></div>
    <h3><a href="#">Physiotherapy</a></h3>
    <div id='physiotherapy-section'></div>
    <h3><a href="#">MDT Rehabilitation Goals</a></h3>
    <div id='nurse-led-therapy-section'></div>
    <h3><a href="#">Post Stroke Assessments</a></h3>
    <div id='baseline-assessments-section'></div>
    <h3><a href="#">Therapy Summary</a></h3>
    <div id='therapy-summary-section'></div>  
</div>

