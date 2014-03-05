cimss.namespace("model.therapy").Barthel = function () {
	this.id  = ko.observable();
	this.manualTotal = ko.observable();
	this.bowels  = ko.observable();
	this.bladder  = ko.observable();
	this.grooming  = ko.observable();
	this.toilet  = ko.observable();
	this.feeding  = ko.observable();
	this.transfer  = ko.observable();
	this.mobility  = ko.observable();
	this.dressing  = ko.observable();
	this.stairs = ko.observable();
	this.bathing = ko.observable();
	
	this.detailScore = function (){
		return (isNumber(this.bowels())? parseInt(this.bowels(),10): 0) +
			(isNumber(this.bladder())? parseInt(this.bladder(),10): 0) +
			(isNumber(this.grooming())? parseInt(this.grooming(),10): 0) + 
			(isNumber(this.toilet())?  parseInt(this.toilet(),10): 0) +
			(isNumber(this.feeding())?  parseInt(this.feeding(),10): 0) +
			(isNumber(this.transfer())?  parseInt(this.transfer(),10): 0) +
			(isNumber(this.mobility())?  parseInt(this.mobility(),10): 0) +
			(isNumber(this.dressing())? parseInt(this.dressing(),10): 0) +
			(isNumber(this.stairs())? parseInt(this.stairs(),10): 0) +
			(isNumber(this.bathing())? parseInt(this.bathing(),10): 0) ;
		};
		
	this.printDetails = function (){
		if(typeof(console) !== 'undefined'){
			console.log(this.bowels());
			console.log(this.bladder());
			console.log(this.grooming());
			console.log(this.toilet());
			console.log(this.feeding());
			console.log(this.transfer());
			console.log(this.mobility());
			console.log(this.dressing());
			console.log(this.stairs());
			console.log(this.bathing());	
		}
	};
		
	this.clean =  function (){
		if(this.bowels()==="null"){this.bowels(null);}
		if(this.bladder()==="null"){this.bladder(null);}
		if(this.grooming()==="null"){this.grooming(null);}
		if(this.toilet()==="null"){this.toilet(null);}
		if(this.feeding()==="null"){this.feeding(null);}
		if(this.transfer()==="null"){this.transfer(null);}
		if(this.mobility()==="null"){this.mobility(null);}
		if(this.dressing()==="null"){this.dressing(null);}
		if(this.stairs()==="null"){this.stairs(null);}
		if(this.bathing()==="null"){this.bathing(null);}	
	};
		
	this.hasDetail = function (){
		this.clean();		
		return this.bowels() !== null ||
			this.bladder() !== null ||
			this.grooming()  !== null ||
			this.toilet()  !== null ||
			this.feeding()  !== null ||
			this.transfer()  !== null ||
			this.mobility()  !== null ||
			this.dressing()  !== null ||
			this.stairs()  !== null ||
			this.bathing()  !== null;
	};
	
	this.clearDetail = function(){
		this.bowels(null);
		this.bladder(null);
		this.grooming(null) ;
		this.toilet(null) ;
		this.feeding(null) ;
		this.transfer(null) ;
		this.mobility(null) ;
		this.dressing(null) ;
		this.stairs(null) ;
		this.bathing(null);
	};
	
	this.isComplete = function(){
		this.clean();
		return this.bowels() !==null &&
		this.bladder() !==null  &&
		this.grooming() !==null &&
		this.toilet() !==null &&
		this.feeding() !==null &&
		this.transfer() !==null &&
		this.mobility() !==null &&
		this.dressing() !==null &&
		this.stairs() !==null &&
		this.bathing() !==null;
	};

	this.clear = function() {
		this.id(null);
		this.manualTotal(null);
		this.bowels (null);
		this.bladder (null);
		this.grooming (null);
		this.toilet (null);
		this.feeding (null);
		this.transfer (null);
		this.mobility (null);
		this.dressing (null);
		this.stairs(null);
		this.bathing(null);
	};
	
	this.set = function(data) {
		if (data) {
			for (var propertyName in this) 
			{
				if(ko.isObservable(this[propertyName])){
					if(data[propertyName] !==null){
						this[propertyName](data[propertyName]) ;
					}else{
						this[propertyName](null);
					}
				}
			}
		} else {
			this.clear();
		}
	};

	

};

