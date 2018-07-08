import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Mohamed Essam on 4/22/2018.
 */

public class Compiler {

    private Regex iMatch=new Regex();
    private String Exp;
    private String Check;

    public Compiler() throws IOException {
    }

    public void Compile(File F){
        try (Scanner reader = new Scanner(F)) {
            while (reader.hasNextLine()) {
                Exp=reader.nextLine();
                try {
                    iMatch.EXP_Match(Exp);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(0);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tokens file not found");
        }
        iMatch.Show();
        try {
            iMatch.Token_File(new File("src/Tokens.txt"));
            iMatch.LexOut(new File("src/Lexical.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



