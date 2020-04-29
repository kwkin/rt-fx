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
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        
//        if (this.style != null)
//        {
//            // TODO remove listeners
//            this.tableViewProperty().addListener((ov, oldVal, newVal) -> 
//            {            
//                newVal.getItems().addListener(new ListChangeListener<S>() 
//                {
//                    @Override
//                    public void onChanged(javafx.collections.ListChangeListener.Change<? extends S> c)
//                    {
//                        if (c.next() && c.wasAdded())
//                        {
//                            T type = getCellObservableValue(0).getValue();
//                            if (type instanceof Number)
//                            {
//                                getStyleClass().add(TableColumnStyle.NUMBER.getCssName());
//                            }
//                            else
//                            {
//                                getStyleClass().add(TableColumnStyle.TEXT.getCssName());
//                            }
//                        }
//                    }
//                });
//            });
//        }
    }
}
