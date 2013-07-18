package biz.webgate.dominoext.tika.library;

import com.ibm.xsp.library.AbstractXspLibrary;

public class TikaLibrary extends AbstractXspLibrary {

	public String getLibraryId() {
		return "biz.webgate.dominoext.tika.library";
	}

	@Override
	public String[] getFacesConfigFiles() {
		String[] files = new String[] { "META-INF/tika-beans-faces-config.xml"};
		return files;
	}

	@Override
	public String getPluginId() {
		return "biz.webgate.dominoext.tika";
	}

	@Override
	public String[] getXspConfigFiles() {
		return new String[] {};
	}

	@Override
	public String getTagVersion() {
		return "1.0.0";
	}
}
