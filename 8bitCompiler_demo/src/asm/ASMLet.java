

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
public class ASMLet implements ASMAst{
   private List<ASMAst> stmts;
   private ASMAst statements;
   
   
   public ASMLet(List<ASMAst> ids, ASMAst st){
     stmts = ids;
	 statements = st;
   }
   
   public void genCode(PrintStream out){
		stmts.stream().forEach( t -> {t.genCode();});
		this.statements.genCode(out);
   }
}