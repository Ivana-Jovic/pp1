// generated with ast extension for cup
// version 0.8
// 6/1/2022 23:53:27


package rs.ac.bg.etf.pp1.ast;

public class DesignatorRec extends Designator {

    private DesignatorIdentRec DesignatorIdentRec;
    private DesignList DesignList;

    public DesignatorRec (DesignatorIdentRec DesignatorIdentRec, DesignList DesignList) {
        this.DesignatorIdentRec=DesignatorIdentRec;
        if(DesignatorIdentRec!=null) DesignatorIdentRec.setParent(this);
        this.DesignList=DesignList;
        if(DesignList!=null) DesignList.setParent(this);
    }

    public DesignatorIdentRec getDesignatorIdentRec() {
        return DesignatorIdentRec;
    }

    public void setDesignatorIdentRec(DesignatorIdentRec DesignatorIdentRec) {
        this.DesignatorIdentRec=DesignatorIdentRec;
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
        if(DesignatorIdentRec!=null) DesignatorIdentRec.accept(visitor);
        if(DesignList!=null) DesignList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorIdentRec!=null) DesignatorIdentRec.traverseTopDown(visitor);
        if(DesignList!=null) DesignList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorIdentRec!=null) DesignatorIdentRec.traverseBottomUp(visitor);
        if(DesignList!=null) DesignList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorRec(\n");

        if(DesignatorIdentRec!=null)
            buffer.append(DesignatorIdentRec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignList!=null)
            buffer.append(DesignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorRec]");
        return buffer.toString();
    }
}
