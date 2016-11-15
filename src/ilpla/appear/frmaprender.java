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

public class frmaprender extends Activity implements B4AActivity{
	public static frmaprender mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmaprender");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmaprender).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmaprender");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmaprender", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmaprender) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmaprender) Resume **");
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
		return frmaprender.class;
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
        BA.LogInfo("** Activity (frmaprender) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmaprender) Resume **");
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
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrquiz = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad5 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad6 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad7 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad8 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad9 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad10 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad11 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad12 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad13 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad14 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad15 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad16 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad17 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad18 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad19 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad20 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad21 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rad22 = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblaprendemas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmuestreos = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntipoambientes = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnfactores = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnquiz = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerraraprender = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncheckquiz = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncadenas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnciclo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncomunidades = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontaminacion = null;
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
 //BA.debugLineNum = 32;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 33;BA.debugLine="Activity.LoadLayout(\"frmAprenderMas\")";
mostCurrent._activity.LoadLayout("frmAprenderMas",mostCurrent.activityBA);
 //BA.debugLineNum = 34;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 42;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 38;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 40;BA.debugLine="End Sub";
return "";
}
public static String  _btncadenas_click() throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub btnCadenas_Click";
 //BA.debugLineNum = 98;BA.debugLine="Game_Trofica.origen = \"aprender\"";
mostCurrent._game_trofica._origen = "aprender";
 //BA.debugLineNum = 99;BA.debugLine="StartActivity(Game_Trofica)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._game_trofica.getObject()));
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _btncerraraprender_click() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub btnCerrarAprender_Click";
 //BA.debugLineNum = 69;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 70;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _btncerraraprenderlay_click() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub btnCerrarAprenderLay_Click";
 //BA.debugLineNum = 119;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 120;BA.debugLine="Activity.LoadLayout(\"frmAprenderMas\")";
mostCurrent._activity.LoadLayout("frmAprenderMas",mostCurrent.activityBA);
 //BA.debugLineNum = 121;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public static String  _btncheckquiz_click() throws Exception{
int _score = 0;
 //BA.debugLineNum = 334;BA.debugLine="Sub btnCheckQuiz_Click";
 //BA.debugLineNum = 337;BA.debugLine="Dim score As Int";
_score = 0;
 //BA.debugLineNum = 338;BA.debugLine="If rad1.Checked = True Then";
if (mostCurrent._rad1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 339;BA.debugLine="score = score + 1";
_score = (int) (_score+1);
 };
 //BA.debugLineNum = 341;BA.debugLine="If rad4.Checked = True Then";
if (mostCurrent._rad4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 342;BA.debugLine="score = score + 1";
_score = (int) (_score+1);
 };
 //BA.debugLineNum = 344;BA.debugLine="If rad8.Checked = True Then";
if (mostCurrent._rad8.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 345;BA.debugLine="score = score + 1";
_score = (int) (_score+1);
 };
 //BA.debugLineNum = 347;BA.debugLine="If rad10.Checked = True Then";
if (mostCurrent._rad10.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 348;BA.debugLine="score = score + 1";
_score = (int) (_score+1);
 };
 //BA.debugLineNum = 350;BA.debugLine="If rad12.Checked = True Then";
if (mostCurrent._rad12.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 351;BA.debugLine="score = score + 1";
_score = (int) (_score+1);
 };
 //BA.debugLineNum = 353;BA.debugLine="If rad16.Checked = True Then";
if (mostCurrent._rad16.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 354;BA.debugLine="score = score + 1";
_score = (int) (_score+1);
 };
 //BA.debugLineNum = 356;BA.debugLine="If rad22.Checked = True Then";
if (mostCurrent._rad22.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 357;BA.debugLine="score = score + 1";
_score = (int) (_score+1);
 };
 //BA.debugLineNum = 359;BA.debugLine="score = (score * 100) / 7";
_score = (int) ((_score*100)/(double)7);
 //BA.debugLineNum = 360;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 361;BA.debugLine="utilidades.Mensaje (\"Encuesta finalizada\", \"Tu p";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Encuesta finalizada","Tu puntaje final: "+BA.NumberToString(_score)+"%","OK","","");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 363;BA.debugLine="utilidades.Mensaje (\"Quiz finished\", \"Your final";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Quiz finished","Your final score is: "+BA.NumberToString(_score)+"%","OK","","");
 };
 //BA.debugLineNum = 366;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 367;BA.debugLine="Activity.LoadLayout(\"frmAprenderMas\")";
mostCurrent._activity.LoadLayout("frmAprenderMas",mostCurrent.activityBA);
 //BA.debugLineNum = 368;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 369;BA.debugLine="End Sub";
return "";
}
public static String  _btnciclo_click() throws Exception{
 //BA.debugLineNum = 107;BA.debugLine="Sub btnCiclo_Click";
 //BA.debugLineNum = 108;BA.debugLine="Game_Ciclo.origen = \"aprender\"";
mostCurrent._game_ciclo._origen = "aprender";
 //BA.debugLineNum = 109;BA.debugLine="StartActivity(Game_Ciclo)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._game_ciclo.getObject()));
 //BA.debugLineNum = 110;BA.debugLine="End Sub";
return "";
}
public static String  _btncomunidades_click() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub btnComunidades_Click";
 //BA.debugLineNum = 103;BA.debugLine="Game_Comunidades.origen = \"aprender\"";
mostCurrent._game_comunidades._origen = "aprender";
 //BA.debugLineNum = 104;BA.debugLine="StartActivity(Game_Comunidades)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._game_comunidades.getObject()));
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public static String  _btncontaminacion_click() throws Exception{
 //BA.debugLineNum = 112;BA.debugLine="Sub btnContaminacion_Click";
 //BA.debugLineNum = 113;BA.debugLine="Game_SourcePoint.origen = \"aprender\"";
mostCurrent._game_sourcepoint._origen = "aprender";
 //BA.debugLineNum = 114;BA.debugLine="StartActivity(Game_SourcePoint)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._game_sourcepoint.getObject()));
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _btnfactores_click() throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub btnFactores_Click";
 //BA.debugLineNum = 94;BA.debugLine="StartActivity(Aprender_Factores)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._aprender_factores.getObject()));
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static String  _btnmuestreos_click() throws Exception{
String _html = "";
 //BA.debugLineNum = 79;BA.debugLine="Sub btnMuestreos_Click";
 //BA.debugLineNum = 80;BA.debugLine="Activity.LoadLayout(\"layAprenderMas\")";
mostCurrent._activity.LoadLayout("layAprenderMas",mostCurrent.activityBA);
 //BA.debugLineNum = 81;BA.debugLine="Dim HTML As String";
_html = "";
 //BA.debugLineNum = 84;BA.debugLine="HTML = File.GetText(File.DirAssets, Main.lang & \"";
_html = anywheresoftware.b4a.keywords.Common.File.GetText(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-seguridad.html");
 //BA.debugLineNum = 85;BA.debugLine="WebView1.LoadHtml(HTML)";
mostCurrent._webview1.LoadHtml(_html);
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _btnquiz_click() throws Exception{
 //BA.debugLineNum = 131;BA.debugLine="Sub btnQuiz_Click";
 //BA.debugLineNum = 132;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 133;BA.debugLine="Activity.LoadLayout(\"layQuiz\")";
mostCurrent._activity.LoadLayout("layQuiz",mostCurrent.activityBA);
 //BA.debugLineNum = 134;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 135;BA.debugLine="lblTitulo.Text = \"Test your knowldge!\"";
mostCurrent._lbltitulo.setText((Object)("Test your knowldge!"));
 //BA.debugLineNum = 136;BA.debugLine="btnCheckQuiz.Text = \"Check your results!\"";
mostCurrent._btncheckquiz.setText((Object)("Check your results!"));
 };
 //BA.debugLineNum = 138;BA.debugLine="CrearQuiz";
_crearquiz();
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _btntipoambientes_click() throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub btnTipoAmbientes_Click";
 //BA.debugLineNum = 90;BA.debugLine="StartActivity(Aprender_TipoAgua)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._aprender_tipoagua.getObject()));
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _crearquiz() throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _pnl1 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl2 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl3 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl4 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl5 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl6 = null;
anywheresoftware.b4a.objects.PanelWrapper _pnl7 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl1 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl2 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl3 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl4 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl5 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl6 = null;
anywheresoftware.b4a.objects.LabelWrapper _lbl7 = null;
 //BA.debugLineNum = 141;BA.debugLine="Sub CrearQuiz";
 //BA.debugLineNum = 143;BA.debugLine="Dim pnl1, pnl2, pnl3, pnl4, pnl5, pnl6, pnl7 As P";
_pnl1 = new anywheresoftware.b4a.objects.PanelWrapper();
_pnl2 = new anywheresoftware.b4a.objects.PanelWrapper();
_pnl3 = new anywheresoftware.b4a.objects.PanelWrapper();
_pnl4 = new anywheresoftware.b4a.objects.PanelWrapper();
_pnl5 = new anywheresoftware.b4a.objects.PanelWrapper();
_pnl6 = new anywheresoftware.b4a.objects.PanelWrapper();
_pnl7 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 144;BA.debugLine="Dim lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7 As L";
_lbl1 = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl2 = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl3 = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl4 = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl5 = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl6 = new anywheresoftware.b4a.objects.LabelWrapper();
_lbl7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 145;BA.debugLine="pnl1.Initialize(\"\")";
_pnl1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 146;BA.debugLine="pnl2.Initialize(\"\")";
_pnl2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 147;BA.debugLine="pnl3.Initialize(\"\")";
_pnl3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 148;BA.debugLine="pnl4.Initialize(\"\")";
_pnl4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 149;BA.debugLine="pnl5.Initialize(\"\")";
_pnl5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 150;BA.debugLine="pnl6.Initialize(\"\")";
_pnl6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 151;BA.debugLine="pnl7.Initialize(\"\")";
_pnl7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 152;BA.debugLine="rad1.Initialize(\"rad1\")";
mostCurrent._rad1.Initialize(mostCurrent.activityBA,"rad1");
 //BA.debugLineNum = 153;BA.debugLine="rad2.Initialize(\"rad2\")";
mostCurrent._rad2.Initialize(mostCurrent.activityBA,"rad2");
 //BA.debugLineNum = 154;BA.debugLine="rad3.Initialize(\"rad3\")";
mostCurrent._rad3.Initialize(mostCurrent.activityBA,"rad3");
 //BA.debugLineNum = 155;BA.debugLine="rad4.Initialize(\"rad4\")";
mostCurrent._rad4.Initialize(mostCurrent.activityBA,"rad4");
 //BA.debugLineNum = 156;BA.debugLine="rad5.Initialize(\"rad5\")";
mostCurrent._rad5.Initialize(mostCurrent.activityBA,"rad5");
 //BA.debugLineNum = 157;BA.debugLine="rad6.Initialize(\"rad6\")";
mostCurrent._rad6.Initialize(mostCurrent.activityBA,"rad6");
 //BA.debugLineNum = 158;BA.debugLine="rad7.Initialize(\"rad7\")";
mostCurrent._rad7.Initialize(mostCurrent.activityBA,"rad7");
 //BA.debugLineNum = 159;BA.debugLine="rad8.Initialize(\"rad8\")";
mostCurrent._rad8.Initialize(mostCurrent.activityBA,"rad8");
 //BA.debugLineNum = 160;BA.debugLine="rad9.Initialize(\"rad9\")";
mostCurrent._rad9.Initialize(mostCurrent.activityBA,"rad9");
 //BA.debugLineNum = 161;BA.debugLine="rad10.Initialize(\"rad10\")";
mostCurrent._rad10.Initialize(mostCurrent.activityBA,"rad10");
 //BA.debugLineNum = 162;BA.debugLine="rad11.Initialize(\"rad11\")";
mostCurrent._rad11.Initialize(mostCurrent.activityBA,"rad11");
 //BA.debugLineNum = 163;BA.debugLine="rad12.Initialize(\"rad12\")";
mostCurrent._rad12.Initialize(mostCurrent.activityBA,"rad12");
 //BA.debugLineNum = 164;BA.debugLine="rad13.Initialize(\"rad13\")";
mostCurrent._rad13.Initialize(mostCurrent.activityBA,"rad13");
 //BA.debugLineNum = 165;BA.debugLine="rad14.Initialize(\"rad14\")";
mostCurrent._rad14.Initialize(mostCurrent.activityBA,"rad14");
 //BA.debugLineNum = 166;BA.debugLine="rad15.Initialize(\"rad15\")";
mostCurrent._rad15.Initialize(mostCurrent.activityBA,"rad15");
 //BA.debugLineNum = 167;BA.debugLine="rad16.Initialize(\"rad16\")";
mostCurrent._rad16.Initialize(mostCurrent.activityBA,"rad16");
 //BA.debugLineNum = 168;BA.debugLine="rad17.Initialize(\"rad17\")";
mostCurrent._rad17.Initialize(mostCurrent.activityBA,"rad17");
 //BA.debugLineNum = 169;BA.debugLine="rad18.Initialize(\"rad18\")";
mostCurrent._rad18.Initialize(mostCurrent.activityBA,"rad18");
 //BA.debugLineNum = 170;BA.debugLine="rad19.Initialize(\"rad19\")";
mostCurrent._rad19.Initialize(mostCurrent.activityBA,"rad19");
 //BA.debugLineNum = 171;BA.debugLine="rad20.Initialize(\"rad20\")";
mostCurrent._rad20.Initialize(mostCurrent.activityBA,"rad20");
 //BA.debugLineNum = 172;BA.debugLine="rad21.Initialize(\"rad21\")";
mostCurrent._rad21.Initialize(mostCurrent.activityBA,"rad21");
 //BA.debugLineNum = 173;BA.debugLine="rad22.Initialize(\"rad22\")";
mostCurrent._rad22.Initialize(mostCurrent.activityBA,"rad22");
 //BA.debugLineNum = 174;BA.debugLine="lbl1.Initialize(\"\")";
_lbl1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 175;BA.debugLine="lbl2.Initialize(\"\")";
_lbl2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 176;BA.debugLine="lbl3.Initialize(\"\")";
_lbl3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 177;BA.debugLine="lbl4.Initialize(\"\")";
_lbl4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 178;BA.debugLine="lbl5.Initialize(\"\")";
_lbl5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 179;BA.debugLine="lbl6.Initialize(\"\")";
_lbl6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 180;BA.debugLine="lbl7.Initialize(\"\")";
_lbl7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 181;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 182;BA.debugLine="lbl1.Text = \"1. El ganado, ¿afecta a la calidad";
_lbl1.setText((Object)("1. El ganado, ¿afecta a la calidad del agua?"));
 //BA.debugLineNum = 183;BA.debugLine="rad1.Text = \"Si, pisotean y agregan nutrientes a";
mostCurrent._rad1.setText((Object)("Si, pisotean y agregan nutrientes al agua"));
 //BA.debugLineNum = 184;BA.debugLine="rad2.Text = \"No, no causan un gran efecto\"";
mostCurrent._rad2.setText((Object)("No, no causan un gran efecto"));
 //BA.debugLineNum = 185;BA.debugLine="lbl2.Text = \"2. Los residuos y la basura, ¿contr";
_lbl2.setText((Object)("2. Los residuos y la basura, ¿contribuyen a mejorar la calidad del agua?"));
 //BA.debugLineNum = 186;BA.debugLine="rad3.Text = \"Si, la basura mejora el sitio\"";
mostCurrent._rad3.setText((Object)("Si, la basura mejora el sitio"));
 //BA.debugLineNum = 187;BA.debugLine="rad4.Text = \"No, la basura empeora el sitio\"";
mostCurrent._rad4.setText((Object)("No, la basura empeora el sitio"));
 //BA.debugLineNum = 188;BA.debugLine="lbl3.Text = \"3. Cuál de los siguientes NO es est";
_lbl3.setText((Object)("3. Cuál de los siguientes NO es estudiado por la LIMNOLOGÍA?"));
 //BA.debugLineNum = 189;BA.debugLine="rad5.Text = \"Ríos y arroyos\"";
mostCurrent._rad5.setText((Object)("Ríos y arroyos"));
 //BA.debugLineNum = 190;BA.debugLine="rad6.Text = \"Estuarios\"";
mostCurrent._rad6.setText((Object)("Estuarios"));
 //BA.debugLineNum = 191;BA.debugLine="rad7.Text = \"Lagos y lagunas\"";
mostCurrent._rad7.setText((Object)("Lagos y lagunas"));
 //BA.debugLineNum = 192;BA.debugLine="rad8.Text = \"El mar\"";
mostCurrent._rad8.setText((Object)("El mar"));
 //BA.debugLineNum = 193;BA.debugLine="rad9.Text = \"Todos estos son estudiados por la L";
mostCurrent._rad9.setText((Object)("Todos estos son estudiados por la LIMNOLOGIA"));
 //BA.debugLineNum = 194;BA.debugLine="lbl4.Text = \"4. Las represas y diques, ¿alteran";
_lbl4.setText((Object)("4. Las represas y diques, ¿alteran a los organismos que habitan los ríos?"));
 //BA.debugLineNum = 195;BA.debugLine="rad10.Text = \"Si, alteran el flujo y a los organ";
mostCurrent._rad10.setText((Object)("Si, alteran el flujo y a los organismos"));
 //BA.debugLineNum = 196;BA.debugLine="rad11.Text = \"No mucho\"";
mostCurrent._rad11.setText((Object)("No mucho"));
 //BA.debugLineNum = 197;BA.debugLine="lbl5.text = \"5. Las plantas acuáticas, ¿contribu";
_lbl5.setText((Object)("5. Las plantas acuáticas, ¿contribuyen a mejorar la calidad del agua?"));
 //BA.debugLineNum = 198;BA.debugLine="rad12.Text = \"Si, ayudan a remover contaminantes";
mostCurrent._rad12.setText((Object)("Si, ayudan a remover contaminantes"));
 //BA.debugLineNum = 199;BA.debugLine="rad13.Text = \"No, son feas y hay que removerlas\"";
mostCurrent._rad13.setText((Object)("No, son feas y hay que removerlas"));
 //BA.debugLineNum = 200;BA.debugLine="lbl6.Text = \"6. ¿Qué es la LIMNOLOGÍA?\"";
_lbl6.setText((Object)("6. ¿Qué es la LIMNOLOGÍA?"));
 //BA.debugLineNum = 201;BA.debugLine="rad14.Text = \"El estudio del agua\"";
mostCurrent._rad14.setText((Object)("El estudio del agua"));
 //BA.debugLineNum = 202;BA.debugLine="rad15.Text = \"El estudio de los animales y las p";
mostCurrent._rad15.setText((Object)("El estudio de los animales y las plantas"));
 //BA.debugLineNum = 203;BA.debugLine="rad16.Text = \"El estudio de las aguas continenta";
mostCurrent._rad16.setText((Object)("El estudio de las aguas continentales"));
 //BA.debugLineNum = 204;BA.debugLine="rad17.Text = \"Todas las anteriores\"";
mostCurrent._rad17.setText((Object)("Todas las anteriores"));
 //BA.debugLineNum = 205;BA.debugLine="lbl7.Text = \"7. ¿Qué factores afectan la calidad";
_lbl7.setText((Object)("7. ¿Qué factores afectan la calidad del agua y del hábitat de los ambientes acuáticos?"));
 //BA.debugLineNum = 206;BA.debugLine="rad18.Text = \"Los nutrientes\"";
mostCurrent._rad18.setText((Object)("Los nutrientes"));
 //BA.debugLineNum = 207;BA.debugLine="rad19.Text = \"Las canalizaciones y entubados\"";
mostCurrent._rad19.setText((Object)("Las canalizaciones y entubados"));
 //BA.debugLineNum = 208;BA.debugLine="rad20.Text = \"La basura\"";
mostCurrent._rad20.setText((Object)("La basura"));
 //BA.debugLineNum = 209;BA.debugLine="rad21.Text = \"Los metales pesados\"";
mostCurrent._rad21.setText((Object)("Los metales pesados"));
 //BA.debugLineNum = 210;BA.debugLine="rad22.Text = \"Todas las anteriores\"";
mostCurrent._rad22.setText((Object)("Todas las anteriores"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 212;BA.debugLine="lbl1.Text = \"1. Does cattle affect the water qua";
_lbl1.setText((Object)("1. Does cattle affect the water quality?"));
 //BA.debugLineNum = 213;BA.debugLine="rad1.Text = \"Yes, it does\"";
mostCurrent._rad1.setText((Object)("Yes, it does"));
 //BA.debugLineNum = 214;BA.debugLine="rad2.Text = \"No, not really\"";
mostCurrent._rad2.setText((Object)("No, not really"));
 //BA.debugLineNum = 215;BA.debugLine="lbl2.Text = \"2. Do debris or litter make the hab";
_lbl2.setText((Object)("2. Do debris or litter make the habitat quality better?"));
 //BA.debugLineNum = 216;BA.debugLine="rad3.Text = \"Yes, they improve the habitat\"";
mostCurrent._rad3.setText((Object)("Yes, they improve the habitat"));
 //BA.debugLineNum = 217;BA.debugLine="rad4.Text = \"No, they worsen the habitat\"";
mostCurrent._rad4.setText((Object)("No, they worsen the habitat"));
 //BA.debugLineNum = 218;BA.debugLine="lbl3.Text = \"3. Which of the following is NOT st";
_lbl3.setText((Object)("3. Which of the following is NOT studied by limnologists?"));
 //BA.debugLineNum = 219;BA.debugLine="rad5.Text = \"Rivers and streams\"";
mostCurrent._rad5.setText((Object)("Rivers and streams"));
 //BA.debugLineNum = 220;BA.debugLine="rad6.Text = \"Estuaries\"";
mostCurrent._rad6.setText((Object)("Estuaries"));
 //BA.debugLineNum = 221;BA.debugLine="rad7.Text = \"Lakes and lagoons\"";
mostCurrent._rad7.setText((Object)("Lakes and lagoons"));
 //BA.debugLineNum = 222;BA.debugLine="rad8.Text = \"The sea\"";
mostCurrent._rad8.setText((Object)("The sea"));
 //BA.debugLineNum = 223;BA.debugLine="rad9.Text = \"They are all studied by limnologist";
mostCurrent._rad9.setText((Object)("They are all studied by limnologists"));
 //BA.debugLineNum = 224;BA.debugLine="lbl4.Text = \"4. Do dams alter the organisms that";
_lbl4.setText((Object)("4. Do dams alter the organisms that inhabit rivers?"));
 //BA.debugLineNum = 225;BA.debugLine="rad10.Text = \"Yes, they alter the flow and the o";
mostCurrent._rad10.setText((Object)("Yes, they alter the flow and the organisms"));
 //BA.debugLineNum = 226;BA.debugLine="rad11.Text = \"Not much\"";
mostCurrent._rad11.setText((Object)("Not much"));
 //BA.debugLineNum = 227;BA.debugLine="lbl5.text = \"5. Do aquatic plants improve the wa";
_lbl5.setText((Object)("5. Do aquatic plants improve the water quality?"));
 //BA.debugLineNum = 228;BA.debugLine="rad12.Text = \"Yes, they remove pollutants\"";
mostCurrent._rad12.setText((Object)("Yes, they remove pollutants"));
 //BA.debugLineNum = 229;BA.debugLine="rad13.Text = \"No, they are ugly and need to be r";
mostCurrent._rad13.setText((Object)("No, they are ugly and need to be removed"));
 //BA.debugLineNum = 230;BA.debugLine="lbl6.Text = \"6. What is 'Limnology'?\"";
_lbl6.setText((Object)("6. What is 'Limnology'?"));
 //BA.debugLineNum = 231;BA.debugLine="rad14.Text = \"The study of water\"";
mostCurrent._rad14.setText((Object)("The study of water"));
 //BA.debugLineNum = 232;BA.debugLine="rad15.Text = \"The study of animals and plants\"";
mostCurrent._rad15.setText((Object)("The study of animals and plants"));
 //BA.debugLineNum = 233;BA.debugLine="rad16.Text = \"The study of continental (freshwat";
mostCurrent._rad16.setText((Object)("The study of continental (freshwater) waters"));
 //BA.debugLineNum = 234;BA.debugLine="rad17.Text = \"All of the above\"";
mostCurrent._rad17.setText((Object)("All of the above"));
 //BA.debugLineNum = 235;BA.debugLine="lbl7.Text = \"7. Which of these factors affect th";
_lbl7.setText((Object)("7. Which of these factors affect the water and habitat quality of the aquatic environments?"));
 //BA.debugLineNum = 236;BA.debugLine="rad18.Text = \"Nutrients\"";
mostCurrent._rad18.setText((Object)("Nutrients"));
 //BA.debugLineNum = 237;BA.debugLine="rad19.Text = \"Channelization and dredging\"";
mostCurrent._rad19.setText((Object)("Channelization and dredging"));
 //BA.debugLineNum = 238;BA.debugLine="rad20.Text = \"Litter\"";
mostCurrent._rad20.setText((Object)("Litter"));
 //BA.debugLineNum = 239;BA.debugLine="rad21.Text = \"Heavy metals\"";
mostCurrent._rad21.setText((Object)("Heavy metals"));
 //BA.debugLineNum = 240;BA.debugLine="rad22.Text = \"All of the above\"";
mostCurrent._rad22.setText((Object)("All of the above"));
 };
 //BA.debugLineNum = 244;BA.debugLine="lbl1.TextColor = Colors.White";
_lbl1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 245;BA.debugLine="lbl2.TextColor = Colors.White";
_lbl2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 246;BA.debugLine="lbl3.TextColor = Colors.White";
_lbl3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 247;BA.debugLine="lbl4.TextColor = Colors.White";
_lbl4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 248;BA.debugLine="lbl5.TextColor = Colors.White";
_lbl5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 249;BA.debugLine="lbl6.TextColor = Colors.White";
_lbl6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 250;BA.debugLine="lbl7.TextColor = Colors.White";
_lbl7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 252;BA.debugLine="rad1.TextColor = Colors.LightGray";
mostCurrent._rad1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 253;BA.debugLine="rad2.TextColor = Colors.LightGray";
mostCurrent._rad2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 254;BA.debugLine="rad3.TextColor = Colors.LightGray";
mostCurrent._rad3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 255;BA.debugLine="rad4.TextColor = Colors.LightGray";
mostCurrent._rad4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 256;BA.debugLine="rad5.TextColor = Colors.LightGray";
mostCurrent._rad5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 257;BA.debugLine="rad6.TextColor = Colors.LightGray";
mostCurrent._rad6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 258;BA.debugLine="rad7.TextColor = Colors.LightGray";
mostCurrent._rad7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 259;BA.debugLine="rad8.TextColor = Colors.LightGray";
mostCurrent._rad8.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 260;BA.debugLine="rad9.TextColor = Colors.LightGray";
mostCurrent._rad9.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 261;BA.debugLine="rad10.TextColor = Colors.LightGray";
mostCurrent._rad10.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 262;BA.debugLine="rad11.TextColor = Colors.LightGray";
mostCurrent._rad11.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 263;BA.debugLine="rad12.TextColor = Colors.LightGray";
mostCurrent._rad12.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 264;BA.debugLine="rad13.TextColor = Colors.LightGray";
mostCurrent._rad13.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 265;BA.debugLine="rad14.TextColor = Colors.LightGray";
mostCurrent._rad14.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 266;BA.debugLine="rad15.TextColor = Colors.LightGray";
mostCurrent._rad15.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 267;BA.debugLine="rad16.TextColor = Colors.LightGray";
mostCurrent._rad16.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 268;BA.debugLine="rad17.TextColor = Colors.LightGray";
mostCurrent._rad17.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 269;BA.debugLine="rad18.TextColor = Colors.LightGray";
mostCurrent._rad18.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 270;BA.debugLine="rad19.TextColor = Colors.LightGray";
mostCurrent._rad19.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 271;BA.debugLine="rad20.TextColor = Colors.LightGray";
mostCurrent._rad20.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 272;BA.debugLine="rad21.TextColor = Colors.LightGray";
mostCurrent._rad21.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 273;BA.debugLine="rad22.TextColor = Colors.LightGray";
mostCurrent._rad22.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 //BA.debugLineNum = 276;BA.debugLine="pnl1.Color = Colors.ARGB(255,119,119,119)";
_pnl1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (119),(int) (119),(int) (119)));
 //BA.debugLineNum = 277;BA.debugLine="pnl3.Color = Colors.ARGB(255,119,119,119)";
_pnl3.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (119),(int) (119),(int) (119)));
 //BA.debugLineNum = 278;BA.debugLine="pnl5.Color = Colors.ARGB(255,119,119,119)";
_pnl5.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (119),(int) (119),(int) (119)));
 //BA.debugLineNum = 279;BA.debugLine="pnl7.Color = Colors.ARGB(255,119,119,119)";
_pnl7.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (119),(int) (119),(int) (119)));
 //BA.debugLineNum = 281;BA.debugLine="pnl2.Color = Colors.ARGB(255,85,85,85)";
_pnl2.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (85),(int) (85),(int) (85)));
 //BA.debugLineNum = 282;BA.debugLine="pnl4.Color = Colors.ARGB(255,85,85,85)";
_pnl4.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (85),(int) (85),(int) (85)));
 //BA.debugLineNum = 283;BA.debugLine="pnl6.Color = Colors.ARGB(255,85,85,85)";
_pnl6.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (85),(int) (85),(int) (85)));
 //BA.debugLineNum = 286;BA.debugLine="pnl1.AddView(lbl1, 20, 0, 90%x, 40dip)";
_pnl1.AddView((android.view.View)(_lbl1.getObject()),(int) (20),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 287;BA.debugLine="pnl1.AddView(rad1, 0, lbl1.Height + lbl1.Top, 100";
_pnl1.AddView((android.view.View)(mostCurrent._rad1.getObject()),(int) (0),(int) (_lbl1.getHeight()+_lbl1.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 288;BA.debugLine="pnl1.AddView(rad2, 0, rad1.Height + rad1.Top , 10";
_pnl1.AddView((android.view.View)(mostCurrent._rad2.getObject()),(int) (0),(int) (mostCurrent._rad1.getHeight()+mostCurrent._rad1.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 290;BA.debugLine="pnl2.AddView(lbl2, 20, 0, 90%x, 40dip)";
_pnl2.AddView((android.view.View)(_lbl2.getObject()),(int) (20),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 291;BA.debugLine="pnl2.AddView(rad3, 0, lbl2.Height + lbl2.top, 100";
_pnl2.AddView((android.view.View)(mostCurrent._rad3.getObject()),(int) (0),(int) (_lbl2.getHeight()+_lbl2.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 292;BA.debugLine="pnl2.AddView(rad4, 0, rad3.Height + rad3.Top , 10";
_pnl2.AddView((android.view.View)(mostCurrent._rad4.getObject()),(int) (0),(int) (mostCurrent._rad3.getHeight()+mostCurrent._rad3.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 294;BA.debugLine="pnl3.AddView(lbl3, 20, 0, 90%x, 40dip)";
_pnl3.AddView((android.view.View)(_lbl3.getObject()),(int) (20),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 295;BA.debugLine="pnl3.AddView(rad5, 0, lbl3.Height + lbl3.top, 100";
_pnl3.AddView((android.view.View)(mostCurrent._rad5.getObject()),(int) (0),(int) (_lbl3.getHeight()+_lbl3.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 296;BA.debugLine="pnl3.AddView(rad6, 0, rad5.Height + rad5.Top , 10";
_pnl3.AddView((android.view.View)(mostCurrent._rad6.getObject()),(int) (0),(int) (mostCurrent._rad5.getHeight()+mostCurrent._rad5.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 297;BA.debugLine="pnl3.AddView(rad7, 0, rad6.Height + rad6.Top , 10";
_pnl3.AddView((android.view.View)(mostCurrent._rad7.getObject()),(int) (0),(int) (mostCurrent._rad6.getHeight()+mostCurrent._rad6.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 298;BA.debugLine="pnl3.AddView(rad8, 0, rad7.Height + rad7.Top , 10";
_pnl3.AddView((android.view.View)(mostCurrent._rad8.getObject()),(int) (0),(int) (mostCurrent._rad7.getHeight()+mostCurrent._rad7.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 299;BA.debugLine="pnl3.AddView(rad9, 0, rad8.Height + rad8.Top , 10";
_pnl3.AddView((android.view.View)(mostCurrent._rad9.getObject()),(int) (0),(int) (mostCurrent._rad8.getHeight()+mostCurrent._rad8.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 301;BA.debugLine="pnl4.AddView(lbl4, 20, 0, 90%x, 40dip)";
_pnl4.AddView((android.view.View)(_lbl4.getObject()),(int) (20),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 302;BA.debugLine="pnl4.AddView(rad10, 0, lbl4.Height + lbl4.top, 10";
_pnl4.AddView((android.view.View)(mostCurrent._rad10.getObject()),(int) (0),(int) (_lbl4.getHeight()+_lbl4.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 303;BA.debugLine="pnl4.AddView(rad11, 0, rad10.Height + rad10.Top ,";
_pnl4.AddView((android.view.View)(mostCurrent._rad11.getObject()),(int) (0),(int) (mostCurrent._rad10.getHeight()+mostCurrent._rad10.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 305;BA.debugLine="pnl5.AddView(lbl5, 20, 0, 90%x, 40dip)";
_pnl5.AddView((android.view.View)(_lbl5.getObject()),(int) (20),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 306;BA.debugLine="pnl5.AddView(rad12, 0, lbl5.Top + lbl5.Height, 10";
_pnl5.AddView((android.view.View)(mostCurrent._rad12.getObject()),(int) (0),(int) (_lbl5.getTop()+_lbl5.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 307;BA.debugLine="pnl5.AddView(rad13, 0, rad12.Height + rad12.Top ,";
_pnl5.AddView((android.view.View)(mostCurrent._rad13.getObject()),(int) (0),(int) (mostCurrent._rad12.getHeight()+mostCurrent._rad12.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 309;BA.debugLine="pnl6.AddView(lbl6, 20, 10, 90%x, 40dip)";
_pnl6.AddView((android.view.View)(_lbl6.getObject()),(int) (20),(int) (10),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 310;BA.debugLine="pnl6.AddView(rad14, 0, lbl6.Height + lbl6.top, 10";
_pnl6.AddView((android.view.View)(mostCurrent._rad14.getObject()),(int) (0),(int) (_lbl6.getHeight()+_lbl6.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 311;BA.debugLine="pnl6.AddView(rad15, 0, rad14.Height + rad14.Top ,";
_pnl6.AddView((android.view.View)(mostCurrent._rad15.getObject()),(int) (0),(int) (mostCurrent._rad14.getHeight()+mostCurrent._rad14.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 312;BA.debugLine="pnl6.AddView(rad16, 0, rad15.Height + rad15.Top ,";
_pnl6.AddView((android.view.View)(mostCurrent._rad16.getObject()),(int) (0),(int) (mostCurrent._rad15.getHeight()+mostCurrent._rad15.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 313;BA.debugLine="pnl6.AddView(rad17, 0, rad16.Height + rad16.Top ,";
_pnl6.AddView((android.view.View)(mostCurrent._rad17.getObject()),(int) (0),(int) (mostCurrent._rad16.getHeight()+mostCurrent._rad16.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 315;BA.debugLine="pnl7.AddView(lbl7, 20, 10, 90%x, 40dip)";
_pnl7.AddView((android.view.View)(_lbl7.getObject()),(int) (20),(int) (10),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 316;BA.debugLine="pnl7.AddView(rad18, 0, lbl7.Height + lbl7.Top, 10";
_pnl7.AddView((android.view.View)(mostCurrent._rad18.getObject()),(int) (0),(int) (_lbl7.getHeight()+_lbl7.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 317;BA.debugLine="pnl7.AddView(rad19, 0, rad18.Height + rad18.Top ,";
_pnl7.AddView((android.view.View)(mostCurrent._rad19.getObject()),(int) (0),(int) (mostCurrent._rad18.getHeight()+mostCurrent._rad18.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 318;BA.debugLine="pnl7.AddView(rad20, 0, rad19.Height + rad19.Top ,";
_pnl7.AddView((android.view.View)(mostCurrent._rad20.getObject()),(int) (0),(int) (mostCurrent._rad19.getHeight()+mostCurrent._rad19.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 319;BA.debugLine="pnl7.AddView(rad21, 0, rad20.Height + rad20.Top ,";
_pnl7.AddView((android.view.View)(mostCurrent._rad21.getObject()),(int) (0),(int) (mostCurrent._rad20.getHeight()+mostCurrent._rad20.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 320;BA.debugLine="pnl7.AddView(rad22, 0, rad21.Height + rad21.Top ,";
_pnl7.AddView((android.view.View)(mostCurrent._rad22.getObject()),(int) (0),(int) (mostCurrent._rad21.getHeight()+mostCurrent._rad21.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 323;BA.debugLine="scrQuiz.Panel.AddView(pnl1, 0, 0, 100%x, 90dip)";
mostCurrent._scrquiz.getPanel().AddView((android.view.View)(_pnl1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 324;BA.debugLine="scrQuiz.Panel.AddView(pnl2, 0, pnl1.Height + pnl1";
mostCurrent._scrquiz.getPanel().AddView((android.view.View)(_pnl2.getObject()),(int) (0),(int) (_pnl1.getHeight()+_pnl1.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 325;BA.debugLine="scrQuiz.Panel.AddView(pnl3, 0, pnl2.Height + pnl2";
mostCurrent._scrquiz.getPanel().AddView((android.view.View)(_pnl3.getObject()),(int) (0),(int) (_pnl2.getHeight()+_pnl2.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (170)));
 //BA.debugLineNum = 326;BA.debugLine="scrQuiz.Panel.AddView(pnl4, 0, pnl3.Height + pnl3";
mostCurrent._scrquiz.getPanel().AddView((android.view.View)(_pnl4.getObject()),(int) (0),(int) (_pnl3.getHeight()+_pnl3.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 327;BA.debugLine="scrQuiz.Panel.AddView(pnl5, 0, pnl4.Height + pnl4";
mostCurrent._scrquiz.getPanel().AddView((android.view.View)(_pnl5.getObject()),(int) (0),(int) (_pnl4.getHeight()+_pnl4.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90)));
 //BA.debugLineNum = 328;BA.debugLine="scrQuiz.Panel.AddView(pnl6, 0, pnl5.Height + pnl5";
mostCurrent._scrquiz.getPanel().AddView((android.view.View)(_pnl6.getObject()),(int) (0),(int) (_pnl5.getHeight()+_pnl5.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150)));
 //BA.debugLineNum = 329;BA.debugLine="scrQuiz.Panel.AddView(pnl7, 0, pnl6.Height + pnl6";
mostCurrent._scrquiz.getPanel().AddView((android.view.View)(_pnl7.getObject()),(int) (0),(int) (_pnl6.getHeight()+_pnl6.getTop()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150)));
 //BA.debugLineNum = 330;BA.debugLine="scrQuiz.Panel.Height = pnl1.Height + pnl2.Height";
mostCurrent._scrquiz.getPanel().setHeight((int) (_pnl1.getHeight()+_pnl2.getHeight()+_pnl3.getHeight()+_pnl4.getHeight()+_pnl5.getHeight()+_pnl6.getHeight()+_pnl7.getHeight()));
 //BA.debugLineNum = 332;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private scrQuiz As ScrollView";
mostCurrent._scrquiz = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Dim rad1, rad2, rad3, rad4, rad5, rad6, rad7, rad";
mostCurrent._rad1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad7 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad8 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad9 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad10 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad11 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad12 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad13 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad14 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad15 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad16 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad17 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad18 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad19 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad20 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad21 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
mostCurrent._rad22 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblAprendeMas As Label";
mostCurrent._lblaprendemas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnMuestreos As Button";
mostCurrent._btnmuestreos = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnTipoAmbientes As Button";
mostCurrent._btntipoambientes = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnFactores As Button";
mostCurrent._btnfactores = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnQuiz As Button";
mostCurrent._btnquiz = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnCerrarAprender As Button";
mostCurrent._btncerraraprender = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private btnCheckQuiz As Button";
mostCurrent._btncheckquiz = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private btnCadenas As Button";
mostCurrent._btncadenas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnCiclo As Button";
mostCurrent._btnciclo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnComunidades As Button";
mostCurrent._btncomunidades = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private btnContaminacion As Button";
mostCurrent._btncontaminacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 53;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 54;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 55;BA.debugLine="lblAprendeMas.Text = \"Learn about the aquatic en";
mostCurrent._lblaprendemas.setText((Object)("Learn about the aquatic environments while playing!"));
 //BA.debugLineNum = 56;BA.debugLine="btnMuestreos.Text = \"How to sample them?\"";
mostCurrent._btnmuestreos.setText((Object)("How to sample them?"));
 //BA.debugLineNum = 57;BA.debugLine="btnTipoAmbientes.Text = \"Environments\"";
mostCurrent._btntipoambientes.setText((Object)("Environments"));
 //BA.debugLineNum = 58;BA.debugLine="btnFactores.Text = \"What affects them?\"";
mostCurrent._btnfactores.setText((Object)("What affects them?"));
 //BA.debugLineNum = 59;BA.debugLine="btnCadenas.Text = \"Trophic networks\"";
mostCurrent._btncadenas.setText((Object)("Trophic networks"));
 //BA.debugLineNum = 60;BA.debugLine="btnCiclo.Text = \"The water cycle\"";
mostCurrent._btnciclo.setText((Object)("The water cycle"));
 //BA.debugLineNum = 61;BA.debugLine="btnComunidades.Text = \"Aquatic communities\"";
mostCurrent._btncomunidades.setText((Object)("Aquatic communities"));
 //BA.debugLineNum = 62;BA.debugLine="btnContaminacion.Text = \"Pollution\"";
mostCurrent._btncontaminacion.setText((Object)("Pollution"));
 //BA.debugLineNum = 63;BA.debugLine="btnQuiz.Text = \"Test your knowledge!\"";
mostCurrent._btnquiz.setText((Object)("Test your knowledge!"));
 };
 //BA.debugLineNum = 65;BA.debugLine="End Sub";
return "";
}
}
