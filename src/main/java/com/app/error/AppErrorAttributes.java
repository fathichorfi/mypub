package com.app.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class AppErrorAttributes extends DefaultErrorAttributes {

	@Autowired
	private MessageSource messageSource;

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
		errorAttributes.remove("trace");
		errorAttributes.remove("timestamp");
		errorAttributes.remove("error");
		errorAttributes.put("message", messageSource.getMessage("app.validation.ServerError", new Object[] {},
				LocaleContextHolder.getLocale()));
		errorAttributes.put("success", Boolean.FALSE);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		errorAttributes.put("dateTime", formatter.format(LocalDateTime.now()));
		 // Get the path of the requst
        String path = (String) webRequest.getAttribute("javax.servlet.error.request_uri", WebRequest.SCOPE_REQUEST);
        if (path == null) {
            path = (String) webRequest.getAttribute("javax.servlet.error.servlet_path", WebRequest.SCOPE_REQUEST);
        }
		//errorAttributes.put("path", path);
		errorAttributes.put("locale", webRequest.getLocale());
		errorAttributes.put("detail", getError(webRequest).getMessage());
		return errorAttributes;
	}

	/*
	 * private String message; private String success; private String status;
	 * 
	 * @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	 * private LocalDateTime dateTime; private String path; //@JsonIgnore private
	 * String locale; //@JsonIgnore private List<String> details;
	 */

}
