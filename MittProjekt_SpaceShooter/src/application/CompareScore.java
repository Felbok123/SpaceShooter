package application;

import java.util.Comparator;

import application.Score;

public class CompareScore implements Comparator<Score> {

	public int compare(Score score1, Score score2) {

		int sc1 = score1.getScore();
		int sc2 = score2.getScore();

		return Integer.compare(sc2, sc1);
	}

}
