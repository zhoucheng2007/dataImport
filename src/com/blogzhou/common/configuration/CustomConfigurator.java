package com.blogzhou.common.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CustomConfigurator implements IConfigurator {
	  private static final CustomConfigurator instance = new CustomConfigurator();

	  public static IConfigurator getInstance()
	  {
	    return instance;
	  }

	  public void configure(IConfigurationItem confItem) throws ConfigurationException
	  {
	    Class configuratorClass;
	    try
	    {
	      configuratorClass = Class.forName(confItem.getConfigurator());
	      Object configurator = null;
	      if (IConfigurator.class.isAssignableFrom(configuratorClass))
	        ((IConfigurator)configuratorClass.newInstance()).configure(confItem);
	      else
	        try {
	          if (confItem.getEncoding() != null) {
	        	  Method method = configuratorClass.getDeclaredMethod(confItem.getConfigmethod(), new Class[] { String.class, String.class });

	            if (method != null) {
	              if (!(Modifier.isStatic(method.getModifiers())))
	                configurator = configuratorClass.newInstance();

	              method.invoke(configurator, new Object[] { confItem.getFile(), confItem.getEncoding() });
	              return;
	            }
	          }
	          Method method = configuratorClass.getDeclaredMethod(confItem.getConfigmethod(), new Class[] { String.class });

	          if (method == null)
	            throw new ConfigurationException("Not found method '" + confItem.getConfigmethod() + "'");

	          if (!(Modifier.isStatic(method.getModifiers())))
	            configurator = configuratorClass.newInstance();

	          method.invoke(configurator, new Object[] { confItem.getFile() });
	        } catch (SecurityException e) {
	          throw new ConfigurationException("Fail to confirue with class '" + confItem.getConfigurator() + "' and method '" + confItem.getConfigmethod() + "'.", e);
	        }
	        catch (NoSuchMethodException e) {
	          throw new ConfigurationException("Fail to confirue with class '" + confItem.getConfigurator() + "' and method '" + confItem.getConfigmethod() + "'.", e);
	        }
	        catch (IllegalArgumentException e) {
	          throw new ConfigurationException("Fail to confirue with class '" + confItem.getConfigurator() + "' and method '" + confItem.getConfigmethod() + "'.", e);
	        }
	        catch (InvocationTargetException e) {
	          throw new ConfigurationException("Fail to confirue with class '" + confItem.getConfigurator() + "' and method '" + confItem.getConfigmethod() + "'.", e);
	        }
	    }
	    catch (InstantiationException e)
	    {
	      throw new ConfigurationException("Fail to confirue with class '" + confItem.getConfigurator() + "' and method '" + confItem.getConfigmethod() + "'.", e);
	    }
	    catch (IllegalAccessException e) {
	      throw new ConfigurationException("Fail to confirue with class '" + confItem.getConfigurator() + "' and method '" + confItem.getConfigmethod() + "'.", e);
	    }
	    catch (ClassNotFoundException e) {
	      throw new ConfigurationException("Fail to confirue with class '" + confItem.getConfigurator() + "' and method '" + confItem.getConfigmethod() + "'.", e);
	    }
	  }
}
