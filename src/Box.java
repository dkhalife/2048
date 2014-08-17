
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dany
 */
public class Box extends JLabel {
    private static Color[] COLORS = {
        new Color(204, 192, 179),
        new Color(238, 228, 218),
        new Color(237, 224, 200), 
        new Color(242, 177, 121),
        new Color(245, 149, 99), 
        new Color(246, 124, 95), 
        new Color(246, 94, 59), 
        new Color(237, 207, 114), 
        new Color(237, 204, 97), 
        new Color(237, 200, 80), 
        new Color(237, 197, 63), 
        new Color(237, 194, 46) 
    };
    
    private static Font font = new Font("Verdana", Font.BOLD, 28);
    
    public Box(){
        setOpaque(true);
        setSize(150, 150);
        setFont(font);
        setHorizontalAlignment(SwingConstants.CENTER);
        setBackground(new Color(204, 192, 179));
    }
    
    public void setValue(int i){
        int l = (int) Math.log(i) + 1;
        
        setText(i == 0 ? "" : String.valueOf(i));
        setForeground(l < 3 ? new Color(119, 110, 101) : new Color(249, 246, 242));
        setBackground(COLORS[Math.min(Math.max(l, 0), 11)]);
    }
    
    public int getValue(){
        String t = getText();
        
        return t.isEmpty() ? 0 : Integer.parseInt(t);
    }
}