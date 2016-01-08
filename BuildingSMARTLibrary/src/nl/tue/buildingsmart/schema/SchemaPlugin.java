package nl.tue.buildingsmart.schema;

import java.nio.file.Path;

import org.bimserver.plugins.Plugin;
import org.bimserver.plugins.PluginConfiguration;

public interface SchemaPlugin extends Plugin {
	SchemaDefinition getSchemaDefinition(PluginConfiguration pluginConfiguration);
	Path getExpressSchemaFile();
	String getSchemaVersion();
}