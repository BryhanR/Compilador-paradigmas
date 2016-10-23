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
		stmts.stream().forEach( t -> {t.genCode();});
		this.statements.genCode(out);
   }
}