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
    int flag_move, flag_turn = 2;
    int[][] ary_ban = {{1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2},
                     {1,0,0,0,0,0,0,0,2}};

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



        Label label1 = new Label("Top");

        
        canvas.setOnMouseClicked((event)->{
            x = ((int)event.getX()-20+50)/50;
            y = ((int)event.getY()-20+50)/50;

            if(flag_move == 0){
                if(ary_ban[x-1][y-1] == 0){
                    label1.setText("("+x+","+y+"）に駒は無い");
                    
                }else if(ary_ban[x-1][y-1] == 1 && flag_turn == 1){
                    label1.setText("("+x+","+y+"）の駒(青)");
                    temp_x = x;
                    temp_y = y;
                    flag_move = 1;
                }else if(ary_ban[x-1][y-1] == 2 && flag_turn == 2){
                    label1.setText("("+x+","+y+"）の駒(赤)");
                    temp_x = x;
                    temp_y = y;
                    flag_move = 1;
                }
            }else if(flag_move == 1){
                if(ary_ban[x-1][y-1] == 0){
                    label1.setText("("+x+","+y+"）に駒を移動した");
                    gc.clearRect(x*50-30+2,y*50-30+2,46,46);
                    gc.clearRect(temp_x*50-30+2,temp_y*50-30+2,46,46);

                    ary_ban[temp_x-1][temp_y-1] = 0;
                    flag_move = 0;

                    if(flag_turn == 2){
                        ary_ban[x-1][y-1] = 2;
                        koma.setFill(Color.RED);
                        koma.fillOval(x*50-30+2,y*50-30+2,46,46);
                        flag_turn = 1;
                    }else if(flag_turn == 1){
                        ary_ban[x-1][y-1] = 1;
                        koma.setFill(Color.BLUE);
                        koma.fillOval(x*50-30+2,y*50-30+2,46,46);
                        flag_turn = 2;
                    }

                }else if(ary_ban[x-1][y-1] >= 1){
                    label1.setText("("+x+","+y+"）に移動できない");
                    flag_move = 0;
                }
            }


        });
        
        


        VBox vbox = new VBox(); vbox.getChildren().addAll(label1,canvas);

        stage.setScene(new Scene(vbox));
        stage.show();
    }
}
