package project660;

import java.io.File;

public class Config {
	public String rootUrl = "/project660";
	public String dataPath = "data" + File.separator;
	public String rootDir = "";
	private static Config self = null;

	public void setRootDir(String root) {
		this.rootDir = normalize(root);
	}

	public static Config getInstance() {
		if (self == null) {
			self = new Config();
		}

		return self;
	}

	protected Config() {
	}

	String normalize(String root) {
		String s = root;

		if (!s.endsWith(File.separator)) {
			s += File.separator;
		}

		return s;
	}

}
