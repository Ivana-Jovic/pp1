// generated with ast extension for cup
// version 0.8
// 8/1/2022 0:34:21


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdentRec implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String desRName;

    public DesignatorIdentRec (String desRName) {
        this.desRName=desRName;
    }

    public String getDesRName() {
        return desRName;
    }

    public void setDesRName(String desRName) {
        this.desRName=desRName;
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
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorIdentRec(\n");

        buffer.append(" "+tab+desRName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdentRec]");
        return buffer.toString();
    }
}
