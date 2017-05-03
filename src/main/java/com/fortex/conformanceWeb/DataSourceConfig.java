/**<p>Description</p>
 * @author Ivan Huo
 */
package com.fortex.conformanceWeb;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author Ivan Huo
 *
 */

@Configuration
public class DataSourceConfig {
	@Bean(name = "userDataSource")
    @Qualifier("userDataSource")
    @ConfigurationProperties(prefix="spring.datasource.user")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }
	
	
    @Bean(name = "xRingDataSource")
    @Qualifier("xRingDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.xRing")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    
    @Bean(name = "userJdbcTemplate")
	public JdbcTemplate userJdbcTemplate(
	        @Qualifier("userDataSource") DataSource dataSource) {
	    return new JdbcTemplate(dataSource);
	}
    
    
    @Bean(name = "xRingJdbcTemplate")
	public JdbcTemplate xRingJdbcTemplate(
	        @Qualifier("xRingDataSource") DataSource dataSource) {
	    return new JdbcTemplate(dataSource);
	}
}
