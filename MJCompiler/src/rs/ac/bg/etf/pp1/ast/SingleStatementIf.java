// generated with ast extension for cup
// version 0.8
// 13/1/2022 20:32:13


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementIf extends SingleStatement {

    private ConditionDone ConditionDone;
    private Statement Statement;

    public SingleStatementIf (ConditionDone ConditionDone, Statement Statement) {
        this.ConditionDone=ConditionDone;
        if(ConditionDone!=null) ConditionDone.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ConditionDone getConditionDone() {
        return ConditionDone;
    }

    public void setConditionDone(ConditionDone ConditionDone) {
        this.ConditionDone=ConditionDone;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionDone!=null) ConditionDone.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionDone!=null) ConditionDone.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionDone!=null) ConditionDone.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementIf(\n");

        if(ConditionDone!=null)
            buffer.append(ConditionDone.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementIf]");
        return buffer.toString();
    }
}
