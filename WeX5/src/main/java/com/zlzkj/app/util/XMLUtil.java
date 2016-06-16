package com.zlzkj.app.util;

public class XMLUtil {

	public static String msgToXML(String phoneNumber, String smsContent, String from) {
		StringBuffer sbRoot = new StringBuffer();
		addReportRootHead(sbRoot);
			addReportRootNode(sbRoot, "msgtype", "sms");
			addReportRootNode(sbRoot, "from", from);
			addReportRootNode(sbRoot, "phoneNumber", phoneNumber);
			addReportRootNode(sbRoot, "content", smsContent);		
		addReportRootTail(sbRoot);
		return sbRoot.toString();
	}

	public static String mailToXML(String sendTo, String subject, String content, String from) {
		StringBuffer sbRoot = new StringBuffer();
		addReportRootHead(sbRoot);
			addReportRootNode(sbRoot, "msgtype", "email");
			addReportRootNode(sbRoot, "sendTo", sendTo);
			addReportRootNode(sbRoot, "subject", subject);
			addReportRootNode(sbRoot, "content", content);	
			addReportRootNode(sbRoot, "from", from);		
		addReportRootTail(sbRoot);
		return sbRoot.toString();
	}
	
	/**
	 * xml节点内容
	 * @param sbRoot
	 */
	private static void addReportRootNode(StringBuffer sbRoot, String nodeName, String content){
		sbRoot.append("<" + nodeName + ">" + content + "</" + nodeName + ">");
	}

	/**
	 * xml根节点定义开始
	 * @param sbRoot
	 */
	private static void addReportRootHead(StringBuffer sbRoot){
		sbRoot.append("<?xml version='1.0' encoding='gb2312'?><root>");
	}

	/**
	 * xml根节点定义结束
	 * @param sbRoot
	 */
	private static void addReportRootTail(StringBuffer sbRoot){
		sbRoot.append("</root>");
	}
	
	
}
