// generated with ast extension for cup
// version 0.8
// 8/1/2022 0:34:21


package rs.ac.bg.etf.pp1.ast;

public class RecordDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private RecordDeclName RecordDeclName;
    private VarDeclListList VarDeclListList;

    public RecordDecl (RecordDeclName RecordDeclName, VarDeclListList VarDeclListList) {
        this.RecordDeclName=RecordDeclName;
        if(RecordDeclName!=null) RecordDeclName.setParent(this);
        this.VarDeclListList=VarDeclListList;
        if(VarDeclListList!=null) VarDeclListList.setParent(this);
    }

    public RecordDeclName getRecordDeclName() {
        return RecordDeclName;
    }

    public void setRecordDeclName(RecordDeclName RecordDeclName) {
        this.RecordDeclName=RecordDeclName;
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
        if(RecordDeclName!=null) RecordDeclName.accept(visitor);
        if(VarDeclListList!=null) VarDeclListList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(RecordDeclName!=null) RecordDeclName.traverseTopDown(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(RecordDeclName!=null) RecordDeclName.traverseBottomUp(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("RecordDecl(\n");

        if(RecordDeclName!=null)
            buffer.append(RecordDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
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
