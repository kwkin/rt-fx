package mil.af.eglin.ccf.rt.fx.icons.svg;

import java.nio.file.Path;

import mil.af.eglin.ccf.rt.util.ResourceLoader;

public enum SvgFile
{
    // @formatter:off
    ACCOUNT("account.svg"),
    ALERT_CIRCLE_OUTLINE("alert-circle-outline.svg"),
    ALERT_CIRCLE("alert-circle.svg"),
    ARROW_DOWN_DROP_CIRCLE("arrow-down-drop-circle.svg"),
    ARROW_LEFT("arrow-left.svg"),
    ARROW_RIGHT("arrow-right.svg"),
    BELL_OFF("bell-off.svg"),
    BELL("bell.svg"),
    BRIGHTNESS_1("brightness-1.svg"),
    BRIGHTNESS_2("brightness-2.svg"),
    BRIGHTNESS_3("brightness-3.svg"),
    BRIGHTNESS_4("brightness-4.svg"),
    BRIGHTNESS_5("brightness-5.svg"),
    BRIGHTNESS_6("brightness-6.svg"),
    BRIGHTNESS_7("brightness-7.svg"),
    CHART_BELL_CURVE("chart-bell-curve.svg"),
    CHART_LINE("chart-line.svg"),
    CHECKBOX_BLANK_CIRCLE_OUTLINE("checkbox-blank-circle-outline.svg"),
    CHECKBOX_BLANK_OUTLINE("checkbox-blank-outline.svg"),
    CHECKBOX_MARKED("checkbox-marked.svg"),
    CHECKBOX_MARKED_CIRCLE("checkbox-marked-circle.svg"),
    CROSSHAIRS_GPS("crosshairs-gps.svg"),
    CLOUD_ALERT("cloud-alert.svg"),
    CLOUD_DOWNLOAD("cloud-download.svg"),
    CLOUD_QUESTION("cloud-question.svg"),
    CLOUD_UPLOAD("cloud-upload.svg"),
    COG("cog.svg"),
    EMOTICON_EXCITED_OUTLINE("emoticon-excited-outline.svg"),
    EMOTICON_EXCITED("emoticon-excited.svg"),
    EYEDROPPER_VARIANT("eyedropper-variant.svg"),
    EYE_OUTLINE("eye-outline.svg"),
    EYE("eye.svg"),
    FILE("file.svg"),
    FOLDER_DOWNLOAD("folder-download.svg"),
    FOLDER_PLUS("folder-plus.svg"),
    FOLDER_UPLOAD("folder-upload.svg"),
    FOLDER("folder.svg"),
    LIGHTBULB("lightbulb.svg"),
    LOCK_OPEN("lock-open.svg"),
    LOCK("lock.svg"),
    MAGNIFY_MINUS("magnify-minus.svg"),
    MAGNIFY_PLUS("magnify-plus.svg"),
    MAGNIFY_REMOVE_CURSOR("magnify-remove-cursor.svg"),
    MAGNIFY("magnify.svg"),
    MAP_MARKER_ALERT_OUTLINE("map-marker-alert-outline.svg"),
    MAP_MARKER_ALERT("map-marker-alert.svg"),
    MAP_MARKER("map-marker.svg"),
    MAP("map.svg"),
    PAUSE("pause.svg"),
    PALETTE("palette.svg"),
    PENCIL("pencil.svg"),
    PLAY("play.svg"),
    REDO("redo.svg"),
    SHIELD_ALERT_OUTLINE("shield-alert-outline.svg"),
    SHIELD_ALERT("shield-alert.svg"),
    SQUARE("square.svg"),
    TACO("taco.svg"),
    UNDO("undo.svg");
    // @formatter:on
    
    private String svgFileName;
    
    SvgFile(String svgFileName)
    {
        this.svgFileName = svgFileName;
    }
    
    public String getSvgFileName()
    {
        return svgFileName;
    }
    
    public Path getFilePath()
    {
        return ResourceLoader.loadIconPath(getSvgFileName());
    }
}
