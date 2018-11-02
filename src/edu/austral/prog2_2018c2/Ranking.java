package edu.austral.prog2_2018c2;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking {
    private String fileName = "F:\\Escritorio\\SpaceInvadersF\\src\\Ranking.txt";
    private List<Score> ranking;
    private int rankingSize = 10;
    private String delimiter = "-";

    public Ranking() {
        ranking = new ArrayList<>(rankingSize);
        BufferedReader Reader = null;
        FileReader rankingReader = null;
        try {
            rankingReader = new FileReader(fileName);
            Reader = new BufferedReader(rankingReader);
            String strCurrentLine;

            while ((strCurrentLine = Reader.readLine()) != null) {

                Score temp;
                String[] parts = strCurrentLine.split(delimiter);
                String name = parts[0];
                int score = Integer.parseInt(parts[1]);
                temp = new Score(name, score);
                ranking.add(temp);
            }
            ranking.sort(Comparator.comparingDouble(Score::getPoints).reversed());

            Reader.close();
        } catch (IOException e) {
            System.out.println("File not Found");
        }
    }

    public void addScore(Score score) {
        if (isHighScore(score)) {
            int points = score.getPoints();
            FileWriter fileWriter = null;
            BufferedWriter writer = null;
            int temp =0;

            try {
                fileWriter = new FileWriter(fileName, true);
                writer = new BufferedWriter(fileWriter);
                writer.newLine();
                writer.write("asd" + "-" + "24");
                writer.flush();

            } catch (IOException e) {

            }
        }
    }

    public void deleteWorstScore(){
        String inputFileName = fileName;
        String outputFileName = "F:\\Escritorio\\SpaceInvadersF\\src\\temp.txt";
        String lineToRemove = ranking.get(ranking.size()-1).getName()+"-"+ranking.get(ranking.size()-1).getPoints();

        try {
            File inputFile = new File(inputFileName);
            File outputFile = new File(outputFileName);

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (!line.equals(lineToRemove)) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }

            if (inputFile.delete()) {

                if (!outputFile.renameTo(inputFile)) {
                    throw new IOException("Could not rename " + outputFileName + " to " + inputFileName);
                }
            } else {
                throw new IOException("Could not delete original input file " + inputFileName);
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }
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
