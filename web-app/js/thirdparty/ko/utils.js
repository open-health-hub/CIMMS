function changeTracker(objectToTrack, hashFunction) {    
	hashFunction = hashFunction || ko.toJSON;
    var lastCleanState = ko.observable(hashFunction(objectToTrack)); 
   
    
    var result = {
        somethingHasChanged : ko.dependentObservable(function() {

        if ( hashFunction(objectToTrack) != lastCleanState() ) {        	
        }
             return hashFunction(objectToTrack) != lastCleanState()
        }),
        markCurrentStateAsClean : function() {
            lastCleanState(hashFunction(objectToTrack));   
        }
    };  
    return function() { return result }
}

function getUniqueUrl(url) {
	return url + "/?cacheKey=" + new Date().getTime()
}


function isNumber(n) {
	  return !isNaN(parseFloat(n)) && isFinite(n);
}

function jsonCompare(obj1, obj2, _Q){
	_Q = (_Q == undefined)? new Array : _Q;
	 
	function size(obj) {
		var size = 0;
		for (var keyName in obj){
			if(keyName != null) size++;
		}
		return size;
	};
	 
	if (size(obj1) != size(obj2)) {
	//console.log('JSON compare - size not equal > '+keyName)
	};
	 
	var newO2 = jQuery.extend(true, {}, obj2);
	 
	for(var keyName in obj1){

		var value1 = obj1[keyName],
		value2 = obj2[keyName];
		 
		delete newO2[keyName];
		 
		if (typeof value1 != typeof value2 && value2 == undefined) {
			_Q.push(['missing', keyName, value1, value2, obj1])
		}else if (typeof value1 != typeof value2) {
			_Q.push(['diffType', keyName, value1, value2, obj1])
		}else{
		// For jQuery objects:
			if (value1 && value1.length && (value1[0] !== undefined && value1[0].tagName)) {
				if (!value2 || value2.length != value1.length || !value2[0].tagName || value2[0].tagName != value1[0].tagName) {
					_Q.push(['diffJqueryObj', keyName, value1, value2, obj1])
				}
			}else if(value1 && value1.length && (value1.tagName !== value2.tagName)){
				_Q.push(['diffHtmlObj', keyName, value1, value2, obj1])
			}else if (typeof value1 == 'function' || typeof value2 == 'function') {
				_Q.push(['function', keyName, value1, value2, obj1])
			}else if(typeof value1 == 'object'){
				var equal = Arcadia.Utility.CompareJson(value1, value2, _Q);
			}else if (value1 != value2) {
				_Q.push(['diffValue', keyName, value1, value2, obj1])
			}
		};
	}
	 
	for(var keyName in newO2){
		_Q.push(['new', keyName, obj1[keyName], newO2[keyName], newO2])
	}
	 
	/*
	*/
	return _Q;
	}


