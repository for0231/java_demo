// MemMapProxyLib.cpp
//
///////////////////////////////////////////////////////////////////////////////

/**
 * demo project for the MemMapProxyLib.dll
 */
#include <windows.h>
#include <tchar.h>
#include <process.h>
#include "MemMapProxyLib.h"

#define UWM_DATA_READY_MSG _T("UWM_DATA_READY_MSG-{7FDB2CB4-5510-4d30-99A9-CD7752E0D680}")

// data ready message
UINT UWM_DATA_READY;

// thread id
UINT uThreadId;
// cache for the JNIEnv* pointer
JNIEnv* g_pEnv = NULL;
// cache for the JavaVM* pointer
JavaVM* g_pJvm = NULL;
// cache for the jobject(MemMapProxy) pointer
jobject g_jobj = NULL;

// cache for the instance handle
HINSTANCE hInstance;

// forward declaration
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);
void RegisterWindowClass();
unsigned WINAPI CreateWndThread(LPVOID);
void Callback();
void ErrorHandler(LPCTSTR);


BOOL APIENTRY DllMain(HINSTANCE hinstDll, DWORD dwReasion, LPVOID lpReserved) {
	if(dwReasion == DLL_PROCESS_ATTACH) {
		hInstance = hinstDll;
		UWM_DATA_READY = RegisterWindowMessage(UWM_DATA_READY_MSG);
		RegisterWindowClass();
	}

	return TRUE;
}

void RegisterWindowClass() {
	WNDCLASSEX wcex;

	wcex.cbSize = sizeof(WNDCLASSEX);
	wcex.style			= CS_HREDRAW | CS_VREDRAW;
	wcex.lpfnWndProc	= WndProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= 0;
	wcex.hCursor		= 0;
	wcex.hbrBackground	= (HBRUSH)(COLOR_WINDOW + 1);
	wcex.lpszMenuName	= 0;
	wcex.lpszClassName	= _T("dummy window");
	wcex.hIconSm		= 0;

	RegisterClassEx(&wcex);
}

LRESULT CALLBACK WndProc(HWND hWnd, UINT Msg, WPARAM wParam, LPARAM lParam) {
	if(Msg == UWM_DATA_READY) {
		Callback();
	}
	else return DefWindowProc(hWnd, Msg, wParam, lParam);

	return 0;
}

unsigned WINAPI CreateWndThread(LPVOID pThreadParam) {
	HANDLE hWnd = CreateWindow(_T("dummy window"), NULL, WS_OVERLAPPEDWINDOW,
						CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT, CW_USEDEFAULT,
						NULL, NULL, hInstance, NULL);
	if(hWnd == NULL) {
		MessageBox(NULL, _T("Failed create dummy window"), "Failed", MB_OK | MB_ICONERROR);
		return 0;
	}

	jint nSize = 1;
	jint nVms;
	jint nStatus = JNI_GetCreatedJavaVMs(&g_pJvm, nSize, &nVms);

	if(nStatus == 0) {
		nStatus = g_pJvm->AttachCurrentThread(reinterpret_cast<void**>(&g_pEnv), NULL);
		if(nStatus != 0) ErrorHandler(_T("Can not attach thread"));
	}
	else {
		ErrorHandler(_T("Can not get the jvm"));
	}

	MSG Msg;
	while(GetMessage(&Msg, 0, 0, 0)) {
		TranslateMessage(&Msg);
		DispatchMessage(&Msg);
	}
	return Msg.wParam;
}

JNIEXPORT jboolean JNICALL Java_com_stanley_memmap_MemMapProxy_init
	(JNIEnv * pEnv, jobject jobj) {

	HANDLE hThread;
	hThread = (HANDLE)_beginthreadex(NULL, 0, &CreateWndThread, NULL, 0, &uThreadId);
	if(!hThread)
	{
		MessageBox(NULL, _T("Fail creating thread"), NULL, MB_OK);
		return false;
	}

	g_jobj = pEnv->NewGlobalRef(jobj);

	return true;
}

JNIEXPORT void JNICALL Java_com_stanley_memmap_MemMapProxy_destroy
	(JNIEnv * pEnv, jobject) {

	if(g_jobj) pEnv->DeleteGlobalRef(g_jobj);
	PostThreadMessage(uThreadId, WM_QUIT, 0, 0);
}

void Callback() {
	if(g_pEnv == NULL || g_jobj == NULL) return;

	jclass cls = g_pEnv->GetObjectClass(g_jobj);
	jmethodID mid = g_pEnv->GetMethodID(cls, "fireDataReadyEvent", "()V");
	g_pEnv->CallVoidMethod(g_jobj, mid, NULL);

}

void ErrorHandler(LPCTSTR pszErrorMessage) {
	MessageBox(NULL, pszErrorMessage, _T("Error"), MB_OK | MB_ICONERROR);
}