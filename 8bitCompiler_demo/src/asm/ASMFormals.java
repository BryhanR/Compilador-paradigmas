

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
public class ASMFormals extends ASMBlock {
	private String owner;
   public ASMFormals(List<ASMAst> members){
	  
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