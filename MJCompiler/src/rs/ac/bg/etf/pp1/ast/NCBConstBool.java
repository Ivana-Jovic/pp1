// generated with ast extension for cup
// version 0.8
// 13/1/2022 20:32:13


package rs.ac.bg.etf.pp1.ast;

public class NCBConstBool extends NCBConst {

    private String boolVal;

    public NCBConstBool (String boolVal) {
        this.boolVal=boolVal;
    }

    public String getBoolVal() {
        return boolVal;
    }

    public void setBoolVal(String boolVal) {
        this.boolVal=boolVal;
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
        buffer.append("NCBConstBool(\n");

        buffer.append(" "+tab+boolVal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NCBConstBool]");
        return buffer.toString();
    }
}
