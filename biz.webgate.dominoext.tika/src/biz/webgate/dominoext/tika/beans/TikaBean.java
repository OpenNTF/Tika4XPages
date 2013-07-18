package biz.webgate.dominoext.tika.beans;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;

import javax.faces.context.FacesContext;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import biz.webgate.dominoext.tika.Activator;

public class TikaBean {

	public static final String BEAN_NAME = "tikaBean"; //$NON-NLS-1$

	public static TikaBean get(FacesContext context) {
		TikaBean bean = (TikaBean) context.getApplication()
				.getVariableResolver().resolveVariable(context, BEAN_NAME);
		return bean;
	}

	public static TikaBean get() {
		if(FacesContext.getCurrentInstance() == null)
			return new TikaBean();
		return get(FacesContext.getCurrentInstance());
	}

	public HashMap<String, String> extractInfo(File inputFile)
			throws FileNotFoundException, IOException {

		HashMap<String, String> rc = new HashMap<String, String>();

		// File inputFile = new File(fileName);
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				inputFile));
		Parser p = getAutoDetectParser();
		StringWriter writer = new StringWriter();
		Metadata metadata = new Metadata();
		metadata.set(TikaMetadataKeys.RESOURCE_NAME_KEY, inputFile.getName()); // fileName

		if (!parse(p, bis, writer, metadata))
			return null;

		for (String name : metadata.names()) {
			rc.put(name, metadata.get(name));
			// System.out.println(name + "->" + metadata.get(name));
		}

		bis.close();

		return rc;
		// return writer.toString();

	}

	private AutoDetectParser getAutoDetectParser() {
		AutoDetectParser adpRC = AccessController
				.doPrivileged(new PrivilegedAction<AutoDetectParser>() {

					public AutoDetectParser run() {
						try {
							AutoDetectParser adp = new AutoDetectParser();
							return adp;
						} catch (Exception e) {
							e.printStackTrace();
						}
						return null;
					}

				});
		return adpRC;
	}

	private boolean parse(final Parser p, final BufferedInputStream bis,
			final StringWriter writer, final Metadata metadata) {
		return AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
			public Boolean run() {
				try {
					p.parse(bis, new BodyContentHandler(writer), metadata,
							new ParseContext());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				return true; // nothing to return
			}
		});
	}

	public String getVersion() {
		try {
			String s = AccessController
					.doPrivileged(new PrivilegedAction<String>() {
						public String run() {
							if (Activator.getContext() != null) {
								Object o = Activator.getContext().getBundle()
										.getHeaders().get("Bundle-Version"); // $NON-NLS-1$
								if (o != null) {
									return o.toString();
								}
							}
							return null;
						}
					});
			if (s != null) {
				return s;
			} else {

			}
		} catch (SecurityException ex) {
		}
		return null;
	}

}
