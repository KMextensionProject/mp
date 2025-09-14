package sk.mkrajcovic.mp;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private final List<Song> songs = new ArrayList<>();
	private int currentIndex = -1;
	private int currentRepeatLeft = 0;

	public void addSong(Song song) {
		songs.add(song);
	}

	public Song getCurrentSong() {
		if (currentIndex >= 0 && currentIndex < songs.size()) {
			return songs.get(currentIndex);
		}
		return null;
	}

	public Song nextSong() {
		while (true) {
			if (currentIndex == -1 || currentRepeatLeft == 0) {
				currentIndex++;
				if (currentIndex >= songs.size())
					return null;
				currentRepeatLeft = songs.get(currentIndex).getRepeatCount();
			}

			if (currentRepeatLeft > 0) {
				currentRepeatLeft--;
				return songs.get(currentIndex);
			}
		}
	}

	public boolean hasNext() {
		return (currentIndex + 1 < songs.size()) || (currentRepeatLeft > 0);
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void reset() {
		currentIndex = -1;
		currentRepeatLeft = 0;
	}
}