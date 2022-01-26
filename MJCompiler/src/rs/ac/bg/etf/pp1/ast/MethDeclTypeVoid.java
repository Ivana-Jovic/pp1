// generated with ast extension for cup
// version 0.8
// 25/0/2022 13:3:52


package rs.ac.bg.etf.pp1.ast;

public class MethDeclTypeVoid extends MethDeclType {

    public MethDeclTypeVoid () {
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
        buffer.append("MethDeclTypeVoid(\n");

        buffer.append(tab);
        buffer.append(") [MethDeclTypeVoid]");
        return buffer.toString();
    }
}
