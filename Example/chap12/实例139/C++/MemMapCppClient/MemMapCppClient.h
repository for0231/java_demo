// MemMapCppClient.h : main header file for the MEMMAPCPPCLIENT application
//

#if !defined(AFX_MEMMAPCPPCLIENT_H__6FC73066_4FE5_46F4_891C_2434753F81AA__INCLUDED_)
#define AFX_MEMMAPCPPCLIENT_H__6FC73066_4FE5_46F4_891C_2434753F81AA__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CMemMapCppClientApp:
// See MemMapCppClient.cpp for the implementation of this class
//

class CMemMapCppClientApp : public CWinApp
{
public:
	CMemMapCppClientApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMemMapCppClientApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

// Implementation

	//{{AFX_MSG(CMemMapCppClientApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MEMMAPCPPCLIENT_H__6FC73066_4FE5_46F4_891C_2434753F81AA__INCLUDED_)
