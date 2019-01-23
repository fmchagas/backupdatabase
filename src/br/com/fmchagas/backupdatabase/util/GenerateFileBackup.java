package br.com.fmchagas.backupdatabase.util;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.Logger;

import br.com.fmchagas.backupdatabase.config.ConfigurationProperties;

public class GenerateFileBackup {
	private static Logger logger = Logger.getLogger(GenerateFileBackup.class);

	private ConfigurationProperties configurationProperties;

	public GenerateFileBackup(ConfigurationProperties configuration) {
		this.configurationProperties = configuration;
	}

	public void manageQuantity() {
		
		for (File file : getFileFiltrado()) {
			
			if (getFileFiltrado().length > 2 ) {
				removerFileAntigo(file.getAbsolutePath());
			}
		}
		
	}

	private File[] getFileFiltrado() {
		File dir = new File(configurationProperties.getPathBackup());

		File[] files = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(".backup");
			}
		});
		
		return files;
	}
	
	private void removerFileAntigo(String absolutePath) {
		File file = new File(absolutePath);
		
		if (file.exists()) {
			file.delete();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Backup \"" + file.getName() + "\" Removido.");
			}
		}
	}
	
	public String getFullNameBackup() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(configurationProperties.getPathBackup())
			.append("Dump-")
			.append(configurationProperties.getDataBaseName())
			.append("-")
			.append(DateTimeUtil.getDateTimeFomat())
			.append(".backup");

		if (logger.isInfoEnabled()) {
			logger.info("Saving backup at: " + sb.toString());
		}

		return sb.toString();
	}

}
