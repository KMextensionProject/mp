package sk.mkrajcovic.mp;

import java.io.File;

public class Song {
	private final String path;
	private int repeatCount;

	public Song(String path, int repeatCount) {
		this.path = path;
		this.repeatCount = repeatCount;
	}

	public String getPath() {
		return path;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	@Override
	public String toString() {
		return path.substring(path.lastIndexOf(File.separator) + 1) + " (repeat: " + repeatCount + ")";
	}
}