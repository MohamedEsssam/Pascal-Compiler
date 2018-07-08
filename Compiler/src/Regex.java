import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mohamed Essam on 4/22/2018.
 */

public class Regex {
    private final Pattern ADD_EXP=Pattern.compile("^[\\s]*(?<ID>[a-zA-z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*(?<EXP>([a-zA-Z0-9]{1,}[\\s]*[+]*[\\s]*)+)$");
    private final Pattern SUB_EXP=Pattern.compile("^[\\s]*(?<ID>[a-zA-z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*(?<EXP>([a-zA-Z0-9]{1,}[\\s]*[-]*[\\s]*)+)$");
    private final Pattern DIV_EXP=Pattern.compile("^[\\s]*(?<ID>[a-zA-z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*(?<EXP>([a-zA-Z0-9]{1,}[\\s]*[\\/]*[\\s]*)+)$");
    private final Pattern Porgram_Name=Pattern.compile("^[\\s]{0,}PROGRAM(?<NAME>[\\s]{1,}[a-zA-Z]{1,})[\\s]{0,}$");
    private final Pattern Varibale_Type=Pattern.compile("^[\\s]{0,}(VAR)(?<VAR>[\\s]{1,}[\\n]{0,}[a-zA-Z][a-zA-Z0-9\\s,]*)[\\s]{1,}[:][\\s]{0,}(INTEGER)$");
    private final Pattern ADD_DIGIT=Pattern.compile("^[\\s]{0,}(?<ID>[a-zA-Z]{1}[\\w]{0,}[\\w]{0,})[\\s]{0,}(?<operation1>(:=){1})[\\s]{0,}(?<Digit1>[0-9]{1,})[\\s]{0,}(?<operation2>[+]{1})[\\s]{0,}(?<Digit2>[0-9]{1,})[\\s]{0,}[;]$");
    private final Pattern ADD_EXP_DIGIT=Pattern.compile("^[\\s]{0,}(?<ID>[a-zA-Z]{1}[\\w]{0,}[\\w]{0,})[\\s]{0,}(?<operation1>(:=){1})[\\s]{0,}(?<Term>[a-zA-Z][a-zA-Z0-9]{0,})[\\s]{0,}(?<operation2>[+]{1})[\\s]{0,}(?<Digit>[0-9]{1,})[\\s]{0,}[;]$");
    private final Pattern ADD_DIGIT_EXP=Pattern.compile("^[\\s]{0,}(?<ID>[a-zA-Z]{1}[\\w]{0,}[\\w]{0,})[\\s]{0,}(?<operation1>(:=){1})[\\s]{0,}(?<Digit>[0-9]{1,})[\\s]{0,}(?<operation2>[+]{1})[\\s]{0,}(?<Term>[a-zA-Z][a-zA-Z0-9]{0,})[\\s]{0,}[;]$");
    private final Pattern MULT_EXP=Pattern.compile("^[\\s]*(?<ID>[a-zA-z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*(?<EXP>([a-zA-Z]{1,}[\\s]*[*]*)+)$");
    private final Pattern MULT_DIGIT=Pattern.compile("^[\\s]{0,}(?<ID>[a-zA-Z]{1}[\\w]{0,}[\\w]{0,})[\\s]{0,}(?<operation1>(:=){1})[\\s]{0,}(?<Digit1>[0-9]{1,})[\\s]{0,}(?<operation2>[*]{1})[\\s]{0,}(?<Digit2>[0-9]{1,})[\\s]{0,}[;]$");
    private final Pattern MULT_EXP_DIGIT=Pattern.compile("^[\\s]{0,}(?<ID>[a-zA-Z]{1}[\\w]{0,}[\\w]{0,})[\\s]{0,}(?<operation1>(:=){1})[\\s]{0,}(?<Term>[a-zA-Z][a-zA-Z0-9]{0,})[\\s]{0,}(?<operation2>[*]{1})[\\s]{0,}(?<Digit>[0-9]{1,})[\\s]{0,}[;]$");
    private final Pattern MULT_DIGIT_EXP=Pattern.compile("^[\\s]{0,}(?<ID>[a-zA-Z]{1}[\\w]{0,}[\\w]{0,})[\\s]{0,}(?<operation1>(:=){1})[\\s]{0,}(?<Digit>[0-9]{1,})[\\s]{0,}(?<operation2>[*]{1})[\\s]{0,}(?<Term>[a-zA-Z][a-zA-Z0-9]{0,})[\\s]{0,}[;]$");
    private final Pattern READ=Pattern.compile("^[\\s]{0,}(READ)[\\s]{0,}[(][\\s]{0,}(?<READ>[a-zA-Z,]{1,})[\\s]{0,}[)][\\s]{0,}$");
    private final Pattern WRITE=Pattern.compile("^[\\s]{0,}(WRITE)[\\s]{0,}[(][\\s]{0,}(?<WRITE>[a-zA-Z,]{1,})[\\s]{0,}[)][\\s]{0,}$");
    private final Pattern BEGIN=Pattern.compile("^[\\s]{0,}(?<BEGIN>(BEGIN)[\\s]{0,})$");
    private final Pattern END=Pattern.compile("^[\\s]{0,}(?<END>(END))[\\s]{0,}$");
    private final Pattern ENDALL=Pattern.compile("^[\\s]{0,}(?<ENDALL>(END.))[\\s]{0,}$");
    private final Pattern ForLooP=Pattern.compile("^[\\s]*(FOR)[\\s]+[\\s]*(?<FOR>[a-zA-z][a-zA-Z0-9][\\s]*[=][\\s]*[0-9]+[\\s]+(to)[\\s]+[0-9]+)[\\s]+(DO)[\\s]*$");
    private final Pattern IF=Pattern.compile("[\\s]*(if)[\\s]*[(][\\s]*(?<IF>[a-zA-Z]+[\\s]*[<>=][=]*[\\s]*[a-zA-Z0-9]+)[\\s]*[)][\\s]*[{][\\s]*[\\n]*[}][\\s]*");
    private final Pattern BRACKET_X_FACTOR=Pattern.compile("[\\s]*(?<ID>[a-zA-Z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*[(][\\s]*(?<EXP>[a-zA-Z+\\s]+)[\\s]*[)][\\s]*[*][\\s]*(?<EXP2>[a-zA-Z][a-zA-Z0-9]*)[\\s]*$");
    private final Pattern BRACKET_ADD_FACTOR=Pattern.compile("[\\s]*(?<ID>[a-zA-Z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*[(][\\s]*(?<EXP>[a-zA-Z*\\s]+)[\\s]*[)][\\s]*[+][\\s]*(?<EXP2>[a-zA-Z][a-zA-Z0-9]*)[\\s]*$");
    private final Pattern FACTOR_ADD_BRACKET=Pattern.compile("^[\\s]*(?<ID>[a-zA-Z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*(?<EXP>[a-zA-Z][a-zA-Z0-9]*)[\\s]*[+][\\s]*[(](?<EXP1>[a-zA-Z*\\s]+)[\\s]*[)][\\s]*$");
    private final Pattern FACTOR_X_BRACKET=Pattern.compile("^[\\s]*(?<ID>[a-zA-Z][a-zA-Z0-9]*)[\\s]*(:=){1}[\\s]*(?<EXP>[a-zA-Z][a-zA-Z0-9]*)[\\s]*[*][\\s]*[(](?<EXP1>[a-zA-Z+\\s]+)[\\s]*[)][\\s]*$");
    private final Pattern SPACES=Pattern.compile("^[\\s]*[\\n]*$");
    private List<Pattern>patterns;
    private String Id;
    private String Operation1;
    private String Operation2;
    private String Digit1;
    private String Digit2;
    private String Term1;
    private String Term2;
    private String Exp;
    private String Exp1;
    private int line=1;
    private HashMap Tokens = new HashMap<String,Integer>();
    private ArrayList<String>Token=new ArrayList<String>();
    private ArrayList<String> arrayList= new ArrayList<String>();
    private PrintWriter printWriter=new PrintWriter(new File("src/generate.txt"));
    private StringBuilder builder=new StringBuilder();
    private int count =0;
    private String[]check;
    private ArrayList Variables=new ArrayList<String>();
    private ArrayList c=new ArrayList<String>();
    private ArrayList arrange=new ArrayList<String>();


    public Regex() throws FileNotFoundException {
        patterns=new ArrayList<Pattern>();
        patterns.add(ADD_EXP);
        patterns.add(ADD_DIGIT);
        patterns.add(ADD_EXP_DIGIT);
        patterns.add(ADD_DIGIT_EXP);
        patterns.add(MULT_EXP);
        patterns.add(MULT_DIGIT);
        patterns.add(MULT_EXP_DIGIT);
        patterns.add(MULT_DIGIT_EXP);
        patterns.add(Porgram_Name);
        patterns.add(Varibale_Type);
        patterns.add(WRITE);
        patterns.add(READ);
        patterns.add(BEGIN);
        patterns.add(END);
        patterns.add(ENDALL);
        patterns.add(ForLooP);
        patterns.add(IF);
        patterns.add(BRACKET_ADD_FACTOR);
        patterns.add(BRACKET_X_FACTOR);
        patterns.add(FACTOR_ADD_BRACKET);
        patterns.add(FACTOR_X_BRACKET);
        patterns.add(SUB_EXP);
        patterns.add(DIV_EXP);
        patterns.add(SPACES);
    }

    public void EXP_Match(String EXP)throws Exception {
        boolean match = false;
        for (int i = 0; i < patterns.size(); i++) {
            Pattern p = patterns.get(i);
            Matcher matcher = p.matcher(EXP);
            if(matcher.matches()) {
                switch (i) {
                    case 0:
                        line++;
                        c=new ArrayList<String>();
                        arrayList =new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        Exp=Exp.replaceAll("\\s+","");
                        check=Exp.split("\\s*");
                        if (check[Exp.length()-1].equals("+")) {
                            throw new Exception("Invalid Expiration\t"+EXP);
                        }
                        Token.add(Id);
                        Token.add(":=");
                        String[]ss=Exp.split("[+]");
                        count=Token.size();
                        c.add(Id);
                        for (String SS:ss) {
                            Token.add(SS);
                            c.add(SS);
                            arrayList.add(SS);
                            Token.add("+");
                            count=Token.size();
                        }
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","LDA",arrayList.get(0)));
                        arrayList.remove(0);
                        for (String anArrayList : arrayList) {
                            builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "ADD", anArrayList));
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","STA",Id));
                        Token.remove(count-1);
                        break;
                    case 1:
                        line++;
                        Id = matcher.group("ID");
                        Operation1 = matcher.group("operation1");
                        Operation2 = matcher.group("operation2");
                        Digit1 = matcher.group("Digit1");
                        Digit2 = matcher.group("Digit2");
                        Term1 = null;
                        Term2 = null;
                        Token.add(Id);
                        Token.add(Operation1);
                        Token.add(Digit1);
                        Token.add(Operation2);
                        Token.add(Digit2);
                        Token.add(";");
                        break;
                    case 2:
                        line++;
                        Id = matcher.group("ID");
                        Operation1 = matcher.group("operation1");
                        Operation2 = matcher.group("operation2");
                        Digit1 = matcher.group("Digit");
                        Digit2 = null;
                        Term1 = matcher.group("Term");
                        Term2 = null;
                        Token.add(Id);
                        Token.add(Operation1);
                        Token.add(Term1);
                        Token.add(Operation2);
                        Token.add(Digit1);
                        Token.add(";");
                        break;
                    case 3:
                        line++;
                        Id = matcher.group("ID");
                        Operation1 = matcher.group("operation1");
                        Operation2 = matcher.group("operation2");
                        Digit1 = matcher.group("Digit");
                        Digit2 = null;
                        Term1 = matcher.group("Term");
                        Term2 = null;
                        Token.add(Id);
                        Token.add(Operation1);
                        Token.add(Digit1);
                        Token.add(Operation2);
                        Token.add(Term1);
                        Token.add(";");
                        break;
                    case 4:
                        line++;
                        c=new ArrayList<String>();
                        arrayList =new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        check=Exp.split("\\s*");
                        if (check[Exp.length()-1].equals("*")) {
                            throw new Exception("Invalid Expiration"+EXP+" ,Line:"+line);
                        }
                        Token.add(Id);
                        Token.add(":=");
                        String[]sss=Exp.split("[*]");
                        count=Token.size();
                        c.add(Id);
                        for (String SS:sss) {
                            Token.add(SS);
                            arrayList.add(SS);
                            Token.add("*");
                            count=Token.size();
                        }
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","LDA",arrayList.get(0)));
                        arrayList.remove(0);
                        for (String anArrayList : arrayList) {
                            builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "MUL", anArrayList));
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","STA",Id));
                        Token.remove(count-1);
                        break;
                    case 5:
                        line++;
                        Id = matcher.group("ID");
                        Operation1 = matcher.group("operation1");
                        Operation2 = matcher.group("operation2");
                        Digit1 = matcher.group("Digit1");
                        Digit2 = matcher.group("Digit2");
                        Term1 = null;
                        Term2 = null;
                        Token.add(Id);
                        Token.add(Operation1);
                        Token.add(Digit1);
                        Token.add(Operation2);
                        Token.add(Digit2);
                        Token.add(";");
                        break;
                    case 6:
                        line++;
                        Id = matcher.group("ID");
                        Operation1 = matcher.group("operation1");
                        Operation2 = matcher.group("operation2");
                        Digit1 = matcher.group("Digit");
                        Digit2 = null;
                        Term1 = matcher.group("Term");
                        Term2 = null;
                        Token.add(Id);
                        Token.add(Operation1);
                        Token.add(Term1);
                        Token.add(Operation2);
                        Token.add(Digit1);
                        Token.add(";");
                        break;
                    case 7:
                        line++;
                        Id = matcher.group("ID");
                        Operation1 = matcher.group("operation1");
                        Operation2 = matcher.group("operation2");
                        Digit1 = matcher.group("Digit");
                        Digit2 = null;
                        Term1 = matcher.group("Term");
                        Term2 = null;
                        Token.add(Id);
                        Token.add(Operation1);
                        Token.add(Digit1);
                        Token.add(Operation2);
                        Token.add(Term1);
                        Token.add(";");
                        break;
                    case 8:
                        arrange.add("PROGRAM");
                        Exp = matcher.group("NAME");
                        Token.add("PROGRAM");
                        Exp = Exp.replaceAll("\\s+", "");
                        Token.add(Exp);
                        builder.append(String.format("%-5s\t\t\t%-5s\t\t\t\t%-5s\n\n",Exp,"START","0"));
                        break;
                    case 9:
                        line++;
                        try {
                            if (arrange.get(0)!="PROGRAM")
                                throw new Exception("ERROR");
                        }catch (IndexOutOfBoundsException e){
                         throw new Exception("ERROR \n\t\t\t\t\t\tMissed Program_Name");
                        }

                      arrange.add("VAR");
                        arrayList=new ArrayList<String>();
                        Token.add("VAR");
                        Exp = matcher.group("VAR");
                        Exp=Exp.replaceAll("\\s+","");
                        String[] vars = Exp.split(",");
                        vars[0] = vars[0].replaceAll("\\s+", "");
                        for (String var : vars) {
                           Variables.add(var);
                            arrayList.add(var);
                            Token.add(var);
                            Token.add(",");
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "EXTREF","XREAD,XWRITE"));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "STL","RETADR"));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "J","{EXTADAR}"));


                        for (int j = 0; j <arrayList.size() ; j++) {
                            builder.append(String.format("%-5s\t\t\t%-5s\t\t\t\t%-5s\n",arrayList.get(j),"RESW","1"));
                        }
                        builder.append(String.format("%-5s\t\t\t%-5s\t\t\t\t%-5s\n","EXTADAR","LDA","#0"));


                        break;
                    case 10:
                        line++;
                        arrayList=new ArrayList<String>();
                        Token.add("WRITE");
                        Token.add("(");
                        Exp = matcher.group("WRITE");
                        check=Exp.split("\\s*");
                        try {
                            if (arrange.get(3)!="READ"){
                                throw new Exception("ERROR");
                            }
                        }catch (IndexOutOfBoundsException e) {
                            throw new Exception("ERROR \n\t\t\t\t\t\tREAD NOT FOUND");
                        }
                        if (check[Exp.length()-1].equals(",")) {
                            throw new Exception("Invalid Expiration"+EXP+" ,Line:"+line);
                        }
                        String[] writs = Exp.split(",");
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","+JSUB","XWRIRE"));
                        count=Token.size();
                        for (String writ : writs) {
                            Token.add(writ);
                            arrayList.add(writ);
                            Token.add(",");
                            count=Token.size();
                        }
                        Token.remove(count-1);
                        count=0;
                        for (int j = 0; j <arrayList.size() ; j++) {
                            count++;
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","WORD",count));
                        for (int j = 0; j <count ; j++) {
                            builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","WORD",arrayList.get(j)));
                        }
                        Token.add(")");
                        break;
                    case 11:
                        line++;
                        arrange.add("READ");
                        arrayList=new ArrayList<String>();
                        Token.add("READ");
                        Token.add("(");
                        Exp=matcher.group("READ");
                        check=Exp.split("\\s*");
                        try {
                            if (arrange.get(2)!="BEGIN"){
                                throw new Exception("ERROR");
                            }
                        }catch (IndexOutOfBoundsException e){
                            throw new Exception("ERROR \n\t\t\t\t\t\tBEGIN NOT FOUND");
                        }
                        if (check[Exp.length()-1].equals(",")) {
                            throw new Exception("Invalid Expiration"+EXP+" ,Line:"+line);
                        }
                        String[] reads = Exp.split(",");
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","+JSUB","XREAD"));
                        count=Token.size();
                        for (String read:reads)
                        {
                            arrayList.add(read);
                            Token.add(read);
                            Token.add(",");
                            count=Token.size();
                        }
                        Token.remove(count-1);
                        count=0;
                        for (int j = 0; j <arrayList.size() ; j++) {
                            count++;
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","WORD",count));
                        for (int j = 0; j <count ; j++) {
                            builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","WORD",arrayList.get(j)));
                        }


                        Token.add(")");
                        break;
                    case 12:
                        line++;
                       try {
                           if (arrange.get(1)!="VAR"){
                               throw new Exception("ERROR");
                           }
                       }catch (IndexOutOfBoundsException e){
                           throw new Exception("ERROR \n\t\t\t\t\t\tDEFINE CONSTANT NOT FOUND");
                       }
                       arrange.add("BEGIN");
                        Exp=matcher.group("BEGIN");
                        Token.add(Exp);
                        break;
                    case 13:
                        line++;
                        Exp=matcher.group("END");
                        Token.add(Exp);
                        break;
                    case 14:
                        line++;
                        Exp=matcher.group("ENDALL");
                        Token.add(Exp);
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s","END","0"));
                        printWriter.println(builder);
                        printWriter.flush();
                        printWriter.close();
                        break;
                    case 15:
                        line++;
                        Exp=matcher.group("FOR");
                        System.out.println(Exp);
                        String e=Exp.replaceAll("[-+,=<>]","");
                        String[]ex=e.split("[\\s]+");
                        for (String E:ex) {
                            System.out.println(E);
                        }
                        break;
                    case 16:
                        line++;
                        Token.add("IF");
                        Token.add("(");
                        Token.add(")");
                        Exp=matcher.group("IF");
                        // String ii=Exp.replaceAll("[=><]","");
                        String[]exx=Exp.split("[=><]+");
                        for (String Ex:exx) {
                            if (Ex.equals(exx.length-1))
                                Token.add(Ex);
                            else
                                Token.add(Ex);
                            //System.out.println(Ex);

                        }
                        break;
                    case 17:
                        line++;
                        c=new ArrayList<String>();
                        arrayList=new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        Exp1=matcher.group("EXP2");
                        Token.add(Id);
                        Token.add(":=");
                        Token.add("(");
                        count=Token.size();
                        String[]gg=Exp.split("[*]");
                        c.add(Id);
                        c.add(Exp1);
                        for (String g:gg) {
                            c.add(g);
                            arrayList.add(g);
                            Token.add(g);
                            Token.add("*");
                            count=Token.size();
                        }
                        arrayList.add(Exp1);
                        arrayList.add(Id);
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "LDA",arrayList.get(0)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "MUL",arrayList.get(1)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "ADD",arrayList.get(2)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "STA",arrayList.get(3)));
                        Token.remove(count-1);
                        Token.add(")");
                        Token.add("+");
                        Token.add(Exp1);
                        break;
                    case 18:
                        line++;
                        c=new ArrayList<String>();
                        arrayList=new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        Exp1=matcher.group("EXP2");
                        Token.add(Id);
                        Token.add(":=");
                        Token.add("(");
                        count=Token.size();
                        String[]h=Exp.split("[+]");
                        c.add(Id);
                        c.add(Exp1);
                        for (String g:h) {
                            c.add(g);
                            arrayList.add(g);
                            Token.add(g);
                            Token.add("+");
                            count=Token.size();
                        }
                        arrayList.add(Exp1);
                        arrayList.add(Id);
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "LDA",arrayList.get(0)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "ADD",arrayList.get(1)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "MUL",arrayList.get(2)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "STA",arrayList.get(3)));
                        Token.remove(count-1);
                        Token.add(")");
                        Token.add("*");
                        Token.add(Exp1);
                        break;
                    case 19:
                        line++;
                        c=new ArrayList<String>();
                        arrayList=new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        Exp1=matcher.group("EXP1");
                        Token.add(Id);
                        Token.add(":=");
                        Token.add(Exp);
                        Token.add("+");
                        Token.add("(");
                        count=Token.size();
                        Exp1=Exp1.replaceAll("\\s+","");
                        String[]k=Exp1.split("[*]");
                        c.add(Id);
                        c.add(Exp);
                        for (String g:k) {
                            c.add(g);
                            arrayList.add(g);
                            Token.add(g);
                            Token.add("*");
                            count=Token.size();
                        }
                        arrayList.add(Exp);
                        arrayList.add(Id);
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "LDA",arrayList.get(0)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "MUL",arrayList.get(1)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "ADD",arrayList.get(2)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "STA",arrayList.get(3)));
                        Token.remove(count-1);
                        Token.add(")");
                        break;
                    case 20:
                        line++;
                        c=new ArrayList<String>();
                        arrayList=new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        Exp1=matcher.group("EXP1");
                        Token.add(Id);
                        Token.add(":=");
                        Token.add(Exp);
                        Token.add("*");
                        Token.add("(");
                        count=Token.size();
                        Exp1=Exp1.replaceAll("\\s+","");
                        String[]l=Exp1.split("[+]");
                        c.add(Id);
                        c.add(Exp);
                        for (String g:l) {
                            c.add(g);
                            arrayList.add(g);
                            Token.add(g);
                            Token.add("+");
                            count=Token.size();
                        }
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        arrayList.add(Exp);
                        arrayList.add(Id);
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "LDA",arrayList.get(0)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "ADD",arrayList.get(1)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "MUL",arrayList.get(2)));
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "STA",arrayList.get(3)));
                        Token.remove(count-1);
                        Token.add(")");
                        break;
                    case 21:
                        line++;
                        c=new ArrayList<String>();
                        arrayList =new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        Exp=Exp.replaceAll("\\s+","");
                        check=Exp.split("\\s*");
                        if (check[Exp.length()-1].equals("-")) {
                            throw new Exception("Invalid Expiration"+EXP);
                        }
                        Token.add(Id);
                        Token.add(":=");
                        String[]dd=Exp.split("[-]");
                        count=Token.size();
                        c.add(Id);
                        for (String SS:dd) {
                            Token.add(SS);
                            arrayList.add(SS);
                            Token.add("-");
                            count=Token.size();
                        }
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","LDA",arrayList.get(0)));
                        arrayList.remove(0);
                        for (String anArrayList : arrayList) {
                            builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "SUB", anArrayList));
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","STA",Id));
                        Token.remove(count-1);
                        break;
                    case 22:
                        line++;
                        c=new ArrayList<String>();
                        arrayList =new ArrayList<String>();
                        Id=matcher.group("ID");
                        Exp=matcher.group("EXP");
                        Exp=Exp.replaceAll("\\s+","");
                        check=Exp.split("\\s*");
                        if (check[Exp.length()-1].equals("/")) {
                            throw new Exception("Invalid Expiration"+EXP+" ,Line:"+line);
                        }
                        Token.add(Id);
                        Token.add(":=");
                        String[]kk=Exp.split("[/]");
                        count=Token.size();
                        c.add(Id);
                        for (String SS:kk) {
                            Token.add(SS);
                            arrayList.add(SS);
                            Token.add("/");
                            count=Token.size();
                        }
                        for (int j = 0; j <c.size() ; j++) {
                            if (!Variables.contains(c.get(j))){
                                throw new Exception("Invalid Variable("+EXP+")"+",Variable("+c.get(j)+")Not found"+" ,Line:"+line);
                            }
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","LDA",arrayList.get(0)));
                        arrayList.remove(0);
                        for (String anArrayList : arrayList) {
                            builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n", "DIV", anArrayList));
                        }
                        builder.append(String.format("\t\t\t\t%-5s\t\t\t\t%-5s\n","STA",Id));
                        Token.remove(count-1);
                        break;
                    case 23:
                        line++;
                        break;

                }
                match=true;
                break;
            }
        }
        if (!match) {
            throw new Exception("Expiration not valid: " + EXP+" ,Line:"+(line+1));
        }
    }

    public void Token_File(File F) {
        try (Scanner reader = new Scanner(F)) {
            while (reader.hasNextLine()) {
                Tokens.put(reader.next(), reader.next());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Tokens file not found");
            System.exit(0);
        }
    }

    public String getID() {
        return Id;
    }

    public String getExp() {
        return Exp;
    }

    public String getOperation1() {
        return Operation1;
    }

    public String getOperation2() {
        return Operation2;
    }

    public String getDigit1() {
        return Digit1;
    }

    public String getDigit2() {
        return Digit2;
    }

    public String getTerm1() {
        return Term1;
    }

    public String getTerm2() {
        return Term2;
    }

    public void Show() {
        System.out.println(Token.size());
        for (int i = 0; i < Token.size(); i++) {
            Exp = Token.get(i);
            System.out.println(Token.get(i));
        }
    }

    public void LexOut(File F) throws IOException
    {
        try {
            PrintWriter writer = new PrintWriter(F);
            writer.println(String.format("%-20s %-20s %-20s%n", "Token ", "Token_type ", "Token_Specifier"));
            for (int n = 0; n < Token.size(); n++) {
                if (Tokens.containsKey(Token.get(n))) {
                    writer.println(String.format("%-23s %-23s %-23s%n", Token.get(n), Tokens.get(Token.get(n)), "   "));
                } else if (Token.get(n).matches("[0-9]+")) {
                    writer.println(String.format("%-23s %-23s %-23s%n", Token.get(n), Tokens.get("imm"), Token.get(n)));
                } else {
                    writer.println(String.format("%-23s %-23s %-23s%n", Token.get(n), Tokens.get("id"), Token.get(n)));
                }
            }
            writer.flush();
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

}