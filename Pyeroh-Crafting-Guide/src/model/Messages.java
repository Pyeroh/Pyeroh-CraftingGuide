package model;

import java.beans.Beans;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

	private Messages() {
	}

	private static final String BUNDLE_NAME = "gui.lang"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = loadBundle();

	private static ResourceBundle loadBundle() {
		return ResourceBundle.getBundle(BUNDLE_NAME);
	}

	public static String getString(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle() : RESOURCE_BUNDLE;
			return bundle.getString(key);
		}
		catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}

	public static void reloadBundle() {
		RESOURCE_BUNDLE = loadBundle();
	}
}
