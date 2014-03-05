
<div class="patient-status dataStatus">
	<div class="accordion" id="accordion_${page}">
		<div class="accordion-group">
			<div class="accordion-heading info-panel-header">
				<a class="accordion-toggle" data-toggle="collapse"
					data-parent="#accordion_${page}" href="#collapseOne_${page}"> <i
					class="icon-chevron-down"></i> Patient Info
				</a>
			</div>
			<div id="collapseOne_${page}" class="accordion-body collapse in">
				<div class="accordion-inner info-panel-body">

					<div class="clearfix">
						<span class="pull-left">Care start</span>
						<span class="pull-right" data-bind="text: dataStatus().patientInfo().careStart()">
							
						</span>
					</div>
					<div class="clearfix">
						<span class="pull-left">D-O-B</span>
						<span class="pull-right" data-bind="text: dataStatus().patientInfo().dob()">
							
						</span>
					</div>
					<div class="clearfix">
						<span class="pull-left">Onset</span>
						
						<span class="pull-right" data-bind="text: dataStatus().patientInfo().onset()">							
						</span>
					</div>
					<div class="clearfix">
						<span class="pull-left">In-patient @ onset</span>
						<span class="pull-right" data-bind="text: dataStatus().patientInfo().inpatientAtOnset()">							
						</span>
					</div>
					<div class="clearfix">
						<span class="pull-left">Arrival</span>
						<span class="pull-right" data-bind="text: dataStatus().patientInfo().arrival()">
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
