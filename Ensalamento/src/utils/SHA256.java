package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
	public String desorganizar(String data) {
		byte[] crip = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] saltByte = "79C9B6F8E0B2417A84A9".getBytes("UTF-8");

			md.update(saltByte);
			md.update(data.getBytes("UTF-8"));
			crip = md.digest();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		StringBuilder build = new StringBuilder();
		for (byte b : crip) {
			build.append(String.format("%02X", 0xFF & b));
		}
		return build.toString();
	}
}
