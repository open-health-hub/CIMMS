/*global cimss*/
window.cimss = {
    namespace: function (ns) {
        var parts = ns.split("."), object = this, i, len;
        for (i = 0, len = parts.length; i < len; i = i + 1) {
            if (!object[parts[i]]) {
                object[parts[i]] = {};
            }
            object = object[parts[i]];
        }
        return object;
    }
};

var OptionData = function(value, description) {
	this.value = value;
	this.description = description;
}

var __GLOBAL_FLAGS = {}

var setGlobalFlag = function(key, value) {
	__GLOBAL_FLAGS[key] = value; 
}
var getGlobalFlag = function(key) {
	return __GLOBAL_FLAGS[key]; 
}