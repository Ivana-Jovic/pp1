// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class SingleStatementDo extends SingleStatement {

    private DoStmt DoStmt;
    private Statement Statement;
    private ConditionStart ConditionStart;
    private ConditionDone ConditionDone;

    public SingleStatementDo (DoStmt DoStmt, Statement Statement, ConditionStart ConditionStart, ConditionDone ConditionDone) {
        this.DoStmt=DoStmt;
        if(DoStmt!=null) DoStmt.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.ConditionStart=ConditionStart;
        if(ConditionStart!=null) ConditionStart.setParent(this);
        this.ConditionDone=ConditionDone;
        if(ConditionDone!=null) ConditionDone.setParent(this);
    }

    public DoStmt getDoStmt() {
        return DoStmt;
    }

    public void setDoStmt(DoStmt DoStmt) {
        this.DoStmt=DoStmt;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public ConditionStart getConditionStart() {
        return ConditionStart;
    }

    public void setConditionStart(ConditionStart ConditionStart) {
        this.ConditionStart=ConditionStart;
    }

    public ConditionDone getConditionDone() {
        return ConditionDone;
    }

    public void setConditionDone(ConditionDone ConditionDone) {
        this.ConditionDone=ConditionDone;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DoStmt!=null) DoStmt.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(ConditionStart!=null) ConditionStart.accept(visitor);
        if(ConditionDone!=null) ConditionDone.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DoStmt!=null) DoStmt.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(ConditionStart!=null) ConditionStart.traverseTopDown(visitor);
        if(ConditionDone!=null) ConditionDone.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DoStmt!=null) DoStmt.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(ConditionStart!=null) ConditionStart.traverseBottomUp(visitor);
        if(ConditionDone!=null) ConditionDone.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStatementDo(\n");

        if(DoStmt!=null)
            buffer.append(DoStmt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionStart!=null)
            buffer.append(ConditionStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionDone!=null)
            buffer.append(ConditionDone.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStatementDo]");
        return buffer.toString();
    }
}
