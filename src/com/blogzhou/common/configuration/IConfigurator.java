package com.blogzhou.common.configuration;

public abstract interface  IConfigurator {
	  public abstract void configure(IConfigurationItem paramIConfigurationItem) throws ConfigurationException;
}
