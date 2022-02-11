// generated with ast extension for cup
// version 0.8
// 11/1/2022 16:57:17


package rs.ac.bg.etf.pp1.ast;

public class YMethodDeclListList extends MethodDeclListList {

    private MethodDeclListList MethodDeclListList;
    private MethodDecl MethodDecl;

    public YMethodDeclListList (MethodDeclListList MethodDeclListList, MethodDecl MethodDecl) {
        this.MethodDeclListList=MethodDeclListList;
        if(MethodDeclListList!=null) MethodDeclListList.setParent(this);
        this.MethodDecl=MethodDecl;
        if(MethodDecl!=null) MethodDecl.setParent(this);
    }

    public MethodDeclListList getMethodDeclListList() {
        return MethodDeclListList;
    }

    public void setMethodDeclListList(MethodDeclListList MethodDeclListList) {
        this.MethodDeclListList=MethodDeclListList;
    }

    public MethodDecl getMethodDecl() {
        return MethodDecl;
    }

    public void setMethodDecl(MethodDecl MethodDecl) {
        this.MethodDecl=MethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclListList!=null) MethodDeclListList.accept(visitor);
        if(MethodDecl!=null) MethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclListList!=null) MethodDeclListList.traverseTopDown(visitor);
        if(MethodDecl!=null) MethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclListList!=null) MethodDeclListList.traverseBottomUp(visitor);
        if(MethodDecl!=null) MethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("YMethodDeclListList(\n");

        if(MethodDeclListList!=null)
            buffer.append(MethodDeclListList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDecl!=null)
            buffer.append(MethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [YMethodDeclListList]");
        return buffer.toString();
    }
}
