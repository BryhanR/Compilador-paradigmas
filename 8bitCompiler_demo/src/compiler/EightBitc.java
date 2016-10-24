/*
 loriacarlos@gmail.com EIF400 II-2016
 EightBit starting controller
*/

/*
* 			INTEGRANTES:
*
*	ALEXANDRA AGUILAR NAJERA	304780037	1 pm
*	MASIEL MORA RODRIGUEZ		604190071	8 am
* 	BRYHAN RODRIGUEZ MORA		115420325	1 pm
*	JEAN CARLO VARGAS ZUÑIGA	402220474	1 pm
*
*/





package eightBit.compile;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;


public class EightBitc {
	
    public static void main(String[] args) throws Exception {
        String inputFile = null;
        if (args.length > 0) 
		  inputFile = args[0];
	  
        InputStream is = System.in;
		
        if (inputFile != null){
 		   is = new FileInputStream(inputFile);
		   System.err.println("EightBitc Reading from " + inputFile);
		} else{
		   System.err.println("EightBitc Reading from console (enter CTRL-Z+ENTER to finish");
		}
		
        ANTLRInputStream input = new ANTLRInputStream(is);
        EightBitLexer lexer = new EightBitLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        EightBitParser parser = new EightBitParser(tokens);
        ParseTree tree = parser.eightProgram(); 
        
		System.err.println("EightBitc v0.0 CR II-2016");
		Compiler comp = new Compiler();
		comp.compile(tree);
		comp.genCode();
		
    }
}
