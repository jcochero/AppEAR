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

public class game_ciclo extends Activity implements B4AActivity{
	public static game_ciclo mostCurrent;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isFirst) {
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.game_ciclo");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (game_ciclo).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.game_ciclo");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.game_ciclo", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (game_ciclo) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (game_ciclo) Resume **");
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
		return game_ciclo.class;
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
        BA.LogInfo("** Activity (game_ciclo) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (game_ciclo) Resume **");
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
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmprincipal _frmprincipal = null;
public ilpla.appear.frmevaluacion _frmevaluacion = null;
public ilpla.appear.utilidades _utilidades = null;
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
 //BA.debugLineNum = 60;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 63;BA.debugLine="If origen = \"aprender\" Then";
if ((_origen).equals("aprender")) { 
 //BA.debugLineNum = 64;BA.debugLine="verInstruccionesCiclo = True";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 67;BA.debugLine="Activity.LoadLayout(\"Game_Ciclo\")";
mostCurrent._activity.LoadLayout("Game_Ciclo",mostCurrent.activityBA);
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
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 102;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _butcerrar_click() throws Exception{
 //BA.debugLineNum = 760;BA.debugLine="Sub butCerrar_Click";
 //BA.debugLineNum = 761;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 762;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 763;BA.debugLine="End Sub";
return "";
}
public static String  _buthelp_click() throws Exception{
 //BA.debugLineNum = 765;BA.debugLine="Sub butHelp_Click";
 //BA.debugLineNum = 766;BA.debugLine="verInstruccionesCiclo = True";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 767;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 768;BA.debugLine="End Sub";
return "";
}
public static String  _butok_click() throws Exception{
 //BA.debugLineNum = 383;BA.debugLine="Sub butOK_Click";
 //BA.debugLineNum = 384;BA.debugLine="verInstruccionesCiclo = False";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 385;BA.debugLine="origen = \"\"";
_origen = "";
 //BA.debugLineNum = 386;BA.debugLine="p.SetScreenOrientation(0)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (0));
 //BA.debugLineNum = 387;BA.debugLine="End Sub";
return "";
}
public static String  _butreset_click() throws Exception{
 //BA.debugLineNum = 755;BA.debugLine="Sub butReset_Click";
 //BA.debugLineNum = 756;BA.debugLine="verInstruccionesCiclo = True";
_verinstruccionesciclo = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 757;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 758;BA.debugLine="End Sub";
return "";
}
public static String  _cargainstrucciones() throws Exception{
anywheresoftware.b4a.objects.ImageViewWrapper _img1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img4 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img5 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img6 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img7 = null;
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
anywheresoftware.b4a.objects.LabelWrapper _texto4 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto5 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto6 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto7 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel1 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel2 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel3 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel4 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel5 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel6 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel7 = null;
String _lineinst = "";
String _line1 = "";
String _line2 = "";
String _line3 = "";
String _line4 = "";
String _line5 = "";
String _line6 = "";
String _line7 = "";
anywheresoftware.b4a.objects.LabelWrapper _flechas = null;
anywheresoftware.b4a.objects.ButtonWrapper _butok = null;
 //BA.debugLineNum = 149;BA.debugLine="Sub CargaInstrucciones";
 //BA.debugLineNum = 150;BA.debugLine="If verInstruccionesCiclo = False Then";
if (_verinstruccionesciclo==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 151;BA.debugLine="LoadLevel";
_loadlevel();
 //BA.debugLineNum = 152;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 153;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 156;BA.debugLine="SD.Initialize(\"SD\",300,Activity,Me,False) 'Initia";
mostCurrent._sd._initialize(mostCurrent.activityBA,"SD",(int) (300),(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._activity.getObject())),game_ciclo.getObject(),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="SD.ModeFullScreen(8,False) 'Creates the mode of S";
mostCurrent._sd._modefullscreen((int) (8),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 158;BA.debugLine="SD.panels(0).Color = Colors.White";
mostCurrent._sd._panels[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 159;BA.debugLine="SD.panels(1).Color = Colors.White";
mostCurrent._sd._panels[(int) (1)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 160;BA.debugLine="SD.panels(2).Color = Colors.White";
mostCurrent._sd._panels[(int) (2)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 161;BA.debugLine="SD.panels(3).Color = Colors.White";
mostCurrent._sd._panels[(int) (3)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 162;BA.debugLine="SD.panels(4).Color = Colors.White";
mostCurrent._sd._panels[(int) (4)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 163;BA.debugLine="SD.panels(5).Color = Colors.White";
mostCurrent._sd._panels[(int) (5)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 164;BA.debugLine="SD.panels(6).Color = Colors.White";
mostCurrent._sd._panels[(int) (6)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 165;BA.debugLine="SD.panels(7).Color = Colors.White";
mostCurrent._sd._panels[(int) (7)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 166;BA.debugLine="SD.Start(0) 'Start the SlidingPanels.";
mostCurrent._sd._start((int) (0));
 //BA.debugLineNum = 170;BA.debugLine="Dim img1 As ImageView";
_img1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 171;BA.debugLine="Dim img2 As ImageView";
_img2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 172;BA.debugLine="Dim img3 As ImageView";
_img3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 173;BA.debugLine="Dim img4 As ImageView";
_img4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 174;BA.debugLine="Dim img5 As ImageView";
_img5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 175;BA.debugLine="Dim img6 As ImageView";
_img6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 176;BA.debugLine="Dim img7 As ImageView";
_img7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 178;BA.debugLine="img1.Initialize(\"\")";
_img1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 179;BA.debugLine="img2.Initialize(\"\")";
_img2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 180;BA.debugLine="img3.Initialize(\"\")";
_img3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 181;BA.debugLine="img4.Initialize(\"\")";
_img4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 182;BA.debugLine="img5.Initialize(\"\")";
_img5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 183;BA.debugLine="img6.Initialize(\"\")";
_img6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 184;BA.debugLine="img7.Initialize(\"\")";
_img7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 186;BA.debugLine="img1.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ciclo1.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 187;BA.debugLine="img2.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ciclo2.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 188;BA.debugLine="img3.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ciclo3.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 189;BA.debugLine="img4.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ciclo4.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 190;BA.debugLine="img5.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ciclo5.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 191;BA.debugLine="img6.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ciclo6.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 192;BA.debugLine="img7.Bitmap = LoadBitmapSample(File.DirAssets, \"c";
_img7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"ciclo7.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 193;BA.debugLine="img1.Gravity = Gravity.FILL";
_img1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 194;BA.debugLine="img2.Gravity = Gravity.FILL";
_img2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 195;BA.debugLine="img3.Gravity = Gravity.FILL";
_img3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 196;BA.debugLine="img4.Gravity = Gravity.FILL";
_img4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 197;BA.debugLine="img5.Gravity = Gravity.FILL";
_img5.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 198;BA.debugLine="img6.Gravity = Gravity.FILL";
_img6.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 199;BA.debugLine="img7.Gravity = Gravity.FILL";
_img7.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 202;BA.debugLine="Dim headerInst As Label";
_headerinst = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 203;BA.debugLine="Dim header1 As Label";
_header1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 204;BA.debugLine="Dim header2 As Label";
_header2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 205;BA.debugLine="Dim header3 As Label";
_header3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 206;BA.debugLine="Dim header4 As Label";
_header4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 207;BA.debugLine="Dim header5 As Label";
_header5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 208;BA.debugLine="Dim header6 As Label";
_header6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 209;BA.debugLine="Dim header7 As Label";
_header7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 210;BA.debugLine="headerInst.Initialize(\"\")";
_headerinst.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 211;BA.debugLine="header1.Initialize(\"\")";
_header1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 212;BA.debugLine="header2.Initialize(\"\")";
_header2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 213;BA.debugLine="header3.Initialize(\"\")";
_header3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 214;BA.debugLine="header4.Initialize(\"\")";
_header4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 215;BA.debugLine="header5.Initialize(\"\")";
_header5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 216;BA.debugLine="header6.Initialize(\"\")";
_header6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 217;BA.debugLine="header7.Initialize(\"\")";
_header7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 218;BA.debugLine="headerInst.TextColor = Colors.Black";
_headerinst.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 219;BA.debugLine="header1.TextColor = Colors.Black";
_header1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 220;BA.debugLine="header2.TextColor = Colors.Black";
_header2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 221;BA.debugLine="header3.TextColor = Colors.Black";
_header3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 222;BA.debugLine="header4.TextColor = Colors.Black";
_header4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 223;BA.debugLine="header5.TextColor = Colors.Black";
_header5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 224;BA.debugLine="header6.TextColor = Colors.Black";
_header6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 225;BA.debugLine="header7.TextColor = Colors.Black";
_header7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 226;BA.debugLine="headerInst.TextSize = 26";
_headerinst.setTextSize((float) (26));
 //BA.debugLineNum = 227;BA.debugLine="header1.TextSize = 26";
_header1.setTextSize((float) (26));
 //BA.debugLineNum = 228;BA.debugLine="header2.TextSize = 26";
_header2.setTextSize((float) (26));
 //BA.debugLineNum = 229;BA.debugLine="header3.TextSize = 26";
_header3.setTextSize((float) (26));
 //BA.debugLineNum = 230;BA.debugLine="header4.TextSize = 26";
_header4.setTextSize((float) (26));
 //BA.debugLineNum = 231;BA.debugLine="header5.TextSize = 26";
_header5.setTextSize((float) (26));
 //BA.debugLineNum = 232;BA.debugLine="header6.TextSize = 26";
_header6.setTextSize((float) (26));
 //BA.debugLineNum = 233;BA.debugLine="header7.TextSize = 26";
_header7.setTextSize((float) (26));
 //BA.debugLineNum = 234;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 235;BA.debugLine="headerInst.Text = \"Ciclo del agua\"";
_headerinst.setText((Object)("Ciclo del agua"));
 //BA.debugLineNum = 236;BA.debugLine="header1.Text = \"Evaporación\"";
_header1.setText((Object)("Evaporación"));
 //BA.debugLineNum = 237;BA.debugLine="header2.Text = \"Condensación\"";
_header2.setText((Object)("Condensación"));
 //BA.debugLineNum = 238;BA.debugLine="header3.Text = \"Agua subterránea\"";
_header3.setText((Object)("Agua subterránea"));
 //BA.debugLineNum = 239;BA.debugLine="header4.Text = \"Transpiración\"";
_header4.setText((Object)("Transpiración"));
 //BA.debugLineNum = 240;BA.debugLine="header5.Text = \"Escurrimiento\"";
_header5.setText((Object)("Escurrimiento"));
 //BA.debugLineNum = 241;BA.debugLine="header6.Text = \"Precipitación\"";
_header6.setText((Object)("Precipitación"));
 //BA.debugLineNum = 242;BA.debugLine="header7.Text = \"Infiltración\"";
_header7.setText((Object)("Infiltración"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 244;BA.debugLine="headerInst.Text = \"Water cycle\"";
_headerinst.setText((Object)("Water cycle"));
 //BA.debugLineNum = 245;BA.debugLine="header1.Text = \"Evaporation\"";
_header1.setText((Object)("Evaporation"));
 //BA.debugLineNum = 246;BA.debugLine="header2.Text = \"Condensation\"";
_header2.setText((Object)("Condensation"));
 //BA.debugLineNum = 247;BA.debugLine="header3.Text = \"Groundwater\"";
_header3.setText((Object)("Groundwater"));
 //BA.debugLineNum = 248;BA.debugLine="header4.Text = \"Transpiration\"";
_header4.setText((Object)("Transpiration"));
 //BA.debugLineNum = 249;BA.debugLine="header5.Text = \"Runoff\"";
_header5.setText((Object)("Runoff"));
 //BA.debugLineNum = 250;BA.debugLine="header6.Text = \"Precipitation\"";
_header6.setText((Object)("Precipitation"));
 //BA.debugLineNum = 251;BA.debugLine="header7.Text = \"Infiltration\"";
_header7.setText((Object)("Infiltration"));
 };
 //BA.debugLineNum = 256;BA.debugLine="Dim textoInstruccion As Label";
_textoinstruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 257;BA.debugLine="Dim texto1 As Label";
_texto1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 258;BA.debugLine="Dim texto2 As Label";
_texto2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 259;BA.debugLine="Dim texto3 As Label";
_texto3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 260;BA.debugLine="Dim texto4 As Label";
_texto4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 261;BA.debugLine="Dim texto5 As Label";
_texto5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 262;BA.debugLine="Dim texto6 As Label";
_texto6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 263;BA.debugLine="Dim texto7 As Label";
_texto7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 265;BA.debugLine="textoInstruccion.Initialize(\"\")";
_textoinstruccion.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 266;BA.debugLine="texto1.Initialize(\"\")";
_texto1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 267;BA.debugLine="texto2.Initialize(\"\")";
_texto2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 268;BA.debugLine="texto3.Initialize(\"\")";
_texto3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 269;BA.debugLine="texto4.Initialize(\"\")";
_texto4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 270;BA.debugLine="texto5.Initialize(\"\")";
_texto5.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 271;BA.debugLine="texto6.Initialize(\"\")";
_texto6.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 272;BA.debugLine="texto7.Initialize(\"\")";
_texto7.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 273;BA.debugLine="textoInstruccion.TextColor = Colors.Black";
_textoinstruccion.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 274;BA.debugLine="texto1.TextColor = Colors.Black";
_texto1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 275;BA.debugLine="texto2.TextColor = Colors.Black";
_texto2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 276;BA.debugLine="texto3.TextColor = Colors.Black";
_texto3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 277;BA.debugLine="texto4.TextColor = Colors.Black";
_texto4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 278;BA.debugLine="texto5.TextColor = Colors.Black";
_texto5.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 279;BA.debugLine="texto6.TextColor = Colors.Black";
_texto6.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 280;BA.debugLine="texto7.TextColor = Colors.Black";
_texto7.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 282;BA.debugLine="Dim panel1 As ScrollView";
_panel1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 283;BA.debugLine="Dim panel2 As ScrollView";
_panel2 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 284;BA.debugLine="Dim panel3 As ScrollView";
_panel3 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 285;BA.debugLine="Dim panel4 As ScrollView";
_panel4 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 286;BA.debugLine="Dim panel5 As ScrollView";
_panel5 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 287;BA.debugLine="Dim panel6 As ScrollView";
_panel6 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 288;BA.debugLine="Dim panel7 As ScrollView";
_panel7 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 289;BA.debugLine="panel1.Initialize(500dip)";
_panel1.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 290;BA.debugLine="panel2.Initialize(500dip)";
_panel2.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 291;BA.debugLine="panel3.Initialize(500dip)";
_panel3.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 292;BA.debugLine="panel4.Initialize(500dip)";
_panel4.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 293;BA.debugLine="panel5.Initialize(500dip)";
_panel5.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 294;BA.debugLine="panel6.Initialize(500dip)";
_panel6.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 295;BA.debugLine="panel7.Initialize(500dip)";
_panel7.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 297;BA.debugLine="Dim lineInst As String";
_lineinst = "";
 //BA.debugLineNum = 298;BA.debugLine="lineInst = File.ReadString(File.DirAssets, Mai";
_lineinst = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-cicloInst.txt");
 //BA.debugLineNum = 299;BA.debugLine="Dim line1 As String";
_line1 = "";
 //BA.debugLineNum = 300;BA.debugLine="line1 = File.ReadString(File.DirAssets, Main.l";
_line1 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ciclo1.txt");
 //BA.debugLineNum = 301;BA.debugLine="Dim line2 As String";
_line2 = "";
 //BA.debugLineNum = 302;BA.debugLine="line2 = File.ReadString(File.DirAssets, Main.l";
_line2 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ciclo2.txt");
 //BA.debugLineNum = 303;BA.debugLine="Dim line3 As String";
_line3 = "";
 //BA.debugLineNum = 304;BA.debugLine="line3 = File.ReadString(File.DirAssets, Main.l";
_line3 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ciclo3.txt");
 //BA.debugLineNum = 305;BA.debugLine="Dim line4 As String";
_line4 = "";
 //BA.debugLineNum = 306;BA.debugLine="line4 = File.ReadString(File.DirAssets, Main.l";
_line4 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ciclo4.txt");
 //BA.debugLineNum = 307;BA.debugLine="Dim line5 As String";
_line5 = "";
 //BA.debugLineNum = 308;BA.debugLine="line5 = File.ReadString(File.DirAssets, Main.l";
_line5 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ciclo5.txt");
 //BA.debugLineNum = 309;BA.debugLine="Dim line6 As String";
_line6 = "";
 //BA.debugLineNum = 310;BA.debugLine="line6 = File.ReadString(File.DirAssets, Main.l";
_line6 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ciclo6.txt");
 //BA.debugLineNum = 311;BA.debugLine="Dim line7 As String";
_line7 = "";
 //BA.debugLineNum = 312;BA.debugLine="line7 = File.ReadString(File.DirAssets, Main.l";
_line7 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ciclo7.txt");
 //BA.debugLineNum = 313;BA.debugLine="textoInstruccion.Text = lineInst";
_textoinstruccion.setText((Object)(_lineinst));
 //BA.debugLineNum = 314;BA.debugLine="texto1.Text = line1";
_texto1.setText((Object)(_line1));
 //BA.debugLineNum = 315;BA.debugLine="texto2.Text = line2";
_texto2.setText((Object)(_line2));
 //BA.debugLineNum = 316;BA.debugLine="texto3.Text = line3";
_texto3.setText((Object)(_line3));
 //BA.debugLineNum = 317;BA.debugLine="texto4.Text = line4";
_texto4.setText((Object)(_line4));
 //BA.debugLineNum = 318;BA.debugLine="texto5.Text = line5";
_texto5.setText((Object)(_line5));
 //BA.debugLineNum = 319;BA.debugLine="texto6.Text = line6";
_texto6.setText((Object)(_line6));
 //BA.debugLineNum = 320;BA.debugLine="texto7.Text = line7";
_texto7.setText((Object)(_line7));
 //BA.debugLineNum = 323;BA.debugLine="Dim flechas As Label";
_flechas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 324;BA.debugLine="flechas.Initialize(\"\")";
_flechas.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 325;BA.debugLine="flechas.TextSize = 18";
_flechas.setTextSize((float) (18));
 //BA.debugLineNum = 326;BA.debugLine="flechas.TextColor = Colors.ARGB(255, 73,202,138)";
_flechas.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 327;BA.debugLine="flechas.Gravity = Gravity.CENTER";
_flechas.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 328;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 329;BA.debugLine="flechas.Text = \"<< Scroll for more info >>\"";
_flechas.setText((Object)("<< Scroll for more info >>"));
 }else {
 //BA.debugLineNum = 331;BA.debugLine="flechas.Text = \"<< Desliza para mas información";
_flechas.setText((Object)("<< Desliza para mas información >>"));
 };
 //BA.debugLineNum = 337;BA.debugLine="SD.panels(1).AddView(img1,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_img1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 338;BA.debugLine="SD.panels(2).AddView(img2,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_img2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 339;BA.debugLine="SD.panels(3).AddView(img3,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_img3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 340;BA.debugLine="SD.panels(4).AddView(img4,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (4)].AddView((android.view.View)(_img4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 341;BA.debugLine="SD.panels(5).AddView(img5,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (5)].AddView((android.view.View)(_img5.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 342;BA.debugLine="SD.panels(6).AddView(img6,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (6)].AddView((android.view.View)(_img6.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 343;BA.debugLine="SD.panels(7).AddView(img7,0,0,100%x,30%y)";
mostCurrent._sd._panels[(int) (7)].AddView((android.view.View)(_img7.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 345;BA.debugLine="SD.panels(0).AddView(headerInst,5%x,5%y,90%x,90%y";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_headerinst.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 346;BA.debugLine="SD.panels(1).AddView(header1,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_header1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 347;BA.debugLine="SD.panels(2).AddView(header2,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_header2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 348;BA.debugLine="SD.panels(3).AddView(header3,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_header3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 349;BA.debugLine="SD.panels(4).AddView(header4,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (4)].AddView((android.view.View)(_header4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 350;BA.debugLine="SD.panels(5).AddView(header5,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (5)].AddView((android.view.View)(_header5.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 351;BA.debugLine="SD.panels(6).AddView(header6,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (6)].AddView((android.view.View)(_header6.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 352;BA.debugLine="SD.panels(7).AddView(header7,5%x,30%y,100%x,5%y)";
mostCurrent._sd._panels[(int) (7)].AddView((android.view.View)(_header7.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 354;BA.debugLine="SD.panels(0).AddView(textoInstruccion,5%x,10%y,90";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_textoinstruccion.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 355;BA.debugLine="SD.panels(1).AddView(panel1,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_panel1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 356;BA.debugLine="SD.panels(2).AddView(panel2,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_panel2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 357;BA.debugLine="SD.panels(3).AddView(panel3,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_panel3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 358;BA.debugLine="SD.panels(4).AddView(panel4,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (4)].AddView((android.view.View)(_panel4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 359;BA.debugLine="SD.panels(5).AddView(panel5,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (5)].AddView((android.view.View)(_panel5.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 360;BA.debugLine="SD.panels(6).AddView(panel6,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (6)].AddView((android.view.View)(_panel6.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 361;BA.debugLine="SD.panels(7).AddView(panel7,5%x,40%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (7)].AddView((android.view.View)(_panel7.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 363;BA.debugLine="panel1.Panel.AddView(texto1,0,0,90%x,50%y)";
_panel1.getPanel().AddView((android.view.View)(_texto1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 364;BA.debugLine="panel2.Panel.AddView(texto2,0,0,90%x,50%y)";
_panel2.getPanel().AddView((android.view.View)(_texto2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 365;BA.debugLine="panel3.Panel.AddView(texto3,0,0,90%x,50%y)";
_panel3.getPanel().AddView((android.view.View)(_texto3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 366;BA.debugLine="panel4.Panel.AddView(texto4,0,0,90%x,50%y)";
_panel4.getPanel().AddView((android.view.View)(_texto4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 367;BA.debugLine="panel5.Panel.AddView(texto5,0,0,90%x,50%y)";
_panel5.getPanel().AddView((android.view.View)(_texto5.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 368;BA.debugLine="panel6.Panel.AddView(texto6,0,0,90%x,50%y)";
_panel6.getPanel().AddView((android.view.View)(_texto6.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 369;BA.debugLine="panel7.Panel.AddView(texto7,0,0,90%x,50%y)";
_panel7.getPanel().AddView((android.view.View)(_texto7.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 372;BA.debugLine="Dim butOK As Button";
_butok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 373;BA.debugLine="butOK.Initialize(\"butOK\")";
_butok.Initialize(mostCurrent.activityBA,"butOK");
 //BA.debugLineNum = 374;BA.debugLine="butOK.Text = \"OK\"";
_butok.setText((Object)("OK"));
 //BA.debugLineNum = 375;BA.debugLine="butOK.Color = Colors.ARGB(255,73,202,138)";
_butok.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 376;BA.debugLine="butOK.TextColor = Colors.White";
_butok.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 378;BA.debugLine="Activity.AddView(butOK, 0,90%y,100%x,10%y)";
mostCurrent._activity.AddView((android.view.View)(_butok.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 379;BA.debugLine="Activity.AddView(flechas, 0, 80%y, 100%x, 10%y)";
mostCurrent._activity.AddView((android.view.View)(_flechas.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 380;BA.debugLine="End Sub";
return "";
}
public static String  _chequear() throws Exception{
String _ms = "";
 //BA.debugLineNum = 725;BA.debugLine="Sub Chequear";
 //BA.debugLineNum = 726;BA.debugLine="If word1.Tag = \"OK\" And word2.Tag = \"OK\" And word";
if ((mostCurrent._word1.getTag()).equals((Object)("OK")) && (mostCurrent._word2.getTag()).equals((Object)("OK")) && (mostCurrent._word3.getTag()).equals((Object)("OK")) && (mostCurrent._word4.getTag()).equals((Object)("OK")) && (mostCurrent._word5.getTag()).equals((Object)("OK")) && (mostCurrent._word6.getTag()).equals((Object)("OK")) && (mostCurrent._word7.getTag()).equals((Object)("OK"))) { 
 //BA.debugLineNum = 727;BA.debugLine="word1.Enabled = False";
mostCurrent._word1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 728;BA.debugLine="word2.Enabled = False";
mostCurrent._word2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 729;BA.debugLine="word3.Enabled = False";
mostCurrent._word3.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 730;BA.debugLine="word4.Enabled = False";
mostCurrent._word4.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 731;BA.debugLine="word5.Enabled = False";
mostCurrent._word5.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 732;BA.debugLine="word6.Enabled = False";
mostCurrent._word6.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 733;BA.debugLine="word7.Enabled = False";
mostCurrent._word7.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 734;BA.debugLine="Dim ms As String";
_ms = "";
 //BA.debugLineNum = 735;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 736;BA.debugLine="ms = utilidades.Mensaje(\"Congratulations!\", \"Yo";
_ms = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Congratulations!","You have correctly placed each term!","OK","","");
 }else {
 //BA.debugLineNum = 739;BA.debugLine="ms = utilidades.Mensaje(\"Felicitaciones!\", \"Has";
_ms = mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Felicitaciones!","Has colocado correctamente a cada término!","OK","","");
 };
 //BA.debugLineNum = 741;BA.debugLine="If ms = DialogResponse.POSITIVE Then";
if ((_ms).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 742;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 743;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 };
 };
 //BA.debugLineNum = 747;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 395;BA.debugLine="Sub LoadLevel";
 //BA.debugLineNum = 396;BA.debugLine="GD1.SetOnGestureListener(word1, \"word1\")";
mostCurrent._gd1.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word1.getObject()),"word1");
 //BA.debugLineNum = 397;BA.debugLine="GD2.SetOnGestureListener(word2, \"word2\")";
mostCurrent._gd2.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word2.getObject()),"word2");
 //BA.debugLineNum = 398;BA.debugLine="GD3.SetOnGestureListener(word3, \"word3\")";
mostCurrent._gd3.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word3.getObject()),"word3");
 //BA.debugLineNum = 399;BA.debugLine="GD4.SetOnGestureListener(word4, \"word4\")";
mostCurrent._gd4.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word4.getObject()),"word4");
 //BA.debugLineNum = 400;BA.debugLine="GD5.SetOnGestureListener(word5, \"word5\")";
mostCurrent._gd5.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word5.getObject()),"word5");
 //BA.debugLineNum = 401;BA.debugLine="GD6.SetOnGestureListener(word6, \"word6\")";
mostCurrent._gd6.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word6.getObject()),"word6");
 //BA.debugLineNum = 402;BA.debugLine="GD7.SetOnGestureListener(word7, \"word7\")";
mostCurrent._gd7.SetOnGestureListener(processBA,(android.view.View)(mostCurrent._word7.getObject()),"word7");
 //BA.debugLineNum = 407;BA.debugLine="slot1.Tag = \"1\"";
mostCurrent._slot1.setTag((Object)("1"));
 //BA.debugLineNum = 408;BA.debugLine="slot2.Tag = \"2\"";
mostCurrent._slot2.setTag((Object)("2"));
 //BA.debugLineNum = 409;BA.debugLine="slot3.Tag = \"3\"";
mostCurrent._slot3.setTag((Object)("3"));
 //BA.debugLineNum = 410;BA.debugLine="slot4.Tag = \"4\"";
mostCurrent._slot4.setTag((Object)("4"));
 //BA.debugLineNum = 411;BA.debugLine="slot5.Tag = \"5\"";
mostCurrent._slot5.setTag((Object)("5"));
 //BA.debugLineNum = 412;BA.debugLine="slot6.Tag = \"6\"";
mostCurrent._slot6.setTag((Object)("6"));
 //BA.debugLineNum = 413;BA.debugLine="slot7.Tag = \"7\"";
mostCurrent._slot7.setTag((Object)("7"));
 //BA.debugLineNum = 416;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 116;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 117;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 118;BA.debugLine="explicacion1 = \"The sun causes liquid water to e";
mostCurrent._explicacion1 = "The sun causes liquid water to evaporate, or turn from a liquid to a gas (water vapor). The invisible water vapor floats high into the atmosphere (the air that surrounds the earth).";
 //BA.debugLineNum = 119;BA.debugLine="explicacion2 = \"The colder temperatures high in";
mostCurrent._explicacion2 = "The colder temperatures high in the atmosphere cause the water vapor to turn back into tiny liquid water droplets—the clouds.";
 //BA.debugLineNum = 120;BA.debugLine="explicacion3 = \"Lots of water exists in the grou";
mostCurrent._explicacion3 = "Lots of water exists in the ground below your feet. Some precipitation and runoff soaks into the ground to become groundwater. Plants use groundwater to grow. The water underground is always moving, with most of it ending up back in the oceans.";
 //BA.debugLineNum = 121;BA.debugLine="explicacion4 = \"When a person breathes, their br";
mostCurrent._explicacion4 = "When a person breathes, their breath contains water molecules. All the plants around you are 'breathing' and releasing water, too. The term is called 'transpiration', and although a brussels sprout doesn't have a mouth, it has tiny holes in its leaves that allow water to leave the leaf, via evaporation, and go into the air.";
 //BA.debugLineNum = 122;BA.debugLine="explicacion5 = \"When rain hits the land or snow";
mostCurrent._explicacion5 = "When rain hits the land or snow melts, it flows downhill over the landscape. This is called runoff, which provides water to rivers, lakes, and the oceans. Some runoff even soaks into the ground to become groundwater.";
 //BA.debugLineNum = 123;BA.debugLine="explicacion6 = \"When water droplets in clouds gr";
mostCurrent._explicacion6 = "When water droplets in clouds grow large enough, they fall as rain. If the temperatures are low enough, these water droplets crystallize into snowflakes";
 //BA.debugLineNum = 124;BA.debugLine="explicacion7 = \"It may all start as precipitatio";
mostCurrent._explicacion7 = "It may all start as precipitation, but through infiltration and seepage, water soaks into the ground in vast amounts. Water in the ground keeps all plant life alive and serves peoples' needs, too. How much rainfall infiltrates the ground depends on many things And varies a lot all over the world. But infiltration works everywhere, And pretty much anywhere in the world you are, there Is some water at some depth below your feet, courtesy of infiltration.";
 //BA.debugLineNum = 125;BA.debugLine="word1.Text = \"Evaporation\"";
mostCurrent._word1.setText((Object)("Evaporation"));
 //BA.debugLineNum = 126;BA.debugLine="word2.Text = \"Condensation\"";
mostCurrent._word2.setText((Object)("Condensation"));
 //BA.debugLineNum = 127;BA.debugLine="word3.text = \"Subterranean water\"";
mostCurrent._word3.setText((Object)("Subterranean water"));
 //BA.debugLineNum = 128;BA.debugLine="word4.Text = \"Transpiration\"";
mostCurrent._word4.setText((Object)("Transpiration"));
 //BA.debugLineNum = 129;BA.debugLine="word5.Text = \"Runoff\"";
mostCurrent._word5.setText((Object)("Runoff"));
 //BA.debugLineNum = 130;BA.debugLine="word6.Text = \"Precipitation\"";
mostCurrent._word6.setText((Object)("Precipitation"));
 //BA.debugLineNum = 131;BA.debugLine="word7.Text = \"Infiltration\"";
mostCurrent._word7.setText((Object)("Infiltration"));
 }else if((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 133;BA.debugLine="explicacion1 = \"El sol causa que el agua se evap";
mostCurrent._explicacion1 = "El sol causa que el agua se evapore, o pase de un líquido a un gas (vapor). El vapor invisible flota a la atmósfera.";
 //BA.debugLineNum = 134;BA.debugLine="explicacion2 = \"Las bajas temperaturas en la atm";
mostCurrent._explicacion2 = "Las bajas temperaturas en la atmósfera causan que el vapor se convierta de nuevo a pequeñas gotitas de agua, formando las nubes.";
 //BA.debugLineNum = 135;BA.debugLine="explicacion3 = \"Hay mucha agua bajo tus pies! Un";
mostCurrent._explicacion3 = "Hay mucha agua bajo tus pies! Una parte de la precipitación y la escorrentía se escurre al suelo y se convierte en agua subterránea. Las plantas usan este agua subterránea para crecer. El agua subterránea siempre se está moviendo, y la mayoría termina en los océanos.";
 //BA.debugLineNum = 136;BA.debugLine="explicacion4 = \"Cuando una persona respira, su r";
mostCurrent._explicacion4 = "Cuando una persona respira, su respiración contiene moléculas de agua. Todas las plantas a tu alrededor están 'respirando' y liberando agua también. Esto se llama 'transpiración', y ese agua se evapora a la atmósfera.";
 //BA.debugLineNum = 137;BA.debugLine="explicacion5 = \"Cuando la lluvia toca el suelo o";
mostCurrent._explicacion5 = "Cuando la lluvia toca el suelo o se derrite la nieve, fluye por los paisajes. Ésto se llama 'escorrentía', que provee agua a los ríos, lagos y océanos. Alguna escorrentía también es absorbida por el suelo y se convierte en agua subterránea.";
 //BA.debugLineNum = 138;BA.debugLine="explicacion6 = \"Las pequeñas gotas en las nubes";
mostCurrent._explicacion6 = "Las pequeñas gotas en las nubes se combinan para formar gotas más grandes. Cuando son lo suficientemente pesada, caen a la tierra como precipitación, tal como la lluvia y la nieve.";
 //BA.debugLineNum = 139;BA.debugLine="explicacion7 = \"El agua de lluvia o de nieve der";
mostCurrent._explicacion7 = "El agua de lluvia o de nieve derretida ingresa al suelo en grandes cantidades. Ese agua mantiene viva a las plantas y personas. La cantidad que ingresa al suelo depende de muchas cosas, incluyendo el tipo de suelo, y si hay un desarrollo urbano cerca!";
 };
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public static String  _word1_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 424;BA.debugLine="Sub word1_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 425;BA.debugLine="Dim newleft As Int = Max(0, Min(word1.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word1.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word1.getWidth())));
 //BA.debugLineNum = 426;BA.debugLine="Dim newtop As Int = Max(0, Min(word1.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word1.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word1.getHeight())));
 //BA.debugLineNum = 427;BA.debugLine="word1.Left = newleft";
mostCurrent._word1.setLeft(_newleft);
 //BA.debugLineNum = 428;BA.debugLine="word1.Top = newtop";
mostCurrent._word1.setTop(_newtop);
 //BA.debugLineNum = 429;BA.debugLine="word1.Tag = \"\"";
mostCurrent._word1.setTag((Object)(""));
 //BA.debugLineNum = 430;BA.debugLine="checkOK1.Left = 6000dip";
mostCurrent._checkok1.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 431;BA.debugLine="checkOK1.Top = 6000dip";
mostCurrent._checkok1.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 433;BA.debugLine="If word1.Left >= slot1.Left And word1.Left <= slo";
if (mostCurrent._word1.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 434;BA.debugLine="word1.Left = slot1.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 435;BA.debugLine="word1.Top = slot1.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 437;BA.debugLine="word1.Left = slot2.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 438;BA.debugLine="word1.Top = slot2.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 439;BA.debugLine="checkOK1.Left = word1.Left + word1.Width";
mostCurrent._checkok1.setLeft((int) (mostCurrent._word1.getLeft()+mostCurrent._word1.getWidth()));
 //BA.debugLineNum = 440;BA.debugLine="checkOK1.Top = word1.Top";
mostCurrent._checkok1.setTop(mostCurrent._word1.getTop());
 //BA.debugLineNum = 441;BA.debugLine="word1.Tag = \"OK\"";
mostCurrent._word1.setTag((Object)("OK"));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 443;BA.debugLine="word1.Left = slot3.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 444;BA.debugLine="word1.Top = slot3.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 446;BA.debugLine="word1.Left = slot4.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 447;BA.debugLine="word1.Top = slot4.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 449;BA.debugLine="word1.Left = slot5.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 450;BA.debugLine="word1.Top = slot5.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 452;BA.debugLine="word1.Left = slot6.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 453;BA.debugLine="word1.Top = slot6.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word1.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word1.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word1.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word1.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 455;BA.debugLine="word1.Left = slot7.Left + 5dip";
mostCurrent._word1.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 456;BA.debugLine="word1.Top = slot7.Top + 5dip";
mostCurrent._word1.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 458;BA.debugLine="End Sub";
return "";
}
public static boolean  _word1_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 670;BA.debugLine="Sub word1_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 671;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 672;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 674;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 676;BA.debugLine="End Sub";
return false;
}
public static String  _word2_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 459;BA.debugLine="Sub word2_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 460;BA.debugLine="Dim newleft As Int = Max(0, Min(word2.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word2.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word2.getWidth())));
 //BA.debugLineNum = 461;BA.debugLine="Dim newtop As Int = Max(0, Min(word2.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word2.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word2.getHeight())));
 //BA.debugLineNum = 462;BA.debugLine="word2.Left = newleft";
mostCurrent._word2.setLeft(_newleft);
 //BA.debugLineNum = 463;BA.debugLine="word2.Top = newtop";
mostCurrent._word2.setTop(_newtop);
 //BA.debugLineNum = 464;BA.debugLine="word2.Tag = \"\"";
mostCurrent._word2.setTag((Object)(""));
 //BA.debugLineNum = 465;BA.debugLine="checkOK2.Left = 6000dip";
mostCurrent._checkok2.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 466;BA.debugLine="checkOK2.Top = 6000dip";
mostCurrent._checkok2.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 468;BA.debugLine="If word2.Left >= slot1.Left And word2.Left <= slo";
if (mostCurrent._word2.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 469;BA.debugLine="word2.Left = slot1.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 470;BA.debugLine="word2.Top = slot1.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 471;BA.debugLine="checkOK2.Left = word2.Left + word2.Width";
mostCurrent._checkok2.setLeft((int) (mostCurrent._word2.getLeft()+mostCurrent._word2.getWidth()));
 //BA.debugLineNum = 472;BA.debugLine="checkOK2.Top = word2.Top";
mostCurrent._checkok2.setTop(mostCurrent._word2.getTop());
 //BA.debugLineNum = 473;BA.debugLine="word2.Tag = \"OK\"";
mostCurrent._word2.setTag((Object)("OK"));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 475;BA.debugLine="word2.Left = slot2.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 476;BA.debugLine="word2.Top = slot2.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 478;BA.debugLine="word2.Left = slot3.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 479;BA.debugLine="word2.Top = slot3.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 481;BA.debugLine="word2.Left = slot4.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 482;BA.debugLine="word2.Top = slot4.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 484;BA.debugLine="word2.Left = slot5.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 485;BA.debugLine="word2.Top = slot5.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 487;BA.debugLine="word2.Left = slot6.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 488;BA.debugLine="word2.Top = slot6.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word2.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word2.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word2.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word2.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 490;BA.debugLine="word2.Left = slot7.Left + 5dip";
mostCurrent._word2.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 491;BA.debugLine="word2.Top = slot7.Top + 5dip";
mostCurrent._word2.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 493;BA.debugLine="End Sub";
return "";
}
public static boolean  _word2_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 677;BA.debugLine="Sub word2_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 678;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 679;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 681;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 683;BA.debugLine="End Sub";
return false;
}
public static String  _word3_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 494;BA.debugLine="Sub word3_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 495;BA.debugLine="Dim newleft As Int = Max(0, Min(word3.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word3.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word3.getWidth())));
 //BA.debugLineNum = 496;BA.debugLine="Dim newtop As Int = Max(0, Min(word3.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word3.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word3.getHeight())));
 //BA.debugLineNum = 497;BA.debugLine="word3.Left = newleft";
mostCurrent._word3.setLeft(_newleft);
 //BA.debugLineNum = 498;BA.debugLine="word3.Top = newtop";
mostCurrent._word3.setTop(_newtop);
 //BA.debugLineNum = 499;BA.debugLine="word3.Tag = \"\"";
mostCurrent._word3.setTag((Object)(""));
 //BA.debugLineNum = 500;BA.debugLine="checkOK3.Left = 6000dip";
mostCurrent._checkok3.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 501;BA.debugLine="checkOK3.Top = 6000dip";
mostCurrent._checkok3.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 503;BA.debugLine="If word3.Left >= slot1.Left And word3.Left <= slo";
if (mostCurrent._word3.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 504;BA.debugLine="word3.Left = slot1.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 505;BA.debugLine="word3.Top = slot1.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 507;BA.debugLine="word3.Left = slot2.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 508;BA.debugLine="word3.Top = slot2.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 510;BA.debugLine="word3.Left = slot3.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 511;BA.debugLine="word3.Top = slot3.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 513;BA.debugLine="word3.Left = slot4.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 514;BA.debugLine="word3.Top = slot4.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 516;BA.debugLine="word3.Left = slot5.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 517;BA.debugLine="word3.Top = slot5.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 519;BA.debugLine="word3.Left = slot6.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 520;BA.debugLine="word3.Top = slot6.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 521;BA.debugLine="checkOK3.Left = word3.Left + word3.Width";
mostCurrent._checkok3.setLeft((int) (mostCurrent._word3.getLeft()+mostCurrent._word3.getWidth()));
 //BA.debugLineNum = 522;BA.debugLine="checkOK3.Top = word3.Top";
mostCurrent._checkok3.setTop(mostCurrent._word3.getTop());
 //BA.debugLineNum = 523;BA.debugLine="word3.Tag = \"OK\"";
mostCurrent._word3.setTag((Object)("OK"));
 }else if(mostCurrent._word3.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word3.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word3.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word3.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 525;BA.debugLine="word3.Left = slot7.Left + 5dip";
mostCurrent._word3.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 526;BA.debugLine="word3.Top = slot7.Top + 5dip";
mostCurrent._word3.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 528;BA.debugLine="End Sub";
return "";
}
public static boolean  _word3_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 684;BA.debugLine="Sub word3_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 685;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 686;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 688;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 690;BA.debugLine="End Sub";
return false;
}
public static String  _word4_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 529;BA.debugLine="Sub word4_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 530;BA.debugLine="Dim newleft As Int = Max(0, Min(word4.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word4.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word4.getWidth())));
 //BA.debugLineNum = 531;BA.debugLine="Dim newtop As Int = Max(0, Min(word4.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word4.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word4.getHeight())));
 //BA.debugLineNum = 532;BA.debugLine="word4.Left = newleft";
mostCurrent._word4.setLeft(_newleft);
 //BA.debugLineNum = 533;BA.debugLine="word4.Top = newtop";
mostCurrent._word4.setTop(_newtop);
 //BA.debugLineNum = 534;BA.debugLine="word4.Tag = \"\"";
mostCurrent._word4.setTag((Object)(""));
 //BA.debugLineNum = 535;BA.debugLine="checkOK4.Left = 6000dip";
mostCurrent._checkok4.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 536;BA.debugLine="checkOK4.Top = 6000dip";
mostCurrent._checkok4.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 538;BA.debugLine="If word4.Left >= slot1.Left And word4.Left <= slo";
if (mostCurrent._word4.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 539;BA.debugLine="word4.Left = slot1.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 540;BA.debugLine="word4.Top = slot1.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 542;BA.debugLine="word4.Left = slot2.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 543;BA.debugLine="word4.Top = slot2.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 545;BA.debugLine="word4.Left = slot3.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 546;BA.debugLine="word4.Top = slot3.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 547;BA.debugLine="checkOK4.Left = word4.Left + word4.Width";
mostCurrent._checkok4.setLeft((int) (mostCurrent._word4.getLeft()+mostCurrent._word4.getWidth()));
 //BA.debugLineNum = 548;BA.debugLine="checkOK4.Top = word4.Top";
mostCurrent._checkok4.setTop(mostCurrent._word4.getTop());
 //BA.debugLineNum = 549;BA.debugLine="word4.Tag = \"OK\"";
mostCurrent._word4.setTag((Object)("OK"));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 551;BA.debugLine="word4.Left = slot4.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 552;BA.debugLine="word4.Top = slot4.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 554;BA.debugLine="word4.Left = slot5.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 555;BA.debugLine="word4.Top = slot5.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 557;BA.debugLine="word4.Left = slot6.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 558;BA.debugLine="word4.Top = slot6.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word4.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word4.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word4.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word4.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 560;BA.debugLine="word4.Left = slot7.Left + 5dip";
mostCurrent._word4.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 561;BA.debugLine="word4.Top = slot7.Top + 5dip";
mostCurrent._word4.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 563;BA.debugLine="End Sub";
return "";
}
public static boolean  _word4_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 691;BA.debugLine="Sub word4_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 692;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 693;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 695;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 697;BA.debugLine="End Sub";
return false;
}
public static String  _word5_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 564;BA.debugLine="Sub word5_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 565;BA.debugLine="Dim newleft As Int = Max(0, Min(word5.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word5.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word5.getWidth())));
 //BA.debugLineNum = 566;BA.debugLine="Dim newtop As Int = Max(0, Min(word5.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word5.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word5.getHeight())));
 //BA.debugLineNum = 567;BA.debugLine="word5.Left = newleft";
mostCurrent._word5.setLeft(_newleft);
 //BA.debugLineNum = 568;BA.debugLine="word5.Top = newtop";
mostCurrent._word5.setTop(_newtop);
 //BA.debugLineNum = 569;BA.debugLine="word5.Tag = \"\"";
mostCurrent._word5.setTag((Object)(""));
 //BA.debugLineNum = 570;BA.debugLine="checkOK5.Left = 6000dip";
mostCurrent._checkok5.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 571;BA.debugLine="checkOK5.Top = 6000dip";
mostCurrent._checkok5.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 573;BA.debugLine="If word5.Left >= slot1.Left And word5.Left <= slo";
if (mostCurrent._word5.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 574;BA.debugLine="word5.Left = slot1.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 575;BA.debugLine="word5.Top = slot1.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 577;BA.debugLine="word5.Left = slot2.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 578;BA.debugLine="word5.Top = slot2.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 580;BA.debugLine="word5.Left = slot3.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 581;BA.debugLine="word5.Top = slot3.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 583;BA.debugLine="word5.Left = slot4.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 584;BA.debugLine="word5.Top = slot4.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 586;BA.debugLine="word5.Left = slot5.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 587;BA.debugLine="word5.Top = slot5.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 588;BA.debugLine="checkOK5.Left = word5.Left + word5.Width";
mostCurrent._checkok5.setLeft((int) (mostCurrent._word5.getLeft()+mostCurrent._word5.getWidth()));
 //BA.debugLineNum = 589;BA.debugLine="checkOK5.Top = word5.Top";
mostCurrent._checkok5.setTop(mostCurrent._word5.getTop());
 //BA.debugLineNum = 590;BA.debugLine="word5.Tag = \"OK\"";
mostCurrent._word5.setTag((Object)("OK"));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 592;BA.debugLine="word5.Left = slot6.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 593;BA.debugLine="word5.Top = slot6.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word5.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word5.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word5.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word5.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 595;BA.debugLine="word5.Left = slot7.Left + 5dip";
mostCurrent._word5.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 596;BA.debugLine="word5.Top = slot7.Top + 5dip";
mostCurrent._word5.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 598;BA.debugLine="End Sub";
return "";
}
public static boolean  _word5_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 698;BA.debugLine="Sub word5_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 699;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 700;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 702;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 704;BA.debugLine="End Sub";
return false;
}
public static String  _word6_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 599;BA.debugLine="Sub word6_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 600;BA.debugLine="Dim newleft As Int = Max(0, Min(word6.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word6.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word6.getWidth())));
 //BA.debugLineNum = 601;BA.debugLine="Dim newtop As Int = Max(0, Min(word6.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word6.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word6.getHeight())));
 //BA.debugLineNum = 602;BA.debugLine="word6.Left = newleft";
mostCurrent._word6.setLeft(_newleft);
 //BA.debugLineNum = 603;BA.debugLine="word6.Top = newtop";
mostCurrent._word6.setTop(_newtop);
 //BA.debugLineNum = 604;BA.debugLine="word6.Tag = \"\"";
mostCurrent._word6.setTag((Object)(""));
 //BA.debugLineNum = 605;BA.debugLine="checkOK6.Left = 6000dip";
mostCurrent._checkok6.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 606;BA.debugLine="checkOK6.Top = 6000dip";
mostCurrent._checkok6.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 608;BA.debugLine="If word6.Left >= slot1.Left And word6.Left <= slo";
if (mostCurrent._word6.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 609;BA.debugLine="word6.Left = slot1.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 610;BA.debugLine="word6.Top = slot1.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 612;BA.debugLine="word6.Left = slot2.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 613;BA.debugLine="word6.Top = slot2.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 615;BA.debugLine="word6.Left = slot3.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 616;BA.debugLine="word6.Top = slot3.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 618;BA.debugLine="word6.Left = slot4.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 619;BA.debugLine="word6.Top = slot4.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 621;BA.debugLine="word6.Left = slot5.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 622;BA.debugLine="word6.Top = slot5.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 624;BA.debugLine="word6.Left = slot6.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 625;BA.debugLine="word6.Top = slot6.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word6.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word6.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word6.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word6.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 627;BA.debugLine="word6.Left = slot7.Left + 5dip";
mostCurrent._word6.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 628;BA.debugLine="word6.Top = slot7.Top + 5dip";
mostCurrent._word6.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 629;BA.debugLine="checkOK6.Left = word6.Left + word6.Width";
mostCurrent._checkok6.setLeft((int) (mostCurrent._word6.getLeft()+mostCurrent._word6.getWidth()));
 //BA.debugLineNum = 630;BA.debugLine="checkOK6.Top = word6.Top";
mostCurrent._checkok6.setTop(mostCurrent._word6.getTop());
 //BA.debugLineNum = 631;BA.debugLine="word6.Tag = \"OK\"";
mostCurrent._word6.setTag((Object)("OK"));
 };
 //BA.debugLineNum = 633;BA.debugLine="End Sub";
return "";
}
public static boolean  _word6_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 705;BA.debugLine="Sub word6_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 706;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 707;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 709;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 711;BA.debugLine="End Sub";
return false;
}
public static String  _word7_ondrag(float _deltax,float _deltay,Object _motionevent) throws Exception{
int _newleft = 0;
int _newtop = 0;
 //BA.debugLineNum = 634;BA.debugLine="Sub word7_onDrag(deltaX As Float, deltaY As Float,";
 //BA.debugLineNum = 635;BA.debugLine="Dim newleft As Int = Max(0, Min(word7.Left + delt";
_newleft = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word7.getLeft()+_deltax,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word7.getWidth())));
 //BA.debugLineNum = 636;BA.debugLine="Dim newtop As Int = Max(0, Min(word7.Top + deltaY";
_newtop = (int) (anywheresoftware.b4a.keywords.Common.Max(0,anywheresoftware.b4a.keywords.Common.Min(mostCurrent._word7.getTop()+_deltay,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-mostCurrent._word7.getHeight())));
 //BA.debugLineNum = 637;BA.debugLine="word7.Left = newleft";
mostCurrent._word7.setLeft(_newleft);
 //BA.debugLineNum = 638;BA.debugLine="word7.Top = newtop";
mostCurrent._word7.setTop(_newtop);
 //BA.debugLineNum = 639;BA.debugLine="word7.Tag = \"\"";
mostCurrent._word7.setTag((Object)(""));
 //BA.debugLineNum = 640;BA.debugLine="checkOK7.Left = 6000dip";
mostCurrent._checkok7.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 641;BA.debugLine="checkOK7.Top = 6000dip";
mostCurrent._checkok7.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (6000)));
 //BA.debugLineNum = 643;BA.debugLine="If word7.Left >= slot1.Left And word7.Left <= slo";
if (mostCurrent._word7.getLeft()>=mostCurrent._slot1.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot1.getLeft()+mostCurrent._slot1.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot1.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot1.getTop()+mostCurrent._slot1.getHeight()) { 
 //BA.debugLineNum = 644;BA.debugLine="word7.Left = slot1.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot1.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 645;BA.debugLine="word7.Top = slot1.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot1.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot2.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot2.getLeft()+mostCurrent._slot2.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot2.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot2.getTop()+mostCurrent._slot2.getHeight()) { 
 //BA.debugLineNum = 647;BA.debugLine="word7.Left = slot2.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot2.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 648;BA.debugLine="word7.Top = slot2.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot2.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot3.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot3.getLeft()+mostCurrent._slot3.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot3.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot3.getTop()+mostCurrent._slot3.getHeight()) { 
 //BA.debugLineNum = 650;BA.debugLine="word7.Left = slot3.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot3.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 651;BA.debugLine="word7.Top = slot3.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot3.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot4.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot4.getLeft()+mostCurrent._slot4.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot4.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot4.getTop()+mostCurrent._slot4.getHeight()) { 
 //BA.debugLineNum = 653;BA.debugLine="word7.Left = slot4.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot4.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 654;BA.debugLine="word7.Top = slot4.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot4.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 655;BA.debugLine="checkOK7.Left = word7.Left + word7.Width";
mostCurrent._checkok7.setLeft((int) (mostCurrent._word7.getLeft()+mostCurrent._word7.getWidth()));
 //BA.debugLineNum = 656;BA.debugLine="checkOK7.Top = word7.Top";
mostCurrent._checkok7.setTop(mostCurrent._word7.getTop());
 //BA.debugLineNum = 657;BA.debugLine="word7.Tag = \"OK\"";
mostCurrent._word7.setTag((Object)("OK"));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot5.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot5.getLeft()+mostCurrent._slot5.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot5.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot5.getTop()+mostCurrent._slot5.getHeight()) { 
 //BA.debugLineNum = 659;BA.debugLine="word7.Left = slot5.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot5.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 660;BA.debugLine="word7.Top = slot5.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot5.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot6.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot6.getLeft()+mostCurrent._slot6.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot6.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot6.getTop()+mostCurrent._slot6.getHeight()) { 
 //BA.debugLineNum = 662;BA.debugLine="word7.Left = slot6.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot6.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 663;BA.debugLine="word7.Top = slot6.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot6.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 }else if(mostCurrent._word7.getLeft()>=mostCurrent._slot7.getLeft() && mostCurrent._word7.getLeft()<=mostCurrent._slot7.getLeft()+mostCurrent._slot7.getWidth() && mostCurrent._word7.getTop()>=mostCurrent._slot7.getTop() && mostCurrent._word7.getTop()<=mostCurrent._slot7.getTop()+mostCurrent._slot7.getHeight()) { 
 //BA.debugLineNum = 665;BA.debugLine="word7.Left = slot7.Left + 5dip";
mostCurrent._word7.setLeft((int) (mostCurrent._slot7.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 //BA.debugLineNum = 666;BA.debugLine="word7.Top = slot7.Top + 5dip";
mostCurrent._word7.setTop((int) (mostCurrent._slot7.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5))));
 };
 //BA.debugLineNum = 668;BA.debugLine="End Sub";
return "";
}
public static boolean  _word7_ontouch(int _action,float _x,float _y,Object _motionevent) throws Exception{
 //BA.debugLineNum = 712;BA.debugLine="Sub word7_onTouch(Action As Int, X As Float, Y As";
 //BA.debugLineNum = 713;BA.debugLine="If Action = 1 Then";
if (_action==1) { 
 //BA.debugLineNum = 714;BA.debugLine="Chequear";
_chequear();
 };
 //BA.debugLineNum = 716;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 718;BA.debugLine="End Sub";
return false;
}
}
