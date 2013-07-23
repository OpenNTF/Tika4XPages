package biz.webgate.tika;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

import biz.webgate.dominoext.tika.beans.TikaBean;

import com.ibm.xsp.component.UIFileuploadEx.UploadedFile;
import com.ibm.xsp.http.IUploadedFile;

public class FileEvaluation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UploadedFile m_File;
	private HashMap<String, String> m_Metadata;
	private UploadedFile last_File;

	/*
	 * public static HashMap<String, String> extractText(UploadedFile file)
	 * throws FileNotFoundException, IOException { IUploadedFile FTemp =
	 * file.getUploadedFile(); File inputFile = FTemp.getServerFile();
	 * HashMap<String, String> s = TikaBean.extractText(inputFile);
	 * System.out.println(s); return s; }
	 */

	public void setFile(UploadedFile file) {
		m_File = file;
	}

	public UploadedFile getFile() {
		return m_File;
	}

	public void setMetadata(HashMap<String, String> metadata) {
		m_Metadata = metadata;
	}

	public HashMap<String, String> getMetadata() {
		if (m_File != null) {
			if (last_File != m_File) {
				last_File = m_File;
				IUploadedFile FTemp = m_File.getUploadedFile();
				File inputFile = FTemp.getServerFile();
				HashMap<String, String> s;
				try {
					System.out.println(TikaBean.get().getVersion());
					
					s = TikaBean.get().extractInfo(inputFile);
					m_Metadata = s;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return m_Metadata;
	}

}
