package ilpla.appear.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_laymainexplorar{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("btninatlink").vw.setLeft((int)((50d / 100 * width)-((views.get("btninatlink").vw.getWidth())/2d)));
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btninatlink").vw).setTextSize((float)((45d * scale)));
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}