package mil.af.eglin.ccf.rt.fx.control;

import mil.af.eglin.ccf.rt.fx.control.style.TableColumnStyle;

public class TableColumn<S, T> extends javafx.scene.control.TableColumn<S, T>
{
    protected TableColumnStyle style = TableColumnStyle.TEXT;
    
    private static final String CSS_CLASS = "rt-table-column";
    
    public TableColumn() 
    {
        super();
        initialize();
    }
    
    public TableColumn(String text)
    {
        super(text);
        initialize();
    }

    public TableColumn(TableColumnStyle style) 
    {
        super();
        this.style = style;
        initialize();
    }
    
    public TableColumn(String text, TableColumnStyle style)
    {
        super(text);
        this.style = style;
        initialize();
    }
    

    private void initialize() 
    {
        getStyleClass().add(String.format("%s-%s", CSS_CLASS, this.style.getCssStyleName()));
    }
}
