package com.wickedhobo.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServletInitPropsContextListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//TODO finish this
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext c = event.getServletContext();
		Map<String, String> props = new HashMap<String, String>();
		if (c != null) {
	    if (c.getInitParameter("HibernateConfigFile") != null) {       
	      props.put("HibernateConfigFile",  c.getInitParameter("HibernateConfigFile")); 
	    }
	    if (c.getInitParameter("JNDISourceLocation") != null) {       
	    	props.put("JNDISourceLocation",  c.getInitParameter("JNDISourceLocation")); 
	    }
	  }
		@SuppressWarnings("unused")
		ConfigurationInit config = new ConfigurationInit(props);
	}
}
