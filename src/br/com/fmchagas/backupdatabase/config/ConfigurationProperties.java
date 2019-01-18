package br.com.fmchagas.backupdatabase.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import br.com.fmchagas.backupdatabase.util.StringUtils;
import br.com.fmchagas.backupdatabase.util.ValidationException;

public class ConfigurationProperties {

	private static Logger logger = Logger.getLogger(ConfigurationProperties.class);

	private Path fileConf;

	private String host;
	private String port;
	private String user;
	private String password;
	private String dump;
	private String pathBackup;
	private String dataBaseName;

	public ConfigurationProperties() {
		PropertyConfigurator.configure("log4j.properties");
		fileConf = Paths.get("config.properties");// está na raiz do projeto
	}

	public void carregarProperties() throws FileNotFoundException, IOException, ValidationException {
		Properties properties = new Properties();

		if (logger.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Carregando o arquivo de configuração: ").append(fileConf);
			logger.debug(sb);
		}

		properties.load(new FileInputStream(fileConf.toString()));

		host = properties.getProperty("database.host");
		port = properties.getProperty("database.port");
		user = properties.getProperty("database.user");
		password = properties.getProperty("database.password");
		dump = properties.getProperty("config.dump");
		pathBackup = properties.getProperty("config.pathBackup");
		dataBaseName = properties.getProperty("database.name");
		
		if (StringUtils.isEmpty(host)) {
			throw new ValidationException("Propriedade database.host=<valor> deve ser informada");
		}
		
		if (StringUtils.isEmpty(port)) {
			throw new ValidationException("Propriedade database.port=<valor> deve ser informada");
		}
		
		if (StringUtils.isEmpty(user)) {
			throw new ValidationException("Propriedade database.user=<valor> deve ser informada");
		}
		
		if (StringUtils.isEmpty(password)) {
			throw new ValidationException("Propriedade database.password=<valor> deve ser informada");
		}
		
		if (StringUtils.isEmpty(dump)) {
			throw new ValidationException("Propriedade config.dump=<valor> deve ser informada");
		}
		
		if (StringUtils.isEmpty(pathBackup)) {
			throw new ValidationException("Propriedade config.pathBackup=<valor> deve ser informada");
		}
		
		if (StringUtils.isEmpty(dataBaseName)) {
			throw new ValidationException("Propriedade database.name=<valor> deve ser informada");
		}
		
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getDump() {
		return dump;
	}

	public String getPathBackup() {
		return pathBackup;
	}

	public String getDataBaseName() {
		return dataBaseName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigurationProperties [fileConf=");
		builder.append(fileConf);
		builder.append(", host=");
		builder.append(host);
		builder.append(", port=");
		builder.append(port);
		builder.append(", user=");
		builder.append(user);
		builder.append(", password=");
		builder.append(password);
		builder.append(", dump=");
		builder.append(dump);
		builder.append(", pathBackup=");
		builder.append(pathBackup);
		builder.append(", dataBaseName=");
		builder.append(dataBaseName);
		builder.append("]");
		return builder.toString();
	}

}
