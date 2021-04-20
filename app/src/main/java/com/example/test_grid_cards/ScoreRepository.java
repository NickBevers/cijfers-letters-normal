package com.example.test_grid_cards;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

public class ScoreRepository {
    private ScoreDao scoreDao;
    private List<Score> allScores;

    public ScoreRepository(Application application){
        ScoreDatabase db = ScoreDatabase.getInstance(application);
        scoreDao= db.scoreDao();
        allScores = scoreDao.getAll();
    }

    public void insert (Score score){
        new InsertScoreAsync(scoreDao).execute(score);
    }

    private static class InsertScoreAsync extends AsyncTask<Score, Void, Void>{
        private ScoreDao scoreDao;
        private InsertScoreAsync(ScoreDao scoreDao){
            this.scoreDao = scoreDao;
        }

        @Override
        protected Void doInBackground(Score... scores) {
            scoreDao.insert(scores[0]);
            return null;
        }
    }
}
