package com.thy.easycheck.lopaManager;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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


public class LopaDrawer extends View {
    Paint paint = new Paint();
    
    public LopaHandler lopa;
    
    public int nrColums;
    public int nrRows;
    
    public Point cursor;
    public Point outerRect_lt;
    public Point outerRect_rb;
    
    public static final int RECT_SIZE = 50;
    public static final int GAP_SIZE  = 20;
    
    public static final int DRAW_MARGIN = 0;
    
    public static final int SCREEN_SIZE_MULTIPLIER = 2;
    
    public static final int CANVAS_W = 200;
    public static final int CANVAS_H = 600;
    
    
    public Path headCurve;
    public Path rearCurve;
    
    public Path leftLine;
    public Path rightLine;
    
    private ArrayList<Point> lopaCoordinates;
    

    public LopaDrawer(Context context) {
        super(context); 

    }
    
    // Constructor for inflating via XML
    public LopaDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);          
        outerRect_lt = new Point(0,0);
        outerRect_rb = new Point(0,0);
        
        headCurve = new Path();
        rearCurve = new Path();
        
        leftLine = new Path();
        rightLine = new Path();
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
        paint.setStrokeWidth(20);
        
        lopa.resetSeatIndex();

        headCurve.reset();
        rearCurve.reset();
        leftLine.reset();
        rightLine.reset();
        
        //Calculates outer rectangle
        
        calcOuterRect();

        Matrix shiftMatrix = new Matrix();
        Point trans = new Point();
        
        drawEndCurves(shiftMatrix,trans);
        
        RectF bounds = new RectF();
        
        rearCurve.computeBounds(bounds, false);
        
        float canvasScale = 1.0f;
        
        
        
        
        if((CANVAS_W /bounds.right ) < (CANVAS_H / bounds.bottom))
        {
            canvasScale = (CANVAS_W / bounds.right) * SCREEN_SIZE_MULTIPLIER;
            canvas.scale( canvasScale, canvasScale);
            paint.setStrokeWidth(5 / canvasScale);
        }
        else
        {
            
            
            canvas.translate((CANVAS_W - bounds.right * (CANVAS_H / bounds.bottom)) * SCREEN_SIZE_MULTIPLIER / 2, 0.0f);
        
            canvasScale = (CANVAS_H / bounds.bottom) * SCREEN_SIZE_MULTIPLIER;
            canvas.scale( canvasScale, canvasScale);
            paint.setStrokeWidth(5 / canvasScale);
        }
        
        
        
        //canvas.scale((CANVAS_W / bounds.right), (CANVAS_H / bounds.bottom));
        
        
        canvas.drawPath(headCurve, paint);
        canvas.drawPath(rearCurve, paint);
        
        canvas.drawPath(leftLine, paint);
        canvas.drawPath(rightLine, paint);
        
        // Draw seats 
        for(int i=0; i<lopa.getSeats().size(); i++)
        {
            Rect rect = new Rect(lopa.getNextSeat().getSeatRect());
            
            rect.offset(trans.x, trans.y);
            rect = drawLopaRect(rect, canvas, paint);
        }
        
        

    }
    
    public Rect drawLopaRect(Rect rect, Canvas canvas, Paint paint)
    {
        
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
    
    
    public void calcOuterRect()
    {
        outerRect_lt.set(1000, 1000);
        outerRect_rb.set(0, 0);
        
        Point point;
        for(int i=0;i<lopa.getSeats().size();i++)
        {
            point = lopa.getSeats().get(i).getPosition();
            if(point.x < outerRect_lt.x)
                outerRect_lt.x = point.x - RECT_SIZE - GAP_SIZE;
            if(point.y < outerRect_lt.y)
                outerRect_lt.y = point.y - RECT_SIZE - GAP_SIZE;
            
            if(point.x + RECT_SIZE > outerRect_rb.x)
                outerRect_rb.x = point.x + RECT_SIZE + GAP_SIZE;
            if(point.y + RECT_SIZE > outerRect_rb.y)
                outerRect_rb.y = point.y + RECT_SIZE + GAP_SIZE;
        }

    }
    
    
    public void drawEndCurves(Matrix translateMatrix, Point trans)
    {
        RectF bounds = new RectF();
        
        RectF rect = new RectF(outerRect_lt.x, outerRect_lt.y-(outerRect_rb.x-outerRect_lt.x)*4/3, outerRect_rb.x, outerRect_lt.y+(outerRect_rb.x-outerRect_lt.x)*4/3);
        headCurve.addArc(rect, 0, -180);
        headCurve.computeBounds(bounds, false);
        
        translateMatrix.setTranslate(DRAW_MARGIN-bounds.left,DRAW_MARGIN-bounds.top);
        trans.set((int)(DRAW_MARGIN-bounds.left),(int)(DRAW_MARGIN-bounds.top));
        headCurve.transform(translateMatrix);

        
        rect = new RectF(outerRect_lt.x, outerRect_rb.y-(outerRect_rb.x-outerRect_lt.x)*1/3, outerRect_rb.x, outerRect_rb.y+(outerRect_rb.x-outerRect_lt.x)*1/3);
        rearCurve.addArc(rect, 0, 180);
        rearCurve.transform(translateMatrix);
        
        leftLine.moveTo(outerRect_lt.x, outerRect_lt.y);
        leftLine.lineTo(outerRect_lt.x, outerRect_rb.y);
        leftLine.transform(translateMatrix);
        
        
        rightLine.moveTo(outerRect_rb.x, outerRect_lt.y);
        rightLine.lineTo(outerRect_rb.x, outerRect_rb.y);
        rightLine.transform(translateMatrix);
                
    }
    
}