package uk.ac.leeds.lihs.auecr.cimss.taglib



/*
 * 
 *  <input type="radio" value="yes" id="faceWeaknessYes" name="faceWeakness" class="ui-helper-hidden-accessible">
	    <label for="faceWeaknessYes" style="margin-bottom: 5px; width: 150px;" aria-pressed="true" class="ui-button ui-widget ui-state-default ui-button-text-only ui-corner-left ui-state-active" role="button" aria-disabled="false"><span class="ui-button-text">Yes</span></label>
		</div>
 * 
 * 
 * 
 * 
 */

class FormTagLib {
	
	
	
	def getAppend = {idx, spaceEvery, accumulated ->
		if(spaceEvery && idx > 0){
			if( (idx + 1)  % (spaceEvery.toInteger()) ==0){
				def result = " append-" + (23-accumulated)
				accumulated = 2
				return result
			}else{
				return ""
			}
		}else{
			return ""
		}
		
		
	}
	
	private def getBinding = {bind, onEvent, name ->
		def eventBinding = '';
		if (onEvent) {
			eventBinding = ", event: {"+onEvent+"} ";
		}
		if(bind){
			if(bind=="toField"){
				return "data-bind=\"checkedWithToggle: ${name} ${eventBinding} \""
			}else{
				return "data-bind=\"checkedWithToggle: ${bind}.${name} ${eventBinding} \""
			}
			
		}else{
			return ""
		}
		
	}
	
	def buttonRadioGroup = {attrs, body ->
		def value = attrs.remove('value')
		def values = attrs.remove('values')
		def labels = attrs.remove('labels')
		def spans = attrs.remove('spans')
		def spaceEvery = attrs.remove('spaceEvery')
		def space = attrs.remove('space')
		def name = attrs.remove('name')
		def style = attrs.remove('style')
		def bind = attrs.remove('bind')
		def claZZ = attrs.remove('class')
//		if(!style){
//			style = 'margin-bottom:5px;width:150px'
			style = ''
//		}
		if(!claZZ) {
				claZZ = 'answer'
		}
		def idStem = attrs.remove('idStem')
		if(!idStem){
			idStem=""
		}
		
		def noData = attrs.remove('noData')
		def noDataValue = attrs.remove('noDataValue')
		def noDataLabel =  attrs.remove('noDataLabel')
		def onEvent = attrs.remove('onEvent')
		
		def Integer accumulated = 2;
		values.eachWithIndex {val, idx ->
			def it = new Expando();
			if(spaceEvery  && (idx > 0) &&  ( (idx)  % (spaceEvery.toInteger()) ==0 ) ){
				accumulated = 2
				//it.radio = "<div class=\"span-24\"><p></P></div><div class=\"span-2\"><p></P></div>"
				it.radio = "<div class=\"span-2\"><p></P></div>"
			}else{
				it.radio = ""
			}
//			it.radio += "<div class=\" span-" + spans[idx] +  getAppend(idx, spaceEvery , accumulated += spans[idx].toInteger()) + "\"><input type=\"radio\"  ${getBinding(bind, onEvent, name)} name=\"${idStem}${name}\" id=\"${idStem}${name}${val}\" "
			it.radio += "<div class=\""+ claZZ  + "\"><input type=\"radio\"  ${getBinding(bind, onEvent, name)} name=\"${idStem}${name}\" id=\"${idStem}${name}${val}\" "
			if (value?.toString().equals(val.toString())) {
				it.radio += 'checked '
			}
			it.radio += "value=\"${val.toString().encodeAsHTML()}\" />"
			it.label = "<label style=\"" + style + "\" for=\"${idStem}${name}${val}\" >" 
			it.label += labels == null ? 'Radio ' + val : labels[idx].toString()
			it.label += "</label></div>"
			out << body(it)		
			out.println("")
		}
		if(noData=='true'){
			def aBean = new Expando()
			def val = ""
			aBean.radio=aBean.radio = "<input type=\"radio\" name=\"${name}\" "
			
			if(noDataValue){
				aBean.radio +='checked '
			}
			
			aBean.radio += "value=\"${val.toString().encodeAsHTML()}\" ondblclick=\"deselect('${name}')\" />"
			aBean.label = noDataLabel
			out << body(aBean)
			out.println("")
		}
	}

}
