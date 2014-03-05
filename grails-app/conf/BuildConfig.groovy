grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
	
	
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
	
	
    log "error" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
		
		
        grailsPlugins()
        grailsHome()
        grailsCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        mavenLocal()
        mavenCentral()
		
		//mavenRepo "http://bhts-maternityb:9081/artifactory/repo/"
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
	
	
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

        // runtime 'mysql:mysql-connector-java:5.1.13'
		compile 'org.apache.commons:commons-jexl:2.1.1'
    }
	
	
	
	// This closure is passed the location of the staging directory that
	// is zipped up to make the WAR file, and the command line arguments.
	// Here we override the standard web.xml with our own.
	grails.war.resources = { stagingDir, args ->
		copy(toDir: "${stagingDir}/therapy/TherapyApp") {
			fileset(dir:"${basedir}/../TherapyApp/TherapyApp/public_html")
		}
	}
}

//grails.project.work.dir = "c:/tmp/development/stroke_chft/work"
