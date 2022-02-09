package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	
	private int mainPC;  
	
	public int getMainPc() {
		return this.mainPC;
	}
	
	
	
//	
	// *************************** METH ***************************
	
	@Override
	public void visit(MethodTypeNameType methodName){
		methodName.obj.setAdr(Code.pc);//ovo mora prvo
		Code.put(Code.enter);
	}
	
	@Override
	public void visit(MethodTypeNameVoid methodName){
		methodName.obj.setAdr(Code.pc);//ovo mora prvo
		if(methodName.getMethodName().equalsIgnoreCase("main")) {
			mainPC=Code.pc;
		}
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());//b1 form param - level polje
		Code.put(methodName.obj.getLocalSymbols().size());//b2 form param plus lok
	}
	@Override
	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	// *************************** SIN STMT ***************************
	 @Override
    public void visit(SingleStatementPrintNum ssPrint) {
		 Code.loadConst(ssPrint.getN2());//width??
			if(ssPrint.getExpr().struct.equals(Tab.charType)) {
				Code.put(Code.bprint);
			}
			else {
				Code.put(Code.print);
			}
	}
	 @Override
    public void visit(SingleStatementPrint ssPrint) {
			Code.loadConst(0);//width??
			if(ssPrint.getExpr().struct.equals(Tab.charType)) {
				Code.put(Code.bprint);
			}
			else {
				Code.put(Code.print);
			}
	}
	 
	 @Override
	    public void visit(SingleStatementRet singleStatementRet) {
				Code.put(Code.exit);
		    	Code.put(Code.return_);
		}
	 @Override
	    public void visit(SingleStatementRetExpr singleStatementRetExpr) {//expr je vec na steku
//				Code.put(Code.exit);
//		    	Code.put(Code.return_);
//		    	Code.put(singleStatementRetExpr.getExpr().struct)
		}
	 
	 @Override
	    public void visit(SingleStatementRead singleStatementRead) {
		 if(singleStatementRead.getDesignator().obj.getType().equals(Tab.charType)) {
		 	Code.put(Code.bread);
		 }
		 else {
			 Code.put(Code.read);
		 }
		 Code.store(singleStatementRead.getDesignator().obj);
		}
	// *************************** FACTOR ***************************
	 @Override
	    public void visit(FactorNum factor) {
			Code.loadConst(factor.getVal());// stavljanje const na stek
		}
	 @Override
	    public void visit(FactorChar factor) {
			Code.loadConst(factor.getVal());// stavljanje const na stek
		}
	 @Override
	    public void visit(FactorBool factor) {
			Code.loadConst((factor.getVal().equals("true"))?1:0);// stavljanje const na stek
		}
	 @Override
		public void visit(FactorDes factor) {
		 Code.load(factor.getDesignator().obj);// stavljanje Varova na stek
	 }
	 
	 @Override
		public void visit(Factor factor) {//negiramo vrednost sa steka
		 if(factor.getUnary() instanceof UnaryMinus) {
			 Code.put(Code.neg);
		 } else {}
	 }
	 
	 
//	 FactorType
	 @Override
		public void visit(FactorTypeExpr factor) {//pre ovoga je stavljena duzina niza
		 Code.put(Code.newarray);//ovo ostavlja stavlja adresu niza nakon svega (i param) koja je 2??
		 if(factor.getType().struct.equals(Tab.charType)) {//stavljamo parametar od newarr
			 Code.put(0);// operand instrukcije ubacujemo;u compile  time 1B ubaci u code mem
			 //; (loadconst(0))->const_0(compile)_> 0 na expr stack (izvrsavamo u runtime)
		 }
		 else {
			 Code.put(1);
		 }
	 }
	// *************************** EXPR ***************************
	 @Override
		public void visit(ExprAddop exprAddop) {// stavljanje operacija na stek
		 if(exprAddop.getAddop() instanceof AddopPlus) {
			 Code.put(Code.add);
		 }
		 else  if(exprAddop.getAddop() instanceof AddopMinus) {
			 Code.put(Code.sub);
		 } else {}
		}
	 
	// *************************** TERM ***************************
	 @Override
		public void visit(TermMlop termMlop) {// stavljanje operacija na stek
		 if(termMlop.getMulop() instanceof MulopMul) {
			 Code.put(Code.mul);
		 }
		 else  if(termMlop.getMulop() instanceof MulopDiv) {
			 Code.put(Code.div);
		 }
		 else  if(termMlop.getMulop() instanceof MulopMod) {
			 Code.put(Code.rem);
		 } else {}
		}
	 
	 
	 
	// *************************** DESIGN ***************************
	 @Override
		public void visit(DesignatorIdentArr designatorIdentArr) {
		 Code.load(designatorIdentArr.obj);// stavljanje adr niza na stek (uvek prva stvar za nizove)
	 }
		 
	 @Override
		public void visit(DesignatorAssignop designatorAssignop) {
		 Code.store(designatorAssignop.getDesignator().obj);// Expr je na steku i izracunat; samo treba ubaciti u Design
	 }
		 
	 @Override
		public void visit(DesignatorPlus designator) {
		 if(designator.getDesignator().obj.getKind()== Obj.Elem) {
			 Code.put(Code.dup2);//ako je niz  load ce pokupiti adr i index, a onda store nece imati...
		 }
		 Code.load(designator.getDesignator().obj);// inc design
		 Code.loadConst(1);
		 Code.put(Code.add);
		 Code.store(designator.getDesignator().obj);
	 }
	 @Override
		public void visit(DesignatorMinus designator) {
		 if(designator.getDesignator().obj.getKind()== Obj.Elem) {
			 Code.put(Code.dup2);//ako je niz  load ce pokupiti adr i index, a onda store nece imati...
		 }
		 Code.load(designator.getDesignator().obj);// inc design
		 Code.loadConst(1);
		 Code.put(Code.sub);
		 Code.store(designator.getDesignator().obj);
	 }
}