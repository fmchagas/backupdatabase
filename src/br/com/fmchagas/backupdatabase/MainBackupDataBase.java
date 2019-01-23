package br.com.fmchagas.backupdatabase;

import java.io.IOException;

import org.apache.log4j.Logger;

import br.com.fmchagas.backupdatabase.config.ConfigurationProperties;
import br.com.fmchagas.backupdatabase.postgresql.Backup;
import br.com.fmchagas.backupdatabase.util.ValidationException;

public class MainBackupDataBase {

	private static Logger logger = Logger.getLogger(MainBackupDataBase.class);

	public static void main(String[] args) {

		ConfigurationProperties cfgProp = new ConfigurationProperties();
		try {
			cfgProp.carregarProperties();
			
			Backup backup = new Backup(cfgProp);

			backup.startBackup();
			
		} catch (IOException | ValidationException e) {
			if (logger.isInfoEnabled()) {
				logger.info(e.getMessage());
			}
		}

	}

}
