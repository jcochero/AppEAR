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

public class frmtiporio extends Activity implements B4AActivity{
	public static frmtiporio mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmtiporio");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmtiporio).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmtiporio");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmtiporio", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmtiporio) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmtiporio) Resume **");
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
		return frmtiporio.class;
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
        BA.LogInfo("** Activity (frmtiporio) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmtiporio) Resume **");
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
public anywheresoftware.b4a.objects.EditTextWrapper _txtnombrerio = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlnombrerio = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spntiporio = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltiporio = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoknombre = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnnombrenose = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnoktipo = null;
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
public ilpla.appear.register _register = null;
public ilpla.appear.frmlocalizacion _frmlocalizacion = null;
public ilpla.appear.game_memory _game_memory = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 22;BA.debugLine="Activity.LoadLayout(\"frmTipoRio\")";
mostCurrent._activity.LoadLayout("frmTipoRio",mostCurrent.activityBA);
 //BA.debugLineNum = 23;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 24;BA.debugLine="pnlNombreRio.Visible = True";
mostCurrent._pnlnombrerio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 25;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 26;BA.debugLine="spnTipoRio.AddAll(Array As String (\"\", \"Río de m";
mostCurrent._spntiporio.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"","Río de montaña","Río de llanura","Laguna","Estuario"}));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 28;BA.debugLine="spnTipoRio.AddAll(Array As String (\"\", \"Mountain";
mostCurrent._spntiporio.AddAll(anywheresoftware.b4a.keywords.Common.ArrayToList(new String[]{"","Mountain river","Lowland river","Lake","Estuary"}));
 };
 //BA.debugLineNum = 30;BA.debugLine="ImageView1.Bitmap = Null";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 34;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 35;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 36;BA.debugLine="If Msgbox2(\"Volver al inicio?\", \"SALIR\", \"";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Volver al inicio?","SALIR","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 37;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 38;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 41;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _btnnombrenose_click() throws Exception{
 //BA.debugLineNum = 156;BA.debugLine="Sub btnNombreNose_Click";
 //BA.debugLineNum = 157;BA.debugLine="Main.nombrerio = \"SN\"";
mostCurrent._main._nombrerio = "SN";
 //BA.debugLineNum = 158;BA.debugLine="Main.k.HideKeyboard(Activity)";
mostCurrent._main._k.HideKeyboard(mostCurrent._activity);
 //BA.debugLineNum = 159;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 160;BA.debugLine="If spnTipoRio.SelectedItem = \"Río de montaña\" Th";
if ((mostCurrent._spntiporio.getSelectedItem()).equals("Río de montaña")) { 
 //BA.debugLineNum = 161;BA.debugLine="Main.tiporio = \"Montana\"";
mostCurrent._main._tiporio = "Montana";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Río de llanura")) { 
 //BA.debugLineNum = 163;BA.debugLine="Main.tiporio = \"Llanura\"";
mostCurrent._main._tiporio = "Llanura";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Laguna")) { 
 //BA.debugLineNum = 165;BA.debugLine="Main.tiporio = \"Laguna\"";
mostCurrent._main._tiporio = "Laguna";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Estuario")) { 
 //BA.debugLineNum = 167;BA.debugLine="Main.tiporio = \"Estuario\"";
mostCurrent._main._tiporio = "Estuario";
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 170;BA.debugLine="If spnTipoRio.SelectedItem = \"Mountain river\" Th";
if ((mostCurrent._spntiporio.getSelectedItem()).equals("Mountain river")) { 
 //BA.debugLineNum = 171;BA.debugLine="Main.tiporio = \"Montana\"";
mostCurrent._main._tiporio = "Montana";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Lowland river")) { 
 //BA.debugLineNum = 173;BA.debugLine="Main.tiporio = \"Llanura\"";
mostCurrent._main._tiporio = "Llanura";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Lake")) { 
 //BA.debugLineNum = 175;BA.debugLine="Main.tiporio = \"Laguna\"";
mostCurrent._main._tiporio = "Laguna";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Estuary")) { 
 //BA.debugLineNum = 177;BA.debugLine="Main.tiporio = \"Estuario\"";
mostCurrent._main._tiporio = "Estuario";
 };
 };
 //BA.debugLineNum = 182;BA.debugLine="frmLocalizacion.formorigen = \"TipoRio\"";
mostCurrent._frmlocalizacion._formorigen = "TipoRio";
 //BA.debugLineNum = 183;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 184;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 185;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 187;BA.debugLine="End Sub";
return "";
}
public static String  _btnoknombre_click() throws Exception{
 //BA.debugLineNum = 116;BA.debugLine="Sub btnOkNombre_Click";
 //BA.debugLineNum = 117;BA.debugLine="If txtNombreRio.Text <> \"\" Then";
if ((mostCurrent._txtnombrerio.getText()).equals("") == false) { 
 //BA.debugLineNum = 118;BA.debugLine="Main.nombrerio = txtNombreRio.Text";
mostCurrent._main._nombrerio = mostCurrent._txtnombrerio.getText();
 //BA.debugLineNum = 119;BA.debugLine="Main.k.HideKeyboard(Activity)";
mostCurrent._main._k.HideKeyboard(mostCurrent._activity);
 //BA.debugLineNum = 120;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 121;BA.debugLine="If spnTipoRio.SelectedItem = \"Río de montaña\" T";
if ((mostCurrent._spntiporio.getSelectedItem()).equals("Río de montaña")) { 
 //BA.debugLineNum = 122;BA.debugLine="Main.tiporio = \"Montana\"";
mostCurrent._main._tiporio = "Montana";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Río de llanura")) { 
 //BA.debugLineNum = 124;BA.debugLine="Main.tiporio = \"Llanura\"";
mostCurrent._main._tiporio = "Llanura";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Laguna")) { 
 //BA.debugLineNum = 126;BA.debugLine="Main.tiporio = \"Laguna\"";
mostCurrent._main._tiporio = "Laguna";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Estuario")) { 
 //BA.debugLineNum = 128;BA.debugLine="Main.tiporio = \"Estuario\"";
mostCurrent._main._tiporio = "Estuario";
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 131;BA.debugLine="If spnTipoRio.SelectedItem = \"Mountain river\" T";
if ((mostCurrent._spntiporio.getSelectedItem()).equals("Mountain river")) { 
 //BA.debugLineNum = 132;BA.debugLine="Main.tiporio = \"Montana\"";
mostCurrent._main._tiporio = "Montana";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Lowland river")) { 
 //BA.debugLineNum = 134;BA.debugLine="Main.tiporio = \"Llanura\"";
mostCurrent._main._tiporio = "Llanura";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Lake")) { 
 //BA.debugLineNum = 136;BA.debugLine="Main.tiporio = \"Laguna\"";
mostCurrent._main._tiporio = "Laguna";
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Estuary")) { 
 //BA.debugLineNum = 138;BA.debugLine="Main.tiporio = \"Estuario\"";
mostCurrent._main._tiporio = "Estuario";
 };
 };
 //BA.debugLineNum = 142;BA.debugLine="frmLocalizacion.formorigen = \"TipoRio\"";
mostCurrent._frmlocalizacion._formorigen = "TipoRio";
 //BA.debugLineNum = 143;BA.debugLine="StartActivity(frmLocalizacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmlocalizacion.getObject()));
 //BA.debugLineNum = 144;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 145;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 }else {
 //BA.debugLineNum = 147;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 148;BA.debugLine="ToastMessageShow(\"Ingrese el nombre del lugar\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Ingrese el nombre del lugar",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 150;BA.debugLine="ToastMessageShow(\"Enter a name for the place\",";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Enter a name for the place",anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 154;BA.debugLine="End Sub";
return "";
}
public static String  _btnoktipo_click() throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Sub btnOkTipo_Click";
 //BA.debugLineNum = 69;BA.debugLine="If spnTipoRio.SelectedItem = \"\" Then";
if ((mostCurrent._spntiporio.getSelectedItem()).equals("")) { 
 //BA.debugLineNum = 70;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 71;BA.debugLine="Msgbox(\"Elije un tipo de ambiente\", \"¿Qué ambie";
anywheresoftware.b4a.keywords.Common.Msgbox("Elije un tipo de ambiente","¿Qué ambiente es?",mostCurrent.activityBA);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 73;BA.debugLine="Msgbox(\"Choose the type of environment\", \"What";
anywheresoftware.b4a.keywords.Common.Msgbox("Choose the type of environment","What environment?",mostCurrent.activityBA);
 };
 //BA.debugLineNum = 75;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 77;BA.debugLine="btnOkNombre.Visible = True";
mostCurrent._btnoknombre.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 78;BA.debugLine="btnNombreNose.Visible = True";
mostCurrent._btnnombrenose.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 79;BA.debugLine="txtNombreRio.Visible = True";
mostCurrent._txtnombrerio.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 80;BA.debugLine="btnOkTipo.Visible = False";
mostCurrent._btnoktipo.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 81;BA.debugLine="spnTipoRio.Visible = False";
mostCurrent._spntiporio.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 82;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 83;BA.debugLine="lblTipoRio.Text = \"¿Cómo se llama el lugar?\"";
mostCurrent._lbltiporio.setText((Object)("¿Cómo se llama el lugar?"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 85;BA.debugLine="lblTipoRio.Text = \"What is the name of the plac";
mostCurrent._lbltiporio.setText((Object)("What is the name of the place?"));
 };
 };
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 11;BA.debugLine="Private txtNombreRio As EditText";
mostCurrent._txtnombrerio = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 12;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private pnlNombreRio As Panel";
mostCurrent._pnlnombrerio = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private spnTipoRio As Spinner";
mostCurrent._spntiporio = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblTipoRio As Label";
mostCurrent._lbltiporio = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private btnOkNombre As Button";
mostCurrent._btnoknombre = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private btnNombreNose As Button";
mostCurrent._btnnombrenose = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnOkTipo As Button";
mostCurrent._btnoktipo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="End Sub";
return "";
}
public static String  _spntiporio_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 90;BA.debugLine="Sub spnTipoRio_ItemClick (Position As Int, Value A";
 //BA.debugLineNum = 91;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 92;BA.debugLine="If spnTipoRio.SelectedItem = \"Río de montaña\" Th";
if ((mostCurrent._spntiporio.getSelectedItem()).equals("Río de montaña")) { 
 //BA.debugLineNum = 93;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tiporiomontana.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Río de llanura")) { 
 //BA.debugLineNum = 95;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tiporiollanura.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Laguna")) { 
 //BA.debugLineNum = 97;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tiporiolaguna.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Estuario")) { 
 //BA.debugLineNum = 99;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"tiporioestuario.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 102;BA.debugLine="If spnTipoRio.SelectedItem = \"Mountain river\" Th";
if ((mostCurrent._spntiporio.getSelectedItem()).equals("Mountain river")) { 
 //BA.debugLineNum = 103;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-tiporiomontana.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Lowland river")) { 
 //BA.debugLineNum = 105;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-tiporiollanura.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Lake")) { 
 //BA.debugLineNum = 107;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-tiporiolaguna.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 }else if((mostCurrent._spntiporio.getSelectedItem()).equals("Estuary")) { 
 //BA.debugLineNum = 109;BA.debugLine="ImageView1.Bitmap = LoadBitmapSample(File.DirAs";
mostCurrent._imageview1.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"en-tiporioestuario.png",mostCurrent._activity.getWidth(),(int) (mostCurrent._activity.getHeight()/(double)2)).getObject()));
 };
 };
 //BA.debugLineNum = 113;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 61;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 62;BA.debugLine="lblTipoRio.Text = \"What kind of water body is it";
mostCurrent._lbltiporio.setText((Object)("What kind of water body is it?"));
 //BA.debugLineNum = 63;BA.debugLine="btnOkNombre.Text = \"Ok, that's the name\"";
mostCurrent._btnoknombre.setText((Object)("Ok, that's the name"));
 //BA.debugLineNum = 64;BA.debugLine="btnNombreNose.Text = \"I don't know what it's cal";
mostCurrent._btnnombrenose.setText((Object)("I don't know what it's called"));
 };
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
}
