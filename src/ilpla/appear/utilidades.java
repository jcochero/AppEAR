package ilpla.appear;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class utilidades {
private static utilidades mostCurrent = new utilidades();
public static Object getObject() {
    throw new RuntimeException("Code module does not support this method.");
}
 public anywheresoftware.b4a.keywords.Common __c = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmperfil _frmperfil = null;
public ilpla.appear.register _register = null;
public ilpla.appear.aprender_memory _aprender_memory = null;
public ilpla.appear.aprender_ahorcado _aprender_ahorcado = null;
public ilpla.appear.starter _starter = null;
public ilpla.appear.reporte_envio _reporte_envio = null;
public ilpla.appear.aprender_ambientes _aprender_ambientes = null;
public ilpla.appear.aprender_ciclo _aprender_ciclo = null;
public ilpla.appear.aprender_comunidades _aprender_comunidades = null;
public ilpla.appear.aprender_contaminacion _aprender_contaminacion = null;
public ilpla.appear.aprender_factores _aprender_factores = null;
public ilpla.appear.aprender_muestreo _aprender_muestreo = null;
public ilpla.appear.aprender_trofica _aprender_trofica = null;
public ilpla.appear.dbutils _dbutils = null;
public ilpla.appear.downloadservice _downloadservice = null;
public ilpla.appear.firebasemessaging _firebasemessaging = null;
public ilpla.appear.form_main _form_main = null;
public ilpla.appear.form_reporte _form_reporte = null;
public ilpla.appear.frmabout _frmabout = null;
public ilpla.appear.frmdatosanteriores _frmdatosanteriores = null;
public ilpla.appear.frmeditprofile _frmeditprofile = null;
public ilpla.appear.frmfelicitaciones _frmfelicitaciones = null;
public ilpla.appear.frmlocalizacion _frmlocalizacion = null;
public ilpla.appear.frmlogin _frmlogin = null;
public ilpla.appear.frmpoliticadatos _frmpoliticadatos = null;
public ilpla.appear.httputils2service _httputils2service = null;
public ilpla.appear.imagedownloader _imagedownloader = null;
public ilpla.appear.inatcheck _inatcheck = null;
public ilpla.appear.reporte_fotos _reporte_fotos = null;
public ilpla.appear.reporte_habitat_estuario _reporte_habitat_estuario = null;
public ilpla.appear.reporte_habitat_laguna _reporte_habitat_laguna = null;
public ilpla.appear.reporte_habitat_rio _reporte_habitat_rio = null;
public ilpla.appear.uploadfiles _uploadfiles = null;
public static String  _bmsgbox3_click(anywheresoftware.b4a.BA _ba) throws Exception{
anywheresoftware.b4a.objects.ButtonWrapper _b = null;
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 557;BA.debugLine="Sub BMsgBox3_Click";
 //BA.debugLineNum = 558;BA.debugLine="Dim B As Button = Sender";
_b = new anywheresoftware.b4a.objects.ButtonWrapper();
_b = (anywheresoftware.b4a.objects.ButtonWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ButtonWrapper(), (android.widget.Button)(anywheresoftware.b4a.keywords.Common.Sender(_ba)));
 //BA.debugLineNum = 559;BA.debugLine="Dim P As Panel = B.Parent";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(_b.getParent()));
 //BA.debugLineNum = 561;BA.debugLine="P.Tag=B.Tag";
_p.setTag(_b.getTag());
 //BA.debugLineNum = 562;BA.debugLine="End Sub";
return "";
}
public static String  _colorearcirculos(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.LabelWrapper _label,String _valor) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _labelborder = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _sldwhite = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _labelborderclicked = null;
int _valorind = 0;
 //BA.debugLineNum = 265;BA.debugLine="Sub ColorearCirculos (label As Label, valor As Str";
 //BA.debugLineNum = 266;BA.debugLine="Dim LabelBorder As ColorDrawable";
_labelborder = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 267;BA.debugLine="Dim sldWhite As StateListDrawable";
_sldwhite = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 268;BA.debugLine="Dim LabelBorderClicked As ColorDrawable";
_labelborderclicked = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 270;BA.debugLine="LabelBorderClicked.Initialize2(Colors.ARGB(255,25";
_labelborderclicked.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 271;BA.debugLine="sldWhite.Initialize";
_sldwhite.Initialize();
 //BA.debugLineNum = 273;BA.debugLine="If valor = \"NS\" Then";
if ((_valor).equals("NS")) { 
 //BA.debugLineNum = 274;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,255,255,25";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 276;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 277;BA.debugLine="label.TextColor = Colors.White";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 }else if((_valor).equals("")) { 
 //BA.debugLineNum = 279;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,255,255,25";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 280;BA.debugLine="sldWhite.AddState(sldWhite.State_Pressed, LabelB";
_sldwhite.AddState(_sldwhite.State_Pressed,(android.graphics.drawable.Drawable)(_labelborderclicked.getObject()));
 //BA.debugLineNum = 281;BA.debugLine="sldWhite.AddState (sldWhite.State_Enabled,LabelB";
_sldwhite.AddState(_sldwhite.State_Enabled,(android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 282;BA.debugLine="label.background = sldWhite";
_label.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 283;BA.debugLine="label.TextColor = Colors.Gray";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 }else {
 //BA.debugLineNum = 285;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19)";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 286;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 288;BA.debugLine="Dim valorind As Int";
_valorind = 0;
 //BA.debugLineNum = 289;BA.debugLine="valorind = valor";
_valorind = (int)(Double.parseDouble(_valor));
 //BA.debugLineNum = 290;BA.debugLine="label.TextColor = Colors.White";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 291;BA.debugLine="If valorind < 2 Then";
if (_valorind<2) { 
 //BA.debugLineNum = 293;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 294;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<4 && _valorind>=2) { 
 //BA.debugLineNum = 297;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 298;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<6 && _valorind>=4) { 
 //BA.debugLineNum = 301;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,179,191,0";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (179),(int) (191),(int) (0)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (179),(int) (191),(int) (0)));
 //BA.debugLineNum = 302;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<8 && _valorind>=6) { 
 //BA.debugLineNum = 305;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,66,191,41";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (66),(int) (191),(int) (41)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (66),(int) (191),(int) (41)));
 //BA.debugLineNum = 306;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind>8) { 
 //BA.debugLineNum = 309;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,36,73,191";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (36),(int) (73),(int) (191)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (36),(int) (73),(int) (191)));
 //BA.debugLineNum = 310;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 };
 };
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.ColorDrawable  _corner(anywheresoftware.b4a.BA _ba,int _colore) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cdb = null;
 //BA.debugLineNum = 363;BA.debugLine="Sub Corner(Colore As Int) As ColorDrawable";
 //BA.debugLineNum = 364;BA.debugLine="Dim cdb As ColorDrawable";
_cdb = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 365;BA.debugLine="cdb.Initialize(Colore, 20dip)";
_cdb.Initialize(_colore,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 366;BA.debugLine="Return cdb";
if (true) return _cdb;
 //BA.debugLineNum = 367;BA.debugLine="End Sub";
return null;
}
public static void  _createhaloeffect(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.ButtonWrapper _objeto,int _clr) throws Exception{
ResumableSub_CreateHaloEffect rsub = new ResumableSub_CreateHaloEffect(null,_ba,_parent,_objeto,_clr);
rsub.resume((_ba.processBA == null ? _ba : _ba.processBA), null);
}
public static class ResumableSub_CreateHaloEffect extends BA.ResumableSub {
public ResumableSub_CreateHaloEffect(ilpla.appear.utilidades parent,anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.ButtonWrapper _objeto,int _clr) {
this.parent = parent;
this._ba = _ba;
this._parent = _parent;
this._objeto = _objeto;
this._clr = _clr;
}
ilpla.appear.utilidades parent;
anywheresoftware.b4a.BA _ba;
anywheresoftware.b4a.objects.B4XViewWrapper _parent;
anywheresoftware.b4a.objects.ButtonWrapper _objeto;
int _clr;
anywheresoftware.b4a.objects.B4XCanvas _cvs = null;
anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _radius = 0;
int _x = 0;
int _y = 0;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp = null;
int _i = 0;
int step12;
int limit12;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 323;BA.debugLine="Dim cvs As B4XCanvas";
_cvs = new anywheresoftware.b4a.objects.B4XCanvas();
 //BA.debugLineNum = 324;BA.debugLine="Dim xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 325;BA.debugLine="Dim p As B4XView = xui.CreatePanel(\"\")";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = _xui.CreatePanel((_ba.processBA == null ? _ba : _ba.processBA),"");
 //BA.debugLineNum = 326;BA.debugLine="Dim radius As Int = 150dip";
_radius = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150));
 //BA.debugLineNum = 327;BA.debugLine="Dim x,y As Int";
_x = 0;
_y = 0;
 //BA.debugLineNum = 328;BA.debugLine="x = objeto.left + (objeto.Width / 2)";
_x = (int) (_objeto.getLeft()+(_objeto.getWidth()/(double)2));
 //BA.debugLineNum = 329;BA.debugLine="y = objeto.top + (objeto.Height / 2)";
_y = (int) (_objeto.getTop()+(_objeto.getHeight()/(double)2));
 //BA.debugLineNum = 331;BA.debugLine="p.SetLayoutAnimated(0, 0, 0, radius * 2, radius *";
_p.SetLayoutAnimated((int) (0),(int) (0),(int) (0),(int) (_radius*2),(int) (_radius*2));
 //BA.debugLineNum = 332;BA.debugLine="cvs.Initialize(p)";
_cvs.Initialize(_p);
 //BA.debugLineNum = 333;BA.debugLine="cvs.DrawCircle(cvs.TargetRect.CenterX, cvs.Target";
_cvs.DrawCircle(_cvs.getTargetRect().getCenterX(),_cvs.getTargetRect().getCenterY(),(float) (_cvs.getTargetRect().getWidth()/(double)2),_clr,anywheresoftware.b4a.keywords.Common.True,(float) (0));
 //BA.debugLineNum = 334;BA.debugLine="Dim bmp As B4XBitmap = cvs.CreateBitmap";
_bmp = new anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper();
_bmp = _cvs.CreateBitmap();
 //BA.debugLineNum = 335;BA.debugLine="For i = 1 To 2";
if (true) break;

case 1:
//for
this.state = 4;
step12 = 1;
limit12 = (int) (2);
_i = (int) (1) ;
this.state = 5;
if (true) break;

case 5:
//C
this.state = 4;
if ((step12 > 0 && _i <= limit12) || (step12 < 0 && _i >= limit12)) this.state = 3;
if (true) break;

case 6:
//C
this.state = 5;
_i = ((int)(0 + _i + step12)) ;
if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 336;BA.debugLine="CreateHaloEffectHelper(Parent,bmp, x, y, clr, ra";
_createhaloeffecthelper(_ba,_parent,_bmp,_x,_y,_clr,_radius);
 //BA.debugLineNum = 337;BA.debugLine="Sleep(200)";
anywheresoftware.b4a.keywords.Common.Sleep(_ba,this,(int) (200));
this.state = 7;
return;
case 7:
//C
this.state = 6;
;
 if (true) break;
if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 339;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _createhaloeffecthelper(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp,int _x,int _y,int _clr,int _radius) throws Exception{
ResumableSub_CreateHaloEffectHelper rsub = new ResumableSub_CreateHaloEffectHelper(null,_ba,_parent,_bmp,_x,_y,_clr,_radius);
rsub.resume((_ba.processBA == null ? _ba : _ba.processBA), null);
}
public static class ResumableSub_CreateHaloEffectHelper extends BA.ResumableSub {
public ResumableSub_CreateHaloEffectHelper(ilpla.appear.utilidades parent,anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _parent,anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp,int _x,int _y,int _clr,int _radius) {
this.parent = parent;
this._ba = _ba;
this._parent = _parent;
this._bmp = _bmp;
this._x = _x;
this._y = _y;
this._clr = _clr;
this._radius = _radius;
}
ilpla.appear.utilidades parent;
anywheresoftware.b4a.BA _ba;
anywheresoftware.b4a.objects.B4XViewWrapper _parent;
anywheresoftware.b4a.objects.B4XViewWrapper.B4XBitmapWrapper _bmp;
int _x;
int _y;
int _clr;
int _radius;
anywheresoftware.b4a.objects.ImageViewWrapper _iv = null;
anywheresoftware.b4a.objects.B4XViewWrapper _p = null;
int _duration = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 342;BA.debugLine="Dim iv As ImageView";
_iv = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 343;BA.debugLine="iv.Initialize(\"\")";
_iv.Initialize(_ba,"");
 //BA.debugLineNum = 344;BA.debugLine="Dim p As B4XView = iv";
_p = new anywheresoftware.b4a.objects.B4XViewWrapper();
_p = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_iv.getObject()));
 //BA.debugLineNum = 345;BA.debugLine="p.SetBitmap(bmp)";
_p.SetBitmap((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 346;BA.debugLine="Parent.AddView(p, x, y, 0, 0)";
_parent.AddView((android.view.View)(_p.getObject()),_x,_y,(int) (0),(int) (0));
 //BA.debugLineNum = 347;BA.debugLine="Dim duration As Int = 1000";
_duration = (int) (1000);
 //BA.debugLineNum = 348;BA.debugLine="p.SetLayoutAnimated(duration, x - radius, y - rad";
_p.SetLayoutAnimated(_duration,(int) (_x-_radius),(int) (_y-_radius),(int) (2*_radius),(int) (2*_radius));
 //BA.debugLineNum = 349;BA.debugLine="p.SetVisibleAnimated(duration, False)";
_p.SetVisibleAnimated(_duration,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 350;BA.debugLine="Sleep(duration)";
anywheresoftware.b4a.keywords.Common.Sleep(_ba,this,_duration);
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 351;BA.debugLine="p.RemoveViewFromParent";
_p.RemoveViewFromParent();
 //BA.debugLineNum = 352;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _ellisse(anywheresoftware.b4a.BA _ba,int _colore) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bm = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _can = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec1 = null;
 //BA.debugLineNum = 397;BA.debugLine="Sub Ellisse(Colore As Int) As Bitmap";
 //BA.debugLineNum = 398;BA.debugLine="Dim Bm As Bitmap";
_bm = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 399;BA.debugLine="Dim Can As Canvas";
_can = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 400;BA.debugLine="Dim Rec1 As Rect";
_rec1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 402;BA.debugLine="Bm.InitializeMutable(300dip,100dip)";
_bm.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 403;BA.debugLine="Can.Initialize2(Bm)";
_can.Initialize2((android.graphics.Bitmap)(_bm.getObject()));
 //BA.debugLineNum = 404;BA.debugLine="Rec1.Initialize(-40dip,-100dip,340dip,100dip)";
_rec1.Initialize((int) (-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),(int) (-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (340)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 405;BA.debugLine="Can.DrawOval(Rec1,Colore,True,1dip)";
_can.DrawOval((android.graphics.Rect)(_rec1.getObject()),_colore,anywheresoftware.b4a.keywords.Common.True,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 407;BA.debugLine="Return Bm";
if (true) return _bm;
 //BA.debugLineNum = 408;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _frangia(anywheresoftware.b4a.BA _ba,int _colore) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bm = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _can = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec1 = null;
int _i = 0;
 //BA.debugLineNum = 410;BA.debugLine="Sub Frangia(Colore As Int) As Bitmap";
 //BA.debugLineNum = 411;BA.debugLine="Dim Bm As Bitmap";
_bm = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 412;BA.debugLine="Dim Can As Canvas";
_can = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 413;BA.debugLine="Dim Rec1 As Rect";
_rec1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 415;BA.debugLine="Bm.InitializeMutable(300dip,100dip)";
_bm.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 416;BA.debugLine="Can.Initialize2(Bm)";
_can.Initialize2((android.graphics.Bitmap)(_bm.getObject()));
 //BA.debugLineNum = 417;BA.debugLine="Can.DrawColor(Colors.White)";
_can.DrawColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 418;BA.debugLine="Rec1.Initialize(0dip,0dip,300dip,90dip)";
_rec1.Initialize(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 419;BA.debugLine="Can.DrawRect(Rec1,Colore,True,1dip)";
_can.DrawRect((android.graphics.Rect)(_rec1.getObject()),_colore,anywheresoftware.b4a.keywords.Common.True,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 420;BA.debugLine="For i=0 To 7";
{
final int step9 = 1;
final int limit9 = (int) (7);
_i = (int) (0) ;
for (;_i <= limit9 ;_i = _i + step9 ) {
 //BA.debugLineNum = 421;BA.debugLine="Can.DrawCircle(10dip+i*40dip,90dip,10dip,Colore,";
_can.DrawCircle((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))+_i*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),_colore,anywheresoftware.b4a.keywords.Common.True,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 422;BA.debugLine="Can.DrawCircle(30dip+i*40dip,90dip,10dip,Colors.";
_can.DrawCircle((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))+_i*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.True,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 }
};
 //BA.debugLineNum = 425;BA.debugLine="Return Bm";
if (true) return _bm;
 //BA.debugLineNum = 426;BA.debugLine="End Sub";
return null;
}
public static String  _getdeviceid(anywheresoftware.b4a.BA _ba) throws Exception{
String _deviceid = "";
anywheresoftware.b4a.objects.collections.Map _wherefields = null;
 //BA.debugLineNum = 17;BA.debugLine="Sub GetDeviceId As String";
 //BA.debugLineNum = 19;BA.debugLine="Dim deviceID As String";
_deviceid = "";
 //BA.debugLineNum = 20;BA.debugLine="deviceID = FirebaseMessaging.fm.Token";
_deviceid = mostCurrent._firebasemessaging._fm /*anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper*/ .getToken();
 //BA.debugLineNum = 21;BA.debugLine="Return deviceID";
if (true) return _deviceid;
 //BA.debugLineNum = 22;BA.debugLine="Log(deviceID)";
anywheresoftware.b4a.keywords.Common.LogImpl("71835013",_deviceid,0);
 //BA.debugLineNum = 24;BA.debugLine="Dim WhereFields As Map";
_wherefields = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 25;BA.debugLine="WhereFields.Initialize";
_wherefields.Initialize();
 //BA.debugLineNum = 26;BA.debugLine="WhereFields.Put(\"configid\", \"1\")";
_wherefields.Put((Object)("configid"),(Object)("1"));
 //BA.debugLineNum = 27;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"appconfig\",";
mostCurrent._dbutils._updaterecord /*String*/ (_ba,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"appconfig","deviceID",(Object)(_deviceid),_wherefields);
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _mensaje(anywheresoftware.b4a.BA _ba,String _titulo,String _imagen,String _text,String _textsub,String _botontextoyes,String _botontextocan,String _botontextono,boolean _textolargo) throws Exception{
flm.b4a.betterdialogs.BetterDialogs _dial = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgbottomimg = null;
anywheresoftware.b4a.objects.PanelWrapper _panelcontent = null;
anywheresoftware.b4a.objects.LabelWrapper _titulolbl = null;
anywheresoftware.b4a.objects.ImageViewWrapper _msgtopimg = null;
anywheresoftware.b4a.objects.LabelWrapper _contenido = null;
anywheresoftware.b4a.objects.LabelWrapper _textosub = null;
String _msg = "";
anywheresoftware.b4a.objects.ImageViewWrapper _imgbitmap = null;
 //BA.debugLineNum = 67;BA.debugLine="Sub Mensaje (titulo As String, imagen As String, t";
 //BA.debugLineNum = 68;BA.debugLine="Dim dial As BetterDialogs";
_dial = new flm.b4a.betterdialogs.BetterDialogs();
 //BA.debugLineNum = 69;BA.debugLine="Dim MsgBottomImg As ImageView";
_msgbottomimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Dim panelcontent As Panel";
_panelcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim titulolbl As Label";
_titulolbl = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim MsgTopImg As ImageView";
_msgtopimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim contenido As Label";
_contenido = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Dim textoSub As Label";
_textosub = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 77;BA.debugLine="MsgBottomImg.Initialize(\"\")";
_msgbottomimg.Initialize(_ba,"");
 //BA.debugLineNum = 78;BA.debugLine="MsgTopImg.Initialize(\"\")";
_msgtopimg.Initialize(_ba,"");
 //BA.debugLineNum = 79;BA.debugLine="panelcontent.Initialize(\"\")";
_panelcontent.Initialize(_ba,"");
 //BA.debugLineNum = 80;BA.debugLine="titulolbl.Initialize(\"\")";
_titulolbl.Initialize(_ba,"");
 //BA.debugLineNum = 81;BA.debugLine="contenido.Initialize(\"\")";
_contenido.Initialize(_ba,"");
 //BA.debugLineNum = 82;BA.debugLine="textoSub.Initialize(\"\")";
_textosub.Initialize(_ba,"");
 //BA.debugLineNum = 83;BA.debugLine="MsgTopImg.Gravity = Gravity.FILL";
_msgtopimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 84;BA.debugLine="MsgBottomImg.Gravity = Gravity.FILL";
_msgbottomimg.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 86;BA.debugLine="titulolbl.Text = titulo";
_titulolbl.setText(BA.ObjectToCharSequence(_titulo));
 //BA.debugLineNum = 87;BA.debugLine="titulolbl.TextColor = Colors.Black";
_titulolbl.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 88;BA.debugLine="titulolbl.Gravity = Gravity.CENTER_HORIZONTAL";
_titulolbl.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 89;BA.debugLine="titulolbl.TextSize = 22";
_titulolbl.setTextSize((float) (22));
 //BA.debugLineNum = 90;BA.debugLine="contenido.Text = text";
_contenido.setText(BA.ObjectToCharSequence(_text));
 //BA.debugLineNum = 91;BA.debugLine="contenido.TextColor = Colors.Black";
_contenido.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 92;BA.debugLine="contenido.TextSize = 18";
_contenido.setTextSize((float) (18));
 //BA.debugLineNum = 93;BA.debugLine="contenido.Gravity = Gravity.CENTER_HORIZONTAL";
_contenido.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 94;BA.debugLine="contenido.Color = Colors.white";
_contenido.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 95;BA.debugLine="textoSub.Text = textsub";
_textosub.setText(BA.ObjectToCharSequence(_textsub));
 //BA.debugLineNum = 96;BA.debugLine="textoSub.TextColor = Colors.DarkGray";
_textosub.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 97;BA.debugLine="textoSub.TextSize = 16";
_textosub.setTextSize((float) (16));
 //BA.debugLineNum = 98;BA.debugLine="textoSub.Gravity = Gravity.CENTER_HORIZONTAL";
_textosub.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 99;BA.debugLine="textoSub.Color = Colors.white";
_textosub.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 100;BA.debugLine="panelcontent.Color = Colors.white";
_panelcontent.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 103;BA.debugLine="Dim imgbitmap As ImageView";
_imgbitmap = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 104;BA.debugLine="imgbitmap.Initialize(\"\")";
_imgbitmap.Initialize(_ba,"");
 //BA.debugLineNum = 105;BA.debugLine="If imagen <> \"Null\" Then";
if ((_imagen).equals("Null") == false) { 
 //BA.debugLineNum = 106;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_imagen,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 }else {
 //BA.debugLineNum = 108;BA.debugLine="imgbitmap.Bitmap =  LoadBitmapSample(File.DirAss";
_imgbitmap.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"MsgIcon.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba)).getObject()));
 };
 //BA.debugLineNum = 111;BA.debugLine="panelcontent.AddView(MsgTopImg, 0, 0,90%x, 60dip)";
_panelcontent.AddView((android.view.View)(_msgtopimg.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 112;BA.debugLine="panelcontent.AddView(imgbitmap, 5%x, 3%y, 80%x, 3";
_panelcontent.AddView((android.view.View)(_imgbitmap.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba));
 //BA.debugLineNum = 113;BA.debugLine="panelcontent.AddView(titulolbl, 0, 35%y, 90%x, 60";
_panelcontent.AddView((android.view.View)(_titulolbl.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 114;BA.debugLine="panelcontent.AddView(contenido, 5%x, titulolbl.To";
_panelcontent.AddView((android.view.View)(_contenido.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_titulolbl.getTop()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
 //BA.debugLineNum = 115;BA.debugLine="If textolargo = True Then";
if (_textolargo==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 116;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.To";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (25),_ba));
 }else {
 //BA.debugLineNum = 118;BA.debugLine="panelcontent.AddView(textoSub, 5%x, contenido.To";
_panelcontent.AddView((android.view.View)(_textosub.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),(int) (_contenido.getTop()+_contenido.getHeight()+anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (3),_ba)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),_ba));
 };
 //BA.debugLineNum = 121;BA.debugLine="panelcontent.AddView(MsgBottomImg, 0, 280dip, 90%";
_panelcontent.AddView((android.view.View)(_msgbottomimg.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (280)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 123;BA.debugLine="msg = dial.CustomDialog(\"\", 90%x, 60dip, panelcon";
_msg = BA.NumberToString(_dial.CustomDialog((Object)(""),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)),(anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(_panelcontent.getObject())),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),_ba),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),_ba),anywheresoftware.b4a.keywords.Common.Null,(Object)(_botontextoyes),(Object)(_botontextocan),(Object)(_botontextono),anywheresoftware.b4a.keywords.Common.False,"",_ba));
 //BA.debugLineNum = 124;BA.debugLine="Return msg";
if (true) return _msg;
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return "";
}
public static int  _msgbox3(anywheresoftware.b4a.BA _ba,String _message,String _title,String _positive,String _cancel,String _negative,anywheresoftware.b4a.objects.ActivityWrapper _act) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.PanelWrapper _box = null;
int _ret = 0;
int _larghezza = 0;
int _lbu2 = 0;
int _lbu3 = 0;
boolean _buttontrasparent = false;
int _buttoncolor = 0;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.LabelWrapper _mess = null;
anywheresoftware.b4a.objects.ButtonWrapper _bpositive = null;
anywheresoftware.b4a.objects.ButtonWrapper _bcancel = null;
anywheresoftware.b4a.objects.ButtonWrapper _bnegative = null;
 //BA.debugLineNum = 432;BA.debugLine="Sub MsgBox3(Message As String, Title As String, Po";
 //BA.debugLineNum = 433;BA.debugLine="Dim P, Box As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_box = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 434;BA.debugLine="Dim Ret As Int";
_ret = 0;
 //BA.debugLineNum = 435;BA.debugLine="Dim Larghezza As Int = 300dip";
_larghezza = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300));
 //BA.debugLineNum = 436;BA.debugLine="Dim LBu2 As Int = (Larghezza-30dip)/2";
_lbu2 = (int) ((_larghezza-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)))/(double)2);
 //BA.debugLineNum = 437;BA.debugLine="Dim LBu3 As Int = (Larghezza-40dip)/3";
_lbu3 = (int) ((_larghezza-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)))/(double)3);
 //BA.debugLineNum = 438;BA.debugLine="Dim ButtonTrasparent As Boolean = False";
_buttontrasparent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 439;BA.debugLine="Dim ButtonColor As Int= Colors.RGB(50,120,60)";
_buttoncolor = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (50),(int) (120),(int) (60));
 //BA.debugLineNum = 441;BA.debugLine="P.Initialize(\"PanelMsgBox3\")";
_p.Initialize(_ba,"PanelMsgBox3");
 //BA.debugLineNum = 442;BA.debugLine="P.Color=Colors.ARGB(210,10,10,10)";
_p.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (210),(int) (10),(int) (10),(int) (10)));
 //BA.debugLineNum = 443;BA.debugLine="Act.AddView(P,0dip,0dip,100%x,100%y)";
_act.AddView((android.view.View)(_p.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba));
 //BA.debugLineNum = 445;BA.debugLine="Box.Initialize(\"Box\")";
_box.Initialize(_ba,"Box");
 //BA.debugLineNum = 446;BA.debugLine="Box.Tag=\"\"";
_box.setTag((Object)(""));
 //BA.debugLineNum = 448;BA.debugLine="Box.Background=Corner(Colors.White)";
_box.setBackground((android.graphics.drawable.Drawable)(_corner(_ba,anywheresoftware.b4a.keywords.Common.Colors.White).getObject()));
 //BA.debugLineNum = 450;BA.debugLine="P.AddView(Box,50%x-Larghezza/2,50%y-150dip,Larghe";
_p.AddView((android.view.View)(_box.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),_ba)-_larghezza/(double)2),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),_ba)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))),_larghezza,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 452;BA.debugLine="Dim L As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 453;BA.debugLine="L.Initialize(\"\")";
_l.Initialize(_ba,"");
 //BA.debugLineNum = 454;BA.debugLine="L.SetBackgroundImage(Underline(Colors.White,Color";
_l.SetBackgroundImageNew((android.graphics.Bitmap)(_underline(_ba,anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.DarkGray).getObject()));
 //BA.debugLineNum = 455;BA.debugLine="L.Textcolor=Colors.Black";
_l.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 456;BA.debugLine="L.Gravity=Gravity.CENTER";
_l.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 457;BA.debugLine="L.TextSize=18";
_l.setTextSize((float) (18));
 //BA.debugLineNum = 458;BA.debugLine="L.Typeface=Typeface.DEFAULT_BOLD";
_l.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 459;BA.debugLine="L.Text=Title";
_l.setText(BA.ObjectToCharSequence(_title));
 //BA.debugLineNum = 460;BA.debugLine="Box.AddView(L,20dip,0dip,Larghezza-40dip,50dip)";
_box.AddView((android.view.View)(_l.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (_larghezza-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 462;BA.debugLine="Dim Mess As Label";
_mess = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 463;BA.debugLine="Mess.Initialize(\"\")";
_mess.Initialize(_ba,"");
 //BA.debugLineNum = 464;BA.debugLine="Mess.textColor=Colors.Black";
_mess.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 465;BA.debugLine="Mess.Text=Message";
_mess.setText(BA.ObjectToCharSequence(_message));
 //BA.debugLineNum = 466;BA.debugLine="Mess.Gravity=Gravity.CENTER";
_mess.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 467;BA.debugLine="Mess.TextSize=22";
_mess.setTextSize((float) (22));
 //BA.debugLineNum = 468;BA.debugLine="Box.AddView(Mess,10dip,50dip,Larghezza-20dip,150d";
_box.AddView((android.view.View)(_mess.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_larghezza-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150)));
 //BA.debugLineNum = 470;BA.debugLine="Dim BPositive, BCancel, BNegative As Button";
_bpositive = new anywheresoftware.b4a.objects.ButtonWrapper();
_bcancel = new anywheresoftware.b4a.objects.ButtonWrapper();
_bnegative = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 472;BA.debugLine="If Positive.Trim<>\"\" Then";
if ((_positive.trim()).equals("") == false) { 
 //BA.debugLineNum = 473;BA.debugLine="BPositive.Initialize(\"BMsgBox3\")";
_bpositive.Initialize(_ba,"BMsgBox3");
 //BA.debugLineNum = 474;BA.debugLine="BPositive.Text=Positive";
_bpositive.setText(BA.ObjectToCharSequence(_positive));
 //BA.debugLineNum = 475;BA.debugLine="BPositive.Gravity=Gravity.CENTER";
_bpositive.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 476;BA.debugLine="BPositive.Typeface=Typeface.DEFAULT_BOLD";
_bpositive.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 477;BA.debugLine="BPositive.Tag=DialogResponse.POSITIVE";
_bpositive.setTag((Object)(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE));
 //BA.debugLineNum = 478;BA.debugLine="Box.AddView(BPositive,10dip,220dip,LBu3,60dip)";
_box.AddView((android.view.View)(_bpositive.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),_lbu3,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 480;BA.debugLine="If ButtonTrasparent Then";
if (_buttontrasparent) { 
 //BA.debugLineNum = 481;BA.debugLine="BPositive.Color=Colors.Transparent";
_bpositive.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 482;BA.debugLine="BPositive.TextColor=ButtonColor";
_bpositive.setTextColor(_buttoncolor);
 }else {
 //BA.debugLineNum = 484;BA.debugLine="BPositive.Background=Corner(ButtonColor)";
_bpositive.setBackground((android.graphics.drawable.Drawable)(_corner(_ba,_buttoncolor).getObject()));
 //BA.debugLineNum = 485;BA.debugLine="BPositive.TextColor=Colors.White";
_bpositive.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
 //BA.debugLineNum = 487;BA.debugLine="If Negative.Trim=\"\" And Cancel.Trim=\"\" Then BPos";
if ((_negative.trim()).equals("") && (_cancel.trim()).equals("")) { 
_bpositive.setLeft((int) (_box.getWidth()/(double)2-_bpositive.getWidth()/(double)2));};
 };
 //BA.debugLineNum = 490;BA.debugLine="If Negative.Trim<>\"\" Then";
if ((_negative.trim()).equals("") == false) { 
 //BA.debugLineNum = 491;BA.debugLine="BNegative.Initialize(\"BMsgBox3\")";
_bnegative.Initialize(_ba,"BMsgBox3");
 //BA.debugLineNum = 492;BA.debugLine="BNegative.Text=Negative";
_bnegative.setText(BA.ObjectToCharSequence(_negative));
 //BA.debugLineNum = 493;BA.debugLine="BNegative.Gravity=Gravity.CENTER";
_bnegative.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 494;BA.debugLine="BNegative.Typeface=Typeface.DEFAULT_BOLD";
_bnegative.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 495;BA.debugLine="BNegative.Tag=DialogResponse.NEGATIVE";
_bnegative.setTag((Object)(anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE));
 //BA.debugLineNum = 496;BA.debugLine="Box.AddView(BNegative,Larghezza-LBu3-10dip,220di";
_box.AddView((android.view.View)(_bnegative.getObject()),(int) (_larghezza-_lbu3-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),_lbu3,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 498;BA.debugLine="If ButtonTrasparent Then";
if (_buttontrasparent) { 
 //BA.debugLineNum = 499;BA.debugLine="BNegative.Color=Colors.Transparent";
_bnegative.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 500;BA.debugLine="BNegative.TextColor=ButtonColor";
_bnegative.setTextColor(_buttoncolor);
 }else {
 //BA.debugLineNum = 502;BA.debugLine="BNegative.Background=Corner(ButtonColor)";
_bnegative.setBackground((android.graphics.drawable.Drawable)(_corner(_ba,_buttoncolor).getObject()));
 //BA.debugLineNum = 503;BA.debugLine="BNegative.TextColor=Colors.White";
_bnegative.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
 };
 //BA.debugLineNum = 507;BA.debugLine="If Cancel.Trim<>\"\" Then";
if ((_cancel.trim()).equals("") == false) { 
 //BA.debugLineNum = 508;BA.debugLine="BCancel.Initialize(\"BMsgBox3\")";
_bcancel.Initialize(_ba,"BMsgBox3");
 //BA.debugLineNum = 509;BA.debugLine="BCancel.Text=Cancel";
_bcancel.setText(BA.ObjectToCharSequence(_cancel));
 //BA.debugLineNum = 510;BA.debugLine="BCancel.Gravity=Gravity.CENTER";
_bcancel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 511;BA.debugLine="BCancel.Typeface=Typeface.DEFAULT_BOLD";
_bcancel.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 512;BA.debugLine="BCancel.Tag=DialogResponse.CANCEL";
_bcancel.setTag((Object)(anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL));
 //BA.debugLineNum = 513;BA.debugLine="Box.AddView(BCancel,20dip+LBu3,220dip,LBu3,60dip";
_box.AddView((android.view.View)(_bcancel.getObject()),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))+_lbu3),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),_lbu3,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 515;BA.debugLine="If  Positive.trim<>\"\" And Negative.Trim=\"\" Then";
if ((_positive.trim()).equals("") == false && (_negative.trim()).equals("")) { 
 //BA.debugLineNum = 516;BA.debugLine="BPositive.Width=LBu2";
_bpositive.setWidth(_lbu2);
 //BA.debugLineNum = 517;BA.debugLine="BCancel.Left=Larghezza-LBu2-10dip";
_bcancel.setLeft((int) (_larghezza-_lbu2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 518;BA.debugLine="BCancel.Width=LBu2";
_bcancel.setWidth(_lbu2);
 };
 //BA.debugLineNum = 520;BA.debugLine="If ButtonTrasparent Then";
if (_buttontrasparent) { 
 //BA.debugLineNum = 521;BA.debugLine="BCancel.Color=Colors.Transparent";
_bcancel.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 522;BA.debugLine="BCancel.TextColor=ButtonColor";
_bcancel.setTextColor(_buttoncolor);
 }else {
 //BA.debugLineNum = 524;BA.debugLine="BCancel.Background=Corner(ButtonColor)";
_bcancel.setBackground((android.graphics.drawable.Drawable)(_corner(_ba,_buttoncolor).getObject()));
 //BA.debugLineNum = 525;BA.debugLine="BCancel.TextColor=Colors.White";
_bcancel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
 }else if((_positive.trim()).equals("") == false && (_negative.trim()).equals("") == false) { 
 //BA.debugLineNum = 528;BA.debugLine="BPositive.Width=LBu2";
_bpositive.setWidth(_lbu2);
 //BA.debugLineNum = 529;BA.debugLine="BNegative.Left=Larghezza-LBu2-10dip";
_bnegative.setLeft((int) (_larghezza-_lbu2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 530;BA.debugLine="BNegative.Width=LBu2";
_bnegative.setWidth(_lbu2);
 };
 //BA.debugLineNum = 533;BA.debugLine="Do While Box.Tag=\"\"";
while ((_box.getTag()).equals((Object)(""))) {
 //BA.debugLineNum = 534;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 535;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 536;BA.debugLine="If P.Width<>GetDeviceLayoutValues.Width  Then";
if (_p.getWidth()!=anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Width) { 
 //BA.debugLineNum = 537;BA.debugLine="Act.Width=GetDeviceLayoutValues.Width";
_act.setWidth(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Width);
 //BA.debugLineNum = 538;BA.debugLine="Act.Height=GetDeviceLayoutValues.Height";
_act.setHeight(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Height);
 //BA.debugLineNum = 539;BA.debugLine="P.Width=GetDeviceLayoutValues.Width";
_p.setWidth(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Width);
 //BA.debugLineNum = 540;BA.debugLine="P.Height=GetDeviceLayoutValues.Height";
_p.setHeight(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Height);
 //BA.debugLineNum = 541;BA.debugLine="Box.Left=P.Width/2-180dip";
_box.setLeft((int) (_p.getWidth()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (180))));
 //BA.debugLineNum = 542;BA.debugLine="Box.Top=P.Height/2-150dip";
_box.setTop((int) (_p.getHeight()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))));
 };
 }
;
 //BA.debugLineNum = 546;BA.debugLine="Ret=Box.Tag";
_ret = (int)(BA.ObjectToNumber(_box.getTag()));
 //BA.debugLineNum = 547;BA.debugLine="P.RemoveView";
_p.RemoveView();
 //BA.debugLineNum = 548;BA.debugLine="Return Ret";
if (true) return _ret;
 //BA.debugLineNum = 549;BA.debugLine="End Sub";
return 0;
}
public static int  _msgbox4(anywheresoftware.b4a.BA _ba,String _message,String _title,String _positive,String _cancel,String _negative,int _graficstyle,anywheresoftware.b4a.objects.ActivityWrapper _act) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
anywheresoftware.b4a.objects.PanelWrapper _box = null;
int _ret = 0;
int _larghezza = 0;
int _lbu2 = 0;
int _lbu3 = 0;
anywheresoftware.b4a.objects.LabelWrapper _l = null;
anywheresoftware.b4a.objects.LabelWrapper _mess = null;
anywheresoftware.b4a.objects.ButtonWrapper _bpositive = null;
anywheresoftware.b4a.objects.ButtonWrapper _bcancel = null;
anywheresoftware.b4a.objects.ButtonWrapper _bnegative = null;
 //BA.debugLineNum = 564;BA.debugLine="Sub MsgBox4(Message As String, Title As String, Po";
 //BA.debugLineNum = 566;BA.debugLine="Dim P, Box As Panel";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_box = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 567;BA.debugLine="Dim Ret As Int";
_ret = 0;
 //BA.debugLineNum = 568;BA.debugLine="Dim Larghezza As Int = 300dip";
_larghezza = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300));
 //BA.debugLineNum = 569;BA.debugLine="Dim LBu2 As Int = (Larghezza-30dip)/2";
_lbu2 = (int) ((_larghezza-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)))/(double)2);
 //BA.debugLineNum = 570;BA.debugLine="Dim LBu3 As Int = (Larghezza-40dip)/3";
_lbu3 = (int) ((_larghezza-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)))/(double)3);
 //BA.debugLineNum = 572;BA.debugLine="P.Initialize(\"PanelMsgBox3\")";
_p.Initialize(_ba,"PanelMsgBox3");
 //BA.debugLineNum = 573;BA.debugLine="P.Color=Colors.ARGB(210,10,10,10)";
_p.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (210),(int) (10),(int) (10),(int) (10)));
 //BA.debugLineNum = 574;BA.debugLine="Act.AddView(P,0dip,0dip,100%x,100%y)";
_act.AddView((android.view.View)(_p.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),_ba),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),_ba));
 //BA.debugLineNum = 576;BA.debugLine="Box.Initialize(\"Box\")";
_box.Initialize(_ba,"Box");
 //BA.debugLineNum = 577;BA.debugLine="Box.Tag=\"\"";
_box.setTag((Object)(""));
 //BA.debugLineNum = 578;BA.debugLine="Box.Color=Colors.White";
_box.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 580;BA.debugLine="P.AddView(Box,50%x-Larghezza/2,50%y-150dip,Larghe";
_p.AddView((android.view.View)(_box.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),_ba)-_larghezza/(double)2),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),_ba)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))),_larghezza,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 582;BA.debugLine="Dim L As Label";
_l = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 583;BA.debugLine="L.Initialize(\"\")";
_l.Initialize(_ba,"");
 //BA.debugLineNum = 584;BA.debugLine="Select GraficStyle";
switch (_graficstyle) {
case 0: {
 //BA.debugLineNum = 586;BA.debugLine="L.SetBackgroundImage(Trapezio(Colors.RGB(60,150";
_l.SetBackgroundImageNew((android.graphics.Bitmap)(_trapezio(_ba,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (60),(int) (150),(int) (80))).getObject()));
 break; }
case 1: {
 //BA.debugLineNum = 588;BA.debugLine="L.SetBackgroundImage(Ellisse(Colors.RGB(60,150,";
_l.SetBackgroundImageNew((android.graphics.Bitmap)(_ellisse(_ba,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (60),(int) (150),(int) (80))).getObject()));
 break; }
case 2: {
 //BA.debugLineNum = 590;BA.debugLine="L.SetBackgroundImage(Frangia(Colors.RGB(60,150,";
_l.SetBackgroundImageNew((android.graphics.Bitmap)(_frangia(_ba,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (60),(int) (150),(int) (80))).getObject()));
 break; }
}
;
 //BA.debugLineNum = 593;BA.debugLine="L.Textcolor=Colors.White";
_l.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 594;BA.debugLine="L.Gravity=Gravity.CENTER";
_l.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 595;BA.debugLine="L.TextSize=14";
_l.setTextSize((float) (14));
 //BA.debugLineNum = 596;BA.debugLine="L.Typeface=Typeface.DEFAULT_BOLD";
_l.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 597;BA.debugLine="L.Text=Title";
_l.setText(BA.ObjectToCharSequence(_title));
 //BA.debugLineNum = 598;BA.debugLine="Box.AddView(L,0dip,0dip,Larghezza,80dip)";
_box.AddView((android.view.View)(_l.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),_larghezza,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)));
 //BA.debugLineNum = 600;BA.debugLine="Dim Mess As Label";
_mess = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 601;BA.debugLine="Mess.Initialize(\"\")";
_mess.Initialize(_ba,"");
 //BA.debugLineNum = 602;BA.debugLine="Mess.textColor=Colors.Black";
_mess.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 603;BA.debugLine="Mess.Text=Message";
_mess.setText(BA.ObjectToCharSequence(_message));
 //BA.debugLineNum = 604;BA.debugLine="Mess.Gravity=Gravity.CENTER";
_mess.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 605;BA.debugLine="Mess.TextSize=22";
_mess.setTextSize((float) (22));
 //BA.debugLineNum = 606;BA.debugLine="Box.AddView(Mess,10dip,80dip,Larghezza-20dip,130d";
_box.AddView((android.view.View)(_mess.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (80)),(int) (_larghezza-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 608;BA.debugLine="Dim BPositive, BCancel, BNegative As Button";
_bpositive = new anywheresoftware.b4a.objects.ButtonWrapper();
_bcancel = new anywheresoftware.b4a.objects.ButtonWrapper();
_bnegative = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 610;BA.debugLine="If Positive.Trim<>\"\" Then";
if ((_positive.trim()).equals("") == false) { 
 //BA.debugLineNum = 611;BA.debugLine="BPositive.Initialize(\"BMsgBox3\")";
_bpositive.Initialize(_ba,"BMsgBox3");
 //BA.debugLineNum = 612;BA.debugLine="BPositive.Text=Positive";
_bpositive.setText(BA.ObjectToCharSequence(_positive));
 //BA.debugLineNum = 613;BA.debugLine="BPositive.Gravity=Gravity.CENTER";
_bpositive.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 614;BA.debugLine="BPositive.TextColor=Colors.White";
_bpositive.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 615;BA.debugLine="BPositive.Typeface=Typeface.DEFAULT_BOLD";
_bpositive.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 616;BA.debugLine="BPositive.Background=Corner(Colors.RGB(60,150,80";
_bpositive.setBackground((android.graphics.drawable.Drawable)(_corner(_ba,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (60),(int) (150),(int) (80))).getObject()));
 //BA.debugLineNum = 617;BA.debugLine="BPositive.Tag=DialogResponse.POSITIVE";
_bpositive.setTag((Object)(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE));
 //BA.debugLineNum = 618;BA.debugLine="Box.AddView(BPositive,10dip,220dip,LBu3,60dip)";
_box.AddView((android.view.View)(_bpositive.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),_lbu3,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 619;BA.debugLine="If Negative.Trim=\"\" And Cancel.Trim=\"\" Then BPos";
if ((_negative.trim()).equals("") && (_cancel.trim()).equals("")) { 
_bpositive.setLeft((int) (_box.getWidth()/(double)2-_bpositive.getWidth()/(double)2));};
 };
 //BA.debugLineNum = 622;BA.debugLine="If Negative.Trim<>\"\" Then";
if ((_negative.trim()).equals("") == false) { 
 //BA.debugLineNum = 623;BA.debugLine="BNegative.Initialize(\"BMsgBox3\")";
_bnegative.Initialize(_ba,"BMsgBox3");
 //BA.debugLineNum = 624;BA.debugLine="BNegative.Text=Negative";
_bnegative.setText(BA.ObjectToCharSequence(_negative));
 //BA.debugLineNum = 625;BA.debugLine="BNegative.Gravity=Gravity.CENTER";
_bnegative.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 626;BA.debugLine="BNegative.TextColor=Colors.White";
_bnegative.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 627;BA.debugLine="BNegative.Typeface=Typeface.DEFAULT_BOLD";
_bnegative.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 628;BA.debugLine="BNegative.Background=Corner(Colors.RGB(60,150,80";
_bnegative.setBackground((android.graphics.drawable.Drawable)(_corner(_ba,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (60),(int) (150),(int) (80))).getObject()));
 //BA.debugLineNum = 629;BA.debugLine="BNegative.Tag=DialogResponse.NEGATIVE";
_bnegative.setTag((Object)(anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE));
 //BA.debugLineNum = 630;BA.debugLine="Box.AddView(BNegative,Larghezza-LBu3-10dip,220di";
_box.AddView((android.view.View)(_bnegative.getObject()),(int) (_larghezza-_lbu3-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),_lbu3,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 };
 //BA.debugLineNum = 633;BA.debugLine="If Cancel.Trim<>\"\" Then";
if ((_cancel.trim()).equals("") == false) { 
 //BA.debugLineNum = 634;BA.debugLine="BCancel.Initialize(\"BMsgBox3\")";
_bcancel.Initialize(_ba,"BMsgBox3");
 //BA.debugLineNum = 635;BA.debugLine="BCancel.Text=Cancel";
_bcancel.setText(BA.ObjectToCharSequence(_cancel));
 //BA.debugLineNum = 636;BA.debugLine="BCancel.Gravity=Gravity.CENTER";
_bcancel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 637;BA.debugLine="BCancel.TextColor=Colors.White";
_bcancel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 638;BA.debugLine="BCancel.Typeface=Typeface.DEFAULT_BOLD";
_bcancel.setTypeface(anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT_BOLD);
 //BA.debugLineNum = 639;BA.debugLine="BCancel.Background=Corner(Colors.RGB(60,150,80))";
_bcancel.setBackground((android.graphics.drawable.Drawable)(_corner(_ba,anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (60),(int) (150),(int) (80))).getObject()));
 //BA.debugLineNum = 640;BA.debugLine="BCancel.Tag=DialogResponse.CANCEL";
_bcancel.setTag((Object)(anywheresoftware.b4a.keywords.Common.DialogResponse.CANCEL));
 //BA.debugLineNum = 641;BA.debugLine="Box.AddView(BCancel,20dip+LBu3,220dip,LBu3,60dip";
_box.AddView((android.view.View)(_bcancel.getObject()),(int) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))+_lbu3),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (220)),_lbu3,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 642;BA.debugLine="If  Positive.trim<>\"\" And Negative.Trim=\"\" Then";
if ((_positive.trim()).equals("") == false && (_negative.trim()).equals("")) { 
 //BA.debugLineNum = 643;BA.debugLine="BPositive.Width=LBu2";
_bpositive.setWidth(_lbu2);
 //BA.debugLineNum = 644;BA.debugLine="BCancel.Left=Larghezza-LBu2-10dip";
_bcancel.setLeft((int) (_larghezza-_lbu2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 645;BA.debugLine="BCancel.Width=LBu2";
_bcancel.setWidth(_lbu2);
 };
 }else if((_positive.trim()).equals("") == false && (_negative.trim()).equals("") == false) { 
 //BA.debugLineNum = 648;BA.debugLine="BPositive.Width=LBu2";
_bpositive.setWidth(_lbu2);
 //BA.debugLineNum = 649;BA.debugLine="BNegative.Left=Larghezza-LBu2-10dip";
_bnegative.setLeft((int) (_larghezza-_lbu2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 650;BA.debugLine="BNegative.Width=LBu2";
_bnegative.setWidth(_lbu2);
 };
 //BA.debugLineNum = 653;BA.debugLine="Do While Box.Tag=\"\"";
while ((_box.getTag()).equals((Object)(""))) {
 //BA.debugLineNum = 654;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 655;BA.debugLine="If P.Width<>GetDeviceLayoutValues.Width  Then";
if (_p.getWidth()!=anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Width) { 
 //BA.debugLineNum = 656;BA.debugLine="Act.Width=GetDeviceLayoutValues.Width";
_act.setWidth(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Width);
 //BA.debugLineNum = 657;BA.debugLine="Act.Height=GetDeviceLayoutValues.Height";
_act.setHeight(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Height);
 //BA.debugLineNum = 658;BA.debugLine="P.Width=GetDeviceLayoutValues.Width";
_p.setWidth(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Width);
 //BA.debugLineNum = 659;BA.debugLine="P.Height=GetDeviceLayoutValues.Height";
_p.setHeight(anywheresoftware.b4a.keywords.Common.GetDeviceLayoutValues(_ba).Height);
 //BA.debugLineNum = 660;BA.debugLine="Box.Left=P.Width/2-180dip";
_box.setLeft((int) (_p.getWidth()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (180))));
 //BA.debugLineNum = 661;BA.debugLine="Box.Top=P.Height/2-150dip";
_box.setTop((int) (_p.getHeight()/(double)2-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))));
 };
 }
;
 //BA.debugLineNum = 665;BA.debugLine="Ret=Box.Tag";
_ret = (int)(BA.ObjectToNumber(_box.getTag()));
 //BA.debugLineNum = 666;BA.debugLine="P.RemoveView";
_p.RemoveView();
 //BA.debugLineNum = 668;BA.debugLine="Return Ret";
if (true) return _ret;
 //BA.debugLineNum = 669;BA.debugLine="End Sub";
return 0;
}
public static String  _panelmsgbox3_touch(anywheresoftware.b4a.BA _ba,int _action,float _x,float _y) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _p = null;
 //BA.debugLineNum = 551;BA.debugLine="Sub PanelMsgBox3_Touch (Action As Int, X As Float,";
 //BA.debugLineNum = 552;BA.debugLine="Dim P As Panel = Sender";
_p = new anywheresoftware.b4a.objects.PanelWrapper();
_p = (anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(anywheresoftware.b4a.keywords.Common.Sender(_ba)));
 //BA.debugLineNum = 554;BA.debugLine="P.RemoveView";
_p.RemoveView();
 //BA.debugLineNum = 555;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 3;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _sabiasque(anywheresoftware.b4a.BA _ba) throws Exception{
String _titulo = "";
String _textoboton = "";
int _random = 0;
anywheresoftware.b4a.objects.collections.List _sqpalabras = null;
int _rndsize = 0;
String _wordx = "";
 //BA.debugLineNum = 37;BA.debugLine="Sub SabiasQue";
 //BA.debugLineNum = 38;BA.debugLine="Dim titulo As String";
_titulo = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim textoboton As String";
_textoboton = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim random As Int";
_random = 0;
 //BA.debugLineNum = 41;BA.debugLine="Dim SQPalabras As List";
_sqpalabras = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 44;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 45;BA.debugLine="titulo = \"Did you know?\"";
_titulo = "Did you know?";
 //BA.debugLineNum = 46;BA.debugLine="textoboton = \"Great!\"";
_textoboton = "Great!";
 //BA.debugLineNum = 47;BA.debugLine="SQPalabras = File.ReadList(File.DirAssets, \"en-S";
_sqpalabras = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-SQPalabras.txt");
 }else {
 //BA.debugLineNum = 49;BA.debugLine="titulo = \"¿Sabías?\"";
_titulo = "¿Sabías?";
 //BA.debugLineNum = 50;BA.debugLine="textoboton = \"Buenísimo!\"";
_textoboton = "Buenísimo!";
 //BA.debugLineNum = 51;BA.debugLine="SQPalabras = File.ReadList(File.DirAssets, \"SQPa";
_sqpalabras = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"SQPalabras.txt");
 };
 //BA.debugLineNum = 57;BA.debugLine="Dim rndSize As Int";
_rndsize = 0;
 //BA.debugLineNum = 58;BA.debugLine="rndSize = SQPalabras.Size";
_rndsize = _sqpalabras.getSize();
 //BA.debugLineNum = 59;BA.debugLine="random = Rnd(1,rndSize)";
_random = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),_rndsize);
 //BA.debugLineNum = 60;BA.debugLine="Dim wordx As String";
_wordx = "";
 //BA.debugLineNum = 61;BA.debugLine="wordx = SQPalabras.Get(random)";
_wordx = BA.ObjectToString(_sqpalabras.Get(_random));
 //BA.debugLineNum = 63;BA.debugLine="Mensaje(titulo,\"MsgIcon.png\", \"Sabías que?\", word";
_mensaje(_ba,_titulo,"MsgIcon.png","Sabías que?",_wordx,_textoboton,"","",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 64;BA.debugLine="End Sub";
return "";
}
public static String  _setcbdrawable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _cb,int _boxcolor,int _boxwidth,int _tickcolor,String _tickchar,int _disabledcolor,int _size,int _padding) throws Exception{
anywheresoftware.b4a.objects.drawable.StateListDrawable _sld = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmenabled = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmchecked = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmdisabled = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cnv = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rect1 = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _enabled = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _checked = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _disabled = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cnv1 = null;
int _fontsize = 0;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cnv2 = null;
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 207;BA.debugLine="Sub SetCBDrawable(CB As RadioButton,BoxColor As In";
 //BA.debugLineNum = 208;BA.debugLine="Dim SLD As StateListDrawable";
_sld = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 209;BA.debugLine="SLD.Initialize";
_sld.Initialize();
 //BA.debugLineNum = 211;BA.debugLine="Dim BMEnabled,BMChecked,BMDisabled As Bitmap";
_bmenabled = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_bmchecked = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
_bmdisabled = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 212;BA.debugLine="BMEnabled.InitializeMutable(Size,Size)";
_bmenabled.InitializeMutable(_size,_size);
 //BA.debugLineNum = 213;BA.debugLine="BMChecked.InitializeMutable(Size,Size)";
_bmchecked.InitializeMutable(_size,_size);
 //BA.debugLineNum = 214;BA.debugLine="BMDisabled.InitializeMutable(Size,Size)";
_bmdisabled.InitializeMutable(_size,_size);
 //BA.debugLineNum = 216;BA.debugLine="Dim CNV As Canvas";
_cnv = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 217;BA.debugLine="CNV.Initialize2(BMEnabled)";
_cnv.Initialize2((android.graphics.Bitmap)(_bmenabled.getObject()));
 //BA.debugLineNum = 218;BA.debugLine="Dim Rect1 As Rect";
_rect1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 219;BA.debugLine="Rect1.Initialize(Padding ,Padding ,Size - Padding";
_rect1.Initialize(_padding,_padding,(int) (_size-_padding),(int) (_size-_padding));
 //BA.debugLineNum = 220;BA.debugLine="CNV.DrawRect(Rect1,BoxColor,False,BoxWidth)";
_cnv.DrawRect((android.graphics.Rect)(_rect1.getObject()),_boxcolor,anywheresoftware.b4a.keywords.Common.False,(float) (_boxwidth));
 //BA.debugLineNum = 221;BA.debugLine="Dim Enabled,Checked,Disabled As BitmapDrawable";
_enabled = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
_checked = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
_disabled = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 222;BA.debugLine="Enabled.Initialize(BMEnabled)";
_enabled.Initialize((android.graphics.Bitmap)(_bmenabled.getObject()));
 //BA.debugLineNum = 224;BA.debugLine="Dim CNV1 As Canvas";
_cnv1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 225;BA.debugLine="CNV1.Initialize2(BMChecked)";
_cnv1.Initialize2((android.graphics.Bitmap)(_bmchecked.getObject()));
 //BA.debugLineNum = 226;BA.debugLine="If TickChar = \"Fill\" Then";
if ((_tickchar).equals("Fill")) { 
 //BA.debugLineNum = 227;BA.debugLine="CNV1.DrawRect(Rect1,TickColor,True,BoxWidth)";
_cnv1.DrawRect((android.graphics.Rect)(_rect1.getObject()),_tickcolor,anywheresoftware.b4a.keywords.Common.True,(float) (_boxwidth));
 //BA.debugLineNum = 228;BA.debugLine="CNV1.DrawRect(Rect1,BoxColor,False,BoxWidth)";
_cnv1.DrawRect((android.graphics.Rect)(_rect1.getObject()),_boxcolor,anywheresoftware.b4a.keywords.Common.False,(float) (_boxwidth));
 }else {
 //BA.debugLineNum = 230;BA.debugLine="CNV1.DrawRect(Rect1,BoxColor,False,BoxWidth)";
_cnv1.DrawRect((android.graphics.Rect)(_rect1.getObject()),_boxcolor,anywheresoftware.b4a.keywords.Common.False,(float) (_boxwidth));
 //BA.debugLineNum = 232;BA.debugLine="Dim FontSize As Int = 6";
_fontsize = (int) (6);
 //BA.debugLineNum = 233;BA.debugLine="Do While CNV.MeasureStringHeight(TickChar,Typefa";
while (_cnv.MeasureStringHeight(_tickchar,anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (_fontsize))<_size-(_boxwidth*2)-(_padding*2)) {
 //BA.debugLineNum = 234;BA.debugLine="FontSize = FontSize + 1";
_fontsize = (int) (_fontsize+1);
 }
;
 //BA.debugLineNum = 236;BA.debugLine="FontSize = FontSize - 1";
_fontsize = (int) (_fontsize-1);
 //BA.debugLineNum = 238;BA.debugLine="CNV1.DrawText(TickChar,Size/2,(Size + CNV.Measur";
_cnv1.DrawText(_ba,_tickchar,(float) (_size/(double)2),(float) ((_size+_cnv.MeasureStringHeight(_tickchar,anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (_fontsize)))/(double)2),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (_fontsize),_tickcolor,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 };
 //BA.debugLineNum = 240;BA.debugLine="Checked.Initialize(BMChecked)";
_checked.Initialize((android.graphics.Bitmap)(_bmchecked.getObject()));
 //BA.debugLineNum = 242;BA.debugLine="Dim CNV2 As Canvas";
_cnv2 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 243;BA.debugLine="CNV2.Initialize2(BMDisabled)";
_cnv2.Initialize2((android.graphics.Bitmap)(_bmdisabled.getObject()));
 //BA.debugLineNum = 244;BA.debugLine="CNV2.DrawRect(Rect1,DisabledColor,True,BoxWidth)";
_cnv2.DrawRect((android.graphics.Rect)(_rect1.getObject()),_disabledcolor,anywheresoftware.b4a.keywords.Common.True,(float) (_boxwidth));
 //BA.debugLineNum = 245;BA.debugLine="CNV2.DrawRect(Rect1,BoxColor,False,BoxWidth)";
_cnv2.DrawRect((android.graphics.Rect)(_rect1.getObject()),_boxcolor,anywheresoftware.b4a.keywords.Common.False,(float) (_boxwidth));
 //BA.debugLineNum = 246;BA.debugLine="Disabled.Initialize(BMDisabled)";
_disabled.Initialize((android.graphics.Bitmap)(_bmdisabled.getObject()));
 //BA.debugLineNum = 249;BA.debugLine="SLD.AddState(SLD.State_Disabled,Disabled)";
_sld.AddState(_sld.State_Disabled,(android.graphics.drawable.Drawable)(_disabled.getObject()));
 //BA.debugLineNum = 250;BA.debugLine="SLD.AddState(SLD.State_Checked,Checked)";
_sld.AddState(_sld.State_Checked,(android.graphics.drawable.Drawable)(_checked.getObject()));
 //BA.debugLineNum = 251;BA.debugLine="SLD.AddState(SLD.State_Enabled,Enabled)";
_sld.AddState(_sld.State_Enabled,(android.graphics.drawable.Drawable)(_enabled.getObject()));
 //BA.debugLineNum = 252;BA.debugLine="SLD.AddCatchAllState(Enabled)";
_sld.AddCatchAllState((android.graphics.drawable.Drawable)(_enabled.getObject()));
 //BA.debugLineNum = 254;BA.debugLine="Dim JO As JavaObject = CB";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_cb.getObject()));
 //BA.debugLineNum = 255;BA.debugLine="JO.RunMethod(\"setButtonDrawable\",Array As Object(";
_jo.RunMethod("setButtonDrawable",new Object[]{(Object)(_sld.getObject())});
 //BA.debugLineNum = 256;BA.debugLine="End Sub";
return "";
}
public static String  _setprogressdrawable(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.ProgressBarWrapper _p,Object _drawable,Object _backgrounddrawable) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _clipdrawable = null;
 //BA.debugLineNum = 133;BA.debugLine="Sub SetProgressDrawable(p As ProgressBar, drawable";
 //BA.debugLineNum = 134;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 136;BA.debugLine="Dim clipDrawable As Object";
_clipdrawable = new Object();
 //BA.debugLineNum = 137;BA.debugLine="clipDrawable = r.CreateObject2(\"android.graphics.";
_clipdrawable = _r.CreateObject2("android.graphics.drawable.ClipDrawable",new Object[]{_drawable,(Object)(anywheresoftware.b4a.keywords.Common.Gravity.LEFT),(Object)(1)},new String[]{"android.graphics.drawable.Drawable","java.lang.int","java.lang.int"});
 //BA.debugLineNum = 140;BA.debugLine="r.Target = p";
_r.Target = (Object)(_p.getObject());
 //BA.debugLineNum = 141;BA.debugLine="r.Target = r.RunMethod(\"getProgressDrawable\") 'Ge";
_r.Target = _r.RunMethod("getProgressDrawable");
 //BA.debugLineNum = 142;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Arra";
_r.RunMethod4("setDrawableByLayerId",new Object[]{(Object)(16908288),_backgrounddrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 145;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Arra";
_r.RunMethod4("setDrawableByLayerId",new Object[]{_r.GetStaticField("android.R$id","progress"),_clipdrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _share(anywheresoftware.b4a.BA _ba,String _imagetoshare,boolean _facebook,String _txtmensaje) throws Exception{
String _app_id = "";
String _redirect_uri = "";
String _name = "";
String _caption = "";
String _description = "";
String _picture = "";
String _link = "";
String _all = "";
anywheresoftware.b4a.objects.IntentWrapper _i = null;
com.madelephantstudios.MESShareLibrary.MESShareLibrary _shareothers = null;
 //BA.debugLineNum = 157;BA.debugLine="Sub Share(imagetoshare As String, facebook As Bool";
 //BA.debugLineNum = 159;BA.debugLine="If facebook = True Then";
if (_facebook==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 161;BA.debugLine="Dim app_id As String = \"1714627388781317\" ' <---";
_app_id = "1714627388781317";
 //BA.debugLineNum = 162;BA.debugLine="Dim redirect_uri As String = \"https://www.facebo";
_redirect_uri = "https://www.facebook.com";
 //BA.debugLineNum = 163;BA.debugLine="Dim name, caption, description, picture, link, a";
_name = "";
_caption = "";
_description = "";
_picture = "";
_link = "";
_all = "";
 //BA.debugLineNum = 166;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 167;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 168;BA.debugLine="caption = \"Yo utilizo AppEAR y ayudo a la cienc";
_caption = "Yo utilizo AppEAR y ayudo a la ciencia!";
 //BA.debugLineNum = 169;BA.debugLine="description = \"Conseguí una nueva medalla!!!\"";
_description = "Conseguí una nueva medalla!!!";
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 172;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 173;BA.debugLine="caption = \"I use AppEAR and help science!\"";
_caption = "I use AppEAR and help science!";
 //BA.debugLineNum = 174;BA.debugLine="description = \"I got a new medal!!!\"";
_description = "I got a new medal!!!";
 };
 //BA.debugLineNum = 177;BA.debugLine="link = \"http://www.app-ear.com.ar\"";
_link = "http://www.app-ear.com.ar";
 //BA.debugLineNum = 178;BA.debugLine="picture = \"http://www.app-ear.com.ar/users/badge";
_picture = "http://www.app-ear.com.ar/users/badges/"+_imagetoshare;
 //BA.debugLineNum = 180;BA.debugLine="all = \"https://www.facebook.com/dialog/feed?app_";
_all = "https://www.facebook.com/dialog/feed?app_id="+_app_id+"&link="+_link+"&name="+_name+"&caption="+_caption+"&description="+_description+"&picture="+_picture+"&redirect_uri="+_redirect_uri;
 //BA.debugLineNum = 183;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 184;BA.debugLine="i.Initialize(i.ACTION_VIEW, all)";
_i.Initialize(_i.ACTION_VIEW,_all);
 //BA.debugLineNum = 185;BA.debugLine="i.SetType(\"text/plain\")";
_i.SetType("text/plain");
 //BA.debugLineNum = 186;BA.debugLine="i.PutExtra(\"android.intent.extra.TEXT\", all)";
_i.PutExtra("android.intent.extra.TEXT",(Object)(_all));
 //BA.debugLineNum = 187;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity((_ba.processBA == null ? _ba : _ba.processBA),(Object)(_i.getObject()));
 }else {
 //BA.debugLineNum = 190;BA.debugLine="Dim ShareOthers As MESShareLibrary";
_shareothers = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 191;BA.debugLine="File.Copy(File.DirAssets, imagetoshare, File.Dir";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_imagetoshare,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"ShareAppEAR.png");
 //BA.debugLineNum = 192;BA.debugLine="ShareOthers.sharebinary(\"file://\" & File.DirDefa";
_shareothers.sharebinary(_ba,"file://"+anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal()+"/ShareAppEAR.png","image/png","Yo uso AppEAR!",_txtmensaje);
 };
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _trapezio(anywheresoftware.b4a.BA _ba,int _colore) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bm = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _can = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper _path1 = null;
 //BA.debugLineNum = 380;BA.debugLine="Sub Trapezio(Colore As Int) As Bitmap";
 //BA.debugLineNum = 381;BA.debugLine="Dim Bm As Bitmap";
_bm = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 382;BA.debugLine="Dim Can As Canvas";
_can = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 383;BA.debugLine="Dim Path1 As Path";
_path1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.PathWrapper();
 //BA.debugLineNum = 385;BA.debugLine="Bm.InitializeMutable(300dip,100dip)";
_bm.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)));
 //BA.debugLineNum = 386;BA.debugLine="Can.Initialize2(Bm)";
_can.Initialize2((android.graphics.Bitmap)(_bm.getObject()));
 //BA.debugLineNum = 387;BA.debugLine="Path1.Initialize(0dip,0dip)";
_path1.Initialize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))));
 //BA.debugLineNum = 388;BA.debugLine="Path1.LineTo(0dip,70dip)";
_path1.LineTo((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))));
 //BA.debugLineNum = 389;BA.debugLine="Path1.LineTo(150dip,100dip)";
_path1.LineTo((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100))));
 //BA.debugLineNum = 390;BA.debugLine="Path1.LineTo(300dip,70dip)";
_path1.LineTo((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (70))));
 //BA.debugLineNum = 391;BA.debugLine="Path1.LineTo(300dip,0dip)";
_path1.LineTo((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))));
 //BA.debugLineNum = 392;BA.debugLine="Path1.LineTo(0dip,0dip)";
_path1.LineTo((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))));
 //BA.debugLineNum = 393;BA.debugLine="Can.DrawPath(Path1,Colore,True,1dip)";
_can.DrawPath((android.graphics.Path)(_path1.getObject()),_colore,anywheresoftware.b4a.keywords.Common.True,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 394;BA.debugLine="Return Bm";
if (true) return _bm;
 //BA.debugLineNum = 395;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper  _underline(anywheresoftware.b4a.BA _ba,int _colore,int _colorelinea) throws Exception{
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bm = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _can = null;
 //BA.debugLineNum = 369;BA.debugLine="Sub Underline(Colore As Int, ColoreLinea As Int) A";
 //BA.debugLineNum = 370;BA.debugLine="Dim Bm As Bitmap";
_bm = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 371;BA.debugLine="Dim Can As Canvas";
_can = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 373;BA.debugLine="Bm.InitializeMutable(300dip,200dip)";
_bm.InitializeMutable(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (200)));
 //BA.debugLineNum = 374;BA.debugLine="Can.Initialize2(Bm)";
_can.Initialize2((android.graphics.Bitmap)(_bm.getObject()));
 //BA.debugLineNum = 375;BA.debugLine="Can.DrawColor(Colore)";
_can.DrawColor(_colore);
 //BA.debugLineNum = 376;BA.debugLine="Can.DrawLine(0dip,198dip,300dip,198dip,ColoreLine";
_can.DrawLine((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (198))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300))),(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (198))),_colorelinea,(float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2))));
 //BA.debugLineNum = 377;BA.debugLine="Return Bm";
if (true) return _bm;
 //BA.debugLineNum = 378;BA.debugLine="End Sub";
return null;
}
}
