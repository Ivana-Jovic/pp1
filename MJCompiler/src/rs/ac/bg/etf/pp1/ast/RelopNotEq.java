// generated with ast extension for cup
// version 0.8
// 12/0/2022 13:2:9


package rs.ac.bg.etf.pp1.ast;

public class RelopNotEq extends Relop {

    public RelopNotEq () {
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
        buffer.append("RelopNotEq(\n");

        buffer.append(tab);
        buffer.append(") [RelopNotEq]");
        return buffer.toString();
    }
}
