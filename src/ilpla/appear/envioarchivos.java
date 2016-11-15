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

public class envioarchivos extends Activity implements B4AActivity{
	public static envioarchivos mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.envioarchivos");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (envioarchivos).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.envioarchivos");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.envioarchivos", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (envioarchivos) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (envioarchivos) Resume **");
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
		return envioarchivos.class;
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
        BA.LogInfo("** Activity (envioarchivos) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (envioarchivos) Resume **");
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
public static int _currentfilesize = 0;
public static anywheresoftware.b4a.net.FTPWrapper _ftp = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lstresultados = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblestado = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpuntosenviados = null;
public static int _puntostotalenviar = 0;
public static int _numfotosenviar = 0;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _progressbar1 = null;
public static String _currentfilesent = "";
public anywheresoftware.b4a.objects.PanelWrapper _pnlemail = null;
public static String _returnsfrom = "";
public static String _imagetoshare = "";
public anywheresoftware.b4a.objects.ImageViewWrapper _imgbadge = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public static int _nivel = 0;
public static boolean _enviandoshare = false;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnemail = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbldatosenviados = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnvolverperfil = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshare = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnshareothers = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnocompartir = null;
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
public ilpla.appear.frmperfil _frmperfil = null;
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
anywheresoftware.b4a.objects.drawable.GradientDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 47;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="Activity.LoadLayout(\"EnvioArchivos\")";
mostCurrent._activity.LoadLayout("EnvioArchivos",mostCurrent.activityBA);
 //BA.debugLineNum = 49;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 51;BA.debugLine="lstResultados.Clear";
mostCurrent._lstresultados.Clear();
 //BA.debugLineNum = 52;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Left =";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)));
 //BA.debugLineNum = 53;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Gravity";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.FILL);
 //BA.debugLineNum = 54;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Width =";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setWidth(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 55;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.Height";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 56;BA.debugLine="lstResultados.TwoLinesAndBitmap.ImageView.top = 1";
mostCurrent._lstresultados.getTwoLinesAndBitmap().ImageView.setTop(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (10)));
 //BA.debugLineNum = 57;BA.debugLine="lstResultados.TwoLinesAndBitmap.ItemHeight = 50di";
mostCurrent._lstresultados.getTwoLinesAndBitmap().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 58;BA.debugLine="ListView1.SingleLineLayout.ItemHeight = 50dip";
mostCurrent._listview1.getSingleLineLayout().setItemHeight(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (50)));
 //BA.debugLineNum = 59;BA.debugLine="ListView1.SingleLineLayout.Label.TextColor = Colo";
mostCurrent._listview1.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 60;BA.debugLine="ListView1.SingleLineLayout.Label.TextSize = 20";
mostCurrent._listview1.getSingleLineLayout().Label.setTextSize((float) (20));
 //BA.debugLineNum = 61;BA.debugLine="ListView1.SingleLineLayout.Label.left = 20dip";
mostCurrent._listview1.getSingleLineLayout().Label.setLeft(anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)));
 //BA.debugLineNum = 62;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 63;BA.debugLine="lblEstado.Text = \"Se enviará el proyecto del río";
mostCurrent._lblestado.setText((Object)("Se enviará el proyecto del río "+mostCurrent._main._evaluacionpath.substring((int) (mostCurrent._main._evaluacionpath.lastIndexOf("@")+1),mostCurrent._main._evaluacionpath.indexOf("-"))));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 66;BA.debugLine="lblEstado.Text = \"You will send the project enti";
mostCurrent._lblestado.setText((Object)("You will send the project entitled "+mostCurrent._main._evaluacionpath.substring((int) (mostCurrent._main._evaluacionpath.lastIndexOf("@")+1),mostCurrent._main._evaluacionpath.indexOf("-"))));
 };
 //BA.debugLineNum = 73;BA.debugLine="Dim gd As GradientDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.GradientDrawable();
 //BA.debugLineNum = 74;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 75;BA.debugLine="gd.Initialize(\"TOP_BOTTOM\", Array As Int(Colors.W";
_gd.Initialize(BA.getEnumFromString(android.graphics.drawable.GradientDrawable.Orientation.class,"TOP_BOTTOM"),new int[]{anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.Colors.White});
 //BA.debugLineNum = 76;BA.debugLine="cd.Initialize(Colors.ARGB(255,139,211,175), 1dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (139),(int) (211),(int) (175)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1)));
 //BA.debugLineNum = 77;BA.debugLine="SetProgressDrawable(ProgressBar1, gd, cd)";
_setprogressdrawable(mostCurrent._progressbar1,(Object)(_gd.getObject()),(Object)(_cd.getObject()));
 //BA.debugLineNum = 80;BA.debugLine="ListFiles";
_listfiles();
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 94;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 95;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 96;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 97;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 83;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 84;BA.debugLine="If returnsfrom = \"perfil\" Then";
if ((mostCurrent._returnsfrom).equals("perfil")) { 
 //BA.debugLineNum = 85;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 86;BA.debugLine="Activity_Create(True)";
_activity_create(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _btnemail_click() throws Exception{
String _filename = "";
String _zipname = "";
com.AB.ABZipUnzip.ABZipUnzip _myzip = null;
anywheresoftware.b4a.objects.collections.List _mylist = null;
int _i = 0;
String _myfile = "";
anywheresoftware.b4a.phone.Phone.Email _message = null;
 //BA.debugLineNum = 761;BA.debugLine="Sub btnEmail_Click";
 //BA.debugLineNum = 765;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/email\", \"\"";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/email","")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 766;BA.debugLine="File.MakeDir(Main.savedir & \"/AppEAR/\", \"email\")";
anywheresoftware.b4a.keywords.Common.File.MakeDir(mostCurrent._main._savedir+"/AppEAR/","email");
 };
 //BA.debugLineNum = 769;BA.debugLine="File.MakeDir(Main.savedir & \"/AppEAR/email\",\"temp";
anywheresoftware.b4a.keywords.Common.File.MakeDir(mostCurrent._main._savedir+"/AppEAR/email","temp");
 //BA.debugLineNum = 771;BA.debugLine="Dim filename As String = Main.evaluacionpath & \".";
_filename = mostCurrent._main._evaluacionpath+".txt";
 //BA.debugLineNum = 772;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filename , M";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/email/temp",_filename);
 //BA.debugLineNum = 773;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
 //BA.debugLineNum = 774;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/email/temp",_filename);
 };
 //BA.debugLineNum = 776;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
 //BA.debugLineNum = 777;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/email/temp",_filename);
 };
 //BA.debugLineNum = 779;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
 //BA.debugLineNum = 780;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/email/temp",_filename);
 };
 //BA.debugLineNum = 782;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
 //BA.debugLineNum = 783;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filename ,";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/email/temp",_filename);
 };
 //BA.debugLineNum = 787;BA.debugLine="Dim zipname As String = Main.evaluacionpath.Repla";
_zipname = mostCurrent._main._evaluacionpath.replace(".txt","");
 //BA.debugLineNum = 788;BA.debugLine="Dim myzip As ABZipUnzip";
_myzip = new com.AB.ABZipUnzip.ABZipUnzip();
 //BA.debugLineNum = 789;BA.debugLine="myzip.ABZipDirectory(Main.savedir & \"/AppEAR/emai";
_myzip.ABZipDirectory(mostCurrent._main._savedir+"/AppEAR/email/temp",_zipname);
 //BA.debugLineNum = 792;BA.debugLine="Dim mylist As List";
_mylist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 793;BA.debugLine="mylist=File.ListFiles(Main.savedir & \"/AppEAR/ema";
_mylist = anywheresoftware.b4a.keywords.Common.File.ListFiles(mostCurrent._main._savedir+"/AppEAR/email/temp");
 //BA.debugLineNum = 794;BA.debugLine="For i= mylist.Size-1 To 0 Step -1";
{
final int step24 = (int) (-1);
final int limit24 = (int) (0);
for (_i = (int) (_mylist.getSize()-1) ; (step24 > 0 && _i <= limit24) || (step24 < 0 && _i >= limit24); _i = ((int)(0 + _i + step24)) ) {
 //BA.debugLineNum = 795;BA.debugLine="Dim MyFile As String =mylist.Get(i)";
_myfile = BA.ObjectToString(_mylist.Get(_i));
 //BA.debugLineNum = 796;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/email/temp";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/email/temp",_myfile);
 }
};
 //BA.debugLineNum = 798;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/email/temp\",";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/email/temp","");
 //BA.debugLineNum = 802;BA.debugLine="Dim Message As Email";
_message = new anywheresoftware.b4a.phone.Phone.Email();
 //BA.debugLineNum = 803;BA.debugLine="Message.To.Add(\"info@app-ear.com.ar\")";
_message.To.Add((Object)("info@app-ear.com.ar"));
 //BA.debugLineNum = 804;BA.debugLine="Message.Attachments.Add(Main.savedir & \"/AppEAR/";
_message.Attachments.Add((Object)(mostCurrent._main._savedir+"/AppEAR/email/"+_zipname));
 //BA.debugLineNum = 805;BA.debugLine="StartActivity(Message.GetIntent)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_message.GetIntent()));
 //BA.debugLineNum = 807;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 808;BA.debugLine="ToastMessageShow(\"El archivo es: \" & Main.savedi";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("El archivo es: "+mostCurrent._main._savedir+"/AppEAR/email/"+_zipname,anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 810;BA.debugLine="ToastMessageShow(\"Your file is: \" & Main.savedir";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Your file is: "+mostCurrent._main._savedir+"/AppEAR/email/"+_zipname,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 812;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 820;BA.debugLine="Sub btnShare_Click";
 //BA.debugLineNum = 822;BA.debugLine="Dim app_id As String = \"1714627388781317\" ' <---";
_app_id = "1714627388781317";
 //BA.debugLineNum = 823;BA.debugLine="Dim redirect_uri As String = \"https://www.faceboo";
_redirect_uri = "https://www.facebook.com";
 //BA.debugLineNum = 824;BA.debugLine="Dim name, caption, description, picture, link, al";
_name = "";
_caption = "";
_description = "";
_picture = "";
_link = "";
_all = "";
 //BA.debugLineNum = 828;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 829;BA.debugLine="link = \"http://www.app-ear.com.ar\"";
_link = "http://www.app-ear.com.ar";
 //BA.debugLineNum = 830;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 831;BA.debugLine="caption = \"Yo utilizo AppEAR y ayudo a la cienci";
_caption = "Yo utilizo AppEAR y ayudo a la ciencia!";
 //BA.debugLineNum = 832;BA.debugLine="If lblTitulo.Text.Contains(\"Ahora eres nivel\") T";
if (mostCurrent._lbltitulo.getText().contains("Ahora eres nivel")) { 
 //BA.debugLineNum = 833;BA.debugLine="description = \"He subido de nivel! Ahora soy ni";
_description = "He subido de nivel! Ahora soy nivel "+BA.NumberToString(_nivel)+" en AppEAR!";
 }else {
 //BA.debugLineNum = 835;BA.debugLine="description = \"Conseguí una nueva medalla!!!\"";
_description = "Conseguí una nueva medalla!!!";
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 838;BA.debugLine="link = \"http://www.app-ear.com.ar\"";
_link = "http://www.app-ear.com.ar";
 //BA.debugLineNum = 839;BA.debugLine="name = \"AppEAR\"";
_name = "AppEAR";
 //BA.debugLineNum = 840;BA.debugLine="caption = \"I use AppEAR to help science!\"";
_caption = "I use AppEAR to help science!";
 //BA.debugLineNum = 841;BA.debugLine="If lblTitulo.Text.Contains(\"You are now level\")";
if (mostCurrent._lbltitulo.getText().contains("You are now level")) { 
 //BA.debugLineNum = 842;BA.debugLine="description = \"I've leveled up! I'm now level \"";
_description = "I've leveled up! I'm now level "+BA.NumberToString(_nivel)+" in AppEAR!";
 }else {
 //BA.debugLineNum = 844;BA.debugLine="description = \"I have a new medal!!!\"";
_description = "I have a new medal!!!";
 };
 };
 //BA.debugLineNum = 850;BA.debugLine="picture = \"http://www.app-ear.com.ar/users/badges";
_picture = "http://www.app-ear.com.ar/users/badges/"+mostCurrent._imagetoshare;
 //BA.debugLineNum = 851;BA.debugLine="all = \"https://www.facebook.com/dialog/feed?app_i";
_all = "https://www.facebook.com/dialog/feed?app_id="+_app_id+"&link="+_link+"&name="+_name+"&caption="+_caption+"&description="+_description+"&picture="+_picture+"&redirect_uri="+_redirect_uri;
 //BA.debugLineNum = 853;BA.debugLine="Dim i As Intent";
_i = new anywheresoftware.b4a.objects.IntentWrapper();
 //BA.debugLineNum = 854;BA.debugLine="i.Initialize(i.ACTION_VIEW, all)";
_i.Initialize(_i.ACTION_VIEW,_all);
 //BA.debugLineNum = 855;BA.debugLine="i.SetType(\"text/plain\")";
_i.SetType("text/plain");
 //BA.debugLineNum = 856;BA.debugLine="i.PutExtra(\"android.intent.extra.TEXT\", all)";
_i.PutExtra("android.intent.extra.TEXT",(Object)(_all));
 //BA.debugLineNum = 857;BA.debugLine="StartActivity(i)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(_i.getObject()));
 //BA.debugLineNum = 860;BA.debugLine="Main.numshares = Main.numshares + 1";
mostCurrent._main._numshares = (int) (mostCurrent._main._numshares+1);
 //BA.debugLineNum = 861;BA.debugLine="EnviarShares";
_enviarshares();
 //BA.debugLineNum = 863;BA.debugLine="If Main.numshares = 1 Then";
if (mostCurrent._main._numshares==1) { 
 //BA.debugLineNum = 864;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 865;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-primershare.png").getObject()));
 //BA.debugLineNum = 866;BA.debugLine="imagetoshare = Main.lang & \"-primershare.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-primershare.png";
 };
 //BA.debugLineNum = 869;BA.debugLine="If Main.numshares = 10 Then";
if (mostCurrent._main._numshares==10) { 
 //BA.debugLineNum = 870;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 871;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-sociable.png").getObject()));
 //BA.debugLineNum = 872;BA.debugLine="imagetoshare = Main.lang & \"-sociable.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-sociable.png";
 };
 //BA.debugLineNum = 876;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 877;BA.debugLine="For j = Activity.NumberOfViews - 1 To Activity";
{
final int step43 = (int) (-1);
final int limit43 = (int) (mostCurrent._activity.getNumberOfViews()-6);
for (_j = (int) (mostCurrent._activity.getNumberOfViews()-1) ; (step43 > 0 && _j <= limit43) || (step43 < 0 && _j >= limit43); _j = ((int)(0 + _j + step43)) ) {
 //BA.debugLineNum = 878;BA.debugLine="Activity.RemoveViewAt(j)";
mostCurrent._activity.RemoveViewAt(_j);
 }
};
 //BA.debugLineNum = 881;BA.debugLine="End Sub";
return "";
}
public static String  _btnshareothers_click() throws Exception{
com.madelephantstudios.MESShareLibrary.MESShareLibrary _share = null;
int _j = 0;
 //BA.debugLineNum = 883;BA.debugLine="Sub btnShareOthers_Click";
 //BA.debugLineNum = 884;BA.debugLine="Dim share As MESShareLibrary";
_share = new com.madelephantstudios.MESShareLibrary.MESShareLibrary();
 //BA.debugLineNum = 885;BA.debugLine="File.Copy(File.DirAssets, imagetoshare, File.DirD";
anywheresoftware.b4a.keywords.Common.File.Copy(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._imagetoshare,anywheresoftware.b4a.keywords.Common.File.getDirDefaultExternal(),"ShareAppEAR.png");
 //BA.debugLineNum = 886;BA.debugLine="share.sharebinary(\"file://\" & Main.savedir & \"/Sh";
_share.sharebinary(mostCurrent.activityBA,"file://"+mostCurrent._main._savedir+"/ShareAppEAR.png","image/png","Comparte con tus amigos!","Conseguí una nueva medalla!!!");
 //BA.debugLineNum = 888;BA.debugLine="Main.numshares = Main.numshares + 1";
mostCurrent._main._numshares = (int) (mostCurrent._main._numshares+1);
 //BA.debugLineNum = 889;BA.debugLine="EnviarShares";
_enviarshares();
 //BA.debugLineNum = 891;BA.debugLine="If Main.numshares = 10 Then";
if (mostCurrent._main._numshares==10) { 
 //BA.debugLineNum = 892;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 893;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-sociable.png").getObject()));
 //BA.debugLineNum = 894;BA.debugLine="imagetoshare = Main.lang & \"-sociable.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-sociable.png";
 };
 //BA.debugLineNum = 898;BA.debugLine="Dim j As Int";
_j = 0;
 //BA.debugLineNum = 899;BA.debugLine="For j = Activity.NumberOfViews - 1 To Activity";
{
final int step12 = (int) (-1);
final int limit12 = (int) (mostCurrent._activity.getNumberOfViews()-6);
for (_j = (int) (mostCurrent._activity.getNumberOfViews()-1) ; (step12 > 0 && _j <= limit12) || (step12 < 0 && _j >= limit12); _j = ((int)(0 + _j + step12)) ) {
 //BA.debugLineNum = 900;BA.debugLine="Activity.RemoveViewAt(j)";
mostCurrent._activity.RemoveViewAt(_j);
 }
};
 //BA.debugLineNum = 903;BA.debugLine="End Sub";
return "";
}
public static String  _btnvolverperfil_click() throws Exception{
 //BA.debugLineNum = 749;BA.debugLine="Sub btnVolverPerfil_Click";
 //BA.debugLineNum = 750;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 751;BA.debugLine="frmPrincipal.recargaPuntos = True";
mostCurrent._frmprincipal._recargapuntos = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 752;BA.debugLine="StartActivity(frmPrincipal)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmprincipal.getObject()));
 //BA.debugLineNum = 753;BA.debugLine="End Sub";
return "";
}
public static String  _buscarpuntos() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _getpuntos = null;
 //BA.debugLineNum = 380;BA.debugLine="Sub BuscarPuntos";
 //BA.debugLineNum = 381;BA.debugLine="Dim GetPuntos As HttpJob";
_getpuntos = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 382;BA.debugLine="GetPuntos.Initialize(\"GetPuntos\",Me)";
_getpuntos._initialize(processBA,"GetPuntos",envioarchivos.getObject());
 //BA.debugLineNum = 383;BA.debugLine="GetPuntos.Download2(\"http://www.app-ear.com.ar/co";
_getpuntos._download2("http://www.app-ear.com.ar/connect/getpuntos.php",new String[]{"user_id",mostCurrent._main._struserid});
 //BA.debugLineNum = 384;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
String _archivonotas = "";
anywheresoftware.b4a.objects.streams.File.TextWriterWrapper _writer = null;
String _notastxt = "";
 //BA.debugLineNum = 190;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 191;BA.debugLine="Button1.Enabled = False";
mostCurrent._button1.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 192;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 193;BA.debugLine="Button1.Text = \"Enviando... aguarde\"";
mostCurrent._button1.setText((Object)("Enviando... aguarde"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 195;BA.debugLine="Button1.Text = \"Sending... please hold\"";
mostCurrent._button1.setText((Object)("Sending... please hold"));
 };
 //BA.debugLineNum = 199;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 200;BA.debugLine="ToastMessageShow(\"Enviando proyecto, esto tardar";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Enviando proyecto, esto tardará unos minutos",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 202;BA.debugLine="ToastMessageShow(\"Sending project, this will tak";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Sending project, this will take a few minutes",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 205;BA.debugLine="FTP.Initialize(\"FTP\", \"ftp.ipage.com\", 21, \"usera";
_ftp.Initialize(processBA,"FTP","ftp.ipage.com",(int) (21),"userapp","|Ne0druid83|");
 //BA.debugLineNum = 208;BA.debugLine="Dim archivonotas As String";
_archivonotas = "";
 //BA.debugLineNum = 209;BA.debugLine="archivonotas = Main.username & \"@\" & Main.Idproye";
_archivonotas = mostCurrent._main._username+"@"+BA.NumberToString(mostCurrent._main._idproyecto)+"@"+"-Notas.txt";
 //BA.debugLineNum = 210;BA.debugLine="Dim Writer As TextWriter";
_writer = new anywheresoftware.b4a.objects.streams.File.TextWriterWrapper();
 //BA.debugLineNum = 212;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", archivo";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",_archivonotas)) { 
 //BA.debugLineNum = 213;BA.debugLine="Dim notastxt As String";
_notastxt = "";
 //BA.debugLineNum = 214;BA.debugLine="notastxt = File.ReadString(Main.savedir & \"/AppE";
_notastxt = anywheresoftware.b4a.keywords.Common.File.ReadString(mostCurrent._main._savedir+"/AppEAR/",_archivonotas);
 //BA.debugLineNum = 215;BA.debugLine="Writer.Initialize(File.OpenOutput(Main.savedir &";
_writer.Initialize((java.io.OutputStream)(anywheresoftware.b4a.keywords.Common.File.OpenOutput(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._main._evaluacionpath+".txt",anywheresoftware.b4a.keywords.Common.True).getObject()));
 //BA.debugLineNum = 216;BA.debugLine="Writer.WriteLine(notastxt)";
_writer.WriteLine(_notastxt);
 //BA.debugLineNum = 217;BA.debugLine="Writer.Close";
_writer.Close();
 //BA.debugLineNum = 219;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", archivono";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",_archivonotas);
 };
 //BA.debugLineNum = 223;BA.debugLine="currentfilesent = Main.evaluacionpath & \".txt\"";
mostCurrent._currentfilesent = mostCurrent._main._evaluacionpath+".txt";
 //BA.debugLineNum = 224;BA.debugLine="ComenzarEnvio(currentfilesent)";
_comenzarenvio(mostCurrent._currentfilesent);
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _cargafelicitaciones() throws Exception{
 //BA.debugLineNum = 578;BA.debugLine="Sub CargaFelicitaciones";
 //BA.debugLineNum = 580;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 581;BA.debugLine="Activity.LoadLayout(\"frmFelicitaciones\")";
mostCurrent._activity.LoadLayout("frmFelicitaciones",mostCurrent.activityBA);
 //BA.debugLineNum = 585;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 586;BA.debugLine="lblpuntosenviados.Text = \"Has ganado \" & ((10 *";
mostCurrent._lblpuntosenviados.setText((Object)("Has ganado "+BA.NumberToString(((10*_numfotosenviar)+60))+" puntos"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 588;BA.debugLine="lblpuntosenviados.Text = \"You won \" & ((10 * num";
mostCurrent._lblpuntosenviados.setText((Object)("You won "+BA.NumberToString(((10*_numfotosenviar)+60))+" points"));
 //BA.debugLineNum = 589;BA.debugLine="lblDatosEnviados.Text = \"Data sent, thank you!\"";
mostCurrent._lbldatosenviados.setText((Object)("Data sent, thank you!"));
 //BA.debugLineNum = 590;BA.debugLine="btnVolverPerfil.Text = \"Continue\"";
mostCurrent._btnvolverperfil.setText((Object)("Continue"));
 };
 //BA.debugLineNum = 592;BA.debugLine="Main.puntostotales = Main.puntostotales + (10 * n";
mostCurrent._main._puntostotales = (int) (mostCurrent._main._puntostotales+(10*_numfotosenviar)+60);
 //BA.debugLineNum = 595;BA.debugLine="CheckNivel";
_checknivel();
 //BA.debugLineNum = 598;BA.debugLine="CheckMedallas";
_checkmedallas();
 //BA.debugLineNum = 599;BA.debugLine="End Sub";
return "";
}
public static String  _checkmedallas() throws Exception{
 //BA.debugLineNum = 641;BA.debugLine="Sub CheckMedallas";
 //BA.debugLineNum = 644;BA.debugLine="If Main.numevalsok = 1 Then";
if (mostCurrent._main._numevalsok==1) { 
 //BA.debugLineNum = 645;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 646;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-novato.png").getObject()));
 //BA.debugLineNum = 647;BA.debugLine="imagetoshare = Main.lang & \"-novato.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-novato.png";
 };
 //BA.debugLineNum = 649;BA.debugLine="If Main.numevalsok = 3 Then";
if (mostCurrent._main._numevalsok==3) { 
 //BA.debugLineNum = 650;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 651;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-aprendiz.png").getObject()));
 //BA.debugLineNum = 652;BA.debugLine="imagetoshare = Main.lang & \"-aprendiz.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-aprendiz.png";
 };
 //BA.debugLineNum = 654;BA.debugLine="If Main.numevalsok = 5 Then";
if (mostCurrent._main._numevalsok==5) { 
 //BA.debugLineNum = 655;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 656;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-aventurero.png").getObject()));
 //BA.debugLineNum = 657;BA.debugLine="imagetoshare = Main.lang & \"-aventurero.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-aventurero.png";
 };
 //BA.debugLineNum = 659;BA.debugLine="If Main.numevalsok = 15 Then";
if (mostCurrent._main._numevalsok==15) { 
 //BA.debugLineNum = 660;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 661;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-explorador.png").getObject()));
 //BA.debugLineNum = 662;BA.debugLine="imagetoshare = Main.lang & \"-explorador.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-explorador.png";
 };
 //BA.debugLineNum = 664;BA.debugLine="If Main.numevalsok = 30 Then";
if (mostCurrent._main._numevalsok==30) { 
 //BA.debugLineNum = 665;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 666;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-legendario.png").getObject()));
 //BA.debugLineNum = 667;BA.debugLine="imagetoshare = Main.lang & \"-legendario.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-legendario.png";
 };
 //BA.debugLineNum = 671;BA.debugLine="If Main.numfotosok = 16 Then";
if (mostCurrent._main._numfotosok==16) { 
 //BA.debugLineNum = 672;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 673;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-fotogenico.png").getObject()));
 //BA.debugLineNum = 674;BA.debugLine="imagetoshare = Main.lang & \"-fotogenico.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-fotogenico.png";
 };
 //BA.debugLineNum = 676;BA.debugLine="If Main.numfotosok = 120 Then";
if (mostCurrent._main._numfotosok==120) { 
 //BA.debugLineNum = 677;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 678;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-pulitzer.png").getObject()));
 //BA.debugLineNum = 679;BA.debugLine="imagetoshare = Main.lang & \"-pulitzer.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-pulitzer.png";
 };
 //BA.debugLineNum = 683;BA.debugLine="If Main.tiporio = \"Llanura\" Then";
if ((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 684;BA.debugLine="If Main.numriollanura = 1 Then";
if (mostCurrent._main._numriollanura==1) { 
 //BA.debugLineNum = 685;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 686;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-llanura.png").getObject()));
 //BA.debugLineNum = 687;BA.debugLine="imagetoshare = Main.lang & \"-llanura.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-llanura.png";
 };
 }else if((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 690;BA.debugLine="If Main.numriomontana = 1 Then";
if (mostCurrent._main._numriomontana==1) { 
 //BA.debugLineNum = 691;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 692;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-montana.png").getObject()));
 //BA.debugLineNum = 693;BA.debugLine="imagetoshare = Main.lang & \"-montana.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-montana.png";
 };
 }else if((mostCurrent._main._tiporio).equals("Laguna")) { 
 //BA.debugLineNum = 696;BA.debugLine="If Main.numlaguna = 1 Then";
if (mostCurrent._main._numlaguna==1) { 
 //BA.debugLineNum = 697;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 698;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-laguna.png").getObject()));
 //BA.debugLineNum = 699;BA.debugLine="imagetoshare = Main.lang & \"-laguna.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-laguna.png";
 };
 }else if((mostCurrent._main._tiporio).equals("Estuario")) { 
 //BA.debugLineNum = 702;BA.debugLine="If Main.numestuario = 1 Then";
if (mostCurrent._main._numestuario==1) { 
 //BA.debugLineNum = 703;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 704;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Ma";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-estuario.png").getObject()));
 //BA.debugLineNum = 705;BA.debugLine="imagetoshare = Main.lang & \"-estuario.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-estuario.png";
 };
 };
 //BA.debugLineNum = 708;BA.debugLine="If Main.numlaguna = 1 And Main.numestuario = 1 An";
if (mostCurrent._main._numlaguna==1 && mostCurrent._main._numestuario==1 && mostCurrent._main._numriomontana==1 && mostCurrent._main._numriollanura==1) { 
 //BA.debugLineNum = 709;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 710;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-maestrodeambientes.png").getObject()));
 //BA.debugLineNum = 711;BA.debugLine="imagetoshare = Main.lang & \"-maestrodeambientes.";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-maestrodeambientes.png";
 };
 //BA.debugLineNum = 715;BA.debugLine="If Main.puntostotales >= 5000 And (Main.puntostot";
if (mostCurrent._main._puntostotales>=5000 && (mostCurrent._main._puntostotales-60-(10*_numfotosenviar))<5000) { 
 //BA.debugLineNum = 716;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 717;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-goleador.png").getObject()));
 //BA.debugLineNum = 718;BA.debugLine="imagetoshare = Main.lang & \"-goleador.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-goleador.png";
 };
 //BA.debugLineNum = 720;BA.debugLine="If Main.puntostotales >= 10000 And (Main.puntosto";
if (mostCurrent._main._puntostotales>=10000 && (mostCurrent._main._puntostotales-60-(10*_numfotosenviar))<10000) { 
 //BA.debugLineNum = 721;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 722;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-pichichi.png").getObject()));
 //BA.debugLineNum = 723;BA.debugLine="imagetoshare = Main.lang & \"-pichichi.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-pichichi.png";
 };
 //BA.debugLineNum = 725;BA.debugLine="If Main.numevalsok >= 1 And (Main.numevalsok - 1)";
if (mostCurrent._main._numevalsok>=1 && (mostCurrent._main._numevalsok-1)<1) { 
 //BA.debugLineNum = 726;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 727;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-primeraeval.png").getObject()));
 //BA.debugLineNum = 728;BA.debugLine="imagetoshare = Main.lang & \"-primeraeval.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-primeraeval.png";
 };
 //BA.debugLineNum = 730;BA.debugLine="If Main.numfotosok >= 1 And (Main.numfotosok - nu";
if (mostCurrent._main._numfotosok>=1 && (mostCurrent._main._numfotosok-_numfotosenviar)<1) { 
 //BA.debugLineNum = 731;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 732;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, Mai";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-primerafoto.png").getObject()));
 //BA.debugLineNum = 733;BA.debugLine="imagetoshare = Main.lang & \"-primerafoto.png\"";
mostCurrent._imagetoshare = mostCurrent._main._lang+"-primerafoto.png";
 };
 //BA.debugLineNum = 735;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 736;BA.debugLine="btnShare.Text = \"Share in Facebook\"";
mostCurrent._btnshare.setText((Object)("Share in Facebook"));
 //BA.debugLineNum = 737;BA.debugLine="btnShareOthers.Text = \"Share as...\"";
mostCurrent._btnshareothers.setText((Object)("Share as..."));
 //BA.debugLineNum = 738;BA.debugLine="lblNoCompartir.Text = \"Do not share\"";
mostCurrent._lblnocompartir.setText((Object)("Do not share"));
 //BA.debugLineNum = 739;BA.debugLine="lblTitulo.Text = \"Achievement unlocked!\"";
mostCurrent._lbltitulo.setText((Object)("Achievement unlocked!"));
 };
 //BA.debugLineNum = 741;BA.debugLine="End Sub";
return "";
}
public static String  _checknivel() throws Exception{
double _nivelfull = 0;
double _nivelfullanterior = 0;
int _nivelanterior = 0;
 //BA.debugLineNum = 608;BA.debugLine="Sub CheckNivel";
 //BA.debugLineNum = 610;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 612;BA.debugLine="nivelfull = (Sqrt(Main.puntostotales) * 0.25)";
_nivelfull = (anywheresoftware.b4a.keywords.Common.Sqrt(mostCurrent._main._puntostotales)*0.25);
 //BA.debugLineNum = 613;BA.debugLine="nivel = Floor(nivelfull)";
_nivel = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 615;BA.debugLine="Dim nivelfullanterior As Double";
_nivelfullanterior = 0;
 //BA.debugLineNum = 616;BA.debugLine="Dim nivelanterior As Int";
_nivelanterior = 0;
 //BA.debugLineNum = 617;BA.debugLine="nivelfullanterior = (Sqrt(Main.puntostotales - 60";
_nivelfullanterior = (anywheresoftware.b4a.keywords.Common.Sqrt(mostCurrent._main._puntostotales-60-(10*_numfotosenviar))*0.25);
 //BA.debugLineNum = 618;BA.debugLine="nivelanterior = Floor(nivelfullanterior)";
_nivelanterior = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfullanterior));
 //BA.debugLineNum = 621;BA.debugLine="If nivelanterior < nivel Then";
if (_nivelanterior<_nivel) { 
 //BA.debugLineNum = 622;BA.debugLine="Activity.LoadLayout(\"layShare\")";
mostCurrent._activity.LoadLayout("layShare",mostCurrent.activityBA);
 //BA.debugLineNum = 623;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 624;BA.debugLine="lblTitulo.Text = \"Ahora eres nivel \" & nivel &";
mostCurrent._lbltitulo.setText((Object)("Ahora eres nivel "+BA.NumberToString(_nivel)+"!"));
 //BA.debugLineNum = 625;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, \"n";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nivel.png").getObject()));
 //BA.debugLineNum = 626;BA.debugLine="imagetoshare = \"novato.png\"";
mostCurrent._imagetoshare = "novato.png";
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 628;BA.debugLine="lblTitulo.Text = \"You are now level \" & nivel &";
mostCurrent._lbltitulo.setText((Object)("You are now level "+BA.NumberToString(_nivel)+"!"));
 //BA.debugLineNum = 629;BA.debugLine="imgBadge.Bitmap = LoadBitmap(File.DirAssets, \"n";
mostCurrent._imgbadge.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"nivel.png").getObject()));
 //BA.debugLineNum = 630;BA.debugLine="imagetoshare = \"novato.png\"";
mostCurrent._imagetoshare = "novato.png";
 };
 };
 //BA.debugLineNum = 634;BA.debugLine="End Sub";
return "";
}
public static String  _comenzarenvio(String _filetosend) throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Sub ComenzarEnvio (filetosend As String)";
 //BA.debugLineNum = 231;BA.debugLine="FTP.PassiveMode = True";
_ftp.setPassiveMode(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 232;BA.debugLine="currentFileSize = File.Size(Main.savedir  & \"/";
_currentfilesize = (int) (anywheresoftware.b4a.keywords.Common.File.Size(mostCurrent._main._savedir+"/AppEAR/",_filetosend));
 //BA.debugLineNum = 233;BA.debugLine="ProgressBar1.Progress = 0";
mostCurrent._progressbar1.setProgress((int) (0));
 //BA.debugLineNum = 234;BA.debugLine="FTP.UploadFile(Main.savedir & \"/AppEAR/\", fileto";
_ftp.UploadFile(processBA,mostCurrent._main._savedir+"/AppEAR/",_filetosend,anywheresoftware.b4a.keywords.Common.True,"/AppEAR/collecteddata/unverified/"+_filetosend);
 //BA.debugLineNum = 235;BA.debugLine="End Sub";
return "";
}
public static String  _enviarmarcadores() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _enviomarcadores = null;
 //BA.debugLineNum = 366;BA.debugLine="Sub EnviarMarcadores";
 //BA.debugLineNum = 368;BA.debugLine="Dim enviomarcadores As HttpJob";
_enviomarcadores = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 369;BA.debugLine="enviomarcadores.Initialize(\"EnvioMarcador\", Me)";
_enviomarcadores._initialize(processBA,"EnvioMarcador",envioarchivos.getObject());
 //BA.debugLineNum = 370;BA.debugLine="enviomarcadores.Download2(\"http://www.app-ear.com";
_enviomarcadores._download2("http://www.app-ear.com.ar/connect/addpuntomapa.php",new String[]{"username",mostCurrent._main._username,"dateandtime",mostCurrent._main._dateandtime,"nombresitio",mostCurrent._main._nombrerio,"tiporio",mostCurrent._main._tiporio,"lat",mostCurrent._main._latitud,"lng",mostCurrent._main._longitud,"indice",BA.NumberToString(mostCurrent._main._valorcalidad),"precision",BA.NumberToString(mostCurrent._main._valorns),"valorind1",mostCurrent._main._valorind1,"valorind2",mostCurrent._main._valorind2,"valorind3",mostCurrent._main._valorind3,"valorind4",mostCurrent._main._valorind4,"valorind5",mostCurrent._main._valorind5,"valorind6",mostCurrent._main._valorind6,"valorind7",mostCurrent._main._valorind7,"valorind8",mostCurrent._main._valorind8,"valorind9",mostCurrent._main._valorind9,"valorind10",mostCurrent._main._valorind10,"valorind11",mostCurrent._main._valorind11,"valorind12",mostCurrent._main._valorind12,"valorind13",mostCurrent._main._valorind13,"valorind14",mostCurrent._main._valorind14,"valorind15",mostCurrent._main._valorind15,"valorind16",mostCurrent._main._valorind16,"foto1path",mostCurrent._main._fotopath0,"foto2path",mostCurrent._main._fotopath1,"foto3path",mostCurrent._main._fotopath2,"foto4path",mostCurrent._main._fotopath3,"verificado","No Verificado"});
 //BA.debugLineNum = 377;BA.debugLine="End Sub";
return "";
}
public static String  _enviarpuntos() throws Exception{
int _puntosfotos = 0;
int _puntosevals = 0;
anywheresoftware.b4a.samples.httputils2.httpjob _enviopuntos = null;
 //BA.debugLineNum = 387;BA.debugLine="Sub EnviarPuntos";
 //BA.debugLineNum = 388;BA.debugLine="Dim puntosfotos As Int";
_puntosfotos = 0;
 //BA.debugLineNum = 389;BA.debugLine="Dim puntosevals As Int";
_puntosevals = 0;
 //BA.debugLineNum = 390;BA.debugLine="numfotosenviar = 0";
_numfotosenviar = (int) (0);
 //BA.debugLineNum = 392;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
 //BA.debugLineNum = 393;BA.debugLine="numfotosenviar = numfotosenviar + 1";
_numfotosenviar = (int) (_numfotosenviar+1);
 };
 //BA.debugLineNum = 395;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
 //BA.debugLineNum = 396;BA.debugLine="numfotosenviar = numfotosenviar + 1";
_numfotosenviar = (int) (_numfotosenviar+1);
 };
 //BA.debugLineNum = 398;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
 //BA.debugLineNum = 399;BA.debugLine="numfotosenviar = numfotosenviar + 1";
_numfotosenviar = (int) (_numfotosenviar+1);
 };
 //BA.debugLineNum = 401;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
 //BA.debugLineNum = 402;BA.debugLine="numfotosenviar = numfotosenviar + 1";
_numfotosenviar = (int) (_numfotosenviar+1);
 };
 //BA.debugLineNum = 406;BA.debugLine="puntostotalenviar = Main.puntostotales + (10 * nu";
_puntostotalenviar = (int) (mostCurrent._main._puntostotales+(10*_numfotosenviar)+60);
 //BA.debugLineNum = 408;BA.debugLine="puntosevals = Main.puntosnumevals + 60";
_puntosevals = (int) (mostCurrent._main._puntosnumevals+60);
 //BA.debugLineNum = 409;BA.debugLine="puntosfotos = Main.puntosnumfotos + (10 * numfoto";
_puntosfotos = (int) (mostCurrent._main._puntosnumfotos+(10*_numfotosenviar));
 //BA.debugLineNum = 413;BA.debugLine="If Main.tiporio = \"Llanura\" Then";
if ((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 414;BA.debugLine="Main.numriollanura = Main.numriollanura + 1";
mostCurrent._main._numriollanura = (int) (mostCurrent._main._numriollanura+1);
 }else if((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 416;BA.debugLine="Main.numriomontana = Main.numriomontana + 1";
mostCurrent._main._numriomontana = (int) (mostCurrent._main._numriomontana+1);
 }else if((mostCurrent._main._tiporio).equals("Laguna")) { 
 //BA.debugLineNum = 418;BA.debugLine="Main.numlaguna = Main.numlaguna + 1";
mostCurrent._main._numlaguna = (int) (mostCurrent._main._numlaguna+1);
 }else if((mostCurrent._main._tiporio).equals("Estuario")) { 
 //BA.debugLineNum = 420;BA.debugLine="Main.numestuario = Main.numestuario + 1";
mostCurrent._main._numestuario = (int) (mostCurrent._main._numestuario+1);
 };
 //BA.debugLineNum = 423;BA.debugLine="Dim EnvioPuntos As HttpJob";
_enviopuntos = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 424;BA.debugLine="EnvioPuntos.Initialize(\"EnvioPuntos\", Me)";
_enviopuntos._initialize(processBA,"EnvioPuntos",envioarchivos.getObject());
 //BA.debugLineNum = 425;BA.debugLine="EnvioPuntos.Download2(\"http://www.app-ear.com.ar/";
_enviopuntos._download2("http://www.app-ear.com.ar/connect/recpuntos.php",new String[]{"UserID",mostCurrent._main._struserid,"PuntosFotos",BA.NumberToString(_puntosfotos),"PuntosEvals",BA.NumberToString(_puntosevals),"PuntosTotal",BA.NumberToString(_puntostotalenviar),"numriollanura",BA.NumberToString(mostCurrent._main._numriollanura),"numriomontana",BA.NumberToString(mostCurrent._main._numriomontana),"numlaguna",BA.NumberToString(mostCurrent._main._numlaguna),"numestuario",BA.NumberToString(mostCurrent._main._numestuario)});
 //BA.debugLineNum = 429;BA.debugLine="End Sub";
return "";
}
public static String  _enviarshares() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _envioshares = null;
 //BA.debugLineNum = 431;BA.debugLine="Sub EnviarShares";
 //BA.debugLineNum = 432;BA.debugLine="Dim EnvioShares As HttpJob";
_envioshares = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 433;BA.debugLine="EnvioShares.Initialize(\"EnvioShares\", Me)";
_envioshares._initialize(processBA,"EnvioShares",envioarchivos.getObject());
 //BA.debugLineNum = 434;BA.debugLine="EnvioShares.Download2(\"http://www.app-ear.com.ar/";
_envioshares._download2("http://www.app-ear.com.ar/connect/recshare.php",new String[]{"UserID",mostCurrent._main._struserid,"NumShares",BA.NumberToString(mostCurrent._main._numshares),"PuntosTotal",BA.NumberToString(mostCurrent._main._puntostotales+50)});
 //BA.debugLineNum = 436;BA.debugLine="End Sub";
return "";
}
public static String  _ftp_uploadcompleted(String _serverpath,boolean _success) throws Exception{
 //BA.debugLineNum = 245;BA.debugLine="Sub FTP_UploadCompleted (ServerPath As String, Suc";
 //BA.debugLineNum = 246;BA.debugLine="If Success = False Then";
if (_success==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 247;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 248;BA.debugLine="Msgbox(\"Error en envío de archivo: \" & currentf";
anywheresoftware.b4a.keywords.Common.Msgbox("Error en envío de archivo: "+mostCurrent._currentfilesent+" ."+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),"Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 250;BA.debugLine="Msgbox(\"Error sending file: \" & currentfilesent";
anywheresoftware.b4a.keywords.Common.Msgbox("Error sending file: "+mostCurrent._currentfilesent+" ."+anywheresoftware.b4a.keywords.Common.LastException(mostCurrent.activityBA).getMessage(),"Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 252;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",LoadBi";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cross.png").getObject()),(Object)(""));
 //BA.debugLineNum = 253;BA.debugLine="pnlEmail.Visible = True";
mostCurrent._pnlemail.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 254;BA.debugLine="pnlEmail.BringToFront";
mostCurrent._pnlemail.BringToFront();
 //BA.debugLineNum = 255;BA.debugLine="Button1.Visible = False";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 257;BA.debugLine="ProgressBar1.Progress = 100";
mostCurrent._progressbar1.setProgress((int) (100));
 //BA.debugLineNum = 258;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",LoadB";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)(""));
 //BA.debugLineNum = 262;BA.debugLine="If currentfilesent.EndsWith(\".txt\") Then";
if (mostCurrent._currentfilesent.endsWith(".txt")) { 
 //BA.debugLineNum = 264;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
 //BA.debugLineNum = 265;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 266;BA.debugLine="ToastMessageShow(\"Evaluación enviada. Enviand";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Evaluación enviada. Enviando foto #1",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 268;BA.debugLine="ToastMessageShow(\"Report sent. Sending photo";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Report sent. Sending photo #1",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 271;BA.debugLine="currentfilesent = Main.fotopath0 & \".jpg\"";
mostCurrent._currentfilesent = mostCurrent._main._fotopath0+".jpg";
 //BA.debugLineNum = 272;BA.debugLine="ComenzarEnvio(currentfilesent)";
_comenzarenvio(mostCurrent._currentfilesent);
 }else {
 //BA.debugLineNum = 274;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 275;BA.debugLine="Msgbox(\"No hay ninguna foto asociada a este p";
anywheresoftware.b4a.keywords.Common.Msgbox("No hay ninguna foto asociada a este proyecto. No se computarán puntos","Lo siento...",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 277;BA.debugLine="Msgbox(\"There are no photos in this report. N";
anywheresoftware.b4a.keywords.Common.Msgbox("There are no photos in this report. No points awarded","Sorry...",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 280;BA.debugLine="Return";
if (true) return "";
 };
 }else if(mostCurrent._currentfilesent.endsWith("_1.jpg")) { 
 //BA.debugLineNum = 284;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 285;BA.debugLine="ToastMessageShow(\"Foto #1 enviada. Continuando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #1 enviada. Continuando envio...",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 287;BA.debugLine="ToastMessageShow(\"Photo #1 sent. Continuing...";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #1 sent. Continuing...",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 290;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
 //BA.debugLineNum = 291;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 292;BA.debugLine="ToastMessageShow(\"Foto #1 enviada. Enviando f";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #1 enviada. Enviando foto #2",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 294;BA.debugLine="ToastMessageShow(\"Photo #1 sent. Sending phot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #1 sent. Sending photo #2",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 296;BA.debugLine="currentfilesent = Main.fotopath1 & \".jpg\"";
mostCurrent._currentfilesent = mostCurrent._main._fotopath1+".jpg";
 //BA.debugLineNum = 297;BA.debugLine="ComenzarEnvio(currentfilesent)";
_comenzarenvio(mostCurrent._currentfilesent);
 }else {
 //BA.debugLineNum = 299;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 300;BA.debugLine="ToastMessageShow(\"Foto #1 enviada. Enviando m";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #1 enviada. Enviando marcadores",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 302;BA.debugLine="ToastMessageShow(\"Photo #1 sent. Sending mark";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #1 sent. Sending markers",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 304;BA.debugLine="EnviarMarcadores";
_enviarmarcadores();
 };
 }else if(mostCurrent._currentfilesent.endsWith("_2.jpg")) { 
 //BA.debugLineNum = 308;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 309;BA.debugLine="ToastMessageShow(\"Foto #2 enviada. Continuando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #2 enviada. Continuando envio...",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 311;BA.debugLine="ToastMessageShow(\"Photo #2 sent. Continuing...";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #2 sent. Continuing...",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 313;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
 //BA.debugLineNum = 314;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 315;BA.debugLine="ToastMessageShow(\"Foto #2 enviada. Enviando f";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #2 enviada. Enviando foto #3",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 317;BA.debugLine="ToastMessageShow(\"Photo #2 sent. Sending phot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #2 sent. Sending photo #3",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 319;BA.debugLine="currentfilesent = Main.fotopath2 & \".jpg\"";
mostCurrent._currentfilesent = mostCurrent._main._fotopath2+".jpg";
 //BA.debugLineNum = 320;BA.debugLine="ComenzarEnvio(currentfilesent)";
_comenzarenvio(mostCurrent._currentfilesent);
 }else {
 //BA.debugLineNum = 322;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 323;BA.debugLine="ToastMessageShow(\"Foto #2 enviada. Enviando m";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #2 enviada. Enviando marcadores",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 325;BA.debugLine="ToastMessageShow(\"Photo #2 sent. Sending mark";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #2 sent. Sending markers",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 327;BA.debugLine="EnviarMarcadores";
_enviarmarcadores();
 };
 }else if(mostCurrent._currentfilesent.endsWith("_3.jpg")) { 
 //BA.debugLineNum = 331;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 332;BA.debugLine="ToastMessageShow(\"Foto #3 enviada. Continuando";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #3 enviada. Continuando envio...",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 334;BA.debugLine="ToastMessageShow(\"Photo #3 sent. Continuing...";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #3 sent. Continuing...",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 336;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
 //BA.debugLineNum = 337;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 338;BA.debugLine="ToastMessageShow(\"Foto #3 enviada. Enviando f";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #3 enviada. Enviando foto #4",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 340;BA.debugLine="ToastMessageShow(\"Photo #3 sent. Sending phot";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #3 sent. Sending photo #4",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 342;BA.debugLine="currentfilesent = Main.fotopath3 & \".jpg\"";
mostCurrent._currentfilesent = mostCurrent._main._fotopath3+".jpg";
 //BA.debugLineNum = 343;BA.debugLine="ComenzarEnvio(currentfilesent)";
_comenzarenvio(mostCurrent._currentfilesent);
 }else {
 //BA.debugLineNum = 345;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 346;BA.debugLine="ToastMessageShow(\"Foto #3 enviada. Enviando m";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #3 enviada. Enviando marcadores",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 348;BA.debugLine="ToastMessageShow(\"Photo #3 sent. Sending mark";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #3 sent. Sending markers",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 350;BA.debugLine="EnviarMarcadores";
_enviarmarcadores();
 };
 }else if(mostCurrent._currentfilesent.endsWith("_4.jpg")) { 
 //BA.debugLineNum = 354;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 355;BA.debugLine="ToastMessageShow(\"Foto #4 enviada. Enviando m";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Foto #4 enviada. Enviando marcadores",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 357;BA.debugLine="ToastMessageShow(\"Photo #4 sent. Sending mark";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo #4 sent. Sending markers",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 359;BA.debugLine="EnviarMarcadores";
_enviarmarcadores();
 };
 };
 //BA.debugLineNum = 362;BA.debugLine="End Sub";
return "";
}
public static String  _ftp_uploadprogress(String _serverpath,long _totaluploaded,long _total) throws Exception{
String _s = "";
 //BA.debugLineNum = 238;BA.debugLine="Sub FTP_UploadProgress (ServerPath As String, Tota";
 //BA.debugLineNum = 239;BA.debugLine="Dim s As String";
_s = "";
 //BA.debugLineNum = 240;BA.debugLine="s = \"Uploaded \" & Round(TotalUploaded / 1000)";
_s = "Uploaded "+BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round(_totaluploaded/(double)1000))+"KB";
 //BA.debugLineNum = 241;BA.debugLine="Log(s)";
anywheresoftware.b4a.keywords.Common.Log(_s);
 //BA.debugLineNum = 242;BA.debugLine="ProgressBar1.Progress = 100 * TotalUploaded /";
mostCurrent._progressbar1.setProgress((int) (100*_totaluploaded/(double)_currentfilesize));
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private lstResultados As ListView";
mostCurrent._lstresultados = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private lblEstado As Label";
mostCurrent._lblestado = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblpuntosenviados As Label";
mostCurrent._lblpuntosenviados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim puntostotalenviar As Int = 0";
_puntostotalenviar = (int) (0);
 //BA.debugLineNum = 20;BA.debugLine="Dim numfotosenviar As Int = 0";
_numfotosenviar = (int) (0);
 //BA.debugLineNum = 21;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private ProgressBar1 As ProgressBar";
mostCurrent._progressbar1 = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Dim currentfilesent As String";
mostCurrent._currentfilesent = "";
 //BA.debugLineNum = 27;BA.debugLine="Private pnlEmail As Panel";
mostCurrent._pnlemail = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim returnsfrom As String";
mostCurrent._returnsfrom = "";
 //BA.debugLineNum = 30;BA.debugLine="Dim imagetoshare As String";
mostCurrent._imagetoshare = "";
 //BA.debugLineNum = 31;BA.debugLine="Private imgBadge As ImageView";
mostCurrent._imgbadge = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim nivel As Int";
_nivel = 0;
 //BA.debugLineNum = 36;BA.debugLine="Dim enviandoshare As Boolean";
_enviandoshare = false;
 //BA.debugLineNum = 37;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnEmail As Button";
mostCurrent._btnemail = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private lblDatosEnviados As Label";
mostCurrent._lbldatosenviados = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btnVolverPerfil As Button";
mostCurrent._btnvolverperfil = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnShare As Button";
mostCurrent._btnshare = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnShareOthers As Button";
mostCurrent._btnshareothers = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private lblNoCompartir As Label";
mostCurrent._lblnocompartir = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String[] _actarray = null;
String _filename = "";
String _filenotas = "";
 //BA.debugLineNum = 439;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 440;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 441;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 442;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 443;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring();
 //BA.debugLineNum = 444;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 445;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 446;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 449;BA.debugLine="If Job.JobName = \"EnvioMarcador\" Then";
if ((_job._jobname).equals("EnvioMarcador")) { 
 //BA.debugLineNum = 450;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 451;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 452;BA.debugLine="ToastMessageShow(\"Error en la carga de marca";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error en la carga de marcadores",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 454;BA.debugLine="ToastMessageShow(\"Error loading markers\", Tr";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error loading markers",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 457;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cross.png").getObject()),(Object)("FalloMarcador"));
 //BA.debugLineNum = 458;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("Marcadores")) { 
 //BA.debugLineNum = 460;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",Loa";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)(""));
 //BA.debugLineNum = 461;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 462;BA.debugLine="ToastMessageShow(\"Recuperando puntos\", False";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Recuperando puntos",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 464;BA.debugLine="ToastMessageShow(\"Recovering points\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Recovering points",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 466;BA.debugLine="BuscarPuntos";
_buscarpuntos();
 };
 };
 //BA.debugLineNum = 470;BA.debugLine="If Job.JobName = \"GetPuntos\" Then";
if ((_job._jobname).equals("GetPuntos")) { 
 //BA.debugLineNum = 471;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 472;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",Loa";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cross.png").getObject()),(Object)("FalloGetPuntos"));
 //BA.debugLineNum = 473;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("GetPuntos OK")) { 
 //BA.debugLineNum = 475;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 476;BA.debugLine="Dim actarray() As String = Regex.Split(\"-\", a";
_actarray = anywheresoftware.b4a.keywords.Common.Regex.Split("-",_act);
 //BA.debugLineNum = 477;BA.debugLine="Main.puntostotales = actarray(0)";
mostCurrent._main._puntostotales = (int)(Double.parseDouble(_actarray[(int) (0)]));
 //BA.debugLineNum = 478;BA.debugLine="Main.puntosnumfotos = actarray(1)";
mostCurrent._main._puntosnumfotos = (int)(Double.parseDouble(_actarray[(int) (1)]));
 //BA.debugLineNum = 479;BA.debugLine="Main.puntosnumevals = actarray(2)";
mostCurrent._main._puntosnumevals = (int)(Double.parseDouble(_actarray[(int) (2)]));
 //BA.debugLineNum = 480;BA.debugLine="Main.numevalsok = actarray(3)";
mostCurrent._main._numevalsok = (int)(Double.parseDouble(_actarray[(int) (3)]));
 //BA.debugLineNum = 481;BA.debugLine="Main.numriollanura = actarray(4)";
mostCurrent._main._numriollanura = (int)(Double.parseDouble(_actarray[(int) (4)]));
 //BA.debugLineNum = 482;BA.debugLine="Main.numriomontana = actarray(5)";
mostCurrent._main._numriomontana = (int)(Double.parseDouble(_actarray[(int) (5)]));
 //BA.debugLineNum = 483;BA.debugLine="Main.numlaguna = actarray(6)";
mostCurrent._main._numlaguna = (int)(Double.parseDouble(_actarray[(int) (6)]));
 //BA.debugLineNum = 484;BA.debugLine="Main.numestuario = actarray(7)";
mostCurrent._main._numestuario = (int)(Double.parseDouble(_actarray[(int) (7)]));
 //BA.debugLineNum = 485;BA.debugLine="Main.numshares = actarray(8)";
mostCurrent._main._numshares = (int)(Double.parseDouble(_actarray[(int) (8)]));
 //BA.debugLineNum = 486;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",Loa";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)(""));
 //BA.debugLineNum = 487;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 488;BA.debugLine="ToastMessageShow(\"Enviando puntos\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Enviando puntos",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 490;BA.debugLine="ToastMessageShow(\"Sending points\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Sending points",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 492;BA.debugLine="EnviarPuntos";
_enviarpuntos();
 };
 };
 //BA.debugLineNum = 497;BA.debugLine="If Job.JobName = \"EnvioPuntos\" Then";
if ((_job._jobname).equals("EnvioPuntos")) { 
 //BA.debugLineNum = 498;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 499;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",Loa";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"cross.png").getObject()),(Object)("FalloEnvioPuntos"));
 //BA.debugLineNum = 500;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 }else if((_act).equals("Puntos Cargados")) { 
 //BA.debugLineNum = 502;BA.debugLine="lstResultados.AddTwoLinesAndBitmap2(\"\",\"\",Loa";
mostCurrent._lstresultados.AddTwoLinesAndBitmap2("","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"check.png").getObject()),(Object)(""));
 //BA.debugLineNum = 505;BA.debugLine="Dim filename As String = Main.evaluacionpath";
_filename = mostCurrent._main._evaluacionpath+".txt";
 //BA.debugLineNum = 506;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filename";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/sent/",_filename);
 //BA.debugLineNum = 509;BA.debugLine="Dim filenotas As String";
_filenotas = "";
 //BA.debugLineNum = 510;BA.debugLine="filenotas = Main.evaluacionpath.SubString2(0,";
_filenotas = mostCurrent._main._evaluacionpath.substring((int) (0),mostCurrent._main._evaluacionpath.indexOf("@",(int) (mostCurrent._main._evaluacionpath.indexOf("@")+1)))+"@-Notas.txt";
 //BA.debugLineNum = 512;BA.debugLine="If File.Exists(Main.savedir & \"/AppEAR/\", fil";
if (anywheresoftware.b4a.keywords.Common.File.Exists(mostCurrent._main._savedir+"/AppEAR/",_filenotas)) { 
 //BA.debugLineNum = 513;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filenot";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filenotas,mostCurrent._main._savedir+"/AppEAR/sent/",_filenotas);
 //BA.debugLineNum = 514;BA.debugLine="File.delete(Main.savedir & \"/AppEAR/\", filen";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",_filenotas);
 };
 //BA.debugLineNum = 516;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", filena";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",_filename);
 //BA.debugLineNum = 521;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
 //BA.debugLineNum = 522;BA.debugLine="filename = Main.fotopath0 & \".jpg\"";
_filename = mostCurrent._main._fotopath0+".jpg";
 //BA.debugLineNum = 523;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filenam";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/sent/",_filename);
 //BA.debugLineNum = 524;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", filen";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",_filename);
 };
 //BA.debugLineNum = 526;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
 //BA.debugLineNum = 527;BA.debugLine="filename = Main.fotopath1 & \".jpg\"";
_filename = mostCurrent._main._fotopath1+".jpg";
 //BA.debugLineNum = 528;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filenam";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/sent/",_filename);
 //BA.debugLineNum = 529;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", filen";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",_filename);
 };
 //BA.debugLineNum = 531;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
 //BA.debugLineNum = 532;BA.debugLine="filename = Main.fotopath2 & \".jpg\"";
_filename = mostCurrent._main._fotopath2+".jpg";
 //BA.debugLineNum = 533;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filenam";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/sent/",_filename);
 //BA.debugLineNum = 534;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", filen";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",_filename);
 };
 //BA.debugLineNum = 536;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
 //BA.debugLineNum = 537;BA.debugLine="filename = Main.fotopath3 & \".jpg\"";
_filename = mostCurrent._main._fotopath3+".jpg";
 //BA.debugLineNum = 538;BA.debugLine="File.Copy(Main.savedir & \"/AppEAR/\", filenam";
anywheresoftware.b4a.keywords.Common.File.Copy(mostCurrent._main._savedir+"/AppEAR/",_filename,mostCurrent._main._savedir+"/AppEAR/sent/",_filename);
 //BA.debugLineNum = 539;BA.debugLine="File.Delete(Main.savedir & \"/AppEAR/\", filen";
anywheresoftware.b4a.keywords.Common.File.Delete(mostCurrent._main._savedir+"/AppEAR/",_filename);
 };
 //BA.debugLineNum = 543;BA.debugLine="CargaFelicitaciones";
_cargafelicitaciones();
 //BA.debugLineNum = 544;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 };
 };
 //BA.debugLineNum = 547;BA.debugLine="If Job.JobName = \"EnvioShares\" Then";
if ((_job._jobname).equals("EnvioShares")) { 
 //BA.debugLineNum = 548;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 549;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 550;BA.debugLine="ToastMessageShow(\"Ha fallado el envio!\", Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Ha fallado el envio!",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 552;BA.debugLine="ToastMessageShow(\"Delivery failed\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Delivery failed",anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("SharesOK")) { 
 //BA.debugLineNum = 556;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 557;BA.debugLine="ToastMessageShow(\"Has compartido tus resulta";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Has compartido tus resultados, y ganado otros 50 puntos!",anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 559;BA.debugLine="ToastMessageShow(\"You have shared your resul";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("You have shared your results, and won another 50 points!",anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 561;BA.debugLine="BuscarPuntos";
_buscarpuntos();
 };
 };
 }else {
 };
 //BA.debugLineNum = 568;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 569;BA.debugLine="End Sub";
return "";
}
public static String  _lblnocompartir_click() throws Exception{
 //BA.debugLineNum = 905;BA.debugLine="Sub lblNoCompartir_Click";
 //BA.debugLineNum = 906;BA.debugLine="frmEvaluacion.formorigen = \"Enviado\"";
mostCurrent._frmevaluacion._formorigen = "Enviado";
 //BA.debugLineNum = 907;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 908;BA.debugLine="End Sub";
return "";
}
public static String  _listfiles() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub ListFiles";
 //BA.debugLineNum = 119;BA.debugLine="ListView1.SingleLineLayout.Label.TextColor = Colo";
mostCurrent._listview1.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 120;BA.debugLine="ListView1.SingleLineLayout.Label.TextSize = 8dip";
mostCurrent._listview1.getSingleLineLayout().Label.setTextSize((float) (anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (8))));
 //BA.debugLineNum = 121;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 122;BA.debugLine="ListView1.AddSingleLine(\"Envío de la evaluación\"";
mostCurrent._listview1.AddSingleLine("Envío de la evaluación");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 124;BA.debugLine="ListView1.AddSingleLine(\"Send report\")";
mostCurrent._listview1.AddSingleLine("Send report");
 };
 //BA.debugLineNum = 128;BA.debugLine="If Main.fotopath0 <> \"\" Then";
if ((mostCurrent._main._fotopath0).equals("") == false) { 
 //BA.debugLineNum = 129;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 130;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #1\")";
mostCurrent._listview1.AddSingleLine("Envío: Foto #1");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 132;BA.debugLine="ListView1.AddSingleLine(\"Sen: Photo #1\")";
mostCurrent._listview1.AddSingleLine("Sen: Photo #1");
 };
 };
 //BA.debugLineNum = 136;BA.debugLine="If Main.fotopath1 <> \"\" Then";
if ((mostCurrent._main._fotopath1).equals("") == false) { 
 //BA.debugLineNum = 137;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 138;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #2\")";
mostCurrent._listview1.AddSingleLine("Envío: Foto #2");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 140;BA.debugLine="ListView1.AddSingleLine(\"Sen: Photo #2\")";
mostCurrent._listview1.AddSingleLine("Sen: Photo #2");
 };
 };
 //BA.debugLineNum = 144;BA.debugLine="If Main.fotopath2 <> \"\" Then";
if ((mostCurrent._main._fotopath2).equals("") == false) { 
 //BA.debugLineNum = 145;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 146;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #3\")";
mostCurrent._listview1.AddSingleLine("Envío: Foto #3");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 148;BA.debugLine="ListView1.AddSingleLine(\"Sen: Photo #3\")";
mostCurrent._listview1.AddSingleLine("Sen: Photo #3");
 };
 };
 //BA.debugLineNum = 152;BA.debugLine="If Main.fotopath3 <> \"\" Then";
if ((mostCurrent._main._fotopath3).equals("") == false) { 
 //BA.debugLineNum = 153;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 154;BA.debugLine="ListView1.AddSingleLine(\"Envío: Foto #4\")";
mostCurrent._listview1.AddSingleLine("Envío: Foto #4");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 156;BA.debugLine="ListView1.AddSingleLine(\"Sen: Photo #5\")";
mostCurrent._listview1.AddSingleLine("Sen: Photo #5");
 };
 };
 //BA.debugLineNum = 159;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 160;BA.debugLine="ListView1.AddSingleLine(\"Envío de marcadores\")";
mostCurrent._listview1.AddSingleLine("Envío de marcadores");
 //BA.debugLineNum = 161;BA.debugLine="ListView1.AddSingleLine(\"Recuperación de puntos";
mostCurrent._listview1.AddSingleLine("Recuperación de puntos anteriores");
 //BA.debugLineNum = 162;BA.debugLine="ListView1.AddSingleLine(\"Envío de puntos nuevos\"";
mostCurrent._listview1.AddSingleLine("Envío de puntos nuevos");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 164;BA.debugLine="ListView1.AddSingleLine(\"Send markers\")";
mostCurrent._listview1.AddSingleLine("Send markers");
 //BA.debugLineNum = 165;BA.debugLine="ListView1.AddSingleLine(\"Recovering previous poi";
mostCurrent._listview1.AddSingleLine("Recovering previous points");
 //BA.debugLineNum = 166;BA.debugLine="ListView1.AddSingleLine(\"Sending new points\")";
mostCurrent._listview1.AddSingleLine("Sending new points");
 };
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim currentFileSize As Int";
_currentfilesize = 0;
 //BA.debugLineNum = 10;BA.debugLine="Dim FTP As FTP";
_ftp = new anywheresoftware.b4a.net.FTPWrapper();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _setprogressdrawable(anywheresoftware.b4a.objects.ProgressBarWrapper _p,Object _drawable,Object _backgrounddrawable) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
Object _clipdrawable = null;
 //BA.debugLineNum = 172;BA.debugLine="Sub SetProgressDrawable(p As ProgressBar, drawable";
 //BA.debugLineNum = 173;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 174;BA.debugLine="Dim clipDrawable As Object";
_clipdrawable = new Object();
 //BA.debugLineNum = 175;BA.debugLine="clipDrawable = r.CreateObject2(\"android.graphic";
_clipdrawable = _r.CreateObject2("android.graphics.drawable.ClipDrawable",new Object[]{_drawable,(Object)(anywheresoftware.b4a.keywords.Common.Gravity.LEFT),(Object)(1)},new String[]{"android.graphics.drawable.Drawable","java.lang.int","java.lang.int"});
 //BA.debugLineNum = 178;BA.debugLine="r.Target = p";
_r.Target = (Object)(_p.getObject());
 //BA.debugLineNum = 179;BA.debugLine="r.Target = r.RunMethod(\"getProgressDrawable\") '";
_r.Target = _r.RunMethod("getProgressDrawable");
 //BA.debugLineNum = 180;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Ar";
_r.RunMethod4("setDrawableByLayerId",new Object[]{(Object)(16908288),_backgrounddrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 183;BA.debugLine="r.RunMethod4(\"setDrawableByLayerId\", _       Ar";
_r.RunMethod4("setDrawableByLayerId",new Object[]{_r.GetStaticField("android.R$id","progress"),_clipdrawable},new String[]{"java.lang.int","android.graphics.drawable.Drawable"});
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 108;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 109;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 110;BA.debugLine="Label1.Text = \"Send files. For every report, you";
mostCurrent._label1.setText((Object)("Send files. For every report, you will get 60 points, and 10 points for each photo sent!"));
 //BA.debugLineNum = 111;BA.debugLine="Label2.Text = \"If the process failed, you can se";
mostCurrent._label2.setText((Object)("If the process failed, you can send the report by email, and your points will be awarded later"));
 //BA.debugLineNum = 112;BA.debugLine="Button1.Text = \"Send files!\"";
mostCurrent._button1.setText((Object)("Send files!"));
 };
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
}
