package sk.mkrajcovic.mp;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

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
			if (mediaPlayer != null)
				mediaPlayer.stop();
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
				}
			}

			Song song = new Song(file.getAbsolutePath(), repeat);
			playlist.addSong(song);
			playlistView.getItems().setAll(playlist.getSongs());
		}
	}
	
	// support reordering...

	private void playNext() {
		Song next = playlist.nextSong();
		// restart - go from 1st .. nevolat next ak bol playlist stopnuty, treba ist odzaciatku
		// volat resume/repeat toho isteho ak bolo pausenute
		if (next==null) {playlist.reset(); next=playlist.nextSong();}
//		if (next == null) {
			//zatial takto
//			showAlert("End of playlist!");
//			return;
//		}

//		if (mediaPlayer != null)
//			mediaPlayer.stop();

		Media media = new Media(new File(next.getPath()).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(this::playNext);
		mediaPlayer.play();
	}

	private void showAlert(String msg) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
		alert.showAndWait();
	}
}