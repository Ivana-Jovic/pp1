// generated with ast extension for cup
// version 0.8
// 6/1/2022 23:53:27


package rs.ac.bg.etf.pp1.ast;

public class DesignatorIdentArr implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String desAName;

    public DesignatorIdentArr (String desAName) {
        this.desAName=desAName;
    }

    public String getDesAName() {
        return desAName;
    }

    public void setDesAName(String desAName) {
        this.desAName=desAName;
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
        buffer.append("DesignatorIdentArr(\n");

        buffer.append(" "+tab+desAName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorIdentArr]");
        return buffer.toString();
    }
}
