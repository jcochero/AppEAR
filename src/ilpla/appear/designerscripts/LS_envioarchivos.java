package ilpla.appear.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_envioarchivos{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("lblestado").vw).setTextSize((float)(12d));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("button1").vw).setTextSize((float)(25d));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("label2").vw).setTextSize((float)(12d));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnemail").vw).setTextSize((float)(14d));
//BA.debugLineNum = 6;BA.debugLine="Label1.TextSize = 14"[envioarchivos/320x480,scale=1]
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("label1").vw).setTextSize((float)(14d));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}