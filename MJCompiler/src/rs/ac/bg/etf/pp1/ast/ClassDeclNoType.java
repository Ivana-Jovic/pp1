// generated with ast extension for cup
// version 0.8
// 12/0/2022 13:20:40


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclNoType extends ClassDecl {

    private String I1;
    private VarDeclListList VarDeclListList;
    private ConstrMeth ConstrMeth;

    public ClassDeclNoType (String I1, VarDeclListList VarDeclListList, ConstrMeth ConstrMeth) {
        this.I1=I1;
        this.VarDeclListList=VarDeclListList;
        if(VarDeclListList!=null) VarDeclListList.setParent(this);
        this.ConstrMeth=ConstrMeth;
        if(ConstrMeth!=null) ConstrMeth.setParent(this);
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

    public ConstrMeth getConstrMeth() {
        return ConstrMeth;
    }

    public void setConstrMeth(ConstrMeth ConstrMeth) {
        this.ConstrMeth=ConstrMeth;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclListList!=null) VarDeclListList.accept(visitor);
        if(ConstrMeth!=null) ConstrMeth.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseTopDown(visitor);
        if(ConstrMeth!=null) ConstrMeth.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclListList!=null) VarDeclListList.traverseBottomUp(visitor);
        if(ConstrMeth!=null) ConstrMeth.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclNoType(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(VarDeclListList!=null)
            buffer.append(VarDeclListList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstrMeth!=null)
            buffer.append(ConstrMeth.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclNoType]");
        return buffer.toString();
    }
}
