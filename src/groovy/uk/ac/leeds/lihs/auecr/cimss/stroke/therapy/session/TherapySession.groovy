/**
 * 
 */
package uk.ac.leeds.lihs.auecr.cimss.stroke.therapy.session

import uk.ac.leeds.lihs.auecr.cimss.stroke.CareActivity
import uk.ac.leeds.lihs.auecr.cimss.stroke.PatientProxy

/**
 * @author AjasinA
 *
 */
class TherapySession {
	List therapistList = []
	Date startTime
	Date endTime
	Long durationMinutes
	Long duration
	PatientProxy patient
	String therapyType
	CareActivity careActivity
}
