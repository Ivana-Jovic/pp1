// generated with ast extension for cup
// version 0.8
// 6/1/2022 23:53:27


package rs.ac.bg.etf.pp1.ast;

public class Factor implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Struct struct = null;

    private Unary Unary;
    private Factorfactor Factorfactor;

    public Factor (Unary Unary, Factorfactor Factorfactor) {
        this.Unary=Unary;
        if(Unary!=null) Unary.setParent(this);
        this.Factorfactor=Factorfactor;
        if(Factorfactor!=null) Factorfactor.setParent(this);
    }

    public Unary getUnary() {
        return Unary;
    }

    public void setUnary(Unary Unary) {
        this.Unary=Unary;
    }

    public Factorfactor getFactorfactor() {
        return Factorfactor;
    }

    public void setFactorfactor(Factorfactor Factorfactor) {
        this.Factorfactor=Factorfactor;
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
        if(Unary!=null) Unary.accept(visitor);
        if(Factorfactor!=null) Factorfactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Unary!=null) Unary.traverseTopDown(visitor);
        if(Factorfactor!=null) Factorfactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Unary!=null) Unary.traverseBottomUp(visitor);
        if(Factorfactor!=null) Factorfactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Factor(\n");

        if(Unary!=null)
            buffer.append(Unary.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Factorfactor!=null)
            buffer.append(Factorfactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Factor]");
        return buffer.toString();
    }
}
