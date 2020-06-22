package mil.af.eglin.ccf.rt.fx.control.style;

/**
 * A {@code mil.af.eglin.ccf.rt.fx.control.TableColumn TableColumn} can have two
 * styles:
 * <ul>
 * <li>TEXT: Text is aligned to the left.</li>
 * <li>NUMBER: Text is aligned to the right.</li>
 * </ul>
 * <p>
 * Each style is applied as its own CSS style class.
 * 
 * @see mil.af.eglin.ccf.rt.fx.control.TableColumn
 */
public enum TableColumnStyle
{
    TEXT("text"), 
    NUMBER("number");

    private String cssName;

    TableColumnStyle(String cssName)
    {
        this.cssName = cssName;
    }

    /**
     * Gets the CSS style class name for the accent type.
     * 
     * @return The name of the CSS style class
     */
    public String getCssStyleName()
    {
        return this.cssName;
    }
}