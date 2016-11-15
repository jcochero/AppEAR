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

public class frmhabitatrio extends Activity implements B4AActivity{
	public static frmhabitatrio mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmhabitatrio");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmhabitatrio).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmhabitatrio");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmhabitatrio", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmhabitatrio) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmhabitatrio) Resume **");
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
		return frmhabitatrio.class;
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
        BA.LogInfo("** Activity (frmhabitatrio) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmhabitatrio) Resume **");
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
public anywheresoftware.b4a.objects.PanelWrapper _pnlquestions = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta8 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta9 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta10 = null;
public static String _minimagen = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta8 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsiguiente = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlopciones = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlchecks = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion5 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper _rdopcion6 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion3 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion2 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion1 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion4 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion5 = null;
public anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper _chkopcion6 = null;
public static String _imgej1 = "";
public anywheresoftware.b4a.objects.WebViewWrapper _explicacionviewer = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnrdopcion1 = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _csv = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _csvchk = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _recchk = null;
public static int _pgbpreguntas = 0;
public static int _cantidadpreguntas = 0;
public static int _currentpregunta = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpreguntas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlresultados = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnterminar = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrresultados = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipreguntaentubado = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloresultados = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public static String _langlocal = "";
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
 //BA.debugLineNum = 104;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 105;BA.debugLine="Activity.LoadLayout(\"HabitatRio\")";
mostCurrent._activity.LoadLayout("HabitatRio",mostCurrent.activityBA);
 //BA.debugLineNum = 106;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 107;BA.debugLine="If pnlQuestions.IsInitialized = False Then";
if (mostCurrent._pnlquestions.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 108;BA.debugLine="pnlQuestions.Initialize(\"pnlQuestions\")";
mostCurrent._pnlquestions.Initialize(mostCurrent.activityBA,"pnlQuestions");
 };
 //BA.debugLineNum = 111;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 112;BA.debugLine="pnlQuestions.LoadLayout(\"layPregunta\")";
mostCurrent._pnlquestions.LoadLayout("layPregunta",mostCurrent.activityBA);
 //BA.debugLineNum = 114;BA.debugLine="csv.Initialize(pnlOpciones)";
mostCurrent._csv.Initialize((android.view.View)(mostCurrent._pnlopciones.getObject()));
 //BA.debugLineNum = 115;BA.debugLine="csvChk.Initialize(pnlChecks)";
mostCurrent._csvchk.Initialize((android.view.View)(mostCurrent._pnlchecks.getObject()));
 //BA.debugLineNum = 121;BA.debugLine="Main.preguntanumero = 1";
mostCurrent._main._preguntanumero = (int) (1);
 //BA.debugLineNum = 122;BA.debugLine="Main.valorind1 = \"\"";
mostCurrent._main._valorind1 = "";
 //BA.debugLineNum = 123;BA.debugLine="Main.valorind2 = \"\"";
mostCurrent._main._valorind2 = "";
 //BA.debugLineNum = 124;BA.debugLine="Main.valorind3 = \"\"";
mostCurrent._main._valorind3 = "";
 //BA.debugLineNum = 125;BA.debugLine="Main.valorind4 = \"\"";
mostCurrent._main._valorind4 = "";
 //BA.debugLineNum = 126;BA.debugLine="Main.valorind5 = \"\"";
mostCurrent._main._valorind5 = "";
 //BA.debugLineNum = 127;BA.debugLine="Main.valorind6 = \"\"";
mostCurrent._main._valorind6 = "";
 //BA.debugLineNum = 128;BA.debugLine="Main.valorind7 = \"\"";
mostCurrent._main._valorind7 = "";
 //BA.debugLineNum = 129;BA.debugLine="Main.valorind8 = \"\"";
mostCurrent._main._valorind8 = "";
 //BA.debugLineNum = 130;BA.debugLine="Main.valorind9 = \"\"";
mostCurrent._main._valorind9 = "";
 //BA.debugLineNum = 131;BA.debugLine="Main.valorind10 = \"\"";
mostCurrent._main._valorind10 = "";
 //BA.debugLineNum = 132;BA.debugLine="Main.valorind11 = \"\"";
mostCurrent._main._valorind11 = "";
 //BA.debugLineNum = 133;BA.debugLine="Main.valorind12 = \"\"";
mostCurrent._main._valorind12 = "";
 //BA.debugLineNum = 134;BA.debugLine="Main.valorind13 = \"\"";
mostCurrent._main._valorind13 = "";
 //BA.debugLineNum = 135;BA.debugLine="Main.valorind14 = \"\"";
mostCurrent._main._valorind14 = "";
 //BA.debugLineNum = 136;BA.debugLine="Main.valorind15 = \"\"";
mostCurrent._main._valorind15 = "";
 //BA.debugLineNum = 137;BA.debugLine="Main.valorind16 = \"\"";
mostCurrent._main._valorind16 = "";
 //BA.debugLineNum = 138;BA.debugLine="miniPregunta1.SetBackgroundImage(Null)";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 139;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 140;BA.debugLine="miniPregunta3.SetBackgroundImage(Null)";
mostCurrent._minipregunta3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 141;BA.debugLine="miniPregunta4.SetBackgroundImage(Null)";
mostCurrent._minipregunta4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 142;BA.debugLine="miniPregunta5.SetBackgroundImage(Null)";
mostCurrent._minipregunta5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 143;BA.debugLine="miniPregunta6.SetBackgroundImage(Null)";
mostCurrent._minipregunta6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 144;BA.debugLine="miniPregunta7.SetBackgroundImage(Null)";
mostCurrent._minipregunta7.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 145;BA.debugLine="miniPregunta8.SetBackgroundImage(Null)";
mostCurrent._minipregunta8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 146;BA.debugLine="miniPregunta9.SetBackgroundImage(Null)";
mostCurrent._minipregunta9.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 147;BA.debugLine="miniPregunta10.SetBackgroundImage(Null)";
mostCurrent._minipregunta10.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 148;BA.debugLine="miniPreguntaEntubado.SetBackgroundImage(Null)";
mostCurrent._minipreguntaentubado.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 150;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 151;BA.debugLine="DateTime.TimeFormat = \"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 152;BA.debugLine="Main.dateandtime = DateTime.Date(DateTime.now)";
mostCurrent._main._dateandtime = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 153;BA.debugLine="Main.valorcalidad = 0";
mostCurrent._main._valorcalidad = 0;
 //BA.debugLineNum = 154;BA.debugLine="Main.valorNS = 0";
mostCurrent._main._valorns = (int) (0);
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
String _filename = "";
String _contenidoexportar = "";
 //BA.debugLineNum = 1766;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 1769;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 1770;BA.debugLine="filename = Main.username & \"@\" & frmEvaluacion.c";
_filename = mostCurrent._main._username+"@"+mostCurrent._frmevaluacion._currentproject+"@"+mostCurrent._main._nombrerio+"-"+mostCurrent._main._dateandtime;
 //BA.debugLineNum = 1771;BA.debugLine="Dim contenidoexportar As String";
_contenidoexportar = "";
 //BA.debugLineNum = 1772;BA.debugLine="contenidoexportar = Main.username & \";\" & Main.d";
_contenidoexportar = mostCurrent._main._username+";"+mostCurrent._main._dateandtime+";"+mostCurrent._main._latitud+";"+mostCurrent._main._longitud+";"+mostCurrent._main._tiporio+";"+mostCurrent._main._nombrerio+";"+BA.NumberToString(mostCurrent._main._valorcalidad)+";"+BA.NumberToString(mostCurrent._main._valorns)+";"+mostCurrent._main._valorind1+";"+mostCurrent._main._valorind2+";"+mostCurrent._main._valorind3+";"+mostCurrent._main._valorind4+";"+mostCurrent._main._valorind5+";"+mostCurrent._main._valorind6+";"+mostCurrent._main._valorind7+";"+mostCurrent._main._valorind8+";"+mostCurrent._main._valorind9+";"+mostCurrent._main._valorind10+";"+mostCurrent._main._valorind11+";"+mostCurrent._main._valorind12+";"+mostCurrent._main._valorind13+";"+mostCurrent._main._valorind14+";"+mostCurrent._main._valorind15+";"+mostCurrent._main._valorind16;
 //BA.debugLineNum = 1778;BA.debugLine="Main.evaluacionpath = filename";
mostCurrent._main._evaluacionpath = _filename;
 //BA.debugLineNum = 1779;BA.debugLine="File.WriteString(Main.savedir & \"/AppEAR/\", file";
anywheresoftware.b4a.keywords.Common.File.WriteString(mostCurrent._main._savedir+"/AppEAR/",_filename+".txt",_contenidoexportar);
 //BA.debugLineNum = 1782;BA.debugLine="Main.preguntanumero = 0";
mostCurrent._main._preguntanumero = (int) (0);
 //BA.debugLineNum = 1783;BA.debugLine="frmEvaluacion.evaluaciondone = True";
mostCurrent._frmevaluacion._evaluaciondone = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 1784;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1785;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1786;BA.debugLine="StartActivity(frmEvaluacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmevaluacion.getObject()));
 //BA.debugLineNum = 1788;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta1_click() throws Exception{
 //BA.debugLineNum = 446;BA.debugLine="Sub btnPregunta1_Click";
 //BA.debugLineNum = 447;BA.debugLine="CargarPregunta(1)";
_cargarpregunta((int) (1));
 //BA.debugLineNum = 448;BA.debugLine="currentpregunta = 1";
_currentpregunta = (int) (1);
 //BA.debugLineNum = 449;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta10_click() throws Exception{
 //BA.debugLineNum = 482;BA.debugLine="Sub btnPregunta10_Click";
 //BA.debugLineNum = 483;BA.debugLine="CargarPregunta(13)";
_cargarpregunta((int) (13));
 //BA.debugLineNum = 484;BA.debugLine="currentpregunta = 13";
_currentpregunta = (int) (13);
 //BA.debugLineNum = 485;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta2_click() throws Exception{
 //BA.debugLineNum = 450;BA.debugLine="Sub btnPregunta2_Click";
 //BA.debugLineNum = 451;BA.debugLine="CargarPregunta(2)";
_cargarpregunta((int) (2));
 //BA.debugLineNum = 452;BA.debugLine="currentpregunta = 2";
_currentpregunta = (int) (2);
 //BA.debugLineNum = 453;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta3_click() throws Exception{
 //BA.debugLineNum = 454;BA.debugLine="Sub btnPregunta3_Click";
 //BA.debugLineNum = 455;BA.debugLine="CargarPregunta(3)";
_cargarpregunta((int) (3));
 //BA.debugLineNum = 456;BA.debugLine="currentpregunta = 3";
_currentpregunta = (int) (3);
 //BA.debugLineNum = 457;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta4_click() throws Exception{
 //BA.debugLineNum = 458;BA.debugLine="Sub btnPregunta4_Click";
 //BA.debugLineNum = 459;BA.debugLine="CargarPregunta(4)";
_cargarpregunta((int) (4));
 //BA.debugLineNum = 460;BA.debugLine="currentpregunta = 4";
_currentpregunta = (int) (4);
 //BA.debugLineNum = 461;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta5_click() throws Exception{
 //BA.debugLineNum = 462;BA.debugLine="Sub btnPregunta5_Click";
 //BA.debugLineNum = 463;BA.debugLine="CargarPregunta(5)";
_cargarpregunta((int) (5));
 //BA.debugLineNum = 464;BA.debugLine="currentpregunta = 5";
_currentpregunta = (int) (5);
 //BA.debugLineNum = 465;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta6_click() throws Exception{
 //BA.debugLineNum = 466;BA.debugLine="Sub btnPregunta6_Click";
 //BA.debugLineNum = 467;BA.debugLine="CargarPregunta(7)";
_cargarpregunta((int) (7));
 //BA.debugLineNum = 468;BA.debugLine="currentpregunta = 7";
_currentpregunta = (int) (7);
 //BA.debugLineNum = 469;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta7_click() throws Exception{
 //BA.debugLineNum = 470;BA.debugLine="Sub btnPregunta7_Click";
 //BA.debugLineNum = 471;BA.debugLine="CargarPregunta(9)";
_cargarpregunta((int) (9));
 //BA.debugLineNum = 472;BA.debugLine="currentpregunta = 9";
_currentpregunta = (int) (9);
 //BA.debugLineNum = 473;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta8_click() throws Exception{
 //BA.debugLineNum = 474;BA.debugLine="Sub btnPregunta8_Click";
 //BA.debugLineNum = 475;BA.debugLine="CargarPregunta(11)";
_cargarpregunta((int) (11));
 //BA.debugLineNum = 476;BA.debugLine="currentpregunta = 11";
_currentpregunta = (int) (11);
 //BA.debugLineNum = 477;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta9_click() throws Exception{
 //BA.debugLineNum = 478;BA.debugLine="Sub btnPregunta9_Click";
 //BA.debugLineNum = 479;BA.debugLine="CargarPregunta(12)";
_cargarpregunta((int) (12));
 //BA.debugLineNum = 480;BA.debugLine="currentpregunta = 12";
_currentpregunta = (int) (12);
 //BA.debugLineNum = 481;BA.debugLine="End Sub";
return "";
}
public static String  _btnrdopcion1_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _fondogris = null;
String _htmlej = "";
anywheresoftware.b4a.objects.ButtonWrapper _butongris = null;
 //BA.debugLineNum = 248;BA.debugLine="Sub btnRdOpcion1_Click";
 //BA.debugLineNum = 250;BA.debugLine="Dim fondogris As ImageView";
_fondogris = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 251;BA.debugLine="fondogris.Initialize(\"fondogris\")";
_fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 252;BA.debugLine="fondogris.Color = Colors.ARGB(150, 64,64,64)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 253;BA.debugLine="Activity.AddView(fondogris, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 256;BA.debugLine="Dim htmlej As String";
_htmlej = "";
 //BA.debugLineNum = 257;BA.debugLine="htmlej = File.GetText(File.DirAssets, imgej1)";
_htmlej = anywheresoftware.b4a.keywords.Common.File.GetText(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imgej1);
 //BA.debugLineNum = 259;BA.debugLine="explicacionviewer.Initialize(\"explicacionviewer\")";
mostCurrent._explicacionviewer.Initialize(mostCurrent.activityBA,"explicacionviewer");
 //BA.debugLineNum = 260;BA.debugLine="explicacionviewer.LoadHTML(htmlej)";
mostCurrent._explicacionviewer.LoadHtml(_htmlej);
 //BA.debugLineNum = 261;BA.debugLine="Activity.AddView(explicacionviewer, 15%x, 15%y, 7";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._explicacionviewer.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (75),mostCurrent.activityBA));
 //BA.debugLineNum = 264;BA.debugLine="Dim butongris As Button";
_butongris = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 265;BA.debugLine="butongris.Initialize(\"fondogris\")";
_butongris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 266;BA.debugLine="butongris.Color = Colors.ARGB(255, 255,255,255)";
_butongris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 267;BA.debugLine="butongris.TextColor = Colors.Black";
_butongris.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 268;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 269;BA.debugLine="butongris.Text = \"Cerrar\"";
_butongris.setText((Object)("Cerrar"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 271;BA.debugLine="butongris.Text = \"Close\"";
_butongris.setText((Object)("Close"));
 };
 //BA.debugLineNum = 273;BA.debugLine="Activity.AddView(butongris, 15%x, 90%y, 75%x, 5%y";
mostCurrent._activity.AddView((android.view.View)(_butongris.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
public static String  _btnshare_click() throws Exception{
String _filename = "";
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
long _now = 0L;
Object[] _args = null;
String[] _types = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
 //BA.debugLineNum = 1801;BA.debugLine="Sub btnShare_Click";
 //BA.debugLineNum = 1803;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1804;BA.debugLine="ToastMessageShow(\"Capturando para compartir....";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Capturando para compartir....",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1806;BA.debugLine="ToastMessageShow(\"Capturing image to share....\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Capturing image to share....",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1809;BA.debugLine="lblPregunta10.Visible = False";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1810;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1811;BA.debugLine="btnTerminar.Visible = False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1814;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 1815;BA.debugLine="filename = Main.username & \"_\" & frmEvaluacion.cu";
_filename = mostCurrent._main._username+"_"+mostCurrent._frmevaluacion._currentproject;
 //BA.debugLineNum = 1818;BA.debugLine="Dim Obj1, Obj2 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1819;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1820;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 1821;BA.debugLine="Dim now As Long";
_now = 0L;
 //BA.debugLineNum = 1822;BA.debugLine="DateTime.DateFormat = \"yyMMddHHmmss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyMMddHHmmss");
 //BA.debugLineNum = 1823;BA.debugLine="now = DateTime.Now";
_now = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1824;BA.debugLine="Obj1.Target = Obj1.GetActivityBA";
_obj1.Target = (Object)(_obj1.GetActivityBA(processBA));
 //BA.debugLineNum = 1825;BA.debugLine="Obj1.Target = Obj1.GetField(\"vg\")";
_obj1.Target = _obj1.GetField("vg");
 //BA.debugLineNum = 1826;BA.debugLine="bmp.InitializeMutable(Activity.Width, Activity.";
_bmp.InitializeMutable(mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight());
 //BA.debugLineNum = 1827;BA.debugLine="c.Initialize2(bmp)";
_c.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 1828;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int) (1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 1829;BA.debugLine="Dim types(1) As String";
_types = new String[(int) (1)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 1830;BA.debugLine="Obj2.Target = c";
_obj2.Target = (Object)(_c);
 //BA.debugLineNum = 1831;BA.debugLine="Obj2.Target = Obj2.GetField(\"canvas\")";
_obj2.Target = _obj2.GetField("canvas");
 //BA.debugLineNum = 1832;BA.debugLine="args(0) = Obj2.Target";
_args[(int) (0)] = _obj2.Target;
 //BA.debugLineNum = 1833;BA.debugLine="types(0) = \"android.graphics.Canvas\"";
_types[(int) (0)] = "android.graphics.Canvas";
 //BA.debugLineNum = 1834;BA.debugLine="Obj1.RunMethod4(\"draw\", args, types)";
_obj1.RunMethod4("draw",_args,_types);
 //BA.debugLineNum = 1835;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 1836;BA.debugLine="out = File.OpenOutput(Main.savedir & \"/AppEAR/\"";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(mostCurrent._main._savedir+"/AppEAR/","Final-"+_filename+".png",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1837;BA.debugLine="bmp.WriteToStream(out, 100, \"PNG\")";
_bmp.WriteToStream((java.io.OutputStream)(_out.getObject()),(int) (100),BA.getEnumFromString(android.graphics.Bitmap.CompressFormat.class,"PNG"));
 //BA.debugLineNum = 1838;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 1841;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", \"Final-\"";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/","Final-"+_filename+".png",anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"Share-Appear.png");
 //BA.debugLineNum = 1845;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 1846;BA.debugLine="share.sharebinary(\"file://\" & File.DirDefaultExte";
_share.sharebinary(mostCurrent.activityBA,"file://"+anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal()+"/Share-Appear.png","image/png","Yo utilizo AppEAR y ayudo a la ciencia!","He evaluado otro ambiente acuático!");
 //BA.debugLineNum = 1849;BA.debugLine="pnlResultados.Visible = True";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1850;BA.debugLine="btnTerminar.Visible = True";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1853;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1854;BA.debugLine="ToastMessageShow(\"Buscando puntos....\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Buscando puntos....",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1856;BA.debugLine="ToastMessageShow(\"Fetching points....\",False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Fetching points....",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1859;BA.debugLine="Main.numshares = Main.numshares + 1";
mostCurrent._main._numshares = (int) (mostCurrent._main._numshares+1);
 //BA.debugLineNum = 1860;BA.debugLine="EnviarShares";
_enviarshares();
 //BA.debugLineNum = 1861;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1862;BA.debugLine="End Sub";
return "";
}
public static String  _btnsiguiente_click() throws Exception{
String _valresp = "";
int _promagua = 0;
int _divagua = 0;
int _promdesb = 0;
int _didesb = 0;
anywheresoftware.b4a.objects.drawable.GradientDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 1355;BA.debugLine="Sub btnSiguiente_Click";
 //BA.debugLineNum = 1356;BA.debugLine="Dim valresp As String = \"\"";
_valresp = "";
 //BA.debugLineNum = 1357;BA.debugLine="valresp = ValidarRespuesta";
_valresp = _validarrespuesta();
 //BA.debugLineNum = 1359;BA.debugLine="If valresp = \"no\" Then";
if ((_valresp).equals("no")) { 
 //BA.debugLineNum = 1360;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1361;BA.debugLine="Msgbox(\"Debe seleccionar una opción para contin";
anywheresoftware.b4a.keywords.Common.Msgbox("Debe seleccionar una opción para continuar","Atención!",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1363;BA.debugLine="Msgbox(\"You have to select an option to continu";
anywheresoftware.b4a.keywords.Common.Msgbox("You have to select an option to continue","Attention",mostCurrent.activityBA);
 };
 }else {
 //BA.debugLineNum = 1366;BA.debugLine="If btnSiguiente.Text = \"Ok\" Then";
if ((mostCurrent._btnsiguiente.getText()).equals("Ok")) { 
 //BA.debugLineNum = 1367;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1369;BA.debugLine="ColorearCirculos(btnPregunta1, Main.valorind1)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta1.getObject())),mostCurrent._main._valorind1);
 //BA.debugLineNum = 1370;BA.debugLine="ColorearCirculos(btnPregunta2, Main.valorind2)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta2.getObject())),mostCurrent._main._valorind2);
 //BA.debugLineNum = 1371;BA.debugLine="ColorearCirculos(btnPregunta3, Main.valorind3)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta3.getObject())),mostCurrent._main._valorind3);
 //BA.debugLineNum = 1372;BA.debugLine="ColorearCirculos(btnPregunta4, Main.valorind4)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta4.getObject())),mostCurrent._main._valorind4);
 //BA.debugLineNum = 1373;BA.debugLine="ColorearCirculos(btnPregunta6, Main.valorind7)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta6.getObject())),mostCurrent._main._valorind7);
 //BA.debugLineNum = 1374;BA.debugLine="ColorearCirculos(btnPregunta8, Main.valorind11)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta8.getObject())),mostCurrent._main._valorind11);
 //BA.debugLineNum = 1375;BA.debugLine="ColorearCirculos(btnPregunta9, Main.valorind12)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta9.getObject())),mostCurrent._main._valorind12);
 //BA.debugLineNum = 1376;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 1377;BA.debugLine="ColorearCirculos(btnPregunta10, Main.valorind1";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta10.getObject())),mostCurrent._main._valorind13);
 };
 //BA.debugLineNum = 1381;BA.debugLine="Dim promagua As Int";
_promagua = 0;
 //BA.debugLineNum = 1382;BA.debugLine="Dim divagua As Int";
_divagua = 0;
 //BA.debugLineNum = 1383;BA.debugLine="If Main.valorind5 <> \"\" And Main.valorind5 <> \"";
if ((mostCurrent._main._valorind5).equals("") == false && (mostCurrent._main._valorind5).equals("NS") == false) { 
 //BA.debugLineNum = 1384;BA.debugLine="promagua = promagua + Main.valorind5";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._main._valorind5)));
 //BA.debugLineNum = 1385;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1387;BA.debugLine="If Main.valorind6 <> \"\" And Main.valorind6 <> \"";
if ((mostCurrent._main._valorind6).equals("") == false && (mostCurrent._main._valorind6).equals("NS") == false) { 
 //BA.debugLineNum = 1388;BA.debugLine="promagua = promagua + Main.valorind6";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._main._valorind6)));
 //BA.debugLineNum = 1389;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1391;BA.debugLine="If Main.valorind8 <> \"\" And Main.valorind8 <> \"";
if ((mostCurrent._main._valorind8).equals("") == false && (mostCurrent._main._valorind8).equals("NS") == false) { 
 //BA.debugLineNum = 1392;BA.debugLine="promagua = promagua + Main.valorind8";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._main._valorind8)));
 //BA.debugLineNum = 1393;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1395;BA.debugLine="If divagua <> 0 Then";
if (_divagua!=0) { 
 //BA.debugLineNum = 1396;BA.debugLine="promagua = promagua / divagua";
_promagua = (int) (_promagua/(double)_divagua);
 //BA.debugLineNum = 1397;BA.debugLine="ColorearCirculos(btnPregunta5, promagua)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),BA.NumberToString(_promagua));
 }else {
 //BA.debugLineNum = 1399;BA.debugLine="If Main.valorind5 <> \"NS\" And Main.valorind6 <";
if ((mostCurrent._main._valorind5).equals("NS") == false && (mostCurrent._main._valorind6).equals("NS") == false && (mostCurrent._main._valorind8).equals("NS") == false) { 
 //BA.debugLineNum = 1400;BA.debugLine="ColorearCirculos(btnPregunta5, \"\")";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),"");
 }else {
 //BA.debugLineNum = 1402;BA.debugLine="ColorearCirculos(btnPregunta5, \"NS\")";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1408;BA.debugLine="Dim promdesb As Int";
_promdesb = 0;
 //BA.debugLineNum = 1409;BA.debugLine="Dim didesb As Int";
_didesb = 0;
 //BA.debugLineNum = 1410;BA.debugLine="If Main.valorind9 <> \"\" And Main.valorind9 <> \"";
if ((mostCurrent._main._valorind9).equals("") == false && (mostCurrent._main._valorind9).equals("NS") == false) { 
 //BA.debugLineNum = 1411;BA.debugLine="promdesb = promdesb + Main.valorind9";
_promdesb = (int) (_promdesb+(double)(Double.parseDouble(mostCurrent._main._valorind9)));
 //BA.debugLineNum = 1412;BA.debugLine="didesb = didesb + 1";
_didesb = (int) (_didesb+1);
 };
 //BA.debugLineNum = 1414;BA.debugLine="If Main.valorind10 <> \"\" And Main.valorind10 <>";
if ((mostCurrent._main._valorind10).equals("") == false && (mostCurrent._main._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1415;BA.debugLine="promdesb = promdesb + Main.valorind10";
_promdesb = (int) (_promdesb+(double)(Double.parseDouble(mostCurrent._main._valorind10)));
 //BA.debugLineNum = 1416;BA.debugLine="didesb = didesb + 1";
_didesb = (int) (_didesb+1);
 };
 //BA.debugLineNum = 1419;BA.debugLine="If didesb <> 0 Then";
if (_didesb!=0) { 
 //BA.debugLineNum = 1420;BA.debugLine="promdesb = promdesb / didesb";
_promdesb = (int) (_promdesb/(double)_didesb);
 //BA.debugLineNum = 1421;BA.debugLine="ColorearCirculos(btnPregunta7, promdesb)";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),BA.NumberToString(_promdesb));
 }else {
 //BA.debugLineNum = 1423;BA.debugLine="If Main.valorind9 <> \"NS\" And Main.valorind10";
if ((mostCurrent._main._valorind9).equals("NS") == false && (mostCurrent._main._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1424;BA.debugLine="ColorearCirculos(btnPregunta7, \"\")";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),"");
 }else {
 //BA.debugLineNum = 1426;BA.debugLine="ColorearCirculos(btnPregunta7, \"NS\")";
_colorearcirculos((anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1431;BA.debugLine="Dim gd As GradientDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.GradientDrawable();
 //BA.debugLineNum = 1432;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 1433;BA.debugLine="gd.Initialize(\"TOP_BOTTOM\", Array As Int(Color";
_gd.Initialize(BA.getEnumFromString(android.graphics.drawable.GradientDrawable.Orientation.class,"TOP_BOTTOM"),new int[]{anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.White});
 //BA.debugLineNum = 1434;BA.debugLine="cd.Initialize(Colors.ARGB(255,139,211,175), 1d";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (139),(int) (211),(int) (175)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 1440;BA.debugLine="cantidadpreguntas = 0";
_cantidadpreguntas = (int) (0);
 //BA.debugLineNum = 1441;BA.debugLine="If Main.valorind1 <> \"\" Then";
if ((mostCurrent._main._valorind1).equals("") == false) { 
 //BA.debugLineNum = 1442;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1444;BA.debugLine="If Main.valorind2 <> \"\" Then";
if ((mostCurrent._main._valorind2).equals("") == false) { 
 //BA.debugLineNum = 1445;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1447;BA.debugLine="If Main.valorind3 <> \"\" Then";
if ((mostCurrent._main._valorind3).equals("") == false) { 
 //BA.debugLineNum = 1448;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1450;BA.debugLine="If Main.valorind4 <> \"\" Then";
if ((mostCurrent._main._valorind4).equals("") == false) { 
 //BA.debugLineNum = 1451;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1453;BA.debugLine="If Main.valorind5 <> \"\" Then";
if ((mostCurrent._main._valorind5).equals("") == false) { 
 //BA.debugLineNum = 1454;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1456;BA.debugLine="If Main.valorind6 <> \"\" Then";
if ((mostCurrent._main._valorind6).equals("") == false) { 
 //BA.debugLineNum = 1457;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1459;BA.debugLine="If Main.valorind7 <> \"\" Then";
if ((mostCurrent._main._valorind7).equals("") == false) { 
 //BA.debugLineNum = 1460;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1462;BA.debugLine="If Main.valorind8 <> \"\" Then";
if ((mostCurrent._main._valorind8).equals("") == false) { 
 //BA.debugLineNum = 1463;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1465;BA.debugLine="If Main.valorind9 <> \"\" Then";
if ((mostCurrent._main._valorind9).equals("") == false) { 
 //BA.debugLineNum = 1466;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1468;BA.debugLine="If Main.valorind10 <> \"\" Then";
if ((mostCurrent._main._valorind10).equals("") == false) { 
 //BA.debugLineNum = 1469;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1471;BA.debugLine="If Main.valorind11 <> \"\" Then";
if ((mostCurrent._main._valorind11).equals("") == false) { 
 //BA.debugLineNum = 1472;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1474;BA.debugLine="If Main.valorind12 <> \"\" Then";
if ((mostCurrent._main._valorind12).equals("") == false) { 
 //BA.debugLineNum = 1475;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1477;BA.debugLine="If Main.valorind13 <> \"\" Then";
if ((mostCurrent._main._valorind13).equals("") == false) { 
 //BA.debugLineNum = 1478;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1480;BA.debugLine="If Main.valorind14 <> \"\" Then";
if ((mostCurrent._main._valorind14).equals("") == false) { 
 //BA.debugLineNum = 1481;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1483;BA.debugLine="If Main.valorind15 <> \"\" Then";
if ((mostCurrent._main._valorind15).equals("") == false) { 
 //BA.debugLineNum = 1484;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1486;BA.debugLine="If Main.valorind16 <> \"\" Then";
if ((mostCurrent._main._valorind16).equals("") == false) { 
 //BA.debugLineNum = 1487;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1492;BA.debugLine="If Main.tiporio = \"Llanura\" Then";
if ((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 1493;BA.debugLine="pgbPreguntas = (cantidadpreguntas * 100) / 12";
_pgbpreguntas = (int) ((_cantidadpreguntas*100)/(double)12);
 }else if((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 1495;BA.debugLine="pgbPreguntas = (cantidadpreguntas * 100) / 13";
_pgbpreguntas = (int) ((_cantidadpreguntas*100)/(double)13);
 };
 //BA.debugLineNum = 1499;BA.debugLine="If btnPregunta1.Visible = True And btnPregunta1";
if (mostCurrent._btnpregunta1.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta1.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1500;BA.debugLine="btnPregunta2.Visible = True";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1501;BA.debugLine="lblPregunta2.Visible = True";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1502;BA.debugLine="lblPregunta1.Visible = False";
mostCurrent._lblpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1504;BA.debugLine="If btnPregunta2.Visible = True And btnPregunta2";
if (mostCurrent._btnpregunta2.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta2.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1505;BA.debugLine="btnPregunta3.Visible = True";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1506;BA.debugLine="lblPregunta3.Visible = True";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1507;BA.debugLine="lblPregunta2.Visible = False";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1509;BA.debugLine="If btnPregunta3.Visible = True And btnPregunta3";
if (mostCurrent._btnpregunta3.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta3.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1510;BA.debugLine="btnPregunta4.Visible = True";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1511;BA.debugLine="lblPregunta4.Visible = True";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1512;BA.debugLine="lblPregunta3.Visible = False";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1514;BA.debugLine="If btnPregunta4.Visible = True And btnPregunta4";
if (mostCurrent._btnpregunta4.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta4.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1515;BA.debugLine="btnPregunta5.Visible = True";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1516;BA.debugLine="lblPregunta5.Visible = True";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1517;BA.debugLine="lblPregunta4.Visible = False";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1519;BA.debugLine="If btnPregunta5.Visible = True And btnPregunta5";
if (mostCurrent._btnpregunta5.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta5.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1520;BA.debugLine="btnPregunta6.Visible = True";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1521;BA.debugLine="lblPregunta6.Visible = True";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1522;BA.debugLine="lblPregunta5.Visible = False";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1524;BA.debugLine="If btnPregunta6.Visible = True And btnPregunta6";
if (mostCurrent._btnpregunta6.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta6.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1525;BA.debugLine="btnPregunta7.Visible = True";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1526;BA.debugLine="lblPregunta7.Visible = True";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1527;BA.debugLine="lblPregunta6.Visible = False";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1529;BA.debugLine="If btnPregunta7.Visible = True And btnPregunta7";
if (mostCurrent._btnpregunta7.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta7.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1530;BA.debugLine="btnPregunta8.Visible = True";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1531;BA.debugLine="lblPregunta8.Visible = True";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1532;BA.debugLine="lblPregunta7.Visible = False";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1534;BA.debugLine="If btnPregunta8.Visible = True And btnPregunta8";
if (mostCurrent._btnpregunta8.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta8.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1535;BA.debugLine="btnPregunta9.Visible = True";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1536;BA.debugLine="lblPregunta9.Visible = True";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1537;BA.debugLine="lblPregunta8.Visible = False";
mostCurrent._lblpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1539;BA.debugLine="If btnPregunta9.Visible = True And btnPregunta9";
if (mostCurrent._btnpregunta9.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnpregunta9.getTextColor()!=anywheresoftware.b4a.keywords.Common.Colors.Gray) { 
 //BA.debugLineNum = 1541;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 1542;BA.debugLine="btnPregunta10.Visible = True";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1543;BA.debugLine="lblPregunta10.visible = True";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1544;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 1546;BA.debugLine="btnPregunta10.Visible = False";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1547;BA.debugLine="lblPregunta10.visible = False";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1548;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 1553;BA.debugLine="If pgbPreguntas = 100 Then";
if (_pgbpreguntas==100) { 
 //BA.debugLineNum = 1554;BA.debugLine="btnTerminar.Visible = True";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 1557;BA.debugLine="currentpregunta = btnSiguiente.tag";
_currentpregunta = (int)(BA.ObjectToNumber(mostCurrent._btnsiguiente.getTag()));
 //BA.debugLineNum = 1558;BA.debugLine="CargarPregunta(btnSiguiente.Tag)";
_cargarpregunta((int)(BA.ObjectToNumber(mostCurrent._btnsiguiente.getTag())));
 };
 };
 //BA.debugLineNum = 1562;BA.debugLine="End Sub";
return "";
}
public static String  _btnterminar_click() throws Exception{
 //BA.debugLineNum = 1626;BA.debugLine="Sub btnTerminar_Click";
 //BA.debugLineNum = 1627;BA.debugLine="TerminarEval";
_terminareval();
 //BA.debugLineNum = 1628;BA.debugLine="End Sub";
return "";
}
public static String  _buscarpuntos() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _getpuntos = null;
 //BA.debugLineNum = 1873;BA.debugLine="Sub BuscarPuntos";
 //BA.debugLineNum = 1874;BA.debugLine="Dim GetPuntos As HttpJob";
_getpuntos = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 1875;BA.debugLine="GetPuntos.Initialize(\"GetPuntos\",Me)";
_getpuntos._initialize(processBA,"GetPuntos",frmhabitatrio.getObject());
 //BA.debugLineNum = 1876;BA.debugLine="GetPuntos.Download2(\"http://www.app-ear.com.ar/co";
_getpuntos._download2("http://www.app-ear.com.ar/connect/getpuntos.php",new String[]{"user_id",mostCurrent._main._struserid});
 //BA.debugLineNum = 1877;BA.debugLine="End Sub";
return "";
}
public static String  _butongris_click() throws Exception{
 //BA.debugLineNum = 281;BA.debugLine="Sub butongris_click";
 //BA.debugLineNum = 282;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 283;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 284;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return "";
}
public static String  _cargarpregunta(int _numpregunta) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 493;BA.debugLine="Sub CargarPregunta (numpregunta As Int)";
 //BA.debugLineNum = 495;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 496;BA.debugLine="pnlQuestions.BringToFront";
mostCurrent._pnlquestions.BringToFront();
 //BA.debugLineNum = 498;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 499;BA.debugLine="If csv.Bitmap = Null Then";
if (mostCurrent._csv.getBitmap()== null) { 
 //BA.debugLineNum = 500;BA.debugLine="csv.Initialize(pnlOpciones)";
mostCurrent._csv.Initialize((android.view.View)(mostCurrent._pnlopciones.getObject()));
 //BA.debugLineNum = 501;BA.debugLine="csvChk.Initialize(pnlChecks)";
mostCurrent._csvchk.Initialize((android.view.View)(mostCurrent._pnlchecks.getObject()));
 //BA.debugLineNum = 502;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 503;BA.debugLine="cd.Initialize(Colors.ARGB(0,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 504;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 505;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 }else {
 //BA.debugLineNum = 507;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 508;BA.debugLine="cd.Initialize(Colors.ARGB(0,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 509;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 510;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 };
 //BA.debugLineNum = 513;BA.debugLine="If numpregunta = 1 Then";
if (_numpregunta==1) { 
 //BA.debugLineNum = 514;BA.debugLine="TipoPreguntaRad(6, False)";
_tipopreguntarad((int) (6),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 515;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 516;BA.debugLine="lblPregunta.Text = \"¿Que hay alrededor del luga";
mostCurrent._lblpregunta.setText((Object)("¿Que hay alrededor del lugar?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 518;BA.debugLine="lblPregunta.Text = \"What are the surroundings l";
mostCurrent._lblpregunta.setText((Object)("What are the surroundings like?"));
 };
 //BA.debugLineNum = 520;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 521;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 522;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 523;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1d.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 524;BA.debugLine="rdOpcion5.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1e.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 525;BA.debugLine="rdOpcion6.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 526;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==2) { 
 //BA.debugLineNum = 528;BA.debugLine="TipoPreguntaRad(5, True)";
_tipopreguntarad((int) (5),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 529;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 530;BA.debugLine="lblPregunta.Text = \"¿Hay ganado cerca del sitio";
mostCurrent._lblpregunta.setText((Object)("¿Hay ganado cerca del sitio?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 532;BA.debugLine="lblPregunta.Text = \"Is there cattle nearby?\"";
mostCurrent._lblpregunta.setText((Object)("Is there cattle nearby?"));
 };
 //BA.debugLineNum = 534;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 535;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 536;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 537;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2d.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 538;BA.debugLine="rdOpcion5.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 540;BA.debugLine="imgej1 = \"ej1.html\"";
mostCurrent._imgej1 = "ej1.html";
 //BA.debugLineNum = 541;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==3) { 
 //BA.debugLineNum = 543;BA.debugLine="TipoPreguntaRad(4, False)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 544;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 545;BA.debugLine="lblPregunta.Text = \"¿Cómo son los árboles?\"";
mostCurrent._lblpregunta.setText((Object)("¿Cómo son los árboles?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 547;BA.debugLine="lblPregunta.Text = \"Are there trees nearby\"";
mostCurrent._lblpregunta.setText((Object)("Are there trees nearby"));
 };
 //BA.debugLineNum = 549;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 550;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 551;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 552;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 553;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==4) { 
 //BA.debugLineNum = 555;BA.debugLine="TipoPreguntaRad(4, False)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 556;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 557;BA.debugLine="lblPregunta.Text = \"¿Hay plantas acuáticas?\"";
mostCurrent._lblpregunta.setText((Object)("¿Hay plantas acuáticas?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 559;BA.debugLine="lblPregunta.Text = \"Are there aquatic plants?\"";
mostCurrent._lblpregunta.setText((Object)("Are there aquatic plants?"));
 };
 //BA.debugLineNum = 561;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"4a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 562;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"4b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 563;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"4c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 564;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 565;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==5) { 
 //BA.debugLineNum = 567;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 568;BA.debugLine="lblPregunta.Text = \"¿Es transparente el agua?\"";
mostCurrent._lblpregunta.setText((Object)("¿Es transparente el agua?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 570;BA.debugLine="lblPregunta.Text = \"Is the water transparent?\"";
mostCurrent._lblpregunta.setText((Object)("Is the water transparent?"));
 };
 //BA.debugLineNum = 572;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 573;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 574;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 575;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 576;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 578;BA.debugLine="imgej1 = \"ej5.html\"";
mostCurrent._imgej1 = "ej5.html";
 //BA.debugLineNum = 579;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 580;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText((Object)("Siguiente pregunta"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 582;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText((Object)("Next question"));
 };
 //BA.debugLineNum = 584;BA.debugLine="btnSiguiente.Tag = \"6\"";
mostCurrent._btnsiguiente.setTag((Object)("6"));
 }else if(_numpregunta==6) { 
 //BA.debugLineNum = 586;BA.debugLine="TipoPreguntaRad(3,False)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 587;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 588;BA.debugLine="lblPregunta.Text = \"¿Tiene mal olor?\"";
mostCurrent._lblpregunta.setText((Object)("¿Tiene mal olor?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 590;BA.debugLine="lblPregunta.Text = \"Does the water smell bad?\"";
mostCurrent._lblpregunta.setText((Object)("Does the water smell bad?"));
 };
 //BA.debugLineNum = 592;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"6a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 593;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"6b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 594;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 595;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 596;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText((Object)("Siguiente pregunta"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 598;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText((Object)("Next question"));
 };
 //BA.debugLineNum = 600;BA.debugLine="btnSiguiente.Tag = \"8\"";
mostCurrent._btnsiguiente.setTag((Object)("8"));
 }else if(_numpregunta==7) { 
 //BA.debugLineNum = 602;BA.debugLine="TipoPreguntaRad(4,True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 603;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 604;BA.debugLine="lblPregunta.Text = \"¿Hay basura en la costa?\"";
mostCurrent._lblpregunta.setText((Object)("¿Hay basura en la costa?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 606;BA.debugLine="lblPregunta.Text = \"Is there litter in the shor";
mostCurrent._lblpregunta.setText((Object)("Is there litter in the shore?"));
 };
 //BA.debugLineNum = 608;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 609;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 610;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 611;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 613;BA.debugLine="imgej1 = \"ej7.html\"";
mostCurrent._imgej1 = "ej7.html";
 //BA.debugLineNum = 614;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==8) { 
 //BA.debugLineNum = 619;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 620;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 621;BA.debugLine="lblPregunta.Text = \"¿Corre rápido o lento el ag";
mostCurrent._lblpregunta.setText((Object)("¿Corre rápido o lento el agua?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 623;BA.debugLine="lblPregunta.Text = \"Is water moving fast or slo";
mostCurrent._lblpregunta.setText((Object)("Is water moving fast or slow?"));
 };
 //BA.debugLineNum = 625;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio1a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 626;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio1b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 627;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 629;BA.debugLine="imgej1 = \"rioej1.html\"";
mostCurrent._imgej1 = "rioej1.html";
 //BA.debugLineNum = 630;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==9) { 
 //BA.debugLineNum = 632;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 633;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 634;BA.debugLine="lblPregunta.Text = \"¿Está desbordado el cauce?\"";
mostCurrent._lblpregunta.setText((Object)("¿Está desbordado el cauce?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 636;BA.debugLine="lblPregunta.Text = \"Is the river overflowing?\"";
mostCurrent._lblpregunta.setText((Object)("Is the river overflowing?"));
 };
 //BA.debugLineNum = 638;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio2a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 639;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio2b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 640;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 642;BA.debugLine="imgej1 = \"rioej2.html\"";
mostCurrent._imgej1 = "rioej2.html";
 //BA.debugLineNum = 643;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 644;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText((Object)("Siguiente pregunta"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 646;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText((Object)("Next question"));
 };
 //BA.debugLineNum = 648;BA.debugLine="btnSiguiente.Tag = \"10\"";
mostCurrent._btnsiguiente.setTag((Object)("10"));
 }else if(_numpregunta==10) { 
 //BA.debugLineNum = 650;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 651;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 652;BA.debugLine="lblPregunta.Text = \"¿Está canalizado o entubado";
mostCurrent._lblpregunta.setText((Object)("¿Está canalizado o entubado?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 654;BA.debugLine="lblPregunta.Text = \"Is the river dredged or cha";
mostCurrent._lblpregunta.setText((Object)("Is the river dredged or channelized?"));
 };
 //BA.debugLineNum = 656;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio3a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 657;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio3b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 658;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 660;BA.debugLine="imgej1 = \"rioej3.html\"";
mostCurrent._imgej1 = "rioej3.html";
 //BA.debugLineNum = 661;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==11) { 
 //BA.debugLineNum = 664;BA.debugLine="TipoPreguntaChk(6, False)";
_tipopreguntachk((int) (6),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 665;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 666;BA.debugLine="lblPregunta.Text = \"¿Cómo es el fondo?\"";
mostCurrent._lblpregunta.setText((Object)("¿Cómo es el fondo?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 668;BA.debugLine="lblPregunta.Text = \"What is the sediment like?\"";
mostCurrent._lblpregunta.setText((Object)("What is the sediment like?"));
 };
 //BA.debugLineNum = 670;BA.debugLine="chkOpcion1.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 671;BA.debugLine="chkOpcion2.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 672;BA.debugLine="chkOpcion3.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 673;BA.debugLine="chkOpcion4.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4d.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 674;BA.debugLine="chkOpcion5.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio4e.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 675;BA.debugLine="chkOpcion6.SetBackgroundImage(LoadBitmapSample(F";
mostCurrent._chkopcion6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 676;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==12) { 
 //BA.debugLineNum = 680;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 681;BA.debugLine="lblPregunta.Text = \"¿Cómo son las márgenes?\"";
mostCurrent._lblpregunta.setText((Object)("¿Cómo son las márgenes?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 683;BA.debugLine="lblPregunta.Text = \"What is the inclination of";
mostCurrent._lblpregunta.setText((Object)("What is the inclination of the margins?"));
 };
 //BA.debugLineNum = 685;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 686;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio5a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 687;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio5b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 688;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 690;BA.debugLine="imgej1 = \"rioej5.html\"";
mostCurrent._imgej1 = "rioej5.html";
 //BA.debugLineNum = 691;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 }else if(_numpregunta==13) { 
 //BA.debugLineNum = 693;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 694;BA.debugLine="lblPregunta.Text = \"¿Está sombreado el río?\"";
mostCurrent._lblpregunta.setText((Object)("¿Está sombreado el río?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 696;BA.debugLine="lblPregunta.Text = \"Are the trees shadowing the";
mostCurrent._lblpregunta.setText((Object)("Are the trees shadowing the river flow?"));
 };
 //BA.debugLineNum = 699;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 700;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio6a.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 701;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio6b.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 702;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"rio6c.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 703;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmapSample(Fi";
mostCurrent._rdopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 705;BA.debugLine="imgej1 = \"rioej6.html\"";
mostCurrent._imgej1 = "rioej6.html";
 //BA.debugLineNum = 706;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText((Object)("Ok"));
 };
 //BA.debugLineNum = 709;BA.debugLine="End Sub";
return "";
}
public static String  _colorearcirculos(anywheresoftware.b4a.objects.LabelWrapper _label,String _valor) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _labelborder = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _sldwhite = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _labelborderclicked = null;
int _valorind = 0;
 //BA.debugLineNum = 1570;BA.debugLine="Sub ColorearCirculos (label As Label, valor As Str";
 //BA.debugLineNum = 1571;BA.debugLine="Dim LabelBorder As ColorDrawable";
_labelborder = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 1572;BA.debugLine="Dim sldWhite As StateListDrawable";
_sldwhite = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 1573;BA.debugLine="Dim LabelBorderClicked As ColorDrawable";
_labelborderclicked = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 1575;BA.debugLine="LabelBorderClicked.Initialize2(Colors.ARGB(255,25";
_labelborderclicked.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1576;BA.debugLine="sldWhite.Initialize";
_sldwhite.Initialize();
 //BA.debugLineNum = 1578;BA.debugLine="If valor = \"NS\" Then";
if ((_valor).equals("NS")) { 
 //BA.debugLineNum = 1579;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,255,255,25";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 1581;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 1582;BA.debugLine="label.TextColor = Colors.White";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 }else if((_valor).equals("")) { 
 //BA.debugLineNum = 1584;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,255,255,25";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1585;BA.debugLine="sldWhite.AddState(sldWhite.State_Pressed, LabelB";
_sldwhite.AddState(_sldwhite.State_Pressed,(android.graphics.drawable.Drawable)(_labelborderclicked.getObject()));
 //BA.debugLineNum = 1586;BA.debugLine="sldWhite.AddState (sldWhite.State_Enabled,LabelB";
_sldwhite.AddState(_sldwhite.State_Enabled,(android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 1587;BA.debugLine="label.background = sldWhite";
_label.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 1588;BA.debugLine="label.TextColor = Colors.Gray";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Gray);
 }else {
 //BA.debugLineNum = 1590;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19)";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 1591;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 1593;BA.debugLine="Dim valorind As Int";
_valorind = 0;
 //BA.debugLineNum = 1594;BA.debugLine="valorind = valor";
_valorind = (int)(Double.parseDouble(_valor));
 //BA.debugLineNum = 1595;BA.debugLine="label.TextColor = Colors.White";
_label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 1596;BA.debugLine="If valorind < 2 Then";
if (_valorind<2) { 
 //BA.debugLineNum = 1598;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 1599;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<4 && _valorind>=2) { 
 //BA.debugLineNum = 1602;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,125,15,19";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 1603;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<6 && _valorind>=4) { 
 //BA.debugLineNum = 1606;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,179,191,0";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (179),(int) (191),(int) (0)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (179),(int) (191),(int) (0)));
 //BA.debugLineNum = 1607;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind<8 && _valorind>=6) { 
 //BA.debugLineNum = 1610;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,66,191,41";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (66),(int) (191),(int) (41)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (66),(int) (191),(int) (41)));
 //BA.debugLineNum = 1611;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 }else if(_valorind>8) { 
 //BA.debugLineNum = 1614;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,36,73,191";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (36),(int) (73),(int) (191)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (36),(int) (73),(int) (191)));
 //BA.debugLineNum = 1615;BA.debugLine="label.background = LabelBorder";
_label.setBackground((android.graphics.drawable.Drawable)(_labelborder.getObject()));
 };
 };
 //BA.debugLineNum = 1618;BA.debugLine="End Sub";
return "";
}
public static String  _enviarshares() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _envioshares = null;
 //BA.debugLineNum = 1865;BA.debugLine="Sub EnviarShares";
 //BA.debugLineNum = 1866;BA.debugLine="Dim EnvioShares As HttpJob";
_envioshares = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 1867;BA.debugLine="EnvioShares.Initialize(\"EnvioShares\", Me)";
_envioshares._initialize(processBA,"EnvioShares",frmhabitatrio.getObject());
 //BA.debugLineNum = 1868;BA.debugLine="EnvioShares.Download2(\"http://www.app-ear.com.ar/";
_envioshares._download2("http://www.app-ear.com.ar/connect/recshare.php",new String[]{"UserID",mostCurrent._main._struserid,"NumShares",BA.NumberToString(mostCurrent._main._numshares),"PuntosTotal",BA.NumberToString(mostCurrent._main._puntostotales+50)});
 //BA.debugLineNum = 1870;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 276;BA.debugLine="Sub fondogris_click";
 //BA.debugLineNum = 277;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 278;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 279;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 280;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private pnlQuestions As Panel";
mostCurrent._pnlquestions = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private btnPregunta1 As Button";
mostCurrent._btnpregunta1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private btnPregunta2 As Button";
mostCurrent._btnpregunta2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnPregunta3 As Button";
mostCurrent._btnpregunta3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnPregunta4 As Button";
mostCurrent._btnpregunta4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private btnPregunta5 As Button";
mostCurrent._btnpregunta5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnPregunta6 As Button";
mostCurrent._btnpregunta6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private btnPregunta7 As Button";
mostCurrent._btnpregunta7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private btnPregunta8 As Button";
mostCurrent._btnpregunta8 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnPregunta9 As Button";
mostCurrent._btnpregunta9 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private btnPregunta10 As Button";
mostCurrent._btnpregunta10 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lblPregunta1 As Label";
mostCurrent._lblpregunta1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblPregunta2 As Label";
mostCurrent._lblpregunta2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private lblPregunta3 As Label";
mostCurrent._lblpregunta3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private lblPregunta4 As Label";
mostCurrent._lblpregunta4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lblPregunta5 As Label";
mostCurrent._lblpregunta5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblPregunta6 As Label";
mostCurrent._lblpregunta6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lblPregunta7 As Label";
mostCurrent._lblpregunta7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblPregunta8 As Label";
mostCurrent._lblpregunta8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblPregunta9 As Label";
mostCurrent._lblpregunta9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblPregunta10 As Label";
mostCurrent._lblpregunta10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private minimagen As String";
mostCurrent._minimagen = "";
 //BA.debugLineNum = 40;BA.debugLine="Private miniPregunta1 As ImageView";
mostCurrent._minipregunta1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private miniPregunta2 As ImageView";
mostCurrent._minipregunta2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private miniPregunta3 As ImageView";
mostCurrent._minipregunta3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private miniPregunta4 As ImageView";
mostCurrent._minipregunta4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private miniPregunta5 As ImageView";
mostCurrent._minipregunta5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private miniPregunta6 As ImageView";
mostCurrent._minipregunta6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private miniPregunta7 As ImageView";
mostCurrent._minipregunta7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private miniPregunta8 As ImageView";
mostCurrent._minipregunta8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private miniPregunta9 As ImageView";
mostCurrent._minipregunta9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private miniPregunta10 As ImageView";
mostCurrent._minipregunta10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private lblPregunta As Label";
mostCurrent._lblpregunta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private btnSiguiente As Button";
mostCurrent._btnsiguiente = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private pnlOpciones As Panel";
mostCurrent._pnlopciones = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private pnlChecks As Panel";
mostCurrent._pnlchecks = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private rdOpcion1 As RadioButton";
mostCurrent._rdopcion1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private rdOpcion2 As RadioButton";
mostCurrent._rdopcion2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private rdOpcion3 As RadioButton";
mostCurrent._rdopcion3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private rdOpcion4 As RadioButton";
mostCurrent._rdopcion4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private rdOpcion5 As RadioButton";
mostCurrent._rdopcion5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private rdOpcion6 As RadioButton";
mostCurrent._rdopcion6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private chkOpcion3 As CheckBox";
mostCurrent._chkopcion3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private chkOpcion2 As CheckBox";
mostCurrent._chkopcion2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private chkOpcion1 As CheckBox";
mostCurrent._chkopcion1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private chkOpcion4 As CheckBox";
mostCurrent._chkopcion4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private chkOpcion5 As CheckBox";
mostCurrent._chkopcion5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private chkOpcion6 As CheckBox";
mostCurrent._chkopcion6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim imgej1 As String = \"\"";
mostCurrent._imgej1 = "";
 //BA.debugLineNum = 74;BA.debugLine="Dim explicacionviewer As WebView";
mostCurrent._explicacionviewer = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Private btnRdOpcion1 As Button";
mostCurrent._btnrdopcion1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Dim csv As Canvas";
mostCurrent._csv = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim rec As Rect";
mostCurrent._rec = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim csvChk As Canvas";
mostCurrent._csvchk = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Dim recChk As Rect";
mostCurrent._recchk = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Private pgbPreguntas As Int";
_pgbpreguntas = 0;
 //BA.debugLineNum = 85;BA.debugLine="Dim cantidadpreguntas As Int";
_cantidadpreguntas = 0;
 //BA.debugLineNum = 86;BA.debugLine="Dim currentpregunta As Int";
_currentpregunta = 0;
 //BA.debugLineNum = 88;BA.debugLine="Private pnlPreguntas As Panel";
mostCurrent._pnlpreguntas = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 89;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private pnlResultados As Panel";
mostCurrent._pnlresultados = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private btnTerminar As Button";
mostCurrent._btnterminar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private scrResultados As ScrollView";
mostCurrent._scrresultados = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private miniPreguntaEntubado As ImageView";
mostCurrent._minipreguntaentubado = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private lblTituloResultados As Label";
mostCurrent._lbltituloresultados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private langLocal As String";
mostCurrent._langlocal = "";
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String[] _actarray = null;
 //BA.debugLineNum = 1879;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 1880;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1881;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1882;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1883;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring();
 //BA.debugLineNum = 1884;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1885;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1886;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1887;BA.debugLine="If Job.JobName = \"GetPuntos\" Then";
if ((_job._jobname).equals("GetPuntos")) { 
 //BA.debugLineNum = 1888;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 1889;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1890;BA.debugLine="Msgbox(\"Error en la conexión, tus punto se ac";
anywheresoftware.b4a.keywords.Common.Msgbox("Error en la conexión, tus punto se actualizán luego","Oops...",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1892;BA.debugLine="Msgbox(\"Error in your connection, your points";
anywheresoftware.b4a.keywords.Common.Msgbox("Error in your connection, your points will update later","Oops...",mostCurrent.activityBA);
 };
 }else if((_act).equals("GetPuntos OK")) { 
 //BA.debugLineNum = 1895;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1896;BA.debugLine="Dim actarray() As String = Regex.Split(\"-\", a";
_actarray = anywheresoftware.b4a.keywords.Common.Regex.Split("-",_act);
 //BA.debugLineNum = 1897;BA.debugLine="Main.puntostotales = actarray(0)";
mostCurrent._main._puntostotales = (int)(Double.parseDouble(_actarray[(int) (0)]));
 //BA.debugLineNum = 1898;BA.debugLine="Main.puntosnumfotos = actarray(1)";
mostCurrent._main._puntosnumfotos = (int)(Double.parseDouble(_actarray[(int) (1)]));
 //BA.debugLineNum = 1899;BA.debugLine="Main.puntosnumevals = actarray(2)";
mostCurrent._main._puntosnumevals = (int)(Double.parseDouble(_actarray[(int) (2)]));
 //BA.debugLineNum = 1900;BA.debugLine="Main.numevalsok = actarray(3)";
mostCurrent._main._numevalsok = (int)(Double.parseDouble(_actarray[(int) (3)]));
 //BA.debugLineNum = 1901;BA.debugLine="Main.numriollanura = actarray(4)";
mostCurrent._main._numriollanura = (int)(Double.parseDouble(_actarray[(int) (4)]));
 //BA.debugLineNum = 1902;BA.debugLine="Main.numriomontana = actarray(5)";
mostCurrent._main._numriomontana = (int)(Double.parseDouble(_actarray[(int) (5)]));
 //BA.debugLineNum = 1903;BA.debugLine="Main.numlaguna = actarray(6)";
mostCurrent._main._numlaguna = (int)(Double.parseDouble(_actarray[(int) (6)]));
 //BA.debugLineNum = 1904;BA.debugLine="Main.numestuario = actarray(7)";
mostCurrent._main._numestuario = (int)(Double.parseDouble(_actarray[(int) (7)]));
 //BA.debugLineNum = 1905;BA.debugLine="Main.numshares = actarray(8)";
mostCurrent._main._numshares = (int)(Double.parseDouble(_actarray[(int) (8)]));
 };
 };
 //BA.debugLineNum = 1909;BA.debugLine="If Job.JobName = \"EnvioShares\" Then";
if ((_job._jobname).equals("EnvioShares")) { 
 //BA.debugLineNum = 1910;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 1911;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1912;BA.debugLine="ToastMessageShow(\"Ha fallado el envio!\", Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Ha fallado el envio!",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1914;BA.debugLine="ToastMessageShow(\"Delivery failed!\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Delivery failed!",anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("SharesOK")) { 
 //BA.debugLineNum = 1917;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1918;BA.debugLine="ToastMessageShow(\"Has compartido tus resulta";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Has compartido tus resultados, y ganado otros 50 puntos!",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1920;BA.debugLine="ToastMessageShow(\"You have shared your resul";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You have shared your results and earned another 50 points!",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 1922;BA.debugLine="BuscarPuntos";
_buscarpuntos();
 };
 };
 }else {
 };
 //BA.debugLineNum = 1930;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 1931;BA.debugLine="End Sub";
return "";
}
public static String  _pnlresultados_click() throws Exception{
 //BA.debugLineNum = 1790;BA.debugLine="Sub pnlResultados_Click";
 //BA.debugLineNum = 1791;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1792;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion1_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 202;BA.debugLine="Sub rdOpcion1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 203;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 205;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 206;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 207;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rdO";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion1.getWidth()+mostCurrent._rdopcion1.getLeft()),(int) (mostCurrent._rdopcion1.getHeight()+mostCurrent._rdopcion1.getTop()));
 //BA.debugLineNum = 208;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion2_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 211;BA.debugLine="Sub rdOpcion2_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 212;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 213;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 214;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 215;BA.debugLine="rec.Initialize(rdOpcion2.Left, rdOpcion2.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion2.getLeft(),mostCurrent._rdopcion2.getTop(),(int) (mostCurrent._rdopcion2.getWidth()+mostCurrent._rdopcion2.getLeft()),(int) (mostCurrent._rdopcion2.getHeight()+mostCurrent._rdopcion2.getTop()));
 //BA.debugLineNum = 216;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 217;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion3_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 218;BA.debugLine="Sub rdOpcion3_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 219;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 220;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 221;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 222;BA.debugLine="rec.Initialize(rdOpcion3.Left, rdOpcion3.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion3.getLeft(),mostCurrent._rdopcion3.getTop(),(int) (mostCurrent._rdopcion3.getWidth()+mostCurrent._rdopcion3.getLeft()),(int) (mostCurrent._rdopcion3.getHeight()+mostCurrent._rdopcion3.getTop()));
 //BA.debugLineNum = 223;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion4_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 225;BA.debugLine="Sub rdOpcion4_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 226;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 227;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 228;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 229;BA.debugLine="rec.Initialize(rdOpcion4.Left, rdOpcion4.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion4.getLeft(),mostCurrent._rdopcion4.getTop(),(int) (mostCurrent._rdopcion4.getWidth()+mostCurrent._rdopcion4.getLeft()),(int) (mostCurrent._rdopcion4.getHeight()+mostCurrent._rdopcion4.getTop()));
 //BA.debugLineNum = 230;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion5_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 232;BA.debugLine="Sub rdOpcion5_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 233;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 234;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 235;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 236;BA.debugLine="rec.Initialize(rdOpcion5.Left, rdOpcion5.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion5.getLeft(),mostCurrent._rdopcion5.getTop(),(int) (mostCurrent._rdopcion5.getWidth()+mostCurrent._rdopcion5.getLeft()),(int) (mostCurrent._rdopcion5.getHeight()+mostCurrent._rdopcion5.getTop()));
 //BA.debugLineNum = 237;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion6_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 239;BA.debugLine="Sub rdOpcion6_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 240;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 241;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 242;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 243;BA.debugLine="rec.Initialize(rdOpcion6.Left, rdOpcion6.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion6.getLeft(),mostCurrent._rdopcion6.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 244;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return "";
}
public static String  _terminareval() throws Exception{
int _totpreg = 0;
 //BA.debugLineNum = 1629;BA.debugLine="Sub TerminarEval";
 //BA.debugLineNum = 1632;BA.debugLine="pnlResultados.Visible = True";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1633;BA.debugLine="pnlResultados.BringToFront";
mostCurrent._pnlresultados.BringToFront();
 //BA.debugLineNum = 1635;BA.debugLine="Dim totpreg As Int";
_totpreg = 0;
 //BA.debugLineNum = 1636;BA.debugLine="If Main.tiporio = \"Llanura\" Then";
if ((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 1637;BA.debugLine="totpreg = 120";
_totpreg = (int) (120);
 }else if((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 1639;BA.debugLine="totpreg = 130";
_totpreg = (int) (130);
 };
 //BA.debugLineNum = 1642;BA.debugLine="If Main.valorind1 <> \"NS\" Then";
if ((mostCurrent._main._valorind1).equals("NS") == false) { 
 //BA.debugLineNum = 1643;BA.debugLine="Main.valorind1  = Round2(((Main.valorind1 * 10";
mostCurrent._main._valorind1 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind1))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1644;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind1));
 }else {
 //BA.debugLineNum = 1646;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1648;BA.debugLine="If Main.valorind2 <> \"NS\" Then";
if ((mostCurrent._main._valorind2).equals("NS") == false) { 
 //BA.debugLineNum = 1649;BA.debugLine="Main.valorind2  = Round2(((Main.valorind2 * 10";
mostCurrent._main._valorind2 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind2))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1650;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind2));
 }else {
 //BA.debugLineNum = 1652;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1654;BA.debugLine="If Main.valorind3 <> \"NS\" Then";
if ((mostCurrent._main._valorind3).equals("NS") == false) { 
 //BA.debugLineNum = 1655;BA.debugLine="Main.valorind3  = Round2(((Main.valorind3 * 10";
mostCurrent._main._valorind3 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind3))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1656;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind3));
 }else {
 //BA.debugLineNum = 1658;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1660;BA.debugLine="If Main.valorind4 <> \"NS\" Then";
if ((mostCurrent._main._valorind4).equals("NS") == false) { 
 //BA.debugLineNum = 1661;BA.debugLine="Main.valorind4  = Round2(((Main.valorind4 * 10";
mostCurrent._main._valorind4 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind4))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1662;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind4));
 }else {
 //BA.debugLineNum = 1664;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1666;BA.debugLine="If Main.valorind5 <> \"NS\" Then";
if ((mostCurrent._main._valorind5).equals("NS") == false) { 
 //BA.debugLineNum = 1667;BA.debugLine="Main.valorind5  = Round2(((Main.valorind5 * 10";
mostCurrent._main._valorind5 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind5))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1668;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind5));
 }else {
 //BA.debugLineNum = 1670;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1672;BA.debugLine="If Main.valorind6 <> \"NS\" Then";
if ((mostCurrent._main._valorind6).equals("NS") == false) { 
 //BA.debugLineNum = 1673;BA.debugLine="Main.valorind6  = Round2(((Main.valorind6 * 10";
mostCurrent._main._valorind6 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind6))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1674;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind6));
 }else {
 //BA.debugLineNum = 1676;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1678;BA.debugLine="If Main.valorind7 <> \"NS\" Then";
if ((mostCurrent._main._valorind7).equals("NS") == false) { 
 //BA.debugLineNum = 1679;BA.debugLine="Main.valorind7  = Round2(((Main.valorind7 * 10";
mostCurrent._main._valorind7 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind7))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1680;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind7));
 }else {
 //BA.debugLineNum = 1682;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1684;BA.debugLine="If Main.valorind8 <> \"NS\" Then";
if ((mostCurrent._main._valorind8).equals("NS") == false) { 
 //BA.debugLineNum = 1685;BA.debugLine="Main.valorind8  = Round2(((Main.valorind8 * 10";
mostCurrent._main._valorind8 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind8))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1686;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind8));
 }else {
 //BA.debugLineNum = 1688;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1690;BA.debugLine="If Main.valorind9 <> \"NS\" Then";
if ((mostCurrent._main._valorind9).equals("NS") == false) { 
 //BA.debugLineNum = 1691;BA.debugLine="Main.valorind9  = Round2(((Main.valorind9 * 10";
mostCurrent._main._valorind9 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind9))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1692;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind9));
 }else {
 //BA.debugLineNum = 1694;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1696;BA.debugLine="If Main.valorind10 <> \"NS\" Then";
if ((mostCurrent._main._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1697;BA.debugLine="Main.valorind10  = Round2(((Main.valorind10 *";
mostCurrent._main._valorind10 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind10))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1698;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind10));
 }else {
 //BA.debugLineNum = 1700;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1702;BA.debugLine="If Main.valorind11 <> \"NS\" Then";
if ((mostCurrent._main._valorind11).equals("NS") == false) { 
 //BA.debugLineNum = 1703;BA.debugLine="Main.valorind11  = Round2(((Main.valorind11 *";
mostCurrent._main._valorind11 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind11))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1704;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind11));
 }else {
 //BA.debugLineNum = 1706;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1708;BA.debugLine="If Main.valorind12 <> \"NS\" Then";
if ((mostCurrent._main._valorind12).equals("NS") == false) { 
 //BA.debugLineNum = 1709;BA.debugLine="Main.valorind12  = Round2(((Main.valorind12 *";
mostCurrent._main._valorind12 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind12))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1710;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind12));
 }else {
 //BA.debugLineNum = 1712;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1715;BA.debugLine="If Main.tiporio = \"Montana\" And Main.valorind13";
if ((mostCurrent._main._tiporio).equals("Montana") && (mostCurrent._main._valorind13).equals("NS") == false) { 
 //BA.debugLineNum = 1716;BA.debugLine="Main.valorind13  = Round2(((Main.valorind13 *";
mostCurrent._main._valorind13 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._main._valorind13))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1717;BA.debugLine="Main.valorcalidad = Main.valorcalidad + Main.v";
mostCurrent._main._valorcalidad = mostCurrent._main._valorcalidad+(double)(Double.parseDouble(mostCurrent._main._valorind13));
 }else {
 //BA.debugLineNum = 1719;BA.debugLine="Main.valorNS = Main.valorNS + 1";
mostCurrent._main._valorns = (int) (mostCurrent._main._valorns+1);
 };
 //BA.debugLineNum = 1724;BA.debugLine="If (Main.valorNS * 100) / (totpreg / 10) > 50  T";
if ((mostCurrent._main._valorns*100)/(double)(_totpreg/(double)10)>50) { 
 //BA.debugLineNum = 1725;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1726;BA.debugLine="Msgbox(\"Ha salteado muchas opciones en la encu";
anywheresoftware.b4a.keywords.Common.Msgbox("Ha salteado muchas opciones en la encuesta, el estado del hábitat no puede ser calculado con precisión","Oops!",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1728;BA.debugLine="Msgbox(\"You have skipped too many questions, t";
anywheresoftware.b4a.keywords.Common.Msgbox("You have skipped too many questions, the habitat status cannot be calculated accurately","Oops!",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 1730;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1731;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1734;BA.debugLine="Main.valorNS = Round2((Main.valorNS * 100) / (to";
mostCurrent._main._valorns = (int) (anywheresoftware.b4a.keywords.Common.Round2((mostCurrent._main._valorns*100)/(double)(_totpreg/(double)10),(int) (1)));
 //BA.debugLineNum = 1735;BA.debugLine="Main.valorcalidad = Round2(Main.valorcalidad,1)";
mostCurrent._main._valorcalidad = anywheresoftware.b4a.keywords.Common.Round2(mostCurrent._main._valorcalidad,(int) (1));
 //BA.debugLineNum = 1739;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1740;BA.debugLine="If Main.valorcalidad < 20 Then";
if (mostCurrent._main._valorcalidad<20) { 
 //BA.debugLineNum = 1741;BA.debugLine="lblEstado.Text = \"Muy malo\"";
mostCurrent._lblestado.setText((Object)("Muy malo"));
 }else if(mostCurrent._main._valorcalidad>=20 && mostCurrent._main._valorcalidad<40) { 
 //BA.debugLineNum = 1743;BA.debugLine="lblEstado.Text = \"Malo\"";
mostCurrent._lblestado.setText((Object)("Malo"));
 }else if(mostCurrent._main._valorcalidad>=40 && mostCurrent._main._valorcalidad<60) { 
 //BA.debugLineNum = 1745;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText((Object)("Regular"));
 }else if(mostCurrent._main._valorcalidad>=60 && mostCurrent._main._valorcalidad<80) { 
 //BA.debugLineNum = 1747;BA.debugLine="lblEstado.Text = \"Bueno\"";
mostCurrent._lblestado.setText((Object)("Bueno"));
 }else if(mostCurrent._main._valorcalidad>=80) { 
 //BA.debugLineNum = 1749;BA.debugLine="lblEstado.Text = \"Muy bueno!\"";
mostCurrent._lblestado.setText((Object)("Muy bueno!"));
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1752;BA.debugLine="If Main.valorcalidad < 20 Then";
if (mostCurrent._main._valorcalidad<20) { 
 //BA.debugLineNum = 1753;BA.debugLine="lblEstado.Text = \"Very bad\"";
mostCurrent._lblestado.setText((Object)("Very bad"));
 }else if(mostCurrent._main._valorcalidad>=20 && mostCurrent._main._valorcalidad<40) { 
 //BA.debugLineNum = 1755;BA.debugLine="lblEstado.Text = \"Bad\"";
mostCurrent._lblestado.setText((Object)("Bad"));
 }else if(mostCurrent._main._valorcalidad>=40 && mostCurrent._main._valorcalidad<60) { 
 //BA.debugLineNum = 1757;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText((Object)("Regular"));
 }else if(mostCurrent._main._valorcalidad>=60 && mostCurrent._main._valorcalidad<80) { 
 //BA.debugLineNum = 1759;BA.debugLine="lblEstado.Text = \"Good\"";
mostCurrent._lblestado.setText((Object)("Good"));
 }else if(mostCurrent._main._valorcalidad>=80) { 
 //BA.debugLineNum = 1761;BA.debugLine="lblEstado.Text = \"Very good!\"";
mostCurrent._lblestado.setText((Object)("Very good!"));
 };
 };
 //BA.debugLineNum = 1765;BA.debugLine="End Sub";
return "";
}
public static String  _tipopreguntachk(int _cantidadopciones,boolean _imagenvisible) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _chk = null;
 //BA.debugLineNum = 347;BA.debugLine="Sub TipoPreguntaChk (cantidadopciones As Int, imag";
 //BA.debugLineNum = 348;BA.debugLine="pnlChecks.Visible = True";
mostCurrent._pnlchecks.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 349;BA.debugLine="pnlOpciones.Visible = False";
mostCurrent._pnlopciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 350;BA.debugLine="chkOpcion1.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 351;BA.debugLine="chkOpcion2.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 352;BA.debugLine="chkOpcion3.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 353;BA.debugLine="chkOpcion4.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 354;BA.debugLine="chkOpcion5.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 355;BA.debugLine="chkOpcion6.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 356;BA.debugLine="chkOpcion1.Text = \"\"";
mostCurrent._chkopcion1.setText((Object)(""));
 //BA.debugLineNum = 357;BA.debugLine="chkOpcion2.Text = \"\"";
mostCurrent._chkopcion2.setText((Object)(""));
 //BA.debugLineNum = 358;BA.debugLine="chkOpcion3.Text = \"\"";
mostCurrent._chkopcion3.setText((Object)(""));
 //BA.debugLineNum = 359;BA.debugLine="chkOpcion4.Text = \"\"";
mostCurrent._chkopcion4.setText((Object)(""));
 //BA.debugLineNum = 360;BA.debugLine="chkOpcion5.Text = \"\"";
mostCurrent._chkopcion5.setText((Object)(""));
 //BA.debugLineNum = 361;BA.debugLine="chkOpcion6.Text = \"\"";
mostCurrent._chkopcion6.setText((Object)(""));
 //BA.debugLineNum = 362;BA.debugLine="Dim chk As ColorDrawable";
_chk = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 363;BA.debugLine="chk.initialize2(Colors.ARGB(0,0,0,0),5dip,0dip,C";
_chk.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 364;BA.debugLine="chkOpcion1.Background = chk";
mostCurrent._chkopcion1.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 365;BA.debugLine="chkOpcion2.Background = chk";
mostCurrent._chkopcion2.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 366;BA.debugLine="chkOpcion3.Background = chk";
mostCurrent._chkopcion3.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 367;BA.debugLine="chkOpcion4.Background = chk";
mostCurrent._chkopcion4.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 368;BA.debugLine="chkOpcion5.Background = chk";
mostCurrent._chkopcion5.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 369;BA.debugLine="chkOpcion6.Background = chk";
mostCurrent._chkopcion6.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 370;BA.debugLine="chkOpcion1.width = rdOpcion1.Width";
mostCurrent._chkopcion1.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 371;BA.debugLine="chkOpcion2.width = rdOpcion1.Width";
mostCurrent._chkopcion2.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 372;BA.debugLine="chkOpcion3.width = rdOpcion1.Width";
mostCurrent._chkopcion3.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 373;BA.debugLine="chkOpcion4.width = rdOpcion1.Width";
mostCurrent._chkopcion4.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 374;BA.debugLine="chkOpcion5.width = rdOpcion1.Width";
mostCurrent._chkopcion5.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 375;BA.debugLine="chkOpcion6.width = rdOpcion1.Width";
mostCurrent._chkopcion6.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 376;BA.debugLine="chkOpcion1.Height = rdOpcion1.Height";
mostCurrent._chkopcion1.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 377;BA.debugLine="chkOpcion2.Height = rdOpcion1.Height";
mostCurrent._chkopcion2.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 378;BA.debugLine="chkOpcion3.Height = rdOpcion1.Height";
mostCurrent._chkopcion3.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 379;BA.debugLine="chkOpcion4.Height = rdOpcion1.Height";
mostCurrent._chkopcion4.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 380;BA.debugLine="chkOpcion5.Height = rdOpcion1.Height";
mostCurrent._chkopcion5.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 381;BA.debugLine="chkOpcion6.Height = rdOpcion1.Height";
mostCurrent._chkopcion6.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 383;BA.debugLine="chkOpcion1.Checked = False";
mostCurrent._chkopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 384;BA.debugLine="chkOpcion2.Checked = False";
mostCurrent._chkopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 385;BA.debugLine="chkOpcion3.Checked = False";
mostCurrent._chkopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 386;BA.debugLine="chkOpcion4.Checked = False";
mostCurrent._chkopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 387;BA.debugLine="chkOpcion5.Checked = False";
mostCurrent._chkopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 388;BA.debugLine="chkOpcion6.Checked = False";
mostCurrent._chkopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 389;BA.debugLine="chkOpcion1.Top = rdOpcion1.top";
mostCurrent._chkopcion1.setTop(mostCurrent._rdopcion1.getTop());
 //BA.debugLineNum = 390;BA.debugLine="chkOpcion2.Top = rdOpcion2.top";
mostCurrent._chkopcion2.setTop(mostCurrent._rdopcion2.getTop());
 //BA.debugLineNum = 391;BA.debugLine="chkOpcion3.Top = rdOpcion3.top";
mostCurrent._chkopcion3.setTop(mostCurrent._rdopcion3.getTop());
 //BA.debugLineNum = 392;BA.debugLine="chkOpcion4.Top = rdOpcion4.top";
mostCurrent._chkopcion4.setTop(mostCurrent._rdopcion4.getTop());
 //BA.debugLineNum = 393;BA.debugLine="chkOpcion5.Top = rdOpcion5.top";
mostCurrent._chkopcion5.setTop(mostCurrent._rdopcion5.getTop());
 //BA.debugLineNum = 394;BA.debugLine="chkOpcion6.Top = rdOpcion6.top";
mostCurrent._chkopcion6.setTop(mostCurrent._rdopcion6.getTop());
 //BA.debugLineNum = 395;BA.debugLine="chkOpcion1.Left = rdOpcion1.left";
mostCurrent._chkopcion1.setLeft(mostCurrent._rdopcion1.getLeft());
 //BA.debugLineNum = 396;BA.debugLine="chkOpcion2.Left = rdOpcion2.left";
mostCurrent._chkopcion2.setLeft(mostCurrent._rdopcion2.getLeft());
 //BA.debugLineNum = 397;BA.debugLine="chkOpcion3.Left = rdOpcion3.left";
mostCurrent._chkopcion3.setLeft(mostCurrent._rdopcion3.getLeft());
 //BA.debugLineNum = 398;BA.debugLine="chkOpcion4.Left = rdOpcion4.left";
mostCurrent._chkopcion4.setLeft(mostCurrent._rdopcion4.getLeft());
 //BA.debugLineNum = 399;BA.debugLine="chkOpcion5.Left = rdOpcion5.left";
mostCurrent._chkopcion5.setLeft(mostCurrent._rdopcion5.getLeft());
 //BA.debugLineNum = 400;BA.debugLine="chkOpcion6.Left = rdOpcion6.left";
mostCurrent._chkopcion6.setLeft(mostCurrent._rdopcion6.getLeft());
 //BA.debugLineNum = 401;BA.debugLine="If cantidadopciones = 2 Then";
if (_cantidadopciones==2) { 
 //BA.debugLineNum = 402;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 403;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 404;BA.debugLine="chkOpcion3.Visible = False";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 405;BA.debugLine="chkOpcion4.Visible = False";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 406;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 407;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==3) { 
 //BA.debugLineNum = 409;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 410;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 411;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 412;BA.debugLine="chkOpcion4.Visible = False";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 413;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 414;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==4) { 
 //BA.debugLineNum = 416;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 417;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 418;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 419;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 420;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 421;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==5) { 
 //BA.debugLineNum = 423;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 424;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 425;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 426;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 427;BA.debugLine="chkOpcion5.Visible = True";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 428;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==6) { 
 //BA.debugLineNum = 430;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 431;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 432;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 433;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 434;BA.debugLine="chkOpcion5.Visible = True";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 435;BA.debugLine="chkOpcion6.Visible = True";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 437;BA.debugLine="End Sub";
return "";
}
public static String  _tipopreguntarad(int _cantidadopciones,boolean _imagenvisible) throws Exception{
 //BA.debugLineNum = 288;BA.debugLine="Sub TipoPreguntaRad (cantidadopciones As Int, imag";
 //BA.debugLineNum = 290;BA.debugLine="pnlChecks.Visible = False";
mostCurrent._pnlchecks.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 291;BA.debugLine="pnlOpciones.Visible = True";
mostCurrent._pnlopciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 294;BA.debugLine="rdOpcion1.Checked = False";
mostCurrent._rdopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="rdOpcion2.Checked = False";
mostCurrent._rdopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 296;BA.debugLine="rdOpcion3.Checked = False";
mostCurrent._rdopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 297;BA.debugLine="rdOpcion4.Checked = False";
mostCurrent._rdopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 298;BA.debugLine="rdOpcion5.Checked = False";
mostCurrent._rdopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 299;BA.debugLine="rdOpcion6.Checked = False";
mostCurrent._rdopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 300;BA.debugLine="rdOpcion1.Visible = False";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 301;BA.debugLine="rdOpcion2.Visible = False";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 302;BA.debugLine="rdOpcion3.Visible = False";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 303;BA.debugLine="rdOpcion4.Visible = False";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 304;BA.debugLine="rdOpcion5.Visible = False";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 305;BA.debugLine="rdOpcion6.Visible = False";
mostCurrent._rdopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 308;BA.debugLine="If imagenvisible = True Then";
if (_imagenvisible==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 309;BA.debugLine="btnRdOpcion1.Visible = True";
mostCurrent._btnrdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 311;BA.debugLine="btnRdOpcion1.Visible = False";
mostCurrent._btnrdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 316;BA.debugLine="If cantidadopciones = 2 Then";
if (_cantidadopciones==2) { 
 //BA.debugLineNum = 317;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 318;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==3) { 
 //BA.debugLineNum = 321;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 322;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 323;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==4) { 
 //BA.debugLineNum = 325;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 326;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 327;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 328;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==5) { 
 //BA.debugLineNum = 330;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 331;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 332;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 333;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 334;BA.debugLine="rdOpcion5.Visible = True";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==6) { 
 //BA.debugLineNum = 336;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 337;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 338;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 339;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 340;BA.debugLine="rdOpcion5.Visible = True";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 341;BA.debugLine="rdOpcion6.Visible = True";
mostCurrent._rdopcion6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 179;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 180;BA.debugLine="lblTituloResultados.Text = \"You have finished th";
mostCurrent._lbltituloresultados.setText((Object)("You have finished the questionnaire!"));
 //BA.debugLineNum = 181;BA.debugLine="btnCerrar.Text = \"Close\"";
mostCurrent._btncerrar.setText((Object)("Close"));
 //BA.debugLineNum = 182;BA.debugLine="Label2.Text = \"This site has a habitat quality o";
mostCurrent._label2.setText((Object)("This site has a habitat quality of: "));
 //BA.debugLineNum = 183;BA.debugLine="btnShare.Text = \"Share\"";
mostCurrent._btnshare.setText((Object)("Share"));
 //BA.debugLineNum = 184;BA.debugLine="btnTerminar.Text = \"Finish!\"";
mostCurrent._btnterminar.setText((Object)("Finish!"));
 //BA.debugLineNum = 185;BA.debugLine="lblPregunta1.Text = \"Land use\"";
mostCurrent._lblpregunta1.setText((Object)("Land use"));
 //BA.debugLineNum = 186;BA.debugLine="lblPregunta2.Text = \"Cattle\"";
mostCurrent._lblpregunta2.setText((Object)("Cattle"));
 //BA.debugLineNum = 187;BA.debugLine="lblPregunta3.Text = \"Vegetation\"";
mostCurrent._lblpregunta3.setText((Object)("Vegetation"));
 //BA.debugLineNum = 188;BA.debugLine="lblPregunta4.Text = \"Aquatic plants\"";
mostCurrent._lblpregunta4.setText((Object)("Aquatic plants"));
 //BA.debugLineNum = 189;BA.debugLine="lblPregunta5.Text = \"Water\"";
mostCurrent._lblpregunta5.setText((Object)("Water"));
 //BA.debugLineNum = 190;BA.debugLine="lblPregunta6.Text = \"Litter\"";
mostCurrent._lblpregunta6.setText((Object)("Litter"));
 //BA.debugLineNum = 191;BA.debugLine="lblPregunta7.Text = \"Channelization\"";
mostCurrent._lblpregunta7.setText((Object)("Channelization"));
 //BA.debugLineNum = 192;BA.debugLine="lblPregunta8.Text = \"Sediment\"";
mostCurrent._lblpregunta8.setText((Object)("Sediment"));
 //BA.debugLineNum = 193;BA.debugLine="lblPregunta9.Text = \"Margins\"";
mostCurrent._lblpregunta9.setText((Object)("Margins"));
 //BA.debugLineNum = 194;BA.debugLine="lblPregunta10.Text = \"Shading\"";
mostCurrent._lblpregunta10.setText((Object)("Shading"));
 //BA.debugLineNum = 195;BA.debugLine="langLocal= \"en-\"";
mostCurrent._langlocal = "en-";
 }else {
 //BA.debugLineNum = 197;BA.debugLine="langLocal = \"\"";
mostCurrent._langlocal = "";
 };
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _validarrespuesta() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _pregresultadoview = null;
int _preg = 0;
anywheresoftware.b4a.objects.ImageViewWrapper _pregresultadoimg = null;
 //BA.debugLineNum = 717;BA.debugLine="Sub ValidarRespuesta";
 //BA.debugLineNum = 719;BA.debugLine="Dim pregResultadoView As Label";
_pregresultadoview = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 720;BA.debugLine="pregResultadoView.Initialize(\"\")";
_pregresultadoview.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 721;BA.debugLine="pregResultadoView.Color = Colors.White";
_pregresultadoview.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 723;BA.debugLine="If currentpregunta = 1 Then";
if (_currentpregunta==1) { 
 //BA.debugLineNum = 724;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 725;BA.debugLine="Main.valorind1 = \"0\"";
mostCurrent._main._valorind1 = "0";
 //BA.debugLineNum = 726;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usoindustrial.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 727;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 728;BA.debugLine="pregResultadoView.Text = \"Las industrias puede";
_pregresultadoview.setText((Object)("Las industrias pueden volcar contaminantes directamente al agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 730;BA.debugLine="pregResultadoView.Text = \"Industries could pou";
_pregresultadoview.setText((Object)("Industries could pour pollutants into the water"));
 };
 //BA.debugLineNum = 732;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 734;BA.debugLine="Main.valorind1 = \"2\"";
mostCurrent._main._valorind1 = "2";
 //BA.debugLineNum = 735;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usourbano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 736;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 737;BA.debugLine="pregResultadoView.Text = \"Las áreas urbanas ge";
_pregresultadoview.setText((Object)("Las áreas urbanas generan gran cantidad de contaminantes que ingresan al agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 739;BA.debugLine="pregResultadoView.Text = \"Urban areas generate";
_pregresultadoview.setText((Object)("Urban areas generate pollutants that enter the water"));
 };
 //BA.debugLineNum = 741;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 743;BA.debugLine="Main.valorind1 = \"4\"";
mostCurrent._main._valorind1 = "4";
 //BA.debugLineNum = 744;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usouburbano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 745;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 746;BA.debugLine="pregResultadoView.Text = \"Las áreas suburbanas";
_pregresultadoview.setText((Object)("Las áreas suburbanas generan algunos contaminantes que ingresan al agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 748;BA.debugLine="pregResultadoView.Text = \"Suburban areas gener";
_pregresultadoview.setText((Object)("Suburban areas generate some pollutants that enter the water"));
 };
 //BA.debugLineNum = 750;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 752;BA.debugLine="Main.valorind1 = \"4\"";
mostCurrent._main._valorind1 = "4";
 //BA.debugLineNum = 753;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usoagricola.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 754;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 755;BA.debugLine="pregResultadoView.Text = \"Los fertilizantes y";
_pregresultadoview.setText((Object)("Los fertilizantes y pesticidas usados en la agricultura pueden afectar a los organismos acuáticos"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 757;BA.debugLine="pregResultadoView.Text = \"Fertilizers and pest";
_pregresultadoview.setText((Object)("Fertilizers and pesticides used in agriculture can affect the ecosystem"));
 };
 //BA.debugLineNum = 759;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 761;BA.debugLine="Main.valorind1 = \"10\"";
mostCurrent._main._valorind1 = "10";
 //BA.debugLineNum = 762;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usonatural.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 763;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 764;BA.debugLine="pregResultadoView.Text = \"Las áreas naturales";
_pregresultadoview.setText((Object)("Las áreas naturales suelen tener sus cuerpos de agua bien preservados"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 766;BA.debugLine="pregResultadoView.Text = \"Natural areas tend t";
_pregresultadoview.setText((Object)("Natural areas tend to have well preserved water bodies"));
 };
 //BA.debugLineNum = 768;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 770;BA.debugLine="Main.valorind1 = \"NS\"";
mostCurrent._main._valorind1 = "NS";
 //BA.debugLineNum = 771;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 772;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre el uso del suelo"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 774;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about the land use"));
 };
 //BA.debugLineNum = 776;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 777;BA.debugLine="miniPregunta1.SetBackgroundImage(Null)";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 779;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==2) { 
 //BA.debugLineNum = 782;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 783;BA.debugLine="Main.valorind2 = \"0\"";
mostCurrent._main._valorind2 = "0";
 //BA.debugLineNum = 784;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-feedlot.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 785;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 786;BA.debugLine="pregResultadoView.Text = \"Los feedlot ganadero";
_pregresultadoview.setText((Object)("Los feedlot ganaderos vuelcan una gran cantidad de nutrientes en el agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 788;BA.debugLine="pregResultadoView.Text = \"Feedlots pour a grea";
_pregresultadoview.setText((Object)("Feedlots pour a great deal of pollutants into the water"));
 };
 //BA.debugLineNum = 790;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 792;BA.debugLine="Main.valorind2 = \"2\"";
mostCurrent._main._valorind2 = "2";
 //BA.debugLineNum = 793;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadodisperso.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 794;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 795;BA.debugLine="pregResultadoView.Text = \"El ganado pisotea la";
_pregresultadoview.setText((Object)("El ganado pisotea las márgenes y, mediante sus deshechos, aumentan la cantidad de nutrientes en el agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 797;BA.debugLine="pregResultadoView.Text = \"Cattle stomps the ma";
_pregresultadoview.setText((Object)("Cattle stomps the margins, and provides the water with nutrients and pollutants"));
 };
 //BA.debugLineNum = 799;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 802;BA.debugLine="Main.valorind2 = \"4\"";
mostCurrent._main._valorind2 = "4";
 //BA.debugLineNum = 803;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadopoco.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 804;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 805;BA.debugLine="pregResultadoView.Text = \"El ganado pisotea la";
_pregresultadoview.setText((Object)("El ganado pisotea las márgenes y, mediante sus deshechos, aumentan la cantidad de nutrientes en el agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 807;BA.debugLine="pregResultadoView.Text = \"Cattle stomps the ma";
_pregresultadoview.setText((Object)("Cattle stomps the margins, and provides the water with nutrients and pollutants"));
 };
 //BA.debugLineNum = 809;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 811;BA.debugLine="Main.valorind2 = \"10\"";
mostCurrent._main._valorind2 = "10";
 //BA.debugLineNum = 812;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadonada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 813;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 814;BA.debugLine="pregResultadoView.Text = \"No hay ganado cerca";
_pregresultadoview.setText((Object)("No hay ganado cerca que pisotee las márgenes"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 816;BA.debugLine="pregResultadoView.Text = \"No cattle near the m";
_pregresultadoview.setText((Object)("No cattle near the margins"));
 };
 //BA.debugLineNum = 818;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 819;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else if(mostCurrent._rdopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 821;BA.debugLine="Main.valorind2 = \"NS\"";
mostCurrent._main._valorind2 = "NS";
 //BA.debugLineNum = 822;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 823;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre ganado"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 825;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about cattle"));
 };
 //BA.debugLineNum = 827;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 828;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 830;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==3) { 
 //BA.debugLineNum = 834;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 835;BA.debugLine="Main.valorind3 = \"10\"";
mostCurrent._main._valorind3 = "10";
 //BA.debugLineNum = 836;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 837;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 838;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 839;BA.debugLine="pregResultadoView.Text = \"La variada vegetaci";
_pregresultadoview.setText((Object)("La variada vegetación  suele indicar un buen estado del hábitat"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 841;BA.debugLine="pregResultadoView.Text = \"Varied vegetation t";
_pregresultadoview.setText((Object)("Varied vegetation tends to indicate a good habitat quality"));
 };
 //BA.debugLineNum = 843;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 845;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 846;BA.debugLine="pregResultadoView.Text = \"La presencia de árb";
_pregresultadoview.setText((Object)("La presencia de árboles en las llanuras no siempre indica un buen hábitat"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 848;BA.debugLine="pregResultadoView.Text = \"Varied vegetation i";
_pregresultadoview.setText((Object)("Varied vegetation in lowland rivers does not always indicate a good habitat quality"));
 };
 //BA.debugLineNum = 850;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 };
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 853;BA.debugLine="Main.valorind3 = \"5\"";
mostCurrent._main._valorind3 = "5";
 //BA.debugLineNum = 854;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionpoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 855;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 856;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 857;BA.debugLine="pregResultadoView.Text = \"La ausencia de vege";
_pregresultadoview.setText((Object)("La ausencia de vegetación suele indicar un hábitat degradado"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 859;BA.debugLine="pregResultadoView.Text = \"The absence of vege";
_pregresultadoview.setText((Object)("The absence of vegetation tends to indicate a degraded habitat"));
 };
 //BA.debugLineNum = 861;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 863;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 864;BA.debugLine="pregResultadoView.Text = \"La ausencia de vege";
_pregresultadoview.setText((Object)("La ausencia de vegetación en las llanuras no siempre indica un hábitat degradado"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 866;BA.debugLine="pregResultadoView.Text = \"The absence of vege";
_pregresultadoview.setText((Object)("The absence of vegetation in lowland ecosystems no always indicates a degraded habitat"));
 };
 //BA.debugLineNum = 868;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 };
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 871;BA.debugLine="Main.valorind3 = \"1\"";
mostCurrent._main._valorind3 = "1";
 //BA.debugLineNum = 872;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionnada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 873;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 874;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 875;BA.debugLine="pregResultadoView.Text = \"La ausencia de vege";
_pregresultadoview.setText((Object)("La ausencia de vegetación suele indicar un hábitat degradado"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 877;BA.debugLine="pregResultadoView.Text = \"The absence of vege";
_pregresultadoview.setText((Object)("The absence of vegetation tends to indicate a degraded habitat"));
 };
 //BA.debugLineNum = 879;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 881;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 882;BA.debugLine="pregResultadoView.Text = \"La ausencia de vege";
_pregresultadoview.setText((Object)("La ausencia de vegetación en las llanuras no siempre indica un hábitat degradado"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 884;BA.debugLine="pregResultadoView.Text = \"The absence of vege";
_pregresultadoview.setText((Object)("The absence of vegetation in lowland ecosystems no always indicates a degraded habitat"));
 };
 //BA.debugLineNum = 886;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 };
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 889;BA.debugLine="Main.valorind3 = \"NS\"";
mostCurrent._main._valorind3 = "NS";
 //BA.debugLineNum = 890;BA.debugLine="miniPregunta3.SetBackgroundImage(Null)";
mostCurrent._minipregunta3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 891;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 892;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre la vegetación"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 894;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about vegetation"));
 };
 //BA.debugLineNum = 896;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 898;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==4) { 
 //BA.debugLineNum = 902;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 903;BA.debugLine="Main.valorind4 = \"10\"";
mostCurrent._main._valorind4 = "10";
 //BA.debugLineNum = 904;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticavarios.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 905;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 906;BA.debugLine="pregResultadoView.Text = \"La presencia de vari";
_pregresultadoview.setText((Object)("La presencia de varias plantas acuáticas puede indicar un muy buen hábitat"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 908;BA.debugLine="pregResultadoView.Text = \"The presence of vari";
_pregresultadoview.setText((Object)("The presence of various aquatic plants can indicate a good habitat quality"));
 };
 //BA.debugLineNum = 910;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 912;BA.debugLine="Main.valorind4 = \"5\"";
mostCurrent._main._valorind4 = "5";
 //BA.debugLineNum = 913;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticapocos.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 914;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 915;BA.debugLine="pregResultadoView.Text = \"La presencia de alg";
_pregresultadoview.setText((Object)("La presencia de algunas plantas acuáticas suele indicar un hábitat conservado"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 917;BA.debugLine="pregResultadoView.Text = \"The presence of som";
_pregresultadoview.setText((Object)("The presence of some aquatic plants can indicate a preserved habitat"));
 };
 //BA.debugLineNum = 919;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 921;BA.debugLine="Main.valorind4 = \"0\"";
mostCurrent._main._valorind4 = "0";
 //BA.debugLineNum = 922;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticanada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 923;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 924;BA.debugLine="pregResultadoView.Text = \"La ausencia de plan";
_pregresultadoview.setText((Object)("La ausencia de plantas acuáticas suele indicar un hábitat degradado"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 926;BA.debugLine="pregResultadoView.Text = \"The absence of aqua";
_pregresultadoview.setText((Object)("The absence of aquatic plants could indicate a degraded habitat"));
 };
 //BA.debugLineNum = 928;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 930;BA.debugLine="Main.valorind4 = \"NS\"";
mostCurrent._main._valorind4 = "NS";
 //BA.debugLineNum = 931;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 932;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre las plantas acuáticas"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 934;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about aquatic vegetation"));
 };
 //BA.debugLineNum = 936;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 937;BA.debugLine="miniPregunta4.SetBackgroundImage(Null)";
mostCurrent._minipregunta4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 939;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==5) { 
 //BA.debugLineNum = 943;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 944;BA.debugLine="Main.valorind5 = \"10\"";
mostCurrent._main._valorind5 = "10";
 //BA.debugLineNum = 945;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 946;BA.debugLine="pregResultadoView.Text = \"El color transparent";
_pregresultadoview.setText((Object)("El color transparente del agua en estos ríos es normal"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 948;BA.debugLine="pregResultadoView.Text = \"Transparent color in";
_pregresultadoview.setText((Object)("Transparent color in this places is normal"));
 };
 //BA.debugLineNum = 950;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 951;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguabuena.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 953;BA.debugLine="Main.valorind5 = \"10\"";
mostCurrent._main._valorind5 = "10";
 //BA.debugLineNum = 954;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 955;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 956;BA.debugLine="pregResultadoView.Text = \"El color marrón del";
_pregresultadoview.setText((Object)("El color marrón del agua en estos ríos puede ser anormal"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 958;BA.debugLine="pregResultadoView.Text = \"Brown color in this";
_pregresultadoview.setText((Object)("Brown color in this places could not be normal"));
 };
 //BA.debugLineNum = 960;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguamarron.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 961;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 963;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 964;BA.debugLine="pregResultadoView.Text = \"El color marrón del";
_pregresultadoview.setText((Object)("El color marrón del agua en estos ríos es normal"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 966;BA.debugLine="pregResultadoView.Text = \"Brown color in this";
_pregresultadoview.setText((Object)("Brown color in this places is normal"));
 };
 //BA.debugLineNum = 968;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguamarron.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 969;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 };
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 972;BA.debugLine="Main.valorind5 = \"2\"";
mostCurrent._main._valorind5 = "2";
 //BA.debugLineNum = 973;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 974;BA.debugLine="pregResultadoView.Text = \"El color oscuro del";
_pregresultadoview.setText((Object)("El color oscuro del agua puede indicar una contaminación fuerte"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 976;BA.debugLine="pregResultadoView.Text = \"Dark-colored water c";
_pregresultadoview.setText((Object)("Dark-colored water could indicate pollution"));
 };
 //BA.debugLineNum = 978;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguaoscura.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 979;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 981;BA.debugLine="Main.valorind5 = \"NS\"";
mostCurrent._main._valorind5 = "NS";
 //BA.debugLineNum = 982;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 983;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre el color del agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 985;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about the water color"));
 };
 //BA.debugLineNum = 987;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 989;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==6) { 
 //BA.debugLineNum = 993;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 994;BA.debugLine="Main.valorind6 = \"10\"";
mostCurrent._main._valorind6 = "10";
 //BA.debugLineNum = 995;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 996;BA.debugLine="pregResultadoView.Text = \"El agua tiene buen o";
_pregresultadoview.setText((Object)("El agua tiene buen olor"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 998;BA.debugLine="pregResultadoView.Text = \"The water has normal";
_pregresultadoview.setText((Object)("The water has normal smell"));
 };
 //BA.debugLineNum = 1000;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1002;BA.debugLine="Main.valorind6 = \"0\"";
mostCurrent._main._valorind6 = "0";
 //BA.debugLineNum = 1003;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1004;BA.debugLine="pregResultadoView.Text = \"El agua con olor feo";
_pregresultadoview.setText((Object)("El agua con olor feo puede indicar una contaminación fuerte"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1006;BA.debugLine="pregResultadoView.Text = \"The water with stron";
_pregresultadoview.setText((Object)("The water with strong smell could indicate pollution"));
 };
 //BA.debugLineNum = 1008;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1010;BA.debugLine="Main.valorind6 = \"NS\"";
mostCurrent._main._valorind6 = "NS";
 //BA.debugLineNum = 1011;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1012;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre el olor del agua"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1014;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about the water's smell"));
 };
 //BA.debugLineNum = 1016;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1018;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==7) { 
 //BA.debugLineNum = 1021;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1022;BA.debugLine="Main.valorind7 = \"10\"";
mostCurrent._main._valorind7 = "10";
 //BA.debugLineNum = 1023;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basuranada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1024;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1025;BA.debugLine="pregResultadoView.Text = \"No hay basura!\"";
_pregresultadoview.setText((Object)("No hay basura!"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1027;BA.debugLine="pregResultadoView.Text = \"There is no litter!";
_pregresultadoview.setText((Object)("There is no litter! Great!"));
 };
 //BA.debugLineNum = 1029;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1031;BA.debugLine="Main.valorind7 = \"5\"";
mostCurrent._main._valorind7 = "5";
 //BA.debugLineNum = 1032;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basurapoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1033;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1034;BA.debugLine="pregResultadoView.Text = \"La basura contamina,";
_pregresultadoview.setText((Object)("La basura contamina, aunque sea en poca cantidad!"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1036;BA.debugLine="pregResultadoView.Text = \"Litter, even in litt";
_pregresultadoview.setText((Object)("Litter, even in little quantities, pollutes!"));
 };
 //BA.debugLineNum = 1038;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1040;BA.debugLine="Main.valorind7 = \"0\"";
mostCurrent._main._valorind7 = "0";
 //BA.debugLineNum = 1041;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basuramucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1042;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1043;BA.debugLine="pregResultadoView.Text = \"La basura contamina!";
_pregresultadoview.setText((Object)("La basura contamina!"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1045;BA.debugLine="pregResultadoView.Text = \"Litter pollutes! Bad";
_pregresultadoview.setText((Object)("Litter pollutes! Bad litter!"));
 };
 //BA.debugLineNum = 1047;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1049;BA.debugLine="Main.valorind7 = \"NS\"";
mostCurrent._main._valorind7 = "NS";
 //BA.debugLineNum = 1050;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1051;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre basura"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1053;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about litter!"));
 };
 //BA.debugLineNum = 1055;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 1056;BA.debugLine="miniPregunta6.SetBackgroundImage(Null)";
mostCurrent._minipregunta6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 1058;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==8) { 
 //BA.debugLineNum = 1062;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1063;BA.debugLine="Main.valorind8 = \"10\"";
mostCurrent._main._valorind8 = "10";
 //BA.debugLineNum = 1064;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1065;BA.debugLine="pregResultadoView.Text = \"La velocidad de la c";
_pregresultadoview.setText((Object)("La velocidad de la corriente es normal"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1067;BA.debugLine="pregResultadoView.Text = \"Water speed is norma";
_pregresultadoview.setText((Object)("Water speed is normal"));
 };
 //BA.debugLineNum = 1069;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1071;BA.debugLine="Main.valorind8 = \"10\"";
mostCurrent._main._valorind8 = "10";
 //BA.debugLineNum = 1072;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1073;BA.debugLine="pregResultadoView.Text = \"La velocidad de la c";
_pregresultadoview.setText((Object)("La velocidad de la corriente es normal"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1075;BA.debugLine="pregResultadoView.Text = \"Water speed is norma";
_pregresultadoview.setText((Object)("Water speed is normal"));
 };
 //BA.debugLineNum = 1077;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1079;BA.debugLine="Main.valorind8 = \"NS\"";
mostCurrent._main._valorind8 = "NS";
 //BA.debugLineNum = 1080;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1081;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre la velocidad"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1083;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about water speed"));
 };
 //BA.debugLineNum = 1085;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1087;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==9) { 
 //BA.debugLineNum = 1090;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1091;BA.debugLine="Main.valorind9 = \"0\"";
mostCurrent._main._valorind9 = "0";
 //BA.debugLineNum = 1092;BA.debugLine="miniPregunta7.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta7.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-desbordado.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1093;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1094;BA.debugLine="pregResultadoView.Text = \"Los ríos desbordados";
_pregresultadoview.setText((Object)("Los ríos desbordados pueden generar inundaciones en pueblos y ciudades!"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1096;BA.debugLine="pregResultadoView.Text = \"Overflowing rivers c";
_pregresultadoview.setText((Object)("Overflowing rivers can cause floods in cities and villages!"));
 };
 //BA.debugLineNum = 1098;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1100;BA.debugLine="miniPregunta7.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta7.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-desbordadonada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1101;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1102;BA.debugLine="pregResultadoView.Text = \"El río está en su ca";
_pregresultadoview.setText((Object)("El río está en su cauce, no debería generar inundaciones en pueblos y ciudades"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1104;BA.debugLine="pregResultadoView.Text = \"The river is not ove";
_pregresultadoview.setText((Object)("The river is not overflowing, there shouldn't be a problem with floods!"));
 };
 //BA.debugLineNum = 1106;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 1107;BA.debugLine="Main.valorind9 = \"10\"";
mostCurrent._main._valorind9 = "10";
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1109;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1110;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre el estado del cauce"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1112;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about water level"));
 };
 //BA.debugLineNum = 1114;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 1115;BA.debugLine="Main.valorind9 = \"NS\"";
mostCurrent._main._valorind9 = "NS";
 }else {
 //BA.debugLineNum = 1117;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==10) { 
 //BA.debugLineNum = 1120;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1121;BA.debugLine="Main.valorind10 = \"0\"";
mostCurrent._main._valorind10 = "0";
 //BA.debugLineNum = 1122;BA.debugLine="miniPreguntaEntubado.SetBackgroundImage(LoadBit";
mostCurrent._minipreguntaentubado.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-entubado.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1123;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1124;BA.debugLine="pregResultadoView.Text = \"Las canalizaciones y";
_pregresultadoview.setText((Object)("Las canalizaciones y entubamientos destruyen el hábitat para los organismos acuáticos"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1126;BA.debugLine="pregResultadoView.Text = \"Channeling and dredg";
_pregresultadoview.setText((Object)("Channeling and dredging contribute to the destruction of the habitat"));
 };
 //BA.debugLineNum = 1129;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1131;BA.debugLine="miniPreguntaEntubado.SetBackgroundImage(LoadBit";
mostCurrent._minipreguntaentubado.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-entubadonada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1132;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1133;BA.debugLine="pregResultadoView.Text = \"El río no está canal";
_pregresultadoview.setText((Object)("El río no está canalizado ni entubado, hay conexión entre el río y el agua subterránea"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1135;BA.debugLine="pregResultadoView.Text = \"There is good connec";
_pregresultadoview.setText((Object)("There is good connection between the river and the subterranean water"));
 };
 //BA.debugLineNum = 1137;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 1138;BA.debugLine="Main.valorind10 = \"10\"";
mostCurrent._main._valorind10 = "10";
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1140;BA.debugLine="Main.valorind10 = \"NS\"";
mostCurrent._main._valorind10 = "NS";
 //BA.debugLineNum = 1141;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1142;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText((Object)("No has ingresado información sobre canalizaciones o dragados"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1144;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about channeling or dredging"));
 };
 //BA.debugLineNum = 1146;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1148;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==11) { 
 //BA.debugLineNum = 1151;BA.debugLine="Dim preg As Int = 0";
_preg = (int) (0);
 //BA.debugLineNum = 1152;BA.debugLine="If chkOpcion1.Checked = True Then";
if (mostCurrent._chkopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1153;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 1155;BA.debugLine="If chkOpcion2.Checked = True Then";
if (mostCurrent._chkopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1156;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 1158;BA.debugLine="If chkOpcion3.Checked = True Then";
if (mostCurrent._chkopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1159;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 1161;BA.debugLine="If chkOpcion4.Checked = True Then";
if (mostCurrent._chkopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1162;BA.debugLine="preg = preg + 1";
_preg = (int) (_preg+1);
 };
 //BA.debugLineNum = 1164;BA.debugLine="If chkOpcion5.Checked = True Then";
if (mostCurrent._chkopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1165;BA.debugLine="preg = 0";
_preg = (int) (0);
 //BA.debugLineNum = 1166;BA.debugLine="Main.valorind11 = \"0\"";
mostCurrent._main._valorind11 = "0";
 //BA.debugLineNum = 1167;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1168;BA.debugLine="pregResultadoView.Text = \"El fondo es artifici";
_pregresultadoview.setText((Object)("El fondo es artificial, no hay hábitats para los organismos del fondo"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1170;BA.debugLine="pregResultadoView.Text = \"The sediment is arti";
_pregresultadoview.setText((Object)("The sediment is artificial, there is no habitat for sediment organisms"));
 };
 //BA.debugLineNum = 1174;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondoartificial.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1175;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 };
 //BA.debugLineNum = 1177;BA.debugLine="If chkOpcion6.Checked = True Then";
if (mostCurrent._chkopcion6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1178;BA.debugLine="preg = 0";
_preg = (int) (0);
 //BA.debugLineNum = 1179;BA.debugLine="Main.valorind11 = \"NS\"";
mostCurrent._main._valorind11 = "NS";
 //BA.debugLineNum = 1180;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1181;BA.debugLine="pregResultadoView.Text = \"No has ingresado in";
_pregresultadoview.setText((Object)("No has ingresado información sobre el fondo del río"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1183;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about the sediment"));
 };
 //BA.debugLineNum = 1185;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 };
 //BA.debugLineNum = 1188;BA.debugLine="If preg = 4 Then";
if (_preg==4) { 
 //BA.debugLineNum = 1189;BA.debugLine="Main.valorind11 = \"10\"";
mostCurrent._main._valorind11 = "10";
 //BA.debugLineNum = 1190;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1191;BA.debugLine="pregResultadoView.Text = \"Hay una gran varied";
_pregresultadoview.setText((Object)("Hay una gran variedad de sustratos en el fondo"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1193;BA.debugLine="pregResultadoView.Text = \"There's a great var";
_pregresultadoview.setText((Object)("There's a great variety of substrates in the sediment"));
 };
 //BA.debugLineNum = 1195;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondoarena.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1196;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(_preg==3) { 
 //BA.debugLineNum = 1198;BA.debugLine="Main.valorind11 = \"7.5\"";
mostCurrent._main._valorind11 = "7.5";
 //BA.debugLineNum = 1199;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1200;BA.debugLine="pregResultadoView.Text = \"Hay una buena varie";
_pregresultadoview.setText((Object)("Hay una buena variedad de sustratos en el fondo"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1203;BA.debugLine="pregResultadoView.Text = \"There's good variet";
_pregresultadoview.setText((Object)("There's good variety of substrates in the sediment"));
 };
 //BA.debugLineNum = 1206;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondopiedras.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1207;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(_preg==2) { 
 //BA.debugLineNum = 1209;BA.debugLine="Main.valorind11 = \"5\"";
mostCurrent._main._valorind11 = "5";
 //BA.debugLineNum = 1210;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1211;BA.debugLine="pregResultadoView.Text = \"Hay poca variedad d";
_pregresultadoview.setText((Object)("Hay poca variedad de sustratos en el fondo"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1214;BA.debugLine="pregResultadoView.Text = \"There's little vari";
_pregresultadoview.setText((Object)("There's little variety of substrates in the sediment"));
 };
 //BA.debugLineNum = 1216;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondoguijarros.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1217;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(_preg==1) { 
 //BA.debugLineNum = 1219;BA.debugLine="Main.valorind11 = \"2.5\"";
mostCurrent._main._valorind11 = "2.5";
 //BA.debugLineNum = 1220;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1221;BA.debugLine="pregResultadoView.Text = \"Hay muy poca varied";
_pregresultadoview.setText((Object)("Hay muy poca variedad de sustratos en el fondo"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1224;BA.debugLine="pregResultadoView.Text = \"There's very little";
_pregresultadoview.setText((Object)("There's very little variety of substrates in the sediment"));
 };
 //BA.debugLineNum = 1226;BA.debugLine="miniPregunta8.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-fondobarro.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1227;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 };
 }else if(_currentpregunta==12) { 
 //BA.debugLineNum = 1231;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1232;BA.debugLine="Main.valorind12 = \"0\"";
mostCurrent._main._valorind12 = "0";
 //BA.debugLineNum = 1233;BA.debugLine="miniPregunta9.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta9.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-margenmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1234;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 1235;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1236;BA.debugLine="pregResultadoView.Text = \"Las márgenes empina";
_pregresultadoview.setText((Object)("Las márgenes empinadas en los ríos de montaña pueden ser normales"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1239;BA.debugLine="pregResultadoView.Text = \"The steep margins i";
_pregresultadoview.setText((Object)("The steep margins in mountain rivers can be normal"));
 };
 //BA.debugLineNum = 1241;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 1243;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1244;BA.debugLine="pregResultadoView.Text = \"Las márgenes empina";
_pregresultadoview.setText((Object)("Las márgenes empinadas en los ríos de llanura indican una alteración del hábitat"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1247;BA.debugLine="pregResultadoView.Text = \"The steep margins i";
_pregresultadoview.setText((Object)("The steep margins in lowland rivers could indicate a habitat alteration"));
 };
 //BA.debugLineNum = 1249;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 };
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1252;BA.debugLine="Main.valorind12 = \"10\"";
mostCurrent._main._valorind12 = "10";
 //BA.debugLineNum = 1253;BA.debugLine="miniPregunta9.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta9.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-margenpoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1254;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 1255;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1256;BA.debugLine="pregResultadoView.Text = \"Las márgenes llanas";
_pregresultadoview.setText((Object)("Las márgenes llanas en los ríos de montaña pueden ser normales"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1259;BA.debugLine="pregResultadoView.Text = \"Gentle margins in m";
_pregresultadoview.setText((Object)("Gentle margins in mountain rivers can be normal"));
 };
 //BA.debugLineNum = 1261;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 1263;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1264;BA.debugLine="pregResultadoView.Text = \"Las márgenes llanas";
_pregresultadoview.setText((Object)("Las márgenes llanas en los ríos de llanura son normales"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1267;BA.debugLine="pregResultadoView.Text = \"Gentle margins in l";
_pregresultadoview.setText((Object)("Gentle margins in lowland rivers can be normal"));
 };
 //BA.debugLineNum = 1269;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 };
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1272;BA.debugLine="Main.valorind12 = \"NS\"";
mostCurrent._main._valorind12 = "NS";
 //BA.debugLineNum = 1273;BA.debugLine="miniPregunta9.SetBackgroundImage(Null)";
mostCurrent._minipregunta9.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 1274;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1275;BA.debugLine="pregResultadoView.Text = \"No has ingresado in";
_pregresultadoview.setText((Object)("No has ingresado información sobre las márgenes"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1278;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about the margins"));
 };
 //BA.debugLineNum = 1280;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1282;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==13) { 
 //BA.debugLineNum = 1285;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1286;BA.debugLine="Main.valorind13 = \"5\"";
mostCurrent._main._valorind13 = "5";
 //BA.debugLineNum = 1287;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1288;BA.debugLine="pregResultadoView.Text = \"El sombreado total";
_pregresultadoview.setText((Object)("El sombreado total de los ríos afecta al desarrollo de la comunidad de algas"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1291;BA.debugLine="pregResultadoView.Text = \"A total shadowing o";
_pregresultadoview.setText((Object)("A total shadowing of the water could affect the development of algae"));
 };
 //BA.debugLineNum = 1293;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 //BA.debugLineNum = 1294;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta10.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-totalsombra.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1296;BA.debugLine="Main.valorind13 = \"10\"";
mostCurrent._main._valorind13 = "10";
 //BA.debugLineNum = 1297;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1298;BA.debugLine="pregResultadoView.Text = \"Un sombreado inter";
_pregresultadoview.setText((Object)("Un sombreado intermedio es óptimo para el desarrollo de la comunidad de algas"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1301;BA.debugLine="pregResultadoView.Text = \"An intermediate sh";
_pregresultadoview.setText((Object)("An intermediate shadowing of the water is optimal for the development of algae"));
 };
 //BA.debugLineNum = 1303;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapS";
mostCurrent._minipregunta10.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-parcialsombra.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1304;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1306;BA.debugLine="Main.valorind13 = \"5\"";
mostCurrent._main._valorind13 = "5";
 //BA.debugLineNum = 1307;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1308;BA.debugLine="pregResultadoView.Text = \"La ausencia de somb";
_pregresultadoview.setText((Object)("La ausencia de sombreado puede indicar una degradación del hábitat"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1310;BA.debugLine="pregResultadoView.Text = \"No shadowing of the";
_pregresultadoview.setText((Object)("No shadowing of the water could indicate habitat degradation"));
 };
 //BA.debugLineNum = 1312;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 //BA.debugLineNum = 1313;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta10.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-sombranada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1315;BA.debugLine="Main.valorind13 = \"NS\"";
mostCurrent._main._valorind13 = "NS";
 //BA.debugLineNum = 1316;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 1317;BA.debugLine="pregResultadoView.Text = \"No has ingresado in";
_pregresultadoview.setText((Object)("No has ingresado información sobre el sombreado del río"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 1319;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText((Object)("You haven't entered information about shadowing"));
 };
 //BA.debugLineNum = 1322;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1324;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 };
 //BA.debugLineNum = 1329;BA.debugLine="Dim pregResultadoImg As ImageView";
_pregresultadoimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1330;BA.debugLine="pregResultadoImg.Initialize(\"\")";
_pregresultadoimg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1331;BA.debugLine="If pregResultadoView.Tag <> Null Then";
if (_pregresultadoview.getTag()!= null) { 
 //BA.debugLineNum = 1332;BA.debugLine="If pregResultadoView.Tag = \"MM\" Then";
if ((_pregresultadoview.getTag()).equals((Object)("MM"))) { 
 //BA.debugLineNum = 1333;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("M"))) { 
 //BA.debugLineNum = 1335;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("R"))) { 
 //BA.debugLineNum = 1337;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("B"))) { 
 //BA.debugLineNum = 1339;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("MB"))) { 
 //BA.debugLineNum = 1341;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("NS"))) { 
 //BA.debugLineNum = 1343;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaNS.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 };
 //BA.debugLineNum = 1349;BA.debugLine="scrResultados.Panel.AddView(pregResultadoImg, 10d";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_pregresultadoimg.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_currentpregunta*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1350;BA.debugLine="scrResultados.Panel.AddView(pregResultadoView, 50";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_pregresultadoview.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_currentpregunta*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1351;BA.debugLine="scrResultados.Panel.Height = 12 * 60dip + 60dip -";
mostCurrent._scrresultados.getPanel().setHeight((int) (12*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))));
 //BA.debugLineNum = 1352;BA.debugLine="End Sub";
return "";
}
}
