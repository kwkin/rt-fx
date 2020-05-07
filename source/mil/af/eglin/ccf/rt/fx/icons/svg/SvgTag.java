package mil.af.eglin.ccf.rt.fx.icons.svg;

public enum SvgTag
{
    RECT("rect"),
    CIRCLE("circle"),
    ELLIPSE("ellipse"),
    PATH("path"),
    POLYGON("polygon"),
    LINE("line"),
    POLYLINE("polyline"),
    TEXT("text"),
    IMAGE("image"),
    SVG("svg"),
    G("g"),
    LINEAR_GRADIENT("linearGradient"),
    RADIAL_GRADIENT("radialGradient"),
    INVALID("");
    
    private String svgTag;
    
    SvgTag(String tag)
    {
        this.svgTag = tag;
    }
    
    public static SvgTag getTagFromString(String name)
    {
        SvgTag selectedTag = SvgTag.INVALID;
        for (SvgTag tag : SvgTag.values())
        {
            if (tag.getSvgTag().equals(name))
            {
                selectedTag = tag;
                break;
            }
        }
        return selectedTag;
    }
    
    public String getSvgTag()
    {
        return this.svgTag;
    }
}
