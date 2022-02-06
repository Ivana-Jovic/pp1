package rs.ac.bg.etf.pp1;
import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;

import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass  extends VisitorAdaptor{

	
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
	
	public SemanticPass() {
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
	
	public boolean passed() {
		return !errorDetected;
	}
	 public void visit(ProgName progName){
//		 progName.obj=Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		 currProgram=Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		 Tab.openScope();
	 }
	    
    public void visit(Program program){
//    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.chainLocalSymbols(currProgram);
    	Tab.closeScope();
    	currProgram= null;
    	
    	if(mainMethod == null || mainMethod.getLevel()>0) {
    		report_error("ERROR! NO MAIN METHOD IN THIS PROGRAM OR HAS FORM PAR", program);
    	}
    	
    }
    
	public void visit(VarDecl varDecl){
		//varDeclCount++;
///!!!!!!!!!!!!!!!!!!!!!!DODAJ
//		report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
//		Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
		currVarType=null;
	}
	
	
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
			}
			else {// u recordu smo i nismo nasli lokalno poklapanje
				varObj = Tab.insert(Obj.Fld, variable.getVarName(), currVarType);
				varObj.setLevel(2);
			}
			
			report_info("VARIABLE " + varObj.getName() + " IS BEING DEFINED!", variable);
		}
		else {//already exists in Tab
			//mima if jos jedan
			report_error("ERROR! VARIABLE " + variable.getVarName() + " ALREADY DEFINED IN SYMBOL TABLE! ", variable);
		}
	}
	
	
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
				}
			else {// u recordu smo i nismo nasli lokalno poklapanje
				varObj = Tab.insert(Obj.Fld, variable.getVarName(), new Struct(Struct.Array,currVarType));
				varObj.setLevel(2);
			}	
				report_info("ARRAY VARIABLE " + varObj.getName() + " IS BEING DEFINED!", variable);
		}
		else {
			//mima if jos jedan
			report_error("ERROR! ARRAY VARIABLE " + variable.getVarName() + " ALREADY DEFINED IN SYMBOL TABLE! ", variable);
		}
	}
	
	
	 public void visit(Type type){
		 Obj objType = Tab.find(type.getTypeName());
			
			if(objType == Tab.noObj){ //tip ne postoji
				report_error("ERROR! TYPE " + type.getTypeName() + " NOT FOUND IN SYMBOL TABLE! ", type);
				currVarType=Tab.noType;
				//ovo nema na  snimku struct..
				type.struct = Tab.noType;
			}
			else if(objType.getKind()!=Obj.Type) {//ako postoji ali nije tip
				report_error("ERROR! " + type.getTypeName() + " IS NOT A VALID!", type);
				currVarType=Tab.noType;
				//ovo nema na  snimku struct..
				type.struct = Tab.noType;
			}
			else {
				currVarType=objType.getType();
				//ovo nema na  snimku struct..
				type.struct = objType.getType();
			}
			//zasto se dodeljuje type structu
	 }
	 
	////CONST 
	 public void visit(NCBConstNum constNum) {
			constValue = constNum.getNumVal();
//			constNum.struct = Tab.intType;
			constType= Tab.intType;
			report_info("CONST VALUE " + constNum.getNumVal() + " IS BEING USED!", constNum);
	}
	 public void visit(NCBConstChar constChar) {
//		 constChar.struct = Tab.charType;
		 constType = Tab.charType;
		 constValue = constChar.getCharVal();
		 report_info("CONST VALUE " + constChar.getCharVal() + " IS BEING USED!", constChar);
	
	}
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
		report_info("CONST VALUE " + constBool.getBoolVal() + " IS BEING USED!", constBool);
	}
	 public void visit(ConstTypeNCB constant) {	
			Obj constObj = Tab.find(constant.getNcbIdent());
			if(constObj!= Tab.noObj) {
				report_error("ERROR! CONST " + constant.getNcbIdent() + " ALREADY DEFINED IN SYMBOL TABLE! ", constant);
			}
			else if(!constType.assignableTo(currVarType)) {
				report_error("ERROR! CONST " + constant.getNcbIdent() + " WRONG TYPE! ", null);
			}
//PROVERI OVO
else if(currVarType.getKind()!=Struct.Int) {
	report_error("ERROR! CONST " + constant.getNcbIdent() + " MUST BE INT! ", null);
}
			else {
				constObj = Tab.insert(Obj.Con, constant.getNcbIdent(), currVarType);
				constObj.setAdr(constValue);
				report_info("CONST  " + constant.getNcbIdent() + " IS BEING DEFINED!", constant);
			
			}
	}
//	 
////	 public void visit(ConstTypeChar constant) {}
////	 public void visit(ConstTypeBool constant) {}
//	////////
//	 ///PRISTUP ELEM NIZA

//	 
//	 public void visit(DesignListSquare designListSquare) {
//		 Obj designNode = Tab.find(designatorIdentPomocnaProm);
//
//			if (designNode == Tab.noObj) {
//				report_error("ERROR! DESIGNATOR IDENT " +designatorIdentPomocnaProm + " DOES NOT EXIST IN SYMBOL TABLE! ", designListSquare);
//			} 
//			else if (designNode.getKind() != Struct.Array) {
//				report_error("ERROR! DESIGNATOR IDENT " + designatorIdentPomocnaProm+ " NOT AN ARRAY! ", designListSquare);
//			}//probaj sa getKind()
//			else if (!designListSquare.getExpr().struct.assignableTo(Tab.intType)) {
//				report_error("ERROR! EXPR INSIDE BRACKETS NOT INT " + designatorIdentPomocnaProm, designListSquare);
//			} 
////			else {
////				report_info(
////						"ARRAY  " + designatorIdentPomocnaProm + "USED ON LINE " + designListSquare.getLine(),
////						designListSquare);
////			}
//
//	 }
//	 
//	 ///////
//	 
	 ////METODE
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
			report_info("DEALING WITH FUNCTION " + methodTypeName.getMethodName(), methodTypeName);
	   
		}
	} 
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
			report_info("DEALING WITH FUNCTION " + methodTypeName.getMethodName(), methodTypeName);
	   
		}
	}
	public void visit(MethodDecl methodDecl){
    	if(!returnFound && currentMethod.getType() != Tab.noType){
			report_error("ERROR! RETURN NOT FOUND... " + currentMethod.getName() + " DOES NOT HAVE A RETURN STMT!", null);
		}
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		returnFound = false;
		isVoid=false;
		currentMethod = null;
	}
//	   @Override
//	   public void visit(SingleStatementRetExpr r){
//			returnFound = true;
//	   }
//	   @Override
//	   public void visit(SingleStatementRet r){
//			if(isVoid)returnFound = true;
//	   }
//	   @Override
//	   public void visit(MethDeclTypeVoid r){
//		   isVoid = true;
//		   returnFound=true;// zato sto nije bitno onda
//	   }
	   
//////////////FORMAL PAR	   
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
				varObj.setFpPos(1);
				currentMethod.setLevel(currentMethod.getLevel() +1);
				report_info("FORM PAR " + varObj.getName() + " IS BEING DEFINED!", formP);
			}
			else {
				report_error("ERROR! FORM PAR " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
			}
		}
		
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
				varObj.setFpPos(1);
				currentMethod.setLevel(currentMethod.getLevel() +1);				
				report_info("FORM PAR SQ " + varObj.getName() + " IS BEING DEFINED!", formP);
			}
			else {
				report_error("ERROR! FORM PAR SQ " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
			}
		}
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
				varObj.setFpPos(1);
				currentMethod.setLevel(currentMethod.getLevel() +1);
				report_info("FORM PAR " + varObj.getName() + " IS BEING DEFINED!", formP);
			}
			else {
				report_error("ERROR! FORM PAR " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
			}
		}
		
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
				varObj.setFpPos(1);
				currentMethod.setLevel(currentMethod.getLevel() +1);				
				report_info("FORM PAR SQ " + varObj.getName() + " IS BEING DEFINED!", formP);
			}
			else {
				report_error("ERROR! FORM PAR SQ " + formP.getVarN() + " ALREADY DEFINED IN SYMBOL TABLE! ", formP);
			}
		}
	public void visit(RecordDeclName recordDeclName) {
		Obj recObj = Tab.find(recordDeclName.getRecName());
		if(recObj!= Tab.noObj) {
			report_error("ERROR! RECORD NAME " + recordDeclName.getRecName() + " ALREADY DEFINED IN SYMBOL TABLE! ", recordDeclName);
		}
		else {
			currRecord = new Struct(Struct.Class);
			recObj = Tab.insert(Obj.Type, recordDeclName.getRecName(), currRecord);
			Tab.openScope();
			report_info("RECORD  " + recordDeclName.getRecName() + " IS BEING DEFINED!", recordDeclName);
		
		}
	}
	public void visit(RecordDecl recordDecl) {
		Tab.chainLocalSymbols(currRecord);
		Tab.closeScope();
		currRecord = null;
	}
//		
/////////////////////////////////////////////???
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
	    public void visit(FactorNum factorNum) {
	    	factorNum.struct = Tab.intType;
	    	report_info("CONST NUMBER " + factorNum.getVal(), factorNum);
	    }
	    
	    public void visit(FactorChar factorChar) {
	    	factorChar.struct = Tab.charType;
	    	report_info("CONST CHAR " + factorChar.getVal(), factorChar);
	    }
	    
	    public void visit(FactorBool factorBool) {
	    	factorBool.struct = boolStruct;
	    	report_info("CONST CHAR " + factorBool.getVal(), factorBool);
	    }
	    
	    public void visit(FactorDes factor) {
	    	factor.struct = factor.getDesignator().obj.getType();
	    }
	    
	    public void visit(FactorDesPar factor) {
	    	factor.struct = factor.getDesignator().obj.getType();
	    }
	   public void visit(FactorDesParenAct factor) {
	    	factor.struct = factor.getDesignator().obj.getType();
	    }
	   /// 
	   public void visit(FactorType factorType) {//pravi se record
	    	factorType.struct = currVarType;// ili sa new class, curType.getMemberstab
	    	report_info("USING KEYWORD NEW!", factorType);
	    }
	    public void visit(FactorTypeExpr factorTypeExpr) {// pravi se niz
	    	if (factorTypeExpr.getExpr().struct.equals(Tab.intType)) {
	    		factorTypeExpr.struct= Tab.noType;
				report_error("ERROR! INDEX OF AN ARRAY MUST BE INT ", factorTypeExpr);
	    	}
	    	else {
				factorTypeExpr.struct = new Struct(Struct.Array, currVarType);
		    	report_info(" USING KEYWORD NEW ARRAY!", factorTypeExpr);
	    	}
	    	
	    }
	    public void visit(FactorParen factorParen) {
	    	factorParen.struct = factorParen.getExpr().struct;
	    }
	    
	    
//	    public void visit(Designator designator) {
////	    	factor.struct = factor.getDesignator().obj.getType();
//	    }
//		 String designatorIdentPomocnaProm="";
		 public void visit(DesignatorId designatorIdent) {
//			 designatorIdentPomocnaProm=designatorIdent.getDesignName();
			 Obj desObj = Tab.find(designatorIdent.getDesName());
			 if (desObj == Tab.noObj) {
				 designatorIdent.obj= Tab.noObj;
				 report_error("ERROR! DESIGN NAME " + designatorIdent.getDesName() + " ALREADY DEFINED IN SYMBOL TABLE! ", designatorIdent);
			}
			 else if(desObj.getKind() != Obj.Con &&desObj.getKind() != Obj.Var &&desObj.getKind() != Obj.Meth ) {
				 designatorIdent.obj= Tab.noObj;
				 report_error("ERROR! DESIGN " + designatorIdent.getDesName() + " TYPE IS NOT OK! ", designatorIdent);
			 }
			 else {
				 designatorIdent.obj= desObj;
			 }
		 }
		 public void visit(DesignatorIdentArr designator) {// OVO JE SIGURNO NIZ!!!
			 Obj desObj = Tab.find(designator.getDesAName());
			 if (desObj == Tab.noObj) {
				 designator.obj= Tab.noObj;
				 report_error("ERROR! DESIGN ARR NAME " + designator.getDesAName() + " ALREADY DEFINED IN SYMBOL TABLE! ", designator);
			}
			 else if(desObj.getType().getKind() != Struct.Array || desObj.getKind() != Obj.Var ) {
				 designator.obj= Tab.noObj;
				 report_error("ERROR! DESIGN ARR " + designator.getDesAName() + " TYPE IS NOT OK! ", designator);
			 }
			 else {
				 designator.obj= desObj;
			 }
		 }
//		 /
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
		 public void visit(DesignatorRec designator) {// poslednji sa desna se upisuje u rec
			 designator.obj= designator.getDesignList().obj;
		 }
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
				 
			 }
		 }
		
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
		 public void visit(DesignatorExprList designator) {
			 designator.obj= designator.getDesignList().obj;
		 }
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
		 
		 public void visit(DesignListExprNoList designator) {
			  if(currRecord == Tab.noType) {
				  designator.obj= Tab.noObj;
				  currRecord= null;
			  }
			  else if (designator.getExpr().struct.equals(Tab.intType)) {
				  designator.obj= Tab.noObj;
				  currRecord= null;
					report_error("ERROR! DesignListExprNoList INDEX OF AN ARRAY MUST BE INT ", designator);
		    	}
			  else {
				  String fname= designator.getDesRAName();
				  for(Obj f : currRecord.getMembers()) {
					  if(f.getName().equals(fname) && f.getType().getKind()== Struct.Array){
						  currRecord= null;
						  designator.obj=new Obj(Obj.Elem,fname + "[$]", f.getType().getElemType());
						 return;
					  }
				  }
				  designator.obj= Tab.noObj;
				  currRecord= null;
				  report_error("ERROR! DESIGN DesignListExprNoList " + designator.getDesRAName() + "NOT OK! ", designator);
			 }
		 }
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
		 
		 public void visit(DesignListExprList designator) {
			 Struct rec = designator.getDesignList().obj.getType();
			  if(rec == Tab.noType) {
				  designator.obj= Tab.noObj;
			  }
			  else if (designator.getExpr().struct.equals(Tab.intType)) {
				  designator.obj= Tab.noObj;
					report_error("ERROR! DesignListExprList INDEX OF AN ARRAY MUST BE INT ", designator);
		    	}
			  else {
				  String fname= designator.getDesRAName();
				  for(Obj f : rec.getMembers()) {
					  if(f.getName().equals(fname) && f.getType().getKind()== Struct.Array){
						  designator.obj=new Obj(Obj.Elem,fname + "[$]", f.getType().getElemType());
						 return;
					  }
				  }
				  designator.obj= Tab.noObj;
				  report_error("ERROR! DESIGN DesignListExprNoList " + designator.getDesRAName() + "NOT OK! ", designator);
			 }
		 }
//	    public void visit(TermFactor termFactor) {
//	    	termFactor.struct = termFactor.getFactor().struct;
//		}
//	    public void visit(TermMlop termMlop) {
//			if (termMlop.getTerm().struct != Tab.intType)
//				report_error("ERROR! TERM IS NOT TYPE INT ", termMlop);
//			else if ( termMlop.getFactor().struct != Tab.intType)
//				report_error("ERROR! FACTOR IS NOT TYPE INT", termMlop);
//			else if(termMlop.getTerm().struct == Tab.intType && termMlop.getFactor().struct == Tab.intType){
//				termMlop.struct = termMlop.getTerm().struct;
//				//info
//			} 
//			else report_error("ERROR! TERM ", termMlop);
//		
//		}
//
//	    public void visit(ExprTerm exprTerm) {
//	    	exprTerm.struct = exprTerm.getTerm().struct;
//			
//		}
//	    public void visit(ExprMin exprMin) {
//			if (exprMin.getTerm().struct.getKind() != Tab.intType.Int)
//				report_error("ERROR! TERM (in expr) IS NOT TYPE INT", exprMin);
//			else {
//				exprMin.struct = exprMin.getTerm().struct;
//			}
//		}
//	    public void visit(ExprAddop exprAddop) {
//	    	if (exprAddop.getTerm().struct.getKind() != Tab.intType.Int) {
//				report_error("ERROR! TERM(in exprAdd)IS NOT TYPE INT", exprAddop);
//			}
//	    	else if (exprAddop.getExpr().struct.getKind() != Tab.intType.Int) {
//				//da li moze zbog rekurzije
//				report_error("ERROR! EXPR(in exprAdd)IS NOT TYPE INT", exprAddop);
//			}
//	    	else if (!exprAddop.getExpr().struct.compatibleWith(exprAddop.getTerm().struct) ) {
//				report_error("ERROR! EXPR NOT COMPATIBLE WITH TERM", exprAddop);
//			}
//	    	else {
//				exprAddop.struct = exprAddop.getTerm().struct;
//			}
//		}
//	    
//	    
//	    
//	    public void visit(SingleStatementRead singleStatementRead) {
//				// U designatoru proveri da li postoji u tabeli simbola
//				if (singleStatementRead.getDesignator().obj.getKind() != Obj.Var 
//						|| singleStatementRead.getDesignator().obj.getKind() != Obj.Elem
//						|| singleStatementRead.getDesignator().obj.getKind() != Obj.Fld) {
//					report_error("ERROR! READ MUST BE VAR,ELEM OR FIELD", singleStatementRead);
//				} 
//				else 
//				// da li je bolje equals ili kao sto sam pre radila
//				// ili izmeni ovde ili na ostalim mestima- sta je bolje????
//				if (singleStatementRead.getDesignator().obj.equals(Tab.intType)
//						|| singleStatementRead.getDesignator().obj.equals(Tab.charType)
//						|| singleStatementRead.getDesignator().obj.equals(boolStruct) ) {
//					report_error("ERROR! READ MUST BE INT,CHAR OR BOOL", singleStatementRead);
//				} 
//				else {
//					report_info("READ", singleStatementRead);
//				}
//		}
//	    public void visit(SingleStatementPrintNum ssPrint) {
//	    	//jos provera??
//	    	if (!(ssPrint.getExpr().struct.equals(Tab.intType) 
//					|| ssPrint.getExpr().struct.equals(Tab.charType)
//					|| ssPrint.getExpr().struct.equals(boolStruct)))
//				report_error("ERROR! PRINT MUST BE INT,CHAR OR BOOL", null);
//			else {
////		ovo u drugom visitu da se numConst uzme
////				ssPrint.struct = Tab.intType;
////		    	report_info("CONST NUMBER " + ssPrint.getVal(), ssPrint);
//			}
//		}
//	    public void visit(SingleStatementPrint ssPrint) {
//	    	//jos provera??
//	    	if (!(ssPrint.getExpr().struct.equals(Tab.intType) 
//					|| ssPrint.getExpr().struct.equals(Tab.charType)
//					|| ssPrint.getExpr().struct.equals(boolStruct)))
//				report_error("ERROR! PRINT MUST BE INT,CHAR OR BOOL", null);
//			else {
////		mozda info
//			}
//		}
//	    
//	    
//	    
//	    public void visit(DesignatorAssignop designatorAssignop) {
//			if (designatorAssignop.getDesignator().obj.getKind() != Obj.Var
//					|| designatorAssignop.getDesignator().obj.getKind() != Obj.Elem
//					|| designatorAssignop.getDesignator().obj.getKind() != Obj.Fld) {
//				report_error("ERROR! DESIGN MUST BE VAR,ELEM OR FIELD ", designatorAssignop);
//			}
//			else if (!designatorAssignop.getExpr().struct.compatibleWith(designatorAssignop.getDesignator().obj.getType())) {
//				report_error("ERROR! EXPR NOT COMPATIBLE WITH DESIGN",designatorAssignop);
//			}
//			else {
//				report_info("ASSIGNOP OK", designatorAssignop);
//			}
//		}
//	    
//	    public void visit(DesignatorPlus designatorPlus) {
//			if (designatorPlus.getDesignator().obj.getKind() != Obj.Var
//					|| designatorPlus.getDesignator().obj.getKind() != Obj.Elem
//					|| designatorPlus.getDesignator().obj.getKind() != Obj.Fld)
//				report_error("ERROR! DESIGN MUST BE VAR,ELEM OR FIELD ",
//						designatorPlus);
//			else if (designatorPlus.getDesignator().obj.getType().getKind() != Struct.Int)
//				report_error("ERROR! DESIGN MUST BE INT", designatorPlus);
//			else {
//				report_info("DESIGNPlusPlus OK", designatorPlus);
//			}
//		}
//	    public void visit(DesignatorMinus designatorMinus) {
//			if (designatorMinus.getDesignator().obj.getKind() != Obj.Var
//					|| designatorMinus.getDesignator().obj.getKind() != Obj.Elem
//					|| designatorMinus.getDesignator().obj.getKind() != Obj.Fld)
//				report_error("ERROR! DESIGN MUST BE VAR,ELEM OR FIELD ",
//						designatorMinus);
//			else if (designatorMinus.getDesignator().obj.getType().getKind() != Struct.Int)
//				report_error("ERROR! DESIGN MUST BE INT", designatorMinus);
//			else {
//				report_info("DESIGNPlusPlus OK", designatorMinus);
//			}
//		}
//	    public void visit(SingleStatemenGoto singleStatemenGoto) {
//	    	//UNUTAR TEKUCE  FJKE
//	    	Obj gotoNode = Tab.find(singleStatemenGoto.getLabel().getLab());
//	    	if (gotoNode == Tab.noObj) {
//				report_error("ERROR! LABEL " +singleStatemenGoto.getLabel().getLab() + " DOES NOT EXIST IN SYMBOL TABLE! ", singleStatemenGoto);
//			} 
//		}
}
