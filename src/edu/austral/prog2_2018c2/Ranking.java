package edu.austral.prog2_2018c2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ranking {
    private String fileName = "F:\\Escritorio\\SpaceInvadersF\\src\\Ranking.txt";
    static StringBuffer stringBufferOfData = new StringBuffer();
    private List<Score> ranking;
    private int rankingSize = 10;
    private String delimiter = "-";

    public Ranking() {
        ranking = new ArrayList<>(rankingSize);
        BufferedReader rankingBuffered = null;
        FileReader rankingReader = null;
        try {
            rankingReader = new FileReader(fileName);
            rankingBuffered = new BufferedReader(rankingReader);
            String strCurrentLine;

            while ((strCurrentLine = rankingBuffered.readLine()) != null) {

                Score temp;
                String[] parts = strCurrentLine.split(delimiter);
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                temp = new Score(name, score);
                ranking.add(temp);
            }

            rankingBuffered.close();
        } catch (IOException e) {
            System.out.println("File not Found");
        }
    }

    public void addScore(Score score){
        if (isHighScore(score)){

            String oldContent = ranking.get(ranking.size()-1).getName()+"-"+ranking.get(ranking.size()-1).getPoints();
            ranking.remove(ranking.get(ranking.size()-1));

            BufferedReader reader = null;

            FileWriter writer = null;

            try
            {
                reader = new BufferedReader(new FileReader(fileName));
                String line = reader.readLine();

                while (line != null)
                {
                    oldContent = oldContent + line + System.lineSeparator();
                    line = reader.readLine();
                }

                String newContent = oldContent.replaceAll(oldContent, score.getName()+"-"+score.getPoints());
                writer = new FileWriter(fileName);
                writer.write(newContent);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
    public void replaceScore(Score score) {

        String lineToEdit = ranking.get(ranking.size()-1).getName()+"-"+ ranking.get(ranking.size()-1).getPoints();
        String replacementText = score.getName()+"-"+score.getPoints();

        int startIndex = stringBufferOfData.indexOf(lineToEdit);
        int endIndex = startIndex + lineToEdit.length();
        stringBufferOfData.replace(startIndex, endIndex, replacementText);
        System.out.println("Here is the new edited text:\n" + stringBufferOfData);
    }

    public boolean isHighScore(Score score){
        return (score.getPoints() > ranking.get(ranking.size()-1).getPoints());
    }


    public List<Score> getRanking() {
        return ranking;
    }



    public static class Score {
        private String name;
        private int points;


        public Score(String name, int points) {
            this.name = name;
            this.points = points;
        }

        public String getName() {
            return name;
        }

        public int getPoints() {
            return points;
        }
    }
}
