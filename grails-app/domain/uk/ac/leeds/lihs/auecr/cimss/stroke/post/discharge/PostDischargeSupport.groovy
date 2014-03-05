package uk.ac.leeds.lihs.auecr.cimss.stroke.post.discharge

import uk.ac.leeds.lihs.auecr.cimss.stroke.PostDischargeCare;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.ComorbidityType;
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.PostDischargeSupportType;

class PostDischargeSupport {

   PostDischargeSupportType type
	
	static belongsTo = [postDischargeCare:PostDischargeCare]
	static auditable = [ignore:[]]
    static constraints = {
    }
}
