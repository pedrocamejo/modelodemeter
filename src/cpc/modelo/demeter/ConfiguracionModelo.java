package cpc.modelo.demeter;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


@Configuration
@PropertySource("classpath:data.properties")
public class ConfiguracionModelo {
	
    @Autowired
	private Environment environment;

	@Bean()
	public  Flyway flyway (SingleConnectionDataSource datasource){
		Flyway flyway = new Flyway();
		flyway.setDataSource(datasource);
		flyway.setBaselineOnMigrate(true);
		flyway.setLocations("db.migration/");
		flyway.setTable("schema_structure_version");
		flyway.migrate();
		return flyway;
	}

	
	@Bean
	public SingleConnectionDataSource dataSource(){
		SingleConnectionDataSource datasource = new SingleConnectionDataSource();
		datasource.setDriverClassName("org.postgresql.Driver");
		datasource.setUrl(environment.getProperty("demeter.db.url"));
		datasource.setUsername(environment.getProperty("demeter.db.username"));
		datasource.setPassword(environment.getProperty("demeter.db.password"));
		datasource.setSuppressClose(true);
		return datasource;
	}

	@Bean(name="idsede")
	public String idsede(){
		return environment.getProperty("idsede");
	}

	@Bean
	public String idsedesincronizar(){
		return environment.getProperty("idsedesincronizar") ;
	}

}
