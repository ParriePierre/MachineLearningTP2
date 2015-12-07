/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ihm;

import java.awt.Color;
import java.awt.Graphics;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author slash
 */
public class PlotFrame extends JFrame{

    public static void main(String[] args) {
        System.out.println("Start main plot");
        PlotFrame t = new PlotFrame();
        
    }

    private ArrayList<HashMap<String, String>> Points = new <HashMap<String, String>>ArrayList();
    
    public void addPoint(Double x, Double y, int val){

        HashMap<String, String> Pt = new HashMap<String, String>();
        Pt.put("x", x + "");
        Pt.put("y", y*(-1) + "");
        Pt.put("val", val+"");

        this.Points.add(Pt);
    }

    private int offset_x = 150;
    private int offset_y = 15;
    private int width = 600;
    private int height = 600;
    private double wx = 1;
    private double wy = 1;

    public PlotFrame(){
        setSize(width,height);
        setVisible(true);
    }

    public void clean(){
        this.Points =  new <HashMap<String, String>>ArrayList();

    }
    
    @Override
    synchronized public void paint(Graphics g){
        Double max = 0.0;

        try{
            super.paint(g);
        for(HashMap<String, String> v : this.Points)
            if(Math.abs(Double.parseDouble(v.get("y"))) > max)
               max  = Math.abs(Double.parseDouble(v.get("y")));
        if(max > 1.0)
            max = Math.ceil(max)+1;

        wx =  (Double)(Double.parseDouble(width+"")-(Double.parseDouble(offset_x+"")*2) )/Double.parseDouble(""+this.Points.size());
        wy =  (Double)(Double.parseDouble(height+"")-(Double.parseDouble(offset_y+"")*2) )/max;

        // Draw line
        for(int k = -(getSize().height - offset_y) / 2;
                k < (getSize().height - 2*offset_y);
                k += (getSize().height - 2*offset_y)/9){

        int y = k + offset_y + getSize().height/2;
        DecimalFormat df = new DecimalFormat("#.######");

        g.drawString(df.format((-1)*(double)(k) / (double)wy),5 , y);

            for(int i = offset_x-10; i< (getSize().width-offset_x); i+= (getSize().width-offset_x)*0.01)
                g.drawString(".",i,y);
        }

        String p = ".";
            for(HashMap<String, String> v : this.Points){
                int x = (int) Math.ceil(Double.parseDouble(v.get("x")) * wx + offset_x);
                int y = (int) Math.ceil(Double.parseDouble(v.get("y")) * wy + offset_y + getSize().height/2);

                Color c;
                if(v.get("val").equals("0"))             c = Color.RED;
                else if(v.get("val").equals("1"))        c = Color.GREEN;
                else                                     c = Color.BLACK;

                g.setColor(c);
                g.drawString(".",x,y);
            }
        }
        catch(Exception e){System.out.println("Warning : "+e.getMessage());}
    }

}

