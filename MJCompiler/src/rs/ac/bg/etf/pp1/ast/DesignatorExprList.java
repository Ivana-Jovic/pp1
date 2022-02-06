// generated with ast extension for cup
// version 0.8
// 6/1/2022 23:53:27


package rs.ac.bg.etf.pp1.ast;

public class DesignatorExprList extends Designator {

    private DesignRecElem DesignRecElem;
    private DesignList DesignList;

    public DesignatorExprList (DesignRecElem DesignRecElem, DesignList DesignList) {
        this.DesignRecElem=DesignRecElem;
        if(DesignRecElem!=null) DesignRecElem.setParent(this);
        this.DesignList=DesignList;
        if(DesignList!=null) DesignList.setParent(this);
    }

    public DesignRecElem getDesignRecElem() {
        return DesignRecElem;
    }

    public void setDesignRecElem(DesignRecElem DesignRecElem) {
        this.DesignRecElem=DesignRecElem;
    }

    public DesignList getDesignList() {
        return DesignList;
    }

    public void setDesignList(DesignList DesignList) {
        this.DesignList=DesignList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignRecElem!=null) DesignRecElem.accept(visitor);
        if(DesignList!=null) DesignList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignRecElem!=null) DesignRecElem.traverseTopDown(visitor);
        if(DesignList!=null) DesignList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignRecElem!=null) DesignRecElem.traverseBottomUp(visitor);
        if(DesignList!=null) DesignList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorExprList(\n");

        if(DesignRecElem!=null)
            buffer.append(DesignRecElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignList!=null)
            buffer.append(DesignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorExprList]");
        return buffer.toString();
    }
}
