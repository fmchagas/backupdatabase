package br.com.fmchagas.backupdatabase.config;

import java.io.File;
import java.io.FileFilter;

import org.apache.log4j.Logger;

public class GerenciarQuantidadeBackup {
	private static Logger logger = Logger.getLogger(GerenciarQuantidadeBackup.class);

	private ConfigurationProperties conf;

	public GerenciarQuantidadeBackup(ConfigurationProperties conf) {
		this.conf = conf;
	}

	public void gerenciarQuantidade() {
		if (getQuantidadeFiles(getFileFiltrado()) > 2) {
			// TODO-se qtd > 3 remover a mais antiga
			logger.info("Qtd: " + getQuantidadeFiles(getFileFiltrado()) + "= maior");
			removerFileAntigo(getFileFiltrado());
		}
	}

	private void removerFileAntigo(File[] files) {
		// TODO Auto-generated method stub

	}

	private File[] getFileFiltrado() {
		File dir = new File(conf.getPathBackup());

		File[] files = dir.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(".backup");
			}
		});
		
		return files;
	}

	private Integer getQuantidadeFiles(File[] files) {
		return files.length;
	}

}
