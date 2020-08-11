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
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;
    public static boolean dontPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
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
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
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
            BA.LogInfo("** Activity (main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (main) Pause event (activity is not paused). **");
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
            main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (main) Resume **");
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
public static String _deviceid = "";
public static String _lang = "";
public static String _username = "";
public static String _pass = "";
public static boolean _modooffline = false;
public static boolean _forceoffline = false;
public static String _serverversion = "";
public static String _servernewstitulo = "";
public static String _servernewstext = "";
public static String _versionactual = "";
public static String _serverpath = "";
public static String _struserid = "";
public static String _strusername = "";
public static String _struserfullname = "";
public static String _struserlocation = "";
public static String _struseremail = "";
public static String _struserorg = "";
public static String _strusertipousuario = "";
public static String _strusergroup = "";
public static String _numfotosok = "";
public static String _numevalsok = "";
public static String _msjprivadouser = "";
public static boolean _msjprivadoleido = false;
public static String _userlang = "";
public static String _evalsok = "";
public static String _evalscorregidas = "";
public static String _evalsinvalidas = "";
public static String _evalssospechosas = "";
public static String _validaciontotal = "";
public static String _usercategoria = "";
public static String _puntostotales = "";
public static String _puntosnumfotos = "";
public static String _puntosnumevals = "";
public static String _numriollanura = "";
public static String _numriomontana = "";
public static String _numlaguna = "";
public static String _numestuario = "";
public static String _numshares = "";
public static String _latitud = "";
public static String _longitud = "";
public static String _dateandtime = "";
public static String _currentproject = "";
public static String _screenshotpath = "";
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoffline = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.sql.SQL.CursorWrapper _cursor1 = null;
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
public ilpla.appear.reporte_habitat_estuario _reporte_habitat_estuario = null;
public ilpla.appear.reporte_habitat_laguna _reporte_habitat_laguna = null;
public ilpla.appear.reporte_habitat_rio _reporte_habitat_rio = null;
public ilpla.appear.uploadfiles _uploadfiles = null;

public static boolean isAnyActivityVisible() {
    boolean vis = false;
vis = vis | (main.mostCurrent != null);
vis = vis | (frmperfil.mostCurrent != null);
vis = vis | (register.mostCurrent != null);
vis = vis | (aprender_memory.mostCurrent != null);
vis = vis | (aprender_ahorcado.mostCurrent != null);
vis = vis | (reporte_envio.mostCurrent != null);
vis = vis | (aprender_ambientes.mostCurrent != null);
vis = vis | (aprender_ciclo.mostCurrent != null);
vis = vis | (aprender_comunidades.mostCurrent != null);
vis = vis | (aprender_contaminacion.mostCurrent != null);
vis = vis | (aprender_factores.mostCurrent != null);
vis = vis | (aprender_muestreo.mostCurrent != null);
vis = vis | (aprender_trofica.mostCurrent != null);
vis = vis | (form_main.mostCurrent != null);
vis = vis | (form_reporte.mostCurrent != null);
vis = vis | (frmabout.mostCurrent != null);
vis = vis | (frmdatosanteriores.mostCurrent != null);
vis = vis | (frmeditprofile.mostCurrent != null);
vis = vis | (frmfelicitaciones.mostCurrent != null);
vis = vis | (frmlocalizacion.mostCurrent != null);
vis = vis | (frmlogin.mostCurrent != null);
vis = vis | (frmpoliticadatos.mostCurrent != null);
vis = vis | (inatcheck.mostCurrent != null);
vis = vis | (reporte_fotos.mostCurrent != null);
vis = vis | (reporte_habitat_estuario.mostCurrent != null);
vis = vis | (reporte_habitat_laguna.mostCurrent != null);
vis = vis | (reporte_habitat_rio.mostCurrent != null);
return vis;}
public static void  _activity_create(boolean _firsttime) throws Exception{
ResumableSub_Activity_Create rsub = new ResumableSub_Activity_Create(null,_firsttime);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Create extends BA.ResumableSub {
public ResumableSub_Activity_Create(ilpla.appear.main parent,boolean _firsttime) {
this.parent = parent;
this._firsttime = _firsttime;
}
ilpla.appear.main parent;
boolean _firsttime;
anywheresoftware.b4a.phone.Phone _p = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 121;BA.debugLine="Dim p As Phone";
_p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 122;BA.debugLine="p.SetScreenOrientation(1)";
_p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 123;BA.debugLine="If Starter.auth.CurrentUser.IsInitialized Then St";
if (true) break;

case 1:
//if
this.state = 6;
if (parent.mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .getCurrentUser().IsInitialized()) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
parent.mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .SignOutFromGoogle();
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 126;BA.debugLine="Sleep(500)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (500));
this.state = 13;
return;
case 13:
//C
this.state = 7;
;
 //BA.debugLineNum = 127;BA.debugLine="deviceID = Starter.UpdateFCMToken";
parent._deviceid = parent.mostCurrent._starter._updatefcmtoken /*String*/ ();
 //BA.debugLineNum = 130;BA.debugLine="BuscarConfiguracion";
_buscarconfiguracion();
 //BA.debugLineNum = 132;BA.debugLine="Activity.LoadLayout(\"layload\")";
parent.mostCurrent._activity.LoadLayout("layload",mostCurrent.activityBA);
 //BA.debugLineNum = 134;BA.debugLine="deviceID = utilidades.GetDeviceId";
parent._deviceid = parent.mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 136;BA.debugLine="If lang = \"es\" Then";
if (true) break;

case 7:
//if
this.state = 12;
if ((parent._lang).equals("es")) { 
this.state = 9;
}else if((parent._lang).equals("en")) { 
this.state = 11;
}if (true) break;

case 9:
//C
this.state = 12;
 //BA.debugLineNum = 137;BA.debugLine="lblEstado.Text = \"Comprobando conexión a interne";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 //BA.debugLineNum = 138;BA.debugLine="btnOffline.Text = \"Trabajar sin conexión\"";
parent.mostCurrent._btnoffline.setText(BA.ObjectToCharSequence("Trabajar sin conexión"));
 if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 140;BA.debugLine="lblEstado.Text = \"Checking the connection to the";
parent.mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Checking the connection to the internet"));
 //BA.debugLineNum = 141;BA.debugLine="btnOffline.Text = \"Work offline\"";
parent.mostCurrent._btnoffline.setText(BA.ObjectToCharSequence("Work offline"));
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 143;BA.debugLine="timer1.Initialize(\"Timer1\", 1000)";
parent._timer1.Initialize(processBA,"Timer1",(long) (1000));
 //BA.debugLineNum = 144;BA.debugLine="timer1.Enabled = True";
parent._timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static void  _activity_resume() throws Exception{
ResumableSub_Activity_Resume rsub = new ResumableSub_Activity_Resume(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Resume extends BA.ResumableSub {
public ResumableSub_Activity_Resume(ilpla.appear.main parent) {
this.parent = parent;
}
ilpla.appear.main parent;
String _permission = "";
boolean _result = false;
Object[] group1;
int index1;
int groupLen1;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 149;BA.debugLine="For Each Permission As String In Array(rp.PERMISS";
if (true) break;

case 1:
//for
this.state = 8;
group1 = new Object[]{(Object)(parent._rp.PERMISSION_ACCESS_FINE_LOCATION),(Object)(parent._rp.PERMISSION_CAMERA),(Object)(parent._rp.PERMISSION_WRITE_EXTERNAL_STORAGE)};
index1 = 0;
groupLen1 = group1.length;
this.state = 21;
if (true) break;

case 21:
//C
this.state = 8;
if (index1 < groupLen1) {
this.state = 3;
_permission = BA.ObjectToString(group1[index1]);}
if (true) break;

case 22:
//C
this.state = 21;
index1++;
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 150;BA.debugLine="Sleep(100)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (100));
this.state = 23;
return;
case 23:
//C
this.state = 4;
;
 //BA.debugLineNum = 151;BA.debugLine="rp.CheckAndRequest(Permission)";
parent._rp.CheckAndRequest(processBA,_permission);
 //BA.debugLineNum = 152;BA.debugLine="Wait For Activity_PermissionResult (Permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 24;
return;
case 24:
//C
this.state = 4;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 153;BA.debugLine="If Result = False Then";
if (true) break;

case 4:
//if
this.state = 7;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 154;BA.debugLine="ToastMessageShow(\"Para usar AppEAR, se necesita";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Para usar AppEAR, se necesitan permisos para usar la cámara, el GPS y de escritura"),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 155;BA.debugLine="Activity.Finish";
parent.mostCurrent._activity.Finish();
 //BA.debugLineNum = 156;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 22;
;
 if (true) break;
if (true) break;
;
 //BA.debugLineNum = 161;BA.debugLine="If File.ExternalWritable = True Then";

case 8:
//if
this.state = 13;
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()==anywheresoftware.b4a.keywords.Common.True) { 
this.state = 10;
}else {
this.state = 12;
}if (true) break;

case 10:
//C
this.state = 13;
 //BA.debugLineNum = 162;BA.debugLine="Starter.savedir = File.DirRootExternal";
parent.mostCurrent._starter._savedir /*String*/  = anywheresoftware.b4a.keywords.Common.File.getDirRootExternal();
 if (true) break;

case 12:
//C
this.state = 13;
 //BA.debugLineNum = 165;BA.debugLine="Starter.savedir = File.DirInternal";
parent.mostCurrent._starter._savedir /*String*/  = anywheresoftware.b4a.keywords.Common.File.getDirInternal();
 if (true) break;

case 13:
//C
this.state = 14;
;
 //BA.debugLineNum = 169;BA.debugLine="Starter.dbdir = DBUtils.CopyDBFromAssets(\"appearD";
parent.mostCurrent._starter._dbdir /*String*/  = parent.mostCurrent._dbutils._copydbfromassets /*String*/ (mostCurrent.activityBA,"appearDB.db");
 //BA.debugLineNum = 174;BA.debugLine="If File.Exists(Starter.savedir & \"/AppEAR/\", \"\")";
if (true) break;

case 14:
//if
this.state = 17;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._starter._savedir /*String*/ +"/AppEAR/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 16;
}if (true) break;

case 16:
//C
this.state = 17;
 //BA.debugLineNum = 175;BA.debugLine="File.MakeDir(Starter.savedir, \"AppEAR\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ ,"AppEAR");
 if (true) break;
;
 //BA.debugLineNum = 181;BA.debugLine="If File.Exists(Starter.savedir & \"/AppEAR/sent/\",";

case 17:
//if
this.state = 20;
if (anywheresoftware.b4a.keywords.Common.File.Exists(parent.mostCurrent._starter._savedir /*String*/ +"/AppEAR/sent/","")==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 19;
}if (true) break;

case 19:
//C
this.state = 20;
 //BA.debugLineNum = 182;BA.debugLine="File.MakeDir(Starter.savedir & \"/AppEAR/\", \"sent";
anywheresoftware.b4a.keywords.Common.File.MakeDir(parent.mostCurrent._starter._savedir /*String*/ +"/AppEAR/","sent");
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 186;BA.debugLine="UpdateInternalDB(\"userFullName\")";
_updateinternaldb("userFullName");
 //BA.debugLineNum = 187;BA.debugLine="UpdateInternalDB(\"usergroup\")";
_updateinternaldb("usergroup");
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _btnoffline_click() throws Exception{
 //BA.debugLineNum = 642;BA.debugLine="Sub btnOffline_Click";
 //BA.debugLineNum = 645;BA.debugLine="CallSubDelayed2(DownloadService, \"CancelDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"CancelDownload",(Object)("http://www.app-ear.com.ar/connect3/connecttest.php"));
 //BA.debugLineNum = 647;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 648;BA.debugLine="forceoffline = True";
_forceoffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 649;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 650;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 651;BA.debugLine="utilidades.Mensaje(\"Advertencia\", \"MsgIcon.png\",";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Advertencia","MsgIcon.png","Modo sin internet","Has seleccionado el modo sin conexión. AppEAR iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes.","OK","","",anywheresoftware.b4a.keywords.Common.True);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 653;BA.debugLine="utilidades.Mensaje(\"Warning\", \"MsgIcon.png\", \"Of";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Warning","MsgIcon.png","Offline mode","You have selected the offline mode. AppEAR will start with the last user that logged in, but won't be able to send data.","OK","","",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 656;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 657;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 //BA.debugLineNum = 660;BA.debugLine="End Sub";
return "";
}
public static String  _buscarconfiguracion() throws Exception{
anywheresoftware.b4a.objects.collections.Map _langmap = null;
anywheresoftware.b4a.objects.collections.Map _deviceidmap = null;
String _deviceactual = "";
anywheresoftware.b4a.objects.collections.Map _usermap = null;
 //BA.debugLineNum = 213;BA.debugLine="Sub BuscarConfiguracion";
 //BA.debugLineNum = 219;BA.debugLine="Dim langMap As Map";
_langmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 220;BA.debugLine="langMap.Initialize";
_langmap.Initialize();
 //BA.debugLineNum = 221;BA.debugLine="langMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_langmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT applang FROM appconfig WHERE configID = '1'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 222;BA.debugLine="If langMap = Null Or langMap.IsInitialized = Fals";
if (_langmap== null || _langmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 223;BA.debugLine="lang = \"es\"";
_lang = "es";
 }else {
 //BA.debugLineNum = 225;BA.debugLine="If langMap.Get(\"applang\") = Null Then";
if (_langmap.Get((Object)("applang"))== null) { 
 //BA.debugLineNum = 226;BA.debugLine="lang = \"es\"";
_lang = "es";
 }else {
 //BA.debugLineNum = 228;BA.debugLine="lang = langMap.Get(\"applang\")";
_lang = BA.ObjectToString(_langmap.Get((Object)("applang")));
 };
 };
 //BA.debugLineNum = 234;BA.debugLine="Dim deviceIdMap As Map";
_deviceidmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 235;BA.debugLine="deviceIdMap.Initialize";
_deviceidmap.Initialize();
 //BA.debugLineNum = 236;BA.debugLine="deviceIdMap = DBUtils.ExecuteMap(Starter.sqlDB, \"";
_deviceidmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT deviceID FROM appconfig WHERE configID = '1'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 237;BA.debugLine="If deviceIdMap = Null Or deviceIdMap.IsInitialize";
if (_deviceidmap== null || _deviceidmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 238;BA.debugLine="deviceID = \"noID\"";
_deviceid = "noID";
 }else {
 //BA.debugLineNum = 240;BA.debugLine="deviceID = deviceIdMap.Get(\"deviceID\")";
_deviceid = BA.ObjectToString(_deviceidmap.Get((Object)("deviceID")));
 //BA.debugLineNum = 241;BA.debugLine="Log(\"deviceID de DB:\" & deviceID)";
anywheresoftware.b4a.keywords.Common.LogImpl("748824348","deviceID de DB:"+_deviceid,0);
 //BA.debugLineNum = 242;BA.debugLine="Log(\"deviceID de codigo:\" & utilidades.GetDevice";
anywheresoftware.b4a.keywords.Common.LogImpl("748824349","deviceID de codigo:"+mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA),0);
 };
 //BA.debugLineNum = 246;BA.debugLine="Dim deviceactual As String";
_deviceactual = "";
 //BA.debugLineNum = 247;BA.debugLine="deviceactual = utilidades.GetDeviceId";
_deviceactual = mostCurrent._utilidades._getdeviceid /*String*/ (mostCurrent.activityBA);
 //BA.debugLineNum = 248;BA.debugLine="If deviceactual <> deviceID Then";
if ((_deviceactual).equals(_deviceid) == false) { 
 //BA.debugLineNum = 249;BA.debugLine="deviceID = deviceactual";
_deviceid = _deviceactual;
 };
 //BA.debugLineNum = 254;BA.debugLine="Dim userMap As Map";
_usermap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 255;BA.debugLine="userMap.Initialize";
_usermap.Initialize();
 //BA.debugLineNum = 256;BA.debugLine="userMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usermap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 257;BA.debugLine="If userMap = Null Or userMap.IsInitialized = Fals";
if (_usermap== null || _usermap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 258;BA.debugLine="username = \"None\"";
_username = "None";
 //BA.debugLineNum = 259;BA.debugLine="pass = \"None\"";
_pass = "None";
 }else {
 //BA.debugLineNum = 261;BA.debugLine="username = userMap.Get(\"username\")";
_username = BA.ObjectToString(_usermap.Get((Object)("username")));
 //BA.debugLineNum = 262;BA.debugLine="strUserName = username";
_strusername = _username;
 //BA.debugLineNum = 263;BA.debugLine="pass = userMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usermap.Get((Object)("pass")));
 //BA.debugLineNum = 264;BA.debugLine="strUserLocation = userMap.Get(\"userlocation\")";
_struserlocation = BA.ObjectToString(_usermap.Get((Object)("userlocation")));
 //BA.debugLineNum = 265;BA.debugLine="strUserFullName = userMap.Get(\"userfullname\")";
_struserfullname = BA.ObjectToString(_usermap.Get((Object)("userfullname")));
 //BA.debugLineNum = 266;BA.debugLine="strUserOrg = userMap.Get(\"userorg\")";
_struserorg = BA.ObjectToString(_usermap.Get((Object)("userorg")));
 //BA.debugLineNum = 267;BA.debugLine="strUserTipoUsuario = userMap.Get(\"usertipousuari";
_strusertipousuario = BA.ObjectToString(_usermap.Get((Object)("usertipousuario")));
 //BA.debugLineNum = 268;BA.debugLine="strUserGroup = userMap.Get(\"usergroup\")";
_strusergroup = BA.ObjectToString(_usermap.Get((Object)("usergroup")));
 //BA.debugLineNum = 269;BA.debugLine="puntostotales = userMap.Get(\"puntostotales\")";
_puntostotales = BA.ObjectToString(_usermap.Get((Object)("puntostotales")));
 //BA.debugLineNum = 270;BA.debugLine="numevalsok  = userMap.Get(\"puntostotales\")";
_numevalsok = BA.ObjectToString(_usermap.Get((Object)("puntostotales")));
 //BA.debugLineNum = 271;BA.debugLine="numriollanura = userMap.Get(\"puntostotales\")";
_numriollanura = BA.ObjectToString(_usermap.Get((Object)("puntostotales")));
 //BA.debugLineNum = 272;BA.debugLine="numriomontana = userMap.Get(\"puntostotales\")";
_numriomontana = BA.ObjectToString(_usermap.Get((Object)("puntostotales")));
 //BA.debugLineNum = 273;BA.debugLine="numlaguna= userMap.Get(\"puntostotales\")";
_numlaguna = BA.ObjectToString(_usermap.Get((Object)("puntostotales")));
 //BA.debugLineNum = 274;BA.debugLine="numestuario = userMap.Get(\"puntostotales\")";
_numestuario = BA.ObjectToString(_usermap.Get((Object)("puntostotales")));
 //BA.debugLineNum = 275;BA.debugLine="numshares = userMap.Get(\"puntostotales\")";
_numshares = BA.ObjectToString(_usermap.Get((Object)("puntostotales")));
 //BA.debugLineNum = 277;BA.debugLine="If puntostotales = \"null\" Or puntostotales = \"\"";
if ((_puntostotales).equals("null") || (_puntostotales).equals("")) { 
 //BA.debugLineNum = 278;BA.debugLine="puntostotales = 0";
_puntostotales = BA.NumberToString(0);
 };
 //BA.debugLineNum = 280;BA.debugLine="If numevalsok = \"null\" Or numevalsok = \"\" Then";
if ((_numevalsok).equals("null") || (_numevalsok).equals("")) { 
 //BA.debugLineNum = 281;BA.debugLine="numevalsok = 0";
_numevalsok = BA.NumberToString(0);
 };
 //BA.debugLineNum = 283;BA.debugLine="If numriollanura = \"null\" Or numriollanura = \"\"";
if ((_numriollanura).equals("null") || (_numriollanura).equals("")) { 
 //BA.debugLineNum = 284;BA.debugLine="numriollanura = 0";
_numriollanura = BA.NumberToString(0);
 };
 //BA.debugLineNum = 286;BA.debugLine="If numriomontana = \"null\" Or numriomontana = \"\"";
if ((_numriomontana).equals("null") || (_numriomontana).equals("")) { 
 //BA.debugLineNum = 287;BA.debugLine="numriomontana = 0";
_numriomontana = BA.NumberToString(0);
 };
 //BA.debugLineNum = 289;BA.debugLine="If numlaguna = \"null\" Or numlaguna = \"\" Then";
if ((_numlaguna).equals("null") || (_numlaguna).equals("")) { 
 //BA.debugLineNum = 290;BA.debugLine="numlaguna = 0";
_numlaguna = BA.NumberToString(0);
 };
 //BA.debugLineNum = 292;BA.debugLine="If numestuario = \"null\" Or numestuario = \"\" Then";
if ((_numestuario).equals("null") || (_numestuario).equals("")) { 
 //BA.debugLineNum = 293;BA.debugLine="numestuario = 0";
_numestuario = BA.NumberToString(0);
 };
 //BA.debugLineNum = 295;BA.debugLine="If numshares = \"null\" Or numshares = \"\" Then";
if ((_numshares).equals("null") || (_numshares).equals("")) { 
 //BA.debugLineNum = 296;BA.debugLine="numshares = 0";
_numshares = BA.NumberToString(0);
 };
 //BA.debugLineNum = 298;BA.debugLine="If evalsOK = \"null\"  Or evalsOK = \"\" Then";
if ((_evalsok).equals("null") || (_evalsok).equals("")) { 
 //BA.debugLineNum = 299;BA.debugLine="evalsOK = 0";
_evalsok = BA.NumberToString(0);
 };
 //BA.debugLineNum = 301;BA.debugLine="If evalsCorregidas = \"null\"  Or evalsCorregidas";
if ((_evalscorregidas).equals("null") || (_evalscorregidas).equals("")) { 
 //BA.debugLineNum = 302;BA.debugLine="evalsCorregidas = 0";
_evalscorregidas = BA.NumberToString(0);
 };
 //BA.debugLineNum = 304;BA.debugLine="If evalsInvalidas = \"null\" Or evalsInvalidas = \"";
if ((_evalsinvalidas).equals("null") || (_evalsinvalidas).equals("")) { 
 //BA.debugLineNum = 305;BA.debugLine="evalsInvalidas = 0";
_evalsinvalidas = BA.NumberToString(0);
 };
 //BA.debugLineNum = 307;BA.debugLine="If evalsSospechosas = \"null\" Or evalsSospechosas";
if ((_evalssospechosas).equals("null") || (_evalssospechosas).equals("")) { 
 //BA.debugLineNum = 308;BA.debugLine="evalsSospechosas = 0";
_evalssospechosas = BA.NumberToString(0);
 };
 };
 //BA.debugLineNum = 312;BA.debugLine="End Sub";
return "";
}
public static String  _checkmessages() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 442;BA.debugLine="Sub CheckMessages";
 //BA.debugLineNum = 444;BA.debugLine="If modooffline = False Then";
if (_modooffline==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 446;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 447;BA.debugLine="dd.url = serverPath & \"/connect3/checkmessages.p";
_dd.url /*String*/  = _serverpath+"/connect3/checkmessages.php?deviceID="+_deviceid;
 //BA.debugLineNum = 448;BA.debugLine="dd.EventName = \"checkMessages\"";
_dd.EventName /*String*/  = "checkMessages";
 //BA.debugLineNum = 449;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 450;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 }else {
 //BA.debugLineNum = 452;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 453;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 454;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 };
 //BA.debugLineNum = 456;BA.debugLine="End Sub";
return "";
}
public static String  _checkmessages_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _msg = "";
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _datomap = null;
anywheresoftware.b4a.objects.collections.Map _usrmap = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _datoid = "";
 //BA.debugLineNum = 457;BA.debugLine="Sub checkMessages_Complete(Job As HttpJob)";
 //BA.debugLineNum = 458;BA.debugLine="Log(\"Mensajes chequeados: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("749152001","Mensajes chequeados: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 459;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 461;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 462;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 463;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 464;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 465;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 466;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 467;BA.debugLine="modooffline = False";
_modooffline = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 468;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 469;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 470;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 471;BA.debugLine="ToastMessageShow(\"No hay mensajes para el usua";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay mensajes para el usuario"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 473;BA.debugLine="ToastMessageShow(\"No messages for the user\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No messages for the user"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 476;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 477;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 478;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 }else if((_act).equals("Error")) { 
 }else if((_act).equals("MensajesOK")) { 
 //BA.debugLineNum = 482;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 483;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 484;BA.debugLine="For i = 0 To numresults - 1";
{
final int step24 = 1;
final int limit24 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit24 ;_i = _i + step24 ) {
 //BA.debugLineNum = 485;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 486;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 488;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 490;BA.debugLine="Dim serverId As String";
_serverid = "";
 //BA.debugLineNum = 491;BA.debugLine="serverId = newpunto.Get(\"markerID\")";
_serverid = BA.ObjectToString(_newpunto.Get((Object)("markerID")));
 //BA.debugLineNum = 495;BA.debugLine="Dim datoMap As Map";
_datomap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 496;BA.debugLine="datoMap.Initialize";
_datomap.Initialize();
 //BA.debugLineNum = 497;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"S";
_datomap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE serverId=?",new String[]{_serverid});
 //BA.debugLineNum = 498;BA.debugLine="If datoMap = Null Or datoMap.IsInitialized = F";
if (_datomap== null || _datomap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 500;BA.debugLine="Dim usrMap As Map";
_usrmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 501;BA.debugLine="usrMap.Initialize";
_usrmap.Initialize();
 //BA.debugLineNum = 502;BA.debugLine="usrMap = DBUtils.ExecuteMap(Starter.sqlDB, \"S";
_usrmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 503;BA.debugLine="If usrMap = Null Or usrMap.IsInitialized = Fa";
if (_usrmap== null || _usrmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 505;BA.debugLine="username = \"guest\"";
_username = "guest";
 }else {
 //BA.debugLineNum = 509;BA.debugLine="username = usrMap.Get(\"username\")";
_username = BA.ObjectToString(_usrmap.Get((Object)("username")));
 //BA.debugLineNum = 510;BA.debugLine="pass = usrMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usrmap.Get((Object)("pass")));
 };
 //BA.debugLineNum = 513;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 514;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 515;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 }else {
 //BA.debugLineNum = 518;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 519;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 520;BA.debugLine="Map1.Put(\"serverId\", serverId)";
_map1.Put((Object)("serverId"),(Object)(_serverid));
 //BA.debugLineNum = 521;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","expValidacion",(Object)("si"),_map1);
 //BA.debugLineNum = 522;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","estadoValidacion",(Object)("experto"),_map1);
 //BA.debugLineNum = 523;BA.debugLine="msg = utilidades.Mensaje(\"Análisis validado\",";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Análisis validado","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"Mas información","","Ok gracias!",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 524;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 526;BA.debugLine="Dim datoId As String";
_datoid = "";
 //BA.debugLineNum = 527;BA.debugLine="datoId = datoMap.Get(\"id\")";
_datoid = BA.ObjectToString(_datomap.Get((Object)("id")));
 //BA.debugLineNum = 528;BA.debugLine="CallSubDelayed2(frmDatosAnteriores, \"VerDeta";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()),"VerDetalles",(Object)(_datoid));
 //BA.debugLineNum = 530;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 533;BA.debugLine="ResetMessages";
_resetmessages();
 };
 }
};
 };
 }else {
 //BA.debugLineNum = 543;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 544;BA.debugLine="Msgbox(\"No tienes conexión a Internet, no puede";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes conexión a Internet, no puedes recibir los mensajes de los revisores"),BA.ObjectToCharSequence("Advertencia"),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 546;BA.debugLine="Msgbox(\"You are not connected to the internet,";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You are not connected to the internet, you cannot receive any messages from the experts"),BA.ObjectToCharSequence("Warning"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 549;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 551;BA.debugLine="Dim usrMap As Map";
_usrmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 552;BA.debugLine="usrMap.Initialize";
_usrmap.Initialize();
 //BA.debugLineNum = 553;BA.debugLine="usrMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELE";
_usrmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 554;BA.debugLine="If usrMap = Null Or usrMap.IsInitialized = False";
if (_usrmap== null || _usrmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 556;BA.debugLine="username = \"guest\"";
_username = "guest";
 }else {
 //BA.debugLineNum = 560;BA.debugLine="username = usrMap.Get(\"username\")";
_username = BA.ObjectToString(_usrmap.Get((Object)("username")));
 //BA.debugLineNum = 561;BA.debugLine="pass = usrMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usrmap.Get((Object)("pass")));
 };
 //BA.debugLineNum = 564;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 565;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 566;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 };
 //BA.debugLineNum = 569;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 570;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 100;BA.debugLine="Private btnOffline As Button";
mostCurrent._btnoffline = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 104;BA.debugLine="Dim Cursor1 As Cursor";
mostCurrent._cursor1 = new anywheresoftware.b4a.sql.SQL.CursorWrapper();
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}

public static void initializeProcessGlobals() {
    
    if (main.processGlobalsRun == false) {
	    main.processGlobalsRun = true;
		try {
		        main._process_globals();
frmperfil._process_globals();
utilidades._process_globals();
register._process_globals();
aprender_memory._process_globals();
aprender_ahorcado._process_globals();
starter._process_globals();
reporte_envio._process_globals();
aprender_ambientes._process_globals();
aprender_ciclo._process_globals();
aprender_comunidades._process_globals();
aprender_contaminacion._process_globals();
aprender_factores._process_globals();
aprender_muestreo._process_globals();
aprender_trofica._process_globals();
dbutils._process_globals();
downloadservice._process_globals();
firebasemessaging._process_globals();
form_main._process_globals();
form_reporte._process_globals();
frmabout._process_globals();
frmdatosanteriores._process_globals();
frmeditprofile._process_globals();
frmfelicitaciones._process_globals();
frmlocalizacion._process_globals();
frmlogin._process_globals();
frmpoliticadatos._process_globals();
httputils2service._process_globals();
imagedownloader._process_globals();
inatcheck._process_globals();
reporte_fotos._process_globals();
reporte_habitat_estuario._process_globals();
reporte_habitat_laguna._process_globals();
reporte_habitat_rio._process_globals();
uploadfiles._process_globals();
		
        } catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 25;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 30;BA.debugLine="Dim deviceID As String";
_deviceid = "";
 //BA.debugLineNum = 32;BA.debugLine="Dim lang As String";
_lang = "";
 //BA.debugLineNum = 34;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim modooffline As Boolean";
_modooffline = false;
 //BA.debugLineNum = 40;BA.debugLine="Dim forceoffline As Boolean";
_forceoffline = false;
 //BA.debugLineNum = 45;BA.debugLine="Dim serverversion As String";
_serverversion = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim servernewstitulo As String";
_servernewstitulo = "";
 //BA.debugLineNum = 47;BA.debugLine="Dim servernewstext As String";
_servernewstext = "";
 //BA.debugLineNum = 48;BA.debugLine="Dim versionactual As String";
_versionactual = "";
 //BA.debugLineNum = 49;BA.debugLine="Dim serverPath As String = \"http://www.app-ear.co";
_serverpath = "http://www.app-ear.com.ar";
 //BA.debugLineNum = 52;BA.debugLine="Dim strUserID As String";
_struserid = "";
 //BA.debugLineNum = 53;BA.debugLine="Dim strUserName As String";
_strusername = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim strUserFullName As String";
_struserfullname = "";
 //BA.debugLineNum = 55;BA.debugLine="Dim strUserLocation As String";
_struserlocation = "";
 //BA.debugLineNum = 56;BA.debugLine="Dim strUserEmail As String";
_struseremail = "";
 //BA.debugLineNum = 57;BA.debugLine="Dim strUserOrg As String";
_struserorg = "";
 //BA.debugLineNum = 58;BA.debugLine="Dim strUserTipoUsuario As String";
_strusertipousuario = "";
 //BA.debugLineNum = 59;BA.debugLine="Dim strUserGroup As String";
_strusergroup = "";
 //BA.debugLineNum = 60;BA.debugLine="Dim numfotosok As String  = 0";
_numfotosok = BA.NumberToString(0);
 //BA.debugLineNum = 61;BA.debugLine="Dim numevalsok As String  = 0";
_numevalsok = BA.NumberToString(0);
 //BA.debugLineNum = 62;BA.debugLine="Dim username As String";
_username = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim pass As String";
_pass = "";
 //BA.debugLineNum = 64;BA.debugLine="Dim msjprivadouser As String";
_msjprivadouser = "";
 //BA.debugLineNum = 65;BA.debugLine="Dim msjprivadoleido As Boolean";
_msjprivadoleido = false;
 //BA.debugLineNum = 66;BA.debugLine="Dim userlang As String";
_userlang = "";
 //BA.debugLineNum = 67;BA.debugLine="Dim evalsOK As String";
_evalsok = "";
 //BA.debugLineNum = 68;BA.debugLine="Dim evalsCorregidas As String";
_evalscorregidas = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim evalsInvalidas As String";
_evalsinvalidas = "";
 //BA.debugLineNum = 70;BA.debugLine="Dim evalsSospechosas As String";
_evalssospechosas = "";
 //BA.debugLineNum = 71;BA.debugLine="Dim validacionTotal As String";
_validaciontotal = "";
 //BA.debugLineNum = 72;BA.debugLine="Dim userCategoria As String";
_usercategoria = "";
 //BA.debugLineNum = 75;BA.debugLine="Dim puntostotales As String = 0";
_puntostotales = BA.NumberToString(0);
 //BA.debugLineNum = 76;BA.debugLine="Dim puntosnumfotos As String = 0";
_puntosnumfotos = BA.NumberToString(0);
 //BA.debugLineNum = 77;BA.debugLine="Dim puntosnumevals As String = 0";
_puntosnumevals = BA.NumberToString(0);
 //BA.debugLineNum = 78;BA.debugLine="Dim numriollanura As String = 0";
_numriollanura = BA.NumberToString(0);
 //BA.debugLineNum = 79;BA.debugLine="Dim numriomontana As String = 0";
_numriomontana = BA.NumberToString(0);
 //BA.debugLineNum = 80;BA.debugLine="Dim numlaguna As String = 0";
_numlaguna = BA.NumberToString(0);
 //BA.debugLineNum = 81;BA.debugLine="Dim numestuario As String = 0";
_numestuario = BA.NumberToString(0);
 //BA.debugLineNum = 82;BA.debugLine="Dim numshares As String = 0";
_numshares = BA.NumberToString(0);
 //BA.debugLineNum = 83;BA.debugLine="Dim latitud As String";
_latitud = "";
 //BA.debugLineNum = 84;BA.debugLine="Dim longitud As String";
_longitud = "";
 //BA.debugLineNum = 85;BA.debugLine="Dim dateandtime As String";
_dateandtime = "";
 //BA.debugLineNum = 86;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 89;BA.debugLine="Dim screenshotpath As String";
_screenshotpath = "";
 //BA.debugLineNum = 92;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 93;BA.debugLine="Private timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static String  _resetmessages() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 579;BA.debugLine="Sub ResetMessages";
 //BA.debugLineNum = 582;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 583;BA.debugLine="dd.url = serverPath & \"/connect3/resetmessages_ne";
_dd.url /*String*/  = _serverpath+"/connect3/resetmessages_new.php?deviceID="+_deviceid;
 //BA.debugLineNum = 584;BA.debugLine="dd.EventName = \"ResetMessages\"";
_dd.EventName /*String*/  = "ResetMessages";
 //BA.debugLineNum = 585;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 586;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 588;BA.debugLine="End Sub";
return "";
}
public static String  _resetmessages_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _usrmap = null;
 //BA.debugLineNum = 589;BA.debugLine="Sub ResetMessages_Complete(Job As HttpJob)";
 //BA.debugLineNum = 590;BA.debugLine="Log(\"Reset messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("749283073","Reset messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 591;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 592;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 593;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 594;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 595;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 596;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 597;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 598;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 599;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error reseteando los mensajes, ")) { 
 //BA.debugLineNum = 601;BA.debugLine="ToastMessageShow(\"Login failed\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Login failed"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("ResetMessages")) { 
 //BA.debugLineNum = 604;BA.debugLine="Dim usrMap As Map";
_usrmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 605;BA.debugLine="usrMap.Initialize";
_usrmap.Initialize();
 //BA.debugLineNum = 606;BA.debugLine="usrMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SEL";
_usrmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 607;BA.debugLine="If usrMap = Null Or usrMap.IsInitialized = Fals";
if (_usrmap== null || _usrmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 609;BA.debugLine="username = \"guest\"";
_username = "guest";
 }else {
 //BA.debugLineNum = 613;BA.debugLine="username = usrMap.Get(\"username\")";
_username = BA.ObjectToString(_usrmap.Get((Object)("username")));
 //BA.debugLineNum = 614;BA.debugLine="pass = usrMap.Get(\"pass\")";
_pass = BA.ObjectToString(_usrmap.Get((Object)("pass")));
 };
 //BA.debugLineNum = 617;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 618;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 619;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 };
 }else {
 //BA.debugLineNum = 622;BA.debugLine="Log(\"reset messages not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("749283105","reset messages not ok",0);
 //BA.debugLineNum = 623;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 624;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 626;BA.debugLine="Msgbox(\"There seems to be a problem with our se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 630;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 631;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 354;BA.debugLine="Sub TestInternet";
 //BA.debugLineNum = 355;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 356;BA.debugLine="dd.url = \"http://www.app-ear.com.ar/connect3/conn";
_dd.url /*String*/  = "http://www.app-ear.com.ar/connect3/connecttest.php";
 //BA.debugLineNum = 357;BA.debugLine="dd.EventName = \"TestInternet\"";
_dd.EventName /*String*/  = "TestInternet";
 //BA.debugLineNum = 358;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = main.getObject();
 //BA.debugLineNum = 359;BA.debugLine="btnOffline.Visible = True";
mostCurrent._btnoffline.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 360;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 361;BA.debugLine="End Sub";
return "";
}
public static String  _testinternet_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _nd = null;
int _upd = 0;
anywheresoftware.b4a.objects.IntentWrapper _market = null;
String _uri = "";
 //BA.debugLineNum = 362;BA.debugLine="Sub TestInternet_Complete(Job As HttpJob)";
 //BA.debugLineNum = 363;BA.debugLine="Log(\"Job completed: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("749020929","Job completed: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 364;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 365;BA.debugLine="versionactual = Application.VersionCode";
_versionactual = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Application.getVersionCode());
 //BA.debugLineNum = 366;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 367;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 368;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 369;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 370;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 371;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 372;BA.debugLine="If act = \"Connected\" Then";
if ((_act).equals("Connected")) { 
 //BA.debugLineNum = 374;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 375;BA.debugLine="lblEstado.Text = \"Conectado. Comprobando versi";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Conectado. Comprobando versión de la aplicación"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 377;BA.debugLine="lblEstado.Text = \"Connected. Checking app vers";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Connected. Checking app version"));
 };
 //BA.debugLineNum = 380;BA.debugLine="modooffline = False";
_modooffline = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 381;BA.debugLine="Dim nd As Map";
_nd = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 382;BA.debugLine="nd = parser.NextObject";
_nd = _parser.NextObject();
 //BA.debugLineNum = 383;BA.debugLine="serverversion = nd.Get(\"currentversion\")";
_serverversion = BA.ObjectToString(_nd.Get((Object)("currentversion")));
 //BA.debugLineNum = 384;BA.debugLine="servernewstitulo = nd.Get(\"newstitulo\")";
_servernewstitulo = BA.ObjectToString(_nd.Get((Object)("newstitulo")));
 //BA.debugLineNum = 385;BA.debugLine="servernewstext = nd.Get(\"newstext\")";
_servernewstext = BA.ObjectToString(_nd.Get((Object)("newstext")));
 //BA.debugLineNum = 386;BA.debugLine="If serverversion <> versionactual Then";
if ((_serverversion).equals(_versionactual) == false) { 
 //BA.debugLineNum = 387;BA.debugLine="Dim upd As Int";
_upd = 0;
 //BA.debugLineNum = 388;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 389;BA.debugLine="upd = Msgbox2(\"Para continuar, debe descargar";
_upd = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Para continuar, debe descargar una actualización importante"),BA.ObjectToCharSequence("Actualización"),"Ir a GooglePlay","Lo haré después","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 391;BA.debugLine="upd = Msgbox2(\"To continue, an important upda";
_upd = anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("To continue, an important update has to be downloaded"),BA.ObjectToCharSequence("Update"),"Go to GooglePlay","I'll do it later","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 394;BA.debugLine="If upd = DialogResponse.POSITIVE Then";
if (_upd==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 396;BA.debugLine="Dim market As Intent, uri As String";
_market = new anywheresoftware.b4a.objects.IntentWrapper();
_uri = "";
 //BA.debugLineNum = 397;BA.debugLine="uri=\"market://details?id=ilpla.appear\"";
_uri = "market://details?id=ilpla.appear";
 //BA.debugLineNum = 398;BA.debugLine="market.Initialize(market.ACTION_VIEW,uri)";
_market.Initialize(_market.ACTION_VIEW,_uri);
 //BA.debugLineNum = 399;BA.debugLine="StartActivity(market)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_market.getObject()));
 //BA.debugLineNum = 401;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 402;BA.debugLine="CheckMessages";
_checkmessages();
 }else {
 //BA.debugLineNum = 405;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 406;BA.debugLine="CheckMessages";
_checkmessages();
 };
 }else {
 //BA.debugLineNum = 410;BA.debugLine="If servernewstitulo <> \"\" And servernewstitulo";
if ((_servernewstitulo).equals("") == false && (_servernewstitulo).equals("Nada") == false) { 
 //BA.debugLineNum = 411;BA.debugLine="Msgbox(servernewstext, servernewstitulo)";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_servernewstext),BA.ObjectToCharSequence(_servernewstitulo),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 415;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 416;BA.debugLine="CheckMessages";
_checkmessages();
 };
 };
 }else {
 //BA.debugLineNum = 421;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 422;BA.debugLine="Msgbox(\"No tienes conexión a Internet. AppEAR i";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes conexión a Internet. AppEAR iniciará con el último usuario que utilizaste, pero no podrás ver ni cargar puntos hasta que no te conectes!"),BA.ObjectToCharSequence("Advertencia"),mostCurrent.activityBA);
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 424;BA.debugLine="Msgbox(\"You have no connection to the internet.";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You have no connection to the internet. AppEAR will start with the last user that logged in, but you won't be able to send data unless you have internet!"),BA.ObjectToCharSequence("Warning"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 427;BA.debugLine="modooffline = True";
_modooffline = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 429;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 431;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 432;BA.debugLine="CheckMessages";
_checkmessages();
 };
 //BA.debugLineNum = 434;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 200;BA.debugLine="Sub timer1_Tick";
 //BA.debugLineNum = 201;BA.debugLine="If lang = \"es\" Then";
if ((_lang).equals("es")) { 
 //BA.debugLineNum = 202;BA.debugLine="lblEstado.Text = \"Comprobando conexión a interne";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Comprobando conexión a internet"));
 }else if((_lang).equals("en")) { 
 //BA.debugLineNum = 204;BA.debugLine="lblEstado.Text = \"Checking the connection to the";
mostCurrent._lblestado.setText(BA.ObjectToCharSequence("Checking the connection to the internet"));
 };
 //BA.debugLineNum = 209;BA.debugLine="TestInternet";
_testinternet();
 //BA.debugLineNum = 211;BA.debugLine="timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _updateinternaldb(String _columnname) throws Exception{
String _mytable = "";
String _txt = "";
int _cols = 0;
String[] _mycolname = null;
String _newcolumn = "";
boolean _flag = false;
int _i = 0;
 //BA.debugLineNum = 317;BA.debugLine="Sub UpdateInternalDB(columnname As String)";
 //BA.debugLineNum = 320;BA.debugLine="Dim MyTable As String = \"userconfig\"";
_mytable = "userconfig";
 //BA.debugLineNum = 321;BA.debugLine="Dim txt As String";
_txt = "";
 //BA.debugLineNum = 322;BA.debugLine="txt=\"SELECT * FROM \" & MyTable & \" LIMIT 1\"";
_txt = "SELECT * FROM "+_mytable+" LIMIT 1";
 //BA.debugLineNum = 323;BA.debugLine="Cursor1=Starter.sqlDB.ExecQuery(txt)";
mostCurrent._cursor1 = (anywheresoftware.b4a.sql.SQL.CursorWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.sql.SQL.CursorWrapper(), (android.database.Cursor)(mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ .ExecQuery(_txt)));
 //BA.debugLineNum = 324;BA.debugLine="Cursor1.Position=0";
mostCurrent._cursor1.setPosition((int) (0));
 //BA.debugLineNum = 325;BA.debugLine="Dim cols As Int = Cursor1.ColumnCount";
_cols = mostCurrent._cursor1.getColumnCount();
 //BA.debugLineNum = 326;BA.debugLine="Dim MyColName(cols) As String";
_mycolname = new String[_cols];
java.util.Arrays.fill(_mycolname,"");
 //BA.debugLineNum = 327;BA.debugLine="Dim NewColumn As String = columnname";
_newcolumn = _columnname;
 //BA.debugLineNum = 328;BA.debugLine="Dim Flag As Boolean =False";
_flag = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 330;BA.debugLine="For i=0 To Cursor1.ColumnCount-1";
{
final int step10 = 1;
final int limit10 = (int) (mostCurrent._cursor1.getColumnCount()-1);
_i = (int) (0) ;
for (;_i <= limit10 ;_i = _i + step10 ) {
 //BA.debugLineNum = 331;BA.debugLine="MyColName(i)=Cursor1.GetColumnName(i)";
_mycolname[_i] = mostCurrent._cursor1.GetColumnName(_i);
 //BA.debugLineNum = 332;BA.debugLine="If MyColName(i)= NewColumn Then";
if ((_mycolname[_i]).equals(_newcolumn)) { 
 //BA.debugLineNum = 334;BA.debugLine="Log(\"Columna \" &columnname &\" existe\")";
anywheresoftware.b4a.keywords.Common.LogImpl("748889873","Columna "+_columnname+" existe",0);
 //BA.debugLineNum = 335;BA.debugLine="Flag=True";
_flag = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 336;BA.debugLine="Exit";
if (true) break;
 };
 }
};
 //BA.debugLineNum = 339;BA.debugLine="If Flag=False Then";
if (_flag==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 340;BA.debugLine="txt=\"ALTER TABLE \" & MyTable & \" ADD COLUMN \" &";
_txt = "ALTER TABLE "+_mytable+" ADD COLUMN "+_newcolumn+" VARCHAR(100)";
 //BA.debugLineNum = 341;BA.debugLine="Starter.sqlDB.ExecNonQuery(txt)";
mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ .ExecNonQuery(_txt);
 //BA.debugLineNum = 342;BA.debugLine="ToastMessageShow(\"Base de datos interna actualiz";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Base de datos interna actualizada / Internal DB checked"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 343;BA.debugLine="Log(\"Columna \" &columnname & \" agregada\")";
anywheresoftware.b4a.keywords.Common.LogImpl("748889882","Columna "+_columnname+" agregada",0);
 };
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return "";
}
}
