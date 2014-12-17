package com.blogzhou.common.configuration;

public abstract interface  IConfigurationItem {
	  public static final String CONFIGURE_ITEM_FILE = "file";
	  public static final String CONFIGURE_ITEM_ENCODING = "encoding";
	  public static final String CONFIGURE_ITEM_TYPE = "type";
	  public static final String CONFIGURE_ITEM_CONFIGURATOR = "configurator";
	  public static final String CONFIGURE_ITEM_CONFIGMETHOD = "configmethod";

	  public abstract String getConfigmethod();

	  public abstract void setConfigmethod(String paramString);

	  public abstract String getConfigurator();

	  public abstract void setConfigurator(String paramString);

	  public abstract String getEncoding();

	  public abstract void setEncoding(String paramString);

	  public abstract String getFile();

	  public abstract void setFile(String paramString);

	  public abstract String getType();

	  public abstract void setType(String paramString);

	  public abstract void setProperty(String paramString1, String paramString2);

	  public abstract String getProperty(String paramString);
}
