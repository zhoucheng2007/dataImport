package com.blogzhou.common.configuration;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationRegister {
	  private static final Logger LOGGER=LoggerFactory.getLogger(ConfigurationRegister.class);
	  private static ConfigurationRegister register;
	  private Configuration mainConfig;
	  private Map configMap;
	  private static final String MAIN_CONFIG_FILE = "foresee-wssw.properties";
	  private static final String CONFIG_ITEM = "configuration";
	  private static final String CONFIG_ITEM_FILE = "file";
	  private static final String CONFIG_ITEM_TYPE = "type";
	  private static final String CONFIG_ITEM_ENCODING = "encoding";
	  private static final String CONFIG_ITEM_CONFIGURATOR = "configurator";
	  private static final String CONFIG_ITEM_TYPE_XML = "xml";
	  private static final String CONFIG_ITEM_TYPE_PROPERTIES = "properties";
	  private static final String CONFIG_ITEM_TYPE_CUSTOM = "custom";
	  private static final String CONFIG_ITEM_TYPE_PATH = "path";
	  public static final String ENV_PROPERTY_WEBAPP_ROOT = "env.property.webapp.root";
	  private static Map env=new HashMap();;

	  static {
	  }


	  private ConfigurationRegister(String mainCfg) throws  org.apache.commons.configuration.ConfigurationException
	  {
	    if (LOGGER.isDebugEnabled()) {
	      LOGGER.debug("Create configuration:" + mainCfg);
	    }

	    if ((mainCfg == null) || ("".equals(mainCfg.trim())))
	    {
	      System.out.println("- Loading from 'foresee-wssw.properties' :");
	      mainCfg = "foresee-wssw.properties";
	    }

	    try
	    {
	      this.mainConfig = new PropertiesConfiguration(mainCfg);
	    } catch (ConfigurationException e) {
	      throw e;
	    }

	    this.configMap = new Hashtable();
	    refreshAllConfiguration();
	  }

	  public static ConfigurationRegister getInstance() throws ConfigurationException
	  {
	    return getInstance(null);
	  }

	  public static synchronized ConfigurationRegister getInstance(String mainCfg) throws ConfigurationException, ConfigurationException
	  {
	    if (register == null)
	      register = new ConfigurationRegister(mainCfg);

	    return register;
	  }

	  public Configuration getConfiguration(String cfgName) throws ConfigurationException
	  {
	    return ((Configuration)this.configMap.get(cfgName));
	  }

	  public void addConfiguration(String cfgName, Configuration config) throws ConfigurationException
	  {
	    this.configMap.put(cfgName, config);
	  }

	  public void removeConfiguration(String cfgName) throws ConfigurationException
	  {
	    this.configMap.remove(cfgName);
	  }

	public void refreshConfiguration(String cfgName) throws ConfigurationException
	  {
	    String itemFile = null;
	    String itemType = null;

	    Configuration itemConfiguration = null;

	    if (cfgName == null) {
	      return;
	    }

	    itemFile = this.mainConfig.getString(cfgName + "." + "file");
	    if (itemFile == null) {
	      LOGGER.error("No config file for '" + cfgName + "'");
	      return;
	    }

	    String webappRoot = (String)env.get("env.property.webapp.root");

	    if (LOGGER.isDebugEnabled()) {
	      LOGGER.debug("Refresh configuration " + cfgName + " with webappRoot: " + webappRoot);
	    }

	    if ((itemFile.startsWith(".")) && (webappRoot != null)) {
	      itemFile = webappRoot + itemFile.substring(1);
	      this.mainConfig.setProperty(cfgName + "." + "file", itemFile);
	    }

	    itemType = this.mainConfig.getString(cfgName + "." + "type");

	    if (itemType == null) {
	      LOGGER.warn("not supported null type, skip this configuration item.");
	    } else if (itemType.equalsIgnoreCase("xml")) {
	      LOGGER.info("Loading '" + itemFile + "' ...");
	      itemConfiguration = new XMLConfiguration(itemFile);
	      LOGGER.info("Loading '" + itemFile + "' success");
	    } else if (itemType.equalsIgnoreCase("properties")) {
	      LOGGER.info("Loading '" + itemFile + "' ...");
	      itemConfiguration = new PropertiesConfiguration(itemFile);
	      LOGGER.info("Loading '" + itemFile + "' success");
	    } else if (itemType.equalsIgnoreCase("custom")) {
	      LOGGER.info("Loading '" + itemFile + "' ...");

	      String configurator = this.mainConfig.getString(cfgName + "." + "configurator");
	      if (configurator == null) {
	        LOGGER.error("Loading '" + itemFile + "' fail, configurator is null.");
	        return;
	      }
	      IConfigurationItem confRep = new ConfigurationItem();
	      Iterator<String> ite = this.mainConfig.getKeys();
	      while (ite.hasNext()) {
	        String cfgItemName = (String)ite.next();
	        if (cfgItemName.startsWith(cfgName + ".")) {
	          String cfgItemValue = this.mainConfig.getString(cfgItemName);
	          cfgItemName = cfgItemName.substring(cfgName.length());
	          confRep.setProperty(cfgItemName, cfgItemValue);
	        }
	      }
	      IConfigurator customConf = CustomConfigurator.getInstance();
	      customConf.configure(confRep);
	      LOGGER.info("Loading '" + itemFile + "' success");
	    } else if (!(itemType.equalsIgnoreCase("path"))) {
	      LOGGER.error("not supported type : " + itemType + " , skip this configuration item.");
	    }

	    if (itemConfiguration != null)
	    {
	      removeConfiguration(cfgName);

	      addConfiguration(cfgName, itemConfiguration);
	    }
	  }

	  public void refreshAllConfiguration()
	    throws ConfigurationException
	  {
	    if (LOGGER.isDebugEnabled()) {
	      LOGGER.debug("Begin to refresh all configuration.");
	    }

	    String itemPrefix = null;

	    if (this.configMap == null) {
	      this.configMap = new Hashtable();
	    }

	    this.configMap.clear();

	    Iterator ir = this.mainConfig.getList("configuration").iterator();
	    while (ir.hasNext())
	    {
	      itemPrefix = (String)ir.next();
	      refreshConfiguration(itemPrefix);
	    }

	    if (LOGGER.isDebugEnabled())
	      LOGGER.debug("Finish refresh all configuration.");
	  }

	  public String getConfigurationFile(String cfgName)
	    throws ConfigurationException
	  {
	    String confFile = this.mainConfig.getString(cfgName + "." + "file");

	    String webappRoot = (String)env.get("env.property.webapp.root");
	    if ((confFile.startsWith(".")) && (webappRoot != null)) {
	      confFile = webappRoot + confFile.substring(1);
	    }

	    return confFile;
	  }

	  public String getConfigurationType(String cfgName)
	    throws ConfigurationException
	  {
	    return this.mainConfig.getString(cfgName + "." + "type");
	  }

	  public String getConfigurationEncoding(String cfgName)
	    throws ConfigurationException
	  {
	    return this.mainConfig.getString(cfgName + "." + "encoding");
	  }

	  public String getConfigurationProperty(String cfgName, String propName)
	    throws ConfigurationException
	  {
	    return this.mainConfig.getString(cfgName + "." + propName);
	  }

	  public static void putEnvProperty(String key, Object value)
	  {
	    env.put(key, value);
	  }

	  public static String getEnvProperty(String key)
	  {
	    return ((String)env.get(key));
	  }
}
