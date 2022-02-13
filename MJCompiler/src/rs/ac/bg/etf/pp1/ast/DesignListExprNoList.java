// generated with ast extension for cup
// version 0.8
// 13/1/2022 20:32:13


package rs.ac.bg.etf.pp1.ast;

public class DesignListExprNoList extends DesignList {

    private DesignatorIdentRecArr DesignatorIdentRecArr;
    private Expr Expr;

    public DesignListExprNoList (DesignatorIdentRecArr DesignatorIdentRecArr, Expr Expr) {
        this.DesignatorIdentRecArr=DesignatorIdentRecArr;
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorIdentRecArr getDesignatorIdentRecArr() {
        return DesignatorIdentRecArr;
    }

    public void setDesignatorIdentRecArr(DesignatorIdentRecArr DesignatorIdentRecArr) {
        this.DesignatorIdentRecArr=DesignatorIdentRecArr;
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
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignListExprNoList(\n");

        if(DesignatorIdentRecArr!=null)
            buffer.append(DesignatorIdentRecArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
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
