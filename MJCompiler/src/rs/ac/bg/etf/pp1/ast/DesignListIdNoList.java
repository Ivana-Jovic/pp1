// generated with ast extension for cup
// version 0.8
// 13/1/2022 20:32:13


package rs.ac.bg.etf.pp1.ast;

public class DesignListIdNoList extends DesignList {

    private String desLiName;

    public DesignListIdNoList (String desLiName) {
        this.desLiName=desLiName;
    }

    public String getDesLiName() {
        return desLiName;
    }

    public void setDesLiName(String desLiName) {
        this.desLiName=desLiName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignListIdNoList(\n");

        buffer.append(" "+tab+desLiName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignListIdNoList]");
        return buffer.toString();
    }
}
