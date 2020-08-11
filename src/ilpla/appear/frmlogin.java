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

public class frmlogin extends Activity implements B4AActivity{
	public static frmlogin mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmlogin");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmlogin).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmlogin");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmlogin", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmlogin) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmlogin) Resume **");
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
		return frmlogin.class;
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
            BA.LogInfo("** Activity (frmlogin) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (frmlogin) Pause event (activity is not paused). **");
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
            frmlogin mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (frmlogin) Resume **");
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
public static anywheresoftware.b4a.phone.Phone _k = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnregister = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtpassword = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtuserid = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlrecoverpass = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtrecoverpass = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnforgot = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.EditTextWrapper _txtemailguest = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnlogin = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnguest = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butcancel = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnsendforgot = null;
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
anywheresoftware.b4a.objects.collections.Map _usrmap = null;
 //BA.debugLineNum = 36;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 37;BA.debugLine="Activity.LoadLayout(\"layLogin\")";
mostCurrent._activity.LoadLayout("layLogin",mostCurrent.activityBA);
 //BA.debugLineNum = 38;BA.debugLine="Dim usrMap As Map";
_usrmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 39;BA.debugLine="usrMap.Initialize";
_usrmap.Initialize();
 //BA.debugLineNum = 40;BA.debugLine="usrMap = DBUtils.ExecuteMap(Starter.sqlDB, \"SELEC";
_usrmap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE lastUser = 'si'",(String[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 41;BA.debugLine="If usrMap = Null Or usrMap.IsInitialized = False";
if (_usrmap== null || _usrmap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 }else {
 //BA.debugLineNum = 44;BA.debugLine="txtUserID.Text = usrMap.Get(\"username\")";
mostCurrent._txtuserid.setText(BA.ObjectToCharSequence(_usrmap.Get((Object)("username"))));
 //BA.debugLineNum = 45;BA.debugLine="txtPassword.Text = usrMap.Get(\"pass\")";
mostCurrent._txtpassword.setText(BA.ObjectToCharSequence(_usrmap.Get((Object)("pass"))));
 };
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 60;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 61;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 62;BA.debugLine="If Msgbox2(\"Cerrar AppEAR?\", \"SALIR\", \"Si\", \"\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Cerrar AppEAR?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 63;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 64;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 65;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 }else {
 //BA.debugLineNum = 67;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 70;BA.debugLine="If Msgbox2(\"Close AppEAR?\", \"Exit\", \"Yes\", \"\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Close AppEAR?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 72;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 73;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 74;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 }else {
 //BA.debugLineNum = 76;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 51;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _btnforgot_click() throws Exception{
 //BA.debugLineNum = 483;BA.debugLine="Sub btnForgot_Click";
 //BA.debugLineNum = 484;BA.debugLine="pnlRecoverPass.Visible = True";
mostCurrent._pnlrecoverpass.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 485;BA.debugLine="pnlRecoverPass.BringToFront";
mostCurrent._pnlrecoverpass.BringToFront();
 //BA.debugLineNum = 486;BA.debugLine="End Sub";
return "";
}
public static String  _btnguest_click() throws Exception{
String _msg = "";
 //BA.debugLineNum = 566;BA.debugLine="Sub btnGuest_Click";
 //BA.debugLineNum = 567;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 568;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 569;BA.debugLine="msg = utilidades.Mensaje(\"Invitado\", \"MsgIcon.pn";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Invitado","MsgIcon.png","Ingresar como invitado","Los revisores no podrán ayudarte a confirmar si has encontrado una vinchuca. Deseas continuar igual?","Si, quiero continuar","","Me registraré",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 571;BA.debugLine="msg = utilidades.Mensaje(\"Guest\", \"MsgIcon.png\",";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Guest","MsgIcon.png","Log in as guest","Reviewers might not be able to tell you if you found a kissing bug!","Yes, continue","","Ok I'll sign up",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 574;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 575;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 576;BA.debugLine="Main.username = \"guest\"";
mostCurrent._main._username /*String*/  = "guest";
 //BA.debugLineNum = 577;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 }else if((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.NEGATIVE))) { 
 //BA.debugLineNum = 579;BA.debugLine="StartActivity(register)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._register.getObject()));
 };
 //BA.debugLineNum = 581;BA.debugLine="End Sub";
return "";
}
public static String  _btnlogin_click() throws Exception{
String _strpassword = "";
 //BA.debugLineNum = 121;BA.debugLine="Sub btnLogin_Click";
 //BA.debugLineNum = 122;BA.debugLine="k.HideKeyboard(Activity)";
_k.HideKeyboard(mostCurrent._activity);
 //BA.debugLineNum = 125;BA.debugLine="Main.strUserID = txtUserID.Text.Trim";
mostCurrent._main._struserid /*String*/  = mostCurrent._txtuserid.getText().trim();
 //BA.debugLineNum = 126;BA.debugLine="If Main.strUserID = \"\" Then";
if ((mostCurrent._main._struserid /*String*/ ).equals("")) { 
 //BA.debugLineNum = 127;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 128;BA.debugLine="Msgbox(\"Ingrese su usuario\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese su usuario"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 130;BA.debugLine="Msgbox(\"Enter a username\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Enter a username"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 132;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 134;BA.debugLine="Dim strPassword As String = txtPassword.Text.Trim";
_strpassword = mostCurrent._txtpassword.getText().trim();
 //BA.debugLineNum = 135;BA.debugLine="If strPassword = \"\" Then";
if ((_strpassword).equals("")) { 
 //BA.debugLineNum = 136;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 137;BA.debugLine="Msgbox(\"Ingrese su clave\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Ingrese su clave"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 //BA.debugLineNum = 138;BA.debugLine="Return";
if (true) return "";
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 140;BA.debugLine="Msgbox(\"Enter your password\", \"Error\")";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Enter your password"),BA.ObjectToCharSequence("Error"),mostCurrent.activityBA);
 //BA.debugLineNum = 141;BA.debugLine="Return";
if (true) return "";
 };
 };
 //BA.debugLineNum = 147;BA.debugLine="Login(Main.strUserID, strPassword)";
_login(mostCurrent._main._struserid /*String*/ ,_strpassword);
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _btnregister_click() throws Exception{
 //BA.debugLineNum = 473;BA.debugLine="Sub btnRegister_Click";
 //BA.debugLineNum = 474;BA.debugLine="StartActivity(\"register\")";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)("register"));
 //BA.debugLineNum = 475;BA.debugLine="End Sub";
return "";
}
public static String  _btnsendforgot_click() throws Exception{
String _recoveremail = "";
 //BA.debugLineNum = 487;BA.debugLine="Sub btnSendForgot_Click";
 //BA.debugLineNum = 488;BA.debugLine="Dim recoveremail As String";
_recoveremail = "";
 //BA.debugLineNum = 489;BA.debugLine="recoveremail = txtRecoverPass.Text";
_recoveremail = mostCurrent._txtrecoverpass.getText();
 //BA.debugLineNum = 490;BA.debugLine="If recoveremail = \"\" Then";
if ((_recoveremail).equals("")) { 
 //BA.debugLineNum = 491;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 492;BA.debugLine="ToastMessageShow(\"Ingrese su email\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingrese su email"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 494;BA.debugLine="ToastMessageShow(\"Enter your email address\", Fa";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Enter your email address"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 496;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 498;BA.debugLine="Password_Reset(recoveremail)";
_password_reset(_recoveremail);
 };
 //BA.debugLineNum = 500;BA.debugLine="End Sub";
return "";
}
public static String  _btnsigningoogle_click() throws Exception{
 //BA.debugLineNum = 405;BA.debugLine="Sub btnSignInGoogle_Click";
 //BA.debugLineNum = 408;BA.debugLine="CheckForGooglePlayServices";
_checkforgoogleplayservices();
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
return "";
}
public static String  _butcancel_click() throws Exception{
 //BA.debugLineNum = 554;BA.debugLine="Sub butCancel_Click";
 //BA.debugLineNum = 555;BA.debugLine="pnlRecoverPass.Visible = False";
mostCurrent._pnlrecoverpass.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 556;BA.debugLine="pnlRecoverPass.SendToBack";
mostCurrent._pnlrecoverpass.SendToBack();
 //BA.debugLineNum = 557;BA.debugLine="End Sub";
return "";
}
public static boolean  _checkforgoogleplayservices() throws Exception{
anywheresoftware.b4j.object.JavaObject _googleapiavailablity = null;
anywheresoftware.b4j.object.JavaObject _context = null;
 //BA.debugLineNum = 412;BA.debugLine="Sub CheckForGooglePlayServices As Boolean";
 //BA.debugLineNum = 413;BA.debugLine="Dim GoogleApiAvailablity As JavaObject";
_googleapiavailablity = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 414;BA.debugLine="GoogleApiAvailablity = GoogleApiAvailablity.Initi";
_googleapiavailablity = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_googleapiavailablity.InitializeStatic("com.google.android.gms.common.GoogleApiAvailability").RunMethod("getInstance",(Object[])(anywheresoftware.b4a.keywords.Common.Null))));
 //BA.debugLineNum = 415;BA.debugLine="Dim context As JavaObject";
_context = new anywheresoftware.b4j.object.JavaObject();
 //BA.debugLineNum = 416;BA.debugLine="context.InitializeContext";
_context.InitializeContext(processBA);
 //BA.debugLineNum = 417;BA.debugLine="If GoogleApiAvailablity.RunMethod(\"isGooglePlaySe";
if ((_googleapiavailablity.RunMethod("isGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())})).equals((Object)(0)) == false) { 
 //BA.debugLineNum = 418;BA.debugLine="GoogleApiAvailablity.RunMethod(\"makeGooglePlaySe";
_googleapiavailablity.RunMethod("makeGooglePlayServicesAvailable",new Object[]{(Object)(_context.getObject())});
 //BA.debugLineNum = 420;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 421;BA.debugLine="ToastMessageShow(\"Google Play services no se en";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Google Play services no se encuentra en su dispositivo. Intente registrarse por otra vía"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 423;BA.debugLine="ToastMessageShow(\"Google Play services not foun";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Google Play services not found in your device. Try to sign up with your email address"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 425;BA.debugLine="Return False";
if (true) return anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 431;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
 //BA.debugLineNum = 432;BA.debugLine="fondoblanco.Color = Colors.ARGB(190, 255,255,255)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (190),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 433;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 436;BA.debugLine="LoginGoogle(True)";
_logingoogle(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 438;BA.debugLine="End Sub";
return false;
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private btnRegister As Button";
mostCurrent._btnregister = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private txtPassword As EditText";
mostCurrent._txtpassword = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private txtUserID As EditText";
mostCurrent._txtuserid = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private pnlRecoverPass As Panel";
mostCurrent._pnlrecoverpass = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private txtRecoverPass As EditText";
mostCurrent._txtrecoverpass = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private btnForgot As Button";
mostCurrent._btnforgot = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private txtEmailGuest As EditText";
mostCurrent._txtemailguest = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnLogin As Button";
mostCurrent._btnlogin = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private btnGuest As Button";
mostCurrent._btnguest = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private butCancel As Button";
mostCurrent._butcancel = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private btnSendForgot As Button";
mostCurrent._btnsendforgot = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public static String  _login(String _usr,String _pss) throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 158;BA.debugLine="Sub Login (usr As String, pss As String)";
 //BA.debugLineNum = 160;BA.debugLine="Log(\"Starting LOGIN\")";
anywheresoftware.b4a.keywords.Common.LogImpl("738535170","Starting LOGIN",0);
 //BA.debugLineNum = 162;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 163;BA.debugLine="dd.url = Main.serverPath & \"/connect3/signin2.ph";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect3/signin2.php?user_id="+_usr+"&password="+_pss;
 //BA.debugLineNum = 164;BA.debugLine="dd.EventName = \"Login\"";
_dd.EventName /*String*/  = "Login";
 //BA.debugLineNum = 165;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmlogin.getObject();
 //BA.debugLineNum = 166;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 168;BA.debugLine="End Sub";
return "";
}
public static String  _login_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _userexists = null;
anywheresoftware.b4a.objects.collections.List _newuser = null;
anywheresoftware.b4a.objects.collections.Map _m = null;
anywheresoftware.b4a.objects.collections.Map _mapreset = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _msj = "";
 //BA.debugLineNum = 176;BA.debugLine="Sub Login_Complete(Job As HttpJob)";
 //BA.debugLineNum = 177;BA.debugLine="Log(\"Conexion LOGIN: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("738666241","Conexion LOGIN: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 178;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 179;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 180;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 181;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 183;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 184;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 185;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 187;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 188;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 189;BA.debugLine="ToastMessageShow(\"Usuario o contraseña incorre";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Usuario o contraseña incorrectos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 191;BA.debugLine="ToastMessageShow(\"Wrong user or password\", Fal";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Wrong user or password"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("Mail")) { 
 //BA.debugLineNum = 194;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 195;BA.debugLine="ToastMessageShow(\"Registrado usuario por Googl";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Registrado usuario por Google..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 197;BA.debugLine="ToastMessageShow(\"Google-registered user...\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Google-registered user..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 199;BA.debugLine="Log(\"Usuario registrado por Google\")";
anywheresoftware.b4a.keywords.Common.LogImpl("738666263","Usuario registrado por Google",0);
 //BA.debugLineNum = 204;BA.debugLine="Dim userExists As Map";
_userexists = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 205;BA.debugLine="userExists.Initialize";
_userexists.Initialize();
 //BA.debugLineNum = 206;BA.debugLine="userExists = DBUtils.ExecuteMap(Starter.sqlDB,";
_userexists = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE username=?",new String[]{mostCurrent._main._username /*String*/ });
 //BA.debugLineNum = 208;BA.debugLine="If userExists = Null Or userExists.IsInitialize";
if (_userexists== null || _userexists.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 210;BA.debugLine="Dim newUser As List";
_newuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 211;BA.debugLine="newUser.Initialize";
_newuser.Initialize();
 //BA.debugLineNum = 212;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 213;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 214;BA.debugLine="m.Put(\"username\", Main.username)";
_m.Put((Object)("username"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 215;BA.debugLine="m.Put(\"userID\", Main.strUserID)";
_m.Put((Object)("userID"),(Object)(mostCurrent._main._struserid /*String*/ ));
 //BA.debugLineNum = 216;BA.debugLine="m.Put(\"userLocation\", Main.strUserLocation )";
_m.Put((Object)("userLocation"),(Object)(mostCurrent._main._struserlocation /*String*/ ));
 //BA.debugLineNum = 217;BA.debugLine="m.Put(\"userEmail\", Main.strUserEmail)";
_m.Put((Object)("userEmail"),(Object)(mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 218;BA.debugLine="m.Put(\"userFullName\", Main.strUserFullName)";
_m.Put((Object)("userFullName"),(Object)(mostCurrent._main._struserfullname /*String*/ ));
 //BA.debugLineNum = 219;BA.debugLine="m.Put(\"userOrg\", Main.strUserOrg)";
_m.Put((Object)("userOrg"),(Object)(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 220;BA.debugLine="m.Put(\"usergroup\", Main.strUserGroup)";
_m.Put((Object)("usergroup"),(Object)(mostCurrent._main._strusergroup /*String*/ ));
 //BA.debugLineNum = 221;BA.debugLine="m.Put(\"userTipoUsuario\", Main.strUserTipoUsuar";
_m.Put((Object)("userTipoUsuario"),(Object)(mostCurrent._main._strusertipousuario /*String*/ ));
 //BA.debugLineNum = 222;BA.debugLine="m.Put(\"pass\", Main.pass)";
_m.Put((Object)("pass"),(Object)(mostCurrent._main._pass /*String*/ ));
 //BA.debugLineNum = 223;BA.debugLine="m.Put(\"firstuse\", \"True\")";
_m.Put((Object)("firstuse"),(Object)("True"));
 //BA.debugLineNum = 224;BA.debugLine="m.Put(\"lastuser\", \"si\")";
_m.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 225;BA.debugLine="m.Put(\"puntostotales\", Main.puntostotales)";
_m.Put((Object)("puntostotales"),(Object)(mostCurrent._main._puntostotales /*String*/ ));
 //BA.debugLineNum = 226;BA.debugLine="m.Put(\"puntosnumfotos\", Main.puntosnumfotos)";
_m.Put((Object)("puntosnumfotos"),(Object)(mostCurrent._main._puntosnumfotos /*String*/ ));
 //BA.debugLineNum = 227;BA.debugLine="m.Put(\"puntosnumevals\", Main.puntosnumevals)";
_m.Put((Object)("puntosnumevals"),(Object)(mostCurrent._main._puntosnumevals /*String*/ ));
 //BA.debugLineNum = 228;BA.debugLine="m.Put(\"numriollanura\", Main.numriollanura)";
_m.Put((Object)("numriollanura"),(Object)(mostCurrent._main._numriollanura /*String*/ ));
 //BA.debugLineNum = 229;BA.debugLine="m.Put(\"numriomontana\", Main.numriomontana)";
_m.Put((Object)("numriomontana"),(Object)(mostCurrent._main._numriomontana /*String*/ ));
 //BA.debugLineNum = 230;BA.debugLine="m.Put(\"numlaguna\", Main.numlaguna)";
_m.Put((Object)("numlaguna"),(Object)(mostCurrent._main._numlaguna /*String*/ ));
 //BA.debugLineNum = 231;BA.debugLine="m.Put(\"numestuario\", Main.numestuario)";
_m.Put((Object)("numestuario"),(Object)(mostCurrent._main._numestuario /*String*/ ));
 //BA.debugLineNum = 232;BA.debugLine="m.Put(\"numshares\", Main.numshares)";
_m.Put((Object)("numshares"),(Object)(mostCurrent._main._numshares /*String*/ ));
 //BA.debugLineNum = 233;BA.debugLine="m.Put(\"pass\", Main.pass)";
_m.Put((Object)("pass"),(Object)(mostCurrent._main._pass /*String*/ ));
 //BA.debugLineNum = 234;BA.debugLine="m.Put(\"lang\", Main.userlang)";
_m.Put((Object)("lang"),(Object)(mostCurrent._main._userlang /*String*/ ));
 //BA.debugLineNum = 235;BA.debugLine="m.Put(\"evalsok\", Main.evalsok)";
_m.Put((Object)("evalsok"),(Object)(mostCurrent._main._evalsok /*String*/ ));
 //BA.debugLineNum = 237;BA.debugLine="newUser.Add(m)";
_newuser.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 238;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"userconfig\",";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig",_newuser);
 }else {
 //BA.debugLineNum = 243;BA.debugLine="Dim MapReset As Map";
_mapreset = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 244;BA.debugLine="MapReset.Initialize";
_mapreset.Initialize();
 //BA.debugLineNum = 245;BA.debugLine="MapReset.Put(\"lastuser\", \"si\")";
_mapreset.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 246;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("no"),_mapreset);
 //BA.debugLineNum = 248;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 249;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 250;BA.debugLine="Map1.Put(\"username\", Main.username)";
_map1.Put((Object)("username"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 251;BA.debugLine="Main.strUserName = Main.username";
mostCurrent._main._strusername /*String*/  = mostCurrent._main._username /*String*/ ;
 //BA.debugLineNum = 252;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("si"),_map1);
 //BA.debugLineNum = 254;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userLocation",(Object)(mostCurrent._main._struserlocation /*String*/ ),_map1);
 //BA.debugLineNum = 255;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userEmail",(Object)(mostCurrent._main._struseremail /*String*/ ),_map1);
 //BA.debugLineNum = 256;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userFullName",(Object)(mostCurrent._main._struserfullname /*String*/ ),_map1);
 //BA.debugLineNum = 257;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userOrg",(Object)(mostCurrent._main._struserorg /*String*/ ),_map1);
 //BA.debugLineNum = 258;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","UserTipoUsuario",(Object)(mostCurrent._main._strusertipousuario /*String*/ ),_map1);
 //BA.debugLineNum = 259;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","usergroup",(Object)(mostCurrent._main._struserorg /*String*/ ),_map1);
 };
 //BA.debugLineNum = 262;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 263;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 264;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 268;BA.debugLine="ToastMessageShow(\"Error\", False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.False);
 }else if((_act).equals("Login OK")) { 
 //BA.debugLineNum = 271;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 272;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 274;BA.debugLine="Main.strUserID = newpunto.Get(\"id\")";
mostCurrent._main._struserid /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("id")));
 //BA.debugLineNum = 275;BA.debugLine="Main.strUserName = newpunto.Get(\"username\")";
mostCurrent._main._strusername /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("username")));
 //BA.debugLineNum = 276;BA.debugLine="Main.strUserFullName = newpunto.Get(\"user_fulln";
mostCurrent._main._struserfullname /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("user_fullname")));
 //BA.debugLineNum = 277;BA.debugLine="Main.strUserLocation = newpunto.Get(\"location\")";
mostCurrent._main._struserlocation /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("location")));
 //BA.debugLineNum = 278;BA.debugLine="Main.strUserEmail = newpunto.Get(\"email\")";
mostCurrent._main._struseremail /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("email")));
 //BA.debugLineNum = 279;BA.debugLine="Main.strUserOrg = newpunto.Get(\"org\")";
mostCurrent._main._struserorg /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("org")));
 //BA.debugLineNum = 280;BA.debugLine="Main.strUserTipoUsuario = newpunto.Get(\"tipousu";
mostCurrent._main._strusertipousuario /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("tipousuario")));
 //BA.debugLineNum = 281;BA.debugLine="Main.strUserGroup = newpunto.Get(\"group\")";
mostCurrent._main._strusergroup /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("group")));
 //BA.debugLineNum = 282;BA.debugLine="Main.pass = newpunto.Get(\"password\")";
mostCurrent._main._pass /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("password")));
 //BA.debugLineNum = 283;BA.debugLine="Main.username = Main.strUserName";
mostCurrent._main._username /*String*/  = mostCurrent._main._strusername /*String*/ ;
 //BA.debugLineNum = 284;BA.debugLine="Main.puntostotales = newpunto.Get(\"puntostotale";
mostCurrent._main._puntostotales /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("puntostotales")));
 //BA.debugLineNum = 285;BA.debugLine="Main.puntosnumfotos = newpunto.Get(\"puntosfotos";
mostCurrent._main._puntosnumfotos /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("puntosfotos")));
 //BA.debugLineNum = 286;BA.debugLine="Main.puntosnumevals = newpunto.Get(\"puntosevals";
mostCurrent._main._puntosnumevals /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("puntosevals")));
 //BA.debugLineNum = 287;BA.debugLine="Main.numriollanura = newpunto.Get(\"numriollanur";
mostCurrent._main._numriollanura /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numriollanura")));
 //BA.debugLineNum = 288;BA.debugLine="Main.numriomontana = newpunto.Get(\"numriomontan";
mostCurrent._main._numriomontana /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numriomontana")));
 //BA.debugLineNum = 289;BA.debugLine="Main.numlaguna = newpunto.Get(\"numlaguna\")";
mostCurrent._main._numlaguna /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numlaguna")));
 //BA.debugLineNum = 290;BA.debugLine="Main.numestuario = newpunto.Get(\"numestuario\")";
mostCurrent._main._numestuario /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numestuario")));
 //BA.debugLineNum = 291;BA.debugLine="Main.numshares = newpunto.Get(\"numshares\")";
mostCurrent._main._numshares /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("numshares")));
 //BA.debugLineNum = 292;BA.debugLine="Main.evalsok = newpunto.Get(\"evalsok\")";
mostCurrent._main._evalsok /*String*/  = BA.ObjectToString(_newpunto.Get((Object)("evalsok")));
 //BA.debugLineNum = 294;BA.debugLine="btnForgot.Visible = False";
mostCurrent._btnforgot.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 295;BA.debugLine="Main.modooffline = False";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 298;BA.debugLine="Dim msj As String = newpunto.Get(\"msjprivado\")";
_msj = BA.ObjectToString(_newpunto.Get((Object)("msjprivado")));
 //BA.debugLineNum = 299;BA.debugLine="If msj = Main.msjprivadouser Then";
if ((_msj).equals(mostCurrent._main._msjprivadouser /*String*/ )) { 
 //BA.debugLineNum = 300;BA.debugLine="Main.msjprivadoleido = True";
mostCurrent._main._msjprivadoleido /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 }else {
 //BA.debugLineNum = 302;BA.debugLine="Main.msjprivadoleido = False";
mostCurrent._main._msjprivadoleido /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 };
 //BA.debugLineNum = 304;BA.debugLine="If msj <> Null And msj <> \"\" Then";
if (_msj!= null && (_msj).equals("") == false) { 
 //BA.debugLineNum = 305;BA.debugLine="Main.msjprivadouser = msj";
mostCurrent._main._msjprivadouser /*String*/  = _msj;
 }else {
 //BA.debugLineNum = 307;BA.debugLine="Main.msjprivadouser = \"None\"";
mostCurrent._main._msjprivadouser /*String*/  = "None";
 };
 //BA.debugLineNum = 312;BA.debugLine="Dim userExists As Map";
_userexists = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 313;BA.debugLine="userExists.Initialize";
_userexists.Initialize();
 //BA.debugLineNum = 314;BA.debugLine="userExists = DBUtils.ExecuteMap(Starter.sqlDB,";
_userexists = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE username=?",new String[]{mostCurrent._main._username /*String*/ });
 //BA.debugLineNum = 315;BA.debugLine="If userExists = Null Or userExists.IsInitialize";
if (_userexists== null || _userexists.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 317;BA.debugLine="Dim newUser As List";
_newuser = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 318;BA.debugLine="newUser.Initialize";
_newuser.Initialize();
 //BA.debugLineNum = 319;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 320;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 322;BA.debugLine="m.Put(\"username\", Main.username)";
_m.Put((Object)("username"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 323;BA.debugLine="m.Put(\"userID\", Main.strUserID)";
_m.Put((Object)("userID"),(Object)(mostCurrent._main._struserid /*String*/ ));
 //BA.debugLineNum = 324;BA.debugLine="m.Put(\"userLocation\", Main.strUserLocation )";
_m.Put((Object)("userLocation"),(Object)(mostCurrent._main._struserlocation /*String*/ ));
 //BA.debugLineNum = 325;BA.debugLine="m.Put(\"userEmail\", Main.strUserEmail)";
_m.Put((Object)("userEmail"),(Object)(mostCurrent._main._struseremail /*String*/ ));
 //BA.debugLineNum = 326;BA.debugLine="m.Put(\"userFullName\", Main.strUserFullName)";
_m.Put((Object)("userFullName"),(Object)(mostCurrent._main._struserfullname /*String*/ ));
 //BA.debugLineNum = 327;BA.debugLine="m.Put(\"userOrg\", Main.strUserOrg)";
_m.Put((Object)("userOrg"),(Object)(mostCurrent._main._struserorg /*String*/ ));
 //BA.debugLineNum = 328;BA.debugLine="m.Put(\"usergroup\", Main.strUserGroup)";
_m.Put((Object)("usergroup"),(Object)(mostCurrent._main._strusergroup /*String*/ ));
 //BA.debugLineNum = 329;BA.debugLine="m.Put(\"userTipoUsuario\", Main.strUserTipoUsuar";
_m.Put((Object)("userTipoUsuario"),(Object)(mostCurrent._main._strusertipousuario /*String*/ ));
 //BA.debugLineNum = 330;BA.debugLine="m.Put(\"pass\", Main.pass)";
_m.Put((Object)("pass"),(Object)(mostCurrent._main._pass /*String*/ ));
 //BA.debugLineNum = 331;BA.debugLine="m.Put(\"firstuse\", \"True\")";
_m.Put((Object)("firstuse"),(Object)("True"));
 //BA.debugLineNum = 332;BA.debugLine="m.Put(\"lastuser\", \"si\")";
_m.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 333;BA.debugLine="m.Put(\"puntostotales\", Main.puntostotales)";
_m.Put((Object)("puntostotales"),(Object)(mostCurrent._main._puntostotales /*String*/ ));
 //BA.debugLineNum = 334;BA.debugLine="m.Put(\"puntosnumfotos\", Main.puntosnumfotos)";
_m.Put((Object)("puntosnumfotos"),(Object)(mostCurrent._main._puntosnumfotos /*String*/ ));
 //BA.debugLineNum = 335;BA.debugLine="m.Put(\"puntosnumevals\", Main.puntosnumevals)";
_m.Put((Object)("puntosnumevals"),(Object)(mostCurrent._main._puntosnumevals /*String*/ ));
 //BA.debugLineNum = 336;BA.debugLine="m.Put(\"numriollanura\", Main.numriollanura)";
_m.Put((Object)("numriollanura"),(Object)(mostCurrent._main._numriollanura /*String*/ ));
 //BA.debugLineNum = 337;BA.debugLine="m.Put(\"numriomontana\", Main.numriomontana)";
_m.Put((Object)("numriomontana"),(Object)(mostCurrent._main._numriomontana /*String*/ ));
 //BA.debugLineNum = 338;BA.debugLine="m.Put(\"numlaguna\", Main.numlaguna)";
_m.Put((Object)("numlaguna"),(Object)(mostCurrent._main._numlaguna /*String*/ ));
 //BA.debugLineNum = 339;BA.debugLine="m.Put(\"numestuario\", Main.numestuario)";
_m.Put((Object)("numestuario"),(Object)(mostCurrent._main._numestuario /*String*/ ));
 //BA.debugLineNum = 340;BA.debugLine="m.Put(\"numshares\", Main.numshares)";
_m.Put((Object)("numshares"),(Object)(mostCurrent._main._numshares /*String*/ ));
 //BA.debugLineNum = 341;BA.debugLine="m.Put(\"pass\", Main.pass)";
_m.Put((Object)("pass"),(Object)(mostCurrent._main._pass /*String*/ ));
 //BA.debugLineNum = 342;BA.debugLine="m.Put(\"lang\", Main.userlang)";
_m.Put((Object)("lang"),(Object)(mostCurrent._main._userlang /*String*/ ));
 //BA.debugLineNum = 343;BA.debugLine="m.Put(\"evalsok\", Main.evalsok)";
_m.Put((Object)("evalsok"),(Object)(mostCurrent._main._evalsok /*String*/ ));
 //BA.debugLineNum = 345;BA.debugLine="newUser.Add(m)";
_newuser.Add((Object)(_m.getObject()));
 //BA.debugLineNum = 346;BA.debugLine="DBUtils.InsertMaps(Starter.sqlDB,\"userconfig\",";
mostCurrent._dbutils._insertmaps /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig",_newuser);
 }else {
 //BA.debugLineNum = 351;BA.debugLine="Dim MapReset As Map";
_mapreset = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 352;BA.debugLine="MapReset.Initialize";
_mapreset.Initialize();
 //BA.debugLineNum = 353;BA.debugLine="MapReset.Put(\"lastuser\", \"si\")";
_mapreset.Put((Object)("lastuser"),(Object)("si"));
 //BA.debugLineNum = 354;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("no"),_mapreset);
 //BA.debugLineNum = 358;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 359;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 360;BA.debugLine="Map1.Put(\"username\", Main.username)";
_map1.Put((Object)("username"),(Object)(mostCurrent._main._username /*String*/ ));
 //BA.debugLineNum = 361;BA.debugLine="Main.strUserName = Main.username";
mostCurrent._main._strusername /*String*/  = mostCurrent._main._username /*String*/ ;
 //BA.debugLineNum = 362;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","lastuser",(Object)("si"),_map1);
 //BA.debugLineNum = 364;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userLocation",(Object)(mostCurrent._main._struserlocation /*String*/ ),_map1);
 //BA.debugLineNum = 365;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userEmail",(Object)(mostCurrent._main._struseremail /*String*/ ),_map1);
 //BA.debugLineNum = 366;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userFullName",(Object)(mostCurrent._main._struserfullname /*String*/ ),_map1);
 //BA.debugLineNum = 367;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","userOrg",(Object)(mostCurrent._main._struserorg /*String*/ ),_map1);
 //BA.debugLineNum = 368;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","UserTipoUsuario",(Object)(mostCurrent._main._strusertipousuario /*String*/ ),_map1);
 //BA.debugLineNum = 369;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","puntostotales",(Object)(mostCurrent._main._puntostotales /*String*/ ),_map1);
 //BA.debugLineNum = 370;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","puntosnumfotos",(Object)(mostCurrent._main._puntosnumfotos /*String*/ ),_map1);
 //BA.debugLineNum = 371;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","puntosnumevals",(Object)(mostCurrent._main._puntosnumevals /*String*/ ),_map1);
 //BA.debugLineNum = 372;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","numriollanura",(Object)(mostCurrent._main._numriollanura /*String*/ ),_map1);
 //BA.debugLineNum = 373;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","numriomontana",(Object)(mostCurrent._main._numriomontana /*String*/ ),_map1);
 //BA.debugLineNum = 374;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","numlaguna",(Object)(mostCurrent._main._numlaguna /*String*/ ),_map1);
 //BA.debugLineNum = 375;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","numestuario",(Object)(mostCurrent._main._numestuario /*String*/ ),_map1);
 //BA.debugLineNum = 376;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","numshares",(Object)(mostCurrent._main._numshares /*String*/ ),_map1);
 //BA.debugLineNum = 377;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","pass",(Object)(mostCurrent._main._pass /*String*/ ),_map1);
 //BA.debugLineNum = 378;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"userconfi";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"userconfig","evalsok",(Object)(mostCurrent._main._evalsok /*String*/ ),_map1);
 };
 //BA.debugLineNum = 381;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 382;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 383;BA.debugLine="StartActivity(Form_Main)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_main.getObject()));
 };
 }else {
 //BA.debugLineNum = 387;BA.debugLine="Log(\"login not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("738666451","login not ok",0);
 //BA.debugLineNum = 388;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 389;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 391;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 395;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 397;BA.debugLine="End Sub";
return "";
}
public static String  _logingoogle(boolean _closesession) throws Exception{
 //BA.debugLineNum = 439;BA.debugLine="Sub LoginGoogle(closesession As Boolean)";
 //BA.debugLineNum = 440;BA.debugLine="If closesession = True Then";
if (_closesession==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 441;BA.debugLine="If Starter.auth.CurrentUser.IsInitialized Then S";
if (mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .getCurrentUser().IsInitialized()) { 
mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .SignOutFromGoogle();};
 //BA.debugLineNum = 442;BA.debugLine="Log(\"Signin in with google...\")";
anywheresoftware.b4a.keywords.Common.LogImpl("738862851","Signin in with google...",0);
 //BA.debugLineNum = 443;BA.debugLine="Starter.auth.SignInWithGoogle";
mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .SignInWithGoogle(processBA);
 //BA.debugLineNum = 444;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 445;BA.debugLine="ToastMessageShow(\"Ingresando con su cuenta de G";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingresando con su cuenta de Google"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 447;BA.debugLine="ToastMessageShow(\"Login in with your Google acc";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Login in with your Google account"),anywheresoftware.b4a.keywords.Common.True);
 };
 }else {
 //BA.debugLineNum = 451;BA.debugLine="Log(\"Signin in with google...\")";
anywheresoftware.b4a.keywords.Common.LogImpl("738862860","Signin in with google...",0);
 //BA.debugLineNum = 452;BA.debugLine="Starter.auth.SignInWithGoogle";
mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .SignInWithGoogle(processBA);
 //BA.debugLineNum = 453;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 454;BA.debugLine="ToastMessageShow(\"Ingresando con su cuenta de G";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Ingresando con su cuenta de Google"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 456;BA.debugLine="ToastMessageShow(\"Signin in with Googl\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Signin in with Googl"),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 460;BA.debugLine="End Sub";
return "";
}
public static String  _loginok_firebase() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 169;BA.debugLine="Sub LoginOk_Firebase";
 //BA.debugLineNum = 170;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 171;BA.debugLine="dd.url = Main.serverPath & \"/connect3/signinFireb";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect3/signinFirebase.php?user_id="+mostCurrent._main._struserid /*String*/ +"&FullName="+mostCurrent._main._struserfullname /*String*/ +"&deviceID="+mostCurrent._main._deviceid /*String*/ ;
 //BA.debugLineNum = 172;BA.debugLine="dd.EventName = \"Login\"";
_dd.EventName /*String*/  = "Login";
 //BA.debugLineNum = 173;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmlogin.getObject();
 //BA.debugLineNum = 174;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _password_reset(String _recoveremail) throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 502;BA.debugLine="Sub Password_Reset(recoveremail As String)";
 //BA.debugLineNum = 503;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 504;BA.debugLine="dd.url = Main.serverPath & \"/connect3/recover.php";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect3/recover.php?email="+_recoveremail;
 //BA.debugLineNum = 505;BA.debugLine="dd.EventName = \"PasswordReset\"";
_dd.EventName /*String*/  = "PasswordReset";
 //BA.debugLineNum = 506;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = frmlogin.getObject();
 //BA.debugLineNum = 507;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 509;BA.debugLine="End Sub";
return "";
}
public static String  _passwordreset_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 510;BA.debugLine="Sub PasswordReset_Complete(Job As HttpJob)";
 //BA.debugLineNum = 511;BA.debugLine="Log(\"Recuperando email: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("739256065","Recuperando email: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 512;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 513;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 514;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 515;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 517;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 518;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 519;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 521;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 522;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 523;BA.debugLine="ToastMessageShow(\"Error: El email no existe\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: El email no existe"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 525;BA.debugLine="ToastMessageShow(\"Error: Email not registered\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: Email not registered"),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 529;BA.debugLine="ToastMessageShow(\"Error: Ha ocurrido un error!\"";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: Ha ocurrido un error!"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("FirebaseUser")) { 
 //BA.debugLineNum = 532;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 533;BA.debugLine="ToastMessageShow(\"Debe ingresar con su cuenta";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Debe ingresar con su cuenta de Google."),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 535;BA.debugLine="ToastMessageShow(\"You have to log in with your";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You have to log in with your Google account"),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("RecoverOK")) { 
 //BA.debugLineNum = 539;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 540;BA.debugLine="ToastMessageShow(\"Se ha enviado su clave a su";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Se ha enviado su clave a su email registrado"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 542;BA.debugLine="ToastMessageShow(\"Your password has been sent";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Your password has been sent to your registered email address"),anywheresoftware.b4a.keywords.Common.True);
 };
 };
 }else {
 //BA.debugLineNum = 547;BA.debugLine="Log(\"password reset not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("739256101","password reset not ok",0);
 //BA.debugLineNum = 548;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros s";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 551;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 553;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Dim k As Phone";
_k = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _signoutgoogle() throws Exception{
 //BA.debugLineNum = 462;BA.debugLine="Sub SignOutGoogle";
 //BA.debugLineNum = 463;BA.debugLine="Log(\"sign out from google\")";
anywheresoftware.b4a.keywords.Common.LogImpl("738928385","sign out from google",0);
 //BA.debugLineNum = 464;BA.debugLine="Starter.auth.SignOutFromGoogle";
mostCurrent._starter._auth /*anywheresoftware.b4a.objects.FirebaseAuthWrapper*/ .SignOutFromGoogle();
 //BA.debugLineNum = 465;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 89;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 90;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 91;BA.debugLine="txtEmailGuest.Hint = \"Ingrese su usuario\"";
mostCurrent._txtemailguest.setHint("Ingrese su usuario");
 //BA.debugLineNum = 92;BA.debugLine="txtPassword.Hint = \"Ingrese su clave\"";
mostCurrent._txtpassword.setHint("Ingrese su clave");
 //BA.debugLineNum = 93;BA.debugLine="btnGuest.Text = \"Ingresar sin registrarme\"";
mostCurrent._btnguest.setText(BA.ObjectToCharSequence("Ingresar sin registrarme"));
 //BA.debugLineNum = 94;BA.debugLine="btnLogin.Text = \"Ingresar\"";
mostCurrent._btnlogin.setText(BA.ObjectToCharSequence("Ingresar"));
 //BA.debugLineNum = 95;BA.debugLine="btnRegister.Text = \"Registrarse\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Registrarse"));
 //BA.debugLineNum = 96;BA.debugLine="Label2.Text = \"¿Olvidó su clave?\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("¿Olvidó su clave?"));
 //BA.debugLineNum = 97;BA.debugLine="Label3.Text = \" Ingrese su email:\"";
mostCurrent._label3.setText(BA.ObjectToCharSequence(" Ingrese su email:"));
 //BA.debugLineNum = 98;BA.debugLine="butCancel.Text = \"Cancelar\"";
mostCurrent._butcancel.setText(BA.ObjectToCharSequence("Cancelar"));
 //BA.debugLineNum = 99;BA.debugLine="btnSendForgot.Text = \"Recuperar contraseña\"";
mostCurrent._btnsendforgot.setText(BA.ObjectToCharSequence("Recuperar contraseña"));
 //BA.debugLineNum = 100;BA.debugLine="btnForgot.Text = \"Olvidé mi clave\"";
mostCurrent._btnforgot.setText(BA.ObjectToCharSequence("Olvidé mi clave"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 102;BA.debugLine="txtEmailGuest.Hint = \"Enter your username\"";
mostCurrent._txtemailguest.setHint("Enter your username");
 //BA.debugLineNum = 103;BA.debugLine="txtPassword.Hint = \"Enter your password\"";
mostCurrent._txtpassword.setHint("Enter your password");
 //BA.debugLineNum = 104;BA.debugLine="btnGuest.Text = \"Enter as guest\"";
mostCurrent._btnguest.setText(BA.ObjectToCharSequence("Enter as guest"));
 //BA.debugLineNum = 105;BA.debugLine="btnLogin.Text = \"Enter\"";
mostCurrent._btnlogin.setText(BA.ObjectToCharSequence("Enter"));
 //BA.debugLineNum = 106;BA.debugLine="btnRegister.Text = \"Register\"";
mostCurrent._btnregister.setText(BA.ObjectToCharSequence("Register"));
 //BA.debugLineNum = 107;BA.debugLine="Label2.Text = \"Forgot your password?\"";
mostCurrent._label2.setText(BA.ObjectToCharSequence("Forgot your password?"));
 //BA.debugLineNum = 108;BA.debugLine="Label3.Text = \" Enter your email:\"";
mostCurrent._label3.setText(BA.ObjectToCharSequence(" Enter your email:"));
 //BA.debugLineNum = 109;BA.debugLine="butCancel.Text = \"Cancel\"";
mostCurrent._butcancel.setText(BA.ObjectToCharSequence("Cancel"));
 //BA.debugLineNum = 110;BA.debugLine="btnSendForgot.Text = \"Recover password\"";
mostCurrent._btnsendforgot.setText(BA.ObjectToCharSequence("Recover password"));
 //BA.debugLineNum = 111;BA.debugLine="btnForgot.Text = \"I forgot my password\"";
mostCurrent._btnforgot.setText(BA.ObjectToCharSequence("I forgot my password"));
 };
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
}
