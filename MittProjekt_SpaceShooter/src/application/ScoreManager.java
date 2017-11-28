package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ScoreManager {

	private static final String HIGHSCORE_FILE = "highscore.txt";

	private ArrayList<Score> scores;

	public ScoreManager() {
		scores = new ArrayList<>();
	}

	public ArrayList<Score> getScores() {
		loadFile();
		sort();
		return scores;
	}

	private void sort() {
		CompareScore comparator = new CompareScore();
		Collections.sort(scores, comparator);
	}

	public void addScore(String name, int score) {
		loadFile();
		scores.add(new Score(name, score));
		updateScoreFile();
	}

	@SuppressWarnings("unchecked")
	public void loadFile() {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));) {

			scores = (ArrayList<Score>) inputStream.readObject();

		} catch (FileNotFoundException e) {
			System.out.println("File not found " + e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("I/O Error: " + e.getMessage());
		}

	}

	public void updateScoreFile() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));) {

			outputStream.writeObject(scores);
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + e.getMessage());
		} catch (IOException e) {
			System.out.println("I/O Error: " + e.getMessage());

		}
	}

	public String getHighscoreString() {
		String highscore = "";
		int maxScores = 10;

		ArrayList<Score> scores;
		scores = getScores();

		int i = 0;
		int x = scores.size();
		if (x > maxScores) {
			x = maxScores;
		}
		while (i < x) {
			highscore += (i + 1) + "." + scores.get(i).getName() + "\t\t" + scores.get(i).getScore() + "\n";
			i++;
		}
		return highscore;
	}

}
