import com.sun.media.jai.widget.DisplayJAI;

import javax.media.jai.JAI;
import javax.media.jai.KernelJAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class App {
    private static PlanarImage binarize(PlanarImage img, double arg) {
        ParameterBlock pb = new ParameterBlock();
        pb.addSource(img);
        pb.add(arg);

        return JAI.create("binarize", pb);
    }

    private static PlanarImage dilate(PlanarImage img) {
        float[] e ={0, 0, 0, 0, 0, 0, 0,
                    0, 1, 1, 1, 1, 1, 0,
                    0, 1, 1, 1, 1, 1, 0,
                    0, 1, 1, 1, 1, 1, 0,
                    0, 1, 1, 1, 1, 1, 0,
                    0, 1, 1, 1, 1, 1, 0,
                    0, 0, 0, 0, 0, 0, 0 };

        ParameterBlock pd = new ParameterBlock();
        pd.addSource(img);
        pd.add(new KernelJAI(7, 7, e));

        return JAI.create("dilate", pd);
    }

    private static int[][] mapBinaryImage(PlanarImage img, int subSets) {
        int[][] imgMap = new int[subSets][subSets];

        BufferedImage bufferedImage = img.getAsBufferedImage();
        RandomIter randomIter = RandomIterFactory.create(bufferedImage, null);

        for (ImageSet imageSet : getImageSets(randomIter, bufferedImage, subSets)) {
            if (imageSet.getBlackPyxels()> 200) {
                imgMap[(int)Math.ceil((imageSet.getId()-1)/subSets)][imageSet.getId()%subSets] = -1;
            } else if (imageSet.getWhitePyxels() > 2000) {
                imgMap[(int)Math.ceil((imageSet.getId()-1)/subSets)][imageSet.getId()%subSets] = 0;
            } else {
                imgMap[(int)Math.ceil((imageSet.getId()-1)/subSets)][imageSet.getId()%subSets] = 1;
            }
        }

        return imgMap;
    }

    private static ArrayList<ImageSet> getImageSets(RandomIter iter, BufferedImage img, int subSets) {
        ArrayList<ImageSet> sets = new ArrayList<>();
        int[] pyxel = new int[3];
        int smallerValue = (img.getHeight() > img.getWidth()) ? img.getWidth() : img.getHeight();

        for (int i = 1; i < smallerValue; i++) {
            for (int j = 1; j < smallerValue; j++) {
                int a = (int)Math.ceil((double)i / (subSets*subSets));
                int b = (int)Math.ceil((double)j / (subSets*subSets));
                int index = a * b;

                ImageSet imageIndex = sets.stream()
                        .filter(im -> im.getId() == index)
                        .findAny()
                        .orElseGet(() -> new ImageSet(index));

                if (!sets.contains(imageIndex))
                    sets.add(imageIndex);

                iter.getPixel(j, i, pyxel);

                if (pyxel[0] == 0 && pyxel[1] == 0 && pyxel[2] == 0) {
                    imageIndex.incBlackPyxels();
                } else {
                    imageIndex.incWhitePyxels();
                }
            }
        }
        return sets;
    }

    private static void createFile(Path filePath, String fileContent) throws IOException {
        if (!Files.exists(filePath)) {
            FileWriter fileWriter = new FileWriter(filePath.toFile());
            try (BufferedWriter writer = new BufferedWriter(fileWriter)) {
                writer.write(fileContent);
            }
        }
    }

    public static void main(String[] args) {
        PlanarImage image = JAI.create("fileload", "Cenario.gif");
        PlanarImage dilatada = dilate(binarize(image, 150d));

        int[][] map = mapBinaryImage(dilatada, 7);

        StringBuilder stringBuilder = new StringBuilder(Arrays.deepToString(map));

        try {
            createFile(Paths.get("Cenario.txt"), stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Imagem");
        frame.add(new DisplayJAI(dilatada));
        frame.pack();
        frame.setVisible(true);
    }
}
