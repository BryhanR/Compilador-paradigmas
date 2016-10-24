


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

import eightBit.asm.*;
import java.util.*;

public interface ASMEmiter{
	
   default ASMAst PROGRAM(List<ASMAst> functions){ return new ASMProgram(functions);} 
   
   default ASMAst BLOCK(List<ASMAst> members){ return new ASMBlock(members);}
   default ASMAst BLOCK(){ return new ASMBlock(Arrays.asList());}
   
   default ASMAst EMPTY(){
	   return new ASMEmpty();
   }
   
   default ASMNum NUM(int value){ return new ASMNum(value);}
   default ASMString STRING(String value){ return new ASMString(value);}
   
   default ASMId ID(String value){return new ASMId(value);}
   
   default ASMFunction FUNCTION(ASMId id, ASMAst formals, ASMAst body){
           return new ASMFunction(id, formals, body);
   }
   
   
   default ASMIf IF(ASMAst c, ASMAst t, ASMAst e){
       return new ASMIf(c, t, e);
   }
   
   default ASMCall CALL(ASMAst f, List<ASMAst> args){
       return new ASMCall(f, args);
   }
   default ASMAst OPERATION(ASMAst oper, ASMAst left, ASMAst right){
	   return new ASMOperation(oper, left, right);
   }
   default ASMAst FOLD_LEFT(ASMAst left, ASMAst right){
	   ASMOperation rightOperation = (ASMOperation)right;
	   return new ASMOperation(rightOperation.getOper(), left, rightOperation.getRight());
   }
   default ASMAst ASSIGN(ASMAst left, ASMAst right){
	   return new ASMAssign(left, right);
   }
   default List<ASMAst> ARGS(List<ASMAst>  args){ return args;}
   default ASMAst FORMALS(List<ASMAst> args){ return new ASMFormals (args);}
   default ASMAst FORMALS(){ return new ASMFormals (Arrays.asList());}
   
   
   default ASMAst WHILE(ASMAst exp, ASMAst stm){ return new ASMWhile(exp, stm);};
   
   default ASMAst LET(List<ASMAst> args, ASMAst st){return new ASMLet(args, st);}
   default ASMAst LET(List<ASMAst> args){return new ASMLet(args, null);}
   
   
   default ASMAst RET(ASMAst e, String p){ return new ASMReturn(e, p);}
   default ASMAst OPER(String op){return new ASMId(op);}
   final ASMBool TRUE = new ASMBool(true);
   final ASMBool FALSE = new ASMBool(false);
   final ASMAst ADD = new ASMId("+");
   final ASMAst MINUS = new ASMId("-");
   final ASMAst MUL = new ASMId("*");
   final ASMAst DIV = new ASMId("/");
   final ASMAst NULL = new ASMId("null");
   
   
   
}