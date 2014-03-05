dataSource {
//    pooled = true
//    driverClassName = "org.hsqldb.jdbcDriver"
//    username = "sa"
//    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource{
			pooled = true
            dbCreate = "update" 
//			username = "stroke_app"
//			password = "p4ssword"
//			driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
//			url = "jdbc:sqlserver://localhost;database=stroke"
//			dialect = "org.hibernate.dialect.SQLServerDialect"
//			validationQuery="select 1"
			testWhileIdle=true
			timeBetweenEvictionRunsMillis=60000
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
			pooled = true
			driverClassName = "org.hsqldb.jdbcDriver"
			username = "sa"
			password = ""
        }
    }
    production {
        dataSource{
			pooled = true
            //dbCreate = "update" 
//			username = "stroke_app"
//			password = "p4ssword"
//			driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"
//			url = "jdbc:sqlserver://localhost;database=stroke"
//			dialect = "org.hibernate.dialect.SQLServerDialect"
//			validationQuery="select 1"
			testWhileIdle=true
			timeBetweenEvictionRunsMillis=60000
        }
    }
}
