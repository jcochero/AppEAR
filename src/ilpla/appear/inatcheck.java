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

public class inatcheck extends Activity implements B4AActivity{
	public static inatcheck mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.inatcheck");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (inatcheck).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.inatcheck");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.inatcheck", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (inatcheck) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (inatcheck) Resume **");
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
		return inatcheck.class;
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
            BA.LogInfo("** Activity (inatcheck) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        else
            BA.LogInfo("** Activity (inatcheck) Pause event (activity is not paused). **");
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
            inatcheck mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (inatcheck) Resume **");
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
public static anywheresoftware.b4a.objects.collections.List _imagelinks = null;
public static anywheresoftware.b4a.objects.collections.List _prefered_common_namelist = null;
public static anywheresoftware.b4a.objects.collections.List _iconicnamelist = null;
public static anywheresoftware.b4a.objects.collections.List _scientific_namelist = null;
public static anywheresoftware.b4a.objects.collections.List _wikilinklist = null;
public static anywheresoftware.b4a.objects.collections.List _threatlist = null;
public static anywheresoftware.b4a.objects.collections.List _attributionlist = null;
public static uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper _fusedlocationprovider1 = null;
public static anywheresoftware.b4a.gps.LocationWrapper _lastlocation = null;
public static anywheresoftware.b4a.objects.RuntimePermissions _rp = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulochecklist = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblthreaticon = null;
public static String _currentscreen = "";
public anywheresoftware.b4a.objects.ButtonWrapper _butplantas = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butfungi = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butaves = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butbugs = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butpeces = null;
public anywheresoftware.b4a.objects.ButtonWrapper _butmammals = null;
public static String _nelat = "";
public static String _nelng = "";
public static String _swlat = "";
public static String _swlng = "";
public ilpla.appear.customlistview _clvchecklist = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlchecklist = null;
public anywheresoftware.b4a.phone.Phone _phone1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombrecomun = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblnombrecientifico = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.collections.List _imageviews = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllat = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbllon = null;
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
public ilpla.appear.frmlogin _frmlogin = null;
public ilpla.appear.frmpoliticadatos _frmpoliticadatos = null;
public ilpla.appear.httputils2service _httputils2service = null;
public ilpla.appear.imagedownloader _imagedownloader = null;
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
 //BA.debugLineNum = 56;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 58;BA.debugLine="phone1.SetScreenOrientation(1)";
mostCurrent._phone1.SetScreenOrientation(processBA,(int) (1));
 //BA.debugLineNum = 59;BA.debugLine="If FusedLocationProvider1.IsInitialized = False T";
if (_fusedlocationprovider1.IsInitialized()==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 60;BA.debugLine="FusedLocationProvider1.Initialize(\"FusedLocation";
_fusedlocationprovider1.Initialize(processBA,"FusedLocationProvider1");
 };
 //BA.debugLineNum = 63;BA.debugLine="If LastLocation.IsInitialized Then";
if (_lastlocation.IsInitialized()) { 
 };
 //BA.debugLineNum = 67;BA.debugLine="Activity.LoadLayout(\"layCheckLists_Home\")";
mostCurrent._activity.LoadLayout("layCheckLists_Home",mostCurrent.activityBA);
 //BA.debugLineNum = 68;BA.debugLine="currentScreen = \"CheckListHome\"";
mostCurrent._currentscreen = "CheckListHome";
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 89;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 90;BA.debugLine="If currentScreen = \"CheckListHome\" Then";
if ((mostCurrent._currentscreen).equals("CheckListHome")) { 
 //BA.debugLineNum = 91;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 92;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 93;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 }else if((mostCurrent._currentscreen).equals("CheckListList")) { 
 //BA.debugLineNum = 95;BA.debugLine="CallSub(ImageDownloader, \"CancelEverything\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"CancelEverything");
 //BA.debugLineNum = 96;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 97;BA.debugLine="Activity.LoadLayout(\"layCheckLists_Home\")";
mostCurrent._activity.LoadLayout("layCheckLists_Home",mostCurrent.activityBA);
 //BA.debugLineNum = 98;BA.debugLine="currentScreen = \"CheckListHome\"";
mostCurrent._currentscreen = "CheckListHome";
 //BA.debugLineNum = 99;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub Activity_Pause(UserClosed As Boolean)";
 //BA.debugLineNum = 86;BA.debugLine="CallSub(ImageDownloader, \"ActivityIsPaused\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"ActivityIsPaused");
 //BA.debugLineNum = 87;BA.debugLine="End Sub";
return "";
}
public static void  _activity_resume() throws Exception{
ResumableSub_Activity_Resume rsub = new ResumableSub_Activity_Resume(null);
rsub.resume(processBA, null);
}
public static class ResumableSub_Activity_Resume extends BA.ResumableSub {
public ResumableSub_Activity_Resume(ilpla.appear.inatcheck parent) {
this.parent = parent;
}
ilpla.appear.inatcheck parent;
String _permission = "";
boolean _result = false;
Object[] group1;
int index1;
int groupLen1;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 73;BA.debugLine="For Each Permission As String In Array(rp.PERMISS";
if (true) break;

case 1:
//for
this.state = 8;
group1 = new Object[]{(Object)(parent._rp.PERMISSION_ACCESS_FINE_LOCATION)};
index1 = 0;
groupLen1 = group1.length;
this.state = 9;
if (true) break;

case 9:
//C
this.state = 8;
if (index1 < groupLen1) {
this.state = 3;
_permission = BA.ObjectToString(group1[index1]);}
if (true) break;

case 10:
//C
this.state = 9;
index1++;
if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 74;BA.debugLine="Sleep(100)";
anywheresoftware.b4a.keywords.Common.Sleep(mostCurrent.activityBA,this,(int) (100));
this.state = 11;
return;
case 11:
//C
this.state = 4;
;
 //BA.debugLineNum = 75;BA.debugLine="rp.CheckAndRequest(Permission)";
parent._rp.CheckAndRequest(processBA,_permission);
 //BA.debugLineNum = 76;BA.debugLine="Wait For Activity_PermissionResult (Permission A";
anywheresoftware.b4a.keywords.Common.WaitFor("activity_permissionresult", processBA, this, null);
this.state = 12;
return;
case 12:
//C
this.state = 4;
_permission = (String) result[0];
_result = (Boolean) result[1];
;
 //BA.debugLineNum = 77;BA.debugLine="If Result = False Then";
if (true) break;

case 4:
//if
this.state = 7;
if (_result==anywheresoftware.b4a.keywords.Common.False) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 79;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 7:
//C
this.state = 10;
;
 if (true) break;
if (true) break;

case 8:
//C
this.state = -1;
;
 //BA.debugLineNum = 82;BA.debugLine="FusedLocationProvider1.Connect";
parent._fusedlocationprovider1.Connect();
 //BA.debugLineNum = 83;BA.debugLine="TranslateGUI";
_translategui();
 //BA.debugLineNum = 84;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static void  _activity_permissionresult(String _permission,boolean _result) throws Exception{
}
public static String  _btncerrarcheclist_click() throws Exception{
 //BA.debugLineNum = 490;BA.debugLine="Sub btnCerrarChecList_Click";
 //BA.debugLineNum = 491;BA.debugLine="CallSub(ImageDownloader, \"CancelEverything\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"CancelEverything");
 //BA.debugLineNum = 492;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 493;BA.debugLine="Activity.LoadLayout(\"layCheckLists_Home\")";
mostCurrent._activity.LoadLayout("layCheckLists_Home",mostCurrent.activityBA);
 //BA.debugLineNum = 494;BA.debugLine="currentScreen = \"CheckListHome\"";
mostCurrent._currentscreen = "CheckListHome";
 //BA.debugLineNum = 495;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarckecklisthome_click() throws Exception{
 //BA.debugLineNum = 497;BA.debugLine="Sub btnCerrarCkeckListHome_Click";
 //BA.debugLineNum = 498;BA.debugLine="CallSub(ImageDownloader, \"CancelEverything\")";
anywheresoftware.b4a.keywords.Common.CallSubNew(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"CancelEverything");
 //BA.debugLineNum = 499;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 500;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 501;BA.debugLine="End Sub";
return "";
}
public static String  _builditems(ilpla.appear.customlistview _clv) throws Exception{
anywheresoftware.b4a.objects.collections.Map _m = null;
int _i = 0;
anywheresoftware.b4a.objects.PanelWrapper _pa = null;
anywheresoftware.b4a.objects.collections.Map _valuemap = null;
 //BA.debugLineNum = 320;BA.debugLine="Sub BuildItems (clv As CustomListView)";
 //BA.debugLineNum = 321;BA.debugLine="If Imagelinks.Size = 0 Then Return";
if (_imagelinks.getSize()==0) { 
if (true) return "";};
 //BA.debugLineNum = 323;BA.debugLine="clv.Clear";
_clv._clear /*String*/ ();
 //BA.debugLineNum = 324;BA.debugLine="Dim m As Map";
_m = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 325;BA.debugLine="m.Initialize";
_m.Initialize();
 //BA.debugLineNum = 327;BA.debugLine="For i = 0 To Imagelinks.Size - 1";
{
final int step5 = 1;
final int limit5 = (int) (_imagelinks.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 328;BA.debugLine="Dim pA As Panel";
_pa = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 329;BA.debugLine="pA.Initialize(\"\")";
_pa.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 330;BA.debugLine="Dim valueMap As Map";
_valuemap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 331;BA.debugLine="valueMap.Initialize";
_valuemap.Initialize();
 //BA.debugLineNum = 332;BA.debugLine="valueMap.Put(\"nombrecomun\", prefered_common_name";
_valuemap.Put((Object)("nombrecomun"),_prefered_common_namelist.Get(_i));
 //BA.debugLineNum = 333;BA.debugLine="valueMap.Put(\"wikilink\", wikilinkList.Get(i))";
_valuemap.Put((Object)("wikilink"),_wikilinklist.Get(_i));
 //BA.debugLineNum = 334;BA.debugLine="valueMap.Put(\"attribution\", attributionList.Get(";
_valuemap.Put((Object)("attribution"),_attributionlist.Get(_i));
 //BA.debugLineNum = 335;BA.debugLine="valueMap.Put(\"medium_photo\", Imagelinks.Get(i))";
_valuemap.Put((Object)("medium_photo"),_imagelinks.Get(_i));
 //BA.debugLineNum = 336;BA.debugLine="clv.Add(pA, 140dip, valueMap)";
_clv._add /*String*/ (_pa,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (140)),(Object)(_valuemap.getObject()));
 //BA.debugLineNum = 337;BA.debugLine="pA.LoadLayout(\"layCheckList_Item\")";
_pa.LoadLayout("layCheckList_Item",mostCurrent.activityBA);
 //BA.debugLineNum = 338;BA.debugLine="lblNombreComun.Text = prefered_common_nameList.G";
mostCurrent._lblnombrecomun.setText(BA.ObjectToCharSequence(_prefered_common_namelist.Get(_i)));
 //BA.debugLineNum = 339;BA.debugLine="lblNombreCientifico.Text = scientific_nameList.G";
mostCurrent._lblnombrecientifico.setText(BA.ObjectToCharSequence(_scientific_namelist.Get(_i)));
 //BA.debugLineNum = 341;BA.debugLine="If threatList.Get(i) = \"true\" Then";
if ((_threatlist.Get(_i)).equals((Object)("true"))) { 
 //BA.debugLineNum = 342;BA.debugLine="lblThreatIcon.TextColor = Colors.Red";
mostCurrent._lblthreaticon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Red);
 }else if((_threatlist.Get(_i)).equals((Object)("false"))) { 
 //BA.debugLineNum = 344;BA.debugLine="lblThreatIcon.TextColor = Colors.Green";
mostCurrent._lblthreaticon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Green);
 }else {
 //BA.debugLineNum = 346;BA.debugLine="lblThreatIcon.TextColor = Colors.LightGray";
mostCurrent._lblthreaticon.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.LightGray);
 };
 //BA.debugLineNum = 348;BA.debugLine="m.Put(ImageView1, Imagelinks.Get(i))";
_m.Put((Object)(mostCurrent._imageview1.getObject()),_imagelinks.Get(_i));
 }
};
 //BA.debugLineNum = 353;BA.debugLine="CallSubDelayed2(ImageDownloader, \"Download\", m)";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._imagedownloader.getObject()),"Download",(Object)(_m));
 //BA.debugLineNum = 354;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 356;BA.debugLine="End Sub";
return "";
}
public static String  _butaves_click() throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Sub butAves_Click";
 //BA.debugLineNum = 159;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 160;BA.debugLine="ProgressDialogShow2(\"Buscando especies de aves r";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de aves reportadas en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 162;BA.debugLine="ProgressDialogShow2(\"Searching for species of bi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of birds reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 164;BA.debugLine="GetTaxaiNat(\"Aves\")";
_gettaxainat("Aves");
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _butbugs_click() throws Exception{
 //BA.debugLineNum = 166;BA.debugLine="Sub butBugs_Click";
 //BA.debugLineNum = 167;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 168;BA.debugLine="ProgressDialogShow2(\"Buscando especies de invert";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de invertebrados reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 170;BA.debugLine="ProgressDialogShow2(\"Searching for species of in";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of invertebrates reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 172;BA.debugLine="GetTaxaiNat(\"Insecta,Arachnida,Mollusca\")";
_gettaxainat("Insecta,Arachnida,Mollusca");
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return "";
}
public static String  _butfungi_click() throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Sub butFungi_Click";
 //BA.debugLineNum = 176;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 177;BA.debugLine="ProgressDialogShow2(\"Buscando especies de microo";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de microorganismos y hongos reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 179;BA.debugLine="ProgressDialogShow2(\"Searching for species of mi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of microorganisms and fungi reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 181;BA.debugLine="GetTaxaiNat(\"Protozoa,Fungi\")";
_gettaxainat("Protozoa,Fungi");
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public static String  _butmammals_click() throws Exception{
 //BA.debugLineNum = 141;BA.debugLine="Sub butMammals_Click";
 //BA.debugLineNum = 142;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 143;BA.debugLine="ProgressDialogShow2(\"Buscando especies de mamífe";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de mamíferos reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 145;BA.debugLine="ProgressDialogShow2(\"Searching for species of ma";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of mammals reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 148;BA.debugLine="GetTaxaiNat(\"Mammalia\")";
_gettaxainat("Mammalia");
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _butpeces_click() throws Exception{
 //BA.debugLineNum = 150;BA.debugLine="Sub butPeces_Click";
 //BA.debugLineNum = 151;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 152;BA.debugLine="ProgressDialogShow2(\"Buscando especies de peces,";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de peces, reptiles y anfíbios reportados en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 154;BA.debugLine="ProgressDialogShow2(\"Searching for species of fi";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of fishes, reptiles and amphibians reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 156;BA.debugLine="GetTaxaiNat(\"Actinopterigii,Reptilia,Amphibia\")";
_gettaxainat("Actinopterigii,Reptilia,Amphibia");
 //BA.debugLineNum = 157;BA.debugLine="End Sub";
return "";
}
public static String  _butplantas_click() throws Exception{
 //BA.debugLineNum = 184;BA.debugLine="Sub butPlantas_Click";
 //BA.debugLineNum = 185;BA.debugLine="GetTaxaiNat(\"Plantae\")";
_gettaxainat("Plantae");
 //BA.debugLineNum = 186;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 187;BA.debugLine="ProgressDialogShow2(\"Buscando especies de planta";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Buscando especies de plantas reportadas en la zona..."),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 189;BA.debugLine="ProgressDialogShow2(\"Searching for species of pl";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow2(mostCurrent.activityBA,BA.ObjectToCharSequence("Searching for species of plants reported in the area..."),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _clvchecklist_itemclick(int _index,Object _value) throws Exception{
anywheresoftware.b4a.objects.collections.Map _valuemap = null;
String _msg = "";
anywheresoftware.b4a.phone.Phone.PhoneIntents _pi = null;
 //BA.debugLineNum = 358;BA.debugLine="Sub clvCheckList_ItemClick (Index As Int, Value As";
 //BA.debugLineNum = 360;BA.debugLine="Dim valuemap As Map";
_valuemap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 361;BA.debugLine="valuemap.Initialize";
_valuemap.Initialize();
 //BA.debugLineNum = 362;BA.debugLine="valuemap = Value";
_valuemap = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_value));
 //BA.debugLineNum = 365;BA.debugLine="Dim msg As String";
_msg = "";
 //BA.debugLineNum = 366;BA.debugLine="msg = Msgbox2(\"Desea más información sobre este o";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2(BA.ObjectToCharSequence("Desea más información sobre este organismo?"),BA.ObjectToCharSequence(_valuemap.Get((Object)("nombrecomun"))),"Si, abre Wikipedia","No gracias","",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 //BA.debugLineNum = 367;BA.debugLine="If msg = DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 368;BA.debugLine="Dim pi As PhoneIntents";
_pi = new anywheresoftware.b4a.phone.Phone.PhoneIntents();
 //BA.debugLineNum = 369;BA.debugLine="StartActivity(pi.OpenBrowser(valuemap.Get(\"wikil";
anywheresoftware.b4a.keywords.Common.StartActivity(processBA,(Object)(_pi.OpenBrowser(BA.ObjectToString(_valuemap.Get((Object)("wikilink"))))));
 };
 //BA.debugLineNum = 373;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionfailed(int _connectionresult1) throws Exception{
 //BA.debugLineNum = 402;BA.debugLine="Sub FusedLocationProvider1_ConnectionFailed(Connec";
 //BA.debugLineNum = 403;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionFailed\")";
anywheresoftware.b4a.keywords.Common.LogImpl("744236801","FusedLocationProvider1_ConnectionFailed",0);
 //BA.debugLineNum = 405;BA.debugLine="Select ConnectionResult1";
switch (BA.switchObjectToInt(_connectionresult1,_fusedlocationprovider1.ConnectionResult.NETWORK_ERROR)) {
case 0: {
 //BA.debugLineNum = 407;BA.debugLine="FusedLocationProvider1.Connect";
_fusedlocationprovider1.Connect();
 break; }
default: {
 break; }
}
;
 //BA.debugLineNum = 411;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuccess() throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest _locationrequest1 = null;
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder _locationsettingsrequestbuilder1 = null;
 //BA.debugLineNum = 412;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuccess";
 //BA.debugLineNum = 413;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuccess\")";
anywheresoftware.b4a.keywords.Common.LogImpl("744302337","FusedLocationProvider1_ConnectionSuccess",0);
 //BA.debugLineNum = 414;BA.debugLine="Dim LocationRequest1 As LocationRequest";
_locationrequest1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationRequest();
 //BA.debugLineNum = 415;BA.debugLine="LocationRequest1.Initialize";
_locationrequest1.Initialize();
 //BA.debugLineNum = 416;BA.debugLine="LocationRequest1.SetInterval(1000)	'	1000 millise";
_locationrequest1.SetInterval((long) (1000));
 //BA.debugLineNum = 417;BA.debugLine="LocationRequest1.SetPriority(LocationRequest1.Pri";
_locationrequest1.SetPriority(_locationrequest1.Priority.PRIORITY_HIGH_ACCURACY);
 //BA.debugLineNum = 418;BA.debugLine="LocationRequest1.SetSmallestDisplacement(1)	'	1 m";
_locationrequest1.SetSmallestDisplacement((float) (1));
 //BA.debugLineNum = 420;BA.debugLine="Dim LocationSettingsRequestBuilder1 As LocationSe";
_locationsettingsrequestbuilder1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsRequestBuilder();
 //BA.debugLineNum = 421;BA.debugLine="LocationSettingsRequestBuilder1.Initialize";
_locationsettingsrequestbuilder1.Initialize();
 //BA.debugLineNum = 422;BA.debugLine="LocationSettingsRequestBuilder1.AddLocationReques";
_locationsettingsrequestbuilder1.AddLocationRequest((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 423;BA.debugLine="FusedLocationProvider1.CheckLocationSettings(Loca";
_fusedlocationprovider1.CheckLocationSettings(_locationsettingsrequestbuilder1.Build());
 //BA.debugLineNum = 425;BA.debugLine="FusedLocationProvider1.RequestLocationUpdates(Loc";
_fusedlocationprovider1.RequestLocationUpdates((com.google.android.gms.location.LocationRequest)(_locationrequest1.getObject()));
 //BA.debugLineNum = 426;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_connectionsuspended(int _suspendedcause1) throws Exception{
 //BA.debugLineNum = 427;BA.debugLine="Sub FusedLocationProvider1_ConnectionSuspended(Sus";
 //BA.debugLineNum = 428;BA.debugLine="Log(\"FusedLocationProvider1_ConnectionSuspended\")";
anywheresoftware.b4a.keywords.Common.LogImpl("744367873","FusedLocationProvider1_ConnectionSuspended",0);
 //BA.debugLineNum = 429;BA.debugLine="Select SuspendedCause1";
switch (BA.switchObjectToInt(_suspendedcause1,_fusedlocationprovider1.SuspendedCause.CAUSE_NETWORK_LOST,_fusedlocationprovider1.SuspendedCause.CAUSE_SERVICE_DISCONNECTED)) {
case 0: {
 break; }
case 1: {
 break; }
}
;
 //BA.debugLineNum = 435;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationchanged(anywheresoftware.b4a.gps.LocationWrapper _location1) throws Exception{
 //BA.debugLineNum = 436;BA.debugLine="Sub FusedLocationProvider1_LocationChanged(Locatio";
 //BA.debugLineNum = 437;BA.debugLine="Log(\"FusedLocationProvider1_LocationChanged\")";
anywheresoftware.b4a.keywords.Common.LogImpl("744433409","FusedLocationProvider1_LocationChanged",0);
 //BA.debugLineNum = 438;BA.debugLine="LastLocation=Location1";
_lastlocation = _location1;
 //BA.debugLineNum = 439;BA.debugLine="Log(\"Lat: \" & LastLocation.Latitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("744433411","Lat: "+BA.NumberToString(_lastlocation.getLatitude()),0);
 //BA.debugLineNum = 440;BA.debugLine="Log(\"Lng: \" & LastLocation.Longitude)";
anywheresoftware.b4a.keywords.Common.LogImpl("744433412","Lng: "+BA.NumberToString(_lastlocation.getLongitude()),0);
 //BA.debugLineNum = 441;BA.debugLine="lblLat.Text = LastLocation.Latitude";
mostCurrent._lbllat.setText(BA.ObjectToCharSequence(_lastlocation.getLatitude()));
 //BA.debugLineNum = 442;BA.debugLine="lblLon.Text = LastLocation.Longitude";
mostCurrent._lbllon.setText(BA.ObjectToCharSequence(_lastlocation.getLongitude()));
 //BA.debugLineNum = 443;BA.debugLine="End Sub";
return "";
}
public static String  _fusedlocationprovider1_locationsettingschecked(uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsResult _locationsettingsresult1) throws Exception{
uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus _locationsettingsstatus1 = null;
 //BA.debugLineNum = 444;BA.debugLine="Sub FusedLocationProvider1_LocationSettingsChecked";
 //BA.debugLineNum = 445;BA.debugLine="Log(\"FusedLocationProvider1_LocationSettingsCheck";
anywheresoftware.b4a.keywords.Common.LogImpl("744498945","FusedLocationProvider1_LocationSettingsChecked",0);
 //BA.debugLineNum = 446;BA.debugLine="Dim LocationSettingsStatus1 As LocationSettingsSt";
_locationsettingsstatus1 = new uk.co.martinpearman.b4a.fusedlocationprovider.LocationSettingsStatus();
_locationsettingsstatus1 = _locationsettingsresult1.GetLocationSettingsStatus();
 //BA.debugLineNum = 447;BA.debugLine="Select LocationSettingsStatus1.GetStatusCode";
switch (BA.switchObjectToInt(_locationsettingsstatus1.GetStatusCode(),_locationsettingsstatus1.StatusCodes.RESOLUTION_REQUIRED,_locationsettingsstatus1.StatusCodes.SETTINGS_CHANGE_UNAVAILABLE,_locationsettingsstatus1.StatusCodes.SUCCESS)) {
case 0: {
 //BA.debugLineNum = 449;BA.debugLine="Log(\"RESOLUTION_REQUIRED\")";
anywheresoftware.b4a.keywords.Common.LogImpl("744498949","RESOLUTION_REQUIRED",0);
 //BA.debugLineNum = 452;BA.debugLine="LocationSettingsStatus1.StartResolutionDialog(\"";
_locationsettingsstatus1.StartResolutionDialog(mostCurrent.activityBA,"LocationSettingsResult1");
 break; }
case 1: {
 //BA.debugLineNum = 454;BA.debugLine="Log(\"SETTINGS_CHANGE_UNAVAILABLE\")";
anywheresoftware.b4a.keywords.Common.LogImpl("744498954","SETTINGS_CHANGE_UNAVAILABLE",0);
 //BA.debugLineNum = 457;BA.debugLine="Msgbox(\"Error, tu dispositivo no tiene localiza";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Error, tu dispositivo no tiene localización. Busca tu posición en el mapa!"),BA.ObjectToCharSequence("Problem"),mostCurrent.activityBA);
 //BA.debugLineNum = 458;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 break; }
case 2: {
 //BA.debugLineNum = 460;BA.debugLine="Log(\"SUCCESS\")";
anywheresoftware.b4a.keywords.Common.LogImpl("744498960","SUCCESS",0);
 break; }
}
;
 //BA.debugLineNum = 464;BA.debugLine="End Sub";
return "";
}
public static String  _getinattaxa_complete(ilpla.appear.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _json = null;
anywheresoftware.b4a.objects.collections.Map _map1 = null;
anywheresoftware.b4a.objects.collections.Map _ma = null;
anywheresoftware.b4a.objects.collections.Map _msp = null;
anywheresoftware.b4a.objects.collections.Map _mimg = null;
anywheresoftware.b4a.objects.collections.List _menuitems = null;
int _i = 0;
String _iconicname = "";
String _prefered_common_name = "";
String _threatlevel = "";
String _scientificname = "";
String _wikilink = "";
String _attribution = "";
 //BA.debugLineNum = 271;BA.debugLine="Sub GetiNatTaxa_Complete(Job As HttpJob)";
 //BA.debugLineNum = 272;BA.debugLine="Log(\"GetiNatTaxa messages: \" & Job.Success)";
anywheresoftware.b4a.keywords.Common.LogImpl("743974657","GetiNatTaxa messages: "+BA.ObjectToString(_job._success /*boolean*/ ),0);
 //BA.debugLineNum = 273;BA.debugLine="If Job.Success = True Then";
if (_job._success /*boolean*/ ==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 274;BA.debugLine="Dim JSON As JSONParser";
_json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 275;BA.debugLine="Dim Map1 As Map";
_map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 276;BA.debugLine="JSON.Initialize(Job.GetString)";
_json.Initialize(_job._getstring /*String*/ ());
 //BA.debugLineNum = 277;BA.debugLine="Map1 = JSON.NextObject";
_map1 = _json.NextObject();
 //BA.debugLineNum = 278;BA.debugLine="Dim ma As Map 'helper map for navigating results";
_ma = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 279;BA.debugLine="Dim msp As Map ' map para la especie";
_msp = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 280;BA.debugLine="Dim mimg As Map 'map para la imagen";
_mimg = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 281;BA.debugLine="Dim MenuItems As List";
_menuitems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 282;BA.debugLine="MenuItems = Map1.Get(\"results\")";
_menuitems = (anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(_map1.Get((Object)("results"))));
 //BA.debugLineNum = 283;BA.debugLine="For i = 0 To MenuItems.Size - 1";
{
final int step12 = 1;
final int limit12 = (int) (_menuitems.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit12 ;_i = _i + step12 ) {
 //BA.debugLineNum = 284;BA.debugLine="ma = MenuItems.Get(i)";
_ma = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_menuitems.Get(_i)));
 //BA.debugLineNum = 286;BA.debugLine="msp = ma.Get(\"taxon\")";
_msp = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_ma.Get((Object)("taxon"))));
 //BA.debugLineNum = 287;BA.debugLine="mimg = msp.Get(\"default_photo\")";
_mimg = (anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (anywheresoftware.b4a.objects.collections.Map.MyMap)(_msp.Get((Object)("default_photo"))));
 //BA.debugLineNum = 288;BA.debugLine="Dim iconicname As String";
_iconicname = "";
 //BA.debugLineNum = 289;BA.debugLine="Dim prefered_common_name As String";
_prefered_common_name = "";
 //BA.debugLineNum = 290;BA.debugLine="Dim threatlevel As String";
_threatlevel = "";
 //BA.debugLineNum = 291;BA.debugLine="Dim scientificname As String";
_scientificname = "";
 //BA.debugLineNum = 292;BA.debugLine="Dim wikilink As String";
_wikilink = "";
 //BA.debugLineNum = 293;BA.debugLine="Dim attribution As String";
_attribution = "";
 //BA.debugLineNum = 294;BA.debugLine="iconicname = msp.Get(\"iconic_taxon_name\")";
_iconicname = BA.ObjectToString(_msp.Get((Object)("iconic_taxon_name")));
 //BA.debugLineNum = 295;BA.debugLine="prefered_common_name = msp.Get(\"preferred_commo";
_prefered_common_name = BA.ObjectToString(_msp.Get((Object)("preferred_common_name")));
 //BA.debugLineNum = 296;BA.debugLine="threatlevel = msp.Get(\"threatened\")";
_threatlevel = BA.ObjectToString(_msp.Get((Object)("threatened")));
 //BA.debugLineNum = 297;BA.debugLine="scientificname = msp.Get(\"name\")";
_scientificname = BA.ObjectToString(_msp.Get((Object)("name")));
 //BA.debugLineNum = 298;BA.debugLine="wikilink = msp.Get(\"wikipedia_url\")";
_wikilink = BA.ObjectToString(_msp.Get((Object)("wikipedia_url")));
 //BA.debugLineNum = 299;BA.debugLine="attribution  = mimg.Get(\"attribution\")";
_attribution = BA.ObjectToString(_mimg.Get((Object)("attribution")));
 //BA.debugLineNum = 300;BA.debugLine="If prefered_common_name = \"null\" Then";
if ((_prefered_common_name).equals("null")) { 
 //BA.debugLineNum = 301;BA.debugLine="prefered_common_name = msp.Get(\"name\")";
_prefered_common_name = BA.ObjectToString(_msp.Get((Object)("name")));
 };
 //BA.debugLineNum = 303;BA.debugLine="Imagelinks.Add(mimg.Get(\"medium_url\"))";
_imagelinks.Add(_mimg.Get((Object)("medium_url")));
 //BA.debugLineNum = 304;BA.debugLine="prefered_common_nameList.Add(prefered_common_na";
_prefered_common_namelist.Add((Object)(_prefered_common_name));
 //BA.debugLineNum = 305;BA.debugLine="iconicnameList.Add(iconicname)";
_iconicnamelist.Add((Object)(_iconicname));
 //BA.debugLineNum = 306;BA.debugLine="threatList.Add(threatlevel)";
_threatlist.Add((Object)(_threatlevel));
 //BA.debugLineNum = 307;BA.debugLine="scientific_nameList.Add(scientificname)";
_scientific_namelist.Add((Object)(_scientificname));
 //BA.debugLineNum = 308;BA.debugLine="wikilinkList.Add(wikilink)";
_wikilinklist.Add((Object)(_wikilink));
 //BA.debugLineNum = 309;BA.debugLine="attributionList.Add(attribution)";
_attributionlist.Add((Object)(_attribution));
 }
};
 //BA.debugLineNum = 312;BA.debugLine="BuildItems(clvCheckList)";
_builditems(mostCurrent._clvchecklist);
 }else {
 //BA.debugLineNum = 314;BA.debugLine="Log(Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.LogImpl("743974699",_job._errormessage /*String*/ ,0);
 };
 //BA.debugLineNum = 316;BA.debugLine="End Sub";
return "";
}
public static String  _gettaxainat(String _taxa) throws Exception{
ilpla.appear.downloadservice._downloaddata _dd = null;
 //BA.debugLineNum = 194;BA.debugLine="Sub GetTaxaiNat(taxa As String)";
 //BA.debugLineNum = 195;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 196;BA.debugLine="Activity.LoadLayout(\"layCheckList\")";
mostCurrent._activity.LoadLayout("layCheckList",mostCurrent.activityBA);
 //BA.debugLineNum = 197;BA.debugLine="currentScreen = \"CheckListList\"";
mostCurrent._currentscreen = "CheckListList";
 //BA.debugLineNum = 198;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 199;BA.debugLine="If taxa = \"Mammalia\" Then";
if ((_taxa).equals("Mammalia")) { 
 //BA.debugLineNum = 200;BA.debugLine="lblTituloCheckList.Text = \"Mamíferos\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Mamíferos"));
 }else if((_taxa).equals("Actinopterigii,Reptilia,Amphibia")) { 
 //BA.debugLineNum = 202;BA.debugLine="lblTituloCheckList.Text = \"Peces, reptiles y an";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Peces, reptiles y anfíbios"));
 }else if((_taxa).equals("Aves")) { 
 //BA.debugLineNum = 204;BA.debugLine="lblTituloCheckList.Text = taxa";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence(_taxa));
 }else if((_taxa).equals("Insecta,Arachnida,Mollusca")) { 
 //BA.debugLineNum = 206;BA.debugLine="lblTituloCheckList.Text = \"Invertebrados\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Invertebrados"));
 }else if((_taxa).equals("Protozoa,Fungi")) { 
 //BA.debugLineNum = 208;BA.debugLine="lblTituloCheckList.Text = \"Microorganismos y ho";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Microorganismos y hongos"));
 }else if((_taxa).equals("Plantae")) { 
 //BA.debugLineNum = 210;BA.debugLine="lblTituloCheckList.Text = \"Plantas\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Plantas"));
 };
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 213;BA.debugLine="If taxa = \"Mammalia\" Then";
if ((_taxa).equals("Mammalia")) { 
 //BA.debugLineNum = 214;BA.debugLine="lblTituloCheckList.Text = \"Mammals\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Mammals"));
 }else if((_taxa).equals("Actinopterigii,Reptilia,Amphibia")) { 
 //BA.debugLineNum = 216;BA.debugLine="lblTituloCheckList.Text = \"Fish, reptiles & amp";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Fish, reptiles & amphibians"));
 }else if((_taxa).equals("Aves")) { 
 //BA.debugLineNum = 218;BA.debugLine="lblTituloCheckList.Text = \"Birds\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Birds"));
 }else if((_taxa).equals("Insecta,Arachnida,Mollusca")) { 
 //BA.debugLineNum = 220;BA.debugLine="lblTituloCheckList.Text = \"Invertebrates\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Invertebrates"));
 }else if((_taxa).equals("Protozoa,Fungi")) { 
 //BA.debugLineNum = 222;BA.debugLine="lblTituloCheckList.Text = \"Microorganisms & fun";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Microorganisms & fungi"));
 }else if((_taxa).equals("Plantae")) { 
 //BA.debugLineNum = 224;BA.debugLine="lblTituloCheckList.Text = \"Plants\"";
mostCurrent._lbltitulochecklist.setText(BA.ObjectToCharSequence("Plants"));
 };
 };
 //BA.debugLineNum = 231;BA.debugLine="LastLocation = FusedLocationProvider1.GetLastKnow";
_lastlocation = _fusedlocationprovider1.GetLastKnownLocation();
 //BA.debugLineNum = 232;BA.debugLine="If LastLocation.IsInitialized Then";
if (_lastlocation.IsInitialized()) { 
 //BA.debugLineNum = 234;BA.debugLine="nelat = LastLocation.Latitude + 0.1";
mostCurrent._nelat = BA.NumberToString(_lastlocation.getLatitude()+0.1);
 //BA.debugLineNum = 235;BA.debugLine="nelng = LastLocation.Longitude + 0.1";
mostCurrent._nelng = BA.NumberToString(_lastlocation.getLongitude()+0.1);
 //BA.debugLineNum = 236;BA.debugLine="swlat = LastLocation.Latitude - 0.1";
mostCurrent._swlat = BA.NumberToString(_lastlocation.getLatitude()-0.1);
 //BA.debugLineNum = 237;BA.debugLine="swlng = LastLocation.Longitude - 0.1";
mostCurrent._swlng = BA.NumberToString(_lastlocation.getLongitude()-0.1);
 }else {
 //BA.debugLineNum = 239;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 244;BA.debugLine="ImageViews.Initialize";
mostCurrent._imageviews.Initialize();
 //BA.debugLineNum = 245;BA.debugLine="prefered_common_nameList.Initialize";
_prefered_common_namelist.Initialize();
 //BA.debugLineNum = 246;BA.debugLine="iconicnameList.Initialize";
_iconicnamelist.Initialize();
 //BA.debugLineNum = 247;BA.debugLine="threatList.Initialize";
_threatlist.Initialize();
 //BA.debugLineNum = 248;BA.debugLine="scientific_nameList.Initialize";
_scientific_namelist.Initialize();
 //BA.debugLineNum = 249;BA.debugLine="wikilinkList.Initialize";
_wikilinklist.Initialize();
 //BA.debugLineNum = 250;BA.debugLine="attributionList.Initialize";
_attributionlist.Initialize();
 //BA.debugLineNum = 251;BA.debugLine="Imagelinks.Initialize";
_imagelinks.Initialize();
 //BA.debugLineNum = 252;BA.debugLine="clvCheckList.Initialize(Me, \"clvCheckList\")";
mostCurrent._clvchecklist._initialize /*String*/ (mostCurrent.activityBA,inatcheck.getObject(),"clvCheckList");
 //BA.debugLineNum = 253;BA.debugLine="pnlCheckList.AddView(clvCheckList.AsView, 0, 0, 1";
mostCurrent._pnlchecklist.AddView((android.view.View)(mostCurrent._clvchecklist._asview /*anywheresoftware.b4a.objects.ConcreteViewWrapper*/ ().getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 263;BA.debugLine="Dim dd As DownloadData";
_dd = new ilpla.appear.downloadservice._downloaddata();
 //BA.debugLineNum = 264;BA.debugLine="dd.url = \"https://api.inaturalist.org/v1/observat";
_dd.url /*String*/  = "https://api.inaturalist.org/v1/observations/species_counts?preferred_place_id=7190&locale=es&iconic_taxa="+_taxa+"&quality_grade=research&reviewed=true&nelat="+mostCurrent._nelat+"&nelng="+mostCurrent._nelng+"&swlat="+mostCurrent._swlat+"&swlng="+mostCurrent._swlng;
 //BA.debugLineNum = 265;BA.debugLine="dd.EventName = \"GetiNatTaxa\"";
_dd.EventName /*String*/  = "GetiNatTaxa";
 //BA.debugLineNum = 266;BA.debugLine="dd.Target = Me";
_dd.Target /*Object*/  = inatcheck.getObject();
 //BA.debugLineNum = 267;BA.debugLine="CallSubDelayed2(DownloadService, \"StartDownload\",";
anywheresoftware.b4a.keywords.Common.CallSubDelayed2(processBA,(Object)(mostCurrent._downloadservice.getObject()),"StartDownload",(Object)(_dd));
 //BA.debugLineNum = 270;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 20;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 23;BA.debugLine="Private lblTituloCheckList As Label";
mostCurrent._lbltitulochecklist = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblThreatIcon As Label";
mostCurrent._lblthreaticon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private currentScreen As String";
mostCurrent._currentscreen = "";
 //BA.debugLineNum = 26;BA.debugLine="Private butPlantas As Button";
mostCurrent._butplantas = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private butFungi As Button";
mostCurrent._butfungi = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private butAves As Button";
mostCurrent._butaves = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private butBugs As Button";
mostCurrent._butbugs = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private butPeces As Button";
mostCurrent._butpeces = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private butMammals As Button";
mostCurrent._butmammals = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Dim nelat As String";
mostCurrent._nelat = "";
 //BA.debugLineNum = 35;BA.debugLine="Dim nelng As String";
mostCurrent._nelng = "";
 //BA.debugLineNum = 36;BA.debugLine="Dim swlat As String";
mostCurrent._swlat = "";
 //BA.debugLineNum = 37;BA.debugLine="Dim swlng As String";
mostCurrent._swlng = "";
 //BA.debugLineNum = 40;BA.debugLine="Dim clvCheckList As CustomListView";
mostCurrent._clvchecklist = new ilpla.appear.customlistview();
 //BA.debugLineNum = 41;BA.debugLine="Private pnlCheckList As Panel";
mostCurrent._pnlchecklist = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Dim phone1 As Phone";
mostCurrent._phone1 = new anywheresoftware.b4a.phone.Phone();
 //BA.debugLineNum = 43;BA.debugLine="Dim lblNombreComun As Label";
mostCurrent._lblnombrecomun = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Dim lblNombreCientifico As Label";
mostCurrent._lblnombrecientifico = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Dim ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Dim ImageViews As List";
mostCurrent._imageviews = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 50;BA.debugLine="Dim lblLat As Label";
mostCurrent._lbllat = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 51;BA.debugLine="Dim lblLon As Label";
mostCurrent._lbllon = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static String  _lblthreaticon_click() throws Exception{
 //BA.debugLineNum = 384;BA.debugLine="Sub lblThreatIcon_Click";
 //BA.debugLineNum = 385;BA.debugLine="If lblThreatIcon.TextColor = Colors.Red Then";
if (mostCurrent._lblthreaticon.getTextColor()==anywheresoftware.b4a.keywords.Common.Colors.Red) { 
 //BA.debugLineNum = 386;BA.debugLine="Msgbox(\"Este organismo se encuentra amenazado o";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Este organismo se encuentra amenazado o en peligro de extinción!"),BA.ObjectToCharSequence("Nivel de amenaza"),mostCurrent.activityBA);
 }else if(mostCurrent._lblthreaticon.getTextColor()==anywheresoftware.b4a.keywords.Common.Colors.Green) { 
 //BA.debugLineNum = 388;BA.debugLine="Msgbox(\"Este organismo no se encuentra amenazado";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("Este organismo no se encuentra amenazado"),BA.ObjectToCharSequence("Nivel de amenaza"),mostCurrent.activityBA);
 }else if(mostCurrent._lblthreaticon.getTextColor()==anywheresoftware.b4a.keywords.Common.Colors.LightGray) { 
 //BA.debugLineNum = 390;BA.debugLine="Msgbox(\"No se conoce el nivel de amenaza de exti";
anywheresoftware.b4a.keywords.Common.Msgbox(BA.ObjectToCharSequence("No se conoce el nivel de amenaza de extinción de este organismo"),BA.ObjectToCharSequence("Nivel de amenaza"),mostCurrent.activityBA);
 };
 //BA.debugLineNum = 392;BA.debugLine="End Sub";
return "";
}
public static String  _locationsettingsresult1_resolutiondialogdismissed(boolean _locationsettingsupdated) throws Exception{
 //BA.debugLineNum = 465;BA.debugLine="Sub LocationSettingsResult1_ResolutionDialogDismis";
 //BA.debugLineNum = 466;BA.debugLine="Log(\"LocationSettingsResult1_ResolutionDialogDism";
anywheresoftware.b4a.keywords.Common.LogImpl("744564481","LocationSettingsResult1_ResolutionDialogDismissed",0);
 //BA.debugLineNum = 467;BA.debugLine="If Not(LocationSettingsUpdated) Then";
if (anywheresoftware.b4a.keywords.Common.Not(_locationsettingsupdated)) { 
 //BA.debugLineNum = 476;BA.debugLine="If FusedLocationProvider1.IsConnected = True The";
if (_fusedlocationprovider1.IsConnected()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 477;BA.debugLine="FusedLocationProvider1.DisConnect";
_fusedlocationprovider1.Disconnect();
 };
 };
 //BA.debugLineNum = 480;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 4;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Private Imagelinks As List";
_imagelinks = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 7;BA.debugLine="Private prefered_common_nameList As List";
_prefered_common_namelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 8;BA.debugLine="Private iconicnameList As List";
_iconicnamelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 9;BA.debugLine="Private scientific_nameList As List";
_scientific_namelist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 10;BA.debugLine="Private wikilinkList As List";
_wikilinklist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 11;BA.debugLine="Private threatList As List";
_threatlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 12;BA.debugLine="Private attributionList As List";
_attributionlist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 15;BA.debugLine="Private FusedLocationProvider1 As FusedLocationPr";
_fusedlocationprovider1 = new uk.co.martinpearman.b4a.fusedlocationprovider.FusedLocationProviderWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private LastLocation As Location";
_lastlocation = new anywheresoftware.b4a.gps.LocationWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim rp As RuntimePermissions";
_rp = new anywheresoftware.b4a.objects.RuntimePermissions();
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public static String  _translategui() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub TranslateGUI";
 //BA.debugLineNum = 115;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang /*String*/ ).equals("es")) { 
 //BA.debugLineNum = 116;BA.debugLine="butAves.Text = \"Aves\"";
mostCurrent._butaves.setText(BA.ObjectToCharSequence("Aves"));
 //BA.debugLineNum = 117;BA.debugLine="butBugs.Text = \"Invertebrados\"";
mostCurrent._butbugs.setText(BA.ObjectToCharSequence("Invertebrados"));
 //BA.debugLineNum = 118;BA.debugLine="butFungi.Text = \"Hongos\"";
mostCurrent._butfungi.setText(BA.ObjectToCharSequence("Hongos"));
 //BA.debugLineNum = 119;BA.debugLine="butMammals.Text = \"Mamíferos\"";
mostCurrent._butmammals.setText(BA.ObjectToCharSequence("Mamíferos"));
 //BA.debugLineNum = 120;BA.debugLine="butPeces.Text = \"Peces, reptiles y anfíbios\"";
mostCurrent._butpeces.setText(BA.ObjectToCharSequence("Peces, reptiles y anfíbios"));
 //BA.debugLineNum = 121;BA.debugLine="butPlantas.Text = \"Plantas\"";
mostCurrent._butplantas.setText(BA.ObjectToCharSequence("Plantas"));
 }else if((mostCurrent._main._lang /*String*/ ).equals("en")) { 
 //BA.debugLineNum = 123;BA.debugLine="butAves.Text = \"Birds\"";
mostCurrent._butaves.setText(BA.ObjectToCharSequence("Birds"));
 //BA.debugLineNum = 124;BA.debugLine="butBugs.Text = \"Invertebrates\"";
mostCurrent._butbugs.setText(BA.ObjectToCharSequence("Invertebrates"));
 //BA.debugLineNum = 125;BA.debugLine="butFungi.Text = \"Fungi\"";
mostCurrent._butfungi.setText(BA.ObjectToCharSequence("Fungi"));
 //BA.debugLineNum = 126;BA.debugLine="butMammals.Text = \"Mammals\"";
mostCurrent._butmammals.setText(BA.ObjectToCharSequence("Mammals"));
 //BA.debugLineNum = 127;BA.debugLine="butPeces.Text = \"Fish, reptiles & amphibians\"";
mostCurrent._butpeces.setText(BA.ObjectToCharSequence("Fish, reptiles & amphibians"));
 //BA.debugLineNum = 128;BA.debugLine="butPlantas.Text = \"Plants\"";
mostCurrent._butplantas.setText(BA.ObjectToCharSequence("Plants"));
 };
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
}
