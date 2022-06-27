package com.example.intelligentfaultdiagnosis;

public class ButtonList {
    public int start_id;
    public int size;

    public void setButton(int start,int size)
    {
        this.start_id=start;
        this.size=size;
    }
    public int getStart_id() {
        return start_id;
    }

    public int getSize() {
        return size;
    }
}
