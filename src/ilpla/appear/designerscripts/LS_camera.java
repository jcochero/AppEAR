package ilpla.appear.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_camera{

public static void LS_320x480_1(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="btnTakePicture.TextSize = 14"[camera/320x480,scale=1]
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btntakepicture").vw).setTextSize((float)(14d));
//BA.debugLineNum = 3;BA.debugLine="btnTomarFoto.TextSize = 12"[camera/320x480,scale=1]
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btntomarfoto").vw).setTextSize((float)(12d));
//BA.debugLineNum = 4;BA.debugLine="btnAdjuntarFoto.TextSize = 12"[camera/320x480,scale=1]
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("btnadjuntarfoto").vw).setTextSize((float)(12d));
//BA.debugLineNum = 5;BA.debugLine="lblInstruccion.TextSize = 10"[camera/320x480,scale=1]
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("lblinstruccion").vw).setTextSize((float)(10d));
//BA.debugLineNum = 6;BA.debugLine="lblTitulo.TextSize = 14"[camera/320x480,scale=1]
((anywheresoftware.b4a.keywords.LayoutBuilder.DesignerTextSizeMethod)views.get("lbltitulo").vw).setTextSize((float)(14d));

}
public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);

}
}