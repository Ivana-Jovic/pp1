// generated with ast extension for cup
// version 0.8
// 17/1/2022 0:13:55


package rs.ac.bg.etf.pp1.ast;

public class DesignatorExprNoList extends Designator {

    private DesignatorIdentArr DesignatorIdentArr;
    private Expr Expr;

    public DesignatorExprNoList (DesignatorIdentArr DesignatorIdentArr, Expr Expr) {
        this.DesignatorIdentArr=DesignatorIdentArr;
        if(DesignatorIdentArr!=null) DesignatorIdentArr.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public DesignatorIdentArr getDesignatorIdentArr() {
        return DesignatorIdentArr;
    }

    public void setDesignatorIdentArr(DesignatorIdentArr DesignatorIdentArr) {
        this.DesignatorIdentArr=DesignatorIdentArr;
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
        if(DesignatorIdentArr!=null) DesignatorIdentArr.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorIdentArr!=null) DesignatorIdentArr.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorIdentArr!=null) DesignatorIdentArr.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorExprNoList(\n");

        if(DesignatorIdentArr!=null)
            buffer.append(DesignatorIdentArr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorExprNoList]");
        return buffer.toString();
    }
}
