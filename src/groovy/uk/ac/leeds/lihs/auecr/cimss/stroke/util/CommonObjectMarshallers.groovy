package uk.ac.leeds.lihs.auecr.cimss.stroke.util

/**
 * 
 * Based on example By Joe Rinehart
 * http://compiledammit.com/2012/08/16/custom-json-marshalling-in-grails-done-right/
 *
 */

class CommonObjectMarshallers {
	   List marshallers = []
		
	   def register() {
		   marshallers.each{ it.register() }
	   }
 }

