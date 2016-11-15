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

public class frmprincipal extends Activity implements B4AActivity{
	public static frmprincipal mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmprincipal");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmprincipal).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmprincipal");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmprincipal", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmprincipal) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmprincipal) Resume **");
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
		return frmprincipal.class;
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
        BA.LogInfo("** Activity (frmprincipal) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmprincipal) Resume **");
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
public static boolean _recargapuntos = false;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntostotales = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnumevals = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbnivel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnivel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public static int _pasonum = 0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public ilpla.appear.main _main = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
String _arrancatutorial = "";
 //BA.debugLineNum = 28;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 29;BA.debugLine="Activity.LoadLayout(\"frmPrincipal\")";
mostCurrent._activity.LoadLayout("frmPrincipal",mostCurrent.activityBA);
 //BA.debugLineNum = 30;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 31;BA.debugLine="recargaPuntos = False";
_recargapuntos = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 33;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 34;BA.debugLine="startBienvienido(False, True)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 36;BA.debugLine="startBienvienido(True, True)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 40;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 41;BA.debugLine="Activity.AddMenuItem2(\"Ver mi perfil\", \"mnuVermi";
mostCurrent._activity.AddMenuItem2("Ver mi perfil","mnuVermiperfil",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"miperfil.png").getObject()));
 //BA.debugLineNum = 42;BA.debugLine="Activity.AddMenuItem2(\"Noticias\", \"mnuNoticias\",";
mostCurrent._activity.AddMenuItem2("Noticias","mnuNoticias",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"news.png").getObject()));
 //BA.debugLineNum = 43;BA.debugLine="Activity.AddMenuItem2(\"Cerrar sesión\", \"mnuCerra";
mostCurrent._activity.AddMenuItem2("Cerrar sesión","mnuCerrarSesion",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cerraruser.png").getObject()));
 //BA.debugLineNum = 44;BA.debugLine="Activity.AddMenuItem2(\"Acerca de AppEAR\", \"mnuAb";
mostCurrent._activity.AddMenuItem2("Acerca de AppEAR","mnuAbout",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 46;BA.debugLine="Activity.AddMenuItem2(\"My profile\", \"mnuVermiper";
mostCurrent._activity.AddMenuItem2("My profile","mnuVermiperfil",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"miperfil.png").getObject()));
 //BA.debugLineNum = 47;BA.debugLine="Activity.AddMenuItem2(\"News\", \"mnuNoticias\", Loa";
mostCurrent._activity.AddMenuItem2("News","mnuNoticias",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"news.png").getObject()));
 //BA.debugLineNum = 48;BA.debugLine="Activity.AddMenuItem2(\"Log off\", \"mnuCerrarSesio";
mostCurrent._activity.AddMenuItem2("Log off","mnuCerrarSesion",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cerraruser.png").getObject()));
 //BA.debugLineNum = 49;BA.debugLine="Activity.AddMenuItem2(\"About AppEAR\", \"mnuAbout\"";
mostCurrent._activity.AddMenuItem2("About AppEAR","mnuAbout",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()));
 };
 //BA.debugLineNum = 53;BA.debugLine="If Main.firstuse = \"Si\" Then";
if ((mostCurrent._main._firstuse).equals("Si")) { 
 //BA.debugLineNum = 54;BA.debugLine="Dim arrancatutorial As String";
_arrancatutorial = "";
 //BA.debugLineNum = 55;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 56;BA.debugLine="arrancatutorial = utilidades.Mensaje(\"Tutorial\"";
_arrancatutorial = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Tutorial","It's the first time you use AppEAR, do you need help?","Yes, show me","No, I already know how to use it","");
 }else {
 //BA.debugLineNum = 58;BA.debugLine="arrancatutorial = utilidades.Mensaje(\"Tutorial\"";
_arrancatutorial = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Tutorial","Es la primera vez que usa AppEAR, desea realizar un recorrido introductorio?","Si, muestrame","No, ya se cómo usar AppEAR","");
 };
 //BA.debugLineNum = 60;BA.debugLine="If arrancatutorial = DialogResponse.POSITIVE The";
if ((_arrancatutorial).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 61;BA.debugLine="StartTutorial";
_starttutorial();
 };
 };
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 69;BA.debugLine="recargar";
_recargar();
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _btnaprender_click() throws Exception{
 //BA.debugLineNum = 575;BA.debugLine="Sub btnAprender_Click";
 //BA.debugLineNum = 576;BA.debugLine="StartActivity(frmAprender)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmaprender.getObject()));
 //BA.debugLineNum = 577;BA.debugLine="End Sub";
return "";
}
public static String  _btnevaluacion_click() throws Exception{
 //BA.debugLineNum = 537;BA.debugLine="Sub btnEvaluacion_Click";
 //BA.debugLineNum = 539;BA.debugLine="Main.fotopath0 = \"\"";
mostCurrent._main._fotopath0 = "";
 //BA.debugLineNum = 540;BA.debugLine="Main.fotopath1 = \"\"";
mostCurrent._main._fotopath1 = "";
 //BA.debugLineNum = 541;BA.debugLine="Main.fotopath2 = \"\"";
mostCurrent._main._fotopath2 = "";
 //BA.debugLineNum = 542;BA.debugLine="Main.fotopath3 = \"\"";
mostCurrent._main._fotopath3 = "";
 //BA.debugLineNum = 543;BA.debugLine="frmEvaluacion.evaluaciondone = False";
mostCurrent._frmevaluacion._evaluaciondone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 544;BA.debugLine="frmEvaluacion.fotosdone = False";
mostCurrent._frmevaluacion._fotosdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 545;BA.debugLine="frmEvaluacion.notasdone = False";
mostCurrent._frmevaluacion._notasdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 546;BA.debugLine="frmEvaluacion.otrosdone = False";
mostCurrent._frmevaluacion._otrosdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 547;BA.debugLine="frmEvaluacion.currentproject = \"\"";
mostCurrent._frmevaluacion._currentproject = "";
 //BA.debugLineNum = 548;BA.debugLine="frmEvaluacion.formorigen = \"Principal\"";
mostCurrent._frmevaluacion._formorigen = "Principal";
 //BA.debugLineNum = 550;BA.debugLine="utilidades.SabiasQue";
mostCurrent._utilidades._sabiasque(mostCurrent.activityBA);
 //BA.debugLineNum = 552;BA.debugLine="StartActivity(frmEvaluacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmevaluacion.getObject()));
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
return "";
}
public static String  _btnmapa_click() throws Exception{
 //BA.debugLineNum = 502;BA.debugLine="Sub btnMapa_Click";
 //BA.debugLineNum = 503;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 505;BA.debugLine="frmLocalizacion.formorigen = \"Principal\"";
mostCurrent._frmlocalizacion._formorigen = "Principal";
 //BA.debugLineNum = 506;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 }else {
 //BA.debugLineNum = 508;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 509;BA.debugLine="ToastMessageShow(\"Necesitas internet para ver t";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Necesitas internet para ver tus contribuciones anteriores en el mapa",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 511;BA.debugLine="ToastMessageShow(\"You need internet to view the";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You need internet to view the map",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 514;BA.debugLine="End Sub";
return "";
}
public static String  _btnminijuegos_click() throws Exception{
 //BA.debugLineNum = 588;BA.debugLine="Sub btnMiniJuegos_Click";
 //BA.debugLineNum = 589;BA.debugLine="StartActivity(frmMiniGames)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmminigames.getObject()));
 //BA.debugLineNum = 590;BA.debugLine="End Sub";
return "";
}
public static String  _btnperfil_click() throws Exception{
 //BA.debugLineNum = 473;BA.debugLine="Sub btnPerfil_Click";
 //BA.debugLineNum = 474;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 475;BA.debugLine="StartActivity(frmPerfil)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmperfil.getObject()));
 }else {
 //BA.debugLineNum = 477;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 478;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No ha iniciado sesión aún",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 480;BA.debugLine="ToastMessageShow(\"You are not logged in\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You are not logged in",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 483;BA.debugLine="End Sub";
return "";
}
public static String  _butcontinue_click() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _labelborder = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.LabelWrapper _txt = null;
anywheresoftware.b4a.objects.ButtonWrapper _butcontinue = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 208;BA.debugLine="Sub butContinue_Click";
 //BA.debugLineNum = 210;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 211;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 212;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 214;BA.debugLine="If pasonum = 1 Then";
if (_pasonum==1) { 
 //BA.debugLineNum = 216;BA.debugLine="Dim labelborder As Label";
_labelborder = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 217;BA.debugLine="labelborder.Initialize(\"\")";
_labelborder.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 218;BA.debugLine="Activity.AddView(labelborder, 0, Button1.Top, 1";
mostCurrent._activity.AddView((android.view.View)(_labelborder.getObject()),(int) (0),mostCurrent._button1.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),mostCurrent._button1.getHeight());
 //BA.debugLineNum = 219;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 220;BA.debugLine="cd.Initialize2(Colors.ARGB(0,0,0,0),5dip,";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 221;BA.debugLine="labelborder.Background=cd";
_labelborder.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 224;BA.debugLine="Dim txt As Label";
_txt = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 225;BA.debugLine="txt.Initialize(\"\")";
_txt.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 226;BA.debugLine="txt.TextColor = Colors.Black";
_txt.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 227;BA.debugLine="txt.TextSize = 22";
_txt.setTextSize((float) (22));
 //BA.debugLineNum = 228;BA.debugLine="txt.Color = Colors.ARGB(230,255,255,255)";
_txt.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (230),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 229;BA.debugLine="txt.Gravity = Gravity.CENTER_HORIZONTAL";
_txt.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 230;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 231;BA.debugLine="txt.Text = \"Desde este botón, comenzarás la ev";
_txt.setText((Object)("Desde este botón, comenzarás la evaluación de un río, arroyo, lago o estuario!"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 233;BA.debugLine="txt.Text = \"From this button, you'll start a n";
_txt.setText((Object)("From this button, you'll start a new report of a site at a river, lake or estuary!"));
 };
 //BA.debugLineNum = 235;BA.debugLine="Activity.AddView(txt,0,Button2.Top, 100%x, 100%";
mostCurrent._activity.AddView((android.view.View)(_txt.getObject()),(int) (0),mostCurrent._button2.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 237;BA.debugLine="Dim butContinue As Button";
_butcontinue = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 238;BA.debugLine="butContinue.Initialize(\"butContinue\")";
_butcontinue.Initialize(mostCurrent.activityBA,"butContinue");
 //BA.debugLineNum = 239;BA.debugLine="butContinue.Text = \">>\"";
_butcontinue.setText((Object)(">>"));
 //BA.debugLineNum = 240;BA.debugLine="Activity.AddView(butContinue, 50%x, 80%y, 100di";
mostCurrent._activity.AddView((android.view.View)(_butcontinue.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 241;BA.debugLine="pasonum = pasonum + 1";
_pasonum = (int) (_pasonum+1);
 }else if(_pasonum==2) { 
 //BA.debugLineNum = 245;BA.debugLine="Dim labelborder As Label";
_labelborder = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 246;BA.debugLine="labelborder.Initialize(\"\")";
_labelborder.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 247;BA.debugLine="Activity.AddView(labelborder, 0, Button2.Top, 1";
mostCurrent._activity.AddView((android.view.View)(_labelborder.getObject()),(int) (0),mostCurrent._button2.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),mostCurrent._button2.getHeight());
 //BA.debugLineNum = 248;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 249;BA.debugLine="cd.Initialize2(Colors.ARGB(0,0,0,0),5dip,";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 250;BA.debugLine="labelborder.Background=cd";
_labelborder.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 253;BA.debugLine="Dim txt As Label";
_txt = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 254;BA.debugLine="txt.Initialize(\"\")";
_txt.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 255;BA.debugLine="txt.TextColor = Colors.Black";
_txt.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 256;BA.debugLine="txt.TextSize = 22";
_txt.setTextSize((float) (22));
 //BA.debugLineNum = 257;BA.debugLine="txt.Color = Colors.ARGB(230,255,255,255)";
_txt.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (230),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 258;BA.debugLine="txt.Gravity = Gravity.CENTER_HORIZONTAL";
_txt.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 259;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 260;BA.debugLine="txt.Text = \"En la sección 'Aprender Mas' encon";
_txt.setText((Object)("En la sección 'Aprender Mas' encontrarás más información sobre los ecosistemas acuáticos "));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 262;BA.debugLine="txt.Text = \"In this section, you'll find infor";
_txt.setText((Object)("In this section, you'll find information about the aquatic ecosystems"));
 };
 //BA.debugLineNum = 264;BA.debugLine="Activity.AddView(txt,0,Button5.Top, 100%x, 100%";
mostCurrent._activity.AddView((android.view.View)(_txt.getObject()),(int) (0),mostCurrent._button5.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 267;BA.debugLine="Dim butContinue As Button";
_butcontinue = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 268;BA.debugLine="butContinue.Initialize(\"butContinue\")";
_butcontinue.Initialize(mostCurrent.activityBA,"butContinue");
 //BA.debugLineNum = 269;BA.debugLine="butContinue.Text = \">>\"";
_butcontinue.setText((Object)(">>"));
 //BA.debugLineNum = 270;BA.debugLine="Activity.AddView(butContinue, 50%x, 80%y, 100di";
mostCurrent._activity.AddView((android.view.View)(_butcontinue.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 271;BA.debugLine="pasonum = pasonum + 1";
_pasonum = (int) (_pasonum+1);
 }else if(_pasonum==3) { 
 //BA.debugLineNum = 275;BA.debugLine="Dim labelborder As Label";
_labelborder = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 276;BA.debugLine="labelborder.Initialize(\"\")";
_labelborder.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 277;BA.debugLine="Activity.AddView(labelborder, 0, Button5.Top, 1";
mostCurrent._activity.AddView((android.view.View)(_labelborder.getObject()),(int) (0),mostCurrent._button5.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),mostCurrent._button5.getHeight());
 //BA.debugLineNum = 278;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 279;BA.debugLine="cd.Initialize2(Colors.ARGB(0,0,0,0),5dip,";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 280;BA.debugLine="labelborder.Background=cd";
_labelborder.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 283;BA.debugLine="Dim txt As Label";
_txt = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 284;BA.debugLine="txt.Initialize(\"\")";
_txt.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 285;BA.debugLine="txt.TextColor = Colors.Black";
_txt.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 286;BA.debugLine="txt.TextSize = 22";
_txt.setTextSize((float) (22));
 //BA.debugLineNum = 287;BA.debugLine="txt.Color = Colors.ARGB(230,255,255,255)";
_txt.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (230),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 288;BA.debugLine="txt.Gravity = Gravity.CENTER_HORIZONTAL";
_txt.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 289;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 290;BA.debugLine="txt.Text = \"En la sección 'Mini Juegos' encont";
_txt.setText((Object)("En la sección 'Mini Juegos' encontrarás divertidas actividades para los más chicos"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 292;BA.debugLine="txt.Text = \"In the 'Mini games' section, you'l";
_txt.setText((Object)("In the 'Mini games' section, you'll find fun games for the younger kids"));
 };
 //BA.debugLineNum = 294;BA.debugLine="Activity.AddView(txt,0,Button3.Top, 100%x, 100%";
mostCurrent._activity.AddView((android.view.View)(_txt.getObject()),(int) (0),mostCurrent._button3.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 296;BA.debugLine="Dim butContinue As Button";
_butcontinue = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 297;BA.debugLine="butContinue.Initialize(\"butContinue\")";
_butcontinue.Initialize(mostCurrent.activityBA,"butContinue");
 //BA.debugLineNum = 298;BA.debugLine="butContinue.Text = \">>\"";
_butcontinue.setText((Object)(">>"));
 //BA.debugLineNum = 299;BA.debugLine="Activity.AddView(butContinue, 50%x, 80%y, 100di";
mostCurrent._activity.AddView((android.view.View)(_butcontinue.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 300;BA.debugLine="pasonum = pasonum + 1";
_pasonum = (int) (_pasonum+1);
 }else if(_pasonum==4) { 
 //BA.debugLineNum = 305;BA.debugLine="Dim labelborder As Label";
_labelborder = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 306;BA.debugLine="labelborder.Initialize(\"\")";
_labelborder.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 307;BA.debugLine="Activity.AddView(labelborder, 0, Button3.Top, 1";
mostCurrent._activity.AddView((android.view.View)(_labelborder.getObject()),(int) (0),mostCurrent._button3.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (mostCurrent._button3.getHeight()+mostCurrent._button4.getHeight()));
 //BA.debugLineNum = 308;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 309;BA.debugLine="cd.Initialize2(Colors.ARGB(0,0,0,0),5dip,";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 310;BA.debugLine="labelborder.Background=cd";
_labelborder.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 313;BA.debugLine="Dim txt As Label";
_txt = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 314;BA.debugLine="txt.Initialize(\"\")";
_txt.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 315;BA.debugLine="txt.TextColor = Colors.Black";
_txt.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 316;BA.debugLine="txt.TextSize = 22";
_txt.setTextSize((float) (22));
 //BA.debugLineNum = 317;BA.debugLine="txt.Color = Colors.ARGB(230,255,255,255)";
_txt.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (230),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 318;BA.debugLine="txt.Gravity = Gravity.BOTTOM";
_txt.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.BOTTOM);
 //BA.debugLineNum = 319;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 320;BA.debugLine="txt.Text = \"Aqui podrás ver datos de tu perfil";
_txt.setText((Object)("Aqui podrás ver datos de tu perfil y el mapa con tus sitios evaluados anteriormente"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 322;BA.debugLine="txt.Text = \"Here you can access your profile a";
_txt.setText((Object)("Here you can access your profile and a map with the reports you sent previously"));
 };
 //BA.debugLineNum = 324;BA.debugLine="Activity.AddView(txt,0,0, 100%x, Button3.top)";
mostCurrent._activity.AddView((android.view.View)(_txt.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),mostCurrent._button3.getTop());
 //BA.debugLineNum = 327;BA.debugLine="Dim butContinue As Button";
_butcontinue = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 328;BA.debugLine="butContinue.Initialize(\"butContinue\")";
_butcontinue.Initialize(mostCurrent.activityBA,"butContinue");
 //BA.debugLineNum = 329;BA.debugLine="butContinue.Text = \">>\"";
_butcontinue.setText((Object)(">>"));
 //BA.debugLineNum = 330;BA.debugLine="Activity.AddView(butContinue, 50%x, 80%y, 100di";
mostCurrent._activity.AddView((android.view.View)(_butcontinue.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 331;BA.debugLine="pasonum = pasonum + 1";
_pasonum = (int) (_pasonum+1);
 }else if(_pasonum==5) { 
 //BA.debugLineNum = 335;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews -";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 336;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews -";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 337;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews -";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 339;BA.debugLine="Main.firstuse = \"No\"";
mostCurrent._main._firstuse = "No";
 //BA.debugLineNum = 340;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 341;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 342;BA.debugLine="Map1.Put(\"strUserID\", Main.strUserID)";
_map1.Put((Object)("strUserID"),(Object)(mostCurrent._main._struserid));
 //BA.debugLineNum = 343;BA.debugLine="Map1.Put(\"username\", Main.username)";
_map1.Put((Object)("username"),(Object)(mostCurrent._main._username));
 //BA.debugLineNum = 344;BA.debugLine="Map1.Put(\"msjprivadouser\", Main.msjprivadouser)";
_map1.Put((Object)("msjprivadouser"),(Object)(mostCurrent._main._msjprivadouser));
 //BA.debugLineNum = 345;BA.debugLine="Map1.Put(\"pass\", Main.pass)";
_map1.Put((Object)("pass"),(Object)(mostCurrent._main._pass));
 //BA.debugLineNum = 346;BA.debugLine="Map1.Put(\"lang\", Main.lang)";
_map1.Put((Object)("lang"),(Object)(mostCurrent._main._lang));
 //BA.debugLineNum = 347;BA.debugLine="Map1.Put(\"firstuse\", Main.firstuse)";
_map1.Put((Object)("firstuse"),(Object)(mostCurrent._main._firstuse));
 //BA.debugLineNum = 348;BA.debugLine="File.WriteMap(File.DirInternal, \"config.txt\"";
anywheresoftware.b4a.keywords.Common.File.WriteMap(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",_map1);
 };
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 554;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 555;BA.debugLine="Main.fotopath0 = \"\"";
mostCurrent._main._fotopath0 = "";
 //BA.debugLineNum = 556;BA.debugLine="Main.fotopath1 = \"\"";
mostCurrent._main._fotopath1 = "";
 //BA.debugLineNum = 557;BA.debugLine="Main.fotopath2 = \"\"";
mostCurrent._main._fotopath2 = "";
 //BA.debugLineNum = 558;BA.debugLine="Main.fotopath3 = \"\"";
mostCurrent._main._fotopath3 = "";
 //BA.debugLineNum = 559;BA.debugLine="frmEvaluacion.evaluaciondone = False";
mostCurrent._frmevaluacion._evaluaciondone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 560;BA.debugLine="frmEvaluacion.fotosdone = False";
mostCurrent._frmevaluacion._fotosdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 561;BA.debugLine="frmEvaluacion.notasdone = False";
mostCurrent._frmevaluacion._notasdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 562;BA.debugLine="frmEvaluacion.otrosdone = False";
mostCurrent._frmevaluacion._otrosdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 563;BA.debugLine="frmEvaluacion.currentproject = \"\"";
mostCurrent._frmevaluacion._currentproject = "";
 //BA.debugLineNum = 565;BA.debugLine="utilidades.SabiasQue";
mostCurrent._utilidades._sabiasque(mostCurrent.activityBA);
 //BA.debugLineNum = 566;BA.debugLine="StartActivity(frmEvaluacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmevaluacion.getObject()));
 //BA.debugLineNum = 567;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 578;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 579;BA.debugLine="StartActivity(frmAprender)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmaprender.getObject()));
 //BA.debugLineNum = 580;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 515;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 516;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 518;BA.debugLine="frmLocalizacion.formorigen = \"Principal\"";
mostCurrent._frmlocalizacion._formorigen = "Principal";
 //BA.debugLineNum = 519;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 }else {
 //BA.debugLineNum = 521;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 522;BA.debugLine="ToastMessageShow(\"Necesitas internet para ver t";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Necesitas internet para ver tus contribuciones anteriores en el mapa",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 524;BA.debugLine="ToastMessageShow(\"You need internet to view the";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You need internet to view the map",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 527;BA.debugLine="End Sub";
return "";
}
public static String  _button4_click() throws Exception{
 //BA.debugLineNum = 484;BA.debugLine="Sub Button4_Click";
 //BA.debugLineNum = 485;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 486;BA.debugLine="StartActivity(frmPerfil)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmperfil.getObject()));
 }else {
 //BA.debugLineNum = 488;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 489;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No ha iniciado sesión aún",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 491;BA.debugLine="ToastMessageShow(\"You are not logged in\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You are not logged in",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 494;BA.debugLine="End Sub";
return "";
}
public static String  _button5_click() throws Exception{
 //BA.debugLineNum = 591;BA.debugLine="Sub Button5_Click";
 //BA.debugLineNum = 592;BA.debugLine="StartActivity(frmMiniGames)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmminigames.getObject()));
 //BA.debugLineNum = 593;BA.debugLine="End Sub";
return "";
}
public static String  _checkpuntos() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _getpuntos = null;
 //BA.debugLineNum = 601;BA.debugLine="Sub CheckPuntos";
 //BA.debugLineNum = 602;BA.debugLine="Dim GetPuntos As HttpJob";
_getpuntos = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 603;BA.debugLine="GetPuntos.Initialize(\"GetPuntos\",Me)";
_getpuntos._initialize(processBA,"GetPuntos",frmprincipal.getObject());
 //BA.debugLineNum = 604;BA.debugLine="GetPuntos.Download2(\"http://www.app-ear.com.ar/co";
_getpuntos._download2("http://www.app-ear.com.ar/connect/getpuntos.php",new String[]{"user_id",mostCurrent._main._struserid});
 //BA.debugLineNum = 605;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private lblPuntosTotales As Label";
mostCurrent._lblpuntostotales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private lblNumEvals As Label";
mostCurrent._lblnumevals = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private pgbNivel As ProgressBar";
mostCurrent._pgbnivel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblNivel As Label";
mostCurrent._lblnivel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private Button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private Button4 As Button";
mostCurrent._button4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private Button5 As Button";
mostCurrent._button5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim pasonum As Int";
_pasonum = 0;
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String[] _actarray = null;
double _nivelfull = 0;
int _nivel = 0;
double _resto = 0;
 //BA.debugLineNum = 606;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 607;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 608;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 609;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 610;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring();
 //BA.debugLineNum = 611;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 612;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 613;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 616;BA.debugLine="If Job.JobName = \"GetPuntos\" Then";
if ((_job._jobname).equals("GetPuntos")) { 
 //BA.debugLineNum = 617;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 618;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 619;BA.debugLine="ToastMessageShow(\"Error recuperando los punt";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error recuperando los puntos",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 621;BA.debugLine="ToastMessageShow(\"Error recovering your poin";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error recovering your points",anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("GetPuntos OK")) { 
 //BA.debugLineNum = 625;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 626;BA.debugLine="Dim actarray() As String = Regex.Split(\"-\", a";
_actarray = anywheresoftware.b4a.keywords.Common.Regex.Split("-",_act);
 //BA.debugLineNum = 627;BA.debugLine="Main.puntostotales = actarray(0)";
mostCurrent._main._puntostotales = (int)(Double.parseDouble(_actarray[(int) (0)]));
 //BA.debugLineNum = 628;BA.debugLine="Main.puntosnumfotos = actarray(1)";
mostCurrent._main._puntosnumfotos = (int)(Double.parseDouble(_actarray[(int) (1)]));
 //BA.debugLineNum = 629;BA.debugLine="Main.puntosnumevals = actarray(2)";
mostCurrent._main._puntosnumevals = (int)(Double.parseDouble(_actarray[(int) (2)]));
 //BA.debugLineNum = 630;BA.debugLine="Main.numevalsok = actarray(3)";
mostCurrent._main._numevalsok = (int)(Double.parseDouble(_actarray[(int) (3)]));
 //BA.debugLineNum = 631;BA.debugLine="Main.numriollanura = actarray(4)";
mostCurrent._main._numriollanura = (int)(Double.parseDouble(_actarray[(int) (4)]));
 //BA.debugLineNum = 632;BA.debugLine="Main.numriomontana = actarray(5)";
mostCurrent._main._numriomontana = (int)(Double.parseDouble(_actarray[(int) (5)]));
 //BA.debugLineNum = 633;BA.debugLine="Main.numlaguna = actarray(6)";
mostCurrent._main._numlaguna = (int)(Double.parseDouble(_actarray[(int) (6)]));
 //BA.debugLineNum = 634;BA.debugLine="Main.numestuario = actarray(7)";
mostCurrent._main._numestuario = (int)(Double.parseDouble(_actarray[(int) (7)]));
 //BA.debugLineNum = 635;BA.debugLine="Main.numshares = actarray(8)";
mostCurrent._main._numshares = (int)(Double.parseDouble(_actarray[(int) (8)]));
 //BA.debugLineNum = 638;BA.debugLine="lblPuntosTotales.Text = Main.puntostotales";
mostCurrent._lblpuntostotales.setText((Object)(mostCurrent._main._puntostotales));
 //BA.debugLineNum = 639;BA.debugLine="lblNumEvals.Text = Main.numevalsok";
mostCurrent._lblnumevals.setText((Object)(mostCurrent._main._numevalsok));
 //BA.debugLineNum = 641;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 642;BA.debugLine="Dim nivel As Int";
_nivel = 0;
 //BA.debugLineNum = 643;BA.debugLine="Dim resto As Double";
_resto = 0;
 //BA.debugLineNum = 644;BA.debugLine="nivelfull = (Sqrt(Main.puntostotales) * 0.25";
_nivelfull = (anywheresoftware.b4a.keywords.Common.Sqrt(mostCurrent._main._puntostotales)*0.25);
 //BA.debugLineNum = 645;BA.debugLine="nivel = Floor(nivelfull)";
_nivel = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 646;BA.debugLine="resto = Round2(Abs(nivelfull - nivel) * 100,";
_resto = anywheresoftware.b4a.keywords.Common.Round2(anywheresoftware.b4a.keywords.Common.Abs(_nivelfull-_nivel)*100,(int) (0));
 //BA.debugLineNum = 647;BA.debugLine="pgbNivel.Progress = resto";
mostCurrent._pgbnivel.setProgress((int) (_resto));
 //BA.debugLineNum = 648;BA.debugLine="lblNivel.Text = nivel";
mostCurrent._lblnivel.setText((Object)(_nivel));
 };
 };
 }else {
 //BA.debugLineNum = 653;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: "+_job._errormessage,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 654;BA.debugLine="Msgbox(Job.ErrorMessage, \"Oops!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(_job._errormessage,"Oops!",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 656;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 657;BA.debugLine="End Sub";
return "";
}
public static String  _lblnumevals_click() throws Exception{
 //BA.debugLineNum = 673;BA.debugLine="Sub lblNumEvals_Click";
 //BA.debugLineNum = 674;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 675;BA.debugLine="Msgbox(\"Las evaluaciones que envías serán valida";
anywheresoftware.b4a.keywords.Common.Msgbox("Las evaluaciones que envías serán validadas por expertos","Validación",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 677;BA.debugLine="Msgbox(\"The reports you send will be validated b";
anywheresoftware.b4a.keywords.Common.Msgbox("The reports you send will be validated by experts","Validation",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 679;BA.debugLine="End Sub";
return "";
}
public static String  _lblpuntostotales_click() throws Exception{
 //BA.debugLineNum = 665;BA.debugLine="Sub lblPuntosTotales_Click";
 //BA.debugLineNum = 666;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 667;BA.debugLine="Msgbox(\"Los puntos te sirven para subir de nivel";
anywheresoftware.b4a.keywords.Common.Msgbox("Los puntos te sirven para subir de nivel, y ganar medallas!","Puntos",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 669;BA.debugLine="Msgbox(\"Points will help you level up, and win m";
anywheresoftware.b4a.keywords.Common.Msgbox("Points will help you level up, and win medals!","Points",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 672;BA.debugLine="End Sub";
return "";
}
public static String  _mnuabout_click() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub mnuAbout_Click";
 //BA.debugLineNum = 143;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _mnucerrarsesion_click() throws Exception{
String _msg = "";
 //BA.debugLineNum = 117;BA.debugLine="Sub mnuCerrarSesion_Click";
 //BA.debugLineNum = 118;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 120;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 121;BA.debugLine="msg = utilidades.Mensaje(\"Seguro?\", \"Desea cerra";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Seguro?","Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!","Si, tengo internet","No, estoy en el campo","");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 123;BA.debugLine="msg = utilidades.Mensaje(\"Sure?\", \"Are you sure";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Sure?","Are you sure you want to log off? To log back in, you will need an internet connection!","Yes, I have internet","","No, I have no internet");
 };
 //BA.debugLineNum = 126;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 127;BA.debugLine="Main.strUserID = \"\"";
mostCurrent._main._struserid = "";
 //BA.debugLineNum = 128;BA.debugLine="Main.strUserName = \"\"";
mostCurrent._main._strusername = "";
 //BA.debugLineNum = 129;BA.debugLine="Main.strUserLocation = \"\"";
mostCurrent._main._struserlocation = "";
 //BA.debugLineNum = 130;BA.debugLine="Main.strUserEmail = \"\"";
mostCurrent._main._struseremail = "";
 //BA.debugLineNum = 131;BA.debugLine="Main.strUserOrg = \"\"";
mostCurrent._main._struserorg = "";
 //BA.debugLineNum = 132;BA.debugLine="Main.puntostotales = 0";
mostCurrent._main._puntostotales = (int) (0);
 //BA.debugLineNum = 133;BA.debugLine="Main.numfotosok = 0";
mostCurrent._main._numfotosok = (int) (0);
 //BA.debugLineNum = 134;BA.debugLine="Main.puntosnumfotos = 0";
mostCurrent._main._puntosnumfotos = (int) (0);
 //BA.debugLineNum = 135;BA.debugLine="Main.numevalsok = 0";
mostCurrent._main._numevalsok = (int) (0);
 //BA.debugLineNum = 136;BA.debugLine="Main.puntosnumevals = 0";
mostCurrent._main._puntosnumevals = (int) (0);
 //BA.debugLineNum = 137;BA.debugLine="Main.username = \"\"";
mostCurrent._main._username = "";
 //BA.debugLineNum = 138;BA.debugLine="StartActivity(Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._main.getObject()));
 };
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _mnunoticias_click() throws Exception{
anywheresoftware.b4a.phone.Phone.PhoneIntents _p = null;
 //BA.debugLineNum = 112;BA.debugLine="Sub mnuNoticias_Click";
 //BA.debugLineNum = 113;BA.debugLine="Dim p As PhoneIntents";
_p = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 114;BA.debugLine="StartActivity(p.OpenBrowser(\"http://www.app-ea";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_p.OpenBrowser("http://www.app-ear.com.ar/#noticias")));
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _mnuvermiperfil_click() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub mnuVermiperfil_Click";
 //BA.debugLineNum = 101;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 102;BA.debugLine="StartActivity(frmPerfil)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmperfil.getObject()));
 }else {
 //BA.debugLineNum = 104;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 105;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No ha iniciado sesión aún",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 107;BA.debugLine="ToastMessageShow(\"You are not logged in\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You are not logged in",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim recargaPuntos As Boolean";
_recargapuntos = false;
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _recargar() throws Exception{
 //BA.debugLineNum = 358;BA.debugLine="Sub recargar";
 //BA.debugLineNum = 359;BA.debugLine="If recargaPuntos = True Then";
if (_recargapuntos==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 360;BA.debugLine="CheckPuntos";
_checkpuntos();
 }else {
 //BA.debugLineNum = 362;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 363;BA.debugLine="startBienvienido(False, False)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.False,anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 365;BA.debugLine="startBienvienido(True, False)";
_startbienvienido(anywheresoftware.b4a.keywords.Common.True,anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
return "";
}
public static String  _setprogressdrawable(anywheresoftware.b4a.objects.ProgressBarWrapper _p,Object _drawable,Object _backgrounddrawable) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _clipdrawable = null;
 //BA.debugLineNum = 147;BA.debugLine="Sub SetProgressDrawable(p As ProgressBar, drawable";
 //BA.debugLineNum = 148;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 149;BA.debugLine="Dim clipDrawable As Object";
_clipdrawable = new Object();
 //BA.debugLineNum = 150;BA.debugLine="clipDrawable = r.CreateObject2(\"android.graphic";
_clipdrawable = _r.CreateObject2("android.graphics.drawable.ClipDrawable",new Object[]{_drawable,(Object)(anywheresoftware.b4a.keywords.Common.Gravity.LEFT),(Object)(1)},new String[]{"android.graphics.drawable.Drawable","java.lang.int","java.lang.int"});
 //BA.debugLineNum = 153;BA.debugLine="r.Target = p";
_r.Target = (Object)(_p.getObject());
 //BA.debugLineNum = 154;BA.debugLine="r.Target = r.RunMethod(\"getProgressDrawable\") '";
_r.Target = _r.RunMethod("getProgressDrawable");
 //BA.debugLineNum = 155;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Ar";
_r.RunMethod4("setDrawableByLayerId",new Object[]{(Object)(16908288),_backgrounddrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 158;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Ar";
_r.RunMethod4("setDrawableByLayerId",new Object[]{_r.GetStaticField("android.R$id","progress"),_clipdrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 162;BA.debugLine="End Sub";
return "";
}
public static String  _startbienvienido(boolean _online,boolean _firsttime) throws Exception{
double _nivelfull = 0;
int _nivel = 0;
double _resto = 0;
anywheresoftware.b4a.objects.drawable.GradientDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
String _msjpriv = "";
boolean _hayevals = false;
anywheresoftware.b4a.objects.collections.List _filelist = null;
int _n = 0;
String _file1 = "";
String _msg = "";
 //BA.debugLineNum = 372;BA.debugLine="Sub startBienvienido(online As Boolean, firsttime";
 //BA.debugLineNum = 375;BA.debugLine="If online = True Then";
if (_online==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 377;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 378;BA.debugLine="lblPuntosTotales.Text = Main.puntostotales & \"";
mostCurrent._lblpuntostotales.setText((Object)(BA.NumberToString(mostCurrent._main._puntostotales)+" puntos"));
 //BA.debugLineNum = 379;BA.debugLine="lblNumEvals.Text = Main.numevalsok & \" evaluac";
mostCurrent._lblnumevals.setText((Object)(BA.NumberToString(mostCurrent._main._numevalsok)+" evaluaciones validadas"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 381;BA.debugLine="lblPuntosTotales.Text = Main.puntostotales & \"";
mostCurrent._lblpuntostotales.setText((Object)(BA.NumberToString(mostCurrent._main._puntostotales)+" points"));
 //BA.debugLineNum = 382;BA.debugLine="lblNumEvals.Text = Main.numevalsok & \" valid e";
mostCurrent._lblnumevals.setText((Object)(BA.NumberToString(mostCurrent._main._numevalsok)+" valid evaluations"));
 };
 //BA.debugLineNum = 386;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 387;BA.debugLine="Dim nivel As Int";
_nivel = 0;
 //BA.debugLineNum = 388;BA.debugLine="Dim resto As Double";
_resto = 0;
 //BA.debugLineNum = 389;BA.debugLine="nivelfull = (Sqrt(Main.puntostotales) * 0.25)";
_nivelfull = (anywheresoftware.b4a.keywords.Common.Sqrt(mostCurrent._main._puntostotales)*0.25);
 //BA.debugLineNum = 390;BA.debugLine="nivel = Floor(nivelfull)";
_nivel = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 391;BA.debugLine="resto = Round2(Abs(nivelfull - nivel) * 100,0)";
_resto = anywheresoftware.b4a.keywords.Common.Round2(anywheresoftware.b4a.keywords.Common.Abs(_nivelfull-_nivel)*100,(int) (0));
 //BA.debugLineNum = 392;BA.debugLine="pgbNivel.Progress = resto";
mostCurrent._pgbnivel.setProgress((int) (_resto));
 //BA.debugLineNum = 393;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 394;BA.debugLine="lblNivel.Text = \"Nivel \" & nivel";
mostCurrent._lblnivel.setText((Object)("Nivel "+BA.NumberToString(_nivel)));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 396;BA.debugLine="lblNivel.Text = \"Level \" & nivel";
mostCurrent._lblnivel.setText((Object)("Level "+BA.NumberToString(_nivel)));
 };
 //BA.debugLineNum = 400;BA.debugLine="Dim gd As GradientDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.GradientDrawable();
 //BA.debugLineNum = 401;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 402;BA.debugLine="gd.Initialize(\"TOP_BOTTOM\", Array As Int(Colors";
_gd.Initialize(BA.getEnumFromString(android.graphics.drawable.GradientDrawable.Orientation.class,"TOP_BOTTOM"),new int[]{anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.White});
 //BA.debugLineNum = 403;BA.debugLine="cd.Initialize(Colors.ARGB(70,255,255,255), 1dip";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (70),(int) (255),(int) (255),(int) (255)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 404;BA.debugLine="SetProgressDrawable(pgbNivel, gd, cd)";
_setprogressdrawable(mostCurrent._pgbnivel,(Object)(_gd.getObject()),(Object)(_cd.getObject()));
 //BA.debugLineNum = 408;BA.debugLine="If Main.msjprivadouser <> \"None\" And Main.msjpr";
if ((mostCurrent._main._msjprivadouser).equals("None") == false && (mostCurrent._main._msjprivadouser).equals("") == false && _firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 409;BA.debugLine="Dim msjpriv As String";
_msjpriv = "";
 //BA.debugLineNum = 410;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 411;BA.debugLine="msjpriv = Msgbox2(Main.msjprivadouser, \"Notif";
_msjpriv = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(mostCurrent._main._msjprivadouser,"Notificación privada","Ok","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 413;BA.debugLine="msjpriv = Msgbox2(Main.msjprivadouser, \"Priva";
_msjpriv = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(mostCurrent._main._msjprivadouser,"Private notification","Ok","","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 415;BA.debugLine="If msjpriv = DialogResponse.POSITIVE Then";
if ((_msjpriv).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 416;BA.debugLine="File.WriteString(File.DirInternal, \"config.tx";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"config.txt",mostCurrent._main._struserid+"@"+mostCurrent._main._username+"@"+mostCurrent._main._pass+"@"+mostCurrent._main._msjprivadouser);
 };
 };
 //BA.debugLineNum = 421;BA.debugLine="Dim hayevals As Boolean";
_hayevals = false;
 //BA.debugLineNum = 422;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 423;BA.debugLine="Dim n As Int";
_n = 0;
 //BA.debugLineNum = 424;BA.debugLine="Dim file1 As String";
_file1 = "";
 //BA.debugLineNum = 425;BA.debugLine="hayevals = False";
_hayevals = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 426;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", \"\") =";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 427;BA.debugLine="File.MakeDir(Main.savedir, \"AppEAR\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(mostCurrent._main._savedir,"AppEAR");
 };
 //BA.debugLineNum = 429;BA.debugLine="fileList = File.ListFiles (Main.savedir & \"/App";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._main._savedir+"/AppEAR/");
 //BA.debugLineNum = 430;BA.debugLine="fileList.Sort (True)";
_filelist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 431;BA.debugLine="For n = 0 To fileList.Size-1";
{
final int step47 = 1;
final int limit47 = (int) (_filelist.getSize()-1);
for (_n = (int) (0) ; (step47 > 0 && _n <= limit47) || (step47 < 0 && _n >= limit47); _n = ((int)(0 + _n + step47)) ) {
 //BA.debugLineNum = 432;BA.debugLine="file1 = fileList.Get (n)";
_file1 = BA.ObjectToString(_filelist.Get(_n));
 //BA.debugLineNum = 433;BA.debugLine="If file1.StartsWith(Main.username) Then";
if (_file1.startsWith(mostCurrent._main._username)) { 
 //BA.debugLineNum = 434;BA.debugLine="hayevals = True";
_hayevals = anywheresoftware.b4a.keywords.Common.True;
 };
 }
};
 //BA.debugLineNum = 439;BA.debugLine="If hayevals = True And firsttime = True Then";
if (_hayevals==anywheresoftware.b4a.keywords.Common.True && _firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 440;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 441;BA.debugLine="Dim msg As String = utilidades.Mensaje(\"Atenc";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Atención","Tiene reportes sin enviar. Desea hacerlo ahora?","Si, las enviaré ahora","","No, las enviaré luego");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 443;BA.debugLine="Dim msg As String = utilidades.Mensaje(\"Atten";
_msg = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Attention","You have unsent reports. Do you wish to upload them now","Yes, I want to upload them now","","No, I will upload them later");
 };
 //BA.debugLineNum = 445;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 446;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 447;BA.debugLine="StartActivity(frmPerfil)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmperfil.getObject()));
 }else {
 //BA.debugLineNum = 449;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 450;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("No ha iniciado sesión aún",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 452;BA.debugLine="ToastMessageShow(\"You are not logged in\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You are not logged in",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 };
 }else {
 //BA.debugLineNum = 458;BA.debugLine="lblPuntosTotales.Text = \"?\"";
mostCurrent._lblpuntostotales.setText((Object)("?"));
 //BA.debugLineNum = 459;BA.debugLine="lblNumEvals.Text = \"?\"";
mostCurrent._lblnumevals.setText((Object)("?"));
 //BA.debugLineNum = 460;BA.debugLine="lblNivel.Text = \"?\"";
mostCurrent._lblnivel.setText((Object)("?"));
 //BA.debugLineNum = 461;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 462;BA.debugLine="Main.actionbarenabled = True";
mostCurrent._main._actionbarenabled = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 464;BA.debugLine="End Sub";
return "";
}
public static String  _starttutorial() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _labelborder = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
anywheresoftware.b4a.objects.LabelWrapper _txt = null;
anywheresoftware.b4a.objects.ButtonWrapper _butcontinue = null;
 //BA.debugLineNum = 170;BA.debugLine="Sub StartTutorial";
 //BA.debugLineNum = 171;BA.debugLine="pasonum = 0";
_pasonum = (int) (0);
 //BA.debugLineNum = 174;BA.debugLine="Dim labelborder As Label";
_labelborder = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 175;BA.debugLine="labelborder.Initialize(\"\")";
_labelborder.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 176;BA.debugLine="Activity.AddView(labelborder, 0, Label1.Top, 100%";
mostCurrent._activity.AddView((android.view.View)(_labelborder.getObject()),(int) (0),mostCurrent._label1.getTop(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),mostCurrent._label1.getHeight());
 //BA.debugLineNum = 177;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 178;BA.debugLine="cd.Initialize2(Colors.ARGB(0,0,0,0),5dip,2dip,Col";
_cd.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 179;BA.debugLine="labelborder.Background=cd";
_labelborder.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 182;BA.debugLine="Dim txt As Label";
_txt = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 183;BA.debugLine="txt.Initialize(\"\")";
_txt.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 184;BA.debugLine="txt.TextColor = Colors.Black";
_txt.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 185;BA.debugLine="txt.TextSize = 22";
_txt.setTextSize((float) (22));
 //BA.debugLineNum = 186;BA.debugLine="txt.Color = Colors.ARGB(230,255,255,255)";
_txt.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (230),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 187;BA.debugLine="txt.Gravity = Gravity.CENTER_HORIZONTAL";
_txt.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 188;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 189;BA.debugLine="txt.Text = \"Aqui arriba se muestran tus puntos a";
_txt.setText((Object)("Aqui arriba se muestran tus puntos actuales, tus evaluaciones validadas y tu nivel actual"));
 //BA.debugLineNum = 190;BA.debugLine="txt.Text = txt.Text & CRLF";
_txt.setText((Object)(_txt.getText()+anywheresoftware.b4a.keywords.Common.CRLF));
 //BA.debugLineNum = 191;BA.debugLine="txt.Text = txt.Text & CRLF";
_txt.setText((Object)(_txt.getText()+anywheresoftware.b4a.keywords.Common.CRLF));
 //BA.debugLineNum = 192;BA.debugLine="txt.Text = txt.Text & \"Mientras mas evaluaciones";
_txt.setText((Object)(_txt.getText()+"Mientras mas evaluaciones envíes, más puntos tendrás!"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 194;BA.debugLine="txt.Text = \"Up here you can see your current poi";
_txt.setText((Object)("Up here you can see your current points, your validated reports and your current level"));
 //BA.debugLineNum = 195;BA.debugLine="txt.Text = txt.Text & CRLF";
_txt.setText((Object)(_txt.getText()+anywheresoftware.b4a.keywords.Common.CRLF));
 //BA.debugLineNum = 196;BA.debugLine="txt.Text = txt.Text & CRLF";
_txt.setText((Object)(_txt.getText()+anywheresoftware.b4a.keywords.Common.CRLF));
 //BA.debugLineNum = 197;BA.debugLine="txt.Text = txt.Text & \"You'll win points by send";
_txt.setText((Object)(_txt.getText()+"You'll win points by sending reports!"));
 };
 //BA.debugLineNum = 199;BA.debugLine="Activity.AddView(txt,0,Label1.Height, 100%x, 100%";
mostCurrent._activity.AddView((android.view.View)(_txt.getObject()),(int) (0),mostCurrent._label1.getHeight(),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 202;BA.debugLine="Dim butContinue As Button";
_butcontinue = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 203;BA.debugLine="butContinue.Initialize(\"butContinue\")";
_butcontinue.Initialize(mostCurrent.activityBA,"butContinue");
 //BA.debugLineNum = 204;BA.debugLine="butContinue.Text = \">>\"";
_butcontinue.setText((Object)(">>"));
 //BA.debugLineNum = 205;BA.debugLine="Activity.AddView(butContinue, 50%x, 80%y, 100dip,";
mostCurrent._activity.AddView((android.view.View)(_butcontinue.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (100)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 206;BA.debugLine="pasonum = pasonum + 1";
_pasonum = (int) (_pasonum+1);
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 84;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 85;BA.debugLine="lblNivel.Text = \"Level\"";
mostCurrent._lblnivel.setText((Object)("Level"));
 //BA.debugLineNum = 86;BA.debugLine="Button1.Text = \"New report\"";
mostCurrent._button1.setText((Object)("New report"));
 //BA.debugLineNum = 87;BA.debugLine="Button2.Text = \"Learn playing\"";
mostCurrent._button2.setText((Object)("Learn playing"));
 //BA.debugLineNum = 88;BA.debugLine="Button5.Text = \"Mini-games\"";
mostCurrent._button5.setText((Object)("Mini-games"));
 //BA.debugLineNum = 89;BA.debugLine="Button3.Text = \"My map\"";
mostCurrent._button3.setText((Object)("My map"));
 //BA.debugLineNum = 90;BA.debugLine="Button4.Text = \"My profile\"";
mostCurrent._button4.setText((Object)("My profile"));
 };
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
}
