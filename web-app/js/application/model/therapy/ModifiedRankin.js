cimss.namespace("model.therapy").ModifiedRankin = function () {
	this.id  = ko.observable();
	this.modifiedRankinScore  = ko.observable();
	
	this.clear = function() {
		this.id(null);
		this.modifiedRankinScore(null);
	};

	this.set = function(data) {
		if (data) {
			for (var propertyName in this) 
			{
				if(ko.isObservable(this[propertyName]))
				{
					if(data[propertyName] !==null){
						this[propertyName](data[propertyName]);
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
