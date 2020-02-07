package com.gargamel113.charles;

import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        Charles charles = Charles.getCharles();


        drawCharles();
        System.out.println("===============================================================\n" +
                "HELLO SIR! MY NAME IS CHARLES\n" +
                "I WILL HELP YOU SORT YOUR PICTURES BASED ON DAY, MONTH AND YEAR\n" +
                "===============================================================\n" +
                "IF YOU WOULD BE SO KIND TO POINT ME TO THE FOLDER WHERE YOU KEEP YOUR\n" +
                "FILES. I WOULD BE DELIGHTED TO SORT THEM FOR YOU!" +
                "I WILL GIVE YOU AN EXAMPLE OF AN ADDRESS FOR YOU SIR!\n" +
                "C:/Users/name/desktop/pictures");
        System.out.print("Plesae Enter full address here: ");

        String path = input.nextLine();

        charles.sortFiles(path);
    }

    public static void drawCharles() {
        System.out.println("       .#####.\n" +
                "       |_____|\n" +
                "      (\\#/ \\#/)\n" +
                "       |  U  |\n" +
                "       \\  _  /\n" +
                "        \\___/\n" +
                "    .---'   `---.\n" +
                "   /  #########  \\\n" +
                "  /  |####|####|  \\\n" +
                " /  /\\ ####### /\\  \\\n" +
                "(  \\  \\  ###  /  /  )\n" +
                " \\  \\  \\_###_/  /  /\n" +
                "  \\  \\ |\\   /| /  /\n" +
                "   'uuu| \\_/ |uuu'\n" +
                "       |  |  |\n" +
                "       |  |  |\n" +
                "       |  |  |\n" +
                "       |  |  |\n" +
                "  dp   |  |  |\n" +
                "       )  |  (\n" +
                "     .oooO Oooo.\n" +
                "\n" +
                "------------------------------------------------");
    }
}
