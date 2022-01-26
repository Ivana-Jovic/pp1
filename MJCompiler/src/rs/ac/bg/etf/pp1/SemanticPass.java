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
	
	public SemanticPass() {
		super();
		Tab.insert(Obj.Type, "bool", boolStruct);
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
	
	
	 public void visit(ProgName progName){
		 progName.obj=Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
	    Tab.openScope();
	 }
	    
    public void visit(Program program){
//    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
	public void visit(VarDecl varDecl){
		//varDeclCount++;
///!!!!!!!!!!!!!!!!!!!!!!DODAJ
//		report_info("Deklarisana promenljiva "+ varDecl.getVarName(), varDecl);
//		Obj varNode = Tab.insert(Obj.Var, varDecl.getVarName(), varDecl.getType().struct);
		currVarType=null;
	}
	
	
	public void visit(VariableNoArray variable){
		Obj error_handler = Tab.find(variable.getVarName());
		if(error_handler!= Tab.noObj) {//already exists in Tab
//mima if jos jedan
			report_error("ERROR! VARIABLE " + variable.getVarName() + " ALREADY DEFINED IN SYMBOL TABLE! ", null);
		}
		else if(currVarType==null) {
			//da li treba proverriti ako je noType
			report_error("ERROR! VARIABLE " + variable.getVarName() + " IS BEING DEFINED - NO TYPE PROVIDED! ", null);
		}
		else {
			
		}
		Obj objVar = Tab.insert(Obj.Var, variable.getVarName(), currVarType);
		report_info("VARIABLE " + objVar.getName() + " IS BEING DEFINED!", variable);
	}
	
	
	public void visit(VariableArray variable){
		Obj error_handler = Tab.find(variable.getVarName());
		if(error_handler!= Tab.noObj) {//already exists in Tab
			//mima if jos jedan
			report_error("ERROR! VARIABLE " + variable.getVarName() + " ALREADY DEFINED IN SYMBOL TABLE! ", null);
		}
		else if(currVarType==null) {//nema tip
			//da li treba proverriti ako je noType
			report_error("ERROR! VARIABLE " + variable.getVarName() + " BEING DEFINED, BUT NO TYPE PROVIDED! ", null);
		}
		else {
			
		}
		Obj objVar = Tab.insert(Obj.Var, variable.getVarName(), new Struct(Struct.Array,currVarType));
		report_info("ARRAY VARIABLE " + objVar.getName() + " IS BEING DEFINED!", variable);
				
	}
	
	
	 public void visit(Type type){
		 Obj objType = Tab.find(type.getTypeName());
			
			if(objType == Tab.noObj){ //tip ne postoji
				report_error("TYPE " + type.getTypeName() + " NOT FOUND IN SYMBOL TABLE! ", null);
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
			
			
			//zasto se dodeljuje type structu

	 }
	 
	////CONST 
	 public void visit(NCBConstNum constNum) {
			constValue = constNum.getNumVal();
			constNum.struct = Tab.intType;
			report_info("CONST VALUE " + constNum + " IS BEING USED!", constNum);
	}
	 public void visit(NCBConstChar constChar) {
		 constChar.struct = Tab.charType;
		 constValue = constChar.getCharVal();
		 report_info("CONST VALUE " + constChar.getCharVal() + " IS BEING USED!", constChar);
	
	}
	 public void visit(NCBConstBool constBool) {
		 constBool.struct = boolStruct;
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
			Obj error_handler = Tab.find(constant.getNcbIdent());
			if(error_handler!= Tab.noObj) {
				report_error("ERROR! CONST " + constant.getNcbIdent() + " ALREADY DEFINED IN SYMBOL TABLE! ", null);
			}
			else if(!constant.getNCBConst().struct.equals(currVarType)) {
				report_error("ERROR! CONST " + constant.getNcbIdent() + " WRONG TYPE! ", null);
			}
			else if(currVarType.getKind()!=Struct.Int) {
				report_error("ERROR! CONST " + constant.getNcbIdent() + "MUST BE INT! ", null);
			}
			else {
				Obj numNode = Tab.insert(Obj.Con, constant.getNcbIdent(), currVarType);
				numNode.setAdr(constValue);
				report_info("CONST  " + constant.getNcbIdent() + " IS BEING DEFINED!", constant);
			
			}
	}
	 
//	 public void visit(ConstTypeChar constant) {}
//	 public void visit(ConstTypeBool constant) {}
	////////
	 ///PRISTUP ELEM NIZA
	 String designatorIdentPomocnaProm="";
	 public void visit(DesignatorIdent designatorIdent) {
		 designatorIdentPomocnaProm=designatorIdent.getDesignName();
	 }
	 
	 public void visit(DesignListSquare designListSquare) {
		 Obj designNode = Tab.find(designatorIdentPomocnaProm);

			if (designNode == Tab.noObj) {
				report_error("ERROR! DESIGNATOR IDENT " +designatorIdentPomocnaProm + " DOES NOT EXIST IN SYMBOL TABLE! ", designListSquare);
			} 
			else if (designNode.getKind() != Struct.Array) {
				report_error("ERROR! DESIGNATOR IDENT " + designatorIdentPomocnaProm+ " NOT AN ARRAY! ", designListSquare);
			}//probaj sa getKind()
			else if (!designListSquare.getExpr().struct.assignableTo(Tab.intType)) {
				report_error("ERROR! EXPR INSIDE BRACKETS NOT INT " + designatorIdentPomocnaProm, designListSquare);
			} 
//			else {
//				report_info(
//						"ARRAY  " + designatorIdentPomocnaProm + "USED ON LINE " + designListSquare.getLine(),
//						designListSquare);
//			}

	 }
	 
	 ///////
	 
	 ////METODE
	public void visit(MethodTypeName methodTypeName){
		Obj methodNode = Tab.find(methodTypeName.getMethodName());
		
		if (methodNode != Tab.noObj) {
			report_error("ERROR! METHOD IDENT " +designatorIdentPomocnaProm + " EXISTS IN SYMBOL TABLE! ", null);
		} 
		else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethodName(), methodTypeName.getMethDeclType().struct);
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
	   
	   public void visit(SingleStatementRetExpr r){
			returnFound = true;
	   }
	   public void visit(SingleStatementRet r){
			if(isVoid)returnFound = true;
	   }
	   public void visit(MethDeclTypeVoid r){
		   isVoid = true;
		   returnFound=true;// zato sto nije bitno onda
	   }
///////////////////////////////////////////???
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
	    
	    public void visit(FactorType factorType) {
	    	factorType.struct = new Struct(Struct.Array, currVarType);
	    	report_info("USING KEYWORD NEW!", factorType);
	    }
	    public void visit(FactorTypeExpr factorTypeExpr) {
	    	if (factorTypeExpr.getExpr().struct!=Tab.intType) {
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
	    
	    public void visit(TermFactor termFactor) {
	    	termFactor.struct = termFactor.getFactor().struct;
		}
	    public void visit(TermMlop termMlop) {
			if (termMlop.getTerm().struct != Tab.intType)
				report_error("ERROR! TERM IS NOT TYPE INT ", termMlop);
			else if ( termMlop.getFactor().struct != Tab.intType)
				report_error("ERROR! FACTOR IS NOT TYPE INT", termMlop);
			else if(termMlop.getTerm().struct == Tab.intType && termMlop.getFactor().struct == Tab.intType){
				termMlop.struct = termMlop.getTerm().struct;
				//info
			} 
			else report_error("ERROR! TERM ", termMlop);
		
		}

	    public void visit(ExprTerm exprTerm) {
	    	exprTerm.struct = exprTerm.getTerm().struct;
			
		}
	    public void visit(ExprMin exprMin) {
			if (exprMin.getTerm().struct.getKind() != Tab.intType.Int)
				report_error("ERROR! TERM (in expr) IS NOT TYPE INT", exprMin);
			else {
				exprMin.struct = exprMin.getTerm().struct;
			}
		}
	    public void visit(ExprAddop exprAddop) {
	    	if (exprAddop.getTerm().struct.getKind() != Tab.intType.Int) {
				report_error("ERROR! TERM(in exprAdd)IS NOT TYPE INT", exprAddop);
			}
	    	else if (exprAddop.getExpr().struct.getKind() != Tab.intType.Int) {
				//da li moze zbog rekurzije
				report_error("ERROR! EXPR(in exprAdd)IS NOT TYPE INT", exprAddop);
			}
	    	else if (!exprAddop.getExpr().struct.compatibleWith(exprAddop.getTerm().struct) ) {
				report_error("ERROR! EXPR NOT COMPATIBLE WITH TERM", exprAddop);
			}
	    	else {
				exprAddop.struct = exprAddop.getTerm().struct;
			}
		}
	    
	    
	    
	    public void visit(SingleStatementRead singleStatementRead) {
				// U designatoru proveri da li postoji u tabeli simbola
				if (singleStatementRead.getDesignator().obj.getKind() != Obj.Var 
						|| singleStatementRead.getDesignator().obj.getKind() != Obj.Elem
						|| singleStatementRead.getDesignator().obj.getKind() != Obj.Fld) {
					report_error("ERROR! READ MUST BE VAR,ELEM OR FIELD", singleStatementRead);
				} 
				else 
				// da li je bolje equals ili kao sto sam pre radila
				// ili izmeni ovde ili na ostalim mestima- sta je bolje????
				if (singleStatementRead.getDesignator().obj.equals(Tab.intType)
						|| singleStatementRead.getDesignator().obj.equals(Tab.charType)
						|| singleStatementRead.getDesignator().obj.equals(boolStruct) ) {
					report_error("ERROR! READ MUST BE INT,CHAR OR BOOL", singleStatementRead);
				} 
				else {
					report_info("READ", singleStatementRead);
				}
		}
	    public void visit(SingleStatementPrintNum ssPrint) {
	    	//jos provera??
	    	if (!(ssPrint.getExpr().struct.equals(Tab.intType) 
					|| ssPrint.getExpr().struct.equals(Tab.charType)
					|| ssPrint.getExpr().struct.equals(boolStruct)))
				report_error("ERROR! PRINT MUST BE INT,CHAR OR BOOL", null);
			else {
//		ovo u drugom visitu da se numConst uzme
//				ssPrint.struct = Tab.intType;
//		    	report_info("CONST NUMBER " + ssPrint.getVal(), ssPrint);
			}
		}
	    public void visit(SingleStatementPrint ssPrint) {
	    	//jos provera??
	    	if (!(ssPrint.getExpr().struct.equals(Tab.intType) 
					|| ssPrint.getExpr().struct.equals(Tab.charType)
					|| ssPrint.getExpr().struct.equals(boolStruct)))
				report_error("ERROR! PRINT MUST BE INT,CHAR OR BOOL", null);
			else {
//		mozda info
			}
		}
	    
	    
	    
	    public void visit(DesignatorAssignop designatorAssignop) {
			if (designatorAssignop.getDesignator().obj.getKind() != Obj.Var
					|| designatorAssignop.getDesignator().obj.getKind() != Obj.Elem
					|| designatorAssignop.getDesignator().obj.getKind() != Obj.Fld) {
				report_error("ERROR! DESIGN MUST BE VAR,ELEM OR FIELD ", designatorAssignop);
			}
			else if (!designatorAssignop.getExpr().struct.compatibleWith(designatorAssignop.getDesignator().obj.getType())) {
				report_error("ERROR! EXPR NOT COMPATIBLE WITH DESIGN",designatorAssignop);
			}
			else {
				report_info("ASSIGNOP OK", designatorAssignop);
			}
		}
	    
	    public void visit(DesignatorPlus designatorPlus) {
			if (designatorPlus.getDesignator().obj.getKind() != Obj.Var
					|| designatorPlus.getDesignator().obj.getKind() != Obj.Elem
					|| designatorPlus.getDesignator().obj.getKind() != Obj.Fld)
				report_error("ERROR! DESIGN MUST BE VAR,ELEM OR FIELD ",
						designatorPlus);
			else if (designatorPlus.getDesignator().obj.getType().getKind() != Struct.Int)
				report_error("ERROR! DESIGN MUST BE INT", designatorPlus);
			else {
				report_info("DESIGNPlusPlus OK", designatorPlus);
			}
		}
	    public void visit(DesignatorMinus designatorMinus) {
			if (designatorMinus.getDesignator().obj.getKind() != Obj.Var
					|| designatorMinus.getDesignator().obj.getKind() != Obj.Elem
					|| designatorMinus.getDesignator().obj.getKind() != Obj.Fld)
				report_error("ERROR! DESIGN MUST BE VAR,ELEM OR FIELD ",
						designatorMinus);
			else if (designatorMinus.getDesignator().obj.getType().getKind() != Struct.Int)
				report_error("ERROR! DESIGN MUST BE INT", designatorMinus);
			else {
				report_info("DESIGNPlusPlus OK", designatorMinus);
			}
		}
	    public void visit(SingleStatemenGoto singleStatemenGoto) {
	    	//UNUTAR TEKUCE  FJKE
	    	Obj gotoNode = Tab.find(singleStatemenGoto.getLabel().getLab());
	    	if (gotoNode == Tab.noObj) {
				report_error("ERROR! LABEL " +singleStatemenGoto.getLabel().getLab() + " DOES NOT EXIST IN SYMBOL TABLE! ", singleStatemenGoto);
			} 
		}
}