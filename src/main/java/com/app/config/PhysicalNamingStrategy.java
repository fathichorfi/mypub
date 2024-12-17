package com.app.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class PhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	private static final String TABLE_PREFIX = "app_";

	@Override
	public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
		return new Identifier(TABLE_PREFIX + convertToSnakeCase(logicalName.getText()), logicalName.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment context) {
		String newName = convertToSnakeCase(logicalName.getText());
		return new Identifier(newName, logicalName.isQuoted());
	}

	private String convertToSnakeCase(String name) {
		return name.replaceAll("([a-z])([A-Z])", "$1_$2").replaceAll("([A-Z])([0-9])", "$1_$2")
				.replaceAll("([0-9])([A-Z])", "$1_$2").toLowerCase();
	}

}
