# Java Music Player

This project is a simple Java standalone music player with a unique feature that makes it different from other players.

The key idea is **custom repeat/skip control in the playlist**:

* Each song in the playlist has a **repeat count**.
* If the repeat count is set to **0**, the song will be skipped during playback, but it will **not be removed** from the playlist.
* If the repeat count is set to **N > 0**, the song will be played **N times in a row** before moving on to the next track.

This way, the playlist can be adapted to the listener’s mood without deleting or permanently modifying it. Songs you don’t feel like hearing can be skipped, while favorites can play multiple times, all within the same playlist session.
