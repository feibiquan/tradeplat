package com.xfpay.utils.kit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
	private ZipUtils() {
		// empty
	}

	/**
	 * 压缩文件
	 * 
	 * @param filePath
	 *            待压缩的文件路径
	 * @return 压缩后的文件
	 */
	public static File zip(String filePath) {
		File target = null;
		File source = new File(filePath);
		if (source.exists()) {
			// 压缩文件名=源文件名.zip
			String zipName = source.getName() + ".zip";
			target = new File(source.getParent(), zipName);
			if (target.exists()) {
				target.delete(); // 删除旧的文件
			}
			FileOutputStream fos = null;
			ZipOutputStream zos = null;
			try {
				fos = new FileOutputStream(target);
				zos = new ZipOutputStream(new BufferedOutputStream(fos));
				// 添加对应的文件Entry
				addEntry("/", source, zos);
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOKit.closeQuietly(zos, fos);
			}
		}
		return target;
	}

	public static ZImage buildZImage(BufferedImage image, String filename) {
		return new ZImage(image, filename);
	}

	public static class ZImage {
		private BufferedImage image;
		private String filename;

		public ZImage(BufferedImage image, String filename) {
			super();
			this.image = image;
			this.filename = filename;
		}

		public BufferedImage getImage() {
			return image;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
		}

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

	}

	public static File zip(String filePath,String zipName, List<ZImage> images) {
		File target = null;
		File source = new File(filePath);
		
		if (!source.exists()) {
			source.mkdirs();
		}
		target = new File(source,zipName);
		if (target.exists()) {
			target.delete(); // 删除旧的文件
		}
		FileOutputStream fos = null;
		ZipOutputStream zos = null;
		try {
			fos = new FileOutputStream(target);
			zos = new ZipOutputStream(new BufferedOutputStream(fos));
			for (ZImage image : images) {
				zos.putNextEntry(new ZipEntry(image.getFilename()));
				ImageIO.write(image.getImage(), "JPG", zos);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOKit.closeQuietly(zos, fos);
		}

		return target;

	}

	/**
	 * 扫描添加文件Entry
	 * 
	 * @param base
	 *            基路径
	 * 
	 * @param source
	 *            源文件
	 * @param zos
	 *            Zip文件输出流
	 * @throws IOException
	 */
	private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
		// 按目录分级，形如：/aaa/bbb.txt
		String entry = base + source.getName();
		if (source.isDirectory()) {
			for (File file : source.listFiles()) {
				// 递归列出目录下的所有文件，添加文件Entry
				addEntry(entry + "/", file, zos);
			}
		} else {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				byte[] buffer = new byte[1024 * 10];
				fis = new FileInputStream(source);
				bis = new BufferedInputStream(fis, buffer.length);
				int read = 0;
				zos.putNextEntry(new ZipEntry(entry));
				while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
					zos.write(buffer, 0, read);
				}
				zos.closeEntry();
			} finally {
				IOKit.closeQuietly(bis, fis);
			}
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param filePath
	 *            压缩文件路径
	 */
	public static void unzip(String filePath) {
		File source = new File(filePath);
		if (source.exists()) {
			ZipInputStream zis = null;
			BufferedOutputStream bos = null;
			try {
				zis = new ZipInputStream(new FileInputStream(source));
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null && !entry.isDirectory()) {
					File target = new File(source.getParent(), entry.getName());
					if (!target.getParentFile().exists()) {
						// 创建文件父目录
						target.getParentFile().mkdirs();
					}
					// 写入文件
					bos = new BufferedOutputStream(new FileOutputStream(target));
					int read = 0;
					byte[] buffer = new byte[1024 * 10];
					while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
						bos.write(buffer, 0, read);
					}
					bos.flush();
				}
				zis.closeEntry();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				IOKit.closeQuietly(zis, bos);
			}
		}
	}

}
