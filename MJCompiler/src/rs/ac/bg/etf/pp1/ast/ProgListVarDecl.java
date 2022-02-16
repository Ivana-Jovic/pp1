// generated with ast extension for cup
// version 0.8
// 16/1/2022 21:2:36


package rs.ac.bg.etf.pp1.ast;

public class ProgListVarDecl extends ProgList {

    private ProgList ProgList;
    private VarDecl VarDecl;

    public ProgListVarDecl (ProgList ProgList, VarDecl VarDecl) {
        this.ProgList=ProgList;
        if(ProgList!=null) ProgList.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public ProgList getProgList() {
        return ProgList;
    }

    public void setProgList(ProgList ProgList) {
        this.ProgList=ProgList;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgList!=null) ProgList.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgList!=null) ProgList.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgList!=null) ProgList.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgListVarDecl(\n");

        if(ProgList!=null)
            buffer.append(ProgList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgListVarDecl]");
        return buffer.toString();
    }
}
