package com.blogzhou.common.configuration;

import java.util.HashMap;

public class ConfigurationItem implements IConfigurationItem{
	
	  private final HashMap properties;

	  public String getConfigmethod()
	  {
	    return ((String)this.properties.get("configmethod"));
	  }

	  public void setConfigmethod(String configmethod)
	  {
	    this.properties.put("configmethod", configmethod);
	  }

	  public String getConfigurator()
	  {
	    return ((String)this.properties.get("configurator"));
	  }

	  public void setConfigurator(String configurator)
	  {
	    this.properties.put("configurator", configurator);
	  }

	  public String getEncoding()
	  {
	    return ((String)this.properties.get("encoding"));
	  }

	  public void setEncoding(String encoding)
	  {
	    this.properties.put("encoding", encoding);
	  }

	  public String getFile()
	  {
	    return ((String)this.properties.get("file"));
	  }

	  public void setFile(String file)
	  {
	    this.properties.put("file", file);
	  }

	  public String getType()
	  {
	    return ((String)this.properties.get("type"));
	  }

	  public void setType(String type)
	  {
	    this.properties.put("type", type);
	  }

	  public void setProperty(String name, String value)
	  {
	    this.properties.put(name, value);
	  }

	  public String getProperty(String name)
	  {
	    return ((String)this.properties.get(name));
	  }

	  public ConfigurationItem() {
	    this.properties = new HashMap();
	  }
}
