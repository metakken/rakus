import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.RotateTransition;

import javafx.scene.input.MouseEvent;

public class syougi extends Application{
    int x,y;
    int flag_move, flag_turn = 2, flag_koma;
    int temp_koma;
    /*1の位が・・・1=歩,2=角,3=飛車,4=香車,5=桂馬,6=銀,7=金,8=王*/
    /*10以上は自分の駒*/
    int[][] ary_ban = {{4,0,1,0,0,0,11,0,14},
                     {5,2,1,0,0,0,11,13,15},
                     {6,0,1,0,0,0,11,0,16},
                     {7,0,1,0,0,0,11,0,17},
                     {8,0,1,0,0,0,11,0,18},
                     {7,0,1,0,0,0,11,0,17},
                     {6,0,1,0,0,0,11,0,16},
                     {5,3,1,0,0,0,11,12,15},
                     {4,0,1,0,0,0,11,0,14}};

    int temp_x, temp_y; 





    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("将棋");

        Canvas canvas = new Canvas(520,520);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GraphicsContext koma = canvas.getGraphicsContext2D();


        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeLine(20,20,470,20);
        gc.strokeLine(20,70,470,70);
        gc.strokeLine(20,120,470,120);
        gc.strokeLine(20,170,470,170);
        gc.strokeLine(20,220,470,220);
        gc.strokeLine(20,270,470,270);
        gc.strokeLine(20,320,470,320);
        gc.strokeLine(20,370,470,370);
        gc.strokeLine(20,420,470,420);
        gc.strokeLine(20,470,470,470);

        gc.strokeLine(20,20,20,470);
        gc.strokeLine(70,20,70,470);
        gc.strokeLine(120,20,120,470);
        gc.strokeLine(170,20,170,470);
        gc.strokeLine(220,20,220,470);
        gc.strokeLine(270,20,270,470);
        gc.strokeLine(320,20,320,470);
        gc.strokeLine(370,20,370,470);
        gc.strokeLine(420,20,420,470);
        gc.strokeLine(470,20,470,470);

        koma.setFill(Color.BLUE);
        koma.fillOval(20+3,20+3,50-6,50-6);
        koma.fillOval(70+3,20+3,50-6,50-6);
        koma.fillOval(120+3,20+3,50-6,50-6);
        koma.fillOval(170+3,20+3,50-6,50-6);
        koma.fillOval(220+3,20+3,50-6,50-6);
        koma.fillOval(270+3,20+3,50-6,50-6);
        koma.fillOval(320+3,20+3,50-6,50-6);
        koma.fillOval(370+3,20+3,50-6,50-6);
        koma.fillOval(420+3,20+3,50-6,50-6);

        koma.fillOval(70+3,70+3,50-6,50-6);
        koma.fillOval(370+3,70+3,50-6,50-6);

        koma.fillOval(20+3,120+3,50-6,50-6);
        koma.fillOval(70+3,120+3,50-6,50-6);
        koma.fillOval(120+3,120+3,50-6,50-6);
        koma.fillOval(170+3,120+3,50-6,50-6);
        koma.fillOval(220+3,120+3,50-6,50-6);
        koma.fillOval(270+3,120+3,50-6,50-6);
        koma.fillOval(320+3,120+3,50-6,50-6);
        koma.fillOval(370+3,120+3,50-6,50-6);
        koma.fillOval(420+3,120+3,50-6,50-6);

        
        /*自分の駒*/        

        koma.setFill(Color.RED);
        koma.fillOval(20+3,420+3,50-6,50-6);
        koma.fillOval(70+3,420+3,50-6,50-6);
        koma.fillOval(120+3,420+3,50-6,50-6);
        koma.fillOval(170+3,420+3,50-6,50-6);
        koma.fillOval(220+3,420+3,50-6,50-6);
        koma.fillOval(270+3,420+3,50-6,50-6);
        koma.fillOval(320+3,420+3,50-6,50-6);
        koma.fillOval(370+3,420+3,50-6,50-6);
        koma.fillOval(420+3,420+3,50-6,50-6);

        koma.fillOval(70+3,370+3,50-6,50-6);
        koma.fillOval(370+3,370+3,50-6,50-6);

        koma.fillOval(20+3,320+3,50-6,50-6);
        koma.fillOval(70+3,320+3,50-6,50-6);
        koma.fillOval(120+3,320+3,50-6,50-6);
        koma.fillOval(170+3,320+3,50-6,50-6);
        koma.fillOval(220+3,320+3,50-6,50-6);
        koma.fillOval(270+3,320+3,50-6,50-6);
        koma.fillOval(320+3,320+3,50-6,50-6);
        koma.fillOval(370+3,320+3,50-6,50-6);
        koma.fillOval(420+3,320+3,50-6,50-6);



        Label label1 = new Label("Top");

        
        canvas.setOnMouseClicked((event)->{
            x = ((int)event.getX()-20+50)/50;
            y = ((int)event.getY()-20+50)/50;

            if(flag_move == 0){
                if(ary_ban[x-1][y-1] == 0){
                    label1.setText("("+x+","+y+"）に駒は無い");
                    
                }else if(ary_ban[x-1][y-1] >= 1 && ary_ban[x-1][y-1] <=8  && flag_turn == 1){
                    temp_x = x;
                    temp_y = y;
                    flag_move = 1;
                    switch(ary_ban[x-1][y-1]){
                        case 1:
                          label1.setText("("+x+","+y+"）の駒は歩(青)"); flag_koma=1;
                          if(ary_ban[x-1][y-1+1]>=11 || ary_ban[x-1][y-1+1]==0)ary_ban[x-1][y-1+1] = 9; 
                          break;
                        case 2:
                          label1.setText("("+x+","+y+"）の駒は角(青)"); flag_koma=2; break;
                        case 3:
                          label1.setText("("+x+","+y+"）の駒は飛車(青)"); flag_koma=3; break;
                        case 4:
                          label1.setText("("+x+","+y+"）の駒は香車(青)"); flag_koma=4; break;
                        case 5:
                          label1.setText("("+x+","+y+"）の駒は桂馬(青)"); flag_koma=5; 
                          if(ary_ban[x-1-1][y-1+2]>=11 || ary_ban[x-1-1][y-1+2]==0) ary_ban[x-1-1][y-1+2] = 9;
                          if(ary_ban[x-1+1][y-1+2]>=11 || ary_ban[x-1+1][y-1+2]==0) ary_ban[x-1+1][y-1+2] = 9;
                          break;
                          
                        case 6:
                          label1.setText("("+x+","+y+"）の駒は銀(青)"); flag_koma=6; 
                          if(ary_ban[x-1-1][y-1+1]>=11 || ary_ban[x-1-1][y-1+1]==0) ary_ban[x-1-1][y-1+1] = 9;
                          if(ary_ban[x-1][y-1+1]>=11 || ary_ban[x-1][y-1+1]==0) ary_ban[x-1][y-1+1] = 9;
                          if(ary_ban[x-1+1][y-1+1]>=11 || ary_ban[x-1+1][y-1+1]==0) ary_ban[x-1+1][y-1+1] = 9;
                          if(ary_ban[x-1-1][y-1-1]>=11 || ary_ban[x-1-1][y-1-1]==0) ary_ban[x-1-1][y-1-1] = 9;
                          if(ary_ban[x-1+1][y-1-1]>=11 || ary_ban[x-1+1][y-1-1]==0) ary_ban[x-1+1][y-1-1] = 9; 
                          break;
                        case 7:
                          label1.setText("("+x+","+y+"）の駒は金(青)"); flag_koma=7; 
                          if(ary_ban[x-1-1][y-1+1]>=11 || ary_ban[x-1-1][y-1+1]==0) ary_ban[x-1-1][y-1+1] = 9;
                          if(ary_ban[x-1][y-1+1]>=11 || ary_ban[x-1][y-1+1]==0) ary_ban[x-1][y-1+1] = 9;
                          if(ary_ban[x-1+1][y-1+1]>=11 || ary_ban[x-1+1][y-1+1]==0) ary_ban[x-1+1][y-1+1] = 9;
                          if(ary_ban[x-1-1][y-1]>=11 || ary_ban[x-1-1][y-1]==0) ary_ban[x-1-1][y-1] = 9;
                          if(ary_ban[x-1+1][y-1]>=11 || ary_ban[x-1+1][y-1]==0) ary_ban[x-1+1][y-1] = 9;
                          if(ary_ban[x-1][y-1-1]>=11 || ary_ban[x-1][y-1-1]==0) ary_ban[x-1][y-1-1] = 9;
                          break;
                        case 8:
                          label1.setText("("+x+","+y+"）の駒は王(青)"); flag_koma=8; 
                          if(ary_ban[x-1-1][y-1+1]>=11 || ary_ban[x-1-1][y-1+1]==0) ary_ban[x-1-1][y-1+1] = 9;
                          if(ary_ban[x-1][y-1+1]>=11 || ary_ban[x-1][y-1+1]==0) ary_ban[x-1][y-1+1] = 9;
                          if(ary_ban[x-1+1][y-1+1]>=11 || ary_ban[x-1+1][y-1+1]==0) ary_ban[x-1+1][y-1+1] = 9;
                          if(ary_ban[x-1-1][y-1]>=11 || ary_ban[x-1-1][y-1]==0) ary_ban[x-1-1][y-1] = 9;
                          if(ary_ban[x-1+1][y-1]>=11 || ary_ban[x-1+1][y-1]==0) ary_ban[x-1+1][y-1] = 9;
                          if(ary_ban[x-1-1][y-1-1]>=11 || ary_ban[x-1-1][y-1-1]==0) ary_ban[x-1-1][y-1-1] = 9;
                          if(ary_ban[x-1+1][y-1-1]>=11 || ary_ban[x-1+1][y-1-1]==0) ary_ban[x-1+1][y-1-1] = 9;
                          if(ary_ban[x-1][y-1-1]>=11 || ary_ban[x-1][y-1-1]==0) ary_ban[x-1][y-1-1] = 9;
                          break;
                    }
                }else if(ary_ban[x-1][y-1] >= 11 && ary_ban[x-1][y-1] <=18 && flag_turn == 2){
                    temp_x = x;
                    temp_y = y;
                    flag_move = 1;
                    switch(ary_ban[x-1][y-1]%10){
                        case 1:
                          label1.setText("("+x+","+y+"）の駒は歩(赤)"); flag_koma=11; 
                          if(ary_ban[x-1][y-1-1]<=8)ary_ban[x-1][y-1-1] = 9;
                          break;
                        case 2:
                          label1.setText("("+x+","+y+"）の駒は角(赤)"); flag_koma=12; 
                          break;
                        case 3:
                          label1.setText("("+x+","+y+"）の駒は飛車(赤)"); flag_koma=13; 
                          break;
                        case 4:
                          label1.setText("("+x+","+y+"）の駒は香車(赤)"); flag_koma=14; 
                          break;
                        case 5:
                          label1.setText("("+x+","+y+"）の駒は桂馬(赤)"); flag_koma=15;
                          if(ary_ban[x-1-1][y-1-2]<=8) ary_ban[x-1-1][y-1-2] = 9;
                          if(ary_ban[x-1+1][y-1-2]<=8) ary_ban[x-1+1][y-1-2] = 9;
                          break;
                        case 6:
                          label1.setText("("+x+","+y+"）の駒は銀(赤)"); flag_koma=16;
                          if(ary_ban[x-1-1][y-1-1]<=8) ary_ban[x-1-1][y-1-1] = 9;
                          if(ary_ban[x-1][y-1-1]<=8) ary_ban[x-1][y-1-1] = 9;
                          if(ary_ban[x-1+1][y-1-1]<=8) ary_ban[x-1+1][y-1-1] = 9;
                          if(ary_ban[x-1-1][y-1+1]<=8) ary_ban[x-1-1][y-1+1] = 9;
                          if(ary_ban[x-1+1][y-1+1]<=8) ary_ban[x-1+1][y-1+1] = 9; 
                          break;
                        case 7:
                          label1.setText("("+x+","+y+"）の駒は金(赤)"); flag_koma=17; 
                          if(ary_ban[x-1-1][y-1-1]<=8) ary_ban[x-1-1][y-1-1] = 9;
                          if(ary_ban[x-1][y-1-1]<=8) ary_ban[x-1][y-1-1] = 9;
                          if(ary_ban[x-1+1][y-1-1]<=8) ary_ban[x-1+1][y-1-1] = 9;
                          if(ary_ban[x-1-1][y-1]<=8) ary_ban[x-1][y-1] = 9;
                          if(ary_ban[x-1+1][y-1]<=8) ary_ban[x-1][y-1] = 9;
                          if(ary_ban[x-1][y-1+1]<=8) ary_ban[x-1][y-1+1] = 9;
                          break;
                        case 8:
                          label1.setText("("+x+","+y+"）の駒は王(赤)"); flag_koma=18; 
                          if(ary_ban[x-1-1][y-1-1]<=8) ary_ban[x-1-1][y-1-1] = 9;
                          if(ary_ban[x-1][y-1-1]<=8) ary_ban[x-1][y-1-1] = 9;
                          if(ary_ban[x-1+1][y-1-1]<=8) ary_ban[x-1+1][y-1-1] = 9;
                          if(ary_ban[x-1-1][y-1]<=8) ary_ban[x-1][y-1] = 9;
                          if(ary_ban[x-1+1][y-1]<=8) ary_ban[x-1][y-1] = 9;
                          if(ary_ban[x-1-1][y-1+1]<=8) ary_ban[x-1-1][y-1+1] = 9;
                          if(ary_ban[x-1+1][y-1+1]<=8) ary_ban[x-1+1][y-1+1] = 9;
                          if(ary_ban[x-1][y-1+1]<=8) ary_ban[x-1][y-1+1] = 9;
                          break;
                    }
                }
            }else if(flag_move == 1 && ary_ban[x-1][y-1]==9){

                 
                    label1.setText("("+x+","+y+"）に駒を移動した");
                    gc.clearRect(x*50-30+2,y*50-30+2,46,46);
                    gc.clearRect(temp_x*50-30+2,temp_y*50-30+2,46,46);

                    ary_ban[temp_x-1][temp_y-1] = 0;
                    flag_move = 0;

                    if(flag_turn == 2){
                        ary_ban[x-1][y-1] = flag_koma;
                        koma.setFill(Color.RED);
                        koma.fillOval(x*50-30+2,y*50-30+2,46,46);
                        flag_turn = 1;
                    }else if(flag_turn == 1){
                        ary_ban[x-1][y-1] = flag_koma;
                        koma.setFill(Color.BLUE);
                        koma.fillOval(x*50-30+2,y*50-30+2,46,46);
                        flag_turn = 2;
                    }



            }else if(flag_move == 1 && ary_ban[x-1][y-1]!=9){
                label1.setText("("+x+","+y+"）に駒を移動出来ない");
                flag_move = 0;
                for(int i = 0; i<ary_ban.length; i++){
                    for(int j = 0; j<ary_ban[i].length; j++){
                        if(ary_ban[i][j]==9)
                        ary_ban[i][j]=0;
                   }
                   }
            }


        });
        
        


        VBox vbox = new VBox(); vbox.getChildren().addAll(label1,canvas);

        stage.setScene(new Scene(vbox));
        stage.show();
    }
}
