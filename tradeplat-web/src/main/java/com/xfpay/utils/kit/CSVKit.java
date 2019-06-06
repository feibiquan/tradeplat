package com.xfpay.utils.kit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVKit {
	private static final String NEW_LINE_SEPARATOR = "\n";

	private FileWriter cfileWriter;

	private File file;

	public static File writeCsvFile(Object[] FILE_HEADER, List<List<Object>> list) {

		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		// 创建 CSVFormat
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		try {
			File file = File.createTempFile("tmp", ".csv");
			// 初始化FileWriter
			fileWriter = new FileWriter(file);
			// 初始化 CSVPrinter
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			// 创建CSV文件头
			csvFilePrinter.printRecord(FILE_HEADER);

			for (List<Object> dataRecord : list) {
				csvFilePrinter.printRecord(dataRecord);
			}
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
				csvFilePrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CSVKit buildCSVKit(Object[] FILE_HEADER) {
		CSVKit kit = new CSVKit();
		// 创建 CSVFormat
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		try {
			kit.file = File.createTempFile("tmp", ".csv");
			// 初始化FileWriter
			kit.cfileWriter = new FileWriter(kit.file);
			// 初始化 CSVPrinter
			kit.csvFilePrinter = new CSVPrinter(kit.cfileWriter, csvFileFormat);

			// 创建CSV文件头
			kit.csvFilePrinter.printRecord(FILE_HEADER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kit;
	}

	private CSVPrinter csvFilePrinter;

	public File end() {
		try {
			this.cfileWriter.flush();
			this.cfileWriter.close();
			this.csvFilePrinter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public CSVKit append(List<List<Object>> list) throws IOException {
		for (List<Object> dataRecord : list) {
			this.csvFilePrinter.printRecord(dataRecord);
		}
		return this;
	}
}
