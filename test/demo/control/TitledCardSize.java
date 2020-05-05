package test.demo.control;

public enum TitledCardSize
{
    SIZE_1x1(1, 1),
    SIZE_1x2(1, 2),
    SIZE_2x1(2, 1),
    SIZE_2x2(2, 2),
    SIZE_2x3(2, 3),
    SIZE_3x2(3, 2),
    SIZE_3x3(3, 3);
    
    private int width;
    private int height;
    
    TitledCardSize(int widthBlock, int heightBlock)
    {
        int blockWidth = 340;
        int blockHeight = 340;
        int hPadding = 20;
        int vPadding = 20;
        
        this.width = widthBlock * blockWidth;
        this.width += hPadding * (widthBlock - 1);
        this.height = heightBlock * blockHeight;
        this.height += vPadding * (heightBlock - 1);
    }
    
    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }
}
