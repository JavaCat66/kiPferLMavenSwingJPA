package com.javahelps.kiPferL.model.kiUtils.kiStringUtils;

import java.text.DecimalFormat;

/**
 * UtilityKlasse zum Prüfen von Strings        -->     wurde im Zuge der Version 3.0 hinzugefügt
 *
 * @author Susanna Gruber
 * @version Alpha 1.0 2020.12.26
 * @since 1.14
 */
public class KiStringUtils {

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // ID erzeugen
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public static String idIncrement(String pPraefix, int pId) {
        return pPraefix + new DecimalFormat("00000").format(pId);
    }

    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    // EMAIL-ADRESSE AUF RICHTIGKEIT PRÜFEN
    // §§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§
    public static boolean verifyEmailAddress(String pEmailAddress) {
        boolean correct = true;
        if(pEmailAddress != null && !pEmailAddress.isEmpty()) {
            // **************************************************************************************************************************************************************************
            // überprüfung, ob die emailAdresse ein @ enthält und keine leerzeichen
            // **************************************************************************************************************************************************************************
            if(pEmailAddress.contains("@") && pEmailAddress.indexOf(' ') == -1) {
                // erlaubte zeichen teil vor @ (local)
                char [] permissibleCharacterLocal =
                        {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                                '!', '#', '$', '%', '&', '\'', '*', '+', '-', '/', '=', '?', '^', '_', '`', '{', '|', '}', '~', '.'};
                // **********************************************************************************************************************************************************************
                // der localTeil wird auf gültige zeichen überprüft
                // **********************************************************************************************************************************************************************
                String localPart = pEmailAddress.substring(0, pEmailAddress.indexOf("@"));
                char [] character = localPart.toCharArray();
                for(char c : character) {
                    boolean permissible = false;
                    for(char c1 : permissibleCharacterLocal) {
                        if(c == c1) {
                            permissible = true;
                            break;
                        }
                    }
                    if(!permissible) {
                        correct = false;
                        break;
                    }
                }
                // **********************************************************************************************************************************************************************
                // wenn keine ungültigen zeichen gefunden wurden, wird der domainTeil überprüft
                // **********************************************************************************************************************************************************************
                if(correct) {
                    // erlaubte zeichen teil nach @ (domain)
                    char [] permissibleCharacterDomain =
                            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};
                    // teil nach dem @
                    String domainPart = pEmailAddress.substring(pEmailAddress.indexOf("@") + 1);
                    // überprüfung, ob ein punkt vorhanden und nicht an letzter stelle ist
                    if(domainPart.contains(".") && domainPart.lastIndexOf(".") < domainPart.length() - 1) {
                        // überprüfung auf gültige zeichen
                        character = domainPart.toCharArray();
                        for(char c : character) {
                            if(c != '.') {
                                boolean permissible = false;
                                for(char c1 : permissibleCharacterDomain) {
                                    if(c == c1) {
                                        permissible = true;
                                        break;
                                    }
                                }
                                if(!permissible) {
                                    correct = false;
                                    break;
                                }
                            }
                        }
                    } else {
                        correct = false;
                    }
                }
            } else {
                correct = false;
            }
        }
        return correct;
    }
}