package shapes;

import java.awt.Font;
import java.awt.Graphics2D;

public class Text extends Shape {
    private static final long serialVersionUID = 1L;
    public String fontName=null,inputContent;
    
    @Override
    public void draw(Graphics2D p) {
        p.setColor(color);
        p.setFont(new Font(fontName, Font.PLAIN, (int)thickness*10));
        if(inputContent!=null)
            p.drawString(inputContent, x1, y1);
    }

}
