// generated with ast extension for cup
// version 0.8
// 11/1/2022 16:57:17


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementIfElse extends SingleStatement {

    private ConditionDone ConditionDone;
    private Statement Statement;
    private ElseWrapper ElseWrapper;
    private Statement Statement1;

    public SingleStatementIfElse (ConditionDone ConditionDone, Statement Statement, ElseWrapper ElseWrapper, Statement Statement1) {
        this.ConditionDone=ConditionDone;
        if(ConditionDone!=null) ConditionDone.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ElseWrapper=ElseWrapper;
        if(ElseWrapper!=null) ElseWrapper.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
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

    public ElseWrapper getElseWrapper() {
        return ElseWrapper;
    }

    public void setElseWrapper(ElseWrapper ElseWrapper) {
        this.ElseWrapper=ElseWrapper;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionDone!=null) ConditionDone.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ElseWrapper!=null) ElseWrapper.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionDone!=null) ConditionDone.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ElseWrapper!=null) ElseWrapper.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionDone!=null) ConditionDone.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ElseWrapper!=null) ElseWrapper.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementIfElse(\n");

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

        if(ElseWrapper!=null)
            buffer.append(ElseWrapper.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementIfElse]");
        return buffer.toString();
    }
}
