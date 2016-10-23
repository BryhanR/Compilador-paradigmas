/*
 loriacarlos@gmail.com EIF400 II-2016
 EightBit starting compiler
*/
package eightBit.compile;


import eightBit.js.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;
import java.util.stream.*;


public class Compiler extends EightBitBaseVisitor<JSAst> implements JSEmiter{
   protected JSAst program;
   protected Scope symbolTable = new Scope();
   public JSAst getProgram(){
	   return this.program;
   }
   protected List<JSAst> statements = new ArrayList<>();
   
   public void genCode(){
		((JSProgram)this.program).genData();
	   System.out.println(";.codeArea:");
		this.program.genCode();
		
		
		//Corregir esto, es para poder imprimir numeros
	/*
		System.out.println(".print_number:\nPOP C\nPOP B\nPUSH C\nMOV C, 0\n.print_number_loop_01:\nINC C\nMOV A, B\nDIV 10\n");
		System.out.println("PUSH A\nMUL 10\nSUB B, A\nMOV A, B\nPOP B\nPUSH A\nCMP B, 0\nJNZ .print_number_loop_01\n.print_number_loop_02:\n");
		System.out.println("POP B\nADD B, 48\nMOV [D], B\nINC D\nDEC C\nCMP C, 0\nJNZ .print_number_loop_02\n.print_number_exit:\nPOP C\nPUSH .UNDEF\nPUSH C\nRET\n");
		
		// y esta es para imprimir texto
		System.out.println(".print_string:\nPOP C\nPOP B\nPUSH C\n.print_string_loop_01:\nMOV C, [B]\nCMP C, 0\nJE .print_string_exit\nMOV [D], C\n");
		System.out.println("INC D\nINC B\nJMP .print_string_loop_01\n.print_string_exit:\nPOP C\nPUSH .UNDEF\nPUSH C\nRET");
		// y esta para imprimir boolean
		System.out.println(".print_boolean:\nPOP C\nPOP A\nPUSH C\nCMP A, 1\nJE .print_boolean_true\nJNE .print_boolean_false");
		System.out.println(".print_boolean_true:\n PUSH .boolean_true\nJMP .end_print_boolean\n.print_boolean_false:\nPUSH .boolean_false\nJMP .end_print_boolean");
		System.out.println(".end_print_boolean:\nCALL .print_string\nPOP A\nPOP C\nPUSH .UNDEF\nPUSH C\nRET");
	*/
 }
   public JSAst compile(ParseTree tree){
      return visit(tree);
   }
   @Override
   public JSAst visitEightProgram(EightBitParser.EightProgramContext ctx){
	   ctx.eightFunction().stream()
	                      .forEach( fun -> visit(fun) );
						  
		JSProgram p = ((JSProgram)PROGRAM(this.statements));
		p.setDataArea(symbolTable.getCollectedData());
		this.program = p;
	   return this.program;
   }
   
   @Override
   public JSAst visitEightFunction(EightBitParser.EightFunctionContext ctx){
      
      symbolTable.newScope(ctx.id().getText());	// se agrega la funcion al scope	  
	  JSId id = (JSId)visit(ctx.id());	  
	  JSAst f = visit(ctx.formals());	  
	  List<JSAst> fb = ((JSFormals)f).getMembers();	  
	  fb.stream().forEach(e -> {
									JSId idf = ((JSId)e);
									String v = idf.getValue();
									idf.setValue(symbolTable.add(v));
								});	  
	  JSAst body = visit(ctx.funBody());
	  if((JSBlock.class.isInstance(body)))
		  ((JSBlock)body).setOwner(id);
	  JSAst function = FUNCTION(id, f, body);
	  this.statements.add(function);
	  return function;
   }
   
   @Override
   public JSAst visitEmptyStatement(EightBitParser.EmptyStatementContext ctx){
      return EMPTY();
	                
   }
   
   @Override
   public JSAst visitReturnStatement(EightBitParser.ReturnStatementContext ctx){
	   JSAst ex = visit(ctx.expr());
      return RET(ex);
	                
   }
   
   @Override
   public JSAst visitAssignStatement(EightBitParser.AssignStatementContext ctx){
	   JSAst id = visit(ctx.id());
	  return ASSIGN(id, visit(ctx.expr()));
	                
   }
   @Override
   public JSAst visitBlockStatement(EightBitParser.BlockStatementContext ctx){
	  EightBitParser.ClosedListContext closedList = ctx.closedList();
      return (closedList == null ) ? BLOCK() 
	                               : visit(closedList);
   }
   @Override
   public JSAst visitClosedList(EightBitParser.ClosedListContext ctx){					  
					   return  BLOCK(ctx.closedStatement().stream()
	                                                      .map( c -> 
																	{
																		JSAst e = visit(c);
																		if(JSAssign.class.isInstance(e))
																		{
																			
																			JSId id = ((JSId)((JSAssign)e).getLeft());
																			id.setValue(symbolTable.getValueCurrent(id.getValue()));																			
																		}
																		return e;
																	}
																	)
										                  .collect(Collectors.toList()));
	                
   }
   @Override
   public JSAst visitFormals(EightBitParser.FormalsContext ctx){
	   EightBitParser.IdListContext idList = ctx.idList();
	   return (idList == null ) ? FORMALS()
	                            : visit(idList);
   }
   
   @Override
   public JSAst visitWhileStatement(EightBitParser.WhileStatementContext ctx){
	   JSAst exp = visit(ctx.expr());
	   JSAst stmt = visit(ctx.closedStatement());
	   if((JSBlock.class.isInstance(stmt)))
	   {
		   ((JSBlock)stmt).setOwner(ID(symbolTable.getCurrent()));
	   }
		  
	   return WHILE(exp, stmt);
   }
   
      @Override
   public JSAst visitLetStatement(EightBitParser.LetStatementContext ctx){
	  JSAst st = visit(ctx.assignStmtList());
	  

	  
	  List<JSAst> s = ((JSBlock)st).getMembers();
	  s.stream().forEach(e -> 
							{
								JSAssign ass = ((JSAssign)e);
											
								JSAtom at = ((JSAtom)ass.getLeft());
								String val = ((String)at.getValue());
								at.setValue(((String)symbolTable.add(val)));
							});
							
			  JSAst stat = visit(ctx.closedStatement());
			  if((JSBlock.class.isInstance(stat)))
		  ((JSBlock)stat).setOwner(ID(symbolTable.getCurrent()));
	  return LET(s,stat);
   }
   
    @Override
   public JSAst visitAssignStmtList(EightBitParser.AssignStmtListContext ctx){
	  
		//visit(ctx.assignStatement().stream());
		return  BLOCK(ctx.assignStatement().stream()
						     .map( c ->	visit(c))
						     .collect(Collectors.toList()));
	
   } 
   
    @Override
   public JSAst visitIfStatement(EightBitParser.IfStatementContext ctx){
	   JSAst exp = visit(ctx.expr());	   
	   List<JSAst> ar = ctx.closedStatement().stream()
	                                                .map( c -> visit(c))
										            .collect(Collectors.toList());
													
		JSAst a = ar.get(0);
		if((JSBlock.class.isInstance(a)))
		  ((JSBlock)a).setOwner(ID(symbolTable.getCurrent()));
		JSAst b = (ar.size()>1)? ar.get(1): null;
		if( b != null && (JSBlock.class.isInstance(a)))
		  ((JSBlock)b).setOwner(ID(symbolTable.getCurrent()));
	   return IF(exp, a, b);
   }
   
   
   
   @Override
   public JSAst visitCallStatement(EightBitParser.CallStatementContext ctx){
	   JSAst id = ID(ctx.ID().getText());
	   JSAst tmp = visit(ctx.arguments());
	   List<JSAst> arg = ((JSBlock)tmp).getMembers();
	   /*arg.stream().forEach(e -> 
								{
									if(JSId.class.isInstance(e))
									{
										((JSId)e).setValue(symbolTable.getValueCurrent(((JSId)e).getValue()));
										System.err.println(((JSId)e).getValue());
									}
										
								}
							);*/
	   return CALL(id, arg);
   }
   
   
   @Override
   public JSAst visitArguments(EightBitParser.ArgumentsContext ctx){
	   return  (ctx.args() != null)? visit(ctx.args()) : BLOCK();	
   } 
   
   @Override
   public JSAst visitArgs(EightBitParser.ArgsContext ctx){
	   return  BLOCK(ctx.expr().stream()
						     .map( c -> visit(c))
						     .collect(Collectors.toList()));
	
   } 
   
   @Override
   public JSAst visitIdList(EightBitParser.IdListContext ctx){
	   return  FORMALS(ctx.id().stream()
						     .map( c -> visit(c))
						     .collect(Collectors.toList()));
	
   } 
   @Override
   public JSAst visitId(EightBitParser.IdContext ctx){
	  return  ID(ctx.ID().getText());
   }
   @Override
    public JSAst visitArithOperation(EightBitParser.ArithOperationContext ctx) {
	   if (ctx.oper == null)
		   return visit(ctx.arithMonom().get(0));
	   JSAst oper = ( ctx.oper.getType() == EightBitParser.ADD ) ? ADD : MINUS;
       List<JSAst> exprs = ctx.arithMonom().stream()
	                                       .map( c -> visit(c) )
										   .collect(Collectors.toList());
										   
										   
		
	   return exprs.stream()
	               .skip(1)
				   .reduce(exprs.get(0), (opers, expr) ->
	                              OPERATION(oper, opers , expr));
	   
    }
    @Override
    public JSAst visitArithMonom(EightBitParser.ArithMonomContext ctx){
		JSAst left = visit(ctx.arithSingle());
		return (ctx.operTDArithSingle() == null) 
		       ? left
		       :ctx.operTDArithSingle().stream()
	                                   .map( c -> visit(c) )
									   .reduce(left, (opers, expr) 
									                      -> FOLD_LEFT(opers , expr));
   } 
   @Override
   public JSAst visitOperTDArithSingle(EightBitParser.OperTDArithSingleContext ctx){
	   JSAst oper = ( ctx.oper.getType() == EightBitParser.MUL ) ? MUL : DIV;
	   JSAst right = visit(ctx.arithSingle());
	   return OPERATION(oper, NULL, right);
   }
   @Override
   public JSAst visitArithIdSingle(EightBitParser.ArithIdSingleContext ctx){
	   if(ctx.id() != null && ctx.arguments() != null)
	   {
			JSAst id = visit(ctx.id());
			JSAst ar =  visit(ctx.arguments());
			List<JSAst> args =  ((JSBlock)ar).getMembers();
			return CALL(id ,args);
	   }
	   else  
	   {
		   JSAst ids = visit(ctx.id());
		   ((JSId)ids).setValue(symbolTable.getValueCurrent(((JSId)ids).getValue()));
			return ids;
	   }
   }
   
   
   @Override
    public JSAst visitRelOperation(EightBitParser.RelOperationContext ctx) {
	   if (ctx.relOperator() == null)
		   return visit(ctx.arithOperation().get(0));
	   else
	   {
		    List<JSAst> arr = ctx.relOperator().stream()
								.map( c -> visit(c))
								.collect(Collectors.toList());
		JSAst oper = (arr.size()>0)? arr.get(0): ID(">"); 
		List<JSAst> exprs = ctx.arithOperation().stream()
	                                       .map( c -> visit(c))
										   .collect(Collectors.toList());							  
	   return exprs.stream()
	               .skip(1)
				   .reduce(exprs.get(0), (opers, expr) ->
	                              OPERATION(oper, opers , expr));
	   }
	 
	   
    }
   
   
     @Override
   public JSAst visitRelOperator(EightBitParser.RelOperatorContext ctx){
      return ID(ctx.getText());
   }
   
   
   
   @Override
   public JSAst visitExprNum(EightBitParser.ExprNumContext ctx){
      return NUM(Integer.valueOf(ctx.NUMBER().getText()));
   }
     
   @Override
   public JSAst visitExprString(EightBitParser.ExprStringContext ctx){
	  return STRING(symbolTable.addString(ctx.STRING().getText()));
   }
   
   @Override
   public JSAst visitExprTrue(EightBitParser.ExprTrueContext ctx){
      return TRUE;
   }
   
   @Override
   public JSAst visitExprFalse(EightBitParser.ExprFalseContext ctx){
      return FALSE;
   }
   
}
  