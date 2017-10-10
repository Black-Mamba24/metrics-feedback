package com.zhaiyi.metrics_feedback.test.xml;

import com.zhaiyi.metrics_feedback.configuration.FeedbackConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by zhaiyi on 2017/10/10.
 */
public class ConfigurationsTest {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document document = dBuilder.parse(new File("/Users/zhaiyi/Desktop/metrics-feedback/src/test/java/com/zhaiyi/metrics_feedback/test/xml/Configurations.xml"));
        Element element = document.getDocumentElement();
        NodeList configurations = element.getChildNodes();

        for (int i = 0; i < configurations.getLength(); i++) {
            FeedbackConfiguration.Builder builder = new FeedbackConfiguration.Builder();
            Node configuration = configurations.item(i);
            NodeList params = configuration.getChildNodes();

            for (int j = 0; j < params.getLength(); j++) {
                Node param = params.item(j);
                Node value = param.getChildNodes().item(0);
                switch (param.getNodeName()) {
                    case "type":
                        break;
                    case "metricName":
                        break;
                    case "initialDelay":
                        break;
                    case "period":
                        break;
                    case "timeUnit":
                        break;
                    case "action":
                        break;
                    case "thresholds":
                        NodeList thresholds = param.getChildNodes();
                        for (int k = 0; k < thresholds.getLength(); k++) {
                            Node threshold = thresholds.item(k);
                            NodeList thresholdParams = threshold.getChildNodes();

                            String name = null;
                            String min = "0";
                            String max = null;
                            for (int l = 0; l < thresholdParams.getLength(); l++) {
                                Node thresholdParam = thresholdParams.item(l);
                                Node thresholdValue = thresholdParam.getChildNodes().item(0);
                                switch (thresholdParam.getNodeName()) {
                                    case "name":
                                        name = thresholdValue.getNodeValue();
//                                        System.out.println(thresholdValue.getNodeValue());
                                        break;
                                    case "min":
                                        min = thresholdValue.getNodeValue();
//                                        System.out.println(thresholdValue.getNodeValue());
                                        break;
                                    case "max":
                                        max = thresholdValue.getNodeValue();
//                                        System.out.println(thresholdValue.getNodeValue());
                                        break;
                                    default:
                                        //ignore
                                }
                            }
                            System.out.print(name + "  " +min  + "  "+ max);
                        }
                        break;
                    default:
                        //ignore
                }
            }
        }
    }
}
