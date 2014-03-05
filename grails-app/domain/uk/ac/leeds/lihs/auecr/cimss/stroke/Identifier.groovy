package uk.ac.leeds.lihs.auecr.cimss.stroke

import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.IdentifierType;

class Identifier {
	
	
	String reference
	IdentifierType type
	
	static auditable = [ignore:[]]
    static constraints = {
    }
}
