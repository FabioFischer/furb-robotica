package examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorldMapResource {
    public static int[][] getWorldResource(String fileName) throws IOException {
        String[] mapRow = readFile(Paths.get(fileName)).split("([|])+");
        int[][] map = new int[mapRow.length][];

        for (int i = 0; i < mapRow.length; i++) {
            String[] mapCol = mapRow[i].split("([,])+");
            int[] tempCol = new int[mapCol.length];

            for (int j = 0; j < mapCol.length; j++) {
                tempCol[j] = Integer.parseInt(mapCol[j]);
            }
            map[i] = tempCol;
        }

        return map;
    }

    private static String readFile(Path filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(filePath.toUri())));
        StringBuilder str = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            str.append(line);
        }
        return str.toString();
    }
}
