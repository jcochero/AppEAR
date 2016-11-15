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

public class frmcamara extends Activity implements B4AActivity{
	public static frmcamara mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmcamara");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmcamara).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmcamara");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmcamara", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmcamara) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmcamara) Resume **");
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
		return frmcamara.class;
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
        BA.LogInfo("** Activity (frmcamara) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmcamara) Resume **");
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
public static boolean _sacandofoto = false;
public static anywheresoftware.b4a.phone.Phone.ContentChooser _cc = null;
public xvs.ACL.ACL _camera1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntakepicture = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public static String _newfilename = "";
public static int _fotonumlibre = 0;
public anywheresoftware.b4a.objects.LabelWrapper _lbltitulo = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinstruccion = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _imageview1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnadjuntarfoto = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btntomarfoto = null;
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
 //BA.debugLineNum = 30;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 31;BA.debugLine="Activity.LoadLayout(\"camera\")";
mostCurrent._activity.LoadLayout("camera",mostCurrent.activityBA);
 //BA.debugLineNum = 32;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 34;BA.debugLine="If File.ExternalWritable = True Then";
if (anywheresoftware.b4a.keywords.Common.File.getExternalWritable()==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 35;BA.debugLine="If File.IsDirectory(File.DirRootEx";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"AppEAR/")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 36;BA.debugLine="File.MakeDir(File.DirRootExter";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirRootExternal(),"AppEAR");
 };
 }else {
 //BA.debugLineNum = 39;BA.debugLine="If File.IsDirectory(File.DirIntern";
if (anywheresoftware.b4a.keywords.Common.File.IsDirectory(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"AppEAR/")==anywheresoftware.b4a.keywords.Common.False) { 
 //BA.debugLineNum = 40;BA.debugLine="File.MakeDir(File.DirInternal,";
anywheresoftware.b4a.keywords.Common.File.MakeDir(anywheresoftware.b4a.keywords.Common.File.getDirInternal(),"AppEAR");
 };
 };
 //BA.debugLineNum = 46;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 48;BA.debugLine="If Main.fotopath0 = \"\" Then";
if ((mostCurrent._main._fotopath0).equals("")) { 
 //BA.debugLineNum = 49;BA.debugLine="fotonumlibre = 1";
_fotonumlibre = (int) (1);
 //BA.debugLineNum = 50;BA.debugLine="newfilename = Main.username & \"@\" & frmEvaluacio";
mostCurrent._newfilename = mostCurrent._main._username+"@"+mostCurrent._frmevaluacion._currentproject+"@"+mostCurrent._main._nombrerio+"-"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+"_"+BA.NumberToString(_fotonumlibre);
 //BA.debugLineNum = 51;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 52;BA.debugLine="lblTitulo.Text = \"Fotografía general #1\"";
mostCurrent._lbltitulo.setText((Object)("Fotografía general #1"));
 //BA.debugLineNum = 53;BA.debugLine="lblInstruccion.Text = \"En esta fotografía inten";
mostCurrent._lblinstruccion.setText((Object)("En esta fotografía intenta tomar una vista lo más completa del sitio que puedas"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 55;BA.debugLine="lblTitulo.Text = \"General photo #1\"";
mostCurrent._lbltitulo.setText((Object)("General photo #1"));
 //BA.debugLineNum = 56;BA.debugLine="lblInstruccion.Text = \"In this photo, try to ta";
mostCurrent._lblinstruccion.setText((Object)("In this photo, try to take a wide shot of the site"));
 };
 //BA.debugLineNum = 59;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fotoGral.png").getObject()));
 }else if((mostCurrent._main._fotopath1).equals("")) { 
 //BA.debugLineNum = 61;BA.debugLine="fotonumlibre = 2";
_fotonumlibre = (int) (2);
 //BA.debugLineNum = 62;BA.debugLine="newfilename = Main.username & \"@\" & frmEvaluacio";
mostCurrent._newfilename = mostCurrent._main._username+"@"+mostCurrent._frmevaluacion._currentproject+"@"+mostCurrent._main._nombrerio+"-"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+"_"+BA.NumberToString(_fotonumlibre);
 //BA.debugLineNum = 63;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 64;BA.debugLine="lblTitulo.Text = \"Fotografía general\"";
mostCurrent._lbltitulo.setText((Object)("Fotografía general"));
 //BA.debugLineNum = 65;BA.debugLine="lblInstruccion.Text = \"En esta fotografía tambi";
mostCurrent._lblinstruccion.setText((Object)("En esta fotografía también intenta tomar una vista lo más completa del sitio que puedas, desde otro ángulo que la anterior"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 67;BA.debugLine="lblTitulo.Text = \"General photo #2\"";
mostCurrent._lbltitulo.setText((Object)("General photo #2"));
 //BA.debugLineNum = 68;BA.debugLine="lblInstruccion.Text = \"In this photo, try to ta";
mostCurrent._lblinstruccion.setText((Object)("In this photo, try to take another wide shot of the site, from a different perspective"));
 };
 //BA.debugLineNum = 70;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fotoGral.png").getObject()));
 }else if((mostCurrent._main._fotopath2).equals("")) { 
 //BA.debugLineNum = 72;BA.debugLine="fotonumlibre = 3";
_fotonumlibre = (int) (3);
 //BA.debugLineNum = 73;BA.debugLine="newfilename = Main.username & \"@\" & frmEvaluacio";
mostCurrent._newfilename = mostCurrent._main._username+"@"+mostCurrent._frmevaluacion._currentproject+"@"+mostCurrent._main._nombrerio+"-"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+"_"+BA.NumberToString(_fotonumlibre);
 //BA.debugLineNum = 75;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 76;BA.debugLine="lblTitulo.Text = \"Fotografía del agua\"";
mostCurrent._lbltitulo.setText((Object)("Fotografía del agua"));
 //BA.debugLineNum = 77;BA.debugLine="lblInstruccion.Text = \"En esta foto trata de ca";
mostCurrent._lblinstruccion.setText((Object)("En esta foto trata de capturar el color del agua y su transparencia"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 79;BA.debugLine="lblTitulo.Text = \"Water photo\"";
mostCurrent._lbltitulo.setText((Object)("Water photo"));
 //BA.debugLineNum = 80;BA.debugLine="lblInstruccion.Text = \"In this photo, try to ta";
mostCurrent._lblinstruccion.setText((Object)("In this photo, try to take a photo of the water and its colour"));
 };
 //BA.debugLineNum = 82;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fotoEspecifico1.png").getObject()));
 }else if((mostCurrent._main._fotopath3).equals("")) { 
 //BA.debugLineNum = 84;BA.debugLine="fotonumlibre = 4";
_fotonumlibre = (int) (4);
 //BA.debugLineNum = 85;BA.debugLine="newfilename = Main.username & \"@\" & frmEvaluacio";
mostCurrent._newfilename = mostCurrent._main._username+"@"+mostCurrent._frmevaluacion._currentproject+"@"+mostCurrent._main._nombrerio+"-"+anywheresoftware.b4a.keywords.Common.DateTime.Date(anywheresoftware.b4a.keywords.Common.DateTime.getNow())+"_"+BA.NumberToString(_fotonumlibre);
 //BA.debugLineNum = 86;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 87;BA.debugLine="lblTitulo.Text = \"Fotografía un detalle\"";
mostCurrent._lbltitulo.setText((Object)("Fotografía un detalle"));
 //BA.debugLineNum = 88;BA.debugLine="lblInstruccion.Text = \"En esta fotografía trata";
mostCurrent._lblinstruccion.setText((Object)("En esta fotografía trata de capturar algún detalle que consideres importante"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 90;BA.debugLine="lblTitulo.Text = \"Details\"";
mostCurrent._lbltitulo.setText((Object)("Details"));
 //BA.debugLineNum = 91;BA.debugLine="lblInstruccion.Text = \"In this photo, try to ca";
mostCurrent._lblinstruccion.setText((Object)("In this photo, try to capture some detail you consider important"));
 };
 //BA.debugLineNum = 93;BA.debugLine="ImageView2.Bitmap = LoadBitmap(File.DirAssets, \"";
mostCurrent._imageview2.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"fotoEspecifico2.png").getObject()));
 };
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 140;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 141;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 142;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 143;BA.debugLine="If Msgbox2(\"Salir de la evaluación?\", \"SA";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Salir de la evaluación?","SALIR","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 144;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 145;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 147;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 150;BA.debugLine="If Msgbox2(\"Exit the report?\", \"Exit\", \"Y";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Exit the report?","Exit","Yes","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 151;BA.debugLine="Activity.finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 152;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 }else {
 //BA.debugLineNum = 154;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 };
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 161;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 162;BA.debugLine="camera1.Release";
mostCurrent._camera1.Release();
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 135;BA.debugLine="If sacandofoto = True Then";
if (_sacandofoto==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 136;BA.debugLine="camera1.Initialize(Panel1, \"Camera1\")";
mostCurrent._camera1.Initialize(mostCurrent.activityBA,(android.view.ViewGroup)(mostCurrent._panel1.getObject()),"Camera1");
 };
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
return "";
}
public static String  _btnadjuntarfoto_click() throws Exception{
 //BA.debugLineNum = 224;BA.debugLine="Sub btnAdjuntarFoto_Click";
 //BA.debugLineNum = 226;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 227;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 228;BA.debugLine="CC.Show(\"image/\", \"Elija la foto\")";
_cc.Show(processBA,"image/","Elija la foto");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 230;BA.debugLine="CC.Initialize(\"CC\")";
_cc.Initialize("CC");
 //BA.debugLineNum = 231;BA.debugLine="CC.Show(\"image/\", \"Choose photo\")";
_cc.Show(processBA,"image/","Choose photo");
 };
 //BA.debugLineNum = 233;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrarconsejosfotos_click() throws Exception{
 //BA.debugLineNum = 266;BA.debugLine="Sub btnCerrarConsejosFotos_Click";
 //BA.debugLineNum = 268;BA.debugLine="End Sub";
return "";
}
public static String  _btntakepicture_click() throws Exception{
 //BA.debugLineNum = 212;BA.debugLine="Sub btnTakePicture_Click";
 //BA.debugLineNum = 213;BA.debugLine="btnTakePicture.Enabled = False";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 215;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 216;BA.debugLine="ProgressDialogShow(\"Capturando foto\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Capturando foto");
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 218;BA.debugLine="ProgressDialogShow(\"Capturing photo\")";
anywheresoftware.b4a.keywords.Common.ProgressDialogShow(mostCurrent.activityBA,"Capturing photo");
 };
 //BA.debugLineNum = 220;BA.debugLine="camera1.TakePicture";
mostCurrent._camera1.TakePicture();
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
return "";
}
public static String  _btntomarfoto_click() throws Exception{
 //BA.debugLineNum = 202;BA.debugLine="Sub btnTomarFoto_Click";
 //BA.debugLineNum = 203;BA.debugLine="Panel1.Visible = True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 204;BA.debugLine="Panel2.Visible = False";
mostCurrent._panel2.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 205;BA.debugLine="btnTakePicture.Visible = True";
mostCurrent._btntakepicture.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 206;BA.debugLine="btnTakePicture.Enabled = False";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 208;BA.debugLine="camera1.Initialize(Panel1, \"Camera1\")";
mostCurrent._camera1.Initialize(mostCurrent.activityBA,(android.view.ViewGroup)(mostCurrent._panel1.getObject()),"Camera1");
 //BA.debugLineNum = 209;BA.debugLine="sacandofoto = True";
_sacandofoto = anywheresoftware.b4a.keywords.Common.True;
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_picturetaken(byte[] _data) throws Exception{
anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper _out = null;
 //BA.debugLineNum = 165;BA.debugLine="Sub Camera1_PictureTaken (Data() As Byte)";
 //BA.debugLineNum = 166;BA.debugLine="camera1.StartPreview";
mostCurrent._camera1.StartPreview();
 //BA.debugLineNum = 168;BA.debugLine="If fotonumlibre = 1 Then";
if (_fotonumlibre==1) { 
 //BA.debugLineNum = 169;BA.debugLine="Main.fotopath0 = newfilename";
mostCurrent._main._fotopath0 = mostCurrent._newfilename;
 }else if(_fotonumlibre==2) { 
 //BA.debugLineNum = 171;BA.debugLine="Main.fotopath1 = newfilename";
mostCurrent._main._fotopath1 = mostCurrent._newfilename;
 }else if(_fotonumlibre==3) { 
 //BA.debugLineNum = 173;BA.debugLine="Main.fotopath2 = newfilename";
mostCurrent._main._fotopath2 = mostCurrent._newfilename;
 }else if(_fotonumlibre==4) { 
 //BA.debugLineNum = 175;BA.debugLine="Main.fotopath3 = newfilename";
mostCurrent._main._fotopath3 = mostCurrent._newfilename;
 };
 //BA.debugLineNum = 180;BA.debugLine="Dim out As OutputStream";
_out = new anywheresoftware.b4a.objects.streams.File.OutputStreamWrapper();
 //BA.debugLineNum = 181;BA.debugLine="out = File.OpenOutput(Main.savedir & \"/AppEAR/\",";
_out = anywheresoftware.b4a.keywords.Common.File.OpenOutput(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._newfilename+".jpg",anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 182;BA.debugLine="out.WriteBytes(Data, 0, Data.Length)";
_out.WriteBytes(_data,(int) (0),_data.length);
 //BA.debugLineNum = 183;BA.debugLine="out.Close";
_out.Close();
 //BA.debugLineNum = 185;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 186;BA.debugLine="ToastMessageShow(\"Imagen guardada: \" & File.Comb";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Imagen guardada: "+anywheresoftware.b4a.keywords.Common.File.Combine(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._newfilename+".jpg"),anywheresoftware.b4a.keywords.Common.False);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 188;BA.debugLine="ToastMessageShow(\"Photo saved: \" & File.Combine(";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Photo saved: "+anywheresoftware.b4a.keywords.Common.File.Combine(mostCurrent._main._savedir+"/AppEAR/",mostCurrent._newfilename+".jpg"),anywheresoftware.b4a.keywords.Common.False);
 };
 //BA.debugLineNum = 191;BA.debugLine="ProgressDialogHide";
anywheresoftware.b4a.keywords.Common.ProgressDialogHide();
 //BA.debugLineNum = 192;BA.debugLine="btnTakePicture.Enabled = True";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 195;BA.debugLine="camera1.Release";
mostCurrent._camera1.Release();
 //BA.debugLineNum = 196;BA.debugLine="sacandofoto = False";
_sacandofoto = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 197;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 198;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 199;BA.debugLine="End Sub";
return "";
}
public static String  _camera1_ready(boolean _success) throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub Camera1_Ready (Success As Boolean)";
 //BA.debugLineNum = 115;BA.debugLine="If Success Then";
if (_success) { 
 //BA.debugLineNum = 116;BA.debugLine="camera1.PictureSize(1280,720)";
mostCurrent._camera1.PictureSize((int) (1280),(int) (720));
 //BA.debugLineNum = 117;BA.debugLine="camera1.Quality = 80";
mostCurrent._camera1.setQuality((int) (80));
 //BA.debugLineNum = 118;BA.debugLine="camera1.FlashAuto";
mostCurrent._camera1.FlashAuto();
 //BA.debugLineNum = 119;BA.debugLine="camera1.OriPortrait";
mostCurrent._camera1.OriPortrait();
 //BA.debugLineNum = 120;BA.debugLine="camera1.CameraBack()";
mostCurrent._camera1.CameraBack();
 //BA.debugLineNum = 121;BA.debugLine="camera1.StartPreview";
mostCurrent._camera1.StartPreview();
 //BA.debugLineNum = 123;BA.debugLine="btnTakePicture.Enabled = True";
mostCurrent._btntakepicture.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else {
 //BA.debugLineNum = 125;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 126;BA.debugLine="ToastMessageShow(\"Hay un problema con la cámara";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("Hay un problema con la cámara",anywheresoftware.b4a.keywords.Common.True);
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 128;BA.debugLine="ToastMessageShow(\"There's a problem with the ca";
anywheresoftware.b4a.keywords.Common.ToastMessageShow("There's a problem with the camera",anywheresoftware.b4a.keywords.Common.True);
 };
 };
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public static String  _cc_result(boolean _success,String _dir,String _filename) throws Exception{
 //BA.debugLineNum = 236;BA.debugLine="Sub CC_Result (Success As Boolean, Dir As String,";
 //BA.debugLineNum = 237;BA.debugLine="If Success = True Then";
if (_success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 238;BA.debugLine="DateTime.DateFormat = \"dd-MM-yyyy\"";
anywheresoftware.b4a.keywords.Common.DateTime.setDateFormat("dd-MM-yyyy");
 //BA.debugLineNum = 240;BA.debugLine="If fotonumlibre = 1 Then";
if (_fotonumlibre==1) { 
 //BA.debugLineNum = 241;BA.debugLine="Main.fotopath0 = newfilename";
mostCurrent._main._fotopath0 = mostCurrent._newfilename;
 }else if(_fotonumlibre==2) { 
 //BA.debugLineNum = 243;BA.debugLine="Main.fotopath1 = newfilename";
mostCurrent._main._fotopath1 = mostCurrent._newfilename;
 }else if(_fotonumlibre==3) { 
 //BA.debugLineNum = 245;BA.debugLine="Main.fotopath2 = newfilename";
mostCurrent._main._fotopath2 = mostCurrent._newfilename;
 }else if(_fotonumlibre==4) { 
 //BA.debugLineNum = 247;BA.debugLine="Main.fotopath3 = newfilename";
mostCurrent._main._fotopath3 = mostCurrent._newfilename;
 };
 //BA.debugLineNum = 250;BA.debugLine="File.Copy(Dir, FileName, Main.savedir & \"/AppEAR/";
anywheresoftware.b4a.keywords.Common.File.Copy(_dir,_filename,mostCurrent._main._savedir+"/AppEAR/",mostCurrent._newfilename+".jpg");
 //BA.debugLineNum = 253;BA.debugLine="sacandofoto = False";
_sacandofoto = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 254;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 255;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 };
 //BA.debugLineNum = 257;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 13;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim camera1 As AdvancedCamera";
mostCurrent._camera1 = new xvs.ACL.ACL();
 //BA.debugLineNum = 16;BA.debugLine="Dim btnTakePicture As Button";
mostCurrent._btntakepicture = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim newfilename As String";
mostCurrent._newfilename = "";
 //BA.debugLineNum = 21;BA.debugLine="Dim fotonumlibre As Int";
_fotonumlibre = 0;
 //BA.debugLineNum = 22;BA.debugLine="Private lblTitulo As Label";
mostCurrent._lbltitulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblInstruccion As Label";
mostCurrent._lblinstruccion = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private ImageView2 As ImageView";
mostCurrent._imageview2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private ImageView1 As ImageView";
mostCurrent._imageview1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private btnAdjuntarFoto As Button";
mostCurrent._btnadjuntarfoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private btnTomarFoto As Button";
mostCurrent._btntomarfoto = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 8;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim sacandofoto As Boolean";
_sacandofoto = false;
 //BA.debugLineNum = 10;BA.debugLine="Dim CC As ContentChooser";
_cc = new anywheresoftware.b4a.phone.Phone.ContentChooser();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public static String  _removeviews() throws Exception{
int _i = 0;
 //BA.debugLineNum = 259;BA.debugLine="Sub RemoveViews";
 //BA.debugLineNum = 260;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 262;BA.debugLine="For i=Activity.NumberOfViews-1 To 0 Step -1";
{
final int step2 = (int) (-1);
final int limit2 = (int) (0);
for (_i = (int) (mostCurrent._activity.getNumberOfViews()-1) ; (step2 > 0 && _i <= limit2) || (step2 < 0 && _i >= limit2); _i = ((int)(0 + _i + step2)) ) {
 //BA.debugLineNum = 263;BA.debugLine="Activity.RemoveViewAt(i)";
mostCurrent._activity.RemoveViewAt(_i);
 }
};
 //BA.debugLineNum = 265;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 105;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 106;BA.debugLine="lblInstruccion.Text = \"For this photo, try to ta";
mostCurrent._lblinstruccion.setText((Object)("For this photo, try to take a wide photo of the site"));
 //BA.debugLineNum = 107;BA.debugLine="btnAdjuntarFoto.Text = \"Use a photo from the gal";
mostCurrent._btnadjuntarfoto.setText((Object)("Use a photo from the gallery"));
 //BA.debugLineNum = 108;BA.debugLine="btnTomarFoto.Text = \"Take a new photo\"";
mostCurrent._btntomarfoto.setText((Object)("Take a new photo"));
 //BA.debugLineNum = 109;BA.debugLine="btnTakePicture.Text = \"Take photo!\"";
mostCurrent._btntakepicture.setText((Object)("Take photo!"));
 };
 //BA.debugLineNum = 111;BA.debugLine="End Sub";
return "";
}
}
