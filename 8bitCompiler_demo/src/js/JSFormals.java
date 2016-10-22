package eightBit.js;
import java.util.*;
import java.io.*;
public class JSFormals extends JSBlock {
	private String owner;
   public JSFormals(List<JSAst> members){
	  
      super(members);
	   owner = ".salute";
   }
   @Override
   public void genCode(PrintStream out){
	   int []b = {0};
	   char []r = {'A', 'B'};
       this.members.stream().filter(t -> t != null)
	                        .forEach( t -> { out.println("POP " + r[b[0]++]);});
								   	  out.println("PUSH C");	// devolvemos la dir de retorno a la pila

		this.members.stream().filter(t -> t != null)
	                        .forEach( t -> {out.print("PUSH [");t.genCode(out);out.println("]");});
	
		this.members.stream().filter(t -> t != null)
	                        .forEach( t -> {out.print("MOV [");t.genCode(out); out.println("], " + r[--b[0]]);});
		
   }
   
}