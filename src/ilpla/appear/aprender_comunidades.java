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

public class aprender_comunidades extends Activity implements B4AActivity{
	public static aprender_comunidades mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.aprender_comunidades");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (aprender_comunidades).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.aprender_comunidades");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.aprender_comunidades", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (aprender_comunidades) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (aprender_comunidades) Resume **");
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
		return aprender_comunidades.class;
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
            BA.LogInfo("** Activity (aprender_comunidades) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (aprender_comunidades) Pause event (activity is not paused). **");
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
            aprender_comunidades mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (aprender_comunidades) Resume **");
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
public static boolean _verinstruccionescomunidades = false;
public static String _origen = "";
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd1 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd2 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd3 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd4 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd5 = null;
public dominex.slidingpanels.slidingpanels _sd = null;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.objects.LabelWrapper _word1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _word2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _word3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _word4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _word5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _word6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _word7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot2 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _slot7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreset = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnhelp = null;
public static String _explicacion1 = "";
public static String _explicacion2 = "";
public static String _explicacion3 = "";
public static String _explicacion4 = "";
public static String _explicacion5 = "";
public static String _explicacion6 = "";
public static String _explicacion7 = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _checkok1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _checkok2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _checkok3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _checkok4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _checkok5 = null;
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
 //BA.debugLineNum = 57;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 60;BA.debugLine="If origen = \"menu\" Then";
if ((_origen).equals("menu")) { 
 //BA.debugLineNum = 61;BA.debugLine="verInstruccionesComunidades = True";
_verinstruccionescomunidades = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 64;BA.debugLine="Activity.LoadLayout(\"Aprender_Comunidades\")";
mostCurrent._activity.LoadLayout("Aprender_Comunidades",mostCurrent.activityBA);
 //BA.debugLineNum = 65;BA.debugLine="CargaInstrucciones";
_cargainstrucciones();
 //BA.debugLineNum = 67;BA.debugLine="checkOK1.Initialize(\"checkOK1\")";
mostCurrent._checkok1.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 68;BA.debugLine="checkOK1.Gravity = Gravity.FILL";
mostCurrent._checkok1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 69;BA.debugLine="checkOK1.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 70;BA.debugLine="checkOK2.Initialize(\"checkOK2\")";
mostCurrent._checkok2.Initialize(mostCurrent.activityBA,"checkOK2");
 //BA.debugLineNum = 71;BA.debugLine="checkOK2.Gravity = Gravity.FILL";
mostCurrent._checkok2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 72;BA.debugLine="checkOK2.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 73;BA.debugLine="checkOK3.Initialize(\"checkOK3\")";
mostCurrent._checkok3.Initialize(mostCurrent.activityBA,"checkOK3");
 //BA.debugLineNum = 74;BA.debugLine="checkOK3.Gravity = Gravity.FILL";
mostCurrent._checkok3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 75;BA.debugLine="checkOK3.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 76;BA.debugLine="checkOK4.Initialize(\"checkOK4\")";
mostCurrent._checkok4.Initialize(mostCurrent.activityBA,"checkOK4");
 //BA.debugLineNum = 77;BA.debugLine="checkOK4.Gravity = Gravity.FILL";
mostCurrent._checkok4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 78;BA.debugLine="checkOK4.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 79;BA.debugLine="checkOK5.Initialize(\"checkOK5\")";
mostCurrent._checkok5.Initialize(mostCurrent.activityBA,"checkOK5");
 //BA.debugLineNum = 80;BA.debugLine="checkOK5.Gravity = Gravity.FILL";
mostCurrent._checkok5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 81;BA.debugLine="checkOK5.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 83;BA.debugLine="Activity.AddView(checkOK1, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 84;BA.debugLine="Activity.AddView(checkOK2, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 85;BA.debugLine="Activity.AddView(checkOK3, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 86;BA.debugLine="Activity.AddView(checkOK4, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 87;BA.debugLine="Activity.AddView(checkOK5, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 96;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 98;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 598;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 599;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 600;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 601;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_click() throws Exception{
 //BA.debugLineNum = 603;BA.debugLine="Sub btnHelp_Click";
 //BA.debugLineNum = 604;BA.debugLine="verInstruccionesComunidades = True";
_verinstruccionescomunidades = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 605;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 606;BA.debugLine="End Sub";
return "";
}
public static String  _btnreset_click() throws Exception{
 //BA.debugLineNum = 593;BA.debugLine="Sub btnReset_Click";
 //BA.debugLineNum = 594;BA.debugLine="verInstruccionesComunidades = True";
_verinstruccionescomunidades = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 595;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 596;BA.debugLine="End Sub";
return "";
}
public static String  _butok_click() throws Exception{
 //BA.debugLineNum = 329;BA.debugLine="Sub butOK_Click";
 //BA.debugLineNum = 330;BA.debugLine="verInstruccionesComunidades = False";
_verinstruccionescomunidades = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 331;BA.debugLine="origen = \"\"";
_origen = "";
 //BA.debugLineNum = 332;BA.debugLine="p.SetScreenOrientation(0)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (0));
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return "";
}
public static String  _cargainstrucciones() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _img1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img4 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img5 = null;
anywheresoftware.b4a.objects.LabelWrapper _headerinst = null;
anywheresoftware.b4a.objects.LabelWrapper _header1 = null;
anywheresoftware.b4a.objects.LabelWrapper _header2 = null;
anywheresoftware.b4a.objects.LabelWrapper _header3 = null;
anywheresoftware.b4a.objects.LabelWrapper _header4 = null;
anywheresoftware.b4a.objects.LabelWrapper _header5 = null;
anywheresoftware.b4a.objects.LabelWrapper _textoinstruccion = null;
anywheresoftware.b4a.objects.LabelWrapper _texto1 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto2 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto3 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto4 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto5 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel1 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel2 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel3 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel4 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel5 = null;
String _lineinst = "";
String _line1 = "";
String _line2 = "";
String _line3 = "";
String _line4 = "";
String _line5 = "";
anywheresoftware.b4a.objects.LabelWrapper _flechas = null;
anywheresoftware.b4a.objects.ButtonWrapper _butok = null;
 //BA.debugLineNum = 137;BA.debugLine="Sub CargaInstrucciones";
 //BA.debugLineNum = 138;BA.debugLine="If verInstruccionesComunidades = False Then";
if (_verinstruccionescomunidades==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 139;BA.debugLine="LoadLevel";
_loadlevel();
 //BA.debugLineNum = 140;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 141;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 144;BA.debugLine="SD.Initialize(\"SD\",300,Activity,Me,False) 'Initia";
mostCurrent._sd._initialize(mostCurrent.activityBA,"SD",(int) (300),(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._activity.getObject())),aprender_comunidades.getObject(),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 145;BA.debugLine="SD.ModeFullScreen(6,False) 'Creates the mode of S";
mostCurrent._sd._modefullscreen((int) (6),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 146;BA.debugLine="SD.panels(0).Color = Colors.White";
mostCurrent._sd._panels[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 147;BA.debugLine="SD.panels(1).Color = Colors.White";
mostCurrent._sd._panels[(int) (1)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 148;BA.debugLine="SD.panels(2).Color = Colors.White";
mostCurrent._sd._panels[(int) (2)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 149;BA.debugLine="SD.panels(3).Color = Colors.White";
mostCurrent._sd._panels[(int) (3)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 150;BA.debugLine="SD.panels(4).Color = Colors.White";
mostCurrent._sd._panels[(int) (4)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 151;BA.debugLine="SD.panels(5).Color = Colors.White";
mostCurrent._sd._panels[(int) (5)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 152;BA.debugLine="SD.Start(0) 'Start the SlidingPanels.";
mostCurrent._sd._start((int) (0));
 //BA.debugLineNum = 156;BA.debugLine="Dim img1 As ImageView";
_img1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 157;BA.debugLine="Dim img2 As ImageView";
_img2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 158;BA.debugLine="Dim img3 As ImageView";
_img3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 159;BA.debugLine="Dim img4 As ImageView";
_img4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 160;BA.debugLine="Dim img5 As ImageView";
_img5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 162;BA.debugLine="img1.Initialize(\"\")";
_img1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 163;BA.debugLine="img2.Initialize(\"\")";
_img2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 164;BA.debugLine="img3.Initialize(\"\")";
_img3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 165;BA.debugLine="img4.Initialize(\"\")";
_img4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 166;BA.debugLine="img5.Initialize(\"\")";
_img5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 168;BA.debugLine="img1.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"comunidades1.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 169;BA.debugLine="img2.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"comunidades2.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 170;BA.debugLine="img3.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"comunidades3.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 171;BA.debugLine="img4.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"comunidades4.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 172;BA.debugLine="img5.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"comunidades5.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 173;BA.debugLine="img1.Gravity = Gravity.FILL";
_img1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 174;BA.debugLine="img2.Gravity = Gravity.FILL";
_img2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 175;BA.debugLine="img3.Gravity = Gravity.FILL";
_img3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 176;BA.debugLine="img4.Gravity = Gravity.FILL";
_img4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 177;BA.debugLine="img5.Gravity = Gravity.FILL";
_img5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 180;BA.debugLine="Dim headerInst As Label";
_headerinst = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 181;BA.debugLine="Dim header1 As Label";
_header1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 182;BA.debugLine="Dim header2 As Label";
_header2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 183;BA.debugLine="Dim header3 As Label";
_header3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 184;BA.debugLine="Dim header4 As Label";
_header4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 185;BA.debugLine="Dim header5 As Label";
_header5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 186;BA.debugLine="headerInst.Initialize(\"\")";
_headerinst.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 187;BA.debugLine="header1.Initialize(\"\")";
_header1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 188;BA.debugLine="header2.Initialize(\"\")";
_header2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 189;BA.debugLine="header3.Initialize(\"\")";
_header3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 190;BA.debugLine="header4.Initialize(\"\")";
_header4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 191;BA.debugLine="header5.Initialize(\"\")";
_header5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 192;BA.debugLine="headerInst.TextColor = Colors.Black";
_headerinst.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 193;BA.debugLine="header1.TextColor = Colors.Black";
_header1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 194;BA.debugLine="header2.TextColor = Colors.Black";
_header2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 195;BA.debugLine="header3.TextColor = Colors.Black";
_header3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 196;BA.debugLine="header4.TextColor = Colors.Black";
_header4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 197;BA.debugLine="header5.TextColor = Colors.Black";
_header5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 198;BA.debugLine="headerInst.TextSize = 16";
_headerinst.setTextSize((float) (16));
 //BA.debugLineNum = 199;BA.debugLine="header1.TextSize = 26";
_header1.setTextSize((float) (26));
 //BA.debugLineNum = 200;BA.debugLine="header2.TextSize = 26";
_header2.setTextSize((float) (26));
 //BA.debugLineNum = 201;BA.debugLine="header3.TextSize = 26";
_header3.setTextSize((float) (26));
 //BA.debugLineNum = 202;BA.debugLine="header4.TextSize = 26";
_header4.setTextSize((float) (26));
 //BA.debugLineNum = 203;BA.debugLine="header5.TextSize = 26";
_header5.setTextSize((float) (26));
 //BA.debugLineNum = 204;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 205;BA.debugLine="headerInst.Text = \"Las Comunidades acuáticas\"";
_headerinst.setText(BA.ObjectToCharSequence("Las Comunidades acuáticas"));
 //BA.debugLineNum = 206;BA.debugLine="header1.Text = \"Plancton\"";
_header1.setText(BA.ObjectToCharSequence("Plancton"));
 //BA.debugLineNum = 207;BA.debugLine="header2.Text = \"Necton\"";
_header2.setText(BA.ObjectToCharSequence("Necton"));
 //BA.debugLineNum = 208;BA.debugLine="header3.Text = \"Neuston\"";
_header3.setText(BA.ObjectToCharSequence("Neuston"));
 //BA.debugLineNum = 209;BA.debugLine="header4.Text = \"Bentos\"";
_header4.setText(BA.ObjectToCharSequence("Bentos"));
 //BA.debugLineNum = 210;BA.debugLine="header5.Text = \"Macrófitas\"";
_header5.setText(BA.ObjectToCharSequence("Macrófitas"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 212;BA.debugLine="headerInst.Text = \"Aquatic communities\"";
_headerinst.setText(BA.ObjectToCharSequence("Aquatic communities"));
 //BA.debugLineNum = 213;BA.debugLine="header1.Text = \"Plankton\"";
_header1.setText(BA.ObjectToCharSequence("Plankton"));
 //BA.debugLineNum = 214;BA.debugLine="header2.Text = \"Nekton\"";
_header2.setText(BA.ObjectToCharSequence("Nekton"));
 //BA.debugLineNum = 215;BA.debugLine="header3.Text = \"Neuston\"";
_header3.setText(BA.ObjectToCharSequence("Neuston"));
 //BA.debugLineNum = 216;BA.debugLine="header4.Text = \"Benthos\"";
_header4.setText(BA.ObjectToCharSequence("Benthos"));
 //BA.debugLineNum = 217;BA.debugLine="header5.Text = \"Macrophytes\"";
_header5.setText(BA.ObjectToCharSequence("Macrophytes"));
 };
 //BA.debugLineNum = 222;BA.debugLine="Dim textoInstruccion As Label";
_textoinstruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 223;BA.debugLine="Dim texto1 As Label";
_texto1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 224;BA.debugLine="Dim texto2 As Label";
_texto2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 225;BA.debugLine="Dim texto3 As Label";
_texto3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 226;BA.debugLine="Dim texto4 As Label";
_texto4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 227;BA.debugLine="Dim texto5 As Label";
_texto5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 229;BA.debugLine="textoInstruccion.Initialize(\"\")";
_textoinstruccion.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 230;BA.debugLine="texto1.Initialize(\"\")";
_texto1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 231;BA.debugLine="texto2.Initialize(\"\")";
_texto2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 232;BA.debugLine="texto3.Initialize(\"\")";
_texto3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 233;BA.debugLine="texto4.Initialize(\"\")";
_texto4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 234;BA.debugLine="texto5.Initialize(\"\")";
_texto5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 235;BA.debugLine="textoInstruccion.TextColor = Colors.Black";
_textoinstruccion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 236;BA.debugLine="texto1.TextColor = Colors.Black";
_texto1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 237;BA.debugLine="texto2.TextColor = Colors.Black";
_texto2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 238;BA.debugLine="texto3.TextColor = Colors.Black";
_texto3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 239;BA.debugLine="texto4.TextColor = Colors.Black";
_texto4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 240;BA.debugLine="texto5.TextColor = Colors.Black";
_texto5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 242;BA.debugLine="Dim panel1 As ScrollView";
_panel1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 243;BA.debugLine="Dim panel2 As ScrollView";
_panel2 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 244;BA.debugLine="Dim panel3 As ScrollView";
_panel3 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 245;BA.debugLine="Dim panel4 As ScrollView";
_panel4 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 246;BA.debugLine="Dim panel5 As ScrollView";
_panel5 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 248;BA.debugLine="panel1.Initialize(500dip)";
_panel1.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 249;BA.debugLine="panel2.Initialize(500dip)";
_panel2.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 250;BA.debugLine="panel3.Initialize(500dip)";
_panel3.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 251;BA.debugLine="panel4.Initialize(500dip)";
_panel4.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 252;BA.debugLine="panel5.Initialize(500dip)";
_panel5.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 254;BA.debugLine="Dim lineInst As String";
_lineinst = "";
 //BA.debugLineNum = 255;BA.debugLine="lineInst = File.ReadString(File.DirAssets, Mai";
_lineinst = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-comunidadesInst.txt");
 //BA.debugLineNum = 256;BA.debugLine="Dim line1 As String";
_line1 = "";
 //BA.debugLineNum = 257;BA.debugLine="line1 = File.ReadString(File.DirAssets, Main.l";
_line1 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-comunidades1.txt");
 //BA.debugLineNum = 258;BA.debugLine="Dim line2 As String";
_line2 = "";
 //BA.debugLineNum = 259;BA.debugLine="line2 = File.ReadString(File.DirAssets, Main.l";
_line2 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-comunidades2.txt");
 //BA.debugLineNum = 260;BA.debugLine="Dim line3 As String";
_line3 = "";
 //BA.debugLineNum = 261;BA.debugLine="line3 = File.ReadString(File.DirAssets, Main.l";
_line3 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-comunidades3.txt");
 //BA.debugLineNum = 262;BA.debugLine="Dim line4 As String";
_line4 = "";
 //BA.debugLineNum = 263;BA.debugLine="line4 = File.ReadString(File.DirAssets, Main.l";
_line4 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-comunidades4.txt");
 //BA.debugLineNum = 264;BA.debugLine="Dim line5 As String";
_line5 = "";
 //BA.debugLineNum = 265;BA.debugLine="line5 = File.ReadString(File.DirAssets, Main.l";
_line5 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-comunidades5.txt");
 //BA.debugLineNum = 267;BA.debugLine="textoInstruccion.Text = lineInst";
_textoinstruccion.setText(BA.ObjectToCharSequence(_lineinst));
 //BA.debugLineNum = 268;BA.debugLine="texto1.Text = line1";
_texto1.setText(BA.ObjectToCharSequence(_line1));
 //BA.debugLineNum = 269;BA.debugLine="texto2.Text = line2";
_texto2.setText(BA.ObjectToCharSequence(_line2));
 //BA.debugLineNum = 270;BA.debugLine="texto3.Text = line3";
_texto3.setText(BA.ObjectToCharSequence(_line3));
 //BA.debugLineNum = 271;BA.debugLine="texto4.Text = line4";
_texto4.setText(BA.ObjectToCharSequence(_line4));
 //BA.debugLineNum = 272;BA.debugLine="texto5.Text = line5";
_texto5.setText(BA.ObjectToCharSequence(_line5));
 //BA.debugLineNum = 277;BA.debugLine="SD.panels(1).AddView(img1,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_img1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 278;BA.debugLine="SD.panels(2).AddView(img2,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_img2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 279;BA.debugLine="SD.panels(3).AddView(img3,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_img3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 280;BA.debugLine="SD.panels(4).AddView(img4,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (4)].AddView((android.view.View)(_img4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 281;BA.debugLine="SD.panels(5).AddView(img5,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (5)].AddView((android.view.View)(_img5.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 283;BA.debugLine="SD.panels(0).AddView(headerInst,5%x,5%y,90%x,90%y";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_headerinst.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 284;BA.debugLine="SD.panels(1).AddView(header1,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_header1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 285;BA.debugLine="SD.panels(2).AddView(header2,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_header2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 286;BA.debugLine="SD.panels(3).AddView(header3,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_header3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 287;BA.debugLine="SD.panels(4).AddView(header4,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (4)].AddView((android.view.View)(_header4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 288;BA.debugLine="SD.panels(5).AddView(header5,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (5)].AddView((android.view.View)(_header5.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 290;BA.debugLine="SD.panels(0).AddView(textoInstruccion,5%x,10%y,90";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_textoinstruccion.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 291;BA.debugLine="SD.panels(1).AddView(panel1,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_panel1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 292;BA.debugLine="SD.panels(2).AddView(panel2,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_panel2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 293;BA.debugLine="SD.panels(3).AddView(panel3,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_panel3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 294;BA.debugLine="SD.panels(4).AddView(panel4,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (4)].AddView((android.view.View)(_panel4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 295;BA.debugLine="SD.panels(5).AddView(panel5,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (5)].AddView((android.view.View)(_panel5.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 297;BA.debugLine="panel1.Panel.AddView(texto1,0,0,90%x,50%y)";
_panel1.getPanel().AddView((android.view.View)(_texto1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 298;BA.debugLine="panel2.Panel.AddView(texto2,0,0,90%x,50%y)";
_panel2.getPanel().AddView((android.view.View)(_texto2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 299;BA.debugLine="panel3.Panel.AddView(texto3,0,0,90%x,50%y)";
_panel3.getPanel().AddView((android.view.View)(_texto3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 300;BA.debugLine="panel4.Panel.AddView(texto4,0,0,90%x,50%y)";
_panel4.getPanel().AddView((android.view.View)(_texto4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 301;BA.debugLine="panel5.Panel.AddView(texto5,0,0,90%x,50%y)";
_panel5.getPanel().AddView((android.view.View)(_texto5.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 304;BA.debugLine="Dim flechas As Label";
_flechas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 305;BA.debugLine="flechas.Initialize(\"\")";
_flechas.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 306;BA.debugLine="flechas.TextSize = 18";
_flechas.setTextSize((float) (18));
 //BA.debugLineNum = 307;BA.debugLine="flechas.TextColor = Colors.ARGB(255, 73,202,138)";
_flechas.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 308;BA.debugLine="flechas.Gravity = Gravity.CENTER";
_flechas.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 309;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 310;BA.debugLine="flechas.Text = \"<< Scroll for more info >>\"";
_flechas.setText(BA.ObjectToCharSequence("<< Scroll for more info >>"));
 }else {
 //BA.debugLineNum = 312;BA.debugLine="flechas.Text = \"<< Desliza para mas información";
_flechas.setText(BA.ObjectToCharSequence("<< Desliza para mas información >>"));
 };
 //BA.debugLineNum = 316;BA.debugLine="Dim butOK As Button";
_butok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 317;BA.debugLine="butOK.Initialize(\"butOK\")";
_butok.Initialize(mostCurrent.activityBA,"butOK");
 //BA.debugLineNum = 318;BA.debugLine="butOK.Text = \"Jugar\"";
_butok.setText(BA.ObjectToCharSequence("Jugar"));
 //BA.debugLineNum = 319;BA.debugLine="butOK.Color = Colors.ARGB(255,73,202,138)";
_butok.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 320;BA.debugLine="butOK.TextColor = Colors.White";
_butok.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 322;BA.debugLine="Activity.AddView(butOK, Activity.Width - 35%x ,0,";
mostCurrent._activity.AddView((android.view.View)(_butok.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (35),mostCurrent.activityBA)),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (35),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 323;BA.debugLine="Activity.AddView(flechas, 0, 90%y, 100%x, 10%y)";
mostCurrent._activity.AddView((android.view.View)(_flechas.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 326;BA.debugLine="End Sub";
return "";
}
public static String  _chequear() throws Exception{
String _ms = "";
 //BA.debugLineNum = 563;BA.debugLine="Sub Chequear";
 //BA.debugLineNum = 564;BA.debugLine="If word1.Tag = \"OK\" And word2.Tag = \"OK\" And word";
if ((mostCurrent._word1.getTag()).equals((Object)("OK")) && (mostCurrent._word2.getTag()).equals((Object)("OK")) && (mostCurrent._word3.getTag()).equals((Object)("OK")) && (mostCurrent._word4.getTag()).equals((Object)("OK")) && (mostCurrent._word5.getTag()).equals((Object)("OK"))) { 
 //BA.debugLineNum = 565;BA.debugLine="word1.Enabled = False";
mostCurrent._word1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 566;BA.debugLine="word2.Enabled = False";
mostCurrent._word2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 567;BA.debugLine="word3.Enabled = False";
mostCurrent._word3.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 568;BA.debugLine="word4.Enabled = False";
mostCurrent._word4.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 569;BA.debugLine="word5.Enabled = False";
mostCurrent._word5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 571;BA.debugLine="Dim ms As String";
_ms = "";
 //BA.debugLineNum = 572;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 573;BA.debugLine="ms = utilidades.Mensaje(\"Congratulations!\",\"Nul";
_ms = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Congratulations!","Null","You have correctly named every aquatic community!","","OK","","",anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 575;BA.debugLine="ms = utilidades.Mensaje(\"Felicitaciones!\",\"Null";
_ms = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Felicitaciones!","Null","Has nombrado correctamente a cada comunidad acuática!","","OK","","",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 578;BA.debugLine="If ms = DialogResponse.POSITIVE Then";
if ((_ms).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 579;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 580;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 };
 //BA.debugLineNum = 584;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 17;BA.debugLine="Dim SD As SlidingPanels";
mostCurrent._sd = new dominex.slidingpanels.slidingpanels();
 //BA.debugLineNum = 19;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 20;BA.debugLine="Private word1 As Label";
mostCurrent._word1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private word2 As Label";
mostCurrent._word2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private word3 As Label";
mostCurrent._word3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private word4 As Label";
mostCurrent._word4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private word5 As Label";
mostCurrent._word5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private word6 As Label";
mostCurrent._word6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private word7 As Label";
mostCurrent._word7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private slot1 As Panel";
mostCurrent._slot1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private slot2 As Panel";
mostCurrent._slot2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private slot3 As Panel";
mostCurrent._slot3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private slot4 As Panel";
mostCurrent._slot4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private slot5 As Panel";
mostCurrent._slot5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private slot6 As Panel";
mostCurrent._slot6 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private slot7 As Panel";
mostCurrent._slot7 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnReset As Button";
mostCurrent._btnreset = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnHelp As Button";
mostCurrent._btnhelp = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim explicacion1 As String";
mostCurrent._explicacion1 = "";
 //BA.debugLineNum = 42;BA.debugLine="Dim explicacion2 As String";
mostCurrent._explicacion2 = "";
 //BA.debugLineNum = 43;BA.debugLine="Dim explicacion3 As String";
mostCurrent._explicacion3 = "";
 //BA.debugLineNum = 44;BA.debugLine="Dim explicacion4 As String";
mostCurrent._explicacion4 = "";
 //BA.debugLineNum = 45;BA.debugLine="Dim explicacion5 As String";
mostCurrent._explicacion5 = "";
 //BA.debugLineNum = 46;BA.debugLine="Dim explicacion6 As String";
mostCurrent._explicacion6 = "";
 //BA.debugLineNum = 47;BA.debugLine="Dim explicacion7 As String";
mostCurrent._explicacion7 = "";
 //BA.debugLineNum = 49;BA.debugLine="Dim checkOK1 As ImageView";
mostCurrent._checkok1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Dim checkOK2 As ImageView";
mostCurrent._checkok2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim checkOK3 As ImageView";
mostCurrent._checkok3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim checkOK4 As ImageView";
mostCurrent._checkok4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim checkOK5 As ImageView";
mostCurrent._checkok5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _loadlevel() throws Exception{
 //BA.debugLineNum = 341;BA.debugLine="Sub LoadLevel";
 //BA.debugLineNum = 342;BA.debugLine="GD1.SetOnGestureListener(word1, \"word1\")";
mostCurrent._gd1.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word1.getObject()),"word1");
 //BA.debugLineNum = 343;BA.debugLine="GD2.SetOnGestureListener(word2, \"word2\")";
mostCurrent._gd2.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word2.getObject()),"word2");
 //BA.debugLineNum = 344;BA.debugLine="GD3.SetOnGestureListener(word3, \"word3\")";
mostCurrent._gd3.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word3.getObject()),"word3");
 //BA.debugLineNum = 345;BA.debugLine="GD4.SetOnGestureListener(word4, \"word4\")";
mostCurrent._gd4.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word4.getObject()),"word4");
 //BA.debugLineNum = 346;BA.debugLine="GD5.SetOnGestureListener(word5, \"word5\")";
mostCurrent._gd5.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word5.getObject()),"word5");
 //BA.debugLineNum = 349;BA.debugLine="slot1.Tag = \"1\"";
mostCurrent._slot1.setTag((Object)("1"));
 //BA.debugLineNum = 350;BA.debugLine="slot2.Tag = \"2\"";
mostCurrent._slot2.setTag((Object)("2"));
 //BA.debugLineNum = 351;BA.debugLine="slot3.Tag = \"3\"";
mostCurrent._slot3.setTag((Object)("3"));
 //BA.debugLineNum = 352;BA.debugLine="slot4.Tag = \"4\"";
mostCurrent._slot4.setTag((Object)("4"));
 //BA.debugLineNum = 353;BA.debugLine="slot5.Tag = \"5\"";
mostCurrent._slot5.setTag((Object)("5"));
 //BA.debugLineNum = 357;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim verInstruccionesComunidades As Boolean";
_verinstruccionescomunidades = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim origen As String";
_origen = "";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 107;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 108;BA.debugLine="explicacion1 = \"Those organisms that float in th";
mostCurrent._explicacion1 = "Those organisms that float in the water, such as phytoplankton (algae) or zooplankton.";
 //BA.debugLineNum = 109;BA.debugLine="explicacion2 = \"Those organisms capable of swimm";
mostCurrent._explicacion2 = "Those organisms capable of swimming and moving by their own means, such as fish.";
 //BA.debugLineNum = 110;BA.debugLine="explicacion3 = \"Small organisms that live in inm";
mostCurrent._explicacion3 = "Small organisms that live in inmediate contact with the water surface, in the air-water surface, right above or below it. Mainly insects.";
 //BA.debugLineNum = 111;BA.debugLine="explicacion4 = \"Animals and plants that attach t";
mostCurrent._explicacion4 = "Animals and plants that attach to the bottom or crawl in it, such as algae, plants, mollusks, bivalves and worms.";
 //BA.debugLineNum = 112;BA.debugLine="explicacion5 = \"Macrophytes are type of plant, s";
mostCurrent._explicacion5 = "Macrophytes are type of plant, specifically aquatic vegetation.";
 //BA.debugLineNum = 115;BA.debugLine="word1.Text = \"Plankton\"";
mostCurrent._word1.setText(BA.ObjectToCharSequence("Plankton"));
 //BA.debugLineNum = 116;BA.debugLine="word2.Text = \"Nekton\"";
mostCurrent._word2.setText(BA.ObjectToCharSequence("Nekton"));
 //BA.debugLineNum = 117;BA.debugLine="word3.text = \"Neuston\"";
mostCurrent._word3.setText(BA.ObjectToCharSequence("Neuston"));
 //BA.debugLineNum = 118;BA.debugLine="word4.Text = \"Benthos\"";
mostCurrent._word4.setText(BA.ObjectToCharSequence("Benthos"));
 //BA.debugLineNum = 119;BA.debugLine="word5.Text = \"Macrophyte\"";
mostCurrent._word5.setText(BA.ObjectToCharSequence("Macrophyte"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 121;BA.debugLine="explicacion1 = \"Los organismos que van a la deri";
mostCurrent._explicacion1 = "Los organismos que van a la deriva o que flotan en el agua, como el fitoplancton (algas) o el zooplancton.";
 //BA.debugLineNum = 122;BA.debugLine="explicacion2 = \"Todos aquellos organismos capace";
mostCurrent._explicacion2 = "Todos aquellos organismos capaces de nadar y desplazarse por sus propios medios, como los peces.";
 //BA.debugLineNum = 123;BA.debugLine="explicacion3 = \"Conjunto de organismos, de dimen";
mostCurrent._explicacion3 = "Conjunto de organismos, de dimensiones muy pequeñas, que viven en contacto inmediato con la lámina de agua superficial, en la fase de contacto aire-agua, por encima o por debajo de ella. Principalmente son insectos.";
 //BA.debugLineNum = 124;BA.debugLine="explicacion4 = \"Los animales y plantas que se fi";
mostCurrent._explicacion4 = "Los animales y plantas que se fijan a los fondos, o que se deslizan sobre ellos, como algas, plantas, moluscos, bivalvos y gusanos.";
 //BA.debugLineNum = 125;BA.debugLine="explicacion5 = \"Las macrófitas son un tipo de pl";
mostCurrent._explicacion5 = "Las macrófitas son un tipo de plantas, más específicamente se trata de vegetación acuática.";
 };
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public static String  _word1_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 365;BA.debugLine="Sub word1_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 367;BA.debugLine="Dim newleft As Int = Max(0, Min(word1.Left + del";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word1.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word1.getWidth())));
 //BA.debugLineNum = 368;BA.debugLine="Dim newtop As Int = Max(0, Min(word1.Top + delta";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word1.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word1.getHeight())));
 //BA.debugLineNum = 369;BA.debugLine="word1.Left = newleft";
mostCurrent._word1.setLeft(_newleft);
 //BA.debugLineNum = 370;BA.debugLine="word1.Top = newtop";
mostCurrent._word1.setTop(_newtop);
 //BA.debugLineNum = 371;BA.debugLine="word1.Tag = \"\"";
mostCurrent._word1.setTag((Object)(""));
 //BA.debugLineNum = 372;BA.debugLine="checkOK1.Left = 6000dip";
mostCurrent._checkok1.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 373;BA.debugLine="checkOK1.Top = 6000dip";
mostCurrent._checkok1.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 374;BA.debugLine="If word1.Left >= slot1.Left And word1.Left <= sl";
if (mostCurrent._word1.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 375;BA.debugLine="word1.Left = slot1.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 376;BA.debugLine="word1.Top = slot1.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 378;BA.debugLine="word1.Left = slot2.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 379;BA.debugLine="word1.Top = slot2.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 381;BA.debugLine="word1.Left = slot3.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 382;BA.debugLine="word1.Top = slot3.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 383;BA.debugLine="checkOK1.Left = word1.Left + word1.Width";
mostCurrent._checkok1.setLeft((int) (mostCurrent._word1.getLeft()+mostCurrent._word1.getWidth()));
 //BA.debugLineNum = 384;BA.debugLine="checkOK1.Top = word1.Top";
mostCurrent._checkok1.setTop(mostCurrent._word1.getTop());
 //BA.debugLineNum = 385;BA.debugLine="word1.Tag = \"OK\"";
mostCurrent._word1.setTag((Object)("OK"));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 387;BA.debugLine="word1.Left = slot4.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 388;BA.debugLine="word1.Top = slot4.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 390;BA.debugLine="word1.Left = slot5.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 391;BA.debugLine="word1.Top = slot5.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 395;BA.debugLine="End Sub";
return "";
}
public static boolean  _word1_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 520;BA.debugLine="Sub word1_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 521;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 522;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 524;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 526;BA.debugLine="End Sub";
return false;
}
public static String  _word2_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 396;BA.debugLine="Sub word2_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 397;BA.debugLine="Dim newleft As Int = Max(0, Min(word2.Left + del";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word2.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word2.getWidth())));
 //BA.debugLineNum = 398;BA.debugLine="Dim newtop As Int = Max(0, Min(word2.Top + delta";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word2.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word2.getHeight())));
 //BA.debugLineNum = 399;BA.debugLine="word2.Left = newleft";
mostCurrent._word2.setLeft(_newleft);
 //BA.debugLineNum = 400;BA.debugLine="word2.Top = newtop";
mostCurrent._word2.setTop(_newtop);
 //BA.debugLineNum = 401;BA.debugLine="word2.Tag = \"\"";
mostCurrent._word2.setTag((Object)(""));
 //BA.debugLineNum = 402;BA.debugLine="checkOK2.Left = 6000dip";
mostCurrent._checkok2.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 403;BA.debugLine="checkOK2.Top = 6000dip";
mostCurrent._checkok2.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 404;BA.debugLine="If word2.Left >= slot1.Left And word2.Left <= sl";
if (mostCurrent._word2.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 405;BA.debugLine="word2.Left = slot1.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 406;BA.debugLine="word2.Top = slot1.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 408;BA.debugLine="word2.Left = slot2.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 409;BA.debugLine="word2.Top = slot2.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 411;BA.debugLine="word2.Left = slot3.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 412;BA.debugLine="word2.Top = slot3.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 414;BA.debugLine="word2.Left = slot4.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 415;BA.debugLine="word2.Top = slot4.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 416;BA.debugLine="checkOK2.Left = word2.Left + word2.Width";
mostCurrent._checkok2.setLeft((int) (mostCurrent._word2.getLeft()+mostCurrent._word2.getWidth()));
 //BA.debugLineNum = 417;BA.debugLine="checkOK2.Top = word2.Top";
mostCurrent._checkok2.setTop(mostCurrent._word2.getTop());
 //BA.debugLineNum = 418;BA.debugLine="word2.Tag = \"OK\"";
mostCurrent._word2.setTag((Object)("OK"));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 420;BA.debugLine="word2.Left = slot5.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 421;BA.debugLine="word2.Top = slot5.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 425;BA.debugLine="End Sub";
return "";
}
public static boolean  _word2_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 527;BA.debugLine="Sub word2_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 528;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 529;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 531;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 533;BA.debugLine="End Sub";
return false;
}
public static String  _word3_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 427;BA.debugLine="Sub word3_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 428;BA.debugLine="Dim newleft As Int = Max(0, Min(word3.Left + del";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word3.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word3.getWidth())));
 //BA.debugLineNum = 429;BA.debugLine="Dim newtop As Int = Max(0, Min(word3.Top + delta";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word3.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word3.getHeight())));
 //BA.debugLineNum = 430;BA.debugLine="word3.Left = newleft";
mostCurrent._word3.setLeft(_newleft);
 //BA.debugLineNum = 431;BA.debugLine="word3.Top = newtop";
mostCurrent._word3.setTop(_newtop);
 //BA.debugLineNum = 432;BA.debugLine="word3.Tag = \"\"";
mostCurrent._word3.setTag((Object)(""));
 //BA.debugLineNum = 433;BA.debugLine="checkOK3.Left = 6000dip";
mostCurrent._checkok3.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 434;BA.debugLine="checkOK3.Top = 6000dip";
mostCurrent._checkok3.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 435;BA.debugLine="If word3.Left >= slot1.Left And word3.Left <= sl";
if (mostCurrent._word3.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 436;BA.debugLine="word3.Left = slot1.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 437;BA.debugLine="word3.Top = slot1.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 439;BA.debugLine="word3.Left = slot2.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 440;BA.debugLine="word3.Top = slot2.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 441;BA.debugLine="checkOK3.Left = word3.Left + word3.Width";
mostCurrent._checkok3.setLeft((int) (mostCurrent._word3.getLeft()+mostCurrent._word3.getWidth()));
 //BA.debugLineNum = 442;BA.debugLine="checkOK3.Top = word3.Top";
mostCurrent._checkok3.setTop(mostCurrent._word3.getTop());
 //BA.debugLineNum = 443;BA.debugLine="word3.Tag = \"OK\"";
mostCurrent._word3.setTag((Object)("OK"));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 445;BA.debugLine="word3.Left = slot3.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 446;BA.debugLine="word3.Top = slot3.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 448;BA.debugLine="word3.Left = slot4.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 449;BA.debugLine="word3.Top = slot4.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 451;BA.debugLine="word3.Left = slot5.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 452;BA.debugLine="word3.Top = slot5.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 455;BA.debugLine="End Sub";
return "";
}
public static boolean  _word3_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 534;BA.debugLine="Sub word3_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 535;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 536;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 538;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 540;BA.debugLine="End Sub";
return false;
}
public static String  _word4_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 457;BA.debugLine="Sub word4_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 458;BA.debugLine="Dim newleft As Int = Max(0, Min(word4.Left + del";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word4.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word4.getWidth())));
 //BA.debugLineNum = 459;BA.debugLine="Dim newtop As Int = Max(0, Min(word4.Top + delta";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word4.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word4.getHeight())));
 //BA.debugLineNum = 460;BA.debugLine="word4.Left = newleft";
mostCurrent._word4.setLeft(_newleft);
 //BA.debugLineNum = 461;BA.debugLine="word4.Top = newtop";
mostCurrent._word4.setTop(_newtop);
 //BA.debugLineNum = 462;BA.debugLine="word4.Tag = \"\"";
mostCurrent._word4.setTag((Object)(""));
 //BA.debugLineNum = 463;BA.debugLine="checkOK4.Left = 6000dip";
mostCurrent._checkok4.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 464;BA.debugLine="checkOK4.Top = 6000dip";
mostCurrent._checkok4.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 465;BA.debugLine="If word4.Left >= slot1.Left And word4.Left <= sl";
if (mostCurrent._word4.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 466;BA.debugLine="word4.Left = slot1.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 467;BA.debugLine="word4.Top = slot1.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 469;BA.debugLine="word4.Left = slot2.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 470;BA.debugLine="word4.Top = slot2.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 472;BA.debugLine="word4.Left = slot3.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 473;BA.debugLine="word4.Top = slot3.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 476;BA.debugLine="word4.Left = slot4.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 477;BA.debugLine="word4.Top = slot4.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 479;BA.debugLine="word4.Left = slot5.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 480;BA.debugLine="word4.Top = slot5.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 481;BA.debugLine="checkOK4.Left = word4.Left + word4.Width";
mostCurrent._checkok4.setLeft((int) (mostCurrent._word4.getLeft()+mostCurrent._word4.getWidth()));
 //BA.debugLineNum = 482;BA.debugLine="checkOK4.Top = word4.Top";
mostCurrent._checkok4.setTop(mostCurrent._word4.getTop());
 //BA.debugLineNum = 483;BA.debugLine="word4.Tag = \"OK\"";
mostCurrent._word4.setTag((Object)("OK"));
 };
 //BA.debugLineNum = 485;BA.debugLine="End Sub";
return "";
}
public static boolean  _word4_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 541;BA.debugLine="Sub word4_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 542;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 543;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 545;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 547;BA.debugLine="End Sub";
return false;
}
public static String  _word5_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 487;BA.debugLine="Sub word5_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 489;BA.debugLine="Dim newleft As Int = Max(0, Min(word5.Left + del";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word5.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word5.getWidth())));
 //BA.debugLineNum = 490;BA.debugLine="Dim newtop As Int = Max(0, Min(word5.Top + delta";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word5.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word5.getHeight())));
 //BA.debugLineNum = 491;BA.debugLine="word5.Left = newleft";
mostCurrent._word5.setLeft(_newleft);
 //BA.debugLineNum = 492;BA.debugLine="word5.Top = newtop";
mostCurrent._word5.setTop(_newtop);
 //BA.debugLineNum = 493;BA.debugLine="word5.Tag = \"\"";
mostCurrent._word5.setTag((Object)(""));
 //BA.debugLineNum = 494;BA.debugLine="checkOK5.Left = 6000dip";
mostCurrent._checkok5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 495;BA.debugLine="checkOK5.Top = 6000dip";
mostCurrent._checkok5.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 497;BA.debugLine="If word5.Left >= slot1.Left And word5.Left <= sl";
if (mostCurrent._word5.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 498;BA.debugLine="word5.Left = slot1.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 499;BA.debugLine="word5.Top = slot1.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 500;BA.debugLine="checkOK5.Left = word5.Left + word5.Width";
mostCurrent._checkok5.setLeft((int) (mostCurrent._word5.getLeft()+mostCurrent._word5.getWidth()));
 //BA.debugLineNum = 501;BA.debugLine="checkOK5.Top = word5.Top";
mostCurrent._checkok5.setTop(mostCurrent._word5.getTop());
 //BA.debugLineNum = 502;BA.debugLine="word5.Tag = \"OK\"";
mostCurrent._word5.setTag((Object)("OK"));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 504;BA.debugLine="word5.Left = slot2.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 505;BA.debugLine="word5.Top = slot2.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 507;BA.debugLine="word5.Left = slot3.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 508;BA.debugLine="word5.Top = slot3.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 510;BA.debugLine="word5.Left = slot4.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 511;BA.debugLine="word5.Top = slot4.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 513;BA.debugLine="word5.Left = slot5.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 514;BA.debugLine="word5.Top = slot5.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 518;BA.debugLine="End Sub";
return "";
}
public static boolean  _word5_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 548;BA.debugLine="Sub word5_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 549;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 550;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 552;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 554;BA.debugLine="End Sub";
return false;
}
}
