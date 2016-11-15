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

public class frmperfil extends Activity implements B4AActivity{
	public static frmperfil mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmperfil");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmperfil).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmperfil");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmperfil", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmperfil) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmperfil) Resume **");
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
		return frmperfil.class;
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
        BA.LogInfo("** Activity (frmperfil) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmperfil) Resume **");
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
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblstatus = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfullname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocation = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluser = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntostotales = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipousuario = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtorg = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmisevals = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmisdatos = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlbadges = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgblevel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnivel = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstbadges = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstachievements = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbadge = null;
public static String _imagetoshare = "";
public anywheresoftware.b4a.objects.TabHostWrapper _tabperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluserid = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfullname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllocation = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltusmedallas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltuslogros = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
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
public ilpla.appear.game_ahorcado _game_ahorcado = null;
public ilpla.appear.aprender_factores _aprender_factores = null;
public ilpla.appear.frmminigames _frmminigames = null;
public ilpla.appear.frmhabitatrio _frmhabitatrio = null;
public ilpla.appear.frmabout _frmabout = null;
public ilpla.appear.frmhabitatlaguna _frmhabitatlaguna = null;
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
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _misdatosico = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _misevalsico = null;
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _mismedallasico = null;
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="Activity.LoadLayout(\"frmPerfil\")";
mostCurrent._activity.LoadLayout("frmPerfil",mostCurrent.activityBA);
 //BA.debugLineNum = 49;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 51;BA.debugLine="tabPerfil.Color = Colors.ARGB(255,51,51,51)";
mostCurrent._tabperfil.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 52;BA.debugLine="Dim misdatosico As Bitmap";
_misdatosico = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 53;BA.debugLine="Dim misevalsico As Bitmap";
_misevalsico = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Dim mismedallasico As Bitmap";
_mismedallasico = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 56;BA.debugLine="misdatosico = LoadBitmap(File.DirAssets, \"misdato";
_misdatosico = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"misdatos.png");
 //BA.debugLineNum = 57;BA.debugLine="misevalsico = LoadBitmap(File.DirAssets, \"miseval";
_misevalsico = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"misevals.png");
 //BA.debugLineNum = 58;BA.debugLine="mismedallasico = LoadBitmap(File.DirAssets, \"mism";
_mismedallasico = anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mismedallas.png");
 //BA.debugLineNum = 60;BA.debugLine="tabPerfil.AddTabWithIcon2(\"\", misdatosico, misdat";
mostCurrent._tabperfil.AddTabWithIcon2("",(android.graphics.Bitmap)(_misdatosico.getObject()),(android.graphics.Bitmap)(_misdatosico.getObject()),(android.view.View)(mostCurrent._pnlmisdatos.getObject()));
 //BA.debugLineNum = 61;BA.debugLine="tabPerfil.AddTabWithIcon2(\"\", misevalsico, miseva";
mostCurrent._tabperfil.AddTabWithIcon2("",(android.graphics.Bitmap)(_misevalsico.getObject()),(android.graphics.Bitmap)(_misevalsico.getObject()),(android.view.View)(mostCurrent._pnlmisevals.getObject()));
 //BA.debugLineNum = 62;BA.debugLine="tabPerfil.AddTabWithIcon2(\"\", mismedallasico, mis";
mostCurrent._tabperfil.AddTabWithIcon2("",(android.graphics.Bitmap)(_mismedallasico.getObject()),(android.graphics.Bitmap)(_mismedallasico.getObject()),(android.view.View)(mostCurrent._pnlbadges.getObject()));
 //BA.debugLineNum = 67;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 68;BA.debugLine="ToastMessageShow(\"Buscando puntos!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Buscando puntos!",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 70;BA.debugLine="ToastMessageShow(\"Retrieving points!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Retrieving points!",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 72;BA.debugLine="CheckPuntos";
_checkpuntos();
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 85;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 86;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 87;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 79;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 76;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 222;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 223;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 224;BA.debugLine="End Sub";
return "";
}
public static String  _btnshare_click() throws Exception{
String _app_id = "";
String _redirect_uri = "";
String _name = "";
String _caption = "";
String _description = "";
String _picture = "";
String _link = "";
String _all = "";
anywheresoftware.b4a.objects.IntentWrapper _i = null;
int _j = 0;
 //BA.debugLineNum = 607;BA.debugLine="Sub btnShare_Click";
 //BA.debugLineNum = 609;BA.debugLine="Dim app_id As String = \"1714627388781317\" ' <---";
_app_id = "1714627388781317";
 //BA.debugLineNum = 610;BA.debugLine="Dim redirect_uri As String = \"https://www.faceboo";
_redirect_uri = "https://www.facebook.com";
 //BA.debugLineNum = 611;BA.debugLine="Dim name, caption, description, picture, link, al";
_name = "";
_caption = "";
_description = "";
_picture = "";
_link = "";
_all = "";
 //BA.debugLineNum = 614;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 615;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 616;BA.debugLine="caption = \"Yo utilizo AppEAR y ayudo a la cienci";
_caption = "Yo utilizo AppEAR y ayudo a la ciencia!";
 //BA.debugLineNum = 617;BA.debugLine="description = \"Conseguí una nueva medalla!!!\"";
_description = "Conseguí una nueva medalla!!!";
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 620;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 621;BA.debugLine="caption = \"I use AppEAR and help science!\"";
_caption = "I use AppEAR and help science!";
 //BA.debugLineNum = 622;BA.debugLine="description = \"I got a new medal!!!\"";
_description = "I got a new medal!!!";
 };
 //BA.debugLineNum = 625;BA.debugLine="link = \"http://www.app-ear.com.ar\"";
_link = "http://www.app-ear.com.ar";
 //BA.debugLineNum = 626;BA.debugLine="picture = \"http://www.app-ear.com.ar/users/badges";
_picture = "http://www.app-ear.com.ar/users/badges/"+mostCurrent._imagetoshare;
 //BA.debugLineNum = 628;BA.debugLine="all = \"https://www.facebook.com/dialog/feed?app_i";
_all = "https://www.facebook.com/dialog/feed?app_id="+_app_id+"&link="+_link+"&name="+_name+"&caption="+_caption+"&description="+_description+"&picture="+_picture+"&redirect_uri="+_redirect_uri;
 //BA.debugLineNum = 631;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 632;BA.debugLine="i.Initialize(i.ACTION_VIEW, all)";
_i.Initialize(_i.ACTION_VIEW,_all);
 //BA.debugLineNum = 633;BA.debugLine="i.SetType(\"text/plain\")";
_i.SetType("text/plain");
 //BA.debugLineNum = 634;BA.debugLine="i.PutExtra(\"android.intent.extra.TEXT\", all)";
_i.PutExtra("android.intent.extra.TEXT",(Object)(_all));
 //BA.debugLineNum = 635;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 638;BA.debugLine="Main.numshares = Main.numshares + 1";
mostCurrent._main._numshares = (int) (mostCurrent._main._numshares+1);
 //BA.debugLineNum = 639;BA.debugLine="EnviarShares";
_enviarshares();
 //BA.debugLineNum = 642;BA.debugLine="If Main.numshares = 10 Then";
if (mostCurrent._main._numshares==10) { 
 //BA.debugLineNum = 643;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 644;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, \"so";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sociable.png").getObject()));
 //BA.debugLineNum = 645;BA.debugLine="imagetoshare = Main.lang & \"-sociable.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-sociable.png";
 };
 //BA.debugLineNum = 649;BA.debugLine="If Main.puntostotales >= 5000 And (Main.puntostot";
if (mostCurrent._main._puntostotales>=5000 && (mostCurrent._main._puntostotales-50)<5000) { 
 //BA.debugLineNum = 650;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 651;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, \"go";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"goleador.png").getObject()));
 //BA.debugLineNum = 652;BA.debugLine="imagetoshare = Main.lang & \"-goleador.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-goleador.png";
 };
 //BA.debugLineNum = 654;BA.debugLine="If Main.puntostotales >= 10000 And (Main.puntosto";
if (mostCurrent._main._puntostotales>=10000 && (mostCurrent._main._puntostotales-50)<10000) { 
 //BA.debugLineNum = 655;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 656;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, \"pi";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pichichi.png").getObject()));
 //BA.debugLineNum = 657;BA.debugLine="imagetoshare = Main.lang & \"-pichichi.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-pichichi.png";
 };
 //BA.debugLineNum = 662;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 663;BA.debugLine="For j = Activity.NumberOfViews - 1 To Activity";
{
final int step39 = (int) (-1);
final int limit39 = (int) (mostCurrent._activity.getNumberOfViews()-6);
for (_j = (int) (mostCurrent._activity.getNumberOfViews()-1) ; (step39 > 0 && _j <= limit39) || (step39 < 0 && _j >= limit39); _j = ((int)(0 + _j + step39)) ) {
 //BA.debugLineNum = 664;BA.debugLine="Activity.RemoveViewAt(j)";
mostCurrent._activity.RemoveViewAt(_j);
 }
};
 //BA.debugLineNum = 667;BA.debugLine="End Sub";
return "";
}
public static String  _btnshareothers_click() throws Exception{
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
int _j = 0;
 //BA.debugLineNum = 669;BA.debugLine="Sub btnShareOthers_Click";
 //BA.debugLineNum = 670;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 671;BA.debugLine="File.Copy(File.DirAssets, imagetoshare, File.DirD";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imagetoshare,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"ShareAppEAR.png");
 //BA.debugLineNum = 672;BA.debugLine="share.sharebinary(\"file://\" & File.DirDefaultExte";
_share.sharebinary(mostCurrent.activityBA,"file://"+anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal()+"/ShareAppEAR.png","image/png","Comparte con tus amigos!","Conseguí una nueva medalla!!!");
 //BA.debugLineNum = 675;BA.debugLine="Main.numshares = Main.numshares + 1";
mostCurrent._main._numshares = (int) (mostCurrent._main._numshares+1);
 //BA.debugLineNum = 676;BA.debugLine="EnviarShares";
_enviarshares();
 //BA.debugLineNum = 678;BA.debugLine="If Main.numshares = 10 Then";
if (mostCurrent._main._numshares==10) { 
 //BA.debugLineNum = 679;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 680;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-sociable.png").getObject()));
 //BA.debugLineNum = 681;BA.debugLine="imagetoshare = Main.lang & \"sociable.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"sociable.png";
 };
 //BA.debugLineNum = 685;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 686;BA.debugLine="For j = Activity.NumberOfViews - 1 To Activity";
{
final int step12 = (int) (-1);
final int limit12 = (int) (mostCurrent._activity.getNumberOfViews()-6);
for (_j = (int) (mostCurrent._activity.getNumberOfViews()-1) ; (step12 > 0 && _j <= limit12) || (step12 < 0 && _j >= limit12); _j = ((int)(0 + _j + step12)) ) {
 //BA.debugLineNum = 687;BA.debugLine="Activity.RemoveViewAt(j)";
mostCurrent._activity.RemoveViewAt(_j);
 }
};
 //BA.debugLineNum = 690;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmedallas() throws Exception{
 //BA.debugLineNum = 383;BA.debugLine="Sub cargarMedallas";
 //BA.debugLineNum = 386;BA.debugLine="lstBadges.Clear";
mostCurrent._lstbadges.Clear();
 //BA.debugLineNum = 387;BA.debugLine="lstAchievements.Clear";
mostCurrent._lstachievements.Clear();
 //BA.debugLineNum = 389;BA.debugLine="lstBadges.TwoLinesAndBitmap.ItemHeight = 160dip";
mostCurrent._lstbadges.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 390;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Left = 20di";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 391;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Gravity = G";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 392;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Width = 130";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 393;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.Height = 13";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 394;BA.debugLine="lstBadges.TwoLinesAndBitmap.ImageView.top = 5dip";
mostCurrent._lstbadges.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 395;BA.debugLine="lstBadges.TwoLinesAndBitmap.Label.Visible = False";
mostCurrent._lstbadges.getTwoLinesAndBitmap().Label.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 396;BA.debugLine="lstBadges.TwoLinesAndBitmap.Secondlabel.Visible =";
mostCurrent._lstbadges.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 398;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ItemHeight = 16";
mostCurrent._lstachievements.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (160)));
 //BA.debugLineNum = 399;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Left";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 400;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Gravi";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 401;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Width";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 402;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.Heigh";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (130)));
 //BA.debugLineNum = 403;BA.debugLine="lstAchievements.TwoLinesAndBitmap.ImageView.top =";
mostCurrent._lstachievements.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 404;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Label.Visible =";
mostCurrent._lstachievements.getTwoLinesAndBitmap().Label.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 405;BA.debugLine="lstAchievements.TwoLinesAndBitmap.Secondlabel.Vis";
mostCurrent._lstachievements.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 407;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 409;BA.debugLine="If Main.numevalsok = 1 Then";
if (mostCurrent._main._numevalsok==1) { 
 //BA.debugLineNum = 410;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Novato\", \"Una";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Novato","Una evaluación completa",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"novato.png").getObject()),(Object)("novato"));
 };
 //BA.debugLineNum = 412;BA.debugLine="If Main.numevalsok >= 3 Then";
if (mostCurrent._main._numevalsok>=3) { 
 //BA.debugLineNum = 413;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Aprendiz\", \"Tr";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Aprendiz","Tres evaluaciones completas",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"aprendiz.png").getObject()),(Object)("aprendiz"));
 };
 //BA.debugLineNum = 415;BA.debugLine="If Main.numevalsok >= 5 Then";
if (mostCurrent._main._numevalsok>=5) { 
 //BA.debugLineNum = 416;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Aventurero\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Aventurero","Cinco evaluaciones completas",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"aventurero.png").getObject()),(Object)("aventurero"));
 };
 //BA.debugLineNum = 418;BA.debugLine="If Main.numevalsok >= 15 Then";
if (mostCurrent._main._numevalsok>=15) { 
 //BA.debugLineNum = 419;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Explorador\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Explorador","Quince evaluaciones completas",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"explorador.png").getObject()),(Object)("explorador"));
 };
 //BA.debugLineNum = 421;BA.debugLine="If Main.numevalsok >= 30 Then";
if (mostCurrent._main._numevalsok>=30) { 
 //BA.debugLineNum = 422;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Legendario\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Legendario","Treinta evaluaciones completas!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"legendario.png").getObject()),(Object)("legendario"));
 };
 //BA.debugLineNum = 427;BA.debugLine="If Main.numfotosok >= 16 Then";
if (mostCurrent._main._numfotosok>=16) { 
 //BA.debugLineNum = 428;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Fotogénico\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Fotogénico","16 fotos enviadas",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fotogenico.png").getObject()),(Object)("fotogenico"));
 };
 //BA.debugLineNum = 430;BA.debugLine="If Main.numfotosok >= 120 Then";
if (mostCurrent._main._numfotosok>=120) { 
 //BA.debugLineNum = 431;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Púlitzer fotog";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Púlitzer fotográfico","120 fotos enviadas!!!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pulitzer.png").getObject()),(Object)("pulitzer"));
 };
 //BA.debugLineNum = 435;BA.debugLine="If Main.numshares >= 10 Then";
if (mostCurrent._main._numshares>=10) { 
 //BA.debugLineNum = 436;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Sociable\", \"10";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Sociable","10 evaluaciones compartidas en Facebook",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"sociable.png").getObject()),(Object)("sociable"));
 };
 //BA.debugLineNum = 440;BA.debugLine="If Main.numriollanura >= 1 Then";
if (mostCurrent._main._numriollanura>=1) { 
 //BA.debugLineNum = 441;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Llanura\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Llanura","Completaste tu primera evaluación de un río de llanura",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"llanura.png").getObject()),(Object)("llanura"));
 };
 //BA.debugLineNum = 443;BA.debugLine="If Main.numriomontana >= 1 Then";
if (mostCurrent._main._numriomontana>=1) { 
 //BA.debugLineNum = 444;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Montaña\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Montaña","Completaste tu primera evaluación de un río de montaña",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"montana.png").getObject()),(Object)("montana"));
 };
 //BA.debugLineNum = 446;BA.debugLine="If Main.numestuario >= 1 Then";
if (mostCurrent._main._numestuario>=1) { 
 //BA.debugLineNum = 447;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Estuario";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Estuario","Completaste tu primera evaluación de un estuario",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"estuario.png").getObject()),(Object)("estuario"));
 };
 //BA.debugLineNum = 449;BA.debugLine="If Main.numlaguna >= 1 Then";
if (mostCurrent._main._numlaguna>=1) { 
 //BA.debugLineNum = 450;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Laguna\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Laguna","Completaste tu primera evaluación de una laguna",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"laguna.png").getObject()),(Object)("laguna"));
 };
 //BA.debugLineNum = 452;BA.debugLine="If Main.numlaguna >= 1 And Main.numestuario >= 1";
if (mostCurrent._main._numlaguna>=1 && mostCurrent._main._numestuario>=1 && mostCurrent._main._numriomontana>=1 && mostCurrent._main._numriollanura>=1) { 
 //BA.debugLineNum = 453;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Maestro";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Maestro de todos los ambientes","Completaste una evaluación de cada ambiente",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"maestrodeambientes.png").getObject()),(Object)("maestrodeambientes"));
 };
 //BA.debugLineNum = 456;BA.debugLine="If Main.puntostotales >= 5000 Then";
if (mostCurrent._main._puntostotales>=5000) { 
 //BA.debugLineNum = 457;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Goleador";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Goleador","Alcanzaste los 5000 puntos",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"goleador.png").getObject()),(Object)("goleador"));
 };
 //BA.debugLineNum = 459;BA.debugLine="If Main.puntostotales >= 10000 Then";
if (mostCurrent._main._puntostotales>=10000) { 
 //BA.debugLineNum = 460;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Pichichi";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Pichichi","Alcanzaste los 10000 puntos",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"pichichi.png").getObject()),(Object)("pichichi"));
 };
 //BA.debugLineNum = 462;BA.debugLine="If Main.numevalsok >= 1 Then";
if (mostCurrent._main._numevalsok>=1) { 
 //BA.debugLineNum = 463;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Primera";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Primera validación","Validaron tu primera evaluación!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"primeraeval.png").getObject()),(Object)("primeraeval"));
 };
 //BA.debugLineNum = 465;BA.debugLine="If Main.numfotosok >= 1 Then";
if (mostCurrent._main._numfotosok>=1) { 
 //BA.debugLineNum = 466;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Primera";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Primera foto validada","Validaron tu primera foto!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"primerafoto.png").getObject()),(Object)("primerafoto"));
 };
 //BA.debugLineNum = 468;BA.debugLine="If Main.numshares >= 1 Then";
if (mostCurrent._main._numshares>=1) { 
 //BA.debugLineNum = 469;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Primer s";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Primer share","Compartiste tu primera evaluación en Facebook!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"primershare.png").getObject()),(Object)("primershare"));
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 473;BA.debugLine="If Main.numevalsok = 1 Then";
if (mostCurrent._main._numevalsok==1) { 
 //BA.debugLineNum = 474;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Novice\", \"One";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Novice","One complete report",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-novato.png").getObject()),(Object)("novato"));
 };
 //BA.debugLineNum = 476;BA.debugLine="If Main.numevalsok >= 3 Then";
if (mostCurrent._main._numevalsok>=3) { 
 //BA.debugLineNum = 477;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Apprentice\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Apprentice","Three complete reports",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-aprendiz.png").getObject()),(Object)("aprendiz"));
 };
 //BA.debugLineNum = 479;BA.debugLine="If Main.numevalsok >= 5 Then";
if (mostCurrent._main._numevalsok>=5) { 
 //BA.debugLineNum = 480;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Adventurer\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Adventurer","Five complete reports",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-aventurero.png").getObject()),(Object)("aventurero"));
 };
 //BA.debugLineNum = 482;BA.debugLine="If Main.numevalsok >= 15 Then";
if (mostCurrent._main._numevalsok>=15) { 
 //BA.debugLineNum = 483;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Explorer\", \"Fi";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Explorer","Fifteen complete reports",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-explorador.png").getObject()),(Object)("explorador"));
 };
 //BA.debugLineNum = 485;BA.debugLine="If Main.numevalsok >= 30 Then";
if (mostCurrent._main._numevalsok>=30) { 
 //BA.debugLineNum = 486;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Legendary\", \"T";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Legendary","Thirty complete reports!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-legendario.png").getObject()),(Object)("legendario"));
 };
 //BA.debugLineNum = 491;BA.debugLine="If Main.numfotosok >= 16 Then";
if (mostCurrent._main._numfotosok>=16) { 
 //BA.debugLineNum = 492;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Photogenic\", \"";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Photogenic","16 photos sent",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-fotogenico.png").getObject()),(Object)("fotogenico"));
 };
 //BA.debugLineNum = 494;BA.debugLine="If Main.numfotosok >= 120 Then";
if (mostCurrent._main._numfotosok>=120) { 
 //BA.debugLineNum = 495;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Pulitzer prize";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Pulitzer prize","120 photos sent!!!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-pulitzer.png").getObject()),(Object)("pulitzer"));
 };
 //BA.debugLineNum = 499;BA.debugLine="If Main.numshares >= 10 Then";
if (mostCurrent._main._numshares>=10) { 
 //BA.debugLineNum = 500;BA.debugLine="lstBadges.AddTwoLinesAndBitmap2(\"Sociable\", \"10";
mostCurrent._lstbadges.AddTwoLinesAndBitmap2("Sociable","10 shares in Facebook",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-sociable.png").getObject()),(Object)("sociable"));
 };
 //BA.debugLineNum = 504;BA.debugLine="If Main.numriollanura >= 1 Then";
if (mostCurrent._main._numriollanura>=1) { 
 //BA.debugLineNum = 505;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Lowland\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Lowland","One lowland river report complete",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-llanura.png").getObject()),(Object)("llanura"));
 };
 //BA.debugLineNum = 507;BA.debugLine="If Main.numriomontana >= 1 Then";
if (mostCurrent._main._numriomontana>=1) { 
 //BA.debugLineNum = 508;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Mountain";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Mountain","One mountain river report complete",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-montana.png").getObject()),(Object)("montana"));
 };
 //BA.debugLineNum = 510;BA.debugLine="If Main.numestuario >= 1 Then";
if (mostCurrent._main._numestuario>=1) { 
 //BA.debugLineNum = 511;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Estuary\"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Estuary","One estuary report complete",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-estuario.png").getObject()),(Object)("estuario"));
 };
 //BA.debugLineNum = 513;BA.debugLine="If Main.numlaguna >= 1 Then";
if (mostCurrent._main._numlaguna>=1) { 
 //BA.debugLineNum = 514;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Lake\", \"";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Lake","One lake report complete",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-laguna.png").getObject()),(Object)("laguna"));
 };
 //BA.debugLineNum = 516;BA.debugLine="If Main.numlaguna >= 1 And Main.numestuario >= 1";
if (mostCurrent._main._numlaguna>=1 && mostCurrent._main._numestuario>=1 && mostCurrent._main._numriomontana>=1 && mostCurrent._main._numriollanura>=1) { 
 //BA.debugLineNum = 517;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Master o";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Master of all","One report of each environment complete",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-maestrodeambientes.png").getObject()),(Object)("maestrodeambientes"));
 };
 //BA.debugLineNum = 520;BA.debugLine="If Main.puntostotales >= 5000 Then";
if (mostCurrent._main._puntostotales>=5000) { 
 //BA.debugLineNum = 521;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Scorer\",";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Scorer","You reached 5000 points",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-goleador.png").getObject()),(Object)("goleador"));
 };
 //BA.debugLineNum = 523;BA.debugLine="If Main.puntostotales >= 10000 Then";
if (mostCurrent._main._puntostotales>=10000) { 
 //BA.debugLineNum = 524;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"Champion";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("Champion","You reached 10000 points",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-pichichi.png").getObject()),(Object)("pichichi"));
 };
 //BA.debugLineNum = 526;BA.debugLine="If Main.numevalsok >= 1 Then";
if (mostCurrent._main._numevalsok>=1) { 
 //BA.debugLineNum = 527;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"First re";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("First report","Your first report was validated!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-primeraeval.png").getObject()),(Object)("primeraeval"));
 };
 //BA.debugLineNum = 529;BA.debugLine="If Main.numfotosok >= 1 Then";
if (mostCurrent._main._numfotosok>=1) { 
 //BA.debugLineNum = 530;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"First ph";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("First photo","Your first photo was validated!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-primerafoto.png").getObject()),(Object)("primerafoto"));
 };
 //BA.debugLineNum = 532;BA.debugLine="If Main.numshares >= 1 Then";
if (mostCurrent._main._numshares>=1) { 
 //BA.debugLineNum = 533;BA.debugLine="lstAchievements.AddTwoLinesAndBitmap2(\"First sh";
mostCurrent._lstachievements.AddTwoLinesAndBitmap2("First share","You shared for the first time!",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-primershare.png").getObject()),(Object)("primershare"));
 };
 };
 //BA.debugLineNum = 540;BA.debugLine="End Sub";
return "";
}
public static String  _cargarnivel() throws Exception{
double _nivelfull = 0;
int _nivel = 0;
double _resto = 0;
anywheresoftware.b4a.objects.drawable.GradientDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 561;BA.debugLine="Sub cargarNivel";
 //BA.debugLineNum = 563;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 564;BA.debugLine="Dim nivel As Int";
_nivel = 0;
 //BA.debugLineNum = 565;BA.debugLine="Dim resto As Double";
_resto = 0;
 //BA.debugLineNum = 566;BA.debugLine="nivelfull = (Sqrt(Main.puntostotales) * 0.25)";
_nivelfull = (anywheresoftware.b4a.keywords.Common.Sqrt(mostCurrent._main._puntostotales)*0.25);
 //BA.debugLineNum = 567;BA.debugLine="nivel = Floor(nivelfull)";
_nivel = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 568;BA.debugLine="resto = Round2(Abs(nivelfull - nivel) * 100,0)";
_resto = anywheresoftware.b4a.keywords.Common.Round2(anywheresoftware.b4a.keywords.Common.Abs(_nivelfull-_nivel)*100,(int) (0));
 //BA.debugLineNum = 569;BA.debugLine="pgbLevel.Progress = resto";
mostCurrent._pgblevel.setProgress((int) (_resto));
 //BA.debugLineNum = 570;BA.debugLine="lblNivel.Text = nivel";
mostCurrent._lblnivel.setText((Object)(_nivel));
 //BA.debugLineNum = 574;BA.debugLine="Dim gd As GradientDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.GradientDrawable();
 //BA.debugLineNum = 575;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 576;BA.debugLine="gd.Initialize(\"TOP_BOTTOM\", Array As Int(Colors";
_gd.Initialize(BA.getEnumFromString(android.graphics.drawable.GradientDrawable.Orientation.class,"TOP_BOTTOM"),new int[]{anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.White});
 //BA.debugLineNum = 577;BA.debugLine="cd.Initialize(Colors.ARGB(70,255,255,255), 1dip";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (70),(int) (255),(int) (255),(int) (255)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 578;BA.debugLine="SetProgressDrawable(pgbLevel, gd, cd)";
_setprogressdrawable(mostCurrent._pgblevel,(Object)(_gd.getObject()),(Object)(_cd.getObject()));
 //BA.debugLineNum = 579;BA.debugLine="End Sub";
return "";
}
public static String  _checkpuntos() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _getpuntos = null;
 //BA.debugLineNum = 204;BA.debugLine="Sub CheckPuntos";
 //BA.debugLineNum = 205;BA.debugLine="Dim GetPuntos As HttpJob";
_getpuntos = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 206;BA.debugLine="GetPuntos.Initialize(\"GetPuntos\",Me)";
_getpuntos._initialize(processBA,"GetPuntos",frmperfil.getObject());
 //BA.debugLineNum = 207;BA.debugLine="GetPuntos.Download2(\"http://www.app-ear.com.ar/co";
_getpuntos._download2("http://www.app-ear.com.ar/connect/getpuntos.php",new String[]{"user_id",mostCurrent._main._struserid});
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _enviarshares() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _envioshares = null;
 //BA.debugLineNum = 210;BA.debugLine="Sub EnviarShares";
 //BA.debugLineNum = 211;BA.debugLine="Dim EnvioShares As HttpJob";
_envioshares = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 212;BA.debugLine="EnvioShares.Initialize(\"EnvioShares\", Me)";
_envioshares._initialize(processBA,"EnvioShares",frmperfil.getObject());
 //BA.debugLineNum = 213;BA.debugLine="EnvioShares.Download2(\"http://www.app-ear.com.ar/";
_envioshares._download2("http://www.app-ear.com.ar/connect/recshare.php",new String[]{"UserID",mostCurrent._main._struserid,"NumShares",BA.NumberToString(mostCurrent._main._numshares),"PuntosTotal",BA.NumberToString(mostCurrent._main._puntostotales+50)});
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private lblStatus As Label";
mostCurrent._lblstatus = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private txtFullName As EditText";
mostCurrent._txtfullname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private txtLocation As EditText";
mostCurrent._txtlocation = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private txtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblUser As Label";
mostCurrent._lbluser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblPuntosTotales As Label";
mostCurrent._lblpuntostotales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblTipoUsuario As Label";
mostCurrent._lbltipousuario = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private txtOrg As EditText";
mostCurrent._txtorg = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private pnlMisEvals As Panel";
mostCurrent._pnlmisevals = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private pnlMisDatos As Panel";
mostCurrent._pnlmisdatos = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private pnlBadges As Panel";
mostCurrent._pnlbadges = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private pgbLevel As ProgressBar";
mostCurrent._pgblevel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private lblNivel As Label";
mostCurrent._lblnivel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lstBadges As ListView";
mostCurrent._lstbadges = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private lstAchievements As ListView";
mostCurrent._lstachievements = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private imgBadge As ImageView";
mostCurrent._imgbadge = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Dim imagetoshare As String";
mostCurrent._imagetoshare = "";
 //BA.debugLineNum = 34;BA.debugLine="Private tabPerfil As TabHost";
mostCurrent._tabperfil = new anywheresoftware.b4a.objects.TabHostWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private lblUserID As Label";
mostCurrent._lbluserid = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private lblFullName As Label";
mostCurrent._lblfullname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private lblLocation As Label";
mostCurrent._lbllocation = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private lblEmail As Label";
mostCurrent._lblemail = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private lblTusMedallas As Label";
mostCurrent._lbltusmedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private lblTusLogros As Label";
mostCurrent._lbltuslogros = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String[] _actarray = null;
 //BA.debugLineNum = 120;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 121;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 122;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 123;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 124;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring();
 //BA.debugLineNum = 125;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 126;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 127;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 130;BA.debugLine="If Job.JobName = \"GetPuntos\" Then";
if ((_job._jobname).equals("GetPuntos")) { 
 //BA.debugLineNum = 131;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 132;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 133;BA.debugLine="ToastMessageShow(\"Error recuperando los punt";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error recuperando los puntos",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 135;BA.debugLine="ToastMessageShow(\"Error recovering points\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error recovering points",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 137;BA.debugLine="lblUser.Text = Main.strUserID";
mostCurrent._lbluser.setText((Object)(mostCurrent._main._struserid));
 //BA.debugLineNum = 138;BA.debugLine="txtEmail.Text = Main.strUserEmail";
mostCurrent._txtemail.setText((Object)(mostCurrent._main._struseremail));
 //BA.debugLineNum = 139;BA.debugLine="txtFullName.Text = Main.strUserName";
mostCurrent._txtfullname.setText((Object)(mostCurrent._main._strusername));
 //BA.debugLineNum = 140;BA.debugLine="txtLocation.Text = Main.strUserLocation";
mostCurrent._txtlocation.setText((Object)(mostCurrent._main._struserlocation));
 //BA.debugLineNum = 141;BA.debugLine="txtOrg.Text = Main.strUserOrg";
mostCurrent._txtorg.setText((Object)(mostCurrent._main._struserorg));
 //BA.debugLineNum = 142;BA.debugLine="ListFilesView";
_listfilesview();
 }else if((_act).equals("GetPuntos OK")) { 
 //BA.debugLineNum = 145;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 146;BA.debugLine="Dim actarray() As String = Regex.Split(\"-\", a";
_actarray = anywheresoftware.b4a.keywords.Common.Regex.Split("-",_act);
 //BA.debugLineNum = 147;BA.debugLine="Main.puntostotales = actarray(0)";
mostCurrent._main._puntostotales = (int)(Double.parseDouble(_actarray[(int) (0)]));
 //BA.debugLineNum = 148;BA.debugLine="Main.puntosnumfotos = actarray(1)";
mostCurrent._main._puntosnumfotos = (int)(Double.parseDouble(_actarray[(int) (1)]));
 //BA.debugLineNum = 149;BA.debugLine="Main.puntosnumevals = actarray(2)";
mostCurrent._main._puntosnumevals = (int)(Double.parseDouble(_actarray[(int) (2)]));
 //BA.debugLineNum = 150;BA.debugLine="Main.numevalsok = actarray(3)";
mostCurrent._main._numevalsok = (int)(Double.parseDouble(_actarray[(int) (3)]));
 //BA.debugLineNum = 151;BA.debugLine="Main.numriollanura = actarray(4)";
mostCurrent._main._numriollanura = (int)(Double.parseDouble(_actarray[(int) (4)]));
 //BA.debugLineNum = 152;BA.debugLine="Main.numriomontana = actarray(5)";
mostCurrent._main._numriomontana = (int)(Double.parseDouble(_actarray[(int) (5)]));
 //BA.debugLineNum = 153;BA.debugLine="Main.numlaguna = actarray(6)";
mostCurrent._main._numlaguna = (int)(Double.parseDouble(_actarray[(int) (6)]));
 //BA.debugLineNum = 154;BA.debugLine="Main.numestuario = actarray(7)";
mostCurrent._main._numestuario = (int)(Double.parseDouble(_actarray[(int) (7)]));
 //BA.debugLineNum = 155;BA.debugLine="Main.numshares = actarray(8)";
mostCurrent._main._numshares = (int)(Double.parseDouble(_actarray[(int) (8)]));
 //BA.debugLineNum = 157;BA.debugLine="lblUser.Text = Main.strUserID";
mostCurrent._lbluser.setText((Object)(mostCurrent._main._struserid));
 //BA.debugLineNum = 158;BA.debugLine="txtEmail.Text = Main.strUserEmail";
mostCurrent._txtemail.setText((Object)(mostCurrent._main._struseremail));
 //BA.debugLineNum = 159;BA.debugLine="txtFullName.Text = Main.strUserName";
mostCurrent._txtfullname.setText((Object)(mostCurrent._main._strusername));
 //BA.debugLineNum = 160;BA.debugLine="txtLocation.Text = Main.strUserLocation";
mostCurrent._txtlocation.setText((Object)(mostCurrent._main._struserlocation));
 //BA.debugLineNum = 161;BA.debugLine="lblPuntosTotales.Text = Main.puntostotales";
mostCurrent._lblpuntostotales.setText((Object)(mostCurrent._main._puntostotales));
 //BA.debugLineNum = 162;BA.debugLine="lblTipoUsuario.Text = Main.strUserTipoUsuario";
mostCurrent._lbltipousuario.setText((Object)(mostCurrent._main._strusertipousuario));
 //BA.debugLineNum = 163;BA.debugLine="txtOrg.Text = Main.strUserOrg";
mostCurrent._txtorg.setText((Object)(mostCurrent._main._struserorg));
 //BA.debugLineNum = 164;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 165;BA.debugLine="Label1.Text = \"Evaluaciones validadas: \" & M";
mostCurrent._label1.setText((Object)("Evaluaciones validadas: "+BA.NumberToString(mostCurrent._main._numevalsok)));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 167;BA.debugLine="Label1.Text = \"Validated reports: \" & Main.n";
mostCurrent._label1.setText((Object)("Validated reports: "+BA.NumberToString(mostCurrent._main._numevalsok)));
 };
 //BA.debugLineNum = 170;BA.debugLine="ListFilesView";
_listfilesview();
 //BA.debugLineNum = 171;BA.debugLine="cargarNivel";
_cargarnivel();
 //BA.debugLineNum = 172;BA.debugLine="cargarMedallas";
_cargarmedallas();
 };
 };
 //BA.debugLineNum = 176;BA.debugLine="If Job.JobName = \"EnvioShares\" Then";
if ((_job._jobname).equals("EnvioShares")) { 
 //BA.debugLineNum = 177;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 179;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 180;BA.debugLine="ToastMessageShow(\"Ha fallado el envio!\", Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Ha fallado el envio!",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 182;BA.debugLine="ToastMessageShow(\"Delivery failed!\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Delivery failed!",anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("SharesOK")) { 
 //BA.debugLineNum = 185;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 186;BA.debugLine="ToastMessageShow(\"Has compartido tus resulta";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Has compartido tus resultados, y ganado otros 50 puntos!",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 188;BA.debugLine="ToastMessageShow(\"You have shared your resul";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You have shared your results, and earned another 50 points!",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 191;BA.debugLine="CheckPuntos";
_checkpuntos();
 //BA.debugLineNum = 193;BA.debugLine="frmPrincipal.recargaPuntos = True";
mostCurrent._frmprincipal._recargapuntos = anywheresoftware.b4a.keywords.Common.True;
 };
 };
 }else {
 //BA.debugLineNum = 198;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: "+_job._errormessage,anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 199;BA.debugLine="Msgbox(Job.ErrorMessage, \"Oops!\")";
anywheresoftware.b4a.keywords.Common.Msgbox(_job._errormessage,"Oops!",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 201;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 202;BA.debugLine="End Sub";
return "";
}
public static String  _lblnocompartir_click() throws Exception{
int _i = 0;
 //BA.debugLineNum = 692;BA.debugLine="Sub lblNoCompartir_Click";
 //BA.debugLineNum = 693;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 695;BA.debugLine="For i = Activity.NumberOfViews - 1 To Activity";
{
final int step2 = (int) (-1);
final int limit2 = (int) (mostCurrent._activity.getNumberOfViews()-6);
for (_i = (int) (mostCurrent._activity.getNumberOfViews()-1) ; (step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2); _i = ((int)(0 + _i + step2)) ) {
 //BA.debugLineNum = 696;BA.debugLine="Activity.RemoveViewAt(i)";
mostCurrent._activity.RemoveViewAt(_i);
 }
};
 //BA.debugLineNum = 699;BA.debugLine="End Sub";
return "";
}
public static String  _listfilesview() throws Exception{
anywheresoftware.b4a.objects.collections.List _filelist = null;
int _n = 0;
String _file1 = "";
String _proyrio = "";
String _proydate = "";
String _pdata = "";
anywheresoftware.b4a.objects.collections.List _filelist1 = null;
int _n1 = 0;
String _file11 = "";
 //BA.debugLineNum = 232;BA.debugLine="Sub ListFilesView";
 //BA.debugLineNum = 233;BA.debugLine="ListView1.Clear";
mostCurrent._listview1.Clear();
 //BA.debugLineNum = 235;BA.debugLine="ListView1.Color = Colors.ARGB(255,51,51,51)";
mostCurrent._listview1.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (51),(int) (51),(int) (51)));
 //BA.debugLineNum = 236;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.Width = ListV";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 237;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.Width =";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setWidth((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 238;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextColor = C";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 239;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.TextSize = 12";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setTextSize((float) (12));
 //BA.debugLineNum = 240;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.left = 20dip";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 241;BA.debugLine="ListView1.TwoLinesAndBitmap.SecondLabel.visible";
mostCurrent._listview1.getTwoLinesAndBitmap().SecondLabel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 242;BA.debugLine="ListView1.TwoLinesAndBitmap.Label.Height = 40di";
mostCurrent._listview1.getTwoLinesAndBitmap().Label.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (40)));
 //BA.debugLineNum = 243;BA.debugLine="ListView1.TwoLinesAndBitmap.ItemHeight = 50dip";
mostCurrent._listview1.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 244;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Left = Li";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setLeft((int) (mostCurrent._listview1.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50))));
 //BA.debugLineNum = 245;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Gravity =";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 246;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Width = 2";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 247;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.Height =";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 248;BA.debugLine="ListView1.TwoLinesAndBitmap.ImageView.top = 5di";
mostCurrent._listview1.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 251;BA.debugLine="Dim fileList As List";
_filelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 252;BA.debugLine="Dim n As Int";
_n = 0;
 //BA.debugLineNum = 253;BA.debugLine="Dim file1 As String";
_file1 = "";
 //BA.debugLineNum = 255;BA.debugLine="fileList = File.ListFiles (File.DirRootExter";
_filelist = anywheresoftware.b4a.keywords.Common.File.ListFiles(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal()+"/AppEAR/");
 //BA.debugLineNum = 256;BA.debugLine="fileList.Sort (True)";
_filelist.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 257;BA.debugLine="For n = 0 To fileList.Size-1";
{
final int step21 = 1;
final int limit21 = (int) (_filelist.getSize()-1);
for (_n = (int) (0) ; (step21 > 0 && _n <= limit21) || (step21 < 0 && _n >= limit21); _n = ((int)(0 + _n + step21)) ) {
 //BA.debugLineNum = 258;BA.debugLine="file1 = fileList.Get (n)";
_file1 = BA.ObjectToString(_filelist.Get(_n));
 //BA.debugLineNum = 259;BA.debugLine="If file1 <> \"sent\" Then";
if ((_file1).equals("sent") == false) { 
 //BA.debugLineNum = 260;BA.debugLine="If file1.EndsWith(\".txt\") And file1.StartsWith(";
if (_file1.endsWith(".txt") && _file1.startsWith(mostCurrent._main._username)) { 
 //BA.debugLineNum = 261;BA.debugLine="If file1.EndsWith(\"Notas.txt\") Then";
if (_file1.endsWith("Notas.txt")) { 
 }else {
 //BA.debugLineNum = 263;BA.debugLine="Dim proyrio As String = file1.SubString2(fi";
_proyrio = _file1.substring((int) (_file1.lastIndexOf("@")+1),_file1.indexOf("-"));
 //BA.debugLineNum = 264;BA.debugLine="Dim proydate As String = file1.SubString2(f";
_proydate = _file1.substring((int) (_file1.indexOf("-")+1),_file1.lastIndexOf("."));
 //BA.debugLineNum = 265;BA.debugLine="Dim pdata As String = file1.SubString2(0,file";
_pdata = _file1.substring((int) (0),_file1.lastIndexOf("."));
 //BA.debugLineNum = 266;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(proyrio & \" /";
mostCurrent._listview1.AddTwoLinesAndBitmap2(_proyrio+" /// Fecha: "+_proydate,"",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cross.png").getObject()),(Object)(_pdata));
 };
 };
 };
 }
};
 //BA.debugLineNum = 273;BA.debugLine="Dim fileList1 As List";
_filelist1 = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 274;BA.debugLine="Dim n1 As Int";
_n1 = 0;
 //BA.debugLineNum = 275;BA.debugLine="Dim file11 As String";
_file11 = "";
 //BA.debugLineNum = 277;BA.debugLine="fileList1 = File.ListFiles (Main.savedir & \"/AppE";
_filelist1 = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._main._savedir+"/AppEAR/sent/");
 //BA.debugLineNum = 278;BA.debugLine="fileList1.Sort (True)";
_filelist1.Sort(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 279;BA.debugLine="For n1 = 0 To fileList1.Size-1";
{
final int step40 = 1;
final int limit40 = (int) (_filelist1.getSize()-1);
for (_n1 = (int) (0) ; (step40 > 0 && _n1 <= limit40) || (step40 < 0 && _n1 >= limit40); _n1 = ((int)(0 + _n1 + step40)) ) {
 //BA.debugLineNum = 280;BA.debugLine="file11 = fileList1.Get (n1)";
_file11 = BA.ObjectToString(_filelist1.Get(_n1));
 //BA.debugLineNum = 281;BA.debugLine="If file11.EndsWith(\".txt\") And file11.StartsWith";
if (_file11.endsWith(".txt") && _file11.startsWith(mostCurrent._main._username)) { 
 //BA.debugLineNum = 282;BA.debugLine="If file11.EndsWith(\"Notas.txt\") Then";
if (_file11.endsWith("Notas.txt")) { 
 }else {
 //BA.debugLineNum = 284;BA.debugLine="Dim proyrio As String = file11.SubString2(fi";
_proyrio = _file11.substring((int) (_file11.lastIndexOf("@")+1),_file11.indexOf("-"));
 //BA.debugLineNum = 285;BA.debugLine="Dim proydate As String = file11.SubString2(f";
_proydate = _file11.substring((int) (_file11.indexOf("-")+1),_file11.lastIndexOf("."));
 //BA.debugLineNum = 286;BA.debugLine="ListView1.AddTwoLinesAndBitmap2(proyrio & \" //";
mostCurrent._listview1.AddTwoLinesAndBitmap2(_proyrio+" /// Fecha: "+_proydate,"",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)("cancelar"));
 };
 };
 }
};
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _listview1_itemclick(int _position,Object _value) throws Exception{
String _mysqldata = "";
String[] _numproyectoarray = null;
String[] _actarray = null;
 //BA.debugLineNum = 293;BA.debugLine="Sub ListView1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 294;BA.debugLine="If Value = \"cancelar\" Then";
if ((_value).equals((Object)("cancelar"))) { 
 //BA.debugLineNum = 296;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 297;BA.debugLine="ToastMessageShow(\"Este proyecto ya fue enviado";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Este proyecto ya fue enviado anteriormente",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 299;BA.debugLine="ToastMessageShow(\"This project was already sent";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("This project was already sent",anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 303;BA.debugLine="If File.exists(Main.savedir & \"/AppEAR/\", Value";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",BA.ObjectToString(_value)+".txt")) { 
 //BA.debugLineNum = 304;BA.debugLine="Main.evaluacionpath = Value";
mostCurrent._main._evaluacionpath = BA.ObjectToString(_value);
 //BA.debugLineNum = 307;BA.debugLine="Dim mysqldata As String";
_mysqldata = "";
 //BA.debugLineNum = 308;BA.debugLine="mysqldata = File.ReadString(Main.savedir & \"/Ap";
_mysqldata = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._main._savedir+"/AppEAR/",BA.ObjectToString(_value)+".txt");
 //BA.debugLineNum = 309;BA.debugLine="Dim numproyectoarray() As String = Regex.Split(";
_numproyectoarray = anywheresoftware.b4a.keywords.Common.Regex.Split("@",BA.ObjectToString(_value));
 //BA.debugLineNum = 310;BA.debugLine="Main.Idproyecto = numproyectoarray(1)";
mostCurrent._main._idproyecto = (int)(Double.parseDouble(_numproyectoarray[(int) (1)]));
 //BA.debugLineNum = 311;BA.debugLine="Dim actarray() As String = Regex.Split(\";\", mys";
_actarray = anywheresoftware.b4a.keywords.Common.Regex.Split(";",_mysqldata);
 //BA.debugLineNum = 312;BA.debugLine="Main.username = actarray(0)";
mostCurrent._main._username = _actarray[(int) (0)];
 //BA.debugLineNum = 313;BA.debugLine="Main.dateandtime = actarray(1)";
mostCurrent._main._dateandtime = _actarray[(int) (1)];
 //BA.debugLineNum = 314;BA.debugLine="Main.latitud = actarray(2)";
mostCurrent._main._latitud = _actarray[(int) (2)];
 //BA.debugLineNum = 315;BA.debugLine="Main.longitud = actarray(3)";
mostCurrent._main._longitud = _actarray[(int) (3)];
 //BA.debugLineNum = 316;BA.debugLine="Main.tiporio = actarray(4)";
mostCurrent._main._tiporio = _actarray[(int) (4)];
 //BA.debugLineNum = 317;BA.debugLine="Main.nombrerio = actarray(5)";
mostCurrent._main._nombrerio = _actarray[(int) (5)];
 //BA.debugLineNum = 318;BA.debugLine="Main.valorcalidad = actarray(6)";
mostCurrent._main._valorcalidad = (double)(Double.parseDouble(_actarray[(int) (6)]));
 //BA.debugLineNum = 319;BA.debugLine="Main.valorNS = actarray(7)";
mostCurrent._main._valorns = (int)(Double.parseDouble(_actarray[(int) (7)]));
 //BA.debugLineNum = 320;BA.debugLine="Main.valorind1 = actarray(8)";
mostCurrent._main._valorind1 = _actarray[(int) (8)];
 //BA.debugLineNum = 321;BA.debugLine="Main.valorind2 = actarray(9)";
mostCurrent._main._valorind2 = _actarray[(int) (9)];
 //BA.debugLineNum = 322;BA.debugLine="Main.valorind3 = actarray(10)";
mostCurrent._main._valorind3 = _actarray[(int) (10)];
 //BA.debugLineNum = 323;BA.debugLine="Main.valorind4 = actarray(11)";
mostCurrent._main._valorind4 = _actarray[(int) (11)];
 //BA.debugLineNum = 324;BA.debugLine="Main.valorind5 = actarray(12)";
mostCurrent._main._valorind5 = _actarray[(int) (12)];
 //BA.debugLineNum = 325;BA.debugLine="Main.valorind6 = actarray(13)";
mostCurrent._main._valorind6 = _actarray[(int) (13)];
 //BA.debugLineNum = 326;BA.debugLine="Main.valorind7 = actarray(14)";
mostCurrent._main._valorind7 = _actarray[(int) (14)];
 //BA.debugLineNum = 327;BA.debugLine="Main.valorind8 = actarray(15)";
mostCurrent._main._valorind8 = _actarray[(int) (15)];
 //BA.debugLineNum = 328;BA.debugLine="Main.valorind9 = actarray(16)";
mostCurrent._main._valorind9 = _actarray[(int) (16)];
 //BA.debugLineNum = 329;BA.debugLine="Main.valorind10 = actarray(17)";
mostCurrent._main._valorind10 = _actarray[(int) (17)];
 //BA.debugLineNum = 330;BA.debugLine="Main.valorind11 = actarray(18)";
mostCurrent._main._valorind11 = _actarray[(int) (18)];
 //BA.debugLineNum = 331;BA.debugLine="Main.valorind12 = actarray(19)";
mostCurrent._main._valorind12 = _actarray[(int) (19)];
 //BA.debugLineNum = 332;BA.debugLine="If actarray.Length >= 21 And actarray(20) <> Nu";
if (_actarray.length>=21 && _actarray[(int) (20)]!= null && (_actarray[(int) (20)]).equals("") == false) { 
 //BA.debugLineNum = 333;BA.debugLine="Main.valorind13 = actarray(20)";
mostCurrent._main._valorind13 = _actarray[(int) (20)];
 };
 //BA.debugLineNum = 335;BA.debugLine="If actarray.Length >= 22 And actarray(21) <> Nu";
if (_actarray.length>=22 && _actarray[(int) (21)]!= null && (_actarray[(int) (21)]).equals("") == false) { 
 //BA.debugLineNum = 336;BA.debugLine="Main.valorind14 = actarray(21)";
mostCurrent._main._valorind14 = _actarray[(int) (21)];
 };
 //BA.debugLineNum = 338;BA.debugLine="If actarray.Length >= 23 And actarray(22) <> Nu";
if (_actarray.length>=23 && _actarray[(int) (22)]!= null && (_actarray[(int) (22)]).equals("") == false) { 
 //BA.debugLineNum = 339;BA.debugLine="Main.valorind15 = actarray(22)";
mostCurrent._main._valorind15 = _actarray[(int) (22)];
 };
 //BA.debugLineNum = 341;BA.debugLine="If actarray.Length >= 24 And actarray(23) <> Nu";
if (_actarray.length>=24 && _actarray[(int) (23)]!= null && (_actarray[(int) (23)]).equals("") == false) { 
 //BA.debugLineNum = 342;BA.debugLine="Main.valorind16 = actarray(23)";
mostCurrent._main._valorind16 = _actarray[(int) (23)];
 };
 };
 //BA.debugLineNum = 347;BA.debugLine="If File.exists(Main.savedir & \"/AppEAR/\", Value";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",BA.ObjectToString(_value)+"_1.jpg")) { 
 //BA.debugLineNum = 348;BA.debugLine="Main.fotopath0 = Value & \"_1\"";
mostCurrent._main._fotopath0 = BA.ObjectToString(_value)+"_1";
 }else {
 //BA.debugLineNum = 350;BA.debugLine="Main.fotopath0 = \"\"";
mostCurrent._main._fotopath0 = "";
 };
 //BA.debugLineNum = 352;BA.debugLine="If File.exists(Main.savedir & \"/AppEAR/\", Value";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",BA.ObjectToString(_value)+"_2.jpg")) { 
 //BA.debugLineNum = 353;BA.debugLine="Main.fotopath1 = Value & \"_2\"";
mostCurrent._main._fotopath1 = BA.ObjectToString(_value)+"_2";
 }else {
 //BA.debugLineNum = 355;BA.debugLine="Main.fotopath1 = \"\"";
mostCurrent._main._fotopath1 = "";
 };
 //BA.debugLineNum = 357;BA.debugLine="If File.exists(Main.savedir & \"/AppEAR/\", Value";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",BA.ObjectToString(_value)+"_3.jpg")) { 
 //BA.debugLineNum = 358;BA.debugLine="Main.fotopath2 = Value & \"_3\"";
mostCurrent._main._fotopath2 = BA.ObjectToString(_value)+"_3";
 }else {
 //BA.debugLineNum = 360;BA.debugLine="Main.fotopath2 = \"\"";
mostCurrent._main._fotopath2 = "";
 };
 //BA.debugLineNum = 362;BA.debugLine="If File.exists(Main.savedir & \"/AppEAR/\", Value";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",BA.ObjectToString(_value)+"_4.jpg")) { 
 //BA.debugLineNum = 363;BA.debugLine="Main.fotopath3 = Value & \"_4\"";
mostCurrent._main._fotopath3 = BA.ObjectToString(_value)+"_4";
 }else {
 //BA.debugLineNum = 365;BA.debugLine="Main.fotopath3 = \"\"";
mostCurrent._main._fotopath3 = "";
 };
 //BA.debugLineNum = 367;BA.debugLine="ListView1.Enabled = False";
mostCurrent._listview1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 369;BA.debugLine="frmEvaluacion.evaluaciondone = True";
mostCurrent._frmevaluacion._evaluaciondone = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 370;BA.debugLine="frmEvaluacion.formorigen = \"Envio\"";
mostCurrent._frmevaluacion._formorigen = "Envio";
 //BA.debugLineNum = 371;BA.debugLine="StartActivity(frmEvaluacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmevaluacion.getObject()));
 };
 //BA.debugLineNum = 375;BA.debugLine="End Sub";
return "";
}
public static String  _lstachievements_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 549;BA.debugLine="Sub lstAchievements_ItemClick (Position As Int, Va";
 //BA.debugLineNum = 550;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 551;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Valu";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_value)+".png").getObject()));
 //BA.debugLineNum = 552;BA.debugLine="imagetoshare = Value & \".png\"";
mostCurrent._imagetoshare = BA.ObjectToString(_value)+".png";
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
return "";
}
public static String  _lstbadges_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 542;BA.debugLine="Sub lstBadges_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 543;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 544;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Valu";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),BA.ObjectToString(_value)+".png").getObject()));
 //BA.debugLineNum = 545;BA.debugLine="imagetoshare = Value & \".png\"";
mostCurrent._imagetoshare = BA.ObjectToString(_value)+".png";
 //BA.debugLineNum = 547;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _setprogressdrawable(anywheresoftware.b4a.objects.ProgressBarWrapper _p,Object _drawable,Object _backgrounddrawable) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _clipdrawable = null;
 //BA.debugLineNum = 583;BA.debugLine="Sub SetProgressDrawable(p As ProgressBar, drawable";
 //BA.debugLineNum = 584;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 585;BA.debugLine="Dim clipDrawable As Object";
_clipdrawable = new Object();
 //BA.debugLineNum = 586;BA.debugLine="clipDrawable = r.CreateObject2(\"android.graphic";
_clipdrawable = _r.CreateObject2("android.graphics.drawable.ClipDrawable",new Object[]{_drawable,(Object)(anywheresoftware.b4a.keywords.Common.Gravity.LEFT),(Object)(1)},new String[]{"android.graphics.drawable.Drawable","java.lang.int","java.lang.int"});
 //BA.debugLineNum = 589;BA.debugLine="r.Target = p";
_r.Target = (Object)(_p.getObject());
 //BA.debugLineNum = 590;BA.debugLine="r.Target = r.RunMethod(\"getProgressDrawable\") '";
_r.Target = _r.RunMethod("getProgressDrawable");
 //BA.debugLineNum = 591;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Ar";
_r.RunMethod4("setDrawableByLayerId",new Object[]{(Object)(16908288),_backgrounddrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 594;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Ar";
_r.RunMethod4("setDrawableByLayerId",new Object[]{_r.GetStaticField("android.R$id","progress"),_clipdrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 598;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 98;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 99;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 100;BA.debugLine="Label3.Text = \"Points\"";
mostCurrent._label3.setText((Object)("Points"));
 //BA.debugLineNum = 101;BA.debugLine="lblTusLogros.Text = \"Achievements\"";
mostCurrent._lbltuslogros.setText((Object)("Achievements"));
 //BA.debugLineNum = 102;BA.debugLine="lblTusMedallas.Text = \"Medals\"";
mostCurrent._lbltusmedallas.setText((Object)("Medals"));
 //BA.debugLineNum = 103;BA.debugLine="Label1.Text = \"Completed reports\"";
mostCurrent._label1.setText((Object)("Completed reports"));
 //BA.debugLineNum = 104;BA.debugLine="Label2.Text = \"Those marked with a cross were no";
mostCurrent._label2.setText((Object)("Those marked with a cross were not sent. Press them to send them now!"));
 //BA.debugLineNum = 105;BA.debugLine="lblUserID.Text = \"Username\"";
mostCurrent._lbluserid.setText((Object)("Username"));
 //BA.debugLineNum = 106;BA.debugLine="lblFullName.Text = \"Full name\"";
mostCurrent._lblfullname.setText((Object)("Full name"));
 //BA.debugLineNum = 107;BA.debugLine="lblLocation.Text = \"Location\"";
mostCurrent._lbllocation.setText((Object)("Location"));
 //BA.debugLineNum = 108;BA.debugLine="Label4.Text = \"Organization\"";
mostCurrent._label4.setText((Object)("Organization"));
 //BA.debugLineNum = 109;BA.debugLine="Label5.Text = \"Profile\"";
mostCurrent._label5.setText((Object)("Profile"));
 //BA.debugLineNum = 110;BA.debugLine="btnCerrar.Text = \"Close\"";
mostCurrent._btncerrar.setText((Object)("Close"));
 };
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
}
