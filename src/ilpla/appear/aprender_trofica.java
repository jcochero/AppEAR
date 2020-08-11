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

public class aprender_trofica extends Activity implements B4AActivity{
	public static aprender_trofica mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.aprender_trofica");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (aprender_trofica).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.aprender_trofica");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.aprender_trofica", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (aprender_trofica) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (aprender_trofica) Resume **");
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
		return aprender_trofica.class;
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
            BA.LogInfo("** Activity (aprender_trofica) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (aprender_trofica) Pause event (activity is not paused). **");
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
            aprender_trofica mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (aprender_trofica) Resume **");
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
public static boolean _verinstruccionestrofica = false;
public static String _origen = "";
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd1 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd2 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd3 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd4 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd5 = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.objects.PanelWrapper _img1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _img2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _img3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _img4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _img5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot5 = null;
public static String _solucion = "";
public anywheresoftware.b4a.objects.LabelWrapper _lblnivel = null;
public static int _currentlevel = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btncheck = null;
public static String _organismo1 = "";
public static String _organismo2 = "";
public static String _organismo3 = "";
public static String _organismo4 = "";
public static String _organismo5 = "";
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public static int _inix1 = 0;
public static int _inix2 = 0;
public static int _inix3 = 0;
public static int _inix4 = 0;
public static int _inix5 = 0;
public static int _iniy1 = 0;
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
 //BA.debugLineNum = 54;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 57;BA.debugLine="Activity.LoadLayout(\"Aprender_Trofica\")";
mostCurrent._activity.LoadLayout("Aprender_Trofica",mostCurrent.activityBA);
 //BA.debugLineNum = 58;BA.debugLine="inix1 = img1.Left";
_inix1 = mostCurrent._img1.getLeft();
 //BA.debugLineNum = 59;BA.debugLine="iniy1 = img1.Top";
_iniy1 = mostCurrent._img1.getTop();
 //BA.debugLineNum = 60;BA.debugLine="inix2 = img2.Left";
_inix2 = mostCurrent._img2.getLeft();
 //BA.debugLineNum = 61;BA.debugLine="inix3 = img3.Left";
_inix3 = mostCurrent._img3.getLeft();
 //BA.debugLineNum = 62;BA.debugLine="inix4 = img4.Left";
_inix4 = mostCurrent._img4.getLeft();
 //BA.debugLineNum = 63;BA.debugLine="inix5 = img5.Left";
_inix5 = mostCurrent._img5.getLeft();
 //BA.debugLineNum = 65;BA.debugLine="CargaInstrucciones";
_cargainstrucciones();
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 72;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 74;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 69;BA.debugLine="LoadLevel(currentlevel)";
_loadlevel(_currentlevel);
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public static String  _btncheck_click() throws Exception{
String _currentsolucion = "";
String _okms = "";
 //BA.debugLineNum = 525;BA.debugLine="Sub btnCheck_Click";
 //BA.debugLineNum = 526;BA.debugLine="Dim currentsolucion As String";
_currentsolucion = "";
 //BA.debugLineNum = 527;BA.debugLine="currentsolucion = img1.Tag & img2.Tag & img3.Tag";
_currentsolucion = BA.ObjectToString(mostCurrent._img1.getTag())+BA.ObjectToString(mostCurrent._img2.getTag())+BA.ObjectToString(mostCurrent._img3.getTag())+BA.ObjectToString(mostCurrent._img4.getTag())+BA.ObjectToString(mostCurrent._img5.getTag());
 //BA.debugLineNum = 528;BA.debugLine="If currentsolucion = solucion Then";
if ((_currentsolucion).equals(mostCurrent._solucion)) { 
 //BA.debugLineNum = 529;BA.debugLine="Dim okms As String";
_okms = "";
 //BA.debugLineNum = 530;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 537;BA.debugLine="If okms = DialogResponse.POSITIVE Then";
if ((_okms).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 538;BA.debugLine="currentlevel = currentlevel +1";
_currentlevel = (int) (_currentlevel+1);
 //BA.debugLineNum = 539;BA.debugLine="LoadLevel(currentlevel)";
_loadlevel(_currentlevel);
 }else if((_okms).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE))) { 
 //BA.debugLineNum = 541;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 542;BA.debugLine="Msgbox(VerExplicacion(organismo1), \"Primer niv";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo1)),BA.ObjectToCharSequence("Primer nivel trófico"),mostCurrent.activityBA);
 //BA.debugLineNum = 543;BA.debugLine="Msgbox(VerExplicacion(organismo2), \"Segundo ni";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo2)),BA.ObjectToCharSequence("Segundo nivel trófico"),mostCurrent.activityBA);
 //BA.debugLineNum = 544;BA.debugLine="Msgbox(VerExplicacion(organismo3), \"Tercer niv";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo3)),BA.ObjectToCharSequence("Tercer nivel trófico"),mostCurrent.activityBA);
 //BA.debugLineNum = 545;BA.debugLine="If organismo4 <> \"\" Then";
if ((mostCurrent._organismo4).equals("") == false) { 
 //BA.debugLineNum = 546;BA.debugLine="Msgbox(VerExplicacion(organismo4), \"Cuarto ni";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo4)),BA.ObjectToCharSequence("Cuarto nivel trófico"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 548;BA.debugLine="If organismo5 <> \"\" Then";
if ((mostCurrent._organismo5).equals("") == false) { 
 //BA.debugLineNum = 549;BA.debugLine="Msgbox(VerExplicacion(organismo5), \"Quinto ni";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo5)),BA.ObjectToCharSequence("Quinto nivel trófico"),mostCurrent.activityBA);
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 552;BA.debugLine="Msgbox(VerExplicacion(organismo1), \"First trop";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo1)),BA.ObjectToCharSequence("First trophic level"),mostCurrent.activityBA);
 //BA.debugLineNum = 553;BA.debugLine="Msgbox(VerExplicacion(organismo2), \"Second tro";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo2)),BA.ObjectToCharSequence("Second trophic level"),mostCurrent.activityBA);
 //BA.debugLineNum = 554;BA.debugLine="Msgbox(VerExplicacion(organismo3), \"Third trop";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo3)),BA.ObjectToCharSequence("Third trophic level"),mostCurrent.activityBA);
 //BA.debugLineNum = 555;BA.debugLine="If organismo4 <> \"\" Then";
if ((mostCurrent._organismo4).equals("") == false) { 
 //BA.debugLineNum = 556;BA.debugLine="Msgbox(VerExplicacion(organismo4), \"Fourth tr";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo4)),BA.ObjectToCharSequence("Fourth trophic level"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 558;BA.debugLine="If organismo5 <> \"\" Then";
if ((mostCurrent._organismo5).equals("") == false) { 
 //BA.debugLineNum = 559;BA.debugLine="Msgbox(VerExplicacion(organismo5), \"Fifth tro";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence(_verexplicacion(mostCurrent._organismo5)),BA.ObjectToCharSequence("Fifth trophic level"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 562;BA.debugLine="currentlevel = currentlevel +1";
_currentlevel = (int) (_currentlevel+1);
 //BA.debugLineNum = 563;BA.debugLine="LoadLevel(currentlevel)";
_loadlevel(_currentlevel);
 };
 }else {
 //BA.debugLineNum = 566;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 567;BA.debugLine="Msgbox(\"La cadena trófica no es correcta\",\"Prue";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("La cadena trófica no es correcta"),BA.ObjectToCharSequence("Prueba de nuevo!"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 569;BA.debugLine="Msgbox(\"The trophic network is not correct\",\"Tr";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("The trophic network is not correct"),BA.ObjectToCharSequence("Try again!"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 574;BA.debugLine="End Sub";
return "";
}
public static String  _butcerrar_click() throws Exception{
 //BA.debugLineNum = 640;BA.debugLine="Sub butCerrar_Click";
 //BA.debugLineNum = 641;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 642;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 643;BA.debugLine="End Sub";
return "";
}
public static String  _buthelp_click() throws Exception{
 //BA.debugLineNum = 645;BA.debugLine="Sub butHelp_Click";
 //BA.debugLineNum = 646;BA.debugLine="verInstruccionesTrofica = True";
_verinstruccionestrofica = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 647;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 648;BA.debugLine="End Sub";
return "";
}
public static String  _butok_click() throws Exception{
 //BA.debugLineNum = 147;BA.debugLine="Sub butOK_Click";
 //BA.debugLineNum = 148;BA.debugLine="verInstruccionesTrofica = False";
_verinstruccionestrofica = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 149;BA.debugLine="origen = \"\"";
_origen = "";
 //BA.debugLineNum = 150;BA.debugLine="p.SetScreenOrientation(0)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (0));
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _butreset_click() throws Exception{
 //BA.debugLineNum = 634;BA.debugLine="Sub butReset_Click";
 //BA.debugLineNum = 635;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 636;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 637;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,aprender_trofica.getObject());
 //BA.debugLineNum = 638;BA.debugLine="End Sub";
return "";
}
public static String  _cargainstrucciones() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _headerinst = null;
anywheresoftware.b4a.objects.LabelWrapper _textoinstruccion = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel1 = null;
String _lineinst = "";
anywheresoftware.b4a.objects.ButtonWrapper _butok = null;
 //BA.debugLineNum = 95;BA.debugLine="Sub CargaInstrucciones";
 //BA.debugLineNum = 96;BA.debugLine="If verInstruccionesTrofica = False Then";
if (_verinstruccionestrofica==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 97;BA.debugLine="currentlevel = 1";
_currentlevel = (int) (1);
 //BA.debugLineNum = 98;BA.debugLine="LoadLevel(currentlevel)";
_loadlevel(_currentlevel);
 //BA.debugLineNum = 99;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 100;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 105;BA.debugLine="Dim headerInst As Label";
_headerinst = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 106;BA.debugLine="headerInst.Initialize(\"\")";
_headerinst.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 107;BA.debugLine="headerInst.TextColor = Colors.Black";
_headerinst.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 108;BA.debugLine="headerInst.TextSize = 26";
_headerinst.setTextSize((float) (26));
 //BA.debugLineNum = 110;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 111;BA.debugLine="headerInst.Text = \"La cadena trófica\"";
_headerinst.setText(BA.ObjectToCharSequence("La cadena trófica"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 113;BA.debugLine="headerInst.Text = \"Trophic networks\"";
_headerinst.setText(BA.ObjectToCharSequence("Trophic networks"));
 };
 //BA.debugLineNum = 118;BA.debugLine="Dim textoInstruccion As Label";
_textoinstruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 119;BA.debugLine="textoInstruccion.Initialize(\"\")";
_textoinstruccion.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 120;BA.debugLine="textoInstruccion.TextColor = Colors.Black";
_textoinstruccion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 121;BA.debugLine="Dim panel1 As ScrollView";
_panel1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 122;BA.debugLine="panel1.Initialize(500dip)";
_panel1.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 123;BA.debugLine="panel1.Color = Colors.White";
_panel1.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 124;BA.debugLine="Dim lineInst As String";
_lineinst = "";
 //BA.debugLineNum = 125;BA.debugLine="lineInst = File.ReadString(File.DirAssets, Mai";
_lineinst = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-troficaInst.txt");
 //BA.debugLineNum = 127;BA.debugLine="textoInstruccion.Text = lineInst";
_textoinstruccion.setText(BA.ObjectToCharSequence(_lineinst));
 //BA.debugLineNum = 130;BA.debugLine="Activity.AddView(panel1,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(_panel1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 131;BA.debugLine="panel1.Panel.AddView(headerInst,5%x,5%y,90%x,30di";
_panel1.getPanel().AddView((android.view.View)(_headerinst.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 132;BA.debugLine="panel1.Panel.AddView(textoInstruccion,5%x,5%y + 3";
_panel1.getPanel().AddView((android.view.View)(_textoinstruccion.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA)+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30))),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 136;BA.debugLine="Dim butOK As Button";
_butok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 137;BA.debugLine="butOK.Initialize(\"butOK\")";
_butok.Initialize(mostCurrent.activityBA,"butOK");
 //BA.debugLineNum = 138;BA.debugLine="butOK.Text = \"OK\"";
_butok.setText(BA.ObjectToCharSequence("OK"));
 //BA.debugLineNum = 139;BA.debugLine="butOK.Color = Colors.ARGB(255,73,202,138)";
_butok.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 140;BA.debugLine="butOK.TextColor = Colors.White";
_butok.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 142;BA.debugLine="Activity.AddView(butOK, 0,90%y,100%x,10%y)";
mostCurrent._activity.AddView((android.view.View)(_butok.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim GD1, GD2, GD3, GD4, GD5 As GestureDetector";
mostCurrent._gd1 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd2 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd3 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd4 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd5 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
 //BA.debugLineNum = 18;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 19;BA.debugLine="Private img1 As Panel";
mostCurrent._img1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private img2 As Panel";
mostCurrent._img2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private img3 As Panel";
mostCurrent._img3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private img4 As Panel";
mostCurrent._img4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private img5 As Panel";
mostCurrent._img5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private slot1 As Panel";
mostCurrent._slot1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private slot2 As Panel";
mostCurrent._slot2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private slot3 As Panel";
mostCurrent._slot3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private slot4 As Panel";
mostCurrent._slot4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private slot5 As Panel";
mostCurrent._slot5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim solucion As String";
mostCurrent._solucion = "";
 //BA.debugLineNum = 32;BA.debugLine="Private lblNivel As Label";
mostCurrent._lblnivel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim currentlevel As Int";
_currentlevel = 0;
 //BA.debugLineNum = 34;BA.debugLine="Private btnCheck As Button";
mostCurrent._btncheck = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim organismo1 As String";
mostCurrent._organismo1 = "";
 //BA.debugLineNum = 38;BA.debugLine="Dim organismo2 As String";
mostCurrent._organismo2 = "";
 //BA.debugLineNum = 39;BA.debugLine="Dim organismo3 As String";
mostCurrent._organismo3 = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim organismo4 As String";
mostCurrent._organismo4 = "";
 //BA.debugLineNum = 41;BA.debugLine="Dim organismo5 As String";
mostCurrent._organismo5 = "";
 //BA.debugLineNum = 43;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim inix1 As Int";
_inix1 = 0;
 //BA.debugLineNum = 46;BA.debugLine="Dim inix2 As Int";
_inix2 = 0;
 //BA.debugLineNum = 47;BA.debugLine="Dim inix3 As Int";
_inix3 = 0;
 //BA.debugLineNum = 48;BA.debugLine="Dim inix4 As Int";
_inix4 = 0;
 //BA.debugLineNum = 49;BA.debugLine="Dim inix5 As Int";
_inix5 = 0;
 //BA.debugLineNum = 50;BA.debugLine="Dim iniy1 As Int";
_iniy1 = 0;
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _img1_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 376;BA.debugLine="Sub img1_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 377;BA.debugLine="Dim newleft As Int = Max(0, Min(img1.Left + delta";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img1.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img1.getWidth())));
 //BA.debugLineNum = 378;BA.debugLine="Dim newtop As Int = Max(0, Min(img1.Top + deltaY,";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img1.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img1.getHeight())));
 //BA.debugLineNum = 379;BA.debugLine="img1.Left = newleft";
mostCurrent._img1.setLeft(_newleft);
 //BA.debugLineNum = 380;BA.debugLine="img1.Top = newtop";
mostCurrent._img1.setTop(_newtop);
 //BA.debugLineNum = 382;BA.debugLine="If img1.Left >= slot1.Left And img1.Left <= slot1";
if (mostCurrent._img1.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._img1.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._img1.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._img1.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 383;BA.debugLine="img1.Left = slot1.Left + 10dip";
mostCurrent._img1.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 384;BA.debugLine="img1.Top = slot1.Top + 10dip";
mostCurrent._img1.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 385;BA.debugLine="img1.Tag = slot1.Tag";
mostCurrent._img1.setTag(mostCurrent._slot1.getTag());
 }else if(mostCurrent._img1.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._img1.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._img1.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._img1.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 387;BA.debugLine="img1.Left = slot2.Left + 10dip";
mostCurrent._img1.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 388;BA.debugLine="img1.Top = slot2.Top + 10dip";
mostCurrent._img1.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 389;BA.debugLine="img1.Tag = slot2.Tag";
mostCurrent._img1.setTag(mostCurrent._slot2.getTag());
 }else if(mostCurrent._img1.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._img1.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._img1.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._img1.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 391;BA.debugLine="img1.Left = slot3.Left + 10dip";
mostCurrent._img1.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 392;BA.debugLine="img1.Top = slot3.Top + 10dip";
mostCurrent._img1.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 393;BA.debugLine="img1.Tag = slot3.Tag";
mostCurrent._img1.setTag(mostCurrent._slot3.getTag());
 }else if(mostCurrent._img1.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._img1.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._img1.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._img1.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 395;BA.debugLine="img1.Left = slot4.Left + 10dip";
mostCurrent._img1.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 396;BA.debugLine="img1.Top = slot4.Top + 10dip";
mostCurrent._img1.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 397;BA.debugLine="img1.Tag = slot4.Tag";
mostCurrent._img1.setTag(mostCurrent._slot4.getTag());
 }else if(mostCurrent._img1.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._img1.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._img1.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._img1.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 399;BA.debugLine="img1.Left = slot5.Left + 10dip";
mostCurrent._img1.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 400;BA.debugLine="img1.Top = slot5.Top + 10dip";
mostCurrent._img1.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 401;BA.debugLine="img1.Tag = slot5.Tag";
mostCurrent._img1.setTag(mostCurrent._slot5.getTag());
 };
 //BA.debugLineNum = 403;BA.debugLine="End Sub";
return "";
}
public static String  _img2_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 404;BA.debugLine="Sub img2_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 405;BA.debugLine="Dim newleft As Int = Max(0, Min(img2.Left + delta";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img2.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img2.getWidth())));
 //BA.debugLineNum = 406;BA.debugLine="Dim newtop As Int = Max(0, Min(img2.Top + deltaY,";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img2.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img2.getHeight())));
 //BA.debugLineNum = 407;BA.debugLine="img2.Left = newleft";
mostCurrent._img2.setLeft(_newleft);
 //BA.debugLineNum = 408;BA.debugLine="img2.Top = newtop";
mostCurrent._img2.setTop(_newtop);
 //BA.debugLineNum = 410;BA.debugLine="If img2.Left >= slot1.Left And img2.Left <= slot1";
if (mostCurrent._img2.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._img2.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._img2.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._img2.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 411;BA.debugLine="img2.Left = slot1.Left + 10dip";
mostCurrent._img2.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 412;BA.debugLine="img2.Top = slot1.Top + 10dip";
mostCurrent._img2.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 413;BA.debugLine="img2.Tag = slot1.Tag";
mostCurrent._img2.setTag(mostCurrent._slot1.getTag());
 }else if(mostCurrent._img2.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._img2.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._img2.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._img2.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 415;BA.debugLine="img2.Left = slot2.Left + 10dip";
mostCurrent._img2.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 416;BA.debugLine="img2.Top = slot2.Top + 10dip";
mostCurrent._img2.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 417;BA.debugLine="img2.Tag = slot2.Tag";
mostCurrent._img2.setTag(mostCurrent._slot2.getTag());
 }else if(mostCurrent._img2.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._img2.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._img2.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._img2.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 419;BA.debugLine="img2.Left = slot3.Left + 10dip";
mostCurrent._img2.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 420;BA.debugLine="img2.Top = slot3.Top + 10dip";
mostCurrent._img2.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 421;BA.debugLine="img2.Tag = slot3.Tag";
mostCurrent._img2.setTag(mostCurrent._slot3.getTag());
 }else if(mostCurrent._img2.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._img2.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._img2.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._img2.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 423;BA.debugLine="img2.Left = slot4.Left + 10dip";
mostCurrent._img2.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 424;BA.debugLine="img2.Top = slot4.Top + 10dip";
mostCurrent._img2.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 425;BA.debugLine="img2.Tag = slot4.Tag";
mostCurrent._img2.setTag(mostCurrent._slot4.getTag());
 }else if(mostCurrent._img2.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._img2.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._img2.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._img2.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 427;BA.debugLine="img2.Left = slot5.Left + 10dip";
mostCurrent._img2.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 428;BA.debugLine="img2.Top = slot5.Top + 10dip";
mostCurrent._img2.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 429;BA.debugLine="img2.Tag = slot5.Tag";
mostCurrent._img2.setTag(mostCurrent._slot5.getTag());
 };
 //BA.debugLineNum = 431;BA.debugLine="End Sub";
return "";
}
public static String  _img3_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 432;BA.debugLine="Sub img3_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 433;BA.debugLine="Dim newleft As Int = Max(0, Min(img3.Left + delta";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img3.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img3.getWidth())));
 //BA.debugLineNum = 434;BA.debugLine="Dim newtop As Int = Max(0, Min(img3.Top + deltaY,";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img3.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img3.getHeight())));
 //BA.debugLineNum = 435;BA.debugLine="img3.Left = newleft";
mostCurrent._img3.setLeft(_newleft);
 //BA.debugLineNum = 436;BA.debugLine="img3.Top = newtop";
mostCurrent._img3.setTop(_newtop);
 //BA.debugLineNum = 438;BA.debugLine="If img3.Left >= slot1.Left And img3.Left <= slot1";
if (mostCurrent._img3.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._img3.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._img3.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._img3.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 439;BA.debugLine="img3.Left = slot1.Left + 10dip";
mostCurrent._img3.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 440;BA.debugLine="img3.Top = slot1.Top + 10dip";
mostCurrent._img3.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 441;BA.debugLine="img3.Tag = slot1.Tag";
mostCurrent._img3.setTag(mostCurrent._slot1.getTag());
 }else if(mostCurrent._img3.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._img3.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._img3.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._img3.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 443;BA.debugLine="img3.Left = slot2.Left + 10dip";
mostCurrent._img3.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 444;BA.debugLine="img3.Top = slot2.Top + 10dip";
mostCurrent._img3.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 445;BA.debugLine="img3.Tag = slot2.Tag";
mostCurrent._img3.setTag(mostCurrent._slot2.getTag());
 }else if(mostCurrent._img3.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._img3.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._img3.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._img3.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 447;BA.debugLine="img3.Left = slot3.Left + 10dip";
mostCurrent._img3.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 448;BA.debugLine="img3.Top = slot3.Top + 10dip";
mostCurrent._img3.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 449;BA.debugLine="img3.Tag = slot3.Tag";
mostCurrent._img3.setTag(mostCurrent._slot3.getTag());
 }else if(mostCurrent._img3.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._img3.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._img3.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._img3.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 451;BA.debugLine="img3.Left = slot4.Left + 10dip";
mostCurrent._img3.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 452;BA.debugLine="img3.Top = slot4.Top + 10dip";
mostCurrent._img3.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 453;BA.debugLine="img3.Tag = slot4.Tag";
mostCurrent._img3.setTag(mostCurrent._slot4.getTag());
 }else if(mostCurrent._img3.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._img3.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._img3.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._img3.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 455;BA.debugLine="img3.Left = slot5.Left + 10dip";
mostCurrent._img3.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 456;BA.debugLine="img3.Top = slot5.Top + 10dip";
mostCurrent._img3.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 457;BA.debugLine="img3.Tag = slot5.Tag";
mostCurrent._img3.setTag(mostCurrent._slot5.getTag());
 };
 //BA.debugLineNum = 459;BA.debugLine="End Sub";
return "";
}
public static String  _img4_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 460;BA.debugLine="Sub img4_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 461;BA.debugLine="Dim newleft As Int = Max(0, Min(img4.Left + delta";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img4.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img4.getWidth())));
 //BA.debugLineNum = 462;BA.debugLine="Dim newtop As Int = Max(0, Min(img4.Top + deltaY,";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img4.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img4.getHeight())));
 //BA.debugLineNum = 463;BA.debugLine="img4.Left = newleft";
mostCurrent._img4.setLeft(_newleft);
 //BA.debugLineNum = 464;BA.debugLine="img4.Top = newtop";
mostCurrent._img4.setTop(_newtop);
 //BA.debugLineNum = 466;BA.debugLine="If img4.Left >= slot1.Left And img4.Left <= slot1";
if (mostCurrent._img4.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._img4.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._img4.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._img4.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 467;BA.debugLine="img4.Left = slot1.Left + 10dip";
mostCurrent._img4.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 468;BA.debugLine="img4.Top = slot1.Top + 10dip";
mostCurrent._img4.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 469;BA.debugLine="img4.Tag = slot1.Tag";
mostCurrent._img4.setTag(mostCurrent._slot1.getTag());
 }else if(mostCurrent._img4.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._img4.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._img4.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._img4.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 471;BA.debugLine="img4.Left = slot2.Left + 10dip";
mostCurrent._img4.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 472;BA.debugLine="img4.Top = slot2.Top + 10dip";
mostCurrent._img4.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 473;BA.debugLine="img4.Tag = slot2.Tag";
mostCurrent._img4.setTag(mostCurrent._slot2.getTag());
 }else if(mostCurrent._img4.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._img4.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._img4.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._img4.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 475;BA.debugLine="img4.Left = slot3.Left + 10dip";
mostCurrent._img4.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 476;BA.debugLine="img4.Top = slot3.Top + 10dip";
mostCurrent._img4.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 477;BA.debugLine="img4.Tag = slot3.Tag";
mostCurrent._img4.setTag(mostCurrent._slot3.getTag());
 }else if(mostCurrent._img4.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._img4.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._img4.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._img4.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 479;BA.debugLine="img4.Left = slot4.Left + 10dip";
mostCurrent._img4.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 480;BA.debugLine="img4.Top = slot4.Top + 10dip";
mostCurrent._img4.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 481;BA.debugLine="img4.Tag = slot4.Tag";
mostCurrent._img4.setTag(mostCurrent._slot4.getTag());
 }else if(mostCurrent._img4.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._img4.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._img4.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._img4.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 483;BA.debugLine="img4.Left = slot5.Left + 10dip";
mostCurrent._img4.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 484;BA.debugLine="img4.Top = slot5.Top + 10dip";
mostCurrent._img4.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 485;BA.debugLine="img4.Tag = slot5.Tag";
mostCurrent._img4.setTag(mostCurrent._slot5.getTag());
 };
 //BA.debugLineNum = 487;BA.debugLine="End Sub";
return "";
}
public static String  _img5_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 488;BA.debugLine="Sub img5_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 489;BA.debugLine="Dim newleft As Int = Max(0, Min(img5.Left + delta";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img5.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img5.getWidth())));
 //BA.debugLineNum = 490;BA.debugLine="Dim newtop As Int = Max(0, Min(img5.Top + deltaY,";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._img5.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._img5.getHeight())));
 //BA.debugLineNum = 491;BA.debugLine="img5.Left = newleft";
mostCurrent._img5.setLeft(_newleft);
 //BA.debugLineNum = 492;BA.debugLine="img5.Top = newtop";
mostCurrent._img5.setTop(_newtop);
 //BA.debugLineNum = 494;BA.debugLine="If img5.Left >= slot1.Left And img5.Left <= slot1";
if (mostCurrent._img5.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._img5.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._img5.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._img5.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 495;BA.debugLine="img5.Left = slot1.Left + 10dip";
mostCurrent._img5.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 496;BA.debugLine="img5.Top = slot1.Top + 10dip";
mostCurrent._img5.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 497;BA.debugLine="img5.Tag = slot1.Tag";
mostCurrent._img5.setTag(mostCurrent._slot1.getTag());
 }else if(mostCurrent._img5.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._img5.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._img5.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._img5.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 499;BA.debugLine="img5.Left = slot2.Left + 10dip";
mostCurrent._img5.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 500;BA.debugLine="img5.Top = slot2.Top + 10dip";
mostCurrent._img5.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 501;BA.debugLine="img5.Tag = slot2.Tag";
mostCurrent._img5.setTag(mostCurrent._slot2.getTag());
 }else if(mostCurrent._img5.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._img5.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._img5.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._img5.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 503;BA.debugLine="img5.Left = slot3.Left + 10dip";
mostCurrent._img5.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 504;BA.debugLine="img5.Top = slot3.Top + 10dip";
mostCurrent._img5.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 505;BA.debugLine="img5.Tag = slot3.Tag";
mostCurrent._img5.setTag(mostCurrent._slot3.getTag());
 }else if(mostCurrent._img5.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._img5.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._img5.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._img5.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 507;BA.debugLine="img5.Left = slot4.Left + 10dip";
mostCurrent._img5.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 508;BA.debugLine="img5.Top = slot4.Top + 10dip";
mostCurrent._img5.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 509;BA.debugLine="img5.Tag = slot4.Tag";
mostCurrent._img5.setTag(mostCurrent._slot4.getTag());
 }else if(mostCurrent._img5.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._img5.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._img5.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._img5.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 511;BA.debugLine="img5.Left = slot5.Left + 10dip";
mostCurrent._img5.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 512;BA.debugLine="img5.Top = slot5.Top + 10dip";
mostCurrent._img5.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 513;BA.debugLine="img5.Tag = slot5.Tag";
mostCurrent._img5.setTag(mostCurrent._slot5.getTag());
 };
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static String  _loadlevel(int _numero) throws Exception{
String _ms = "";
 //BA.debugLineNum = 160;BA.debugLine="Sub LoadLevel(numero As Int)";
 //BA.debugLineNum = 161;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 162;BA.debugLine="lblNivel.Text = \"Nivel: \" & numero & \"/10\"";
mostCurrent._lblnivel.setText(BA.ObjectToCharSequence("Nivel: "+BA.NumberToString(_numero)+"/10"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 164;BA.debugLine="lblNivel.Text = \"Level: \" & numero & \"/10\"";
mostCurrent._lblnivel.setText(BA.ObjectToCharSequence("Level: "+BA.NumberToString(_numero)+"/10"));
 };
 //BA.debugLineNum = 167;BA.debugLine="GD1.SetOnGestureListener(img1, \"img1\")";
mostCurrent._gd1.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._img1.getObject()),"img1");
 //BA.debugLineNum = 168;BA.debugLine="GD2.SetOnGestureListener(img2, \"img2\")";
mostCurrent._gd2.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._img2.getObject()),"img2");
 //BA.debugLineNum = 169;BA.debugLine="GD3.SetOnGestureListener(img3, \"img3\")";
mostCurrent._gd3.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._img3.getObject()),"img3");
 //BA.debugLineNum = 170;BA.debugLine="GD4.SetOnGestureListener(img4, \"img4\")";
mostCurrent._gd4.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._img4.getObject()),"img4");
 //BA.debugLineNum = 171;BA.debugLine="GD5.SetOnGestureListener(img5, \"img5\")";
mostCurrent._gd5.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._img5.getObject()),"img5");
 //BA.debugLineNum = 174;BA.debugLine="slot1.Tag = \"1\"";
mostCurrent._slot1.setTag((Object)("1"));
 //BA.debugLineNum = 175;BA.debugLine="slot2.Tag = \"2\"";
mostCurrent._slot2.setTag((Object)("2"));
 //BA.debugLineNum = 176;BA.debugLine="slot3.Tag = \"3\"";
mostCurrent._slot3.setTag((Object)("3"));
 //BA.debugLineNum = 177;BA.debugLine="slot4.Tag = \"4\"";
mostCurrent._slot4.setTag((Object)("4"));
 //BA.debugLineNum = 178;BA.debugLine="slot5.Tag = \"5\"";
mostCurrent._slot5.setTag((Object)("5"));
 //BA.debugLineNum = 179;BA.debugLine="img1.Tag = \"\"";
mostCurrent._img1.setTag((Object)(""));
 //BA.debugLineNum = 180;BA.debugLine="img2.Tag = \"\"";
mostCurrent._img2.setTag((Object)(""));
 //BA.debugLineNum = 181;BA.debugLine="img3.Tag = \"\"";
mostCurrent._img3.setTag((Object)(""));
 //BA.debugLineNum = 182;BA.debugLine="img4.Tag = \"\"";
mostCurrent._img4.setTag((Object)(""));
 //BA.debugLineNum = 183;BA.debugLine="img5.Tag = \"\"";
mostCurrent._img5.setTag((Object)(""));
 //BA.debugLineNum = 184;BA.debugLine="organismo1 = \"\"";
mostCurrent._organismo1 = "";
 //BA.debugLineNum = 185;BA.debugLine="organismo2 = \"\"";
mostCurrent._organismo2 = "";
 //BA.debugLineNum = 186;BA.debugLine="organismo3 = \"\"";
mostCurrent._organismo3 = "";
 //BA.debugLineNum = 187;BA.debugLine="organismo4 = \"\"";
mostCurrent._organismo4 = "";
 //BA.debugLineNum = 188;BA.debugLine="organismo5 = \"\"";
mostCurrent._organismo5 = "";
 //BA.debugLineNum = 189;BA.debugLine="img1.Top = iniy1";
mostCurrent._img1.setTop(_iniy1);
 //BA.debugLineNum = 190;BA.debugLine="img2.Top = iniy1";
mostCurrent._img2.setTop(_iniy1);
 //BA.debugLineNum = 191;BA.debugLine="img3.Top = iniy1";
mostCurrent._img3.setTop(_iniy1);
 //BA.debugLineNum = 192;BA.debugLine="img4.Top = iniy1";
mostCurrent._img4.setTop(_iniy1);
 //BA.debugLineNum = 193;BA.debugLine="img5.Top = iniy1";
mostCurrent._img5.setTop(_iniy1);
 //BA.debugLineNum = 194;BA.debugLine="img1.Left = inix1";
mostCurrent._img1.setLeft(_inix1);
 //BA.debugLineNum = 195;BA.debugLine="img2.Left = inix2";
mostCurrent._img2.setLeft(_inix2);
 //BA.debugLineNum = 196;BA.debugLine="img3.Left = inix3";
mostCurrent._img3.setLeft(_inix3);
 //BA.debugLineNum = 197;BA.debugLine="img4.Left = inix4";
mostCurrent._img4.setLeft(_inix4);
 //BA.debugLineNum = 198;BA.debugLine="img5.Left = inix5";
mostCurrent._img5.setLeft(_inix5);
 //BA.debugLineNum = 199;BA.debugLine="img1.SetBackgroundImage(Null)";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 200;BA.debugLine="img2.SetBackgroundImage(Null)";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 201;BA.debugLine="img3.SetBackgroundImage(Null)";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 202;BA.debugLine="img4.SetBackgroundImage(Null)";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 203;BA.debugLine="img5.SetBackgroundImage(Null)";
mostCurrent._img5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 206;BA.debugLine="If numero = 1 Then";
if (_numero==1) { 
 //BA.debugLineNum = 207;BA.debugLine="img5.Visible = False";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 208;BA.debugLine="img4.Visible = False";
mostCurrent._img4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 209;BA.debugLine="slot4.Visible = False";
mostCurrent._slot4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 210;BA.debugLine="slot5.Visible = False";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 211;BA.debugLine="slot4.Left = 2000dip";
mostCurrent._slot4.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 212;BA.debugLine="slot5.Left = 2000dip";
mostCurrent._slot5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 214;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"3.gif").getObject()));
 //BA.debugLineNum = 215;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 216;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"1.gif").getObject()));
 //BA.debugLineNum = 217;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 218;BA.debugLine="organismo2 = \"alga\"";
mostCurrent._organismo2 = "alga";
 //BA.debugLineNum = 219;BA.debugLine="organismo3 = \"zooplancton\"";
mostCurrent._organismo3 = "zooplancton";
 //BA.debugLineNum = 220;BA.debugLine="solucion = \"213\"";
mostCurrent._solucion = "213";
 }else if(_numero==2) { 
 //BA.debugLineNum = 222;BA.debugLine="img4.Visible = False";
mostCurrent._img4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="img5.Visible = False";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 224;BA.debugLine="slot4.Visible = False";
mostCurrent._slot4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 225;BA.debugLine="slot5.Visible = False";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 226;BA.debugLine="slot4.Left = 2000dip";
mostCurrent._slot4.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 227;BA.debugLine="slot5.Left = 2000dip";
mostCurrent._slot5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 229;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"3.gif").getObject()));
 //BA.debugLineNum = 230;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"2.gif").getObject()));
 //BA.debugLineNum = 231;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 232;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 233;BA.debugLine="organismo2 = \"alga\"";
mostCurrent._organismo2 = "alga";
 //BA.debugLineNum = 234;BA.debugLine="organismo3 = \"zooplancton\"";
mostCurrent._organismo3 = "zooplancton";
 //BA.debugLineNum = 235;BA.debugLine="solucion = \"231\"";
mostCurrent._solucion = "231";
 }else if(_numero==3) { 
 //BA.debugLineNum = 237;BA.debugLine="img4.Visible = False";
mostCurrent._img4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 238;BA.debugLine="img5.Visible = False";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 239;BA.debugLine="slot4.Visible = False";
mostCurrent._slot4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 240;BA.debugLine="slot5.Visible = False";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 241;BA.debugLine="slot4.Left = 2000dip";
mostCurrent._slot4.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 242;BA.debugLine="slot5.Left = 2000dip";
mostCurrent._slot5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 244;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 245;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"9.gif").getObject()));
 //BA.debugLineNum = 246;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"4.gif").getObject()));
 //BA.debugLineNum = 247;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 248;BA.debugLine="organismo2 = \"diatomea\"";
mostCurrent._organismo2 = "diatomea";
 //BA.debugLineNum = 249;BA.debugLine="organismo3 = \"caracol\"";
mostCurrent._organismo3 = "caracol";
 //BA.debugLineNum = 250;BA.debugLine="solucion = \"132\"";
mostCurrent._solucion = "132";
 }else if(_numero==4) { 
 //BA.debugLineNum = 252;BA.debugLine="img4.Visible = True";
mostCurrent._img4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 253;BA.debugLine="slot4.Visible = True";
mostCurrent._slot4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 254;BA.debugLine="slot4.Left = slot3.Left + slot3.Width + 10dip";
mostCurrent._slot4.setLeft((int) (mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 255;BA.debugLine="img5.Visible = False";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 256;BA.debugLine="slot5.Visible = False";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 257;BA.debugLine="slot5.Left = 2000dip";
mostCurrent._slot5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 259;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 260;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"5.gif").getObject()));
 //BA.debugLineNum = 261;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"3.gif").getObject()));
 //BA.debugLineNum = 262;BA.debugLine="img4.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"1.gif").getObject()));
 //BA.debugLineNum = 263;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 264;BA.debugLine="organismo2 = \"alga\"";
mostCurrent._organismo2 = "alga";
 //BA.debugLineNum = 265;BA.debugLine="organismo3 = \"zooplancton\"";
mostCurrent._organismo3 = "zooplancton";
 //BA.debugLineNum = 266;BA.debugLine="organismo4 = \"peces\"";
mostCurrent._organismo4 = "peces";
 //BA.debugLineNum = 267;BA.debugLine="solucion = \"1423\"";
mostCurrent._solucion = "1423";
 }else if(_numero==5) { 
 //BA.debugLineNum = 269;BA.debugLine="img5.Visible = False";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 270;BA.debugLine="slot5.Visible = False";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 271;BA.debugLine="slot5.Left = 2000dip";
mostCurrent._slot5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 273;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"8.gif").getObject()));
 //BA.debugLineNum = 274;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"4.gif").getObject()));
 //BA.debugLineNum = 275;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 276;BA.debugLine="img4.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"9.gif").getObject()));
 //BA.debugLineNum = 277;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 278;BA.debugLine="organismo2 = \"alga\"";
mostCurrent._organismo2 = "alga";
 //BA.debugLineNum = 279;BA.debugLine="organismo3 = \"caracol\"";
mostCurrent._organismo3 = "caracol";
 //BA.debugLineNum = 280;BA.debugLine="organismo4 = \"aves\"";
mostCurrent._organismo4 = "aves";
 //BA.debugLineNum = 281;BA.debugLine="solucion = \"4213\"";
mostCurrent._solucion = "4213";
 }else if(_numero==6) { 
 //BA.debugLineNum = 283;BA.debugLine="img5.Visible = False";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 284;BA.debugLine="slot5.Visible = False";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 285;BA.debugLine="slot5.Left = 2000dip";
mostCurrent._slot5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 287;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"5.gif").getObject()));
 //BA.debugLineNum = 288;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"3.gif").getObject()));
 //BA.debugLineNum = 289;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"2.gif").getObject()));
 //BA.debugLineNum = 290;BA.debugLine="img4.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"8.gif").getObject()));
 //BA.debugLineNum = 291;BA.debugLine="organismo1 = \"alga\"";
mostCurrent._organismo1 = "alga";
 //BA.debugLineNum = 292;BA.debugLine="organismo2 = \"zooplancton\"";
mostCurrent._organismo2 = "zooplancton";
 //BA.debugLineNum = 293;BA.debugLine="organismo3 = \"peces\"";
mostCurrent._organismo3 = "peces";
 //BA.debugLineNum = 294;BA.debugLine="organismo4 = \"aves\"";
mostCurrent._organismo4 = "aves";
 //BA.debugLineNum = 295;BA.debugLine="solucion = \"3124\"";
mostCurrent._solucion = "3124";
 }else if(_numero==7) { 
 //BA.debugLineNum = 297;BA.debugLine="img5.Visible = False";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 298;BA.debugLine="slot5.Visible = False";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 299;BA.debugLine="slot5.Left = 2000dip";
mostCurrent._slot5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (2000)));
 //BA.debugLineNum = 301;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"9.gif").getObject()));
 //BA.debugLineNum = 302;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"10.gif").getObject()));
 //BA.debugLineNum = 303;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 304;BA.debugLine="img4.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"8.gif").getObject()));
 //BA.debugLineNum = 305;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 306;BA.debugLine="organismo2 = \"planta\"";
mostCurrent._organismo2 = "planta";
 //BA.debugLineNum = 307;BA.debugLine="organismo3 = \"caracol\"";
mostCurrent._organismo3 = "caracol";
 //BA.debugLineNum = 308;BA.debugLine="organismo4 = \"aves\"";
mostCurrent._organismo4 = "aves";
 //BA.debugLineNum = 309;BA.debugLine="solucion = \"3214\"";
mostCurrent._solucion = "3214";
 }else if(_numero==8) { 
 //BA.debugLineNum = 311;BA.debugLine="img5.Visible = True";
mostCurrent._img5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 312;BA.debugLine="slot5.Visible = True";
mostCurrent._slot5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 313;BA.debugLine="slot5.Left = slot4.Left + slot4.Width + 10dip";
mostCurrent._slot5.setLeft((int) (mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10))));
 //BA.debugLineNum = 316;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"8.gif").getObject()));
 //BA.debugLineNum = 317;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 318;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"3.gif").getObject()));
 //BA.debugLineNum = 319;BA.debugLine="img4.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"1.gif").getObject()));
 //BA.debugLineNum = 320;BA.debugLine="img5.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"5.gif").getObject()));
 //BA.debugLineNum = 321;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 322;BA.debugLine="organismo2 = \"alga\"";
mostCurrent._organismo2 = "alga";
 //BA.debugLineNum = 323;BA.debugLine="organismo3 = \"zooplancton\"";
mostCurrent._organismo3 = "zooplancton";
 //BA.debugLineNum = 324;BA.debugLine="organismo4 = \"peces\"";
mostCurrent._organismo4 = "peces";
 //BA.debugLineNum = 325;BA.debugLine="organismo5 = \"aves\"";
mostCurrent._organismo5 = "aves";
 //BA.debugLineNum = 326;BA.debugLine="solucion = \"51234\"";
mostCurrent._solucion = "51234";
 }else if(_numero==9) { 
 //BA.debugLineNum = 329;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 330;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"4.gif").getObject()));
 //BA.debugLineNum = 331;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"2.gif").getObject()));
 //BA.debugLineNum = 332;BA.debugLine="img4.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"5.gif").getObject()));
 //BA.debugLineNum = 333;BA.debugLine="img5.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"8.gif").getObject()));
 //BA.debugLineNum = 334;BA.debugLine="solucion = \"12345\"";
mostCurrent._solucion = "12345";
 //BA.debugLineNum = 335;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 336;BA.debugLine="organismo2 = \"diatomea\"";
mostCurrent._organismo2 = "diatomea";
 //BA.debugLineNum = 337;BA.debugLine="organismo3 = \"zooplancton\"";
mostCurrent._organismo3 = "zooplancton";
 //BA.debugLineNum = 338;BA.debugLine="organismo4 = \"peces\"";
mostCurrent._organismo4 = "peces";
 //BA.debugLineNum = 339;BA.debugLine="organismo5 = \"aves\"";
mostCurrent._organismo5 = "aves";
 }else if(_numero==10) { 
 //BA.debugLineNum = 342;BA.debugLine="img1.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img1.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"8.gif").getObject()));
 //BA.debugLineNum = 343;BA.debugLine="img2.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img2.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"4.gif").getObject()));
 //BA.debugLineNum = 344;BA.debugLine="img3.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img3.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"2.gif").getObject()));
 //BA.debugLineNum = 345;BA.debugLine="img4.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img4.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"9.gif").getObject()));
 //BA.debugLineNum = 346;BA.debugLine="img5.SetBackgroundImage(LoadBitmap(File.DirAsset";
mostCurrent._img5.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"11.gif").getObject()));
 //BA.debugLineNum = 347;BA.debugLine="organismo1 = \"sol\"";
mostCurrent._organismo1 = "sol";
 //BA.debugLineNum = 348;BA.debugLine="organismo2 = \"diatomea\"";
mostCurrent._organismo2 = "diatomea";
 //BA.debugLineNum = 349;BA.debugLine="organismo3 = \"zooplancton\"";
mostCurrent._organismo3 = "zooplancton";
 //BA.debugLineNum = 350;BA.debugLine="organismo4 = \"caracol\"";
mostCurrent._organismo4 = "caracol";
 //BA.debugLineNum = 351;BA.debugLine="organismo5 = \"aves\"";
mostCurrent._organismo5 = "aves";
 //BA.debugLineNum = 352;BA.debugLine="solucion = \"52341\"";
mostCurrent._solucion = "52341";
 }else if(_numero==11) { 
 //BA.debugLineNum = 354;BA.debugLine="Dim ms As String";
_ms = "";
 //BA.debugLineNum = 355;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 362;BA.debugLine="If ms = DialogResponse.POSITIVE Then";
if ((_ms).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 363;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 364;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 };
 //BA.debugLineNum = 368;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim verInstruccionesTrofica As Boolean";
_verinstruccionestrofica = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim origen As String";
_origen = "";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 83;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 84;BA.debugLine="btnCheck.Text = \"Check\"";
mostCurrent._btncheck.setText(BA.ObjectToCharSequence("Check"));
 //BA.debugLineNum = 85;BA.debugLine="Label1.Text = \"Complete the trophic network\"";
mostCurrent._label1.setText(BA.ObjectToCharSequence("Complete the trophic network"));
 };
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static String  _verexplicacion(String _explicar) throws Exception{
 //BA.debugLineNum = 582;BA.debugLine="Sub VerExplicacion(explicar As String)";
 //BA.debugLineNum = 583;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 584;BA.debugLine="If explicar = \"sol\" Then";
if ((_explicar).equals("sol")) { 
 //BA.debugLineNum = 585;BA.debugLine="Return \"El sol proporciona la energía lumínica";
if (true) return "El sol proporciona la energía lumínica al sistema";
 }else if((_explicar).equals("alga")) { 
 //BA.debugLineNum = 587;BA.debugLine="Return \"Las algas convierten la energía del sol";
if (true) return "Las algas convierten la energía del sol a energía química";
 }else if((_explicar).equals("zooplancton")) { 
 //BA.debugLineNum = 589;BA.debugLine="Return \"El zooplancton (tanto copépodos, rotífe";
if (true) return "El zooplancton (tanto copépodos, rotíferos o ciliados) se alimentan de las algas";
 }else if((_explicar).equals("peces")) { 
 //BA.debugLineNum = 591;BA.debugLine="Return \"Los peces comen zooplankton, algas u ot";
if (true) return "Los peces comen zooplankton, algas u otros peces";
 }else if((_explicar).equals("aves")) { 
 //BA.debugLineNum = 593;BA.debugLine="Return \"Las aves se alimentan de peces o de inv";
if (true) return "Las aves se alimentan de peces o de invertebrados, como caracoles y cangrejos";
 }else if((_explicar).equals("diatomea")) { 
 //BA.debugLineNum = 595;BA.debugLine="Return \"Las diatomeas son algas, que convierten";
if (true) return "Las diatomeas son algas, que convierten la energía del sol a energía química";
 }else if((_explicar).equals("planta")) { 
 //BA.debugLineNum = 597;BA.debugLine="Return \"Las macrófitas (o plantas acuáticas) co";
if (true) return "Las macrófitas (o plantas acuáticas) convierten la energía del sol a energía química";
 }else if((_explicar).equals("caracol")) { 
 //BA.debugLineNum = 599;BA.debugLine="Return \"Los invertebrados (como los caracoles),";
if (true) return "Los invertebrados (como los caracoles), se alimentan de algas y plantas";
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 602;BA.debugLine="If explicar = \"sol\" Then";
if ((_explicar).equals("sol")) { 
 //BA.debugLineNum = 603;BA.debugLine="Return \"The sun provides the luminic energy int";
if (true) return "The sun provides the luminic energy into the system";
 }else if((_explicar).equals("alga")) { 
 //BA.debugLineNum = 605;BA.debugLine="Return \"Algae turn the luminic energy into chem";
if (true) return "Algae turn the luminic energy into chemical energy";
 }else if((_explicar).equals("zooplancton")) { 
 //BA.debugLineNum = 607;BA.debugLine="Return \"Zooplankton (copeopds, rotifers, ciliat";
if (true) return "Zooplankton (copeopds, rotifers, ciliates) eat the algae";
 }else if((_explicar).equals("peces")) { 
 //BA.debugLineNum = 609;BA.debugLine="Return \"Fish eat zooplankton, algae or even oth";
if (true) return "Fish eat zooplankton, algae or even other fish!";
 }else if((_explicar).equals("aves")) { 
 //BA.debugLineNum = 611;BA.debugLine="Return \"Birds feed on fish or invertebrates, su";
if (true) return "Birds feed on fish or invertebrates, such as snails and crabs";
 }else if((_explicar).equals("diatomea")) { 
 //BA.debugLineNum = 613;BA.debugLine="Return \"Diatoms are algae, that turn the lumini";
if (true) return "Diatoms are algae, that turn the luminic energy into chemical energy";
 }else if((_explicar).equals("planta")) { 
 //BA.debugLineNum = 615;BA.debugLine="Return \"Aquatic plants turn the luminic energy";
if (true) return "Aquatic plants turn the luminic energy into chemical energy";
 }else if((_explicar).equals("caracol")) { 
 //BA.debugLineNum = 617;BA.debugLine="Return \"Invertebrates (such as snails), feed on";
if (true) return "Invertebrates (such as snails), feed on algae an plants";
 };
 };
 //BA.debugLineNum = 624;BA.debugLine="End Sub";
return "";
}
}
