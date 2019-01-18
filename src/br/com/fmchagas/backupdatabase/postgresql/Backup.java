package br.com.fmchagas.backupdatabase.postgresql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.fmchagas.backupdatabase.config.ConfigurationProperties;
import br.com.fmchagas.backupdatabase.util.DateTimeUtil;

public class Backup {

	private static Logger logger = Logger.getLogger(Backup.class);

	private ConfigurationProperties cfgProp;

	public Backup(ConfigurationProperties cfgProp) {
		this.cfgProp = cfgProp;
	}

	public void startBackup() {

		if (logger.isInfoEnabled()) {
			logger.info("Iniciando backup.");
		}

		ProcessBuilder pb = new ProcessBuilder(buildCommand());

		pb.environment().put("PGPASSWORD", cfgProp.getPassword());

		
		try {

			Process process = pb.start();

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = bufferedReader.readLine();

			while (line != null) {

				if (logger.isDebugEnabled()) {
					logger.debug(line);
				}

				line = bufferedReader.readLine();
			}

			bufferedReader.close();

			process.waitFor();
			process.destroy();

			if (logger.isInfoEnabled()) {
				logger.info("Fim do backup.");
			}
		} catch (IOException e) {
			logger.debug(e.getMessage());
		} catch (InterruptedException e) {
			logger.debug(e.getMessage());
		}

	}

	private List<String> buildCommand() {
		final List<String> command = new ArrayList<>();

		command.add(cfgProp.getDump());

		command.add("-h");// hoste alvo do backup
		command.add(cfgProp.getHost());

		command.add("-p");// --port, porta do BD
		command.add(cfgProp.getPort());

		command.add("-U");// --username, usuario do BD
		command.add(cfgProp.getUser());

		command.add("-F");// --format
		command.add("c");// custom

		command.add("-Z");// compression
		command.add("6");// 0 a 9(maximum)

		command.add("-b");// --blobs
		command.add("-v");// --verbose

		command.add("-f");// -f � caminho para salvar o dump
		command.add(getFullNameFile());

		command.add(cfgProp.getDataBaseName());// nome do banco de dados para backup
		return command;
	}

	private String getFullNameFile() {
		StringBuilder sb = new StringBuilder();
		sb.append(cfgProp.getPathBackup()).append("Dump_").append(cfgProp.getDataBaseName()).append("_")
				.append(DateTimeUtil.getDateTimeFomat()).append(".backup");

		if (logger.isInfoEnabled()) {
			logger.info("Salvando cópia de segurança em: " + sb.toString());
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Backup [cfgProp=");
		builder.append(cfgProp);
		builder.append("]");
		return builder.toString();
	}
	
}
