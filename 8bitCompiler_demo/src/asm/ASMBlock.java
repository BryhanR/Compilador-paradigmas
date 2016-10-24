

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
public class ASMBlock implements ASMAst {
   protected List<ASMAst> members;
   public List<ASMAst> getMembers(){
	   return this.members;
   }
   public ASMBlock(List<ASMAst> members){
      this.members = members;
   }
    
   public void genCode(PrintStream out){
       this.members.stream().filter(t -> t != null)
	                        .forEach( t -> 	t.genCode(out));
   }
   
}