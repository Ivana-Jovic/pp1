// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class FactorDesParenAct extends Factorfactor {

    private Designator Designator;
    private ActParsWrap ActParsWrap;

    public FactorDesParenAct (Designator Designator, ActParsWrap ActParsWrap) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActParsWrap=ActParsWrap;
        if(ActParsWrap!=null) ActParsWrap.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActParsWrap getActParsWrap() {
        return ActParsWrap;
    }

    public void setActParsWrap(ActParsWrap ActParsWrap) {
        this.ActParsWrap=ActParsWrap;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ActParsWrap!=null) ActParsWrap.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActParsWrap!=null) ActParsWrap.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActParsWrap!=null) ActParsWrap.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FactorDesParenAct(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsWrap!=null)
            buffer.append(ActParsWrap.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FactorDesParenAct]");
        return buffer.toString();
    }
}
