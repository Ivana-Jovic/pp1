// generated with ast extension for cup
// version 0.8
// 11/1/2022 16:57:17


package rs.ac.bg.etf.pp1.ast;

public class ProgListRecordDecl extends ProgList {

    private ProgList ProgList;
    private RecordDecl RecordDecl;

    public ProgListRecordDecl (ProgList ProgList, RecordDecl RecordDecl) {
        this.ProgList=ProgList;
        if(ProgList!=null) ProgList.setParent(this);
        this.RecordDecl=RecordDecl;
        if(RecordDecl!=null) RecordDecl.setParent(this);
    }

    public ProgList getProgList() {
        return ProgList;
    }

    public void setProgList(ProgList ProgList) {
        this.ProgList=ProgList;
    }

    public RecordDecl getRecordDecl() {
        return RecordDecl;
    }

    public void setRecordDecl(RecordDecl RecordDecl) {
        this.RecordDecl=RecordDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgList!=null) ProgList.accept(visitor);
        if(RecordDecl!=null) RecordDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgList!=null) ProgList.traverseTopDown(visitor);
        if(RecordDecl!=null) RecordDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgList!=null) ProgList.traverseBottomUp(visitor);
        if(RecordDecl!=null) RecordDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgListRecordDecl(\n");

        if(ProgList!=null)
            buffer.append(ProgList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RecordDecl!=null)
            buffer.append(RecordDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgListRecordDecl]");
        return buffer.toString();
    }
}
