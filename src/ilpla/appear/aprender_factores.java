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

public class aprender_factores extends Activity implements B4AActivity{
	public static aprender_factores mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "ilpla.appear", "ilpla.appear.aprender_factores");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (aprender_factores).");
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
		activityBA = new BA(this, layout, processBA, "ilpla.appear", "ilpla.appear.aprender_factores");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.aprender_factores", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (aprender_factores) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (aprender_factores) Resume **");
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
		return aprender_factores.class;
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
        BA.LogInfo("** Activity (aprender_factores) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            BA.LogInfo("** Activity (aprender_factores) Resume **");
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
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblpregunta9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta1 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta10 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta2 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta3 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta4 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta5 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta6 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta7 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta8 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipregunta9 = null;
public anywheresoftware.b4a.objects.ImageViewWrapper _minipreguntaentubado = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta8 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta9 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta10 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta3 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta4 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta5 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta6 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnpregunta7 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnterminar = null;
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
anywheresoftware.b4a.objects.drawable.ColorDrawable _labelborder = null;
anywheresoftware.b4a.objects.drawable.StateListDrawable _sldwhite = null;
anywheresoftware.b4a.objects.LabelWrapper _titulo = null;
 //BA.debugLineNum = 50;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 54;BA.debugLine="Activity.LoadLayout(\"HabitatRio\")";
mostCurrent._activity.LoadLayout("HabitatRio",mostCurrent.activityBA);
 //BA.debugLineNum = 55;BA.debugLine="lblPregunta1.Visible = True";
mostCurrent._lblpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 56;BA.debugLine="lblPregunta2.Visible = True";
mostCurrent._lblpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 57;BA.debugLine="lblPregunta3.Visible = True";
mostCurrent._lblpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 58;BA.debugLine="lblPregunta5.Visible = True";
mostCurrent._lblpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 59;BA.debugLine="lblPregunta6.Visible = True";
mostCurrent._lblpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 60;BA.debugLine="lblPregunta7.Visible = True";
mostCurrent._lblpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 61;BA.debugLine="lblPregunta9.Visible = True";
mostCurrent._lblpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 62;BA.debugLine="lblPregunta10.Visible = True";
mostCurrent._lblpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 63;BA.debugLine="lblPregunta1.Top = miniPregunta1.Top - 25dip";
mostCurrent._lblpregunta1.setTop((int) (mostCurrent._minipregunta1.getTop()-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25))));
 //BA.debugLineNum = 64;BA.debugLine="lblPregunta6.Top = miniPregunta6.Top + miniPregun";
mostCurrent._lblpregunta6.setTop((int) (mostCurrent._minipregunta6.getTop()+mostCurrent._minipregunta6.getHeight()));
 //BA.debugLineNum = 65;BA.debugLine="lblPregunta9.Left = lblPregunta9.Left + 60dip";
mostCurrent._lblpregunta9.setLeft((int) (mostCurrent._lblpregunta9.getLeft()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))));
 //BA.debugLineNum = 66;BA.debugLine="lblPregunta7.Top = miniPreguntaEntubado.Top + 20d";
mostCurrent._lblpregunta7.setTop((int) (mostCurrent._minipreguntaentubado.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 67;BA.debugLine="btnPregunta7.Top = miniPreguntaEntubado.Top + 20d";
mostCurrent._btnpregunta7.setTop((int) (mostCurrent._minipreguntaentubado.getTop()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 68;BA.debugLine="btnPregunta5.Top = btnPregunta8.Top";
mostCurrent._btnpregunta5.setTop(mostCurrent._btnpregunta8.getTop());
 //BA.debugLineNum = 69;BA.debugLine="btnPregunta5.Left = btnPregunta8.Left";
mostCurrent._btnpregunta5.setLeft(mostCurrent._btnpregunta8.getLeft());
 //BA.debugLineNum = 70;BA.debugLine="lblPregunta5.Top = lblPregunta8.Top";
mostCurrent._lblpregunta5.setTop(mostCurrent._lblpregunta8.getTop());
 //BA.debugLineNum = 71;BA.debugLine="lblPregunta5.Left = lblPregunta8.Left";
mostCurrent._lblpregunta5.setLeft(mostCurrent._lblpregunta8.getLeft());
 //BA.debugLineNum = 72;BA.debugLine="lblPregunta5.Text = \"Calidad del agua\"";
mostCurrent._lblpregunta5.setText((Object)("Calidad del agua"));
 //BA.debugLineNum = 73;BA.debugLine="lblPregunta5.Width = lblPregunta5.Width + 20dip";
mostCurrent._lblpregunta5.setWidth((int) (mostCurrent._lblpregunta5.getWidth()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20))));
 //BA.debugLineNum = 74;BA.debugLine="lblPregunta9.Text = \"Márgenes\"";
mostCurrent._lblpregunta9.setText((Object)("Márgenes"));
 //BA.debugLineNum = 77;BA.debugLine="Dim LabelBorder As ColorDrawable";
_labelborder = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 78;BA.debugLine="LabelBorder.initialize2(Colors.ARGB(0,255,255,255";
_labelborder.Initialize2(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (0),(int) (255),(int) (255),(int) (255)),(int) (255),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (3)),anywheresoftware.b4a.keywords.Common.Colors.Red);
 //BA.debugLineNum = 79;BA.debugLine="Dim sldWhite As StateListDrawable";
_sldwhite = new anywheresoftware.b4a.objects.drawable.StateListDrawable();
 //BA.debugLineNum = 80;BA.debugLine="sldWhite.Initialize";
_sldwhite.Initialize();
 //BA.debugLineNum = 81;BA.debugLine="sldWhite.AddState (sldWhite.State_Enabled,LabelBo";
_sldwhite.AddState(_sldwhite.State_Enabled,(android.graphics.drawable.Drawable)(_labelborder.getObject()));
 //BA.debugLineNum = 82;BA.debugLine="btnPregunta1.background = sldWhite";
mostCurrent._btnpregunta1.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 83;BA.debugLine="btnPregunta2.background = sldWhite";
mostCurrent._btnpregunta2.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 84;BA.debugLine="btnPregunta3.background = sldWhite";
mostCurrent._btnpregunta3.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 85;BA.debugLine="btnPregunta5.background = sldWhite";
mostCurrent._btnpregunta5.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 86;BA.debugLine="btnPregunta6.background = sldWhite";
mostCurrent._btnpregunta6.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 87;BA.debugLine="btnPregunta7.background = sldWhite";
mostCurrent._btnpregunta7.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 88;BA.debugLine="btnPregunta9.background = sldWhite";
mostCurrent._btnpregunta9.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 89;BA.debugLine="btnPregunta10.background = sldWhite";
mostCurrent._btnpregunta10.setBackground((android.graphics.drawable.Drawable)(_sldwhite.getObject()));
 //BA.debugLineNum = 91;BA.debugLine="btnPregunta1.Visible = True";
mostCurrent._btnpregunta1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 92;BA.debugLine="btnPregunta2.Visible = True";
mostCurrent._btnpregunta2.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 93;BA.debugLine="btnPregunta3.Visible = True";
mostCurrent._btnpregunta3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 94;BA.debugLine="btnPregunta5.Visible = True";
mostCurrent._btnpregunta5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 95;BA.debugLine="btnPregunta6.Visible = True";
mostCurrent._btnpregunta6.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 96;BA.debugLine="btnPregunta7.Visible = True";
mostCurrent._btnpregunta7.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 97;BA.debugLine="btnPregunta9.Visible = True";
mostCurrent._btnpregunta9.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 98;BA.debugLine="btnPregunta10.Visible = True";
mostCurrent._btnpregunta10.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 101;BA.debugLine="miniPregunta1.SetBackgroundImage(LoadBitmapSample";
mostCurrent._minipregunta1.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-usourbano.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 102;BA.debugLine="miniPregunta2.SetBackgroundImage(LoadBitmapSample";
mostCurrent._minipregunta2.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-ganadodisperso.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 103;BA.debugLine="miniPregunta3.SetBackgroundImage(LoadBitmapSample";
mostCurrent._minipregunta3.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-vegetacionmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 104;BA.debugLine="miniPregunta4.SetBackgroundImage(Null)";
mostCurrent._minipregunta4.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 105;BA.debugLine="miniPregunta5.SetBackgroundImage(Null)";
mostCurrent._minipregunta5.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 106;BA.debugLine="miniPregunta6.SetBackgroundImage(LoadBitmapSample";
mostCurrent._minipregunta6.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-basuramucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 107;BA.debugLine="miniPregunta7.SetBackgroundImage(Null)";
mostCurrent._minipregunta7.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 108;BA.debugLine="miniPregunta8.SetBackgroundImage(Null)";
mostCurrent._minipregunta8.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 109;BA.debugLine="miniPregunta9.SetBackgroundImage(LoadBitmapSample";
mostCurrent._minipregunta9.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-margenmucha.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 110;BA.debugLine="miniPregunta10.SetBackgroundImage(LoadBitmapSampl";
mostCurrent._minipregunta10.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-totalsombra.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 111;BA.debugLine="miniPreguntaEntubado.SetBackgroundImage(LoadBitma";
mostCurrent._minipreguntaentubado.SetBackgroundImage((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"mini-entubado.png",anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 114;BA.debugLine="Dim titulo As Label";
_titulo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 115;BA.debugLine="titulo.Initialize(\"\")";
_titulo.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 116;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 117;BA.debugLine="titulo.Text = \"Factors that affect the freshwate";
_titulo.setText((Object)("Factors that affect the freshwater organisms"));
 }else {
 //BA.debugLineNum = 119;BA.debugLine="titulo.Text = \"Factores que afectan a los organi";
_titulo.setText((Object)("Factores que afectan a los organismos acuáticos"));
 };
 //BA.debugLineNum = 122;BA.debugLine="titulo.TextColor = Colors.White";
_titulo.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 123;BA.debugLine="titulo.Color = Colors.ARGB(200, 64,64,64)";
_titulo.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (200),(int) (64),(int) (64),(int) (64)));
 //BA.debugLineNum = 124;BA.debugLine="titulo.TextSize = 24";
_titulo.setTextSize((float) (24));
 //BA.debugLineNum = 125;BA.debugLine="titulo.Gravity = Gravity.CENTER";
_titulo.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 126;BA.debugLine="Activity.AddView(titulo, 0, 0, 100%x, 16%y)";
mostCurrent._activity.AddView((android.view.View)(_titulo.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (16),mostCurrent.activityBA));
 //BA.debugLineNum = 128;BA.debugLine="btnTerminar.Visible = True";
mostCurrent._btnterminar.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 129;BA.debugLine="If Main.lang = \"es\" Then";
if ((mostCurrent._main._lang).equals("es")) { 
 //BA.debugLineNum = 130;BA.debugLine="btnTerminar.Text = \"Cerrar\"";
mostCurrent._btnterminar.setText((Object)("Cerrar"));
 }else if((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 132;BA.debugLine="btnTerminar.Text = \"Close\"";
mostCurrent._btnterminar.setText((Object)("Close"));
 };
 //BA.debugLineNum = 135;BA.debugLine="TraducirGUI";
_traducirgui();
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 141;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _btnok_click() throws Exception{
 //BA.debugLineNum = 205;BA.debugLine="Sub btnOk_Click";
 //BA.debugLineNum = 206;BA.debugLine="Activity.RemoveViewAt(Activity.NumberOfViews - 1)";
mostCurrent._activity.RemoveViewAt((int) (mostCurrent._activity.getNumberOfViews()-1));
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta1_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 210;BA.debugLine="Sub btnPregunta1_Click";
 //BA.debugLineNum = 211;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 212;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-uso.txt");
 //BA.debugLineNum = 213;BA.debugLine="CargarExplicacion(line, \"2bej.jpg\")";
_cargarexplicacion(_line,"2bej.jpg");
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta10_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 249;BA.debugLine="Sub btnPregunta10_Click";
 //BA.debugLineNum = 251;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 252;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-sombra.txt");
 //BA.debugLineNum = 253;BA.debugLine="CargarExplicacion(line, \"rio6aej.jpg\")";
_cargarexplicacion(_line,"rio6aej.jpg");
 //BA.debugLineNum = 254;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta2_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 216;BA.debugLine="Sub btnPregunta2_Click";
 //BA.debugLineNum = 217;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 218;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-ganado.txt");
 //BA.debugLineNum = 219;BA.debugLine="CargarExplicacion(line, \"2bej.jpg\")";
_cargarexplicacion(_line,"2bej.jpg");
 //BA.debugLineNum = 220;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta3_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 222;BA.debugLine="Sub btnPregunta3_Click";
 //BA.debugLineNum = 223;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 224;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-vegetacion.txt");
 //BA.debugLineNum = 225;BA.debugLine="CargarExplicacion(line, \"arroyo3.jpg\")";
_cargarexplicacion(_line,"arroyo3.jpg");
 //BA.debugLineNum = 226;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta5_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 228;BA.debugLine="Sub btnPregunta5_Click";
 //BA.debugLineNum = 229;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 230;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-agua.txt");
 //BA.debugLineNum = 231;BA.debugLine="CargarExplicacion(line, \"rio6aej.jpg\")";
_cargarexplicacion(_line,"rio6aej.jpg");
 //BA.debugLineNum = 232;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta6_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 233;BA.debugLine="Sub btnPregunta6_Click";
 //BA.debugLineNum = 234;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 235;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-basura.txt");
 //BA.debugLineNum = 236;BA.debugLine="CargarExplicacion(line, \"7cej.jpg\")";
_cargarexplicacion(_line,"7cej.jpg");
 //BA.debugLineNum = 237;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta7_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 238;BA.debugLine="Sub btnPregunta7_Click";
 //BA.debugLineNum = 239;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 240;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-canalizacion.txt");
 //BA.debugLineNum = 241;BA.debugLine="CargarExplicacion(line, \"rio3aej.jpg\")";
_cargarexplicacion(_line,"rio3aej.jpg");
 //BA.debugLineNum = 242;BA.debugLine="End Sub";
return "";
}
public static String  _btnpregunta9_click() throws Exception{
String _line = "";
 //BA.debugLineNum = 243;BA.debugLine="Sub btnPregunta9_Click";
 //BA.debugLineNum = 245;BA.debugLine="Dim line As String";
_line = "";
 //BA.debugLineNum = 246;BA.debugLine="line = File.ReadString(File.DirAssets, Main.la";
_line = anywheresoftware.b4a.keywords.Common.File.ReadString(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),mostCurrent._main._lang+"-margen.txt");
 //BA.debugLineNum = 247;BA.debugLine="CargarExplicacion(line, \"5aej.jpg\")";
_cargarexplicacion(_line,"5aej.jpg");
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return "";
}
public static String  _btnterminar_click() throws Exception{
 //BA.debugLineNum = 256;BA.debugLine="Sub btnTerminar_Click";
 //BA.debugLineNum = 257;BA.debugLine="Activity.RemoveAllViews";
mostCurrent._activity.RemoveAllViews();
 //BA.debugLineNum = 258;BA.debugLine="Activity.Finish";
mostCurrent._activity.Finish();
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public static String  _cargarexplicacion(String _texto,String _foto) throws Exception{
anywheresoftware.b4a.objects.ScrollViewWrapper _fondogris = null;
anywheresoftware.b4a.objects.ImageViewWrapper _img = null;
anywheresoftware.b4a.objects.LabelWrapper _textolabel = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
anywheresoftware.b4a.objects.ButtonWrapper _btnok = null;
 //BA.debugLineNum = 173;BA.debugLine="Sub CargarExplicacion(texto As String, foto As Str";
 //BA.debugLineNum = 174;BA.debugLine="Dim fondogris As ScrollView";
_fondogris = new anywheresoftware.b4a.objects.ScrollViewWrapper();
 //BA.debugLineNum = 175;BA.debugLine="fondogris.Initialize(1000dip)";
_fondogris.Initialize(mostCurrent.activityBA,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1000)));
 //BA.debugLineNum = 176;BA.debugLine="fondogris.Color = Colors.ARGB(255, 255,255,255)";
_fondogris.setColor(anywheresoftware.b4a.keywords.Common.Colors.ARGB((int) (255),(int) (255),(int) (255),(int) (255)));
 //BA.debugLineNum = 177;BA.debugLine="Activity.AddView(fondogris,0,0,100%x,100%y)";
mostCurrent._activity.AddView((android.view.View)(_fondogris.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA));
 //BA.debugLineNum = 179;BA.debugLine="Dim img As ImageView";
_img = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 180;BA.debugLine="img.Initialize(\"\")";
_img.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 181;BA.debugLine="img.Bitmap = LoadBitmapSample(File.DirAssets, fot";
_img.setBitmap((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmapSample(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),_foto,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (95),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA)).getObject()));
 //BA.debugLineNum = 182;BA.debugLine="fondogris.Panel.AddView(img, 5%x, 5%y, 90%x, 40%y";
_fondogris.getPanel().AddView((android.view.View)(_img.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA));
 //BA.debugLineNum = 184;BA.debugLine="Dim textolabel As Label";
_textolabel = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 185;BA.debugLine="textolabel.Initialize(\"\")";
_textolabel.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 186;BA.debugLine="textolabel.Text = texto";
_textolabel.setText((Object)(_texto));
 //BA.debugLineNum = 187;BA.debugLine="textolabel.TextColor = Colors.Black";
_textolabel.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 188;BA.debugLine="textolabel.TextSize = 16";
_textolabel.setTextSize((float) (16));
 //BA.debugLineNum = 189;BA.debugLine="fondogris.Panel.AddView(textolabel,5%x,50%y,90%x,";
_fondogris.getPanel().AddView((android.view.View)(_textolabel.getObject()),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (90),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (50),mostCurrent.activityBA));
 //BA.debugLineNum = 191;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 192;BA.debugLine="textolabel.Height = su.MeasureMultilineTextHeight";
_textolabel.setHeight(_su.MeasureMultilineTextHeight((android.widget.TextView)(_textolabel.getObject()),_textolabel.getText()));
 //BA.debugLineNum = 195;BA.debugLine="Dim btnOk As Button";
_btnok = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 196;BA.debugLine="btnOk.Initialize(\"btnOk\")";
_btnok.Initialize(mostCurrent.activityBA,"btnOk");
 //BA.debugLineNum = 197;BA.debugLine="btnOk.Color = Colors.DarkGray";
_btnok.setColor(anywheresoftware.b4a.keywords.Common.Colors.DarkGray);
 //BA.debugLineNum = 198;BA.debugLine="btnOk.TextColor = Colors.White";
_btnok.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 199;BA.debugLine="btnOk.Text = \"Ok\"";
_btnok.setText((Object)("Ok"));
 //BA.debugLineNum = 200;BA.debugLine="btnOk.Gravity = Gravity.CENTER";
_btnok.setGravity(anywheresoftware.b4a.keywords.Common.Gravity.CENTER);
 //BA.debugLineNum = 201;BA.debugLine="fondogris.Panel.AddView(btnOk, 100%x - 150dip, im";
_fondogris.getPanel().AddView((android.view.View)(_btnok.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150))),(int) (_img.getHeight()+_textolabel.getHeight()+anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60))),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (150)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60)));
 //BA.debugLineNum = 202;BA.debugLine="fondogris.Panel.Height = img.Height + textolabel.";
_fondogris.getPanel().setHeight((int) (_img.getHeight()+_textolabel.getHeight()+_btnok.getHeight()+_btnok.getTop()));
 //BA.debugLineNum = 203;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private lblPregunta1 As Label";
mostCurrent._lblpregunta1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Private lblPregunta10 As Label";
mostCurrent._lblpregunta10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private lblPregunta2 As Label";
mostCurrent._lblpregunta2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private lblPregunta3 As Label";
mostCurrent._lblpregunta3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private lblPregunta4 As Label";
mostCurrent._lblpregunta4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private lblPregunta5 As Label";
mostCurrent._lblpregunta5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private lblPregunta6 As Label";
mostCurrent._lblpregunta6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private lblPregunta7 As Label";
mostCurrent._lblpregunta7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private lblPregunta8 As Label";
mostCurrent._lblpregunta8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private lblPregunta9 As Label";
mostCurrent._lblpregunta9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private miniPregunta1 As ImageView";
mostCurrent._minipregunta1 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private miniPregunta10 As ImageView";
mostCurrent._minipregunta10 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private miniPregunta2 As ImageView";
mostCurrent._minipregunta2 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 29;BA.debugLine="Private miniPregunta3 As ImageView";
mostCurrent._minipregunta3 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private miniPregunta4 As ImageView";
mostCurrent._minipregunta4 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private miniPregunta5 As ImageView";
mostCurrent._minipregunta5 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private miniPregunta6 As ImageView";
mostCurrent._minipregunta6 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private miniPregunta7 As ImageView";
mostCurrent._minipregunta7 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private miniPregunta8 As ImageView";
mostCurrent._minipregunta8 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private miniPregunta9 As ImageView";
mostCurrent._minipregunta9 = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private miniPreguntaEntubado As ImageView";
mostCurrent._minipreguntaentubado = new anywheresoftware.b4a.objects.ImageViewWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private btnPregunta8 As Button";
mostCurrent._btnpregunta8 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private btnPregunta9 As Button";
mostCurrent._btnpregunta9 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private btnPregunta1 As Button";
mostCurrent._btnpregunta1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private btnPregunta10 As Button";
mostCurrent._btnpregunta10 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private btnPregunta2 As Button";
mostCurrent._btnpregunta2 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private btnPregunta3 As Button";
mostCurrent._btnpregunta3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private btnPregunta4 As Button";
mostCurrent._btnpregunta4 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private btnPregunta5 As Button";
mostCurrent._btnpregunta5 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private btnPregunta6 As Button";
mostCurrent._btnpregunta6 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private btnPregunta7 As Button";
mostCurrent._btnpregunta7 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private btnTerminar As Button";
mostCurrent._btnterminar = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _traducirgui() throws Exception{
 //BA.debugLineNum = 153;BA.debugLine="Sub TraducirGUI";
 //BA.debugLineNum = 154;BA.debugLine="If Main.lang = \"en\" Then";
if ((mostCurrent._main._lang).equals("en")) { 
 //BA.debugLineNum = 155;BA.debugLine="lblPregunta1.Text = \"Land use\"";
mostCurrent._lblpregunta1.setText((Object)("Land use"));
 //BA.debugLineNum = 156;BA.debugLine="lblPregunta2.Text = \"Cattle\"";
mostCurrent._lblpregunta2.setText((Object)("Cattle"));
 //BA.debugLineNum = 157;BA.debugLine="lblPregunta3.Text = \"Vegetation\"";
mostCurrent._lblpregunta3.setText((Object)("Vegetation"));
 //BA.debugLineNum = 158;BA.debugLine="lblPregunta5.Text = \"Water\"";
mostCurrent._lblpregunta5.setText((Object)("Water"));
 //BA.debugLineNum = 159;BA.debugLine="lblPregunta6.Text = \"Litter\"";
mostCurrent._lblpregunta6.setText((Object)("Litter"));
 //BA.debugLineNum = 160;BA.debugLine="lblPregunta7.Text = \"Channelization\"";
mostCurrent._lblpregunta7.setText((Object)("Channelization"));
 //BA.debugLineNum = 161;BA.debugLine="lblPregunta9.Text = \"Margins\"";
mostCurrent._lblpregunta9.setText((Object)("Margins"));
 //BA.debugLineNum = 162;BA.debugLine="lblPregunta10.Text = \"Shading\"";
mostCurrent._lblpregunta10.setText((Object)("Shading"));
 };
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
}
