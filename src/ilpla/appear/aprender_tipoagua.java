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

public class aprender_tipoagua extends Activity implements B4AActivity{
	public static aprender_tipoagua mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.aprender_tipoagua");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (aprender_tipoagua).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.aprender_tipoagua");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.aprender_tipoagua", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (aprender_tipoagua) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (aprender_tipoagua) Resume **");
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
		return aprender_tipoagua.class;
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
        BA.LogInfo("** Activity (aprender_tipoagua) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (aprender_tipoagua) Resume **");
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
public dominex.slidingpanels.slidingpanels _sd = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmprincipal _frmprincipal = null;
public ilpla.appear.frmevaluacion _frmevaluacion = null;
public ilpla.appear.utilidades _utilidades = null;
public ilpla.appear.game_ciclo _game_ciclo = null;
public ilpla.appear.game_sourcepoint _game_sourcepoint = null;
public ilpla.appear.game_comunidades _game_comunidades = null;
public ilpla.appear.game_trofica _game_trofica = null;
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
anywheresoftware.b4a.objects.LabelWrapper _titulo1 = null;
anywheresoftware.b4a.objects.LabelWrapper _titulo2 = null;
anywheresoftware.b4a.objects.LabelWrapper _titulo3 = null;
anywheresoftware.b4a.objects.LabelWrapper _titulo4 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img4 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _esquema1 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _esquema2 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _esquema3 = null;
anywheresoftware.b4a.objects.ImageViewWrapper _esquema4 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto1 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto2 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto3 = null;
anywheresoftware.b4a.objects.LabelWrapper _texto4 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel1 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel2 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel3 = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _panel4 = null;
String _line1 = "";
String _line2 = "";
String _line3 = "";
String _line4 = "";
anywheresoftware.b4a.objects.ButtonWrapper _butok = null;
 //BA.debugLineNum = 18;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 20;BA.debugLine="SD.Initialize(\"SD\",300,Activity,Me,False) 'Initia";
mostCurrent._sd._initialize(mostCurrent.activityBA,"SD",(int) (300),(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._activity.getObject())),aprender_tipoagua.getObject(),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 21;BA.debugLine="SD.ModeFullScreen(4,False) 'Creates the mode of S";
mostCurrent._sd._modefullscreen((int) (4),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 22;BA.debugLine="SD.panels(0).Color = Colors.White";
mostCurrent._sd._panels[(int) (0)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 23;BA.debugLine="SD.panels(1).Color = Colors.White";
mostCurrent._sd._panels[(int) (1)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 24;BA.debugLine="SD.panels(2).Color = Colors.White";
mostCurrent._sd._panels[(int) (2)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 25;BA.debugLine="SD.panels(3).Color = Colors.White";
mostCurrent._sd._panels[(int) (3)].setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 26;BA.debugLine="SD.Start(0) 'Start the SlidingPanels.";
mostCurrent._sd._start((int) (0));
 //BA.debugLineNum = 29;BA.debugLine="Dim titulo1 As Label";
_titulo1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim titulo2 As Label";
_titulo2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim titulo3 As Label";
_titulo3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim titulo4 As Label";
_titulo4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="titulo1.Initialize(\"\")";
_titulo1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 36;BA.debugLine="titulo1.TextColor = Colors.Black";
_titulo1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 37;BA.debugLine="titulo1.TextSize = 22";
_titulo1.setTextSize((float) (22));
 //BA.debugLineNum = 38;BA.debugLine="titulo2.Initialize(\"\")";
_titulo2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 39;BA.debugLine="titulo2.TextColor = Colors.Black";
_titulo2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 40;BA.debugLine="titulo2.TextSize = 22";
_titulo2.setTextSize((float) (22));
 //BA.debugLineNum = 41;BA.debugLine="titulo3.Initialize(\"\")";
_titulo3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 42;BA.debugLine="titulo3.TextColor = Colors.Black";
_titulo3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 43;BA.debugLine="titulo3.TextSize = 22";
_titulo3.setTextSize((float) (22));
 //BA.debugLineNum = 44;BA.debugLine="titulo4.Initialize(\"\")";
_titulo4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 45;BA.debugLine="titulo4.TextColor = Colors.Black";
_titulo4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 46;BA.debugLine="titulo4.TextSize = 22";
_titulo4.setTextSize((float) (22));
 //BA.debugLineNum = 48;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 49;BA.debugLine="titulo1.Text = \"Plain rivers\"";
_titulo1.setText((Object)("Plain rivers"));
 //BA.debugLineNum = 50;BA.debugLine="titulo3.Text = \"Lakes\"";
_titulo3.setText((Object)("Lakes"));
 //BA.debugLineNum = 51;BA.debugLine="titulo4.Text = \"Estuaries\"";
_titulo4.setText((Object)("Estuaries"));
 //BA.debugLineNum = 52;BA.debugLine="titulo2.Text = \"Mountain rivers\"";
_titulo2.setText((Object)("Mountain rivers"));
 }else {
 //BA.debugLineNum = 54;BA.debugLine="titulo1.Text = \"Ríos de llanura\"";
_titulo1.setText((Object)("Ríos de llanura"));
 //BA.debugLineNum = 55;BA.debugLine="titulo3.Text = \"Lagos\"";
_titulo3.setText((Object)("Lagos"));
 //BA.debugLineNum = 56;BA.debugLine="titulo4.Text = \"Estuarios\"";
_titulo4.setText((Object)("Estuarios"));
 //BA.debugLineNum = 57;BA.debugLine="titulo2.Text = \"Ríos de montaña\"";
_titulo2.setText((Object)("Ríos de montaña"));
 };
 //BA.debugLineNum = 60;BA.debugLine="Dim img1 As ImageView";
_img1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Dim img2 As ImageView";
_img2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim img3 As ImageView";
_img3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 63;BA.debugLine="Dim img4 As ImageView";
_img4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 65;BA.debugLine="img1.Initialize(\"\")";
_img1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 66;BA.debugLine="img2.Initialize(\"\")";
_img2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 67;BA.debugLine="img3.Initialize(\"\")";
_img3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 68;BA.debugLine="img4.Initialize(\"\")";
_img4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 70;BA.debugLine="img1.Bitmap = LoadBitmapSample(File.DirAssets, \"e";
_img1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"esquemariollanura.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 71;BA.debugLine="img2.Bitmap = LoadBitmapSample(File.DirAssets, \"e";
_img2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"esquemariomontana.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 72;BA.debugLine="img3.Bitmap = LoadBitmapSample(File.DirAssets, \"e";
_img3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"esquemariolaguna.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 73;BA.debugLine="img4.Bitmap = LoadBitmapSample(File.DirAssets, \"e";
_img4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"esquemaestuario.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 75;BA.debugLine="img1.Gravity = Gravity.FILL";
_img1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 76;BA.debugLine="img2.Gravity = Gravity.FILL";
_img2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 77;BA.debugLine="img3.Gravity = Gravity.FILL";
_img3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 78;BA.debugLine="img4.Gravity = Gravity.FILL";
_img4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 81;BA.debugLine="Dim esquema1 As ImageView";
_esquema1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Dim esquema2 As ImageView";
_esquema2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 83;BA.debugLine="Dim esquema3 As ImageView";
_esquema3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Dim esquema4 As ImageView";
_esquema4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 86;BA.debugLine="esquema1.Initialize(\"\")";
_esquema1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 87;BA.debugLine="esquema2.Initialize(\"\")";
_esquema2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 88;BA.debugLine="esquema3.Initialize(\"\")";
_esquema3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 89;BA.debugLine="esquema4.Initialize(\"\")";
_esquema4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 91;BA.debugLine="esquema1.Bitmap = LoadBitmapSample(File.DirAssets";
_esquema1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"arroyo3.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 92;BA.debugLine="esquema2.Bitmap = LoadBitmapSample(File.DirAssets";
_esquema2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"rio6bej.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 93;BA.debugLine="esquema3.Bitmap = LoadBitmapSample(File.DirAssets";
_esquema3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"lago.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 94;BA.debugLine="esquema4.Bitmap = LoadBitmapSample(File.DirAssets";
_esquema4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"estuario1.jpg",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 96;BA.debugLine="esquema1.Gravity = Gravity.FILL";
_esquema1.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 97;BA.debugLine="esquema2.Gravity = Gravity.FILL";
_esquema2.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 98;BA.debugLine="esquema3.Gravity = Gravity.FILL";
_esquema3.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 99;BA.debugLine="esquema4.Gravity = Gravity.FILL";
_esquema4.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 102;BA.debugLine="Dim texto1 As Label";
_texto1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 103;BA.debugLine="Dim texto2 As Label";
_texto2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 104;BA.debugLine="Dim texto3 As Label";
_texto3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 105;BA.debugLine="Dim texto4 As Label";
_texto4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 107;BA.debugLine="texto1.Initialize(\"\")";
_texto1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 108;BA.debugLine="texto2.Initialize(\"\")";
_texto2.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 109;BA.debugLine="texto3.Initialize(\"\")";
_texto3.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 110;BA.debugLine="texto4.Initialize(\"\")";
_texto4.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 111;BA.debugLine="texto1.TextColor = Colors.Black";
_texto1.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 112;BA.debugLine="texto2.TextColor = Colors.Black";
_texto2.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 113;BA.debugLine="texto3.TextColor = Colors.Black";
_texto3.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 114;BA.debugLine="texto4.TextColor = Colors.Black";
_texto4.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 116;BA.debugLine="Dim panel1 As ScrollView";
_panel1 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 117;BA.debugLine="Dim panel2 As ScrollView";
_panel2 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 118;BA.debugLine="Dim panel3 As ScrollView";
_panel3 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 119;BA.debugLine="Dim panel4 As ScrollView";
_panel4 = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 121;BA.debugLine="panel1.Initialize(500dip)";
_panel1.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 122;BA.debugLine="panel2.Initialize(500dip)";
_panel2.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 123;BA.debugLine="panel3.Initialize(500dip)";
_panel3.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 124;BA.debugLine="panel4.Initialize(500dip)";
_panel4.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (500)));
 //BA.debugLineNum = 126;BA.debugLine="Dim line1 As String";
_line1 = "";
 //BA.debugLineNum = 127;BA.debugLine="line1 = File.ReadString(File.DirAssets, Main.l";
_line1 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-riollanura.txt");
 //BA.debugLineNum = 128;BA.debugLine="Dim line2 As String";
_line2 = "";
 //BA.debugLineNum = 129;BA.debugLine="line2 = File.ReadString(File.DirAssets, Main.l";
_line2 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-riomontana.txt");
 //BA.debugLineNum = 130;BA.debugLine="Dim line3 As String";
_line3 = "";
 //BA.debugLineNum = 131;BA.debugLine="line3 = File.ReadString(File.DirAssets, Main.l";
_line3 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-riolaguna.txt");
 //BA.debugLineNum = 132;BA.debugLine="Dim line4 As String";
_line4 = "";
 //BA.debugLineNum = 133;BA.debugLine="line4 = File.ReadString(File.DirAssets, Main.l";
_line4 = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-rioestuario.txt");
 //BA.debugLineNum = 135;BA.debugLine="texto1.Text = line1";
_texto1.setText((Object)(_line1));
 //BA.debugLineNum = 136;BA.debugLine="texto2.Text = line2";
_texto2.setText((Object)(_line2));
 //BA.debugLineNum = 137;BA.debugLine="texto3.Text = line3";
_texto3.setText((Object)(_line3));
 //BA.debugLineNum = 138;BA.debugLine="texto4.Text = line4";
_texto4.setText((Object)(_line4));
 //BA.debugLineNum = 143;BA.debugLine="SD.panels(0).AddView(titulo1,0,0,100%x,5%y)";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_titulo1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 144;BA.debugLine="SD.panels(1).AddView(titulo2,0,0,100%x,5%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_titulo2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 145;BA.debugLine="SD.panels(2).AddView(titulo3,0,0,100%x,5%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_titulo3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 146;BA.debugLine="SD.panels(3).AddView(titulo4,0,0,100%x,5%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_titulo4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA));
 //BA.debugLineNum = 148;BA.debugLine="SD.panels(0).AddView(img1,0,5%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_img1.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 149;BA.debugLine="SD.panels(1).AddView(img2,0,5%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_img2.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 150;BA.debugLine="SD.panels(2).AddView(img3,0,5%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_img3.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 151;BA.debugLine="SD.panels(3).AddView(img4,0,5%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_img4.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 153;BA.debugLine="SD.panels(0).AddView(esquema1,0,35%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_esquema1.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 154;BA.debugLine="SD.panels(1).AddView(esquema2,0,35%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_esquema2.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 155;BA.debugLine="SD.panels(2).AddView(esquema3,0,35%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_esquema3.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 156;BA.debugLine="SD.panels(3).AddView(esquema4,0,35%y,100%x,30%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_esquema4.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (35),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (30),mostCurrent.activityBA));
 //BA.debugLineNum = 158;BA.debugLine="SD.panels(0).AddView(panel1,5%x,68%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (0)].AddView((android.view.View)(_panel1.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (68),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 159;BA.debugLine="SD.panels(1).AddView(panel2,5%x,68%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (1)].AddView((android.view.View)(_panel2.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (68),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 160;BA.debugLine="SD.panels(2).AddView(panel3,5%x,68%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (2)].AddView((android.view.View)(_panel3.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (68),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 161;BA.debugLine="SD.panels(3).AddView(panel4,5%x,68%y,90%x,50%y)";
mostCurrent._sd._panels[(int) (3)].AddView((android.view.View)(_panel4.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (68),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 163;BA.debugLine="panel1.Panel.AddView(texto1,0,0,90%x,50%y)";
_panel1.getPanel().AddView((android.view.View)(_texto1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 164;BA.debugLine="panel2.Panel.AddView(texto2,0,0,90%x,50%y)";
_panel2.getPanel().AddView((android.view.View)(_texto2.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 165;BA.debugLine="panel3.Panel.AddView(texto3,0,0,90%x,50%y)";
_panel3.getPanel().AddView((android.view.View)(_texto3.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 166;BA.debugLine="panel4.Panel.AddView(texto4,0,0,90%x,50%y)";
_panel4.getPanel().AddView((android.view.View)(_texto4.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 171;BA.debugLine="Dim butOK As Button";
_butok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 172;BA.debugLine="butOK.Initialize(\"butOK\")";
_butok.Initialize(mostCurrent.activityBA,"butOK");
 //BA.debugLineNum = 173;BA.debugLine="butOK.Text = \"OK\"";
_butok.setText((Object)("OK"));
 //BA.debugLineNum = 174;BA.debugLine="butOK.Color = Colors.ARGB(255,73,202,138)";
_butok.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (73),(int) (202),(int) (138)));
 //BA.debugLineNum = 175;BA.debugLine="butOK.TextColor = Colors.White";
_butok.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 177;BA.debugLine="Activity.AddView(butOK, 0,90%y,100%x,10%y)";
mostCurrent._activity.AddView((android.view.View)(_butok.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 185;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public static String  _butok_click() throws Exception{
 //BA.debugLineNum = 189;BA.debugLine="Sub butOK_Click";
 //BA.debugLineNum = 190;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 191;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 193;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim SD As SlidingPanels";
mostCurrent._sd = new dominex.slidingpanels.slidingpanels();
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
