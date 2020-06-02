package com.rnd.agilityloyalty.core.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.rnd.agilityloyalty.core.constants.GlobalConstants;

/**
 * @author ajena
 */
public class CommonUtil {
    private CommonUtil() {
        //Restricting Instantiation
    }

    /**
     * Logger configuration for CommonUtil
     */
    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public static Object getObjectFromJson(final String jsonString, final Object obj) {
        final Gson gson = new Gson();
        Object returnValue = null;
        try {
            returnValue = gson.fromJson(jsonString, obj.getClass());
        } catch (final Exception e) {
            log.error("Exception occured in CommonUtil :: getObjectFromJson {}", e);
        }
        return returnValue;
    }

    public static String getJsonFromObject(final Object obj) {
        final Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(obj);
        } catch (final Exception e) {
            log.error("Exception occured in CommonUtil :: getJsonFromObject {}", e);
        }
        return json;
    }


    /**
     * Checks if is new window.
     *
     * @param newWindow the new window
     * @return the string
     */
    public static String isNewWindow(final String newWindow) {
        log.debug("In isNewWindow method of CommonUtil component.");
        if (StringUtils.isNotBlank(newWindow) && newWindow.equalsIgnoreCase(GlobalConstants.TRUE)) {
            return GlobalConstants.TARGET_BLANK;
        } else {
            return GlobalConstants.TARGET_SELF;
        }
    }

    public static String getFormRequestPayload(final SlingHttpServletRequest request) throws IOException {
        new Gson();
        final String body = request.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        log.debug(body);
        //JsonObject formData= gson.fromJson(body, JsonObject.class);
        return body;
    }

    public static String formatTimeStamp(final String timeStampString) {

        try {
            if (StringUtils.isNotEmpty(timeStampString)) {
                final Instant instant = Instant.parse(timeStampString);
                final LocalDateTime result = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
                return result.toLocalDate().toString();
            } else {
                return timeStampString;
            }
        } catch (final Exception e) {
            log.error("CommonUtil :: formatTimeStamp :: Exception {}", e);
        }
        return timeStampString;
    }

    public static String formatDate(final String dateString) {
   	 	final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
   	 	Date date = null;
		try {
			date = format.parse(dateString);
		} catch (final ParseException e) {
            log.error("CommonUtil :: formatDate :: Exception {}", e);
		}
   	 	return new SimpleDateFormat("MM/dd/yyyy").format(date);	
    }
}
