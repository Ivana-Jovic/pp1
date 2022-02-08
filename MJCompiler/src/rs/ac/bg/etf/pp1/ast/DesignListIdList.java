// generated with ast extension for cup
// version 0.8
// 8/1/2022 0:34:21


package rs.ac.bg.etf.pp1.ast;

public class DesignListIdList extends DesignList {

    private DesignList DesignList;
    private String desLiName;

    public DesignListIdList (DesignList DesignList, String desLiName) {
        this.DesignList=DesignList;
        if(DesignList!=null) DesignList.setParent(this);
        this.desLiName=desLiName;
    }

    public DesignList getDesignList() {
        return DesignList;
    }

    public void setDesignList(DesignList DesignList) {
        this.DesignList=DesignList;
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
        if(DesignList!=null) DesignList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignList!=null) DesignList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignList!=null) DesignList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignListIdList(\n");

        if(DesignList!=null)
            buffer.append(DesignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+desLiName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignListIdList]");
        return buffer.toString();
    }
}
