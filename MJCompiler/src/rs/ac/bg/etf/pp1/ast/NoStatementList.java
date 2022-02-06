// generated with ast extension for cup
// version 0.8
// 6/1/2022 23:53:27


package rs.ac.bg.etf.pp1.ast;

public class NoStatementList extends StatementList {

    private Epsilon Epsilon;

    public NoStatementList (Epsilon Epsilon) {
        this.Epsilon=Epsilon;
        if(Epsilon!=null) Epsilon.setParent(this);
    }

    public Epsilon getEpsilon() {
        return Epsilon;
    }

    public void setEpsilon(Epsilon Epsilon) {
        this.Epsilon=Epsilon;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Epsilon!=null) Epsilon.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Epsilon!=null) Epsilon.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Epsilon!=null) Epsilon.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoStatementList(\n");

        if(Epsilon!=null)
            buffer.append(Epsilon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoStatementList]");
        return buffer.toString();
    }
}
