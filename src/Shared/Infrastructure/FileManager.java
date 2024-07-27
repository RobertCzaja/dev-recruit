package Shared.Infrastructure;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    public void saveFile(String rawJsonPayload, String extension) {
        try {
            File dir = new File ("data/invalid");
            File file = new File(dir, "jjit_invalid."+extension);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(rawJsonPayload);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException("Error on saving invalid JSON from provider", e);
        }
    }
}
