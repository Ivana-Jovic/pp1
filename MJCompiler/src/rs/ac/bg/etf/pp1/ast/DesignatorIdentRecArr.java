// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdentRecArr implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String desRAName;

    public DesignatorIdentRecArr (String desRAName) {
        this.desRAName=desRAName;
    }

    public String getDesRAName() {
        return desRAName;
    }

    public void setDesRAName(String desRAName) {
        this.desRAName=desRAName;
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
        buffer.append("DesignatorIdentRecArr(\n");

        buffer.append(" "+tab+desRAName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdentRecArr]");
        return buffer.toString();
    }
}
