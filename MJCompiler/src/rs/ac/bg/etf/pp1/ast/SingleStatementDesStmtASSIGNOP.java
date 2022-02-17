// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementDesStmtASSIGNOP extends SingleStatement {

    private DesStmtASSIGNOP DesStmtASSIGNOP;

    public SingleStatementDesStmtASSIGNOP (DesStmtASSIGNOP DesStmtASSIGNOP) {
        this.DesStmtASSIGNOP=DesStmtASSIGNOP;
        if(DesStmtASSIGNOP!=null) DesStmtASSIGNOP.setParent(this);
    }

    public DesStmtASSIGNOP getDesStmtASSIGNOP() {
        return DesStmtASSIGNOP;
    }

    public void setDesStmtASSIGNOP(DesStmtASSIGNOP DesStmtASSIGNOP) {
        this.DesStmtASSIGNOP=DesStmtASSIGNOP;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesStmtASSIGNOP!=null) DesStmtASSIGNOP.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesStmtASSIGNOP!=null) DesStmtASSIGNOP.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesStmtASSIGNOP!=null) DesStmtASSIGNOP.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementDesStmtASSIGNOP(\n");

        if(DesStmtASSIGNOP!=null)
            buffer.append(DesStmtASSIGNOP.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementDesStmtASSIGNOP]");
        return buffer.toString();
    }
}
