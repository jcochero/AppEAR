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

public class aprender_contaminacion extends Activity implements B4AActivity{
	public static aprender_contaminacion mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.aprender_contaminacion");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (aprender_contaminacion).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.aprender_contaminacion");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.aprender_contaminacion", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (aprender_contaminacion) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (aprender_contaminacion) Resume **");
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
		return aprender_contaminacion.class;
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
            BA.LogInfo("** Activity (aprender_contaminacion) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (aprender_contaminacion) Pause event (activity is not paused). **");
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
            aprender_contaminacion mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (aprender_contaminacion) Resume **");
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
public static boolean _verinstruccionesciclo = false;
public static String _origen = "";
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd1 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd2 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd3 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd4 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd5 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd6 = null;
public flm.b4a.gesturedetector.GestureDetectorForB4A _gd7 = null;
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
public anywheresoftware.b4a.objects.ImageViewWrapper _checkok6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _checkok7 = null;
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
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 63;BA.debugLine="If origen = \"menu\" Then";
if ((_origen).equals("menu")) { 
 //BA.debugLineNum = 64;BA.debugLine="verInstruccionesCiclo = True";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 67;BA.debugLine="Activity.LoadLayout(\"Aprender_Contaminacion\")";
mostCurrent._activity.LoadLayout("Aprender_Contaminacion",mostCurrent.activityBA);
 //BA.debugLineNum = 68;BA.debugLine="CargaInstrucciones";
_cargainstrucciones();
 //BA.debugLineNum = 70;BA.debugLine="checkOK1.Initialize(\"checkOK1\")";
mostCurrent._checkok1.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 71;BA.debugLine="checkOK1.Gravity = Gravity.FILL";
mostCurrent._checkok1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 72;BA.debugLine="checkOK1.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 73;BA.debugLine="checkOK2.Initialize(\"checkOK1\")";
mostCurrent._checkok2.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 74;BA.debugLine="checkOK2.Gravity = Gravity.FILL";
mostCurrent._checkok2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 75;BA.debugLine="checkOK2.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 76;BA.debugLine="checkOK3.Initialize(\"checkOK1\")";
mostCurrent._checkok3.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 77;BA.debugLine="checkOK3.Gravity = Gravity.FILL";
mostCurrent._checkok3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 78;BA.debugLine="checkOK3.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 79;BA.debugLine="checkOK4.Initialize(\"checkOK1\")";
mostCurrent._checkok4.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 80;BA.debugLine="checkOK4.Gravity = Gravity.FILL";
mostCurrent._checkok4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 81;BA.debugLine="checkOK4.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 82;BA.debugLine="checkOK5.Initialize(\"checkOK1\")";
mostCurrent._checkok5.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 83;BA.debugLine="checkOK5.Gravity = Gravity.FILL";
mostCurrent._checkok5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 84;BA.debugLine="checkOK5.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 85;BA.debugLine="checkOK6.Initialize(\"checkOK1\")";
mostCurrent._checkok6.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 86;BA.debugLine="checkOK6.Gravity = Gravity.FILL";
mostCurrent._checkok6.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 87;BA.debugLine="checkOK6.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 88;BA.debugLine="checkOK7.Initialize(\"checkOK1\")";
mostCurrent._checkok7.Initialize(mostCurrent.activityBA,"checkOK1");
 //BA.debugLineNum = 89;BA.debugLine="checkOK7.Gravity = Gravity.FILL";
mostCurrent._checkok7.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 90;BA.debugLine="checkOK7.Bitmap = LoadBitmap(File.DirAssets, \"dat";
mostCurrent._checkok7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"datosenviados.png").getObject()));
 //BA.debugLineNum = 92;BA.debugLine="Activity.AddView(checkOK1, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 93;BA.debugLine="Activity.AddView(checkOK2, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok2.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 94;BA.debugLine="Activity.AddView(checkOK3, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok3.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 95;BA.debugLine="Activity.AddView(checkOK4, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok4.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 96;BA.debugLine="Activity.AddView(checkOK5, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok5.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 97;BA.debugLine="Activity.AddView(checkOK6, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok6.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 98;BA.debugLine="Activity.AddView(checkOK7, 6000dip,6000dip, 30dip";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._checkok7.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (30)));
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 105;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 744;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 745;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 746;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 747;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 748;BA.debugLine="End Sub";
return "";
}
public static String  _btnhelp_click() throws Exception{
 //BA.debugLineNum = 750;BA.debugLine="Sub btnHelp_Click";
 //BA.debugLineNum = 751;BA.debugLine="verInstruccionesCiclo = True";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 752;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 753;BA.debugLine="End Sub";
return "";
}
public static String  _btnreset_click() throws Exception{
 //BA.debugLineNum = 739;BA.debugLine="Sub btnReset_Click";
 //BA.debugLineNum = 740;BA.debugLine="verInstruccionesCiclo = True";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 741;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 742;BA.debugLine="End Sub";
return "";
}
public static String  _butok_click() throws Exception{
 //BA.debugLineNum = 317;BA.debugLine="Sub butOK_Click";
 //BA.debugLineNum = 318;BA.debugLine="verInstruccionesCiclo = False";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 319;BA.debugLine="origen = \"\"";
_origen = "";
 //BA.debugLineNum = 320;BA.debugLine="p.SetScreenOrientation(0)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (0));
 //BA.debugLineNum = 321;BA.debugLine="End Sub";
return "";
}
public static String  _cargainstrucciones() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _img1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img3 = null;
anywheresoftware.b4a.objects.LabelWrapper _headerinst = null;
anywheresoftware.b4a.objects.LabelWrapper _header1 = null;
anywheresoftware.b4a.objects.LabelWrapper _header2 = null;
anywheresoftware.b4a.objects.LabelWrapper _header3 = null;
anywheresoftware.b4a.objects.LabelWrapper _header4 = null;
anywheresoftware.b4a.objects.LabelWrapper _header5 = null;
anywheresoftware.b4a.objects.LabelWrapper _header6 = null;
anywheresoftware.b4a.objects.LabelWrapper _header7 = null;
anywheresoftware.b4a.objects.LabelWrapper _textoinstruccion = null;
anywheresoftware.b4a.objects.LabelWrapper _texto1 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto2 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto3 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel1 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel2 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel3 = null;
String _lineinst = "";
String _line1 = "";
String _line2 = "";
String _line3 = "";
anywheresoftware.b4a.objects.LabelWrapper _flechas = null;
anywheresoftware.b4a.objects.ButtonWrapper _butok = null;
 //BA.debugLineNum = 148;BA.debugLine="Sub CargaInstrucciones";
 //BA.debugLineNum = 149;BA.debugLine="If verInstruccionesCiclo = False Then";
if (_verinstruccionesciclo==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 150;BA.debugLine="LoadLevel";
_loadlevel();
 //BA.debugLineNum = 151;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 152;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 155;BA.debugLine="SD.Initialize(\"SD\",300,Activity,Me,False) 'Initia";
mostCurrent._sd._initialize(mostCurrent.activityBA,"SD",(int) (300),(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._activity.getObject())),aprender_contaminacion.getObject(),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 156;BA.debugLine="SD.ModeFullScreen(4,False) 'Creates the mode of S";
mostCurrent._sd._modefullscreen((int) (4),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="SD.panels(0).Color = Colors.White";
mostCurrent._sd._panels[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 158;BA.debugLine="SD.panels(1).Color = Colors.White";
mostCurrent._sd._panels[(int) (1)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 159;BA.debugLine="SD.panels(2).Color = Colors.White";
mostCurrent._sd._panels[(int) (2)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 160;BA.debugLine="SD.panels(3).Color = Colors.White";
mostCurrent._sd._panels[(int) (3)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 162;BA.debugLine="SD.Start(0) 'Start the SlidingPanels.";
mostCurrent._sd._start((int) (0));
 //BA.debugLineNum = 165;BA.debugLine="Dim img1 As ImageView";
_img1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 166;BA.debugLine="Dim img2 As ImageView";
_img2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 167;BA.debugLine="Dim img3 As ImageView";
_img3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 169;BA.debugLine="img1.Initialize(\"\")";
_img1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 170;BA.debugLine="img2.Initialize(\"\")";
_img2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 171;BA.debugLine="img3.Initialize(\"\")";
_img3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 173;BA.debugLine="img1.Bitmap = LoadBitmapSample(File.DirAssets, \"p";
_img1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pointsource1.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 174;BA.debugLine="img2.Bitmap = LoadBitmapSample(File.DirAssets, \"p";
_img2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pointsource2.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 175;BA.debugLine="img3.Bitmap = LoadBitmapSample(File.DirAssets, \"p";
_img3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pointsource3.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 177;BA.debugLine="img1.Gravity = Gravity.FILL";
_img1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 178;BA.debugLine="img2.Gravity = Gravity.FILL";
_img2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 179;BA.debugLine="img3.Gravity = Gravity.FILL";
_img3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 183;BA.debugLine="Dim headerInst As Label";
_headerinst = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 184;BA.debugLine="Dim header1 As Label";
_header1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 185;BA.debugLine="Dim header2 As Label";
_header2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 186;BA.debugLine="Dim header3 As Label";
_header3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 187;BA.debugLine="Dim header4 As Label";
_header4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 188;BA.debugLine="Dim header5 As Label";
_header5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 189;BA.debugLine="Dim header6 As Label";
_header6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 190;BA.debugLine="Dim header7 As Label";
_header7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 191;BA.debugLine="headerInst.Initialize(\"\")";
_headerinst.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 192;BA.debugLine="header1.Initialize(\"\")";
_header1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 193;BA.debugLine="header2.Initialize(\"\")";
_header2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 194;BA.debugLine="header3.Initialize(\"\")";
_header3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 195;BA.debugLine="header4.Initialize(\"\")";
_header4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 196;BA.debugLine="header5.Initialize(\"\")";
_header5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 197;BA.debugLine="header6.Initialize(\"\")";
_header6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 198;BA.debugLine="header7.Initialize(\"\")";
_header7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 199;BA.debugLine="headerInst.TextColor = Colors.Black";
_headerinst.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 200;BA.debugLine="header1.TextColor = Colors.Black";
_header1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 201;BA.debugLine="header2.TextColor = Colors.Black";
_header2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 202;BA.debugLine="header3.TextColor = Colors.Black";
_header3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 203;BA.debugLine="header4.TextColor = Colors.Black";
_header4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 204;BA.debugLine="header5.TextColor = Colors.Black";
_header5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 205;BA.debugLine="header6.TextColor = Colors.Black";
_header6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 206;BA.debugLine="header7.TextColor = Colors.Black";
_header7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 207;BA.debugLine="headerInst.TextSize = 26";
_headerinst.setTextSize((float) (26));
 //BA.debugLineNum = 208;BA.debugLine="header1.TextSize = 26";
_header1.setTextSize((float) (26));
 //BA.debugLineNum = 209;BA.debugLine="header2.TextSize = 26";
_header2.setTextSize((float) (26));
 //BA.debugLineNum = 210;BA.debugLine="header3.TextSize = 26";
_header3.setTextSize((float) (26));
 //BA.debugLineNum = 211;BA.debugLine="header4.TextSize = 26";
_header4.setTextSize((float) (26));
 //BA.debugLineNum = 212;BA.debugLine="header5.TextSize = 26";
_header5.setTextSize((float) (26));
 //BA.debugLineNum = 213;BA.debugLine="header6.TextSize = 26";
_header6.setTextSize((float) (26));
 //BA.debugLineNum = 214;BA.debugLine="header7.TextSize = 26";
_header7.setTextSize((float) (26));
 //BA.debugLineNum = 215;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 216;BA.debugLine="headerInst.Text = \"La contaminación\"";
_headerinst.setText(BA.ObjectToCharSequence("La contaminación"));
 //BA.debugLineNum = 217;BA.debugLine="header1.Text = \"Contaminación puntual\"";
_header1.setText(BA.ObjectToCharSequence("Contaminación puntual"));
 //BA.debugLineNum = 218;BA.debugLine="header2.Text = \"Contaminación difusa\"";
_header2.setText(BA.ObjectToCharSequence("Contaminación difusa"));
 //BA.debugLineNum = 219;BA.debugLine="header3.Text = \"¿Qué cosas contaminan el agua?\"";
_header3.setText(BA.ObjectToCharSequence("¿Qué cosas contaminan el agua?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 222;BA.debugLine="headerInst.Text = \"Pollution\"";
_headerinst.setText(BA.ObjectToCharSequence("Pollution"));
 //BA.debugLineNum = 223;BA.debugLine="header1.Text = \"Point-source pollution\"";
_header1.setText(BA.ObjectToCharSequence("Point-source pollution"));
 //BA.debugLineNum = 224;BA.debugLine="header2.Text = \"Diffuse pollution\"";
_header2.setText(BA.ObjectToCharSequence("Diffuse pollution"));
 //BA.debugLineNum = 225;BA.debugLine="header3.Text = \"What pollutes the water?\"";
_header3.setText(BA.ObjectToCharSequence("What pollutes the water?"));
 };
 //BA.debugLineNum = 230;BA.debugLine="Dim textoInstruccion As Label";
_textoinstruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 231;BA.debugLine="Dim texto1 As Label";
_texto1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 232;BA.debugLine="Dim texto2 As Label";
_texto2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 233;BA.debugLine="Dim texto3 As Label";
_texto3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 235;BA.debugLine="textoInstruccion.Initialize(\"\")";
_textoinstruccion.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 236;BA.debugLine="texto1.Initialize(\"\")";
_texto1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 237;BA.debugLine="texto2.Initialize(\"\")";
_texto2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 238;BA.debugLine="texto3.Initialize(\"\")";
_texto3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 240;BA.debugLine="textoInstruccion.TextColor = Colors.Black";
_textoinstruccion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 241;BA.debugLine="texto1.TextColor = Colors.Black";
_texto1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 242;BA.debugLine="texto2.TextColor = Colors.Black";
_texto2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 243;BA.debugLine="texto3.TextColor = Colors.Black";
_texto3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 245;BA.debugLine="Dim panel1 As ScrollView";
_panel1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 246;BA.debugLine="Dim panel2 As ScrollView";
_panel2 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 247;BA.debugLine="Dim panel3 As ScrollView";
_panel3 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 249;BA.debugLine="panel1.Initialize(500dip)";
_panel1.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 250;BA.debugLine="panel2.Initialize(500dip)";
_panel2.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 251;BA.debugLine="panel3.Initialize(500dip)";
_panel3.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 254;BA.debugLine="Dim lineInst As String";
_lineinst = "";
 //BA.debugLineNum = 255;BA.debugLine="lineInst = File.ReadString(File.DirAssets, Mai";
_lineinst = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pointsourceInst.txt");
 //BA.debugLineNum = 256;BA.debugLine="Dim line1 As String";
_line1 = "";
 //BA.debugLineNum = 257;BA.debugLine="line1 = File.ReadString(File.DirAssets, Main.l";
_line1 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pointsource1.txt");
 //BA.debugLineNum = 258;BA.debugLine="Dim line2 As String";
_line2 = "";
 //BA.debugLineNum = 259;BA.debugLine="line2 = File.ReadString(File.DirAssets, Main.l";
_line2 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pointsource2.txt");
 //BA.debugLineNum = 260;BA.debugLine="Dim line3 As String";
_line3 = "";
 //BA.debugLineNum = 261;BA.debugLine="line3 = File.ReadString(File.DirAssets, Main.l";
_line3 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang /*String*/ +"-pointsource3.txt");
 //BA.debugLineNum = 263;BA.debugLine="textoInstruccion.Text = lineInst";
_textoinstruccion.setText(BA.ObjectToCharSequence(_lineinst));
 //BA.debugLineNum = 264;BA.debugLine="texto1.Text = line1";
_texto1.setText(BA.ObjectToCharSequence(_line1));
 //BA.debugLineNum = 265;BA.debugLine="texto2.Text = line2";
_texto2.setText(BA.ObjectToCharSequence(_line2));
 //BA.debugLineNum = 266;BA.debugLine="texto3.Text = line3";
_texto3.setText(BA.ObjectToCharSequence(_line3));
 //BA.debugLineNum = 272;BA.debugLine="SD.panels(1).AddView(img1,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_img1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 273;BA.debugLine="SD.panels(2).AddView(img2,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_img2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 274;BA.debugLine="SD.panels(3).AddView(img3,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_img3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 276;BA.debugLine="SD.panels(0).AddView(headerInst,5%x,5%y,90%x,90%y";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_headerinst.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 277;BA.debugLine="SD.panels(1).AddView(header1,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_header1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 278;BA.debugLine="SD.panels(2).AddView(header2,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_header2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 279;BA.debugLine="SD.panels(3).AddView(header3,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_header3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 282;BA.debugLine="SD.panels(0).AddView(textoInstruccion,5%x,10%y,90";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_textoinstruccion.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 283;BA.debugLine="SD.panels(1).AddView(panel1,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_panel1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 284;BA.debugLine="SD.panels(2).AddView(panel2,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_panel2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 285;BA.debugLine="SD.panels(3).AddView(panel3,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_panel3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 287;BA.debugLine="panel1.Panel.AddView(texto1,0,0,90%x,50%y)";
_panel1.getPanel().AddView((android.view.View)(_texto1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 288;BA.debugLine="panel2.Panel.AddView(texto2,0,0,90%x,50%y)";
_panel2.getPanel().AddView((android.view.View)(_texto2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 289;BA.debugLine="panel3.Panel.AddView(texto3,0,0,90%x,100%y)";
_panel3.getPanel().AddView((android.view.View)(_texto3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 292;BA.debugLine="Dim flechas As Label";
_flechas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 293;BA.debugLine="flechas.Initialize(\"\")";
_flechas.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 294;BA.debugLine="flechas.TextSize = 18";
_flechas.setTextSize((float) (18));
 //BA.debugLineNum = 295;BA.debugLine="flechas.TextColor = Colors.White";
_flechas.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 296;BA.debugLine="flechas.Color = Colors.ARGB(255, 73,202,138)";
_flechas.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 297;BA.debugLine="flechas.Gravity = Gravity.CENTER";
_flechas.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 298;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 299;BA.debugLine="flechas.Text = \"<< Scroll for more info >>\"";
_flechas.setText(BA.ObjectToCharSequence("<< Scroll for more info >>"));
 }else {
 //BA.debugLineNum = 301;BA.debugLine="flechas.Text = \"<< Desliza para mas información";
_flechas.setText(BA.ObjectToCharSequence("<< Desliza para mas información >>"));
 };
 //BA.debugLineNum = 306;BA.debugLine="Dim butOK As Button";
_butok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 307;BA.debugLine="butOK.Initialize(\"butOK\")";
_butok.Initialize(mostCurrent.activityBA,"butOK");
 //BA.debugLineNum = 308;BA.debugLine="butOK.Text = \"Jugar\"";
_butok.setText(BA.ObjectToCharSequence("Jugar"));
 //BA.debugLineNum = 309;BA.debugLine="butOK.Color = Colors.ARGB(255,73,202,138)";
_butok.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 310;BA.debugLine="butOK.TextColor = Colors.White";
_butok.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 312;BA.debugLine="Activity.AddView(butOK, Activity.Width - 35%x ,0,";
mostCurrent._activity.AddView((android.view.View)(_butok.getObject()),(int) (mostCurrent._activity.getWidth()-anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (35),mostCurrent.activityBA)),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (35),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 313;BA.debugLine="Activity.AddView(flechas, 0, 90%y, 100%x, 10%y)";
mostCurrent._activity.AddView((android.view.View)(_flechas.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 314;BA.debugLine="End Sub";
return "";
}
public static String  _chequear() throws Exception{
String _ms = "";
 //BA.debugLineNum = 709;BA.debugLine="Sub Chequear";
 //BA.debugLineNum = 710;BA.debugLine="If word1.Tag = \"OK\" And word2.Tag = \"OK\" And word";
if ((mostCurrent._word1.getTag()).equals((Object)("OK")) && (mostCurrent._word2.getTag()).equals((Object)("OK")) && (mostCurrent._word3.getTag()).equals((Object)("OK")) && (mostCurrent._word4.getTag()).equals((Object)("OK")) && (mostCurrent._word5.getTag()).equals((Object)("OK")) && (mostCurrent._word6.getTag()).equals((Object)("OK")) && (mostCurrent._word7.getTag()).equals((Object)("OK"))) { 
 //BA.debugLineNum = 711;BA.debugLine="word1.Enabled = False";
mostCurrent._word1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 712;BA.debugLine="word2.Enabled = False";
mostCurrent._word2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 713;BA.debugLine="word3.Enabled = False";
mostCurrent._word3.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 714;BA.debugLine="word4.Enabled = False";
mostCurrent._word4.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 715;BA.debugLine="word5.Enabled = False";
mostCurrent._word5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 716;BA.debugLine="word6.Enabled = False";
mostCurrent._word6.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 717;BA.debugLine="word7.Enabled = False";
mostCurrent._word7.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 718;BA.debugLine="Dim ms As String";
_ms = "";
 //BA.debugLineNum = 719;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 }else {
 };
 //BA.debugLineNum = 724;BA.debugLine="If ms = DialogResponse.POSITIVE Then";
if ((_ms).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 725;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 726;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 };
 //BA.debugLineNum = 731;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim GD1, GD2, GD3, GD4, GD5, GD6, GD7 As GestureD";
mostCurrent._gd1 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd2 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd3 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd4 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd5 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd6 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
mostCurrent._gd7 = new flm.b4a.gesturedetector.GestureDetectorForB4A();
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
 //BA.debugLineNum = 50;BA.debugLine="Dim checkOK1 As ImageView";
mostCurrent._checkok1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim checkOK2 As ImageView";
mostCurrent._checkok2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 52;BA.debugLine="Dim checkOK3 As ImageView";
mostCurrent._checkok3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim checkOK4 As ImageView";
mostCurrent._checkok4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim checkOK5 As ImageView";
mostCurrent._checkok5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim checkOK6 As ImageView";
mostCurrent._checkok6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim checkOK7 As ImageView";
mostCurrent._checkok7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 58;BA.debugLine="End Sub";
return "";
}
public static String  _loadlevel() throws Exception{
 //BA.debugLineNum = 329;BA.debugLine="Sub LoadLevel";
 //BA.debugLineNum = 330;BA.debugLine="GD1.SetOnGestureListener(word1, \"word1\")";
mostCurrent._gd1.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word1.getObject()),"word1");
 //BA.debugLineNum = 331;BA.debugLine="GD2.SetOnGestureListener(word2, \"word2\")";
mostCurrent._gd2.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word2.getObject()),"word2");
 //BA.debugLineNum = 332;BA.debugLine="GD3.SetOnGestureListener(word3, \"word3\")";
mostCurrent._gd3.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word3.getObject()),"word3");
 //BA.debugLineNum = 333;BA.debugLine="GD4.SetOnGestureListener(word4, \"word4\")";
mostCurrent._gd4.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word4.getObject()),"word4");
 //BA.debugLineNum = 334;BA.debugLine="GD5.SetOnGestureListener(word5, \"word5\")";
mostCurrent._gd5.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word5.getObject()),"word5");
 //BA.debugLineNum = 335;BA.debugLine="GD6.SetOnGestureListener(word6, \"word6\")";
mostCurrent._gd6.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word6.getObject()),"word6");
 //BA.debugLineNum = 336;BA.debugLine="GD7.SetOnGestureListener(word7, \"word7\")";
mostCurrent._gd7.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word7.getObject()),"word7");
 //BA.debugLineNum = 338;BA.debugLine="slot1.Tag = \"1\"";
mostCurrent._slot1.setTag((Object)("1"));
 //BA.debugLineNum = 339;BA.debugLine="slot2.Tag = \"2\"";
mostCurrent._slot2.setTag((Object)("2"));
 //BA.debugLineNum = 340;BA.debugLine="slot3.Tag = \"3\"";
mostCurrent._slot3.setTag((Object)("3"));
 //BA.debugLineNum = 341;BA.debugLine="slot4.Tag = \"4\"";
mostCurrent._slot4.setTag((Object)("4"));
 //BA.debugLineNum = 342;BA.debugLine="slot5.Tag = \"5\"";
mostCurrent._slot5.setTag((Object)("5"));
 //BA.debugLineNum = 343;BA.debugLine="slot6.Tag = \"6\"";
mostCurrent._slot6.setTag((Object)("6"));
 //BA.debugLineNum = 344;BA.debugLine="slot7.Tag = \"7\"";
mostCurrent._slot7.setTag((Object)("7"));
 //BA.debugLineNum = 345;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim verInstruccionesCiclo As Boolean";
_verinstruccionesciclo = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim origen As String";
_origen = "";
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 115;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 116;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 117;BA.debugLine="explicacion1 = \"The sun causes liquid water to e";
mostCurrent._explicacion1 = "The sun causes liquid water to evaporate, or turn from a liquid to a gas (water vapor). The invisible water vapor floats high into the atmosphere (the air that surrounds the earth).";
 //BA.debugLineNum = 118;BA.debugLine="explicacion2 = \"The colder temperatures high in";
mostCurrent._explicacion2 = "The colder temperatures high in the atmosphere cause the water vapor to turn back into tiny liquid water droplets—the clouds.";
 //BA.debugLineNum = 119;BA.debugLine="explicacion3 = \"Lots of water exists in the grou";
mostCurrent._explicacion3 = "Lots of water exists in the ground below your feet. Some precipitation and runoff soaks into the ground to become groundwater. Plants use groundwater to grow. The water underground is always moving, with most of it ending up back in the oceans.";
 //BA.debugLineNum = 120;BA.debugLine="explicacion4 = \"When a person breathes, their br";
mostCurrent._explicacion4 = "When a person breathes, their breath contains water molecules. All the plants around you are 'breathing' and releasing water, too. The term is called 'transpiration', and although a brussels sprout doesn't have a mouth, it has tiny holes in its leaves that allow water to leave the leaf, via evaporation, and go into the air.";
 //BA.debugLineNum = 121;BA.debugLine="explicacion5 = \"When rain hits the land or snow";
mostCurrent._explicacion5 = "When rain hits the land or snow melts, it flows downhill over the landscape. This is called runoff, which provides water to rivers, lakes, and the oceans. Some runoff even soaks into the ground to become groundwater.";
 //BA.debugLineNum = 122;BA.debugLine="explicacion6 = \"When water droplets in clouds gr";
mostCurrent._explicacion6 = "When water droplets in clouds grow large enough, they fall as rain. If the temperatures are low enough, these water droplets crystallize into snowflakes";
 //BA.debugLineNum = 123;BA.debugLine="explicacion7 = \"It may all start as precipitatio";
mostCurrent._explicacion7 = "It may all start as precipitation, but through infiltration and seepage, water soaks into the ground in vast amounts. Water in the ground keeps all plant life alive and serves peoples' needs, too. How much rainfall infiltrates the ground depends on many things And varies a lot all over the world. But infiltration works everywhere, And pretty much anywhere in the world you are, there Is some water at some depth below your feet, courtesy of infiltration.";
 //BA.debugLineNum = 124;BA.debugLine="word1.Text = \"Fish\"";
mostCurrent._word1.setText(BA.ObjectToCharSequence("Fish"));
 //BA.debugLineNum = 125;BA.debugLine="word2.Text = \"Green algae\"";
mostCurrent._word2.setText(BA.ObjectToCharSequence("Green algae"));
 //BA.debugLineNum = 126;BA.debugLine="word3.text = \"Bacteria\"";
mostCurrent._word3.setText(BA.ObjectToCharSequence("Bacteria"));
 //BA.debugLineNum = 127;BA.debugLine="word4.Text = \"High oxygen\"";
mostCurrent._word4.setText(BA.ObjectToCharSequence("High oxygen"));
 //BA.debugLineNum = 128;BA.debugLine="word5.Text = \"Low oxygen\"";
mostCurrent._word5.setText(BA.ObjectToCharSequence("Low oxygen"));
 //BA.debugLineNum = 129;BA.debugLine="word6.Text = \"Fungi\"";
mostCurrent._word6.setText(BA.ObjectToCharSequence("Fungi"));
 //BA.debugLineNum = 130;BA.debugLine="word7.Text = \"Crustaceans\"";
mostCurrent._word7.setText(BA.ObjectToCharSequence("Crustaceans"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 132;BA.debugLine="explicacion1 = \"El sol causa que el agua se evap";
mostCurrent._explicacion1 = "El sol causa que el agua se evapore, o pase de un líquido a un gas (vapor). El vapor invisible flota a la atmósfera.";
 //BA.debugLineNum = 133;BA.debugLine="explicacion2 = \"Las bajas temperaturas en la atm";
mostCurrent._explicacion2 = "Las bajas temperaturas en la atmósfera causan que el vapor se convierta de nuevo a pequeñas gotitas de agua, formando las nubes.";
 //BA.debugLineNum = 134;BA.debugLine="explicacion3 = \"Hay mucha agua bajo tus pies! Un";
mostCurrent._explicacion3 = "Hay mucha agua bajo tus pies! Una parte de la precipitación y la escorrentía se escurre al suelo y se convierte en agua subterránea. Las plantas usan este agua subterránea para crecer. El agua subterránea siempre se está moviendo, y la mayoría termina en los océanos.";
 //BA.debugLineNum = 135;BA.debugLine="explicacion4 = \"Cuando una persona respira, su r";
mostCurrent._explicacion4 = "Cuando una persona respira, su respiración contiene moléculas de agua. Todas las plantas a tu alrededor están 'respirando' y liberando agua también. Esto se llama 'transpiración', y ese agua se evapora a la atmósfera.";
 //BA.debugLineNum = 136;BA.debugLine="explicacion5 = \"Cuando la lluvia toca el suelo o";
mostCurrent._explicacion5 = "Cuando la lluvia toca el suelo o se derrite la nieve, fluye por los paisajes. Ésto se llama 'escorrentía', que provee agua a los ríos, lagos y océanos. Alguna escorrentía también es absorbida por el suelo y se convierte en agua subterránea.";
 //BA.debugLineNum = 137;BA.debugLine="explicacion6 = \"Las pequeñas gotas en las nubes";
mostCurrent._explicacion6 = "Las pequeñas gotas en las nubes se combinan para formar gotas más grandes. Cuando son lo suficientemente pesada, caen a la tierra como precipitación, tal como la lluvia y la nieve.";
 //BA.debugLineNum = 138;BA.debugLine="explicacion7 = \"El agua de lluvia o de nieve der";
mostCurrent._explicacion7 = "El agua de lluvia o de nieve derretida ingresa al suelo en grandes cantidades. Ese agua mantiene viva a las plantas y personas. La cantidad que ingresa al suelo depende de muchas cosas, incluyendo el tipo de suelo, y si hay un desarrollo urbano cerca!";
 };
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
return "";
}
public static String  _word1_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 353;BA.debugLine="Sub word1_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 354;BA.debugLine="Dim newleft As Int = Max(0, Min(word1.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word1.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word1.getWidth())));
 //BA.debugLineNum = 355;BA.debugLine="Dim newtop As Int = Max(0, Min(word1.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word1.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word1.getHeight())));
 //BA.debugLineNum = 356;BA.debugLine="word1.Left = newleft";
mostCurrent._word1.setLeft(_newleft);
 //BA.debugLineNum = 357;BA.debugLine="word1.Top = newtop";
mostCurrent._word1.setTop(_newtop);
 //BA.debugLineNum = 358;BA.debugLine="word1.Tag = \"\"";
mostCurrent._word1.setTag((Object)(""));
 //BA.debugLineNum = 359;BA.debugLine="checkOK1.Left = 6000dip";
mostCurrent._checkok1.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 360;BA.debugLine="checkOK1.Top = 6000dip";
mostCurrent._checkok1.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 362;BA.debugLine="If word1.Left >= slot1.Left And word1.Left <= slo";
if (mostCurrent._word1.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 363;BA.debugLine="word1.Left = slot1.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 364;BA.debugLine="word1.Top = slot1.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 365;BA.debugLine="checkOK1.Left = word1.Left + word1.Width";
mostCurrent._checkok1.setLeft((int) (mostCurrent._word1.getLeft()+mostCurrent._word1.getWidth()));
 //BA.debugLineNum = 366;BA.debugLine="checkOK1.Top = word1.Top";
mostCurrent._checkok1.setTop(mostCurrent._word1.getTop());
 //BA.debugLineNum = 367;BA.debugLine="word1.Tag = \"OK\"";
mostCurrent._word1.setTag((Object)("OK"));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 369;BA.debugLine="word1.Left = slot2.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 370;BA.debugLine="word1.Top = slot2.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 371;BA.debugLine="checkOK1.Left = word1.Left + word1.Width";
mostCurrent._checkok1.setLeft((int) (mostCurrent._word1.getLeft()+mostCurrent._word1.getWidth()));
 //BA.debugLineNum = 372;BA.debugLine="checkOK1.Top = word1.Top";
mostCurrent._checkok1.setTop(mostCurrent._word1.getTop());
 //BA.debugLineNum = 373;BA.debugLine="word1.Tag = \"OK\"";
mostCurrent._word1.setTag((Object)("OK"));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 375;BA.debugLine="word1.Left = slot3.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 376;BA.debugLine="word1.Top = slot3.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 378;BA.debugLine="word1.Left = slot4.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 379;BA.debugLine="word1.Top = slot4.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 380;BA.debugLine="checkOK1.Left = word1.Left + word1.Width";
mostCurrent._checkok1.setLeft((int) (mostCurrent._word1.getLeft()+mostCurrent._word1.getWidth()));
 //BA.debugLineNum = 381;BA.debugLine="checkOK1.Top = word1.Top";
mostCurrent._checkok1.setTop(mostCurrent._word1.getTop());
 //BA.debugLineNum = 382;BA.debugLine="word1.Tag = \"OK\"";
mostCurrent._word1.setTag((Object)("OK"));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 384;BA.debugLine="word1.Left = slot5.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 385;BA.debugLine="word1.Top = slot5.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 387;BA.debugLine="word1.Left = slot6.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 388;BA.debugLine="word1.Top = slot6.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 390;BA.debugLine="word1.Left = slot7.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 391;BA.debugLine="word1.Top = slot7.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 392;BA.debugLine="checkOK1.Left = word1.Left + word1.Width";
mostCurrent._checkok1.setLeft((int) (mostCurrent._word1.getLeft()+mostCurrent._word1.getWidth()));
 //BA.debugLineNum = 393;BA.debugLine="checkOK1.Top = word1.Top";
mostCurrent._checkok1.setTop(mostCurrent._word1.getTop());
 //BA.debugLineNum = 394;BA.debugLine="word1.Tag = \"OK\"";
mostCurrent._word1.setTag((Object)("OK"));
 };
 //BA.debugLineNum = 396;BA.debugLine="End Sub";
return "";
}
public static boolean  _word1_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 653;BA.debugLine="Sub word1_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 654;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 655;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 657;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 659;BA.debugLine="End Sub";
return false;
}
public static String  _word2_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 397;BA.debugLine="Sub word2_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 398;BA.debugLine="Dim newleft As Int = Max(0, Min(word2.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word2.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word2.getWidth())));
 //BA.debugLineNum = 399;BA.debugLine="Dim newtop As Int = Max(0, Min(word2.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word2.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word2.getHeight())));
 //BA.debugLineNum = 400;BA.debugLine="word2.Left = newleft";
mostCurrent._word2.setLeft(_newleft);
 //BA.debugLineNum = 401;BA.debugLine="word2.Top = newtop";
mostCurrent._word2.setTop(_newtop);
 //BA.debugLineNum = 402;BA.debugLine="word2.Tag = \"\"";
mostCurrent._word2.setTag((Object)(""));
 //BA.debugLineNum = 403;BA.debugLine="checkOK2.Left = 6000dip";
mostCurrent._checkok2.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 404;BA.debugLine="checkOK2.Top = 6000dip";
mostCurrent._checkok2.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 405;BA.debugLine="If word2.Left >= slot1.Left And word2.Left <= slo";
if (mostCurrent._word2.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 406;BA.debugLine="word2.Left = slot1.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 407;BA.debugLine="word2.Top = slot1.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 408;BA.debugLine="checkOK2.Left = word2.Left + word2.Width";
mostCurrent._checkok2.setLeft((int) (mostCurrent._word2.getLeft()+mostCurrent._word2.getWidth()));
 //BA.debugLineNum = 409;BA.debugLine="checkOK2.Top = word2.Top";
mostCurrent._checkok2.setTop(mostCurrent._word2.getTop());
 //BA.debugLineNum = 410;BA.debugLine="word2.Tag = \"OK\"";
mostCurrent._word2.setTag((Object)("OK"));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 412;BA.debugLine="word2.Left = slot2.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 413;BA.debugLine="word2.Top = slot2.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 414;BA.debugLine="checkOK2.Left = word2.Left + word2.Width";
mostCurrent._checkok2.setLeft((int) (mostCurrent._word2.getLeft()+mostCurrent._word2.getWidth()));
 //BA.debugLineNum = 415;BA.debugLine="checkOK2.Top = word2.Top";
mostCurrent._checkok2.setTop(mostCurrent._word2.getTop());
 //BA.debugLineNum = 416;BA.debugLine="word2.Tag = \"OK\"";
mostCurrent._word2.setTag((Object)("OK"));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 418;BA.debugLine="word2.Left = slot3.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 419;BA.debugLine="word2.Top = slot3.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 421;BA.debugLine="word2.Left = slot4.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 422;BA.debugLine="word2.Top = slot4.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 423;BA.debugLine="checkOK2.Left = word2.Left + word2.Width";
mostCurrent._checkok2.setLeft((int) (mostCurrent._word2.getLeft()+mostCurrent._word2.getWidth()));
 //BA.debugLineNum = 424;BA.debugLine="checkOK2.Top = word2.Top";
mostCurrent._checkok2.setTop(mostCurrent._word2.getTop());
 //BA.debugLineNum = 425;BA.debugLine="word2.Tag = \"OK\"";
mostCurrent._word2.setTag((Object)("OK"));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 427;BA.debugLine="word2.Left = slot5.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 428;BA.debugLine="word2.Top = slot5.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 430;BA.debugLine="word2.Left = slot6.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 431;BA.debugLine="word2.Top = slot6.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 433;BA.debugLine="word2.Left = slot7.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 434;BA.debugLine="word2.Top = slot7.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 435;BA.debugLine="checkOK2.Left = word2.Left + word2.Width";
mostCurrent._checkok2.setLeft((int) (mostCurrent._word2.getLeft()+mostCurrent._word2.getWidth()));
 //BA.debugLineNum = 436;BA.debugLine="checkOK2.Top = word2.Top";
mostCurrent._checkok2.setTop(mostCurrent._word2.getTop());
 //BA.debugLineNum = 437;BA.debugLine="word2.Tag = \"OK\"";
mostCurrent._word2.setTag((Object)("OK"));
 };
 //BA.debugLineNum = 439;BA.debugLine="End Sub";
return "";
}
public static boolean  _word2_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 660;BA.debugLine="Sub word2_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 661;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 662;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 664;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 666;BA.debugLine="End Sub";
return false;
}
public static String  _word3_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 440;BA.debugLine="Sub word3_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 441;BA.debugLine="Dim newleft As Int = Max(0, Min(word3.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word3.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word3.getWidth())));
 //BA.debugLineNum = 442;BA.debugLine="Dim newtop As Int = Max(0, Min(word3.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word3.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word3.getHeight())));
 //BA.debugLineNum = 443;BA.debugLine="word3.Left = newleft";
mostCurrent._word3.setLeft(_newleft);
 //BA.debugLineNum = 444;BA.debugLine="word3.Top = newtop";
mostCurrent._word3.setTop(_newtop);
 //BA.debugLineNum = 445;BA.debugLine="word3.Tag = \"\"";
mostCurrent._word3.setTag((Object)(""));
 //BA.debugLineNum = 446;BA.debugLine="checkOK3.Left = 6000dip";
mostCurrent._checkok3.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 447;BA.debugLine="checkOK3.Top = 6000dip";
mostCurrent._checkok3.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 448;BA.debugLine="If word3.Left >= slot1.Left And word3.Left <= slo";
if (mostCurrent._word3.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 449;BA.debugLine="word3.Left = slot1.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 450;BA.debugLine="word3.Top = slot1.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 452;BA.debugLine="word3.Left = slot2.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 453;BA.debugLine="word3.Top = slot2.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 455;BA.debugLine="word3.Left = slot3.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 456;BA.debugLine="word3.Top = slot3.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 457;BA.debugLine="checkOK3.Left = word3.Left + word3.Width";
mostCurrent._checkok3.setLeft((int) (mostCurrent._word3.getLeft()+mostCurrent._word3.getWidth()));
 //BA.debugLineNum = 458;BA.debugLine="checkOK3.Top = word3.Top";
mostCurrent._checkok3.setTop(mostCurrent._word3.getTop());
 //BA.debugLineNum = 459;BA.debugLine="word3.Tag = \"OK\"";
mostCurrent._word3.setTag((Object)("OK"));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 461;BA.debugLine="word3.Left = slot4.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 462;BA.debugLine="word3.Top = slot4.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 464;BA.debugLine="word3.Left = slot5.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 465;BA.debugLine="word3.Top = slot5.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 466;BA.debugLine="checkOK3.Left = word3.Left + word3.Width";
mostCurrent._checkok3.setLeft((int) (mostCurrent._word3.getLeft()+mostCurrent._word3.getWidth()));
 //BA.debugLineNum = 467;BA.debugLine="checkOK3.Top = word3.Top";
mostCurrent._checkok3.setTop(mostCurrent._word3.getTop());
 //BA.debugLineNum = 468;BA.debugLine="word3.Tag = \"OK\"";
mostCurrent._word3.setTag((Object)("OK"));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 470;BA.debugLine="word3.Left = slot6.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 471;BA.debugLine="word3.Top = slot6.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 472;BA.debugLine="checkOK3.Left = word3.Left + word3.Width";
mostCurrent._checkok3.setLeft((int) (mostCurrent._word3.getLeft()+mostCurrent._word3.getWidth()));
 //BA.debugLineNum = 473;BA.debugLine="checkOK3.Top = word3.Top";
mostCurrent._checkok3.setTop(mostCurrent._word3.getTop());
 //BA.debugLineNum = 474;BA.debugLine="word3.Tag = \"OK\"";
mostCurrent._word3.setTag((Object)("OK"));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 476;BA.debugLine="word3.Left = slot7.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 477;BA.debugLine="word3.Top = slot7.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 479;BA.debugLine="End Sub";
return "";
}
public static boolean  _word3_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 667;BA.debugLine="Sub word3_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 668;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 669;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 671;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 673;BA.debugLine="End Sub";
return false;
}
public static String  _word4_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 480;BA.debugLine="Sub word4_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 481;BA.debugLine="Dim newleft As Int = Max(0, Min(word4.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word4.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word4.getWidth())));
 //BA.debugLineNum = 482;BA.debugLine="Dim newtop As Int = Max(0, Min(word4.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word4.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word4.getHeight())));
 //BA.debugLineNum = 483;BA.debugLine="word4.Left = newleft";
mostCurrent._word4.setLeft(_newleft);
 //BA.debugLineNum = 484;BA.debugLine="word4.Top = newtop";
mostCurrent._word4.setTop(_newtop);
 //BA.debugLineNum = 485;BA.debugLine="word4.Tag = \"\"";
mostCurrent._word4.setTag((Object)(""));
 //BA.debugLineNum = 486;BA.debugLine="checkOK4.Left = 6000dip";
mostCurrent._checkok4.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 487;BA.debugLine="checkOK4.Top = 6000dip";
mostCurrent._checkok4.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 488;BA.debugLine="If word4.Left >= slot1.Left And word4.Left <= slo";
if (mostCurrent._word4.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 489;BA.debugLine="word4.Left = slot1.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 490;BA.debugLine="word4.Top = slot1.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 491;BA.debugLine="checkOK4.Left = word4.Left + word4.Width";
mostCurrent._checkok4.setLeft((int) (mostCurrent._word4.getLeft()+mostCurrent._word4.getWidth()));
 //BA.debugLineNum = 492;BA.debugLine="checkOK4.Top = word4.Top";
mostCurrent._checkok4.setTop(mostCurrent._word4.getTop());
 //BA.debugLineNum = 493;BA.debugLine="word4.Tag = \"OK\"";
mostCurrent._word4.setTag((Object)("OK"));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 495;BA.debugLine="word4.Left = slot2.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 496;BA.debugLine="word4.Top = slot2.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 497;BA.debugLine="checkOK4.Left = word4.Left + word4.Width";
mostCurrent._checkok4.setLeft((int) (mostCurrent._word4.getLeft()+mostCurrent._word4.getWidth()));
 //BA.debugLineNum = 498;BA.debugLine="checkOK4.Top = word4.Top";
mostCurrent._checkok4.setTop(mostCurrent._word4.getTop());
 //BA.debugLineNum = 499;BA.debugLine="word4.Tag = \"OK\"";
mostCurrent._word4.setTag((Object)("OK"));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 501;BA.debugLine="word4.Left = slot3.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 502;BA.debugLine="word4.Top = slot3.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 505;BA.debugLine="word4.Left = slot4.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 506;BA.debugLine="word4.Top = slot4.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 507;BA.debugLine="checkOK4.Left = word4.Left + word4.Width";
mostCurrent._checkok4.setLeft((int) (mostCurrent._word4.getLeft()+mostCurrent._word4.getWidth()));
 //BA.debugLineNum = 508;BA.debugLine="checkOK4.Top = word4.Top";
mostCurrent._checkok4.setTop(mostCurrent._word4.getTop());
 //BA.debugLineNum = 509;BA.debugLine="word4.Tag = \"OK\"";
mostCurrent._word4.setTag((Object)("OK"));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 511;BA.debugLine="word4.Left = slot5.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 512;BA.debugLine="word4.Top = slot5.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 514;BA.debugLine="word4.Left = slot6.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 515;BA.debugLine="word4.Top = slot6.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 517;BA.debugLine="word4.Left = slot7.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 518;BA.debugLine="word4.Top = slot7.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 519;BA.debugLine="checkOK4.Left = word4.Left + word4.Width";
mostCurrent._checkok4.setLeft((int) (mostCurrent._word4.getLeft()+mostCurrent._word4.getWidth()));
 //BA.debugLineNum = 520;BA.debugLine="checkOK4.Top = word4.Top";
mostCurrent._checkok4.setTop(mostCurrent._word4.getTop());
 //BA.debugLineNum = 521;BA.debugLine="word4.Tag = \"OK\"";
mostCurrent._word4.setTag((Object)("OK"));
 };
 //BA.debugLineNum = 523;BA.debugLine="End Sub";
return "";
}
public static boolean  _word4_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 674;BA.debugLine="Sub word4_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 675;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 676;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 678;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 680;BA.debugLine="End Sub";
return false;
}
public static String  _word5_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 524;BA.debugLine="Sub word5_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 525;BA.debugLine="Dim newleft As Int = Max(0, Min(word5.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word5.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word5.getWidth())));
 //BA.debugLineNum = 526;BA.debugLine="Dim newtop As Int = Max(0, Min(word5.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word5.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word5.getHeight())));
 //BA.debugLineNum = 527;BA.debugLine="word5.Left = newleft";
mostCurrent._word5.setLeft(_newleft);
 //BA.debugLineNum = 528;BA.debugLine="word5.Top = newtop";
mostCurrent._word5.setTop(_newtop);
 //BA.debugLineNum = 529;BA.debugLine="word5.Tag = \"\"";
mostCurrent._word5.setTag((Object)(""));
 //BA.debugLineNum = 530;BA.debugLine="checkOK5.Left = 6000dip";
mostCurrent._checkok5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 531;BA.debugLine="checkOK5.Top = 6000dip";
mostCurrent._checkok5.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 532;BA.debugLine="If word5.Left >= slot1.Left And word5.Left <= slo";
if (mostCurrent._word5.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 533;BA.debugLine="word5.Left = slot1.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 534;BA.debugLine="word5.Top = slot1.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 536;BA.debugLine="word5.Left = slot2.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 537;BA.debugLine="word5.Top = slot2.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 539;BA.debugLine="word5.Left = slot3.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 540;BA.debugLine="word5.Top = slot3.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 541;BA.debugLine="checkOK5.Left = word5.Left + word5.Width";
mostCurrent._checkok5.setLeft((int) (mostCurrent._word5.getLeft()+mostCurrent._word5.getWidth()));
 //BA.debugLineNum = 542;BA.debugLine="checkOK5.Top = word5.Top";
mostCurrent._checkok5.setTop(mostCurrent._word5.getTop());
 //BA.debugLineNum = 543;BA.debugLine="word5.Tag = \"OK\"";
mostCurrent._word5.setTag((Object)("OK"));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 545;BA.debugLine="word5.Left = slot4.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 546;BA.debugLine="word5.Top = slot4.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 548;BA.debugLine="word5.Left = slot5.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 549;BA.debugLine="word5.Top = slot5.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 550;BA.debugLine="checkOK5.Left = word5.Left + word5.Width";
mostCurrent._checkok5.setLeft((int) (mostCurrent._word5.getLeft()+mostCurrent._word5.getWidth()));
 //BA.debugLineNum = 551;BA.debugLine="checkOK5.Top = word5.Top";
mostCurrent._checkok5.setTop(mostCurrent._word5.getTop());
 //BA.debugLineNum = 552;BA.debugLine="word5.Tag = \"OK\"";
mostCurrent._word5.setTag((Object)("OK"));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 554;BA.debugLine="word5.Left = slot6.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 555;BA.debugLine="word5.Top = slot6.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 556;BA.debugLine="checkOK5.Left = word5.Left + word5.Width";
mostCurrent._checkok5.setLeft((int) (mostCurrent._word5.getLeft()+mostCurrent._word5.getWidth()));
 //BA.debugLineNum = 557;BA.debugLine="checkOK5.Top = word5.Top";
mostCurrent._checkok5.setTop(mostCurrent._word5.getTop());
 //BA.debugLineNum = 558;BA.debugLine="word5.Tag = \"OK\"";
mostCurrent._word5.setTag((Object)("OK"));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 560;BA.debugLine="word5.Left = slot7.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 561;BA.debugLine="word5.Top = slot7.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 563;BA.debugLine="End Sub";
return "";
}
public static boolean  _word5_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 681;BA.debugLine="Sub word5_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 682;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 683;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 685;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 687;BA.debugLine="End Sub";
return false;
}
public static String  _word6_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 564;BA.debugLine="Sub word6_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 565;BA.debugLine="Dim newleft As Int = Max(0, Min(word6.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word6.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word6.getWidth())));
 //BA.debugLineNum = 566;BA.debugLine="Dim newtop As Int = Max(0, Min(word6.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word6.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word6.getHeight())));
 //BA.debugLineNum = 567;BA.debugLine="word6.Left = newleft";
mostCurrent._word6.setLeft(_newleft);
 //BA.debugLineNum = 568;BA.debugLine="word6.Top = newtop";
mostCurrent._word6.setTop(_newtop);
 //BA.debugLineNum = 569;BA.debugLine="word6.Tag = \"\"";
mostCurrent._word6.setTag((Object)(""));
 //BA.debugLineNum = 570;BA.debugLine="checkOK6.Left = 6000dip";
mostCurrent._checkok6.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 571;BA.debugLine="checkOK6.Top = 6000dip";
mostCurrent._checkok6.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 572;BA.debugLine="If word6.Left >= slot1.Left And word6.Left <= slo";
if (mostCurrent._word6.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 573;BA.debugLine="word6.Left = slot1.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 574;BA.debugLine="word6.Top = slot1.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 576;BA.debugLine="word6.Left = slot2.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 577;BA.debugLine="word6.Top = slot2.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 579;BA.debugLine="word6.Left = slot3.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 580;BA.debugLine="word6.Top = slot3.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 581;BA.debugLine="checkOK6.Left = word6.Left + word6.Width";
mostCurrent._checkok6.setLeft((int) (mostCurrent._word6.getLeft()+mostCurrent._word6.getWidth()));
 //BA.debugLineNum = 582;BA.debugLine="checkOK6.Top = word6.Top";
mostCurrent._checkok6.setTop(mostCurrent._word6.getTop());
 //BA.debugLineNum = 583;BA.debugLine="word6.Tag = \"OK\"";
mostCurrent._word6.setTag((Object)("OK"));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 585;BA.debugLine="word6.Left = slot4.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 586;BA.debugLine="word6.Top = slot4.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 588;BA.debugLine="word6.Left = slot5.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 589;BA.debugLine="word6.Top = slot5.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 590;BA.debugLine="checkOK6.Left = word6.Left + word6.Width";
mostCurrent._checkok6.setLeft((int) (mostCurrent._word6.getLeft()+mostCurrent._word6.getWidth()));
 //BA.debugLineNum = 591;BA.debugLine="checkOK6.Top = word6.Top";
mostCurrent._checkok6.setTop(mostCurrent._word6.getTop());
 //BA.debugLineNum = 592;BA.debugLine="word6.Tag = \"OK\"";
mostCurrent._word6.setTag((Object)("OK"));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 594;BA.debugLine="word6.Left = slot6.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 595;BA.debugLine="word6.Top = slot6.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 596;BA.debugLine="checkOK6.Left = word6.Left + word6.Width";
mostCurrent._checkok6.setLeft((int) (mostCurrent._word6.getLeft()+mostCurrent._word6.getWidth()));
 //BA.debugLineNum = 597;BA.debugLine="checkOK6.Top = word6.Top";
mostCurrent._checkok6.setTop(mostCurrent._word6.getTop());
 //BA.debugLineNum = 598;BA.debugLine="word6.Tag = \"OK\"";
mostCurrent._word6.setTag((Object)("OK"));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 600;BA.debugLine="word6.Left = slot7.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 601;BA.debugLine="word6.Top = slot7.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 602;BA.debugLine="checkOK6.Left = word6.Left + word6.Width";
mostCurrent._checkok6.setLeft((int) (mostCurrent._word6.getLeft()+mostCurrent._word6.getWidth()));
 //BA.debugLineNum = 603;BA.debugLine="checkOK6.Top = word6.Top";
mostCurrent._checkok6.setTop(mostCurrent._word6.getTop());
 //BA.debugLineNum = 604;BA.debugLine="word6.Tag = \"OK\"";
mostCurrent._word6.setTag((Object)("OK"));
 };
 //BA.debugLineNum = 606;BA.debugLine="End Sub";
return "";
}
public static boolean  _word6_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 688;BA.debugLine="Sub word6_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 689;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 690;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 692;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 694;BA.debugLine="End Sub";
return false;
}
public static String  _word7_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 607;BA.debugLine="Sub word7_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 608;BA.debugLine="Dim newleft As Int = Max(0, Min(word7.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word7.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word7.getWidth())));
 //BA.debugLineNum = 609;BA.debugLine="Dim newtop As Int = Max(0, Min(word7.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word7.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word7.getHeight())));
 //BA.debugLineNum = 610;BA.debugLine="word7.Left = newleft";
mostCurrent._word7.setLeft(_newleft);
 //BA.debugLineNum = 611;BA.debugLine="word7.Top = newtop";
mostCurrent._word7.setTop(_newtop);
 //BA.debugLineNum = 612;BA.debugLine="word7.Tag = \"\"";
mostCurrent._word7.setTag((Object)(""));
 //BA.debugLineNum = 613;BA.debugLine="checkOK7.Left = 6000dip";
mostCurrent._checkok7.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 614;BA.debugLine="checkOK7.Top = 6000dip";
mostCurrent._checkok7.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 615;BA.debugLine="If word7.Left >= slot1.Left And word7.Left <= slo";
if (mostCurrent._word7.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 616;BA.debugLine="word7.Left = slot1.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 617;BA.debugLine="word7.Top = slot1.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 618;BA.debugLine="checkOK7.Left = word7.Left + word7.Width";
mostCurrent._checkok7.setLeft((int) (mostCurrent._word7.getLeft()+mostCurrent._word7.getWidth()));
 //BA.debugLineNum = 619;BA.debugLine="checkOK7.Top = word7.Top";
mostCurrent._checkok7.setTop(mostCurrent._word7.getTop());
 //BA.debugLineNum = 620;BA.debugLine="word7.Tag = \"OK\"";
mostCurrent._word7.setTag((Object)("OK"));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 622;BA.debugLine="word7.Left = slot2.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 623;BA.debugLine="word7.Top = slot2.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 624;BA.debugLine="checkOK7.Left = word7.Left + word7.Width";
mostCurrent._checkok7.setLeft((int) (mostCurrent._word7.getLeft()+mostCurrent._word7.getWidth()));
 //BA.debugLineNum = 625;BA.debugLine="checkOK7.Top = word7.Top";
mostCurrent._checkok7.setTop(mostCurrent._word7.getTop());
 //BA.debugLineNum = 626;BA.debugLine="word7.Tag = \"OK\"";
mostCurrent._word7.setTag((Object)("OK"));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 628;BA.debugLine="word7.Left = slot3.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 629;BA.debugLine="word7.Top = slot3.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 632;BA.debugLine="word7.Left = slot4.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 633;BA.debugLine="word7.Top = slot4.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 634;BA.debugLine="checkOK7.Left = word7.Left + word7.Width";
mostCurrent._checkok7.setLeft((int) (mostCurrent._word7.getLeft()+mostCurrent._word7.getWidth()));
 //BA.debugLineNum = 635;BA.debugLine="checkOK7.Top = word7.Top";
mostCurrent._checkok7.setTop(mostCurrent._word7.getTop());
 //BA.debugLineNum = 636;BA.debugLine="word7.Tag = \"OK\"";
mostCurrent._word7.setTag((Object)("OK"));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 638;BA.debugLine="word7.Left = slot5.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 639;BA.debugLine="word7.Top = slot5.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 641;BA.debugLine="word7.Left = slot6.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 642;BA.debugLine="word7.Top = slot6.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 644;BA.debugLine="word7.Left = slot7.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 645;BA.debugLine="word7.Top = slot7.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 646;BA.debugLine="checkOK7.Left = word7.Left + word7.Width";
mostCurrent._checkok7.setLeft((int) (mostCurrent._word7.getLeft()+mostCurrent._word7.getWidth()));
 //BA.debugLineNum = 647;BA.debugLine="checkOK7.Top = word7.Top";
mostCurrent._checkok7.setTop(mostCurrent._word7.getTop());
 //BA.debugLineNum = 648;BA.debugLine="word7.Tag = \"OK\"";
mostCurrent._word7.setTag((Object)("OK"));
 };
 //BA.debugLineNum = 650;BA.debugLine="End Sub";
return "";
}
public static boolean  _word7_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 695;BA.debugLine="Sub word7_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 696;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 697;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 699;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 701;BA.debugLine="End Sub";
return false;
}
}
