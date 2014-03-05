<div class="pull-left sidebar" >
    
    <div class="sidebar-head">	    
		<h4>Onset/Admission</h4>
    </div>

	<tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="admission"/>
	    
    <ul class="nav nav-list iconised-list">    
	    
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-list-alt"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a id="__patientHistory_01" href="#" onclick="$('#tabs').tabs('select',1); $('#admissionAccordion').accordion('activate',0) ;">Patient History</a>
	    	</span>
	    </li>
	    
	    	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-time"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a id="__strokeOnset_01" href="#" onclick="$('#tabs').tabs('select',1); $('#admissionAccordion').accordion('activate',1) ;">Stroke Onset</a>
	    	</span>
	    </li>
	    	    
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-zoom-in"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a id="__premorbidAssessments_01" href="#" onclick="$('#tabs').tabs('select',1); $('#admissionAccordion').accordion('activate',2) ;">Pre-morbid Assessments</a>
		    </span>
	    </li>
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class=" icon-check"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<g:link controller="ssnapPreview" action="search" params="[hsi: hsi]">SSNAP preview</g:link>
		    </span>
	    </li>
	    
    </ul>
</div>

<div id="admissionAccordion" class="data-entry-narrow">
    <h3><a href="#">Patient History
    </a></h3>
    <div id='patient-history-section'></div>
    <h3><a href="#">Stroke Onset</a></h3>
    <div id='stroke-onset-section'></div>
    <h3><a href="#">Pre-morbid Assessments</a></h3>
    <div id='pre-morbid-assessment-section'></div>
</div>