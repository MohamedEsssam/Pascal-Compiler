import java.io.File;
import java.util.Scanner;

/**
 * Created by Mohamed Essam on 4/22/2018.
 */
public class Tester {
    public static void main(String[] args) throws Exception {
        Regex regex=new Regex();
        Compiler compiler=new Compiler();
        long stat=System.currentTimeMillis();
        compiler.Compile(new File("src/Test.txt"));
        long end=System.currentTimeMillis();
        System.out.println("Time = "+(end-stat)+" ms ");
//     Scanner in=new Scanner(System.in);
//     String exp = in.nextLine();
//     regex.EXP_Match(exp);
    }
}
