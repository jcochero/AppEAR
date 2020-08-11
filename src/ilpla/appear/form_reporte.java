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

public class form_reporte extends Activity implements B4AActivity{
	public static form_reporte mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.form_reporte");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (form_reporte).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.form_reporte");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.form_reporte", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (form_reporte) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (form_reporte) Resume **");
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
		return form_reporte.class;
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
            BA.LogInfo("** Activity (form_reporte) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (form_reporte) Pause event (activity is not paused). **");
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
            form_reporte mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (form_reporte) Resume **");
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
public static String _currentproject = "";
public static String _fullidcurrentproject = "";
public static String _datecurrentproject = "";
public static String _tipoambiente = "";
public static String _origen = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblrojo = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgriomontana = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgriollanura = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgestuario = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imglaguna = null;
public static String _opcionelegida = "";
public anywheresoftware.b4a.objects.EditTextWrapper _txtnombrerio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoktipo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmontana = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestuario = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllaguna = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltiporio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblllanura = null;
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
 //BA.debugLineNum = 40;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 44;BA.debugLine="Activity.LoadLayout(\"layReporte_Basicos\")";
mostCurrent._activity.LoadLayout("layReporte_Basicos",mostCurrent.activityBA);
 //BA.debugLineNum = 45;BA.debugLine="Load_Reporte_Basicos";
_load_reporte_basicos();
 //BA.debugLineNum = 46;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 60;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 61;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 62;BA.debugLine="If Msgbox2(\"Volver al inicio?\", \"SALIR\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Volver al inicio?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 63;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 64;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 66;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 69;BA.debugLine="If Msgbox2(\"Back to the beginning?\", \"Exi";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Back to the beginning?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 70;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 71;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 73;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public static String  _btnoktipo_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 202;BA.debugLine="Sub btnOkTipo_Click";
 //BA.debugLineNum = 204;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 205;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 206;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 210;BA.debugLine="If opcionElegida = \"llanura\" Then";
if ((mostCurrent._opcionelegida).equals("llanura")) { 
 //BA.debugLineNum = 211;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("llanura"),_map1);
 //BA.debugLineNum = 212;BA.debugLine="tipoAmbiente = \"llanura\"";
_tipoambiente = "llanura";
 }else if((mostCurrent._opcionelegida).equals("montana")) { 
 //BA.debugLineNum = 214;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("montana"),_map1);
 //BA.debugLineNum = 215;BA.debugLine="tipoAmbiente = \"montana\"";
_tipoambiente = "montana";
 }else if((mostCurrent._opcionelegida).equals("estuario")) { 
 //BA.debugLineNum = 217;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("estuario"),_map1);
 //BA.debugLineNum = 218;BA.debugLine="tipoAmbiente = \"estuario\"";
_tipoambiente = "estuario";
 }else if((mostCurrent._opcionelegida).equals("laguna")) { 
 //BA.debugLineNum = 220;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"ti";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","tiporio",(Object)("laguna"),_map1);
 //BA.debugLineNum = 221;BA.debugLine="tipoAmbiente = \"laguna\"";
_tipoambiente = "laguna";
 };
 //BA.debugLineNum = 224;BA.debugLine="If txtNombreRio.Text <> \"\" Then";
if ((mostCurrent._txtnombrerio.getText()).equals("") == false) { 
 //BA.debugLineNum = 225;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"no";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","nombresitio",(Object)(mostCurrent._txtnombrerio.getText()),_map1);
 }else {
 //BA.debugLineNum = 227;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"no";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","nombresitio",(Object)("Sin nombre"),_map1);
 };
 //BA.debugLineNum = 231;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 232;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 234;BA.debugLine="frmLocalizacion.currentproject = currentproject";
mostCurrent._frmlocalizacion._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 235;BA.debugLine="frmLocalizacion.origen = origen";
mostCurrent._frmlocalizacion._origen /*String*/  = _origen;
 //BA.debugLineNum = 236;BA.debugLine="frmLocalizacion.tipoAmbiente = tipoAmbiente";
mostCurrent._frmlocalizacion._tipoambiente /*String*/  = _tipoambiente;
 //BA.debugLineNum = 237;BA.debugLine="frmLocalizacion.tipoDetect = \"GPSdetect\"";
mostCurrent._frmlocalizacion._tipodetect /*String*/  = "GPSdetect";
 //BA.debugLineNum = 238;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 240;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 22;BA.debugLine="Private lblRojo As Label";
mostCurrent._lblrojo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private imgRioMontana As ImageView";
mostCurrent._imgriomontana = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private imgRioLlanura As ImageView";
mostCurrent._imgriollanura = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private imgEstuario As ImageView";
mostCurrent._imgestuario = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private imgLaguna As ImageView";
mostCurrent._imglaguna = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim opcionElegida As String";
mostCurrent._opcionelegida = "";
 //BA.debugLineNum = 30;BA.debugLine="Private txtNombreRio As EditText";
mostCurrent._txtnombrerio = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private btnOkTipo As Button";
mostCurrent._btnoktipo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblMontana As Label";
mostCurrent._lblmontana = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private lblEstuario As Label";
mostCurrent._lblestuario = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private lblLaguna As Label";
mostCurrent._lbllaguna = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblTipoRio As Label";
mostCurrent._lbltiporio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private lblLlanura As Label";
mostCurrent._lblllanura = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public static String  _imgestuario_click() throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Sub imgEstuario_Click";
 //BA.debugLineNum = 180;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 181;BA.debugLine="lblRojo.Left = imgEstuario.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgestuario.getLeft());
 //BA.debugLineNum = 182;BA.debugLine="lblRojo.Top = imgEstuario.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgestuario.getTop());
 //BA.debugLineNum = 183;BA.debugLine="opcionElegida = \"estuario\"";
mostCurrent._opcionelegida = "estuario";
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _imglaguna_click() throws Exception{
 //BA.debugLineNum = 172;BA.debugLine="Sub imgLaguna_Click";
 //BA.debugLineNum = 173;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 174;BA.debugLine="lblRojo.Left = imgLaguna.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imglaguna.getLeft());
 //BA.debugLineNum = 175;BA.debugLine="lblRojo.Top = imgLaguna.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imglaguna.getTop());
 //BA.debugLineNum = 176;BA.debugLine="opcionElegida = \"laguna\"";
mostCurrent._opcionelegida = "laguna";
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static String  _imgriollanura_click() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub imgRioLlanura_Click";
 //BA.debugLineNum = 187;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 188;BA.debugLine="lblRojo.Left = imgRioLlanura.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgriollanura.getLeft());
 //BA.debugLineNum = 189;BA.debugLine="lblRojo.Top = imgRioLlanura.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgriollanura.getTop());
 //BA.debugLineNum = 190;BA.debugLine="opcionElegida = \"llanura\"";
mostCurrent._opcionelegida = "llanura";
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _imgriomontana_click() throws Exception{
 //BA.debugLineNum = 193;BA.debugLine="Sub imgRioMontana_Click";
 //BA.debugLineNum = 194;BA.debugLine="lblRojo.Visible = True";
mostCurrent._lblrojo.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 195;BA.debugLine="lblRojo.Left = imgRioMontana.Left";
mostCurrent._lblrojo.setLeft(mostCurrent._imgriomontana.getLeft());
 //BA.debugLineNum = 196;BA.debugLine="lblRojo.Top = imgRioMontana.Top";
mostCurrent._lblrojo.setTop(mostCurrent._imgriomontana.getTop());
 //BA.debugLineNum = 197;BA.debugLine="opcionElegida = \"montana\"";
mostCurrent._opcionelegida = "montana";
 //BA.debugLineNum = 198;BA.debugLine="End Sub";
return "";
}
public static String  _load_reporte_basicos() throws Exception{
anywheresoftware.b4a.objects.collections.List _neweval = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _currentprojectmap = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 114;BA.debugLine="Sub Load_Reporte_Basicos";
 //BA.debugLineNum = 121;BA.debugLine="Dim newEval As List";
_neweval = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 122;BA.debugLine="newEval.Initialize";
_neweval.Initialize();
 //BA.debugLineNum = 123;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 124;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 125;BA.debugLine="m.Put(\"usuario\", Main.username)";
_m.Put((Object)("usuario"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 126;BA.debugLine="newEval.Add(m)";
_neweval.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 127;BA.debugLine="If Starter.sqlDB = Null Then";
if (mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ == null) { 
 //BA.debugLineNum = 128;BA.debugLine="Starter.sqlDB.Initialize(Starter.dbdir, \"appearD";
mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ .Initialize(mostCurrent._starter._dbdir /*String*/ ,"appearDB.db",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 130;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"evals\", newEval";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals",_neweval);
 //BA.debugLineNum = 134;BA.debugLine="Dim currentprojectMap As Map";
_currentprojectmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 135;BA.debugLine="currentprojectMap.Initialize";
_currentprojectmap.Initialize();
 //BA.debugLineNum = 136;BA.debugLine="Try";
try { //BA.debugLineNum = 137;BA.debugLine="currentprojectMap = DBUtils.ExecuteMap(Starter.s";
_currentprojectmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals ORDER BY id DESC LIMIT 1",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 138;BA.debugLine="If currentprojectMap = Null Or currentprojectMap";
if (_currentprojectmap== null || _currentprojectmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 139;BA.debugLine="currentproject = 1";
_currentproject = BA.NumberToString(1);
 //BA.debugLineNum = 140;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 141;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 142;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 143;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 144;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 145;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 146;BA.debugLine="fullidcurrentproject = Main.username & \"_\" & cu";
_fullidcurrentproject = mostCurrent._main._username /*String*/ +"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 147;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 148;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"t";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 149;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 }else {
 //BA.debugLineNum = 151;BA.debugLine="currentproject = currentprojectMap.Get(\"id\")";
_currentproject = BA.ObjectToString(_currentprojectmap.Get((Object)("id")));
 //BA.debugLineNum = 152;BA.debugLine="Main.currentproject = currentproject";
mostCurrent._main._currentproject /*String*/  = _currentproject;
 //BA.debugLineNum = 153;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 154;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 155;BA.debugLine="Map1.Put(\"Id\", currentproject)";
_map1.Put((Object)("Id"),(Object)(_currentproject));
 //BA.debugLineNum = 156;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 157;BA.debugLine="datecurrentproject = DateTime.Date(DateTime.Now";
_datecurrentproject = anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow());
 //BA.debugLineNum = 158;BA.debugLine="fullidcurrentproject = Main.username & \"_\" & cu";
_fullidcurrentproject = mostCurrent._main._username /*String*/ +"_"+_currentproject+"_"+_datecurrentproject;
 //BA.debugLineNum = 159;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"f";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","fullID",(Object)(_fullidcurrentproject),_map1);
 //BA.debugLineNum = 160;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"t";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","terminado",(Object)("no"),_map1);
 //BA.debugLineNum = 161;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\", \"e";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","evalsent",(Object)("no"),_map1);
 };
 } 
       catch (Exception e41) {
			processBA.setLastException(e41); //BA.debugLineNum = 164;BA.debugLine="Log(LastException)";
anywheresoftware.b4a.keywords.Common.LogImpl("730539826",BA.ObjectToString(anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA)),0);
 //BA.debugLineNum = 165;BA.debugLine="ToastMessageShow(\"Hubo un error, intente de nuev";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Hubo un error, intente de nuevo"),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 166;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="Dim currentproject As String";
_currentproject = "";
 //BA.debugLineNum = 9;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 10;BA.debugLine="Dim datecurrentproject As String";
_datecurrentproject = "";
 //BA.debugLineNum = 12;BA.debugLine="Dim tipoAmbiente As String";
_tipoambiente = "";
 //BA.debugLineNum = 13;BA.debugLine="Dim origen As String";
_origen = "";
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 87;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 88;BA.debugLine="txtNombreRio.Hint = \"Escribe el nombre del lugar";
mostCurrent._txtnombrerio.setHint("Escribe el nombre del lugar si lo conoces");
 //BA.debugLineNum = 89;BA.debugLine="btnOkTipo.Text = \"Continuar \"";
mostCurrent._btnoktipo.setText(BA.ObjectToCharSequence("Continuar "));
 //BA.debugLineNum = 90;BA.debugLine="lblMontana.Text = \"Río de montaña\"";
mostCurrent._lblmontana.setText(BA.ObjectToCharSequence("Río de montaña"));
 //BA.debugLineNum = 91;BA.debugLine="lblEstuario.Text = \"Estuario / Playa\"";
mostCurrent._lblestuario.setText(BA.ObjectToCharSequence("Estuario / Playa"));
 //BA.debugLineNum = 92;BA.debugLine="lblLaguna.Text = \"Laguna\"";
mostCurrent._lbllaguna.setText(BA.ObjectToCharSequence("Laguna"));
 //BA.debugLineNum = 93;BA.debugLine="lblTipoRio.Text = \"¿Qué tipo de ambiente es?\"";
mostCurrent._lbltiporio.setText(BA.ObjectToCharSequence("¿Qué tipo de ambiente es?"));
 //BA.debugLineNum = 94;BA.debugLine="lblLlanura.Text = \"Río de llanura\"";
mostCurrent._lblllanura.setText(BA.ObjectToCharSequence("Río de llanura"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 96;BA.debugLine="txtNombreRio.Hint = \"Write the name of the place";
mostCurrent._txtnombrerio.setHint("Write the name of the place if you know it");
 //BA.debugLineNum = 97;BA.debugLine="btnOkTipo.Text = \"Continue \"";
mostCurrent._btnoktipo.setText(BA.ObjectToCharSequence("Continue "));
 //BA.debugLineNum = 98;BA.debugLine="lblMontana.Text = \"Mountain river\"";
mostCurrent._lblmontana.setText(BA.ObjectToCharSequence("Mountain river"));
 //BA.debugLineNum = 99;BA.debugLine="lblEstuario.Text = \"Estuary / Beach\"";
mostCurrent._lblestuario.setText(BA.ObjectToCharSequence("Estuary / Beach"));
 //BA.debugLineNum = 100;BA.debugLine="lblLaguna.Text = \"Lake\"";
mostCurrent._lbllaguna.setText(BA.ObjectToCharSequence("Lake"));
 //BA.debugLineNum = 101;BA.debugLine="lblTipoRio.Text = \"What type of waterbody is it?";
mostCurrent._lbltiporio.setText(BA.ObjectToCharSequence("What type of waterbody is it?"));
 //BA.debugLineNum = 102;BA.debugLine="lblLlanura.Text = \"Plain river\"";
mostCurrent._lblllanura.setText(BA.ObjectToCharSequence("Plain river"));
 };
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
}
