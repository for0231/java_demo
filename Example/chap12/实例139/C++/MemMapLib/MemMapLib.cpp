// dll implementation file MemMapLib.cpp
//
////////////////////////////////////////////////////////////////////////////////////

#include <windows.h>
#include <tchar.h>

#include "MemMapFile.h"

#define UWM_DATA_READY_MSG _T("UWM_DATA_READY_MSG-{7FDB2CB4-5510-4d30-99A9-CD7752E0D680}")

UINT UWM_DATA_READY;

BOOL APIENTRY DllMain(HINSTANCE hinstDll, DWORD dwReasion, LPVOID lpReserved) {

	if(dwReasion == DLL_PROCESS_ATTACH) 
		UWM_DATA_READY = RegisterWindowMessage(UWM_DATA_READY_MSG);

	return TRUE; 
}

void ErrorHandler(LPCTSTR pszErrorMessage) {
	MessageBox(NULL, pszErrorMessage, _T("Error"), MB_OK | MB_ICONERROR);
}

JNIEXPORT jint JNICALL Java_com_stanley_memmap_MemMapFile_createFileMapping
	(JNIEnv * pEnv, jclass, jint lProtect, jint dwMaximumSizeHigh, 
	jint dwMaximumSizeLow, jstring name) {
	
	HANDLE hFile = INVALID_HANDLE_VALUE;
	HANDLE hMapFile = NULL;

	LPCSTR lpName = pEnv->GetStringUTFChars(name, NULL);

	__try {
		hMapFile = CreateFileMapping(hFile, NULL, lProtect, dwMaximumSizeHigh, 
									 dwMaximumSizeLow, lpName);

		if(hMapFile == NULL) {
			ErrorHandler(_T("Can not create file mapping object"));
			__leave;
		}

		if(GetLastError() == ERROR_ALREADY_EXISTS) {
			ErrorHandler(_T("File mapping object already exists"));
			CloseHandle(hMapFile);		
			__leave;
		}
	}
	__finally {
	}

	pEnv->ReleaseStringUTFChars(name, lpName);

	// if hMapFile is NULL, just return NULL, or return the handle
	return reinterpret_cast<jint>(hMapFile);
}

JNIEXPORT jint JNICALL Java_com_stanley_memmap_MemMapFile_openFileMapping
	(JNIEnv * pEnv, jclass, jint dwDesiredAccess, 
	jboolean bInheritHandle, jstring name) {
	
	HANDLE hMapFile = NULL;

	LPCSTR lpName = pEnv->GetStringUTFChars(name, NULL);
	hMapFile = OpenFileMapping(dwDesiredAccess, bInheritHandle, lpName);
	if(hMapFile == NULL) ErrorHandler(_T("Can not open file mapping object"));
	pEnv->ReleaseStringUTFChars(name, lpName);

	return reinterpret_cast<jint>(hMapFile);
}

JNIEXPORT jint JNICALL Java_com_stanley_memmap_MemMapFile_mapViewOfFile
	(JNIEnv *, jclass, jint hMapFile, jint dwDesiredAccess, 
	jint dwFileOffsetHigh, jint dwFileOffsetLow, jint dwNumberOfBytesToMap) {
	
	PVOID pView = NULL;
	pView = MapViewOfFile(reinterpret_cast<HANDLE>(hMapFile), dwDesiredAccess, 
						  dwFileOffsetHigh, dwFileOffsetLow, dwNumberOfBytesToMap);
	if(pView == NULL) ErrorHandler(_T("Can not map view of file"));

	return reinterpret_cast<jint>(pView);
}

JNIEXPORT jboolean JNICALL Java_com_stanley_memmap_MemMapFile_unmapViewOfFile
	(JNIEnv *, jclass, jint lpBaseAddress) {

	BOOL bRet = UnmapViewOfFile(reinterpret_cast<PVOID>(lpBaseAddress));
	if(!bRet) ErrorHandler(_T("Can not unmap view of file"));

	return bRet;
}

JNIEXPORT jboolean JNICALL Java_com_stanley_memmap_MemMapFile_closeHandle
	(JNIEnv *, jclass, jint hObject) {

	return CloseHandle(reinterpret_cast<HANDLE>(hObject));
}

JNIEXPORT void JNICALL Java_com_stanley_memmap_MemMapFile_writeToMem
	(JNIEnv * pEnv, jclass, jint lpBaseAddress, jstring content) {

	LPCSTR pszContent = pEnv->GetStringUTFChars(content, NULL);
	PVOID pView = reinterpret_cast<PVOID>(lpBaseAddress);
	LPSTR pszCopy = reinterpret_cast<LPSTR>(pView);
	lstrcpy(pszCopy, pszContent);
	pEnv->ReleaseStringUTFChars(content, pszContent);
}

JNIEXPORT jstring JNICALL Java_com_stanley_memmap_MemMapFile_readFromMem
	(JNIEnv * pEnv, jclass, jint lpBaseAddress) {

	PVOID pView = reinterpret_cast<PVOID>(lpBaseAddress);
	LPSTR pszContent = reinterpret_cast<LPSTR>(pView);
	return pEnv->NewStringUTF(pszContent);
}

JNIEXPORT void JNICALL Java_com_stanley_memmap_MemMapFile_broadcast
	(JNIEnv *, jclass) {
	
	SendMessage(HWND_BROADCAST, UWM_DATA_READY, 0, 0);
}
