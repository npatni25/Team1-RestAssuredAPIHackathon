package context;


public  class TextContext {

	private static final ScenarioContext context = ScenarioContext.getInstance();

	public static void setToken(String token) {
		context.set(ContextKeyEnum.ADMINLOGIN_TOKEN.name(), token);
	}

	public static void setDieticianUserId(String dieticianuserId) {
		context.set(ContextKeyEnum.DIETICIAN_USER_ID.name(), dieticianuserId);
	}

	public static void setPatientId(String patientId) {
		context.set(ContextKeyEnum.PATIENT_ID.name(), patientId);
	}
	
	public static void setpatientName(String  patientName) {
		context.set(ContextKeyEnum.PATIENT_NAME.name(), patientName);
	}
	
	public static void setdieticianToken(String dieticianToken) {
		context.set(ContextKeyEnum.DIETICIAN_TOKEN.name(), dieticianToken);
	}

	public static String getToken() {
		return (String) context.get(ContextKeyEnum.ADMINLOGIN_TOKEN.name());
	}

	public static Integer getDieticianUserId() {
		return (Integer) context.get(ContextKeyEnum.DIETICIAN_USER_ID.name());
	}
	
	public static Integer getPatientId() {
		return (Integer) context.get(ContextKeyEnum.PATIENT_ID.name());
	}
	
	public static String getdieticianToken() {
		return (String) context.get(ContextKeyEnum.DIETICIAN_TOKEN.name());
	}
	public static String getpatientName() {
		return (String) context.get(ContextKeyEnum.PATIENT_NAME.name());
	}
	
	public static void setMorbidityName(String  MorbidityName) {
		context.set(ContextKeyEnum.MORBIDITY_NAME.name(), MorbidityName);
	}
	
	public static String getMorbidityName() {
		return (String) context.get(ContextKeyEnum.MORBIDITY_NAME.name());
	}
	}

