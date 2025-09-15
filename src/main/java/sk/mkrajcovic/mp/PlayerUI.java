package sk.mkrajcovic.mp;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

public class PlayerUI {

	private MediaPlayer mediaPlayer;
	private final Playlist playlist = new Playlist();
	private final ListView<Song> playlistView = new ListView<>();

	public VBox createUI() {
		VBox root = new VBox(15);
		root.setPadding(new Insets(20));

		playlistView.setPrefHeight(120);

		Button addBtn = new Button("Add Song");
		addBtn.setOnAction(e -> addSong());

		Button playBtn = new Button("Play Playlist");
		playBtn.setOnAction(e -> playNext());

		Button stopBtn = new Button("Stop");
		stopBtn.setOnAction(e -> {
			if (mediaPlayer != null) {
				mediaPlayer.stop();
			}
		});

		HBox controls = new HBox(10, addBtn, playBtn, stopBtn);
		root.getChildren().addAll(new Label("Playlist:"), playlistView, controls);

		return root;
	}

	private void addSong() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
			TextInputDialog dialog = new TextInputDialog("1");
			dialog.setTitle("Repeat Count");
			dialog.setHeaderText("Set repeat count for this song (0 = skip)");
			dialog.setContentText("Repeat:");

			int repeat = 1;
			var result = dialog.showAndWait();
			if (result.isPresent()) {
				try {
					repeat = Integer.parseInt(result.get());
				} catch (NumberFormatException ignored) {
					ignored.printStackTrace();
				}
			}

			Song song = new Song(file.getAbsolutePath(), repeat);
			playlist.addSong(song);
			playlistView.getItems().setAll(playlist.getSongs());
		}
	}

	private void playNext() {
		Song next = playlist.nextSong();
		if (next == null) {
			playlist.reset();
			next = playlist.nextSong();
		}

		Media media = new Media(new File(next.getPath()).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(this::playNext);
		mediaPlayer.play();
	}
}