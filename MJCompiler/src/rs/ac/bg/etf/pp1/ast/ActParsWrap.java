// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class ActParsWrap implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ActParsFirst ActParsFirst;
    private ActPars ActPars;

    public ActParsWrap (ActParsFirst ActParsFirst, ActPars ActPars) {
        this.ActParsFirst=ActParsFirst;
        if(ActParsFirst!=null) ActParsFirst.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public ActParsFirst getActParsFirst() {
        return ActParsFirst;
    }

    public void setActParsFirst(ActParsFirst ActParsFirst) {
        this.ActParsFirst=ActParsFirst;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsFirst!=null) ActParsFirst.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsFirst!=null) ActParsFirst.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsFirst!=null) ActParsFirst.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActParsWrap(\n");

        if(ActParsFirst!=null)
            buffer.append(ActParsFirst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActParsWrap]");
        return buffer.toString();
    }
}
