package com.youli.zbetuch.jingan.naire;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

	public static byte[] getBytesByStream(InputStream inStream) {
		ByteArrayOutputStream boutStream = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				boutStream.write(buffer, 0, len);
			}
			data = boutStream.toByteArray();
			inStream.close();
			boutStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
