// Ӧ�ó���ı��ط������
#include "stdafx.h"
extern "C" int		__argc; 
extern "C" char**	__argv; 
#define argc		__argc
#define argv		__argv
#define MAX_LOADSTRING 100
// ȫ�ֱ���: 
void DisplayHelp( void ) 
{
	::MessageBox(NULL,"Command line : JStart [-hide] \"Java start up command line\"\nExample: 
JStart -hide java -cp C:YourClassPath YourJavaApp","JStart Help", MB_OK ); 
}
int APIENTRY WinMain(HINSTANCE hInstance, 
					 HINSTANCE hPrevInstance, 
					 LPSTR     lpCmdLine, 
					 int       nCmdShow) 
{
	char str[1024]; 
	// ��鿴�û��Ƿ���Ҫ�����а���
	if( argc < 2 ) 
	{
		DisplayHelp();
		return -1; 
	}
	strcpy( str, argv[1]); 
	strupr(str ); 
	if( strstr(str,"-?") == str || strstr(str,"/?" ) == str
		|| ( strstr(str,"-H") == str && strstr(str,"-HIDE") != str ) 
		|| strstr(str,"/H" ) == str ) 
	{
		DisplayHelp();   // ��ʾ����
		return -1; 
	}
	STARTUPINFO sis; 
	PROCESS_INFORMATION pis; 
	memset( &sis, 0, sizeof(sis) ); 
	sis.cb = sizeof(sis); 
	memset(  &pis, 0, sizeof(pis) ); 
	// ����־λ
	char cmdstr[1024]; cmdstr[0] = 0; 
	strcpy( str, argv[1] ); 
	strupr( str ); 
	if( strstr( str, "-HIDE") == str ) 
	{
		sis.wShowWindow = SW_HIDE; 
		sis.dwFlags = STARTF_USESHOWWINDOW; 
	}
	for( int i = 1; i < argc; i++ ) 
	{
		strcpy( str, argv[i] ); 
		strupr( str ); 
		if( strstr( str, "-HIDE") == str && i == 1 ) ; 
		else 
		{
			strcat(cmdstr,argv[i]); strcat(cmdstr," ");
		}
	}
	BOOL bl = CreateProcess( NULL, cmdstr,NULL,NULL,false, 
	CREATE_NEW_CONSOLE | NORMAL_PRIORITY_CLASS, NULL, NULL, &sis, &pis ); 
	return true; 
}
