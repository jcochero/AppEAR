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

public class frmlocalizacion extends Activity implements B4AActivity{
	public static frmlocalizacion mostCurrent;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmlocalizacion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmlocalizacion).");
				p.finish();
			}
		}
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		mostCurrent = this;
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, true))
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmlocalizacion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmlocalizacion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmlocalizacion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmlocalizacion) Resume **");
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
			processBA.raiseEvent(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return frmlocalizacion.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
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
        if (_activity == null) //workaround for emulator bug (Issue 2423)
            return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (frmlocalizacion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
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
			if (mostCurrent == null || mostCurrent != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmlocalizacion) Resume **");
		    processBA.raiseEvent(mostCurrent._activity, "activity_resume", (Object[])null);
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

public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.gps.GPS _gps1 = null;
public static String _formorigen = "";
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmapa = null;
public uk.co.martinpearman.b4a.osmdroid.views.MapView _mapview1 = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay _simplelocationoverlay1 = null;
public uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndetectar = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay _markeroverlay = null;
public static boolean _intentogps = false;
public anywheresoftware.b4a.objects.collections.List _markers = null;
public anywheresoftware.b4a.objects.collections.List _markerexport = null;
public anywheresoftware.b4a.objects.LabelWrapper _markerinfo = null;
public uk.co.martinpearman.b4a.osmdroid.Constants _constants1 = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.ViewHost _viewhost1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgdetectar = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoklocalizacion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnexport = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmprincipal _frmprincipal = null;
public ilpla.appear.frmevaluacion _frmevaluacion = null;
public ilpla.appear.utilidades _utilidades = null;
public ilpla.appear.game_ciclo _game_ciclo = null;
public ilpla.appear.game_sourcepoint _game_sourcepoint = null;
public ilpla.appear.game_comunidades _game_comunidades = null;
public ilpla.appear.game_trofica _game_trofica = null;
public ilpla.appear.aprender_tipoagua _aprender_tipoagua = null;
public ilpla.appear.frmaprender _frmaprender = null;
public ilpla.appear.frmresultados _frmresultados = null;
public ilpla.appear.frmhabitatestuario _frmhabitatestuario = null;
public ilpla.appear.game_ahorcado _game_ahorcado = null;
public ilpla.appear.aprender_factores _aprender_factores = null;
public ilpla.appear.frmminigames _frmminigames = null;
public ilpla.appear.frmhabitatrio _frmhabitatrio = null;
public ilpla.appear.frmabout _frmabout = null;
public ilpla.appear.frmhabitatlaguna _frmhabitatlaguna = null;
public ilpla.appear.frmperfil _frmperfil = null;
public ilpla.appear.envioarchivos _envioarchivos = null;
public ilpla.appear.frmcamara _frmcamara = null;
public ilpla.appear.register _register = null;
public ilpla.appear.frmtiporio _frmtiporio = null;
public ilpla.appear.game_memory _game_memory = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 38;BA.debugLine="Activity.LoadLayout(\"frmLocation\")";
mostCurrent._activity.LoadLayout("frmLocation",mostCurrent.activityBA);
 //BA.debugLineNum = 39;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 40;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 41;BA.debugLine="GPS1.Initialize(\"GPS\")";
_gps1.Initialize("GPS");
 //BA.debugLineNum = 42;BA.debugLine="If formorigen = \"Principal\" Then";
if ((_formorigen).equals("Principal")) { 
 //BA.debugLineNum = 43;BA.debugLine="GetMiMapa";
_getmimapa();
 //BA.debugLineNum = 44;BA.debugLine="btnDetectar.Visible = False";
mostCurrent._btndetectar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 45;BA.debugLine="imgDetectar.Visible = False";
mostCurrent._imgdetectar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 47;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 48;BA.debugLine="Label4.Text = \"Tus sitios anteriores\"";
mostCurrent._label4.setText((Object)("Tus sitios anteriores"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 50;BA.debugLine="Label4.Text = \"Your previous reports\"";
mostCurrent._label4.setText((Object)("Your previous reports"));
 };
 }else if((_formorigen).equals("TipoRio")) { 
 //BA.debugLineNum = 54;BA.debugLine="intentoGPS = True";
_intentogps = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 55;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 56;BA.debugLine="ProgressDialogShow(\"Buscando tu posición por";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Buscando tu posición por GPS..."+anywheresoftware.b4a.keywords.Common.CRLF+"Si deseas usar directamente el mapa, toca fuera de este cuadro");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 58;BA.debugLine="ProgressDialogShow(\"Searching your location b";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Searching your location by GPS..."+anywheresoftware.b4a.keywords.Common.CRLF+"If you wish to use the map, tap outside this box");
 };
 //BA.debugLineNum = 61;BA.debugLine="btnDetectar.Visible = True";
mostCurrent._btndetectar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 62;BA.debugLine="imgDetectar.Visible = True";
mostCurrent._imgdetectar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 63;BA.debugLine="btnExport.Visible = False";
mostCurrent._btnexport.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 64;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 65;BA.debugLine="Label4.Text = \"Ubica tu sitio en el mapa\"";
mostCurrent._label4.setText((Object)("Ubica tu sitio en el mapa"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 67;BA.debugLine="Label4.Text = \"Locate your position in the ma";
mostCurrent._label4.setText((Object)("Locate your position in the map"));
 };
 };
 };
 //BA.debugLineNum = 72;BA.debugLine="CargarMapa(formorigen)";
_cargarmapa(_formorigen);
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 76;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 77;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 78;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 124;BA.debugLine="If GPS1.IsInitialized = True And GPS1.GPSEnabled";
if (_gps1.IsInitialized()==anywheresoftware.b4a.keywords.Common.True && _gps1.getGPSEnabled()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 125;BA.debugLine="GPS1.Stop";
_gps1.Stop();
 };
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 83;BA.debugLine="If formorigen = \"Principal\" Then";
if ((_formorigen).equals("Principal")) { 
 //BA.debugLineNum = 84;BA.debugLine="GetMiMapa";
_getmimapa();
 //BA.debugLineNum = 85;BA.debugLine="btnDetectar.Visible = False";
mostCurrent._btndetectar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 86;BA.debugLine="imgDetectar.Visible = False";
mostCurrent._imgdetectar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 87;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 88;BA.debugLine="Label4.Text = \"Tus sitios anteriores\"";
mostCurrent._label4.setText((Object)("Tus sitios anteriores"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 90;BA.debugLine="Label4.Text = \"Your previous reports\"";
mostCurrent._label4.setText((Object)("Your previous reports"));
 };
 }else if((_formorigen).equals("TipoRio")) { 
 //BA.debugLineNum = 95;BA.debugLine="If GPS1.GPSEnabled = False And intentoGPS = Fal";
if (_gps1.getGPSEnabled()==anywheresoftware.b4a.keywords.Common.False && _intentogps==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 96;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 97;BA.debugLine="ToastMessageShow(\"Por favor, prende el GPS.\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Por favor, prende el GPS.",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 99;BA.debugLine="ToastMessageShow(\"Please turn on your GPS.\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Please turn on your GPS.",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 102;BA.debugLine="intentoGPS = True";
_intentogps = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 103;BA.debugLine="StartActivity(GPS1.LocationSettingsInten";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_gps1.getLocationSettingsIntent()));
 }else {
 //BA.debugLineNum = 105;BA.debugLine="GPS1.Start(0, 0) 'Listen to GPS with no";
_gps1.Start(processBA,(long) (0),(float) (0));
 };
 //BA.debugLineNum = 108;BA.debugLine="If Main.latitud <> \"\" Then";
if ((mostCurrent._main._latitud).equals("") == false) { 
 //BA.debugLineNum = 109;BA.debugLine="lblLat.Text = Main.latitud";
mostCurrent._lbllat.setText((Object)(mostCurrent._main._latitud));
 //BA.debugLineNum = 110;BA.debugLine="lblLon.Text = Main.longitud";
mostCurrent._lbllon.setText((Object)(mostCurrent._main._longitud));
 };
 //BA.debugLineNum = 112;BA.debugLine="btnDetectar.Visible = True";
mostCurrent._btndetectar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 113;BA.debugLine="imgDetectar.Visible = True";
mostCurrent._imgdetectar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 114;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 115;BA.debugLine="Label4.Text = \"Encuentra tu posición\"";
mostCurrent._label4.setText((Object)("Encuentra tu posición"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 117;BA.debugLine="Label4.Text = \"Locate yourself\"";
mostCurrent._label4.setText((Object)("Locate yourself"));
 };
 };
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _btndetectar_click() throws Exception{
 //BA.debugLineNum = 171;BA.debugLine="Sub btnDetectar_Click";
 //BA.debugLineNum = 172;BA.debugLine="If GPS1.GPSEnabled = False Then";
if (_gps1.getGPSEnabled()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 173;BA.debugLine="ToastMessageShow(\"Habilita el GPS de tu disposi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Habilita el GPS de tu dispositivo.",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 174;BA.debugLine="StartActivity (GPS1.LocationSettingsIntent)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_gps1.getLocationSettingsIntent()));
 }else {
 //BA.debugLineNum = 176;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 177;BA.debugLine="ToastMessageShow(\"Activando el GPS\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Activando el GPS",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 178;BA.debugLine="ProgressDialogShow(\"Buscando tu posición por G";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Buscando tu posición por GPS..."+anywheresoftware.b4a.keywords.Common.CRLF+"Si deseas usar directamente el mapa, toca fuera de este cuadro");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 180;BA.debugLine="ToastMessageShow(\"Activating GPS\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Activating GPS",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 181;BA.debugLine="ProgressDialogShow(\"Searching your location by";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Searching your location by GPS..."+anywheresoftware.b4a.keywords.Common.CRLF+"If you wish to use the map, tap outside this box");
 };
 //BA.debugLineNum = 184;BA.debugLine="GPS1.Start(0,0)";
_gps1.Start(processBA,(long) (0),(float) (0));
 };
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _btnexport_click() throws Exception{
String _flname = "";
anywheresoftware.b4a.objects.IntentWrapper _i = null;
 //BA.debugLineNum = 434;BA.debugLine="Sub btnExport_Click";
 //BA.debugLineNum = 435;BA.debugLine="Dim flname As String";
_flname = "";
 //BA.debugLineNum = 436;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 437;BA.debugLine="flname = DateTime.Date(DateTime.Now)";
_flname = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 439;BA.debugLine="File.WriteList(Main.savedir, \"Sitios \" & flname &";
anywheresoftware.b4a.keywords.Common.File.WriteList(mostCurrent._main._savedir,"Sitios "+_flname+" .txt",mostCurrent._markerexport);
 //BA.debugLineNum = 440;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 441;BA.debugLine="Msgbox(\"Tus puntos fueron exportados al archivo";
anywheresoftware.b4a.keywords.Common.Msgbox("Tus puntos fueron exportados al archivo "+"Sitios "+_flname+" .txt","Exportados",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 443;BA.debugLine="Msgbox(\"Your previous sites were exported to the";
anywheresoftware.b4a.keywords.Common.Msgbox("Your previous sites were exported to the file "+"Sitios "+_flname+" .txt","Exported",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 446;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 447;BA.debugLine="i.Initialize(i.ACTION_VIEW, \"file://\" & File.Co";
_i.Initialize(_i.ACTION_VIEW,"file://"+anywheresoftware.b4a.keywords.Common.File.Combine(mostCurrent._main._savedir+"/","Sitios "+_flname+" .txt"));
 //BA.debugLineNum = 448;BA.debugLine="i.SetComponent(\"android/com.android.internal.ap";
_i.SetComponent("android/com.android.internal.app.ResolverActivity");
 //BA.debugLineNum = 449;BA.debugLine="i.SetType(\"text/plain\")";
_i.SetType("text/plain");
 //BA.debugLineNum = 450;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 451;BA.debugLine="End Sub";
return "";
}
public static String  _btnoklocalizacion_click() throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Sub btnOkLocalizacion_Click";
 //BA.debugLineNum = 236;BA.debugLine="If formorigen = \"TipoRio\" Then";
if ((_formorigen).equals("TipoRio")) { 
 //BA.debugLineNum = 237;BA.debugLine="If lblLat.Text <> \"\" And lblLon.Text <> \"\" Then";
if ((mostCurrent._lbllat.getText()).equals("") == false && (mostCurrent._lbllon.getText()).equals("") == false) { 
 //BA.debugLineNum = 238;BA.debugLine="Main.latitud = lblLat.Text";
mostCurrent._main._latitud = mostCurrent._lbllat.getText();
 //BA.debugLineNum = 239;BA.debugLine="Main.longitud = lblLon.Text";
mostCurrent._main._longitud = mostCurrent._lbllon.getText();
 }else {
 //BA.debugLineNum = 241;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 242;BA.debugLine="Msgbox(\"No se han registrado coordenadas, prue";
anywheresoftware.b4a.keywords.Common.Msgbox("No se han registrado coordenadas, pruebe mover el mapa o prender el GPS","Ooops",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 244;BA.debugLine="Msgbox(\"Coordinates not registered, try moving";
anywheresoftware.b4a.keywords.Common.Msgbox("Coordinates not registered, try moving the map or turning the GPS on","Ooops",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 247;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 252;BA.debugLine="If GPS1.IsInitialized = False Then";
if (_gps1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 253;BA.debugLine="GPS1.Stop";
_gps1.Stop();
 };
 //BA.debugLineNum = 255;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 256;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 258;BA.debugLine="If formorigen = \"TipoRio\" Then";
if ((_formorigen).equals("TipoRio")) { 
 //BA.debugLineNum = 259;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 260;BA.debugLine="StartActivity(frmHabitatRio)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmhabitatrio.getObject()));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 262;BA.debugLine="StartActivity(frmHabitatRio)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmhabitatrio.getObject()));
 }else if((mostCurrent._main._tiporio).equals("Laguna")) { 
 //BA.debugLineNum = 264;BA.debugLine="StartActivity(frmHabitatLaguna)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmhabitatlaguna.getObject()));
 }else if((mostCurrent._main._tiporio).equals("Estuario")) { 
 //BA.debugLineNum = 266;BA.debugLine="StartActivity(frmHabitatEstuario)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmhabitatestuario.getObject()));
 };
 };
 //BA.debugLineNum = 270;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa(String _formori) throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub CargarMapa (formori As String)";
 //BA.debugLineNum = 145;BA.debugLine="GeoPoint1.Initialize(-34.9204950,-57.9535660)";
mostCurrent._geopoint1.Initialize(-34.9204950,-57.9535660);
 //BA.debugLineNum = 147;BA.debugLine="MapView1.Initialize(\"MapView1\")";
mostCurrent._mapview1.Initialize(mostCurrent.activityBA,"MapView1");
 //BA.debugLineNum = 148;BA.debugLine="MapView1.SetBuiltInZoomControls(True)";
mostCurrent._mapview1.SetBuiltInZoomControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 149;BA.debugLine="MapView1.SetMultiTouchControls(True)";
mostCurrent._mapview1.SetMultiTouchControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 150;BA.debugLine="MapView1.GetController.SetZoom(12)";
mostCurrent._mapview1.GetController().SetZoom((int) (12));
 //BA.debugLineNum = 151;BA.debugLine="MapView1.GetController.SetCenter(GeoPoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(mostCurrent._geopoint1.getObject()));
 //BA.debugLineNum = 152;BA.debugLine="pnlMapa.AddView(MapView1, 0,0, 100%x, 100%y)";
mostCurrent._pnlmapa.AddView((android.view.View)(mostCurrent._mapview1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 156;BA.debugLine="If formori = \"TipoRio\" Then";
if ((_formori).equals("TipoRio")) { 
 //BA.debugLineNum = 158;BA.debugLine="SimpleLocationOverlay1.Initialize";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 //BA.debugLineNum = 159;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
 //BA.debugLineNum = 160;BA.debugLine="SimpleLocationOverlay1.SetLocation(MapView1.GetM";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(mostCurrent._mapview1.GetMapCenter().getObject()));
 }else if((_formori).equals("Principal")) { 
 //BA.debugLineNum = 163;BA.debugLine="lblLat.Visible = False";
mostCurrent._lbllat.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 164;BA.debugLine="lblLon.Visible = False";
mostCurrent._lbllon.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 165;BA.debugLine="SimpleLocationOverlay1.Initialize";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 };
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _getmapa = null;
 //BA.debugLineNum = 221;BA.debugLine="Sub GetMiMapa";
 //BA.debugLineNum = 222;BA.debugLine="btnDetectar.Visible = False";
mostCurrent._btndetectar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="Dim GetMapa As HttpJob";
_getmapa = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 224;BA.debugLine="GetMapa.Initialize(\"GetMapa\", Me)";
_getmapa._initialize(processBA,"GetMapa",frmlocalizacion.getObject());
 //BA.debugLineNum = 225;BA.debugLine="GetMapa.Download2(\"http://www.app-ear.com.ar/conn";
_getmapa._download2("http://www.app-ear.com.ar/connect/getmimapa.php",new String[]{"user_id",mostCurrent._main._struserid});
 //BA.debugLineNum = 226;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 227;BA.debugLine="ProgressDialogShow(\"Buscando mi mapa...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Buscando mi mapa...");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 229;BA.debugLine="ProgressDialogShow(\"Retrieving your map...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Retrieving your map...");
 };
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Dim lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Dim MapView1 As OSMDroid_MapView";
mostCurrent._mapview1 = new uk.co.martinpearman.b4a.osmdroid.views.MapView();
 //BA.debugLineNum = 17;BA.debugLine="Dim SimpleLocationOverlay1 As OSMDroid_SimpleLoca";
mostCurrent._simplelocationoverlay1 = new uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay();
 //BA.debugLineNum = 18;BA.debugLine="Dim GeoPoint1 As OSMDroid_GeoPoint";
mostCurrent._geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
 //BA.debugLineNum = 19;BA.debugLine="Private btnDetectar As Button";
mostCurrent._btndetectar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim markeroverlay As OSMDroid_MarkerOverlay";
mostCurrent._markeroverlay = new uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay();
 //BA.debugLineNum = 21;BA.debugLine="Dim intentoGPS As Boolean";
_intentogps = false;
 //BA.debugLineNum = 23;BA.debugLine="Dim Markers As List";
mostCurrent._markers = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 24;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 27;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim Constants1 As OSMDroid_Constants";
mostCurrent._constants1 = new uk.co.martinpearman.b4a.osmdroid.Constants();
 //BA.debugLineNum = 29;BA.debugLine="Dim ViewHost1 As OSMDroid_ViewHost";
mostCurrent._viewhost1 = new uk.co.martinpearman.b4a.osmdroid.views.overlay.ViewHost();
 //BA.debugLineNum = 30;BA.debugLine="Private imgDetectar As ImageView";
mostCurrent._imgdetectar = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private btnOkLocalizacion As Button";
mostCurrent._btnoklocalizacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnExport As Button";
mostCurrent._btnexport = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _gps_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub GPS_LocationChanged (Location1 As Location)";
 //BA.debugLineNum = 191;BA.debugLine="lblLat.Text = Location1.Latitude";
mostCurrent._lbllat.setText((Object)(_location1.getLatitude()));
 //BA.debugLineNum = 192;BA.debugLine="lblLon.Text = Location1.Longitude";
mostCurrent._lbllon.setText((Object)(_location1.getLongitude()));
 //BA.debugLineNum = 193;BA.debugLine="GeoPoint1.Initialize(Location1.Latitude,Location1";
mostCurrent._geopoint1.Initialize(_location1.getLatitude(),_location1.getLongitude());
 //BA.debugLineNum = 194;BA.debugLine="SimpleLocationOverlay1.SetLocation(GeoPoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(mostCurrent._geopoint1.getObject()));
 //BA.debugLineNum = 195;BA.debugLine="MapView1.GetController.SetCenter(GeoPoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(mostCurrent._geopoint1.getObject()));
 //BA.debugLineNum = 196;BA.debugLine="MapView1.GetController.SetZoom(14)";
mostCurrent._mapview1.GetController().SetZoom((int) (14));
 //BA.debugLineNum = 198;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 199;BA.debugLine="ToastMessageShow(\"Posición encontrada!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Posición encontrada!",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 201;BA.debugLine="ToastMessageShow(\"Location found!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Location found!",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 203;BA.debugLine="GPS1.Stop";
_gps1.Stop();
 //BA.debugLineNum = 204;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 206;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _nombresitio = "";
double _sitiolat = 0;
double _sitiolong = 0;
String _sitioindice = "";
String _sitiotiporio = "";
String _sitioprecision = "";
String _sitiovalorind1 = "";
String _sitiovalorind2 = "";
String _sitiovalorind3 = "";
String _sitiovalorind4 = "";
String _sitiovalorind5 = "";
String _sitiovalorind6 = "";
String _sitiovalorind7 = "";
String _sitiovalorind8 = "";
String _sitiovalorind9 = "";
String _sitiovalorind10 = "";
String _sitiovalorind11 = "";
String _sitiovalorind12 = "";
String _sitiovalorind13 = "";
String _sitiovalorind14 = "";
String _sitiovalorind15 = "";
String _sitiovalorind16 = "";
String _sitiofoto1path = "";
String _sitiofoto2path = "";
String _sitiofoto3path = "";
String _sitiofoto4path = "";
String _sitioverificado = "";
String _markerexportstr = "";
String _estadositiomarker = "";
uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _marker = null;
uk.co.martinpearman.b4a.osmdroid.views.overlay.ViewHostOverlay _viewhostoverlay1 = null;
 //BA.debugLineNum = 272;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 273;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 274;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 275;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 276;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 277;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring();
 //BA.debugLineNum = 278;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 279;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 280;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 281;BA.debugLine="If Job.JobName = \"GetMapa\" Then";
if ((_job._jobname).equals("GetMapa")) { 
 //BA.debugLineNum = 282;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 283;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 284;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No encuentro tus sitios anteriores, prueba luego",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 286;BA.debugLine="ToastMessageShow(\"Previous reports not found";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Previous reports not found, try again later",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 289;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 290;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No encuentro tus sitios anteriores, prueba luego",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 292;BA.debugLine="ToastMessageShow(\"Previous reports not found";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Previous reports not found, try again later",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("GetMapaOk")) { 
 //BA.debugLineNum = 296;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 297;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 298;BA.debugLine="markeroverlay.Initialize(\"markeroverlay\", Map";
mostCurrent._markeroverlay.Initialize(processBA,"markeroverlay",(org.osmdroid.views.MapView)(mostCurrent._mapview1.getObject()));
 //BA.debugLineNum = 299;BA.debugLine="MapView1.GetOverlays.Add(markeroverlay)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._markeroverlay.getObject()));
 //BA.debugLineNum = 300;BA.debugLine="If numresults = 0 Then";
if ((_numresults).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 301;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 302;BA.debugLine="ToastMessageShow(\"Aún no has enviado ningun";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Aún no has enviado ninguna evaluación. Cuando lo hagas, aparecerá en este mapa!",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 304;BA.debugLine="ToastMessageShow(\"You haven't sent any repo";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You haven't sent any reports yet, when you do, they will show in this map!",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 306;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 308;BA.debugLine="MarkerExport.Initialize()";
mostCurrent._markerexport.Initialize();
 //BA.debugLineNum = 310;BA.debugLine="Markers.Initialize()";
mostCurrent._markers.Initialize();
 //BA.debugLineNum = 311;BA.debugLine="For i = 0 To numresults - 1";
{
final int step37 = 1;
final int limit37 = (int) ((double)(Double.parseDouble(_numresults))-1);
for (_i = (int) (0) ; (step37 > 0 && _i <= limit37) || (step37 < 0 && _i >= limit37); _i = ((int)(0 + _i + step37)) ) {
 //BA.debugLineNum = 312;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 313;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 314;BA.debugLine="Dim nombresitio As String = newpunto.Get(\"no";
_nombresitio = BA.ObjectToString(_newpunto.Get((Object)("nombresitio")));
 //BA.debugLineNum = 315;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 316;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\"";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 317;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"in";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 318;BA.debugLine="Dim sitiotiporio As String = newpunto.Get(\"t";
_sitiotiporio = BA.ObjectToString(_newpunto.Get((Object)("tiporio")));
 //BA.debugLineNum = 319;BA.debugLine="Dim sitioprecision As String = newpunto.Get(";
_sitioprecision = BA.ObjectToString(_newpunto.Get((Object)("precision")));
 //BA.debugLineNum = 320;BA.debugLine="Dim sitiovalorind1 As String = newpunto.Get(";
_sitiovalorind1 = BA.ObjectToString(_newpunto.Get((Object)("valorind1")));
 //BA.debugLineNum = 321;BA.debugLine="Dim sitiovalorind2 As String = newpunto.Get(";
_sitiovalorind2 = BA.ObjectToString(_newpunto.Get((Object)("valorind2")));
 //BA.debugLineNum = 322;BA.debugLine="Dim sitiovalorind3 As String = newpunto.Get(";
_sitiovalorind3 = BA.ObjectToString(_newpunto.Get((Object)("valorind3")));
 //BA.debugLineNum = 323;BA.debugLine="Dim sitiovalorind4 As String = newpunto.Get(";
_sitiovalorind4 = BA.ObjectToString(_newpunto.Get((Object)("valorind4")));
 //BA.debugLineNum = 324;BA.debugLine="Dim sitiovalorind5 As String = newpunto.Get(";
_sitiovalorind5 = BA.ObjectToString(_newpunto.Get((Object)("valorind5")));
 //BA.debugLineNum = 325;BA.debugLine="Dim sitiovalorind6 As String = newpunto.Get(";
_sitiovalorind6 = BA.ObjectToString(_newpunto.Get((Object)("valorind6")));
 //BA.debugLineNum = 326;BA.debugLine="Dim sitiovalorind7 As String = newpunto.Get(";
_sitiovalorind7 = BA.ObjectToString(_newpunto.Get((Object)("valorind7")));
 //BA.debugLineNum = 327;BA.debugLine="Dim sitiovalorind8 As String = newpunto.Get(";
_sitiovalorind8 = BA.ObjectToString(_newpunto.Get((Object)("valorind8")));
 //BA.debugLineNum = 328;BA.debugLine="Dim sitiovalorind9 As String = newpunto.Get(";
_sitiovalorind9 = BA.ObjectToString(_newpunto.Get((Object)("valorind9")));
 //BA.debugLineNum = 329;BA.debugLine="Dim sitiovalorind10 As String = newpunto.Get";
_sitiovalorind10 = BA.ObjectToString(_newpunto.Get((Object)("valorind10")));
 //BA.debugLineNum = 330;BA.debugLine="Dim sitiovalorind11 As String = newpunto.Get";
_sitiovalorind11 = BA.ObjectToString(_newpunto.Get((Object)("valorind11")));
 //BA.debugLineNum = 331;BA.debugLine="Dim sitiovalorind12 As String = newpunto.Get";
_sitiovalorind12 = BA.ObjectToString(_newpunto.Get((Object)("valorind12")));
 //BA.debugLineNum = 332;BA.debugLine="Dim sitiovalorind13 As String = newpunto.Get";
_sitiovalorind13 = BA.ObjectToString(_newpunto.Get((Object)("valorind13")));
 //BA.debugLineNum = 333;BA.debugLine="Dim sitiovalorind14 As String = newpunto.Get";
_sitiovalorind14 = BA.ObjectToString(_newpunto.Get((Object)("valorind14")));
 //BA.debugLineNum = 334;BA.debugLine="Dim sitiovalorind15 As String = newpunto.Get";
_sitiovalorind15 = BA.ObjectToString(_newpunto.Get((Object)("valorind15")));
 //BA.debugLineNum = 335;BA.debugLine="Dim sitiovalorind16 As String = newpunto.Get";
_sitiovalorind16 = BA.ObjectToString(_newpunto.Get((Object)("valorind16")));
 //BA.debugLineNum = 336;BA.debugLine="Dim sitiofoto1path As String = newpunto.Get(";
_sitiofoto1path = BA.ObjectToString(_newpunto.Get((Object)("foto1path")));
 //BA.debugLineNum = 337;BA.debugLine="Dim sitiofoto2path As String = newpunto.Get(";
_sitiofoto2path = BA.ObjectToString(_newpunto.Get((Object)("foto2path")));
 //BA.debugLineNum = 338;BA.debugLine="Dim sitiofoto3path As String = newpunto.Get(";
_sitiofoto3path = BA.ObjectToString(_newpunto.Get((Object)("foto3path")));
 //BA.debugLineNum = 339;BA.debugLine="Dim sitiofoto4path As String = newpunto.Get(";
_sitiofoto4path = BA.ObjectToString(_newpunto.Get((Object)("foto4path")));
 //BA.debugLineNum = 340;BA.debugLine="Dim sitioverificado As String = newpunto.Get";
_sitioverificado = BA.ObjectToString(_newpunto.Get((Object)("validado")));
 //BA.debugLineNum = 342;BA.debugLine="Dim MarkerExportStr As String";
_markerexportstr = "";
 //BA.debugLineNum = 343;BA.debugLine="MarkerExportStr = nombresitio & \",\" & sitiol";
_markerexportstr = _nombresitio+","+BA.NumberToString(_sitiolat)+","+BA.NumberToString(_sitiolong)+","+_sitioindice+","+_sitiotiporio;
 //BA.debugLineNum = 344;BA.debugLine="MarkerExport.Add(MarkerExportStr)";
mostCurrent._markerexport.Add((Object)(_markerexportstr));
 //BA.debugLineNum = 346;BA.debugLine="Dim estadositiomarker As String";
_estadositiomarker = "";
 //BA.debugLineNum = 347;BA.debugLine="If sitioindice < 20 Then";
if ((double)(Double.parseDouble(_sitioindice))<20) { 
 //BA.debugLineNum = 348;BA.debugLine="estadositiomarker = \"Muy malo\"";
_estadositiomarker = "Muy malo";
 }else if((double)(Double.parseDouble(_sitioindice))>=20 && (double)(Double.parseDouble(_sitioindice))<40) { 
 //BA.debugLineNum = 350;BA.debugLine="estadositiomarker = \"Malo\"";
_estadositiomarker = "Malo";
 }else if((double)(Double.parseDouble(_sitioindice))>=40 && (double)(Double.parseDouble(_sitioindice))<60) { 
 //BA.debugLineNum = 352;BA.debugLine="estadositiomarker = \"Regular\"";
_estadositiomarker = "Regular";
 }else if((double)(Double.parseDouble(_sitioindice))>=60 && (double)(Double.parseDouble(_sitioindice))<80) { 
 //BA.debugLineNum = 354;BA.debugLine="estadositiomarker = \"Bueno\"";
_estadositiomarker = "Bueno";
 }else if((double)(Double.parseDouble(_sitioindice))>=80) { 
 //BA.debugLineNum = 356;BA.debugLine="estadositiomarker = \"Muy bueno!\"";
_estadositiomarker = "Muy bueno!";
 };
 //BA.debugLineNum = 359;BA.debugLine="Dim Marker As OSMDroid_Marker";
_marker = new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker();
 //BA.debugLineNum = 360;BA.debugLine="Marker.Initialize(nombresitio, estadositio";
_marker.Initialize(_nombresitio,_estadositiomarker,_sitiolat,_sitiolong);
 //BA.debugLineNum = 361;BA.debugLine="Markers.Add(Marker)";
mostCurrent._markers.Add((Object)(_marker.getObject()));
 }
};
 //BA.debugLineNum = 364;BA.debugLine="markeroverlay.AddItems(Markers)";
mostCurrent._markeroverlay.AddItems(mostCurrent._markers);
 //BA.debugLineNum = 365;BA.debugLine="MapView1.ZoomToBoundingBox(markeroverlay.GetB";
mostCurrent._mapview1.ZoomToBoundingBox((org.osmdroid.util.BoundingBoxE6)(mostCurrent._markeroverlay.GetBoundingBox().getObject()));
 //BA.debugLineNum = 367;BA.debugLine="If Markers.Size > 0 Then";
if (mostCurrent._markers.getSize()>0) { 
 //BA.debugLineNum = 368;BA.debugLine="btnExport.Visible = True";
mostCurrent._btnexport.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 372;BA.debugLine="Dim ViewHostOverlay1 As OSMDroid_ViewHostOver";
_viewhostoverlay1 = new uk.co.martinpearman.b4a.osmdroid.views.overlay.ViewHostOverlay();
 //BA.debugLineNum = 373;BA.debugLine="ViewHostOverlay1.Initialize(MapView1)";
_viewhostoverlay1.Initialize(processBA,(org.osmdroid.views.MapView)(mostCurrent._mapview1.getObject()));
 //BA.debugLineNum = 375;BA.debugLine="MarkerInfo.Initialize(\"MarkerInfo\")";
mostCurrent._markerinfo.Initialize(mostCurrent.activityBA,"MarkerInfo");
 //BA.debugLineNum = 376;BA.debugLine="MarkerInfo.Color=Colors.White";
mostCurrent._markerinfo.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 377;BA.debugLine="MarkerInfo.TextColor=Colors.DarkGray";
mostCurrent._markerinfo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 378;BA.debugLine="MarkerInfo.TextSize=26";
mostCurrent._markerinfo.setTextSize((float) (26));
 //BA.debugLineNum = 379;BA.debugLine="ViewHost1.Initialize(MarkerInfo, Null, 0, 0,";
mostCurrent._viewhost1.Initialize(mostCurrent.activityBA,(android.view.View)(mostCurrent._markerinfo.getObject()),(org.osmdroid.util.GeoPoint)(anywheresoftware.b4a.keywords.Common.Null),(int) (0),(int) (0),mostCurrent._constants1.LayoutParams.ALIGN_TOP_CENTER,anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 380;BA.debugLine="ViewHostOverlay1.AddItem(ViewHost1)";
_viewhostoverlay1.AddItem((uk.co.martinpearman.osmdroid.views.overlay.ViewHost)(mostCurrent._viewhost1.getObject()));
 };
 };
 }else {
 //BA.debugLineNum = 385;BA.debugLine="If Job.JobName = \"Found\" Then";
if ((_job._jobname).equals("Found")) { 
 //BA.debugLineNum = 386;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 387;BA.debugLine="Msgbox(\"No tienes conexión a Internet. AppEAR";
anywheresoftware.b4a.keywords.Common.Msgbox("No tienes conexión a Internet. AppEAR iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes!","Advertencia",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 389;BA.debugLine="Msgbox(\"You have no internet connection. You w";
anywheresoftware.b4a.keywords.Common.Msgbox("You have no internet connection. You won't be able to load your reports until you connect!","Warning",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 391;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else {
 //BA.debugLineNum = 393;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: "+_job._errormessage,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 394;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 395;BA.debugLine="Msgbox(\"Compruebe su conexión a Internet!\", \"O";
anywheresoftware.b4a.keywords.Common.Msgbox("Compruebe su conexión a Internet!","Oops!",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 397;BA.debugLine="Msgbox(\"Check your internet connection!\", \"Oop";
anywheresoftware.b4a.keywords.Common.Msgbox("Check your internet connection!","Oops!",mostCurrent.activityBA);
 };
 };
 };
 //BA.debugLineNum = 402;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 403;BA.debugLine="End Sub";
return "";
}
public static String  _mapview1_centerchanged() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Sub MapView1_CenterChanged";
 //BA.debugLineNum = 210;BA.debugLine="SimpleLocationOverlay1.SetLocation(MapView1.GetMa";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(mostCurrent._mapview1.GetMapCenter().getObject()));
 //BA.debugLineNum = 211;BA.debugLine="Main.latitud = MapView1.GetMapCenter.Latitude";
mostCurrent._main._latitud = BA.NumberToString(mostCurrent._mapview1.GetMapCenter().getLatitude());
 //BA.debugLineNum = 212;BA.debugLine="Main.longitud = MapView1.GetMapCenter.Longitude";
mostCurrent._main._longitud = BA.NumberToString(mostCurrent._mapview1.GetMapCenter().getLongitude());
 //BA.debugLineNum = 213;BA.debugLine="lblLat.Text = Main.latitud";
mostCurrent._lbllat.setText((Object)(mostCurrent._main._latitud));
 //BA.debugLineNum = 214;BA.debugLine="lblLon.Text = Main.longitud";
mostCurrent._lbllon.setText((Object)(mostCurrent._main._longitud));
 //BA.debugLineNum = 215;BA.debugLine="If GPS1.IsInitialized = True Then";
if (_gps1.IsInitialized()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 216;BA.debugLine="GPS1.Stop";
_gps1.Stop();
 };
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public static boolean  _markeroverlay_click(uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _marker1) throws Exception{
 //BA.debugLineNum = 406;BA.debugLine="Sub markeroverlay_Click(Marker1 As OSMDroid_Marker";
 //BA.debugLineNum = 407;BA.debugLine="Log(\"MarkerOverlay1_Click\")";
anywheresoftware.b4a.keywords.Common.Log("MarkerOverlay1_Click");
 //BA.debugLineNum = 408;BA.debugLine="If Marker1.GetSnippet = \"Muy malo\" Then";
if ((_marker1.GetSnippet()).equals("Muy malo")) { 
 //BA.debugLineNum = 409;BA.debugLine="MarkerInfo.Color = Colors.ARGB(255,139,0,0)";
mostCurrent._markerinfo.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (139),(int) (0),(int) (0)));
 }else if((_marker1.GetSnippet()).equals("Malo")) { 
 //BA.debugLineNum = 411;BA.debugLine="MarkerInfo.Color = Colors.ARGB(255,255,69,0)";
mostCurrent._markerinfo.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (69),(int) (0)));
 }else if((_marker1.GetSnippet()).equals("Regular")) { 
 //BA.debugLineNum = 413;BA.debugLine="MarkerInfo.Color = Colors.ARGB(255,204,204,0)";
mostCurrent._markerinfo.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (204),(int) (204),(int) (0)));
 }else if((_marker1.GetSnippet()).equals("Bueno")) { 
 //BA.debugLineNum = 415;BA.debugLine="MarkerInfo.Color = Colors.ARGB(255,34,139,34)";
mostCurrent._markerinfo.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (34),(int) (139),(int) (34)));
 }else if((_marker1.GetSnippet()).equals("Muy bueno!")) { 
 //BA.debugLineNum = 417;BA.debugLine="MarkerInfo.Color = Colors.ARGB(255,70,130,180)";
mostCurrent._markerinfo.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (70),(int) (130),(int) (180)));
 };
 //BA.debugLineNum = 419;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 420;BA.debugLine="MarkerInfo.Text=Marker1.GetTitle& CRLF & \"Estado";
mostCurrent._markerinfo.setText((Object)(_marker1.GetTitle()+anywheresoftware.b4a.keywords.Common.CRLF+"Estado del hábitat: "+_marker1.GetSnippet()));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 422;BA.debugLine="MarkerInfo.Text=Marker1.GetTitle& CRLF & \"Habita";
mostCurrent._markerinfo.setText((Object)(_marker1.GetTitle()+anywheresoftware.b4a.keywords.Common.CRLF+"Habitat status: "+_marker1.GetSnippet()));
 };
 //BA.debugLineNum = 424;BA.debugLine="ViewHost1.SetPosition(Marker1.GetPoint)";
mostCurrent._viewhost1.SetPosition((org.osmdroid.util.GeoPoint)(_marker1.GetPoint().getObject()));
 //BA.debugLineNum = 425;BA.debugLine="If Not(ViewHost1.Visible) Then";
if (anywheresoftware.b4a.keywords.Common.Not(mostCurrent._viewhost1.getVisible())) { 
 //BA.debugLineNum = 426;BA.debugLine="ViewHost1.Visible=True";
mostCurrent._viewhost1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 428;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 429;BA.debugLine="End Sub";
return false;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim GPS1 As GPS";
_gps1 = new anywheresoftware.b4a.gps.GPS();
 //BA.debugLineNum = 8;BA.debugLine="Dim formorigen As String";
_formorigen = "";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 137;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 138;BA.debugLine="Label4.Text = \"Locate your position\"";
mostCurrent._label4.setText((Object)("Locate your position"));
 //BA.debugLineNum = 139;BA.debugLine="btnDetectar.Text = \"Detect it automatically\"";
mostCurrent._btndetectar.setText((Object)("Detect it automatically"));
 //BA.debugLineNum = 140;BA.debugLine="btnOkLocalizacion.Text = \"Continue\"";
mostCurrent._btnoklocalizacion.setText((Object)("Continue"));
 };
 //BA.debugLineNum = 142;BA.debugLine="End Sub";
return "";
}
}
