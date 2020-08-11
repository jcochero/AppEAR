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

public class reporte_habitat_estuario extends Activity implements B4AActivity{
	public static reporte_habitat_estuario mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.reporte_habitat_estuario");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (reporte_habitat_estuario).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.reporte_habitat_estuario");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.reporte_habitat_estuario", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (reporte_habitat_estuario) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (reporte_habitat_estuario) Resume **");
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
		return reporte_habitat_estuario.class;
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
            BA.LogInfo("** Activity (reporte_habitat_estuario) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (reporte_habitat_estuario) Pause event (activity is not paused). **");
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
            reporte_habitat_estuario mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (reporte_habitat_estuario) Resume **");
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
public static String _tiporio = "";
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
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta11 = null;
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
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta11 = null;
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
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta11 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta12 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta13 = null;
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
public anywheresoftware.b4a.objects.ButtonWrapper _btnrdopcion1 = null;
public anywheresoftware.b4a.objects.WebViewWrapper _explicacionviewer = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _csv = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _rec = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _csvchk = null;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper _recchk = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbpreguntas = null;
public static int _cantidadpreguntas = 0;
public static int _currentpregunta = 0;
public anywheresoftware.b4a.objects.PanelWrapper _pnlpreguntas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlresultados = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnterminar = null;
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrresultados = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltituloresultados = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public static String _langlocal = "";
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
public static String _preguntanumero = "";
public static String _dateandtime = "";
public static int _valorcalidad = 0;
public static int _valorns = 0;
public ilpla.appear.gauge _gauge1 = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmasdetalle = null;
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
public ilpla.appear.frmfelicitaciones _frmfelicitaciones = null;
public ilpla.appear.frmlocalizacion _frmlocalizacion = null;
public ilpla.appear.frmlogin _frmlogin = null;
public ilpla.appear.frmpoliticadatos _frmpoliticadatos = null;
public ilpla.appear.httputils2service _httputils2service = null;
public ilpla.appear.imagedownloader _imagedownloader = null;
public ilpla.appear.inatcheck _inatcheck = null;
public ilpla.appear.reporte_fotos _reporte_fotos = null;
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
 //BA.debugLineNum = 131;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 132;BA.debugLine="Activity.LoadLayout(\"HabitatEstuario\")";
mostCurrent._activity.LoadLayout("HabitatEstuario",mostCurrent.activityBA);
 //BA.debugLineNum = 133;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 134;BA.debugLine="If pnlQuestions.IsInitialized = False Then";
if (mostCurrent._pnlquestions.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 135;BA.debugLine="pnlQuestions.Initialize(\"pnlQuestions\")";
mostCurrent._pnlquestions.Initialize(mostCurrent.activityBA,"pnlQuestions");
 };
 //BA.debugLineNum = 138;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="pnlQuestions.LoadLayout(\"layPregunta\")";
mostCurrent._pnlquestions.LoadLayout("layPregunta",mostCurrent.activityBA);
 //BA.debugLineNum = 141;BA.debugLine="If FirstTime = True Then";
if (_firsttime==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 142;BA.debugLine="csv.Initialize(pnlOpciones)";
mostCurrent._csv.Initialize((android.view.View)(mostCurrent._pnlopciones.getObject()));
 //BA.debugLineNum = 143;BA.debugLine="csvChk.Initialize(pnlChecks)";
mostCurrent._csvchk.Initialize((android.view.View)(mostCurrent._pnlchecks.getObject()));
 };
 //BA.debugLineNum = 147;BA.debugLine="preguntanumero = 1";
mostCurrent._preguntanumero = BA.NumberToString(1);
 //BA.debugLineNum = 148;BA.debugLine="valorind1 = \"\"";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 149;BA.debugLine="valorind2 = \"\"";
mostCurrent._valorind2 = "";
 //BA.debugLineNum = 150;BA.debugLine="valorind3 = \"\"";
mostCurrent._valorind3 = "";
 //BA.debugLineNum = 151;BA.debugLine="valorind4 = \"\"";
mostCurrent._valorind4 = "";
 //BA.debugLineNum = 152;BA.debugLine="valorind5 = \"\"";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 153;BA.debugLine="valorind6 = \"\"";
mostCurrent._valorind6 = "";
 //BA.debugLineNum = 154;BA.debugLine="valorind7 = \"\"";
mostCurrent._valorind7 = "";
 //BA.debugLineNum = 155;BA.debugLine="valorind8 = \"\"";
mostCurrent._valorind8 = "";
 //BA.debugLineNum = 156;BA.debugLine="valorind9 = \"\"";
mostCurrent._valorind9 = "";
 //BA.debugLineNum = 157;BA.debugLine="valorind10 = \"\"";
mostCurrent._valorind10 = "";
 //BA.debugLineNum = 158;BA.debugLine="valorind11 = \"\"";
mostCurrent._valorind11 = "";
 //BA.debugLineNum = 159;BA.debugLine="valorind12 = \"\"";
mostCurrent._valorind12 = "";
 //BA.debugLineNum = 160;BA.debugLine="valorind13 = \"\"";
mostCurrent._valorind13 = "";
 //BA.debugLineNum = 161;BA.debugLine="valorind14 = \"\"";
mostCurrent._valorind14 = "";
 //BA.debugLineNum = 162;BA.debugLine="valorind15 = \"\"";
mostCurrent._valorind15 = "";
 //BA.debugLineNum = 163;BA.debugLine="valorind16 = \"\"";
mostCurrent._valorind16 = "";
 //BA.debugLineNum = 164;BA.debugLine="miniPregunta1.SetBackgroundImage(Null)";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 165;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 166;BA.debugLine="miniPregunta3.SetBackgroundImage(Null)";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 167;BA.debugLine="miniPregunta4.SetBackgroundImage(Null)";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 168;BA.debugLine="miniPregunta5.SetBackgroundImage(Null)";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 169;BA.debugLine="miniPregunta6.SetBackgroundImage(Null)";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 170;BA.debugLine="miniPregunta7.SetBackgroundImage(Null)";
mostCurrent._minipregunta7.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 171;BA.debugLine="miniPregunta8.SetBackgroundImage(Null)";
mostCurrent._minipregunta8.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 172;BA.debugLine="miniPregunta9.SetBackgroundImage(Null)";
mostCurrent._minipregunta9.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 173;BA.debugLine="miniPregunta10.SetBackgroundImage(Null)";
mostCurrent._minipregunta10.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 174;BA.debugLine="miniPregunta11.SetBackgroundImage(Null)";
mostCurrent._minipregunta11.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 177;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 178;BA.debugLine="DateTime.TimeFormat = \"HH:mm\"";
anywheresoftware.b4a.keywords.Common.DateTime.setTimeFormat("HH:mm");
 //BA.debugLineNum = 179;BA.debugLine="dateandtime = DateTime.Date(DateTime.now)";
mostCurrent._dateandtime = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 180;BA.debugLine="valorcalidad = 0";
_valorcalidad = (int) (0);
 //BA.debugLineNum = 181;BA.debugLine="valorNS = 0";
_valorns = (int) (0);
 //BA.debugLineNum = 185;BA.debugLine="pgbPreguntas.Initialize(\"\")";
mostCurrent._pgbpreguntas.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 188;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregunta";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta1,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 197;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 193;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 1763;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 1766;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1767;BA.debugLine="GenerarScreenshot";
_generarscreenshot();
 //BA.debugLineNum = 1770;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 1771;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 1772;BA.debugLine="StartActivity(Reporte_Fotos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._reporte_fotos.getObject()));
 //BA.debugLineNum = 1773;BA.debugLine="End Sub";
return "";
}
public static String  _btnmasdetalle_click() throws Exception{
 //BA.debugLineNum = 1757;BA.debugLine="Sub btnMasDetalle_Click";
 //BA.debugLineNum = 1758;BA.debugLine="scrResultados.Visible = True";
mostCurrent._scrresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1759;BA.debugLine="btnMasDetalle.Visible = False";
mostCurrent._btnmasdetalle.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1760;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta1_click() throws Exception{
 //BA.debugLineNum = 471;BA.debugLine="Sub btnPregunta1_Click";
 //BA.debugLineNum = 472;BA.debugLine="CargarPregunta(1)";
_cargarpregunta((int) (1));
 //BA.debugLineNum = 473;BA.debugLine="currentpregunta = 1";
_currentpregunta = (int) (1);
 //BA.debugLineNum = 474;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta10_click() throws Exception{
 //BA.debugLineNum = 507;BA.debugLine="Sub btnPregunta10_Click";
 //BA.debugLineNum = 510;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta2_click() throws Exception{
 //BA.debugLineNum = 475;BA.debugLine="Sub btnPregunta2_Click";
 //BA.debugLineNum = 476;BA.debugLine="CargarPregunta(2)";
_cargarpregunta((int) (2));
 //BA.debugLineNum = 477;BA.debugLine="currentpregunta = 2";
_currentpregunta = (int) (2);
 //BA.debugLineNum = 478;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta3_click() throws Exception{
 //BA.debugLineNum = 479;BA.debugLine="Sub btnPregunta3_Click";
 //BA.debugLineNum = 480;BA.debugLine="CargarPregunta(3)";
_cargarpregunta((int) (3));
 //BA.debugLineNum = 481;BA.debugLine="currentpregunta = 3";
_currentpregunta = (int) (3);
 //BA.debugLineNum = 482;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta4_click() throws Exception{
 //BA.debugLineNum = 483;BA.debugLine="Sub btnPregunta4_Click";
 //BA.debugLineNum = 484;BA.debugLine="CargarPregunta(4)";
_cargarpregunta((int) (4));
 //BA.debugLineNum = 485;BA.debugLine="currentpregunta = 4";
_currentpregunta = (int) (4);
 //BA.debugLineNum = 486;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta5_click() throws Exception{
 //BA.debugLineNum = 487;BA.debugLine="Sub btnPregunta5_Click";
 //BA.debugLineNum = 488;BA.debugLine="CargarPregunta(5)";
_cargarpregunta((int) (5));
 //BA.debugLineNum = 489;BA.debugLine="currentpregunta = 5";
_currentpregunta = (int) (5);
 //BA.debugLineNum = 490;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta6_click() throws Exception{
 //BA.debugLineNum = 491;BA.debugLine="Sub btnPregunta6_Click";
 //BA.debugLineNum = 492;BA.debugLine="CargarPregunta(7)";
_cargarpregunta((int) (7));
 //BA.debugLineNum = 493;BA.debugLine="currentpregunta = 7";
_currentpregunta = (int) (7);
 //BA.debugLineNum = 494;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta7_click() throws Exception{
 //BA.debugLineNum = 495;BA.debugLine="Sub btnPregunta7_Click";
 //BA.debugLineNum = 496;BA.debugLine="CargarPregunta(10)";
_cargarpregunta((int) (10));
 //BA.debugLineNum = 497;BA.debugLine="currentpregunta = 10";
_currentpregunta = (int) (10);
 //BA.debugLineNum = 498;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta8_click() throws Exception{
 //BA.debugLineNum = 499;BA.debugLine="Sub btnPregunta8_Click";
 //BA.debugLineNum = 502;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta9_click() throws Exception{
 //BA.debugLineNum = 503;BA.debugLine="Sub btnPregunta9_Click";
 //BA.debugLineNum = 504;BA.debugLine="CargarPregunta(13)";
_cargarpregunta((int) (13));
 //BA.debugLineNum = 505;BA.debugLine="currentpregunta = 13";
_currentpregunta = (int) (13);
 //BA.debugLineNum = 506;BA.debugLine="End Sub";
return "";
}
public static String  _btnrdopcion1_click() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _fondogris = null;
String _htmlej = "";
anywheresoftware.b4a.objects.ButtonWrapper _butongris = null;
 //BA.debugLineNum = 276;BA.debugLine="Sub btnRdOpcion1_Click";
 //BA.debugLineNum = 278;BA.debugLine="Dim fondogris As ImageView";
_fondogris = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 279;BA.debugLine="fondogris.Initialize(\"fondogris\")";
_fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 280;BA.debugLine="fondogris.Color = Colors.ARGB(150, 64,64,64)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 281;BA.debugLine="Activity.AddView(fondogris, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 284;BA.debugLine="Dim htmlej As String";
_htmlej = "";
 //BA.debugLineNum = 285;BA.debugLine="htmlej = File.GetText(File.DirAssets, imgej1)";
_htmlej = anywheresoftware.b4a.keywords.Common.File.GetText(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imgej1);
 //BA.debugLineNum = 287;BA.debugLine="explicacionviewer.Initialize(\"explicacionviewer\")";
mostCurrent._explicacionviewer.Initialize(mostCurrent.activityBA,"explicacionviewer");
 //BA.debugLineNum = 288;BA.debugLine="explicacionviewer.LoadHTML(htmlej)";
mostCurrent._explicacionviewer.LoadHtml(_htmlej);
 //BA.debugLineNum = 289;BA.debugLine="Activity.AddView(explicacionviewer, 15%x, 15%y, 7";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._explicacionviewer.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (75),mostCurrent.activityBA));
 //BA.debugLineNum = 292;BA.debugLine="Dim butongris As Button";
_butongris = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 293;BA.debugLine="butongris.Initialize(\"fondogris\")";
_butongris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 294;BA.debugLine="butongris.Color = Colors.ARGB(255, 255,255,255)";
_butongris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 295;BA.debugLine="butongris.TextColor = Colors.Black";
_butongris.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 296;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 297;BA.debugLine="butongris.Text = \"Cerrar\"";
_butongris.setText(BA.ObjectToCharSequence("Cerrar"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 299;BA.debugLine="butongris.Text = \"Close\"";
_butongris.setText(BA.ObjectToCharSequence("Close"));
 };
 //BA.debugLineNum = 301;BA.debugLine="Activity.AddView(butongris, 15%x, 90%y, 75%x, 5%y";
mostCurrent._activity.AddView((android.view.View)(_butongris.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (15),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (75),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public static String  _btnsiguiente_click() throws Exception{
String _valresp = "";
int _promuso = 0;
int _divuso = 0;
int _promveg = 0;
int _divveg = 0;
int _promagua = 0;
int _divagua = 0;
int _promdesb = 0;
int _didesb = 0;
 //BA.debugLineNum = 1334;BA.debugLine="Sub btnSiguiente_Click";
 //BA.debugLineNum = 1335;BA.debugLine="Dim valresp As String = \"\"";
_valresp = "";
 //BA.debugLineNum = 1336;BA.debugLine="valresp = ValidarRespuesta";
_valresp = _validarrespuesta();
 //BA.debugLineNum = 1338;BA.debugLine="If valresp = \"no\" Then";
if ((_valresp).equals("no")) { 
 //BA.debugLineNum = 1339;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1340;BA.debugLine="Msgbox(\"Debe seleccionar una opción para contin";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Debe seleccionar una opción para continuar"),BA.ObjectToCharSequence("Atención!"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1342;BA.debugLine="Msgbox(\"You have to select an option to continu";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You have to select an option to continue"),BA.ObjectToCharSequence("Attention"),mostCurrent.activityBA);
 };
 }else {
 //BA.debugLineNum = 1346;BA.debugLine="If btnSiguiente.Text = \"Ok\" Then";
if ((mostCurrent._btnsiguiente.getText()).equals("Ok")) { 
 //BA.debugLineNum = 1347;BA.debugLine="pnlQuestions.Visible = False";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1356;BA.debugLine="Dim promuso As Int";
_promuso = 0;
 //BA.debugLineNum = 1357;BA.debugLine="Dim divuso As Int";
_divuso = 0;
 //BA.debugLineNum = 1358;BA.debugLine="If valorind1 <> \"\" And valorind1 <> \"NS\" Then";
if ((mostCurrent._valorind1).equals("") == false && (mostCurrent._valorind1).equals("NS") == false) { 
 //BA.debugLineNum = 1359;BA.debugLine="promuso = promuso + valorind1";
_promuso = (int) (_promuso+(double)(Double.parseDouble(mostCurrent._valorind1)));
 //BA.debugLineNum = 1360;BA.debugLine="divuso = divuso + 1";
_divuso = (int) (_divuso+1);
 };
 //BA.debugLineNum = 1362;BA.debugLine="If valorind12 <> \"\" And valorind12 <> \"NS\" Then";
if ((mostCurrent._valorind12).equals("") == false && (mostCurrent._valorind12).equals("NS") == false) { 
 //BA.debugLineNum = 1363;BA.debugLine="promuso = promuso + valorind12";
_promuso = (int) (_promuso+(double)(Double.parseDouble(mostCurrent._valorind12)));
 //BA.debugLineNum = 1364;BA.debugLine="divuso = divuso + 1";
_divuso = (int) (_divuso+1);
 };
 //BA.debugLineNum = 1366;BA.debugLine="If divuso <> 0 Then";
if (_divuso!=0) { 
 //BA.debugLineNum = 1367;BA.debugLine="promuso = promuso / divuso";
_promuso = (int) (_promuso/(double)_divuso);
 //BA.debugLineNum = 1368;BA.debugLine="utilidades.ColorearCirculos(btnPregunta1, prom";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta1.getObject())),BA.NumberToString(_promuso));
 }else {
 //BA.debugLineNum = 1370;BA.debugLine="If valorind1 <> \"NS\" And valorind12 <> \"NS\" Th";
if ((mostCurrent._valorind1).equals("NS") == false && (mostCurrent._valorind12).equals("NS") == false) { 
 //BA.debugLineNum = 1371;BA.debugLine="utilidades.ColorearCirculos(btnPregunta1, \"\")";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta1.getObject())),"");
 }else {
 //BA.debugLineNum = 1373;BA.debugLine="utilidades.ColorearCirculos(btnPregunta1, \"NS";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta1.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1378;BA.debugLine="Dim promveg As Int";
_promveg = 0;
 //BA.debugLineNum = 1379;BA.debugLine="Dim divveg As Int";
_divveg = 0;
 //BA.debugLineNum = 1380;BA.debugLine="If valorind3 <> \"\" And valorind3 <> \"NS\" Then";
if ((mostCurrent._valorind3).equals("") == false && (mostCurrent._valorind3).equals("NS") == false) { 
 //BA.debugLineNum = 1381;BA.debugLine="promveg = promveg + valorind3";
_promveg = (int) (_promveg+(double)(Double.parseDouble(mostCurrent._valorind3)));
 //BA.debugLineNum = 1382;BA.debugLine="divveg = divveg + 1";
_divveg = (int) (_divveg+1);
 };
 //BA.debugLineNum = 1384;BA.debugLine="If valorind9 <> \"\" And valorind9 <> \"NS\" Then";
if ((mostCurrent._valorind9).equals("") == false && (mostCurrent._valorind9).equals("NS") == false) { 
 //BA.debugLineNum = 1385;BA.debugLine="promveg = promveg + valorind9";
_promveg = (int) (_promveg+(double)(Double.parseDouble(mostCurrent._valorind9)));
 //BA.debugLineNum = 1386;BA.debugLine="divveg = divveg + 1";
_divveg = (int) (_divveg+1);
 };
 //BA.debugLineNum = 1388;BA.debugLine="If divveg <> 0 Then";
if (_divveg!=0) { 
 //BA.debugLineNum = 1389;BA.debugLine="promveg = promveg / divveg";
_promveg = (int) (_promveg/(double)_divveg);
 //BA.debugLineNum = 1390;BA.debugLine="utilidades.ColorearCirculos(btnPregunta3, prom";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta3.getObject())),BA.NumberToString(_promveg));
 }else {
 //BA.debugLineNum = 1392;BA.debugLine="If valorind3 <> \"NS\" And valorind9 <> \"NS\" The";
if ((mostCurrent._valorind3).equals("NS") == false && (mostCurrent._valorind9).equals("NS") == false) { 
 //BA.debugLineNum = 1393;BA.debugLine="utilidades.ColorearCirculos(btnPregunta3, \"\")";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta3.getObject())),"");
 }else {
 //BA.debugLineNum = 1395;BA.debugLine="utilidades.ColorearCirculos(btnPregunta3, \"NS";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta3.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1400;BA.debugLine="Dim promagua As Int";
_promagua = 0;
 //BA.debugLineNum = 1401;BA.debugLine="Dim divagua As Int";
_divagua = 0;
 //BA.debugLineNum = 1402;BA.debugLine="If valorind5 <> \"\" And valorind5 <> \"NS\" Then";
if ((mostCurrent._valorind5).equals("") == false && (mostCurrent._valorind5).equals("NS") == false) { 
 //BA.debugLineNum = 1403;BA.debugLine="promagua = promagua + valorind5";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._valorind5)));
 //BA.debugLineNum = 1404;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1406;BA.debugLine="If valorind6 <> \"\" And valorind6 <> \"NS\" Then";
if ((mostCurrent._valorind6).equals("") == false && (mostCurrent._valorind6).equals("NS") == false) { 
 //BA.debugLineNum = 1407;BA.debugLine="promagua = promagua + valorind6";
_promagua = (int) (_promagua+(double)(Double.parseDouble(mostCurrent._valorind6)));
 //BA.debugLineNum = 1408;BA.debugLine="divagua = divagua + 1";
_divagua = (int) (_divagua+1);
 };
 //BA.debugLineNum = 1410;BA.debugLine="If divagua <> 0 Then";
if (_divagua!=0) { 
 //BA.debugLineNum = 1411;BA.debugLine="promagua = promagua / divagua";
_promagua = (int) (_promagua/(double)_divagua);
 //BA.debugLineNum = 1412;BA.debugLine="utilidades.ColorearCirculos(btnPregunta5, prom";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),BA.NumberToString(_promagua));
 }else {
 //BA.debugLineNum = 1414;BA.debugLine="If valorind5 <> \"NS\" And valorind6 <> \"NS\" The";
if ((mostCurrent._valorind5).equals("NS") == false && (mostCurrent._valorind6).equals("NS") == false) { 
 //BA.debugLineNum = 1415;BA.debugLine="utilidades.ColorearCirculos(btnPregunta5, \"\")";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),"");
 }else {
 //BA.debugLineNum = 1417;BA.debugLine="utilidades.ColorearCirculos(btnPregunta5, \"NS";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta5.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1422;BA.debugLine="Dim promdesb As Int";
_promdesb = 0;
 //BA.debugLineNum = 1423;BA.debugLine="Dim didesb As Int";
_didesb = 0;
 //BA.debugLineNum = 1424;BA.debugLine="If valorind10 <> \"\" And valorind10 <> \"NS\" Then";
if ((mostCurrent._valorind10).equals("") == false && (mostCurrent._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1425;BA.debugLine="promdesb = promdesb + valorind10";
_promdesb = (int) (_promdesb+(double)(Double.parseDouble(mostCurrent._valorind10)));
 //BA.debugLineNum = 1426;BA.debugLine="didesb = didesb + 1";
_didesb = (int) (_didesb+1);
 };
 //BA.debugLineNum = 1428;BA.debugLine="If valorind11 <> \"\" And valorind11 <> \"NS\" Then";
if ((mostCurrent._valorind11).equals("") == false && (mostCurrent._valorind11).equals("NS") == false) { 
 //BA.debugLineNum = 1429;BA.debugLine="promdesb = promdesb + valorind11";
_promdesb = (int) (_promdesb+(double)(Double.parseDouble(mostCurrent._valorind11)));
 //BA.debugLineNum = 1430;BA.debugLine="didesb = didesb + 1";
_didesb = (int) (_didesb+1);
 };
 //BA.debugLineNum = 1433;BA.debugLine="If didesb <> 0 Then";
if (_didesb!=0) { 
 //BA.debugLineNum = 1434;BA.debugLine="promdesb = promdesb / didesb";
_promdesb = (int) (_promdesb/(double)_didesb);
 //BA.debugLineNum = 1435;BA.debugLine="utilidades.ColorearCirculos(btnPregunta7, prom";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),BA.NumberToString(_promdesb));
 }else {
 //BA.debugLineNum = 1437;BA.debugLine="If valorind10 <> \"NS\" And valorind11 <> \"NS\" T";
if ((mostCurrent._valorind10).equals("NS") == false && (mostCurrent._valorind11).equals("NS") == false) { 
 //BA.debugLineNum = 1438;BA.debugLine="utilidades.ColorearCirculos(btnPregunta7, \"\")";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),"");
 }else {
 //BA.debugLineNum = 1440;BA.debugLine="utilidades.ColorearCirculos(btnPregunta7, \"NS";
mostCurrent._utilidades._colorearcirculos /*String*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.LabelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.LabelWrapper(), (android.widget.TextView)(mostCurrent._btnpregunta7.getObject())),"NS");
 };
 };
 //BA.debugLineNum = 1452;BA.debugLine="cantidadpreguntas = 0";
_cantidadpreguntas = (int) (0);
 //BA.debugLineNum = 1453;BA.debugLine="If valorind1 <> \"\" Then";
if ((mostCurrent._valorind1).equals("") == false) { 
 //BA.debugLineNum = 1454;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1456;BA.debugLine="If valorind2 <> \"\" Then";
if ((mostCurrent._valorind2).equals("") == false) { 
 //BA.debugLineNum = 1457;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1459;BA.debugLine="If valorind3 <> \"\" Then";
if ((mostCurrent._valorind3).equals("") == false) { 
 //BA.debugLineNum = 1460;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1462;BA.debugLine="If valorind4 <> \"\" Then";
if ((mostCurrent._valorind4).equals("") == false) { 
 //BA.debugLineNum = 1463;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1465;BA.debugLine="If valorind5 <> \"\" Then";
if ((mostCurrent._valorind5).equals("") == false) { 
 //BA.debugLineNum = 1466;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1468;BA.debugLine="If valorind6 <> \"\" Then";
if ((mostCurrent._valorind6).equals("") == false) { 
 //BA.debugLineNum = 1469;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1471;BA.debugLine="If valorind7 <> \"\" Then";
if ((mostCurrent._valorind7).equals("") == false) { 
 //BA.debugLineNum = 1472;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1474;BA.debugLine="If valorind8 <> \"\" Then";
if ((mostCurrent._valorind8).equals("") == false) { 
 //BA.debugLineNum = 1475;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1477;BA.debugLine="If valorind9 <> \"\" Then";
if ((mostCurrent._valorind9).equals("") == false) { 
 //BA.debugLineNum = 1478;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1480;BA.debugLine="If valorind10 <> \"\" Then";
if ((mostCurrent._valorind10).equals("") == false) { 
 //BA.debugLineNum = 1481;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1483;BA.debugLine="If valorind11 <> \"\" Then";
if ((mostCurrent._valorind11).equals("") == false) { 
 //BA.debugLineNum = 1484;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1486;BA.debugLine="If valorind12 <> \"\" Then";
if ((mostCurrent._valorind12).equals("") == false) { 
 //BA.debugLineNum = 1487;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1489;BA.debugLine="If valorind13 <> \"\" Then";
if ((mostCurrent._valorind13).equals("") == false) { 
 //BA.debugLineNum = 1490;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1492;BA.debugLine="If valorind14 <> \"\" Then";
if ((mostCurrent._valorind14).equals("") == false) { 
 //BA.debugLineNum = 1493;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1495;BA.debugLine="If valorind15 <> \"\" Then";
if ((mostCurrent._valorind15).equals("") == false) { 
 //BA.debugLineNum = 1496;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1498;BA.debugLine="If valorind16 <> \"\" Then";
if ((mostCurrent._valorind16).equals("") == false) { 
 //BA.debugLineNum = 1499;BA.debugLine="cantidadpreguntas = cantidadpreguntas + 1";
_cantidadpreguntas = (int) (_cantidadpreguntas+1);
 };
 //BA.debugLineNum = 1503;BA.debugLine="pgbPreguntas.Progress = (cantidadpreguntas * 10";
mostCurrent._pgbpreguntas.setProgress((int) ((_cantidadpreguntas*100)/(double)13));
 //BA.debugLineNum = 1504;BA.debugLine="btnPregunta8.Visible = False";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1509;BA.debugLine="If preguntanumero = 1 Then";
if ((mostCurrent._preguntanumero).equals(BA.NumberToString(1))) { 
 //BA.debugLineNum = 1510;BA.debugLine="btnPregunta2.Visible = True";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1512;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta2,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1513;BA.debugLine="lblPregunta2.Visible = True";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1514;BA.debugLine="lblPregunta1.Visible = False";
mostCurrent._lblpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(2))) { 
 //BA.debugLineNum = 1516;BA.debugLine="btnPregunta3.Visible = True";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1517;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta3,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1518;BA.debugLine="lblPregunta3.Visible = True";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1519;BA.debugLine="lblPregunta2.Visible = False";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(3))) { 
 //BA.debugLineNum = 1521;BA.debugLine="btnPregunta4.Visible = True";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1522;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta4,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1523;BA.debugLine="lblPregunta4.Visible = True";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1524;BA.debugLine="lblPregunta3.Visible = False";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(4))) { 
 //BA.debugLineNum = 1526;BA.debugLine="btnPregunta5.Visible = True";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1527;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta5,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1528;BA.debugLine="lblPregunta5.Visible = True";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1529;BA.debugLine="lblPregunta4.Visible = False";
mostCurrent._lblpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(5))) { 
 //BA.debugLineNum = 1531;BA.debugLine="btnPregunta6.Visible = True";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1532;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta6,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1533;BA.debugLine="lblPregunta6.Visible = True";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1534;BA.debugLine="lblPregunta5.Visible = False";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(6))) { 
 //BA.debugLineNum = 1536;BA.debugLine="btnPregunta7.Visible = True";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1537;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta7,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1538;BA.debugLine="lblPregunta7.Visible = True";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1539;BA.debugLine="lblPregunta6.Visible = False";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(7))) { 
 //BA.debugLineNum = 1542;BA.debugLine="btnPregunta9.Visible = True";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1543;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnPregu";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnpregunta9,anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 1544;BA.debugLine="lblPregunta9.Visible = True";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1545;BA.debugLine="lblPregunta7.Visible = False";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._preguntanumero).equals(BA.NumberToString(8))) { 
 //BA.debugLineNum = 1547;BA.debugLine="btnPregunta9.Visible = False";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1548;BA.debugLine="lblPregunta9.Visible = False";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1549;BA.debugLine="utilidades.CreateHaloEffect(Activity, btnTermi";
mostCurrent._utilidades._createhaloeffect /*void*/ (mostCurrent.activityBA,(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),mostCurrent._btnterminar,anywheresoftware.b4a.keywords.Common.Colors.Red);
 };
 //BA.debugLineNum = 1551;BA.debugLine="preguntanumero = preguntanumero + 1";
mostCurrent._preguntanumero = BA.NumberToString((double)(Double.parseDouble(mostCurrent._preguntanumero))+1);
 //BA.debugLineNum = 1554;BA.debugLine="If pgbPreguntas.Progress = 100 Then";
if (mostCurrent._pgbpreguntas.getProgress()==100) { 
 //BA.debugLineNum = 1555;BA.debugLine="btnTerminar.Visible = True";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 1559;BA.debugLine="currentpregunta = btnSiguiente.tag";
_currentpregunta = (int)(BA.ObjectToNumber(mostCurrent._btnsiguiente.getTag()));
 //BA.debugLineNum = 1560;BA.debugLine="CargarPregunta(btnSiguiente.Tag)";
_cargarpregunta((int)(BA.ObjectToNumber(mostCurrent._btnsiguiente.getTag())));
 };
 };
 //BA.debugLineNum = 1565;BA.debugLine="rdOpcion1.Checked = False";
mostCurrent._rdopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1566;BA.debugLine="rdOpcion2.Checked = False";
mostCurrent._rdopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1567;BA.debugLine="rdOpcion3.Checked = False";
mostCurrent._rdopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1568;BA.debugLine="rdOpcion4.Checked = False";
mostCurrent._rdopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1569;BA.debugLine="rdOpcion5.Checked = False";
mostCurrent._rdopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1570;BA.debugLine="rdOpcion6.Checked = False";
mostCurrent._rdopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1573;BA.debugLine="End Sub";
return "";
}
public static String  _btnterminar_click() throws Exception{
 //BA.debugLineNum = 1581;BA.debugLine="Sub btnTerminar_Click";
 //BA.debugLineNum = 1582;BA.debugLine="btnTerminar.Visible = False";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1583;BA.debugLine="TerminarEval";
_terminareval();
 //BA.debugLineNum = 1584;BA.debugLine="GuardarEval";
_guardareval();
 //BA.debugLineNum = 1586;BA.debugLine="End Sub";
return "";
}
public static String  _butongris_click() throws Exception{
 //BA.debugLineNum = 309;BA.debugLine="Sub butongris_click";
 //BA.debugLineNum = 310;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 311;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 312;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 313;BA.debugLine="End Sub";
return "";
}
public static String  _cargarpregunta(int _numpregunta) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 518;BA.debugLine="Sub CargarPregunta (numpregunta As Int)";
 //BA.debugLineNum = 519;BA.debugLine="pnlQuestions.Visible = True";
mostCurrent._pnlquestions.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 520;BA.debugLine="pnlQuestions.BringToFront";
mostCurrent._pnlquestions.BringToFront();
 //BA.debugLineNum = 524;BA.debugLine="btnPregunta1.Visible = False";
mostCurrent._btnpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 525;BA.debugLine="btnPregunta2.Visible = False";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 526;BA.debugLine="btnPregunta3.Visible = False";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 527;BA.debugLine="btnPregunta4.Visible = False";
mostCurrent._btnpregunta4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 528;BA.debugLine="btnPregunta5.Visible = False";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 529;BA.debugLine="btnPregunta6.Visible = False";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 530;BA.debugLine="btnPregunta7.Visible = False";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 531;BA.debugLine="btnPregunta8.Visible = False";
mostCurrent._btnpregunta8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 532;BA.debugLine="btnPregunta9.Visible = False";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 533;BA.debugLine="btnPregunta11.Visible = False";
mostCurrent._btnpregunta11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 535;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 536;BA.debugLine="If csv.Bitmap = Null Then";
if (mostCurrent._csv.getBitmap()== null) { 
 //BA.debugLineNum = 537;BA.debugLine="csv.Initialize(pnlOpciones)";
mostCurrent._csv.Initialize((android.view.View)(mostCurrent._pnlopciones.getObject()));
 //BA.debugLineNum = 538;BA.debugLine="csvChk.Initialize(pnlChecks)";
mostCurrent._csvchk.Initialize((android.view.View)(mostCurrent._pnlchecks.getObject()));
 //BA.debugLineNum = 539;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 540;BA.debugLine="cd.Initialize(Colors.ARGB(0,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 541;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 542;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 }else {
 //BA.debugLineNum = 544;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 545;BA.debugLine="cd.Initialize(Colors.ARGB(0,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 546;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 547;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 };
 //BA.debugLineNum = 550;BA.debugLine="If numpregunta = 1 Then";
if (_numpregunta==1) { 
 //BA.debugLineNum = 551;BA.debugLine="TipoPreguntaRad(6, False)";
_tipopreguntarad((int) (6),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 552;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 553;BA.debugLine="lblPregunta.Text = \"¿Que hay alrededor del luga";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Que hay alrededor del lugar?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 555;BA.debugLine="lblPregunta.Text = \"What are the surroundings l";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("What are the surroundings like?"));
 };
 //BA.debugLineNum = 557;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1a.png").getObject()));
 //BA.debugLineNum = 558;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1b.png").getObject()));
 //BA.debugLineNum = 559;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1c.png").getObject()));
 //BA.debugLineNum = 560;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1d.png").getObject()));
 //BA.debugLineNum = 561;BA.debugLine="rdOpcion5.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"1e.png").getObject()));
 //BA.debugLineNum = 562;BA.debugLine="rdOpcion6.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 563;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 564;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 566;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Next question"));
 };
 //BA.debugLineNum = 569;BA.debugLine="btnSiguiente.Tag = \"12\"";
mostCurrent._btnsiguiente.setTag((Object)("12"));
 }else if(_numpregunta==2) { 
 //BA.debugLineNum = 571;BA.debugLine="TipoPreguntaRad(5, True)";
_tipopreguntarad((int) (5),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 572;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 573;BA.debugLine="lblPregunta.Text = \"¿Hay ganado cerca del sitio";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay ganado cerca del sitio?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 575;BA.debugLine="lblPregunta.Text = \"Is there cattle nearby?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Is there cattle nearby?"));
 };
 //BA.debugLineNum = 577;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2a.png").getObject()));
 //BA.debugLineNum = 578;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2b.png").getObject()));
 //BA.debugLineNum = 579;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2c.png").getObject()));
 //BA.debugLineNum = 580;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"2d.png").getObject()));
 //BA.debugLineNum = 581;BA.debugLine="rdOpcion5.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 583;BA.debugLine="imgej1 = \"ej1.html\"";
mostCurrent._imgej1 = "ej1.html";
 //BA.debugLineNum = 584;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==3) { 
 //BA.debugLineNum = 586;BA.debugLine="TipoPreguntaRad(4, False)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 587;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 588;BA.debugLine="lblPregunta.Text = \"¿Cómo son los árboles?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Cómo son los árboles?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 590;BA.debugLine="lblPregunta.Text = \"Are there trees nearby?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Are there trees nearby?"));
 };
 //BA.debugLineNum = 592;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3a.png").getObject()));
 //BA.debugLineNum = 593;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3b.png").getObject()));
 //BA.debugLineNum = 594;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"3c.png").getObject()));
 //BA.debugLineNum = 595;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 596;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 597;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 599;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Next question"));
 };
 //BA.debugLineNum = 602;BA.debugLine="btnSiguiente.Tag = \"9\"";
mostCurrent._btnsiguiente.setTag((Object)("9"));
 }else if(_numpregunta==4) { 
 //BA.debugLineNum = 604;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 605;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 606;BA.debugLine="lblPregunta.Text = \"¿Hay juncos en la costa?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay juncos en la costa?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 608;BA.debugLine="lblPregunta.Text = \"Are there reeds in the shor";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Are there reeds in the shore?"));
 };
 //BA.debugLineNum = 610;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag1a.png").getObject()));
 //BA.debugLineNum = 611;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag1b.png").getObject()));
 //BA.debugLineNum = 612;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag1c.png").getObject()));
 //BA.debugLineNum = 613;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 615;BA.debugLine="imgej1 = \"est1ej.html\"";
mostCurrent._imgej1 = "est1ej.html";
 //BA.debugLineNum = 616;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 617;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 619;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Next question"));
 };
 //BA.debugLineNum = 622;BA.debugLine="btnSiguiente.Tag = \"8\"";
mostCurrent._btnsiguiente.setTag((Object)("8"));
 }else if(_numpregunta==5) { 
 //BA.debugLineNum = 624;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 625;BA.debugLine="lblPregunta.Text = \"¿Es transparente el agua?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Es transparente el agua?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 627;BA.debugLine="lblPregunta.Text = \"Is the water transparent?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Is the water transparent?"));
 };
 //BA.debugLineNum = 629;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 630;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5a.png").getObject()));
 //BA.debugLineNum = 631;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5b.png").getObject()));
 //BA.debugLineNum = 632;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"5c.png").getObject()));
 //BA.debugLineNum = 633;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 635;BA.debugLine="imgej1 = \"ej5.html\"";
mostCurrent._imgej1 = "ej5.html";
 //BA.debugLineNum = 636;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 637;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 639;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Next question"));
 };
 //BA.debugLineNum = 641;BA.debugLine="btnSiguiente.Tag = \"6\"";
mostCurrent._btnsiguiente.setTag((Object)("6"));
 }else if(_numpregunta==6) { 
 //BA.debugLineNum = 645;BA.debugLine="TipoPreguntaRad(3,False)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 646;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 647;BA.debugLine="lblPregunta.Text = \"¿Tiene mal olor?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Tiene mal olor?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 649;BA.debugLine="lblPregunta.Text = \"Does the water smell bad?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Does the water smell bad?"));
 };
 //BA.debugLineNum = 651;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"6a.png").getObject()));
 //BA.debugLineNum = 652;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"6b.png").getObject()));
 //BA.debugLineNum = 653;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 654;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==7) { 
 //BA.debugLineNum = 656;BA.debugLine="TipoPreguntaRad(4,True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 657;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 658;BA.debugLine="lblPregunta.Text = \"¿Hay basura en la costa?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay basura en la costa?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 660;BA.debugLine="lblPregunta.Text = \"Is there litter in the shor";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Is there litter in the shore?"));
 };
 //BA.debugLineNum = 662;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7a.png").getObject()));
 //BA.debugLineNum = 663;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7b.png").getObject()));
 //BA.debugLineNum = 664;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"7c.png").getObject()));
 //BA.debugLineNum = 665;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 667;BA.debugLine="imgej1 = \"ej7.html\"";
mostCurrent._imgej1 = "ej7.html";
 //BA.debugLineNum = 668;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==8) { 
 //BA.debugLineNum = 673;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 674;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 675;BA.debugLine="lblPregunta.Text = \"¿Hay pajonales cerca de la";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay pajonales cerca de la costa?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 677;BA.debugLine="lblPregunta.Text = \"Are there bulrush plants in";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Are there bulrush plants in the shore?"));
 };
 //BA.debugLineNum = 679;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario2a.png").getObject()));
 //BA.debugLineNum = 680;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario2b.png").getObject()));
 //BA.debugLineNum = 681;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario2c.png").getObject()));
 //BA.debugLineNum = 682;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 684;BA.debugLine="imgej1 = \"est2ej.html\"";
mostCurrent._imgej1 = "est2ej.html";
 //BA.debugLineNum = 685;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==9) { 
 //BA.debugLineNum = 687;BA.debugLine="TipoPreguntaRad(4, True)";
_tipopreguntarad((int) (4),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 688;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 689;BA.debugLine="lblPregunta.Text = \"¿Hay muchos árboles en la c";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay muchos árboles en la costa?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 691;BA.debugLine="lblPregunta.Text = \"Are there many trees nearby";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Are there many trees nearby?"));
 };
 //BA.debugLineNum = 693;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario3a.png").getObject()));
 //BA.debugLineNum = 694;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario3b.png").getObject()));
 //BA.debugLineNum = 695;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario3c.png").getObject()));
 //BA.debugLineNum = 696;BA.debugLine="rdOpcion4.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 698;BA.debugLine="imgej1 = \"est3ej.html\"";
mostCurrent._imgej1 = "est3ej.html";
 //BA.debugLineNum = 699;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==10) { 
 //BA.debugLineNum = 701;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 702;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 703;BA.debugLine="lblPregunta.Text = \"¿Hay murallas en la costa?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay murallas en la costa?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 705;BA.debugLine="lblPregunta.Text = \"Is there a wall separating";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Is there a wall separating the water from the shore?"));
 };
 //BA.debugLineNum = 707;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag2a.png").getObject()));
 //BA.debugLineNum = 708;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag2b.png").getObject()));
 //BA.debugLineNum = 709;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 711;BA.debugLine="imgej1 = \"est4ej.html\"";
mostCurrent._imgej1 = "est4ej.html";
 //BA.debugLineNum = 712;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 713;BA.debugLine="btnSiguiente.Text = \"Siguiente pregunta\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Siguiente pregunta"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 715;BA.debugLine="btnSiguiente.Text = \"Next question\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Next question"));
 };
 //BA.debugLineNum = 718;BA.debugLine="btnSiguiente.Tag = \"11\"";
mostCurrent._btnsiguiente.setTag((Object)("11"));
 }else if(_numpregunta==11) { 
 //BA.debugLineNum = 720;BA.debugLine="TipoPreguntaRad(3, False)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 721;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 722;BA.debugLine="lblPregunta.Text = \"¿Hay muelles cerca?\"";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay muelles cerca?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 724;BA.debugLine="lblPregunta.Text = \"Are there piers or docks ne";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Are there piers or docks nearby?"));
 };
 //BA.debugLineNum = 726;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag3a.png").getObject()));
 //BA.debugLineNum = 727;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag3b.png").getObject()));
 //BA.debugLineNum = 728;BA.debugLine="rdOpcion6.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 730;BA.debugLine="imgej1 = \"est5ej.html\"";
mostCurrent._imgej1 = "est5ej.html";
 //BA.debugLineNum = 731;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==12) { 
 //BA.debugLineNum = 733;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 734;BA.debugLine="lblPregunta.Text = \"¿Hay campings en la costa c";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay campings en la costa cerca tuyo?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 736;BA.debugLine="lblPregunta.Text = \"Are there camping sites nea";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Are there camping sites nearby?"));
 };
 //BA.debugLineNum = 738;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 739;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag4a.png").getObject()));
 //BA.debugLineNum = 740;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"lag4b.png").getObject()));
 //BA.debugLineNum = 741;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 743;BA.debugLine="imgej1 = \"est6ej.html\"";
mostCurrent._imgej1 = "est6ej.html";
 //BA.debugLineNum = 744;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 }else if(_numpregunta==13) { 
 //BA.debugLineNum = 746;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 747;BA.debugLine="lblPregunta.Text = \"¿Hay escombros en la costa?";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("¿Hay escombros en la costa?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 749;BA.debugLine="lblPregunta.Text = \"Is there debris in the shor";
mostCurrent._lblpregunta.setText(BA.ObjectToCharSequence("Is there debris in the shore?"));
 };
 //BA.debugLineNum = 751;BA.debugLine="TipoPreguntaRad(3, True)";
_tipopreguntarad((int) (3),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 752;BA.debugLine="rdOpcion1.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario7a.png").getObject()));
 //BA.debugLineNum = 753;BA.debugLine="rdOpcion2.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"estuario7b.png").getObject()));
 //BA.debugLineNum = 754;BA.debugLine="rdOpcion3.SetBackgroundImage(LoadBitmap(File.Dir";
mostCurrent._rdopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._langlocal+"skipq.png").getObject()));
 //BA.debugLineNum = 756;BA.debugLine="imgej1 = \"est7ej.html\"";
mostCurrent._imgej1 = "est7ej.html";
 //BA.debugLineNum = 757;BA.debugLine="btnSiguiente.Text = \"Ok\"";
mostCurrent._btnsiguiente.setText(BA.ObjectToCharSequence("Ok"));
 };
 //BA.debugLineNum = 760;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 304;BA.debugLine="Sub fondogris_click";
 //BA.debugLineNum = 305;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 306;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 307;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 308;BA.debugLine="End Sub";
return "";
}
public static String  _generarscreenshot() throws Exception{
String _filename = "";
anywheresoftware.b4a.agraham.reflection.Reflection _obj1 = null;
anywheresoftware.b4a.agraham.reflection.Reflection _obj2 = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _bmp = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper _c = null;
long _now = 0L;
Object[] _args = null;
String[] _types = null;
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 1784;BA.debugLine="Sub GenerarScreenshot";
 //BA.debugLineNum = 1786;BA.debugLine="Dim filename As String";
_filename = "";
 //BA.debugLineNum = 1787;BA.debugLine="filename = Main.username & \"_\" & Form_Reporte.cur";
_filename = mostCurrent._main._username /*String*/ +"_"+mostCurrent._form_reporte._currentproject /*String*/ ;
 //BA.debugLineNum = 1788;BA.debugLine="Dim Obj1, Obj2 As Reflector";
_obj1 = new anywheresoftware.b4a.agraham.reflection.Reflection();
_obj2 = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 1789;BA.debugLine="Dim bmp As Bitmap";
_bmp = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1790;BA.debugLine="Dim c As Canvas";
_c = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 1791;BA.debugLine="Dim now As Long";
_now = 0L;
 //BA.debugLineNum = 1792;BA.debugLine="DateTime.DateFormat = \"yyMMddHHmmss\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("yyMMddHHmmss");
 //BA.debugLineNum = 1793;BA.debugLine="now = DateTime.Now";
_now = anywheresoftware.b4a.keywords.Common.DateTime.getNow();
 //BA.debugLineNum = 1794;BA.debugLine="Obj1.Target = Obj1.GetActivityBA";
_obj1.Target = (Object)(_obj1.GetActivityBA(processBA));
 //BA.debugLineNum = 1795;BA.debugLine="Obj1.Target = Obj1.GetField(\"vg\")";
_obj1.Target = _obj1.GetField("vg");
 //BA.debugLineNum = 1796;BA.debugLine="bmp.InitializeMutable(Activity.Width, Activity.";
_bmp.InitializeMutable(mostCurrent._activity.getWidth(),mostCurrent._activity.getHeight());
 //BA.debugLineNum = 1797;BA.debugLine="c.Initialize2(bmp)";
_c.Initialize2((android.graphics.Bitmap)(_bmp.getObject()));
 //BA.debugLineNum = 1798;BA.debugLine="Dim args(1) As Object";
_args = new Object[(int) (1)];
{
int d0 = _args.length;
for (int i0 = 0;i0 < d0;i0++) {
_args[i0] = new Object();
}
}
;
 //BA.debugLineNum = 1799;BA.debugLine="Dim types(1) As String";
_types = new String[(int) (1)];
java.util.Arrays.fill(_types,"");
 //BA.debugLineNum = 1800;BA.debugLine="Obj2.Target = c";
_obj2.Target = (Object)(_c);
 //BA.debugLineNum = 1801;BA.debugLine="Obj2.Target = Obj2.GetField(\"canvas\")";
_obj2.Target = _obj2.GetField("canvas");
 //BA.debugLineNum = 1802;BA.debugLine="args(0) = Obj2.Target";
_args[(int) (0)] = _obj2.Target;
 //BA.debugLineNum = 1803;BA.debugLine="types(0) = \"android.graphics.Canvas\"";
_types[(int) (0)] = "android.graphics.Canvas";
 //BA.debugLineNum = 1804;BA.debugLine="Obj1.RunMethod4(\"draw\", args, types)";
_obj1.RunMethod4("draw",_args,_types);
 //BA.debugLineNum = 1805;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 1806;BA.debugLine="out = File.OpenOutput(Starter.savedir & \"/AppEA";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(mostCurrent._starter._savedir /*String*/ +"/AppEAR/","Final-"+_filename+".png",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1807;BA.debugLine="bmp.WriteToStream(out, 100, \"PNG\")";
_bmp.WriteToStream((java.io.OutputStream)(_out.getObject()),(int) (100),BA.getEnumFromString(android.graphics.Bitmap.CompressFormat.class,"PNG"));
 //BA.debugLineNum = 1808;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 1809;BA.debugLine="Main.screenshotpath = Starter.savedir & \"/AppEAR/";
mostCurrent._main._screenshotpath /*String*/  = mostCurrent._starter._savedir /*String*/ +"/AppEAR/"+"Final-"+_filename+".png";
 //BA.debugLineNum = 1810;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private pnlQuestions As Panel";
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
 //BA.debugLineNum = 24;BA.debugLine="Private btnPregunta11 As Button";
mostCurrent._btnpregunta11 = new anywheresoftware.b4a.objects.ButtonWrapper();
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
 //BA.debugLineNum = 35;BA.debugLine="Private lblPregunta11 As Label";
mostCurrent._lblpregunta11 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private minimagen As String";
mostCurrent._minimagen = "";
 //BA.debugLineNum = 39;BA.debugLine="Private miniPregunta1 As ImageView";
mostCurrent._minipregunta1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private miniPregunta2 As ImageView";
mostCurrent._minipregunta2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private miniPregunta3 As ImageView";
mostCurrent._minipregunta3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private miniPregunta4 As ImageView";
mostCurrent._minipregunta4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private miniPregunta5 As ImageView";
mostCurrent._minipregunta5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private miniPregunta6 As ImageView";
mostCurrent._minipregunta6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private miniPregunta7 As ImageView";
mostCurrent._minipregunta7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private miniPregunta8 As ImageView";
mostCurrent._minipregunta8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private miniPregunta9 As ImageView";
mostCurrent._minipregunta9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private miniPregunta10 As ImageView";
mostCurrent._minipregunta10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private miniPregunta11 As ImageView";
mostCurrent._minipregunta11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private miniPregunta12 As ImageView";
mostCurrent._minipregunta12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Private miniPregunta13 As ImageView";
mostCurrent._minipregunta13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private lblPregunta As Label";
mostCurrent._lblpregunta = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private btnSiguiente As Button";
mostCurrent._btnsiguiente = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private pnlOpciones As Panel";
mostCurrent._pnlopciones = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private pnlChecks As Panel";
mostCurrent._pnlchecks = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private rdOpcion1 As RadioButton";
mostCurrent._rdopcion1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private rdOpcion2 As RadioButton";
mostCurrent._rdopcion2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Private rdOpcion3 As RadioButton";
mostCurrent._rdopcion3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Private rdOpcion4 As RadioButton";
mostCurrent._rdopcion4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Private rdOpcion5 As RadioButton";
mostCurrent._rdopcion5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Private rdOpcion6 As RadioButton";
mostCurrent._rdopcion6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.RadioButtonWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Private chkOpcion3 As CheckBox";
mostCurrent._chkopcion3 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Private chkOpcion2 As CheckBox";
mostCurrent._chkopcion2 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Private chkOpcion1 As CheckBox";
mostCurrent._chkopcion1 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private chkOpcion4 As CheckBox";
mostCurrent._chkopcion4 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Private chkOpcion5 As CheckBox";
mostCurrent._chkopcion5 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private chkOpcion6 As CheckBox";
mostCurrent._chkopcion6 = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Dim imgej1 As String = \"\"";
mostCurrent._imgej1 = "";
 //BA.debugLineNum = 75;BA.debugLine="Private btnRdOpcion1 As Button";
mostCurrent._btnrdopcion1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 76;BA.debugLine="Dim explicacionviewer As WebView";
mostCurrent._explicacionviewer = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 79;BA.debugLine="Dim csv As Canvas";
mostCurrent._csv = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 80;BA.debugLine="Dim rec As Rect";
mostCurrent._rec = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Dim csvChk As Canvas";
mostCurrent._csvchk = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Dim recChk As Rect";
mostCurrent._recchk = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.RectWrapper();
 //BA.debugLineNum = 85;BA.debugLine="Private pgbPreguntas As ProgressBar";
mostCurrent._pgbpreguntas = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Dim cantidadpreguntas As Int";
_cantidadpreguntas = 0;
 //BA.debugLineNum = 87;BA.debugLine="Dim currentpregunta As Int";
_currentpregunta = 0;
 //BA.debugLineNum = 89;BA.debugLine="Private pnlPreguntas As Panel";
mostCurrent._pnlpreguntas = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 90;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Private pnlResultados As Panel";
mostCurrent._pnlresultados = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 92;BA.debugLine="Private btnTerminar As Button";
mostCurrent._btnterminar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private scrResultados As ScrollView";
mostCurrent._scrresultados = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private lblTituloResultados As Label";
mostCurrent._lbltituloresultados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private langLocal As String";
mostCurrent._langlocal = "";
 //BA.debugLineNum = 104;BA.debugLine="Dim valorind1 As String";
mostCurrent._valorind1 = "";
 //BA.debugLineNum = 105;BA.debugLine="Dim valorind2 As String";
mostCurrent._valorind2 = "";
 //BA.debugLineNum = 106;BA.debugLine="Dim valorind3 As String";
mostCurrent._valorind3 = "";
 //BA.debugLineNum = 107;BA.debugLine="Dim valorind4 As String";
mostCurrent._valorind4 = "";
 //BA.debugLineNum = 108;BA.debugLine="Dim valorind5 As String";
mostCurrent._valorind5 = "";
 //BA.debugLineNum = 109;BA.debugLine="Dim valorind6 As String";
mostCurrent._valorind6 = "";
 //BA.debugLineNum = 110;BA.debugLine="Dim valorind7 As String";
mostCurrent._valorind7 = "";
 //BA.debugLineNum = 111;BA.debugLine="Dim valorind8 As String";
mostCurrent._valorind8 = "";
 //BA.debugLineNum = 112;BA.debugLine="Dim valorind9 As String";
mostCurrent._valorind9 = "";
 //BA.debugLineNum = 113;BA.debugLine="Dim valorind10 As String";
mostCurrent._valorind10 = "";
 //BA.debugLineNum = 114;BA.debugLine="Dim valorind11 As String";
mostCurrent._valorind11 = "";
 //BA.debugLineNum = 115;BA.debugLine="Dim valorind12 As String";
mostCurrent._valorind12 = "";
 //BA.debugLineNum = 116;BA.debugLine="Dim valorind13 As String";
mostCurrent._valorind13 = "";
 //BA.debugLineNum = 117;BA.debugLine="Dim valorind14 As String";
mostCurrent._valorind14 = "";
 //BA.debugLineNum = 118;BA.debugLine="Dim valorind15 As String";
mostCurrent._valorind15 = "";
 //BA.debugLineNum = 119;BA.debugLine="Dim valorind16 As String";
mostCurrent._valorind16 = "";
 //BA.debugLineNum = 120;BA.debugLine="Dim preguntanumero As String";
mostCurrent._preguntanumero = "";
 //BA.debugLineNum = 121;BA.debugLine="Dim dateandtime As String";
mostCurrent._dateandtime = "";
 //BA.debugLineNum = 122;BA.debugLine="Dim valorcalidad As Int";
_valorcalidad = 0;
 //BA.debugLineNum = 123;BA.debugLine="Dim valorNS As Int";
_valorns = 0;
 //BA.debugLineNum = 126;BA.debugLine="Private Gauge1 As Gauge";
mostCurrent._gauge1 = new ilpla.appear.gauge();
 //BA.debugLineNum = 127;BA.debugLine="Private xui As XUI";
mostCurrent._xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 128;BA.debugLine="Private btnMasDetalle As Button";
mostCurrent._btnmasdetalle = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static String  _guardareval() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 1726;BA.debugLine="Sub GuardarEval";
 //BA.debugLineNum = 1729;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1730;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 1731;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 1732;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind1",(Object)(mostCurrent._valorind1),_map1);
 //BA.debugLineNum = 1733;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind2",(Object)(mostCurrent._valorind2),_map1);
 //BA.debugLineNum = 1734;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind3",(Object)(mostCurrent._valorind3),_map1);
 //BA.debugLineNum = 1735;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind4",(Object)(mostCurrent._valorind4),_map1);
 //BA.debugLineNum = 1736;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind5",(Object)(mostCurrent._valorind5),_map1);
 //BA.debugLineNum = 1737;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind6",(Object)(mostCurrent._valorind6),_map1);
 //BA.debugLineNum = 1738;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind7",(Object)(mostCurrent._valorind7),_map1);
 //BA.debugLineNum = 1739;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind8",(Object)(mostCurrent._valorind8),_map1);
 //BA.debugLineNum = 1740;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind9",(Object)(mostCurrent._valorind9),_map1);
 //BA.debugLineNum = 1741;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind10",(Object)(mostCurrent._valorind10),_map1);
 //BA.debugLineNum = 1742;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind11",(Object)(mostCurrent._valorind11),_map1);
 //BA.debugLineNum = 1743;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind12",(Object)(mostCurrent._valorind12),_map1);
 //BA.debugLineNum = 1744;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind13",(Object)(mostCurrent._valorind13),_map1);
 //BA.debugLineNum = 1745;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind14",(Object)(mostCurrent._valorind14),_map1);
 //BA.debugLineNum = 1746;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind15",(Object)(mostCurrent._valorind15),_map1);
 //BA.debugLineNum = 1748;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorind20",(Object)(_valorns),_map1);
 //BA.debugLineNum = 1750;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"val";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","valorcalidad",(Object)(_valorcalidad),_map1);
 //BA.debugLineNum = 1751;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"geo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","georeferencedDate",(Object)(mostCurrent._dateandtime),_map1);
 //BA.debugLineNum = 1753;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ter";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("si"),_map1);
 //BA.debugLineNum = 1755;BA.debugLine="End Sub";
return "";
}
public static String  _pnlresultados_click() throws Exception{
 //BA.debugLineNum = 1774;BA.debugLine="Sub pnlResultados_Click";
 //BA.debugLineNum = 1775;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1776;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim tiporio As String";
_tiporio = "";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion1_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 232;BA.debugLine="Sub rdOpcion1_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 233;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 234;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 235;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 236;BA.debugLine="rec.Initialize(rdOpcion1.Left, rdOpcion1.Top, rdO";
mostCurrent._rec.Initialize(mostCurrent._rdopcion1.getLeft(),mostCurrent._rdopcion1.getTop(),(int) (mostCurrent._rdopcion1.getWidth()+mostCurrent._rdopcion1.getLeft()),(int) (mostCurrent._rdopcion1.getHeight()+mostCurrent._rdopcion1.getTop()));
 //BA.debugLineNum = 237;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion2_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 239;BA.debugLine="Sub rdOpcion2_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 240;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 241;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 242;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 243;BA.debugLine="rec.Initialize(rdOpcion2.Left, rdOpcion2.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion2.getLeft(),mostCurrent._rdopcion2.getTop(),(int) (mostCurrent._rdopcion2.getWidth()+mostCurrent._rdopcion2.getLeft()),(int) (mostCurrent._rdopcion2.getHeight()+mostCurrent._rdopcion2.getTop()));
 //BA.debugLineNum = 244;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 245;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion3_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 246;BA.debugLine="Sub rdOpcion3_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 247;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 248;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 249;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 250;BA.debugLine="rec.Initialize(rdOpcion3.Left, rdOpcion3.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion3.getLeft(),mostCurrent._rdopcion3.getTop(),(int) (mostCurrent._rdopcion3.getWidth()+mostCurrent._rdopcion3.getLeft()),(int) (mostCurrent._rdopcion3.getHeight()+mostCurrent._rdopcion3.getTop()));
 //BA.debugLineNum = 251;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 252;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion4_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 253;BA.debugLine="Sub rdOpcion4_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 254;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 255;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 256;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 257;BA.debugLine="rec.Initialize(rdOpcion4.Left, rdOpcion4.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion4.getLeft(),mostCurrent._rdopcion4.getTop(),(int) (mostCurrent._rdopcion4.getWidth()+mostCurrent._rdopcion4.getLeft()),(int) (mostCurrent._rdopcion4.getHeight()+mostCurrent._rdopcion4.getTop()));
 //BA.debugLineNum = 258;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion5_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 260;BA.debugLine="Sub rdOpcion5_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 261;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 262;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 263;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 264;BA.debugLine="rec.Initialize(rdOpcion5.Left, rdOpcion5.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion5.getLeft(),mostCurrent._rdopcion5.getTop(),(int) (mostCurrent._rdopcion5.getWidth()+mostCurrent._rdopcion5.getLeft()),(int) (mostCurrent._rdopcion5.getHeight()+mostCurrent._rdopcion5.getTop()));
 //BA.debugLineNum = 265;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 266;BA.debugLine="End Sub";
return "";
}
public static String  _rdopcion6_checkedchange(boolean _checked) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 267;BA.debugLine="Sub rdOpcion6_CheckedChange(Checked As Boolean)";
 //BA.debugLineNum = 268;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 269;BA.debugLine="csv.DrawColor(0)";
mostCurrent._csv.DrawColor((int) (0));
 //BA.debugLineNum = 270;BA.debugLine="cd.Initialize(Colors.ARGB(100,255,255,255), 0)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (255),(int) (255),(int) (255)),(int) (0));
 //BA.debugLineNum = 271;BA.debugLine="rec.Initialize(rdOpcion6.Left, rdOpcion6.Top, rd";
mostCurrent._rec.Initialize(mostCurrent._rdopcion6.getLeft(),mostCurrent._rdopcion6.getTop(),(int) (mostCurrent._rdopcion6.getWidth()+mostCurrent._rdopcion6.getLeft()),(int) (mostCurrent._rdopcion6.getHeight()+mostCurrent._rdopcion6.getTop()));
 //BA.debugLineNum = 272;BA.debugLine="csv.DrawDrawable(cd, rec)";
mostCurrent._csv.DrawDrawable((android.graphics.drawable.Drawable)(_cd.getObject()),(android.graphics.Rect)(mostCurrent._rec.getObject()));
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public static String  _terminareval() throws Exception{
int _totpreg = 0;
 //BA.debugLineNum = 1587;BA.debugLine="Sub TerminarEval";
 //BA.debugLineNum = 1590;BA.debugLine="pnlResultados.Visible = True";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1591;BA.debugLine="pnlResultados.BringToFront";
mostCurrent._pnlresultados.BringToFront();
 //BA.debugLineNum = 1593;BA.debugLine="Dim totpreg As Int";
_totpreg = 0;
 //BA.debugLineNum = 1594;BA.debugLine="totpreg = 130";
_totpreg = (int) (130);
 //BA.debugLineNum = 1596;BA.debugLine="If valorind1 <> \"NS\" Then";
if ((mostCurrent._valorind1).equals("NS") == false) { 
 //BA.debugLineNum = 1597;BA.debugLine="valorind1  = Round2(((valorind1 * 100) / totpr";
mostCurrent._valorind1 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind1))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1598;BA.debugLine="valorcalidad = valorcalidad + valorind1";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind1)));
 }else {
 //BA.debugLineNum = 1600;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1602;BA.debugLine="If valorind2 <> \"NS\" Then";
if ((mostCurrent._valorind2).equals("NS") == false) { 
 //BA.debugLineNum = 1603;BA.debugLine="valorind2  = Round2(((valorind2 * 100) / totpr";
mostCurrent._valorind2 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind2))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1604;BA.debugLine="valorcalidad = valorcalidad + valorind2";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind2)));
 }else {
 //BA.debugLineNum = 1606;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1608;BA.debugLine="If valorind3 <> \"NS\" Then";
if ((mostCurrent._valorind3).equals("NS") == false) { 
 //BA.debugLineNum = 1609;BA.debugLine="valorind3  = Round2(((valorind3 * 100) / totpr";
mostCurrent._valorind3 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind3))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1610;BA.debugLine="valorcalidad = valorcalidad + valorind3";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind3)));
 }else {
 //BA.debugLineNum = 1612;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1614;BA.debugLine="If valorind4 <> \"NS\" Then";
if ((mostCurrent._valorind4).equals("NS") == false) { 
 //BA.debugLineNum = 1615;BA.debugLine="valorind4  = Round2(((valorind4 * 100) / totpr";
mostCurrent._valorind4 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind4))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1616;BA.debugLine="valorcalidad = valorcalidad + valorind4";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind4)));
 }else {
 //BA.debugLineNum = 1618;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1620;BA.debugLine="If valorind5 <> \"NS\" Then";
if ((mostCurrent._valorind5).equals("NS") == false) { 
 //BA.debugLineNum = 1621;BA.debugLine="valorind5  = Round2(((valorind5 * 100) / totpr";
mostCurrent._valorind5 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind5))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1622;BA.debugLine="valorcalidad = valorcalidad + valorind5";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind5)));
 }else {
 //BA.debugLineNum = 1624;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1626;BA.debugLine="If valorind6 <> \"NS\" Then";
if ((mostCurrent._valorind6).equals("NS") == false) { 
 //BA.debugLineNum = 1627;BA.debugLine="valorind6  = Round2(((valorind6 * 100) / totpr";
mostCurrent._valorind6 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind6))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1628;BA.debugLine="valorcalidad = valorcalidad + valorind6";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind6)));
 }else {
 //BA.debugLineNum = 1630;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1632;BA.debugLine="If valorind7 <> \"NS\" Then";
if ((mostCurrent._valorind7).equals("NS") == false) { 
 //BA.debugLineNum = 1633;BA.debugLine="valorind7  = Round2(((valorind7 * 100) / totpr";
mostCurrent._valorind7 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind7))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1634;BA.debugLine="valorcalidad = valorcalidad + valorind7";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind7)));
 }else {
 //BA.debugLineNum = 1636;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1638;BA.debugLine="If valorind8 <> \"NS\" Then";
if ((mostCurrent._valorind8).equals("NS") == false) { 
 //BA.debugLineNum = 1639;BA.debugLine="valorind8  = Round2(((valorind8 * 100) / totpr";
mostCurrent._valorind8 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind8))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1640;BA.debugLine="valorcalidad = valorcalidad + valorind8";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind8)));
 }else {
 //BA.debugLineNum = 1642;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1644;BA.debugLine="If valorind9 <> \"NS\" Then";
if ((mostCurrent._valorind9).equals("NS") == false) { 
 //BA.debugLineNum = 1645;BA.debugLine="valorind9  = Round2(((valorind9 * 100) / totpr";
mostCurrent._valorind9 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind9))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1646;BA.debugLine="valorcalidad = valorcalidad + valorind9";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind9)));
 }else {
 //BA.debugLineNum = 1648;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1650;BA.debugLine="If valorind10 <> \"NS\" Then";
if ((mostCurrent._valorind10).equals("NS") == false) { 
 //BA.debugLineNum = 1651;BA.debugLine="valorind10  = Round2(((valorind10 * 100) / tot";
mostCurrent._valorind10 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind10))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1652;BA.debugLine="valorcalidad = valorcalidad + valorind10";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind10)));
 }else {
 //BA.debugLineNum = 1654;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1656;BA.debugLine="If valorind11 <> \"NS\" Then";
if ((mostCurrent._valorind11).equals("NS") == false) { 
 //BA.debugLineNum = 1657;BA.debugLine="valorind11  = Round2(((valorind11 * 100) / tot";
mostCurrent._valorind11 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind11))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1658;BA.debugLine="valorcalidad = valorcalidad + valorind11";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind11)));
 }else {
 //BA.debugLineNum = 1660;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1662;BA.debugLine="If valorind12 <> \"NS\" Then";
if ((mostCurrent._valorind12).equals("NS") == false) { 
 //BA.debugLineNum = 1663;BA.debugLine="valorind12  = Round2(((valorind12 * 100) / tot";
mostCurrent._valorind12 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind12))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1664;BA.debugLine="valorcalidad = valorcalidad + valorind12";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind12)));
 }else {
 //BA.debugLineNum = 1666;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1669;BA.debugLine="If valorind13 <> \"NS\" Then";
if ((mostCurrent._valorind13).equals("NS") == false) { 
 //BA.debugLineNum = 1670;BA.debugLine="valorind13  = Round2(((valorind13 * 100) / tot";
mostCurrent._valorind13 = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2((((double)(Double.parseDouble(mostCurrent._valorind13))*100)/(double)_totpreg),(int) (1)));
 //BA.debugLineNum = 1671;BA.debugLine="valorcalidad = valorcalidad + valorind13";
_valorcalidad = (int) (_valorcalidad+(double)(Double.parseDouble(mostCurrent._valorind13)));
 }else {
 //BA.debugLineNum = 1673;BA.debugLine="valorNS = valorNS + 1";
_valorns = (int) (_valorns+1);
 };
 //BA.debugLineNum = 1678;BA.debugLine="If (valorNS * 100) / (totpreg / 10) > 50  Then";
if ((_valorns*100)/(double)(_totpreg/(double)10)>50) { 
 //BA.debugLineNum = 1679;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1680;BA.debugLine="Msgbox(\"Ha salteado muchas opciones en la encu";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ha salteado muchas opciones en la encuesta, el estado del hábitat no puede ser calculado con precisión"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1682;BA.debugLine="Msgbox(\"You have skipped too many questions, t";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You have skipped too many questions, the habitat status cannot be calculated accurately"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 1684;BA.debugLine="pnlResultados.Visible = False";
mostCurrent._pnlresultados.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 1685;BA.debugLine="Return True";
if (true) return BA.ObjectToString(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1688;BA.debugLine="valorNS = Round2((valorNS * 100) / (totpreg/10),";
_valorns = (int) (anywheresoftware.b4a.keywords.Common.Round2((_valorns*100)/(double)(_totpreg/(double)10),(int) (1)));
 //BA.debugLineNum = 1689;BA.debugLine="valorcalidad = Round2(valorcalidad,1)";
_valorcalidad = (int) (anywheresoftware.b4a.keywords.Common.Round2(_valorcalidad,(int) (1)));
 //BA.debugLineNum = 1693;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1694;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 1695;BA.debugLine="lblEstado.Text = \"Muy malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy malo"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 1697;BA.debugLine="lblEstado.Text = \"Malo\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Malo"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 1699;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 1701;BA.debugLine="lblEstado.Text = \"Bueno\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bueno"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 1703;BA.debugLine="lblEstado.Text = \"Muy bueno!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Muy bueno!"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1706;BA.debugLine="If valorcalidad < 20 Then";
if (_valorcalidad<20) { 
 //BA.debugLineNum = 1707;BA.debugLine="lblEstado.Text = \"Very bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very bad"));
 }else if(_valorcalidad>=20 && _valorcalidad<40) { 
 //BA.debugLineNum = 1709;BA.debugLine="lblEstado.Text = \"Bad\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Bad"));
 }else if(_valorcalidad>=40 && _valorcalidad<60) { 
 //BA.debugLineNum = 1711;BA.debugLine="lblEstado.Text = \"Regular\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Regular"));
 }else if(_valorcalidad>=60 && _valorcalidad<80) { 
 //BA.debugLineNum = 1713;BA.debugLine="lblEstado.Text = \"Good\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Good"));
 }else if(_valorcalidad>=80) { 
 //BA.debugLineNum = 1715;BA.debugLine="lblEstado.Text = \"Very good!\"";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Very good!"));
 };
 };
 //BA.debugLineNum = 1720;BA.debugLine="Gauge1.SetRanges(Array(Gauge1.CreateRange(0, 20,";
mostCurrent._gauge1._setranges /*String*/ (anywheresoftware.b4a.keywords.Common.ArrayToList(new Object[]{(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (0),(float) (20),mostCurrent._xui.Color_DarkGray)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (20),(float) (40),mostCurrent._xui.Color_Red)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (40),(float) (60),mostCurrent._xui.Color_Yellow)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (60),(float) (80),mostCurrent._xui.Color_Green)),(Object)(mostCurrent._gauge1._createrange /*ilpla.appear.gauge._gaugerange*/ ((float) (80),(float) (100),mostCurrent._xui.Color_Blue))}));
 //BA.debugLineNum = 1721;BA.debugLine="Gauge1.CurrentValue = 0";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (0));
 //BA.debugLineNum = 1722;BA.debugLine="Gauge1.CurrentValue = valorcalidad";
mostCurrent._gauge1._setcurrentvalue /*float*/ ((float) (_valorcalidad));
 //BA.debugLineNum = 1725;BA.debugLine="End Sub";
return "";
}
public static String  _tipopreguntachk(int _cantidadopciones,boolean _imagenvisible) throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _chk = null;
 //BA.debugLineNum = 373;BA.debugLine="Sub TipoPreguntaChk (cantidadopciones As Int, imag";
 //BA.debugLineNum = 374;BA.debugLine="pnlChecks.Visible = True";
mostCurrent._pnlchecks.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 375;BA.debugLine="pnlOpciones.Visible = False";
mostCurrent._pnlopciones.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 376;BA.debugLine="chkOpcion1.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 377;BA.debugLine="chkOpcion2.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 378;BA.debugLine="chkOpcion3.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 379;BA.debugLine="chkOpcion4.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 380;BA.debugLine="chkOpcion5.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 381;BA.debugLine="chkOpcion6.SetBackgroundImage(LoadBitmap(File.Di";
mostCurrent._chkopcion6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"blank.gif").getObject()));
 //BA.debugLineNum = 382;BA.debugLine="chkOpcion1.Text = \"\"";
mostCurrent._chkopcion1.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 383;BA.debugLine="chkOpcion2.Text = \"\"";
mostCurrent._chkopcion2.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 384;BA.debugLine="chkOpcion3.Text = \"\"";
mostCurrent._chkopcion3.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 385;BA.debugLine="chkOpcion4.Text = \"\"";
mostCurrent._chkopcion4.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 386;BA.debugLine="chkOpcion5.Text = \"\"";
mostCurrent._chkopcion5.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 387;BA.debugLine="chkOpcion6.Text = \"\"";
mostCurrent._chkopcion6.setText(BA.ObjectToCharSequence(""));
 //BA.debugLineNum = 388;BA.debugLine="Dim chk As ColorDrawable";
_chk = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 389;BA.debugLine="chk.initialize2(Colors.ARGB(0,0,0,0),5dip,0dip,C";
_chk.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 390;BA.debugLine="chkOpcion1.Background = chk";
mostCurrent._chkopcion1.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 391;BA.debugLine="chkOpcion2.Background = chk";
mostCurrent._chkopcion2.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 392;BA.debugLine="chkOpcion3.Background = chk";
mostCurrent._chkopcion3.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 393;BA.debugLine="chkOpcion4.Background = chk";
mostCurrent._chkopcion4.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 394;BA.debugLine="chkOpcion5.Background = chk";
mostCurrent._chkopcion5.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 395;BA.debugLine="chkOpcion6.Background = chk";
mostCurrent._chkopcion6.setBackground((android.graphics.drawable.Drawable)(_chk.getObject()));
 //BA.debugLineNum = 396;BA.debugLine="chkOpcion1.width = rdOpcion1.Width";
mostCurrent._chkopcion1.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 397;BA.debugLine="chkOpcion2.width = rdOpcion1.Width";
mostCurrent._chkopcion2.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 398;BA.debugLine="chkOpcion3.width = rdOpcion1.Width";
mostCurrent._chkopcion3.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 399;BA.debugLine="chkOpcion4.width = rdOpcion1.Width";
mostCurrent._chkopcion4.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 400;BA.debugLine="chkOpcion5.width = rdOpcion1.Width";
mostCurrent._chkopcion5.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 401;BA.debugLine="chkOpcion6.width = rdOpcion1.Width";
mostCurrent._chkopcion6.setWidth(mostCurrent._rdopcion1.getWidth());
 //BA.debugLineNum = 402;BA.debugLine="chkOpcion1.Height = rdOpcion1.Height";
mostCurrent._chkopcion1.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 403;BA.debugLine="chkOpcion2.Height = rdOpcion1.Height";
mostCurrent._chkopcion2.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 404;BA.debugLine="chkOpcion3.Height = rdOpcion1.Height";
mostCurrent._chkopcion3.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 405;BA.debugLine="chkOpcion4.Height = rdOpcion1.Height";
mostCurrent._chkopcion4.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 406;BA.debugLine="chkOpcion5.Height = rdOpcion1.Height";
mostCurrent._chkopcion5.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 407;BA.debugLine="chkOpcion6.Height = rdOpcion1.Height";
mostCurrent._chkopcion6.setHeight(mostCurrent._rdopcion1.getHeight());
 //BA.debugLineNum = 409;BA.debugLine="chkOpcion1.Checked = False";
mostCurrent._chkopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 410;BA.debugLine="chkOpcion2.Checked = False";
mostCurrent._chkopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 411;BA.debugLine="chkOpcion3.Checked = False";
mostCurrent._chkopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 412;BA.debugLine="chkOpcion4.Checked = False";
mostCurrent._chkopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 413;BA.debugLine="chkOpcion5.Checked = False";
mostCurrent._chkopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 414;BA.debugLine="chkOpcion6.Checked = False";
mostCurrent._chkopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 415;BA.debugLine="chkOpcion1.Top = rdOpcion1.top";
mostCurrent._chkopcion1.setTop(mostCurrent._rdopcion1.getTop());
 //BA.debugLineNum = 416;BA.debugLine="chkOpcion2.Top = rdOpcion2.top";
mostCurrent._chkopcion2.setTop(mostCurrent._rdopcion2.getTop());
 //BA.debugLineNum = 417;BA.debugLine="chkOpcion3.Top = rdOpcion3.top";
mostCurrent._chkopcion3.setTop(mostCurrent._rdopcion3.getTop());
 //BA.debugLineNum = 418;BA.debugLine="chkOpcion4.Top = rdOpcion4.top";
mostCurrent._chkopcion4.setTop(mostCurrent._rdopcion4.getTop());
 //BA.debugLineNum = 419;BA.debugLine="chkOpcion5.Top = rdOpcion5.top";
mostCurrent._chkopcion5.setTop(mostCurrent._rdopcion5.getTop());
 //BA.debugLineNum = 420;BA.debugLine="chkOpcion6.Top = rdOpcion6.top";
mostCurrent._chkopcion6.setTop(mostCurrent._rdopcion6.getTop());
 //BA.debugLineNum = 421;BA.debugLine="chkOpcion1.Left = rdOpcion1.left";
mostCurrent._chkopcion1.setLeft(mostCurrent._rdopcion1.getLeft());
 //BA.debugLineNum = 422;BA.debugLine="chkOpcion2.Left = rdOpcion2.left";
mostCurrent._chkopcion2.setLeft(mostCurrent._rdopcion2.getLeft());
 //BA.debugLineNum = 423;BA.debugLine="chkOpcion3.Left = rdOpcion3.left";
mostCurrent._chkopcion3.setLeft(mostCurrent._rdopcion3.getLeft());
 //BA.debugLineNum = 424;BA.debugLine="chkOpcion4.Left = rdOpcion4.left";
mostCurrent._chkopcion4.setLeft(mostCurrent._rdopcion4.getLeft());
 //BA.debugLineNum = 425;BA.debugLine="chkOpcion5.Left = rdOpcion5.left";
mostCurrent._chkopcion5.setLeft(mostCurrent._rdopcion5.getLeft());
 //BA.debugLineNum = 426;BA.debugLine="chkOpcion6.Left = rdOpcion6.left";
mostCurrent._chkopcion6.setLeft(mostCurrent._rdopcion6.getLeft());
 //BA.debugLineNum = 427;BA.debugLine="If cantidadopciones = 2 Then";
if (_cantidadopciones==2) { 
 //BA.debugLineNum = 428;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 429;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 430;BA.debugLine="chkOpcion3.Visible = False";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 431;BA.debugLine="chkOpcion4.Visible = False";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 432;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 433;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==3) { 
 //BA.debugLineNum = 435;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 436;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 437;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 438;BA.debugLine="chkOpcion4.Visible = False";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 439;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 440;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==4) { 
 //BA.debugLineNum = 442;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 443;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 444;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 445;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 446;BA.debugLine="chkOpcion5.Visible = False";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 447;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==5) { 
 //BA.debugLineNum = 449;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 450;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 451;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 452;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 453;BA.debugLine="chkOpcion5.Visible = True";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 454;BA.debugLine="chkOpcion6.Visible = False";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else if(_cantidadopciones==6) { 
 //BA.debugLineNum = 456;BA.debugLine="chkOpcion1.Visible = True";
mostCurrent._chkopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 457;BA.debugLine="chkOpcion2.Visible = True";
mostCurrent._chkopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 458;BA.debugLine="chkOpcion3.Visible = True";
mostCurrent._chkopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 459;BA.debugLine="chkOpcion4.Visible = True";
mostCurrent._chkopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 460;BA.debugLine="chkOpcion5.Visible = True";
mostCurrent._chkopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 461;BA.debugLine="chkOpcion6.Visible = True";
mostCurrent._chkopcion6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 463;BA.debugLine="End Sub";
return "";
}
public static String  _tipopreguntarad(int _cantidadopciones,boolean _imagenvisible) throws Exception{
 //BA.debugLineNum = 316;BA.debugLine="Sub TipoPreguntaRad (cantidadopciones As Int, imag";
 //BA.debugLineNum = 318;BA.debugLine="pnlChecks.Visible = False";
mostCurrent._pnlchecks.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 319;BA.debugLine="pnlOpciones.Visible = True";
mostCurrent._pnlopciones.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 322;BA.debugLine="rdOpcion1.Checked = False";
mostCurrent._rdopcion1.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 323;BA.debugLine="rdOpcion2.Checked = False";
mostCurrent._rdopcion2.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 324;BA.debugLine="rdOpcion3.Checked = False";
mostCurrent._rdopcion3.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 325;BA.debugLine="rdOpcion4.Checked = False";
mostCurrent._rdopcion4.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 326;BA.debugLine="rdOpcion5.Checked = False";
mostCurrent._rdopcion5.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 327;BA.debugLine="rdOpcion6.Checked = False";
mostCurrent._rdopcion6.setChecked(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 328;BA.debugLine="rdOpcion1.Visible = False";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 329;BA.debugLine="rdOpcion2.Visible = False";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 330;BA.debugLine="rdOpcion3.Visible = False";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 331;BA.debugLine="rdOpcion4.Visible = False";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 332;BA.debugLine="rdOpcion5.Visible = False";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 333;BA.debugLine="rdOpcion6.Visible = False";
mostCurrent._rdopcion6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 335;BA.debugLine="If imagenvisible = True Then";
if (_imagenvisible==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 336;BA.debugLine="btnRdOpcion1.Visible = True";
mostCurrent._btnrdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 338;BA.debugLine="btnRdOpcion1.Visible = False";
mostCurrent._btnrdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 342;BA.debugLine="If cantidadopciones = 2 Then";
if (_cantidadopciones==2) { 
 //BA.debugLineNum = 343;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 344;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==3) { 
 //BA.debugLineNum = 347;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 348;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 349;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==4) { 
 //BA.debugLineNum = 351;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 352;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 353;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 354;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==5) { 
 //BA.debugLineNum = 356;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 357;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 358;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 359;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 360;BA.debugLine="rdOpcion5.Visible = True";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_cantidadopciones==6) { 
 //BA.debugLineNum = 362;BA.debugLine="rdOpcion1.Visible = True";
mostCurrent._rdopcion1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 363;BA.debugLine="rdOpcion2.Visible = True";
mostCurrent._rdopcion2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 364;BA.debugLine="rdOpcion3.Visible = True";
mostCurrent._rdopcion3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 365;BA.debugLine="rdOpcion4.Visible = True";
mostCurrent._rdopcion4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 366;BA.debugLine="rdOpcion5.Visible = True";
mostCurrent._rdopcion5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 367;BA.debugLine="rdOpcion6.Visible = True";
mostCurrent._rdopcion6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 370;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 208;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 209;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 210;BA.debugLine="lblTituloResultados.Text = \"You have finished th";
mostCurrent._lbltituloresultados.setText(BA.ObjectToCharSequence("You have finished the questionnaire!"));
 //BA.debugLineNum = 211;BA.debugLine="btnCerrar.Text = \"Close\"";
mostCurrent._btncerrar.setText(BA.ObjectToCharSequence("Close"));
 //BA.debugLineNum = 212;BA.debugLine="Label2.Text = \"This site has a habitat quality o";
mostCurrent._label2.setText(BA.ObjectToCharSequence("This site has a habitat quality of: "));
 //BA.debugLineNum = 213;BA.debugLine="btnShare.Text = \"Share\"";
mostCurrent._btnshare.setText(BA.ObjectToCharSequence("Share"));
 //BA.debugLineNum = 214;BA.debugLine="btnTerminar.Text = \"Finish!\"";
mostCurrent._btnterminar.setText(BA.ObjectToCharSequence("Finish!"));
 //BA.debugLineNum = 215;BA.debugLine="lblPregunta1.Text = \"Land use\"";
mostCurrent._lblpregunta1.setText(BA.ObjectToCharSequence("Land use"));
 //BA.debugLineNum = 216;BA.debugLine="lblPregunta2.Text = \"Cattle\"";
mostCurrent._lblpregunta2.setText(BA.ObjectToCharSequence("Cattle"));
 //BA.debugLineNum = 217;BA.debugLine="lblPregunta3.Text = \"Vegetation\"";
mostCurrent._lblpregunta3.setText(BA.ObjectToCharSequence("Vegetation"));
 //BA.debugLineNum = 218;BA.debugLine="lblPregunta4.Text = \"Aquatic plants\"";
mostCurrent._lblpregunta4.setText(BA.ObjectToCharSequence("Aquatic plants"));
 //BA.debugLineNum = 219;BA.debugLine="lblPregunta5.Text = \"Water\"";
mostCurrent._lblpregunta5.setText(BA.ObjectToCharSequence("Water"));
 //BA.debugLineNum = 220;BA.debugLine="lblPregunta6.Text = \"Litter\"";
mostCurrent._lblpregunta6.setText(BA.ObjectToCharSequence("Litter"));
 //BA.debugLineNum = 221;BA.debugLine="lblPregunta7.Text = \"Walls and docks\"";
mostCurrent._lblpregunta7.setText(BA.ObjectToCharSequence("Walls and docks"));
 //BA.debugLineNum = 222;BA.debugLine="lblPregunta8.Text = \"Sediment\"";
mostCurrent._lblpregunta8.setText(BA.ObjectToCharSequence("Sediment"));
 //BA.debugLineNum = 223;BA.debugLine="lblPregunta9.Text = \"Debris\"";
mostCurrent._lblpregunta9.setText(BA.ObjectToCharSequence("Debris"));
 //BA.debugLineNum = 224;BA.debugLine="lblPregunta11.Text = \"Walls and docks\"";
mostCurrent._lblpregunta11.setText(BA.ObjectToCharSequence("Walls and docks"));
 //BA.debugLineNum = 225;BA.debugLine="langLocal = \"en-\"";
mostCurrent._langlocal = "en-";
 }else {
 //BA.debugLineNum = 227;BA.debugLine="langLocal = \"\"";
mostCurrent._langlocal = "";
 };
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _validarrespuesta() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _pregresultadoview = null;
anywheresoftware.b4a.objects.ImageViewWrapper _pregresultadoimg = null;
 //BA.debugLineNum = 768;BA.debugLine="Sub ValidarRespuesta";
 //BA.debugLineNum = 770;BA.debugLine="Dim pregResultadoView As Label";
_pregresultadoview = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 771;BA.debugLine="pregResultadoView.Initialize(\"\")";
_pregresultadoview.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 772;BA.debugLine="pregResultadoView.Color = Colors.White";
_pregresultadoview.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 774;BA.debugLine="If currentpregunta = 1 Then";
if (_currentpregunta==1) { 
 //BA.debugLineNum = 775;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 776;BA.debugLine="valorind1 = \"0\"";
mostCurrent._valorind1 = "0";
 //BA.debugLineNum = 777;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usoindustrial.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 778;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 779;BA.debugLine="pregResultadoView.Text = \"Las industrias puede";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las industrias pueden volcar contaminantes directamente al agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 781;BA.debugLine="pregResultadoView.Text = \"Industries could pou";
_pregresultadoview.setText(BA.ObjectToCharSequence("Industries could pour pollutants into the water"));
 };
 //BA.debugLineNum = 783;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 785;BA.debugLine="valorind1 = \"2\"";
mostCurrent._valorind1 = "2";
 //BA.debugLineNum = 786;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usourbano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 787;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 788;BA.debugLine="pregResultadoView.Text = \"Las áreas urbanas ge";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las áreas urbanas generan gran cantidad de contaminantes que ingresan al agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 790;BA.debugLine="pregResultadoView.Text = \"Urban areas generate";
_pregresultadoview.setText(BA.ObjectToCharSequence("Urban areas generate pollutants that enter the water"));
 };
 //BA.debugLineNum = 792;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 794;BA.debugLine="valorind1 = \"4\"";
mostCurrent._valorind1 = "4";
 //BA.debugLineNum = 795;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usouburbano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 796;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 797;BA.debugLine="pregResultadoView.Text = \"Las áreas suburbanas";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las áreas suburbanas generan algunos contaminantes que ingresan al agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 799;BA.debugLine="pregResultadoView.Text = \"Suburban areas gener";
_pregresultadoview.setText(BA.ObjectToCharSequence("Suburban areas generate some pollutants that enter the water"));
 };
 //BA.debugLineNum = 801;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 803;BA.debugLine="valorind1 = \"4\"";
mostCurrent._valorind1 = "4";
 //BA.debugLineNum = 804;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usoagricola.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 805;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 806;BA.debugLine="pregResultadoView.Text = \"Los fertilizantes y";
_pregresultadoview.setText(BA.ObjectToCharSequence("Los fertilizantes y pesticidas usados en la agricultura pueden afectar a los organismos acuáticos"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 808;BA.debugLine="pregResultadoView.Text = \"Fertilizers and pest";
_pregresultadoview.setText(BA.ObjectToCharSequence("Fertilizers and pesticides used in agriculture can affect the ecosystem"));
 };
 //BA.debugLineNum = 810;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 812;BA.debugLine="valorind1 = \"10\"";
mostCurrent._valorind1 = "10";
 //BA.debugLineNum = 813;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usonatural.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 814;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 815;BA.debugLine="pregResultadoView.Text = \"Las áreas naturales";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las áreas naturales suelen tener sus cuerpos de agua bien preservados"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 817;BA.debugLine="pregResultadoView.Text = \"Natural areas tend t";
_pregresultadoview.setText(BA.ObjectToCharSequence("Natural areas tend to have well preserved water bodies"));
 };
 //BA.debugLineNum = 819;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion6.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 821;BA.debugLine="valorind1 = \"NS\"";
mostCurrent._valorind1 = "NS";
 //BA.debugLineNum = 822;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 823;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el uso del suelo"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 825;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about the land use"));
 };
 //BA.debugLineNum = 827;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 828;BA.debugLine="miniPregunta1.SetBackgroundImage(Null)";
mostCurrent._minipregunta1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 830;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==2) { 
 //BA.debugLineNum = 833;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 834;BA.debugLine="valorind2 = \"0\"";
mostCurrent._valorind2 = "0";
 //BA.debugLineNum = 835;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-feedlot.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 836;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 837;BA.debugLine="pregResultadoView.Text = \"Los feedlot ganadero";
_pregresultadoview.setText(BA.ObjectToCharSequence("Los feedlot ganaderos vuelcan una gran cantidad de nutrientes en el agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 839;BA.debugLine="pregResultadoView.Text = \"Feedlots pour a grea";
_pregresultadoview.setText(BA.ObjectToCharSequence("Feedlots pour a great deal of pollutants into the water"));
 };
 //BA.debugLineNum = 841;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 843;BA.debugLine="valorind2 = \"2\"";
mostCurrent._valorind2 = "2";
 //BA.debugLineNum = 844;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadodisperso.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 845;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 846;BA.debugLine="pregResultadoView.Text = \"El ganado pisotea la";
_pregresultadoview.setText(BA.ObjectToCharSequence("El ganado pisotea las márgenes y, mediante sus deshechos, aumentan la cantidad de nutrientes en el agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 848;BA.debugLine="pregResultadoView.Text = \"Cattle stomps the ma";
_pregresultadoview.setText(BA.ObjectToCharSequence("Cattle stomps the margins, and provides the water with nutrients and pollutants"));
 };
 //BA.debugLineNum = 850;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 853;BA.debugLine="valorind2 = \"4\"";
mostCurrent._valorind2 = "4";
 //BA.debugLineNum = 854;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadopoco.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 855;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 856;BA.debugLine="pregResultadoView.Text = \"El ganado pisotea la";
_pregresultadoview.setText(BA.ObjectToCharSequence("El ganado pisotea las márgenes y, mediante sus deshechos, aumentan la cantidad de nutrientes en el agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 858;BA.debugLine="pregResultadoView.Text = \"Cattle stomps the ma";
_pregresultadoview.setText(BA.ObjectToCharSequence("Cattle stomps the margins, and provides the water with nutrients and pollutants"));
 };
 //BA.debugLineNum = 860;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 862;BA.debugLine="valorind2 = \"10\"";
mostCurrent._valorind2 = "10";
 //BA.debugLineNum = 863;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadonada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 864;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 865;BA.debugLine="pregResultadoView.Text = \"No hay ganado cerca";
_pregresultadoview.setText(BA.ObjectToCharSequence("No hay ganado cerca que pisotee las márgenes"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 867;BA.debugLine="pregResultadoView.Text = \"No cattle near the m";
_pregresultadoview.setText(BA.ObjectToCharSequence("No cattle near the margins"));
 };
 //BA.debugLineNum = 869;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 870;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else if(mostCurrent._rdopcion5.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 872;BA.debugLine="valorind2 = \"NS\"";
mostCurrent._valorind2 = "NS";
 //BA.debugLineNum = 873;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 874;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre ganado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 876;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about cattle"));
 };
 //BA.debugLineNum = 878;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 879;BA.debugLine="miniPregunta2.SetBackgroundImage(Null)";
mostCurrent._minipregunta2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 881;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==3) { 
 //BA.debugLineNum = 885;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 886;BA.debugLine="valorind3 = \"10\"";
mostCurrent._valorind3 = "10";
 //BA.debugLineNum = 887;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 888;BA.debugLine="pregResultadoView.Text = \"La variada vegetació";
_pregresultadoview.setText(BA.ObjectToCharSequence("La variada vegetación  suele indicar un buen estado del hábitat"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 890;BA.debugLine="pregResultadoView.Text = \"Varied vegetation te";
_pregresultadoview.setText(BA.ObjectToCharSequence("Varied vegetation tends to indicate a good habitat quality"));
 };
 //BA.debugLineNum = 893;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 894;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 896;BA.debugLine="valorind3 = \"5\"";
mostCurrent._valorind3 = "5";
 //BA.debugLineNum = 897;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 898;BA.debugLine="pregResultadoView.Text = \"La presencia de poca";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de poca vegetación suele indicar un hábitat degradado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 900;BA.debugLine="pregResultadoView.Text = \"Little vegetation te";
_pregresultadoview.setText(BA.ObjectToCharSequence("Little vegetation tends to indicate a degraded habitat quality"));
 };
 //BA.debugLineNum = 902;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionpoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 904;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 906;BA.debugLine="valorind3 = \"1\"";
mostCurrent._valorind3 = "1";
 //BA.debugLineNum = 907;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionnada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 908;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 909;BA.debugLine="pregResultadoView.Text = \"La ausencia de veget";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de vegetación suele indicar un hábitat degradado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 911;BA.debugLine="pregResultadoView.Text = \"The absence of veget";
_pregresultadoview.setText(BA.ObjectToCharSequence("The absence of vegetation tends to indicate a degraded habitat"));
 };
 //BA.debugLineNum = 914;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 916;BA.debugLine="valorind3 = \"NS\"";
mostCurrent._valorind3 = "NS";
 //BA.debugLineNum = 917;BA.debugLine="miniPregunta3.SetBackgroundImage(Null)";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 918;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 919;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre la vegetación"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 921;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about vegetation"));
 };
 //BA.debugLineNum = 924;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 926;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==4) { 
 //BA.debugLineNum = 930;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 931;BA.debugLine="valorind4 = \"10\"";
mostCurrent._valorind4 = "10";
 //BA.debugLineNum = 932;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 933;BA.debugLine="pregResultadoView.Text = \"La presencia de junc";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de juncos en la costa indica un muy buen hábitat"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 935;BA.debugLine="pregResultadoView.Text = \"The presence of reed";
_pregresultadoview.setText(BA.ObjectToCharSequence("The presence of reeds indicates a good habitat quality"));
 };
 //BA.debugLineNum = 938;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 940;BA.debugLine="valorind4 = \"5\"";
mostCurrent._valorind4 = "5";
 //BA.debugLineNum = 941;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticapocos.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 942;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 943;BA.debugLine="pregResultadoView.Text = \"La presencia de algu";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de algunos juncos en la costa indica un buen hábitat"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 945;BA.debugLine="pregResultadoView.Text = \"The presence of some";
_pregresultadoview.setText(BA.ObjectToCharSequence("The presence of some reeds indicates a good habitat quality"));
 };
 //BA.debugLineNum = 948;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 950;BA.debugLine="valorind4 = \"0\"";
mostCurrent._valorind4 = "0";
 //BA.debugLineNum = 951;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticanada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 952;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 953;BA.debugLine="pregResultadoView.Text = \"La ausencia de  junc";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de  juncos en la costa indica un mal hábitat"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 955;BA.debugLine="pregResultadoView.Text = \"The absence of reeds";
_pregresultadoview.setText(BA.ObjectToCharSequence("The absence of reeds indicates a degraded habitat quality"));
 };
 //BA.debugLineNum = 958;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 960;BA.debugLine="valorind4 = \"NS\"";
mostCurrent._valorind4 = "NS";
 //BA.debugLineNum = 961;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 962;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre las plantas acuáticas"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 964;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about aquatic vegetation"));
 };
 //BA.debugLineNum = 967;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 968;BA.debugLine="miniPregunta4.SetBackgroundImage(Null)";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 970;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==5) { 
 //BA.debugLineNum = 973;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 974;BA.debugLine="valorind5 = \"10\"";
mostCurrent._valorind5 = "10";
 //BA.debugLineNum = 975;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 976;BA.debugLine="pregResultadoView.Text = \"El color transparent";
_pregresultadoview.setText(BA.ObjectToCharSequence("El color transparente del agua en estas lagunas es normal"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 978;BA.debugLine="pregResultadoView.Text = \"Transparent color in";
_pregresultadoview.setText(BA.ObjectToCharSequence("Transparent color in this places is normal"));
 };
 //BA.debugLineNum = 980;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 //BA.debugLineNum = 981;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguabuena.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 983;BA.debugLine="valorind5 = \"10\"";
mostCurrent._valorind5 = "10";
 //BA.debugLineNum = 984;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 985;BA.debugLine="pregResultadoView.Text = \"El color marrón del";
_pregresultadoview.setText(BA.ObjectToCharSequence("El color marrón del agua en estas lagunas ser anormal"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 987;BA.debugLine="pregResultadoView.Text = \"Brown water is not n";
_pregresultadoview.setText(BA.ObjectToCharSequence("Brown water is not normal"));
 };
 //BA.debugLineNum = 990;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguamarron.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 991;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 993;BA.debugLine="valorind5 = \"2\"";
mostCurrent._valorind5 = "2";
 //BA.debugLineNum = 994;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 995;BA.debugLine="pregResultadoView.Text = \"El color oscuro del";
_pregresultadoview.setText(BA.ObjectToCharSequence("El color oscuro del agua puede indicar una contaminación fuerte"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 997;BA.debugLine="pregResultadoView.Text = \"Dark-colored water c";
_pregresultadoview.setText(BA.ObjectToCharSequence("Dark-colored water could indicate pollution"));
 };
 //BA.debugLineNum = 1000;BA.debugLine="miniPregunta5.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-aguaoscura.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1001;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1003;BA.debugLine="valorind5 = \"NS\"";
mostCurrent._valorind5 = "NS";
 //BA.debugLineNum = 1004;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1005;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el color del agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1007;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about the water color"));
 };
 //BA.debugLineNum = 1010;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1012;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==6) { 
 //BA.debugLineNum = 1015;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1016;BA.debugLine="valorind6 = \"10\"";
mostCurrent._valorind6 = "10";
 //BA.debugLineNum = 1017;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1018;BA.debugLine="pregResultadoView.Text = \"El agua tiene buen o";
_pregresultadoview.setText(BA.ObjectToCharSequence("El agua tiene buen olor"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1020;BA.debugLine="pregResultadoView.Text = \"The water has normal";
_pregresultadoview.setText(BA.ObjectToCharSequence("The water has normal smell"));
 };
 //BA.debugLineNum = 1022;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1024;BA.debugLine="valorind6 = \"0\"";
mostCurrent._valorind6 = "0";
 //BA.debugLineNum = 1025;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1026;BA.debugLine="pregResultadoView.Text = \"El agua con olor feo";
_pregresultadoview.setText(BA.ObjectToCharSequence("El agua con olor feo puede indicar una contaminación fuerte"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1028;BA.debugLine="pregResultadoView.Text = \"The water with stron";
_pregresultadoview.setText(BA.ObjectToCharSequence("The water with strong smell could indicate pollution"));
 };
 //BA.debugLineNum = 1030;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1032;BA.debugLine="valorind6 = \"NS\"";
mostCurrent._valorind6 = "NS";
 //BA.debugLineNum = 1033;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1034;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el olor del agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1036;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about the water's smell"));
 };
 //BA.debugLineNum = 1038;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1040;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==7) { 
 //BA.debugLineNum = 1043;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1044;BA.debugLine="valorind7 = \"10\"";
mostCurrent._valorind7 = "10";
 //BA.debugLineNum = 1045;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1046;BA.debugLine="pregResultadoView.Text = \"No hay basura!\"";
_pregresultadoview.setText(BA.ObjectToCharSequence("No hay basura!"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1048;BA.debugLine="pregResultadoView.Text = \"There is no litter!";
_pregresultadoview.setText(BA.ObjectToCharSequence("There is no litter! Great!"));
 };
 //BA.debugLineNum = 1050;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basuranada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1051;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1053;BA.debugLine="valorind7 = \"5\"";
mostCurrent._valorind7 = "5";
 //BA.debugLineNum = 1054;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basurapoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1055;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1056;BA.debugLine="pregResultadoView.Text = \"La basura contamina,";
_pregresultadoview.setText(BA.ObjectToCharSequence("La basura contamina, aunque sea en poca cantidad!"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1058;BA.debugLine="pregResultadoView.Text = \"Litter, even in litt";
_pregresultadoview.setText(BA.ObjectToCharSequence("Litter, even in little quantities, pollutes!"));
 };
 //BA.debugLineNum = 1060;BA.debugLine="pregResultadoView.Tag = \"M\"";
_pregresultadoview.setTag((Object)("M"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1062;BA.debugLine="valorind7 = \"0\"";
mostCurrent._valorind7 = "0";
 //BA.debugLineNum = 1063;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basuramucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1064;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1065;BA.debugLine="pregResultadoView.Text = \"La basura contamina!";
_pregresultadoview.setText(BA.ObjectToCharSequence("La basura contamina!"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1067;BA.debugLine="pregResultadoView.Text = \"Litter pollutes! Bad";
_pregresultadoview.setText(BA.ObjectToCharSequence("Litter pollutes! Bad litter!"));
 };
 //BA.debugLineNum = 1069;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1071;BA.debugLine="valorind7 = \"NS\"";
mostCurrent._valorind7 = "NS";
 //BA.debugLineNum = 1072;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1073;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre basura"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1075;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about litter!"));
 };
 //BA.debugLineNum = 1077;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 1078;BA.debugLine="miniPregunta6.SetBackgroundImage(Null)";
mostCurrent._minipregunta6.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 }else {
 //BA.debugLineNum = 1080;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==8) { 
 //BA.debugLineNum = 1084;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1085;BA.debugLine="valorind8 = \"10\"";
mostCurrent._valorind8 = "10";
 //BA.debugLineNum = 1086;BA.debugLine="If valorind4 = \"10\" Or valorind4 = \"5\" Then";
if ((mostCurrent._valorind4).equals("10") || (mostCurrent._valorind4).equals("5")) { 
 //BA.debugLineNum = 1087;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticavarios.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 1089;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticapocos.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 1091;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1092;BA.debugLine="pregResultadoView.Text = \"La abundancia de paj";
_pregresultadoview.setText(BA.ObjectToCharSequence("La abundancia de pajonales en la costa es muy buena"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1094;BA.debugLine="pregResultadoView.Text = \"The presence of bulr";
_pregresultadoview.setText(BA.ObjectToCharSequence("The presence of bulrush plants is good!"));
 };
 //BA.debugLineNum = 1097;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1099;BA.debugLine="valorind8 = \"5\"";
mostCurrent._valorind8 = "5";
 //BA.debugLineNum = 1100;BA.debugLine="If valorind4 = \"10\" Or valorind4 = \"5\" Then";
if ((mostCurrent._valorind4).equals("10") || (mostCurrent._valorind4).equals("5")) { 
 //BA.debugLineNum = 1101;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticavarios.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 1103;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticapocos.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 1105;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1106;BA.debugLine="pregResultadoView.Text = \"La presencia de pajo";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de pajonales en la costa es buena"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1108;BA.debugLine="pregResultadoView.Text = \"The presence of bulr";
_pregresultadoview.setText(BA.ObjectToCharSequence("The presence of bulrush plants is good!"));
 };
 //BA.debugLineNum = 1111;BA.debugLine="pregResultadoView.Tag = \"R\"";
_pregresultadoview.setTag((Object)("R"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1113;BA.debugLine="If valorind4 = \"0\" Or valorind4 = \"NS\" Then";
if ((mostCurrent._valorind4).equals("0") || (mostCurrent._valorind4).equals("NS")) { 
 //BA.debugLineNum = 1114;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticanada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else {
 //BA.debugLineNum = 1116;BA.debugLine="miniPregunta4.SetBackgroundImage(LoadBitmapSa";
mostCurrent._minipregunta4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegacuaticapocos.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 1118;BA.debugLine="valorind8 = \"0\"";
mostCurrent._valorind8 = "0";
 //BA.debugLineNum = 1119;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1120;BA.debugLine="pregResultadoView.Text = \"La ausencia de pajon";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de pajonales en la costa puede indicar un hábitat degradado"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1122;BA.debugLine="pregResultadoView.Text = \"The absence of bulru";
_pregresultadoview.setText(BA.ObjectToCharSequence("The absence of bulrush plants could indicate a degraded habitat"));
 };
 //BA.debugLineNum = 1125;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1127;BA.debugLine="valorind8 = \"NS\"";
mostCurrent._valorind8 = "NS";
 }else {
 //BA.debugLineNum = 1130;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==9) { 
 //BA.debugLineNum = 1133;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1134;BA.debugLine="valorind9 = \"10\"";
mostCurrent._valorind9 = "10";
 //BA.debugLineNum = 1135;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1136;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1137;BA.debugLine="pregResultadoView.Text = \"La presencia de much";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de muchos árboles en la costa es muy buena"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1139;BA.debugLine="pregResultadoView.Text = \"The presence of many";
_pregresultadoview.setText(BA.ObjectToCharSequence("The presence of many trees is good!"));
 };
 //BA.debugLineNum = 1142;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1144;BA.debugLine="valorind9 = \"5\"";
mostCurrent._valorind9 = "5";
 //BA.debugLineNum = 1145;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionpoca.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1146;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1147;BA.debugLine="pregResultadoView.Text = \"La presencia de algu";
_pregresultadoview.setText(BA.ObjectToCharSequence("La presencia de algunos árboles en la costa es muy buena "));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1149;BA.debugLine="pregResultadoView.Text = \"The presence of some";
_pregresultadoview.setText(BA.ObjectToCharSequence("The presence of some trees is good!"));
 };
 //BA.debugLineNum = 1152;BA.debugLine="pregResultadoView.Tag = \"B\"";
_pregresultadoview.setTag((Object)("B"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1154;BA.debugLine="valorind9 = \"0\"";
mostCurrent._valorind9 = "0";
 //BA.debugLineNum = 1155;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionnada.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1156;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1157;BA.debugLine="pregResultadoView.Text = \"La ausencia de árbol";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de árboles en la costa es generalmente mala"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1159;BA.debugLine="pregResultadoView.Text = \"The presence of some";
_pregresultadoview.setText(BA.ObjectToCharSequence("The presence of some trees could mean a degreaded habitat!"));
 };
 //BA.debugLineNum = 1162;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion4.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1164;BA.debugLine="valorind9 = \"NS\"";
mostCurrent._valorind9 = "NS";
 //BA.debugLineNum = 1165;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1166;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el estado del cauce"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1168;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about the plants"));
 };
 //BA.debugLineNum = 1171;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 //BA.debugLineNum = 1172;BA.debugLine="valorind9 = \"NS\"";
mostCurrent._valorind9 = "NS";
 }else {
 //BA.debugLineNum = 1174;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==10) { 
 //BA.debugLineNum = 1177;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1178;BA.debugLine="valorind10 = \"0\"";
mostCurrent._valorind10 = "0";
 //BA.debugLineNum = 1179;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta10.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-muralla.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1180;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1181;BA.debugLine="pregResultadoView.Text = \"Las murallas interru";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las murallas interrumpen el intercambio entre el agua y la vegetación de la costa"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1183;BA.debugLine="pregResultadoView.Text = \"Walls interrupt the";
_pregresultadoview.setText(BA.ObjectToCharSequence("Walls interrupt the exchange between the water and the coastal ecosystem"));
 };
 //BA.debugLineNum = 1186;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1188;BA.debugLine="valorind10 = \"10\"";
mostCurrent._valorind10 = "10";
 //BA.debugLineNum = 1189;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta10.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-murallano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1190;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1191;BA.debugLine="pregResultadoView.Text = \"Las ausencia de mura";
_pregresultadoview.setText(BA.ObjectToCharSequence("Las ausencia de murallas permite el intercambio entre el agua y la vegetación de la costa"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1193;BA.debugLine="pregResultadoView.Text = \"Absence of walls all";
_pregresultadoview.setText(BA.ObjectToCharSequence("Absence of walls allow the exchange between the water and the coastal ecosystem"));
 };
 //BA.debugLineNum = 1196;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1198;BA.debugLine="valorind10 = \"NS\"";
mostCurrent._valorind10 = "NS";
 //BA.debugLineNum = 1199;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1200;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre murallas"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1202;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about walls"));
 };
 //BA.debugLineNum = 1205;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else {
 //BA.debugLineNum = 1208;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==11) { 
 //BA.debugLineNum = 1211;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1212;BA.debugLine="valorind11 = \"0\"";
mostCurrent._valorind11 = "0";
 //BA.debugLineNum = 1213;BA.debugLine="miniPregunta11.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta11.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-muelles.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1214;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1215;BA.debugLine="pregResultadoView.Text = \"Los muelles implican";
_pregresultadoview.setText(BA.ObjectToCharSequence("Los muelles implican que hay barcos, cuyos combustibles contaminan el agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1217;BA.debugLine="pregResultadoView.Text = \"Docks mean boats. Bo";
_pregresultadoview.setText(BA.ObjectToCharSequence("Docks mean boats. Boats mean fuel and oil in the water!"));
 };
 //BA.debugLineNum = 1220;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1222;BA.debugLine="valorind11 = \"10\"";
mostCurrent._valorind11 = "10";
 //BA.debugLineNum = 1223;BA.debugLine="miniPregunta11.SetBackgroundImage(LoadBitmapSam";
mostCurrent._minipregunta11.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-muellesno.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1224;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1225;BA.debugLine="pregResultadoView.Text = \"La ausencia de muell";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de muelles sugiere que no hay barcos, cuyos combustibles contaminan el agua"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1227;BA.debugLine="pregResultadoView.Text = \"No docks usually mea";
_pregresultadoview.setText(BA.ObjectToCharSequence("No docks usually mean no boats, fuel or oil in the water"));
 };
 //BA.debugLineNum = 1230;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1232;BA.debugLine="valorind11 = \"NS\"";
mostCurrent._valorind11 = "NS";
 //BA.debugLineNum = 1233;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1234;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre los muelles"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1236;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about docks"));
 };
 //BA.debugLineNum = 1239;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1241;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==12) { 
 //BA.debugLineNum = 1244;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1245;BA.debugLine="valorind12 = \"10\"";
mostCurrent._valorind12 = "10";
 //BA.debugLineNum = 1246;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1247;BA.debugLine="pregResultadoView.Text = \"Los campings contrib";
_pregresultadoview.setText(BA.ObjectToCharSequence("Los campings contribuyen a la deforestación y degradación de la costa"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1249;BA.debugLine="pregResultadoView.Text = \"Camping sites may co";
_pregresultadoview.setText(BA.ObjectToCharSequence("Camping sites may contribute to deforestation or degradation of the habitat"));
 };
 //BA.debugLineNum = 1252;BA.debugLine="pregResultadoView.Tag = \"MM\"";
_pregresultadoview.setTag((Object)("MM"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1254;BA.debugLine="valorind12 = \"0\"";
mostCurrent._valorind12 = "0";
 //BA.debugLineNum = 1255;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1256;BA.debugLine="pregResultadoView.Text = \"La ausencia de campi";
_pregresultadoview.setText(BA.ObjectToCharSequence("La ausencia de campings contribuye a no deforestar la costa"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1258;BA.debugLine="pregResultadoView.Text = \"Camping sites may co";
_pregresultadoview.setText(BA.ObjectToCharSequence("Camping sites may contribute to deforestation or degradation of the habitat"));
 };
 //BA.debugLineNum = 1261;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1263;BA.debugLine="valorind12 = \"NS\"";
mostCurrent._valorind12 = "NS";
 //BA.debugLineNum = 1264;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1265;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre campings"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1267;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about campings"));
 };
 //BA.debugLineNum = 1270;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1272;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 }else if(_currentpregunta==13) { 
 //BA.debugLineNum = 1275;BA.debugLine="If rdOpcion1.Checked = True Then";
if (mostCurrent._rdopcion1.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1276;BA.debugLine="valorind13 = \"0\"";
mostCurrent._valorind13 = "0";
 //BA.debugLineNum = 1277;BA.debugLine="miniPregunta9.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta9.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-escombros.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1278;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1279;BA.debugLine="pregResultadoView.Text = \"El volcado de escomb";
_pregresultadoview.setText(BA.ObjectToCharSequence("El volcado de escombros destruye los hábitat para los microinvertebrados y algas del sedimento"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1281;BA.debugLine="pregResultadoView.Text = \"Large debris can des";
_pregresultadoview.setText(BA.ObjectToCharSequence("Large debris can destroy the habitat for macroinvertebrates and algae"));
 };
 //BA.debugLineNum = 1284;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion2.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1286;BA.debugLine="valorind13 = \"10\"";
mostCurrent._valorind13 = "10";
 //BA.debugLineNum = 1287;BA.debugLine="miniPregunta9.SetBackgroundImage(LoadBitmapSamp";
mostCurrent._minipregunta9.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-escombrosno.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 1288;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1289;BA.debugLine="pregResultadoView.Text = \"El volcado de escomb";
_pregresultadoview.setText(BA.ObjectToCharSequence("El volcado de escombros destruye los hábitat para los microinvertebrados y algas del sedimento"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1291;BA.debugLine="pregResultadoView.Text = \"Large debris can des";
_pregresultadoview.setText(BA.ObjectToCharSequence("Large debris can destroy the habitat for macroinvertebrates and algae"));
 };
 //BA.debugLineNum = 1294;BA.debugLine="pregResultadoView.Tag = \"MB\"";
_pregresultadoview.setTag((Object)("MB"));
 }else if(mostCurrent._rdopcion3.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1296;BA.debugLine="valorind13 = \"NS\"";
mostCurrent._valorind13 = "NS";
 //BA.debugLineNum = 1297;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1298;BA.debugLine="pregResultadoView.Text = \"No has ingresado inf";
_pregresultadoview.setText(BA.ObjectToCharSequence("No has ingresado información sobre el fondo del río"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1300;BA.debugLine="pregResultadoView.Text = \"You haven't entered";
_pregresultadoview.setText(BA.ObjectToCharSequence("You haven't entered information about the sediment"));
 };
 //BA.debugLineNum = 1303;BA.debugLine="pregResultadoView.Tag = \"NS\"";
_pregresultadoview.setTag((Object)("NS"));
 }else {
 //BA.debugLineNum = 1305;BA.debugLine="Return(\"no\")";
if (true) return ("no");
 };
 };
 //BA.debugLineNum = 1310;BA.debugLine="Dim pregResultadoImg As ImageView";
_pregresultadoimg = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 1311;BA.debugLine="pregResultadoImg.Initialize(\"\")";
_pregresultadoimg.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 1313;BA.debugLine="If pregResultadoView.Tag = \"MM\" Then";
if ((_pregresultadoview.getTag()).equals((Object)("MM"))) { 
 //BA.debugLineNum = 1314;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File.";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("M"))) { 
 //BA.debugLineNum = 1316;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File.";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaM.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("R"))) { 
 //BA.debugLineNum = 1318;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File.";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaR.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("B"))) { 
 //BA.debugLineNum = 1320;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File.";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("MB"))) { 
 //BA.debugLineNum = 1322;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File.";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaMB.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 }else if((_pregresultadoview.getTag()).equals((Object)("NS"))) { 
 //BA.debugLineNum = 1324;BA.debugLine="pregResultadoImg.Bitmap = LoadBitmapSample(File.";
_pregresultadoimg.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"vinetaNS.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 };
 //BA.debugLineNum = 1327;BA.debugLine="pregResultadoView.TextColor = Colors.Black";
_pregresultadoview.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 1328;BA.debugLine="scrResultados.Panel.AddView(pregResultadoImg, 10d";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_pregresultadoimg.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)),(int) (_currentpregunta*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)));
 //BA.debugLineNum = 1329;BA.debugLine="scrResultados.Panel.AddView(pregResultadoView, 50";
mostCurrent._scrresultados.getPanel().AddView((android.view.View)(_pregresultadoview.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)),(int) (_currentpregunta*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 1330;BA.debugLine="scrResultados.Panel.Height = 12 * 90dip";
mostCurrent._scrresultados.getPanel().setHeight((int) (12*anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (90))));
 //BA.debugLineNum = 1331;BA.debugLine="End Sub";
return "";
}
}
