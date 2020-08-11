package ilpla.appear;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.objects.ServiceHelper;
import anywheresoftware.b4a.debug.*;

public class firebasemessaging extends  android.app.Service{
	public static class firebasemessaging_BR extends android.content.BroadcastReceiver {

		@Override
		public void onReceive(android.content.Context context, android.content.Intent intent) {
            BA.LogInfo("** Receiver (firebasemessaging) OnReceive **");
			android.content.Intent in = new android.content.Intent(context, firebasemessaging.class);
			if (intent != null)
				in.putExtra("b4a_internal_intent", intent);
            ServiceHelper.StarterHelper.startServiceFromReceiver (context, in, false, BA.class);
		}

	}
    static firebasemessaging mostCurrent;
	public static BA processBA;
    private ServiceHelper _service;
    public static Class<?> getObject() {
		return firebasemessaging.class;
	}
	@Override
	public void onCreate() {
        super.onCreate();
        mostCurrent = this;
        if (processBA == null) {
		    processBA = new BA(this, null, null, "ilpla.appear", "ilpla.appear.firebasemessaging");
            if (BA.isShellModeRuntimeCheck(processBA)) {
                processBA.raiseEvent2(null, true, "SHELL", false);
		    }
            try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            processBA.loadHtSubs(this.getClass());
            ServiceHelper.init();
        }
        _service = new ServiceHelper(this);
        processBA.service = this;
        
        if (BA.isShellModeRuntimeCheck(processBA)) {
			processBA.raiseEvent2(null, true, "CREATE", true, "ilpla.appear.firebasemessaging", processBA, _service, anywheresoftware.b4a.keywords.Common.Density);
		}
        if (!false && ServiceHelper.StarterHelper.startFromServiceCreate(processBA, false) == false) {
				
		}
		else {
            processBA.setActivityPaused(false);
            BA.LogInfo("*** Service (firebasemessaging) Create ***");
            processBA.raiseEvent(null, "service_create");
        }
        processBA.runHook("oncreate", this, null);
        if (false) {
			ServiceHelper.StarterHelper.runWaitForLayouts();
		}
    }
		@Override
	public void onStart(android.content.Intent intent, int startId) {
		onStartCommand(intent, 0, 0);
    }
    @Override
    public int onStartCommand(final android.content.Intent intent, int flags, int startId) {
    	if (ServiceHelper.StarterHelper.onStartCommand(processBA, new Runnable() {
            public void run() {
                handleStart(intent);
            }}))
			;
		else {
			ServiceHelper.StarterHelper.addWaitForLayout (new Runnable() {
				public void run() {
                    processBA.setActivityPaused(false);
                    BA.LogInfo("** Service (firebasemessaging) Create **");
                    processBA.raiseEvent(null, "service_create");
					handleStart(intent);
                    ServiceHelper.StarterHelper.removeWaitForLayout();
				}
			});
		}
        processBA.runHook("onstartcommand", this, new Object[] {intent, flags, startId});
		return android.app.Service.START_NOT_STICKY;
    }
    public void onTaskRemoved(android.content.Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        if (false)
            processBA.raiseEvent(null, "service_taskremoved");
            
    }
    private void handleStart(android.content.Intent intent) {
    	BA.LogInfo("** Service (firebasemessaging) Start **");
    	java.lang.reflect.Method startEvent = processBA.htSubs.get("service_start");
    	if (startEvent != null) {
    		if (startEvent.getParameterTypes().length > 0) {
    			anywheresoftware.b4a.objects.IntentWrapper iw = ServiceHelper.StarterHelper.handleStartIntent(intent, _service, processBA);
    			processBA.raiseEvent(null, "service_start", iw);
    		}
    		else {
    			processBA.raiseEvent(null, "service_start");
    		}
    	}
    }
	
	@Override
	public void onDestroy() {
        super.onDestroy();
        if (false) {
            BA.LogInfo("** Service (firebasemessaging) Destroy (ignored)**");
        }
        else {
            BA.LogInfo("** Service (firebasemessaging) Destroy **");
		    processBA.raiseEvent(null, "service_destroy");
            processBA.service = null;
		    mostCurrent = null;
		    processBA.setActivityPaused(true);
            processBA.runHook("ondestroy", this, null);
        }
	}

@Override
	public android.os.IBinder onBind(android.content.Intent intent) {
		return null;
	}public anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper _fm = null;
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
public ilpla.appear.inatcheck _inatcheck = null;
public ilpla.appear.reporte_fotos _reporte_fotos = null;
public ilpla.appear.reporte_habitat_estuario _reporte_habitat_estuario = null;
public ilpla.appear.reporte_habitat_laguna _reporte_habitat_laguna = null;
public ilpla.appear.reporte_habitat_rio _reporte_habitat_rio = null;
public ilpla.appear.uploadfiles _uploadfiles = null;
public static String  _fm_messagearrived(anywheresoftware.b4a.objects.FirebaseNotificationsService.RemoteMessageWrapper _message) throws Exception{
anywheresoftware.b4a.objects.NotificationWrapper _n = null;
 //BA.debugLineNum = 27;BA.debugLine="Sub fm_MessageArrived (Message As RemoteMessage)";
 //BA.debugLineNum = 28;BA.debugLine="Log(\"Message arrived\")";
anywheresoftware.b4a.keywords.Common.LogImpl("726476545","Message arrived",0);
 //BA.debugLineNum = 29;BA.debugLine="Log($\"Message data: ${Message.GetData}\"$)";
anywheresoftware.b4a.keywords.Common.LogImpl("726476546",("Message data: "+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_message.GetData().getObject()))+""),0);
 //BA.debugLineNum = 30;BA.debugLine="Dim n As Notification";
_n = new anywheresoftware.b4a.objects.NotificationWrapper();
 //BA.debugLineNum = 31;BA.debugLine="n.Initialize";
_n.Initialize();
 //BA.debugLineNum = 32;BA.debugLine="n.Icon = \"icon\"";
_n.setIcon("icon");
 //BA.debugLineNum = 33;BA.debugLine="n.SetInfo(Message.GetData.Get(\"title\"), Message.G";
_n.SetInfoNew(processBA,BA.ObjectToCharSequence(_message.GetData().Get((Object)("title"))),BA.ObjectToCharSequence(_message.GetData().Get((Object)("body"))),(Object)(mostCurrent._main.getObject()));
 //BA.debugLineNum = 34;BA.debugLine="n.Notify(1)";
_n.Notify((int) (1));
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 7;BA.debugLine="Public fm As FirebaseMessaging";
_fm = new anywheresoftware.b4a.objects.FirebaseNotificationsService.FirebaseMessageWrapper();
 //BA.debugLineNum = 8;BA.debugLine="End Sub";
return "";
}
public static String  _service_create() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Service_Create";
 //BA.debugLineNum = 11;BA.debugLine="fm.Initialize(\"fm\")";
_fm.Initialize(processBA,"fm");
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return "";
}
public static String  _service_destroy() throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Sub Service_Destroy";
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public static void  _service_start(anywheresoftware.b4a.objects.IntentWrapper _startingintent) throws Exception{
ResumableSub_Service_Start rsub = new ResumableSub_Service_Start(null,_startingintent);
rsub.resume(processBA, null);
}
public static class ResumableSub_Service_Start extends BA.ResumableSub {
public ResumableSub_Service_Start(ilpla.appear.firebasemessaging parent,anywheresoftware.b4a.objects.IntentWrapper _startingintent) {
this.parent = parent;
this._startingintent = _startingintent;
}
ilpla.appear.firebasemessaging parent;
anywheresoftware.b4a.objects.IntentWrapper _startingintent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 22;BA.debugLine="If StartingIntent.IsInitialized Then fm.HandleInt";
if (true) break;

case 1:
//if
this.state = 6;
if (_startingintent.IsInitialized()) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
parent._fm.HandleIntent((android.content.Intent)(_startingintent.getObject()));
if (true) break;

case 6:
//C
this.state = -1;
;
 //BA.debugLineNum = 23;BA.debugLine="Sleep(0)";
anywheresoftware.b4a.keywords.Common.Sleep(processBA,this,(int) (0));
this.state = 7;
return;
case 7:
//C
this.state = -1;
;
 //BA.debugLineNum = 24;BA.debugLine="Service.StopAutomaticForeground";
parent.mostCurrent._service.StopAutomaticForeground();
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public static String  _subscribetotopics() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Public Sub SubscribeToTopics'************ Google P";
 //BA.debugLineNum = 16;BA.debugLine="fm.SubscribeToTopic(\"general\") 'you can subscribe";
_fm.SubscribeToTopic("general");
 //BA.debugLineNum = 17;BA.debugLine="Log (fm.Token)";
anywheresoftware.b4a.keywords.Common.LogImpl("726345475",_fm.getToken(),0);
 //BA.debugLineNum = 18;BA.debugLine="End Sub";
return "";
}
}
