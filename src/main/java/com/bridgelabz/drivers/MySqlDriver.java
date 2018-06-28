package com.bridgelabz.drivers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mysqldatabase.properties")
public class MySqlDriver implements DataBaseDriver {

	@Value("${databaseName}")
    protected String databaseName;
  
	@Value("${disableStatementPooling}")
    protected String disableStatementPooling;
    
    
	
	
	public String getDatabaseName() {
		return databaseName;
	}




	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}




	public String getDisableStatementPooling() {
		return disableStatementPooling;
	}




	public void setDisableStatementPooling(String disableStatementPooling) {
		this.disableStatementPooling = disableStatementPooling;
	}




	public String getInfo() {
		return "[ Driver: mySql" +
                ", databaseName: " + databaseName +
                ", disableStatementPooling: " + disableStatementPooling +
                " ]";
		
	}
	

}
