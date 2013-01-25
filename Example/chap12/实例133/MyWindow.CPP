#include <windows.h>
#include <assert.h>
#include <gl/gl.h>
#include "jawt_md.h"
#include "JAWT_Info.h"
#include "MyWindow.h"

// Static variables for the OpenGL calls.
static HGLRC    hRC = NULL;
static HDC        hDC = NULL;

/*
 * Class:     MyWindow
 * Method:    initializeOpenGL
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MyWindow_initializeOpenGL
  (JNIEnv *env, jobject panel)
{
    // get the window handle
    JAWT_Info info(env, panel);
    HWND hWnd = (HWND)info.getHWND();

    if(hWnd == NULL)
        return;

    PIXELFORMATDESCRIPTOR pfd;
    int iFormat;

    // get the device context (DC)
    HWND hwnd = info.getHWND();
    hDC = ::GetDC(hwnd);

    // set the pixel format for the DC
    ::ZeroMemory( &pfd, sizeof( pfd ) );
    pfd.nSize = sizeof( pfd );
    pfd.nVersion = 1;
    pfd.dwFlags = PFD_DRAW_TO_WINDOW | 
    PFD_SUPPORT_OPENGL | PFD_DOUBLEBUFFER;
    pfd.iPixelType = PFD_TYPE_RGBA;
    pfd.cColorBits = 24;
    pfd.cDepthBits = 16;
    pfd.iLayerType = PFD_MAIN_PLANE;
    iFormat = ::ChoosePixelFormat( hDC, &pfd );
    ::SetPixelFormat( hDC, iFormat, &pfd );

    // create and enable the render context (RC)
    hRC = ::wglCreateContext( hDC );
    ::wglMakeCurrent( hDC, hRC );
}

/*
 * Class:     MyWindow
 * Method:    paint
 * Signature: (Ljava/awt/Graphics;)V
 */
JNIEXPORT void JNICALL Java_MyWindow_paintOpenGL
  (JNIEnv *env, jobject panel)
{
    static float theta = 0.0f;
    // get the window handle
    JAWT_Info info(env, panel);
    HWND hWnd = (HWND)info.getHWND();

    if(hWnd == NULL)
        return;

    // OpenGL animation code goes here
    ::glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
    ::glClear( GL_COLOR_BUFFER_BIT );

    ::glPushMatrix();
    ::glRotatef( theta, 0.0f, 0.0f, 1.0f );
    ::glBegin( GL_TRIANGLES );
    ::glColor3f( 1.0f, 0.0f, 0.0f ); glVertex2f( 0.0f, 1.0f );
    ::glColor3f( 0.0f, 1.0f, 0.0f ); glVertex2f( 0.87f, -0.5f );
    ::glColor3f( 0.0f, 0.0f, 1.0f ); glVertex2f( -0.87f, -0.5f );
    ::glEnd();
    ::glPopMatrix();

    ::SwapBuffers( hDC );

    theta += 1.0f;
}

/*
 * Class:     MyWindow
 * Method:    cleanupOpenGL
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_MyWindow_cleanupOpenGL
(JNIEnv *env, jobject panel)
{
    // get the window handle
    JAWT_Info info(env, panel);
    HWND hWnd = (HWND)info.getHWND();
    if(hWnd == NULL)
        return;

    ::wglMakeCurrent( NULL, NULL );
    ::wglDeleteContext( hRC );
    ::ReleaseDC( hWnd, hDC );
}
