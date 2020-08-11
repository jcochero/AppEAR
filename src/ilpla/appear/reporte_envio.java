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

public class reporte_envio extends Activity implements B4AActivity{
	public static reporte_envio mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.reporte_envio");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (reporte_envio).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.reporte_envio");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.reporte_envio", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (reporte_envio) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (reporte_envio) Resume **");
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
		return reporte_envio.class;
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
            BA.LogInfo("** Activity (reporte_envio) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (reporte_envio) Pause event (activity is not paused). **");
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
            reporte_envio mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (reporte_envio) Resume **");
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
public static com.spinter.uploadfilephp.UploadFilePhp _up1 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up2 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up3 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up4 = null;
public static com.spinter.uploadfilephp.UploadFilePhp _up5 = null;
public static anywheresoftware.b4a.objects.Timer _timerenvio = null;
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
public anywheresoftware.b4a.objects.ScrollViewWrapper _scrchecklist = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtnotas = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstrip1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontinuar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnenviar = null;
public static int _numfotosenviadas = 0;
public static String _tiporio = "";
public static String _puntosfotos = "";
public static String _puntosevals = "";
public static String _puntostotal = "";
public static String _numriollanura = "";
public static String _numriomontana = "";
public static String _numlaguna = "";
public static String _numestuario = "";
public static String _proyectoidenviar = "";
public anywheresoftware.b4a.objects.collections.List _files = null;
public anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfinalizado1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblyatienestodo = null;
public static String _foto1 = "";
public static String _foto2 = "";
public static String _foto3 = "";
public static String _foto4 = "";
public static String _foto5 = "";
public static int _totalfotos = 0;
public static boolean _foto1sent = false;
public static boolean _foto2sent = false;
public static boolean _foto3sent = false;
public static boolean _foto4sent = false;
public static boolean _foto5sent = false;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar2 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar3 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar4 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar5 = null;
public static int _fotosenviadas = 0;
public anywheresoftware.b4a.phone.Phone.PhoneWakeState _pw = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmperfil _frmperfil = null;
public ilpla.appear.utilidades _utilidades = null;
public ilpla.appear.register _register = null;
public ilpla.appear.aprender_memory _aprender_memory = null;
public ilpla.appear.aprender_ahorcado _aprender_ahorcado = null;
public ilpla.appear.starter _starter = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 81;BA.debugLine="Activity.LoadLayout(\"layReporte_Envio\")";
mostCurrent._activity.LoadLayout("layReporte_Envio",mostCurrent.activityBA);
 //BA.debugLineNum = 82;BA.debugLine="TabStrip1.LoadLayout(\"layReporte_Checklist\", \"Otr";
mostCurrent._tabstrip1.LoadLayout("layReporte_Checklist",BA.ObjectToCharSequence("Otros datos"));
 //BA.debugLineNum = 83;BA.debugLine="TabStrip1.LoadLayout(\"layReporte_Notas\", \"Notas\")";
mostCurrent._tabstrip1.LoadLayout("layReporte_Notas",BA.ObjectToCharSequence("Notas"));
 //BA.debugLineNum = 84;BA.debugLine="TabStrip1.LoadLayout(\"layReporte_Envio_Resumen\",";
mostCurrent._tabstrip1.LoadLayout("layReporte_Envio_Resumen",BA.ObjectToCharSequence("Enviar!"));
 //BA.debugLineNum = 88;BA.debugLine="scrChecklist.Panel.LoadLayout(\"layBingo\")";
mostCurrent._scrchecklist.getPanel().LoadLayout("layBingo",mostCurrent.activityBA);
 //BA.debugLineNum = 89;BA.debugLine="scrChecklist.Panel.Height = 90%y";
mostCurrent._scrchecklist.getPanel().setHeight(anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 91;BA.debugLine="ShowScrollbar(scrChecklist)";
_showscrollbar(mostCurrent._scrchecklist);
 //BA.debugLineNum = 94;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 95;BA.debugLine="chkAlgas.Text = \"Filamentous algae\"";
mostCurrent._chkalgas.setText(BA.ObjectToCharSequence("Filamentous algae"));
 //BA.debugLineNum = 96;BA.debugLine="chkAves.Text = \"Aquatic birds\"";
mostCurrent._chkaves.setText(BA.ObjectToCharSequence("Aquatic birds"));
 //BA.debugLineNum = 97;BA.debugLine="chkRanas.Text = \"Frogs\"";
mostCurrent._chkranas.setText(BA.ObjectToCharSequence("Frogs"));
 //BA.debugLineNum = 98;BA.debugLine="chkCaracoles.Text = \"Snails\"";
mostCurrent._chkcaracoles.setText(BA.ObjectToCharSequence("Snails"));
 //BA.debugLineNum = 99;BA.debugLine="chkLibelulas.Text = \"Dragonflies\"";
mostCurrent._chklibelulas.setText(BA.ObjectToCharSequence("Dragonflies"));
 //BA.debugLineNum = 100;BA.debugLine="ChkAnimales.Text = \"Large mammals\"";
mostCurrent._chkanimales.setText(BA.ObjectToCharSequence("Large mammals"));
 //BA.debugLineNum = 101;BA.debugLine="chkPeces.Text = \"Fish\"";
mostCurrent._chkpeces.setText(BA.ObjectToCharSequence("Fish"));
 //BA.debugLineNum = 102;BA.debugLine="chkCulebras.Text = \"Snakes\"";
mostCurrent._chkculebras.setText(BA.ObjectToCharSequence("Snakes"));
 //BA.debugLineNum = 103;BA.debugLine="chkTortugas.Text = \"Turtles\"";
mostCurrent._chktortugas.setText(BA.ObjectToCharSequence("Turtles"));
 //BA.debugLineNum = 104;BA.debugLine="txtNotas.Hint = \"Write your notes and comments h";
mostCurrent._txtnotas.setHint("Write your notes and comments here");
 };
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 113;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 109;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
public static String  _btncontinuar_click() throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Sub btnContinuar_Click";
 //BA.debugLineNum = 189;BA.debugLine="TabStrip1.ScrollTo(TabStrip1.CurrentPage + 1, Tru";
mostCurrent._tabstrip1.ScrollTo((int) (mostCurrent._tabstrip1.getCurrentPage()+1),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return "";
}
public static void  _btnenviar_click() throws Exception{
ResumableSub_btnEnviar_Click rsub = new ResumableSub_btnEnviar_Click(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_btnEnviar_Click extends BA.ResumableSub {
public ResumableSub_btnEnviar_Click(ilpla.appear.reporte_envio parent) {
this.parent = parent;
}
ilpla.appear.reporte_envio parent;
String _notastext = "";
String _bingo = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
int _result = 0;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 227;BA.debugLine="Dim notastext As String = txtNotas.Text";
_notastext = parent.mostCurrent._txtnotas.getText();
 //BA.debugLineNum = 228;BA.debugLine="Dim bingo As String";
_bingo = "";
 //BA.debugLineNum = 229;BA.debugLine="If chkAves.Checked = True Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent.mostCurrent._chkaves.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 230;BA.debugLine="bingo = bingo & \"aves;\"";
_bingo = _bingo+"aves;";
 if (true) break;
;
 //BA.debugLineNum = 232;BA.debugLine="If ChkAnimales.Checked = True Then";

case 4:
//if
this.state = 7;
if (parent.mostCurrent._chkanimales.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 233;BA.debugLine="bingo = bingo & \"animales;\"";
_bingo = _bingo+"animales;";
 if (true) break;
;
 //BA.debugLineNum = 235;BA.debugLine="If chkAlgas.Checked = True Then";

case 7:
//if
this.state = 10;
if (parent.mostCurrent._chkalgas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 236;BA.debugLine="bingo = bingo & \"algas;\"";
_bingo = _bingo+"algas;";
 if (true) break;
;
 //BA.debugLineNum = 238;BA.debugLine="If chkTortugas.Checked = True Then";

case 10:
//if
this.state = 13;
if (parent.mostCurrent._chktortugas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 12;
}if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 239;BA.debugLine="bingo = bingo & \"tortugas;\"";
_bingo = _bingo+"tortugas;";
 if (true) break;
;
 //BA.debugLineNum = 241;BA.debugLine="If chkRanas.Checked = True Then";

case 13:
//if
this.state = 16;
if (parent.mostCurrent._chkranas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 15;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 242;BA.debugLine="bingo = bingo & \"ranas;\"";
_bingo = _bingo+"ranas;";
 if (true) break;
;
 //BA.debugLineNum = 244;BA.debugLine="If chkPeces.Checked = True Then";

case 16:
//if
this.state = 19;
if (parent.mostCurrent._chkpeces.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 245;BA.debugLine="bingo = bingo & \"peces;\"";
_bingo = _bingo+"peces;";
 if (true) break;
;
 //BA.debugLineNum = 247;BA.debugLine="If chkCulebras.Checked = True Then";

case 19:
//if
this.state = 22;
if (parent.mostCurrent._chkculebras.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 21;
}if (true) break;

case 21:
//C
this.state = 22;
 //BA.debugLineNum = 248;BA.debugLine="bingo = bingo & \"culebras;\"";
_bingo = _bingo+"culebras;";
 if (true) break;
;
 //BA.debugLineNum = 250;BA.debugLine="If chkLibelulas.Checked = True Then";

case 22:
//if
this.state = 25;
if (parent.mostCurrent._chklibelulas.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 24;
}if (true) break;

case 24:
//C
this.state = 25;
 //BA.debugLineNum = 251;BA.debugLine="bingo = bingo & \"libelulas;\"";
_bingo = _bingo+"libelulas;";
 if (true) break;
;
 //BA.debugLineNum = 253;BA.debugLine="If chkCaracoles.Checked = True Then";

case 25:
//if
this.state = 28;
if (parent.mostCurrent._chkcaracoles.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 27;
}if (true) break;

case 27:
//C
this.state = 28;
 //BA.debugLineNum = 254;BA.debugLine="bingo = bingo & \"caracoles;\"";
_bingo = _bingo+"caracoles;";
 if (true) break;
;
 //BA.debugLineNum = 256;BA.debugLine="If chkMosquitos.Checked = True Then";

case 28:
//if
this.state = 31;
if (parent.mostCurrent._chkmosquitos.getChecked()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 30;
}if (true) break;

case 30:
//C
this.state = 31;
 //BA.debugLineNum = 257;BA.debugLine="bingo = bingo & \"mosquitos;\"";
_bingo = _bingo+"mosquitos;";
 if (true) break;

case 31:
//C
this.state = 32;
;
 //BA.debugLineNum = 262;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 263;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 264;BA.debugLine="Map1.Put(\"Id\", Form_Reporte.currentproject)";
_map1.Put((Object)("Id"),(Object)(parent.mostCurrent._form_reporte._currentproject /*String*/ ));
 //BA.debugLineNum = 266;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"not";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","notas",(Object)(_notastext),_map1);
 //BA.debugLineNum = 267;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"bin";
parent.mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,parent.mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","bingo",(Object)(_bingo),_map1);
 //BA.debugLineNum = 274;BA.debugLine="Label1.Visible= False";
parent.mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 275;BA.debugLine="Label2.Visible= False";
parent.mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 276;BA.debugLine="Label3.Visible= False";
parent.mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 277;BA.debugLine="Label4.Visible= False";
parent.mostCurrent._label4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 278;BA.debugLine="Label5.Visible= False";
parent.mostCurrent._label5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 279;BA.debugLine="lblFinalizado1.Text = \"Chequeando internet...\"";
parent.mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Chequeando internet..."));
 //BA.debugLineNum = 280;BA.debugLine="lblFinalizado1.TextSize = 16";
parent.mostCurrent._lblfinalizado1.setTextSize((float) (16));
 //BA.debugLineNum = 281;BA.debugLine="lblYaTienesTodo.Visible = False";
parent.mostCurrent._lblyatienestodo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 282;BA.debugLine="btnContinuar.Enabled = False";
parent.mostCurrent._btncontinuar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 285;BA.debugLine="Wait For(CheckInternet) Complete (Result As Int)";
anywheresoftware.b4a.keywords.Common.WaitFor("complete", processBA, this, (Object)(_checkinternet()));
this.state = 56;
return;
case 56:
//C
this.state = 32;
_result = (Integer) result[0];
;
 //BA.debugLineNum = 286;BA.debugLine="If Result = 0 Then";
if (true) break;

case 32:
//if
this.state = 41;
if (_result==0) { 
this.state = 34;
}if (true) break;

case 34:
//C
this.state = 35;
 //BA.debugLineNum = 287;BA.debugLine="If Main.lang = \"en\" Then";
if (true) break;

case 35:
//if
this.state = 40;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 37;
}else {
this.state = 39;
}if (true) break;

case 37:
//C
this.state = 40;
 //BA.debugLineNum = 288;BA.debugLine="Msgbox(\"There is no connection to the internet,";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There is no connection to the internet, try again when you have a connection"),BA.ObjectToCharSequence("No internet"),mostCurrent.activityBA);
 if (true) break;

case 39:
//C
this.state = 40;
 //BA.debugLineNum = 290;BA.debugLine="Msgbox(\"No hay conexión a internet, prueba cuan";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),mostCurrent.activityBA);
 if (true) break;

case 40:
//C
this.state = 41;
;
 //BA.debugLineNum = 292;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 293;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 if (true) break;
;
 //BA.debugLineNum = 296;BA.debugLine="If Main.modooffline = True Then";

case 41:
//if
this.state = 50;
if (parent.mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 43;
}if (true) break;

case 43:
//C
this.state = 44;
 //BA.debugLineNum = 297;BA.debugLine="If Main.lang = \"es\" Then";
if (true) break;

case 44:
//if
this.state = 49;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 46;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 48;
}if (true) break;

case 46:
//C
this.state = 49;
 //BA.debugLineNum = 298;BA.debugLine="ToastMessageShow(\"Estas en modo sin conexión, s";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Estas en modo sin conexión, se guardarán los datos para que los envíes después"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 48:
//C
this.state = 49;
 //BA.debugLineNum = 300;BA.debugLine="ToastMessageShow(\"You're working offline, the d";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You're working offline, the data will be saved so you can send it later"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 49:
//C
this.state = 50;
;
 //BA.debugLineNum = 302;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 303;BA.debugLine="Activity.RemoveAllViews";
parent.mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 304;BA.debugLine="Return";
if (true) return ;
 if (true) break;
;
 //BA.debugLineNum = 309;BA.debugLine="If Main.lang = \"es\" Then";

case 50:
//if
this.state = 55;
if ((parent.mostCurrent._main._lang /*String*/ ).equals("es")) { 
this.state = 52;
}else if((parent.mostCurrent._main._lang /*String*/ ).equals("en")) { 
this.state = 54;
}if (true) break;

case 52:
//C
this.state = 55;
 //BA.debugLineNum = 310;BA.debugLine="ProgressDialogShow2(\"Enviando proyecto, esto tar";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Enviando proyecto, esto tardará unos minutos"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 54:
//C
this.state = 55;
 //BA.debugLineNum = 312;BA.debugLine="ProgressDialogShow2(\"Sending data, this will tak";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Sending data, this will take a few minutes"),anywheresoftware.b4a.keywords.Common.False);
 if (true) break;

case 55:
//C
this.state = -1;
;
 //BA.debugLineNum = 316;BA.debugLine="CheckInternet";
_checkinternet();
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _complete(int _result) throws Exception{
}
public static String  _checkinternet() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 321;BA.debugLine="Sub CheckInternet";
 //BA.debugLineNum = 322;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 323;BA.debugLine="dd.url = \"http://www.app-ear.com.ar/connect3/conn";
_dd.url /*String*/  = "http://www.app-ear.com.ar/connect3/connecttest.php";
 //BA.debugLineNum = 324;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 325;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = reporte_envio.getObject();
 //BA.debugLineNum = 326;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 327;BA.debugLine="End Sub";
return "";
}
public static String  _enviardatos_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 481;BA.debugLine="Sub EnviarDatos_Complete(Job As HttpJob)";
 //BA.debugLineNum = 482;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("710092545","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 483;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 484;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 485;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 486;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 487;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 488;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 489;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 490;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 491;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 492;BA.debugLine="ToastMessageShow(\"Error en la carga de marcado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 494;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 496;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("MarcadorAgregado")) { 
 //BA.debugLineNum = 499;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 500;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 501;BA.debugLine="Dim serverID As String";
_serverid = "";
 //BA.debugLineNum = 502;BA.debugLine="serverID = nd.Get(\"serverId\")";
_serverid = BA.ObjectToString(_nd.Get((Object)("serverId")));
 //BA.debugLineNum = 505;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 506;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 507;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 508;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 509;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"s";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","serverId",(Object)(_serverid),_map1);
 //BA.debugLineNum = 510;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 511;BA.debugLine="ToastMessageShow(\"Datos enviados, enviando fot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Datos enviados, enviando fotos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 513;BA.debugLine="ToastMessageShow(\"Report sent, sending photos\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent, sending photos"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 517;BA.debugLine="EnviarFotos";
_enviarfotos();
 };
 }else {
 //BA.debugLineNum = 520;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710092583","envio datos not ok",0);
 //BA.debugLineNum = 521;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 522;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 524;BA.debugLine="Msgbox(\"There seems to be a problem with our se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 528;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 529;BA.debugLine="End Sub";
return "";
}
public static String  _enviarfotos() throws Exception{
String _usr = "";
String _pss = "";
 //BA.debugLineNum = 535;BA.debugLine="Sub EnviarFotos";
 //BA.debugLineNum = 538;BA.debugLine="If foto1 <> \"null\" Then";
if ((mostCurrent._foto1).equals("null") == false) { 
 //BA.debugLineNum = 539;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 540;BA.debugLine="Label1.Text = \"Foto 1\"";
mostCurrent._label1.setText(BA.ObjectToCharSequence("Foto 1"));
 //BA.debugLineNum = 541;BA.debugLine="Label1.Visible= True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 543;BA.debugLine="If foto2 <>  \"null\" Then";
if ((mostCurrent._foto2).equals("null") == false) { 
 //BA.debugLineNum = 544;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 545;BA.debugLine="Label2.Text = \"Foto 2\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("Foto 2"));
 //BA.debugLineNum = 546;BA.debugLine="Label2.Visible= True";
mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 548;BA.debugLine="If foto3 <>  \"null\" Then";
if ((mostCurrent._foto3).equals("null") == false) { 
 //BA.debugLineNum = 549;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 550;BA.debugLine="Label3.Text = \"Foto 3\"";
mostCurrent._label3.setText(BA.ObjectToCharSequence("Foto 3"));
 //BA.debugLineNum = 551;BA.debugLine="Label3.Visible= True";
mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 553;BA.debugLine="If foto4 <>  \"null\" Then";
if ((mostCurrent._foto4).equals("null") == false) { 
 //BA.debugLineNum = 554;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 555;BA.debugLine="Label4.Text = \"Foto 4\"";
mostCurrent._label4.setText(BA.ObjectToCharSequence("Foto 4"));
 //BA.debugLineNum = 556;BA.debugLine="Label4.Visible= True";
mostCurrent._label4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 558;BA.debugLine="If foto5 <>  \"null\" Then";
if ((mostCurrent._foto5).equals("null") == false) { 
 //BA.debugLineNum = 559;BA.debugLine="totalFotos = totalFotos + 1";
_totalfotos = (int) (_totalfotos+1);
 //BA.debugLineNum = 560;BA.debugLine="Label5.Text = \"Foto 5\"";
mostCurrent._label5.setText(BA.ObjectToCharSequence("Foto 5"));
 //BA.debugLineNum = 561;BA.debugLine="Label5.Visible= True";
mostCurrent._label5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 564;BA.debugLine="lblFinalizado1.Text = \"Enviando fotos...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando fotos..."));
 //BA.debugLineNum = 565;BA.debugLine="lblFinalizado1.TextSize = 16";
mostCurrent._lblfinalizado1.setTextSize((float) (16));
 //BA.debugLineNum = 568;BA.debugLine="TimerEnvio.Initialize(\"TimerEnvio\", 1000)";
_timerenvio.Initialize(processBA,"TimerEnvio",(long) (1000));
 //BA.debugLineNum = 569;BA.debugLine="TimerEnvio.Enabled = True";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 571;BA.debugLine="Dim usr As String";
_usr = "";
 //BA.debugLineNum = 572;BA.debugLine="Dim pss As String";
_pss = "";
 //BA.debugLineNum = 573;BA.debugLine="usr = \"\"";
_usr = "";
 //BA.debugLineNum = 574;BA.debugLine="pss = \"\"";
_pss = "";
 //BA.debugLineNum = 576;BA.debugLine="Up1.B4A_log=True";
_up1.B4A_log = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 579;BA.debugLine="Up1.Initialize(\"Up1\")";
_up1.Initialize(processBA,"Up1");
 //BA.debugLineNum = 584;BA.debugLine="If File.Exists(File.DirRootExternal & \"/AppEAR/\",";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/",mostCurrent._foto1+".jpg") && _foto1sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 585;BA.debugLine="Log(\"Enviando foto 1 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("710158130","Enviando foto 1 ",0);
 //BA.debugLineNum = 586;BA.debugLine="ProgressBar1.Visible = True";
mostCurrent._progressbar1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 587;BA.debugLine="Up1.doFileUpload(ProgressBar1,Null,File.DirRootE";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar1.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/"+mostCurrent._foto1+".jpg","http://www.app-ear.com.ar/connect3/upload_file.php?usr="+_usr+"&pss="+_pss);
 //BA.debugLineNum = 588;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 589;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 1...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 1..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 591;BA.debugLine="lblFinalizado1.Text = \"Uploading foto 1...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Uploading foto 1..."));
 };
 }else {
 //BA.debugLineNum = 594;BA.debugLine="Log(\"no foto 1\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710158139","no foto 1",0);
 };
 //BA.debugLineNum = 597;BA.debugLine="End Sub";
return "";
}
public static String  _enviarpuntos(String _tipo,String _cantidadfotos) throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 766;BA.debugLine="Sub EnviarPuntos(tipo As String, cantidadfotos As";
 //BA.debugLineNum = 767;BA.debugLine="PuntosFotos = Main.puntosnumfotos";
mostCurrent._puntosfotos = mostCurrent._main._puntosnumfotos /*String*/ ;
 //BA.debugLineNum = 768;BA.debugLine="PuntosEvals = Main.puntosnumevals";
mostCurrent._puntosevals = mostCurrent._main._puntosnumevals /*String*/ ;
 //BA.debugLineNum = 769;BA.debugLine="PuntosTotal = Main.puntostotales";
mostCurrent._puntostotal = mostCurrent._main._puntostotales /*String*/ ;
 //BA.debugLineNum = 770;BA.debugLine="numriollanura = Main.numriollanura";
mostCurrent._numriollanura = mostCurrent._main._numriollanura /*String*/ ;
 //BA.debugLineNum = 771;BA.debugLine="numriomontana = Main.numriomontana";
mostCurrent._numriomontana = mostCurrent._main._numriomontana /*String*/ ;
 //BA.debugLineNum = 772;BA.debugLine="numlaguna = Main.numlaguna";
mostCurrent._numlaguna = mostCurrent._main._numlaguna /*String*/ ;
 //BA.debugLineNum = 773;BA.debugLine="numestuario = Main.numestuario";
mostCurrent._numestuario = mostCurrent._main._numestuario /*String*/ ;
 //BA.debugLineNum = 776;BA.debugLine="If tipo = \"llanura\" Then";
if ((_tipo).equals("llanura")) { 
 //BA.debugLineNum = 777;BA.debugLine="numriollanura = numriollanura + 1";
mostCurrent._numriollanura = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numriollanura))+1);
 //BA.debugLineNum = 778;BA.debugLine="Main.numriollanura = numriollanura";
mostCurrent._main._numriollanura /*String*/  = mostCurrent._numriollanura;
 }else if((_tipo).equals("montana")) { 
 //BA.debugLineNum = 780;BA.debugLine="numriomontana = numriomontana + 1";
mostCurrent._numriomontana = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numriomontana))+1);
 //BA.debugLineNum = 781;BA.debugLine="Main.numriomontana = numriomontana";
mostCurrent._main._numriomontana /*String*/  = mostCurrent._numriomontana;
 }else if((_tipo).equals("laguna")) { 
 //BA.debugLineNum = 783;BA.debugLine="numlaguna = numlaguna + 1";
mostCurrent._numlaguna = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numlaguna))+1);
 //BA.debugLineNum = 784;BA.debugLine="Main.numlaguna = numlaguna";
mostCurrent._main._numlaguna /*String*/  = mostCurrent._numlaguna;
 }else if((_tipo).equals("estuario")) { 
 //BA.debugLineNum = 786;BA.debugLine="numestuario = numestuario + 1";
mostCurrent._numestuario = BA.NumberToString((double)(Double.parseDouble(mostCurrent._numestuario))+1);
 //BA.debugLineNum = 787;BA.debugLine="Main.numestuario = numestuario";
mostCurrent._main._numestuario /*String*/  = mostCurrent._numestuario;
 };
 //BA.debugLineNum = 792;BA.debugLine="PuntosEvals = PuntosEvals + 60";
mostCurrent._puntosevals = BA.NumberToString((double)(Double.parseDouble(mostCurrent._puntosevals))+60);
 //BA.debugLineNum = 793;BA.debugLine="PuntosFotos = PuntosFotos + (10 * cantidadfotos)";
mostCurrent._puntosfotos = BA.NumberToString((double)(Double.parseDouble(mostCurrent._puntosfotos))+(10*(double)(Double.parseDouble(_cantidadfotos))));
 //BA.debugLineNum = 794;BA.debugLine="PuntosTotal = PuntosEvals + PuntosFotos";
mostCurrent._puntostotal = BA.NumberToString((double)(Double.parseDouble(mostCurrent._puntosevals))+(double)(Double.parseDouble(mostCurrent._puntosfotos)));
 //BA.debugLineNum = 797;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 798;BA.debugLine="dd.url = \"http://www.app-ear.com.ar/connect3/recp";
_dd.url /*String*/  = "http://www.app-ear.com.ar/connect3/recpuntos.php?"+"UserID="+mostCurrent._main._struserid /*String*/ +"&"+"PuntosFotos="+mostCurrent._puntosfotos+"&"+"PuntosEvals="+mostCurrent._puntosevals+"&"+"PuntosTotal="+mostCurrent._puntostotal+"&"+"numriollanura="+mostCurrent._main._numriollanura /*String*/ +"&"+"numriomontana="+mostCurrent._main._numriomontana /*String*/ +"&"+"numlaguna="+mostCurrent._main._numlaguna /*String*/ +"&"+"numestuario="+mostCurrent._main._numestuario /*String*/ ;
 //BA.debugLineNum = 807;BA.debugLine="dd.EventName = \"envioPuntos\"";
_dd.EventName /*String*/  = "envioPuntos";
 //BA.debugLineNum = 808;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = reporte_envio.getObject();
 //BA.debugLineNum = 809;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 810;BA.debugLine="End Sub";
return "";
}
public static String  _enviodatos(String _proyectonumero) throws Exception{
String _username = "";
String _dateandtime = "";
String _nombresitio = "";
String _lat = "";
String _lng = "";
String _gpsdetect = "";
String _wifidetect = "";
String _mapadetect = "";
String _valorcalidad = "";
String _valorns = "";
String _valorind1 = "";
String _valorind2 = "";
String _valorind3 = "";
String _valorind4 = "";
String _valorind5 = "";
String _valorind6 = "";
String _valorind7 = "";
String _valorind8 = "";
String _valorind9 = "";
String _valorind10 = "";
String _valorind11 = "";
String _valorind12 = "";
String _valorind13 = "";
String _valorind14 = "";
String _valorind15 = "";
String _valorind16 = "";
String _valorind17 = "";
String _valorind18 = "";
String _valorind19 = "";
String _notas = "";
String _bingo = "";
String _terminado = "";
String _privado = "";
String _estadovalidacion = "";
String _deviceid = "";
anywheresoftware.b4a.objects.collections.Map _datosmap = null;
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 352;BA.debugLine="Sub EnvioDatos(proyectonumero As String)";
 //BA.debugLineNum = 354;BA.debugLine="proyectoIDenviar = proyectonumero";
mostCurrent._proyectoidenviar = _proyectonumero;
 //BA.debugLineNum = 356;BA.debugLine="Dim username,dateandtime,nombresitio,tiporio,lat,";
_username = "";
_dateandtime = "";
_nombresitio = "";
mostCurrent._tiporio = "";
_lat = "";
_lng = "";
_gpsdetect = "";
_wifidetect = "";
_mapadetect = "";
 //BA.debugLineNum = 357;BA.debugLine="Dim valorcalidad, valorNS, valorind1,valorind2,va";
_valorcalidad = "";
_valorns = "";
_valorind1 = "";
_valorind2 = "";
_valorind3 = "";
_valorind4 = "";
_valorind5 = "";
_valorind6 = "";
_valorind7 = "";
_valorind8 = "";
_valorind9 = "";
_valorind10 = "";
_valorind11 = "";
_valorind12 = "";
_valorind13 = "";
_valorind14 = "";
_valorind15 = "";
_valorind16 = "";
_valorind17 = "";
_valorind18 = "";
_valorind19 = "";
 //BA.debugLineNum = 358;BA.debugLine="Dim notas,bingo As String";
_notas = "";
_bingo = "";
 //BA.debugLineNum = 359;BA.debugLine="Dim terminado, privado,estadovalidacion, deviceID";
_terminado = "";
_privado = "";
_estadovalidacion = "";
_deviceid = "";
 //BA.debugLineNum = 361;BA.debugLine="Dim datosMap As Map";
_datosmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 362;BA.debugLine="datosMap.Initialize";
_datosmap.Initialize();
 //BA.debugLineNum = 363;BA.debugLine="datosMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_datosmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE Id=?",new String[]{_proyectonumero});
 //BA.debugLineNum = 365;BA.debugLine="If datosMap = Null Or datosMap.IsInitialized = Fa";
if (_datosmap== null || _datosmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 366;BA.debugLine="ToastMessageShow(\"Error cargando el análisis\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error cargando el análisis"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 367;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 368;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 370;BA.debugLine="username = datosMap.Get(\"usuario\")";
_username = BA.ObjectToString(_datosmap.Get((Object)("usuario")));
 //BA.debugLineNum = 371;BA.debugLine="dateandtime = datosMap.Get(\"georeferenceddate\")";
_dateandtime = BA.ObjectToString(_datosmap.Get((Object)("georeferenceddate")));
 //BA.debugLineNum = 372;BA.debugLine="nombresitio = datosMap.Get(\"nombresitio\")";
_nombresitio = BA.ObjectToString(_datosmap.Get((Object)("nombresitio")));
 //BA.debugLineNum = 373;BA.debugLine="tiporio = datosMap.Get(\"tiporio\")";
mostCurrent._tiporio = BA.ObjectToString(_datosmap.Get((Object)("tiporio")));
 //BA.debugLineNum = 374;BA.debugLine="lat = datosMap.Get(\"decimallatitude\")";
_lat = BA.ObjectToString(_datosmap.Get((Object)("decimallatitude")));
 //BA.debugLineNum = 375;BA.debugLine="lng = datosMap.Get(\"decimallongitude\")";
_lng = BA.ObjectToString(_datosmap.Get((Object)("decimallongitude")));
 //BA.debugLineNum = 376;BA.debugLine="gpsdetect = datosMap.Get(\"gpsdetect\")";
_gpsdetect = BA.ObjectToString(_datosmap.Get((Object)("gpsdetect")));
 //BA.debugLineNum = 377;BA.debugLine="wifidetect = datosMap.Get(\"wifidetect\")";
_wifidetect = BA.ObjectToString(_datosmap.Get((Object)("wifidetect")));
 //BA.debugLineNum = 378;BA.debugLine="mapadetect = datosMap.Get(\"mapadetect\")";
_mapadetect = BA.ObjectToString(_datosmap.Get((Object)("mapadetect")));
 //BA.debugLineNum = 379;BA.debugLine="valorcalidad = datosMap.Get(\"valorcalidad\")";
_valorcalidad = BA.ObjectToString(_datosmap.Get((Object)("valorcalidad")));
 //BA.debugLineNum = 380;BA.debugLine="valorNS = datosMap.Get(\"valorind20\")";
_valorns = BA.ObjectToString(_datosmap.Get((Object)("valorind20")));
 //BA.debugLineNum = 381;BA.debugLine="valorind1 = datosMap.Get(\"valorind1\")";
_valorind1 = BA.ObjectToString(_datosmap.Get((Object)("valorind1")));
 //BA.debugLineNum = 382;BA.debugLine="valorind2 = datosMap.Get(\"valorind2\")";
_valorind2 = BA.ObjectToString(_datosmap.Get((Object)("valorind2")));
 //BA.debugLineNum = 383;BA.debugLine="valorind3 = datosMap.Get(\"valorind3\")";
_valorind3 = BA.ObjectToString(_datosmap.Get((Object)("valorind3")));
 //BA.debugLineNum = 384;BA.debugLine="valorind4 = datosMap.Get(\"valorind4\")";
_valorind4 = BA.ObjectToString(_datosmap.Get((Object)("valorind4")));
 //BA.debugLineNum = 385;BA.debugLine="valorind5 = datosMap.Get(\"valorind5\")";
_valorind5 = BA.ObjectToString(_datosmap.Get((Object)("valorind5")));
 //BA.debugLineNum = 386;BA.debugLine="valorind6 = datosMap.Get(\"valorind6\")";
_valorind6 = BA.ObjectToString(_datosmap.Get((Object)("valorind6")));
 //BA.debugLineNum = 387;BA.debugLine="valorind7 = datosMap.Get(\"valorind7\")";
_valorind7 = BA.ObjectToString(_datosmap.Get((Object)("valorind7")));
 //BA.debugLineNum = 388;BA.debugLine="valorind8 = datosMap.Get(\"valorind8\")";
_valorind8 = BA.ObjectToString(_datosmap.Get((Object)("valorind8")));
 //BA.debugLineNum = 389;BA.debugLine="valorind9 = datosMap.Get(\"valorind9\")";
_valorind9 = BA.ObjectToString(_datosmap.Get((Object)("valorind9")));
 //BA.debugLineNum = 390;BA.debugLine="valorind10 = datosMap.Get(\"valorind10\")";
_valorind10 = BA.ObjectToString(_datosmap.Get((Object)("valorind10")));
 //BA.debugLineNum = 391;BA.debugLine="valorind11 = datosMap.Get(\"valorind11\")";
_valorind11 = BA.ObjectToString(_datosmap.Get((Object)("valorind11")));
 //BA.debugLineNum = 392;BA.debugLine="valorind12 = datosMap.Get(\"valorind12\")";
_valorind12 = BA.ObjectToString(_datosmap.Get((Object)("valorind12")));
 //BA.debugLineNum = 393;BA.debugLine="valorind13 = datosMap.Get(\"valorind13\")";
_valorind13 = BA.ObjectToString(_datosmap.Get((Object)("valorind13")));
 //BA.debugLineNum = 394;BA.debugLine="valorind14 = datosMap.Get(\"valorind14\")";
_valorind14 = BA.ObjectToString(_datosmap.Get((Object)("valorind14")));
 //BA.debugLineNum = 395;BA.debugLine="valorind15 = datosMap.Get(\"valorind15\")";
_valorind15 = BA.ObjectToString(_datosmap.Get((Object)("valorind15")));
 //BA.debugLineNum = 396;BA.debugLine="valorind16 = datosMap.Get(\"valorind16\")";
_valorind16 = BA.ObjectToString(_datosmap.Get((Object)("valorind16")));
 //BA.debugLineNum = 397;BA.debugLine="valorind17 = datosMap.Get(\"valorind17\")";
_valorind17 = BA.ObjectToString(_datosmap.Get((Object)("valorind17")));
 //BA.debugLineNum = 398;BA.debugLine="valorind18 = datosMap.Get(\"valorind18\")";
_valorind18 = BA.ObjectToString(_datosmap.Get((Object)("valorind18")));
 //BA.debugLineNum = 399;BA.debugLine="valorind19 = datosMap.Get(\"valorind19\")";
_valorind19 = BA.ObjectToString(_datosmap.Get((Object)("valorind19")));
 //BA.debugLineNum = 400;BA.debugLine="notas = datosMap.Get(\"notas\")";
_notas = BA.ObjectToString(_datosmap.Get((Object)("notas")));
 //BA.debugLineNum = 401;BA.debugLine="bingo = datosMap.Get(\"bingo\")";
_bingo = BA.ObjectToString(_datosmap.Get((Object)("bingo")));
 //BA.debugLineNum = 402;BA.debugLine="foto1 = datosMap.Get(\"foto1\")";
mostCurrent._foto1 = BA.ObjectToString(_datosmap.Get((Object)("foto1")));
 //BA.debugLineNum = 403;BA.debugLine="foto2 = datosMap.Get(\"foto2\")";
mostCurrent._foto2 = BA.ObjectToString(_datosmap.Get((Object)("foto2")));
 //BA.debugLineNum = 404;BA.debugLine="foto3 = datosMap.Get(\"foto3\")";
mostCurrent._foto3 = BA.ObjectToString(_datosmap.Get((Object)("foto3")));
 //BA.debugLineNum = 405;BA.debugLine="foto4 = datosMap.Get(\"foto4\")";
mostCurrent._foto4 = BA.ObjectToString(_datosmap.Get((Object)("foto4")));
 //BA.debugLineNum = 406;BA.debugLine="foto5 = datosMap.Get(\"foto5\")";
mostCurrent._foto5 = BA.ObjectToString(_datosmap.Get((Object)("foto5")));
 //BA.debugLineNum = 407;BA.debugLine="terminado = datosMap.Get(\"terminado\")";
_terminado = BA.ObjectToString(_datosmap.Get((Object)("terminado")));
 //BA.debugLineNum = 408;BA.debugLine="privado = datosMap.Get(\"privado\")";
_privado = BA.ObjectToString(_datosmap.Get((Object)("privado")));
 //BA.debugLineNum = 409;BA.debugLine="If privado = Null Or privado = \"null\" Then";
if (_privado== null || (_privado).equals("null")) { 
 //BA.debugLineNum = 410;BA.debugLine="privado = \"no\"";
_privado = "no";
 };
 //BA.debugLineNum = 412;BA.debugLine="estadovalidacion = datosMap.Get(\"estadovalidacio";
_estadovalidacion = BA.ObjectToString(_datosmap.Get((Object)("estadovalidacion")));
 //BA.debugLineNum = 413;BA.debugLine="If estadovalidacion = \"null\" Then";
if ((_estadovalidacion).equals("null")) { 
 //BA.debugLineNum = 414;BA.debugLine="estadovalidacion = \"No Verificado\"";
_estadovalidacion = "No Verificado";
 };
 //BA.debugLineNum = 416;BA.debugLine="deviceID = datosMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_datosmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 417;BA.debugLine="If deviceID = Null Or deviceID = \"\" Or deviceID";
if (_deviceid== null || (_deviceid).equals("") || (_deviceid).equals("null")) { 
 //BA.debugLineNum = 418;BA.debugLine="deviceID = utilidades.GetDeviceId";
_deviceid = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 438;BA.debugLine="Log(\"Comienza envio de datos\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710027094","Comienza envio de datos",0);
 //BA.debugLineNum = 439;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 440;BA.debugLine="dd.url = Main.serverPath & \"/connect3/addpuntomap";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect3/addpuntomapa.php?"+"username="+_username+"&"+"deviceID="+mostCurrent._main._deviceid /*String*/ +"&"+"dateandtime="+_dateandtime+"&"+"nombresitio="+_nombresitio+"&"+"lat="+_lat+"&"+"lng="+_lng+"&"+"tiporio="+mostCurrent._tiporio+"&"+"indice="+_valorcalidad+"&"+"precision="+_valorns+"&"+"valorind1="+_valorind1+"&"+"valorind2="+_valorind2+"&"+"valorind3="+_valorind3+"&"+"valorind4="+_valorind4+"&"+"valorind5="+_valorind5+"&"+"valorind6="+_valorind6+"&"+"valorind7="+_valorind7+"&"+"valorind8="+_valorind8+"&"+"valorind9="+_valorind9+"&"+"valorind10="+_valorind10+"&"+"valorind11="+_valorind11+"&"+"valorind12="+_valorind12+"&"+"valorind13="+_valorind13+"&"+"valorind14="+_valorind14+"&"+"valorind15="+_valorind15+"&"+"valorind16="+_valorind16+"&"+"valorind17="+_valorind17+"&"+"valorind18="+_valorind18+"&"+"valorind19="+_valorind19+"&"+"foto1path="+mostCurrent._foto1+"&"+"foto2path="+mostCurrent._foto2+"&"+"foto3path="+mostCurrent._foto3+"&"+"foto4path="+mostCurrent._foto4+"&"+"foto5path="+mostCurrent._foto5+"terminado="+_terminado+"&"+"verificado="+_estadovalidacion+"&"+"bingo="+_bingo+"&"+"notas="+_notas+"&"+"mapadetect="+_mapadetect+"&"+"wifidetect="+_wifidetect+"&"+"gpsdetect="+_gpsdetect;
 //BA.debugLineNum = 477;BA.debugLine="dd.EventName = \"EnviarDatos\"";
_dd.EventName /*String*/  = "EnviarDatos";
 //BA.debugLineNum = 478;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = reporte_envio.getObject();
 //BA.debugLineNum = 479;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 480;BA.debugLine="End Sub";
return "";
}
public static String  _enviopuntos_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 812;BA.debugLine="Sub envioPuntos_Complete(Job As HttpJob)";
 //BA.debugLineNum = 813;BA.debugLine="Log(\"Datos enviados : \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("710551297","Datos enviados : "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 814;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 815;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 816;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 817;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 818;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 819;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 820;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 821;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 822;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 823;BA.debugLine="ToastMessageShow(\"Error en la carga de marcado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error en la carga de marcadores"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 825;BA.debugLine="ToastMessageShow(\"Error loading markers\", True";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error loading markers"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 827;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("Puntos Cargados")) { 
 //BA.debugLineNum = 829;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 830;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 831;BA.debugLine="Map1.Put(\"Id\", proyectoIDenviar)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._proyectoidenviar));
 //BA.debugLineNum = 833;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("si"),_map1);
 //BA.debugLineNum = 834;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 835;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 838;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 839;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 840;BA.debugLine="Main.puntostotales = PuntosTotal";
mostCurrent._main._puntostotales /*String*/  = mostCurrent._puntostotal;
 //BA.debugLineNum = 842;BA.debugLine="frmFelicitaciones.numfotosenviadas = numfotosen";
mostCurrent._frmfelicitaciones._numfotosenviadas /*int*/  = _numfotosenviadas;
 //BA.debugLineNum = 843;BA.debugLine="frmFelicitaciones.tiporio = tiporio";
mostCurrent._frmfelicitaciones._tiporio /*String*/  = mostCurrent._tiporio;
 //BA.debugLineNum = 844;BA.debugLine="StartActivity(frmFelicitaciones)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmfelicitaciones.getObject()));
 };
 }else {
 //BA.debugLineNum = 849;BA.debugLine="Log(\"envio datos not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710551333","envio datos not ok",0);
 //BA.debugLineNum = 850;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 851;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 853;BA.debugLine="Msgbox(\"There seems to be a problem with our se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 857;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 858;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 19;BA.debugLine="Private chkAves As CheckBox";
mostCurrent._chkaves = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private ChkAnimales As CheckBox";
mostCurrent._chkanimales = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private chkAlgas As CheckBox";
mostCurrent._chkalgas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private chkTortugas As CheckBox";
mostCurrent._chktortugas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private chkRanas As CheckBox";
mostCurrent._chkranas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private chkPeces As CheckBox";
mostCurrent._chkpeces = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private chkCulebras As CheckBox";
mostCurrent._chkculebras = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private chkLibelulas As CheckBox";
mostCurrent._chklibelulas = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private chkCaracoles As CheckBox";
mostCurrent._chkcaracoles = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private chkMosquitos As CheckBox";
mostCurrent._chkmosquitos = new anywheresoftware.b4a.objects.CompoundButtonWrapper.CheckBoxWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private scrChecklist As ScrollView";
mostCurrent._scrchecklist = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private txtNotas As EditText";
mostCurrent._txtnotas = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private TabStrip1 As TabStrip";
mostCurrent._tabstrip1 = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 33;BA.debugLine="Private btnContinuar As Button";
mostCurrent._btncontinuar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private btnEnviar As Button";
mostCurrent._btnenviar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim numfotosenviadas As Int";
_numfotosenviadas = 0;
 //BA.debugLineNum = 38;BA.debugLine="Dim tiporio As String";
mostCurrent._tiporio = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim PuntosFotos As String";
mostCurrent._puntosfotos = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim PuntosEvals As String";
mostCurrent._puntosevals = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim PuntosTotal As String";
mostCurrent._puntostotal = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim numriollanura As String";
mostCurrent._numriollanura = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim numriomontana As String";
mostCurrent._numriomontana = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim numlaguna As String";
mostCurrent._numlaguna = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim numestuario As String";
mostCurrent._numestuario = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim proyectoIDenviar As String";
mostCurrent._proyectoidenviar = "";
 //BA.debugLineNum = 47;BA.debugLine="Dim files As List";
mostCurrent._files = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 51;BA.debugLine="Dim Timer1 As Timer";
mostCurrent._timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 52;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Private lblFinalizado1 As Label";
mostCurrent._lblfinalizado1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Private lblYaTienesTodo As Label";
mostCurrent._lblyatienestodo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim foto1, foto2, foto3, foto4,foto5 As String";
mostCurrent._foto1 = "";
mostCurrent._foto2 = "";
mostCurrent._foto3 = "";
mostCurrent._foto4 = "";
mostCurrent._foto5 = "";
 //BA.debugLineNum = 64;BA.debugLine="Private totalFotos As Int";
_totalfotos = 0;
 //BA.debugLineNum = 65;BA.debugLine="Private foto1Sent As Boolean = False";
_foto1sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 66;BA.debugLine="Private foto2Sent As Boolean = False";
_foto2sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 67;BA.debugLine="Private foto3Sent As Boolean = False";
_foto3sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 68;BA.debugLine="Private foto4Sent As Boolean = False";
_foto4sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 69;BA.debugLine="Private foto5Sent As Boolean = False";
_foto5sent = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 70;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Private ProgressBar2 As ProgressBar";
mostCurrent._progressbar2 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Private ProgressBar3 As ProgressBar";
mostCurrent._progressbar3 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Private ProgressBar4 As ProgressBar";
mostCurrent._progressbar4 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 74;BA.debugLine="Private ProgressBar5 As ProgressBar";
mostCurrent._progressbar5 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 75;BA.debugLine="Dim fotosEnviadas As Int";
_fotosenviadas = 0;
 //BA.debugLineNum = 76;BA.debugLine="Dim pw As PhoneWakeState";
mostCurrent._pw = new anywheresoftware.b4a.phone.Phone.PhoneWakeState();
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Up1 As UploadFilePhp";
_up1 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 10;BA.debugLine="Dim Up2 As UploadFilePhp";
_up2 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 11;BA.debugLine="Dim Up3 As UploadFilePhp";
_up3 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 12;BA.debugLine="Dim Up4 As UploadFilePhp";
_up4 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 13;BA.debugLine="Dim Up5 As UploadFilePhp";
_up5 = new com.spinter.uploadfilephp.UploadFilePhp();
 //BA.debugLineNum = 15;BA.debugLine="Private TimerEnvio As Timer";
_timerenvio = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _showscrollbar(anywheresoftware.b4a.objects.ScrollViewWrapper _scrollview1) throws Exception{
anywheresoftware.b4j.object.JavaObject _jo = null;
 //BA.debugLineNum = 209;BA.debugLine="Sub ShowScrollbar(ScrollView1 As ScrollView)";
 //BA.debugLineNum = 210;BA.debugLine="Dim jo = ScrollView1 As JavaObject";
_jo = new anywheresoftware.b4j.object.JavaObject();
_jo = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_scrollview1.getObject()));
 //BA.debugLineNum = 211;BA.debugLine="jo.RunMethod(\"setScrollBarFadeDuration\", Array As";
_jo.RunMethod("setScrollBarFadeDuration",new Object[]{(Object)(0)});
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _tabstrip1_pageselected(int _position) throws Exception{
 //BA.debugLineNum = 127;BA.debugLine="Sub TabStrip1_PageSelected (Position As Int)";
 //BA.debugLineNum = 128;BA.debugLine="If Position = 0 Then";
if (_position==0) { 
 //BA.debugLineNum = 129;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==1) { 
 //BA.debugLineNum = 132;BA.debugLine="btnEnviar.Visible = False";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 133;BA.debugLine="btnContinuar.Visible = True";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==2) { 
 //BA.debugLineNum = 135;BA.debugLine="btnContinuar.Visible = False";
mostCurrent._btncontinuar.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 137;BA.debugLine="Label1.Visible = False";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 138;BA.debugLine="Label2.Visible = False";
mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 139;BA.debugLine="Label3.Visible = False";
mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="Label4.Visible = False";
mostCurrent._label4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="Label5.Visible = False";
mostCurrent._label5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 142;BA.debugLine="lblFinalizado1.Visible = False";
mostCurrent._lblfinalizado1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 143;BA.debugLine="lblYaTienesTodo.Visible= False";
mostCurrent._lblyatienestodo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 144;BA.debugLine="Timer1.Initialize(\"Timer1\",250)";
mostCurrent._timer1.Initialize(processBA,"Timer1",(long) (250));
 //BA.debugLineNum = 145;BA.debugLine="Timer1.Enabled = True";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 147;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(ilpla.appear.httpjob _job) throws Exception{
 //BA.debugLineNum = 328;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 329;BA.debugLine="Log(\"Chequeo de internet: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("79961473","Chequeo de internet: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 330;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 332;BA.debugLine="Main.modooffline = False";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 333;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 335;BA.debugLine="EnvioDatos(Form_Reporte.currentproject)";
_enviodatos(mostCurrent._form_reporte._currentproject /*String*/ );
 }else {
 //BA.debugLineNum = 338;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 339;BA.debugLine="Msgbox(\"No hay conexión a internet, prueba cuan";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No hay conexión a internet, prueba cuando estés conectado!"),BA.ObjectToCharSequence("No hay internet"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 341;BA.debugLine="Msgbox(\"No internet connection, try again later";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No internet connection, try again later!"),BA.ObjectToCharSequence("No internet"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 343;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 344;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 345;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 346;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 347;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 349;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 150;BA.debugLine="If Label1.Visible = False Then";
if (mostCurrent._label1.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 151;BA.debugLine="Label1.Visible = True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 152;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 154;BA.debugLine="If Label1.Visible = True And Label2.Visible = Fal";
if (mostCurrent._label1.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._label2.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 155;BA.debugLine="Label2.Visible = True";
mostCurrent._label2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 156;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 158;BA.debugLine="If Label2.Visible = True And Label3.Visible = Fal";
if (mostCurrent._label2.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._label3.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 159;BA.debugLine="Label3.Visible = True";
mostCurrent._label3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 160;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 162;BA.debugLine="If Label3.Visible = True And Label4.Visible = Fal";
if (mostCurrent._label3.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._label4.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 163;BA.debugLine="Label4.Visible = True";
mostCurrent._label4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 164;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 166;BA.debugLine="If Label4.Visible = True And Label5.Visible = Fal";
if (mostCurrent._label4.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._label5.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 167;BA.debugLine="Label5.Visible = True";
mostCurrent._label5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 168;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 170;BA.debugLine="If Label5.Visible = True And lblFinalizado1.Visib";
if (mostCurrent._label5.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._lblfinalizado1.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 173;BA.debugLine="lblFinalizado1.Visible = True";
mostCurrent._lblfinalizado1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 174;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 176;BA.debugLine="If lblFinalizado1.Visible = True And lblYaTienesT";
if (mostCurrent._lblfinalizado1.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._lblyatienestodo.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 177;BA.debugLine="lblYaTienesTodo.Visible = True";
mostCurrent._lblyatienestodo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 178;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 180;BA.debugLine="If lblYaTienesTodo.Visible = True And btnEnviar.V";
if (mostCurrent._lblyatienestodo.getVisible()==anywheresoftware.b4a.keywords.Common.True && mostCurrent._btnenviar.getVisible()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 181;BA.debugLine="btnEnviar.Visible = True";
mostCurrent._btnenviar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 182;BA.debugLine="Timer1.Enabled = False";
mostCurrent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 185;BA.debugLine="End Sub";
return "";
}
public static String  _timerenvio_tick() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 600;BA.debugLine="Sub TimerEnvio_Tick";
 //BA.debugLineNum = 601;BA.debugLine="If fotosEnviadas = totalFotos Then";
if (_fotosenviadas==_totalfotos) { 
 //BA.debugLineNum = 602;BA.debugLine="Log(\"TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMEN";
anywheresoftware.b4a.keywords.Common.LogImpl("710223618","TODAS LAS FOTOS FUERON ENVIADAS CORRECTAMENTE",0);
 //BA.debugLineNum = 603;BA.debugLine="lblFinalizado1.Text = \"Fotos enviadas!\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Fotos enviadas!"));
 //BA.debugLineNum = 604;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 610;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 611;BA.debugLine="ToastMessageShow(\"Evaluación enviada\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Evaluación enviada"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 613;BA.debugLine="ToastMessageShow(\"Report sent\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Report sent"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 617;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 618;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 619;BA.debugLine="Map1.Put(\"Id\", Main.currentproject)";
_map1.Put((Object)("Id"),(Object)(mostCurrent._main._currentproject /*String*/ ));
 //BA.debugLineNum = 620;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto1sent",(Object)("si"),_map1);
 //BA.debugLineNum = 621;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto2sent",(Object)("si"),_map1);
 //BA.debugLineNum = 622;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto3sent",(Object)("si"),_map1);
 //BA.debugLineNum = 623;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto4sent",(Object)("si"),_map1);
 //BA.debugLineNum = 624;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"fo";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","foto5sent",(Object)("si"),_map1);
 //BA.debugLineNum = 626;BA.debugLine="lblFinalizado1.Text = \"Enviando puntos\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando puntos"));
 //BA.debugLineNum = 627;BA.debugLine="EnviarPuntos(tiporio, numfotosenviadas)";
_enviarpuntos(mostCurrent._tiporio,BA.NumberToString(_numfotosenviadas));
 //BA.debugLineNum = 629;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 633;BA.debugLine="End Sub";
return "";
}
public static String  _up1_sendfile(String _value) throws Exception{
 //BA.debugLineNum = 640;BA.debugLine="Sub Up1_sendFile (value As String)";
 //BA.debugLineNum = 641;BA.debugLine="Log(\"sendfile event:\" & value)";
anywheresoftware.b4a.keywords.Common.LogImpl("710354689","sendfile event:"+_value,0);
 //BA.debugLineNum = 642;BA.debugLine="If value = \"success\" Then";
if ((_value).equals("success")) { 
 //BA.debugLineNum = 644;BA.debugLine="If fotosEnviadas = 0 And totalFotos = 1 Then";
if (_fotosenviadas==0 && _totalfotos==1) { 
 //BA.debugLineNum = 645;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 646;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 648;BA.debugLine="If fotosEnviadas = 1 And totalFotos = 2 Then";
if (_fotosenviadas==1 && _totalfotos==2) { 
 //BA.debugLineNum = 649;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 650;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 652;BA.debugLine="If fotosEnviadas = 2 And totalFotos = 3 Then";
if (_fotosenviadas==2 && _totalfotos==3) { 
 //BA.debugLineNum = 653;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 654;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 656;BA.debugLine="If fotosEnviadas = 3 And totalFotos = 4 Then";
if (_fotosenviadas==3 && _totalfotos==4) { 
 //BA.debugLineNum = 657;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 658;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 660;BA.debugLine="If fotosEnviadas = 4 And totalFotos = 5 Then";
if (_fotosenviadas==4 && _totalfotos==5) { 
 //BA.debugLineNum = 661;BA.debugLine="fotosEnviadas = 5";
_fotosenviadas = (int) (5);
 //BA.debugLineNum = 662;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 666;BA.debugLine="If fotosEnviadas = 0 And totalFotos > 1 Then";
if (_fotosenviadas==0 && _totalfotos>1) { 
 //BA.debugLineNum = 667;BA.debugLine="fotosEnviadas = 1";
_fotosenviadas = (int) (1);
 //BA.debugLineNum = 668;BA.debugLine="If File.Exists(File.DirRootExternal & \"/AppEAR/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/",mostCurrent._foto2+".jpg") && _foto2sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 669;BA.debugLine="Log(\"Enviando foto 2 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354717","Enviando foto 2 ",0);
 //BA.debugLineNum = 670;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 2...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 2..."));
 //BA.debugLineNum = 671;BA.debugLine="ProgressBar2.Progress  = 0";
mostCurrent._progressbar2.setProgress((int) (0));
 //BA.debugLineNum = 672;BA.debugLine="Up1.doFileUpload(ProgressBar2,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar2.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/"+mostCurrent._foto2+".jpg","http://www.app-ear.com.ar/connect3/upload_file.php");
 }else {
 //BA.debugLineNum = 674;BA.debugLine="Log(\"no foto 2\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354722","no foto 2",0);
 };
 };
 //BA.debugLineNum = 677;BA.debugLine="If fotosEnviadas = 1 And totalFotos > 2 Then";
if (_fotosenviadas==1 && _totalfotos>2) { 
 //BA.debugLineNum = 678;BA.debugLine="fotosEnviadas = 2";
_fotosenviadas = (int) (2);
 //BA.debugLineNum = 679;BA.debugLine="If File.Exists(File.DirRootExternal & \"/AppEAR/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/",mostCurrent._foto3+".jpg") && _foto3sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 680;BA.debugLine="Log(\"Enviando foto 3 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354728","Enviando foto 3 ",0);
 //BA.debugLineNum = 681;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 3...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 3..."));
 //BA.debugLineNum = 682;BA.debugLine="ProgressBar3.Progress  = 0";
mostCurrent._progressbar3.setProgress((int) (0));
 //BA.debugLineNum = 683;BA.debugLine="Up1.doFileUpload(ProgressBar3,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar3.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/"+mostCurrent._foto3+".jpg","http://www.app-ear.com.ar/connect3/upload_file.php");
 }else {
 //BA.debugLineNum = 685;BA.debugLine="Log(\"no foto 3\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354733","no foto 3",0);
 };
 };
 //BA.debugLineNum = 688;BA.debugLine="If fotosEnviadas = 2 And totalFotos > 3 Then";
if (_fotosenviadas==2 && _totalfotos>3) { 
 //BA.debugLineNum = 689;BA.debugLine="fotosEnviadas = 3";
_fotosenviadas = (int) (3);
 //BA.debugLineNum = 690;BA.debugLine="If File.Exists(File.DirRootExternal & \"/AppEAR/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/",mostCurrent._foto4+".jpg") && _foto4sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 691;BA.debugLine="Log(\"Enviando foto 4 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354739","Enviando foto 4 ",0);
 //BA.debugLineNum = 692;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 4...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 4..."));
 //BA.debugLineNum = 693;BA.debugLine="ProgressBar4.Progress  = 0";
mostCurrent._progressbar4.setProgress((int) (0));
 //BA.debugLineNum = 694;BA.debugLine="Up1.doFileUpload(ProgressBar4,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar4.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/"+mostCurrent._foto4+".jpg","http://www.app-ear.com.ar/connect3/upload_file.php");
 }else {
 //BA.debugLineNum = 696;BA.debugLine="Log(\"no foto 4\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354744","no foto 4",0);
 };
 };
 //BA.debugLineNum = 699;BA.debugLine="If fotosEnviadas = 3 And totalFotos > 4 Then";
if (_fotosenviadas==3 && _totalfotos>4) { 
 //BA.debugLineNum = 700;BA.debugLine="fotosEnviadas = 4";
_fotosenviadas = (int) (4);
 //BA.debugLineNum = 701;BA.debugLine="If File.Exists(File.DirRootExternal & \"/AppEAR/";
if (anywheresoftware.b4a.keywords.Common.File.Exists(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/",mostCurrent._foto5+".jpg") && _foto5sent==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 702;BA.debugLine="Log(\"Enviando foto 5 \")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354750","Enviando foto 5 ",0);
 //BA.debugLineNum = 703;BA.debugLine="lblFinalizado1.Text = \"Enviando foto 5...\"";
mostCurrent._lblfinalizado1.setText(BA.ObjectToCharSequence("Enviando foto 5..."));
 //BA.debugLineNum = 704;BA.debugLine="ProgressBar5.Progress  = 0";
mostCurrent._progressbar5.setProgress((int) (0));
 //BA.debugLineNum = 705;BA.debugLine="Up1.doFileUpload(ProgressBar5,Null,File.DirRoo";
_up1.doFileUpload(processBA,(android.widget.ProgressBar)(mostCurrent._progressbar5.getObject()),(android.widget.TextView)(anywheresoftware.b4a.keywords.Common.Null),anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/"+mostCurrent._foto5+".jpg","http://www.app-ear.com.ar/connect3/upload_file.php");
 }else {
 //BA.debugLineNum = 707;BA.debugLine="Log(\"no foto 5\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354755","no foto 5",0);
 };
 };
 }else if((_value).equals("Error!")) { 
 //BA.debugLineNum = 728;BA.debugLine="Log(\"FOTO error\")";
anywheresoftware.b4a.keywords.Common.LogImpl("710354776","FOTO error",0);
 //BA.debugLineNum = 729;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 730;BA.debugLine="Msgbox(\"Ha habido un error en el envío. Revisa";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ha habido un error en el envío. Revisa tu conexión a Internet e intenta de nuevo desde 'Datos Anteriores'"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 732;BA.debugLine="Msgbox(\"Upload error. Check your connection and";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Upload error. Check your connection and try again from 'My profile'"),BA.ObjectToCharSequence("Oops!"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 734;BA.debugLine="Up1.UploadKill";
_up1.UploadKill(processBA);
 //BA.debugLineNum = 739;BA.debugLine="TimerEnvio.Enabled = False";
_timerenvio.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 740;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 741;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 742;BA.debugLine="Activity_Create(False)";
_activity_create(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 743;BA.debugLine="btnContinuar.Enabled = True";
mostCurrent._btncontinuar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 755;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusupload(String _value) throws Exception{
 //BA.debugLineNum = 636;BA.debugLine="Sub Up1_statusUpload (value As String)";
 //BA.debugLineNum = 638;BA.debugLine="End Sub";
return "";
}
public static String  _up1_statusvisible(boolean _onoff,String _value) throws Exception{
 //BA.debugLineNum = 756;BA.debugLine="Sub Up1_statusVISIBLE (onoff As Boolean,value As S";
 //BA.debugLineNum = 758;BA.debugLine="End Sub";
return "";
}
}
