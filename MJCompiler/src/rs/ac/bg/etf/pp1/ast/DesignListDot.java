// generated with ast extension for cup
// version 0.8
// 12/0/2022 13:2:9


package rs.ac.bg.etf.pp1.ast;

public class DesignListDot extends DesignList {

    private DesignList DesignList;
    private String I2;

    public DesignListDot (DesignList DesignList, String I2) {
        this.DesignList=DesignList;
        if(DesignList!=null) DesignList.setParent(this);
        this.I2=I2;
    }

    public DesignList getDesignList() {
        return DesignList;
    }

    public void setDesignList(DesignList DesignList) {
        this.DesignList=DesignList;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
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
        buffer.append("DesignListDot(\n");

        if(DesignList!=null)
            buffer.append(DesignList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignListDot]");
        return buffer.toString();
    }
}
