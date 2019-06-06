package com.xfpay.utils.common;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class XMLUtil {

	public static String map2XML(Map<String, String> map) {
		// 创建Document对象
		Document document = DocumentHelper.createDocument();
		// 创建根节点
		Element root = document.addElement("rfpay");

		for (String key : map.keySet()) {
			// 创建节点
			Element node = root.addElement(key);
			// 节点的值
			String value = map.get(key);
			if (StringUtils.isNotBlank(value)) {
				node.setText(value);
			} else {
				node.setText("");
			}

		}
		return document.asXML();
	}

	public static String map2MulXML(Map<String, Object> map) {
		// 创建Document对象
		Document document = DocumentHelper.createDocument();
		// 创建根节点
		Element root = document.addElement("rfpay");

		for (String key : map.keySet()) {
			// 创建节点
			Element node = root.addElement(key);
			// 节点的值
			Object obj = map.get(key);
			if (obj instanceof Map) {
				Map<String, String> submap = (Map<String, String>) obj;
				map2subxml(node, submap);
			} else if(obj instanceof String){
				String value = (String) obj;
				if (StringUtils.isNotBlank(value)) {
					node.setText(value);
				} else {
					node.setText("");
				}
			}else {
				String value = String.valueOf(obj);
				if (StringUtils.isNotBlank(value)) {
					node.setText(value);
				} else {
					node.setText("");
				}
			}

		}
		return document.asXML();
	}

	/**
	 * xml二级生成
	 *
	 * @param node
	 * @param map
	 */
	private static void map2subxml(Element node, Map<String, String> map) {

		for (String key : map.keySet()) {
			// 创建节点
			Element subNode = node.addElement(key);
			// 节点的值
			String value = map.get(key);
			if (StringUtils.isNotBlank(value)) {
				subNode.setText(value);
			} else {
				subNode.setText("");
			}

		}
	}

	public Map<String, String> xml2Map(String xmlstr, String charset) {
		try {
			SAXReader reader = new SAXReader(false);
			InputSource source = new InputSource(new ByteArrayInputStream(
					xmlstr.getBytes()));
			source.setEncoding(charset);
			Document doc = reader.read(source);
			TreeMap<String, String> params = toMap(doc.getRootElement());
			return params;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 转MAP
	 *
	 * @author
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TreeMap<String, String> toMap(Element element) {
		TreeMap<String, String> rest = new TreeMap<String, String>();
		List<Element> els = element.elements();
		for (Element el : els) {
			rest.put(el.getName().toLowerCase(), el.getTextTrim());
		}
		return rest;
	}

}
