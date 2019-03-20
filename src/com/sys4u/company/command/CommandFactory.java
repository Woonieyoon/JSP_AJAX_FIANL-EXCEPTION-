package com.sys4u.company.command;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);
	private final String commandPropertiesFilePath;
	private Map<String, Class<Command>> commandMap = new HashMap<>();
	
	public CommandFactory(String commandPropertiesFilePath) {
		this.commandPropertiesFilePath = commandPropertiesFilePath;
	}
	
	/**
	 * uri에 맞는 Command 인스턴스를 신규 생성해준다. 없으면 null을 리턴하니 받는 쪽에서 알아서 잘 처리해라.
	 * @param uri
	 * @return
	 */
	public Command createCommand(String uri) {
		try {
			LOGGER.debug("uri : " + uri);
			return commandMap.get(uri).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	
	public void init() throws ServletException{
		Properties properties = loadProperties();
		Iterator<Entry<Object, Object>> entries = properties.entrySet().iterator();
		loadCommands(entries);
	}

	@SuppressWarnings("unchecked")
	private void loadCommands(Iterator<Entry<Object, Object>> entries) throws ServletException {
		while(entries.hasNext()) {
			Entry<Object, Object> entry = entries.next();
			String uri = (String)entry.getKey();
			String commandName = (String)entry.getValue();
			
			Class<Command> commandClass;
			try {
				commandClass = (Class<Command>)this.getClass().getClassLoader().loadClass(commandName);
				commandMap.put(uri, commandClass);
			} catch (ClassNotFoundException e) {
				throw new ServletException(e);
			}
		}
	}

	private Properties loadProperties() throws ServletException {
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(new File(commandPropertiesFilePath)));
		} catch (IOException e) {
			throw new ServletException(e);
		}
		return properties;
	}
}
