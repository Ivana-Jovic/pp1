// generated with ast extension for cup
// version 0.8
// 16/1/2022 21:2:36


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclType extends ClassDecl {

    private String I1;
    private Type Type;
    private VarDeclListList VarDeclListList;
    private ConstrMeth ConstrMeth;

    public ClassDeclType (String I1, Type Type, VarDeclListList VarDeclListList, ConstrMeth ConstrMeth) {
        this.I1=I1;
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
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

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
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
        if(Type!=null) Type.accept(visitor);
        if(VarDeclListList!=null) VarDeclListList.accept(visitor);
        if(ConstrMeth!=null) ConstrMeth.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseTopDown(visitor);
        if(ConstrMeth!=null) ConstrMeth.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseBottomUp(visitor);
        if(ConstrMeth!=null) ConstrMeth.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclType(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
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
        buffer.append(") [ClassDeclType]");
        return buffer.toString();
    }
}
