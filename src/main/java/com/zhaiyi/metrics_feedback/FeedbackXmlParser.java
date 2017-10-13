package com.zhaiyi.metrics_feedback;

import com.google.common.base.Preconditions;
import com.zhaiyi.metrics_feedback.action.Action;
import com.zhaiyi.metrics_feedback.configuration.FeedbackConfiguration;
import com.zhaiyi.metrics_feedback.constants.MetricType;
import com.zhaiyi.metrics_feedback.util.LogUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaiyi on 2017/10/10.
 */
public class FeedbackXmlParser {
    private static final String SCHEMA_LANGUAGE_ATTRIBUTE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    private static final String XSD_SCHEMA_LANGUAGE = "http://www.w3.org/2001/XMLSchema";

    public List<FeedbackConfiguration> parse(String configFile) throws ParserConfigurationException, IOException, SAXException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        factory.setAttribute(SCHEMA_LANGUAGE_ATTRIBUTE, XSD_SCHEMA_LANGUAGE);

        DocumentBuilder dBuilder = factory.newDocumentBuilder();
//        dBuilder.setEntityResolver(new EntityResolver() {
//            @Override
//            public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
//                if (systemId == null) {
//                    return null;
//                }
//                try {
//                    InputSource source = new InputSource(getClass().getResourceAsStream(systemId));
//                    source.setPublicId(publicId);
//                    source.setSystemId(systemId);
//                    return source;
//                } catch (Exception e) {
//                    return null;
//                }
//            }
//        });
        dBuilder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                LogUtil.getLogger().warn("SAXParse warning: {}", exception.toString());
            }

            @Override
            public void error(SAXParseException exception) throws SAXException {
                LogUtil.getLogger().error("SAXParse error: {}", exception.toString());
            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                LogUtil.getLogger().error("SAXParse fatalError: {}", exception.toString());
            }
        });

        Document document = dBuilder.parse(new File(configFile));
        Element element = document.getDocumentElement();
        NodeList configurations = element.getChildNodes();

        List<FeedbackConfiguration> list = new ArrayList<>();

        for (int i = 0; i < configurations.getLength(); i++) {
            FeedbackConfiguration.Builder builder = new FeedbackConfiguration.Builder();
            Node configuration = configurations.item(i);
            if (!"configuration".equals(configuration.getNodeName())) {
                continue;
            }
            NodeList params = configuration.getChildNodes();
            for (int j = 0; j < params.getLength(); j++) {
                Node param = params.item(j);
                Node value = param.getChildNodes().item(0);
                switch (param.getNodeName()) {
                    case "type":
                        setType(builder, value.getNodeValue().toUpperCase());
                        break;
                    case "metricName":
                        setMetricName(builder, value.getNodeValue());
                        break;
                    case "initialDelay":
                        setInitialDelay(builder, value.getNodeValue());
                        break;
                    case "period":
                        setPeriod(builder, value.getNodeValue());
                        break;
                    case "timeUnit":
                        setTimeUnit(builder, value.getNodeValue().toUpperCase());
                        break;
                    case "action":
                        setAction(builder, value.getNodeValue());
                        break;
                    case "thresholds":
                        NodeList thresholds = param.getChildNodes();
                        for (int k = 0; k < thresholds.getLength(); k++) {
                            Node threshold = thresholds.item(k);
                            if (!"threshold".equals(threshold.getNodeName())) {
                                continue;
                            }
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
                                        break;
                                    case "min":
                                        min = thresholdValue.getNodeValue();
                                        break;
                                    case "max":
                                        max = thresholdValue.getNodeValue();
                                        break;
                                    default:
                                        //ignore
                                }
                            }
                            setThreshold(builder, name, min, max);
                        }
                        break;
                    default:
                        //ignore
                }
            }
            list.add(builder.build());
        }
        return list;
    }

    private void setType(FeedbackConfiguration.Builder builder, String type) {
        Preconditions.checkNotNull(type);
        switch (type) {
            case "GAUGE":
                builder.type(MetricType.GAUGE);
                break;
            case "COUNTER":
                builder.type(MetricType.COUNTER);
                break;
            case "METER":
                builder.type(MetricType.METER);
                break;
            case "HISTOGRAM":
                builder.type(MetricType.HISTOGRAM);
                break;
            case "TIMER":
                builder.type(MetricType.TIMER);
                break;
            default:
                throw new IllegalArgumentException("metric type is wrong");
        }
    }

    private void setMetricName(FeedbackConfiguration.Builder builder, String metricName) {
        Preconditions.checkNotNull(metricName);
        builder.metricName(metricName);
    }

    private void setInitialDelay(FeedbackConfiguration.Builder builder, String initialDelay) {
        Preconditions.checkNotNull(initialDelay);
        builder.initialDelay(Integer.parseInt(initialDelay));
    }

    private void setPeriod(FeedbackConfiguration.Builder builder, String period) {
        Preconditions.checkNotNull(period);
        builder.period(Integer.parseInt(period));
    }

    private void setTimeUnit(FeedbackConfiguration.Builder builder, String timeUnit) {
        Preconditions.checkNotNull(timeUnit);
        switch (timeUnit) {
            case "NANOSECOND":
            case "NANOSECONDS":
                builder.timeUnit(TimeUnit.NANOSECONDS);
                break;
            case "MICROSECOND":
            case "MICROSECONDS":
                builder.timeUnit(TimeUnit.MICROSECONDS);
                break;
            case "MILLISECOND":
            case "MILLISECONDS":
                builder.timeUnit(TimeUnit.MILLISECONDS);
                break;
            case "SECOND":
            case "SECONDS":
                builder.timeUnit(TimeUnit.SECONDS);
                break;
            case "MINUTE":
            case "MINUTES":
                builder.timeUnit(TimeUnit.MINUTES);
                break;
            case "HOUR":
            case "HOURS":
                builder.timeUnit(TimeUnit.HOURS);
                break;
            case "DAY":
            case "DAYS":
                builder.timeUnit(TimeUnit.DAYS);
                break;
            default:
                throw new IllegalArgumentException("timeUnit is wrong");
        }
    }

    private void setThreshold(FeedbackConfiguration.Builder builder, String name, String min, String max) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(min);
        Preconditions.checkNotNull(max);
        builder.setThreshold(name, Double.parseDouble(min), Double.parseDouble(max));
    }

    private void setAction(FeedbackConfiguration.Builder builder, String action) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Preconditions.checkNotNull(action);
        Class<Action> clazz = (Class<Action>) Class.forName(action);
        builder.action(clazz.newInstance());
    }
}


