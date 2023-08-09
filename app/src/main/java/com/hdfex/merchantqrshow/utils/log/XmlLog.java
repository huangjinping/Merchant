package com.hdfex.merchantqrshow.utils.log;

import android.text.TextUtils;
import android.util.Log;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XmlLog extends BaseLog {

    public static void printXml(String tag, String xml, String headString) {

        if (xml != null) {
            xml = XmlLog.formatXML(xml);
            xml = headString + "\n" + xml;
        } else {
            xml = headString + NULL_TIPS;
        }

        printLine(tag, true);
        String[] lines = xml.split(LINE_SEPARATOR);
        for (String line : lines) {
            if (!isEmpty(line)) {
                Log.d(tag, "║ " + line);
            }
        }
        printLine(tag, false);
    }

    public static String formatXML(String inputXML) {

        String xmls = "";
        if (TextUtils.isEmpty(inputXML)) {
            Log.d(XmlLog.class.getSimpleName(), "Empty/Null xml content");

            return xmls;
        }
        Source xmlInput = new StreamSource(new StringReader(inputXML));
        try {

            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            xmls = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            Log.e(XmlLog.class.getSimpleName(), e.toString() + "\n" + inputXML);
        }
        return xmls;
    }


}
