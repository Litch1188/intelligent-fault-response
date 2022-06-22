package com.example.intelligentfaultdiagnosis;

import java.net.URL;

public class Solution_Data {
      String step_content;
      String img_link;
      int id;
      public String getSolutionDataContext(){return step_content;}
      public String getSolutionImgLink(){return img_link;}
      public int getSolutionId(){return id;}

      public void setStep(String step,String link,int ID){
            this.step_content=step;
            this.img_link="http://10.0.2.2:5000"+link;
            this.id=ID;
      }
}
