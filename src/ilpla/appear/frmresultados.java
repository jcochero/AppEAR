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

public class frmresultados extends Activity implements B4AActivity{
	public static frmresultados mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.frmresultados");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (frmresultados).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.frmresultados");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.frmresultados", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (frmresultados) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (frmresultados) Resume **");
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
		return frmresultados.class;
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
        BA.LogInfo("** Activity (frmresultados) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (frmresultados) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _lblvalorgeneral = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnleval = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcanalvalor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcaucevalor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblripariavalor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lbltipoambiente = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btncerrar = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblripariacolor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcaucecolor = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblcanalcolor = null;
public static String _lblestadoescrito = "";
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
 //BA.debugLineNum = 27;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 28;BA.debugLine="Activity.AddMenuItem2(\"Eliminar esta evaluación y";
mostCurrent._activity.AddMenuItem2("Eliminar esta evaluación y rehacerla","mnuBorrar",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()));
 //BA.debugLineNum = 29;BA.debugLine="Activity.AddMenuItem2(\"Acerca de AppEAR\", \"mnuAbo";
mostCurrent._activity.AddMenuItem2("Acerca de AppEAR","mnuAbout",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"about.png").getObject()));
 //BA.debugLineNum = 31;BA.debugLine="CargarResultados";
_cargarresultados();
 //BA.debugLineNum = 32;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 44;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK) { 
 //BA.debugLineNum = 45;BA.debugLine="If Msgbox2(\"Cerrar evaluación sin guardar?";
if (anywheresoftware.b4a.keywords.Common.Msgbox2("Cerrar evaluación sin guardar?","SALIR","Si","","No",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA)==anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE) { 
 //BA.debugLineNum = 46;BA.debugLine="Main.preguntanumero = 1";
mostCurrent._main._preguntanumero = (int) (1);
 //BA.debugLineNum = 47;BA.debugLine="Main.valorcalidad = 0";
mostCurrent._main._valorcalidad = 0;
 //BA.debugLineNum = 48;BA.debugLine="Main.valorind1 = 0";
mostCurrent._main._valorind1 = BA.NumberToString(0);
 //BA.debugLineNum = 49;BA.debugLine="Main.valorind2 = 0";
mostCurrent._main._valorind2 = BA.NumberToString(0);
 //BA.debugLineNum = 50;BA.debugLine="Main.valorind3 = 0";
mostCurrent._main._valorind3 = BA.NumberToString(0);
 //BA.debugLineNum = 51;BA.debugLine="Main.valorind4 = 0";
mostCurrent._main._valorind4 = BA.NumberToString(0);
 //BA.debugLineNum = 52;BA.debugLine="Main.valorind5 = 0";
mostCurrent._main._valorind5 = BA.NumberToString(0);
 //BA.debugLineNum = 53;BA.debugLine="Main.valorind6 = 0";
mostCurrent._main._valorind6 = BA.NumberToString(0);
 //BA.debugLineNum = 54;BA.debugLine="Main.valorind7 = 0";
mostCurrent._main._valorind7 = BA.NumberToString(0);
 //BA.debugLineNum = 55;BA.debugLine="Main.valorind8 = 0";
mostCurrent._main._valorind8 = BA.NumberToString(0);
 //BA.debugLineNum = 56;BA.debugLine="Main.valorind9 = 0";
mostCurrent._main._valorind9 = BA.NumberToString(0);
 //BA.debugLineNum = 57;BA.debugLine="Main.valorind10 = 0";
mostCurrent._main._valorind10 = BA.NumberToString(0);
 //BA.debugLineNum = 58;BA.debugLine="Main.valorind11 = 0";
mostCurrent._main._valorind11 = BA.NumberToString(0);
 //BA.debugLineNum = 59;BA.debugLine="Main.valorind12 = 0";
mostCurrent._main._valorind12 = BA.NumberToString(0);
 //BA.debugLineNum = 60;BA.debugLine="Main.valorind13 = 0";
mostCurrent._main._valorind13 = BA.NumberToString(0);
 //BA.debugLineNum = 61;BA.debugLine="Main.valorind14 = 0";
mostCurrent._main._valorind14 = BA.NumberToString(0);
 //BA.debugLineNum = 62;BA.debugLine="Main.valorind15 = 0";
mostCurrent._main._valorind15 = BA.NumberToString(0);
 //BA.debugLineNum = 63;BA.debugLine="Main.valorind16 = 0";
mostCurrent._main._valorind16 = BA.NumberToString(0);
 //BA.debugLineNum = 64;BA.debugLine="frmEvaluacion.evaluaciondone = False";
mostCurrent._frmevaluacion._evaluaciondone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 66;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 67;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 68;BA.debugLine="StartActivity(frmEvaluacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmevaluacion.getObject()));
 }else {
 //BA.debugLineNum = 72;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 };
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 39;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 36;BA.debugLine="CargarResultados";
_cargarresultados();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public static String  _btncerrar_click() throws Exception{
 //BA.debugLineNum = 204;BA.debugLine="Sub btnCerrar_Click";
 //BA.debugLineNum = 205;BA.debugLine="frmEvaluacion.evaluaciondone = False";
mostCurrent._frmevaluacion._evaluaciondone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 206;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 207;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 208;BA.debugLine="StartActivity(frmEvaluacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmevaluacion.getObject()));
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public static String  _cargarresultados() throws Exception{
 //BA.debugLineNum = 124;BA.debugLine="Sub CargarResultados";
 //BA.debugLineNum = 125;BA.debugLine="btnCerrar.Visible = True";
mostCurrent._btncerrar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 128;BA.debugLine="lblValorGeneral.Text = Round2(Main.valorcalidad,";
mostCurrent._lblvalorgeneral.setText((Object)(BA.NumberToString(anywheresoftware.b4a.keywords.Common.Round2(mostCurrent._main._valorcalidad,(int) (1)))+"%"));
 //BA.debugLineNum = 129;BA.debugLine="ColorearLabels(lblValorGeneral, Main.valorcalida";
_colorearlabels(mostCurrent._lblvalorgeneral,mostCurrent._main._valorcalidad);
 //BA.debugLineNum = 131;BA.debugLine="If Main.tiporio = \"Montana\" Then";
if ((mostCurrent._main._tiporio).equals("Montana")) { 
 //BA.debugLineNum = 132;BA.debugLine="lblTipoAmbiente.Text = \"Arroyo de montaña\"";
mostCurrent._lbltipoambiente.setText((Object)("Arroyo de montaña"));
 }else if((mostCurrent._main._tiporio).equals("Llanura")) { 
 //BA.debugLineNum = 135;BA.debugLine="lblTipoAmbiente.Text = \"Arroyo de llanura\"";
mostCurrent._lbltipoambiente.setText((Object)("Arroyo de llanura"));
 }else if((mostCurrent._main._tiporio).equals("Humedal")) { 
 //BA.debugLineNum = 138;BA.debugLine="lblTipoAmbiente.Text = \"Humedal / Bañado\"";
mostCurrent._lbltipoambiente.setText((Object)("Humedal / Bañado"));
 }else if((mostCurrent._main._tiporio).equals("Laguna")) { 
 //BA.debugLineNum = 141;BA.debugLine="lblTipoAmbiente.Text = \"Laguna\"";
mostCurrent._lbltipoambiente.setText((Object)("Laguna"));
 }else if((mostCurrent._main._tiporio).equals("Estuario")) { 
 //BA.debugLineNum = 144;BA.debugLine="lblTipoAmbiente.Text = \"Estuario\"";
mostCurrent._lbltipoambiente.setText((Object)("Estuario"));
 };
 //BA.debugLineNum = 153;BA.debugLine="If Main.valorcalidad < 20 Then";
if (mostCurrent._main._valorcalidad<20) { 
 //BA.debugLineNum = 154;BA.debugLine="lblEstadoEscrito = \"El sitio tiene, en general";
mostCurrent._lblestadoescrito = "El sitio tiene, en general, una muy mala calidad del hábitat."+anywheresoftware.b4a.keywords.Common.CRLF;
 }else if(mostCurrent._main._valorcalidad<40 && mostCurrent._main._valorcalidad>=20) { 
 //BA.debugLineNum = 157;BA.debugLine="lblEstadoEscrito = \"El sitio tiene, en general";
mostCurrent._lblestadoescrito = "El sitio tiene, en general, una calidad mala del hábitat."+anywheresoftware.b4a.keywords.Common.CRLF;
 }else if(mostCurrent._main._valorcalidad<60 && mostCurrent._main._valorcalidad>=40) { 
 //BA.debugLineNum = 160;BA.debugLine="lblEstadoEscrito = \"El sitio tiene, en general";
mostCurrent._lblestadoescrito = "El sitio tiene, en general, una calidad intermedia del hábitat."+anywheresoftware.b4a.keywords.Common.CRLF;
 }else if(mostCurrent._main._valorcalidad<80 && mostCurrent._main._valorcalidad>=60) { 
 //BA.debugLineNum = 163;BA.debugLine="lblEstadoEscrito = \"El sitio tiene, en general";
mostCurrent._lblestadoescrito = "El sitio tiene, en general, una buena calidad del hábitat."+anywheresoftware.b4a.keywords.Common.CRLF;
 }else if(mostCurrent._main._valorcalidad>80) { 
 //BA.debugLineNum = 165;BA.debugLine="lblEstadoEscrito = \"El sitio tiene, en general";
mostCurrent._lblestadoescrito = "El sitio tiene, en general, una muy buena calidad calidad del hábitat."+anywheresoftware.b4a.keywords.Common.CRLF;
 };
 //BA.debugLineNum = 168;BA.debugLine="CargarResultados";
_cargarresultados();
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _colorearlabels(anywheresoftware.b4a.objects.LabelWrapper _labelname,double _valoractual) throws Exception{
 //BA.debugLineNum = 179;BA.debugLine="Sub ColorearLabels (labelname As Label, valoractua";
 //BA.debugLineNum = 180;BA.debugLine="If valoractual < 20 Then";
if (_valoractual<20) { 
 //BA.debugLineNum = 182;BA.debugLine="labelname.TextColor = Colors.ARGB(255,0,0,0)";
_labelname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (0),(int) (0),(int) (0)));
 //BA.debugLineNum = 183;BA.debugLine="labelname.Color = Colors.ARGB(100,0,0,0)";
_labelname.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (0),(int) (0),(int) (0)));
 }else if(_valoractual<40 && _valoractual>=20) { 
 //BA.debugLineNum = 186;BA.debugLine="labelname.TextColor = Colors.ARGB(255,125,15,1";
_labelname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (125),(int) (15),(int) (19)));
 //BA.debugLineNum = 187;BA.debugLine="labelname.Color =Colors.ARGB(100,125,15,19)";
_labelname.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (125),(int) (15),(int) (19)));
 }else if(_valoractual<60 && _valoractual>=40) { 
 //BA.debugLineNum = 190;BA.debugLine="labelname.TextColor = Colors.ARGB(255,179,191,";
_labelname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (179),(int) (191),(int) (0)));
 //BA.debugLineNum = 191;BA.debugLine="labelname.Color = Colors.ARGB(100,179,191,0)";
_labelname.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (179),(int) (191),(int) (0)));
 }else if(_valoractual<80 && _valoractual>=60) { 
 //BA.debugLineNum = 194;BA.debugLine="labelname.Color = Colors.ARGB(100,66,191,41)";
_labelname.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (66),(int) (191),(int) (41)));
 //BA.debugLineNum = 195;BA.debugLine="labelname.TextColor = Colors.ARGB(255,66,191,4";
_labelname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (66),(int) (191),(int) (41)));
 }else if(_valoractual>80) { 
 //BA.debugLineNum = 198;BA.debugLine="labelname.TextColor = Colors.ARGB(255,36,73,19";
_labelname.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (36),(int) (73),(int) (191)));
 //BA.debugLineNum = 199;BA.debugLine="labelname.Color = Colors.ARGB(100,36,73,191)";
_labelname.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (100),(int) (36),(int) (73),(int) (191)));
 };
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 11;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 12;BA.debugLine="Private lblValorGeneral As Label";
mostCurrent._lblvalorgeneral = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 13;BA.debugLine="Private pnlEval As Panel";
mostCurrent._pnleval = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Private lblCanalValor As Label";
mostCurrent._lblcanalvalor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 15;BA.debugLine="Private lblCauceValor As Label";
mostCurrent._lblcaucevalor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Private lblRipariaValor As Label";
mostCurrent._lblripariavalor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private lblTipoAmbiente As Label";
mostCurrent._lbltipoambiente = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private btnCerrar As Button";
mostCurrent._btncerrar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblRipariaColor As Label";
mostCurrent._lblripariacolor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblCauceColor As Label";
mostCurrent._lblcaucecolor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblCanalColor As Label";
mostCurrent._lblcanalcolor = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim lblEstadoEscrito As String";
mostCurrent._lblestadoescrito = "";
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public static String  _lblvalorgeneral_click() throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Sub lblValorGeneral_Click";
 //BA.debugLineNum = 175;BA.debugLine="Msgbox(lblEstadoEscrito, \"Estado del hábitat\")";
anywheresoftware.b4a.keywords.Common.Msgbox(mostCurrent._lblestadoescrito,"Estado del hábitat",mostCurrent.activityBA);
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return "";
}
public static String  _mnuabout_click() throws Exception{
 //BA.debugLineNum = 118;BA.debugLine="Sub mnuAbout_Click";
 //BA.debugLineNum = 119;BA.debugLine="StartActivity(frmAbout)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmabout.getObject()));
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static String  _mnuborrar_click() throws Exception{
String _msg = "";
 //BA.debugLineNum = 84;BA.debugLine="Sub mnuBorrar_Click";
 //BA.debugLineNum = 85;BA.debugLine="Dim msg As String = Msgbox2(\"Desea rehacer toda e";
_msg = BA.NumberToString(anywheresoftware.b4a.keywords.Common.Msgbox2("Desea rehacer toda esta evaluación? Cuidado, esto borrará la información que habia de este sitio","Cuidado!","Si, la haré de nuevo","","No, me arrepiento",(android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null),mostCurrent.activityBA));
 //BA.debugLineNum = 86;BA.debugLine="If msg=DialogResponse.POSITIVE Then";
if ((_msg).equals(BA.NumberToString(anywheresoftware.b4a.keywords.Common.DialogResponse.POSITIVE))) { 
 //BA.debugLineNum = 87;BA.debugLine="Main.preguntanumero = 1";
mostCurrent._main._preguntanumero = (int) (1);
 //BA.debugLineNum = 88;BA.debugLine="Main.valorcalidad = 0";
mostCurrent._main._valorcalidad = 0;
 //BA.debugLineNum = 89;BA.debugLine="Main.valorind1 = 0";
mostCurrent._main._valorind1 = BA.NumberToString(0);
 //BA.debugLineNum = 90;BA.debugLine="Main.valorind2 = 0";
mostCurrent._main._valorind2 = BA.NumberToString(0);
 //BA.debugLineNum = 91;BA.debugLine="Main.valorind3 = 0";
mostCurrent._main._valorind3 = BA.NumberToString(0);
 //BA.debugLineNum = 92;BA.debugLine="Main.valorind4 = 0";
mostCurrent._main._valorind4 = BA.NumberToString(0);
 //BA.debugLineNum = 93;BA.debugLine="Main.valorind5 = 0";
mostCurrent._main._valorind5 = BA.NumberToString(0);
 //BA.debugLineNum = 94;BA.debugLine="Main.valorind6 = 0";
mostCurrent._main._valorind6 = BA.NumberToString(0);
 //BA.debugLineNum = 95;BA.debugLine="Main.valorind7 = 0";
mostCurrent._main._valorind7 = BA.NumberToString(0);
 //BA.debugLineNum = 96;BA.debugLine="Main.valorind8 = 0";
mostCurrent._main._valorind8 = BA.NumberToString(0);
 //BA.debugLineNum = 97;BA.debugLine="Main.valorind9 = 0";
mostCurrent._main._valorind9 = BA.NumberToString(0);
 //BA.debugLineNum = 98;BA.debugLine="Main.valorind10 = 0";
mostCurrent._main._valorind10 = BA.NumberToString(0);
 //BA.debugLineNum = 99;BA.debugLine="Main.valorind11 = 0";
mostCurrent._main._valorind11 = BA.NumberToString(0);
 //BA.debugLineNum = 100;BA.debugLine="Main.valorind12 = 0";
mostCurrent._main._valorind12 = BA.NumberToString(0);
 //BA.debugLineNum = 101;BA.debugLine="Main.valorind13 = 0";
mostCurrent._main._valorind13 = BA.NumberToString(0);
 //BA.debugLineNum = 102;BA.debugLine="Main.valorind14 = 0";
mostCurrent._main._valorind14 = BA.NumberToString(0);
 //BA.debugLineNum = 103;BA.debugLine="Main.valorind15 = 0";
mostCurrent._main._valorind15 = BA.NumberToString(0);
 //BA.debugLineNum = 104;BA.debugLine="Main.valorind16 = 0";
mostCurrent._main._valorind16 = BA.NumberToString(0);
 //BA.debugLineNum = 106;BA.debugLine="frmEvaluacion.evaluaciondone = False";
mostCurrent._frmevaluacion._evaluaciondone = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 107;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 108;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 109;BA.debugLine="StartActivity(frmEvaluacion)";
anywheresoftware.b4a.keywords.Common.StartActivity(mostCurrent.activityBA,(Object)(mostCurrent._frmevaluacion.getObject()));
 };
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="End Sub";
return "";
}
}
