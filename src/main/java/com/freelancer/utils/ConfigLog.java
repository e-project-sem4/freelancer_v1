package com.freelancer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.swing.JToolBar.Separator;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConfigLog {
	static Logger logger;
	// file log4j.properties dùng để cấu hình log(vị trí đặt file log, file log có cuộn theo giờ hay không,...)
	static Properties p = new Properties();

	public static Logger getLogger(Object obj) {
		try {
			String helper = new File("").getAbsolutePath();
			p.load(new FileInputStream(helper + File.separator + "log4j.properties"));//dọc file log4j ở ngay trong project
			logger = Logger.getLogger(obj.getClass());
			PropertyConfigurator.configure(p);
			return logger;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
