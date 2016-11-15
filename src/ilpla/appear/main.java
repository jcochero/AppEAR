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

public class main extends Activity implements B4AActivity{
	public static main mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (main).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (main) Resume **");
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
		return main.class;
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
        BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (main) Resume **");
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
public static double _valorcalidad = 0;
public static String _valoresindstring = "";
public static String _valorind1 = "";
public static String _valorind2 = "";
public static String _valorind3 = "";
public static String _valorind4 = "";
public static String _valorind5 = "";
public static String _valorind6 = "";
public static String _valorind7 = "";
public static String _valorind8 = "";
public static String _valorind9 = "";
public static String _valorind10 = "";
public static String _valorind11 = "";
public static String _valorind12 = "";
public static String _valorind13 = "";
public static String _valorind14 = "";
public static String _valorind15 = "";
public static String _valorind16 = "";
public static String _dateandtime = "";
public static int _preguntanumero = 0;
public static int _valorns = 0;
public static int _idproyecto = 0;
public static String _tiporio = "";
public static String _longitud = "";
public static String _latitud = "";
public static String _nombrerio = "";
public static String _fotopath0 = "";
public static String _fotopath1 = "";
public static String _fotopath2 = "";
public static String _fotopath3 = "";
public static String _evaluacionpath = "";
public static String _struserid = "";
public static String _strusername = "";
public static String _struserlocation = "";
public static String _struseremail = "";
public static String _struserorg = "";
public static String _strusertipousuario = "";
public static int _numfotosok = 0;
public static int _numevalsok = 0;
public static String _username = "";
public static boolean _modooffline = false;
public static String _pass = "";
public static int _puntostotales = 0;
public static int _puntosnumfotos = 0;
public static int _puntosnumevals = 0;
public static int _numriollanura = 0;
public static int _numriomontana = 0;
public static int _numlaguna = 0;
public static int _numestuario = 0;
public static int _numshares = 0;
public static anywheresoftware.b4a.phone.Phone _k = null;
public static String _proyectoenviar = "";
public static String _savedir = "";
public static String _versionactual = "";
public static String _serverversion = "";
public static String _servernewstitulo = "";
public static String _servernewstext = "";
public static String _msjprivadouser = "";
public static boolean _actionbarenabled = false;
public static String _lang = "";
public static String _firstuse = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblappear = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlogin = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnregister = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtuserid = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmessage = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlrecoverpass = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtrecoverpass = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnforgot = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbload = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsendforgot = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butcancel = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
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
public ilpla.appear.frmlocalizacion _frmlocalizacion = null;
public ilpla.appear.frmtiporio _frmtiporio = null;
public ilpla.appear.game_memory _game_memory = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (frmprincipal.mostCurrent != null);
vis = vis | (frmevaluacion.mostCurrent != null);
vis = vis | (game_ciclo.mostCurrent != null);
vis = vis | (game_sourcepoint.mostCurrent != null);
vis = vis | (game_comunidades.mostCurrent != null);
vis = vis | (game_trofica.mostCurrent != null);
vis = vis | (aprender_tipoagua.mostCurrent != null);
vis = vis | (frmaprender.mostCurrent != null);
vis = vis | (frmresultados.mostCurrent != null);
vis = vis | (frmhabitatestuario.mostCurrent != null);
vis = vis | (game_ahorcado.mostCurrent != null);
vis = vis | (aprender_factores.mostCurrent != null);
vis = vis | (frmminigames.mostCurrent != null);
vis = vis | (frmhabitatrio.mostCurrent != null);
vis = vis | (frmabout.mostCurrent != null);
vis = vis | (frmhabitatlaguna.mostCurrent != null);
vis = vis | (frmperfil.mostCurrent != null);
vis = vis | (envioarchivos.mostCurrent != null);
vis = vis | (frmcamara.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
vis = vis | (frmlocalizacion.mostCurrent != null);
vis = vis | (frmtiporio.mostCurrent != null);
vis = vis | (game_memory.mostCurrent != null);
return vis;}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 123;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 127;BA.debugLine="Activity.LoadLayout(\"layLoad\")";
mostCurrent._activity.LoadLayout("layLoad",mostCurrent.activityBA);
 //BA.debugLineNum = 128;BA.debugLine="TestConnection";
_testconnection();
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 133;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 134;BA.debugLine="If Msgbox2(\"Salir de la aplicación?\", \"SAL";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Salir de la aplicación?","SALIR","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 135;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 136;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 138;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 147;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 143;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static String  _btnen_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 642;BA.debugLine="Sub btnEn_Click";
 //BA.debugLineNum = 643;BA.debugLine="If lang = \"es\" Or lang = \"\" Then";
if ((_lang).equals("es") || (_lang).equals("")) { 
 //BA.debugLineNum = 644;BA.debugLine="lang = \"en\"";
_lang = "en";
 //BA.debugLineNum = 645;BA.debugLine="If File.Exists(File.DirInternal, \"config.txt\") =";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 646;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 647;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 648;BA.debugLine="Map1.Put(\"strUserID\", strUserID)";
_map1.Put((Object)("strUserID"),(Object)(_struserid));
 //BA.debugLineNum = 649;BA.debugLine="Map1.Put(\"username\", username)";
_map1.Put((Object)("username"),(Object)(_username));
 //BA.debugLineNum = 650;BA.debugLine="Map1.Put(\"msjprivadouser\", msjprivadouser)";
_map1.Put((Object)("msjprivadouser"),(Object)(_msjprivadouser));
 //BA.debugLineNum = 651;BA.debugLine="Map1.Put(\"pass\", pass)";
_map1.Put((Object)("pass"),(Object)(_pass));
 //BA.debugLineNum = 652;BA.debugLine="Map1.Put(\"lang\", lang)";
_map1.Put((Object)("lang"),(Object)(_lang));
 //BA.debugLineNum = 653;BA.debugLine="Map1.Put(\"firstuse\", firstuse)";
_map1.Put((Object)("firstuse"),(Object)(_firstuse));
 //BA.debugLineNum = 654;BA.debugLine="File.WriteMap(File.DirInternal, \"config.txt\"";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
 };
 //BA.debugLineNum = 656;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 657;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 };
 //BA.debugLineNum = 659;BA.debugLine="End Sub";
return "";
}
public static String  _btnes_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 622;BA.debugLine="Sub btnEs_Click";
 //BA.debugLineNum = 623;BA.debugLine="If lang = \"en\" Then";
if ((_lang).equals("en")) { 
 //BA.debugLineNum = 624;BA.debugLine="lang = \"es\"";
_lang = "es";
 //BA.debugLineNum = 625;BA.debugLine="If File.Exists(File.DirInternal, \"config.txt\") =";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 626;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 627;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 628;BA.debugLine="Map1.Put(\"strUserID\", strUserID)";
_map1.Put((Object)("strUserID"),(Object)(_struserid));
 //BA.debugLineNum = 629;BA.debugLine="Map1.Put(\"username\", username)";
_map1.Put((Object)("username"),(Object)(_username));
 //BA.debugLineNum = 630;BA.debugLine="Map1.Put(\"msjprivadouser\", msjprivadouser)";
_map1.Put((Object)("msjprivadouser"),(Object)(_msjprivadouser));
 //BA.debugLineNum = 631;BA.debugLine="Map1.Put(\"pass\", pass)";
_map1.Put((Object)("pass"),(Object)(_pass));
 //BA.debugLineNum = 632;BA.debugLine="Map1.Put(\"lang\", lang)";
_map1.Put((Object)("lang"),(Object)(_lang));
 //BA.debugLineNum = 633;BA.debugLine="Map1.Put(\"firstuse\", firstuse)";
_map1.Put((Object)("firstuse"),(Object)(_firstuse));
 //BA.debugLineNum = 634;BA.debugLine="File.WriteMap(File.DirInternal, \"config.txt\"";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
 };
 //BA.debugLineNum = 637;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 638;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 };
 //BA.debugLineNum = 640;BA.debugLine="End Sub";
return "";
}
public static String  _btnforgot_click() throws Exception{
 //BA.debugLineNum = 585;BA.debugLine="Sub btnForgot_Click";
 //BA.debugLineNum = 586;BA.debugLine="pnlRecoverPass.Visible = True";
mostCurrent._pnlrecoverpass.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 587;BA.debugLine="pnlRecoverPass.BringToFront";
mostCurrent._pnlrecoverpass.BringToFront();
 //BA.debugLineNum = 588;BA.debugLine="If lang = \"en\" Then";
if ((_lang).equals("en")) { 
 //BA.debugLineNum = 589;BA.debugLine="Label2.Text = \"Forgot your password?\"";
mostCurrent._label2.setText((Object)("Forgot your password?"));
 //BA.debugLineNum = 590;BA.debugLine="Label3.Text = \"Enter your email:\"";
mostCurrent._label3.setText((Object)("Enter your email:"));
 //BA.debugLineNum = 591;BA.debugLine="btnSendForgot.Text = \"Recover password\"";
mostCurrent._btnsendforgot.setText((Object)("Recover password"));
 //BA.debugLineNum = 592;BA.debugLine="butCancel.Text = \"Cancel\"";
mostCurrent._butcancel.setText((Object)("Cancel"));
 };
 //BA.debugLineNum = 594;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogin_click() throws Exception{
String _strpassword = "";
anywheresoftware.b4a.samples.httputils2.httpjob _login2 = null;
 //BA.debugLineNum = 359;BA.debugLine="Sub btnLogin_Click";
 //BA.debugLineNum = 360;BA.debugLine="k.HideKeyboard(Activity)";
_k.HideKeyboard(mostCurrent._activity);
 //BA.debugLineNum = 362;BA.debugLine="strUserID = txtUserID.Text.Trim";
_struserid = mostCurrent._txtuserid.getText().trim();
 //BA.debugLineNum = 363;BA.debugLine="If strUserID = \"\" Then";
if ((_struserid).equals("")) { 
 //BA.debugLineNum = 364;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 365;BA.debugLine="Msgbox(\"Ingrese su usuario\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Ingrese su usuario","Error",mostCurrent.activityBA);
 //BA.debugLineNum = 366;BA.debugLine="Return";
if (true) return "";
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 368;BA.debugLine="Msgbox(\"Enter your username\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Enter your username","Error",mostCurrent.activityBA);
 //BA.debugLineNum = 369;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 372;BA.debugLine="Dim strPassword As String = txtPassword.Text.Trim";
_strpassword = mostCurrent._txtpassword.getText().trim();
 //BA.debugLineNum = 373;BA.debugLine="If strPassword = \"\" Then";
if ((_strpassword).equals("")) { 
 //BA.debugLineNum = 374;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 375;BA.debugLine="Msgbox(\"Ingrese su clave\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Ingrese su clave","Error",mostCurrent.activityBA);
 //BA.debugLineNum = 376;BA.debugLine="Return";
if (true) return "";
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 378;BA.debugLine="Msgbox(\"Enter your password\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Enter your password","Error",mostCurrent.activityBA);
 //BA.debugLineNum = 379;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 384;BA.debugLine="Dim Login2 As HttpJob";
_login2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 385;BA.debugLine="Login2.Initialize(\"Login\", Me)";
_login2._initialize(processBA,"Login",main.getObject());
 //BA.debugLineNum = 386;BA.debugLine="Login2.Download2(\"http://www.app-ear.com.ar/conne";
_login2._download2("http://www.app-ear.com.ar/connect/signinNew.php",new String[]{"user_id",_struserid,"password",_strpassword});
 //BA.debugLineNum = 388;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 389;BA.debugLine="ProgressDialogShow(\"Conectando al servidor...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Conectando al servidor...");
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 391;BA.debugLine="ProgressDialogShow(\"Connecting to the server...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Connecting to the server...");
 };
 //BA.debugLineNum = 393;BA.debugLine="End Sub";
return "";
}
public static String  _btnregister_click() throws Exception{
 //BA.debugLineNum = 575;BA.debugLine="Sub btnRegister_Click";
 //BA.debugLineNum = 576;BA.debugLine="StartActivity(\"Register\")";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)("Register"));
 //BA.debugLineNum = 577;BA.debugLine="End Sub";
return "";
}
public static String  _btnsendforgot_click() throws Exception{
String _recoveremail = "";
anywheresoftware.b4a.samples.httputils2.httpjob _recoverpass = null;
 //BA.debugLineNum = 595;BA.debugLine="Sub btnSendForgot_Click";
 //BA.debugLineNum = 596;BA.debugLine="Dim recoveremail As String";
_recoveremail = "";
 //BA.debugLineNum = 597;BA.debugLine="recoveremail = txtRecoverPass.Text";
_recoveremail = mostCurrent._txtrecoverpass.getText();
 //BA.debugLineNum = 598;BA.debugLine="Dim RecoverPass As HttpJob";
_recoverpass = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 599;BA.debugLine="RecoverPass.Initialize(\"Recover\", Me)";
_recoverpass._initialize(processBA,"Recover",main.getObject());
 //BA.debugLineNum = 600;BA.debugLine="RecoverPass.Download2(\"http://www.app-ear.com.ar/";
_recoverpass._download2("http://www.app-ear.com.ar/connect/recover.php",new String[]{"email",_recoveremail});
 //BA.debugLineNum = 602;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 603;BA.debugLine="ProgressDialogShow(\"Solicitando recuperación...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Solicitando recuperación...");
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 605;BA.debugLine="ProgressDialogShow(\"Requesting password recovery";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Requesting password recovery...");
 };
 //BA.debugLineNum = 607;BA.debugLine="pnlRecoverPass.Visible = False";
mostCurrent._pnlrecoverpass.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 608;BA.debugLine="pnlRecoverPass.SendToBack";
mostCurrent._pnlrecoverpass.SendToBack();
 //BA.debugLineNum = 609;BA.debugLine="End Sub";
return "";
}
public static String  _butcancel_click() throws Exception{
 //BA.debugLineNum = 610;BA.debugLine="Sub butCancel_Click";
 //BA.debugLineNum = 611;BA.debugLine="pnlRecoverPass.Visible = False";
mostCurrent._pnlrecoverpass.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 612;BA.debugLine="pnlRecoverPass.SendToBack";
mostCurrent._pnlrecoverpass.SendToBack();
 //BA.debugLineNum = 613;BA.debugLine="End Sub";
return "";
}
public static String  _cargarbienvenidos() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _st = "";
String[] _lastuserarray = null;
 //BA.debugLineNum = 245;BA.debugLine="Sub CargarBienvenidos";
 //BA.debugLineNum = 246;BA.debugLine="actionbarenabled = False";
_actionbarenabled = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 247;BA.debugLine="If File.ExternalWritable = True Then";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 248;BA.debugLine="savedir = File.DirRootExternal";
_savedir = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 }else {
 //BA.debugLineNum = 250;BA.debugLine="savedir = File.DirInternal";
_savedir = anywheresoftware.b4a.keywords.Common.File.getDirInternal();
 };
 //BA.debugLineNum = 254;BA.debugLine="If File.Exists(savedir & \"/AppEAR/\", \"\") = False";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_savedir+"/AppEAR/","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 255;BA.debugLine="File.MakeDir(savedir, \"AppEAR\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(_savedir,"AppEAR");
 };
 //BA.debugLineNum = 261;BA.debugLine="If File.Exists(savedir & \"/AppEAR/sent/\", \"\") = F";
if (anywheresoftware.b4a.keywords.Common.File.Exists(_savedir+"/AppEAR/sent/","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 262;BA.debugLine="File.MakeDir(savedir & \"/AppEAR/\", \"sent\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(_savedir+"/AppEAR/","sent");
 };
 //BA.debugLineNum = 272;BA.debugLine="If File.Exists(File.DirInternal, \"config.txt\") =";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 273;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 274;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 275;BA.debugLine="Map1.Put(\"strUserID\", \"NewID\")";
_map1.Put((Object)("strUserID"),(Object)("NewID"));
 //BA.debugLineNum = 276;BA.debugLine="Map1.Put(\"username\", \"None\")";
_map1.Put((Object)("username"),(Object)("None"));
 //BA.debugLineNum = 277;BA.debugLine="Map1.Put(\"msjprivadouser\", \"None\")";
_map1.Put((Object)("msjprivadouser"),(Object)("None"));
 //BA.debugLineNum = 278;BA.debugLine="Map1.Put(\"pass\", \"None\")";
_map1.Put((Object)("pass"),(Object)("None"));
 //BA.debugLineNum = 279;BA.debugLine="Map1.Put(\"lang\", \"None\")";
_map1.Put((Object)("lang"),(Object)("None"));
 //BA.debugLineNum = 280;BA.debugLine="Map1.Put(\"firstuse\", \"Si\")";
_map1.Put((Object)("firstuse"),(Object)("Si"));
 //BA.debugLineNum = 281;BA.debugLine="File.WriteMap(File.DirInternal, \"config.txt\",";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
 }else {
 //BA.debugLineNum = 286;BA.debugLine="Dim st As String";
_st = "";
 //BA.debugLineNum = 287;BA.debugLine="st = File.ReadString(File.DirInternal, \"config.t";
_st = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt");
 //BA.debugLineNum = 288;BA.debugLine="If st.StartsWith(\"#\") Then";
if (_st.startsWith("#")) { 
 //BA.debugLineNum = 289;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 290;BA.debugLine="Map1 = File.ReadMap(File.DirInternal, \"confi";
_map1 = anywheresoftware.b4a.keywords.Common.File.ReadMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt");
 //BA.debugLineNum = 291;BA.debugLine="strUserID = Map1.Get(\"strUserID\")";
_struserid = BA.ObjectToString(_map1.Get((Object)("strUserID")));
 //BA.debugLineNum = 292;BA.debugLine="username = Map1.Get(\"username\")";
_username = BA.ObjectToString(_map1.Get((Object)("username")));
 //BA.debugLineNum = 293;BA.debugLine="pass = Map1.Get(\"pass\")";
_pass = BA.ObjectToString(_map1.Get((Object)("pass")));
 //BA.debugLineNum = 294;BA.debugLine="msjprivadouser = Map1.Get(\"msjprivadouser\")";
_msjprivadouser = BA.ObjectToString(_map1.Get((Object)("msjprivadouser")));
 //BA.debugLineNum = 295;BA.debugLine="lang = Map1.Get(\"lang\")";
_lang = BA.ObjectToString(_map1.Get((Object)("lang")));
 //BA.debugLineNum = 296;BA.debugLine="firstuse = Map1.Get(\"firstuse\")";
_firstuse = BA.ObjectToString(_map1.Get((Object)("firstuse")));
 }else {
 //BA.debugLineNum = 299;BA.debugLine="Dim lastuserarray () As String";
_lastuserarray = new String[(int) (0)];
java.util.Arrays.fill(_lastuserarray,"");
 //BA.debugLineNum = 300;BA.debugLine="lastuserarray = Regex.Split(\"@\", File.ReadStrin";
_lastuserarray = anywheresoftware.b4a.keywords.Common.Regex.Split("@",anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt"));
 //BA.debugLineNum = 301;BA.debugLine="strUserID = lastuserarray(0)";
_struserid = _lastuserarray[(int) (0)];
 //BA.debugLineNum = 302;BA.debugLine="username = lastuserarray(1)";
_username = _lastuserarray[(int) (1)];
 //BA.debugLineNum = 303;BA.debugLine="pass = lastuserarray(2)";
_pass = _lastuserarray[(int) (2)];
 //BA.debugLineNum = 304;BA.debugLine="msjprivadouser = lastuserarray(3)";
_msjprivadouser = _lastuserarray[(int) (3)];
 //BA.debugLineNum = 305;BA.debugLine="If lastuserarray.Length > 4 Then";
if (_lastuserarray.length>4) { 
 //BA.debugLineNum = 306;BA.debugLine="lang = lastuserarray(4)";
_lang = _lastuserarray[(int) (4)];
 }else {
 //BA.debugLineNum = 308;BA.debugLine="lang = \"es\"";
_lang = "es";
 };
 //BA.debugLineNum = 310;BA.debugLine="If firstuse = \"\" Then";
if ((_firstuse).equals("")) { 
 //BA.debugLineNum = 311;BA.debugLine="firstuse = \"Si\"";
_firstuse = "Si";
 };
 //BA.debugLineNum = 315;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 316;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 317;BA.debugLine="Map1.Put(\"strUserID\", strUserID)";
_map1.Put((Object)("strUserID"),(Object)(_struserid));
 //BA.debugLineNum = 318;BA.debugLine="Map1.Put(\"username\", username)";
_map1.Put((Object)("username"),(Object)(_username));
 //BA.debugLineNum = 319;BA.debugLine="Map1.Put(\"msjprivadouser\", msjprivadouser)";
_map1.Put((Object)("msjprivadouser"),(Object)(_msjprivadouser));
 //BA.debugLineNum = 320;BA.debugLine="Map1.Put(\"pass\", pass)";
_map1.Put((Object)("pass"),(Object)(_pass));
 //BA.debugLineNum = 321;BA.debugLine="Map1.Put(\"lang\", lang)";
_map1.Put((Object)("lang"),(Object)(_lang));
 //BA.debugLineNum = 322;BA.debugLine="Map1.Put(\"firstuse\", firstuse)";
_map1.Put((Object)("firstuse"),(Object)(_firstuse));
 //BA.debugLineNum = 323;BA.debugLine="File.WriteMap(File.DirInternal, \"config.txt\"";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
 };
 };
 //BA.debugLineNum = 330;BA.debugLine="If lang = \"es\" Or lang = \"None\" Or lang = \"\" Then";
if ((_lang).equals("es") || (_lang).equals("None") || (_lang).equals("")) { 
 //BA.debugLineNum = 331;BA.debugLine="Activity.AddMenuItem2(\"Ver mi perfil\", \"mnuVermi";
mostCurrent._activity.AddMenuItem2("Ver mi perfil","mnuVermiperfil",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"miperfil.png").getObject()));
 //BA.debugLineNum = 332;BA.debugLine="Activity.AddMenuItem2(\"Cerrar sesión\", \"mnuCerra";
mostCurrent._activity.AddMenuItem2("Cerrar sesión","mnuCerrarSesion",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cerraruser.png").getObject()));
 //BA.debugLineNum = 333;BA.debugLine="Activity.AddMenuItem2(\"Acerca de AppEAR\", \"mnuAb";
mostCurrent._activity.AddMenuItem2("Acerca de AppEAR","mnuAbout",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()));
 //BA.debugLineNum = 334;BA.debugLine="lang = \"es\"";
_lang = "es";
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 336;BA.debugLine="Activity.AddMenuItem2(\"My profile\", \"mnuVermiper";
mostCurrent._activity.AddMenuItem2("My profile","mnuVermiperfil",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"miperfil.png").getObject()));
 //BA.debugLineNum = 337;BA.debugLine="Activity.AddMenuItem2(\"Log off\", \"mnuCerrarSesio";
mostCurrent._activity.AddMenuItem2("Log off","mnuCerrarSesion",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cerraruser.png").getObject()));
 //BA.debugLineNum = 338;BA.debugLine="Activity.AddMenuItem2(\"About AppEAR\", \"mnuAbout\"";
mostCurrent._activity.AddMenuItem2("About AppEAR","mnuAbout",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()));
 };
 //BA.debugLineNum = 342;BA.debugLine="If modooffline = False Then";
if (_modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 343;BA.debugLine="Activity.LoadLayout(\"frmBienvenidos\")";
mostCurrent._activity.LoadLayout("frmBienvenidos",mostCurrent.activityBA);
 //BA.debugLineNum = 344;BA.debugLine="If username <> \"None\" Then";
if ((_username).equals("None") == false) { 
 //BA.debugLineNum = 345;BA.debugLine="txtUserID.Text = username";
mostCurrent._txtuserid.setText((Object)(_username));
 };
 //BA.debugLineNum = 347;BA.debugLine="If pass <> \"None\" And pass <> \"\" Then";
if ((_pass).equals("None") == false && (_pass).equals("") == false) { 
 //BA.debugLineNum = 348;BA.debugLine="txtPassword.Text = pass";
mostCurrent._txtpassword.setText((Object)(_pass));
 };
 }else {
 //BA.debugLineNum = 351;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 352;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 353;BA.debugLine="StartActivity(frmPrincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmprincipal.getObject()));
 };
 //BA.debugLineNum = 356;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 103;BA.debugLine="Private lblAppEAR As Label";
mostCurrent._lblappear = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 104;BA.debugLine="Private btnLogin As Button";
mostCurrent._btnlogin = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Private btnRegister As Button";
mostCurrent._btnregister = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 106;BA.debugLine="Private txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 107;BA.debugLine="Private txtUserID As EditText";
mostCurrent._txtuserid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 108;BA.debugLine="Private lblMessage As Label";
mostCurrent._lblmessage = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 110;BA.debugLine="Private pnlRecoverPass As Panel";
mostCurrent._pnlrecoverpass = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 111;BA.debugLine="Private txtRecoverPass As EditText";
mostCurrent._txtrecoverpass = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 112;BA.debugLine="Private btnForgot As Button";
mostCurrent._btnforgot = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 113;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 115;BA.debugLine="Private pgbLoad As ProgressBar";
mostCurrent._pgbload = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 116;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 118;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Private btnSendForgot As Button";
mostCurrent._btnsendforgot = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 120;BA.debugLine="Private butCancel As Button";
mostCurrent._butcancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 121;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String[] _actarray = null;
int _upd = 0;
anywheresoftware.b4a.objects.IntentWrapper _market = null;
String _uri = "";
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 401;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 402;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 403;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 404;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 405;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 406;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring();
 //BA.debugLineNum = 407;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 408;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 409;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 411;BA.debugLine="If Job.JobName = \"Connect\" Then";
if ((_job._jobname).equals("Connect")) { 
 //BA.debugLineNum = 412;BA.debugLine="If act = \"Connected\" Then";
if ((_act).equals("Connected")) { 
 //BA.debugLineNum = 414;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 415;BA.debugLine="lblEstado.Text = \"Conectado. Comprobando vers";
mostCurrent._lblestado.setText((Object)("Conectado. Comprobando versión de la aplicación"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 417;BA.debugLine="lblEstado.Text = \"Connected. Checking version";
mostCurrent._lblestado.setText((Object)("Connected. Checking version of the app"));
 };
 //BA.debugLineNum = 419;BA.debugLine="modooffline = False";
_modooffline = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 420;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 421;BA.debugLine="Dim actarray() As String = Regex.Split(\"-\", ac";
_actarray = anywheresoftware.b4a.keywords.Common.Regex.Split("-",_act);
 //BA.debugLineNum = 422;BA.debugLine="serverversion = actarray(0)";
_serverversion = _actarray[(int) (0)];
 //BA.debugLineNum = 423;BA.debugLine="servernewstitulo = actarray(1)";
_servernewstitulo = _actarray[(int) (1)];
 //BA.debugLineNum = 424;BA.debugLine="servernewstext = actarray(2)";
_servernewstext = _actarray[(int) (2)];
 //BA.debugLineNum = 426;BA.debugLine="If serverversion <> versionactual Then";
if ((_serverversion).equals(_versionactual) == false) { 
 //BA.debugLineNum = 427;BA.debugLine="Dim upd As Int";
_upd = 0;
 //BA.debugLineNum = 428;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 429;BA.debugLine="upd = utilidades.Mensaje(\"Actualización disp";
_upd = (int)(Double.parseDouble(mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Actualización disponible","Para continuar, debe descargar una actualización importante","Ir a GooglePlay","","Lo haré después")));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 432;BA.debugLine="upd = utilidades.Mensaje(\"Actualización disp";
_upd = (int)(Double.parseDouble(mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Actualización disponible","Para continuar, debe descargar una actualización importante","Ir a GooglePlay","","Lo haré después")));
 };
 //BA.debugLineNum = 434;BA.debugLine="If upd = DialogResponse.POSITIVE Then";
if (_upd==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 436;BA.debugLine="Dim market As Intent, uri As String";
_market = new anywheresoftware.b4a.objects.IntentWrapper();
_uri = "";
 //BA.debugLineNum = 437;BA.debugLine="uri=\"market://details?id=ilpla.appear\"";
_uri = "market://details?id=ilpla.appear";
 //BA.debugLineNum = 438;BA.debugLine="market.Initialize(market.ACTION_VIEW,uri)";
_market.Initialize(_market.ACTION_VIEW,_uri);
 //BA.debugLineNum = 439;BA.debugLine="StartActivity(market)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_market.getObject()));
 //BA.debugLineNum = 441;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 442;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 }else {
 //BA.debugLineNum = 445;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 446;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 };
 }else {
 //BA.debugLineNum = 451;BA.debugLine="If servernewstitulo <> \"\" And servernewstitul";
if ((_servernewstitulo).equals("") == false && (_servernewstitulo).equals("Nada") == false) { 
 //BA.debugLineNum = 452;BA.debugLine="Msgbox(servernewstext, servernewstitulo)";
anywheresoftware.b4a.keywords.Common.Msgbox(_servernewstext,_servernewstitulo,mostCurrent.activityBA);
 };
 //BA.debugLineNum = 456;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 457;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 };
 };
 };
 //BA.debugLineNum = 462;BA.debugLine="If Job.JobName = \"Login\" Then";
if ((_job._jobname).equals("Login")) { 
 //BA.debugLineNum = 463;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 464;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error",anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 466;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 467;BA.debugLine="ToastMessageShow(\"Usuario o clave incorrecta";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Usuario o clave incorrecta",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 469;BA.debugLine="ToastMessageShow(\"Wrong username or password";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Wrong username or password",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("LoginOK")) { 
 //BA.debugLineNum = 476;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 477;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 478;BA.debugLine="strUserID = newpunto.Get(\"user_id\")";
_struserid = BA.ObjectToString(_newpunto.Get((Object)("user_id")));
 //BA.debugLineNum = 479;BA.debugLine="strUserName = newpunto.Get(\"user_name\")";
_strusername = BA.ObjectToString(_newpunto.Get((Object)("user_name")));
 //BA.debugLineNum = 480;BA.debugLine="strUserLocation = newpunto.Get(\"location\")";
_struserlocation = BA.ObjectToString(_newpunto.Get((Object)("location")));
 //BA.debugLineNum = 481;BA.debugLine="strUserEmail = newpunto.Get(\"email\")";
_struseremail = BA.ObjectToString(_newpunto.Get((Object)("email")));
 //BA.debugLineNum = 482;BA.debugLine="strUserOrg = newpunto.Get(\"org\")";
_struserorg = BA.ObjectToString(_newpunto.Get((Object)("org")));
 //BA.debugLineNum = 483;BA.debugLine="strUserTipoUsuario = newpunto.Get(\"tipousuari";
_strusertipousuario = BA.ObjectToString(_newpunto.Get((Object)("tipousuario")));
 //BA.debugLineNum = 485;BA.debugLine="puntostotales = newpunto.Get(\"puntostotales\")";
_puntostotales = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("puntostotales"))));
 //BA.debugLineNum = 486;BA.debugLine="numfotosok = newpunto.Get(\"numfotosok\")";
_numfotosok = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("numfotosok"))));
 //BA.debugLineNum = 487;BA.debugLine="puntosnumfotos = newpunto.Get(\"puntosfotos\")";
_puntosnumfotos = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("puntosfotos"))));
 //BA.debugLineNum = 488;BA.debugLine="numevalsok = newpunto.Get(\"numevalsok\")";
_numevalsok = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("numevalsok"))));
 //BA.debugLineNum = 489;BA.debugLine="puntosnumevals = newpunto.Get(\"puntosevals\")";
_puntosnumevals = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("puntosevals"))));
 //BA.debugLineNum = 491;BA.debugLine="numriollanura = newpunto.Get(\"numriollanura\")";
_numriollanura = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("numriollanura"))));
 //BA.debugLineNum = 492;BA.debugLine="numriomontana = newpunto.Get(\"numriomontana\")";
_numriomontana = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("numriomontana"))));
 //BA.debugLineNum = 493;BA.debugLine="numlaguna = newpunto.Get(\"numlaguna\")";
_numlaguna = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("numlaguna"))));
 //BA.debugLineNum = 494;BA.debugLine="numestuario = newpunto.Get(\"numestuario\")";
_numestuario = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("numestuario"))));
 //BA.debugLineNum = 495;BA.debugLine="numshares = newpunto.Get(\"numshares\")";
_numshares = (int)(BA.ObjectToNumber(_newpunto.Get((Object)("numshares"))));
 //BA.debugLineNum = 496;BA.debugLine="msjprivadouser = newpunto.Get(\"msjprivado\")";
_msjprivadouser = BA.ObjectToString(_newpunto.Get((Object)("msjprivado")));
 //BA.debugLineNum = 497;BA.debugLine="pass = newpunto.Get(\"user_pw\")";
_pass = BA.ObjectToString(_newpunto.Get((Object)("user_pw")));
 //BA.debugLineNum = 498;BA.debugLine="username = strUserName";
_username = _strusername;
 //BA.debugLineNum = 499;BA.debugLine="btnForgot.Visible = False";
mostCurrent._btnforgot.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 500;BA.debugLine="modooffline = False";
_modooffline = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 501;BA.debugLine="If msjprivadouser = \"\" Then";
if ((_msjprivadouser).equals("")) { 
 //BA.debugLineNum = 502;BA.debugLine="msjprivadouser = \"None\"";
_msjprivadouser = "None";
 };
 //BA.debugLineNum = 505;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 506;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 507;BA.debugLine="Map1.Put(\"strUserID\", strUserID)";
_map1.Put((Object)("strUserID"),(Object)(_struserid));
 //BA.debugLineNum = 508;BA.debugLine="Map1.Put(\"username\", username)";
_map1.Put((Object)("username"),(Object)(_username));
 //BA.debugLineNum = 509;BA.debugLine="Map1.Put(\"msjprivadouser\", msjprivadouser)";
_map1.Put((Object)("msjprivadouser"),(Object)(_msjprivadouser));
 //BA.debugLineNum = 510;BA.debugLine="Map1.Put(\"pass\", pass)";
_map1.Put((Object)("pass"),(Object)(_pass));
 //BA.debugLineNum = 511;BA.debugLine="Map1.Put(\"lang\", lang)";
_map1.Put((Object)("lang"),(Object)(_lang));
 //BA.debugLineNum = 512;BA.debugLine="Map1.Put(\"firstuse\", firstuse)";
_map1.Put((Object)("firstuse"),(Object)(_firstuse));
 //BA.debugLineNum = 513;BA.debugLine="File.WriteMap(File.DirInternal, \"config.tx";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
 //BA.debugLineNum = 515;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 516;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 517;BA.debugLine="StartActivity(frmPrincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmprincipal.getObject()));
 }else {
 //BA.debugLineNum = 520;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 521;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestr";
anywheresoftware.b4a.keywords.Common.Msgbox("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!","Mala mía",mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 523;BA.debugLine="Msgbox(\"We are having issues with our server";
anywheresoftware.b4a.keywords.Common.Msgbox("We are having issues with our server, we will fix it soon!","Bad me",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 526;BA.debugLine="Msgbox(act,\"act\")";
anywheresoftware.b4a.keywords.Common.Msgbox(_act,"act",mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 530;BA.debugLine="If Job.JobName = \"Recover\" Then";
if ((_job._jobname).equals("Recover")) { 
 //BA.debugLineNum = 531;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 532;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 533;BA.debugLine="ToastMessageShow(\"Error: El email no existe\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: El email no existe",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 535;BA.debugLine="ToastMessageShow(\"Error: email does not exis";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: email does not exist",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 538;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 539;BA.debugLine="ToastMessageShow(\"Error: Ha ocurrido un erro";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: Ha ocurrido un error!",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 541;BA.debugLine="ToastMessageShow(\"Error: an error has occurr";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: an error has occurred!",anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("RecoverOK")) { 
 //BA.debugLineNum = 544;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 545;BA.debugLine="ToastMessageShow(\"Se ha enviado su clave a s";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Se ha enviado su clave a su email registrado",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 547;BA.debugLine="ToastMessageShow(\"Your password has been sen";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Your password has been sent to your email",anywheresoftware.b4a.keywords.Common.True);
 };
 };
 };
 }else {
 //BA.debugLineNum = 553;BA.debugLine="If Job.JobName = \"Connect\" Then";
if ((_job._jobname).equals("Connect")) { 
 //BA.debugLineNum = 554;BA.debugLine="If lang = \"en\" Then";
if ((_lang).equals("en")) { 
 //BA.debugLineNum = 555;BA.debugLine="utilidades.Mensaje(\"Warning\", \"You have no int";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Warning","You have no internet connection. AppEAR will initialize with the last username you used, but you won't be able to send a report until you're connected to the internet!","OK","","");
 }else {
 //BA.debugLineNum = 557;BA.debugLine="utilidades.Mensaje(\"Advertencia\", \"No tienes c";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Advertencia","No tienes conexión a Internet. AppEAR iniciará con el último usuario que utilizaste, pero no podrás enviar reportes hasta que no te conectes!","OK","","");
 };
 //BA.debugLineNum = 559;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 560;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 562;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 563;BA.debugLine="CargarBienvenidos";
_cargarbienvenidos();
 };
 };
 //BA.debugLineNum = 566;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 567;BA.debugLine="End Sub";
return "";
}
public static String  _mnuabout_click() throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Sub mnuAbout_Click";
 //BA.debugLineNum = 211;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _mnucerrarsesion_click() throws Exception{
String _msg = "";
 //BA.debugLineNum = 175;BA.debugLine="Sub mnuCerrarSesion_Click";
 //BA.debugLineNum = 176;BA.debugLine="If actionbarenabled = True Then";
if (_actionbarenabled==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 177;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 178;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 179;BA.debugLine="msg = utilidades.Mensaje(\"Seguro?\", \"Desea cer";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Seguro?","Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!","Si, tengo internet","No, estoy en el campo","");
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 181;BA.debugLine="msg = utilidades.Mensaje(\"Sure?\", \"Are you sur";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Sure?","Are you sure you want to log off? To log back in, you will need an internet connection!","Yes, I have internet","","No, I have no internet");
 };
 //BA.debugLineNum = 183;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 184;BA.debugLine="strUserID = \"\"";
_struserid = "";
 //BA.debugLineNum = 185;BA.debugLine="strUserName = \"\"";
_strusername = "";
 //BA.debugLineNum = 186;BA.debugLine="strUserLocation = \"\"";
_struserlocation = "";
 //BA.debugLineNum = 187;BA.debugLine="strUserEmail = \"\"";
_struseremail = "";
 //BA.debugLineNum = 188;BA.debugLine="strUserOrg = \"\"";
_struserorg = "";
 //BA.debugLineNum = 189;BA.debugLine="puntostotales = 0";
_puntostotales = (int) (0);
 //BA.debugLineNum = 190;BA.debugLine="numfotosok = 0";
_numfotosok = (int) (0);
 //BA.debugLineNum = 191;BA.debugLine="puntosnumfotos = 0";
_puntosnumfotos = (int) (0);
 //BA.debugLineNum = 192;BA.debugLine="numevalsok = 0";
_numevalsok = (int) (0);
 //BA.debugLineNum = 193;BA.debugLine="puntosnumevals = 0";
_puntosnumevals = (int) (0);
 //BA.debugLineNum = 194;BA.debugLine="username = \"\"";
_username = "";
 //BA.debugLineNum = 195;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 196;BA.debugLine="Activity.LoadLayout(\"frmBienvenidos\")";
mostCurrent._activity.LoadLayout("frmBienvenidos",mostCurrent.activityBA);
 //BA.debugLineNum = 197;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 198;BA.debugLine="actionbarenabled = False";
_actionbarenabled = anywheresoftware.b4a.keywords.Common.False;
 };
 }else {
 //BA.debugLineNum = 201;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 202;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No ha iniciado sesión aún",anywheresoftware.b4a.keywords.Common.False);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 204;BA.debugLine="ToastMessageShow(\"You are not logged in\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You are not logged in",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        anywheresoftware.b4a.samples.httputils2.httputils2service._process_globals();
main._process_globals();
frmprincipal._process_globals();
frmevaluacion._process_globals();
utilidades._process_globals();
game_ciclo._process_globals();
game_sourcepoint._process_globals();
game_comunidades._process_globals();
game_trofica._process_globals();
aprender_tipoagua._process_globals();
frmaprender._process_globals();
frmresultados._process_globals();
frmhabitatestuario._process_globals();
game_ahorcado._process_globals();
aprender_factores._process_globals();
frmminigames._process_globals();
frmhabitatrio._process_globals();
frmabout._process_globals();
frmhabitatlaguna._process_globals();
frmperfil._process_globals();
envioarchivos._process_globals();
frmcamara._process_globals();
register._process_globals();
frmlocalizacion._process_globals();
frmtiporio._process_globals();
game_memory._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 17;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 19;BA.debugLine="Dim valorcalidad As Double";
_valorcalidad = 0;
 //BA.debugLineNum = 20;BA.debugLine="Dim valoresindString As String";
_valoresindstring = "";
 //BA.debugLineNum = 21;BA.debugLine="Dim valorind1 As String";
_valorind1 = "";
 //BA.debugLineNum = 22;BA.debugLine="Dim valorind2 As String";
_valorind2 = "";
 //BA.debugLineNum = 23;BA.debugLine="Dim valorind3 As String";
_valorind3 = "";
 //BA.debugLineNum = 24;BA.debugLine="Dim valorind4 As String";
_valorind4 = "";
 //BA.debugLineNum = 25;BA.debugLine="Dim valorind5 As String";
_valorind5 = "";
 //BA.debugLineNum = 26;BA.debugLine="Dim valorind6 As String";
_valorind6 = "";
 //BA.debugLineNum = 27;BA.debugLine="Dim valorind7 As String";
_valorind7 = "";
 //BA.debugLineNum = 28;BA.debugLine="Dim valorind8 As String";
_valorind8 = "";
 //BA.debugLineNum = 29;BA.debugLine="Dim valorind9 As String";
_valorind9 = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim valorind10 As String";
_valorind10 = "";
 //BA.debugLineNum = 31;BA.debugLine="Dim valorind11 As String";
_valorind11 = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim valorind12 As String";
_valorind12 = "";
 //BA.debugLineNum = 33;BA.debugLine="Dim valorind13 As String";
_valorind13 = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim valorind14 As String";
_valorind14 = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim valorind15 As String";
_valorind15 = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim valorind16 As String";
_valorind16 = "";
 //BA.debugLineNum = 37;BA.debugLine="Dim dateandtime As String";
_dateandtime = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim preguntanumero As Int";
_preguntanumero = 0;
 //BA.debugLineNum = 39;BA.debugLine="Dim valorNS As Int";
_valorns = 0;
 //BA.debugLineNum = 42;BA.debugLine="Dim Idproyecto As Int";
_idproyecto = 0;
 //BA.debugLineNum = 43;BA.debugLine="Dim tiporio As String";
_tiporio = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim longitud As String";
_longitud = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim latitud As String";
_latitud = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim nombrerio As String";
_nombrerio = "";
 //BA.debugLineNum = 49;BA.debugLine="Dim fotopath0 As String = \"\"";
_fotopath0 = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim fotopath1 As String = \"\"";
_fotopath1 = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim fotopath2 As String = \"\"";
_fotopath2 = "";
 //BA.debugLineNum = 52;BA.debugLine="Dim fotopath3 As String = \"\"";
_fotopath3 = "";
 //BA.debugLineNum = 56;BA.debugLine="Dim evaluacionpath As String";
_evaluacionpath = "";
 //BA.debugLineNum = 60;BA.debugLine="Dim strUserID As String";
_struserid = "";
 //BA.debugLineNum = 61;BA.debugLine="Dim strUserName As String";
_strusername = "";
 //BA.debugLineNum = 62;BA.debugLine="Dim strUserLocation As String";
_struserlocation = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim strUserEmail As String";
_struseremail = "";
 //BA.debugLineNum = 64;BA.debugLine="Dim strUserOrg As String";
_struserorg = "";
 //BA.debugLineNum = 65;BA.debugLine="Dim strUserTipoUsuario As String";
_strusertipousuario = "";
 //BA.debugLineNum = 66;BA.debugLine="Dim numfotosok As Int  = 0";
_numfotosok = (int) (0);
 //BA.debugLineNum = 67;BA.debugLine="Dim numevalsok As Int  = 0";
_numevalsok = (int) (0);
 //BA.debugLineNum = 68;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim modooffline As Boolean";
_modooffline = false;
 //BA.debugLineNum = 70;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 73;BA.debugLine="Dim puntostotales As Int = 0";
_puntostotales = (int) (0);
 //BA.debugLineNum = 74;BA.debugLine="Dim puntosnumfotos As Int = 0";
_puntosnumfotos = (int) (0);
 //BA.debugLineNum = 75;BA.debugLine="Dim puntosnumevals As Int = 0";
_puntosnumevals = (int) (0);
 //BA.debugLineNum = 76;BA.debugLine="Dim numriollanura As Int = 0";
_numriollanura = (int) (0);
 //BA.debugLineNum = 77;BA.debugLine="Dim numriomontana As Int = 0";
_numriomontana = (int) (0);
 //BA.debugLineNum = 78;BA.debugLine="Dim numlaguna As Int = 0";
_numlaguna = (int) (0);
 //BA.debugLineNum = 79;BA.debugLine="Dim numestuario As Int = 0";
_numestuario = (int) (0);
 //BA.debugLineNum = 80;BA.debugLine="Dim numshares As Int = 0";
_numshares = (int) (0);
 //BA.debugLineNum = 82;BA.debugLine="Dim k As Phone";
_k = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 85;BA.debugLine="Dim proyectoenviar As String";
_proyectoenviar = "";
 //BA.debugLineNum = 86;BA.debugLine="Dim savedir As String";
_savedir = "";
 //BA.debugLineNum = 89;BA.debugLine="Dim versionactual As String";
_versionactual = "";
 //BA.debugLineNum = 90;BA.debugLine="Dim serverversion As String";
_serverversion = "";
 //BA.debugLineNum = 91;BA.debugLine="Dim servernewstitulo As String";
_servernewstitulo = "";
 //BA.debugLineNum = 92;BA.debugLine="Dim servernewstext As String";
_servernewstext = "";
 //BA.debugLineNum = 93;BA.debugLine="Dim msjprivadouser As String";
_msjprivadouser = "";
 //BA.debugLineNum = 94;BA.debugLine="Dim actionbarenabled As Boolean";
_actionbarenabled = false;
 //BA.debugLineNum = 97;BA.debugLine="Dim lang As String";
_lang = "";
 //BA.debugLineNum = 98;BA.debugLine="Dim firstuse As String";
_firstuse = "";
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _testconnection() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _connect = null;
 //BA.debugLineNum = 226;BA.debugLine="Sub TestConnection";
 //BA.debugLineNum = 227;BA.debugLine="Dim Connect As HttpJob";
_connect = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 228;BA.debugLine="versionactual = Application.VersionCode";
_versionactual = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
 //BA.debugLineNum = 230;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 231;BA.debugLine="lblEstado.Text = \"Comprobando conexión a interne";
mostCurrent._lblestado.setText((Object)("Comprobando conexión a internet"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 233;BA.debugLine="lblEstado.Text = \"Checking internet connection\"";
mostCurrent._lblestado.setText((Object)("Checking internet connection"));
 };
 //BA.debugLineNum = 236;BA.debugLine="Connect.Initialize(\"Connect\", Me)";
_connect._initialize(processBA,"Connect",main.getObject());
 //BA.debugLineNum = 237;BA.debugLine="Connect.Download(\"http://www.app-ear.com.ar/conne";
_connect._download("http://www.app-ear.com.ar/connect/connecttest.php");
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 158;BA.debugLine="If lang = \"en\" Then";
if ((_lang).equals("en")) { 
 //BA.debugLineNum = 159;BA.debugLine="txtUserID.Hint = \"Enter your username\"";
mostCurrent._txtuserid.setHint("Enter your username");
 //BA.debugLineNum = 160;BA.debugLine="txtPassword.Hint = \"Enter your password\"";
mostCurrent._txtpassword.setHint("Enter your password");
 //BA.debugLineNum = 161;BA.debugLine="btnLogin.Text = \"Log in\"";
mostCurrent._btnlogin.setText((Object)("Log in"));
 //BA.debugLineNum = 162;BA.debugLine="btnRegister.Text = \"Sign up!\"";
mostCurrent._btnregister.setText((Object)("Sign up!"));
 //BA.debugLineNum = 163;BA.debugLine="btnForgot.Text = \"Forgot my password\"";
mostCurrent._btnforgot.setText((Object)("Forgot my password"));
 };
 //BA.debugLineNum = 166;BA.debugLine="End Sub";
return "";
}
}
