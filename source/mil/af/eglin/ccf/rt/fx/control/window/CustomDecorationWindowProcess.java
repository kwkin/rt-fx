package mil.af.eglin.ccf.rt.fx.control.window;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.W32APIOptions;

public class CustomDecorationWindowProcess implements WinUser.WindowProc
{
    private static final int WM_DESTROY = 0x0002;
    private static final int WM_NCCALCSIZE = 0x0083;
    private static final int WM_NCHITTEST = 0x0084;

    private static final int SWP_NOSIZE = 0x0001;
    private static final int SWP_NOMOVE = 0x0002;
    private static final int SWP_NOZORDER = 0x0004;
    private static final int SWP_FRAMECHANGED = 0x0020;

    private static final int GWLP_WNDPROC = -4;

    private final User32Ex user32Ex;
    private BaseTSD.LONG_PTR defWndProc;

    public CustomDecorationWindowProcess()
    {
        this.user32Ex = Native.load("user32", User32Ex.class, W32APIOptions.DEFAULT_OPTIONS);
    }

    public void init(WinDef.HWND hwnd)
    {
        this.defWndProc = this.user32Ex.SetWindowLongPtr(hwnd, GWLP_WNDPROC, this);
        this.user32Ex.SetWindowPos(hwnd, hwnd, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE | SWP_NOZORDER | SWP_FRAMECHANGED);
    }

    @Override
    public LRESULT callback(HWND hwnd, int uMsg, WPARAM wparam, LPARAM lparam)
    {
        LRESULT lresult;
        switch (uMsg)
        {
            case WM_NCCALCSIZE:
                lresult = new LRESULT(0);
                break;
            case WM_NCHITTEST:
                lresult = borderLessHitTest(hwnd, uMsg, wparam, lparam);
                if (lresult.intValue() == new LRESULT(0).intValue())
                {
                    lresult = this.user32Ex.CallWindowProc(this.defWndProc, hwnd, uMsg, wparam, lparam);
                }
                break;
            case WM_DESTROY:
                this.user32Ex.SetWindowLongPtr(hwnd, GWLP_WNDPROC, this.defWndProc);
                lresult = new LRESULT(0);
                break;
            default:
                lresult = this.user32Ex.CallWindowProc(this.defWndProc, hwnd, uMsg, wparam, lparam);
                break;
        }
        return lresult;
    }

    LRESULT borderLessHitTest(HWND hWnd, int message, WPARAM wParam, LPARAM lParam)
    {
        int borderOffset = CustomDecorationParameters.getMaximizedWindowFrameThickness();
        int borderThickness = CustomDecorationParameters.getFrameResizeBorderThickness();

        WinDef.POINT mousePoint = new WinDef.POINT();
        WinDef.RECT windowRectable = new WinDef.RECT();
        User32.INSTANCE.GetCursorPos(mousePoint);
        User32.INSTANCE.GetWindowRect(hWnd, windowRectable);

        int uRow = 1;
        int uCol = 1;
        boolean isTopFrameResizing = false;
        boolean isTopFrameDragging = false;

        int topOffset = CustomDecorationParameters.getTitleBarHeight() == 0 ? borderThickness
                : CustomDecorationParameters.getTitleBarHeight();
        if (mousePoint.y >= windowRectable.top && mousePoint.y < windowRectable.top + topOffset + borderOffset)
        {
            isTopFrameResizing = (mousePoint.y < (windowRectable.top + borderThickness));
            if (!isTopFrameResizing)
            {
                isTopFrameDragging = (mousePoint.y <= windowRectable.top + CustomDecorationParameters.getTitleBarHeight()
                        + borderOffset)
                        && (mousePoint.x < (windowRectable.right - (CustomDecorationParameters.getControlBoxWidth()
                                + borderOffset + CustomDecorationParameters.getExtraRightReservedWidth())))
                        && (mousePoint.x > (windowRectable.left + CustomDecorationParameters.getIconWidth()
                                + borderOffset + CustomDecorationParameters.getExtraLeftReservedWidth()));
            }
            uRow = 0;
        }
        else if (mousePoint.y < windowRectable.bottom && mousePoint.y >= windowRectable.bottom - borderThickness)
        {
            uRow = 2;
        }
        
        if (mousePoint.x >= windowRectable.left && mousePoint.x < windowRectable.left + borderThickness)
        {
            uCol = 0;
        }
        else if (mousePoint.x < windowRectable.right && mousePoint.x >= windowRectable.right - borderThickness)
        {
            uCol = 2;
        }
        return new LRESULT(WM_NCHITEST.getCode(uRow, uCol, isTopFrameResizing, isTopFrameDragging));
    }
}