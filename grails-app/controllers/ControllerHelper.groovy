

class ControllerHelper {
	
	public static def getDate = { value ->
		return Date.parse('dd/MM/yyyy' , value)
	}
	
	public  static def getTime = {value ->
		def components = value.tokenize(":")
		return components[0].toInteger() * 60 + components[1].toInteger();
	}

}
