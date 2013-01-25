// MemMapCppClientDlg.h : header file
//

#if !defined(AFX_MEMMAPCPPCLIENTDLG_H__A51F6AF7_F28D_461D_8FB3_EFC7B929D99C__INCLUDED_)
#define AFX_MEMMAPCPPCLIENTDLG_H__A51F6AF7_F28D_461D_8FB3_EFC7B929D99C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

/////////////////////////////////////////////////////////////////////////////
// CMemMapCppClientDlg dialog

class CMemMapCppClientDlg : public CDialog
{
// Construction
public:
	static UINT UWM_DATA_READY;
	CMemMapCppClientDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	//{{AFX_DATA(CMemMapCppClientDlg)
	enum { IDD = IDD_MEMMAPCPPCLIENT_DIALOG };
	CEdit	m_edit;
	//}}AFX_DATA

	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CMemMapCppClientDlg)
	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
	//}}AFX_VIRTUAL

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	//{{AFX_MSG(CMemMapCppClientDlg)
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	//}}AFX_MSG
	afx_msg LRESULT OnDataReady(WPARAM wParam, LPARAM lParam);
	DECLARE_MESSAGE_MAP()
private:
	//enum { m_dwMemFileSize = 2 * 1024 };
	LPCTSTR m_pszMemMapFileName;
};

#define UWM_DATA_READY_MSG _T("UWM_DATA_READY_MSG-{7FDB2CB4-5510-4d30-99A9-CD7752E0D680}")

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_MEMMAPCPPCLIENTDLG_H__A51F6AF7_F28D_461D_8FB3_EFC7B929D99C__INCLUDED_)
