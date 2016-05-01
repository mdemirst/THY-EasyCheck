package com.thy.easycheck.model;

import android.graphics.Point;
import android.graphics.Rect;

public class Seat {
    
    public enum SeatStatus {NOT_CHECKED,NOT_EXPIRED, EXPIRE_SOON, EXPIRED};

    private Long id;
    private Point position;
    private Rect  seatRect;
    private SeatStatus seatStatus;
    
    
    public Seat(Point position, Rect seatRect, SeatStatus seatStatus) {
        super();
        this.position = position;
        this.seatRect = seatRect;
        this.seatStatus = seatStatus;
    }

    

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Rect getSeatRect() {
        return seatRect;
    }


    public void setSeatRect(Rect seatRect) {
        this.seatRect = seatRect;
    }


    public Point getPosition() {
        return position;
    }


    public void setPosition(Point position) {
        this.position = position;
    }


    public SeatStatus getSeatStatus() {
        return seatStatus;
    }


    public void setSeatStatus(SeatStatus seatStatus) {
        this.seatStatus = seatStatus;
    }
    
    
}
