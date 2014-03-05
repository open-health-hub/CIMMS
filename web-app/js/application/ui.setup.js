function setData(object, data) {
	if (data) {

		for ( var propertyName in object) {
	
			if (ko.isObservable(object[propertyName])) {
				if (typeof object[propertyName]() !== 'object') {
					if (data[propertyName] !== null) {
						object[propertyName](data[propertyName]);
					} else {
						object[propertyName](null);
					}
				} else if (object[propertyName]() === null) {
					object[propertyName](data[propertyName]);
				} else {				
					setData(object[propertyName](), data[propertyName]);
					//object[propertyName]().set(data[propertyName]);
				}
			} else {
				if (typeof object[propertyName] !== 'object' &&
						typeof object[propertyName] !== 'function') {
					if (data[propertyName] !== null) {
						object[propertyName] = data[propertyName];
					} else {
						object[propertyName] = null;
					}
				} else if (object[propertyName] === null) {
					if (data[propertyName] !== null) {
						object[propertyName] = data[propertyName];
					} else {
						object[propertyName] = null;
					}
				} else if (typeof object[propertyName] !== 'undefined' &&
						typeof object[propertyName] !== 'function') {
					setData(object[propertyName], data[propertyName]);
				}
			}
		}
	}

}

//timeMask
function setUpTimeMask(scope) {
	$(".time").mask("99:99");
	$("#riskFactorAlcohol").mask("9?99");
	$("#preMorbidModifiedRankinScore").mask("9");
	$("#baselineModifiedRankinScore").mask("9");
	$("#dischargeModifiedRankinScore").mask("9");
	
	
	$("#preMorbidManualBarthelScore").mask("9?9");
	$("#preMorbidCalculatedBarthelScore").mask("9?9");
	$("#baselineManualBarthelScore").mask("9?9");
	$("#baselineCalculatedBarthelScore").mask("9?9");
	$("#dischargeManualBarthelScore").mask("9?9");
	$("#dischargeCalculatedBarthelScore").mask("9?9");
	
	
	
	
	
	
	$("#mustScore").mask("9");
	//$("#riskFactorAlcohol").mask("9?9", {completed:function(){alert("You typed the following: "+this.val());}});
	$("#numberOfSocialServiceVisits").mask("9?9");

	$("#nihssScoreAt24Hours").mask("9?9");

}


//Date picker
function setUpDatePicker(scope) {
	$('.standardDatePicker', scope).datepicker({
		dateFormat : 'dd/mm/yy',
		maxDate : new Date(),
		showOn : 'button',
		buttonImage : '/stroke/images/Apps-evolution-calendar-icon.png',
		buttonImageOnly : true
	});
}



//Checkboxes
function setCheckboxesAsButtons(scope) {
	// sets up all check boxes as buttons
	$(':checkbox:not(.simpleCheckBox)', scope).button({
		text : true
	}).change(function() {
		$(this).button("option", {
			icons : {
				primary : this.checked ? 'ui-icon-check' : ''
			}
		});
	});
	$(':checkbox:checked', scope).button("option", "icons", {
		primary : 'ui-icon-check'
	});	
//	$('.simpleCheckBox', scope).button( "destroy" );
}


function setCheckboxes(scope) {
	setCheckboxesAsButtons(scope);
}


//RadioGroups
function setRadioGroupAsButtons(scope) {
	// makes radio groups display as buttons

	$(':radio', scope).button({
		text : true
	}).buttonset();
	//enables icons on checked radio groups
	$(':radio:checked', scope).button("option", "icons", {
		primary : 'ui-icon-check'
	});
	
	$('.toggleButton',scope).button("destroy", "option", "icons", {
		primary : null, secondary : null
	});
	$('.toggleButtonSet', scope).buttonset({
		text: false, icons:{primary: null, secondary: null}
	});
	$(':radio.toggleButton:checked',scope).css("border","1px solid red !important");
		
}

function setRadioGroups(scope) {
	setRadioGroupAsButtons(scope);
}

function setDatePicker(scope) {
	setUpDatePicker(scope);
}

function setTimeEntry(scope) {
	setUpTimeMask(scope);
}



function refreshRadioGroupAsButtons(scope) {
	// makes radio groups display as buttons
	//alert("Called");
	//console.log("refreshRadioGroupAsButtons called - scope " + scope + " ...");
	$(':radio', scope).button("refresh");
	//enables icons on checked radio groups
	$(':radio', scope).button("option", "icons", {
		primary : null
	});
	$(':radio:checked', scope).button("option", "icons", {
		primary : 'ui-icon-check'
	});
	
	$('.toggleButton', scope).button("refresh");
	$('.toggleButton',scope).button("destroy", "option", "icons", {
		primary : null, secondary : null
	});
	$('.toggleButtonSet', scope).buttonset({
		text: false, icons:{primary: null, secondary: null}
	});
	$(':radio.toggleButtonSet:checked',scope).css("border","1px solid red !important");
	
}


// TimeEntry
function setUpTimeEntry(scope) {
	$('.time', scope).timeEntry({
		show24Hours : true,
		spinnerImage : '/stroke/images/spinnerUpDown-blue.png',
		spinnerSize : [ 15, 16, 0 ],
		spinnerIncDecOnly : true
	});
}

//TimePicker addon
function setUpTimePicker(scope) {
	$('.time', '#admissionForm').timepicker({
		showButtonPanel : false
	});
}




//default setup 
function defaultUISetup(scope) {
	if ($.browser.mozilla){
		$("form").attr("autocomplete", "off");
	}
	setCheckboxesAsButtons(scope);
	setRadioGroupAsButtons(scope);
	setDatePicker(scope);
	setTimeEntry(scope);

	$('button').button();

	$('.scroller').css('top', $(document).scrollTop());

	
}








