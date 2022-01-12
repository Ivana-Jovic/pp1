// generated with ast extension for cup
// version 0.8
// 12/0/2022 13:20:40


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private MethDeclType MethDeclType;
    private String I2;
    private FormParsList FormParsList;
    private VarDeclListList VarDeclListList;
    private StatementList StatementList;

    public MethodDecl (MethDeclType MethDeclType, String I2, FormParsList FormParsList, VarDeclListList VarDeclListList, StatementList StatementList) {
        this.MethDeclType=MethDeclType;
        if(MethDeclType!=null) MethDeclType.setParent(this);
        this.I2=I2;
        this.FormParsList=FormParsList;
        if(FormParsList!=null) FormParsList.setParent(this);
        this.VarDeclListList=VarDeclListList;
        if(VarDeclListList!=null) VarDeclListList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MethDeclType getMethDeclType() {
        return MethDeclType;
    }

    public void setMethDeclType(MethDeclType MethDeclType) {
        this.MethDeclType=MethDeclType;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public FormParsList getFormParsList() {
        return FormParsList;
    }

    public void setFormParsList(FormParsList FormParsList) {
        this.FormParsList=FormParsList;
    }

    public VarDeclListList getVarDeclListList() {
        return VarDeclListList;
    }

    public void setVarDeclListList(VarDeclListList VarDeclListList) {
        this.VarDeclListList=VarDeclListList;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethDeclType!=null) MethDeclType.accept(visitor);
        if(FormParsList!=null) FormParsList.accept(visitor);
        if(VarDeclListList!=null) VarDeclListList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethDeclType!=null) MethDeclType.traverseTopDown(visitor);
        if(FormParsList!=null) FormParsList.traverseTopDown(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethDeclType!=null) MethDeclType.traverseBottomUp(visitor);
        if(FormParsList!=null) FormParsList.traverseBottomUp(visitor);
        if(VarDeclListList!=null) VarDeclListList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(MethDeclType!=null)
            buffer.append(MethDeclType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(FormParsList!=null)
            buffer.append(FormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclListList!=null)
            buffer.append(VarDeclListList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
