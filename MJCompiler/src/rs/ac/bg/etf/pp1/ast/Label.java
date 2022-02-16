// generated with ast extension for cup
// version 0.8
// 16/1/2022 21:2:36


package rs.ac.bg.etf.pp1.ast;

public class Label implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String lab;

    public Label (String lab) {
        this.lab=lab;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab=lab;
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
        buffer.append("Label(\n");

        buffer.append(" "+tab+lab);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Label]");
        return buffer.toString();
    }
}
