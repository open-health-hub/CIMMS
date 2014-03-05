<div class="pull-left sidebar" >
    
    <div class="sidebar-head">	    
		<h4>Discharge</h4>
    </div>
    
    <tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="discharge"/>
    
    <ul class="nav nav-list iconised-list">    
	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-arrow-right"></i>
	    	</span>
	    	<span class="iconised-list-text">
		    	<a href="#" id="on_discharge_link" onclick="$('#tabs').tabs('select',1); $('#dischargeAccordion').accordion('activate',0) ;">On Discharge</a>
		    </span>
	    </li>
	    	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-check"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a href="#" id="pre_discharge_link" onclick="$('#tabs').tabs('select',1); $('#dischargeAccordion').accordion('activate',1) ;">Pre-discharge</a>
	    	</span>
	    </li>
	    
	    	    
	    <li class="iconised-list-item">
	    	<span class="iconised-list-icon">
	    		<i class="icon-list"></i>
	    	</span>
	    	<span class="iconised-list-text">
	    		<a href="#" onclick="$('#tabs').tabs('select',1); $('#dischargeAccordion').accordion('activate',2) ;">Assessments</a>
	    	</span>
	    </li>
	    	    	    
    </ul>
</div>
<div id="dischargeAccordion" class="data-entry-narrow">
    <h3><a href="#">On Discharge</a></h3>
    <div id='post-discharge-section'></div> 
    <h3><a href="#">Pre Discharge</a></h3>
    <div id='pre-discharge-section'></div>
    <h3><a href="#">Assessments</a></h3>
    <div id='discharge-assessments-section'></div>   
</div>