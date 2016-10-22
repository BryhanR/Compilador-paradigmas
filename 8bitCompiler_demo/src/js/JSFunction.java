package eightBit.js;

import java.util.*;
import java.io.*;

public class JSFunction implements JSAst{
   static private JSId UNK = new JSId("");
   private JSId name;
   //private List<JSAst> formals;
   private JSAst formals;
   private JSAst body;
   public JSFunction(/*List<JSAst>*/JSAst formals, JSAst body){
      this(UNK, formals, body);
   }
   public JSFunction(JSId name, JSAst/*List<JSAst>*/ formals, JSAst body){
      this.formals = formals;
	  this.body = body;
	  this.name = name;
   }
   public void genCode(PrintStream out){
       /*out.format("function %s(", this.name.getValue());
	   if (this.formals != null)
		   this.formals.genCode(out);
	     /* this.formals
	          .stream()
	          .filter(f -> f != null)
	          .forEach(f -> f.genCode(out));
	  /
	  
	   out.print("){");
	   if (this.body != null)
	      this.body.genCode(out);
	   out.print("};");
	   */
	   
	   
	   out.println("." + this.name.getValue() + ":");
	   if(this.name.getValue().compareTo("main") != 0)
	   {
	   out.println("POP C");	// extraemos la dir de retorno
	   // Se extraen los parametros
	   this.formals.genCode(out);
	   //	  out.println("PUSH C");	// devolvemos la dir de retorno a la pila
	List<JSAst> mem = ((JSFormals)this.formals).getMembers();
	out.println(";***********Aqui va el cuerpo, operaciones, etc\n");
	 if (this.body != null)
	      this.body.genCode(out);
	  out.println(";***********Aqui Termina el cuerpo, operaciones, etc\n" + this.name.getValue()+"_end:");
	mem.stream().filter(t -> t != null)
	                        .forEach( t -> {out.println("POP B");out.print("MOV [");t.genCode(out); out.println("], B");});
	out.println("POP C\nPUSH A\nPUSH C");
	   
   }
   else{
	   if (this.body != null)
	      this.body.genCode(out);
   }
	   if(this.name.getValue().compareTo("main") == 0)
		   out.println("HLT");
	   else
		out.println("RET");
	   
	   
   }
}