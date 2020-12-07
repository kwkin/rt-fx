package mil.af.eglin.ccf.rt.fx.control.window;

import com.sun.jna.platform.win32.User32;

public interface User32Ex extends User32
{
    LONG_PTR SetWindowLongPtr(HWND hWnd, int nIndex, WindowProc wndProc);

    LONG_PTR SetWindowLongPtr(HWND hWnd, int nIndex, LONG_PTR wndProc);

    LRESULT CallWindowProc(LONG_PTR proc, HWND hWnd, int uMsg, WPARAM uParam, LPARAM lParam);
}