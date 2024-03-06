package nl.tue.buildingsmart.emf;

/******************************************************************************
 * Copyright (C) 2009-2016  BIMserver.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see {@literal<http://www.gnu.org/licenses/>}.
 *****************************************************************************/

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.tue.buildingsmart.express.parser.ExpressSchemaParser;
import nl.tue.buildingsmart.schema.SchemaDefinition;

public class SchemaLoader {
	private static final Logger LOGGER = LoggerFactory.getLogger(SchemaLoader.class);
	public static final File DEFAULT_SCHEMA_FILE = new File("../buildingSMARTLibrary/schema" + File.separator + "IFC2X3_TC1.exp");

//	public static void main(String[] args) {
//		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
//			@Override
//			public void uncaughtException(Thread arg0, Throwable arg1) {
//				arg1.printStackTrace();
//			}
//		});
//		loadDefaultSchema();
//	}
	
	private SchemaLoader() {
	}
	
	public static SchemaDefinition loadIfc2x3tc1() throws IOException {
		return load("IFC2X3_TC1.exp");
	}

	public static SchemaDefinition loadIfc4() throws IOException {
		return load("IFC4_ADD2.exp");
	}

	public static SchemaDefinition loadIfc4x3add2() throws IOException {
		return load("IFC4X3_ADD2.exp");
	}

	private static SchemaDefinition load(String name) throws IOException {
		InputStream inputStream = SchemaLoader.class.getResourceAsStream("/schema/" + name);
		if (inputStream != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(inputStream, baos);
			
			SchemaDefinition schema = null;
			
			ExpressSchemaParser schemaParser = new ExpressSchemaParser(new ByteArrayInputStream(baos.toByteArray()));
			schemaParser.parse();
			schema = schemaParser.getSchema();
			schema.setSchemaData(baos.toByteArray());
			
			new DerivedReader(new ByteArrayInputStream(baos.toByteArray()), schema);
			if (schema != null) {
				LOGGER.info("IFC-Schema successfully loaded from " + name);
			}
			return schema;
		} else {
			LOGGER.error("Schema not found: " + name);
		}
		return null;
	}
	
//	public static SchemaDefinition loadDefaultSchema() {
//		return loadSchema(DEFAULT_SCHEMA_FILE);
//	}
//
//	public static SchemaDefinition loadSchema(InputStream inputStream) {
//		ExpressSchemaParser schemaParser = new ExpressSchemaParser(inputStream);
//		schemaParser.parse();
//		SchemaDefinition schema = schemaParser.getSchema();
//		try {
//			new DerivedReader(schemaFile, schema);
//		} catch (FileNotFoundException e) {
//			LOGGER.error("", e);
//		}
//		return schema;
//	}
}
