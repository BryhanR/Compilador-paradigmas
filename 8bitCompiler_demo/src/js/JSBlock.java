package eightBit.js;
import java.util.*;
import java.io.*;
public class JSBlock implements JSAst {
   protected List<JSAst> members;
   protected JSAst owner;
   public List<JSAst> getMembers(){
	   return this.members;
   }
   public JSBlock(List<JSAst> members){
      this.members = members;
   }
   public JSBlock(List<JSAst> members, JSAst ow){
      this.members = members;
	  this.owner = ow;
   }
   
   public void setOwner(JSAst o)
   {
	   this.owner = o;
   }
   
   public void genCode(PrintStream out){
	//out.println("BLOCK...");
	//if(owner != null)
	//owner.genCode();
       this.members.stream().filter(t -> t != null)
	                        .forEach( t -> {
								t.genCode(out);
								if((JSReturn.class.isInstance(t)))
								{
									owner.genCode();
									out.println("_end");
								}
								//out.print((JSReturn.class.isInstance(t))? "" : "");
								//(JSReturn.class.isInstace(t) == true)? true:false;
								
								});
   }
   
}