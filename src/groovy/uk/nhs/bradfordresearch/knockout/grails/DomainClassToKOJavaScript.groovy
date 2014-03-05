package uk.nhs.bradfordresearch.knockout.grails

import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

class DomainClassToKOJavaScript {

	static getKOJavaScript(grailsDomainClazz) {
		def domainClassSortedPropertiesMap = sortDomainClassProperties(grailsDomainClazz)
		def stringBuilder = new StringBuilder();
		declareObject(stringBuilder, grailsDomainClazz)
		writeNewLine(stringBuilder,"")
		declareProperties(stringBuilder, domainClassSortedPropertiesMap)
		writeNewLine(stringBuilder,"")
		declareOneToManySetMethods(stringBuilder, domainClassSortedPropertiesMap.oneToManyProperties)
		writeNewLine(stringBuilder,"")
		declareClearMethod(stringBuilder, domainClassSortedPropertiesMap)
		writeNewLine(stringBuilder,"")
		declareSetMethod(stringBuilder, domainClassSortedPropertiesMap)
		writeNewLine(stringBuilder,"}")
		return stringBuilder.toString()
	}
	
	private static declareClearMethod(stringBuilder, domainClassSortedPropertiesMap)  {
		writeNewLine(stringBuilder,"\tthis.clear = function() {")
		domainClassSortedPropertiesMap.notAssociatedProperties.each { property ->
			writeNewLine(stringBuilder,"\t\tthis.${property.name}(null);")
		}
		domainClassSortedPropertiesMap.oneToOneProperties.each { property ->
			def referencedDomainClassName = getDomainClassName(property.referencedPropertyType)
			writeNewLine(stringBuilder,"\t\tthis.${property.name}(new ${referencedDomainClassName}());")
		}
		domainClassSortedPropertiesMap.oneToManyProperties.each { property ->
			writeNewLine(stringBuilder,"\t\tthis.${property.name}(null);")
		}
		writeNewLine(stringBuilder,"\t}")
	}
	
	
	private static declareSetMethod(stringBuilder, domainClassSortedPropertiesMap) {
		writeNewLine(stringBuilder,"\tthis.set = function(data) {")
		writeNewLine(stringBuilder,"\t\tif (data) {")
		domainClassSortedPropertiesMap.notAssociatedProperties.each { property ->
			writeNewLine(stringBuilder,"\t\t\tdata.${property.name} !=null ? this.${property.name}(data.${property.name}) : this.${property.name}(null);")
		}
		domainClassSortedPropertiesMap.oneToOneProperties.each { property ->
			def referencedDomainClassName = getDomainClassName(property.referencedPropertyType)
			writeNewLine(stringBuilder,"\t\t\tdata.${property.name} !=null ? this.${property.name}().set(data.${property.name}) : this.${property.name}(new ${referencedDomainClassName}());")
		}
		domainClassSortedPropertiesMap.oneToManyProperties.each { property ->
			def capitalisedPropertyName = getCapitalisedPropertyName(property)
			writeNewLine(stringBuilder,"\t\t\tdata.${property.name} !=null ? this.set${capitalisedPropertyName}(data.${property.name}) : this.set${capitalisedPropertyName}(null);")
		}
		writeNewLine(stringBuilder,"\t\t} else {")
		writeNewLine(stringBuilder,"\t\t\tthis.clear();")
		writeNewLine(stringBuilder,"\t\t}")
		writeNewLine(stringBuilder,"\t}")
	}

	private static declareOneToManySetMethods(stringBuilder, oneToManyProperties) {
		oneToManyProperties.each { property ->
			def capitalisedPropertyName = getCapitalisedPropertyName(property)
			def referencedDomainClassName = getDomainClassName(property.referencedPropertyType)
			writeNewLine(stringBuilder,"\tthis.set${capitalisedPropertyName} = function(${property.name}) {")
			writeNewLine(stringBuilder,"\t\tvar result = [];")
			writeNewLine(stringBuilder,"\t\tif (${property.name}) {")
			writeNewLine(stringBuilder,"\t\t\tfor(var i=0; i < ${property.name}.length; i++) {")
			writeNewLine(stringBuilder,"\t\t\t\tvar oneOfMany = new ${referencedDomainClassName}();")
			writeNewLine(stringBuilder,"\t\t\t\toneOfMany.set(${property.name}[i]);")
			writeNewLine(stringBuilder,"\t\t\t\tresult[i] = oneOfMany;")
			writeNewLine(stringBuilder,"\t\t\t}")
			writeNewLine(stringBuilder,"\t\t}")
			writeNewLine(stringBuilder,"\t\tthis.${property.name}(result);")
			writeNewLine(stringBuilder,"\t}")
		}
	}
	
	private static getCapitalisedPropertyName(property) {
		def propertyName = property.name
		return  propertyName.substring(0,1).toUpperCase() + propertyName.substring(1)
	}
	
	

	private static declareProperties(stringBuilder, domainClassSortedPropertiesMap) {
		domainClassSortedPropertiesMap.notAssociatedProperties.each { property ->
			writeNewLine(stringBuilder,"\tthis.${property.name} = ko.observable();")
		}
		domainClassSortedPropertiesMap.oneToOneProperties.each { property ->
			def referencedDomainClassName = getDomainClassName(property.referencedPropertyType)
			writeNewLine(stringBuilder,"\tthis.${property.name} = ko.observable(new ${referencedDomainClassName}());")
		}
		domainClassSortedPropertiesMap.oneToManyProperties.each { property ->
			writeNewLine(stringBuilder,"\tthis.${property.name} = ko.observableArray([]);")
		}
	}

	private static declareObject(stringBuilder, grailsDomainClazz) {
		def domainClassName = getDomainClassName(grailsDomainClazz)
		writeNewLine(stringBuilder,"function ${domainClassName}() {")
	}

	private static sortDomainClassProperties(grailsDomainClazz) {
		def notAssociatedProperties = []
		def oneToOneProperties = []
		def oneToManyProperties = []
		def leftOverProperties = []
		def defaultDomainClass = new DefaultGrailsDomainClass(grailsDomainClazz)
		defaultDomainClass.properties.each { property ->
			if (property.persistent) {
				if (property.oneToOne) {
					oneToOneProperties.add(property)
				} else if (property.oneToMany) {
					oneToManyProperties.add(property)
				} else if (!property.association && property.referencedPropertyType!=Object.class) {
					notAssociatedProperties.add(property)
				} else {
					leftOverProperties.add(property)
				}
			} else {
				leftOverProperties.add(property)
			}
		}
		def sortedPropertiesMap =  [notAssociatedProperties:notAssociatedProperties
					,oneToOneProperties:oneToOneProperties
					,oneToManyProperties:oneToManyProperties
					,leftOverProperties:leftOverProperties]
		return sortedPropertiesMap
	}

	private static getDomainClassName(domainClazz) {
		def domainClassNameParts = domainClazz.name.split("\\.")
		return domainClassNameParts[-1]
	}
	
	private static writeNewLine(stringBuilder, string) {
		stringBuilder.append(string)
		stringBuilder.append("\n")
	}




	
}
