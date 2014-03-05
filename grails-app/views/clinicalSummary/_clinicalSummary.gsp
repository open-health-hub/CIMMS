<div class="pull-left sidebar" >
    
    <div class="sidebar-head">	    
		<h4>Clinical Summary</h4>
    </div>
    
    <tmpl:/templates/patientInfo careActivityInstance=${careActivityInstance} page="clinicalSummary"/>
</div>

<div id="clinicalSummaryForm" class="data-entry-narrow">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>

	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>

		<div class="page-header">
			<h1>Clinical summary</h1>
		</div>

		<div class="question-block">
			<div class="question-alt">
				<label>What was the Oxfordshire Community Stroke Project (OCSP)
					classification ?</label>
			</div>
			
			<div class="answer-block">			
				<g:buttonRadioGroup
					class="answer-single-column"
					name="classification" labels="['TACS','PACS','LACS', 'POCS']"
					values="['TACS','PACS', 'LACS', 'POCS']" spans="[5,5,5, 5]"
					bind="clinicalSummary" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
       		</div>
		</div>


		<div class="clearfix spacer-1"></div>
		
		<div class="question-block">
			<div class="question-alt">
				<label>What was the patient's worst level of consciousness in the
				first 7 days following initial admission for stroke?</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class=""
					class="answer-single-column"
					name="worstLevelOfConsciousness"
					labels="['Fully conscious','Drowsy','Semi-conscious','Unconscious' ]"
					values="['fully','drowsy','semi','unconscious']" spans="[6,6,6,6]"
					bind="clinicalSummary" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>
		
		<div class="clearfix spacer-1"></div>
		
		<div class="question-block">
			<div class="question-alt">
				<label>Did the patient develop a urinary tract infection in the
					first 7 days following initial admission for stroke as defined by having a positive
					culture or clinically treated?</label>
			</div>
		
			<div class="answer-block">
				<g:buttonRadioGroup 
					class="answer"
					name="urinaryTractInfection" labels="['Yes','No','Not known' ]"
					values="['yes','no','notknown']" spans="[6,6,6]"
					bind="clinicalSummary" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>
		
		<div class="clearfix spacer-1"></div>
		
		<div class="question-block">
			<div class="question-alt">
				<label>Did the patient receive antibiotics for a newly acquired
					pneumonia in the first 7 days following initial admission for stroke?</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer"
					
					name="newPneumonia" labels="['Yes','No','Not known' ]"
					values="['yes','no','notknown']" spans="[6,6,6]"
					bind="clinicalSummary" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>
		<!-- Add the palliative care questions here -->

		<div class="clearfix spacer-1"></div>

		<div class="question-block">
			<div class="question-alt">
				<label>Has it been decided in the first 72 hours that the patient is for palliative care?</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer"
					
					name="palliativeCare" labels="['Yes','No' ]"
					values="['yes','no']" spans="[6,6,]"
					bind="clinicalSummary" value="" onEvent="change: setGlobalFlag('palliativeCareRefresh',true)">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
			
			<div 
				data-bind="visible: clinicalSummary.palliativeCare() === 'yes'">
				
			<div class="clearfix spacer-1"></div>
			
			<div class="question-block"
				data-bind="inError:'clinicalSummary.palliativeCareDate'">
				<div class="question-alt">
					<label>Date started palliative care</label>
				</div>
				<div class="answer-block ">
					<div class="answer value">
					<input type="text"  class="standardDatePicker date"
						data-bind="value: clinicalSummary.palliativeCareDate,
		                                      jqueryui: { widget:'datepicker',
		                                                 options:{dateFormat:'dd/mm/yy',
		                                                          maxDate: new Date,
		                                                          showOn: 'button',
		                                                          buttonImage: '/stroke/images/calendar-sharepoint.gif',
		                                                          buttonImageOnly: true } }, 
		                                      uniqueName: true " />
		           </div>
				</div>
			</div>
			
			<div class="clearfix spacer-1"></div>
			
			<div class="question-block">
				<div class="question-alt">
					<label>Is the patient on an end of life pathway?</label>
				</div>
				<div class="answer-block">
					<g:buttonRadioGroup class="answer"
						
						name="endOfLifePathway" labels="['Yes','No' ]"
						values="['yes','no']" spans="[6,6]"
						bind="clinicalSummary" value="">
						${it.radio}
						${it.label}
					</g:buttonRadioGroup>

				</div>
			</div>
		</div>
		</div>

	</div>
	<!--  end of container -->


	<!-- 
	<hr />
<h2>Debug</h2>

<div data-bind="text: ko.toJSON(clinicalSummaryViewModel)"></div>

 -->


</div>
<!--  end of clinical summary form -->

