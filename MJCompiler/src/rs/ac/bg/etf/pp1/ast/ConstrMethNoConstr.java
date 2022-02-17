// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class ConstrMethNoConstr extends ConstrMeth {

    private MethodDeclListList MethodDeclListList;

    public ConstrMethNoConstr (MethodDeclListList MethodDeclListList) {
        this.MethodDeclListList=MethodDeclListList;
        if(MethodDeclListList!=null) MethodDeclListList.setParent(this);
    }

    public MethodDeclListList getMethodDeclListList() {
        return MethodDeclListList;
    }

    public void setMethodDeclListList(MethodDeclListList MethodDeclListList) {
        this.MethodDeclListList=MethodDeclListList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclListList!=null) MethodDeclListList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclListList!=null) MethodDeclListList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclListList!=null) MethodDeclListList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstrMethNoConstr(\n");

        if(MethodDeclListList!=null)
            buffer.append(MethodDeclListList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstrMethNoConstr]");
        return buffer.toString();
    }
}
