// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class RecordDeclName implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String recName;

    public RecordDeclName (String recName) {
        this.recName=recName;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName=recName;
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
        buffer.append("RecordDeclName(\n");

        buffer.append(" "+tab+recName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDeclName]");
        return buffer.toString();
    }
}
