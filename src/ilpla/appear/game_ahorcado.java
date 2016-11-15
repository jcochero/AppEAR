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

public class game_ahorcado extends Activity implements B4AActivity{
	public static game_ahorcado mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.game_ahorcado");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (game_ahorcado).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.game_ahorcado");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.game_ahorcado", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (game_ahorcado) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (game_ahorcado) Resume **");
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
		return game_ahorcado.class;
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
        BA.LogInfo("** Activity (game_ahorcado) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (game_ahorcado) Resume **");
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
public static int _random = 0;
public static anywheresoftware.b4a.objects.collections.List _words = null;
public static anywheresoftware.b4a.objects.collections.List _definiciones = null;
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
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview21 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview22 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview23 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview24 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview25 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview26 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview27 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public static String _l1 = "";
public static String _l2 = "";
public static String _l3 = "";
public static String _l4 = "";
public static String _l5 = "";
public static String _l6 = "";
public static String _l7 = "";
public static String _l8 = "";
public static String _l9 = "";
public static String _l10 = "";
public static String _p1 = "";
public static String _p2 = "";
public static String _p3 = "";
public static String _p4 = "";
public static String _p5 = "";
public static String _p6 = "";
public static String _p7 = "";
public static String _p8 = "";
public static String _p9 = "";
public static String _p10 = "";
public static String[] _ltr = null;
public static int _z = 0;
public static int _cycle = 0;
public static String _wordxx = "";
public static String _definicionxx = "";
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldefinicion = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndefinicion = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public ilpla.appear.main _main = null;
public ilpla.appear.frmprincipal _frmprincipal = null;
public ilpla.appear.frmevaluacion _frmevaluacion = null;
public ilpla.appear.utilidades _utilidades = null;
public ilpla.appear.game_ciclo _game_ciclo = null;
public ilpla.appear.game_sourcepoint _game_sourcepoint = null;
public ilpla.appear.game_comunidades _game_comunidades = null;
public ilpla.appear.game_trofica _game_trofica = null;
public ilpla.appear.aprender_tipoagua _aprender_tipoagua = null;
public ilpla.appear.frmaprender _frmaprender = null;
public ilpla.appear.frmresultados _frmresultados = null;
public ilpla.appear.frmhabitatestuario _frmhabitatestuario = null;
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
 //BA.debugLineNum = 85;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 86;BA.debugLine="Activity.LoadLayout(\"AhorcadoGame.bal\")";
mostCurrent._activity.LoadLayout("AhorcadoGame.bal",mostCurrent.activityBA);
 //BA.debugLineNum = 87;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 88;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 89;BA.debugLine="words = File.ReadList(File.DirAssets, \"words.txt";
_words = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"words.txt");
 //BA.debugLineNum = 90;BA.debugLine="definiciones = File.ReadList(File.DirAssets, \"de";
_definiciones = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"definiciones.txt");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 92;BA.debugLine="words = File.ReadList(File.DirAssets, \"en-words.";
_words = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-words.txt");
 //BA.debugLineNum = 93;BA.debugLine="definiciones = File.ReadList(File.DirAssets, \"en";
_definiciones = anywheresoftware.b4a.keywords.Common.File.ReadList(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-definiciones.txt");
 };
 //BA.debugLineNum = 96;BA.debugLine="ImageView1.Bitmap = LoadBitmap(File.DirAssets, \"a";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"a.png").getObject()));
 //BA.debugLineNum = 97;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"b";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"b.png").getObject()));
 //BA.debugLineNum = 98;BA.debugLine="ImageView3.Bitmap = LoadBitmap(File.DirAssets, \"c";
mostCurrent._imageview3.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"c.png").getObject()));
 //BA.debugLineNum = 99;BA.debugLine="ImageView4.Bitmap = LoadBitmap(File.DirAssets, \"d";
mostCurrent._imageview4.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"d.png").getObject()));
 //BA.debugLineNum = 100;BA.debugLine="ImageView5.Bitmap = LoadBitmap(File.DirAssets, \"e";
mostCurrent._imageview5.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"e.png").getObject()));
 //BA.debugLineNum = 101;BA.debugLine="ImageView6.Bitmap = LoadBitmap(File.DirAssets, \"f";
mostCurrent._imageview6.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"f.png").getObject()));
 //BA.debugLineNum = 102;BA.debugLine="ImageView7.Bitmap = LoadBitmap(File.DirAssets, \"g";
mostCurrent._imageview7.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"g.png").getObject()));
 //BA.debugLineNum = 103;BA.debugLine="ImageView8.Bitmap = LoadBitmap(File.DirAssets, \"h";
mostCurrent._imageview8.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"h.png").getObject()));
 //BA.debugLineNum = 104;BA.debugLine="ImageView9.Bitmap = LoadBitmap(File.DirAssets, \"i";
mostCurrent._imageview9.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"i.png").getObject()));
 //BA.debugLineNum = 105;BA.debugLine="ImageView10.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview10.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"j.png").getObject()));
 //BA.debugLineNum = 106;BA.debugLine="ImageView11.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview11.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"k.png").getObject()));
 //BA.debugLineNum = 107;BA.debugLine="ImageView12.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview12.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"l.png").getObject()));
 //BA.debugLineNum = 108;BA.debugLine="ImageView13.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview13.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"m.png").getObject()));
 //BA.debugLineNum = 109;BA.debugLine="ImageView14.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview14.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"n.png").getObject()));
 //BA.debugLineNum = 110;BA.debugLine="ImageView15.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview15.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"o.png").getObject()));
 //BA.debugLineNum = 111;BA.debugLine="ImageView16.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview16.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"p.png").getObject()));
 //BA.debugLineNum = 112;BA.debugLine="ImageView17.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview17.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"q.png").getObject()));
 //BA.debugLineNum = 113;BA.debugLine="ImageView18.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview18.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"r.png").getObject()));
 //BA.debugLineNum = 114;BA.debugLine="ImageView19.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview19.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"s.png").getObject()));
 //BA.debugLineNum = 115;BA.debugLine="ImageView20.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview20.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"t.png").getObject()));
 //BA.debugLineNum = 116;BA.debugLine="ImageView21.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview21.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"u.png").getObject()));
 //BA.debugLineNum = 117;BA.debugLine="ImageView22.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview22.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"v.png").getObject()));
 //BA.debugLineNum = 118;BA.debugLine="ImageView23.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview23.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"w.png").getObject()));
 //BA.debugLineNum = 119;BA.debugLine="ImageView24.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview24.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"x.png").getObject()));
 //BA.debugLineNum = 120;BA.debugLine="ImageView25.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview25.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"y.png").getObject()));
 //BA.debugLineNum = 121;BA.debugLine="ImageView26.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview26.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"z.png").getObject()));
 //BA.debugLineNum = 122;BA.debugLine="ImageView27.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview27.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hang0.jpg").getObject()));
 //BA.debugLineNum = 124;BA.debugLine="ltr(0)=\"A\"";
mostCurrent._ltr[(int) (0)] = "A";
 //BA.debugLineNum = 125;BA.debugLine="ltr(1)=\"B\"";
mostCurrent._ltr[(int) (1)] = "B";
 //BA.debugLineNum = 126;BA.debugLine="ltr(2)=\"C\"";
mostCurrent._ltr[(int) (2)] = "C";
 //BA.debugLineNum = 127;BA.debugLine="ltr(3)=\"D\"";
mostCurrent._ltr[(int) (3)] = "D";
 //BA.debugLineNum = 128;BA.debugLine="ltr(4)=\"E\"";
mostCurrent._ltr[(int) (4)] = "E";
 //BA.debugLineNum = 129;BA.debugLine="ltr(5)=\"F\"";
mostCurrent._ltr[(int) (5)] = "F";
 //BA.debugLineNum = 130;BA.debugLine="ltr(6)=\"G\"";
mostCurrent._ltr[(int) (6)] = "G";
 //BA.debugLineNum = 131;BA.debugLine="ltr(7)=\"H\"";
mostCurrent._ltr[(int) (7)] = "H";
 //BA.debugLineNum = 132;BA.debugLine="ltr(8)=\"I\"";
mostCurrent._ltr[(int) (8)] = "I";
 //BA.debugLineNum = 133;BA.debugLine="ltr(9)=\"J\"";
mostCurrent._ltr[(int) (9)] = "J";
 //BA.debugLineNum = 134;BA.debugLine="ltr(10)=\"K\"";
mostCurrent._ltr[(int) (10)] = "K";
 //BA.debugLineNum = 135;BA.debugLine="ltr(11)=\"L\"";
mostCurrent._ltr[(int) (11)] = "L";
 //BA.debugLineNum = 136;BA.debugLine="ltr(12)=\"M\"";
mostCurrent._ltr[(int) (12)] = "M";
 //BA.debugLineNum = 137;BA.debugLine="ltr(13)=\"N\"";
mostCurrent._ltr[(int) (13)] = "N";
 //BA.debugLineNum = 138;BA.debugLine="ltr(14)=\"O\"";
mostCurrent._ltr[(int) (14)] = "O";
 //BA.debugLineNum = 139;BA.debugLine="ltr(15)=\"P\"";
mostCurrent._ltr[(int) (15)] = "P";
 //BA.debugLineNum = 140;BA.debugLine="ltr(16)=\"Q\"";
mostCurrent._ltr[(int) (16)] = "Q";
 //BA.debugLineNum = 141;BA.debugLine="ltr(17)=\"R\"";
mostCurrent._ltr[(int) (17)] = "R";
 //BA.debugLineNum = 142;BA.debugLine="ltr(18)=\"S\"";
mostCurrent._ltr[(int) (18)] = "S";
 //BA.debugLineNum = 143;BA.debugLine="ltr(19)=\"T\"";
mostCurrent._ltr[(int) (19)] = "T";
 //BA.debugLineNum = 144;BA.debugLine="ltr(20)=\"U\"";
mostCurrent._ltr[(int) (20)] = "U";
 //BA.debugLineNum = 145;BA.debugLine="ltr(21)=\"V\"";
mostCurrent._ltr[(int) (21)] = "V";
 //BA.debugLineNum = 146;BA.debugLine="ltr(22)=\"W\"";
mostCurrent._ltr[(int) (22)] = "W";
 //BA.debugLineNum = 147;BA.debugLine="ltr(23)=\"X\"";
mostCurrent._ltr[(int) (23)] = "X";
 //BA.debugLineNum = 148;BA.debugLine="ltr(24)=\"Y\"";
mostCurrent._ltr[(int) (24)] = "Y";
 //BA.debugLineNum = 149;BA.debugLine="ltr(25)=\"Z\"";
mostCurrent._ltr[(int) (25)] = "Z";
 //BA.debugLineNum = 151;BA.debugLine="Button1_Click";
_button1_click();
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 327;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 329;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 323;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 325;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarinfo_click() throws Exception{
 //BA.debugLineNum = 644;BA.debugLine="Sub btnCerrarInfo_Click";
 //BA.debugLineNum = 645;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews -1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 646;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews -1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 647;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews -1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 648;BA.debugLine="End Sub";
return "";
}
public static String  _btndefinicion_click() throws Exception{
 //BA.debugLineNum = 650;BA.debugLine="Sub btnDefinicion_Click";
 //BA.debugLineNum = 651;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 652;BA.debugLine="Msgbox(definicionxx, \"Definición\")";
anywheresoftware.b4a.keywords.Common.Msgbox(mostCurrent._definicionxx,"Definición",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 654;BA.debugLine="Msgbox(definicionxx, \"Definition\")";
anywheresoftware.b4a.keywords.Common.Msgbox(mostCurrent._definicionxx,"Definition",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 657;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
int _rndsize = 0;
String _wordx = "";
String _definicionx = "";
int _d = 0;
 //BA.debugLineNum = 331;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 332;BA.debugLine="Dim rndSize As Int";
_rndsize = 0;
 //BA.debugLineNum = 333;BA.debugLine="rndSize = words.Size";
_rndsize = _words.getSize();
 //BA.debugLineNum = 334;BA.debugLine="random = Rnd(1,rndSize)";
_random = anywheresoftware.b4a.keywords.Common.Rnd((int) (1),_rndsize);
 //BA.debugLineNum = 335;BA.debugLine="Dim wordx As String";
_wordx = "";
 //BA.debugLineNum = 336;BA.debugLine="Dim definicionx As String";
_definicionx = "";
 //BA.debugLineNum = 337;BA.debugLine="wordx = words.Get(random)";
_wordx = BA.ObjectToString(_words.Get(_random));
 //BA.debugLineNum = 338;BA.debugLine="definicionx = definiciones.Get(random)";
_definicionx = BA.ObjectToString(_definiciones.Get(_random));
 //BA.debugLineNum = 339;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 340;BA.debugLine="lblDefinicion.Text = \"Definition: \" & definicion";
mostCurrent._lbldefinicion.setText((Object)("Definition: "+_definicionx));
 }else if((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 342;BA.debugLine="lblDefinicion.Text = \"Definición: \" & definicion";
mostCurrent._lbldefinicion.setText((Object)("Definición: "+_definicionx));
 };
 //BA.debugLineNum = 346;BA.debugLine="Dim d As Int";
_d = 0;
 //BA.debugLineNum = 347;BA.debugLine="d=wordx.Length";
_d = _wordx.length();
 //BA.debugLineNum = 348;BA.debugLine="ImageView27.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview27.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hang0.png").getObject()));
 //BA.debugLineNum = 350;BA.debugLine="P1=\"_\"";
mostCurrent._p1 = "_";
 //BA.debugLineNum = 351;BA.debugLine="P2=\"_\"";
mostCurrent._p2 = "_";
 //BA.debugLineNum = 352;BA.debugLine="P3=\"_\"";
mostCurrent._p3 = "_";
 //BA.debugLineNum = 353;BA.debugLine="P4=\"_\"";
mostCurrent._p4 = "_";
 //BA.debugLineNum = 354;BA.debugLine="P5=\"_\"";
mostCurrent._p5 = "_";
 //BA.debugLineNum = 356;BA.debugLine="P6=\"\"";
mostCurrent._p6 = "";
 //BA.debugLineNum = 357;BA.debugLine="P7=\"\"";
mostCurrent._p7 = "";
 //BA.debugLineNum = 358;BA.debugLine="P8=\"\"";
mostCurrent._p8 = "";
 //BA.debugLineNum = 359;BA.debugLine="P9=\"\"";
mostCurrent._p9 = "";
 //BA.debugLineNum = 360;BA.debugLine="P10=\"\"";
mostCurrent._p10 = "";
 //BA.debugLineNum = 362;BA.debugLine="wordxx=wordx.ToUpperCase";
mostCurrent._wordxx = _wordx.toUpperCase();
 //BA.debugLineNum = 363;BA.debugLine="definicionxx = definicionx";
mostCurrent._definicionxx = _definicionx;
 //BA.debugLineNum = 365;BA.debugLine="L1=wordxx.SubString2(0,1)";
mostCurrent._l1 = mostCurrent._wordxx.substring((int) (0),(int) (1));
 //BA.debugLineNum = 366;BA.debugLine="L2=wordxx.SubString2(1,2)";
mostCurrent._l2 = mostCurrent._wordxx.substring((int) (1),(int) (2));
 //BA.debugLineNum = 367;BA.debugLine="L3=wordxx.SubString2(2,3)";
mostCurrent._l3 = mostCurrent._wordxx.substring((int) (2),(int) (3));
 //BA.debugLineNum = 368;BA.debugLine="L4=wordxx.SubString2(3,4)";
mostCurrent._l4 = mostCurrent._wordxx.substring((int) (3),(int) (4));
 //BA.debugLineNum = 369;BA.debugLine="L5=wordxx.SubString2(4,5)";
mostCurrent._l5 = mostCurrent._wordxx.substring((int) (4),(int) (5));
 //BA.debugLineNum = 371;BA.debugLine="If d=6 Then";
if (_d==6) { 
 //BA.debugLineNum = 372;BA.debugLine="L6=wordxx.SubString2(5,6)";
mostCurrent._l6 = mostCurrent._wordxx.substring((int) (5),(int) (6));
 //BA.debugLineNum = 373;BA.debugLine="P6=\"_\"";
mostCurrent._p6 = "_";
 };
 //BA.debugLineNum = 376;BA.debugLine="If d=7 Then";
if (_d==7) { 
 //BA.debugLineNum = 377;BA.debugLine="L6=wordxx.SubString2(5,6)";
mostCurrent._l6 = mostCurrent._wordxx.substring((int) (5),(int) (6));
 //BA.debugLineNum = 378;BA.debugLine="L7=wordxx.SubString2(6,7)";
mostCurrent._l7 = mostCurrent._wordxx.substring((int) (6),(int) (7));
 //BA.debugLineNum = 379;BA.debugLine="P6=\"_\"";
mostCurrent._p6 = "_";
 //BA.debugLineNum = 380;BA.debugLine="P7=\"_\"";
mostCurrent._p7 = "_";
 };
 //BA.debugLineNum = 383;BA.debugLine="If d=8 Then";
if (_d==8) { 
 //BA.debugLineNum = 384;BA.debugLine="L6=wordxx.SubString2(5,6)";
mostCurrent._l6 = mostCurrent._wordxx.substring((int) (5),(int) (6));
 //BA.debugLineNum = 385;BA.debugLine="L7=wordxx.SubString2(6,7)";
mostCurrent._l7 = mostCurrent._wordxx.substring((int) (6),(int) (7));
 //BA.debugLineNum = 386;BA.debugLine="L8=wordxx.SubString2(7,8)";
mostCurrent._l8 = mostCurrent._wordxx.substring((int) (7),(int) (8));
 //BA.debugLineNum = 387;BA.debugLine="P6=\"_\"";
mostCurrent._p6 = "_";
 //BA.debugLineNum = 388;BA.debugLine="P7=\"_\"";
mostCurrent._p7 = "_";
 //BA.debugLineNum = 389;BA.debugLine="P8=\"_\"";
mostCurrent._p8 = "_";
 };
 //BA.debugLineNum = 392;BA.debugLine="If d=9 Then";
if (_d==9) { 
 //BA.debugLineNum = 393;BA.debugLine="L6=wordxx.SubString2(5,6)";
mostCurrent._l6 = mostCurrent._wordxx.substring((int) (5),(int) (6));
 //BA.debugLineNum = 394;BA.debugLine="L7=wordxx.SubString2(6,7)";
mostCurrent._l7 = mostCurrent._wordxx.substring((int) (6),(int) (7));
 //BA.debugLineNum = 395;BA.debugLine="L8=wordxx.SubString2(7,8)";
mostCurrent._l8 = mostCurrent._wordxx.substring((int) (7),(int) (8));
 //BA.debugLineNum = 396;BA.debugLine="L9=wordxx.SubString2(8,9)";
mostCurrent._l9 = mostCurrent._wordxx.substring((int) (8),(int) (9));
 //BA.debugLineNum = 397;BA.debugLine="P6=\"_\"";
mostCurrent._p6 = "_";
 //BA.debugLineNum = 398;BA.debugLine="P7=\"_\"";
mostCurrent._p7 = "_";
 //BA.debugLineNum = 399;BA.debugLine="P8=\"_\"";
mostCurrent._p8 = "_";
 //BA.debugLineNum = 400;BA.debugLine="P9=\"_\"";
mostCurrent._p9 = "_";
 };
 //BA.debugLineNum = 403;BA.debugLine="If d=10 Then";
if (_d==10) { 
 //BA.debugLineNum = 404;BA.debugLine="L6=wordxx.SubString2(5,6)";
mostCurrent._l6 = mostCurrent._wordxx.substring((int) (5),(int) (6));
 //BA.debugLineNum = 405;BA.debugLine="L7=wordxx.SubString2(6,7)";
mostCurrent._l7 = mostCurrent._wordxx.substring((int) (6),(int) (7));
 //BA.debugLineNum = 406;BA.debugLine="L8=wordxx.SubString2(7,8)";
mostCurrent._l8 = mostCurrent._wordxx.substring((int) (7),(int) (8));
 //BA.debugLineNum = 407;BA.debugLine="L9=wordxx.SubString2(8,9)";
mostCurrent._l9 = mostCurrent._wordxx.substring((int) (8),(int) (9));
 //BA.debugLineNum = 408;BA.debugLine="L10=wordxx.SubString2(9,10)";
mostCurrent._l10 = mostCurrent._wordxx.substring((int) (9),(int) (10));
 //BA.debugLineNum = 409;BA.debugLine="P6=\"_\"";
mostCurrent._p6 = "_";
 //BA.debugLineNum = 410;BA.debugLine="P7=\"_\"";
mostCurrent._p7 = "_";
 //BA.debugLineNum = 411;BA.debugLine="P8=\"_\"";
mostCurrent._p8 = "_";
 //BA.debugLineNum = 412;BA.debugLine="P9=\"_\"";
mostCurrent._p9 = "_";
 //BA.debugLineNum = 413;BA.debugLine="P10=\"_\"";
mostCurrent._p10 = "_";
 };
 //BA.debugLineNum = 417;BA.debugLine="label2.Text=P1 & \" \" & P2 & \" \" & P3 & \" \" & P4 &";
mostCurrent._label2.setText((Object)(mostCurrent._p1+" "+mostCurrent._p2+" "+mostCurrent._p3+" "+mostCurrent._p4+" "+mostCurrent._p5+" "+mostCurrent._p6+" "+mostCurrent._p7+" "+mostCurrent._p8+" "+mostCurrent._p9+" "+mostCurrent._p10));
 //BA.debugLineNum = 418;BA.debugLine="resetx";
_resetx();
 //BA.debugLineNum = 420;BA.debugLine="cycle=1";
_cycle = (int) (1);
 //BA.debugLineNum = 422;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
 //BA.debugLineNum = 424;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 425;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 426;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 594;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 595;BA.debugLine="LearnInfo";
_learninfo();
 //BA.debugLineNum = 596;BA.debugLine="End Sub";
return "";
}
public static String  _calcx() throws Exception{
int _right = 0;
 //BA.debugLineNum = 459;BA.debugLine="Sub calcx";
 //BA.debugLineNum = 461;BA.debugLine="Dim right As Int";
_right = 0;
 //BA.debugLineNum = 462;BA.debugLine="right=0";
_right = (int) (0);
 //BA.debugLineNum = 464;BA.debugLine="If ltr(z)=L1 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l1)) { 
 //BA.debugLineNum = 465;BA.debugLine="P1=L1";
mostCurrent._p1 = mostCurrent._l1;
 //BA.debugLineNum = 466;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 469;BA.debugLine="If ltr(z)=L2 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l2)) { 
 //BA.debugLineNum = 470;BA.debugLine="P2=L2";
mostCurrent._p2 = mostCurrent._l2;
 //BA.debugLineNum = 471;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 474;BA.debugLine="If ltr(z)=L3 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l3)) { 
 //BA.debugLineNum = 475;BA.debugLine="P3=L3";
mostCurrent._p3 = mostCurrent._l3;
 //BA.debugLineNum = 476;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 479;BA.debugLine="If ltr(z)=L4 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l4)) { 
 //BA.debugLineNum = 480;BA.debugLine="P4=L4";
mostCurrent._p4 = mostCurrent._l4;
 //BA.debugLineNum = 481;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 484;BA.debugLine="If ltr(z)=L5 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l5)) { 
 //BA.debugLineNum = 485;BA.debugLine="P5=L5";
mostCurrent._p5 = mostCurrent._l5;
 //BA.debugLineNum = 486;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 490;BA.debugLine="If ltr(z)=L5 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l5)) { 
 //BA.debugLineNum = 491;BA.debugLine="P5=L5";
mostCurrent._p5 = mostCurrent._l5;
 //BA.debugLineNum = 492;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 495;BA.debugLine="If ltr(z)=L6 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l6)) { 
 //BA.debugLineNum = 496;BA.debugLine="P6=L6";
mostCurrent._p6 = mostCurrent._l6;
 //BA.debugLineNum = 497;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 500;BA.debugLine="If ltr(z)=L7 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l7)) { 
 //BA.debugLineNum = 501;BA.debugLine="P7=L7";
mostCurrent._p7 = mostCurrent._l7;
 //BA.debugLineNum = 502;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 505;BA.debugLine="If ltr(z)=L8 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l8)) { 
 //BA.debugLineNum = 506;BA.debugLine="P8=L8";
mostCurrent._p8 = mostCurrent._l8;
 //BA.debugLineNum = 507;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 510;BA.debugLine="If ltr(z)=L9 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l9)) { 
 //BA.debugLineNum = 511;BA.debugLine="P9=L9";
mostCurrent._p9 = mostCurrent._l9;
 //BA.debugLineNum = 512;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 515;BA.debugLine="If ltr(z)=L10 Then";
if ((mostCurrent._ltr[_z]).equals(mostCurrent._l10)) { 
 //BA.debugLineNum = 516;BA.debugLine="P10=L10";
mostCurrent._p10 = mostCurrent._l10;
 //BA.debugLineNum = 517;BA.debugLine="right=right+1";
_right = (int) (_right+1);
 };
 //BA.debugLineNum = 520;BA.debugLine="If right=0 Then";
if (_right==0) { 
 //BA.debugLineNum = 521;BA.debugLine="cycle=cycle+1";
_cycle = (int) (_cycle+1);
 };
 //BA.debugLineNum = 524;BA.debugLine="If cycle=2 Then";
if (_cycle==2) { 
 //BA.debugLineNum = 525;BA.debugLine="ImageView27.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview27.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hang1.png").getObject()));
 //BA.debugLineNum = 526;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 527;BA.debugLine="ToastMessageShow(\"Eclosionaron los huevos del m";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Eclosionaron los huevos del mosquito! Ahora son una larva acuática del 1er estadío",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 529;BA.debugLine="ToastMessageShow(\"The mosquito eggs hatched, an";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("The mosquito eggs hatched, and it's now a 1st stage aquatic larvae",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 534;BA.debugLine="If cycle=3 Then";
if (_cycle==3) { 
 //BA.debugLineNum = 535;BA.debugLine="ImageView27.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview27.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hang2.png").getObject()));
 //BA.debugLineNum = 536;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 537;BA.debugLine="ToastMessageShow(\"La larva del 1er estadío mudó";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("La larva del 1er estadío mudó a una larva acuática más grande",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 539;BA.debugLine="ToastMessageShow(\"The larvae molted into a larg";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("The larvae molted into a larger aquatic larvae (2nd stage larvae)",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 543;BA.debugLine="If cycle=4 Then";
if (_cycle==4) { 
 //BA.debugLineNum = 544;BA.debugLine="ImageView27.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview27.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hang3.png").getObject()));
 //BA.debugLineNum = 545;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 546;BA.debugLine="ToastMessageShow(\"La larva del 2do estadío mudó";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("La larva del 2do estadío mudó a una larva acuática más grande",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 548;BA.debugLine="ToastMessageShow(\"The larvae molted into a larg";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("The larvae molted into a larger aquatic larvae (3rd stage larvae)",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 552;BA.debugLine="If cycle=5 Then";
if (_cycle==5) { 
 //BA.debugLineNum = 553;BA.debugLine="ImageView27.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview27.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hang4.png").getObject()));
 //BA.debugLineNum = 554;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 555;BA.debugLine="ToastMessageShow(\"La larva mudó a una pupa acuá";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("La larva mudó a una pupa acuática",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 557;BA.debugLine="ToastMessageShow(\"The larvae molted into an aqu";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("The larvae molted into an aquatic Pupa",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 561;BA.debugLine="If cycle=6 Then";
if (_cycle==6) { 
 //BA.debugLineNum = 562;BA.debugLine="ImageView27.Bitmap = LoadBitmap(File.DirAssets,";
mostCurrent._imageview27.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"hang5.png").getObject()));
 //BA.debugLineNum = 563;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 564;BA.debugLine="ToastMessageShow(\"De la pupa emergió un mosquit";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("De la pupa emergió un mosquito adulto! Cuidado!",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 566;BA.debugLine="ToastMessageShow(\"From the pupa, an adult mosqu";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("From the pupa, an adult mosquito emerged! Be careful!",anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 570;BA.debugLine="If cycle=7 Then";
if (_cycle==7) { 
 //BA.debugLineNum = 571;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 572;BA.debugLine="utilidades.Mensaje(\"Perdiste...\", \"El mosquito";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Perdiste...","El mosquito se desarrolló completamente y te picó! La palabra era: "+mostCurrent._wordxx,"OK","","");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 574;BA.debugLine="utilidades.Mensaje(\"You lost...\", \"The mosquito";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"You lost...","The mosquito developed completely and you got stung! The word was: "+mostCurrent._wordxx,"OK","","");
 };
 //BA.debugLineNum = 576;BA.debugLine="Button1_Click";
_button1_click();
 };
 //BA.debugLineNum = 579;BA.debugLine="If P1&P2&P3&P4&P5&P6&P7&P8&P9&P10=wordxx Then";
if ((mostCurrent._p1+mostCurrent._p2+mostCurrent._p3+mostCurrent._p4+mostCurrent._p5+mostCurrent._p6+mostCurrent._p7+mostCurrent._p8+mostCurrent._p9+mostCurrent._p10).equals(mostCurrent._wordxx)) { 
 //BA.debugLineNum = 580;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 581;BA.debugLine="utilidades.Mensaje(\"Ganaste!!!\", \"Felicitacione";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"Ganaste!!!","Felicitaciones! adivinaste la palabra correctamente","OK","","");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 584;BA.debugLine="utilidades.Mensaje(\"You won!!!\", \"Congratulatio";
mostCurrent._utilidades._mensaje(mostCurrent.activityBA,"You won!!!","Congratulations! You guessed the word correctly","OK","","");
 };
 };
 //BA.debugLineNum = 589;BA.debugLine="label2.Text=P1 & \" \" & P2 & \" \" & P3 & \" \" & P4 &";
mostCurrent._label2.setText((Object)(mostCurrent._p1+" "+mostCurrent._p2+" "+mostCurrent._p3+" "+mostCurrent._p4+" "+mostCurrent._p5+" "+mostCurrent._p6+" "+mostCurrent._p7+" "+mostCurrent._p8+" "+mostCurrent._p9+" "+mostCurrent._p10));
 //BA.debugLineNum = 592;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 17;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim ImageView3 As ImageView";
mostCurrent._imageview3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim ImageView4 As ImageView";
mostCurrent._imageview4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim ImageView5 As ImageView";
mostCurrent._imageview5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim ImageView6 As ImageView";
mostCurrent._imageview6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim ImageView7 As ImageView";
mostCurrent._imageview7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim ImageView8 As ImageView";
mostCurrent._imageview8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Dim ImageView9 As ImageView";
mostCurrent._imageview9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim ImageView10 As ImageView";
mostCurrent._imageview10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Dim ImageView11 As ImageView";
mostCurrent._imageview11 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim ImageView12 As ImageView";
mostCurrent._imageview12 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Dim ImageView13 As ImageView";
mostCurrent._imageview13 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Dim ImageView14 As ImageView";
mostCurrent._imageview14 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim ImageView15 As ImageView";
mostCurrent._imageview15 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Dim ImageView16 As ImageView";
mostCurrent._imageview16 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim ImageView17 As ImageView";
mostCurrent._imageview17 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim ImageView18 As ImageView";
mostCurrent._imageview18 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Dim ImageView19 As ImageView";
mostCurrent._imageview19 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Dim ImageView20 As ImageView";
mostCurrent._imageview20 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Dim ImageView21 As ImageView";
mostCurrent._imageview21 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Dim ImageView22 As ImageView";
mostCurrent._imageview22 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim ImageView23 As ImageView";
mostCurrent._imageview23 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Dim ImageView24 As ImageView";
mostCurrent._imageview24 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Dim ImageView25 As ImageView";
mostCurrent._imageview25 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim ImageView26 As ImageView";
mostCurrent._imageview26 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Dim ImageView27 As ImageView";
mostCurrent._imageview27 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Dim button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim button2 As Button";
mostCurrent._button2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Dim L1 As String";
mostCurrent._l1 = "";
 //BA.debugLineNum = 50;BA.debugLine="Dim L2 As String";
mostCurrent._l2 = "";
 //BA.debugLineNum = 51;BA.debugLine="Dim L3 As String";
mostCurrent._l3 = "";
 //BA.debugLineNum = 52;BA.debugLine="Dim L4 As String";
mostCurrent._l4 = "";
 //BA.debugLineNum = 53;BA.debugLine="Dim L5 As String";
mostCurrent._l5 = "";
 //BA.debugLineNum = 54;BA.debugLine="Dim L6 As String";
mostCurrent._l6 = "";
 //BA.debugLineNum = 55;BA.debugLine="Dim L7 As String";
mostCurrent._l7 = "";
 //BA.debugLineNum = 56;BA.debugLine="Dim L8 As String";
mostCurrent._l8 = "";
 //BA.debugLineNum = 57;BA.debugLine="Dim L9 As String";
mostCurrent._l9 = "";
 //BA.debugLineNum = 58;BA.debugLine="Dim L10 As String";
mostCurrent._l10 = "";
 //BA.debugLineNum = 61;BA.debugLine="Dim P1 As String";
mostCurrent._p1 = "";
 //BA.debugLineNum = 62;BA.debugLine="Dim P2 As String";
mostCurrent._p2 = "";
 //BA.debugLineNum = 63;BA.debugLine="Dim P3 As String";
mostCurrent._p3 = "";
 //BA.debugLineNum = 64;BA.debugLine="Dim P4 As String";
mostCurrent._p4 = "";
 //BA.debugLineNum = 65;BA.debugLine="Dim P5 As String";
mostCurrent._p5 = "";
 //BA.debugLineNum = 66;BA.debugLine="Dim P6 As String";
mostCurrent._p6 = "";
 //BA.debugLineNum = 67;BA.debugLine="Dim P7 As String";
mostCurrent._p7 = "";
 //BA.debugLineNum = 68;BA.debugLine="Dim P8 As String";
mostCurrent._p8 = "";
 //BA.debugLineNum = 69;BA.debugLine="Dim P9 As String";
mostCurrent._p9 = "";
 //BA.debugLineNum = 70;BA.debugLine="Dim P10 As String";
mostCurrent._p10 = "";
 //BA.debugLineNum = 72;BA.debugLine="Dim ltr(26) As String";
mostCurrent._ltr = new String[(int) (26)];
java.util.Arrays.fill(mostCurrent._ltr,"");
 //BA.debugLineNum = 74;BA.debugLine="Dim Z As Int";
_z = 0;
 //BA.debugLineNum = 76;BA.debugLine="Dim cycle As Int";
_cycle = 0;
 //BA.debugLineNum = 78;BA.debugLine="Dim wordxx As String";
mostCurrent._wordxx = "";
 //BA.debugLineNum = 79;BA.debugLine="Dim definicionxx As String";
mostCurrent._definicionxx = "";
 //BA.debugLineNum = 80;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 81;BA.debugLine="Private lblDefinicion As Label";
mostCurrent._lbldefinicion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 82;BA.debugLine="Private btnDefinicion As Button";
mostCurrent._btndefinicion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _imageview1_click() throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub imageview1_click";
 //BA.debugLineNum = 168;BA.debugLine="ImageView1.Visible=False";
mostCurrent._imageview1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 169;BA.debugLine="z=0";
_z = (int) (0);
 //BA.debugLineNum = 170;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _imageview10_click() throws Exception{
 //BA.debugLineNum = 221;BA.debugLine="Sub imageview10_click";
 //BA.debugLineNum = 222;BA.debugLine="ImageView10.Visible=False";
mostCurrent._imageview10.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 223;BA.debugLine="z=9";
_z = (int) (9);
 //BA.debugLineNum = 224;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
return "";
}
public static String  _imageview11_click() throws Exception{
 //BA.debugLineNum = 227;BA.debugLine="Sub imageview11_click";
 //BA.debugLineNum = 228;BA.debugLine="ImageView11.Visible=False";
mostCurrent._imageview11.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 229;BA.debugLine="z=10";
_z = (int) (10);
 //BA.debugLineNum = 230;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 231;BA.debugLine="End Sub";
return "";
}
public static String  _imageview12_click() throws Exception{
 //BA.debugLineNum = 233;BA.debugLine="Sub imageview12_click";
 //BA.debugLineNum = 234;BA.debugLine="ImageView12.Visible=False";
mostCurrent._imageview12.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 235;BA.debugLine="z=11";
_z = (int) (11);
 //BA.debugLineNum = 236;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}
public static String  _imageview13_click() throws Exception{
 //BA.debugLineNum = 239;BA.debugLine="Sub imageview13_click";
 //BA.debugLineNum = 240;BA.debugLine="ImageView13.Visible=False";
mostCurrent._imageview13.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 241;BA.debugLine="z=12";
_z = (int) (12);
 //BA.debugLineNum = 242;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _imageview14_click() throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Sub imageview14_click";
 //BA.debugLineNum = 246;BA.debugLine="ImageView14.Visible=False";
mostCurrent._imageview14.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 247;BA.debugLine="z=13";
_z = (int) (13);
 //BA.debugLineNum = 248;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 249;BA.debugLine="End Sub";
return "";
}
public static String  _imageview15_click() throws Exception{
 //BA.debugLineNum = 251;BA.debugLine="Sub imageview15_click";
 //BA.debugLineNum = 252;BA.debugLine="ImageView15.Visible=False";
mostCurrent._imageview15.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 253;BA.debugLine="z=14";
_z = (int) (14);
 //BA.debugLineNum = 254;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 255;BA.debugLine="End Sub";
return "";
}
public static String  _imageview16_click() throws Exception{
 //BA.debugLineNum = 257;BA.debugLine="Sub imageview16_click";
 //BA.debugLineNum = 258;BA.debugLine="ImageView16.Visible=False";
mostCurrent._imageview16.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 259;BA.debugLine="z=15";
_z = (int) (15);
 //BA.debugLineNum = 260;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 261;BA.debugLine="End Sub";
return "";
}
public static String  _imageview17_click() throws Exception{
 //BA.debugLineNum = 263;BA.debugLine="Sub imageview17_click";
 //BA.debugLineNum = 264;BA.debugLine="ImageView17.Visible=False";
mostCurrent._imageview17.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 265;BA.debugLine="z=16";
_z = (int) (16);
 //BA.debugLineNum = 266;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 267;BA.debugLine="End Sub";
return "";
}
public static String  _imageview18_click() throws Exception{
 //BA.debugLineNum = 269;BA.debugLine="Sub imageview18_click";
 //BA.debugLineNum = 270;BA.debugLine="ImageView18.Visible=False";
mostCurrent._imageview18.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 271;BA.debugLine="z=17";
_z = (int) (17);
 //BA.debugLineNum = 272;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public static String  _imageview19_click() throws Exception{
 //BA.debugLineNum = 275;BA.debugLine="Sub imageview19_click";
 //BA.debugLineNum = 276;BA.debugLine="ImageView19.Visible=False";
mostCurrent._imageview19.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 277;BA.debugLine="z=18";
_z = (int) (18);
 //BA.debugLineNum = 278;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 279;BA.debugLine="End Sub";
return "";
}
public static String  _imageview2_click() throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Sub imageview2_click";
 //BA.debugLineNum = 174;BA.debugLine="ImageView2.Visible=False";
mostCurrent._imageview2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 175;BA.debugLine="z=1";
_z = (int) (1);
 //BA.debugLineNum = 176;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static String  _imageview20_click() throws Exception{
 //BA.debugLineNum = 281;BA.debugLine="Sub imageview20_click";
 //BA.debugLineNum = 282;BA.debugLine="ImageView20.Visible=False";
mostCurrent._imageview20.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 283;BA.debugLine="z=19";
_z = (int) (19);
 //BA.debugLineNum = 284;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return "";
}
public static String  _imageview21_click() throws Exception{
 //BA.debugLineNum = 287;BA.debugLine="Sub imageview21_click";
 //BA.debugLineNum = 288;BA.debugLine="ImageView21.Visible=False";
mostCurrent._imageview21.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 289;BA.debugLine="z=20";
_z = (int) (20);
 //BA.debugLineNum = 290;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _imageview22_click() throws Exception{
 //BA.debugLineNum = 293;BA.debugLine="Sub imageview22_click";
 //BA.debugLineNum = 294;BA.debugLine="ImageView22.Visible=False";
mostCurrent._imageview22.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="z=21";
_z = (int) (21);
 //BA.debugLineNum = 296;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 297;BA.debugLine="End Sub";
return "";
}
public static String  _imageview23_click() throws Exception{
 //BA.debugLineNum = 299;BA.debugLine="Sub imageview23_click";
 //BA.debugLineNum = 300;BA.debugLine="ImageView23.Visible=False";
mostCurrent._imageview23.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 301;BA.debugLine="z=22";
_z = (int) (22);
 //BA.debugLineNum = 302;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 303;BA.debugLine="End Sub";
return "";
}
public static String  _imageview24_click() throws Exception{
 //BA.debugLineNum = 305;BA.debugLine="Sub imageview24_click";
 //BA.debugLineNum = 306;BA.debugLine="ImageView24.Visible=False";
mostCurrent._imageview24.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 307;BA.debugLine="z=23";
_z = (int) (23);
 //BA.debugLineNum = 308;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 309;BA.debugLine="End Sub";
return "";
}
public static String  _imageview25_click() throws Exception{
 //BA.debugLineNum = 311;BA.debugLine="Sub imageview25_click";
 //BA.debugLineNum = 312;BA.debugLine="ImageView25.Visible=False";
mostCurrent._imageview25.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 313;BA.debugLine="z=24";
_z = (int) (24);
 //BA.debugLineNum = 314;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 315;BA.debugLine="End Sub";
return "";
}
public static String  _imageview26_click() throws Exception{
 //BA.debugLineNum = 317;BA.debugLine="Sub imageview26_click";
 //BA.debugLineNum = 318;BA.debugLine="ImageView26.Visible=False";
mostCurrent._imageview26.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 319;BA.debugLine="z=25";
_z = (int) (25);
 //BA.debugLineNum = 320;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 321;BA.debugLine="End Sub";
return "";
}
public static String  _imageview3_click() throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Sub imageview3_click";
 //BA.debugLineNum = 180;BA.debugLine="ImageView3.Visible=False";
mostCurrent._imageview3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 181;BA.debugLine="z=2";
_z = (int) (2);
 //BA.debugLineNum = 182;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public static String  _imageview4_click() throws Exception{
 //BA.debugLineNum = 185;BA.debugLine="Sub imageview4_click";
 //BA.debugLineNum = 186;BA.debugLine="ImageView4.Visible=False";
mostCurrent._imageview4.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 187;BA.debugLine="z=3";
_z = (int) (3);
 //BA.debugLineNum = 188;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _imageview5_click() throws Exception{
 //BA.debugLineNum = 191;BA.debugLine="Sub imageview5_click";
 //BA.debugLineNum = 192;BA.debugLine="ImageView5.Visible=False";
mostCurrent._imageview5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 193;BA.debugLine="z=4";
_z = (int) (4);
 //BA.debugLineNum = 194;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return "";
}
public static String  _imageview6_click() throws Exception{
 //BA.debugLineNum = 197;BA.debugLine="Sub imageview6_click";
 //BA.debugLineNum = 198;BA.debugLine="ImageView6.Visible=False";
mostCurrent._imageview6.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 199;BA.debugLine="z=5";
_z = (int) (5);
 //BA.debugLineNum = 200;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _imageview7_click() throws Exception{
 //BA.debugLineNum = 203;BA.debugLine="Sub imageview7_click";
 //BA.debugLineNum = 204;BA.debugLine="ImageView7.Visible=False";
mostCurrent._imageview7.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 205;BA.debugLine="z=6";
_z = (int) (6);
 //BA.debugLineNum = 206;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _imageview8_click() throws Exception{
 //BA.debugLineNum = 209;BA.debugLine="Sub imageview8_click";
 //BA.debugLineNum = 210;BA.debugLine="ImageView8.Visible=False";
mostCurrent._imageview8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 211;BA.debugLine="z=7";
_z = (int) (7);
 //BA.debugLineNum = 212;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}
public static String  _imageview9_click() throws Exception{
 //BA.debugLineNum = 215;BA.debugLine="Sub imageview9_click";
 //BA.debugLineNum = 216;BA.debugLine="ImageView9.Visible=False";
mostCurrent._imageview9.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 217;BA.debugLine="z=8";
_z = (int) (8);
 //BA.debugLineNum = 218;BA.debugLine="calcx";
_calcx();
 //BA.debugLineNum = 219;BA.debugLine="End Sub";
return "";
}
public static String  _learninfo() throws Exception{
anywheresoftware.b4a.objects.LabelWrapper _fondogris = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _scrpanel = null;
anywheresoftware.b4a.objects.ImageViewWrapper _imgciclo = null;
anywheresoftware.b4a.objects.LabelWrapper _textinfo = null;
anywheresoftware.b4a.objects.streams.File.TextReaderWrapper _textoreader = null;
anywheresoftware.b4a.objects.ButtonWrapper _btncerrarinfo = null;
 //BA.debugLineNum = 598;BA.debugLine="Sub LearnInfo";
 //BA.debugLineNum = 600;BA.debugLine="Dim fondogris As Label";
_fondogris = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 601;BA.debugLine="fondogris.Initialize(\"fondogris\")";
_fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 602;BA.debugLine="fondogris.Color = Colors.ARGB(230,65,65,65)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (230),(int) (65),(int) (65),(int) (65)));
 //BA.debugLineNum = 603;BA.debugLine="Activity.AddView(fondogris,0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 606;BA.debugLine="Dim scrPanel As ScrollView";
_scrpanel = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 607;BA.debugLine="scrPanel.Initialize(1000dip)";
_scrpanel.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1000)));
 //BA.debugLineNum = 608;BA.debugLine="Activity.AddView(scrPanel, 0, 0, 100%x, 90%y)";
mostCurrent._activity.AddView((android.view.View)(_scrpanel.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA));
 //BA.debugLineNum = 612;BA.debugLine="Dim imgciclo As ImageView";
_imgciclo = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 613;BA.debugLine="imgciclo.Initialize(\"imgciclo\")";
_imgciclo.Initialize(mostCurrent.activityBA,"imgciclo");
 //BA.debugLineNum = 614;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 615;BA.debugLine="imgciclo.Bitmap = LoadBitmap(File.DirAssets, \"es";
_imgciclo.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"es-ciclo.png").getObject()));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 617;BA.debugLine="imgciclo.Bitmap = LoadBitmap(File.DirAssets, \"en";
_imgciclo.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-ciclo.png").getObject()));
 };
 //BA.debugLineNum = 619;BA.debugLine="scrPanel.Panel.AddView(imgciclo, 5%x, 0, 300dip,";
_scrpanel.getPanel().AddView((android.view.View)(_imgciclo.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),(int) (0),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 622;BA.debugLine="Dim textInfo As Label";
_textinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 623;BA.debugLine="textInfo.Initialize(\"\")";
_textinfo.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 624;BA.debugLine="Dim textoReader As TextReader";
_textoreader = new anywheresoftware.b4a.objects.streams.File.TextReaderWrapper();
 //BA.debugLineNum = 625;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 626;BA.debugLine="textoReader.Initialize(File.OpenInput(File.DirAs";
_textoreader.Initialize((java.io.InputStream)(anywheresoftware.b4a.keywords.Common.File.OpenInput(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"es-info.txt").getObject()));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 628;BA.debugLine="textoReader.Initialize(File.OpenInput(File.DirAs";
_textoreader.Initialize((java.io.InputStream)(anywheresoftware.b4a.keywords.Common.File.OpenInput(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-info.txt").getObject()));
 };
 //BA.debugLineNum = 630;BA.debugLine="textInfo.Text = textoReader.ReadAll";
_textinfo.setText((Object)(_textoreader.ReadAll()));
 //BA.debugLineNum = 631;BA.debugLine="textInfo.Gravity = Gravity.CENTER_HORIZONTAL";
_textinfo.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 632;BA.debugLine="textoReader.Close";
_textoreader.Close();
 //BA.debugLineNum = 633;BA.debugLine="scrPanel.Panel.AddView(textInfo, 0, imgciclo.Top";
_scrpanel.getPanel().AddView((android.view.View)(_textinfo.getObject()),(int) (0),(int) (_imgciclo.getTop()+_imgciclo.getHeight()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 636;BA.debugLine="Dim btnCerrarInfo As Button";
_btncerrarinfo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 637;BA.debugLine="btnCerrarInfo.Initialize(\"btnCerrarInfo\")";
_btncerrarinfo.Initialize(mostCurrent.activityBA,"btnCerrarInfo");
 //BA.debugLineNum = 638;BA.debugLine="btnCerrarInfo.Text = \"Ok\"";
_btncerrarinfo.setText((Object)("Ok"));
 //BA.debugLineNum = 639;BA.debugLine="btnCerrarInfo.Color = Colors.White";
_btncerrarinfo.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 640;BA.debugLine="btnCerrarInfo.TextColor = Colors.Black";
_btncerrarinfo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 641;BA.debugLine="Activity.AddView(btnCerrarInfo, 0, 90%y, 100%x, 1";
mostCurrent._activity.AddView((android.view.View)(_btncerrarinfo.getObject()),(int) (0),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 642;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim random As Int";
_random = 0;
 //BA.debugLineNum = 10;BA.debugLine="Dim words As List";
_words = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="Dim definiciones As List";
_definiciones = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _resetx() throws Exception{
 //BA.debugLineNum = 428;BA.debugLine="Sub resetx";
 //BA.debugLineNum = 430;BA.debugLine="ImageView1.Visible=True";
mostCurrent._imageview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 431;BA.debugLine="ImageView2.Visible=True";
mostCurrent._imageview2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 432;BA.debugLine="ImageView3.Visible=True";
mostCurrent._imageview3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 433;BA.debugLine="ImageView4.Visible=True";
mostCurrent._imageview4.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 434;BA.debugLine="ImageView5.Visible=True";
mostCurrent._imageview5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 435;BA.debugLine="ImageView6.Visible=True";
mostCurrent._imageview6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 436;BA.debugLine="ImageView7.Visible=True";
mostCurrent._imageview7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 437;BA.debugLine="ImageView8.Visible=True";
mostCurrent._imageview8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 438;BA.debugLine="ImageView9.Visible=True";
mostCurrent._imageview9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 439;BA.debugLine="ImageView10.Visible=True";
mostCurrent._imageview10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 440;BA.debugLine="ImageView11.Visible=True";
mostCurrent._imageview11.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 441;BA.debugLine="ImageView12.Visible=True";
mostCurrent._imageview12.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 442;BA.debugLine="ImageView13.Visible=True";
mostCurrent._imageview13.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 443;BA.debugLine="ImageView14.Visible=True";
mostCurrent._imageview14.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 444;BA.debugLine="ImageView15.Visible=True";
mostCurrent._imageview15.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 445;BA.debugLine="ImageView16.Visible=True";
mostCurrent._imageview16.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 446;BA.debugLine="ImageView17.Visible=True";
mostCurrent._imageview17.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 447;BA.debugLine="ImageView18.Visible=True";
mostCurrent._imageview18.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 448;BA.debugLine="ImageView19.Visible=True";
mostCurrent._imageview19.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 449;BA.debugLine="ImageView20.Visible=True";
mostCurrent._imageview20.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 450;BA.debugLine="ImageView21.Visible=True";
mostCurrent._imageview21.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 451;BA.debugLine="ImageView22.Visible=True";
mostCurrent._imageview22.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 452;BA.debugLine="ImageView23.Visible=True";
mostCurrent._imageview23.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 453;BA.debugLine="ImageView24.Visible=True";
mostCurrent._imageview24.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 454;BA.debugLine="ImageView25.Visible=True";
mostCurrent._imageview25.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 455;BA.debugLine="ImageView26.Visible=True";
mostCurrent._imageview26.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 457;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 160;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 161;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 162;BA.debugLine="Label1.Text = \"Guess the word\"";
mostCurrent._label1.setText((Object)("Guess the word"));
 //BA.debugLineNum = 163;BA.debugLine="btnDefinicion.Text = \"Read definition\"";
mostCurrent._btndefinicion.setText((Object)("Read definition"));
 };
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
}
