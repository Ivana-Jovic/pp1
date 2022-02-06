// generated with ast extension for cup
// version 0.8
// 6/1/2022 23:53:27


package rs.ac.bg.etf.pp1.ast;

public class DesignListExprNoList extends DesignList {

    private String desRAName;
    private Expr Expr;

    public DesignListExprNoList (String desRAName, Expr Expr) {
        this.desRAName=desRAName;
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public String getDesRAName() {
        return desRAName;
    }

    public void setDesRAName(String desRAName) {
        this.desRAName=desRAName;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignListExprNoList(\n");

        buffer.append(" "+tab+desRAName);
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignListExprNoList]");
        return buffer.toString();
    }
}