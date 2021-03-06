package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {

public CodeGenerator() {
		
		//len -> duzinu
		Obj obj = Tab.find("len");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.arraylength);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		//chr 
		obj =Tab.find("chr");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		// ord -> vraca ascii
		obj = Tab.find("ord");
		obj.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(obj.getLevel());
		Code.put(obj.getLocalSymbols().size());
		Code.put(Code.load_n);
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	private int mainPC;

	public int getMainPc() {
		return this.mainPC;
	}

	// *************************** METH ***************************

	@Override
	public void visit(MethodTypeNameType methodName) {
		methodName.obj.setAdr(Code.pc);// ovo mora prvo

		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());// b1 form param - level polje
		Code.put(methodName.obj.getLocalSymbols().size());// b2 form param plus lok
	}

	@Override
	public void visit(MethodTypeNameVoid methodName) {
		methodName.obj.setAdr(Code.pc);// ovo mora prvo
		if (methodName.getMethodName().equalsIgnoreCase("main")) {
			mainPC = Code.pc;
		}
		Code.put(Code.enter);
		Code.put(methodName.obj.getLevel());// b1 form param - level polje
		Code.put(methodName.obj.getLocalSymbols().size());// b2 form param plus lok
	}

	@Override
	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// *************************** SIN STMT ***************************
	@Override
	public void visit(SingleStatementPrintNum ssPrint) {
		Code.loadConst(ssPrint.getN2());// width??
		if (ssPrint.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	@Override
	public void visit(SingleStatementPrint ssPrint) {
		Code.loadConst(0);// width??
		if (ssPrint.getExpr().struct.equals(Tab.charType)) {
			Code.put(Code.bprint);
		} else {
			Code.put(Code.print);
		}
	}

	@Override
	public void visit(SingleStatementRet singleStatementRet) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(SingleStatementRetExpr singleStatementRetExpr) {// expr je vec na steku
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	@Override
	public void visit(SingleStatementRead singleStatementRead) {
		if (singleStatementRead.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else {
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
		Code.loadConst((factor.getVal().equals("true")) ? 1 : 0);// stavljanje const na stek
	}

	@Override
	public void visit(FactorDes factor) {
		Code.load(factor.getDesignator().obj);// stavljanje Varova na stek
	}

	@Override
	public void visit(Factor factor) {// negiramo vrednost sa steka
		if (factor.getUnary() instanceof UnaryMinus) {
			Code.put(Code.neg);
		} else {}
	}

	@Override
	public void visit(FactorTypeExpr factor) {// pre ovoga je stavljena duzina niza
		Code.put(Code.newarray);// ovo ostavlja stavlja adresu niza nakon svega (i param) koja je 2??
		if (factor.getType().struct.equals(Tab.charType)) {// stavljamo parametar od newarr
			Code.put(0);// operand instrukcije ubacujemo;u compile time 1B ubaci u code mem
			// ; (loadconst(0))->const_0(compile)_> 0 na expr stack (izvrsavamo u runtime)
		} else {
			Code.put(1);
		}
	}

	@Override
	public void visit(FactorType factor) {// pravljene recorda
		Code.put(Code.new_);
		Code.put2(factor.getType().struct.getNumberOfFields() * 4);// put2 zbog s; 4 jer svaki je po 4B
	}

	@Override
	public void visit(FactorDesPar factor) {
		int off = factor.getDesignator().obj.getAdr() - Code.pc;// mora pre calla!
		Code.put(Code.call);
		Code.put2(off);
	}

	@Override
	public void visit(FactorDesParenAct factor) {
		int off = factor.getDesignator().obj.getAdr() - Code.pc;// mora pre calla!
		Code.put(Code.call);
		Code.put2(off);
	}

	// *************************** EXPR ***************************
	@Override
	public void visit(ExprAddop exprAddop) {// stavljanje operacija na stek
		if (exprAddop.getAddop() instanceof AddopPlus) {
			Code.put(Code.add);
		} 
		else if (exprAddop.getAddop() instanceof AddopMinus) {
			Code.put(Code.sub);
		} 
		else {}
	}

	// *************************** TERM ***************************
	@Override
	public void visit(TermMlop termMlop) {// stavljanje operacija na stek
		if (termMlop.getMulop() instanceof MulopMul) {
			Code.put(Code.mul);
		} 
		else if (termMlop.getMulop() instanceof MulopDiv) {
			Code.put(Code.div);
		} 
		else if (termMlop.getMulop() instanceof MulopMod) {
			Code.put(Code.rem);
		} 
		else {}
	}

	// *************************** DESIGN ***************************
	@Override
	public void visit(DesignatorIdentArr designatorIdentArr) {
		Code.load(designatorIdentArr.obj);// stavljanje adr niza na stek (uvek prva stvar za nizove)
	}

	@Override
	public void visit(DesignatorIdentRec designatorIdentRec) {// Kada se pocinje designator koji je rec, stavljamo na
																// stek!!!!
		Code.load(designatorIdentRec.obj);// stavljanje adr rek na stek
	}

	@Override
	public void visit(DesignatorIdentRecArr designatorIdentRecArr) {// Kada se pocinje designator koji je arr/rec,
																	// stavljamo na stek!!!!
		Code.load(designatorIdentRecArr.obj);// stavljanje adr arr rek na stek
	}

	@Override
	public void visit(DesignatorAssignop designatorAssignop) {
		Code.store(designatorAssignop.getDesignator().obj);// Expr je na steku i izracunat; samo treba ubaciti u Design
	}

	@Override
	public void visit(DesignRecElem designRecElem) {
		Code.load(designRecElem.obj);// u sem smo u ovaj cvor ubacili element niza
	}

	/// DESIGNATOR RECORDS AND ARR
	private void loadDesignNodes(DesignList designator) {
		// posto prvi (preko name) i poslednji(preko designator) se vec stavljaju na
		// stek
		// ovde treba staviti ono u sredini
		if (designator.getParent() instanceof DesignListIdList
				|| designator.getParent() instanceof DesignListExprList) {
			Code.load(designator.obj);
		}
		// pazi ako je design sa leve strane onda se stavi rvi
		// a ovom fjom se stave ostali (ali ne i poslenji)
	}

	@Override
	public void visit(DesignListIdNoList designator) {
		loadDesignNodes(designator);
	}

	@Override
	public void visit(DesignListExprNoList designator) {
		loadDesignNodes(designator);
	}

	@Override
	public void visit(DesignListIdList designator) {
		loadDesignNodes(designator);
	}

	@Override
	public void visit(DesignListExprList designator) {
		loadDesignNodes(designator);
	}


	// *************************** DESIGN STMT***************************
	@Override
	public void visit(DesignatorPlus designator) {
		if (designator.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);// ako je niz load ce pokupiti adr i index, a onda store nece imati...
		} else if (designator.getDesignator().obj.getKind() == Obj.Fld) {
			Code.put(Code.dup);// a.b++ ; get fld ce da pokupi adresu od a koja nam treba za storovanje
		}
		Code.load(designator.getDesignator().obj);// inc design
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(designator.getDesignator().obj);
	}

	@Override
	public void visit(DesignatorMinus designator) {
		if (designator.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);// ako je niz load ce pokupiti adr i index, a onda store nece imati...
		} else if (designator.getDesignator().obj.getKind() == Obj.Fld) {
			Code.put(Code.dup);// a.b++ ; get fld ce da pokupi adresu od a koja nam treba za storovanje
		}
		Code.load(designator.getDesignator().obj);// inc design
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(designator.getDesignator().obj);
	}

	@Override
	public void visit(DesignatorActpars designator) {
		int off = designator.getDesignator().obj.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(off);
		if (designator.getDesignator().obj.getType() != Tab.noType) {// posto je sa stmt; mora pop ako nije void
			Code.put(Code.pop);
		}
	}

	// enter sa steka skida form param;
	// prvi arg entera kaze koliko skidmo sa expr steka i upisuje ih na proc stek
	// drugi koliko da ostavi na proc stek
	@Override
	public void visit(DesignatorParen designator) {
		int off = designator.getDesignator().obj.getAdr() - Code.pc;// mora pre call-a!
		Code.put(Code.call);
		Code.put2(off);
		if (designator.getDesignator().obj.getType() != Tab.noType) {// posto je sa stmt; mora pop ako nije void
			Code.put(Code.pop);
		}
	}

	// -------------------------------------SKOKOVI-------------------------------------
	// izvrsice skok ako uslov NIJE ispunjen
	// pomocna fja prima normalno, pa u telu obrce
	// jmp inace prima off, ali u ugradjenu/pomocna fju prosl adresu skoka, pa ona
	// sama racuna

	// fixup - za skokove unapred, prvo stavimo ofset nula (JMP(1B) 0(2B))
	// pa onda kad dodjemo do npr elsa pozovemo fixup i sa adresom gde je 0 tj jmp+1
	private List<Integer> condfactList = new ArrayList<>();// AND
	private List<Integer> conditionList = new ArrayList<>();// OR
	private List<Integer> thenList = new ArrayList<>();// THEN// preskacu then
	private List<Integer> elseList = new ArrayList<>();// ELSE

	private int relopToCode(Relop r) {
		if (r instanceof RelopEqual) {return Code.eq;} 
		else if (r instanceof RelopNotEq) {return Code.ne;}
		else if (r instanceof RelopGrt) {return Code.gt;} 
		else if (r instanceof RelopGrte) {return Code.ge;}
		else if (r instanceof RelopLess) {return Code.lt;}
		else if (r instanceof RelopLesse) {return Code.le;}
		return 0;
	}

	@Override
	public void visit(CondFactNoList cond) {
		Code.loadConst(1);// unesemo sami radji poredjenja
		Code.putFalseJump(Code.eq, 0);// F// jne tj imamo false i 1(true)-> false -> skacemo
		condfactList.add(Code.pc - 2);
		// T//ako je tacno nastavljamo
	}

	@Override
	public void visit(CondFactList cond) {
		Code.putFalseJump(relopToCode(cond.getRelop()), 0);// F// if(a==b)-> skok se desava ako je jne->zato
															// prosledjujemo suprotno
		condfactList.add(Code.pc - 2);
		// T//ako je tacno nastavljamo
	}

	@Override
	public void visit(CondTermDone cond) {// T// do ovog cvora su dosli samo ako su svi cvorovi sa andnovima tacni
		Code.putJump(0);// posto ulazimo u cvor za samo T, onda imamo bezuslovni skok na THEN
		conditionList.add(Code.pc - 2);// za svaki or je skok
		// F
		while (!condfactList.isEmpty()) {
			Code.fixup(condfactList.remove(condfactList.size() - 1));// poslednji element uzima
		} // F nastavljaju dalje
			// i mora ovaj redosled!!!
	}

	@Override
	public void visit(ConditionDone cond) {// F
		Code.putJump(0);// posto ulazimo u cvor za F, onda imamo bezuslovni skok na ELSE
		thenList.add(Code.pc - 2);
		// vratimo tacne
		// THEN GRANA
		while (!conditionList.isEmpty()) {
			Code.fixup(conditionList.remove(conditionList.size() - 1));// poslednji element uzima
		} // T nastavljaju dalje
			// i mora ovaj redosled?
		
		
		/////
	}

	// BEZ ELSA
	@Override
	public void visit(SingleStatementIf cond) {// T// do ovog cvora su dosli samo ako su svi cvorovi tacni
		// T
		//////////////OBRISANO POSLEDNJE
		//while (!thenList.isEmpty()) { // F// posto nema elsa treba da vratimo F
			Code.fixup(thenList.remove(thenList.size() - 1));// poslednji element uzima
		//}
		// i mora ovaj redosled!!!
		// T i F
	}

	// T izbacimo van ifelse, F vratimo
	@Override
	public void visit(ElseWrapper cond) {// T
		Code.putJump(0);// posto ulazimo u cvor za T, onda imamo bezuslovni skok na posle IFELSE
		elseList.add(Code.pc - 2);
		// vratimo F
//		 while(!thenList.isEmpty()) {//NIKAKO WHILE VEC SAMO POSLEDNJI
//		 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Code.fixup(thenList.remove(thenList.size() - 1));// poslednji element uzima
//		 }//T nastavljaju dalje
		// i mora ovaj redosled
	}

	@Override
	public void visit(SingleStatementIfElse cond) {// F
		// vracamo T
//		 while(!elseList.isEmpty()) {//NIKAKO WHILE VEC SAMO POSLEDNJI
//		 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Code.fixup(elseList.remove(elseList.size() - 1));// poslednji element uzima
//		 }
		// T i F nastavljaju dalje
		// i mora ovaj redosled
	}

	// vracamo nesto tako sto fixujemo

	// CONDITION tacneniti pusta, a netacne drzi u thenlisti..

	////////// DO WHILE
	// uslov tacan -> skok
	private List<Integer> doWhileList = new ArrayList<>();
// boolean breakDetected= false;
 private List<Integer> breakDetected = new ArrayList<>();
 private List<Integer> breakPc = new ArrayList<>();
 ////////////////////////
 private List<Integer> continueLevel = new ArrayList<>();
 private List<Integer> continuePc = new ArrayList<>();
int doWhileLevel=0;
 
	@Override
	public void visit(DoStmt cond) {
		doWhileList.add(Code.pc);
	
		doWhileLevel+=1;
		
	}

	@Override
	public void visit(SingleStatementDo cond) {
		///
		Code.putJump(doWhileList.remove(doWhileList.size() - 1));
		/////
		while(breakDetected.size()>0 && doWhileLevel==breakDetected.get(breakDetected.size() - 1)) {
			breakDetected.remove(breakDetected.size() - 1);
			Code.fixup(breakPc.remove(breakPc.size() - 1));
		}
		Code.fixup(thenList.remove(thenList.size() - 1));
		
		doWhileLevel-=1;
	}
	@Override
	public void visit(SingleStatementBreak cond) {		
		Code.putJump(0);
		breakPc.add(Code.pc-2);
		breakDetected.add(doWhileLevel);
	}
	
	//CONTINUE
	
	@Override
	public void visit(ConditionStart cond) {
		
		while(continueLevel.size()>0 && doWhileLevel==continueLevel.get(continueLevel.size() - 1)) {
					continueLevel.remove(continueLevel.size() - 1);
					Code.fixup(continuePc.remove(continuePc.size() - 1));
		}
	}
	@Override
	public void visit(SingleStatementContinue cond) {
		Code.putJump(0);
		continuePc.add(Code.pc-2);
		continueLevel.add(doWhileLevel);
	}
}
