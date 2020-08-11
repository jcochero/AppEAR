package ilpla.appear;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class frmfelicitaciones extends Activity implements B4AActivity{
	public static frmfelicitaciones mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmfelicitaciones");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmfelicitaciones).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(this, processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmfelicitaciones");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmfelicitaciones", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmfelicitaciones) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmfelicitaciones) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return frmfelicitaciones.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        if (!dontPause)
            BA.LogInfo("** Activity (frmfelicitaciones) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmfelicitaciones) Pause event (activity is not paused). **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        if (!dontPause) {
            processBA.setActivityPaused(true);
            mostCurrent = null;
        }

        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            frmfelicitaciones mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmfelicitaciones) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static int _numfotosenviadas = 0;
public static String _tiporio = "";
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldatosenviados = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntosenviados = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolverperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbadge = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshareothers = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnocompartir = null;
public static int _nivel = 0;
public static String _imagetoshare = "";
public anywheresoftware.b4a.objects.PanelWrapper _fondogris = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlshare = null;
public static boolean _subionivel = false;
public ilpla.appear.main _main = null;
public ilpla.appear.frmperfil _frmperfil = null;
public ilpla.appear.utilidades _utilidades = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 39;BA.debugLine="Activity.LoadLayout(\"layFelicitaciones\")";
mostCurrent._activity.LoadLayout("layFelicitaciones",mostCurrent.activityBA);
 //BA.debugLineNum = 42;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 43;BA.debugLine="lblpuntosenviados.Text = \"Has ganado \" & ((10 *";
mostCurrent._lblpuntosenviados.setText(BA.ObjectToCharSequence("Has ganado "+BA.NumberToString(((10*_numfotosenviadas)+60))+" puntos"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 45;BA.debugLine="lblpuntosenviados.Text = \"You won \" & ((10 * num";
mostCurrent._lblpuntosenviados.setText(BA.ObjectToCharSequence("You won "+BA.NumberToString(((10*_numfotosenviadas)+60))+" points"));
 //BA.debugLineNum = 46;BA.debugLine="lblDatosEnviados.Text = \"Data sent, thank you!\"";
mostCurrent._lbldatosenviados.setText(BA.ObjectToCharSequence("Data sent, thank you!"));
 //BA.debugLineNum = 47;BA.debugLine="btnVolverPerfil.Text = \"Continue\"";
mostCurrent._btnvolverperfil.setText(BA.ObjectToCharSequence("Continue"));
 };
 //BA.debugLineNum = 52;BA.debugLine="Timer1.Initialize(\"Timer1\",200)";
_timer1.Initialize(processBA,"Timer1",(long) (200));
 //BA.debugLineNum = 53;BA.debugLine="Timer1.Enabled=True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 56;BA.debugLine="If Form_Main.IsGuest = False Then";
if (mostCurrent._form_main._isguest /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 57;BA.debugLine="CheckNivel";
_checknivel();
 }else {
 //BA.debugLineNum = 59;BA.debugLine="btnVolverPerfil.Visible = True";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 63;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 70;BA.debugLine="Timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 65;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 66;BA.debugLine="Timer1.Enabled=True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return "";
}
public static String  _btnshare_click() throws Exception{
String _app_id = "";
String _redirect_uri = "";
String _name = "";
String _caption = "";
String _description = "";
String _picture = "";
String _link = "";
String _all = "";
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 315;BA.debugLine="Sub btnShare_Click";
 //BA.debugLineNum = 317;BA.debugLine="Dim app_id As String = \"1714627388781317\" ' <---";
_app_id = "1714627388781317";
 //BA.debugLineNum = 318;BA.debugLine="Dim redirect_uri As String = \"https://www.faceboo";
_redirect_uri = "https://www.facebook.com";
 //BA.debugLineNum = 319;BA.debugLine="Dim name, caption, description, picture, link, al";
_name = "";
_caption = "";
_description = "";
_picture = "";
_link = "";
_all = "";
 //BA.debugLineNum = 322;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 323;BA.debugLine="link = \"http://www.app-ear.com.ar\"";
_link = "http://www.app-ear.com.ar";
 //BA.debugLineNum = 324;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 325;BA.debugLine="caption = \"Yo utilizo AppEAR y ayudo a la cienci";
_caption = "Yo utilizo AppEAR y ayudo a la ciencia!";
 //BA.debugLineNum = 326;BA.debugLine="If lblTitulo.Text.Contains(\"Ahora eres nivel\") T";
if (mostCurrent._lbltitulo.getText().contains("Ahora eres nivel")) { 
 //BA.debugLineNum = 327;BA.debugLine="description = \"He subido de nivel! Ahora soy ni";
_description = "He subido de nivel! Ahora soy nivel "+BA.NumberToString(_nivel)+" en AppEAR!";
 }else {
 //BA.debugLineNum = 329;BA.debugLine="description = \"Conseguí una nueva medalla!!!\"";
_description = "Conseguí una nueva medalla!!!";
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 332;BA.debugLine="link = \"http://www.app-ear.com.ar\"";
_link = "http://www.app-ear.com.ar";
 //BA.debugLineNum = 333;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 334;BA.debugLine="caption = \"I use AppEAR to help science!\"";
_caption = "I use AppEAR to help science!";
 //BA.debugLineNum = 335;BA.debugLine="If lblTitulo.Text.Contains(\"You are now level\")";
if (mostCurrent._lbltitulo.getText().contains("You are now level")) { 
 //BA.debugLineNum = 336;BA.debugLine="description = \"I've leveled up! I'm now level \"";
_description = "I've leveled up! I'm now level "+BA.NumberToString(_nivel)+" in AppEAR!";
 }else {
 //BA.debugLineNum = 338;BA.debugLine="description = \"I have a new medal!!!\"";
_description = "I have a new medal!!!";
 };
 };
 //BA.debugLineNum = 342;BA.debugLine="picture = \"http://www.app-ear.com.ar/users/badges";
_picture = "http://www.app-ear.com.ar/users/badges/"+mostCurrent._imagetoshare;
 //BA.debugLineNum = 343;BA.debugLine="all = \"https://www.facebook.com/dialog/feed?app_i";
_all = "https://www.facebook.com/dialog/feed?app_id="+_app_id+"&link="+_link+"&name="+_name+"&caption="+_caption+"&description="+_description+"&picture="+_picture+"&redirect_uri="+_redirect_uri;
 //BA.debugLineNum = 345;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 346;BA.debugLine="i.Initialize(i.ACTION_VIEW, all)";
_i.Initialize(_i.ACTION_VIEW,_all);
 //BA.debugLineNum = 347;BA.debugLine="i.SetType(\"text/plain\")";
_i.SetType("text/plain");
 //BA.debugLineNum = 348;BA.debugLine="i.PutExtra(\"android.intent.extra.TEXT\", all)";
_i.PutExtra("android.intent.extra.TEXT",(Object)(_all));
 //BA.debugLineNum = 349;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 352;BA.debugLine="Main.numshares = Main.numshares + 1";
mostCurrent._main._numshares /*String*/  = BA.NumberToString((double)(Double.parseDouble(mostCurrent._main._numshares /*String*/ ))+1);
 //BA.debugLineNum = 355;BA.debugLine="If Main.numshares = 1 Then";
if ((mostCurrent._main._numshares /*String*/ ).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 356;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 357;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-primershare.png").getObject()));
 //BA.debugLineNum = 358;BA.debugLine="imagetoshare = Main.lang & \"-primershare.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-primershare.png";
 };
 //BA.debugLineNum = 361;BA.debugLine="If Main.numshares = 10 Then";
if ((mostCurrent._main._numshares /*String*/ ).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 362;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 363;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-sociable.png").getObject()));
 //BA.debugLineNum = 364;BA.debugLine="imagetoshare = Main.lang & \"-sociable.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-sociable.png";
 };
 //BA.debugLineNum = 368;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 369;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 372;BA.debugLine="If subionivel = True Then";
if (_subionivel==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 373;BA.debugLine="CheckMedallas";
_checkmedallas();
 }else {
 //BA.debugLineNum = 375;BA.debugLine="btnVolverPerfil.Visible = True";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 379;BA.debugLine="End Sub";
return "";
}
public static String  _btnshareothers_click() throws Exception{
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
 //BA.debugLineNum = 381;BA.debugLine="Sub btnShareOthers_Click";
 //BA.debugLineNum = 382;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 383;BA.debugLine="File.Copy(File.DirAssets, imagetoshare, File.DirD";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imagetoshare,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"ShareAppEAR.png");
 //BA.debugLineNum = 384;BA.debugLine="share.sharebinary(\"file://\" & Starter.savedir & \"";
_share.sharebinary(mostCurrent.activityBA,"file://"+mostCurrent._starter._savedir /*String*/ +"/ShareAppEAR.png","image/png","Comparte con tus amigos!","Conseguí una nueva medalla!!!");
 //BA.debugLineNum = 386;BA.debugLine="Main.numshares = Main.numshares + 1";
mostCurrent._main._numshares /*String*/  = BA.NumberToString((double)(Double.parseDouble(mostCurrent._main._numshares /*String*/ ))+1);
 //BA.debugLineNum = 389;BA.debugLine="If Main.numshares = 10 Then";
if ((mostCurrent._main._numshares /*String*/ ).equals(BA.NumberToString(10))) { 
 //BA.debugLineNum = 390;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 391;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-sociable.png").getObject()));
 //BA.debugLineNum = 392;BA.debugLine="imagetoshare = Main.lang & \"-sociable.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-sociable.png";
 };
 //BA.debugLineNum = 396;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 397;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 399;BA.debugLine="If subionivel = True Then";
if (_subionivel==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 400;BA.debugLine="CheckMedallas";
_checkmedallas();
 }else {
 //BA.debugLineNum = 402;BA.debugLine="btnVolverPerfil.Visible = True";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 404;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolverperfil_click() throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub btnVolverPerfil_Click";
 //BA.debugLineNum = 95;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 96;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 97;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 99;BA.debugLine="CallSubDelayed(Form_Main, \"LoadForm_Main\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._form_main.getObject()),"LoadForm_Main");
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public static String  _checkmedallas() throws Exception{
boolean _tienemedallas = false;
int _numevalsenviadas = 0;
anywheresoftware.b4a.objects.collections.List _table = null;
 //BA.debugLineNum = 151;BA.debugLine="Sub CheckMedallas";
 //BA.debugLineNum = 155;BA.debugLine="subionivel = False";
_subionivel = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 156;BA.debugLine="Dim tienemedallas As Boolean = False";
_tienemedallas = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 158;BA.debugLine="Dim numevalsenviadas As Int";
_numevalsenviadas = 0;
 //BA.debugLineNum = 159;BA.debugLine="Dim Table As List";
_table = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 160;BA.debugLine="Table = DBUtils.ExecuteMemoryTable(Starter.sqlDB,";
_table = mostCurrent._dbutils._executememorytable /*anywheresoftware.b4a.objects.collections.List*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE usuario=?",new String[]{mostCurrent._main._strusername /*String*/ },(int) (0));
 //BA.debugLineNum = 161;BA.debugLine="If Table = Null Or Table.IsInitialized = False Th";
if (_table== null || _table.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 162;BA.debugLine="numevalsenviadas = 0";
_numevalsenviadas = (int) (0);
 }else {
 //BA.debugLineNum = 164;BA.debugLine="numevalsenviadas = Table.Size";
_numevalsenviadas = _table.getSize();
 };
 //BA.debugLineNum = 166;BA.debugLine="If numevalsenviadas < Main.numevalsok Then";
if (_numevalsenviadas<(double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))) { 
 //BA.debugLineNum = 167;BA.debugLine="numevalsenviadas = Main.numevalsok + 1";
_numevalsenviadas = (int) ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))+1);
 };
 //BA.debugLineNum = 173;BA.debugLine="If numevalsenviadas = 1 Then";
if (_numevalsenviadas==1) { 
 //BA.debugLineNum = 174;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 175;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 176;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-novato.png").getObject()));
 //BA.debugLineNum = 177;BA.debugLine="imagetoshare = Main.lang & \"-novato.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-novato.png";
 //BA.debugLineNum = 178;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 180;BA.debugLine="If numevalsenviadas = 3 Then";
if (_numevalsenviadas==3) { 
 //BA.debugLineNum = 181;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 182;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 183;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-aprendiz.png").getObject()));
 //BA.debugLineNum = 184;BA.debugLine="imagetoshare = Main.lang & \"-aprendiz.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-aprendiz.png";
 //BA.debugLineNum = 185;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 187;BA.debugLine="If numevalsenviadas = 5 Then";
if (_numevalsenviadas==5) { 
 //BA.debugLineNum = 188;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 189;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 190;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-aventurero.png").getObject()));
 //BA.debugLineNum = 191;BA.debugLine="imagetoshare = Main.lang & \"-aventurero.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-aventurero.png";
 //BA.debugLineNum = 192;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 194;BA.debugLine="If numevalsenviadas = 15 Then";
if (_numevalsenviadas==15) { 
 //BA.debugLineNum = 195;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 196;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 197;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-explorador.png").getObject()));
 //BA.debugLineNum = 198;BA.debugLine="imagetoshare = Main.lang & \"-explorador.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-explorador.png";
 //BA.debugLineNum = 199;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 201;BA.debugLine="If numevalsenviadas = 30 Then";
if (_numevalsenviadas==30) { 
 //BA.debugLineNum = 202;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 203;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 204;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-legendario.png").getObject()));
 //BA.debugLineNum = 205;BA.debugLine="imagetoshare = Main.lang & \"-legendario.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-legendario.png";
 //BA.debugLineNum = 206;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 211;BA.debugLine="If Main.numfotosok = 16 Then";
if ((mostCurrent._main._numfotosok /*String*/ ).equals(BA.NumberToString(16))) { 
 //BA.debugLineNum = 212;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 213;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 214;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-fotogenico.png").getObject()));
 //BA.debugLineNum = 215;BA.debugLine="imagetoshare = Main.lang & \"-fotogenico.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-fotogenico.png";
 //BA.debugLineNum = 216;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 218;BA.debugLine="If Main.numfotosok = 120 Then";
if ((mostCurrent._main._numfotosok /*String*/ ).equals(BA.NumberToString(120))) { 
 //BA.debugLineNum = 219;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 220;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 221;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pulitzer.png").getObject()));
 //BA.debugLineNum = 222;BA.debugLine="imagetoshare = Main.lang & \"-pulitzer.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-pulitzer.png";
 //BA.debugLineNum = 223;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 227;BA.debugLine="If tiporio = \"llanura\" Then";
if ((_tiporio).equals("llanura")) { 
 //BA.debugLineNum = 228;BA.debugLine="If Main.numriollanura = 1 Then";
if ((mostCurrent._main._numriollanura /*String*/ ).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 229;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 230;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 231;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-llanura.png").getObject()));
 //BA.debugLineNum = 232;BA.debugLine="imagetoshare = Main.lang & \"-llanura.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-llanura.png";
 //BA.debugLineNum = 233;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((_tiporio).equals("montana")) { 
 //BA.debugLineNum = 236;BA.debugLine="If Main.numriomontana = 1 Then";
if ((mostCurrent._main._numriomontana /*String*/ ).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 237;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 238;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 239;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-montana.png").getObject()));
 //BA.debugLineNum = 240;BA.debugLine="imagetoshare = Main.lang & \"-montana.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-montana.png";
 //BA.debugLineNum = 241;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((_tiporio).equals("laguna")) { 
 //BA.debugLineNum = 244;BA.debugLine="If Main.numlaguna = 1 Then";
if ((mostCurrent._main._numlaguna /*String*/ ).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 245;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 246;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 247;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-laguna.png").getObject()));
 //BA.debugLineNum = 248;BA.debugLine="imagetoshare = Main.lang & \"-laguna.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-laguna.png";
 //BA.debugLineNum = 249;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((_tiporio).equals("estuario")) { 
 //BA.debugLineNum = 252;BA.debugLine="If Main.numestuario = 1 Then";
if ((mostCurrent._main._numestuario /*String*/ ).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 253;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 254;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 255;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-estuario.png").getObject()));
 //BA.debugLineNum = 256;BA.debugLine="imagetoshare = Main.lang & \"-estuario.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-estuario.png";
 //BA.debugLineNum = 257;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 260;BA.debugLine="If Main.numlaguna = 1 And Main.numestuario = 1 An";
if ((mostCurrent._main._numlaguna /*String*/ ).equals(BA.NumberToString(1)) && (mostCurrent._main._numestuario /*String*/ ).equals(BA.NumberToString(1)) && (mostCurrent._main._numriomontana /*String*/ ).equals(BA.NumberToString(1)) && (mostCurrent._main._numriollanura /*String*/ ).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 261;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 262;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 263;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-maestrodeambientes.png").getObject()));
 //BA.debugLineNum = 264;BA.debugLine="imagetoshare = Main.lang & \"-maestrodeambientes.";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-maestrodeambientes.png";
 //BA.debugLineNum = 265;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 269;BA.debugLine="If Main.puntostotales >= 5000 And (Main.puntostot";
if ((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))>=5000 && ((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))-60-(10*_numfotosenviadas))<5000) { 
 //BA.debugLineNum = 270;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 271;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 272;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-goleador.png").getObject()));
 //BA.debugLineNum = 273;BA.debugLine="imagetoshare = Main.lang & \"-goleador.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-goleador.png";
 //BA.debugLineNum = 274;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 276;BA.debugLine="If Main.puntostotales >= 10000 And (Main.puntosto";
if ((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))>=10000 && ((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))-60-(10*_numfotosenviadas))<10000) { 
 //BA.debugLineNum = 277;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 278;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 279;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pichichi.png").getObject()));
 //BA.debugLineNum = 280;BA.debugLine="imagetoshare = Main.lang & \"-pichichi.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-pichichi.png";
 //BA.debugLineNum = 281;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 283;BA.debugLine="If Main.numevalsok >= 1 And (Main.numevalsok - 1)";
if ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))>=1 && ((double)(Double.parseDouble(mostCurrent._main._numevalsok /*String*/ ))-1)<1) { 
 //BA.debugLineNum = 284;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 285;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 286;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-primeraeval.png").getObject()));
 //BA.debugLineNum = 287;BA.debugLine="imagetoshare = Main.lang & \"-primeraeval.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-primeraeval.png";
 //BA.debugLineNum = 288;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 290;BA.debugLine="If Main.numfotosok >= 1 And (Main.numfotosok - nu";
if ((double)(Double.parseDouble(mostCurrent._main._numfotosok /*String*/ ))>=1 && ((double)(Double.parseDouble(mostCurrent._main._numfotosok /*String*/ ))-_numfotosenviadas)<1) { 
 //BA.debugLineNum = 291;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 292;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 293;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-primerafoto.png").getObject()));
 //BA.debugLineNum = 294;BA.debugLine="imagetoshare = Main.lang & \"-primerafoto.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-primerafoto.png";
 //BA.debugLineNum = 295;BA.debugLine="tienemedallas = True";
_tienemedallas = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 297;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 298;BA.debugLine="btnShare.Text = \"Share in Facebook\"";
mostCurrent._btnshare.setText(BA.ObjectToCharSequence("Share in Facebook"));
 //BA.debugLineNum = 299;BA.debugLine="btnShareOthers.Text = \"Share as...\"";
mostCurrent._btnshareothers.setText(BA.ObjectToCharSequence("Share as..."));
 //BA.debugLineNum = 300;BA.debugLine="lblNoCompartir.Text = \"Do not share\"";
mostCurrent._lblnocompartir.setText(BA.ObjectToCharSequence("Do not share"));
 //BA.debugLineNum = 301;BA.debugLine="lblTitulo.Text = \"Achievement unlocked!\"";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("Achievement unlocked!"));
 };
 //BA.debugLineNum = 304;BA.debugLine="If tienemedallas = False Then";
if (_tienemedallas==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 305;BA.debugLine="btnVolverPerfil.Visible = True";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 307;BA.debugLine="End Sub";
return "";
}
public static String  _checknivel() throws Exception{
double _nivelfull = 0;
double _nivelfullanterior = 0;
int _nivelanterior = 0;
 //BA.debugLineNum = 109;BA.debugLine="Sub CheckNivel";
 //BA.debugLineNum = 111;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 112;BA.debugLine="subionivel = False";
_subionivel = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 113;BA.debugLine="nivelfull = (Sqrt(Main.puntostotales) * 0.25)";
_nivelfull = (anywheresoftware.b4a.keywords.Common.Sqrt((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ )))*0.25);
 //BA.debugLineNum = 114;BA.debugLine="nivel = Floor(nivelfull)";
_nivel = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 116;BA.debugLine="Dim nivelfullanterior As Double";
_nivelfullanterior = 0;
 //BA.debugLineNum = 117;BA.debugLine="Dim nivelanterior As Int";
_nivelanterior = 0;
 //BA.debugLineNum = 118;BA.debugLine="nivelfullanterior = (Sqrt(Main.puntostotales - 60";
_nivelfullanterior = (anywheresoftware.b4a.keywords.Common.Sqrt((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ ))-60-(10*_numfotosenviadas))*0.25);
 //BA.debugLineNum = 119;BA.debugLine="nivelanterior = Floor(nivelfullanterior)";
_nivelanterior = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfullanterior));
 //BA.debugLineNum = 122;BA.debugLine="If nivelanterior < nivel Then";
if (_nivelanterior<_nivel) { 
 //BA.debugLineNum = 123;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 124;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 125;BA.debugLine="lblTitulo.Text = \"Ahora eres nivel \" & nivel &";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("Ahora eres nivel "+BA.NumberToString(_nivel)+"!"));
 //BA.debugLineNum = 126;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-nivel.png").getObject()));
 //BA.debugLineNum = 127;BA.debugLine="imagetoshare = Main.lang & \"-novato.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-novato.png";
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 129;BA.debugLine="lblTitulo.Text = \"You are now level \" & nivel &";
mostCurrent._lbltitulo.setText(BA.ObjectToCharSequence("You are now level "+BA.NumberToString(_nivel)+"!"));
 //BA.debugLineNum = 130;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-nivel.png").getObject()));
 //BA.debugLineNum = 131;BA.debugLine="imagetoshare = Main.lang & \"-novato.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang /*String*/ +"-novato.png";
 };
 //BA.debugLineNum = 133;BA.debugLine="btnVolverPerfil.Visible = False";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 134;BA.debugLine="subionivel = True";
_subionivel = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 138;BA.debugLine="If subionivel = False Then";
if (_subionivel==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 140;BA.debugLine="CheckMedallas";
_checkmedallas();
 };
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 425;BA.debugLine="Sub fondogris_Click";
 //BA.debugLineNum = 426;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 427;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 428;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblDatosEnviados As Label";
mostCurrent._lbldatosenviados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblpuntosenviados As Label";
mostCurrent._lblpuntosenviados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnVolverPerfil As Button";
mostCurrent._btnvolverperfil = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private imgBadge As ImageView";
mostCurrent._imgbadge = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private btnShareOthers As Button";
mostCurrent._btnshareothers = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblNoCompartir As Label";
mostCurrent._lblnocompartir = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim nivel As Int";
_nivel = 0;
 //BA.debugLineNum = 29;BA.debugLine="Dim imagetoshare As String";
mostCurrent._imagetoshare = "";
 //BA.debugLineNum = 30;BA.debugLine="Private fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private pnlShare As Panel";
mostCurrent._pnlshare = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim subionivel As Boolean";
_subionivel = false;
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _lblnocompartir_click() throws Exception{
 //BA.debugLineNum = 406;BA.debugLine="Sub lblNoCompartir_Click";
 //BA.debugLineNum = 407;BA.debugLine="pnlShare.RemoveView";
mostCurrent._pnlshare.RemoveView();
 //BA.debugLineNum = 408;BA.debugLine="fondogris.RemoveView";
mostCurrent._fondogris.RemoveView();
 //BA.debugLineNum = 411;BA.debugLine="If subionivel = True Then";
if (_subionivel==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 412;BA.debugLine="CheckMedallas";
_checkmedallas();
 }else {
 //BA.debugLineNum = 414;BA.debugLine="btnVolverPerfil.Visible = True";
mostCurrent._btnvolverperfil.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 417;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Private Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="Dim numfotosenviadas As Int";
_numfotosenviadas = 0;
 //BA.debugLineNum = 11;BA.debugLine="Dim tiporio As String";
_tiporio = "";
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static void  _timer1_tick() throws Exception{
ResumableSub_Timer1_Tick rsub = new ResumableSub_Timer1_Tick(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Timer1_Tick extends BA.ResumableSub {
public ResumableSub_Timer1_Tick(ilpla.appear.frmfelicitaciones parent) {
this.parent = parent;
}
ilpla.appear.frmfelicitaciones parent;
anywheresoftware.b4a.objects.ImageViewWrapper _image = null;
int _left = 0;
int _a = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 75;BA.debugLine="Dim image As ImageView";
_image = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 76;BA.debugLine="image.Initialize(\"\")";
_image.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 78;BA.debugLine="image.Bitmap =LoadBitmap(File.DirAssets, \"snow.pn";
_image.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"snow.png").getObject()));
 //BA.debugLineNum = 79;BA.debugLine="Dim left As Int =Rnd(0,100)";
_left = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (100));
 //BA.debugLineNum = 80;BA.debugLine="Panel1.AddView(image,(left*Panel1.Width)/100,-20%";
parent.mostCurrent._panel1.AddView((android.view.View)(_image.getObject()),(int) ((_left*parent.mostCurrent._panel1.getWidth())/(double)100),(int) (-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (20),mostCurrent.activityBA)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (16)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (16)));
 //BA.debugLineNum = 81;BA.debugLine="Dim a As Int = Rnd(0,10)";
_a = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (10));
 //BA.debugLineNum = 82;BA.debugLine="If a >5  Then";
if (true) break;

case 1:
//if
this.state = 6;
if (_a>5) { 
this.state = 3;
}else {
this.state = 5;
}if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 83;BA.debugLine="image.SendToBack";
_image.SendToBack();
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 85;BA.debugLine="image.BringToFront";
_image.BringToFront();
 if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 87;BA.debugLine="image.SetLayoutAnimated(5000,image.Left,120%y,ima";
_image.SetLayoutAnimated((int) (5000),_image.getLeft(),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (120),mostCurrent.activityBA),_image.getWidth(),_image.getHeight());
 //BA.debugLineNum = 88;BA.debugLine="image.SetVisibleAnimated(5000,False)";
_image.SetVisibleAnimated((int) (5000),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 89;BA.debugLine="Sleep(5000)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (5000));
this.state = 7;
return;
case 7:
//C
this.state = -1;
;
 //BA.debugLineNum = 90;BA.debugLine="image.RemoveView";
_image.RemoveView();
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
}
