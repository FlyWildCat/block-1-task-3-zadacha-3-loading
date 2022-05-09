package ru.pda;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        final String saveDirPath = "E:\\Games\\savegames";
        final String zipFilePath = saveDirPath + "\\allsave.zip";
        final String saveFile2 = saveDirPath + "\\save2.dat";

        unZip(zipFilePath,saveDirPath);

        System.out.println(openProgress(saveFile2));
    }

    static void unZip(String archFilePath, String unZipDirPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(archFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                FileOutputStream fos = new FileOutputStream(unZipDirPath + "\\" + entry.getName());
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fos.write(c);
                }
                fos.flush();
                zis.closeEntry();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static GameProgress openProgress(String savedFilePath) {
        GameProgress gameProgress = null;
        try (FileInputStream  fis = new FileInputStream(savedFilePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
             gameProgress = (GameProgress) ois.readObject();
             } catch (Exception ex) {
             System.out.println(ex.getMessage());
             }
        return gameProgress;
    }
}
