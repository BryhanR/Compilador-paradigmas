package eightBit.compile;

import eightBit.js.*;
import java.util.*;

public interface JSEmiter{
	
   default JSAst PROGRAM(List<JSAst> functions){ return new JSProgram(functions);} 
   
   default JSAst BLOCK(List<JSAst> members){ return new JSBlock(members);}
   default JSAst BLOCK(){ return new JSBlock(Arrays.asList());}
   
   default JSAst EMPTY(){
	   return new JSEmpty();
   }
   
   default JSNum NUM(int value){ return new JSNum(value);}
   default JSString STRING(String value){ return new JSString(value);}
   
   default JSId  ID(String value){return new JSId(value);}
   
   default JSFunction FUNCTION(JSId id, JSAst formals, JSAst body){
           return new JSFunction(id, formals, body);
   }
   
   
   default JSIf IF(JSAst c, JSAst t, JSAst e){
       return new JSIf(c, t, e);
   }
   
   default JSCall CALL(JSAst f, List<JSAst> args){
       return new JSCall(f, args);
   }
   default JSAst OPERATION(JSAst oper, JSAst left, JSAst right){
	   return new JSOperation(oper, left, right);
   }
   default JSAst FOLD_LEFT(JSAst left, JSAst right){
	   JSOperation rightOperation = (JSOperation)right;
	   return new JSOperation(rightOperation.getOper(), left, rightOperation.getRight());
   }
   default JSAst ASSIGN(JSAst left, JSAst right){
	   return new JSAssign(left, right);
   }
   default List<JSAst> ARGS(List<JSAst>  args){ return args;}
   default JSAst FORMALS(List<JSAst> args){ return new JSFormals (args);}
   default JSAst FORMALS(){ return new JSFormals (Arrays.asList());}
   
   
   default JSAst WHILE(JSAst exp, JSAst stm){ return new JSWhile(exp, stm);};
   
   default JSAst LET(List<JSAst> args, JSAst st){return new JSLet(args, st);}
   default JSAst LET(List<JSAst> args){return new JSLet(args, null);}
   
   
   default JSAst RET(JSAst e){ return new JSReturn(e);}
   default JSAst OPER(String op){return new JSId(op);}
   final JSBool TRUE = new JSBool(true);
   final JSBool FALSE = new JSBool(false);
   final JSAst ADD = new JSId("+");
   final JSAst MINUS = new JSId("-");
   final JSAst MUL = new JSId("*");
   final JSAst DIV = new JSId("/");
   final JSAst NULL = new JSId("null");
   
   
   
}