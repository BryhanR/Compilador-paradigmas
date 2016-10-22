package eightBit.js;
import java.util.*;
import java.io.*;
public class JSLet implements JSAst{
   private List<JSAst> stmts;
   private JSAst statements;
   
   
   public JSLet(List<JSAst> ids, JSAst st){
     stmts = ids;
	 statements = st;
   }
   
   public void genCode(PrintStream out){
	  // System.err.println("Generando codigo args " + idList.size());
	    //int []count = {0};
		//out.println("Let {");
   //stmts.stream().forEach( t -> {out.print("\t");t.genCode();});
		//out.println("\n}");
		stmts.stream().forEach( t -> {t.genCode();});
		this.statements.genCode(out);
	//out.println("args");
   }
}