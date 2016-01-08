package nl.tue.buildingsmart.schema;

import org.bimserver.shared.exceptions.PluginException;

public class SchemaException extends PluginException {

	private static final long serialVersionUID = 4627067690205941773L;

	public SchemaException(String message) {
		super(message);
	}
}
