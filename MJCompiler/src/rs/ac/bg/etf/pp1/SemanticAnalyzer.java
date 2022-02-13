package rs.ac.bg.etf.pp1;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;

import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer  extends VisitorAdaptor{

	
	boolean errorDetected = false;
	Logger log = Logger.getLogger(getClass());
	
	Struct currVarType=null;
	Obj currentMethod = null;
	boolean returnFound = false;

	boolean isVoid = false;
	Struct boolStruct = new Struct(Struct.Bool);
	int constValue=0;
	private Obj currProgram;
	private Struct constType;
	private Obj mainMethod;
	private Struct currRecord;
	private int doWhileCnt=0;

	List<Struct> formParamsList;// = new ArrayList<>();
	List<Struct> finalactParamsList = new ArrayList<>();
	Stack<List<Struct>> actParamsStack = new Stack<>();
	int numOfVars;
	
	public SemanticAnalyzer() {
		super();
		Tab.insert(Obj.Type, "bool", boolStruct);
//		Obj boolObj=Tab.insert(Obj.Type, "bool", boolStruct);
//		boolObj.setAdr(-1);
//		boolObj.setLevel(-1);
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	private String convertKindToStr(int i, boolean b) {
		switch (i){// b true Obj
			case 0: return b?"Con":"none";
			case 1: return b?"Var":"int";
			case 2: return b?"Type":"char";
			case 3: return b?"Meth":"arr";
			case 4: return b?"Fld":"class";
			case 5: return b?"Elem":"bool";
			case 6: return b?"Prog":"enum";
			case 7: return b?" ":"Interface";
		}
		return "";	
	} 
	public String printObjNode(Obj o) {
//		return "Name: "+o.getName()+" Kind: "+ o.getKind()+
//				" Type: "+ o.getType()+" Adr: "+ o.getAdr()+" Level: "+o.getLevel()+" FpPos: "+ o.getFpPos();
		return "  { "+ convertKindToStr(o.getKind(),true)+":  "+convertKindToStr(o.getType().getKind(), false)+
				"  "+ o.getAdr()+"  "+o.getLevel()+ "}";
	}
	public boolean passed() {
		return !errorDetected;
	}
	// *************************** PROGRAM ***************************
	@Override
	 public void visit(ProgName progName){
//		 progName.obj=Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		 currProgram=Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		 Tab.openScope();
	 }
	@Override   
    public void visit(Program program){
//    	Tab.chainLocalSymbols(program.getProgName().obj);
		numOfVars=Tab.currentScope().getnVars();// glob var, ne const i ne metode;
    	Tab.chainLocalSymbols(currProgram);
    	Tab.closeScope();
    	currProgram= null;
    	
    	if(mainMethod == null || mainMethod.getLevel()>0) {
    		report_error("ERROR! NO MAIN METHOD IN THIS PROGRAM OR HAS FORM PAR", program);
    	}
    }
	// *************************** VARS ***************************
	@Override
	public void visit(VarDecl varDecl){
		//varDeclCount++;
///!!!!!!!!!!!!!!!!!!!!!!DODAJ
		//report_info("Deklarisana promenljiva "+ varDecl.get(), varDecl);
//		Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
		currVarType=null;
	}
	
	@Override
	public void visit(VariableNoArray variable){
		Obj varObj= null;
		if(currentMethod==null && currRecord==null) {//globalno
			varObj = Tab.find(variable.getVarName());
		} else {// localno
			varObj= Tab.currentScope().findSymbol(variable.getVarName());
		}
if(currVarType==Tab.noType) {
	report_error("ERROR! VARIABLE " + variable.getVarName() + " IS BEING DEFINED, BUT NO TYPE PROVIDED! ", variable);
}
		else if(varObj==null ||varObj== Tab.noObj) {
			if(currRecord== null) {
				varObj = Tab.insert(Obj.Var, variable.getVarName(), currVarType);
				varObj.setFpPos(-1);
			}
			else {// u recordu smo i nismo nasli lokalno poklapanje
				varObj = Tab.insert(Obj.Fld, variable.getVarName(), currVarType);
				//varObj.setFpPos(-1);
				varObj.setLevel(2);
			}
			report_info("~~VARIABLE " + varObj.getName() + " IS BEING DEFINED!"+ printObjNode(varObj), variable);
		}
		else {//already exists in Tab
			//mima if jos jedan
			report_error("ERROR! VARIABLE " + variable.getVarName() + " ALREADY DEFINED IN SYMBOL TABLE! ", variable);
		}
	}
	
	@Override
	public void visit(VariableArray variable){
		Obj varObj= null;
		if(currentMethod==null && currRecord==null) {//globalno
			varObj = Tab.find(variable.getVarName());
		} else {// localno
			varObj= Tab.currentScope().findSymbol(variable.getVarName());
		}
		
if(currVarType==Tab.noType) {
	report_error("ERROR! VARIABLE " + variable.getVarName() + " BEING DEFINED, BUT NO TYPE PROVIDED! ", variable);
		} else if(varObj==null ||varObj== Tab.noObj) {//already exists in Tab
			if(currRecord== null) {
				varObj = Tab.insert(Obj.Var, variable.getVarName(), new Struct(Struct.Array,currVarType));
				varObj.setFpPos(-1);
			}
			else {// u recordu smo i nismo nasli lokalno poklapanje
				varObj = Tab.insert(Obj.Fld, variable.getVarName(), new Struct(Struct.Array,currVarType));
				varObj.setLevel(2);
				//varObj.setFpPos(-1);
			}	
				report_info("~~ARRAY VARIABLE " + varObj.getName() + " IS BEING DEFINED!"+ printObjNode(varObj), variable);
		}
		else {
			//mima if jos jedan
			report_error("ERROR! ARRAY VARIABLE " + variable.getVarName() + " ALREADY DEFINED IN SYMBOL TABLE! ", variable);
		}
	}
	
	// *************************** TYPE ***************************
	@Override
	 public void visit(Type type){
		 Obj objType = Tab.find(type.getTypeName());
			
			if(objType == Tab.noObj){ //tip ne postoji
				report_error("ERROR! TYPE " + type.getTypeName() + " NOT FOUND IN SYMBOL TABLE! ", type);
				currVarType=Tab.noType;
				type.struct = Tab.noType;
			}
			else if(objType.getKind()!=Obj.Type) {//ako postoji ali nije tip
				report_error("ERROR! " + type.getTypeName() + " IS NOT A VALID!", type);
				currVarType=Tab.noType;
				type.struct = Tab.noType;
			}
			else {
				currVarType=objType.getType();
				type.struct = objType.getType();
			}
	 }
	 
	// *************************** CONST ***************************
	@Override
	 public void visit(NCBConstNum constNum) {
			constValue = constNum.getNumVal();
//			constNum.struct = Tab.intType;
			constType= Tab.intType;
			//report_info("CONST VALUE " + constNum.getNumVal() + " IS BEING USED!", constNum);
	}
	@Override
	 public void visit(NCBConstChar constChar) {
//		 constChar.struct = Tab.charType;
		 constType = Tab.charType;
		 constValue = constChar.getCharVal();
		 //report_info("CONST VALUE " + constChar.getCharVal() + " IS BEING USED!", constChar);
	}
	@Override
	 public void visit(NCBConstBool constBool) {
//		 constBool.struct = boolStruct;
		 constType= boolStruct;
		if(constBool.getBoolVal().equals("true")) {
			constValue = 1;
		}
		else if(constBool.getBoolVal().equals("false")) {
			constValue = 0;
		}
		else {
			report_error("ERROR! BOOLEAN MUST BE TRUE OR FALSE! ", null);
		}
		//report_info("CONST VALUE " + constBool.getBoolVal() + " IS BEING USED!", constBool);
	}
	@Override
	 public void visit(ConstTypeNCB constant) {	
			Obj constObj = Tab.find(constant.getNcbIdent());
			if(constObj!= Tab.noObj) {
				report_error("ERROR! CONST " + constant.getNcbIdent() + " ALREADY DEFINED IN SYMBOL TABLE! ", constant);
			}
			else if(!constType.assignableTo(currVarType)) {
				report_error("ERROR! CONST " + constant.getNcbIdent() + " WRONG TYPE! ", null);
			}
			////PROVERI OVO
			//else if(currVarType.getKind()!=Struct.Int) {
			//	report_error("ERROR! CONST " + constant.getNcbIdent() + " MUST BE INT! ", null);
			//}
			else {
				constObj = Tab.insert(Obj.Con, constant.getNcbIdent(), currVarType);
				constObj.setAdr(constValue);
				report_info("~~CONST  " + constant.getNcbIdent() + " IS BEING DEFINED!"+ printObjNode(constObj), constant);
		}
	}

	// *************************** METH ***************************
	@Override
	public void visit(MethodDecl methodDecl){
    	if(!returnFound && currentMethod.getType() != Tab.noType){// ako nije void
			report_error("ERROR! RETURN NOT FOUND... " + currentMethod.getName() + " DOES NOT HAVE A RETURN STMT!", null);
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		returnFound = false;
		isVoid=false;
		currentMethod = null;
	}
	
	@Override
	public void visit(MethodTypeNameVoid methodTypeName){
Obj methodNode = Tab.find(methodTypeName.getMethodName());
if (methodNode != Tab.noObj) {
	report_error("ERROR! METHOD IDENT " + methodTypeName.getMethodName() + " EXISTS IN SYMBOL TABLE! ", null);
} 
		else {	
			////VRATII
//			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethodName(), methodTypeName.getMethDeclType().struct);
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethodName(), Tab.noType);
			if(methodTypeName.getMethodName().equalsIgnoreCase("main")) {
				mainMethod = currentMethod;
			}
			methodTypeName.obj = currentMethod;
			Tab.openScope();
			//report_info("~~ DEFINIG FUNCTION " + methodTypeName.getMethodName(), methodTypeName);
	 }
	} 
	@Override
	public void visit(MethodTypeNameType methodTypeName){
Obj methodNode = Tab.find(methodTypeName.getMethodName());
if (methodNode != Tab.noObj) {
	report_error("ERROR! METHOD IDENT " + methodTypeName.getMethodName() + " EXISTS IN SYMBOL TABLE! ", null);
} 
		else {
			////VRATII
//			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethodName(), methodTypeName.getMethDeclType().struct);
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethodName(), currVarType);
			
			methodTypeName.obj = currentMethod;
			Tab.openScope();
			//report_info("~~DEFINING FUNCTION " + methodTypeName.getMethodName(), methodTypeName);
	  }
	}

	// *************************** SINGLE STMT ***************************
	   @Override
	   public void visit(SingleStatementRetExpr r){
//		   if(currentMethod.getType()== Tab.noType) {}// ako je void
			if(!currentMethod.getType().equals(r.getExpr().struct)) {//nije void
				report_error("ERROR! RETURN WITH P... " + currentMethod.getName() + " BUT RET VALUE IS NOT COMPATIBLE!", null);
			}
			returnFound = true;
	   }
	   @Override
	   public void visit(SingleStatementRet r){
			if(currentMethod.getType()!= Tab.noType) {//nije void
				report_error("ERROR! RETURN WITHOUT P... " + currentMethod.getName() + " BUT METHOD IS NOT VOID!", null);
			}
		   returnFound = true;
	   }
  
	// *************************** FORM PARS ***************************
   @Override
	public void visit(FormParsNoSquare formP){
		Obj varObj= null;
		if(currentMethod==null) {//globalno
			report_error("ERROR! FORM PARS " + formP.getVarN() + " NOT IN METHOD! ", formP);
		} else {// localno
			varObj= Tab.currentScope().findSymbol(formP.getVarN());
		}
		
if(currVarType==Tab.noType) {
	report_error("ERROR! FORM PAR " + formP.getVarN() + " IS BEING DEFINED, BUT NO TYPE PROVIDED! ", formP);
}
		else if(varObj==null ||varObj== Tab.noObj) {//already exists in Tab
			varObj = Tab.insert(Obj.Var, formP.getVarN(), currVarType);
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);
			report_info("~~FORM PAR " + varObj.getName() + " IS BEING USED!"+ printObjNode(varObj), formP);
		}
		else {
			report_error("ERROR! FORM PAR " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
		}
	}
   @Override
	public void visit(FormParsSquare formP){
		Obj varObj= null;
		if(currentMethod==null) {//globalno
			report_error("ERROR! FORM PARS SQ " + formP.getVarN() + " NOT IN METHOD! ", formP);
			
		} else {// localno
			varObj= Tab.currentScope().findSymbol(formP.getVarN());
		}
if(currVarType==Tab.noType) {
	report_error("ERROR! FORM PAR SQ" + formP.getVarN() + " IS BEING DEFINED, BUT NO TYPE PROVIDED! ", formP);
}
		else if(varObj==null ||varObj== Tab.noObj) {//already exists in Tab
			varObj = Tab.insert(Obj.Var, formP.getVarN(), new Struct(Struct.Array,currVarType));
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);				
			report_info("~~FORM PAR SQ " + varObj.getName() + " IS BEING USED!"+printObjNode(varObj), formP);
		}
		else {
			report_error("ERROR! FORM PAR SQ " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
		}
	}
   @Override
	public void visit(FormParsNoSquareList  formP){
		Obj varObj= null;
		if(currentMethod==null) {//globalno
			report_error("ERROR! FORM PARS " + formP.getVarN() + " NOT IN METHOD! ", formP);
		} else {// localno
			varObj= Tab.currentScope().findSymbol(formP.getVarN());
		}
if(currVarType==Tab.noType) {
	report_error("ERROR! FORM PAR " + formP.getVarN() + " IS BEING DEFINED, BUT NO TYPE PROVIDED! ", formP);
}
		else if(varObj==null ||varObj== Tab.noObj) {//already exists in Tab
			varObj = Tab.insert(Obj.Var, formP.getVarN(), currVarType);
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);
			report_info("~~FORM PAR " + varObj.getName() + " IS BEING USED!"+printObjNode(varObj), formP);
		}
		else {
			report_error("ERROR! FORM PAR " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
		}
	}
   @Override
	public void visit(FormParsSquareList formP){
		Obj varObj= null;
		if(currentMethod==null) {//globalno
			report_error("ERROR! FORM PARS SQ " + formP.getVarN() + " NOT IN METHOD! ", formP);
			
		} else {// localno
			varObj= Tab.currentScope().findSymbol(formP.getVarN());
		}
if(currVarType==Tab.noType) {
	report_error("ERROR! FORM PAR SQ" + formP.getVarN() + " IS BEING DEFINED, BUT NO TYPE PROVIDED! ", formP);
}
		else if(varObj==null ||varObj== Tab.noObj) {//already exists in Tab
			varObj = Tab.insert(Obj.Var, formP.getVarN(), new Struct(Struct.Array,currVarType));
			varObj.setFpPos(currentMethod.getLevel());
			currentMethod.setLevel(currentMethod.getLevel() +1);				
			report_info("~~FORM PAR SQ " + varObj.getName() + " IS BEING USED!"+printObjNode(varObj), formP);
		}
		else {
			report_error("ERROR! FORM PAR SQ " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
		}
	}
	// *************************** REC DECL ***************************
	   @Override
	public void visit(RecordDeclName recordDeclName) {
		Obj recObj = Tab.find(recordDeclName.getRecName());
		if(recObj!= Tab.noObj) {
			report_error("ERROR! RECORD NAME " + recordDeclName.getRecName() + " ALREADY DEFINED IN SYMBOL TABLE! ", recordDeclName);
		}
		else {
			currRecord = new Struct(Struct.Class);
			recObj = Tab.insert(Obj.Type, recordDeclName.getRecName(), currRecord);
			Tab.openScope();
			report_info("~~RECORD  " + recordDeclName.getRecName() + " IS BEING DEFINED!"+ printObjNode(recObj), recordDeclName);
		}
	}
	   @Override
	public void visit(RecordDecl recordDecl) {
		Tab.chainLocalSymbols(currRecord);
		Tab.closeScope();
		currRecord = null;
	}
//		
	// *************************** FACTOR ***************************
	   @Override
	 public void visit(Factor factor) {// dohvatim sina i pitam koji je tip
		 if(factor.getUnary() instanceof UnaryMinus) {// imamo mius
			 if(factor.getFactorfactor().struct.equals(Tab.intType)) {// imamo minus i int
				 factor.struct = Tab.intType;
			 }
			 else {
				 report_error("ERROR! MINUS IN FRONT OF NON INT VALUE! ", factor);
				 factor.struct = Tab.noType;// da je factor Obj bilo bi noObj
			 }
		 }
		 else {// nemamo minus -> prosledjujemo
			 factor.struct= factor.getFactorfactor().struct;
		 }
	 }
   @Override
    public void visit(FactorNum factorNum) {
    	factorNum.struct = Tab.intType;
    }
   @Override
    public void visit(FactorChar factorChar) {
    	factorChar.struct = Tab.charType;
    }
   @Override
    public void visit(FactorBool factorBool) {
    	factorBool.struct = boolStruct;
    }
   @Override
    public void visit(FactorDes factor) {
    	factor.struct = factor.getDesignator().obj.getType();
    }
   @Override
    public void visit(FactorDesPar factor) {
    	if(factor.getDesignator().obj.getKind()!= Obj.Meth) {
			 report_error("ERROR! DESIGN FactorDesPar " + factor.getDesignator().obj.getName() + "NOT OK - NOT A METHOD! ", factor);
			 factor.struct= Tab.noType;
    	}
    	else {
    		factor.struct = factor.getDesignator().obj.getType();
    		report_info("~~FUNC CALL " + factor.getDesignator().obj.getName() + "!"+printObjNode(factor.getDesignator().obj), factor);
			
    	}
    }
   @Override
   public void visit(FactorDesParenAct factor) {
	   if(factor.getDesignator().obj.getKind()!= Obj.Meth) {
			 report_error("ERROR! DESIGN FactorDesParenAct " + factor.getDesignator().obj.getName() + "NOT OK - NOT A METHOD! ", factor);
			 factor.struct= Tab.noType;
	   }
	   else {
		   factor.struct = factor.getDesignator().obj.getType();
		 
			 formParamsList = new ArrayList<>();
			 for(Obj form: factor.getDesignator().obj.getLocalSymbols()) {
//?????????? kind lvl
				 if(form.getFpPos()!=-1 && form.getKind()==Obj.Var && form.getLevel()==1) {// kind Var, lvl 1
					 formParamsList.add(form.getType());
				 }
			 }
			 if(finalactParamsList.size() != formParamsList.size()) {
				 report_error("ERROR! FORM PARS LIST SIZE (" +formParamsList.size()+") NOT THE SAME SIZE AS ACT PARS LIST("+ finalactParamsList.size()+")" + factor.getDesignator().obj.getName(), factor);
			 }
			 else {
				 for(int i=0; i<finalactParamsList.size();i++) {
					 if(!finalactParamsList.get(i).assignableTo(formParamsList.get(i))) {
						 report_error("ERROR! ACT PARS LIST  (" +finalactParamsList.size()+") NOT COMAPTIBLE TO FORM PARS LIST("+ formParamsList.size()+") on i=" +i+"; "+ factor.getDesignator().obj.getName(), factor);
					}
				 }
			 }
			 report_info("~~FUNC CALL " + factor.getDesignator().obj.getName() + "!"+printObjNode(factor.getDesignator().obj), factor);
				
	   }
    }
   @Override
   public void visit(FactorType factorType) {//pravi se record
    	factorType.struct = currVarType;// ili sa new class, curType.getMemberstab
    }
   @Override
    public void visit(FactorTypeExpr factorTypeExpr) {// pravi se niz
    	if (!factorTypeExpr.getExpr().struct.equals(Tab.intType)) {
    		factorTypeExpr.struct= Tab.noType;
			report_error("ERROR! INDEX OF AN ARRAY MUST BE INT ", factorTypeExpr);
    	}
    	else {
			factorTypeExpr.struct = new Struct(Struct.Array, currVarType);
	   }
    }
   @Override
    public void visit(FactorParen factorParen) {
    	factorParen.struct = factorParen.getExpr().struct;
    }
	    
	// *************************** TERM ***************************
   @Override
    public void visit(TermFactor termFactor) {
	   termFactor.struct = termFactor.getFactor().struct;
	}
	   @Override
    public void visit(TermMlop termMlop) {
		if (!termMlop.getTerm().struct.equals(Tab.intType)) {
			termMlop.struct = Tab.noType;
			report_error("ERROR! TERM IS NOT TYPE INT ", termMlop);
		}
		else if (! termMlop.getFactor().struct.equals(Tab.intType)) {
			termMlop.struct = Tab.noType;
			report_error("ERROR! FACTOR IS NOT TYPE INT ", termMlop);
		}
		else {
			termMlop.struct = Tab.intType;
		}
	}
	// *************************** EXPR ***************************
	   @Override
    public void visit(ExprTerm exprTerm) {
    	exprTerm.struct = exprTerm.getTerm().struct;
	}
	   @Override
    public void visit(ExprAddop exprAddop) {
    	if (!exprAddop.getTerm().struct.equals(Tab.intType)) {
    		exprAddop.struct =Tab.noType;
			report_error("ERROR! TERM(in exprAdd)IS NOT TYPE INT", exprAddop);
		}
    	else if (!exprAddop.getExpr().struct.equals(Tab.intType)) {
    		exprAddop.struct =Tab.noType;
			report_error("ERROR! EXPR(in exprAdd)IS NOT TYPE INT", exprAddop);
		}
    	///??????
else if (!exprAddop.getExpr().struct.compatibleWith(exprAddop.getTerm().struct) ) {
	report_error("ERROR! EXPR NOT COMPATIBLE WITH TERM", exprAddop);
}
    	else {
			exprAddop.struct = Tab.intType ;
		}
	}
    

	// *************************** DESIGNATORS ***************************
   @Override
	 public void visit(DesignatorId designatorIdent) {
//			 designatorIdentPomocnaProm=designatorIdent.getDesignName();
		 Obj desObj = Tab.find(designatorIdent.getDesName());
		 if (desObj == Tab.noObj) {
			 designatorIdent.obj= Tab.noObj;
			 report_error("ERROR! DESIGN NAME " + designatorIdent.getDesName() + " NOT DEFINED IN SYMBOL TABLE! ", designatorIdent);
		}
		 else if(desObj.getKind() != Obj.Con &&desObj.getKind() != Obj.Var &&desObj.getKind() != Obj.Meth ) {
			 designatorIdent.obj= Tab.noObj;
			 report_error("ERROR! DESIGN " + designatorIdent.getDesName() + " TYPE IS NOT OK! ", designatorIdent);
		 }
		 else {
			 designatorIdent.obj= desObj;
			 if(designatorIdent.obj.getLevel()==1 && (designatorIdent.obj.getKind()== Obj.Var||designatorIdent.obj.getKind()== Obj.Con)) {
				 report_info("~~USING DESIGNATOR LOCAL VAR "+designatorIdent.getDesName()+"!"+printObjNode(desObj), designatorIdent);
			 }else if(designatorIdent.obj.getLevel()==0 && (designatorIdent.obj.getKind()== Obj.Var||designatorIdent.obj.getKind()== Obj.Con)) {
				 report_info("~~USING DESIGNATOR GLOBAL VAR "+designatorIdent.getDesName()+"!"+printObjNode(desObj), designatorIdent);
			}else {}
			}
	 }
   @Override
	 public void visit(DesignatorIdentArr designator) {// OVO JE SIGURNO NIZ!!!
		 Obj desObj = Tab.find(designator.getDesAName());
		 if (desObj == Tab.noObj) {
			 designator.obj= Tab.noObj;
			 report_error("ERROR! DESIGN ARR NAME " + designator.getDesAName() + " NOT DEFINED IN SYMBOL TABLE! ", designator);
		}
		 else if(desObj.getType().getKind() != Struct.Array || desObj.getKind() != Obj.Var ) {
			 designator.obj= Tab.noObj;
			 report_error("ERROR! DESIGN ARR " + designator.getDesAName() + " TYPE IS NOT OK! ", designator);
		 }
		 else {
			 designator.obj= desObj;
			 report_info("~~USING ARRAY " + desObj.getName() + "!"+printObjNode(desObj), designator);
		 }
	 }
	 @Override
	 public void visit(DesignatorExprNoList designator) {// OVO JE SIGURNO ELEMENT NIZA!!!
		 Obj desObj = designator.getDesignatorIdentArr().obj;
		 if (desObj == Tab.noObj) {
			 designator.obj= Tab.noObj;
			 report_error("ERROR! DESIGN ARR NAME ENL " + designator.getDesignatorIdentArr().getDesAName()+ " ALREADY DEFINED IN SYMBOL TABLE! ", designator);
		}
		 else if(!designator.getExpr().struct.equals(Tab.intType)) {
			 designator.obj= Tab.noObj;
			 report_error("ERROR! DESIGN ARR ENL " + designator.getDesignatorIdentArr().getDesAName() + " TYPE IS NOT OK! ", designator);
		 }
		 else {
			 designator.obj= new Obj(Obj.Elem,desObj.getName() + "[$]", desObj.getType().getElemType());
		 }
	 }
	 @Override
	 public void visit(DesignatorRec designator) {// poslednji sa desna se upisuje u rec
		 designator.obj= designator.getDesignList().obj;
	 }
	 @Override
	 public void visit(DesignatorIdentRec designator) {
		 Obj desObj = Tab.find(designator.getDesRName());
		 if (desObj == Tab.noObj) {
			 designator.obj= Tab.noObj;
			 currRecord = Tab.noType;
			 report_error("ERROR! DESIGN REC NAME " + designator.getDesRName() + " ALREADY DEFINED IN SYMBOL TABLE! ", designator);
		}
		 else if(desObj.getType().getKind() != Struct.Class || desObj.getKind() != Obj.Var ) {
			 designator.obj= Tab.noObj;
			 currRecord = Tab.noType;
			 report_error("ERROR! DESIGN REC " + designator.getDesRName() + " TYPE IS NOT OK! ", designator);
		 }
		 else {
			 currRecord= desObj.getType();// u  ovom slcaju je upotreba ove prom drugacija
			 designator.obj= desObj;
			 report_info("~~USING RECORD " + desObj.getName() + "!"+printObjNode(desObj), designator);
		 }
	 }
	 /////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!PROVERI
//	 @Override
//	 public void visit(DesignatorIdentRecArr designator) {
//		 Obj desObj = Tab.find(designator.getDesRAName());
//		 if (desObj == Tab.noObj) {
//			 designator.obj= Tab.noObj;
//			 report_error("ERROR! DESIGN RECARR NAME " + designator.getDesRAName() + " NOT DEFINED IN SYMBOL TABLE! ", designator);
//		}
//		 else if(desObj.getType().getKind() != Struct.Array || desObj.getKind() != Obj.Var ) {
//			 designator.obj= Tab.noObj;
//			 report_error("ERROR! DESIGN RECARR " + designator.getDesRAName() + " TYPE IS NOT OK! ", designator);
//		 }
//		 else {
//			 designator.obj= desObj;
//			 //report_info("~~USING ARRAY-RECARR " + desObj.getName() + "!"+printObjNode(desObj), designator);
//		 }
//	 }
	 @Override
	 public void visit(DesignRecElem designator) {// OVO JE SIGURNO ELEMENT NIZA -- LISTA!!!
		 Obj desObj = designator.getDesignatorIdentArr().obj;
		 if (desObj == Tab.noObj) {
			 designator.obj= Tab.noObj;
			 currRecord = Tab.noType;
			 report_error("ERROR! DESIGN ARR NAME ENL " + designator.getDesignatorIdentArr().getDesAName()+ " ALREADY DEFINED IN SYMBOL TABLE! ", designator);
		}
		 else if(!designator.getExpr().struct.equals(Tab.intType)) {
			 designator.obj= Tab.noObj;
			 currRecord = Tab.noType;
			 report_error("ERROR! DESIGN ARR ENL " + designator.getDesignatorIdentArr().getDesAName() + " TYPE IS NOT OK! ", designator);
		 }
		 else {
			  Obj recObj= new Obj(Obj.Elem,desObj.getName() + "[$]", desObj.getType().getElemType());
			 if(recObj.getType().getKind()!= Struct.Class) {
				 report_error("ERROR! DESIGN REC LI " + designator.getDesignatorIdentArr().getDesAName() + " TYPE IS NOT OK! ", designator);
				 designator.obj= Tab.noObj;  
				 currRecord = Tab.noType;
			 }
			 else {
				 designator.obj= recObj;
				 currRecord= designator.obj.getType();// u  ovom slcaju je upotreba ove prom drugacija
			 }
		 } 
	 }
	 @Override
	 public void visit(DesignatorExprList designator) {
		 designator.obj= designator.getDesignList().obj;
	 }
	 @Override
	 public void visit(DesignListIdNoList designator) {
		  if(currRecord == Tab.noType) {
			  currRecord= null;
			  designator.obj= Tab.noObj;
		  }
		  else {
			  String fname= designator.getDesLiName();
			  for(Obj f : currRecord.getMembers()) {
				  if(f.getName().equals(fname)){
					  currRecord= null;
					  designator.obj=f;
					  return;
				  }
			  }
			  designator.obj= Tab.noObj;
			  currRecord= null;
			  report_error("ERROR! DESIGN DesignListIdNoList " + designator.getDesLiName() + "NOT OK! ", designator);
		 }
	 }
	 @Override
	 public void visit(DesignListExprNoList designator) {
		  if(currRecord == Tab.noType) {
			  designator.obj= Tab.noObj;
			  currRecord= null;
		  }
		  else if (!designator.getExpr().struct.equals(Tab.intType)) {
			  designator.obj= Tab.noObj;
			  currRecord= null;
				report_error("ERROR! DesignListExprNoList INDEX OF AN ARRAY MUST BE INT ", designator);
	    	}
		  else {
			  String fname=  designator.getDesignatorIdentRecArr().getDesRAName();//menjano
			  for(Obj f : currRecord.getMembers()) {
				  if(f.getName().equals(fname) && f.getType().getKind()== Struct.Array){
					  currRecord= null;
					  designator.obj=new Obj(Obj.Elem,fname + "[$]", f.getType().getElemType());
					  designator.getDesignatorIdentRecArr().obj=f;//menjano
					  return;
				  }
			  }
			  designator.obj= Tab.noObj;
			  currRecord= null;
			  report_error("ERROR! DESIGN DesignListExprNoList " + fname + "NOT OK! ", designator);
		 }
	 }
	 @Override
	 public void visit(DesignListIdList designator) {
		 Struct rec = designator.getDesignList().obj.getType();
		  if(rec == Tab.noType) {
			  designator.obj= Tab.noObj;
		  }
		  else {
			  String fname= designator.getDesLiName();
			  for(Obj f : rec.getMembers()) {
				  if(f.getName().equals(fname)){
					  designator.obj=f;
					  return;
				  }
			  }
			  designator.obj= Tab.noObj;
			  report_error("ERROR! DESIGN DesignListIdNoList " + designator.getDesLiName() + "NOT OK! ", designator);
		 }
	 }
	 @Override
	 public void visit(DesignListExprList designator) {
		 Struct rec = designator.getDesignList().obj.getType();
		  if(rec == Tab.noType) {
			  designator.obj= Tab.noObj;
		  }
		  else if (!designator.getExpr().struct.equals(Tab.intType)) {
			  designator.obj= Tab.noObj;
				report_error("ERROR! DesignListExprList INDEX OF AN ARRAY MUST BE INT ", designator);
	    	}
		  else {
			  String fname= designator.getDesignatorIdentRecArr().getDesRAName();//menjano
			  for(Obj f : rec.getMembers()) {
				  if(f.getName().equals(fname) && f.getType().getKind()== Struct.Array){
					  designator.obj=new Obj(Obj.Elem,fname + "[$]", f.getType().getElemType());
					  designator.getDesignatorIdentRecArr().obj=f;//menjano
					 // currRecord= null;//menjano
					  return;
				  }
			  }
			  designator.obj= Tab.noObj;
			  report_error("ERROR! DESIGN DesignListExprNoList " + fname + "NOT OK! ", designator);
		 }
		 // currRecord= null;//menjno
	 }
	 @Override
	 public void visit(DesignatorActpars designator) {
		 if(designator.getDesignator().obj.getKind()!= Obj.Meth) {
			 report_error("ERROR! DESIGN DesignatorActpars " + designator.getDesignator().obj.getName() + "NOT OK - NOT A METHOD! ", designator);
		 }
		 else {
			 formParamsList = new ArrayList<>();
			 for(Obj form: designator.getDesignator().obj.getLocalSymbols()) {
//?????????? kind lvl
				 if(form.getFpPos()!=-1 && form.getKind()==Obj.Var && form.getLevel()==1) {// kind Var, lvl 1
					 formParamsList.add(form.getType());
				 }
			 }
			 if(finalactParamsList.size() != formParamsList.size()) {
				 report_error("ERROR! FORM PARS LIST SIZE (" +formParamsList.size()+") NOT THE SAME SIZE AS ACT PARS LIST("+ finalactParamsList.size()+")" + designator.getDesignator().obj.getName(), designator);
			 }
			 else {
				 for(int i=0; i<finalactParamsList.size();i++) {
					 if(!finalactParamsList.get(i).assignableTo(formParamsList.get(i))) {
						 report_error("ERROR! ACT PARS LIST  (" +finalactParamsList.size()+") NOT COMAPTIBLE TO FORM PARS LIST("+ formParamsList.size()+") on i=" +i+"; "+ designator.getDesignator().obj.getName(), designator);
					}
				 }
			 }
			 report_info("~~FUNC CALL " + designator.getDesignator().obj.getName() + "!"+printObjNode(designator.getDesignator().obj), designator);
		}
		//????PRIPREMA ZA SLEDECI DES
		// actParamsStack.push(new ArrayList<>());
	 }
	 @Override
	 public void visit(DesignatorParen designator) {
		 if(designator.getDesignator().obj.getKind()!= Obj.Meth) {
			 report_error("ERROR! DESIGN DesignatorActpars " + designator.getDesignator().obj.getName() + "NOT OK - NOT A METHOD! ", designator);
		 }
		 report_info("~~FUNC CALL " + designator.getDesignator().obj.getName() + "!"+printObjNode(designator.getDesignator().obj), designator);
	 }
	 
	 
	// *************************** SINGLE STMT ***************************
	 @Override
public void visit(SingleStatementRead singleStatementRead) {
	if (singleStatementRead.getDesignator().obj.getKind() != Obj.Var 
			&& singleStatementRead.getDesignator().obj.getKind() != Obj.Elem
			&& singleStatementRead.getDesignator().obj.getKind() != Obj.Fld) {
		report_error("ERROR! READ MUST BE VAR,ELEM OR FIELD", singleStatementRead);
	} 
	else if (!singleStatementRead.getDesignator().obj.getType().equals(Tab.intType)
			&& !singleStatementRead.getDesignator().obj.getType().equals(Tab.charType)
			&& !singleStatementRead.getDesignator().obj.getType().equals(boolStruct) ) {
		report_error("ERROR! READ MUST BE INT,CHAR OR BOOL", singleStatementRead);
	} 
	else {}
}
	 @Override
    public void visit(SingleStatementPrintNum ssPrint) {
    	if (!ssPrint.getExpr().struct.equals(Tab.intType) 
				&& !ssPrint.getExpr().struct.equals(Tab.charType)
				&& !ssPrint.getExpr().struct.equals(boolStruct))
			report_error("ERROR! PRINT MUST BE INT,CHAR OR BOOL", ssPrint);
		else {}
	}
	 @Override
    public void visit(SingleStatementPrint ssPrint) {
    	if (!ssPrint.getExpr().struct.equals(Tab.intType) 
    			&& !ssPrint.getExpr().struct.equals(Tab.charType)
				&& !ssPrint.getExpr().struct.equals(boolStruct))
			report_error("ERROR! PRINT MUST BE INT,CHAR OR BOOL", ssPrint);
		else {}
	}
	 @Override
    public void visit(SingleStatementBreak singleStatementBreak) {
    	if(doWhileCnt==0) {
    		report_error("ERROR! BREAK MUST BE IN A LOOP", singleStatementBreak);
    	}
	}
	 @Override
    public void visit(SingleStatementContinue singleStatementContinue) {
    	if(doWhileCnt==0) {
    		report_error("ERROR! CONTINUE MUST BE IN A LOOP", singleStatementContinue);
    	}
	}
	 @Override
    public void visit(DoStmt doStmt) {
	    doWhileCnt+=1;
	}
	 @Override
    public void visit(SingleStatementDo singleStatementDo) {
	    doWhileCnt-=1;
	    if(!singleStatementDo.getConditionDone().struct.equals(boolStruct)) {
	    	report_error("ERROR! CONDITION ( kind:"+ singleStatementDo.getConditionDone().struct.getKind()+") MUST BE BOOL",singleStatementDo);
		 }
	}
    
    @Override
    public void visit(SingleStatementIf singleStatementIf) {
	    if(!singleStatementIf.getConditionDone().struct.equals(boolStruct)) {
	    	report_error("ERROR! CONDITION ( kind:"+ singleStatementIf.getConditionDone().struct.getKind()+") MUST BE BOOL",singleStatementIf);
		 }
	}
    
    @Override
    public void visit(SingleStatementIfElse singleStatementIfElse) {
	    if(!singleStatementIfElse.getConditionDone().struct.equals(boolStruct)) {
	    	report_error("ERROR! CONDITION ( kind:"+ singleStatementIfElse.getConditionDone().struct.getKind()+") MUST BE BOOL",singleStatementIfElse);
		 }
	}
    
 // *************************** DESIGN ***************************
    @Override
    public void visit(DesignatorAssignop designatorAssignop) {
		if (designatorAssignop.getDesignator().obj.getKind() != Obj.Var
				&& designatorAssignop.getDesignator().obj.getKind() != Obj.Elem
				&& designatorAssignop.getDesignator().obj.getKind() != Obj.Fld) {
			report_error("ERROR! DESIGN MUST BE VAR,ELEM OR FIELD "+ designatorAssignop.getDesignator().obj.getName(), designatorAssignop);
		}//src pa dest tj da li je leva strana dodeljiva desnoj
		else if (!designatorAssignop.getExpr().struct.assignableTo(designatorAssignop.getDesignator().obj.getType())) {
			report_error("ERROR! EXPR NOT COMPATIBLE WITH DESIGN"+ designatorAssignop.getDesignator().obj.getName(),designatorAssignop);
		}
		else {}
	}
    @Override
    public void visit(DesignatorPlus designatorPlus) {
		if (designatorPlus.getDesignator().obj.getKind() != Obj.Var
				&& designatorPlus.getDesignator().obj.getKind() != Obj.Elem
				 && designatorPlus.getDesignator().obj.getKind() != Obj.Fld)
			report_error("ERROR! DESIGN "+ designatorPlus.getDesignator().obj.getName()+"MUST BE VAR,ELEM OR FIELD ",
					designatorPlus);
		else if (!designatorPlus.getDesignator().obj.getType().equals(Tab.intType))
			report_error("ERROR! DESIGN  "+ designatorPlus.getDesignator().obj.getName()+" MUST BE INT", designatorPlus);
		else {}
	}
    @Override
    public void visit(DesignatorMinus designatorMinus) {
		if (designatorMinus.getDesignator().obj.getKind() != Obj.Var
				&& designatorMinus.getDesignator().obj.getKind() != Obj.Elem
				&& designatorMinus.getDesignator().obj.getKind() != Obj.Fld)
			report_error("ERROR! DESIGN  "+ designatorMinus.getDesignator().obj.getName()+" MUST BE VAR,ELEM OR FIELD ",
					designatorMinus);
		else if (!designatorMinus.getDesignator().obj.getType().equals(Tab.intType))
			report_error("ERROR! DESIGN  "+ designatorMinus.getDesignator().obj.getName()+" MUST BE INT", designatorMinus);
		else {}
	}
    
    
 // *************************** COND ***************************
    @Override
    public void visit(CondFactNoList condFactNoList) {
    	if(!condFactNoList.getExpr().struct.equals(boolStruct)){
    		condFactNoList.struct= Tab.noType;
    		report_error("ERROR! CONDITION MUST BE BOOL", condFactNoList);
    		}
    	else {
    		condFactNoList.struct= boolStruct;
    	}
    }
    @Override
    public void visit(CondFactList condFactList) {
    	if(!condFactList.getExpr().struct.compatibleWith(condFactList.getExpr1().struct)){
    		condFactList.struct= Tab.noType;
    		report_error("ERROR! CONDITION VARS MUST BE COMPATIBLE", condFactList);
    	}
    	else if(condFactList.getExpr().struct.isRefType()
    			|| condFactList.getExpr1().struct.isRefType()){
    		 if((condFactList.getRelop() instanceof RelopEqual)
 	    			|| (condFactList.getRelop() instanceof RelopNotEq)){
    			 condFactList.struct= boolStruct;
 	    	}
    		 else {
    			 condFactList.struct= Tab.noType;
	 	    		report_error("ERROR! CONDITION ARR/CL RELOP MUST BE != or ==", condFactList);
    		 }
    	}
    	else {
    		condFactList.struct= boolStruct;
    	}
    }
	@Override
    public void visit(ConditionDone conditionDone) {
		conditionDone.struct = conditionDone.getCondition().struct;
	}
	@Override
    public void visit(CondTermDone condTermDone) {
		condTermDone.struct = condTermDone.getCondTerm().struct;
	}
	@Override
    public void visit(CondTermNoList condTermNoList) {
    	condTermNoList.struct = condTermNoList.getCondFact().struct;
	}
    
	@Override
    public void visit(CondTermList condTermList) {
    	if (!condTermList.getCondTerm().struct.equals(boolStruct)) {
    		condTermList.struct =Tab.noType;
			report_error("ERROR! AND CONDITION(in condTermList)IS NOT TYPE BOOL", condTermList);
		}
    	else if (!condTermList.getCondFact().struct.equals(boolStruct)) {
    		condTermList.struct =Tab.noType;
			report_error("ERROR! AND CONDITION(in condTermList) IS NOT TYPE BOOL", condTermList);
		}
    	else {
    		condTermList.struct = boolStruct;
		}
	}
	@Override
    public void visit(ConditionNoList conditionNoList) {
    	conditionNoList.struct = conditionNoList.getCondTermDone().struct;
	}
	@Override
    public void visit(ConditionList conditionList) {
    	if (!conditionList.getCondition().struct.equals(boolStruct)) {
    		conditionList.struct =Tab.noType;
			report_error("ERROR! OR CONDITION IS NOT TYPE BOOL", conditionList);
		}
    	else if (!conditionList.getCondTermDone().struct.equals(boolStruct)) {
    		conditionList.struct =Tab.noType;
			report_error("ERROR! OR CONDITION IS NOT TYPE BOOL", conditionList);
		}
    	else {
    		conditionList.struct = boolStruct;
		}
	}
	
	// *************************** ACT PARS ***************************
	@Override
    public void visit(ActParsNoList actParsNoList) {
    	actParamsStack.peek().add(actParsNoList.getExpr().struct);
    }
	@Override
    public void visit(ActParsList actParsList) {
    	actParamsStack.peek().add(actParsList.getExpr().struct);
    }
	@Override
    public void visit(ActParsFirst actParsFirst) {
    	actParamsStack.push(new ArrayList<>());
    }
	@Override
    public void visit(ActParsWrap actParsWrap) {
    	finalactParamsList=  actParamsStack.pop();
    }
    //DesignatorActpars
    //FactorDesParenAct
}