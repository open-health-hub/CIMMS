import uk.ac.leeds.lihs.auecr.cimss.nhs.organisation.*
import uk.ac.leeds.lihs.auecr.cimss.stroke.lookup.GeneralPractitioner;


def getSurname = { name ->
    def result = ""
    result = name.replace("^[Dd][Rr] ", "").trim()
    
    def  parts = result.split(" ")
    if(parts.size()>1){
       def previous = ""
       result = ""
        parts.each{
            result = result + previous + " "
            previous = it
        }
        
        if (previous.length() > 3){
         result = result + previous + " "
        }
    }
    
    return result.trim()
    
}

def splitInitials = { initials ->
    def result =""
    initials.each{
        result = result  + it + ". "
    }
    return result.trim()
}

def getName = { name ->
    return "DR. " + splitInitials(name.replace(getSurname( name),"").trim())  + " " +  getSurname( name)
}


def getAddress  = { practice ->

    def result = ""
    if(practice.addressLine1){
        result = result + practice.addressLine1 + " "
    }
    if(practice.addressLine2){
        result = result + practice.addressLine2 + " "
    }
    if(practice.addressLine3){
        result = result + practice.addressLine3 + " "
    }
    if(practice.addressLine4){
        result = result + practice.addressLine4 + " "
    }
    if(practice.addressLine5){
        result = result + practice.addressLine5 + " "
    }
    if(practice.postcode){
        result = result + practice.postcode + " "
    }
    
    return result.trim()

}

def keyWords =  {
            return ["DR AT "
                , "SURGERY"
                , "PRACTICE"
                , "CENTRE"
                , "MEDICAL"
                , "UNIT"
                , "CLINIC"
                , "SERVICE"
                , "LOCUM"
                , "MED PRACT"
                , "DOCTORS"
                , "HAVEN"
                , "HEALTH"
                , "PRIMECARE" 
                , "CENTRAL"
                , "PROJECT"
                , "OUTREACH"
                , "ADVICE"
                , "&"
                , "DEPT"
                , "MEDICINE"
                , "DEPT"
                , "DERMATOLOGY"
                , "PARTNER"
                , "POOLED"
                , "HOSPICE"
                , "ADVISE"
                , "COMBINED"
				, "OUT OF"
				, "TREATMENT"
				, "TEAM"
				, "PRIMARY"
				, "OOH"
				, "CARE"
				, "PCC"
				, "FAMILIES"
				, "FAMILY"
				, "HOSPITAL"
				, "NURSE"
				, "PCT"
				, "NURSING"
				, "1"
				, "2"
				, "3"
				, "4"
				, "5"
				, "6"
				, "7"
				, "8"
				, "9"
				, "0"
				, ")"
				, "("
				, "GPSWI"
				, "ADDACTION"
				, "PROGRAM"
				, "GYNAECO"
				, "TISSUE"]}


def isGP = { practitioner ->
    def result = true
    keyWords().each{word ->
        if(practitioner.name.contains(word) ){
            result = false
        }
    }
    return result;
}


GeneralMedicalPractitioner.findAll().each{practitioner ->
    def practice = GeneralMedicalPractice.findByCode(practitioner.practiceCode)
    if(practice){
        if(isGP(practitioner)){
            def gp = new GeneralPractitioner()
            gp.name  =  getName(practitioner.name)
            gp.surname  = getSurname(practitioner.name)
            gp.practice = practice?.practiceName
            gp.address = getAddress(practice)
            gp.postcode = practice?.postcode
            if(gp.save()){
               
            }else{
                println "${gp.errors}"
            }
         }
     }
    
}


