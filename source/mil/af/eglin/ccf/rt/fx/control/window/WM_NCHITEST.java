package mil.af.eglin.ccf.rt.fx.control.window;

enum WM_NCHITEST
{
    HTTOPLEFT(13),    
    HTTOP(12),        
    HTCAPTION(2),     
    HTTOPRIGHT(14),   
    HTLEFT(10),       
    HTNOWHERE(0),     
    HTRIGHT(11),      
    HTBOTTOMLEFT(16), 
    HTBOTTOM(15),  
    HTBOTTOMRIGHT(17);
    
    private int code;
    
    WM_NCHITEST(int code)
    {
        this.code = code;
    }
    
    public int getCode()
    {
        return this.code;
    }
    
    public static int getCode(int row, int col, boolean isTopFrameResizing, boolean isTopFrameDragging)
    {
        WM_NCHITEST[][] hitTests =
        {
            { HTTOPLEFT, isTopFrameResizing ? HTTOP : isTopFrameDragging ? HTCAPTION : HTNOWHERE, HTTOPRIGHT },
            { HTLEFT, HTNOWHERE, HTRIGHT },
            { HTBOTTOMLEFT, HTBOTTOM, HTBOTTOMRIGHT }, 
        };
        return hitTests[row][col].code;
    }
}
