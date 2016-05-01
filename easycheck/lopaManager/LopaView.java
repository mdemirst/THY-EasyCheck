package com.thy.easycheck.lopaManager;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.thy.easycheck.model.LopaHandler;
import com.thy.easycheck.model.Seat;
import com.thy.easycheck.model.Seat.SeatStatus;


public class LopaView extends View {
    Paint paint = new Paint();
    Rect rect = new Rect();
    
    public LopaHandler lopa;
    
    public int nrColums;
    public int nrRows;
    
    public Point cursor;
    public Point outerRect_lt;
    public Point outerRect_rb;
    
    public static final int RECT_SIZE = 50;
    public static final int GAP_SIZE  = 20;
    
    
    public Path headCurve;
    public Path rearCurve;
    
    private ArrayList<Point> lopaCoordinates;
    

    public LopaView(Context context) {
        super(context); 

    }
    
    // Constructor for inflating via XML
    public LopaView(Context context, AttributeSet attrs) {
        super(context, attrs);          
        outerRect_lt = new Point(0,0);
        outerRect_rb = new Point(0,0);
        
        headCurve = new Path();
        rearCurve = new Path();
    }
    
    public void initializeLopa(LopaHandler lopa)
    {
        this.lopa = lopa;
        postInvalidate();
    }
    
    public void updateSeat(Seat seat, SeatStatus status)
    {
        
    }
    
    public Seat locateSeat(Point coord)
    {
        Seat seat = null;
        
        return seat;
    }
    
    public void getStatistics()
    {
        
    }

    @Override
    public void onDraw(Canvas canvas) {
        
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        
        lopa.resetSeatIndex();

        // Draw seats 
        for(int i=0; i<lopa.getSeats().size(); i++)
        {
            rect = drawLopaRect(lopa.getNextSeat().getPosition(), canvas, paint);
        }
        
        /*
        
        //Calculates outer rectangle
        
        calcOuterRect();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(outerRect_lt.x, outerRect_lt.y, outerRect_lt.x, outerRect_rb.y, paint);
        canvas.drawLine(outerRect_rb.x, outerRect_lt.y, outerRect_rb.x, outerRect_rb.y, paint);

        
        

        drawEndCurves();
        canvas.drawPath(headCurve, paint);
        canvas.drawPath(rearCurve, paint);
        
        */

    }
    
    public Rect drawLopaRect(Point coord, Canvas canvas, Paint paint)
    {
        Rect rect = new Rect(coord.x - (RECT_SIZE / 2),
                             coord.y - (RECT_SIZE / 2),
                             coord.x + (RECT_SIZE / 2),
                             coord.y + (RECT_SIZE / 2));
        canvas.drawRect(rect, paint);
        return rect;
    }
    
    public void resetLopa()
    {
        lopa.resetLopa();
        postInvalidate();
    }
    
    public void addCursorPoint()
    {
        if(lopaCoordinates.contains(cursor) == false)
        {
            lopaCoordinates.add(new Point(cursor));
            
            postInvalidate();
        }
        
    }
    
    /*
    public void calcOuterRect()
    {
        outerRect_lt.set(1000, 1000);
        outerRect_rb.set(0, 0);
        
        Point point;
        for(int i=0;i<lopaCoordinates.size();i++)
        {
            point = getPixelEq(lopaCoordinates.get(i));
            if(point.x < outerRect_lt.x)
                outerRect_lt.x = point.x - GAP_SIZE;
            if(point.y < outerRect_lt.y)
                outerRect_lt.y = point.y - GAP_SIZE;
            
            if(point.x + RECT_SIZE > outerRect_rb.x)
                outerRect_rb.x = point.x + RECT_SIZE + GAP_SIZE;
            if(point.y + RECT_SIZE > outerRect_rb.y)
                outerRect_rb.y = point.y + RECT_SIZE + GAP_SIZE;
        }

    }
    */
    
    public void drawEndCurves()
    {
        headCurve.reset();
        rearCurve.reset();
        
        RectF rect = new RectF(outerRect_lt.x, outerRect_lt.y-(outerRect_rb.x-outerRect_lt.x)*4/3, outerRect_rb.x, outerRect_lt.y+(outerRect_rb.x-outerRect_lt.x)*4/3);
        headCurve.addArc(rect, 0, -180);
        
              rect = new RectF(outerRect_lt.x, outerRect_rb.y-(outerRect_rb.x-outerRect_lt.x)*1/3, outerRect_rb.x, outerRect_rb.y+(outerRect_rb.x-outerRect_lt.x)*1/3);
        rearCurve.addArc(rect, 0, 180);
        
        /*
        headCurve.moveTo(outerRect_lt.x, outerRect_lt.y);
        headCurve.quadTo((outerRect_lt.x+outerRect_rb.x)/2, outerRect_lt.y-(outerRect_rb.x-outerRect_lt.x)*4/3, outerRect_rb.x, outerRect_lt.y);
    
        rearCurve.moveTo(outerRect_lt.x, outerRect_rb.y);
        rearCurve.quadTo((outerRect_lt.x+outerRect_rb.x)/2, outerRect_rb.y+(outerRect_rb.x-outerRect_lt.x), outerRect_rb.x, outerRect_rb.y);
        */
    }
    
}