package mil.af.eglin.ccf.rt.fx.control;

import mil.af.eglin.ccf.rt.fx.control.style.Accent;
import mil.af.eglin.ccf.rt.fx.control.style.TextStyle;

public class Text extends javafx.scene.text.Text implements RtComponent
{
    protected TextStyle style = TextStyle.SMALL;
    protected Accent accent = Accent.PRIMARY_MID;
    
    private static final String CSS_CLASS = "rt-text";

    public Text()
    {
        super();
        initialize();
    }

    public Text(String text)
    {
        super(text);
        initialize();
    }

    public Text(String text, TextStyle style)
    {
        super(text);
        this.style = style;
        initialize();
    }

    public Text(String text, Accent accent)
    {
        super(text);
        this.accent = accent;
        initialize();
    }

    public Text(String text, TextStyle style, Accent accent)
    {
        super(text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    public Text(double x, double y, String text)
    {
        super(x, y, text);
        initialize();
    }

    public Text(double x, double y, String text, TextStyle style)
    {
        super(x, y, text);
        this.style = style;
        initialize();
    }

    public Text(double x, double y, String text, Accent accent)
    {
        super(x, y, text);
        this.accent = accent;
        initialize();
    }

    public Text(double x, double y, String text, TextStyle style, Accent accent)
    {
        super(x, y, text);
        this.style = style;
        this.accent = accent;
        initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Accent getAccent()
    {
        return this.accent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtStyleCssName()
    {
        return CSS_CLASS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRtAccentCssName()
    {
        return this.accent.getCssName();
    }
    
    private void initialize()
    {
        getStyleClass().add(CSS_CLASS);
        getStyleClass().add(this.style.getCssName());
        getStyleClass().add(this.accent.getCssName());
    }
}
