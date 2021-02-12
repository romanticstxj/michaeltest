/***********************************************************************************************************************
 * FileName - ExpirationUtil.java
 * 
 * (c) Disney. All rights reserved.
 * 
 * $Author: ropeng $ 
 * $Revision: #1 $ 
 * $Change: 715510 $ 
 * $Date: Jul 8, 2015 $
 **********************************************************************************************************************/
package com.java.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Utility class for calculating time left to expiration date,  and judging whether expiration date is passed. Meanwhile,
 * there are other useful methods.
 * 
 * @author ropeng
 *
 */
public class PmwUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String REFUND_BATCH_CODE_DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    public static final String ZONEID_OF_SHANGHAI = "Asia/Shanghai";
    
    private static SecureRandom secureRandom = new SecureRandom();

    private PmwUtil() {
    }
    
    /**
     * judge whether the date time specified is after the target date time.
     * 
     * @param dateTime specified date time
     * @param target target date time
     * 
     * @return true - if the specified date time is after target date time
     *         false - if the specified date time is before target date time
     */
    public static boolean isAfter(String dateTime, Instant target) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(dateTime);
        
        Instant specificDate = Instant.from(temporalAccessor);
        
        return specificDate.isAfter(target);
    }
    
    /**
     * convert date to ISO-8601 complied date format. 
     * @param date original date
     * @return ISO-8601 complied date format
     */
//    public static String format(Date date) {
//        return format(date, DATE_TIME_FORMAT);
//    }
    
    public static String format(ZonedDateTime date) {
        return format(date, DATE_TIME_FORMAT);
    }
    
    public static String format(ZonedDateTime date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
    
    public static String format(LocalDate date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return date.format(formatter);
    }
    
//    @SuppressWarnings("PMD.SimpleDateFormatNeedsLocale")
//    public static String format(Date date, String pattern) {
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        return format.format(date);
//    }
    
    /**
     * convert date from original date format to target date format.
     * @param dateTime original date
     * @param originalFormat original date format
     * @param targetFormat target date format
     * @return converted date
     */
    public static String convert(String dateTime, String originalFormat, String targetFormat) {
        DateTimeFormatter originalDateTimeFormatter = DateTimeFormatter.ofPattern(originalFormat);
        TemporalAccessor temporalAccessor = originalDateTimeFormatter.parse(dateTime);
        
        DateTimeFormatter targetDateTimeFormatter = DateTimeFormatter.ofPattern(targetFormat);
        return targetDateTimeFormatter.format(temporalAccessor);
    }
    
    /**
     * convert timestamp from original time zone to target time zone.
     * @param timestamp timestamp
     * @param originalZone original time zone
     * @param targetZone target time zone
     * @return date in target time zone
     */
//    public static Date convert(String timestamp, ZoneId originalZone, ZoneId targetZone) {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_STAMP_FORMAT);
//        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(timestamp);
//        
//        ZonedDateTime originalZonedDateTime = ZonedDateTime.of(LocalDateTime.from(temporalAccessor), originalZone);
//        ZonedDateTime targetZonedDateTime = originalZonedDateTime.withZoneSameInstant(targetZone);
//        
//        return Date.from(targetZonedDateTime.toInstant());
//    }
    
    public static ZonedDateTime convert(String timestamp, ZoneId originalZone, ZoneId targetZone) {
        return convert(timestamp, originalZone, targetZone, TIME_STAMP_FORMAT);
    }
    
    public static ZonedDateTime convert(String timestamp, ZoneId originalZone, ZoneId targetZone, String format) {
    	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(timestamp);
        
        ZonedDateTime originalZonedDateTime = ZonedDateTime.of(LocalDateTime.from(temporalAccessor), originalZone);
        ZonedDateTime targetZonedDateTime = originalZonedDateTime.withZoneSameInstant(targetZone);
        
        return targetZonedDateTime;
    }
    
    /**
     * convert timestamp from China timezone to system default timezone.
     * @param timestamp timestamp in China timezone
     * @return date in system default timezone
     */
//    public static Date fromChinaZoneTimestamp(String timestamp) {
//        return convert(timestamp, ZoneId.of("Asia/Shanghai"), ZoneId.systemDefault());
//    }
    
    public static ZonedDateTime fromChinaZoneTimestamp(String timestamp) {
        return convert(timestamp, ZoneId.of(ZONEID_OF_SHANGHAI), ZoneId.systemDefault());
    }
    
    public static ZonedDateTime fromChinaZoneDateTime(String dateTime) {
        return convert(dateTime, ZoneId.of(ZONEID_OF_SHANGHAI), ZoneId.systemDefault(), DATE_TIME_FORMAT);
    }
    
    public static ZonedDateTime currentSystemDefaultTime() {
    	return ZonedDateTime.now(ZoneId.of(ZONEID_OF_SHANGHAI));
    }
    
    /**
     * convert timestamp from system default timezone to China timezone.
     * @param timestamp timestamp in system default timezone
     * @return timestamp in China timezone
     */
    public static String toChinaZoneTimestamp(String timestamp) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_STAMP_FORMAT);
        TemporalAccessor temporalAccessor = dateTimeFormatter.parse(timestamp);
        
        ZonedDateTime originalZonedDateTime = ZonedDateTime.of(LocalDateTime.from(temporalAccessor), ZoneId.systemDefault());
        ZonedDateTime targetZonedDateTime = originalZonedDateTime.withZoneSameInstant(ZoneId.of(ZONEID_OF_SHANGHAI));
        
        return dateTimeFormatter.format(targetZonedDateTime);
    }
    
    public static String toChinaZoneTimestamp(ZonedDateTime originalZonedDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(TIME_STAMP_FORMAT);
        
        ZonedDateTime targetZonedDateTime = originalZonedDateTime.withZoneSameInstant(ZoneId.of(ZONEID_OF_SHANGHAI));
        return dateTimeFormatter.format(targetZonedDateTime);
    }
    
    public static String getRandomSixNumber() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 6; i++) {
            builder.append((int)(secureRandom.nextDouble() * 10));
        }
        
        return builder.toString();
    }
    
    public static Map<String, String> xmlStringToMap(String xmlStr) throws ParserConfigurationException, IOException, 
    SAXException, XPathExpressionException {
    	return xmlStringToMap(xmlStr, "/xml/*");
    }

    public static Map<String, String> xmlStringToMap(String xmlStr, String xmlCompileRegexp) throws ParserConfigurationException, IOException,
        SAXException, XPathExpressionException {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        builder = factory.newDocumentBuilder();
        doc = builder.parse(new ByteArrayInputStream(xmlStr.getBytes()));
        
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        
        NodeList nl;
        XPathExpression tradeParamExpr = xpath.compile(xmlCompileRegexp);
        nl = (NodeList) tradeParamExpr.evaluate(doc, XPathConstants.NODESET);
        
        Map<String, String> parameterMap = new HashMap<String, String>();
        for(int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            parameterMap.put(node.getNodeName(), node.getTextContent());
        }
        
        return parameterMap;
    }
    
    /**
     * add extra map entries to original map
     * @param originalMap
     * @param extraMap
     */
    public static void mergeParametersMap(Map<String, String> originalMap, Map<String, String> extraMap) {
    	extraMap.forEach((key, value) -> {
    		originalMap.putIfAbsent(key, value);
		});
	}
    
    public static BigDecimal strToBigDecimal(String str) {
		return new BigDecimal(String.format("%.2f", Float.parseFloat(str)));
	}
	
	
	public static String bigDecimalToStr(BigDecimal bd) {
		return String.format("%.2f", bd);
	}
	
}
