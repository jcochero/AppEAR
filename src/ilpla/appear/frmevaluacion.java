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

public class frmevaluacion extends Activity implements B4AActivity{
	public static frmevaluacion mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmevaluacion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmevaluacion).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmevaluacion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmevaluacion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmevaluacion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmevaluacion) Resume **");
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
		return frmevaluacion.class;
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
        BA.LogInfo("** Activity (frmevaluacion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmevaluacion) Resume **");
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
public static boolean _evaluaciondone = false;
public static boolean _fotosdone = false;
public static boolean _otrosdone = false;
public static boolean _notasdone = false;
public static String _currentproject = "";
public static String _formorigen = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _chktesthabitat = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _chkfotos = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _chkotros = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _chknotas = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnotas = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgsitio4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkaves = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkanimales = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkalgas = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chktortugas = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkranas = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkpeces = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkculebras = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chklibelulas = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkcaracoles = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkmosquitos = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfotos = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrchecklist = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnevaluacion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnotas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblsubtitulo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnok = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmprincipal _frmprincipal = null;
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
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 54;BA.debugLine="Activity.LoadLayout(\"frmEvaluacion\")";
mostCurrent._activity.LoadLayout("frmEvaluacion",mostCurrent.activityBA);
 //BA.debugLineNum = 57;BA.debugLine="CheckEstado";
_checkestado();
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 69;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 70;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 71;BA.debugLine="If Msgbox2(\"Volver al inicio?\", \"SALIR\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Volver al inicio?","SALIR","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 72;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 73;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 75;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 78;BA.debugLine="If Msgbox2(\"Back to the beginning?\", \"Exi";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Back to the beginning?","Exit","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 79;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 80;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 82;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 61;BA.debugLine="CheckEstado";
_checkestado();
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return "";
}
public static String  _btnenviar_click() throws Exception{
 //BA.debugLineNum = 574;BA.debugLine="Sub btnEnviar_Click";
 //BA.debugLineNum = 575;BA.debugLine="If evaluaciondone = False Then";
if (_evaluaciondone==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 576;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 577;BA.debugLine="Msgbox(\"Aún no has completado la evaluación!\",";
anywheresoftware.b4a.keywords.Common.Msgbox("Aún no has completado la evaluación!","Completa la evaluación",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 579;BA.debugLine="Msgbox(\"You have not completed the report!\", \"F";
anywheresoftware.b4a.keywords.Common.Msgbox("You have not completed the report!","Fill the report",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 581;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 583;BA.debugLine="If fotosdone = False Then";
if (_fotosdone==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 584;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 585;BA.debugLine="Msgbox(\"Aún no has sacado las fotografías!\", \"S";
anywheresoftware.b4a.keywords.Common.Msgbox("Aún no has sacado las fotografías!","Saca las fotos",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 587;BA.debugLine="Msgbox(\"You haven't taken any photos yet!\", \"Ta";
anywheresoftware.b4a.keywords.Common.Msgbox("You haven't taken any photos yet!","Take a photo",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 589;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 592;BA.debugLine="If Main.modooffline = True Then";
if (mostCurrent._main._modooffline==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 593;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 594;BA.debugLine="Msgbox(\"Esta trabajando en modo offline. El arc";
anywheresoftware.b4a.keywords.Common.Msgbox("Esta trabajando en modo offline. El archivo se guardará para que lo pueda enviar luego desde 'Mi Perfil'","Modo offline",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 596;BA.debugLine="Msgbox(\"You are working offline. The report wil";
anywheresoftware.b4a.keywords.Common.Msgbox("You are working offline. The report will be saved and you can later send it from 'My Profile'","Offline mode",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 599;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 600;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 601;BA.debugLine="StartActivity(frmPrincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmprincipal.getObject()));
 //BA.debugLineNum = 602;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 605;BA.debugLine="StartActivity(EnvioArchivos)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._envioarchivos.getObject()));
 //BA.debugLineNum = 606;BA.debugLine="End Sub";
return "";
}
public static String  _btnevaluacion_click() throws Exception{
String _msgconf = "";
 //BA.debugLineNum = 249;BA.debugLine="Sub btnEvaluacion_Click";
 //BA.debugLineNum = 250;BA.debugLine="If evaluaciondone = True Then";
if (_evaluaciondone==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 251;BA.debugLine="Dim msgconf As String";
_msgconf = "";
 //BA.debugLineNum = 252;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 253;BA.debugLine="msgconf = Msgbox2(\"Ya ha realizado la evaluació";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Ya ha realizado la evaluación del hábitat. Si prosigue, borrará la evaluación anterior. Seguro?","Cuidado!","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 255;BA.debugLine="msgconf = Msgbox2(\"You already completed a repo";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("You already completed a report. If you proceed, you will erase the previous report. Sure?","Careful now!","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 257;BA.debugLine="If msgconf=DialogResponse.POSITIVE Then";
if ((_msgconf).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 258;BA.debugLine="StartActivity(frmTipoRio)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmtiporio.getObject()));
 }else {
 //BA.debugLineNum = 260;BA.debugLine="Return";
if (true) return "";
 };
 }else {
 //BA.debugLineNum = 263;BA.debugLine="StartActivity(frmTipoRio)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmtiporio.getObject()));
 };
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static String  _btnfotos_click() throws Exception{
int _fotonumlibre = 0;
 //BA.debugLineNum = 275;BA.debugLine="Sub btnFotos_Click";
 //BA.debugLineNum = 277;BA.debugLine="Dim fotonumlibre As Int";
_fotonumlibre = 0;
 //BA.debugLineNum = 278;BA.debugLine="If Main.fotopath0 = \"\" Then";
if ((mostCurrent._main._fotopath0).equals("")) { 
 //BA.debugLineNum = 279;BA.debugLine="fotonumlibre = 1";
_fotonumlibre = (int) (1);
 }else if((mostCurrent._main._fotopath1).equals("")) { 
 //BA.debugLineNum = 281;BA.debugLine="fotonumlibre = 2";
_fotonumlibre = (int) (2);
 }else if((mostCurrent._main._fotopath2).equals("")) { 
 //BA.debugLineNum = 283;BA.debugLine="fotonumlibre = 3";
_fotonumlibre = (int) (3);
 }else if((mostCurrent._main._fotopath3).equals("")) { 
 //BA.debugLineNum = 285;BA.debugLine="fotonumlibre = 4";
_fotonumlibre = (int) (4);
 }else {
 //BA.debugLineNum = 287;BA.debugLine="fotonumlibre = 0";
_fotonumlibre = (int) (0);
 };
 //BA.debugLineNum = 290;BA.debugLine="If fotonumlibre = 0 Then";
if (_fotonumlibre==0) { 
 //BA.debugLineNum = 291;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 292;BA.debugLine="Msgbox(\"Sólo puedes tomar hasta 4 fotos de cada";
anywheresoftware.b4a.keywords.Common.Msgbox("Sólo puedes tomar hasta 4 fotos de cada sitio. Borre alguna de las anteriores para tomar una nueva!","Cuidado!",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 294;BA.debugLine="Msgbox(\"You can only upload up to 4 photos of e";
anywheresoftware.b4a.keywords.Common.Msgbox("You can only upload up to 4 photos of each site. To take a new one, erase a previous one!","Careful!",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 297;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 300;BA.debugLine="StartActivity(frmCamara)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmcamara.getObject()));
 //BA.debugLineNum = 303;BA.debugLine="End Sub";
return "";
}
public static String  _btnnotas_click() throws Exception{
String _filenotas = "";
String[] _notasarray = null;
 //BA.debugLineNum = 404;BA.debugLine="Sub btnNotas_Click";
 //BA.debugLineNum = 405;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 406;BA.debugLine="Activity.LoadLayout(\"layNotas\")";
mostCurrent._activity.LoadLayout("layNotas",mostCurrent.activityBA);
 //BA.debugLineNum = 409;BA.debugLine="scrChecklist.Panel.LoadLayout(\"layBingo\")";
mostCurrent._scrchecklist.getPanel().LoadLayout("layBingo",mostCurrent.activityBA);
 //BA.debugLineNum = 410;BA.debugLine="scrChecklist.Panel.Height = 90%y";
mostCurrent._scrchecklist.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 411;BA.debugLine="ShowScrollbar(scrChecklist)";
_showscrollbar(mostCurrent._scrchecklist);
 //BA.debugLineNum = 413;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 414;BA.debugLine="chkAlgas.Text = \"Filamentous algae\"";
mostCurrent._chkalgas.setText((Object)("Filamentous algae"));
 //BA.debugLineNum = 415;BA.debugLine="chkAves.Text = \"Aquatic birds\"";
mostCurrent._chkaves.setText((Object)("Aquatic birds"));
 //BA.debugLineNum = 416;BA.debugLine="chkRanas.Text = \"Frogs\"";
mostCurrent._chkranas.setText((Object)("Frogs"));
 //BA.debugLineNum = 417;BA.debugLine="chkCaracoles.Text = \"Snails\"";
mostCurrent._chkcaracoles.setText((Object)("Snails"));
 //BA.debugLineNum = 418;BA.debugLine="chkLibelulas.Text = \"Dragonflies\"";
mostCurrent._chklibelulas.setText((Object)("Dragonflies"));
 //BA.debugLineNum = 419;BA.debugLine="ChkAnimales.Text = \"Large mammals\"";
mostCurrent._chkanimales.setText((Object)("Large mammals"));
 //BA.debugLineNum = 420;BA.debugLine="chkPeces.Text = \"Fish\"";
mostCurrent._chkpeces.setText((Object)("Fish"));
 //BA.debugLineNum = 421;BA.debugLine="chkCulebras.Text = \"Snakes\"";
mostCurrent._chkculebras.setText((Object)("Snakes"));
 //BA.debugLineNum = 422;BA.debugLine="chkTortugas.Text = \"Turtles\"";
mostCurrent._chktortugas.setText((Object)("Turtles"));
 //BA.debugLineNum = 424;BA.debugLine="Label1.Text = \"If you see any of these organisms";
mostCurrent._label1.setText((Object)("If you see any of these organisms, or signs of them (tracks, excrements), please mark them!"));
 //BA.debugLineNum = 425;BA.debugLine="Label2.Text = \"Notes\"";
mostCurrent._label2.setText((Object)("Notes"));
 //BA.debugLineNum = 426;BA.debugLine="btnOk.Text = \"Save\"";
mostCurrent._btnok.setText((Object)("Save"));
 //BA.debugLineNum = 427;BA.debugLine="txtNotas.Hint = \"Write your notes and comments h";
mostCurrent._txtnotas.setHint("Write your notes and comments here");
 };
 //BA.debugLineNum = 431;BA.debugLine="Dim filenotas As String";
_filenotas = "";
 //BA.debugLineNum = 432;BA.debugLine="filenotas = Main.username & \"@\" & currentproject";
_filenotas = mostCurrent._main._username+"@"+_currentproject+"@"+"-Notas";
 //BA.debugLineNum = 433;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", filenot";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",_filenotas+".txt")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 434;BA.debugLine="Dim notasarray () As String";
_notasarray = new String[(int) (0)];
java.util.Arrays.fill(_notasarray,"");
 //BA.debugLineNum = 436;BA.debugLine="notasarray = Regex.Split(\"@\", File.ReadString(Ma";
_notasarray = anywheresoftware.b4a.keywords.Common.Regex.Split("@",anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._main._savedir+"/AppEAR/",_filenotas+".txt"));
 //BA.debugLineNum = 437;BA.debugLine="If notasarray(0) = \"Aves=Si\" Then";
if ((_notasarray[(int) (0)]).equals("Aves=Si")) { 
 //BA.debugLineNum = 438;BA.debugLine="chkAves.Checked = True";
mostCurrent._chkaves.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 440;BA.debugLine="If notasarray(1) = \"Mamiferos=Si\" Then";
if ((_notasarray[(int) (1)]).equals("Mamiferos=Si")) { 
 //BA.debugLineNum = 441;BA.debugLine="ChkAnimales.Checked = True";
mostCurrent._chkanimales.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 443;BA.debugLine="If notasarray(2) = \"Tortugas=Si\" Then";
if ((_notasarray[(int) (2)]).equals("Tortugas=Si")) { 
 //BA.debugLineNum = 444;BA.debugLine="chkTortugas.Checked = True";
mostCurrent._chktortugas.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 446;BA.debugLine="If notasarray(3) = \"Ranas=Si\" Then";
if ((_notasarray[(int) (3)]).equals("Ranas=Si")) { 
 //BA.debugLineNum = 447;BA.debugLine="chkRanas.Checked = True";
mostCurrent._chkranas.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 449;BA.debugLine="If notasarray(4) = \"Peces=Si\" Then";
if ((_notasarray[(int) (4)]).equals("Peces=Si")) { 
 //BA.debugLineNum = 450;BA.debugLine="chkPeces.Checked = True";
mostCurrent._chkpeces.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 452;BA.debugLine="If notasarray(5) = \"Culebras=Si\" Then";
if ((_notasarray[(int) (5)]).equals("Culebras=Si")) { 
 //BA.debugLineNum = 453;BA.debugLine="chkCulebras.Checked = True";
mostCurrent._chkculebras.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 455;BA.debugLine="If notasarray(6) = \"Libelulas=Si\" Then";
if ((_notasarray[(int) (6)]).equals("Libelulas=Si")) { 
 //BA.debugLineNum = 456;BA.debugLine="chkLibelulas.Checked = True";
mostCurrent._chklibelulas.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 458;BA.debugLine="If notasarray(7) = \"Caracoles=Si\" Then";
if ((_notasarray[(int) (7)]).equals("Caracoles=Si")) { 
 //BA.debugLineNum = 459;BA.debugLine="chkCaracoles.Checked = True";
mostCurrent._chkcaracoles.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 461;BA.debugLine="If notasarray(8) = \"Mosquitos=Si\" Then";
if ((_notasarray[(int) (8)]).equals("Mosquitos=Si")) { 
 //BA.debugLineNum = 462;BA.debugLine="chkMosquitos.Checked = True";
mostCurrent._chkmosquitos.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 464;BA.debugLine="If notasarray(9) = \"Algas=Si\" Then";
if ((_notasarray[(int) (9)]).equals("Algas=Si")) { 
 //BA.debugLineNum = 465;BA.debugLine="chkAlgas.Checked = True";
mostCurrent._chkalgas.setChecked(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 467;BA.debugLine="If notasarray(10) <> \"None\" Then";
if ((_notasarray[(int) (10)]).equals("None") == false) { 
 //BA.debugLineNum = 468;BA.debugLine="txtNotas.Text = notasarray(10)";
mostCurrent._txtnotas.setText((Object)(_notasarray[(int) (10)]));
 };
 };
 //BA.debugLineNum = 472;BA.debugLine="End Sub";
return "";
}
public static String  _btnok_click() throws Exception{
String _filename = "";
String _contenidoexportar = "";
 //BA.debugLineNum = 485;BA.debugLine="Sub btnOk_Click";
 //BA.debugLineNum = 486;BA.debugLine="If txtNotas.Text = \"\" And chkAves.Checked = False";
if ((mostCurrent._txtnotas.getText()).equals("") && mostCurrent._chkaves.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chkanimales.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chktortugas.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chkranas.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chkpeces.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chkculebras.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chklibelulas.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chkmosquitos.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chkalgas.getChecked()==anywheresoftware.b4a.keywords.Common.False && mostCurrent._chkcaracoles.getChecked()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 490;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 491;BA.debugLine="Activity.LoadLayout(\"frmEvaluacion\")";
mostCurrent._activity.LoadLayout("frmEvaluacion",mostCurrent.activityBA);
 //BA.debugLineNum = 492;BA.debugLine="CheckEstado";
_checkestado();
 //BA.debugLineNum = 493;BA.debugLine="Return(False)";
if (true) return BA.ObjectToString((anywheresoftware.b4a.keywords.Common.False));
 };
 //BA.debugLineNum = 497;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 498;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 499;BA.debugLine="filename = Main.username & \"@\" & currentproject";
_filename = mostCurrent._main._username+"@"+_currentproject+"@"+"-Notas";
 //BA.debugLineNum = 501;BA.debugLine="Dim contenidoexportar As String";
_contenidoexportar = "";
 //BA.debugLineNum = 503;BA.debugLine="If chkAves.Checked = True Then";
if (mostCurrent._chkaves.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 504;BA.debugLine="contenidoexportar = contenidoexportar & \"Aves=S";
_contenidoexportar = _contenidoexportar+"Aves=Si@";
 }else {
 //BA.debugLineNum = 506;BA.debugLine="contenidoexportar = contenidoexportar & \"Aves=@";
_contenidoexportar = _contenidoexportar+"Aves=@";
 };
 //BA.debugLineNum = 508;BA.debugLine="If ChkAnimales.Checked = True Then";
if (mostCurrent._chkanimales.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 509;BA.debugLine="contenidoexportar = contenidoexportar & \"Mamife";
_contenidoexportar = _contenidoexportar+"Mamiferos=Si@";
 }else {
 //BA.debugLineNum = 511;BA.debugLine="contenidoexportar = contenidoexportar & \"Mamife";
_contenidoexportar = _contenidoexportar+"Mamiferos=@";
 };
 //BA.debugLineNum = 513;BA.debugLine="If chkTortugas.Checked = True Then";
if (mostCurrent._chktortugas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 514;BA.debugLine="contenidoexportar = contenidoexportar & \"Tortug";
_contenidoexportar = _contenidoexportar+"Tortugas=Si@";
 }else {
 //BA.debugLineNum = 516;BA.debugLine="contenidoexportar = contenidoexportar & \"Tortug";
_contenidoexportar = _contenidoexportar+"Tortugas=@";
 };
 //BA.debugLineNum = 518;BA.debugLine="If chkRanas.Checked = True Then";
if (mostCurrent._chkranas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 519;BA.debugLine="contenidoexportar = contenidoexportar & \"Ranas=";
_contenidoexportar = _contenidoexportar+"Ranas=Si@";
 }else {
 //BA.debugLineNum = 521;BA.debugLine="contenidoexportar = contenidoexportar & \"Ranas@";
_contenidoexportar = _contenidoexportar+"Ranas@";
 };
 //BA.debugLineNum = 523;BA.debugLine="If chkPeces.Checked = True Then";
if (mostCurrent._chkpeces.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 524;BA.debugLine="contenidoexportar = contenidoexportar & \"Peces=";
_contenidoexportar = _contenidoexportar+"Peces=Si@";
 }else {
 //BA.debugLineNum = 526;BA.debugLine="contenidoexportar = contenidoexportar & \"Peces@";
_contenidoexportar = _contenidoexportar+"Peces@";
 };
 //BA.debugLineNum = 528;BA.debugLine="If chkCulebras.Checked = True Then";
if (mostCurrent._chkculebras.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 529;BA.debugLine="contenidoexportar = contenidoexportar & \"Culebr";
_contenidoexportar = _contenidoexportar+"Culebras=Si@";
 }else {
 //BA.debugLineNum = 531;BA.debugLine="contenidoexportar = contenidoexportar & \"Culebr";
_contenidoexportar = _contenidoexportar+"Culebras=@";
 };
 //BA.debugLineNum = 533;BA.debugLine="If chkLibelulas.Checked = True Then";
if (mostCurrent._chklibelulas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 534;BA.debugLine="contenidoexportar = contenidoexportar & \"Libelu";
_contenidoexportar = _contenidoexportar+"Libelulas=Si@";
 }else {
 //BA.debugLineNum = 536;BA.debugLine="contenidoexportar = contenidoexportar & \"Libelu";
_contenidoexportar = _contenidoexportar+"Libelulas=@";
 };
 //BA.debugLineNum = 538;BA.debugLine="If chkCaracoles.Checked = True Then";
if (mostCurrent._chkcaracoles.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 539;BA.debugLine="contenidoexportar = contenidoexportar & \"Caraco";
_contenidoexportar = _contenidoexportar+"Caracoles=Si@";
 }else {
 //BA.debugLineNum = 541;BA.debugLine="contenidoexportar = contenidoexportar & \"Caraco";
_contenidoexportar = _contenidoexportar+"Caracoles=@";
 };
 //BA.debugLineNum = 543;BA.debugLine="If chkMosquitos.Checked = True Then";
if (mostCurrent._chkmosquitos.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 544;BA.debugLine="contenidoexportar = contenidoexportar & \"Mosqui";
_contenidoexportar = _contenidoexportar+"Mosquitos=Si@";
 }else {
 //BA.debugLineNum = 546;BA.debugLine="contenidoexportar = contenidoexportar & \"Mosqui";
_contenidoexportar = _contenidoexportar+"Mosquitos=@";
 };
 //BA.debugLineNum = 548;BA.debugLine="If chkAlgas.Checked = True Then";
if (mostCurrent._chkalgas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 549;BA.debugLine="contenidoexportar = contenidoexportar & \"Algas=";
_contenidoexportar = _contenidoexportar+"Algas=Si@";
 }else {
 //BA.debugLineNum = 551;BA.debugLine="contenidoexportar = contenidoexportar & \"Algas=";
_contenidoexportar = _contenidoexportar+"Algas=@";
 };
 //BA.debugLineNum = 555;BA.debugLine="If txtNotas.Text <> \"\" Then";
if ((mostCurrent._txtnotas.getText()).equals("") == false) { 
 //BA.debugLineNum = 556;BA.debugLine="contenidoexportar = contenidoexportar & txtNota";
_contenidoexportar = _contenidoexportar+mostCurrent._txtnotas.getText();
 }else {
 //BA.debugLineNum = 558;BA.debugLine="contenidoexportar = contenidoexportar & \"None\"";
_contenidoexportar = _contenidoexportar+"None";
 };
 //BA.debugLineNum = 560;BA.debugLine="File.WriteString(Main.savedir & \"/AppEAR/\", file";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._main._savedir+"/AppEAR/",_filename+".txt",_contenidoexportar);
 //BA.debugLineNum = 561;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 562;BA.debugLine="Activity.LoadLayout(\"frmEvaluacion\")";
mostCurrent._activity.LoadLayout("frmEvaluacion",mostCurrent.activityBA);
 //BA.debugLineNum = 563;BA.debugLine="CheckEstado";
_checkestado();
 //BA.debugLineNum = 564;BA.debugLine="End Sub";
return "";
}
public static String  _checkestado() throws Exception{
String _msgq = "";
String _filenotas = "";
 //BA.debugLineNum = 114;BA.debugLine="Sub CheckEstado";
 //BA.debugLineNum = 116;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 117;BA.debugLine="If formorigen = \"Enviado\" Then";
if ((_formorigen).equals("Enviado")) { 
 //BA.debugLineNum = 118;BA.debugLine="frmPrincipal.recargaPuntos = True";
mostCurrent._frmprincipal._recargapuntos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 119;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 //BA.debugLineNum = 124;BA.debugLine="If currentproject = \"\" Or currentproject = 0 Then";
if ((_currentproject).equals("") || (_currentproject).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 125;BA.debugLine="If File.Exists(File.DirInternal, \"lastproject-\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"lastproject-"+mostCurrent._main._struserid+".txt")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 126;BA.debugLine="currentproject = \"1\"";
_currentproject = "1";
 //BA.debugLineNum = 127;BA.debugLine="File.WriteString(File.DirInternal, \"lastprojec";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"lastproject-"+mostCurrent._main._struserid+".txt",_currentproject);
 }else {
 //BA.debugLineNum = 129;BA.debugLine="If evaluaciondone = False And fotosdone = Fals";
if (_evaluaciondone==anywheresoftware.b4a.keywords.Common.False && _fotosdone==anywheresoftware.b4a.keywords.Common.False && _notasdone==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 130;BA.debugLine="currentproject = File.ReadString(File.DirInte";
_currentproject = BA.NumberToString((double)(Double.parseDouble(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"lastproject-"+mostCurrent._main._struserid+".txt")))+1);
 //BA.debugLineNum = 131;BA.debugLine="File.WriteString(File.DirInternal, \"lastproje";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"lastproject-"+mostCurrent._main._struserid+".txt",_currentproject);
 }else {
 //BA.debugLineNum = 133;BA.debugLine="If formorigen <> \"Envio\" Then";
if ((_formorigen).equals("Envio") == false) { 
 //BA.debugLineNum = 134;BA.debugLine="Dim msgq As String";
_msgq = "";
 //BA.debugLineNum = 135;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 136;BA.debugLine="msgq = Msgbox2(\"Deseas comenzar una evaluac";
_msgq = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Deseas comenzar una evaluación nueva?","Nueva evaluación","Si","No, cancelar","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 138;BA.debugLine="msgq = Msgbox2(\"Do you wish to start a new";
_msgq = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Do you wish to start a new report?","New report","Yes","No, cancel","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 141;BA.debugLine="If msgq = DialogResponse.POSITIVE Then";
if ((_msgq).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 142;BA.debugLine="evaluaciondone = False";
_evaluaciondone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 143;BA.debugLine="fotosdone = False";
_fotosdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 144;BA.debugLine="notasdone = False";
_notasdone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 145;BA.debugLine="currentproject = File.ReadString(File.DirIn";
_currentproject = BA.NumberToString((double)(Double.parseDouble(anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"lastproject-"+mostCurrent._main._struserid+".txt")))+1);
 //BA.debugLineNum = 146;BA.debugLine="File.WriteString(File.DirInternal, \"lastpro";
anywheresoftware.b4a.keywords.Common.File.WriteString(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"lastproject-"+mostCurrent._main._struserid+".txt",_currentproject);
 //BA.debugLineNum = 147;BA.debugLine="Main.fotopath0 = \"\"";
mostCurrent._main._fotopath0 = "";
 //BA.debugLineNum = 148;BA.debugLine="Main.fotopath1 = \"\"";
mostCurrent._main._fotopath1 = "";
 //BA.debugLineNum = 149;BA.debugLine="Main.fotopath2 = \"\"";
mostCurrent._main._fotopath2 = "";
 //BA.debugLineNum = 150;BA.debugLine="Main.fotopath3 = \"\"";
mostCurrent._main._fotopath3 = "";
 };
 };
 };
 };
 };
 //BA.debugLineNum = 160;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
 //BA.debugLineNum = 161;BA.debugLine="imgSitio1.Visible = True";
mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 162;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.f";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath0+".jpg")) { 
 //BA.debugLineNum = 163;BA.debugLine="imgSitio1.Bitmap = Null";
mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 164;BA.debugLine="imgSitio1.Bitmap = LoadBitmapSample(Main.savedi";
mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath0+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 165;BA.debugLine="fotosdone = True";
_fotosdone = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 166;BA.debugLine="btnFotos.Text = \"Fotos (1/4)\"";
mostCurrent._btnfotos.setText((Object)("Fotos (1/4)"));
 }else {
 //BA.debugLineNum = 168;BA.debugLine="imgSitio1.Visible = False";
mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 169;BA.debugLine="btnFotos.Text = \"Fotos (0/4)\"";
mostCurrent._btnfotos.setText((Object)("Fotos (0/4)"));
 };
 }else {
 //BA.debugLineNum = 172;BA.debugLine="imgSitio1.Visible = False";
mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 174;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
 //BA.debugLineNum = 175;BA.debugLine="imgSitio2.Visible = True";
mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 176;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.f";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath1+".jpg")) { 
 //BA.debugLineNum = 177;BA.debugLine="imgSitio2.Bitmap = Null";
mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 178;BA.debugLine="imgSitio2.Bitmap = LoadBitmapSample(Main.savedi";
mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath1+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 179;BA.debugLine="fotosdone = True";
_fotosdone = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 180;BA.debugLine="btnFotos.Text = \"Fotos (2/4)\"";
mostCurrent._btnfotos.setText((Object)("Fotos (2/4)"));
 }else {
 //BA.debugLineNum = 182;BA.debugLine="imgSitio2.Visible = False";
mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 185;BA.debugLine="imgSitio2.Visible = False";
mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 187;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
 //BA.debugLineNum = 188;BA.debugLine="imgSitio3.Visible = True";
mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 189;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.f";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath2+".jpg")) { 
 //BA.debugLineNum = 190;BA.debugLine="imgSitio3.Bitmap = Null";
mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 191;BA.debugLine="imgSitio3.Bitmap = LoadBitmapSample(Main.savedi";
mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath2+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 192;BA.debugLine="fotosdone = True";
_fotosdone = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 193;BA.debugLine="btnFotos.Text = \"Fotos (3/4)\"";
mostCurrent._btnfotos.setText((Object)("Fotos (3/4)"));
 }else {
 //BA.debugLineNum = 195;BA.debugLine="imgSitio3.Visible = False";
mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 198;BA.debugLine="imgSitio3.Visible = False";
mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 200;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
 //BA.debugLineNum = 201;BA.debugLine="imgSitio4.Visible = True";
mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 202;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.f";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath3+".jpg")) { 
 //BA.debugLineNum = 203;BA.debugLine="imgSitio4.Bitmap = Null";
mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 204;BA.debugLine="imgSitio4.Bitmap = LoadBitmapSample(Main.savedi";
mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath3+".jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 205;BA.debugLine="fotosdone = True";
_fotosdone = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 206;BA.debugLine="btnFotos.Text = \"Fotos (4/4)\"";
mostCurrent._btnfotos.setText((Object)("Fotos (4/4)"));
 }else {
 //BA.debugLineNum = 208;BA.debugLine="imgSitio4.Visible = False";
mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 211;BA.debugLine="imgSitio4.Visible = False";
mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 216;BA.debugLine="If fotosdone = True Then";
if (_fotosdone==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 217;BA.debugLine="chkFotos.Bitmap = LoadBitmap(File.DirAssets, \"ch";
mostCurrent._chkfotos.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()));
 }else {
 //BA.debugLineNum = 219;BA.debugLine="chkFotos.Bitmap = LoadBitmap(File.DirAssets, \"no";
mostCurrent._chkfotos.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nocheck.png").getObject()));
 };
 //BA.debugLineNum = 224;BA.debugLine="Dim filenotas As String";
_filenotas = "";
 //BA.debugLineNum = 225;BA.debugLine="filenotas = Main.username & \"@\" & currentproject";
_filenotas = mostCurrent._main._username+"@"+_currentproject+"@"+"-Notas";
 //BA.debugLineNum = 226;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", filenot";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",_filenotas+".txt")==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 227;BA.debugLine="notasdone = True";
_notasdone = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 228;BA.debugLine="chkNotas.Bitmap = LoadBitmap(File.DirAssets, \"ch";
mostCurrent._chknotas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()));
 }else {
 //BA.debugLineNum = 230;BA.debugLine="chkNotas.Bitmap = LoadBitmap(File.DirAssets, \"no";
mostCurrent._chknotas.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nocheck.png").getObject()));
 };
 //BA.debugLineNum = 234;BA.debugLine="If evaluaciondone = True Then";
if (_evaluaciondone==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 235;BA.debugLine="chkTestHabitat.Bitmap = LoadBitmap(File.DirAsset";
mostCurrent._chktesthabitat.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()));
 }else {
 //BA.debugLineNum = 237;BA.debugLine="chkTestHabitat.Bitmap = LoadBitmap(File.DirAsset";
mostCurrent._chktesthabitat.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nocheck.png").getObject()));
 };
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private chkTestHabitat As ImageView";
mostCurrent._chktesthabitat = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private chkFotos As ImageView";
mostCurrent._chkfotos = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private chkOtros As ImageView";
mostCurrent._chkotros = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private chkNotas As ImageView";
mostCurrent._chknotas = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private txtNotas As EditText";
mostCurrent._txtnotas = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private imgSitio1 As ImageView";
mostCurrent._imgsitio1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private imgSitio2 As ImageView";
mostCurrent._imgsitio2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private imgSitio3 As ImageView";
mostCurrent._imgsitio3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private imgSitio4 As ImageView";
mostCurrent._imgsitio4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private chkAves As CheckBox";
mostCurrent._chkaves = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private ChkAnimales As CheckBox";
mostCurrent._chkanimales = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private chkAlgas As CheckBox";
mostCurrent._chkalgas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private chkTortugas As CheckBox";
mostCurrent._chktortugas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private chkRanas As CheckBox";
mostCurrent._chkranas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private chkPeces As CheckBox";
mostCurrent._chkpeces = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private chkCulebras As CheckBox";
mostCurrent._chkculebras = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private chkLibelulas As CheckBox";
mostCurrent._chklibelulas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private chkCaracoles As CheckBox";
mostCurrent._chkcaracoles = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private chkMosquitos As CheckBox";
mostCurrent._chkmosquitos = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btnFotos As Button";
mostCurrent._btnfotos = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private scrChecklist As ScrollView";
mostCurrent._scrchecklist = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnEvaluacion As Button";
mostCurrent._btnevaluacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private btnNotas As Button";
mostCurrent._btnnotas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private lblSubtitulo As Label";
mostCurrent._lblsubtitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btnEnviar As Button";
mostCurrent._btnenviar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private btnOk As Button";
mostCurrent._btnok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio1_click() throws Exception{
String _msgconf = "";
 //BA.debugLineNum = 308;BA.debugLine="Sub imgSitio1_Click";
 //BA.debugLineNum = 309;BA.debugLine="Dim msgconf As String";
_msgconf = "";
 //BA.debugLineNum = 310;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 311;BA.debugLine="msgconf = Msgbox2(\"Desea eliminar la fotografía?";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Desea eliminar la fotografía?","Cuidado!","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 313;BA.debugLine="msgconf = Msgbox2(\"Do you want to delete this ph";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Do you want to delete this photo?","Careful!","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 316;BA.debugLine="If msgconf=DialogResponse.POSITIVE Then";
if ((_msgconf).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 317;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
 //BA.debugLineNum = 318;BA.debugLine="imgSitio1.Bitmap = Null";
mostCurrent._imgsitio1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 319;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath0+".jpg")) { 
 //BA.debugLineNum = 320;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", Main.fo";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath0+".jpg");
 }else if(anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath0+".jpg")) { 
 //BA.debugLineNum = 322;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/sent/\", Ma";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath0+".jpg");
 };
 //BA.debugLineNum = 324;BA.debugLine="Main.fotopath0 = \"\"";
mostCurrent._main._fotopath0 = "";
 //BA.debugLineNum = 325;BA.debugLine="imgSitio1.Visible = False";
mostCurrent._imgsitio1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 328;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio2_click() throws Exception{
String _msgconf = "";
 //BA.debugLineNum = 329;BA.debugLine="Sub imgSitio2_Click";
 //BA.debugLineNum = 330;BA.debugLine="Dim msgconf As String";
_msgconf = "";
 //BA.debugLineNum = 331;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 332;BA.debugLine="msgconf = Msgbox2(\"Desea eliminar la fotografía?";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Desea eliminar la fotografía?","Cuidado!","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 334;BA.debugLine="msgconf = Msgbox2(\"Do you want to delete this ph";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Do you want to delete this photo?","Careful!","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 336;BA.debugLine="If msgconf=DialogResponse.POSITIVE Then";
if ((_msgconf).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 337;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
 //BA.debugLineNum = 338;BA.debugLine="imgSitio2.Bitmap = Null";
mostCurrent._imgsitio2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 339;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.f";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath1+".jpg")) { 
 //BA.debugLineNum = 340;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", Main.fot";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath1+".jpg");
 }else if(anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath1+".jpg")) { 
 //BA.debugLineNum = 342;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/sent/\", Mai";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath1+".jpg");
 };
 //BA.debugLineNum = 344;BA.debugLine="Main.fotopath1 = \"\"";
mostCurrent._main._fotopath1 = "";
 //BA.debugLineNum = 345;BA.debugLine="imgSitio2.Visible = False";
mostCurrent._imgsitio2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio3_click() throws Exception{
String _msgconf = "";
 //BA.debugLineNum = 349;BA.debugLine="Sub imgSitio3_Click";
 //BA.debugLineNum = 350;BA.debugLine="Dim msgconf As String";
_msgconf = "";
 //BA.debugLineNum = 351;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 352;BA.debugLine="msgconf = Msgbox2(\"Desea eliminar la fotografía?";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Desea eliminar la fotografía?","Cuidado!","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 354;BA.debugLine="msgconf = Msgbox2(\"Do you want to delete this ph";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Do you want to delete this photo?","Careful!","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 356;BA.debugLine="If msgconf=DialogResponse.POSITIVE Then";
if ((_msgconf).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 357;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
 //BA.debugLineNum = 358;BA.debugLine="imgSitio3.Bitmap = Null";
mostCurrent._imgsitio3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 359;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.f";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath2+".jpg")) { 
 //BA.debugLineNum = 360;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", Main.fot";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath2+".jpg");
 }else if(anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath2+".jpg")) { 
 //BA.debugLineNum = 362;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/sent/\", Mai";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath2+".jpg");
 };
 //BA.debugLineNum = 364;BA.debugLine="Main.fotopath2 = \"\"";
mostCurrent._main._fotopath2 = "";
 //BA.debugLineNum = 365;BA.debugLine="imgSitio3.Visible = False";
mostCurrent._imgsitio3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 368;BA.debugLine="End Sub";
return "";
}
public static String  _imgsitio4_click() throws Exception{
String _msgconf = "";
 //BA.debugLineNum = 369;BA.debugLine="Sub imgSitio4_Click";
 //BA.debugLineNum = 370;BA.debugLine="Dim msgconf As String";
_msgconf = "";
 //BA.debugLineNum = 371;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 372;BA.debugLine="msgconf = Msgbox2(\"Desea eliminar la fotografía?";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Desea eliminar la fotografía?","Cuidado!","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 374;BA.debugLine="msgconf = Msgbox2(\"Do you want to delete this ph";
_msgconf = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Do you want to delete this photo?","Careful!","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 376;BA.debugLine="If msgconf=DialogResponse.POSITIVE Then";
if ((_msgconf).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 377;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
 //BA.debugLineNum = 378;BA.debugLine="imgSitio4.Bitmap = Null";
mostCurrent._imgsitio4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 379;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", Main.f";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath3+".jpg")) { 
 //BA.debugLineNum = 380;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", Main.fot";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._fotopath3+".jpg");
 }else if(anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath3+".jpg")) { 
 //BA.debugLineNum = 382;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/sent/\", Mai";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/sent/",mostCurrent._main._fotopath3+".jpg");
 };
 //BA.debugLineNum = 384;BA.debugLine="Main.fotopath3 = \"\"";
mostCurrent._main._fotopath3 = "";
 //BA.debugLineNum = 385;BA.debugLine="imgSitio4.Visible = False";
mostCurrent._imgsitio4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 388;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim evaluaciondone As Boolean";
_evaluaciondone = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim fotosdone As Boolean";
_fotosdone = false;
 //BA.debugLineNum = 11;BA.debugLine="Dim otrosdone As Boolean";
_otrosdone = false;
 //BA.debugLineNum = 12;BA.debugLine="Dim notasdone As Boolean";
_notasdone = false;
 //BA.debugLineNum = 14;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 15;BA.debugLine="Dim formorigen As String";
_formorigen = "";
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _showscrollbar(anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 474;BA.debugLine="Sub ShowScrollbar(ScrollView1 As ScrollView)";
 //BA.debugLineNum = 475;BA.debugLine="Dim jo = ScrollView1 As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo.setObject((java.lang.Object)(_scrollview1.getObject()));
 //BA.debugLineNum = 476;BA.debugLine="jo.RunMethod(\"setScrollBarFadeDuration\", Array";
_jo.RunMethod("setScrollBarFadeDuration",new Object[]{(Object)(0)});
 //BA.debugLineNum = 477;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 99;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 100;BA.debugLine="btnEnviar.Text = \"Send report!\"";
mostCurrent._btnenviar.setText((Object)("Send report!"));
 //BA.debugLineNum = 101;BA.debugLine="btnEvaluacion.Text = \"Questionnaire\"";
mostCurrent._btnevaluacion.setText((Object)("Questionnaire"));
 //BA.debugLineNum = 102;BA.debugLine="btnFotos.Text = \"Photos\"";
mostCurrent._btnfotos.setText((Object)("Photos"));
 //BA.debugLineNum = 103;BA.debugLine="btnNotas.Text = \"Optional data\"";
mostCurrent._btnnotas.setText((Object)("Optional data"));
 //BA.debugLineNum = 104;BA.debugLine="lblSubtitulo.Text = \"For your report, please fil";
mostCurrent._lblsubtitulo.setText((Object)("For your report, please fill the questionnaire and take some photos!"));
 };
 //BA.debugLineNum = 106;BA.debugLine="End Sub";
return "";
}
public static String  _txtnotas_enterpressed() throws Exception{
 //BA.debugLineNum = 480;BA.debugLine="Sub txtNotas_EnterPressed";
 //BA.debugLineNum = 481;BA.debugLine="Main.k.HideKeyboard(Activity)";
mostCurrent._main._k.HideKeyboard(mostCurrent._activity);
 //BA.debugLineNum = 482;BA.debugLine="End Sub";
return "";
}
}
