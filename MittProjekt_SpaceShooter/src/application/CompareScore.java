package application;

import java.util.Comparator;

import application.Score;

public class CompareScore implements Comparator<Score> {

	public int compare(Score score1, Score score2) {

		int sc1 = score1.getScore();
		int sc2 = score2.getScore();

		if (sc1 > sc2) {
			return -1;
		} else if (sc1 < sc2) {
			return 1;
		} else {
			return 0;
		}
	}

}
