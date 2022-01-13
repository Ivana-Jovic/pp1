// generated with ast extension for cup
// version 0.8
// 13/0/2022 11:44:18


package rs.ac.bg.etf.pp1.ast;

public class RecordDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private VarDeclListList VarDeclListList;

    public RecordDecl (String I1, VarDeclListList VarDeclListList) {
        this.I1=I1;
        this.VarDeclListList=VarDeclListList;
        if(VarDeclListList!=null) VarDeclListList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public VarDeclListList getVarDeclListList() {
        return VarDeclListList;
    }

    public void setVarDeclListList(VarDeclListList VarDeclListList) {
        this.VarDeclListList=VarDeclListList;
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
        if(VarDeclListList!=null) VarDeclListList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclListList!=null) VarDeclListList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(VarDeclListList!=null)
            buffer.append(VarDeclListList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [RecordDecl]");
        return buffer.toString();
    }
}
