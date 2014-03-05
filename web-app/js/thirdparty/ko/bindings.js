function getText(key) {
	
	var value = '';
	if (isNumber(key) ) {
		if ( key != -1) {
			value = key;
		} else {
			value = "Unknown";
		}
		value = '(' + value + ')';
	}
	return value;
}
ko.bindingHandlers.numberSelector = {
    init: function(element, valueAccessor, allBindingsAccessor) {
        var allBindings = allBindingsAccessor();
        var symbols = allBindings.symbols || 1;
        var min = isNumber(allBindings.min) ? min = allBindings.min : 1
        var allowUnknown = false;
        var unknownValue = -1;
        if ( isNumber(allBindings.unspecified)  ) {
        	allowUnknown = true;
        	unknownValue = allBindings.unspecified;
        	
        	var observable = valueAccessor()
        	if ( !observable ) {
        		observable(unknownValue);
        	}
        }
        
        $(element).addClass("numberSelector");
    	
    	
        // add one for cancel 
    	$("<span>").appendTo(element);
        
        for (var i = 0; i < symbols ; i++)
           $("<span>").appendTo(element);
        
        var firstSpan = $('span:first', element);
    
        // Handle mouse events on the numbers
        $("span", element).each(function(index) {
        	if(index != 0){
        		$(this).hover(
                        function() { $(this).prevUntil(firstSpan).add(this).addClass("hoverChosen") },
                        function() { $(this).prevUntil(firstSpan).add(this).removeClass("hoverChosen") }                
                    ).click(function() {
                        var observable = valueAccessor();  // Get the associated observable
                        observable(index -1 + min);        // Write the new rating to it subtracting 1 for the cancel span
                });
        	}else{
        		$(this).click(function() {
                        var observable = valueAccessor();  // Get the associated observable
                        if ( allowUnknown ) {
                        	observable(unknownValue);		// Set the value to the special "unknown" value                        
                        } else {
                        	observable(null) ;            	// Set the value to null
                        }
        		
                }).addClass("clear");
        	}
            
        });        
    },
    update: function(element, valueAccessor, allBindingsAccessor) {
        // Give the first x stars the "chosen" class, where x <= rating
    	 var allBindings = allBindingsAccessor();
    	 var min = 1;
         if (isNumber(allBindings.min)){
         	min = allBindings.min;
         }
    	
        var observable = valueAccessor();
        if(isNumber(observable())){
        	 $("span", element).each(function(index) {
        		 if(index > 0){
        			 $(this).toggleClass("chosen", index - 1 + min <= observable()); 
        		 }
             });
        }else{
        	$("span", element).each(function(index) {
        		if(index > 0){
        			$(this).toggleClass("chosen", false);
        		}
        		
            });
        }
       
    }    
};

ko.bindingHandlers.datepicker = {
	    init: function(element, valueAccessor, allBindingsAccessor) {
	        //initialize datepicker with some optional options
	        var options = allBindingsAccessor().datepickerOptions || {};
	        $(element).datepicker(options);

	        //handle the field changing
	        ko.utils.registerEventHandler(element, "change", function() {
	            var observable = valueAccessor();
	            //observable($(element).datepicker("getDate"));
	        });

	        //handle disposal (if KO removes by the template binding)
	        ko.utils.domNodeDisposal.addDisposeCallback(element, function() {
	            $(element).datepicker("destroy");
	        });
	        
	      
	        ko.bindingHandlers.value.init(element, valueAccessor,
					allBindingsAccessor);

	    },
	    update: function(element, valueAccessor) {
	        var value = ko.utils.unwrapObservable(valueAccessor());
	        //console.log("value", value);
	        //$(element).datepicker("setDate", value);
	        ko.bindingHandlers.value.update(element, valueAccessor);
	    }
	};


ko.bindingHandlers['inError'] = {
	    'update': function (element, valueAccessor,allBindingsAccessor,viewModel) {
	    	var value = ko.utils.unwrapObservable(valueAccessor() || '');
	    	var values = value.split(' ')
	    	var isInError = false;
	    	for (i=0;i<values.length;i++){
	    		if(viewModel.fieldInError(values[i])){
	    			isInError=true;
	    		}					
			}
	    	ko.bindingHandlers.css.update(element, ko.observable({errors:isInError}));	    
	    }
};

ko.bindingHandlers['uniqueNameForRadio'] = {
	    'init': function (element, valueAccessor) {
	        if (valueAccessor()) {
	        	var prev = $(element).prev().prev();
	        	var theName = $(prev).attr('name');
	            $(element).attr('name',theName);
	            if (ko.utils.isIe7)
	                element.mergeAttributes(document.createElement("<input name='" + element.name + "'/>"), false);
	            
	        }
	    }
};


ko.bindingHandlers['uniqueFor'] = {
	    'init': function (element, valueAccessor) {
	        if (valueAccessor()) {
	            $(element).attr('for',"ko_unique_" + (ko.bindingHandlers['uniqueId'].currentIndex));
	        }
	    }
};


ko.bindingHandlers['uniqueId'] = {
	    'init': function (element, valueAccessor) {
	        if (valueAccessor()) {
	            element.id = "ko_unique_" + (++ko.bindingHandlers['uniqueId'].currentIndex);           
	        }
	    }
};

ko.bindingHandlers['uniqueId'].currentIndex = 0;


(function($) {
	ko.bindingHandlers.refreshjqueryui = {
		update: function(element, valueAccessor, allBindingsAccessor, viewModel) {
			var widgetBindings = _getWidgetBindings(element, valueAccessor, allBindingsAccessor, viewModel);
			$(element).siblings().first().hide();
			$(element).siblings().first().remove();
			ko.bindingHandlers.jqueryui.update(element, valueAccessor,allBindingsAccessor, viewModel);
		}
	}
	function _getWidgetBindings(element, valueAccessor, allBindingsAccessor, viewModel) {
    
	    var value = valueAccessor();
	    var myBinding = ko.utils.unwrapObservable(value);
	    
	    if (typeof(myBinding) === 'string') {
	        // Short-form data-bind='jqueryui: "widget_name"'
	        // with no additional options
	        myBinding = {'widget': myBinding};
	    }
	    
	    var widgetName = myBinding.widget;
	    
	    return {
	        widgetName: widgetName
	    };
	}

})(jQuery);
			     
		
		



ko.bindingHandlers.enableButtons = {
	update : function(element, valueAccessor, allBindingsAccessor) {
		var value = ko.utils.unwrapObservable(valueAccessor());
		if (value && element.disabled)
			$(element).button("enable");
		else if ((!value) && (!element.disabled))
			$(element).button("disable");
		ko.bindingHandlers.enable.update(element, valueAccessor,
				allBindingsAccessor);
	}
}
	    

ko.bindingHandlers.checkBoxAsButton = {
	init : function(element, valueAccessor, allBindingsAccessor) {
		ko.bindingHandlers.checked.init(element, valueAccessor,
				allBindingsAccessor);
		$(element).data("valueAccessor", valueAccessor); // So we can fetch later    
	},
	update : function(element, valueAccessor, allBindingsAccessor) {
		var observable = valueAccessor();
		if (observable() == true) {
			$(element).attr('checked', true);
		} else {
			$(element).attr('checked', false);
		}
		$(element).button("option", {
			icons : {
				primary : element.checked ? 'ui-icon-check' : ''
			}
		});
		$(element).button("refresh");
		ko.bindingHandlers.checked.update(element, valueAccessor,
				allBindingsAccessor);
	}
}

ko.bindingHandlers.checkedWithToggle = {
    
    init: function(element, valueAccessor, allBindingsAccessor) {
    	
//    	console.log("in checkedWithToggle : init " + $(element).val() );
    	
    	var observable = valueAccessor();
    	var clickHandler = function() {
        	//console.log("in checkedWithToggle : Calling Click  " + $(element).val() );
        	//console.log("Comparing with "  + observable() );
            if ($(element).val() == observable()) {
               $(element).attr('checked', false);
               observable("null");
               //console.log("in checkedWithToggle : setting to false " + $(element).val() );
               
            }
        };
    	
    	$(element).data("valueAccessor", valueAccessor); // So we can fetch later    
    	
        $(element).click(clickHandler);   
       
        var changeHandler = function(event) {
        	var theSelector = "input[name=" + $(element).attr('name')+ "]";
	    	var theSelectorChecked = "input[name=" + $(element).attr('name')+ "]:checked";
	    	$(theSelector).each(function(){
	            $(element).button("option", {
		            icons: { primary: $(element).val() == $(theSelectorChecked).val() ? 'ui-icon-check' :  '' }
		        });
	        }); 
		};
		
        $(element).change(changeHandler);
		
		
        ko.bindingHandlers.checked.init(element, valueAccessor, allBindingsAccessor);    
    },
    update: function(element, valueAccessor, allBindingsAccessor) {
    	//console.log("in checkedWithToggle : update " + $(element).val() );
		/*
    	var observable = valueAccessor();
		if(observable() == $(element).val()){
			$(element).attr('checked', true);
		}else{
			$(element).removeAttr('checked');
		}*/
		
		
		
    	
    	
		var theSelector = "input[name=" + $(element).attr('name')+ "]";
		var theSelectorChecked = "input[name=" + $(element).attr('name')+ "]:checked";
		$(theSelector).each(function(){
			//console.log("in checkedWithToggle : each - setting icons " + $(element).val() );
			//console.log("in checkedWithToggle : each - setting icons " + $(theSelectorChecked).val() );
	        $(element).button("option", {
	            icons: { primary: $(element).val() == $(theSelectorChecked).val() ? 'ui-icon-check' :  '' }
	        }).button("refresh");
        });
        
		ko.bindingHandlers.checked.update(element, valueAccessor, allBindingsAccessor);
    }
}

