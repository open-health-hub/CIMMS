import uk.nhs.bradfordresearch.knockout.grails.*

def grailsApplication = ctx.getBean("grailsApplication")
grailsApplication.getArtefacts("Domain").each {
    def domainClazz = it.clazz
    def domainClassName = it.clazz.name
    def file = new File("C:\\tmp\\javascript\\${domainClassName.split("\\.")[-1]}.js")
    file.text = DomainClassToKOJavaScript.getKOJavaScript(domainClazz)
}