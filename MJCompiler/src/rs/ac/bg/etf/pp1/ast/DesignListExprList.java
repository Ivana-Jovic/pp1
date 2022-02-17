// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class DesignListExprList extends DesignList {

    private DesignList DesignList;
    private DesignatorIdentRecArr DesignatorIdentRecArr;
    private Expr Expr;

    public DesignListExprList (DesignList DesignList, DesignatorIdentRecArr DesignatorIdentRecArr, Expr Expr) {
        this.DesignList=DesignList;
        if(DesignList!=null) DesignList.setParent(this);
        this.DesignatorIdentRecArr=DesignatorIdentRecArr;
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignList getDesignList() {
        return DesignList;
    }

    public void setDesignList(DesignList DesignList) {
        this.DesignList=DesignList;
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
        if(DesignList!=null) DesignList.accept(visitor);
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignList!=null) DesignList.traverseTopDown(visitor);
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignList!=null) DesignList.traverseBottomUp(visitor);
        if(DesignatorIdentRecArr!=null) DesignatorIdentRecArr.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignListExprList(\n");

        if(DesignList!=null)
            buffer.append(DesignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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
        buffer.append(") [DesignListExprList]");
        return buffer.toString();
    }
}
