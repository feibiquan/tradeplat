package com.xfpay.utils.common;


import au.com.bytecode.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipSCVReader {

	public static void main(String[] args) throws Exception {
		String fileStr = "D:\\fund_bill_2017-03-10.zip";
		readZipFile(fileStr, (t) -> {
			return true;
		});
		// readCsv();
	}

	@SuppressWarnings("resource")
	public static List<String[]> readCsv(InputStreamReader reader) throws IOException {
		CSVReader csvReader = new CSVReader(reader, ',', '\'', 1);
		String[] s = null;
		List<String[]> rows = new ArrayList<>();
		while ((s = csvReader.readNext()) != null) {
			rows.add(s);
		}
		return rows;
	}

	@SuppressWarnings("resource")
	public static void readCsv() throws IOException {
		File tempFile = File.createTempFile("temp", ".csv");
		DataInputStream in = new DataInputStream(new FileInputStream(tempFile));
		// Reader reader = new FileReader(csvFilePath);
		CSVReader csvReader = new CSVReader(new InputStreamReader(in, "GBK"), ',', '\'', 1);
		String[] s = null;
		while ((s = csvReader.readNext()) != null) {
			String line = "";
			System.out.println(s);
			for (int i = 0; i < s.length; i++) {
				line += "[" + i + "]:" + s[i] + ",";
			}
			System.out.println(line);
		}
	}

	public static List<String[]> readZipFile(String file, Function<String, Boolean> isRadCall) {
		InputStream in = null;
		ZipFile zf = null;
		try {
			zf = new ZipFile(file);
			in = new BufferedInputStream(new FileInputStream(file));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry ze;
			List<String[]> all = new ArrayList<>();
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.isDirectory()) {
				} else {
					if (isRadCall.apply(ze.getName()) && ze.getSize() > 0) {
						all.addAll(readCsv(new InputStreamReader(zf.getInputStream(ze), "UTF-8")));
					}
				}
			}
			zin.closeEntry();
			return all;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (zf != null)
					zf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void redcsv() {
		File csv = new File("D:\\20884217594140140156_20170310_DETAILS.csv"); // CSV文件路径
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		String everyLine = "";
		try {
			List<String> allString = new ArrayList<>();
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
			{
				everyLine = line;
				System.out.println(everyLine);
				allString.add(everyLine);
			}
			System.out.println("csv表格中所有行数：" + allString.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
