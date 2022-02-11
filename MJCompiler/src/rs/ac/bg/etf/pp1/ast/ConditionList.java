// generated with ast extension for cup
// version 0.8
// 11/1/2022 16:57:17


package rs.ac.bg.etf.pp1.ast;

public class ConditionList extends Condition {

    private Condition Condition;
    private CondTermDone CondTermDone;

    public ConditionList (Condition Condition, CondTermDone CondTermDone) {
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.CondTermDone=CondTermDone;
        if(CondTermDone!=null) CondTermDone.setParent(this);
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public CondTermDone getCondTermDone() {
        return CondTermDone;
    }

    public void setCondTermDone(CondTermDone CondTermDone) {
        this.CondTermDone=CondTermDone;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Condition!=null) Condition.accept(visitor);
        if(CondTermDone!=null) CondTermDone.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(CondTermDone!=null) CondTermDone.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(CondTermDone!=null) CondTermDone.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionList(\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTermDone!=null)
            buffer.append(CondTermDone.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionList]");
        return buffer.toString();
    }
}
