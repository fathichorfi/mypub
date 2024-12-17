package com.app.service;

import java.util.Map;

public interface JWTService {
	
	String generateJWT(Map<String, Object> claims,String subject);

}
