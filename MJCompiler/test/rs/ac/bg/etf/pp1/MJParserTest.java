package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		
		Reader br = null;
		try {
			File sourceCode = new File("test/program.mj");
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        Program prog = (Program)(s.value); 
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");
////snimciDr
			Tab.init();
			SemanticPass sp= new SemanticPass();
			prog.traverseBottomUp(sp);
			//sem an
			
			// ispis tab simb  
			log.info("===================================");
			Tab.dump();
			
			if(!p.errorDetected && sp.passed()) {
				log.info("Parsiranje uspesno zavrseno");
			}
			else {
				log.info("Parsiranje NIJE uspesno zavrseno");
				log.info("GRESAKA U PARS:" + p.errorDetected);
				log.info("GRESAKA U SEM:" + !sp.passed());
			}
			////
			// ispis prepoznatih programskih konstrukcija
			RuleVisitor v = new RuleVisitor();
			prog.traverseBottomUp(v); 
//	      
//			log.info(" Print count calls = " + v.printCallCount);
//
//			log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	
}
