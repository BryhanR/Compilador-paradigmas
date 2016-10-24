

/*
* 			INTEGRANTES:
*
*	ALEXANDRA AGUILAR NAJERA	304780037	1 pm
*	MASIEL MORA RODRIGUEZ		604190071	8 am
* 	BRYHAN RODRIGUEZ MORA		115420325	1 pm
*	JEAN CARLO VARGAS ZUÑIGA	402220474	1 pm
*
*/


package eightBit.asm;

import java.util.*;
import java.io.*;

public class ASMFunction implements ASMAst{
   static private ASMId UNK = new ASMId("");
   private ASMId name;
   private ASMAst formals;
   private ASMAst body;
   public ASMFunction(ASMAst formals, ASMAst body){
      this(UNK, formals, body);
   }
   public ASMFunction(ASMId name, ASMAst formals, ASMAst body){
      this.formals = formals;
	  this.body = body;
	  this.name = name;
   }
   public void genCode(PrintStream out){	   
	   
	   out.println("." + this.name.getValue() + ":");
	   if(this.name.getValue().compareTo("main") != 0)
	   {
	   out.println("POP C");	// extraemos la dir de retorno
	   // Se extraen los parametros
	   this.formals.genCode(out);
	List<ASMAst> mem = ((ASMFormals)this.formals).getMembers();
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