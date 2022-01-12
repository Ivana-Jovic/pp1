// generated with ast extension for cup
// version 0.8
// 12/0/2022 13:20:40


package rs.ac.bg.etf.pp1.ast;

public class ProgListClassDecl extends ProgList {

    private ProgList ProgList;
    private ClassDecl ClassDecl;

    public ProgListClassDecl (ProgList ProgList, ClassDecl ClassDecl) {
        this.ProgList=ProgList;
        if(ProgList!=null) ProgList.setParent(this);
        this.ClassDecl=ClassDecl;
        if(ClassDecl!=null) ClassDecl.setParent(this);
    }

    public ProgList getProgList() {
        return ProgList;
    }

    public void setProgList(ProgList ProgList) {
        this.ProgList=ProgList;
    }

    public ClassDecl getClassDecl() {
        return ClassDecl;
    }

    public void setClassDecl(ClassDecl ClassDecl) {
        this.ClassDecl=ClassDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgList!=null) ProgList.accept(visitor);
        if(ClassDecl!=null) ClassDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgList!=null) ProgList.traverseTopDown(visitor);
        if(ClassDecl!=null) ClassDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgList!=null) ProgList.traverseBottomUp(visitor);
        if(ClassDecl!=null) ClassDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgListClassDecl(\n");

        if(ProgList!=null)
            buffer.append(ProgList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassDecl!=null)
            buffer.append(ClassDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgListClassDecl]");
        return buffer.toString();
    }
}
