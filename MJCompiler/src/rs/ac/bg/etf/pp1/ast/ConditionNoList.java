// generated with ast extension for cup
// version 0.8
// 13/1/2022 20:32:13


package rs.ac.bg.etf.pp1.ast;

public class ConditionNoList extends Condition {

    private CondTermDone CondTermDone;

    public ConditionNoList (CondTermDone CondTermDone) {
        this.CondTermDone=CondTermDone;
        if(CondTermDone!=null) CondTermDone.setParent(this);
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
        if(CondTermDone!=null) CondTermDone.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTermDone!=null) CondTermDone.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTermDone!=null) CondTermDone.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionNoList(\n");

        if(CondTermDone!=null)
            buffer.append(CondTermDone.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionNoList]");
        return buffer.toString();
    }
}
