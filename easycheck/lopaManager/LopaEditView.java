package com.thy.easycheck.lopaManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.gesture.GesturePoint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.thy.easycheck.CreateLopaActivity;
import com.thy.easycheck.model.LopaHandler;
import com.thy.easycheck.model.Seat;
import com.thy.easycheck.model.Seat.SeatStatus;

public class LopaEditView extends View {

    private Paint paint;
    private Path path;
    public Bitmap lopaTemplateImage;
    public Bitmap lopaSavedImage;

    
    public static LopaHandler lopaHandle;
    
    ViewGroup.LayoutParams params;
    
    public ProgressDialog pd;
    final Point tappedPoint = new Point();
    public Canvas canvas;
    private int filledSeatCount;
    private boolean isTemplateLoaded;
    
    
    File savedLopaImgFile;
    
    public final static int MIN_SEAT_AREA = 500;
    public final static int MAX_SEAT_AREA = 5000;
    
    public final static int MIN_SEAT_LENGHT = 20;
    public final static int MAX_SEAT_LENGHT = 100;
    public final static int MIN_SEAT_WIDTH = 20;
    public final static int MAX_SEAT_WIDTH = 100;
    
    public final static int MAX_SEAT_ORIENT = 10;
    public final static int SEAT_MARGIN = 2;
    
    public static Context ctx;

    public LopaEditView(Context context, AttributeSet attrs) {
        super(context, attrs); 
        
        if(!isInEditMode())
        {
            this.ctx = context;
    
            this.paint = new Paint();
            this.paint.setAntiAlias(true);
            pd = new ProgressDialog(context);
            this.paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(5f);
            this.path = new Path();
            filledSeatCount = 0;
            isTemplateLoaded = false;
            
            savedLopaImgFile = new File("/mnt/sdcard/sample/temp.txt");
            savedLopaImgFile.getParentFile().mkdirs();
            
            
        }
    }
    
    public LopaEditView(Context context) {
        super(context); 
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!isInEditMode() && lopaHandle != null && lopaTemplateImage != null)
        {
            this.canvas = canvas;
            this.paint.setColor(Color.RED);
    
    
            canvas.drawBitmap(lopaTemplateImage, 0, 0, paint);
        }
    }

    public int getFilledSeatCount() {
        return filledSeatCount;
    }

    public void setFilledSeatCount(int filledSeatCount) {
        this.filledSeatCount = filledSeatCount;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_UP:

                tappedPoint.x = (int) x;
                tappedPoint.y = (int) y;
                final int sourceColor = lopaTemplateImage.getPixel((int) x, (int) y);
                final int targetColor = paint.getColor();
                new BucketFillTask(lopaTemplateImage, tappedPoint, sourceColor, targetColor).execute();
                invalidate();
        }
        return true;
    }

    public void clear() {
        path.reset();
        invalidate();
    }
    
    public void setLopaTemplate(LopaHandler lopaHandle, Bitmap lopaTemplateImage)
    {
        this.lopaHandle = lopaHandle;
        this.lopaTemplateImage = lopaTemplateImage.copy(lopaTemplateImage.getConfig(), true);
        
        isTemplateLoaded = true;
        
        invalidate();
    }

    public int getCurrentPaintColor() {
        return paint.getColor();
    }

    public void changePaintColor(int color){
        this.paint.setColor(color);
    }
    
    public void saveFillStep()
    {
        RandomAccessFile randomAccessFile;
        try {
            randomAccessFile = new RandomAccessFile(savedLopaImgFile, "rw");
            
            int width = lopaTemplateImage.getWidth();
            int height = lopaTemplateImage.getHeight();
            
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, width*height*4);
            
            lopaTemplateImage.copyPixelsToBuffer(map);
            
            channel.close();
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    
    public void undoFillStep()
    {
        
        RandomAccessFile randomAccessFile;
        
        try {
            randomAccessFile = new RandomAccessFile(savedLopaImgFile, "rw");
            
            int width = lopaTemplateImage.getWidth();
            int height = lopaTemplateImage.getHeight();
            
            FileChannel channel = randomAccessFile.getChannel();
            MappedByteBuffer map = channel.map(MapMode.READ_WRITE, 0, width*height*4);
            
            lopaTemplateImage.recycle();
            
            lopaTemplateImage = Bitmap.createBitmap(width, height, Config.ARGB_8888);
            
            map.position(0);
            
            lopaTemplateImage.copyPixelsFromBuffer(map);
            
            filledSeatCount = filledSeatCount - 1;
            invalidate();
            
            //close the temporary file and channel , then delete that also
            channel.close();
            randomAccessFile.close();
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
      
      
        /*
       if(lopaSavedImage != null)
       {
           filledSeatCount = filledSeatCount - 1;
           this.lopaTemplateImage = lopaSavedImage;
           invalidate();
       }*/
    }

    class BucketFillTask extends AsyncTask<Void, Integer, Void> {

        Bitmap image;
        Point point;
        int replacementColor, targetColor;
        boolean floodResult;

        public BucketFillTask(Bitmap bm, Point p, int sc, int tc) {
            this.image = bm;
            this.point = p;
            this.replacementColor = tc;
            this.targetColor = sc;
            pd.setMessage("Filling....");
            pd.show();
        }

        @Override
        protected void onPreExecute() {
            pd.show();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            FloodFill f = new FloodFill();
            saveFillStep();
            filledSeatCount = filledSeatCount + 1;
            floodResult = f.floodFill(image, point, targetColor, replacementColor);
            if(floodResult == false)   
            {
                ((Activity)ctx).runOnUiThread(new Runnable()
                {
                   @Override
                   public void run()
                   {
                       undoFillStep();
                   }
                });
            }
            else
            {
                ((Activity)ctx).runOnUiThread(new Runnable()
                {
                   @Override
                   public void run()
                   {
                       ((CreateLopaActivity) ctx).setSeatCountLabel(filledSeatCount);
                   }
                }); 
            }
            
                
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            pd.dismiss();
            if(floodResult == true)
                invalidate();
        }
    }
    
    public int getColorDiff(int color1, int color2)
    {
        int colorDiff = 0;
        
        colorDiff = Math.abs(Color.red(color1)-Color.red(color2));
        colorDiff = colorDiff + Math.abs(Color.green(color1)-Color.green(color2));
        colorDiff = colorDiff + Math.abs(Color.blue(color1)-Color.blue(color2));
        return colorDiff;
    }

    // Flood Fill Algorithm
    public class FloodFill {

        public boolean floodFill(Bitmap image, Point point, int targetColor, int replacementColor) {

            int width = image.getWidth();
            int height = image.getHeight();
            int target = targetColor;
            int replacement = replacementColor;
            
            ArrayList<GesturePoint> seatArea = new ArrayList<GesturePoint>();
            SeatLocater sl = new SeatLocater();

            if (target != replacement) {
                Queue<Point> queue = new LinkedList<Point>();
                do {

                    int x = point.x;
                    int y = point.y;
                    while (x > 0 && getColorDiff(image.getPixel(x-1, y), target) < 10) {
                        x--;
                    }

                    boolean spanUp = false;
                    boolean spanDown = false;
                    
                    
                    while (x < width && getColorDiff(image.getPixel(x, y), target) < 10) {
                        image.setPixel(x, y, replacement);
                        seatArea.add(new GesturePoint(x, y, 0));
                        if(seatArea.size() > MAX_SEAT_AREA)
                        {
                            return false;
                        }
                        if (!spanUp && y > 0 && getColorDiff(image.getPixel(x, y-1), target) < 10) {
                            queue.add(new Point(x, y - 1));
                            spanUp = true;
                        } else if (spanUp && y > 0 && getColorDiff(image.getPixel(x, y-1), target) >= 10) {
                            spanUp = false;
                        }
                        if (!spanDown && y < height - 1 && getColorDiff(image.getPixel(x, y+1), target) < 10) {
                            queue.add(new Point(x, y + 1));
                            spanDown = true;
                        } else if (spanDown && y < (height - 1) && getColorDiff(image.getPixel(x, y+1), target) >= 10) {
                            spanDown = false;
                        }
                        x++;
                    }

                } while ((point = queue.poll()) != null);
                
                
                
                if(true == sl.seatLocate(seatArea,image))
                    return true;
                else
                    return false;
            }
            
            return false;
        }
    }
    
    public class SeatLocater
    {
        public boolean seatLocate(ArrayList<GesturePoint> seatArea, Bitmap lopaImage)
        {
            OrientedBoundingBox boundingBox;
            float setOrientation;
            Canvas canvas = new Canvas(lopaImage);
            Rect seatRectangle;
            
            boundingBox = GestureUtils.computeOrientedBoundingBox(seatArea);
            
            if(boundingBox.height > MAX_SEAT_LENGHT || boundingBox.height < MIN_SEAT_LENGHT || boundingBox.width > MAX_SEAT_WIDTH || boundingBox.width < MIN_SEAT_WIDTH)
            {
                return false;
            }
            
            //Orientation of seats
            /*
            setOrientation = boundingBox.orientation;
            
            int i = 0;
            while(Math.abs(setOrientation) > MAX_SEAT_ORIENT || i > 4)
            {
                setOrientation = setOrientation + 90;
            }*/
            
            //Set orientation manually
            setOrientation = 0;
            
            seatRectangle = new Rect((int)(boundingBox.centerX-SEAT_MARGIN-boundingBox.width/2), (int)(boundingBox.centerY-SEAT_MARGIN-boundingBox.height/2), (int)(boundingBox.centerX+SEAT_MARGIN+boundingBox.width/2), (int)(boundingBox.centerY+SEAT_MARGIN+boundingBox.height/2));
            
            lopaHandle.getSeats().add(new Seat(new Point((int)boundingBox.centerX,(int)boundingBox.centerY),seatRectangle,SeatStatus.NOT_CHECKED));
            
            
            Paint paint = new Paint();

            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4);
            paint.setTextSize(40);
            paint.setTextAlign(Align.CENTER);
            

            canvas.rotate(setOrientation, boundingBox.centerX, boundingBox.centerY);
            canvas.drawRect(seatRectangle, paint);
            canvas.drawText(String.valueOf(filledSeatCount), boundingBox.centerX, boundingBox.centerY-((paint.descent() + paint.ascent()) / 2), paint);

            return true;
        }
    }
    
}
