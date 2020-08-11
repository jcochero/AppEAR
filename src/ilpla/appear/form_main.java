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

public class form_main extends Activity implements B4AActivity{
	public static form_main mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.form_main");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (form_main).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.form_main");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.form_main", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (form_main) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (form_main) Resume **");
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
		return form_main.class;
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
            BA.LogInfo("** Activity (form_main) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (form_main) Pause event (activity is not paused). **");
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
            form_main mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (form_main) Resume **");
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
public static uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper _fusedlocationprovider1 = null;
public static anywheresoftware.b4a.gps.LocationWrapper _lastlocation = null;
public static boolean _isguest = false;
public static String _fullidcurrentproject = "";
public static boolean _formmainloaded = false;
public anywheresoftware.b4a.phone.Phone _p = null;
public anywheresoftware.b4a.objects.ProgressBarWrapper _pgbnivel = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllevel = null;
public anywheresoftware.b4a.objects.TabStripViewPager _tabstripmain = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblanalizar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnreportar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionaanalizar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncomunidades = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntipoambientes = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncadenas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnciclo = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmemory = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnahorcado = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncontaminacion = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbluserlevel = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlmapa = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblconoce = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblrequiereinternet = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcomofuncionaexplorar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btndetectar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
public uk.co.martinpearman.b4a.osmdroid.views.MapView _mapview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnzoomall = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay _simplelocationoverlay1 = null;
public anywheresoftware.b4a.objects.collections.List _markerexport = null;
public anywheresoftware.b4a.objects.LabelWrapper _markerinfo = null;
public anywheresoftware.b4a.objects.LabelWrapper _fondoblanco = null;
public anywheresoftware.b4a.objects.LabelWrapper _detectandolabel = null;
public anywheresoftware.b4a.objects.collections.List _markerslist = null;
public uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay _markersoverlay = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public static String _firstuse = "";
public anywheresoftware.b4a.objects.PanelWrapper _fondogris = null;
public static int _tutorialetapa = 0;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenu = null;
public ilpla.appear.b4xdrawer _drawer = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrarsesion = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnedituser = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnvermedallas = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblusername = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblregistrate = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnverperfil = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnabout = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnpoliticadatos = null;
public anywheresoftware.b4a.objects.LabelWrapper _btndatosanteriores = null;
public anywheresoftware.b4a.objects.LabelWrapper _btnmuestreos = null;
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
 //BA.debugLineNum = 106;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 108;BA.debugLine="If FirstTime Then";
if (_firsttime) { 
 //BA.debugLineNum = 109;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
 };
 //BA.debugLineNum = 112;BA.debugLine="p.SetScreenOrientation(1)";
mostCurrent._p.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 114;BA.debugLine="Drawer.Initialize(Me, \"Drawer\", Activity, 300dip)";
mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,form_main.getObject(),"Drawer",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 115;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"layMain\")";
mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 116;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"frmSideNav\")";
mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmSideNav",mostCurrent.activityBA);
 //BA.debugLineNum = 118;BA.debugLine="If Main.username = \"guest\" Then";
if ((mostCurrent._main._username /*String*/ ).equals("guest")) { 
 //BA.debugLineNum = 119;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 120;BA.debugLine="btnCerrarSesion.Text = \"Iniciar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Iniciar sesión"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 122;BA.debugLine="btnCerrarSesion.Text = \"Start session\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Start session"));
 };
 //BA.debugLineNum = 125;BA.debugLine="lblUserName.Visible = False";
mostCurrent._lblusername.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 126;BA.debugLine="lblRegistrate.Visible = True";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 127;BA.debugLine="btnEditUser.Visible = False";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.False);
 }else {
 //BA.debugLineNum = 129;BA.debugLine="lblUserName.Text = Main.strUserName";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._strusername /*String*/ ));
 //BA.debugLineNum = 130;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 131;BA.debugLine="btnCerrarSesion.Text = \"Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Cerrar sesión"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 133;BA.debugLine="btnCerrarSesion.Text = \"Close session\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Close session"));
 };
 //BA.debugLineNum = 135;BA.debugLine="lblRegistrate.Visible = False";
mostCurrent._lblregistrate.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 136;BA.debugLine="btnEditUser.Visible = True";
mostCurrent._btnedituser.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 140;BA.debugLine="If frmDatosAnteriores.notificacion = True Then";
if (mostCurrent._frmdatosanteriores._notificacion /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 141;BA.debugLine="CallSubDelayed3(frmDatosAnteriores,\"VerDetalles\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed3(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()),"VerDetalles",(Object)(mostCurrent._frmdatosanteriores._serveridnum /*String*/ ),(Object)(anywheresoftware.b4a.keywords.Common.True));
 };
 //BA.debugLineNum = 143;BA.debugLine="LoadForm_Main";
_loadform_main();
 //BA.debugLineNum = 145;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 152;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 154;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 155;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 156;BA.debugLine="If Msgbox2(\"Cerrar AppEAR?\", \"SALIR\", \"Si\", \"\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Cerrar AppEAR?"),BA.ObjectToCharSequence("SALIR"),"Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 157;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 158;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 159;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 }else {
 //BA.debugLineNum = 161;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 164;BA.debugLine="If Msgbox2(\"Close AppEAR?\", \"Exit\", \"Yes\", \"\",";
if (anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Close AppEAR?"),BA.ObjectToCharSequence("Exit"),"Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 166;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 167;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 168;BA.debugLine="ExitApplication";
anywheresoftware.b4a.keywords.Common.ExitApplication();
 }else {
 //BA.debugLineNum = 170;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 149;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 151;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 146;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 147;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return "";
}
public static String  _btnabout_click() throws Exception{
 //BA.debugLineNum = 279;BA.debugLine="Sub btnAbout_Click";
 //BA.debugLineNum = 280;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 281;BA.debugLine="End Sub";
return "";
}
public static String  _btnahorcado_click() throws Exception{
 //BA.debugLineNum = 714;BA.debugLine="Sub btnAhorcado_Click";
 //BA.debugLineNum = 715;BA.debugLine="StartActivity(Aprender_Ahorcado)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_ahorcado.getObject()));
 //BA.debugLineNum = 716;BA.debugLine="End Sub";
return "";
}
public static String  _btncadenas_click() throws Exception{
 //BA.debugLineNum = 721;BA.debugLine="Sub btnCadenas_Click";
 //BA.debugLineNum = 722;BA.debugLine="Aprender_Trofica.origen = \"menu\"";
mostCurrent._aprender_trofica._origen /*String*/  = "menu";
 //BA.debugLineNum = 723;BA.debugLine="StartActivity(Aprender_Trofica)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_trofica.getObject()));
 //BA.debugLineNum = 724;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarsesion_click() throws Exception{
String _msg = "";
 //BA.debugLineNum = 258;BA.debugLine="Sub btnCerrarSesion_Click";
 //BA.debugLineNum = 259;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 260;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 261;BA.debugLine="msg = Msgbox2(\"Desea cerrar la sesión? Ingresar";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Desea cerrar la sesión? Ingresar con otro usuario requiere de internet!"),BA.ObjectToCharSequence("Seguro?"),"Si, tengo internet","","No, no tengo internet ahora",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 263;BA.debugLine="msg = Msgbox2(\"Do you want to close the session?";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Do you want to close the session? To log in with another user you need an internet connection!"),BA.ObjectToCharSequence("Sure?"),"Yes, I have internet","","No, i'm offline now",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 };
 //BA.debugLineNum = 265;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 266;BA.debugLine="Main.strUserID = \"\"";
mostCurrent._main._struserid /*String*/  = "";
 //BA.debugLineNum = 267;BA.debugLine="Main.strUserName = \"\"";
mostCurrent._main._strusername /*String*/  = "";
 //BA.debugLineNum = 268;BA.debugLine="Main.strUserLocation = \"\"";
mostCurrent._main._struserlocation /*String*/  = "";
 //BA.debugLineNum = 269;BA.debugLine="Main.strUserEmail = \"\"";
mostCurrent._main._struseremail /*String*/  = "";
 //BA.debugLineNum = 270;BA.debugLine="Main.strUserOrg = \"\"";
mostCurrent._main._struserorg /*String*/  = "";
 //BA.debugLineNum = 271;BA.debugLine="Main.username = \"\"";
mostCurrent._main._username /*String*/  = "";
 //BA.debugLineNum = 272;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 273;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 275;BA.debugLine="CallSubDelayed(frmLogin, \"SignOutGoogle\")";
anywheresoftware.b4a.keywords.Common.CallSubDelayed(processBA,(Object)(mostCurrent._frmlogin.getObject()),"SignOutGoogle");
 //BA.debugLineNum = 276;BA.debugLine="StartActivity(frmLogin)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmlogin.getObject()));
 };
 //BA.debugLineNum = 278;BA.debugLine="End Sub";
return "";
}
public static String  _btnciclo_click() throws Exception{
 //BA.debugLineNum = 727;BA.debugLine="Sub btnCiclo_Click";
 //BA.debugLineNum = 728;BA.debugLine="Aprender_Ciclo.origen = \"menu\"";
mostCurrent._aprender_ciclo._origen /*String*/  = "menu";
 //BA.debugLineNum = 729;BA.debugLine="StartActivity(Aprender_Ciclo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_ciclo.getObject()));
 //BA.debugLineNum = 730;BA.debugLine="End Sub";
return "";
}
public static String  _btncomunidades_click() throws Exception{
 //BA.debugLineNum = 731;BA.debugLine="Sub btnComunidades_Click";
 //BA.debugLineNum = 732;BA.debugLine="Aprender_Comunidades.origen = \"menu\"";
mostCurrent._aprender_comunidades._origen /*String*/  = "menu";
 //BA.debugLineNum = 733;BA.debugLine="StartActivity(Aprender_Comunidades)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_comunidades.getObject()));
 //BA.debugLineNum = 735;BA.debugLine="End Sub";
return "";
}
public static String  _btncontaminacion_click() throws Exception{
 //BA.debugLineNum = 717;BA.debugLine="Sub btnContaminacion_Click";
 //BA.debugLineNum = 718;BA.debugLine="Aprender_Contaminacion.origen = \"menu\"";
mostCurrent._aprender_contaminacion._origen /*String*/  = "menu";
 //BA.debugLineNum = 719;BA.debugLine="StartActivity(Aprender_Contaminacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_contaminacion.getObject()));
 //BA.debugLineNum = 720;BA.debugLine="End Sub";
return "";
}
public static String  _btndatosanteriores_click() throws Exception{
 //BA.debugLineNum = 312;BA.debugLine="Sub btnDatosAnteriores_Click";
 //BA.debugLineNum = 313;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 314;BA.debugLine="StartActivity(frmDatosAnteriores)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()));
 }else {
 //BA.debugLineNum = 316;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 317;BA.debugLine="ToastMessageShow(\"No ha iniciado sesión aún\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No ha iniciado sesión aún"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 320;BA.debugLine="ToastMessageShow(\"You haven't logged in yet\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You haven't logged in yet"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 323;BA.debugLine="End Sub";
return "";
}
public static String  _btndetectar_click() throws Exception{
 //BA.debugLineNum = 787;BA.debugLine="Sub btnDetectar_Click";
 //BA.debugLineNum = 788;BA.debugLine="DetectarPosicion";
_detectarposicion();
 //BA.debugLineNum = 789;BA.debugLine="End Sub";
return "";
}
public static String  _btnedituser_click() throws Exception{
 //BA.debugLineNum = 282;BA.debugLine="Sub btnEditUser_Click";
 //BA.debugLineNum = 283;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 284;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 286;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 287;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 289;BA.debugLine="ToastMessageShow(\"You need to be online to chec";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be online to check your profile"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 293;BA.debugLine="End Sub";
return "";
}
public static String  _btninatlink_click() throws Exception{
 //BA.debugLineNum = 1115;BA.debugLine="Sub btniNatLink_Click";
 //BA.debugLineNum = 1116;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1117;BA.debugLine="StartActivity(iNatCheck)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._inatcheck.getObject()));
 }else {
 //BA.debugLineNum = 1119;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1120;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para ingresar a ver el catálogo de organismos"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1122;BA.debugLine="ToastMessageShow(\"You need to be online to view";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be online to view the catalogue of species"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 1125;BA.debugLine="End Sub";
return "";
}
public static String  _btnlangen_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 337;BA.debugLine="Sub btnLangEn_Click";
 //BA.debugLineNum = 338;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 339;BA.debugLine="Main.lang = \"en\"";
mostCurrent._main._lang /*String*/  = "en";
 //BA.debugLineNum = 340;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 341;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 342;BA.debugLine="Map1.Put(\"configID\", \"1\")";
_map1.Put((Object)("configID"),(Object)("1"));
 //BA.debugLineNum = 343;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"appconfig\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"appconfig","applang",(Object)(mostCurrent._main._lang /*String*/ ),_map1);
 //BA.debugLineNum = 345;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 346;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 347;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,form_main.getObject());
 };
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _btnlanges_click() throws Exception{
anywheresoftware.b4a.objects.collections.Map _map1 = null;
 //BA.debugLineNum = 324;BA.debugLine="Sub btnLangEs_Click";
 //BA.debugLineNum = 325;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 326;BA.debugLine="Main.lang = \"es\"";
mostCurrent._main._lang /*String*/  = "es";
 //BA.debugLineNum = 327;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 328;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 329;BA.debugLine="Map1.Put(\"configID\", \"1\")";
_map1.Put((Object)("configID"),(Object)("1"));
 //BA.debugLineNum = 330;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"appconfig\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"appconfig","applang",(Object)(mostCurrent._main._lang /*String*/ ),_map1);
 //BA.debugLineNum = 332;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 333;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 334;BA.debugLine="StartActivity(Me)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,form_main.getObject());
 };
 //BA.debugLineNum = 336;BA.debugLine="End Sub";
return "";
}
public static String  _btnmemory_click() throws Exception{
 //BA.debugLineNum = 742;BA.debugLine="Sub btnMemory_Click";
 //BA.debugLineNum = 743;BA.debugLine="StartActivity(Aprender_Memory)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_memory.getObject()));
 //BA.debugLineNum = 744;BA.debugLine="End Sub";
return "";
}
public static String  _btnmenu_click() throws Exception{
 //BA.debugLineNum = 228;BA.debugLine="Sub btnMenu_Click";
 //BA.debugLineNum = 229;BA.debugLine="Drawer.LeftOpen = True";
mostCurrent._drawer._setleftopen /*boolean*/ (anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 230;BA.debugLine="If Main.username = \"guest\" Then";
if ((mostCurrent._main._username /*String*/ ).equals("guest")) { 
 //BA.debugLineNum = 231;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 232;BA.debugLine="btnCerrarSesion.Text = \"Registrarse!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Registrarse!"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 234;BA.debugLine="btnCerrarSesion.Text = \"Sign up!\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence("Sign up!"));
 };
 };
 //BA.debugLineNum = 238;BA.debugLine="End Sub";
return "";
}
public static String  _btnmuestreos_click() throws Exception{
 //BA.debugLineNum = 306;BA.debugLine="Sub btnMuestreos_Click";
 //BA.debugLineNum = 307;BA.debugLine="StartActivity(Aprender_Muestreo)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_muestreo.getObject()));
 //BA.debugLineNum = 308;BA.debugLine="End Sub";
return "";
}
public static String  _btnpoliticadatos_click() throws Exception{
 //BA.debugLineNum = 309;BA.debugLine="Sub btnPoliticaDatos_Click";
 //BA.debugLineNum = 310;BA.debugLine="StartActivity(frmPoliticaDatos)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmpoliticadatos.getObject()));
 //BA.debugLineNum = 311;BA.debugLine="End Sub";
return "";
}
public static String  _btnreportar_click() throws Exception{
 //BA.debugLineNum = 686;BA.debugLine="Sub btnReportar_Click";
 //BA.debugLineNum = 687;BA.debugLine="Form_Reporte.origen = \"Form_Main\"";
mostCurrent._form_reporte._origen /*String*/  = "Form_Main";
 //BA.debugLineNum = 688;BA.debugLine="StartActivity(Form_Reporte)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._form_reporte.getObject()));
 //BA.debugLineNum = 691;BA.debugLine="End Sub";
return "";
}
public static String  _btntipoambientes_click() throws Exception{
 //BA.debugLineNum = 736;BA.debugLine="Sub btnTipoAmbientes_Click";
 //BA.debugLineNum = 738;BA.debugLine="StartActivity(Aprender_Ambientes)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._aprender_ambientes.getObject()));
 //BA.debugLineNum = 739;BA.debugLine="End Sub";
return "";
}
public static String  _btnvermedallas_click() throws Exception{
 //BA.debugLineNum = 294;BA.debugLine="Sub btnVerMedallas_Click";
 //BA.debugLineNum = 295;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 296;BA.debugLine="StartActivity(frmPerfil)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmperfil.getObject()));
 }else {
 //BA.debugLineNum = 298;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 299;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 301;BA.debugLine="ToastMessageShow(\"You need to be online to chec";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be online to check your profile"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 //BA.debugLineNum = 305;BA.debugLine="End Sub";
return "";
}
public static String  _btnverperfil_click() throws Exception{
 //BA.debugLineNum = 239;BA.debugLine="Sub btnVerPerfil_Click";
 //BA.debugLineNum = 240;BA.debugLine="If Main.username = \"guest\" Then";
if ((mostCurrent._main._username /*String*/ ).equals("guest")) { 
 //BA.debugLineNum = 241;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 242;BA.debugLine="ToastMessageShow(\"Necesita estar registrado par";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita estar registrado para ver su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 244;BA.debugLine="ToastMessageShow(\"You need to be registered to";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be registered to view your profile"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else {
 //BA.debugLineNum = 247;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 248;BA.debugLine="StartActivity(frmEditProfile)";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(mostCurrent._frmeditprofile.getObject()));
 }else {
 //BA.debugLineNum = 250;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 251;BA.debugLine="ToastMessageShow(\"Necesita tener internet para";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Necesita tener internet para editar su perfil"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 253;BA.debugLine="ToastMessageShow(\"You need to be online to che";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You need to be online to check your profile"),anywheresoftware.b4a.keywords.Common.False);
 };
 };
 };
 //BA.debugLineNum = 257;BA.debugLine="End Sub";
return "";
}
public static String  _btnzoomall_click() throws Exception{
 //BA.debugLineNum = 1107;BA.debugLine="Sub btnZoomAll_Click";
 //BA.debugLineNum = 1108;BA.debugLine="MapView1.GetController.SetZoom(5)";
mostCurrent._mapview1.GetController().SetZoom((int) (5));
 //BA.debugLineNum = 1109;BA.debugLine="End Sub";
return "";
}
public static String  _cargarmapa() throws Exception{
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
 //BA.debugLineNum = 755;BA.debugLine="Sub CargarMapa";
 //BA.debugLineNum = 757;BA.debugLine="If LastLocation.IsInitialized Then";
if (_lastlocation.IsInitialized()) { 
 //BA.debugLineNum = 758;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
 //BA.debugLineNum = 759;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 }else {
 //BA.debugLineNum = 762;BA.debugLine="lblLat.Text = \"-34.9204950\"";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence("-34.9204950"));
 //BA.debugLineNum = 763;BA.debugLine="lblLon.Text = \"-57.9535660\"";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence("-57.9535660"));
 };
 //BA.debugLineNum = 765;BA.debugLine="MapView1.Initialize(\"MapView1\")";
mostCurrent._mapview1.Initialize(mostCurrent.activityBA,"MapView1");
 //BA.debugLineNum = 766;BA.debugLine="MapView1.SetBuiltInZoomControls(True)";
mostCurrent._mapview1.SetBuiltInZoomControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 767;BA.debugLine="MapView1.SetMultiTouchControls(True)";
mostCurrent._mapview1.SetMultiTouchControls(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 769;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
 //BA.debugLineNum = 770;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
 //BA.debugLineNum = 771;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 772;BA.debugLine="MapView1.GetController.SetZoom(6)";
mostCurrent._mapview1.GetController().SetZoom((int) (6));
 //BA.debugLineNum = 774;BA.debugLine="pnlMapa.RemoveAllViews";
mostCurrent._pnlmapa.RemoveAllViews();
 //BA.debugLineNum = 775;BA.debugLine="pnlMapa.AddView(MapView1, 0,0, 100%x, 100%y)";
mostCurrent._pnlmapa.AddView((android.view.View)(mostCurrent._mapview1.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 776;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 777;BA.debugLine="SimpleLocationOverlay1.Initialize()";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 //BA.debugLineNum = 778;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
 //BA.debugLineNum = 779;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 }else {
 //BA.debugLineNum = 781;BA.debugLine="MapView1.GetOverlays.Add(SimpleLocationOverlay1)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._simplelocationoverlay1.getObject()));
 //BA.debugLineNum = 782;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 };
 //BA.debugLineNum = 784;BA.debugLine="GetMiMapa";
_getmimapa();
 //BA.debugLineNum = 785;BA.debugLine="End Sub";
return "";
}
public static String  _cargarpuntos() throws Exception{
anywheresoftware.b4a.objects.drawable.ColorDrawable _gd = null;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
double _nivelfull = 0;
int _nivela = 0;
double _resto = 0;
 //BA.debugLineNum = 435;BA.debugLine="Sub CargarPuntos";
 //BA.debugLineNum = 436;BA.debugLine="If Main.strUserName = \"\" Then";
if ((mostCurrent._main._strusername /*String*/ ).equals("")) { 
 //BA.debugLineNum = 437;BA.debugLine="pgbNivel.Visible = False";
mostCurrent._pgbnivel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 438;BA.debugLine="lblLevel.Visible = False";
mostCurrent._lbllevel.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 439;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 443;BA.debugLine="Dim gd As ColorDrawable";
_gd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 444;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 445;BA.debugLine="gd.Initialize(Colors.White,5dip)";
_gd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.White,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 446;BA.debugLine="cd.Initialize(Colors.ARGB(70,255,255,255), 5dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (70),(int) (255),(int) (255),(int) (255)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 447;BA.debugLine="utilidades.SetProgressDrawable(pgbNivel, gd, cd)";
mostCurrent._utilidades._setprogressdrawable /*String*/ (mostCurrent.activityBA,mostCurrent._pgbnivel,(Object)(_gd.getObject()),(Object)(_cd.getObject()));
 //BA.debugLineNum = 450;BA.debugLine="Dim nivelfull As Double";
_nivelfull = 0;
 //BA.debugLineNum = 451;BA.debugLine="Dim nivela As Int";
_nivela = 0;
 //BA.debugLineNum = 452;BA.debugLine="Dim resto As Double";
_resto = 0;
 //BA.debugLineNum = 453;BA.debugLine="If Main.puntostotales = \"\" Then";
if ((mostCurrent._main._puntostotales /*String*/ ).equals("")) { 
 //BA.debugLineNum = 454;BA.debugLine="Main.puntostotales = 0";
mostCurrent._main._puntostotales /*String*/  = BA.NumberToString(0);
 };
 //BA.debugLineNum = 456;BA.debugLine="nivelfull = (Sqrt(Main.puntostotales) * 0.25)";
_nivelfull = (anywheresoftware.b4a.keywords.Common.Sqrt((double)(Double.parseDouble(mostCurrent._main._puntostotales /*String*/ )))*0.25);
 //BA.debugLineNum = 457;BA.debugLine="nivela = Floor(nivelfull)";
_nivela = (int) (anywheresoftware.b4a.keywords.Common.Floor(_nivelfull));
 //BA.debugLineNum = 458;BA.debugLine="resto = Round2(Abs(nivelfull - nivela) * 100,0)";
_resto = anywheresoftware.b4a.keywords.Common.Round2(anywheresoftware.b4a.keywords.Common.Abs(_nivelfull-_nivela)*100,(int) (0));
 //BA.debugLineNum = 459;BA.debugLine="pgbNivel.Progress = resto";
mostCurrent._pgbnivel.setProgress((int) (_resto));
 //BA.debugLineNum = 460;BA.debugLine="If nivela = 0 Then";
if (_nivela==0) { 
 //BA.debugLineNum = 461;BA.debugLine="lblLevel.Text = \"1    \"";
mostCurrent._lbllevel.setText(BA.ObjectToCharSequence("1    "));
 }else {
 //BA.debugLineNum = 463;BA.debugLine="lblLevel.Text = nivela & \"    \"";
mostCurrent._lbllevel.setText(BA.ObjectToCharSequence(BA.NumberToString(_nivela)+"    "));
 };
 //BA.debugLineNum = 465;BA.debugLine="lblLevel.TextColor = Colors.DarkGray";
mostCurrent._lbllevel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 466;BA.debugLine="End Sub";
return "";
}
public static String  _checkmessages_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _msg = "";
String _serverid = "";
anywheresoftware.b4a.objects.collections.Map _datomap = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
String _datoid = "";
 //BA.debugLineNum = 537;BA.debugLine="Sub checkMessages_Complete(Job As HttpJob)";
 //BA.debugLineNum = 538;BA.debugLine="Log(\"Mensajes chequeados: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("728114945","Mensajes chequeados: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 539;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 540;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 541;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 542;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 543;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 544;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 545;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 546;BA.debugLine="Main.modooffline = False";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 547;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 548;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 549;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 550;BA.debugLine="ToastMessageShow(\"No hay mensajes para el usua";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No hay mensajes para el usuario"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 552;BA.debugLine="ToastMessageShow(\"No messages for the user\", F";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No messages for the user"),anywheresoftware.b4a.keywords.Common.False);
 };
 }else if((_act).equals("Error")) { 
 }else if((_act).equals("MensajesOK")) { 
 //BA.debugLineNum = 557;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 558;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 559;BA.debugLine="For i = 0 To numresults - 1";
{
final int step21 = 1;
final int limit21 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit21 ;_i = _i + step21 ) {
 //BA.debugLineNum = 560;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 561;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 563;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 565;BA.debugLine="Dim serverId As String";
_serverid = "";
 //BA.debugLineNum = 566;BA.debugLine="serverId = newpunto.Get(\"markerID\")";
_serverid = BA.ObjectToString(_newpunto.Get((Object)("markerID")));
 //BA.debugLineNum = 569;BA.debugLine="Dim datoMap As Map";
_datomap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 570;BA.debugLine="datoMap.Initialize";
_datomap.Initialize();
 //BA.debugLineNum = 571;BA.debugLine="datoMap = DBUtils.ExecuteMap(Starter.sqlDB, \"S";
_datomap = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM evals WHERE serverId=?",new String[]{_serverid});
 //BA.debugLineNum = 572;BA.debugLine="If datoMap = Null Or datoMap.IsInitialized = F";
if (_datomap== null || _datomap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 }else {
 //BA.debugLineNum = 577;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 578;BA.debugLine="Map1.Initialize";
_map1.Initialize();
 //BA.debugLineNum = 579;BA.debugLine="Map1.Put(\"serverId\", serverId)";
_map1.Put((Object)("serverId"),(Object)(_serverid));
 //BA.debugLineNum = 580;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","expValidacion",(Object)("si"),_map1);
 //BA.debugLineNum = 581;BA.debugLine="DBUtils.UpdateRecord(Starter.sqlDB, \"evals\",";
mostCurrent._dbutils._updaterecord /*String*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"evals","estadoValidacion",(Object)("experto"),_map1);
 //BA.debugLineNum = 582;BA.debugLine="msg = utilidades.Mensaje(\"Análisis validado\",";
_msg = mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"Análisis validado","MsgMensaje.png","Notificación privada",BA.ObjectToString(_newpunto.Get((Object)("mensaje"))),"Mas información","","Ok gracias!",anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 583;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 585;BA.debugLine="Dim datoId As String";
_datoid = "";
 //BA.debugLineNum = 586;BA.debugLine="datoId = datoMap.Get(\"id\")";
_datoid = BA.ObjectToString(_datomap.Get((Object)("id")));
 //BA.debugLineNum = 587;BA.debugLine="CallSubDelayed2(frmDatosAnteriores, \"VerDeta";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._frmdatosanteriores.getObject()),"VerDetalles",(Object)(_datoid));
 //BA.debugLineNum = 589;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 592;BA.debugLine="ResetMessages";
_resetmessages();
 };
 }
};
 };
 }else {
 //BA.debugLineNum = 599;BA.debugLine="Msgbox(\"No tienes conexión a Internet, no puedes";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes conexión a Internet, no puedes recibir los mensajes de los expertos"),BA.ObjectToCharSequence("Advertencia"),mostCurrent.activityBA);
 //BA.debugLineNum = 600;BA.debugLine="Main.modooffline = True";
mostCurrent._main._modooffline /*boolean*/  = anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 603;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 604;BA.debugLine="End Sub";
return "";
}
public static String  _checkmessages_new() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 525;BA.debugLine="Sub CheckMessages_New";
 //BA.debugLineNum = 527;BA.debugLine="If Main.modooffline = False Then";
if (mostCurrent._main._modooffline /*boolean*/ ==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 530;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 531;BA.debugLine="dd.url = Main.serverPath & \"/connect3/checkmessa";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect3/checkmessages.php?deviceID="+mostCurrent._main._deviceid /*String*/ ;
 //BA.debugLineNum = 532;BA.debugLine="dd.EventName = \"checkMessages\"";
_dd.EventName /*String*/  = "checkMessages";
 //BA.debugLineNum = 533;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = form_main.getObject();
 //BA.debugLineNum = 534;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\"";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 };
 //BA.debugLineNum = 536;BA.debugLine="End Sub";
return "";
}
public static String  _crearmenu() throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Sub CrearMenu";
 //BA.debugLineNum = 222;BA.debugLine="Drawer.Initialize(Me, \"Drawer\", Activity, 300dip)";
mostCurrent._drawer._initialize /*String*/ (mostCurrent.activityBA,form_main.getObject(),"Drawer",(anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(mostCurrent._activity.getObject())),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (300)));
 //BA.debugLineNum = 223;BA.debugLine="Drawer.CenterPanel.LoadLayout(\"layMain\")";
mostCurrent._drawer._getcenterpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 224;BA.debugLine="Drawer.LeftPanel.LoadLayout(\"frmSideNav\")";
mostCurrent._drawer._getleftpanel /*anywheresoftware.b4a.objects.B4XViewWrapper*/ ().LoadLayout("frmSideNav",mostCurrent.activityBA);
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _detectarposicion() throws Exception{
 //BA.debugLineNum = 791;BA.debugLine="Sub DetectarPosicion";
 //BA.debugLineNum = 794;BA.debugLine="fondoblanco.Initialize(\"fondoblanco\")";
mostCurrent._fondoblanco.Initialize(mostCurrent.activityBA,"fondoblanco");
 //BA.debugLineNum = 795;BA.debugLine="detectandoLabel.Initialize(\"\")";
mostCurrent._detectandolabel.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 796;BA.debugLine="detectandoLabel.TextColor = Colors.White";
mostCurrent._detectandolabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 797;BA.debugLine="detectandoLabel.Gravity = Gravity.CENTER_HORIZONT";
mostCurrent._detectandolabel.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER_HORIZONTAL);
 //BA.debugLineNum = 799;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 800;BA.debugLine="detectandoLabel.Text = \"Buscando tu posición aut";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Buscando tu posición automáticamente..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 802;BA.debugLine="detectandoLabel.Text = \"Looking for your positio";
mostCurrent._detectandolabel.setText(BA.ObjectToCharSequence("Looking for your position automatically..."));
 };
 //BA.debugLineNum = 806;BA.debugLine="fondoblanco.Color = Colors.ARGB(150, 64,64,64)";
mostCurrent._fondoblanco.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (150),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 807;BA.debugLine="Activity.AddView(fondoblanco, 0,0, 100%x, 100%y)";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fondoblanco.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 808;BA.debugLine="Activity.AddView(detectandoLabel, 5%x, 55%y, 80%x";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._detectandolabel.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (55),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (80),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (10),mostCurrent.activityBA));
 //BA.debugLineNum = 809;BA.debugLine="btnDetectar.Enabled = False";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 810;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 811;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 813;BA.debugLine="If FusedLocationProvider1.IsInitialized = False T";
if (_fusedlocationprovider1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 815;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProviderExplorar");
 //BA.debugLineNum = 816;BA.debugLine="Log(\"init fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729163545","init fusedlocationproviderExplorar",0);
 };
 //BA.debugLineNum = 818;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 819;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 //BA.debugLineNum = 820;BA.debugLine="Log(\"connecting fusedlocationproviderExplorar\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729163549","connecting fusedlocationproviderExplorar",0);
 };
 //BA.debugLineNum = 822;BA.debugLine="End Sub";
return "";
}
public static String  _fondoblanco_click() throws Exception{
 //BA.debugLineNum = 824;BA.debugLine="Sub fondoblanco_Click";
 //BA.debugLineNum = 826;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 827;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 828;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 830;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 831;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 //BA.debugLineNum = 832;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionfailed(int _connectionresult1) throws Exception{
 //BA.debugLineNum = 834;BA.debugLine="Sub FusedLocationProvider1_ConnectionFailed(Connec";
 //BA.debugLineNum = 835;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionFailed\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729294593","FusedLocationProvider1_ConnectionFailed",0);
 //BA.debugLineNum = 839;BA.debugLine="Select ConnectionResult1";
switch (BA.switchObjectToInt(_connectionresult1,_fusedlocationprovider1.ConnectionResult.NETWORK_ERROR)) {
case 0: {
 //BA.debugLineNum = 843;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 break; }
default: {
 break; }
}
;
 //BA.debugLineNum = 847;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuccess() throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _locationrequest1 = null;
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder _locationsettingsrequestbuilder1 = null;
 //BA.debugLineNum = 848;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuccess";
 //BA.debugLineNum = 849;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuccess\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729360129","FusedLocationProvider1_ConnectionSuccess",0);
 //BA.debugLineNum = 850;BA.debugLine="Dim LocationRequest1 As LocationRequest";
_locationrequest1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
 //BA.debugLineNum = 851;BA.debugLine="LocationRequest1.Initialize";
_locationrequest1.Initialize();
 //BA.debugLineNum = 852;BA.debugLine="LocationRequest1.SetInterval(1000)	'	1000 millise";
_locationrequest1.SetInterval((long) (1000));
 //BA.debugLineNum = 854;BA.debugLine="LocationRequest1.SetPriority(LocationRequest1.Pri";
_locationrequest1.SetPriority(_locationrequest1.Priority.PRIORITY_HIGH_ACCURACY);
 //BA.debugLineNum = 855;BA.debugLine="LocationRequest1.SetSmallestDisplacement(1)	'	1 m";
_locationrequest1.SetSmallestDisplacement((float) (1));
 //BA.debugLineNum = 857;BA.debugLine="Dim LocationSettingsRequestBuilder1 As LocationSe";
_locationsettingsrequestbuilder1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder();
 //BA.debugLineNum = 858;BA.debugLine="LocationSettingsRequestBuilder1.Initialize";
_locationsettingsrequestbuilder1.Initialize();
 //BA.debugLineNum = 859;BA.debugLine="LocationSettingsRequestBuilder1.AddLocationReques";
_locationsettingsrequestbuilder1.AddLocationRequest((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 860;BA.debugLine="FusedLocationProvider1.CheckLocationSettings(Loca";
_fusedlocationprovider1.CheckLocationSettings(_locationsettingsrequestbuilder1.Build());
 //BA.debugLineNum = 862;BA.debugLine="FusedLocationProvider1.RequestLocationUpdates(Loc";
_fusedlocationprovider1.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 864;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 865;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 866;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 867;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuspended(int _suspendedcause1) throws Exception{
 //BA.debugLineNum = 868;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuspended(Sus";
 //BA.debugLineNum = 869;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuspended\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729425665","FusedLocationProvider1_ConnectionSuspended",0);
 //BA.debugLineNum = 873;BA.debugLine="Select SuspendedCause1";
switch (BA.switchObjectToInt(_suspendedcause1,_fusedlocationprovider1.SuspendedCause.CAUSE_NETWORK_LOST,_fusedlocationprovider1.SuspendedCause.CAUSE_SERVICE_DISCONNECTED)) {
case 0: {
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 879;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 880;BA.debugLine="Sub FusedLocationProvider1_LocationChanged(Locatio";
 //BA.debugLineNum = 881;BA.debugLine="Log(\"FusedLocationProvider1_LocationChanged\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729491201","FusedLocationProvider1_LocationChanged",0);
 //BA.debugLineNum = 882;BA.debugLine="LastLocation=Location1";
_lastlocation = _location1;
 //BA.debugLineNum = 883;BA.debugLine="Log(LastLocation.Latitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("729491203",BA.NumberToString(_lastlocation.getLatitude()),0);
 //BA.debugLineNum = 884;BA.debugLine="Log(LastLocation.Longitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("729491204",BA.NumberToString(_lastlocation.getLongitude()),0);
 //BA.debugLineNum = 885;BA.debugLine="UpdateUI";
_updateui();
 //BA.debugLineNum = 886;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationsettingschecked(uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsResult _locationsettingsresult1) throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus _locationsettingsstatus1 = null;
 //BA.debugLineNum = 887;BA.debugLine="Sub FusedLocationProvider1_LocationSettingsChecked";
 //BA.debugLineNum = 888;BA.debugLine="Log(\"FusedLocationProvider1_LocationSettingsCheck";
anywheresoftware.b4a.keywords.Common.LogImpl("729556737","FusedLocationProvider1_LocationSettingsChecked",0);
 //BA.debugLineNum = 889;BA.debugLine="Dim LocationSettingsStatus1 As LocationSettingsSt";
_locationsettingsstatus1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus();
_locationsettingsstatus1 = _locationsettingsresult1.GetLocationSettingsStatus();
 //BA.debugLineNum = 890;BA.debugLine="Select LocationSettingsStatus1.GetStatusCode";
switch (BA.switchObjectToInt(_locationsettingsstatus1.GetStatusCode(),_locationsettingsstatus1.StatusCodes.RESOLUTION_REQUIRED,_locationsettingsstatus1.StatusCodes.SETTINGS_CHANGE_UNAVAILABLE,_locationsettingsstatus1.StatusCodes.SUCCESS)) {
case 0: {
 //BA.debugLineNum = 892;BA.debugLine="Log(\"RESOLUTION_REQUIRED\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729556741","RESOLUTION_REQUIRED",0);
 //BA.debugLineNum = 895;BA.debugLine="LocationSettingsStatus1.StartResolutionDialog(\"";
_locationsettingsstatus1.StartResolutionDialog(mostCurrent.activityBA,"LocationSettingsResult1");
 break; }
case 1: {
 //BA.debugLineNum = 897;BA.debugLine="Log(\"SETTINGS_CHANGE_UNAVAILABLE\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729556746","SETTINGS_CHANGE_UNAVAILABLE",0);
 //BA.debugLineNum = 900;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 901;BA.debugLine="Msgbox(\"Error, tu dispositivo no tiene localiz";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error, tu dispositivo no tiene localización. Busca tu posición en el mapa!"),BA.ObjectToCharSequence("Problem"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 903;BA.debugLine="Msgbox(\"Error, your device cannot be located.";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error, your device cannot be located. Find your location in the map!"),BA.ObjectToCharSequence("Problem"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 905;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 2: {
 //BA.debugLineNum = 907;BA.debugLine="Log(\"SUCCESS\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729556756","SUCCESS",0);
 break; }
}
;
 //BA.debugLineNum = 911;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 983;BA.debugLine="Sub GetMiMapa";
 //BA.debugLineNum = 984;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 985;BA.debugLine="ProgressDialogShow(\"Buscando puntos cercanos...\"";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando puntos cercanos..."));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 987;BA.debugLine="ProgressDialogShow(\"Getting nearby reports...\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,BA.ObjectToCharSequence("Getting nearby reports..."));
 };
 //BA.debugLineNum = 989;BA.debugLine="btnDetectar.Enabled = False";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 992;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 993;BA.debugLine="dd.url = Main.serverPath & \"/connect3/getallmapa.";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect3/getallmapa.php";
 //BA.debugLineNum = 994;BA.debugLine="dd.EventName = \"GetMiMapa\"";
_dd.EventName /*String*/  = "GetMiMapa";
 //BA.debugLineNum = 995;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = form_main.getObject();
 //BA.debugLineNum = 996;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 998;BA.debugLine="End Sub";
return "";
}
public static String  _getmimapa_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
String _numresults = "";
anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper _marker_green = null;
anywheresoftware.b4a.objects.drawable.BitmapDrawable _marker_green_dr = null;
int _i = 0;
anywheresoftware.b4a.objects.collections.Map _newpunto = null;
String _nombresitio = "";
double _sitiolat = 0;
double _sitiolong = 0;
String _sitioindice = "";
String _sitiotiporio = "";
uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker _marker = null;
String _markerexportstr = "";
 //BA.debugLineNum = 999;BA.debugLine="Sub GetMiMapa_Complete(Job As HttpJob)";
 //BA.debugLineNum = 1000;BA.debugLine="Log(\"GetMapa messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("729818881","GetMapa messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 1001;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 1003;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 1004;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 1005;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 1006;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 1007;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 1008;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1009;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 1010;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1011;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios ante";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1013;BA.debugLine="ToastMessageShow(\"Previous reports not found,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Previous reports not found, try again later"),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("Error")) { 
 //BA.debugLineNum = 1016;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1017;BA.debugLine="ToastMessageShow(\"No encuentro tus sitios ante";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("No encuentro tus sitios anteriores, prueba luego"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1019;BA.debugLine="ToastMessageShow(\"Previous reports not found,";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Previous reports not found, try again later"),anywheresoftware.b4a.keywords.Common.True);
 };
 }else if((_act).equals("GetMapaOk")) { 
 //BA.debugLineNum = 1023;BA.debugLine="Dim numresults As String";
_numresults = "";
 //BA.debugLineNum = 1024;BA.debugLine="numresults = parser.NextValue";
_numresults = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 1030;BA.debugLine="markersOverlay.Initialize(\"markersOverlay\", Map";
mostCurrent._markersoverlay.Initialize(processBA,"markersOverlay",(org.osmdroid.views.MapView)(mostCurrent._mapview1.getObject()));
 //BA.debugLineNum = 1031;BA.debugLine="MapView1.GetOverlays.Add(markersOverlay)";
mostCurrent._mapview1.GetOverlays().Add((org.osmdroid.views.overlay.Overlay)(mostCurrent._markersoverlay.getObject()));
 //BA.debugLineNum = 1034;BA.debugLine="markersList.Initialize()";
mostCurrent._markerslist.Initialize();
 //BA.debugLineNum = 1036;BA.debugLine="Dim marker_green As Bitmap";
_marker_green = new anywheresoftware.b4a.objects.drawable.CanvasWrapper.BitmapWrapper();
 //BA.debugLineNum = 1037;BA.debugLine="marker_green.Initialize(File.DirAssets, \"marker";
_marker_green.Initialize(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"marker_green.png");
 //BA.debugLineNum = 1038;BA.debugLine="Dim marker_green_dr As BitmapDrawable";
_marker_green_dr = new anywheresoftware.b4a.objects.drawable.BitmapDrawable();
 //BA.debugLineNum = 1039;BA.debugLine="marker_green_dr.Initialize(marker_green)";
_marker_green_dr.Initialize((android.graphics.Bitmap)(_marker_green.getObject()));
 //BA.debugLineNum = 1041;BA.debugLine="If numresults = 0 Then";
if ((_numresults).equals(BA.NumberToString(0))) { 
 //BA.debugLineNum = 1042;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1043;BA.debugLine="ToastMessageShow(\"Aún no has enviado ninguna";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Aún no has enviado ninguna evaluación. Cuando lo hagas, aparecerá en este mapa!"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1045;BA.debugLine="ToastMessageShow(\"You haven't sent any report";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("You haven't sent any reports yet, when you do, they will show in this map!"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1047;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 1049;BA.debugLine="MarkerExport.Initialize()";
mostCurrent._markerexport.Initialize();
 //BA.debugLineNum = 1051;BA.debugLine="For i = 0 To numresults - 1";
{
final int step40 = 1;
final int limit40 = (int) ((double)(Double.parseDouble(_numresults))-1);
_i = (int) (0) ;
for (;_i <= limit40 ;_i = _i + step40 ) {
 //BA.debugLineNum = 1053;BA.debugLine="Dim newpunto As Map";
_newpunto = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 1054;BA.debugLine="newpunto = parser.NextObject";
_newpunto = _parser.NextObject();
 //BA.debugLineNum = 1055;BA.debugLine="Dim nombresitio As String = newpunto.Get(\"nomb";
_nombresitio = BA.ObjectToString(_newpunto.Get((Object)("nombresitio")));
 //BA.debugLineNum = 1056;BA.debugLine="Dim sitiolat As Double = newpunto.Get(\"lat\")";
_sitiolat = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lat"))));
 //BA.debugLineNum = 1057;BA.debugLine="Dim sitiolong As Double = newpunto.Get(\"lng\")";
_sitiolong = (double)(BA.ObjectToNumber(_newpunto.Get((Object)("lng"))));
 //BA.debugLineNum = 1058;BA.debugLine="Dim sitioindice As String = newpunto.Get(\"indi";
_sitioindice = BA.ObjectToString(_newpunto.Get((Object)("indice")));
 //BA.debugLineNum = 1059;BA.debugLine="Dim sitiotiporio As String = newpunto.Get(\"tip";
_sitiotiporio = BA.ObjectToString(_newpunto.Get((Object)("tiporio")));
 //BA.debugLineNum = 1060;BA.debugLine="Dim marker As OSMDroid_Marker";
_marker = new uk.co.martinpearman.b4a.osmdroid.views.overlay.Marker();
 //BA.debugLineNum = 1061;BA.debugLine="marker.Initialize(nombresitio,sitiotiporio,sit";
_marker.Initialize(_nombresitio,_sitiotiporio,_sitiolat,_sitiolong);
 //BA.debugLineNum = 1062;BA.debugLine="marker.SetMarkerIcon(marker_green_dr)";
_marker.SetMarkerIcon((android.graphics.drawable.BitmapDrawable)(_marker_green_dr.getObject()));
 //BA.debugLineNum = 1063;BA.debugLine="Dim MarkerExportStr As String";
_markerexportstr = "";
 //BA.debugLineNum = 1064;BA.debugLine="MarkerExportStr = nombresitio & \",\" & sitiolat";
_markerexportstr = _nombresitio+","+BA.NumberToString(_sitiolat)+","+BA.NumberToString(_sitiolong)+","+_sitioindice+","+_sitiotiporio;
 //BA.debugLineNum = 1065;BA.debugLine="MarkerExport.Add(MarkerExportStr)";
mostCurrent._markerexport.Add((Object)(_markerexportstr));
 //BA.debugLineNum = 1080;BA.debugLine="markersList.Add(marker)";
mostCurrent._markerslist.Add((Object)(_marker.getObject()));
 }
};
 //BA.debugLineNum = 1084;BA.debugLine="markersOverlay.AddItems(markersList)";
mostCurrent._markersoverlay.AddItems(mostCurrent._markerslist);
 };
 }else {
 //BA.debugLineNum = 1088;BA.debugLine="Log(\"GetMapa not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("729818969","GetMapa not ok",0);
 //BA.debugLineNum = 1089;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1090;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1092;BA.debugLine="Msgbox(\"There seems to be a problem with the s";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with the server, we will fix it soon!"),BA.ObjectToCharSequence("My bad!"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 1097;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 1098;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 1099;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 1101;BA.debugLine="If FusedLocationProvider1.IsConnected = False The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 1102;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 };
 //BA.debugLineNum = 1104;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 24;BA.debugLine="Dim FormMainloaded As Boolean = False";
_formmainloaded = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 25;BA.debugLine="Dim p As Phone";
mostCurrent._p = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 29;BA.debugLine="Private pgbNivel As ProgressBar";
mostCurrent._pgbnivel = new anywheresoftware.b4a.objects.ProgressBarWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private lblLevel As Label";
mostCurrent._lbllevel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private tabStripMain As TabStrip";
mostCurrent._tabstripmain = new anywheresoftware.b4a.objects.TabStripViewPager();
 //BA.debugLineNum = 37;BA.debugLine="Private lblAnalizar As Label";
mostCurrent._lblanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnReportar As Button";
mostCurrent._btnreportar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private lblComoFuncionaAnalizar As Label";
mostCurrent._lblcomofuncionaanalizar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnComunidades As Button";
mostCurrent._btncomunidades = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnTipoAmbientes As Button";
mostCurrent._btntipoambientes = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private btnCadenas As Button";
mostCurrent._btncadenas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnCiclo As Button";
mostCurrent._btnciclo = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btnMemory As Button";
mostCurrent._btnmemory = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private btnAhorcado As Button";
mostCurrent._btnahorcado = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private btnContaminacion As Button";
mostCurrent._btncontaminacion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 49;BA.debugLine="Private lblUserLevel As Label";
mostCurrent._lbluserlevel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 50;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 54;BA.debugLine="Private lblConoce As Label";
mostCurrent._lblconoce = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="Private lblRequiereInternet As Label";
mostCurrent._lblrequiereinternet = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 56;BA.debugLine="Private lblComoFuncionaExplorar As Label";
mostCurrent._lblcomofuncionaexplorar = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 59;BA.debugLine="Private btnDetectar As Button";
mostCurrent._btndetectar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 60;BA.debugLine="Private lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 61;BA.debugLine="Private lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 62;BA.debugLine="Dim MapView1 As OSMDroid_MapView";
mostCurrent._mapview1 = new uk.co.martinpearman.b4a.osmdroid.views.MapView();
 //BA.debugLineNum = 63;BA.debugLine="Private btnZoomAll As Button";
mostCurrent._btnzoomall = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 64;BA.debugLine="Dim SimpleLocationOverlay1 As OSMDroid_SimpleLoca";
mostCurrent._simplelocationoverlay1 = new uk.co.martinpearman.b4a.osmdroid.views.overlay.SimpleLocationOverlay();
 //BA.debugLineNum = 65;BA.debugLine="Dim MarkerExport As List";
mostCurrent._markerexport = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 67;BA.debugLine="Dim MarkerInfo As Label";
mostCurrent._markerinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 69;BA.debugLine="Private pnlMapa As Panel";
mostCurrent._pnlmapa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 70;BA.debugLine="Dim fondoblanco As Label";
mostCurrent._fondoblanco = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 71;BA.debugLine="Dim detectandoLabel As Label";
mostCurrent._detectandolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 78;BA.debugLine="Dim markersList As List";
mostCurrent._markerslist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 79;BA.debugLine="Dim markersOverlay As OSMDroid_MarkerOverlay";
mostCurrent._markersoverlay = new uk.co.martinpearman.b4a.osmdroid.views.overlay.MarkerOverlay();
 //BA.debugLineNum = 80;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 84;BA.debugLine="Dim firstuse As String";
mostCurrent._firstuse = "";
 //BA.debugLineNum = 85;BA.debugLine="Dim fondogris As Panel";
mostCurrent._fondogris = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 86;BA.debugLine="Dim tutorialEtapa As Int";
_tutorialetapa = 0;
 //BA.debugLineNum = 87;BA.debugLine="Private btnMenu As Button";
mostCurrent._btnmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 91;BA.debugLine="Dim Drawer As B4XDrawer";
mostCurrent._drawer = new ilpla.appear.b4xdrawer();
 //BA.debugLineNum = 92;BA.debugLine="Private btnCerrarSesion As Button";
mostCurrent._btncerrarsesion = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 93;BA.debugLine="Private btnEditUser As Label";
mostCurrent._btnedituser = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 94;BA.debugLine="Private btnverMedallas As Label";
mostCurrent._btnvermedallas = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 95;BA.debugLine="Private lblUserName As Label";
mostCurrent._lblusername = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 96;BA.debugLine="Private lblRegistrate As Label";
mostCurrent._lblregistrate = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 97;BA.debugLine="Private btnVerPerfil As Label";
mostCurrent._btnverperfil = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Private btnAbout As Label";
mostCurrent._btnabout = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 99;BA.debugLine="Private btnPoliticaDatos As Label";
mostCurrent._btnpoliticadatos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 100;BA.debugLine="Private btnDatosAnteriores As Label";
mostCurrent._btndatosanteriores = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 101;BA.debugLine="Private btnMuestreos As Label";
mostCurrent._btnmuestreos = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaanalizar_click() throws Exception{
 //BA.debugLineNum = 693;BA.debugLine="Sub lblComoFuncionaAnalizar_Click";
 //BA.debugLineNum = 694;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 696;BA.debugLine="utilidades.Mensaje(\"\", \"logo-sololetras.png\", \"Í";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"","logo-sololetras.png","Índices ecológicos","Contesta unas preguntas y toma fotografías, y a través de los índices ecológicos para ambientes acuáticos calculados por AppEAR, sabrás la calidad ambiental del lugar adonde te encuentras."+anywheresoftware.b4a.keywords.Common.CRLF+"Y desde www.app-ear.com.ar podrás ver cómo se calculan estos índices!","Ok!","","",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 698;BA.debugLine="utilidades.Mensaje(\"\", \"logo-sololetras.png\", \"E";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"","logo-sololetras.png","Ecological indices","Answer some questions and take photos, and throught the ecological indices for freshwater environments calculated by AppEAR, you will know the ecological quality of the placer you are in. "+anywheresoftware.b4a.keywords.Common.CRLF+"Go to www.app-ear.com.ar to see how these indices are calculated! ","Ok!","","",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 700;BA.debugLine="End Sub";
return "";
}
public static String  _lblcomofuncionaexplorar_click() throws Exception{
 //BA.debugLineNum = 1128;BA.debugLine="Sub lblComoFuncionaExplorar_Click";
 //BA.debugLineNum = 1129;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 1130;BA.debugLine="utilidades.Mensaje(\"\", \"INaturalist_logo.png\", \"";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"","INaturalist_logo.png","Base de datos de biodiversidad","El catálogo busca las especies que fueron reportadas cercanas a tu posición, tomando la información actualizada de la iNaturalist.org."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Pueden faltar algunas especies, pero se van agregando todos los días con la participación ciudadana! ","Ok!","","",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 1132;BA.debugLine="utilidades.Mensaje(\"\", \"logo-sololetras.png\", \"B";
mostCurrent._utilidades._mensaje /*String*/ (mostCurrent.activityBA,"","logo-sololetras.png","Biodiversity database","The database will show the species close to your position that were reported by other users, retrieving the information from iNaturalist.org."+anywheresoftware.b4a.keywords.Common.CRLF+anywheresoftware.b4a.keywords.Common.CRLF+"Some species might be missing, but the database grows every day through public participation! ","Ok!","","",anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 1134;BA.debugLine="End Sub";
return "";
}
public static String  _lbluserlevel_click() throws Exception{
 //BA.debugLineNum = 710;BA.debugLine="Sub lblUserLevel_Click";
 //BA.debugLineNum = 711;BA.debugLine="Msgbox(\"Para cambiar su actual categoría de usuar";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Para cambiar su actual categoría de usuario, puede hacerlo desde su perfil"),BA.ObjectToCharSequence("Categoría"),mostCurrent.activityBA);
 //BA.debugLineNum = 712;BA.debugLine="End Sub";
return "";
}
public static String  _loadform_main() throws Exception{
 //BA.debugLineNum = 375;BA.debugLine="Sub LoadForm_Main";
 //BA.debugLineNum = 378;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 379;BA.debugLine="Activity.LoadLayout(\"layMain\")";
mostCurrent._activity.LoadLayout("layMain",mostCurrent.activityBA);
 //BA.debugLineNum = 382;BA.debugLine="CrearMenu";
_crearmenu();
 //BA.debugLineNum = 387;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 388;BA.debugLine="tabStripMain.LoadLayout(\"layMainReportar\", \"ANAL";
mostCurrent._tabstripmain.LoadLayout("layMainReportar",BA.ObjectToCharSequence("ANALIZAR"));
 //BA.debugLineNum = 389;BA.debugLine="tabStripMain.LoadLayout(\"layMainExplorar\", \"EXPL";
mostCurrent._tabstripmain.LoadLayout("layMainExplorar",BA.ObjectToCharSequence("EXPLORAR"));
 //BA.debugLineNum = 390;BA.debugLine="tabStripMain.LoadLayout(\"layMainJugar\", \"JUGAR\")";
mostCurrent._tabstripmain.LoadLayout("layMainJugar",BA.ObjectToCharSequence("JUGAR"));
 //BA.debugLineNum = 391;BA.debugLine="tabStripMain.LoadLayout(\"layMainMap\", \"MAPA\")";
mostCurrent._tabstripmain.LoadLayout("layMainMap",BA.ObjectToCharSequence("MAPA"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 393;BA.debugLine="tabStripMain.LoadLayout(\"layMainReportar\", \"ANAL";
mostCurrent._tabstripmain.LoadLayout("layMainReportar",BA.ObjectToCharSequence("ANALYZE"));
 //BA.debugLineNum = 394;BA.debugLine="tabStripMain.LoadLayout(\"layMainExplorar\", \"EXPL";
mostCurrent._tabstripmain.LoadLayout("layMainExplorar",BA.ObjectToCharSequence("EXPLORE"));
 //BA.debugLineNum = 395;BA.debugLine="tabStripMain.LoadLayout(\"layMainJugar\", \"PLAY\")";
mostCurrent._tabstripmain.LoadLayout("layMainJugar",BA.ObjectToCharSequence("PLAY"));
 //BA.debugLineNum = 396;BA.debugLine="tabStripMain.LoadLayout(\"layMainMap\", \"MAP\")";
mostCurrent._tabstripmain.LoadLayout("layMainMap",BA.ObjectToCharSequence("MAP"));
 };
 //BA.debugLineNum = 400;BA.debugLine="CargarPuntos";
_cargarpuntos();
 //BA.debugLineNum = 426;BA.debugLine="FormMainloaded = True";
_formmainloaded = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 427;BA.debugLine="End Sub";
return "";
}
public static String  _locationsettingsresult1_resolutiondialogdismissed(boolean _locationsettingsupdated) throws Exception{
 //BA.debugLineNum = 912;BA.debugLine="Sub LocationSettingsResult1_ResolutionDialogDismis";
 //BA.debugLineNum = 913;BA.debugLine="Log(\"LocationSettingsResult1_ResolutionDialogDism";
anywheresoftware.b4a.keywords.Common.LogImpl("729622273","LocationSettingsResult1_ResolutionDialogDismissed",0);
 //BA.debugLineNum = 914;BA.debugLine="If Not(LocationSettingsUpdated) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_locationsettingsupdated)) { 
 //BA.debugLineNum = 916;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 917;BA.debugLine="Msgbox(\"No tienes habilitada la Localización, b";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No tienes habilitada la Localización, busca en el mapa tu posición"),BA.ObjectToCharSequence("Búsqueda manual"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 919;BA.debugLine="Msgbox(\"You don't have the location services en";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("You don't have the location services enabled, use the map to locate your position"),BA.ObjectToCharSequence("Manual search"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 924;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 925;BA.debugLine="btnDetectar.TextColor = Colors.black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 926;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 929;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 930;BA.debugLine="FusedLocationProvider1.DisConnect";
_fusedlocationprovider1.Disconnect();
 };
 //BA.debugLineNum = 933;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
 //BA.debugLineNum = 934;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 935;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 };
 };
 //BA.debugLineNum = 938;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Private FusedLocationProvider1 As FusedLocationPr";
_fusedlocationprovider1 = new uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper();
 //BA.debugLineNum = 11;BA.debugLine="Private LastLocation As Location";
_lastlocation = new anywheresoftware.b4a.gps.LocationWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim IsGuest As Boolean";
_isguest = false;
 //BA.debugLineNum = 16;BA.debugLine="Dim fullidcurrentproject As String";
_fullidcurrentproject = "";
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _resetmessages() throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 612;BA.debugLine="Sub ResetMessages";
 //BA.debugLineNum = 615;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 616;BA.debugLine="dd.url = Main.serverPath & \"/connect3/resetmessag";
_dd.url /*String*/  = mostCurrent._main._serverpath /*String*/ +"/connect3/resetmessages_new.php?deviceID="+mostCurrent._main._deviceid /*String*/ ;
 //BA.debugLineNum = 617;BA.debugLine="dd.EventName = \"ResetMessages\"";
_dd.EventName /*String*/  = "ResetMessages";
 //BA.debugLineNum = 618;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = form_main.getObject();
 //BA.debugLineNum = 619;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 621;BA.debugLine="End Sub";
return "";
}
public static String  _resetmessages_complete(ilpla.appear.httpjob _job) throws Exception{
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
 //BA.debugLineNum = 622;BA.debugLine="Sub ResetMessages_Complete(Job As HttpJob)";
 //BA.debugLineNum = 623;BA.debugLine="Log(\"Reset messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("728246017","Reset messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 624;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 625;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 626;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 627;BA.debugLine="ret = Job.GetString";
_ret = _job._getstring /*String*/ ();
 //BA.debugLineNum = 628;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 629;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 630;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 631;BA.debugLine="If act = \"Not Found\" Then";
if ((_act).equals("Not Found")) { 
 //BA.debugLineNum = 632;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("Error reseteando los mensajes, ")) { 
 //BA.debugLineNum = 634;BA.debugLine="ToastMessageShow(\"Login failed\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Login failed"),anywheresoftware.b4a.keywords.Common.True);
 }else if((_act).equals("ResetMessages")) { 
 };
 }else {
 //BA.debugLineNum = 639;BA.debugLine="Log(\"reset messages not ok\")";
anywheresoftware.b4a.keywords.Common.LogImpl("728246033","reset messages not ok",0);
 //BA.debugLineNum = 640;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 641;BA.debugLine="Msgbox(\"Al parecer hay un problema en nuestros";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Al parecer hay un problema en nuestros servidores, lo solucionaremos pronto!"),BA.ObjectToCharSequence("Mala mía"),mostCurrent.activityBA);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 643;BA.debugLine="Msgbox(\"There seems to be a problem with our se";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("There seems to be a problem with our servers, we will solve it soon!"),BA.ObjectToCharSequence("My bad"),mostCurrent.activityBA);
 };
 };
 //BA.debugLineNum = 647;BA.debugLine="Job.Release";
_job._release /*String*/ ();
 //BA.debugLineNum = 648;BA.debugLine="End Sub";
return "";
}
public static void  _resetmessages_old(String _markerid) throws Exception{
ResumableSub_ResetMessages_old rsub = new ResumableSub_ResetMessages_old(null,_markerid);
rsub.resume(processBA, null);
}
public static class ResumableSub_ResetMessages_old extends BA.ResumableSub {
public ResumableSub_ResetMessages_old(ilpla.appear.form_main parent,String _markerid) {
this.parent = parent;
this._markerid = _markerid;
}
ilpla.appear.form_main parent;
String _markerid;
ilpla.appear.httpjob _j = null;
String _ret = "";
String _act = "";
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 650;BA.debugLine="Dim j As HttpJob";
_j = new ilpla.appear.httpjob();
 //BA.debugLineNum = 651;BA.debugLine="j.Initialize(\"ResetMessages\", Me)";
_j._initialize /*String*/ (processBA,"ResetMessages",form_main.getObject());
 //BA.debugLineNum = 652;BA.debugLine="j.Download2(\"http://www.app-ear.com.ar/connect3/r";
_j._download2 /*String*/ ("http://www.app-ear.com.ar/connect3/resetmessages_new.php",new String[]{"markerID",_markerid});
 //BA.debugLineNum = 653;BA.debugLine="Wait For (j) JobDone(j As HttpJob)";
anywheresoftware.b4a.keywords.Common.WaitFor("jobdone", processBA, this, (Object)(_j));
this.state = 17;
return;
case 17:
//C
this.state = 1;
_j = (ilpla.appear.httpjob) result[0];
;
 //BA.debugLineNum = 654;BA.debugLine="If j.Success Then";
if (true) break;

case 1:
//if
this.state = 16;
if (_j._success /*boolean*/ ) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 655;BA.debugLine="Dim ret As String";
_ret = "";
 //BA.debugLineNum = 656;BA.debugLine="Dim act As String";
_act = "";
 //BA.debugLineNum = 657;BA.debugLine="ret = j.GetString";
_ret = _j._getstring /*String*/ ();
 //BA.debugLineNum = 658;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 659;BA.debugLine="parser.Initialize(ret)";
_parser.Initialize(_ret);
 //BA.debugLineNum = 660;BA.debugLine="act = parser.NextValue";
_act = BA.ObjectToString(_parser.NextValue());
 //BA.debugLineNum = 663;BA.debugLine="If j.JobName = \"ResetMessages\" Then";
if (true) break;

case 4:
//if
this.state = 15;
if ((_j._jobname /*String*/ ).equals("ResetMessages")) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 664;BA.debugLine="If act = \"Not Found\" Then";
if (true) break;

case 7:
//if
this.state = 14;
if ((_act).equals("Not Found")) { 
this.state = 9;
}else if((_act).equals("Error reseteando los mensajes, ")) { 
this.state = 11;
}else if((_act).equals("ResetMessages")) { 
this.state = 13;
}if (true) break;

case 9:
//C
this.state = 14;
 //BA.debugLineNum = 665;BA.debugLine="ToastMessageShow(\"Error\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 11:
//C
this.state = 14;
 //BA.debugLineNum = 667;BA.debugLine="ToastMessageShow(\"Login failed\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Login failed"),anywheresoftware.b4a.keywords.Common.True);
 if (true) break;

case 13:
//C
this.state = 14;
 if (true) break;

case 14:
//C
this.state = 15;
;
 if (true) break;

case 15:
//C
this.state = 16;
;
 if (true) break;

case 16:
//C
this.state = -1;
;
 //BA.debugLineNum = 673;BA.debugLine="j.Release";
_j._release /*String*/ ();
 //BA.debugLineNum = 675;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _jobdone(ilpla.appear.httpjob _j) throws Exception{
}
public static String  _tabstripmain_pageselected(int _position) throws Exception{
anywheresoftware.b4a.objects.collections.Map _userexists = null;
 //BA.debugLineNum = 470;BA.debugLine="Sub tabStripMain_PageSelected (Position As Int)";
 //BA.debugLineNum = 471;BA.debugLine="If Position = 2 Then";
if (_position==2) { 
 //BA.debugLineNum = 474;BA.debugLine="btnMemory.Width = (Activity.Width * 30) / 100";
mostCurrent._btnmemory.setWidth((int) ((mostCurrent._activity.getWidth()*30)/(double)100));
 //BA.debugLineNum = 475;BA.debugLine="btnMemory.Left =  (Activity.Width / 2) - (btnMem";
mostCurrent._btnmemory.setLeft((int) ((mostCurrent._activity.getWidth()/(double)2)-(mostCurrent._btnmemory.getWidth()/(double)2)));
 //BA.debugLineNum = 478;BA.debugLine="btnTipoAmbientes.Width = (Activity.Width * 30) /";
mostCurrent._btntipoambientes.setWidth((int) ((mostCurrent._activity.getWidth()*30)/(double)100));
 //BA.debugLineNum = 479;BA.debugLine="btnTipoAmbientes.Left =  (Activity.Width * 3) /";
mostCurrent._btntipoambientes.setLeft((int) ((mostCurrent._activity.getWidth()*3)/(double)100));
 //BA.debugLineNum = 480;BA.debugLine="btnComunidades.Width = (Activity.Width * 30) / 1";
mostCurrent._btncomunidades.setWidth((int) ((mostCurrent._activity.getWidth()*30)/(double)100));
 //BA.debugLineNum = 481;BA.debugLine="btnComunidades.Left =  btnTipoAmbientes.Left + b";
mostCurrent._btncomunidades.setLeft((int) (mostCurrent._btntipoambientes.getLeft()+mostCurrent._btntipoambientes.getWidth()+(mostCurrent._activity.getWidth()*3)/(double)100));
 //BA.debugLineNum = 482;BA.debugLine="btnCiclo.Width = (Activity.Width * 30) / 100";
mostCurrent._btnciclo.setWidth((int) ((mostCurrent._activity.getWidth()*30)/(double)100));
 //BA.debugLineNum = 483;BA.debugLine="btnCiclo.Left =  btnComunidades.Left + btnComuni";
mostCurrent._btnciclo.setLeft((int) (mostCurrent._btncomunidades.getLeft()+mostCurrent._btncomunidades.getWidth()+(mostCurrent._activity.getWidth()*3)/(double)100));
 //BA.debugLineNum = 485;BA.debugLine="btnCadenas.Width = (Activity.Width * 30) / 100";
mostCurrent._btncadenas.setWidth((int) ((mostCurrent._activity.getWidth()*30)/(double)100));
 //BA.debugLineNum = 486;BA.debugLine="btnCadenas.Left =  (Activity.Width * 3) / 100";
mostCurrent._btncadenas.setLeft((int) ((mostCurrent._activity.getWidth()*3)/(double)100));
 //BA.debugLineNum = 487;BA.debugLine="btnContaminacion.Width = (Activity.Width * 30) /";
mostCurrent._btncontaminacion.setWidth((int) ((mostCurrent._activity.getWidth()*30)/(double)100));
 //BA.debugLineNum = 488;BA.debugLine="btnContaminacion.Left =  btnCadenas.Left + btnCa";
mostCurrent._btncontaminacion.setLeft((int) (mostCurrent._btncadenas.getLeft()+mostCurrent._btncadenas.getWidth()+(mostCurrent._activity.getWidth()*3)/(double)100));
 //BA.debugLineNum = 489;BA.debugLine="btnAhorcado.Width = (Activity.Width * 30) / 100";
mostCurrent._btnahorcado.setWidth((int) ((mostCurrent._activity.getWidth()*30)/(double)100));
 //BA.debugLineNum = 490;BA.debugLine="btnAhorcado.Left =  btnContaminacion.Left + btnC";
mostCurrent._btnahorcado.setLeft((int) (mostCurrent._btncontaminacion.getLeft()+mostCurrent._btncontaminacion.getWidth()+(mostCurrent._activity.getWidth()*3)/(double)100));
 //BA.debugLineNum = 494;BA.debugLine="Dim userExists As Map";
_userexists = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 495;BA.debugLine="userExists.Initialize";
_userexists.Initialize();
 //BA.debugLineNum = 496;BA.debugLine="userExists = DBUtils.ExecuteMap(Starter.sqlDB, \"";
_userexists = mostCurrent._dbutils._executemap /*anywheresoftware.b4a.objects.collections.Map*/ (mostCurrent.activityBA,mostCurrent._starter._sqldb /*anywheresoftware.b4a.sql.SQL*/ ,"SELECT * FROM userconfig WHERE username=?",new String[]{mostCurrent._main._username /*String*/ });
 //BA.debugLineNum = 498;BA.debugLine="If userExists = Null Or userExists.IsInitialized";
if (_userexists== null || _userexists.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 }else {
 //BA.debugLineNum = 501;BA.debugLine="Main.userCategoria = userExists.Get(\"usercatego";
mostCurrent._main._usercategoria /*String*/  = BA.ObjectToString(_userexists.Get((Object)("usercategoria")));
 };
 }else if(_position==3) { 
 //BA.debugLineNum = 507;BA.debugLine="CargarMapa";
_cargarmapa();
 }else if(_position==0) { 
 }else if(_position==1) { 
 };
 //BA.debugLineNum = 515;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 183;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 184;BA.debugLine="lblUserName.Text = Main.strUserName";
mostCurrent._lblusername.setText(BA.ObjectToCharSequence(mostCurrent._main._strusername /*String*/ ));
 //BA.debugLineNum = 185;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 186;BA.debugLine="btnAbout.Text = \"El proyecto AppEAR\"";
mostCurrent._btnabout.setText(BA.ObjectToCharSequence("El proyecto AppEAR"));
 //BA.debugLineNum = 187;BA.debugLine="lblAnalizar.Text = \"Analiza el ambiente acuático";
mostCurrent._lblanalizar.setText(BA.ObjectToCharSequence("Analiza el ambiente acuático"));
 //BA.debugLineNum = 188;BA.debugLine="btnVerPerfil.Text = \"Mi perfil\"";
mostCurrent._btnverperfil.setText(BA.ObjectToCharSequence("Mi perfil"));
 //BA.debugLineNum = 189;BA.debugLine="btnDatosAnteriores.Text = \"Mis datos anteriores\"";
mostCurrent._btndatosanteriores.setText(BA.ObjectToCharSequence("Mis datos anteriores"));
 //BA.debugLineNum = 190;BA.debugLine="btnPoliticaDatos.Text = \"Política de datos\"";
mostCurrent._btnpoliticadatos.setText(BA.ObjectToCharSequence("Política de datos"));
 //BA.debugLineNum = 191;BA.debugLine="btnCerrarSesion.Text = \" Cerrar sesión\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence(" Cerrar sesión"));
 //BA.debugLineNum = 192;BA.debugLine="btnverMedallas.Text = \" Ver medallas\"";
mostCurrent._btnvermedallas.setText(BA.ObjectToCharSequence(" Ver medallas"));
 //BA.debugLineNum = 193;BA.debugLine="lblConoce.Text = \"¡Explora la biodiversidad cerc";
mostCurrent._lblconoce.setText(BA.ObjectToCharSequence("¡Explora la biodiversidad cerca tuyo!"));
 //BA.debugLineNum = 194;BA.debugLine="lblRequiereInternet.Text = \"Requiere conexión a";
mostCurrent._lblrequiereinternet.setText(BA.ObjectToCharSequence("Requiere conexión a internet"));
 //BA.debugLineNum = 195;BA.debugLine="btnMuestreos.Text = \"Precauciones en el campo\"";
mostCurrent._btnmuestreos.setText(BA.ObjectToCharSequence("Precauciones en el campo"));
 //BA.debugLineNum = 196;BA.debugLine="lblComoFuncionaExplorar.Text = \" ¿Cómo funciona";
mostCurrent._lblcomofuncionaexplorar.setText(BA.ObjectToCharSequence(" ¿Cómo funciona?"));
 //BA.debugLineNum = 197;BA.debugLine="lblComoFuncionaAnalizar.Text = \" ¿Cómo funciona";
mostCurrent._lblcomofuncionaanalizar.setText(BA.ObjectToCharSequence(" ¿Cómo funciona?"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 199;BA.debugLine="btnAbout.Text = \"The AppEAR project\"";
mostCurrent._btnabout.setText(BA.ObjectToCharSequence("The AppEAR project"));
 //BA.debugLineNum = 200;BA.debugLine="lblAnalizar.Text = \"Analize the freshwater envir";
mostCurrent._lblanalizar.setText(BA.ObjectToCharSequence("Analize the freshwater environment"));
 //BA.debugLineNum = 201;BA.debugLine="btnVerPerfil.Text = \"My profile\"";
mostCurrent._btnverperfil.setText(BA.ObjectToCharSequence("My profile"));
 //BA.debugLineNum = 202;BA.debugLine="btnDatosAnteriores.Text = \"Mis previous reports\"";
mostCurrent._btndatosanteriores.setText(BA.ObjectToCharSequence("Mis previous reports"));
 //BA.debugLineNum = 203;BA.debugLine="btnPoliticaDatos.Text = \"Data usage policy\"";
mostCurrent._btnpoliticadatos.setText(BA.ObjectToCharSequence("Data usage policy"));
 //BA.debugLineNum = 204;BA.debugLine="btnCerrarSesion.Text = \" Close session\"";
mostCurrent._btncerrarsesion.setText(BA.ObjectToCharSequence(" Close session"));
 //BA.debugLineNum = 205;BA.debugLine="btnverMedallas.Text = \" View medals\"";
mostCurrent._btnvermedallas.setText(BA.ObjectToCharSequence(" View medals"));
 //BA.debugLineNum = 206;BA.debugLine="lblConoce.Text = \"Explore biodiversity around yo";
mostCurrent._lblconoce.setText(BA.ObjectToCharSequence("Explore biodiversity around you!"));
 //BA.debugLineNum = 207;BA.debugLine="lblRequiereInternet.Text = \"Requieres internet c";
mostCurrent._lblrequiereinternet.setText(BA.ObjectToCharSequence("Requieres internet connection"));
 //BA.debugLineNum = 208;BA.debugLine="btnMuestreos.Text = \"Caution in the field\"";
mostCurrent._btnmuestreos.setText(BA.ObjectToCharSequence("Caution in the field"));
 //BA.debugLineNum = 209;BA.debugLine="lblComoFuncionaExplorar.Text = \" How does it wo";
mostCurrent._lblcomofuncionaexplorar.setText(BA.ObjectToCharSequence(" How does it work?"));
 //BA.debugLineNum = 210;BA.debugLine="lblComoFuncionaAnalizar.Text = \" How does it wo";
mostCurrent._lblcomofuncionaanalizar.setText(BA.ObjectToCharSequence(" How does it work?"));
 };
 //BA.debugLineNum = 212;BA.debugLine="End Sub";
return "";
}
public static String  _updateui() throws Exception{
uk.co.martinpearman.b4a.osmdroid.util.GeoPoint _geopoint1 = null;
 //BA.debugLineNum = 939;BA.debugLine="Sub UpdateUI";
 //BA.debugLineNum = 941;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 942;BA.debugLine="ToastMessageShow(\"Posición encontrada!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Posición encontrada!"),anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 944;BA.debugLine="ToastMessageShow(\"Location found!\", True)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Location found!"),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 947;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
 //BA.debugLineNum = 948;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 //BA.debugLineNum = 949;BA.debugLine="Dim geopoint1 As OSMDroid_GeoPoint";
_geopoint1 = new uk.co.martinpearman.b4a.osmdroid.util.GeoPoint();
 //BA.debugLineNum = 950;BA.debugLine="geopoint1.Initialize(lblLat.Text,lblLon.Text)";
_geopoint1.Initialize((double)(Double.parseDouble(mostCurrent._lbllat.getText())),(double)(Double.parseDouble(mostCurrent._lbllon.getText())));
 //BA.debugLineNum = 953;BA.debugLine="If SimpleLocationOverlay1.IsInitialized = False T";
if (mostCurrent._simplelocationoverlay1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 954;BA.debugLine="SimpleLocationOverlay1.Initialize";
mostCurrent._simplelocationoverlay1.Initialize(processBA);
 };
 //BA.debugLineNum = 956;BA.debugLine="SimpleLocationOverlay1.SetLocation(geopoint1)";
mostCurrent._simplelocationoverlay1.SetLocation((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 959;BA.debugLine="MapView1.GetController.SetCenter(geopoint1)";
mostCurrent._mapview1.GetController().SetCenter((org.osmdroid.util.GeoPoint)(_geopoint1.getObject()));
 //BA.debugLineNum = 960;BA.debugLine="MapView1.GetController.SetZoom(14)";
mostCurrent._mapview1.GetController().SetZoom((int) (14));
 //BA.debugLineNum = 961;BA.debugLine="btnDetectar.Color = Colors.Transparent";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 962;BA.debugLine="btnDetectar.TextColor = Colors.Black";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 964;BA.debugLine="FusedLocationProvider1.Disconnect";
_fusedlocationprovider1.Disconnect();
 //BA.debugLineNum = 965;BA.debugLine="btnDetectar.Enabled = True";
mostCurrent._btndetectar.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 967;BA.debugLine="If fondoblanco.IsInitialized Then";
if (mostCurrent._fondoblanco.IsInitialized()) { 
 //BA.debugLineNum = 968;BA.debugLine="fondoblanco.RemoveView";
mostCurrent._fondoblanco.RemoveView();
 //BA.debugLineNum = 969;BA.debugLine="detectandoLabel.RemoveView";
mostCurrent._detectandolabel.RemoveView();
 }else {
 //BA.debugLineNum = 971;BA.debugLine="btnDetectar.Color = Colors.Black";
mostCurrent._btndetectar.setColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 972;BA.debugLine="btnDetectar.TextColor = Colors.White";
mostCurrent._btndetectar.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 };
 //BA.debugLineNum = 974;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 975;BA.debugLine="End Sub";
return "";
}
}
