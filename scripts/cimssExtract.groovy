import uk.ac.leeds.lihs.auecr.cimss.stroke.extractor.*
import uk.ac.leeds.lihs.auecr.cimss.stroke.*


def extractorService = ctx.getBean("cimssExtractorService")

def extract = extractorService.extractData(CareActivity.get(91))

println extract.previousStroke
println extract.imagingDate
println extract.imagingTime
println extract.thrombolysisGiven
println extract.independentInADLPriorToAdmission
println extract.ableToWalkWithoutAssistanceOnPresentation
println extract.GCSAtPresentation
println extract.powerArm
println extract.powerLeg
println extract.armSideAffected
println extract.legSideAffected
println extract.strokeBedAvailableAtPresentation
println extract.newIncontinence
println extract.OCSP 

println extract.formalCommunicationAssessmentDate
println extract.formalCommunicationTime
println extract.swallowingAssessmentDate
println extract.swallowingAssessmentTime
println extract.occupationalTherapyAssessmentDate
println extract.occupationalTherapyAssessmentTime
println extract.formalSwallowingAssessmentDate
println extract.formalSwallowingAssessmentTime
println extract.physiotherapyAssessmentDate
println extract.physiotherapyAssessmentTime






        