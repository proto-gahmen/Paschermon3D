import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Leaderboard{

    String path;
    File save;
    String input = "input";
    String output;

    public Leaderboard(){  save = new File("Save.txt"); }

    public void hinzufügen(Double zeit){

        JOptionPane.showMessageDialog(null, "Möchtest du dich in die Bestenliste eintragen?");
        do{   input = JOptionPane.showInputDialog(null, "Name eingeben.", "Name", JOptionPane.PLAIN_MESSAGE);
            if(input == null){    JOptionPane.showMessageDialog(null, "Geben sie einen Namen ein");
                continue;
            }
        }while (input == null);

        if(input.isEmpty()){   input = "_";  }

        JOptionPane.showMessageDialog(null, "Danke "+ input);

        try {
            FileWriter writer = new FileWriter("Save.txt", true);
            writer.write(input + " ");
            writer.write(String.valueOf(zeit) + "s");
            writer.write("\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void anzeigen(){
        if(save.exists()){
            try {
                Scanner reader = new Scanner(save);
                while (reader.hasNext()){     output = reader.nextLine();
                    System.out.println(output);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void löschen(){
        try {
            new FileWriter("Save.txt", false).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
