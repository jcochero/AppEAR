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

public class register extends Activity implements B4AActivity{
	public static register mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.register");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (register).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.register");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.register", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (register) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (register) Resume **");
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
		return register.class;
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
        BA.LogInfo("** Activity (register) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (register) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtuserid = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtfullname = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtlocation = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemail = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.collections.Map _spinnermap = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtorg = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblorg = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblmessage = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluserid = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblfullname = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpassword = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpassword2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllocation = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnregister = null;
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
public ilpla.appear.envioarchivos _envioarchivos = null;
public ilpla.appear.frmcamara _frmcamara = null;
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
 //BA.debugLineNum = 31;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 32;BA.debugLine="Activity.LoadLayout(\"frmRegister\")";
mostCurrent._activity.LoadLayout("frmRegister",mostCurrent.activityBA);
 //BA.debugLineNum = 33;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 34;BA.debugLine="spinnerMap.Initialize";
mostCurrent._spinnermap.Initialize();
 //BA.debugLineNum = 35;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 36;BA.debugLine="Spinner1.Add(\"Profesional científico\")";
mostCurrent._spinner1.Add("Profesional científico");
 //BA.debugLineNum = 37;BA.debugLine="spinnerMap.Put(\"Profesional científico\", \"Profes";
mostCurrent._spinnermap.Put((Object)("Profesional científico"),(Object)("Profesional científico"));
 //BA.debugLineNum = 38;BA.debugLine="Spinner1.Add(\"Docente secundario\")";
mostCurrent._spinner1.Add("Docente secundario");
 //BA.debugLineNum = 39;BA.debugLine="spinnerMap.Put(\"Docente secundario\", \"Docente se";
mostCurrent._spinnermap.Put((Object)("Docente secundario"),(Object)("Docente secundario"));
 //BA.debugLineNum = 40;BA.debugLine="Spinner1.Add(\"Escuela\")";
mostCurrent._spinner1.Add("Escuela");
 //BA.debugLineNum = 41;BA.debugLine="spinnerMap.Put(\"Escuela\", \"Escuela\")";
mostCurrent._spinnermap.Put((Object)("Escuela"),(Object)("Escuela"));
 //BA.debugLineNum = 42;BA.debugLine="Spinner1.Add(\"Alumno, individual\")";
mostCurrent._spinner1.Add("Alumno, individual");
 //BA.debugLineNum = 43;BA.debugLine="spinnerMap.Put(\"Alumno, individual\", \"Alumno, in";
mostCurrent._spinnermap.Put((Object)("Alumno, individual"),(Object)("Alumno, individual"));
 //BA.debugLineNum = 44;BA.debugLine="Spinner1.Add(\"Amateur\")";
mostCurrent._spinner1.Add("Amateur");
 //BA.debugLineNum = 45;BA.debugLine="spinnerMap.Put(\"Amateur\", \"Amateur\")";
mostCurrent._spinnermap.Put((Object)("Amateur"),(Object)("Amateur"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 47;BA.debugLine="Spinner1.Add(\"Science professional\")";
mostCurrent._spinner1.Add("Science professional");
 //BA.debugLineNum = 48;BA.debugLine="spinnerMap.Put(\"Science professional\", \"Profesio";
mostCurrent._spinnermap.Put((Object)("Science professional"),(Object)("Profesional científico"));
 //BA.debugLineNum = 49;BA.debugLine="Spinner1.Add(\"Teacher\")";
mostCurrent._spinner1.Add("Teacher");
 //BA.debugLineNum = 50;BA.debugLine="spinnerMap.Put(\"Teacher\", \"Docente secundario\")";
mostCurrent._spinnermap.Put((Object)("Teacher"),(Object)("Docente secundario"));
 //BA.debugLineNum = 51;BA.debugLine="Spinner1.Add(\"School\")";
mostCurrent._spinner1.Add("School");
 //BA.debugLineNum = 52;BA.debugLine="spinnerMap.Put(\"School\", \"Escuela\")";
mostCurrent._spinnermap.Put((Object)("School"),(Object)("Escuela"));
 //BA.debugLineNum = 53;BA.debugLine="Spinner1.Add(\"Student, individual\")";
mostCurrent._spinner1.Add("Student, individual");
 //BA.debugLineNum = 54;BA.debugLine="spinnerMap.Put(\"Student, individual\", \"Alumno, i";
mostCurrent._spinnermap.Put((Object)("Student, individual"),(Object)("Alumno, individual"));
 //BA.debugLineNum = 55;BA.debugLine="Spinner1.Add(\"Amateur\")";
mostCurrent._spinner1.Add("Amateur");
 //BA.debugLineNum = 56;BA.debugLine="spinnerMap.Put(\"Amateur\", \"Amateur\")";
mostCurrent._spinnermap.Put((Object)("Amateur"),(Object)("Amateur"));
 };
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 62;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 63;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 64;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 65;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 67;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public static String  _btnacepto_click() throws Exception{
anywheresoftware.b4a.samples.httputils2.httpjob _register = null;
 //BA.debugLineNum = 358;BA.debugLine="Sub btnAcepto_Click";
 //BA.debugLineNum = 359;BA.debugLine="Dim Register As HttpJob";
_register = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 360;BA.debugLine="Register.Initialize(\"Register\", Me)";
_register._initialize(processBA,"Register",register.getObject());
 //BA.debugLineNum = 361;BA.debugLine="Register.Download2(\"http://www.app-ear.com.ar/";
_register._download2("http://www.app-ear.com.ar/connect/signup.php",new String[]{"Action","Register","UserID",mostCurrent._txtuserid.getText(),"Password",mostCurrent._txtpassword.getText(),"FullName",mostCurrent._txtfullname.getText(),"Location",mostCurrent._txtlocation.getText(),"Org",mostCurrent._txtorg.getText(),"usertipo",mostCurrent._spinner1.getSelectedItem(),"Email",mostCurrent._txtemail.getText()});
 //BA.debugLineNum = 370;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 371;BA.debugLine="ProgressDialogShow(\"Conectando al servidor...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Conectando al servidor...");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 373;BA.debugLine="ProgressDialogShow(\"Connecting to the server...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Connecting to the server...");
 };
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return "";
}
public static String  _btnnoacepto_click() throws Exception{
 //BA.debugLineNum = 378;BA.debugLine="Sub btnNoAcepto_Click";
 //BA.debugLineNum = 379;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 380;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 381;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 382;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 383;BA.debugLine="End Sub";
return "";
}
public static String  _btnregister_click() throws Exception{
String _struserid = "";
String _strpassword = "";
String _strpassword2 = "";
String _stremail = "";
String _txtlegales = "";
anywheresoftware.b4a.objects.LabelWrapper _lbllegales = null;
anywheresoftware.b4a.objects.ScrollViewWrapper _scrlegales = null;
anywheresoftware.b4a.objects.ButtonWrapper _btnacepto = null;
anywheresoftware.b4a.objects.ButtonWrapper _btnnoacepto = null;
anywheresoftware.b4a.objects.ImageViewWrapper _fondogris = null;
float _ht = 0f;
anywheresoftware.b4a.objects.StringUtils _strutil = null;
 //BA.debugLineNum = 133;BA.debugLine="Sub btnRegister_Click";
 //BA.debugLineNum = 135;BA.debugLine="Dim strUserID As String = txtUserID.Text.Trim";
_struserid = mostCurrent._txtuserid.getText().trim();
 //BA.debugLineNum = 136;BA.debugLine="If strUserID = \"\" Then";
if ((_struserid).equals("")) { 
 //BA.debugLineNum = 137;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 138;BA.debugLine="Msgbox(\"Ingrese ID de usuario\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Ingrese ID de usuario","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 140;BA.debugLine="Msgbox(\"Enter your user ID\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Enter your user ID","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 142;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 144;BA.debugLine="If strUserID.Contains(\"@\") Then";
if (_struserid.contains("@")) { 
 //BA.debugLineNum = 145;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 146;BA.debugLine="Msgbox(\"El usuario no puede contener el carácter";
anywheresoftware.b4a.keywords.Common.Msgbox("El usuario no puede contener el carácter '@'","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 148;BA.debugLine="Msgbox(\"The user cannot contan the character '@'";
anywheresoftware.b4a.keywords.Common.Msgbox("The user cannot contan the character '@'","Error",mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 152;BA.debugLine="Dim strPassword As String = txtPassword.Text.Trim";
_strpassword = mostCurrent._txtpassword.getText().trim();
 //BA.debugLineNum = 154;BA.debugLine="If strPassword.Contains(\"@\") Then";
if (_strpassword.contains("@")) { 
 //BA.debugLineNum = 155;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 156;BA.debugLine="Msgbox(\"El usuario no puede contener el carácte";
anywheresoftware.b4a.keywords.Common.Msgbox("El usuario no puede contener el carácter '@'","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 158;BA.debugLine="Msgbox(\"The password cannot contan the characte";
anywheresoftware.b4a.keywords.Common.Msgbox("The password cannot contan the character '@'","Error",mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 163;BA.debugLine="If strPassword = \"\" Then";
if ((_strpassword).equals("")) { 
 //BA.debugLineNum = 164;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 165;BA.debugLine="Msgbox(\"Ingrese contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Ingrese contraseña","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 167;BA.debugLine="Msgbox(\"Enter password\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Enter password","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 170;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 172;BA.debugLine="Dim strPassword2 As String = txtPassword2.Text.Tr";
_strpassword2 = mostCurrent._txtpassword2.getText().trim();
 //BA.debugLineNum = 173;BA.debugLine="If strPassword2 = \"\" Then";
if ((_strpassword2).equals("")) { 
 //BA.debugLineNum = 174;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 175;BA.debugLine="Msgbox(\"Confirme su contraseña\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Confirme su contraseña","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 177;BA.debugLine="Msgbox(\"Confirm your password\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Confirm your password","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 179;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 181;BA.debugLine="If strPassword <> strPassword2 Then";
if ((_strpassword).equals(_strpassword2) == false) { 
 //BA.debugLineNum = 182;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 183;BA.debugLine="Msgbox(\"Las contraseñas no coinciden\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Las contraseñas no coinciden","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 185;BA.debugLine="Msgbox(\"The passwords don't match\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("The passwords don't match","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 187;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 190;BA.debugLine="Dim strEmail As String = txtEmail.Text.Trim";
_stremail = mostCurrent._txtemail.getText().trim();
 //BA.debugLineNum = 191;BA.debugLine="If strEmail = \"\" Then";
if ((_stremail).equals("")) { 
 //BA.debugLineNum = 192;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 193;BA.debugLine="Msgbox(\"Ingrese email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Ingrese email","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 195;BA.debugLine="Msgbox(\"Enter email\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Enter email","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 198;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 200;BA.debugLine="If Validate_Email(strEmail) = False Then";
if (_validate_email(_stremail)==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 201;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 202;BA.debugLine="Msgbox(\"Formato de email incorrecto\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Formato de email incorrecto","Error",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 204;BA.debugLine="Msgbox(\"Email format incorrect\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox("Email format incorrect","Error",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 206;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 211;BA.debugLine="If Spinner1.SelectedItem = \"Escuela\" Or Spinner1.";
if ((mostCurrent._spinner1.getSelectedItem()).equals("Escuela") || (mostCurrent._spinner1.getSelectedItem()).equals("School")) { 
 //BA.debugLineNum = 212;BA.debugLine="If txtOrg.Text = \"\" Then";
if ((mostCurrent._txtorg.getText()).equals("")) { 
 //BA.debugLineNum = 213;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 214;BA.debugLine="Msgbox(\"Indicaste que eras un alumno de un est";
anywheresoftware.b4a.keywords.Common.Msgbox("Indicaste que eras un alumno de un establecimiento educativo, escribe el nombre de tu escuela en el cuadro 'Establecimiento'","Ingresa tu escuela",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 216;BA.debugLine="Msgbox(\"You indicated that you were part of a";
anywheresoftware.b4a.keywords.Common.Msgbox("You indicated that you were part of a school, write its name in the 'School' field","Enter school",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 218;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 223;BA.debugLine="Dim txtLegales As String";
_txtlegales = "";
 //BA.debugLineNum = 224;BA.debugLine="Dim lbllegales As Label";
_lbllegales = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 225;BA.debugLine="Dim scrLegales As ScrollView";
_scrlegales = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 226;BA.debugLine="Dim btnAcepto As Button";
_btnacepto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 227;BA.debugLine="Dim btnNoAcepto As Button";
_btnnoacepto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 228;BA.debugLine="Dim fondogris As ImageView";
_fondogris = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 230;BA.debugLine="fondogris.Initialize(\"fondogris\")";
_fondogris.Initialize(mostCurrent.activityBA,"fondogris");
 //BA.debugLineNum = 231;BA.debugLine="fondogris.Color = Colors.ARGB(150, 64,64,64)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 233;BA.debugLine="lbllegales.Initialize(\"\")";
_lbllegales.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 234;BA.debugLine="scrLegales.Initialize(100%y)";
_scrlegales.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 235;BA.debugLine="btnAcepto.Initialize(\"btnAcepto\")";
_btnacepto.Initialize(mostCurrent.activityBA,"btnAcepto");
 //BA.debugLineNum = 236;BA.debugLine="btnNoAcepto.Initialize(\"btnNoAcepto\")";
_btnnoacepto.Initialize(mostCurrent.activityBA,"btnNoAcepto");
 //BA.debugLineNum = 237;BA.debugLine="scrLegales.Color = Colors.White";
_scrlegales.setColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 238;BA.debugLine="lbllegales.TextColor = Colors.Black";
_lbllegales.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 239;BA.debugLine="btnAcepto.Color = Colors.ARGB(255,160,221,202)";
_btnacepto.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (160),(int) (221),(int) (202)));
 //BA.debugLineNum = 240;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 241;BA.debugLine="btnAcepto.Text = \"Acepto los términos\"";
_btnacepto.setText((Object)("Acepto los términos"));
 //BA.debugLineNum = 242;BA.debugLine="btnNoAcepto.Text = \"No acepto los términos\"";
_btnnoacepto.setText((Object)("No acepto los términos"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 244;BA.debugLine="btnAcepto.Text = \"I accept the terms\"";
_btnacepto.setText((Object)("I accept the terms"));
 //BA.debugLineNum = 245;BA.debugLine="btnNoAcepto.Text = \"I don't accept the terms\"";
_btnnoacepto.setText((Object)("I don't accept the terms"));
 };
 //BA.debugLineNum = 248;BA.debugLine="btnAcepto.TextColor = Colors.Black";
_btnacepto.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 249;BA.debugLine="btnNoAcepto.Color = Colors.ARGB(150,160,221,202)";
_btnnoacepto.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (160),(int) (221),(int) (202)));
 //BA.debugLineNum = 250;BA.debugLine="btnNoAcepto.TextColor = Colors.Black";
_btnnoacepto.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 252;BA.debugLine="Activity.AddView(fondogris, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 253;BA.debugLine="Activity.AddView(scrLegales, 10%x, 10%y, 80%x, 60";
mostCurrent._activity.AddView((android.view.View)(_scrlegales.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (60),mostCurrent.activityBA));
 //BA.debugLineNum = 254;BA.debugLine="Activity.AddView(btnAcepto, 10%x, 70%y, 80%x, 10%";
mostCurrent._activity.AddView((android.view.View)(_btnacepto.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (70),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 255;BA.debugLine="Activity.AddView(btnNoAcepto, 10%x, 80%y, 80%x, 1";
mostCurrent._activity.AddView((android.view.View)(_btnnoacepto.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (10),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 256;BA.debugLine="scrLegales.Panel.AddView(lbllegales,10,0,scrLegal";
_scrlegales.getPanel().AddView((android.view.View)(_lbllegales.getObject()),(int) (10),(int) (0),(int) (_scrlegales.getWidth()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 258;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 259;BA.debugLine="txtLegales = \"LEGALES\" & CRLF & CRLF";
_txtlegales = "LEGALES"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 260;BA.debugLine="txtLegales = txtLegales & \"AppEAR es un proyecto";
_txtlegales = _txtlegales+"AppEAR es un proyecto de ciencia ciudadana diseñado y ejecutado por personal dependiente del Consejo Nacional de Investigaciones Científicas y Técnicas (CONICET) y de la Universidad Nacional de La Plata (UNLP), con sede en la unidad ejecutora ILPLA-Instituto de Limnología “Dr. Raúl A. Ringuelet” (de aquí en más, ILPLA)."+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 261;BA.debugLine="txtLegales = txtLegales & \"El proyecto depende d";
_txtlegales = _txtlegales+"El proyecto depende del envío de datos por parte de la ciudadanía sobre los diversos componentes del hábitat acuático."+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 262;BA.debugLine="txtLegales = txtLegales & \"Los ciudadanos partic";
_txtlegales = _txtlegales+"Los ciudadanos participan compartiendo datos a través de la aplicación para dispositivos móviles. Para ello, será necesario ser mayor de edad o participar bajo la supervisión de un adulto y estar de acuerdo con la política de privacidad de AppEAR, así como con los siguientes términos:"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 263;BA.debugLine="txtLegales = txtLegales & \"1. Al compartir tus d";
_txtlegales = _txtlegales+"1. Al compartir tus datos con el proyecto AppEAR, concedes a los miembros del equipo de investigación una licencia perpetua, libre de royaltys, no-exclusiva y sub-licenciable, para: usar, reproducir, modificar, adaptar, publicar, traducir, crear trabajos derivados, distribuir y ejercer todos los derechos de autor y de publicidad en todo el mundo respecto a tu contribución y/o incorporar tu contribución en otros trabajos en cualquier medio conocido actualmente o desarrollado en el futuro."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 264;BA.debugLine="txtLegales = txtLegales & \"2. Garantizas que tie";
_txtlegales = _txtlegales+"2. Garantizas que tienes el derecho de poner a disposición del equipo de investigación cualquier dato que compartas para los fines especificados en el punto 1 anterior y que estos datos no son difamatorios, ni infringen ninguna ley."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 265;BA.debugLine="txtLegales = txtLegales & \"3. Aceptas indemnizar";
_txtlegales = _txtlegales+"3. Aceptas indemnizar a los miembros del proyecto AppEAR y colaboradores de cualquier carga legal y/o monetaria y de cualquier otro gasto en el que pudiera incurrir como resultado de tu incumplimiento de la garantía mencionada en el punto 2."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 266;BA.debugLine="txtLegales = txtLegales & \"4. Renuncias a cualqu";
_txtlegales = _txtlegales+"4. Renuncias a cualquier derecho moral en relación a tu contribución para los fines especificados en el punto 1."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 267;BA.debugLine="txtLegales = txtLegales & \"5. La información dis";
_txtlegales = _txtlegales+"5. La información disponible del proyecto AppEAR, tanto en la app como en su sitio web, no podrá ser utilizada por terceros de ninguna manera como evidencia científica o técnica sin previa autorización de los directores del proyecto AppEAR."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 268;BA.debugLine="txtLegales = txtLegales & \"6. Los miembros del p";
_txtlegales = _txtlegales+"6. Los miembros del proyecto AppEAR, o las instituciones de las cuales dependen (CONICET, UNLP) no son responsables de los datos aportados por los usuarios desde sus dispositivos móviles."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 270;BA.debugLine="txtLegales = txtLegales & \"POLITICA DE PRIVACIDA";
_txtlegales = _txtlegales+"POLITICA DE PRIVACIDAD"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 271;BA.debugLine="txtLegales = txtLegales & \"El proyecto AppEAR re";
_txtlegales = _txtlegales+"El proyecto AppEAR recoge datos compartidos por los voluntarios y los pone a disposición de terceras partes y del público general. Con el fin de proteger la privacidad de sus voluntarios, se evita la exposición de información que pueda identificar de manera individual a una persona física determinada. Así, no se muestran nombres, direcciones, contraseñas o cualquier otra información personal de los voluntarios en el sitio web de AppEAR."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 272;BA.debugLine="txtLegales = txtLegales & \"Se recogen las ubicac";
_txtlegales = _txtlegales+"Se recogen las ubicaciones exactas de los voluntarios durante el envío, pero esta información se vincula solamente al informe. Los voluntarios pueden incluir notas y fotografías en sus informes, pero es su responsabilidad no incluir en estas notas y fotografías cualquier información que deseen mantener en privado o cualquier información que viole los derechos de privacidad de los demás o sea incompatible con el acuerdo del usuario de AppEAR"+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 273;BA.debugLine="lbllegales.Text = txtLegales";
_lbllegales.setText((Object)(_txtlegales));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 275;BA.debugLine="txtLegales = \"TERMS\" & CRLF & CRLF";
_txtlegales = "TERMS"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 276;BA.debugLine="txtLegales = txtLegales & \"AppEAR es un proyecto";
_txtlegales = _txtlegales+"AppEAR es un proyecto de ciencia ciudadana diseñado y ejecutado por personal dependiente del Consejo Nacional de Investigaciones Científicas y Técnicas (CONICET) y de la Universidad Nacional de La Plata (UNLP), con sede en la unidad ejecutora ILPLA-Instituto de Limnología “Dr. Raúl A. Ringuelet” (de aquí en más, ILPLA)."+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 277;BA.debugLine="txtLegales = txtLegales & \"El proyecto depende d";
_txtlegales = _txtlegales+"El proyecto depende del envío de datos por parte de la ciudadanía sobre los diversos componentes del hábitat acuático."+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 278;BA.debugLine="txtLegales = txtLegales & \"Los ciudadanos partic";
_txtlegales = _txtlegales+"Los ciudadanos participan compartiendo datos a través de la aplicación para dispositivos móviles. Para ello, será necesario ser mayor de edad o participar bajo la supervisión de un adulto y estar de acuerdo con la política de privacidad de AppEAR, así como con los siguientes términos:"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 279;BA.debugLine="txtLegales = txtLegales & \"1. Al compartir tus d";
_txtlegales = _txtlegales+"1. Al compartir tus datos con el proyecto AppEAR, concedes a los miembros del equipo de investigación una licencia perpetua, libre de royaltys, no-exclusiva y sub-licenciable, para: usar, reproducir, modificar, adaptar, publicar, traducir, crear trabajos derivados, distribuir y ejercer todos los derechos de autor y de publicidad en todo el mundo respecto a tu contribución y/o incorporar tu contribución en otros trabajos en cualquier medio conocido actualmente o desarrollado en el futuro."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 280;BA.debugLine="txtLegales = txtLegales & \"2. Garantizas que tie";
_txtlegales = _txtlegales+"2. Garantizas que tienes el derecho de poner a disposición del equipo de investigación cualquier dato que compartas para los fines especificados en el punto 1 anterior y que estos datos no son difamatorios, ni infringen ninguna ley."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 281;BA.debugLine="txtLegales = txtLegales & \"3. Aceptas indemnizar";
_txtlegales = _txtlegales+"3. Aceptas indemnizar a los miembros del proyecto AppEAR y colaboradores de cualquier carga legal y/o monetaria y de cualquier otro gasto en el que pudiera incurrir como resultado de tu incumplimiento de la garantía mencionada en el punto 2."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 282;BA.debugLine="txtLegales = txtLegales & \"4. Renuncias a cualqu";
_txtlegales = _txtlegales+"4. Renuncias a cualquier derecho moral en relación a tu contribución para los fines especificados en el punto 1."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 283;BA.debugLine="txtLegales = txtLegales & \"5. La información dis";
_txtlegales = _txtlegales+"5. La información disponible del proyecto AppEAR, tanto en la app como en su sitio web, no podrá ser utilizada por terceros de ninguna manera como evidencia científica o técnica sin previa autorización de los directores del proyecto AppEAR."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 284;BA.debugLine="txtLegales = txtLegales & \"6. Los miembros del p";
_txtlegales = _txtlegales+"6. Los miembros del proyecto AppEAR, o las instituciones de las cuales dependen (CONICET, UNLP) no son responsables de los datos aportados por los usuarios desde sus dispositivos móviles."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 286;BA.debugLine="txtLegales = txtLegales & \"POLITICA DE PRIVACIDA";
_txtlegales = _txtlegales+"POLITICA DE PRIVACIDAD"+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 287;BA.debugLine="txtLegales = txtLegales & \"El proyecto AppEAR re";
_txtlegales = _txtlegales+"El proyecto AppEAR recoge datos compartidos por los voluntarios y los pone a disposición de terceras partes y del público general. Con el fin de proteger la privacidad de sus voluntarios, se evita la exposición de información que pueda identificar de manera individual a una persona física determinada. Así, no se muestran nombres, direcciones, contraseñas o cualquier otra información personal de los voluntarios en el sitio web de AppEAR."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 288;BA.debugLine="txtLegales = txtLegales & \"Se recogen las ubicac";
_txtlegales = _txtlegales+"Se recogen las ubicaciones exactas de los voluntarios durante el envío, pero esta información se vincula solamente al informe. Los voluntarios pueden incluir notas y fotografías en sus informes, pero es su responsabilidad no incluir en estas notas y fotografías cualquier información que deseen mantener en privado o cualquier información que viole los derechos de privacidad de los demás o sea incompatible con el acuerdo del usuario de AppEAR"+anywheresoftware.b4a.keywords.Common.CRLF;
 //BA.debugLineNum = 289;BA.debugLine="lbllegales.Text = txtLegales";
_lbllegales.setText((Object)(_txtlegales));
 };
 //BA.debugLineNum = 292;BA.debugLine="Dim ht As Float";
_ht = 0f;
 //BA.debugLineNum = 293;BA.debugLine="Dim StrUtil As StringUtils";
_strutil = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 295;BA.debugLine="ht = StrUtil.MeasureMultilineTextHeight(lbllegale";
_ht = (float) (_strutil.MeasureMultilineTextHeight((android.widget.TextView)(_lbllegales.getObject()),_txtlegales));
 //BA.debugLineNum = 296;BA.debugLine="scrLegales.Panel.Height = ht		' set the ScrollVie";
_scrlegales.getPanel().setHeight((int) (_ht));
 //BA.debugLineNum = 297;BA.debugLine="lbllegales.Height = ht				' set the Label height";
_lbllegales.setHeight((int) (_ht));
 //BA.debugLineNum = 299;BA.debugLine="scrLegales.ScrollPosition = 0";
_scrlegales.setScrollPosition((int) (0));
 //BA.debugLineNum = 301;BA.debugLine="End Sub";
return "";
}
public static String  _fondogris_click() throws Exception{
 //BA.debugLineNum = 385;BA.debugLine="Sub fondogris_Click";
 //BA.debugLineNum = 386;BA.debugLine="Return(False)";
if (true) return BA.ObjectToString((anywheresoftware.b4a.keywords.Common.False));
 //BA.debugLineNum = 387;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Dim txtUserID As EditText";
mostCurrent._txtuserid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Dim txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Dim txtFullName As EditText";
mostCurrent._txtfullname = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim txtLocation As EditText";
mostCurrent._txtlocation = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Dim txtEmail As EditText";
mostCurrent._txtemail = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim spinnerMap As Map";
mostCurrent._spinnermap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 18;BA.debugLine="Private txtOrg As EditText";
mostCurrent._txtorg = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private txtPassword2 As EditText";
mostCurrent._txtpassword2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblOrg As Label";
mostCurrent._lblorg = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblMessage As Label";
mostCurrent._lblmessage = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lblUserID As Label";
mostCurrent._lbluserid = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblFullName As Label";
mostCurrent._lblfullname = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblPassword As Label";
mostCurrent._lblpassword = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lblPassword2 As Label";
mostCurrent._lblpassword2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private lblLocation As Label";
mostCurrent._lbllocation = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnRegister As Button";
mostCurrent._btnregister = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
String _res = "";
String _action = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 303;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 304;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 305;BA.debugLine="If Job.Success Then";
if (_job._success) { 
 //BA.debugLineNum = 306;BA.debugLine="Dim res As String, action As String";
_res = "";
_action = "";
 //BA.debugLineNum = 307;BA.debugLine="res = Job.GetString";
_res = _job._getstring();
 //BA.debugLineNum = 308;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 309;BA.debugLine="parser.Initialize(res)";
_parser.Initialize(_res);
 //BA.debugLineNum = 311;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"Register")) {
case 0: {
 //BA.debugLineNum = 313;BA.debugLine="action = parser.NextValue";
_action = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 314;BA.debugLine="If action = \"Mail\" Then";
if ((_action).equals("Mail")) { 
 //BA.debugLineNum = 315;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 316;BA.debugLine="Msgbox(\"Usuario registrado!\", \"Felicitacione";
anywheresoftware.b4a.keywords.Common.Msgbox("Usuario registrado!","Felicitaciones",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 318;BA.debugLine="Msgbox(\"User registered!\", \"Congratulations\"";
anywheresoftware.b4a.keywords.Common.Msgbox("User registered!","Congratulations",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 320;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 321;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else if((_action).equals("MailInUse")) { 
 //BA.debugLineNum = 323;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 324;BA.debugLine="Msgbox(\"El usuario '\" & txtUserID.Text & \"'";
anywheresoftware.b4a.keywords.Common.Msgbox("El usuario '"+mostCurrent._txtuserid.getText()+"' o el email ("+mostCurrent._txtemail.getText()+") ya están en uso","Registro",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 326;BA.debugLine="Msgbox(\"The user '\" & txtUserID.Text & \"' or";
anywheresoftware.b4a.keywords.Common.Msgbox("The user '"+mostCurrent._txtuserid.getText()+"' or the email ("+mostCurrent._txtemail.getText()+") are already in use","Register",mostCurrent.activityBA);
 };
 }else {
 //BA.debugLineNum = 330;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 331;BA.debugLine="Msgbox(\"El servidor no devolvió los valores";
anywheresoftware.b4a.keywords.Common.Msgbox("El servidor no devolvió los valores esperados."+_job._errormessage,"Registro",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 333;BA.debugLine="Msgbox(\"Server fail\" & Job.ErrorMessage, \"Re";
anywheresoftware.b4a.keywords.Common.Msgbox("Server fail"+_job._errormessage,"Register",mostCurrent.activityBA);
 };
 };
 break; }
}
;
 }else {
 //BA.debugLineNum = 340;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Error: "+_job._errormessage,anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 342;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 343;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
String _id = "";
 //BA.debugLineNum = 106;BA.debugLine="Sub Spinner1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 107;BA.debugLine="Dim id As String";
_id = "";
 //BA.debugLineNum = 108;BA.debugLine="id = spinnerMap.Get(Value)";
_id = BA.ObjectToString(mostCurrent._spinnermap.Get(_value));
 //BA.debugLineNum = 110;BA.debugLine="If Value = \"Escuela\" Then";
if ((_value).equals((Object)("Escuela"))) { 
 //BA.debugLineNum = 111;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 112;BA.debugLine="Msgbox(\"Has seleccionado que eres parte de un e";
anywheresoftware.b4a.keywords.Common.Msgbox("Has seleccionado que eres parte de un establecimiento educativo, escribe el nombre de tu escuela en el cuadro 'Establecimiento'","Escuela",mostCurrent.activityBA);
 //BA.debugLineNum = 113;BA.debugLine="lblOrg.Text = \"Escuela\"";
mostCurrent._lblorg.setText((Object)("Escuela"));
 //BA.debugLineNum = 114;BA.debugLine="txtOrg.Hint = \"Escribe el nombre de tu escuela";
mostCurrent._txtorg.setHint("Escribe el nombre de tu escuela o establecimiento");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 116;BA.debugLine="Msgbox(\"You have selected that you are part of";
anywheresoftware.b4a.keywords.Common.Msgbox("You have selected that you are part of an educational establishment. Write the name of your school","School",mostCurrent.activityBA);
 //BA.debugLineNum = 117;BA.debugLine="lblOrg.Text = \"School\"";
mostCurrent._lblorg.setText((Object)("School"));
 //BA.debugLineNum = 118;BA.debugLine="txtOrg.Hint = \"Write the name of your school\"";
mostCurrent._txtorg.setHint("Write the name of your school");
 };
 }else {
 //BA.debugLineNum = 122;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 123;BA.debugLine="lblOrg.Text = \"Organización\"";
mostCurrent._lblorg.setText((Object)("Organización"));
 //BA.debugLineNum = 124;BA.debugLine="txtOrg.Hint = \"Su organización (opcional)\"";
mostCurrent._txtorg.setHint("Su organización (opcional)");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 126;BA.debugLine="lblOrg.Text = \"Company\"";
mostCurrent._lblorg.setText((Object)("Company"));
 //BA.debugLineNum = 127;BA.debugLine="txtOrg.Hint = \"Your company's name (optional)\"";
mostCurrent._txtorg.setHint("Your company's name (optional)");
 };
 };
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 85;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 86;BA.debugLine="lblFullName.Text = \"Full name\"";
mostCurrent._lblfullname.setText((Object)("Full name"));
 //BA.debugLineNum = 87;BA.debugLine="lblUserID.Text = \"Username\"";
mostCurrent._lbluserid.setText((Object)("Username"));
 //BA.debugLineNum = 88;BA.debugLine="lblPassword.Text = \"Password\"";
mostCurrent._lblpassword.setText((Object)("Password"));
 //BA.debugLineNum = 89;BA.debugLine="lblPassword2.Text = \"Confirm password\"";
mostCurrent._lblpassword2.setText((Object)("Confirm password"));
 //BA.debugLineNum = 90;BA.debugLine="lblLocation.Text = \"Location\"";
mostCurrent._lbllocation.setText((Object)("Location"));
 //BA.debugLineNum = 91;BA.debugLine="lblOrg.Text = \"Organization\"";
mostCurrent._lblorg.setText((Object)("Organization"));
 //BA.debugLineNum = 92;BA.debugLine="Label1.Text = \"Profile\"";
mostCurrent._label1.setText((Object)("Profile"));
 //BA.debugLineNum = 93;BA.debugLine="txtUserID.Hint = \"Enter a username\"";
mostCurrent._txtuserid.setHint("Enter a username");
 //BA.debugLineNum = 94;BA.debugLine="txtFullName.hint = \"Enter your full name\"";
mostCurrent._txtfullname.setHint("Enter your full name");
 //BA.debugLineNum = 95;BA.debugLine="txtPassword.Hint = \"Enter a password\"";
mostCurrent._txtpassword.setHint("Enter a password");
 //BA.debugLineNum = 96;BA.debugLine="txtPassword2.Hint = \"Confirm your password\"";
mostCurrent._txtpassword2.setHint("Confirm your password");
 //BA.debugLineNum = 97;BA.debugLine="txtEmail.Hint = \"Enter your email\"";
mostCurrent._txtemail.setHint("Enter your email");
 //BA.debugLineNum = 98;BA.debugLine="txtLocation.Hint = \"Your location (optional)\"";
mostCurrent._txtlocation.setHint("Your location (optional)");
 //BA.debugLineNum = 99;BA.debugLine="txtOrg.Hint = \"Your organization (optional)\"";
mostCurrent._txtorg.setHint("Your organization (optional)");
 //BA.debugLineNum = 100;BA.debugLine="btnRegister.Text = \"Register\"";
mostCurrent._btnregister.setText((Object)("Register"));
 };
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public static boolean  _validate_email(String _emailaddress) throws Exception{
anywheresoftware.b4a.keywords.Regex.MatcherWrapper _matchemail = null;
 //BA.debugLineNum = 345;BA.debugLine="Sub Validate_Email(EmailAddress As String) As Bool";
 //BA.debugLineNum = 346;BA.debugLine="Dim MatchEmail As Matcher = Regex.Matcher(\"^(?";
_matchemail = new anywheresoftware.b4a.keywords.Regex.MatcherWrapper();
_matchemail = anywheresoftware.b4a.keywords.Common.Regex.Matcher("^(?i)[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])$",_emailaddress);
 //BA.debugLineNum = 348;BA.debugLine="If MatchEmail.Find = True Then";
if (_matchemail.Find()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 350;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 353;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 355;BA.debugLine="End Sub";
return false;
}
}
