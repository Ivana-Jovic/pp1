// generated with ast extension for cup
// version 0.8
// 25/0/2022 13:3:52


package rs.ac.bg.etf.pp1.ast;

public class ConstTypeNCB extends ConstType {

    private String ncbIdent;
    private NCBConst NCBConst;

    public ConstTypeNCB (String ncbIdent, NCBConst NCBConst) {
        this.ncbIdent=ncbIdent;
        this.NCBConst=NCBConst;
        if(NCBConst!=null) NCBConst.setParent(this);
    }

    public String getNcbIdent() {
        return ncbIdent;
    }

    public void setNcbIdent(String ncbIdent) {
        this.ncbIdent=ncbIdent;
    }

    public NCBConst getNCBConst() {
        return NCBConst;
    }

    public void setNCBConst(NCBConst NCBConst) {
        this.NCBConst=NCBConst;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(NCBConst!=null) NCBConst.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(NCBConst!=null) NCBConst.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(NCBConst!=null) NCBConst.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstTypeNCB(\n");

        buffer.append(" "+tab+ncbIdent);
        buffer.append("\n");

        if(NCBConst!=null)
            buffer.append(NCBConst.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstTypeNCB]");
        return buffer.toString();
    }
}
