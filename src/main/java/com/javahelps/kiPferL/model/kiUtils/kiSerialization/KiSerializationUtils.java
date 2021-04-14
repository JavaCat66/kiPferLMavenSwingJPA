package com.javahelps.kiPferL.model.kiUtils.kiSerialization;

import com.javahelps.kiPferL.model.kiTableObject.kiParameter.KiParameter;
import com.javahelps.kiPferL.model.kiTableObject.kiPerson.kiUser.KiUser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * UtilityKlasse zur Serialisierung von verschiedenen Objekten und Objekt-Listen
 * Nicht vorhandene AblageVerzeichnisse innerhalb des Projects werden erstellt
 *
 * @author Susanna Gruber
 * @version Alpha 2.0 2020.01.09      -->     Neue Methoden void doSerializationKiUser(String pPath, String pFile, KiUser pKiUser)
 *                                              und KiUser doDeSerializationKiUser(String pPath)
 *                                            -->     wird beim Login verwendet, damit von überall auf die UserDaten zugegriffen werden kann
 * version 3.2 2021.01.14
 * @since 1.14
 */
public class KiSerializationUtils {

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KiUser
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public static String doSerializationKiUser(String pPath, String pFile, KiUser pKiUser) {
        StringBuilder temp = new StringBuilder();
        // Pfad des Verzeichnisses, in das das File gelegt werden soll (wird erzeugt, falls nicht vorhanden)
        Path myPath = Paths.get(pPath);
        if (!Files.exists(myPath)) {
            try {
                // Verzeichnis erstellen
                Files.createDirectories(myPath);
                // Systemmeldung
                temp.append("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiUser: \n\tVerzeichnis ").append(pPath).append(" wurde angelegt.\n");
            } catch (IOException e) {
                // Systemmeldung FEHLER
                temp.append("FEHLER -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiUser: \n\tFEHLER BEIM ERSTELLEN DES VERZEICHNISSES ").append
                        (pPath).append(" -> ").append(e.toString());
            }
        }
        if (Files.exists(myPath)) {
            try {
                FileOutputStream fos = new FileOutputStream(pPath + pFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(pKiUser);
                oos.close();
                fos.close();
                temp.append("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiUser: ");
                temp.append("\n\tSerialisierter com.javahelps.kiPferL.model.kiTableObject.KiUser wurden in ").append(pPath).append(pFile).append(" gespeichert.");
            } catch (Exception e) {
                temp.append("FEHLER -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiUser: \n\tFEHLER BEIM SERIALISIEREN VON ").append
                        (pPath).append(pFile).append(" -> ").append(e.toString());
            }
        }
        return temp.toString();
    }
    // ****************************************************************************************************************************************************************************************
    public static KiUser doDeSerializationKiUser(String pPath) {
        KiUser temp = null;
        Path myPath = Paths.get(pPath);
        if (Files.exists(myPath)) {
            try {
                FileInputStream fis = new FileInputStream(pPath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                temp = (KiUser) ois.readObject();
                // ArrayList<KiParameter> auf NULL abfragen
                if(temp == null) {
                    System.out.println("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doDeSerializationKiParamter: " +
                            "Nichts zur Deserialisierung da. " + pPath + " muss erstellt und serialisiert werden.");
                } else {
                    System.out.println("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doDeSerializationKiParamter: " +
                            "Serialisierte Berechtigungen wurden aus " + pPath + " ausgelesen");
                }
                ois.close();
                fis.close();
            } catch (Exception e) {
                System.out.println("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doDeSerializationKiParamter: " +
                        "FEHLER BEIM DESERIALISIEREN VON " + pPath + " -> " + e.toString());
            }
        }
        return temp;
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // KiParameter
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public static String doSerializationKiParameter(String pPath, String pFile, List<KiParameter> pList) {
        StringBuilder temp = new StringBuilder();
        // Pfad des Verzeichnisses, in das das File gelegt werden soll (wird erzeugt, falls nicht vorhanden)
        Path myPath = Paths.get(pPath);
        if (!Files.exists(myPath)) {
            try {
                // Verzeichnis erstellen
                Files.createDirectories(myPath);
                // Systemmeldung
                temp.append("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiParamter: Verzeichnis ").append(pPath).append(" wurde angelegt.\n");
            } catch (IOException e) {
                // Systemmeldung FEHLER
                temp.append("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiParamter: FEHLER BEIM ERSTELLEN DES VERZEICHNISSES ").append
                        (pPath).append(" -> ").append(e.toString());
            }
        }
        if (Files.exists(myPath)) {
            try {
                FileOutputStream fos = new FileOutputStream(pPath + pFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(pList);
                oos.close();
                fos.close();
                temp.append("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiParamter: ");
                temp.append("Serialisierte model.kiTableObject.KiParameter-Liste wurden in ").append(pPath).append(pFile).append(" gespeichert.");
            } catch (Exception e) {
                temp.append("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doSerializationKiParamter: FEHLER BEIM SERIALISIEREN VON ").append
                        (pPath).append(pFile).append(" -> ").append(e.toString());
            }
        }
        return temp.toString();
    }
    // ****************************************************************************************************************************************************************************************
    public static List<KiParameter> doDeSerializationKiParameter(String pPath) {
        List<KiParameter> temp = new ArrayList<>();
        Path myPath = Paths.get(pPath);
        if (Files.exists(myPath)) {
            try {
                FileInputStream fis = new FileInputStream(pPath);
                ObjectInputStream ois = new ObjectInputStream(fis);
                temp = (List<KiParameter>) ois.readObject();
                // ArrayList<KiParameter> auf NULL abfragen
                if(temp == null) {
                    System.out.println("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doDeSerializationKiParamter: " +
                            "Nichts zur Deserialisierung da. " + pPath + " muss erstellt und serialisiert werden.");
                } else {
                    System.out.println("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doDeSerializationKiParamter: " +
                            "Serialisierte Berechtigungen wurden aus " + pPath + " ausgelesen");
                }
                ois.close();
                fis.close();
            } catch (Exception e) {
                System.out.println("LOG -> com.javahelps.kiPferL.model.kiUtils.kiSerialization.KiSerializationUtils.doDeSerializationKiParamter: " +
                        "FEHLER BEIM DESERIALISIEREN VON " + pPath + " -> " + e.toString());
            }
        }
        return temp;
    }
}
