package com.thy.easycheck.model;

import java.util.ArrayList;

public class LopaHandler {
    
    
    public enum TraverseMethod {FULL_ROW,FULL_COLUMN};
    
    
    private long id;
    private String defn;
    private String model;
    private ArrayList<Seat> seats;

    
    private int currentSeatIndex;
    
    
    
    public LopaHandler() {
        super();
        
        seats = new ArrayList<Seat>();
    }

    public Seat getNextSeat()
    {
        Seat seat;
        
        seat = seats.get(currentSeatIndex);
        
        currentSeatIndex = currentSeatIndex + 1;
        
        return seat;
    }
    
    public void setSeatIndex(int index)
    {
        if(index < 0)
            index = 0;
        else if(index >= seats.size())
            index = seats.size() - 1;
        currentSeatIndex = index;
    }
    
    public void resetSeatIndex()
    {
        currentSeatIndex = 0;
    }

    public void resetLopa()
    {
        for(Seat seat : seats)
        {
            seat.setSeatStatus(Seat.SeatStatus.NOT_CHECKED);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }

    public int getCurrentSeatIndex() {
        return currentSeatIndex;
    }

    public void setCurrentSeatIndex(int currentSeatIndex) {
        this.currentSeatIndex = currentSeatIndex;
    }

    public String getDefn() {
        return defn;
    }

    public void setDefn(String defn) {
        this.defn = defn;
    }
    
    
    

}
