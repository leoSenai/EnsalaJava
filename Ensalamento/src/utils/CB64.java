package utils;

import java.util.Base64;

public class CB64 {

	public String organizar(String data) {
		return new String(Base64.getDecoder().decode(data));
	}

	public String desorganizar(String data) {
		return Base64.getEncoder().encodeToString(data.getBytes());
	}
}
