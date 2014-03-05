<div id="otherFeatureForm">
	<g:render template="/templates/sectionHead"></g:render>
	<g:hiddenField name="careActivityId" value="${careActivityInstance.id}"></g:hiddenField>
	<div class="containPanel"
		data-bind="css : {changed: tracker().somethingHasChanged }">
		<div data-bind="html: errorsAsList"></div>
		<div id="otherFeature">

			<div class="page-header">
				<h1>Other features</h1>
			</div>


			<h3 class="headspace bottomspace">Level Of Consciousness?</h3>
		</div>


		<div class="question-block">
			<div class="question-alt">Stimulation</div>

			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="locStimulation"
					labels="['Keenly responsive','Arousal' ,'Repeated stimulation only', 'Unresponsive', 'Unknown']"
					values="['keen','arousal', 'repeated','unresponsive']"
					spans="[5,5,5,5]" bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="question-block">
			<div class="question-alt">
				<label>Answers questions</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="locQuestions"
					labels="['Both correct','One correct' ,'Neither', 'Not known']"
					values="['both','one', 'neither','unknown']" spans="[5,5,5,5]"
					bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>


		<div class="question-block">
			<div class="question-alt">
				<label>Perform tasks</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="locTasks"
					labels="['Both correct','One correct' ,'Neither', 'Not known']"
					values="['both','one', 'neither','unknown']" spans="[5,5,5,5]"
					bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>


		<div class="clearfix"></div>


		<div class="question-block">
			<div class="question-alt">
				<label>Best Gaze</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="bestGaze"
					labels="['Normal', 'Partial gaze palsy','Forced deviation','Not known']"
					values="['normal','partial','forced','unknown']" spans="[6,6,6,6]"
					bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="question-block">
			<div class="question-alt">
				<label>Dysarthria</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="dysarthria"
					labels="['Normal','Mild-to-moderate' , 'Severe', 'Not known']"
					values="['normal','mild', 'severe', 'unknown']" spans="[6,6,6,6]"
					bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="question-block">
			<div class="question-alt">
				<label>Aphasia</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="aphasia"
					labels="['Normal','Mild-to-moderate' , 'Severe',  'Global', 'Not known']"
					values="['normal','mild', 'severe', 'global', 'unknown']"
					spans="[6,6,6,6,6]" bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>
		<div class="question-block">
			<div class="question-alt">
				<label>Hemianopia</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="hemianopia"
					labels="['No loss','Partial' , 'Complete', 'Bilateral', 'Not known']"
					values="['none','partial', 'complete', 'bilateral', 'unknown']"
					spans="[6,6,6,6,6]" bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>
		<div class="question-block">
			<div class="question-alt">
				<label>Inattention</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="inattention"
					labels="['No abnormality','Single Sense Hemi-inattention' , 'Profound Hemi-inattention', 'Not known']"
					values="['normal','single', 'profound', 'unknown']"
					spans="[6,6,6,6]" bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="question-block">
			<div class="question-alt">
				<label>Limb ataxia</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="limbAtaxia"
					labels="['Absent','Single limb' , 'Two Limbs', 'Not known']"
					values="['yes','single', 'two', 'unknown']" spans="[6,6,6,6]"
					bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>

		<div class="clearfix"></div>

		<div class="question-block">
			<div class="question-alt">
				<label>Other signs at presentation</label>
			</div>
			<div class="answer-block">
				<g:buttonRadioGroup class="answer" name="other"
					labels="['Yes','No' ]" values="['yes','no']" spans="[6,6 ]"
					bind="clinicalAssessment" value="">
					${it.radio}
					${it.label}
				</g:buttonRadioGroup>
			</div>
		</div>
		<div class="clearfix"></div>


		<div id="otherTextSection"
			data-bind="visible: clinicalAssessment.other()=='yes'">
			<div class="question-block">
				<div class="question-alt">
					<label>please specify</label>
				</div>
				<div class="answer-block">
					<div class="answer"
						data-bind="inError:'clinicalAssessment.otherText'">
						<g:textField style="width:400px"
							data-bind="value: clinicalAssessment.otherText" name="otherText"></g:textField>

					</div>
				</div>
			</div>

		</div>
	</div>
	<g:render template="/templates/sectionFoot"></g:render>
</div>


