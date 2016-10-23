package eightBit.js;
import java.io.*;
public class JSOperation implements  JSAst{
   protected JSAst oper;
   protected JSAst left, right;
   public JSAst getOper(){return this.oper;}
   public JSAst getLeft(){return this.left;}
   public JSAst getRight(){return this.right;}
   public JSOperation(JSAst oper, JSAst left, JSAst right){
      this.oper = oper;
	  this.left = left;
	  this.right = right;
   }
   public void genCode(PrintStream out){
	   switch(((JSId)oper).getValue())
	   {
		   case "+":
				out.print("PUSH " + ((JSId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println((JSId.class.isInstance(left))?"]":"");
				
				out.print("PUSH " + ((JSId.class.isInstance(right))?"[":""));
				right.genCode(out);
				out.println(((JSId.class.isInstance(right))?"]":""));
				out.println("POP A\nPOP B");
				out.println("ADD A, B\nPUSH A");
				break;
		   case "-":
				out.print("PUSH " + ((JSId.class.isInstance(right))?"[":""));
				right.genCode(out);
				out.println((JSId.class.isInstance(right))?"]":"");
				
				out.print("PUSH " + ((JSId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println(((JSId.class.isInstance(left))?"]":""));
				out.println("POP A\nPOP B");
				out.println("SUB A, B\nPUSH A");
				break;
		   case "*":
				out.print("PUSH " + ((JSId.class.isInstance(left))?"[":""));
				left.genCode(out);
				out.println((JSId.class.isInstance(left))?"]":"");
				
				if(((JSCall.class.isInstance(right))))
				{
					right.genCode(out);
					out.println("PUSH A");
				}
				else
				{
					out.print("PUSH " + ((JSId.class.isInstance(right))?"[":""));
					right.genCode(out);
					out.println(((JSId.class.isInstance(right))?"]":""));
				}
				out.println("POP A\nPOP B");
				out.println("MUL B\nPUSH A");
				break;
		   case "/":
				out.println("DIV B");
				break;
		   case ">":
		   case "==":
				
				out.print("MOV A, ");
				if((JSId.class.isInstance(left)))
					out.print("[");
				left.genCode(out);
				if((JSId.class.isInstance(left)))
					out.println("]");
				out.print("MOV B, ");
				if((JSId.class.isInstance(right)))
					out.print("[");
				right.genCode(out);
				if((JSId.class.isInstance(right)))
					out.println("]");
				out.println("\nCMP A, B");
				break;
	   }
   }
}