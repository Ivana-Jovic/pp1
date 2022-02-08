// generated with ast extension for cup
// version 0.8
// 8/1/2022 0:34:21


package rs.ac.bg.etf.pp1.ast;

public class FormParsNoSquare extends FormPars {

    private Type Type;
    private String varN;

    public FormParsNoSquare (Type Type, String varN) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.varN=varN;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getVarN() {
        return varN;
    }

    public void setVarN(String varN) {
        this.varN=varN;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsNoSquare(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varN);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsNoSquare]");
        return buffer.toString();
    }
}
