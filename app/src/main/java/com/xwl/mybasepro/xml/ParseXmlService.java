package com.xwl.mybasepro.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ParseXmlService {

	public HashMap<String, String> parseXml(InputStream inStream)
			throws Exception {
		HashMap<String, String> hashMap = new HashMap<String, String>();

		// 实例化一个文档构建器工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// 通过文档构建器工厂获取一个文档构建器
		DocumentBuilder builder = factory.newDocumentBuilder();
		// 通过文档构建器构建一个文档实例
		Document document = builder.parse(inStream);
		// 获取XML文件根节点
		Element root = document.getDocumentElement();
		// 获得子节点
		NodeList childNodes = root.getChildNodes();
		for (int y = 0; y < childNodes.getLength(); y++) {
			if (childNodes.item(y).getNodeType() == Node.ELEMENT_NODE) {
				if ("h5CoursewareUrl".equals(childNodes.item(y).getNodeName())) {
					String h5CoursewareUrl = childNodes.item(y).getTextContent();
					hashMap.put("KJUrl", h5CoursewareUrl);
				}
			}
		}

		return hashMap;
	}
}
