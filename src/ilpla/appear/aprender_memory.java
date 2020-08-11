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

public class aprender_memory extends Activity implements B4AActivity{
	public static aprender_memory mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.aprender_memory");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (aprender_memory).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.aprender_memory");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.aprender_memory", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (aprender_memory) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (aprender_memory) Resume **");
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
		return aprender_memory.class;
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
            BA.LogInfo("** Activity (aprender_memory) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (aprender_memory) Pause event (activity is not paused). **");
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
            aprender_memory mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (aprender_memory) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _timer1 = null;
public static anywheresoftware.b4a.objects.Timer _timer2 = null;
public static int _tiempo = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button8 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button9 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button10 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button11 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button12 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button13 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button14 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button15 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button16 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button17 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button18 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button19 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button20 = null;
public static int _clicks = 0;
public static int[] _loc = null;
public static int[] _sel = null;
public static int _x = 0;
public static int _y = 0;
public static int _z = 0;
public static int _select1 = 0;
public static int _select2 = 0;
public static int _loc1 = 0;
public static int _loc2 = 0;
public anywheresoftware.b4a.objects.drawable.CanvasWrapper _canvas1 = null;
public static String _busy = "";
public static int _bt = 0;
public static int _lc = 0;
public static int _match = 0;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview8 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview10 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview11 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview12 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview13 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview14 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview15 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview16 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview17 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview18 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview19 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview20 = null;
public static int _one = 0;
public static int _two = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lblresultado = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmperfil _frmperfil = null;
public ilpla.appear.utilidades _utilidades = null;
public ilpla.appear.register _register = null;
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

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 78;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 79;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 80;BA.debugLine="Timer1.Initialize(\"Timer1\",1000)";
_timer1.Initialize(processBA,"Timer1",(long) (1000));
 //BA.debugLineNum = 81;BA.debugLine="Timer2.Initialize(\"Timer1\",4000)";
_timer2.Initialize(processBA,"Timer1",(long) (4000));
 };
 //BA.debugLineNum = 83;BA.debugLine="Activity.LoadLayout(\"Aprender_Memory\")";
mostCurrent._activity.LoadLayout("Aprender_Memory",mostCurrent.activityBA);
 //BA.debugLineNum = 84;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 85;BA.debugLine="restart";
_restart();
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 87;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 88;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 91;BA.debugLine="Timer1.Enabled = True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 734;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 735;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 736;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 737;BA.debugLine="End Sub";
return "";
}
public static String  _btnreset_click() throws Exception{
 //BA.debugLineNum = 729;BA.debugLine="Sub btnReset_Click";
 //BA.debugLineNum = 730;BA.debugLine="tiempo = 0";
_tiempo = (int) (0);
 //BA.debugLineNum = 731;BA.debugLine="restart";
_restart();
 //BA.debugLineNum = 732;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 216;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 221;BA.debugLine="lc=loc(0)";
_lc = _loc[(int) (0)];
 //BA.debugLineNum = 222;BA.debugLine="bt=1";
_bt = (int) (1);
 //BA.debugLineNum = 223;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _button10_click() throws Exception{
 //BA.debugLineNum = 265;BA.debugLine="Sub Button10_Click";
 //BA.debugLineNum = 266;BA.debugLine="lc=loc(9)";
_lc = _loc[(int) (9)];
 //BA.debugLineNum = 267;BA.debugLine="bt=10";
_bt = (int) (10);
 //BA.debugLineNum = 268;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 269;BA.debugLine="End Sub";
return "";
}
public static String  _button11_click() throws Exception{
 //BA.debugLineNum = 270;BA.debugLine="Sub Button11_Click";
 //BA.debugLineNum = 271;BA.debugLine="lc=loc(10)";
_lc = _loc[(int) (10)];
 //BA.debugLineNum = 272;BA.debugLine="bt=11";
_bt = (int) (11);
 //BA.debugLineNum = 273;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 274;BA.debugLine="End Sub";
return "";
}
public static String  _button12_click() throws Exception{
 //BA.debugLineNum = 275;BA.debugLine="Sub Button12_Click";
 //BA.debugLineNum = 276;BA.debugLine="lc=loc(11)";
_lc = _loc[(int) (11)];
 //BA.debugLineNum = 277;BA.debugLine="bt=12";
_bt = (int) (12);
 //BA.debugLineNum = 278;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
public static String  _button13_click() throws Exception{
 //BA.debugLineNum = 280;BA.debugLine="Sub Button13_Click";
 //BA.debugLineNum = 281;BA.debugLine="lc=loc(12)";
_lc = _loc[(int) (12)];
 //BA.debugLineNum = 282;BA.debugLine="bt=13";
_bt = (int) (13);
 //BA.debugLineNum = 283;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 284;BA.debugLine="End Sub";
return "";
}
public static String  _button14_click() throws Exception{
 //BA.debugLineNum = 285;BA.debugLine="Sub Button14_Click";
 //BA.debugLineNum = 286;BA.debugLine="lc=loc(13)";
_lc = _loc[(int) (13)];
 //BA.debugLineNum = 287;BA.debugLine="bt=14";
_bt = (int) (14);
 //BA.debugLineNum = 288;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 289;BA.debugLine="End Sub";
return "";
}
public static String  _button15_click() throws Exception{
 //BA.debugLineNum = 290;BA.debugLine="Sub Button15_Click";
 //BA.debugLineNum = 291;BA.debugLine="lc=loc(14)";
_lc = _loc[(int) (14)];
 //BA.debugLineNum = 292;BA.debugLine="bt=15";
_bt = (int) (15);
 //BA.debugLineNum = 293;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 294;BA.debugLine="End Sub";
return "";
}
public static String  _button16_click() throws Exception{
 //BA.debugLineNum = 295;BA.debugLine="Sub Button16_Click";
 //BA.debugLineNum = 296;BA.debugLine="lc=loc(15)";
_lc = _loc[(int) (15)];
 //BA.debugLineNum = 297;BA.debugLine="bt=16";
_bt = (int) (16);
 //BA.debugLineNum = 298;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 299;BA.debugLine="End Sub";
return "";
}
public static String  _button17_click() throws Exception{
 //BA.debugLineNum = 300;BA.debugLine="Sub Button17_Click";
 //BA.debugLineNum = 301;BA.debugLine="lc=loc(16)";
_lc = _loc[(int) (16)];
 //BA.debugLineNum = 302;BA.debugLine="bt=17";
_bt = (int) (17);
 //BA.debugLineNum = 303;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 304;BA.debugLine="End Sub";
return "";
}
public static String  _button18_click() throws Exception{
 //BA.debugLineNum = 305;BA.debugLine="Sub Button18_Click";
 //BA.debugLineNum = 306;BA.debugLine="lc=loc(17)";
_lc = _loc[(int) (17)];
 //BA.debugLineNum = 307;BA.debugLine="bt=18";
_bt = (int) (18);
 //BA.debugLineNum = 308;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _button19_click() throws Exception{
 //BA.debugLineNum = 310;BA.debugLine="Sub Button19_Click";
 //BA.debugLineNum = 311;BA.debugLine="lc=loc(18)";
_lc = _loc[(int) (18)];
 //BA.debugLineNum = 312;BA.debugLine="bt=19";
_bt = (int) (19);
 //BA.debugLineNum = 313;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 314;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 225;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 226;BA.debugLine="lc=loc(1)";
_lc = _loc[(int) (1)];
 //BA.debugLineNum = 227;BA.debugLine="bt=2";
_bt = (int) (2);
 //BA.debugLineNum = 228;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 229;BA.debugLine="End Sub";
return "";
}
public static String  _button20_click() throws Exception{
 //BA.debugLineNum = 315;BA.debugLine="Sub Button20_Click";
 //BA.debugLineNum = 316;BA.debugLine="lc=loc(19)";
_lc = _loc[(int) (19)];
 //BA.debugLineNum = 317;BA.debugLine="bt=20";
_bt = (int) (20);
 //BA.debugLineNum = 318;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 319;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 230;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 231;BA.debugLine="lc=loc(2)";
_lc = _loc[(int) (2)];
 //BA.debugLineNum = 232;BA.debugLine="bt=3";
_bt = (int) (3);
 //BA.debugLineNum = 233;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public static String  _button4_click() throws Exception{
 //BA.debugLineNum = 235;BA.debugLine="Sub Button4_Click";
 //BA.debugLineNum = 236;BA.debugLine="lc=loc(3)";
_lc = _loc[(int) (3)];
 //BA.debugLineNum = 237;BA.debugLine="bt=4";
_bt = (int) (4);
 //BA.debugLineNum = 238;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 239;BA.debugLine="End Sub";
return "";
}
public static String  _button5_click() throws Exception{
 //BA.debugLineNum = 240;BA.debugLine="Sub Button5_Click";
 //BA.debugLineNum = 241;BA.debugLine="lc=loc(4)";
_lc = _loc[(int) (4)];
 //BA.debugLineNum = 242;BA.debugLine="bt=5";
_bt = (int) (5);
 //BA.debugLineNum = 243;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 244;BA.debugLine="End Sub";
return "";
}
public static String  _button6_click() throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Sub Button6_Click";
 //BA.debugLineNum = 246;BA.debugLine="lc=loc(5)";
_lc = _loc[(int) (5)];
 //BA.debugLineNum = 247;BA.debugLine="bt=6";
_bt = (int) (6);
 //BA.debugLineNum = 248;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public static String  _button7_click() throws Exception{
 //BA.debugLineNum = 250;BA.debugLine="Sub Button7_Click";
 //BA.debugLineNum = 251;BA.debugLine="lc=loc(6)";
_lc = _loc[(int) (6)];
 //BA.debugLineNum = 252;BA.debugLine="bt=7";
_bt = (int) (7);
 //BA.debugLineNum = 253;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _button8_click() throws Exception{
 //BA.debugLineNum = 255;BA.debugLine="Sub Button8_Click";
 //BA.debugLineNum = 256;BA.debugLine="lc=loc(7)";
_lc = _loc[(int) (7)];
 //BA.debugLineNum = 257;BA.debugLine="bt=8";
_bt = (int) (8);
 //BA.debugLineNum = 258;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public static String  _button9_click() throws Exception{
 //BA.debugLineNum = 260;BA.debugLine="Sub Button9_Click";
 //BA.debugLineNum = 261;BA.debugLine="lc=loc(8)";
_lc = _loc[(int) (8)];
 //BA.debugLineNum = 262;BA.debugLine="bt=9";
_bt = (int) (9);
 //BA.debugLineNum = 263;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 264;BA.debugLine="End Sub";
return "";
}
public static String  _calcx() throws Exception{
 //BA.debugLineNum = 599;BA.debugLine="Sub calcx";
 //BA.debugLineNum = 601;BA.debugLine="If busy=\"no\" Then";
if ((mostCurrent._busy).equals("no")) { 
 //BA.debugLineNum = 602;BA.debugLine="clicks=clicks+1";
_clicks = (int) (_clicks+1);
 //BA.debugLineNum = 603;BA.debugLine="tiempo = tiempo + 1";
_tiempo = (int) (_tiempo+1);
 //BA.debugLineNum = 604;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 605;BA.debugLine="lblResultado.Text = \"Movimientos: \" & tiempo";
mostCurrent._lblresultado.setText(BA.ObjectToCharSequence("Movimientos: "+BA.NumberToString(_tiempo)));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 607;BA.debugLine="lblResultado.Text = \"Movements: \" & tiempo";
mostCurrent._lblresultado.setText(BA.ObjectToCharSequence("Movements: "+BA.NumberToString(_tiempo)));
 };
 //BA.debugLineNum = 611;BA.debugLine="If clicks<=2 And bt=1 Then";
if (_clicks<=2 && _bt==1) { 
 //BA.debugLineNum = 612;BA.debugLine="button1.visible=False";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 615;BA.debugLine="If clicks<=2 And bt=2 Then";
if (_clicks<=2 && _bt==2) { 
 //BA.debugLineNum = 616;BA.debugLine="button2.visible=False";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 619;BA.debugLine="If clicks<=2 And bt=3 Then";
if (_clicks<=2 && _bt==3) { 
 //BA.debugLineNum = 620;BA.debugLine="button3.visible=False";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 623;BA.debugLine="If clicks<=2 And bt=4 Then";
if (_clicks<=2 && _bt==4) { 
 //BA.debugLineNum = 624;BA.debugLine="button4.visible=False";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 627;BA.debugLine="If clicks<=2 And bt=5 Then";
if (_clicks<=2 && _bt==5) { 
 //BA.debugLineNum = 628;BA.debugLine="button5.visible=False";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 631;BA.debugLine="If clicks<=2 And bt=6 Then";
if (_clicks<=2 && _bt==6) { 
 //BA.debugLineNum = 632;BA.debugLine="button6.visible=False";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 635;BA.debugLine="If clicks<=2 And bt=7 Then";
if (_clicks<=2 && _bt==7) { 
 //BA.debugLineNum = 636;BA.debugLine="button7.visible=False";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 639;BA.debugLine="If clicks<=2 And bt=8 Then";
if (_clicks<=2 && _bt==8) { 
 //BA.debugLineNum = 640;BA.debugLine="button8.visible=False";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 643;BA.debugLine="If clicks<=2 And bt=9 Then";
if (_clicks<=2 && _bt==9) { 
 //BA.debugLineNum = 644;BA.debugLine="button9.visible=False";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 647;BA.debugLine="If clicks<=2 And bt=10 Then";
if (_clicks<=2 && _bt==10) { 
 //BA.debugLineNum = 648;BA.debugLine="button10.visible=False";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 651;BA.debugLine="If clicks<=2 And bt=11 Then";
if (_clicks<=2 && _bt==11) { 
 //BA.debugLineNum = 652;BA.debugLine="button11.visible=False";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 655;BA.debugLine="If clicks<=2 And bt=12 Then";
if (_clicks<=2 && _bt==12) { 
 //BA.debugLineNum = 656;BA.debugLine="button12.visible=False";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 659;BA.debugLine="If clicks<=2 And bt=13 Then";
if (_clicks<=2 && _bt==13) { 
 //BA.debugLineNum = 660;BA.debugLine="button13.visible=False";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 663;BA.debugLine="If clicks<=2 And bt=14 Then";
if (_clicks<=2 && _bt==14) { 
 //BA.debugLineNum = 664;BA.debugLine="button14.visible=False";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 667;BA.debugLine="If clicks<=2 And bt=15 Then";
if (_clicks<=2 && _bt==15) { 
 //BA.debugLineNum = 668;BA.debugLine="button15.visible=False";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 671;BA.debugLine="If clicks<=2 And bt=16 Then";
if (_clicks<=2 && _bt==16) { 
 //BA.debugLineNum = 672;BA.debugLine="button16.visible=False";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 675;BA.debugLine="If clicks<=2 And bt=17 Then";
if (_clicks<=2 && _bt==17) { 
 //BA.debugLineNum = 676;BA.debugLine="button17.visible=False";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 679;BA.debugLine="If clicks<=2 And bt=18 Then";
if (_clicks<=2 && _bt==18) { 
 //BA.debugLineNum = 680;BA.debugLine="button18.visible=False";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 683;BA.debugLine="If clicks<=2 And bt=19 Then";
if (_clicks<=2 && _bt==19) { 
 //BA.debugLineNum = 684;BA.debugLine="button19.visible=False";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 687;BA.debugLine="If clicks<=2 And bt=20 Then";
if (_clicks<=2 && _bt==20) { 
 //BA.debugLineNum = 688;BA.debugLine="button20.visible=False";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 691;BA.debugLine="If clicks=1 Then";
if (_clicks==1) { 
 //BA.debugLineNum = 692;BA.debugLine="Select1=lc";
_select1 = _lc;
 //BA.debugLineNum = 693;BA.debugLine="one=bt";
_one = _bt;
 //BA.debugLineNum = 694;BA.debugLine="loc1=bt";
_loc1 = _bt;
 };
 //BA.debugLineNum = 698;BA.debugLine="If clicks=2 Then";
if (_clicks==2) { 
 //BA.debugLineNum = 699;BA.debugLine="select2=lc";
_select2 = _lc;
 //BA.debugLineNum = 700;BA.debugLine="two=bt";
_two = _bt;
 //BA.debugLineNum = 704;BA.debugLine="If Select1=select2 Then";
if (_select1==_select2) { 
 //BA.debugLineNum = 706;BA.debugLine="match=match+1";
_match = (int) (_match+1);
 }else {
 //BA.debugLineNum = 711;BA.debugLine="loc2=bt";
_loc2 = _bt;
 //BA.debugLineNum = 712;BA.debugLine="busy=\"yes\"";
mostCurrent._busy = "yes";
 //BA.debugLineNum = 713;BA.debugLine="Timer1.Enabled=True";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 716;BA.debugLine="clicks=0";
_clicks = (int) (0);
 };
 //BA.debugLineNum = 719;BA.debugLine="If match=10 Then";
if (_match==10) { 
 //BA.debugLineNum = 720;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 721;BA.debugLine="Msgbox(\"Felicitaciones! Utilizaste \" & tiempo &";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Felicitaciones! Utilizaste "+BA.NumberToString(_tiempo)+" movimientos"),BA.ObjectToCharSequence(""),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 723;BA.debugLine="Msgbox(\"Congratulations! You used \" & tiempo &";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Congratulations! You used "+BA.NumberToString(_tiempo)+" movements"),BA.ObjectToCharSequence(""),mostCurrent.activityBA);
 };
 };
 };
 //BA.debugLineNum = 727;BA.debugLine="End Sub";
return "";
}
public static String  _delayx() throws Exception{
 //BA.debugLineNum = 322;BA.debugLine="Sub delayx";
 //BA.debugLineNum = 324;BA.debugLine="For x=1 To 1000000";
{
final int step1 = 1;
final int limit1 = (int) (1000000);
_x = (int) (1) ;
for (;_x <= limit1 ;_x = _x + step1 ) {
 }
};
 //BA.debugLineNum = 327;BA.debugLine="If loc1=1 Or loc2=1 Then";
if (_loc1==1 || _loc2==1) { 
 //BA.debugLineNum = 328;BA.debugLine="button1.visible=True";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 331;BA.debugLine="If loc1=2 Or loc2=2 Then";
if (_loc1==2 || _loc2==2) { 
 //BA.debugLineNum = 332;BA.debugLine="button2.visible=True";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 335;BA.debugLine="If loc1=3 Or loc2=3 Then";
if (_loc1==3 || _loc2==3) { 
 //BA.debugLineNum = 336;BA.debugLine="button3.visible=True";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 339;BA.debugLine="If loc1=4 Or loc2=4 Then";
if (_loc1==4 || _loc2==4) { 
 //BA.debugLineNum = 340;BA.debugLine="button4.visible=True";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 343;BA.debugLine="If loc1=5 Or loc2=5 Then";
if (_loc1==5 || _loc2==5) { 
 //BA.debugLineNum = 344;BA.debugLine="button5.visible=True";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 347;BA.debugLine="If loc1=6 Or loc2=6 Then";
if (_loc1==6 || _loc2==6) { 
 //BA.debugLineNum = 348;BA.debugLine="button6.visible=True";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 351;BA.debugLine="If loc1=7 Or loc2=7 Then";
if (_loc1==7 || _loc2==7) { 
 //BA.debugLineNum = 352;BA.debugLine="button7.visible=True";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 355;BA.debugLine="If loc1=8 Or loc2=8 Then";
if (_loc1==8 || _loc2==8) { 
 //BA.debugLineNum = 356;BA.debugLine="button8.visible=True";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 359;BA.debugLine="If loc1=9 Or loc2=9 Then";
if (_loc1==9 || _loc2==9) { 
 //BA.debugLineNum = 360;BA.debugLine="button9.visible=True";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 363;BA.debugLine="If loc1=10 Or loc2=10 Then";
if (_loc1==10 || _loc2==10) { 
 //BA.debugLineNum = 364;BA.debugLine="button10.visible=True";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 367;BA.debugLine="If loc1=11 Or loc2=11 Then";
if (_loc1==11 || _loc2==11) { 
 //BA.debugLineNum = 368;BA.debugLine="button11.visible=True";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 371;BA.debugLine="If loc1=12 Or loc2=12 Then";
if (_loc1==12 || _loc2==12) { 
 //BA.debugLineNum = 372;BA.debugLine="button12.visible=True";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 375;BA.debugLine="If loc1=13 Or loc2=13 Then";
if (_loc1==13 || _loc2==13) { 
 //BA.debugLineNum = 376;BA.debugLine="button13.visible=True";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 379;BA.debugLine="If loc1=14 Or loc2=14 Then";
if (_loc1==14 || _loc2==14) { 
 //BA.debugLineNum = 380;BA.debugLine="button14.visible=True";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 383;BA.debugLine="If loc1=15 Or loc2=15 Then";
if (_loc1==15 || _loc2==15) { 
 //BA.debugLineNum = 384;BA.debugLine="button15.visible=True";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 387;BA.debugLine="If loc1=16 Or loc2=16 Then";
if (_loc1==16 || _loc2==16) { 
 //BA.debugLineNum = 388;BA.debugLine="button16.visible=True";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 391;BA.debugLine="If loc1=17 Or loc2=17 Then";
if (_loc1==17 || _loc2==17) { 
 //BA.debugLineNum = 392;BA.debugLine="button17.visible=True";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 395;BA.debugLine="If loc1=18 Or loc2=18 Then";
if (_loc1==18 || _loc2==18) { 
 //BA.debugLineNum = 396;BA.debugLine="button18.visible=True";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 399;BA.debugLine="If loc1=19 Or loc2=19 Then";
if (_loc1==19 || _loc2==19) { 
 //BA.debugLineNum = 400;BA.debugLine="button19.visible=True";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 403;BA.debugLine="If loc1=20 Or loc2=20 Then";
if (_loc1==20 || _loc2==20) { 
 //BA.debugLineNum = 404;BA.debugLine="button20.visible=True";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 407;BA.debugLine="busy=\"no\"";
mostCurrent._busy = "no";
 //BA.debugLineNum = 409;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim button4 As Button";
mostCurrent._button4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim button5 As Button";
mostCurrent._button5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim button6 As Button";
mostCurrent._button6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim button7 As Button";
mostCurrent._button7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim button8 As Button";
mostCurrent._button8 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim button9 As Button";
mostCurrent._button9 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim button10 As Button";
mostCurrent._button10 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim button11 As Button";
mostCurrent._button11 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim button12 As Button";
mostCurrent._button12 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim button13 As Button";
mostCurrent._button13 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim button14 As Button";
mostCurrent._button14 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim button15 As Button";
mostCurrent._button15 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim button16 As Button";
mostCurrent._button16 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim button17 As Button";
mostCurrent._button17 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim button18 As Button";
mostCurrent._button18 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim button19 As Button";
mostCurrent._button19 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim button20 As Button";
mostCurrent._button20 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim clicks As Int";
_clicks = 0;
 //BA.debugLineNum = 39;BA.debugLine="Dim loc(20) As Int";
_loc = new int[(int) (20)];
;
 //BA.debugLineNum = 40;BA.debugLine="Dim sel(20) As Int";
_sel = new int[(int) (20)];
;
 //BA.debugLineNum = 41;BA.debugLine="Dim x As Int";
_x = 0;
 //BA.debugLineNum = 42;BA.debugLine="Dim y As Int";
_y = 0;
 //BA.debugLineNum = 43;BA.debugLine="Dim z As Int";
_z = 0;
 //BA.debugLineNum = 44;BA.debugLine="Dim Select1 As Int";
_select1 = 0;
 //BA.debugLineNum = 45;BA.debugLine="Dim select2 As Int";
_select2 = 0;
 //BA.debugLineNum = 46;BA.debugLine="Dim loc1 As Int";
_loc1 = 0;
 //BA.debugLineNum = 47;BA.debugLine="Dim loc2 As Int";
_loc2 = 0;
 //BA.debugLineNum = 48;BA.debugLine="Dim Canvas1 As Canvas";
mostCurrent._canvas1 = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim busy As String";
mostCurrent._busy = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim bt As Int";
_bt = 0;
 //BA.debugLineNum = 51;BA.debugLine="Dim lc As Int";
_lc = 0;
 //BA.debugLineNum = 52;BA.debugLine="Dim match As Int";
_match = 0;
 //BA.debugLineNum = 53;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Dim ImageView3 As ImageView";
mostCurrent._imageview3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Dim ImageView4 As ImageView";
mostCurrent._imageview4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 57;BA.debugLine="Dim ImageView5 As ImageView";
mostCurrent._imageview5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 58;BA.debugLine="Dim ImageView6 As ImageView";
mostCurrent._imageview6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Dim ImageView7 As ImageView";
mostCurrent._imageview7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Dim ImageView8 As ImageView";
mostCurrent._imageview8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim ImageView9 As ImageView";
mostCurrent._imageview9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim ImageView10 As ImageView";
mostCurrent._imageview10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim ImageView11 As ImageView";
mostCurrent._imageview11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Dim ImageView12 As ImageView";
mostCurrent._imageview12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 65;BA.debugLine="Dim ImageView13 As ImageView";
mostCurrent._imageview13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 66;BA.debugLine="Dim ImageView14 As ImageView";
mostCurrent._imageview14 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 67;BA.debugLine="Dim ImageView15 As ImageView";
mostCurrent._imageview15 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 68;BA.debugLine="Dim ImageView16 As ImageView";
mostCurrent._imageview16 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Dim ImageView17 As ImageView";
mostCurrent._imageview17 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Dim ImageView18 As ImageView";
mostCurrent._imageview18 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim ImageView19 As ImageView";
mostCurrent._imageview19 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 72;BA.debugLine="Dim ImageView20 As ImageView";
mostCurrent._imageview20 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 73;BA.debugLine="Dim one As Int";
_one = 0;
 //BA.debugLineNum = 74;BA.debugLine="Dim two As Int";
_two = 0;
 //BA.debugLineNum = 75;BA.debugLine="Private lblResultado As Label";
mostCurrent._lblresultado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public static String  _mix() throws Exception{
 //BA.debugLineNum = 586;BA.debugLine="Sub mix";
 //BA.debugLineNum = 588;BA.debugLine="If loc(x)=0 Then";
if (_loc[_x]==0) { 
 //BA.debugLineNum = 589;BA.debugLine="y=Rnd(0,20)";
_y = anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (20));
 };
 //BA.debugLineNum = 592;BA.debugLine="If sel(y) >0 And loc(x)=0 Then";
if (_sel[_y]>0 && _loc[_x]==0) { 
 //BA.debugLineNum = 593;BA.debugLine="loc(x)=sel(y)";
_loc[_x] = _sel[_y];
 //BA.debugLineNum = 594;BA.debugLine="sel(y)=0";
_sel[_y] = (int) (0);
 };
 //BA.debugLineNum = 597;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim Timer1 As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="Dim Timer2 As Timer";
_timer2 = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 11;BA.debugLine="Dim tiempo As Int";
_tiempo = 0;
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _restart() throws Exception{
int _a = 0;
 //BA.debugLineNum = 105;BA.debugLine="Sub restart";
 //BA.debugLineNum = 107;BA.debugLine="For x=0 To 19";
{
final int step1 = 1;
final int limit1 = (int) (19);
_x = (int) (0) ;
for (;_x <= limit1 ;_x = _x + step1 ) {
 //BA.debugLineNum = 108;BA.debugLine="loc(x)=0";
_loc[_x] = (int) (0);
 }
};
 //BA.debugLineNum = 112;BA.debugLine="For x=0 To 19";
{
final int step4 = 1;
final int limit4 = (int) (19);
_x = (int) (0) ;
for (;_x <= limit4 ;_x = _x + step4 ) {
 //BA.debugLineNum = 113;BA.debugLine="loc(x)=0";
_loc[_x] = (int) (0);
 }
};
 //BA.debugLineNum = 116;BA.debugLine="Dim a As Int";
_a = 0;
 //BA.debugLineNum = 118;BA.debugLine="For a=0 To 9";
{
final int step8 = 1;
final int limit8 = (int) (9);
_a = (int) (0) ;
for (;_a <= limit8 ;_a = _a + step8 ) {
 //BA.debugLineNum = 119;BA.debugLine="sel(a)=a+1";
_sel[_a] = (int) (_a+1);
 }
};
 //BA.debugLineNum = 122;BA.debugLine="For a=10 To 19";
{
final int step11 = 1;
final int limit11 = (int) (19);
_a = (int) (10) ;
for (;_a <= limit11 ;_a = _a + step11 ) {
 //BA.debugLineNum = 123;BA.debugLine="sel(a)=a-9";
_sel[_a] = (int) (_a-9);
 }
};
 //BA.debugLineNum = 127;BA.debugLine="For z=1 To 100";
{
final int step14 = 1;
final int limit14 = (int) (100);
_z = (int) (1) ;
for (;_z <= limit14 ;_z = _z + step14 ) {
 //BA.debugLineNum = 128;BA.debugLine="For x=0 To 19";
{
final int step15 = 1;
final int limit15 = (int) (19);
_x = (int) (0) ;
for (;_x <= limit15 ;_x = _x + step15 ) {
 //BA.debugLineNum = 129;BA.debugLine="If loc(x)=0 Then";
if (_loc[_x]==0) { 
 //BA.debugLineNum = 130;BA.debugLine="mix";
_mix();
 };
 }
};
 }
};
 //BA.debugLineNum = 138;BA.debugLine="clicks=0";
_clicks = (int) (0);
 //BA.debugLineNum = 139;BA.debugLine="Timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 140;BA.debugLine="loc1=0";
_loc1 = (int) (0);
 //BA.debugLineNum = 141;BA.debugLine="loc2=0";
_loc2 = (int) (0);
 //BA.debugLineNum = 144;BA.debugLine="button1.visible=True";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 145;BA.debugLine="button2.visible=True";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 146;BA.debugLine="button3.visible=True";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 147;BA.debugLine="button4.visible=True";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 148;BA.debugLine="button5.visible=True";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 149;BA.debugLine="button6.visible=True";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 150;BA.debugLine="button7.visible=True";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 151;BA.debugLine="button8.visible=True";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 152;BA.debugLine="button9.visible=True";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 153;BA.debugLine="button10.visible=True";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 154;BA.debugLine="button11.visible=True";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 155;BA.debugLine="button12.visible=True";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 156;BA.debugLine="button13.visible=True";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 157;BA.debugLine="button14.visible=True";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 158;BA.debugLine="button15.visible=True";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 159;BA.debugLine="button16.visible=True";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 160;BA.debugLine="button17.visible=True";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 161;BA.debugLine="button18.visible=True";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 162;BA.debugLine="button19.visible=True";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 163;BA.debugLine="button20.visible=True";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 168;BA.debugLine="ImageView1.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (0)])+".gif").getObject()));
 //BA.debugLineNum = 169;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (1)])+".gif").getObject()));
 //BA.debugLineNum = 170;BA.debugLine="ImageView3.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (2)])+".gif").getObject()));
 //BA.debugLineNum = 171;BA.debugLine="ImageView4.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (3)])+".gif").getObject()));
 //BA.debugLineNum = 172;BA.debugLine="ImageView5.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (4)])+".gif").getObject()));
 //BA.debugLineNum = 173;BA.debugLine="ImageView6.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (5)])+".gif").getObject()));
 //BA.debugLineNum = 174;BA.debugLine="ImageView7.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (6)])+".gif").getObject()));
 //BA.debugLineNum = 175;BA.debugLine="ImageView8.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview8.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (7)])+".gif").getObject()));
 //BA.debugLineNum = 176;BA.debugLine="ImageView9.Bitmap = LoadBitmap(File.DirAssets, lo";
mostCurrent._imageview9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (8)])+".gif").getObject()));
 //BA.debugLineNum = 177;BA.debugLine="ImageView10.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (9)])+".gif").getObject()));
 //BA.debugLineNum = 178;BA.debugLine="ImageView11.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (10)])+".gif").getObject()));
 //BA.debugLineNum = 179;BA.debugLine="ImageView12.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview12.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (11)])+".gif").getObject()));
 //BA.debugLineNum = 180;BA.debugLine="ImageView13.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview13.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (12)])+".gif").getObject()));
 //BA.debugLineNum = 181;BA.debugLine="ImageView14.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview14.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (13)])+".gif").getObject()));
 //BA.debugLineNum = 182;BA.debugLine="ImageView15.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview15.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (14)])+".gif").getObject()));
 //BA.debugLineNum = 183;BA.debugLine="ImageView16.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview16.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (15)])+".gif").getObject()));
 //BA.debugLineNum = 184;BA.debugLine="ImageView17.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview17.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (16)])+".gif").getObject()));
 //BA.debugLineNum = 185;BA.debugLine="ImageView18.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview18.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (17)])+".gif").getObject()));
 //BA.debugLineNum = 186;BA.debugLine="ImageView19.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview19.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (18)])+".gif").getObject()));
 //BA.debugLineNum = 187;BA.debugLine="ImageView20.Bitmap = LoadBitmap(File.DirAssets, l";
mostCurrent._imageview20.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.NumberToString(_loc[(int) (19)])+".gif").getObject()));
 //BA.debugLineNum = 189;BA.debugLine="ImageView1.SendToBack";
mostCurrent._imageview1.SendToBack();
 //BA.debugLineNum = 190;BA.debugLine="ImageView2.SendToBack";
mostCurrent._imageview2.SendToBack();
 //BA.debugLineNum = 191;BA.debugLine="ImageView3.SendToBack";
mostCurrent._imageview3.SendToBack();
 //BA.debugLineNum = 192;BA.debugLine="ImageView4.SendToBack";
mostCurrent._imageview4.SendToBack();
 //BA.debugLineNum = 193;BA.debugLine="ImageView5.SendToBack";
mostCurrent._imageview5.SendToBack();
 //BA.debugLineNum = 194;BA.debugLine="ImageView6.SendToBack";
mostCurrent._imageview6.SendToBack();
 //BA.debugLineNum = 195;BA.debugLine="ImageView7.SendToBack";
mostCurrent._imageview7.SendToBack();
 //BA.debugLineNum = 196;BA.debugLine="ImageView8.SendToBack";
mostCurrent._imageview8.SendToBack();
 //BA.debugLineNum = 197;BA.debugLine="ImageView9.SendToBack";
mostCurrent._imageview9.SendToBack();
 //BA.debugLineNum = 198;BA.debugLine="ImageView10.SendToBack";
mostCurrent._imageview10.SendToBack();
 //BA.debugLineNum = 199;BA.debugLine="ImageView11.SendToBack";
mostCurrent._imageview11.SendToBack();
 //BA.debugLineNum = 200;BA.debugLine="ImageView12.SendToBack";
mostCurrent._imageview12.SendToBack();
 //BA.debugLineNum = 201;BA.debugLine="ImageView13.SendToBack";
mostCurrent._imageview13.SendToBack();
 //BA.debugLineNum = 202;BA.debugLine="ImageView14.SendToBack";
mostCurrent._imageview14.SendToBack();
 //BA.debugLineNum = 203;BA.debugLine="ImageView15.SendToBack";
mostCurrent._imageview15.SendToBack();
 //BA.debugLineNum = 204;BA.debugLine="ImageView16.SendToBack";
mostCurrent._imageview16.SendToBack();
 //BA.debugLineNum = 205;BA.debugLine="ImageView17.SendToBack";
mostCurrent._imageview17.SendToBack();
 //BA.debugLineNum = 206;BA.debugLine="ImageView18.SendToBack";
mostCurrent._imageview18.SendToBack();
 //BA.debugLineNum = 207;BA.debugLine="ImageView19.SendToBack";
mostCurrent._imageview19.SendToBack();
 //BA.debugLineNum = 208;BA.debugLine="ImageView20.SendToBack";
mostCurrent._imageview20.SendToBack();
 //BA.debugLineNum = 210;BA.debugLine="busy=\"no\"";
mostCurrent._busy = "no";
 //BA.debugLineNum = 212;BA.debugLine="match=0";
_match = (int) (0);
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}
public static String  _timer1_tick() throws Exception{
 //BA.debugLineNum = 496;BA.debugLine="Sub Timer1_Tick";
 //BA.debugLineNum = 499;BA.debugLine="If loc1=1 Or loc2=1 Then";
if (_loc1==1 || _loc2==1) { 
 //BA.debugLineNum = 500;BA.debugLine="button1.visible=True";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 503;BA.debugLine="If loc1=2 Or loc2=2 Then";
if (_loc1==2 || _loc2==2) { 
 //BA.debugLineNum = 504;BA.debugLine="button2.visible=True";
mostCurrent._button2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 507;BA.debugLine="If loc1=3 Or loc2=3 Then";
if (_loc1==3 || _loc2==3) { 
 //BA.debugLineNum = 508;BA.debugLine="button3.visible=True";
mostCurrent._button3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 511;BA.debugLine="If loc1=4 Or loc2=4 Then";
if (_loc1==4 || _loc2==4) { 
 //BA.debugLineNum = 512;BA.debugLine="button4.visible=True";
mostCurrent._button4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 515;BA.debugLine="If loc1=5 Or loc2=5 Then";
if (_loc1==5 || _loc2==5) { 
 //BA.debugLineNum = 516;BA.debugLine="button5.visible=True";
mostCurrent._button5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 519;BA.debugLine="If loc1=6 Or loc2=6 Then";
if (_loc1==6 || _loc2==6) { 
 //BA.debugLineNum = 520;BA.debugLine="button6.visible=True";
mostCurrent._button6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 523;BA.debugLine="If loc1=7 Or loc2=7 Then";
if (_loc1==7 || _loc2==7) { 
 //BA.debugLineNum = 524;BA.debugLine="button7.visible=True";
mostCurrent._button7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 527;BA.debugLine="If loc1=8 Or loc2=8 Then";
if (_loc1==8 || _loc2==8) { 
 //BA.debugLineNum = 528;BA.debugLine="button8.visible=True";
mostCurrent._button8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 531;BA.debugLine="If loc1=9 Or loc2=9 Then";
if (_loc1==9 || _loc2==9) { 
 //BA.debugLineNum = 532;BA.debugLine="button9.visible=True";
mostCurrent._button9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 535;BA.debugLine="If loc1=10 Or loc2=10 Then";
if (_loc1==10 || _loc2==10) { 
 //BA.debugLineNum = 536;BA.debugLine="button10.visible=True";
mostCurrent._button10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 539;BA.debugLine="If loc1=11 Or loc2=11 Then";
if (_loc1==11 || _loc2==11) { 
 //BA.debugLineNum = 540;BA.debugLine="button11.visible=True";
mostCurrent._button11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 544;BA.debugLine="If loc1=12 Or loc2=12 Then";
if (_loc1==12 || _loc2==12) { 
 //BA.debugLineNum = 545;BA.debugLine="button12.visible=True";
mostCurrent._button12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 548;BA.debugLine="If loc1=13 Or loc2=13 Then";
if (_loc1==13 || _loc2==13) { 
 //BA.debugLineNum = 549;BA.debugLine="button13.visible=True";
mostCurrent._button13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 552;BA.debugLine="If loc1=14 Or loc2=14 Then";
if (_loc1==14 || _loc2==14) { 
 //BA.debugLineNum = 553;BA.debugLine="button14.visible=True";
mostCurrent._button14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 556;BA.debugLine="If loc1=15 Or loc2=15 Then";
if (_loc1==15 || _loc2==15) { 
 //BA.debugLineNum = 557;BA.debugLine="button15.visible=True";
mostCurrent._button15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 560;BA.debugLine="If loc1=16 Or loc2=16 Then";
if (_loc1==16 || _loc2==16) { 
 //BA.debugLineNum = 561;BA.debugLine="button16.visible=True";
mostCurrent._button16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 564;BA.debugLine="If loc1=17 Or loc2=17 Then";
if (_loc1==17 || _loc2==17) { 
 //BA.debugLineNum = 565;BA.debugLine="button17.visible=True";
mostCurrent._button17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 568;BA.debugLine="If loc1=18 Or loc2=18 Then";
if (_loc1==18 || _loc2==18) { 
 //BA.debugLineNum = 569;BA.debugLine="button18.visible=True";
mostCurrent._button18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 572;BA.debugLine="If loc1=19 Or loc2=19 Then";
if (_loc1==19 || _loc2==19) { 
 //BA.debugLineNum = 573;BA.debugLine="button19.visible=True";
mostCurrent._button19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 576;BA.debugLine="If loc1=20 Or loc2=20 Then";
if (_loc1==20 || _loc2==20) { 
 //BA.debugLineNum = 577;BA.debugLine="button20.visible=True";
mostCurrent._button20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 580;BA.debugLine="busy=\"no\"";
mostCurrent._busy = "no";
 //BA.debugLineNum = 582;BA.debugLine="Timer1.Enabled=False";
_timer1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 584;BA.debugLine="End Sub";
return "";
}
public static String  _timer2_tick() throws Exception{
 //BA.debugLineNum = 411;BA.debugLine="Sub Timer2_Tick";
 //BA.debugLineNum = 413;BA.debugLine="If one=1  Or two=1 Then";
if (_one==1 || _two==1) { 
 //BA.debugLineNum = 414;BA.debugLine="ImageView1.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"Black.gif").getObject()));
 };
 //BA.debugLineNum = 417;BA.debugLine="If one=2  Or two=2 Then";
if (_one==2 || _two==2) { 
 //BA.debugLineNum = 418;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 421;BA.debugLine="If one=3  Or two=3 Then";
if (_one==3 || _two==3) { 
 //BA.debugLineNum = 422;BA.debugLine="ImageView3.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 425;BA.debugLine="If one=4  Or two=4 Then";
if (_one==4 || _two==4) { 
 //BA.debugLineNum = 426;BA.debugLine="ImageView4.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 429;BA.debugLine="If one=5  Or two=5 Then";
if (_one==5 || _two==5) { 
 //BA.debugLineNum = 430;BA.debugLine="ImageView5.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 433;BA.debugLine="If one=6  Or two=6 Then";
if (_one==6 || _two==6) { 
 //BA.debugLineNum = 434;BA.debugLine="ImageView6.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 437;BA.debugLine="If one=7  Or two=7 Then";
if (_one==7 || _two==7) { 
 //BA.debugLineNum = 438;BA.debugLine="ImageView7.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 441;BA.debugLine="If one=8  Or two=8 Then";
if (_one==8 || _two==8) { 
 //BA.debugLineNum = 442;BA.debugLine="ImageView8.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview8.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 445;BA.debugLine="If one=9  Or two=9 Then";
if (_one==9 || _two==9) { 
 //BA.debugLineNum = 446;BA.debugLine="ImageView9.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 449;BA.debugLine="If one=10 Or two=10 Then";
if (_one==10 || _two==10) { 
 //BA.debugLineNum = 450;BA.debugLine="ImageView10.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 453;BA.debugLine="If one=11  Or two=11 Then";
if (_one==11 || _two==11) { 
 //BA.debugLineNum = 454;BA.debugLine="ImageView11.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 457;BA.debugLine="If one=12  Or two=12 Then";
if (_one==12 || _two==12) { 
 //BA.debugLineNum = 458;BA.debugLine="ImageView12.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview12.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 461;BA.debugLine="If one=13  Or two=13 Then";
if (_one==13 || _two==13) { 
 //BA.debugLineNum = 462;BA.debugLine="ImageView13.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview13.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 465;BA.debugLine="If one=14  Or two=14 Then";
if (_one==14 || _two==14) { 
 //BA.debugLineNum = 466;BA.debugLine="ImageView14.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview14.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 469;BA.debugLine="If one=15  Or two=15 Then";
if (_one==15 || _two==15) { 
 //BA.debugLineNum = 470;BA.debugLine="ImageView15.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview15.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 473;BA.debugLine="If one=16  Or two=16 Then";
if (_one==16 || _two==16) { 
 //BA.debugLineNum = 474;BA.debugLine="ImageView16.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview16.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 477;BA.debugLine="If one=17  Or two=17 Then";
if (_one==17 || _two==17) { 
 //BA.debugLineNum = 478;BA.debugLine="ImageView17.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview17.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 481;BA.debugLine="If one=18  Or two=18 Then";
if (_one==18 || _two==18) { 
 //BA.debugLineNum = 482;BA.debugLine="ImageView18.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview18.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 485;BA.debugLine="If one=19  Or two=19 Then";
if (_one==19 || _two==19) { 
 //BA.debugLineNum = 486;BA.debugLine="ImageView19.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview19.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 489;BA.debugLine="If one=20  Or two=20 Then";
if (_one==20 || _two==20) { 
 //BA.debugLineNum = 490;BA.debugLine="ImageView20.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview20.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"black.gif").getObject()));
 };
 //BA.debugLineNum = 493;BA.debugLine="Timer2.Enabled=False";
_timer2.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 494;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 101;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 };
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
}
