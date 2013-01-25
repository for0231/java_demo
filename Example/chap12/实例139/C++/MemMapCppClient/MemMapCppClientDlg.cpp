// MemMapCppClientDlg.cpp : implementation file
//

#include "stdafx.h"
#include "MemMapCppClient.h"
#include "MemMapCppClientDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

UINT CMemMapCppClientDlg::UWM_DATA_READY = RegisterWindowMessage(UWM_DATA_READY_MSG); 
/////////////////////////////////////////////////////////////////////////////
// CMemMapCppClientDlg dialog

CMemMapCppClientDlg::CMemMapCppClientDlg(CWnd* pParent /*=NULL*/) 
	: CDialog(CMemMapCppClientDlg::IDD, pParent)
{
	//{{AFX_DATA_INIT(CMemMapCppClientDlg)
		// NOTE: the ClassWizard will add member initialization here
	//}}AFX_DATA_INIT
	// Note that LoadIcon does not require a subsequent DestroyIcon in Win32
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	m_pszMemMapFileName = _T("Mem_Map_File-{70122C30-0239-4f98-9D21-36885C8A8121}");
}

void CMemMapCppClientDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	//{{AFX_DATA_MAP(CMemMapCppClientDlg)
	DDX_Control(pDX, IDC_EDIT_CONTENT, m_edit);
	//}}AFX_DATA_MAP
}

BEGIN_MESSAGE_MAP(CMemMapCppClientDlg, CDialog)
	//{{AFX_MSG_MAP(CMemMapCppClientDlg)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_REGISTERED_MESSAGE(UWM_DATA_READY, OnDataReady)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CMemMapCppClientDlg message handlers

BOOL CMemMapCppClientDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon
	
	// TODO: Add extra initialization here
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

// If you add a minimize button to your dialog, you will need the code below
//  to draw the icon.  For MFC applications using the document/view model,
//  this is automatically done for you by the framework.

void CMemMapCppClientDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // device context for painting

		SendMessage(WM_ICONERASEBKGND, (WPARAM) dc.GetSafeHdc(), 0);

		// Center icon in client rectangle
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// Draw the icon
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

// The system calls this to obtain the cursor to display while the user drags
//  the minimized window.
HCURSOR CMemMapCppClientDlg::OnQueryDragIcon()
{
	return (HCURSOR) m_hIcon;
}

LRESULT CMemMapCppClientDlg::OnDataReady(WPARAM, LPARAM) 
{
	HANDLE hMapFile = NULL;
	PVOID pView = NULL;

	hMapFile = OpenFileMapping(FILE_MAP_READ, FALSE, m_pszMemMapFileName);
	if(hMapFile == NULL) {
		MessageBox("Can not open file mapping");
		return 0;
	}

	pView = MapViewOfFile(hMapFile, FILE_MAP_READ, 0, 0, 0);
	if(pView == NULL) {
		MessageBox("Can map view of file");
		CloseHandle(hMapFile);
		return 0;
	}

	LPSTR szContent = reinterpret_cast<LPSTR>(pView);
	int nLen = strlen(szContent);
	CString strContent;
	while(nLen > 0) {
		strContent += *szContent++;
		--nLen;
	}
	strContent += '\0';
	strContent.Replace("\n", "\r\n");
	m_edit.SetWindowText(strContent);

	if(pView) UnmapViewOfFile(pView);
	if(hMapFile) CloseHandle(hMapFile);

	return 0;
}


