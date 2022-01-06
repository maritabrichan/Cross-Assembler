package CommandLine;
import java.io.IOException;
import java.util.Scanner;
import Generator.Generator;
import Lexer.Lexer;
import Parser.Parser;
import Reader.Reader;
import SymbolTable.SymbolTable;
import Token.Token;

import java.io.File;


public class CommandLine {
	public static void main(String[] args) throws IOException {


		boolean bool = true;
		Scanner  scan = new Scanner(System.in);






		System.out.println("command format = java 'cma' [ Options ] <file>.asm");
		System.out.println();
		System.out.println("Option types = HelpOption | VerboseOption | BannerOption | ListingOption .");
		System.out.println();
		System.out.println("Options syntax");
		System.out.println("HelpOption      =    '-h' | '-help' " );
		System.out.println("VerboseOption   =    '-v' | '-verbose'" );
		System.out.println("BannerOption    =    '-b' | '-banner'" );
		System.out.println("ListingOption   =    '-l' | '-listing'");
		try {
			while(bool)
			{
				String java = scan.next();
				String command = scan.next();
				String type = scan.next();





				if( "java".equals(java)  && "cma".equals(command) )   {


					if("-b".equals(type)  || "-banner".equals(type) ){
						System.out.println("Cm Cross-Assembler Version 1.4 - Developed by Team 9");
					}


					else if("-l".equals(type)  || "-listing".equals(type) ){


						File file = new File(scan.next());
						Reader read = new Reader(file);
						Lexer lexer = new Lexer(read);
						Parser parse1 = new Parser(lexer);
						Generator generator= new Generator(parse1);
						parse1.parse();


						generator.generateLstFile();
						generator.generateExeFile();



						System.out.println(".lst file has been generated ");
						System.out.println(".exe file has been generated ");

					}

					else if("-h".equals(type)  || "-help".equals(type) ){
						System.out.println("Usage: java cma [ Options ] <file>.asm");
						System.out.println("where options are:       ");
						System.out.println("Short version    Long version    Meaning");
						System.out.println("-h                -help          Print the usage of the program");
						System.out.println("-v                -verbose       Verbose during the execution of the program");
						System.out.println("-l                -listing       Generate a listing of the assembly file.            ");
						System.out.println("-b                -banner        Print the banner of the program.            ");
					}

					else if("-v".equals(type)  || "-verbose".equals(type) ){
						String filen1 = scan.next();
						File file3 = new File(filen1);
						Reader read1 = new Reader(file3);
						Lexer lexer1 = new Lexer(read1);
						Parser parse3 = new Parser(lexer1);
						Generator generatorv= new Generator(parse3);
						parse3.parse();
						generatorv.generateLstFile();
						generatorv.generateExeFile();

						System.out.println("Pass 1 done\n");
						System.out.println("SymbolTable: (after the first pass)");
						System.out.printf("%-10s%-10s%-10s\n","Name","Type","Address/Code");
						SymbolTable<String,Token> s1= parse3.getScanTable();
						s1.printSymbolTable();

						System.out.println("\nListing: (after the first pass)");
						parse3.verbosePass1();
						System.out.println("\nPass 2 done");
						System.out.println("\nListing: (after the second pass)");
						parse3.verbosePass2();


					}

					else if(type != null){

						File file2 = new File(type);
						Reader read2 = new Reader(file2);
						Lexer lexer2 = new Lexer(read2);
						Parser parse2 = new Parser(lexer2);
						Generator generator2 = new Generator(parse2);
						parse2.parse();
						generator2.generateExeFile();
						System.out.println(".exe file for "+ type +" has been generated");

					}

					else {
						System.out.println("invalid input");
					}


				}
				else if (!"java".equals(java) || !"cma".equals(command) || !"-l".equals(type) || !"-h".equals(type) || !"-v".equals(type) || !"-b".equals(type)){
					System.out.println("invalid input");}

				else if (!"java".equals(java) || !"cma".equals(command) ){
					System.out.println("invalid input");}

				else if (!"java".equals(java) ){
					System.out.println("invalid input");}
			}
		}
		catch(NullPointerException e) {
			System.out.println("Invalid Input. Exiting the program...");
			System.exit(0);
		}
    
		scan.close();

 
    }

}

  
  


