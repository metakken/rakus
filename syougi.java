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
import javafx.scene.paint.Color;

import javafx.scene.input.MouseEvent;


public class syougi extends Application{
    int x,y;
    int flag_move, flag_turn = 2, flag_koma;
    int c_koma[] = new int[8];
    int c_koma2[] = new int[8];/*飛車・角行用*/
    int c_koma3[] = new int[8];/*飛車・角行用*/
    int c_koma4[] = new int[8];/*飛車・角行用*/
    int c_koma5[] = new int[8];/*馬・龍用*/

    int temp_koma;
    //相手が持っている駒の数を記録するための変数
    int ekin, egin, ehisya, ekaku, ekei, ekyou, efu;
    //自分が持っている駒の数を記録するための変数
    int mkin, mgin, mhisya, mkaku, mkei, mkyou, mfu;
    /*1の位が・・・1=歩,2=角,3=飛車,4=香車,5=桂馬,6=銀,7=金,8=王*/
    /*11以上かつ18以下は自分の駒*/
    /*成り駒は+20*/
    int ary_ban[][]  = {{4,0,1,0,0,0,11,0,14},
                     {5,3,1,0,0,0,11,12,15},
                     {6,0,1,0,0,0,11,0,16},
                     {7,0,1,0,0,0,11,0,17},
                     {8,0,1,0,0,0,11,0,18},
                     {7,0,1,0,0,0,11,0,17},
                     {6,0,1,0,0,0,11,0,16},
                     {5,2,1,0,0,0,11,13,15},
                     {4,0,1,0,0,0,11,0,14}};

    int ary_banc[][]  = {{0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0},
                     {0,0,0,0,0,0,0,0,0}};

    int temp_x, temp_y; 
    int count_masu = 0;
    int count_masu2 = 0;
    int count_masu3 = 0;
    int count_masu4 = 0;

    /*成る関数（相手エリアに入ったら+20する）*/
    public void naru(Label label1){
      if(y-1>5 && flag_turn==1 && flag_koma<20){
        label1.setText("("+x+","+y+"）の駒は成りました");
        ary_ban[x-1][y-1] +=20;
      }else if(y-1<3 && flag_turn==2 && flag_koma<20){
        label1.setText("("+x+","+y+"）の駒は成りました");
        ary_ban[x-1][y-1] +=20;
      }
    }

    /*取った駒を判定する関数*/
    public void getkoma(Label label1, Label efu, Label ekaku, Label ehisya, Label ekyou, Label ekei, Label egin, Label ekin,
                        Label mfu, Label mkaku, Label mhisya, Label mkyou, Label mkei, Label mgin, Label mkin){
      int a=0;
      if(flag_turn==1){/*青のターンのとき*/
        a=10;
        if(ary_ban[x-1][y-1]==1+a || ary_ban[x-1][y-1]==1+3*a){
          this.efu++;
          efu.setText("歩 X"+this.efu);
        }else if(ary_ban[x-1][y-1]==2+a || ary_ban[x-1][y-1]==2+3*a){
          this.ekaku++;
          ekaku.setText("角 X "+ this.ekaku);
        }else if(ary_ban[x-1][y-1]==3+a || ary_ban[x-1][y-1]==3+3*a){
          this.ehisya++;
          ehisya.setText("飛 X"+this.ehisya);
        }else if(ary_ban[x-1][y-1]==4+a || ary_ban[x-1][y-1]==4+3*a){
          this.ekyou++;
          ekyou.setText("香 X"+this.ekyou);
        }else if(ary_ban[x-1][y-1]==5+a || ary_ban[x-1][y-1]==5+3*a){
          this.ekei++;
          ekei.setText("桂 X "+this.ekei);
        }else if(ary_ban[x-1][y-1]==6+a || ary_ban[x-1][y-1]==6+3*a){
          this.egin++;
          egin.setText("銀 X "+this.egin);
        }else if(ary_ban[x-1][y-1]==7+a){
          this.ekin++;
          ekin.setText("金 X "+this.ekin);
        }else if(ary_ban[x-1][y-1]==8+a){
          label1.setText("青の勝ち");
        }
      }else if(flag_turn==2){
        if(ary_ban[x-1][y-1]==1+a || ary_ban[x-1][y-1]==1+2*a){
          this.mfu++;
          mfu.setText("歩 X"+this.mfu);
        }else if(ary_ban[x-1][y-1]==2+a || ary_ban[x-1][y-1]==2+2*a){
          this.mkaku++;
          mkaku.setText("角 X "+ this.mkaku);
        }else if(ary_ban[x-1][y-1]==3+a || ary_ban[x-1][y-1]==3+2*a){
          this.mhisya++;
          mhisya.setText("飛 X"+this.mhisya);
        }else if(ary_ban[x-1][y-1]==4+a || ary_ban[x-1][y-1]==4+2*a){
          this.mkyou++;
          mkyou.setText("香 X"+this.mkyou);
        }else if(ary_ban[x-1][y-1]==5+a || ary_ban[x-1][y-1]==5+2*a){
          this.mkei++;
          mkei.setText("桂 X "+this.mkei);
        }else if(ary_ban[x-1][y-1]==6+a || ary_ban[x-1][y-1]==6+2*a){
          this.mgin++;
          mgin.setText("銀 X "+this.mgin);
        }else if(ary_ban[x-1][y-1]==7+a){
          this.mkin++;
          mkin.setText("金 X "+this.mkin);
        }else if(ary_ban[x-1][y-1]==8+a){
          label1.setText("赤の勝ち");
        }
      }
      
    }


    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("将棋");

        Canvas canvas = new Canvas(500,500);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GraphicsContext koma = canvas.getGraphicsContext2D();
        int x1, x2, y1, y2;

      //盤面の色の塗りつぶし
        gc.setFill( Color.rgb(255,222,117));
        gc.fillRect(20,20,canvas.getWidth()-50,canvas.getHeight()-50); 
      
      //とった駒を置く場所を作成

        ekin=egin=ehisya=ekaku=ekei=ekyou=efu=0;
        mkin=mgin=mhisya=mkaku=mkei=mkyou=mfu=0;

        Label ekin = new Label();
        Label egin = new Label();
        Label ehisya = new Label();
        Label ekaku = new Label();
        Label ekei = new Label();
        Label ekyou = new Label();
        Label efu = new Label();

        ekin.setText("金 X "+this.ekin);
        egin.setText("銀 X "+this.egin);
        ehisya.setText("飛 X"+this.ehisya);
        ekaku.setText("角 X "+ this.ekaku);
        ekei.setText("桂 X "+this.ekei);
        ekyou.setText("香 X"+this.ekyou);
        efu.setText("歩 X"+this.efu);

        ekin.setPrefSize(150, 50);
        egin.setPrefSize(150, 50);
        ehisya.setPrefSize(150, 50);
        ekaku.setPrefSize(150, 50);
        ekei.setPrefSize(150, 50);
        ekyou.setPrefSize(150, 50);
        efu.setPrefSize(150, 50);


        Label mkin = new Label();
        Label mgin = new Label();
        Label mhisya = new Label();
        Label mkaku = new Label();
        Label mkei = new Label();
        Label mkyou = new Label();
        Label mfu = new Label();

        mkin.setText("金 X "+this.mkin);
        mgin.setText("銀 X "+this.mgin);
        mhisya.setText("飛 X"+this.mhisya);
        mkaku.setText("角 X "+ this.mkaku);
        mkei.setText("桂 X "+this.mkei);
        mkyou.setText("香 X"+this.mkyou);
        mfu.setText("歩 X"+this.mfu);

        mkin.setPrefSize(150, 50);
        mgin.setPrefSize(150, 50);
        mhisya.setPrefSize(150, 50);
        mkaku.setPrefSize(150, 50);
        mkei.setPrefSize(150, 50);
        mkyou.setPrefSize(150, 50);
        mfu.setPrefSize(150, 50);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        
        x1=20;
        x2=470; 
        y1=20;
        y2=20;
        
        while(y1<=470){
          gc.strokeLine(x1, y1, x2, y2);
          y1+=50;
          y2+=50;
        }
        x1=20;
        x2=20;
        y1=20;
        y2=470;
        while(x1<=470){
          gc.strokeLine(x1, y1, x2, y2);
          x1+=50;
          x2+=50;
        }
        
        //相手の駒
        koma.setFill(Color.BLUE);
        int kx1, kx2, ky1,ky2;
        kx1=23;
        kx2=23;
        ky1=44;
        ky2=44;
        while(kx1<=423){
          koma.fillOval(kx1,kx2,ky1,ky2);
          kx1+=50;
        }

        koma.fillOval(70+3,70+3,50-6,50-6);
        koma.fillOval(370+3,70+3,50-6,50-6);

        kx1=23;
        kx2=123;
        ky1=44;
        ky2=44;
        while(kx1<=423){
          koma.fillOval(kx1,kx2,ky1,ky2);
          kx1+=50;
        }
        
        
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
                    
                }else if((ary_ban[x-1][y-1] >= 1 && ary_ban[x-1][y-1] <=8  && flag_turn == 1) || (ary_ban[x-1][y-1] >= 21 && ary_ban[x-1][y-1] <=26  && flag_turn == 1)){
                    temp_x = x;
                    temp_y = y;
                    flag_move = 1;
                    switch(ary_ban[x-1][y-1]){
                        case 1:
                          label1.setText("("+x+","+y+"）の駒は歩(青)"); flag_koma=1;
                          if((ary_ban[x-1][y-1+1]>=11 || ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;} 
                          break;
                        case 2:
                          label1.setText("("+x+","+y+"）の駒は角(青)"); flag_koma=2; 
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_kaku = 0;    
                          /*左上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(x-1-i>-1){
                              if(ary_ban[x-1-i][y-1-i]==0){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                              } else if((ary_ban[x-1-i][y-1-i]>=11 && ary_ban[x-1-i][y-1-i] <=18) || (ary_ban[x-1-i][y-1-i] >= 31 && ary_ban[x-1-i][y-1-i] <=36)){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1-i][y-1-i]>=1 && ary_ban[x-1-i][y-1-i]<=8) || (ary_ban[x-1-i][y-1-i] >= 21 && ary_ban[x-1-i][y-1-i] <=26)) {flag_kaku++;}
                              if(flag_kaku == 1){i = 9;} 
                              count_masu++;
                            }

                          }
                          if(flag_kaku != 1){flag_kaku = 1;}

                          /*左下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(x-1-i>-1){
                              if(ary_ban[x-1-i][y-1+i]==0){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                              } else if((ary_ban[x-1-i][y-1+i]>=11 && ary_ban[x-1-i][y-1+i] <=18) || (ary_ban[x-1-i][y-1+i] >= 31 && ary_ban[x-1-i][y-1+i] <=36)){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1-i][y-1+i]>=1 && ary_ban[x-1-i][y-1+i]<=8) || (ary_ban[x-1-i][y-1+i] >= 21 && ary_ban[x-1-i][y-1+i] <=26)) {flag_kaku++;}
                              if(flag_kaku == 2){i = 9;} 
                              count_masu2++;
                            }
                            
                          }
                          if(flag_kaku != 2){flag_kaku = 2;}

                          /*右上方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1-i>-1){
                              if(ary_ban[x-1+i][y-1-i]==0){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                              } else if((ary_ban[x-1+i][y-1]>=11 && ary_ban[x-1+i][y-1-i] <=18) || (ary_ban[x-1+i][y-1-i] >= 31 && ary_ban[x-1+i][y-1-i] <=36)){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1+i][y-1-i]>=1 && ary_ban[x-1+i][y-1-i]<=8) || (ary_ban[x-1+i][y-1-i] >= 21 && ary_ban[x-1+i][y-1-i] <=26)) {flag_kaku++;}
                              if(flag_kaku == 3){i = 9;} 
                              count_masu3++;
                            }
                            
                          }
                          if(flag_kaku != 3){flag_kaku = 3;}

                          /*右下方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1+i<9){
                              if(ary_ban[x-1+i][y-1+i]==0){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                              } else if((ary_ban[x-1+i][y-1+i]>=11 && ary_ban[x-1+i][y-1+i] <=18) || (ary_ban[x-1+i][y-1+i] >= 31 && ary_ban[x-1+i][y-1+i] <=36)){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1+i][y-1+i]>=1 && ary_ban[x-1+i][y-1+i]<=8) || (ary_ban[x-1+i][y-1+i] >= 21 && ary_ban[x-1+i][y-1+i] <=26)) {flag_kaku++;}
                              if(flag_kaku == 4){i = 9;} 
                              count_masu4++;
                            }
                            
                          }
                          break;
                        case 3:
                          label1.setText("("+x+","+y+"）の駒は飛車(青)"); flag_koma=3;
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_h = 0;    
                          /*上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(ary_ban[x-1][y-1-i]==0){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                            } else if((ary_ban[x-1][y-1-i]>=11 && ary_ban[x-1][y-1-i] <=18) || (ary_ban[x-1][y-1-i] >= 31 && ary_ban[x-1][y-1-i] <=36)){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1][y-1-i]>=1 && ary_ban[x-1][y-1-i]<=8) || (ary_ban[x-1][y-1-i] >= 21 && ary_ban[x-1][y-1-i] <=26)) {flag_h++;}
                            if(flag_h == 1){i = 9;} 
                            count_masu++;
                          }
                          if(flag_h != 1){flag_h = 1;}

                          /*下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(ary_ban[x-1][y-1+i]==0){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                            } else if((ary_ban[x-1][y-1+i]>=11 && ary_ban[x-1][y-1+i] <=18) || (ary_ban[x-1][y-1+i] >= 31 && ary_ban[x-1][y-1+i] <=36)){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1][y-1+i]>=1 && ary_ban[x-1][y-1+i]<=8) || (ary_ban[x-1][y-1+i] >= 21 && ary_ban[x-1][y-1+i] <=26)) {flag_h++;}
                            if(flag_h == 2){i = 9;} 
                            count_masu2++;
                          }
                          if(flag_h != 2){flag_h = 2;}

                          /*右方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(ary_ban[x-1+i][y-1]==0){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                            } else if((ary_ban[x-1+i][y-1]>=11 && ary_ban[x-1+i][y-1] <=18) || (ary_ban[x-1+i][y-1] >= 31 && ary_ban[x-1+i][y-1] <=36)){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1+i][y-1]>=1 && ary_ban[x-1+i][y-1]<=8) || (ary_ban[x-1+i][y-1] >= 21 && ary_ban[x-1+i][y-1] <=26)) {flag_h++;}
                            if(flag_h == 3){i = 9;} 
                            count_masu3++;
                          }
                          if(flag_h != 3){flag_h = 3;}

                          /*左方向*/
                          for( int i=1; x-1-i>-1; i++){
                            if(ary_ban[x-1-i][y-1]==0){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                            } else if((ary_ban[x-1-i][y-1]>=11 && ary_ban[x-1-i][y-1] <=18) || (ary_ban[x-1-i][y-1] >= 31 && ary_ban[x-1-i][y-1] <=36)){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1-i][y-1]>=1 && ary_ban[x-1-i][y-1]<=8) || (ary_ban[x-1-i][y-1] >= 21 && ary_ban[x-1-i][y-1] <=26)) {flag_h++;}
                            if(flag_h == 4){i = 9;} 
                            count_masu4++;
                          }
                          break;
                        case 4:
                          label1.setText("("+x+","+y+"）の駒は香車(青)"); flag_koma=4;
                          count_masu = 0; 
                          int flag_k = 0;
                          for( int i=1; y-1+i<9; i++){
                            if(ary_ban[x-1][y-1+i]==0){
                              c_koma[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                            } else if((ary_ban[x-1][y-1+i]>=11 && ary_ban[x-1][y-1+i] <=18) || (ary_ban[x-1][y-1+i] >= 31 && ary_ban[x-1][y-1+i] <=36)){
                              c_koma[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                              flag_k++;
                            } else if((ary_ban[x-1][y-1+i]>=1 && ary_ban[x-1][y-1+i]<=8) || (ary_ban[x-1][y-1+i] >= 21 && ary_ban[x-1][y-1+i] <=26)) {flag_k++;}
                            if(flag_k == 1){i = 9;} 
                            count_masu++;
                          }
                          break;
                        case 5:
                          label1.setText("("+x+","+y+"）の駒は桂馬(青)"); flag_koma=5; 
                          if((ary_ban[x-1-1][y-1+2]>=11 && ary_ban[x-1-1][y-1+2] <=18) || ary_ban[x-1-1][y-1+2]==0 || (ary_ban[x-1-1][y-1+2] >= 31 && ary_ban[x-1-1][y-1+2] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+2]; ary_banc[x-1-1][y-1+2] = 9;}
                          if((ary_ban[x-1+1][y-1+2]>=11 && ary_ban[x-1+1][y-1+2] <=18) || ary_ban[x-1+1][y-1+2]==0 || (ary_ban[x-1+1][y-1+2] >= 31 && ary_ban[x-1+1][y-1+2] <=36)){c_koma[1] = ary_ban[x-1+1][y-1+2]; ary_banc[x-1+1][y-1+2] = 9;}
                          break;
                          
                        case 6:
                          label1.setText("("+x+","+y+"）の駒は銀(青)"); flag_koma=6; 
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[1] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma[2] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1-1][y-1-1]>=11 && ary_ban[x-1-1][y-1-1] <=18) || ary_ban[x-1-1][y-1-1]==0 || (ary_ban[x-1-1][y-1-1] >= 31 && ary_ban[x-1-1][y-1-1] <=36)){c_koma[3] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]>=11 && ary_ban[x-1+1][y-1-1] <=18) || ary_ban[x-1+1][y-1-1]==0 || (ary_ban[x-1+1][y-1-1] >= 31 && ary_ban[x-1+1][y-1-1] <=36)){c_koma[4] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;} 
                          break;
                        case 7:
                          label1.setText("("+x+","+y+"）の駒は金(青)"); flag_koma=7; 
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[1] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma[2] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1-1][y-1]>=11 && ary_ban[x-1-1][y-1] <=18) || ary_ban[x-1-1][y-1]==0 || (ary_ban[x-1-1][y-1] >= 31 && ary_ban[x-1-1][y-1] <=36)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]>=11 && ary_ban[x-1+1][y-1] <=18) || ary_ban[x-1+1][y-1]==0 || (ary_ban[x-1+1][y-1] >= 31 && ary_ban[x-1+1][y-1] <=36)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1-1]>=11 && ary_ban[x-1][y-1-1] <=18) || ary_ban[x-1][y-1-1]==0 || (ary_ban[x-1][y-1-1] >= 31 && ary_ban[x-1][y-1-1] <=36)){c_koma[5] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          break;
                        case 8:
                          label1.setText("("+x+","+y+"）の駒は王(青)"); flag_koma=8; 
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[1] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma[2] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1-1][y-1]>=11 && ary_ban[x-1-1][y-1] <=18) || ary_ban[x-1-1][y-1]==0 || (ary_ban[x-1-1][y-1] >= 31 && ary_ban[x-1-1][y-1] <=36)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]>=11 && ary_ban[x-1+1][y-1] <=18) || ary_ban[x-1+1][y-1]==0 || (ary_ban[x-1+1][y-1] >= 31 && ary_ban[x-1+1][y-1] <=36)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1-1][y-1-1]>=11 && ary_ban[x-1-1][y-1-1] <=18) || ary_ban[x-1-1][y-1-1]==0 || (ary_ban[x-1-1][y-1-1] >= 31 && ary_ban[x-1-1][y-1-1] <=36)){c_koma[5] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]>=11 && ary_ban[x-1+1][y-1-1] <=18) || ary_ban[x-1+1][y-1-1]==0 || (ary_ban[x-1+1][y-1-1] >= 31 && ary_ban[x-1+1][y-1-1] <=36)){c_koma[6] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]>=11 && ary_ban[x-1][y-1-1] <=18) || ary_ban[x-1][y-1-1]==0 || (ary_ban[x-1][y-1-1] >= 31 && ary_ban[x-1][y-1-1] <=36)){c_koma[7] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          break;
                        case 21:
                          label1.setText("("+x+","+y+"）の駒はと金(青)"); flag_koma=21; 
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[1] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma[2] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1-1][y-1]>=11 && ary_ban[x-1-1][y-1] <=18) || ary_ban[x-1-1][y-1]==0 || (ary_ban[x-1-1][y-1] >= 31 && ary_ban[x-1-1][y-1] <=36)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]>=11 && ary_ban[x-1+1][y-1] <=18) || ary_ban[x-1+1][y-1]==0 || (ary_ban[x-1+1][y-1] >= 31 && ary_ban[x-1+1][y-1] <=36)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1-1]>=11 && ary_ban[x-1][y-1-1] <=18) || ary_ban[x-1][y-1-1]==0 || (ary_ban[x-1][y-1-1] >= 31 && ary_ban[x-1][y-1-1] <=36)){c_koma[5] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          break;
                        case 22:
                          label1.setText("("+x+","+y+"）の駒は馬(青)"); flag_koma=22; 
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_kaku2 = 0;    
                          /*左上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(x-1-i>-1){
                              if(ary_ban[x-1-i][y-1-i]==0){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                              } else if((ary_ban[x-1-i][y-1-i]>=11 && ary_ban[x-1-i][y-1-i] <=18) || (ary_ban[x-1-i][y-1-i] >= 31 && ary_ban[x-1-i][y-1-i] <=36)){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1-i][y-1-i]>=1 && ary_ban[x-1-i][y-1-i]<=8) || (ary_ban[x-1-i][y-1-i] >= 21 && ary_ban[x-1-i][y-1-i] <=26)) {flag_kaku2++;}
                              if(flag_kaku2 == 1){i = 9;} 
                              count_masu++;
                            }

                          }
                          if(flag_kaku2 != 1){flag_kaku2 = 1;}

                          /*左下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(x-1-i>-1){
                              if(ary_ban[x-1-i][y-1+i]==0){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                              } else if((ary_ban[x-1-i][y-1+i]>=11 && ary_ban[x-1-i][y-1+i] <=18) || (ary_ban[x-1-i][y-1+i] >= 31 && ary_ban[x-1-i][y-1+i] <=36)){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1-i][y-1+i]>=1 && ary_ban[x-1-i][y-1+i]<=8) || (ary_ban[x-1-i][y-1+i] >= 21 && ary_ban[x-1-i][y-1+i] <=26)) {flag_kaku2++;}
                              if(flag_kaku2 == 2){i = 9;} 
                              count_masu2++;
                            }
                            
                          }
                          if(flag_kaku2 != 2){flag_kaku2 = 2;}

                          /*右上方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1-i>-1){
                              if(ary_ban[x-1+i][y-1-i]==0){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                              } else if((ary_ban[x-1+i][y-1]>=11 && ary_ban[x-1+i][y-1-i] <=18) || (ary_ban[x-1+i][y-1-i] >= 31 && ary_ban[x-1+i][y-1-i] <=36)){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1+i][y-1-i]>=1 && ary_ban[x-1+i][y-1-i]<=8) || (ary_ban[x-1+i][y-1-i] >= 21 && ary_ban[x-1+i][y-1-i] <=26)) {flag_kaku2++;}
                              if(flag_kaku2 == 3){i = 9;} 
                              count_masu3++;
                            }
                            
                          }
                          if(flag_kaku2 != 3){flag_kaku2 = 3;}

                          /*右下方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1+i<9){
                              if(ary_ban[x-1+i][y-1+i]==0){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                              } else if((ary_ban[x-1+i][y-1+i]>=11 && ary_ban[x-1+i][y-1+i] <=18) || (ary_ban[x-1+i][y-1+i] >= 31 && ary_ban[x-1+i][y-1+i] <=36)){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1+i][y-1+i]>=1 && ary_ban[x-1+i][y-1+i]<=8) || (ary_ban[x-1+i][y-1+i] >= 21 && ary_ban[x-1+i][y-1+i] <=26)) {flag_kaku2++;}
                              if(flag_kaku2 == 4){i = 9;} 
                              count_masu4++;
                            }
                            
                          }
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma5[0] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}/*下*/
                          if((ary_ban[x-1-1][y-1]>=11 && ary_ban[x-1-1][y-1] <=18) || ary_ban[x-1-1][y-1]==0 || (ary_ban[x-1-1][y-1] >= 31 && ary_ban[x-1-1][y-1] <=36)){c_koma5[1] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}/*左*/
                          if((ary_ban[x-1+1][y-1]>=11 && ary_ban[x-1+1][y-1] <=18) || ary_ban[x-1+1][y-1]==0 || (ary_ban[x-1+1][y-1] >= 31 && ary_ban[x-1+1][y-1] <=36)){c_koma5[2] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}/*右*/
                          if((ary_ban[x-1][y-1-1]>=11 && ary_ban[x-1][y-1-1] <=18) || ary_ban[x-1][y-1-1]==0 || (ary_ban[x-1][y-1-1] >= 31 && ary_ban[x-1][y-1-1] <=36)){c_koma5[3] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}/*上*/
                          break;
                        case 23:
                          label1.setText("("+x+","+y+"）の駒は龍(青)"); flag_koma=23;
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_h2 = 0;    
                          /*上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(ary_ban[x-1][y-1-i]==0){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                            } else if((ary_ban[x-1][y-1-i]>=11 && ary_ban[x-1][y-1-i] <=18) || (ary_ban[x-1][y-1-i] >= 31 && ary_ban[x-1][y-1-i] <=36)){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1][y-1-i]>=1 && ary_ban[x-1][y-1-i]<=8) || (ary_ban[x-1][y-1-i] >= 21 && ary_ban[x-1][y-1-i] <=26)) {flag_h2++;}
                            if(flag_h2 == 1){i = 9;} 
                            count_masu++;
                          }
                          if(flag_h2 != 1){flag_h2 = 1;}

                          /*下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(ary_ban[x-1][y-1+i]==0){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                            } else if((ary_ban[x-1][y-1+i]>=11 && ary_ban[x-1][y-1+i] <=18) || (ary_ban[x-1][y-1+i] >= 31 && ary_ban[x-1][y-1+i] <=36)){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1][y-1+i]>=1 && ary_ban[x-1][y-1+i]<=8) || (ary_ban[x-1][y-1+i] >= 21 && ary_ban[x-1][y-1+i] <=26)) {flag_h2++;}
                            if(flag_h2 == 2){i = 9;} 
                            count_masu2++;
                          }
                          if(flag_h2 != 2){flag_h2 = 2;}

                          /*右方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(ary_ban[x-1+i][y-1]==0){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                            } else if((ary_ban[x-1+i][y-1]>=11 && ary_ban[x-1+i][y-1] <=18) || (ary_ban[x-1+i][y-1] >= 31 && ary_ban[x-1+i][y-1] <=36)){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1+i][y-1]>=1 && ary_ban[x-1+i][y-1]<=8) || (ary_ban[x-1+i][y-1] >= 21 && ary_ban[x-1+i][y-1] <=26)) {flag_h2++;}
                            if(flag_h2 == 3){i = 9;} 
                            count_masu3++;
                          }
                          if(flag_h2 != 3){flag_h2 = 3;}

                          /*左方向*/
                          for( int i=1; x-1-i>-1; i++){
                            if(ary_ban[x-1-i][y-1]==0){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                            } else if((ary_ban[x-1-i][y-1]>=11 && ary_ban[x-1-i][y-1] <=18) || (ary_ban[x-1-i][y-1] >= 31 && ary_ban[x-1-i][y-1] <=36)){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1-i][y-1]>=1 && ary_ban[x-1-i][y-1]<=8) || (ary_ban[x-1-i][y-1] >= 21 && ary_ban[x-1-i][y-1] <=26)) {flag_h2++;}
                            if(flag_h2 == 4){i = 9;} 
                            count_masu4++;
                          }
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma5[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}/*左下*/
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma5[1] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}/*右下*/
                          if((ary_ban[x-1-1][y-1-1]>=11 && ary_ban[x-1-1][y-1-1] <=18) || ary_ban[x-1-1][y-1-1]==0 || (ary_ban[x-1-1][y-1-1] >= 31 && ary_ban[x-1-1][y-1-1] <=36)){c_koma5[2] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}/*左上*/
                          if((ary_ban[x-1+1][y-1-1]>=11 && ary_ban[x-1+1][y-1-1] <=18) || ary_ban[x-1+1][y-1-1]==0 || (ary_ban[x-1+1][y-1-1] >= 31 && ary_ban[x-1+1][y-1-1] <=36)){c_koma5[3] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}/*右上*/
                          break;
                        case 24:
                          label1.setText("("+x+","+y+"）の駒は成香(青)"); flag_koma=24; 
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[1] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma[2] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1-1][y-1]>=11 && ary_ban[x-1-1][y-1] <=18) || ary_ban[x-1-1][y-1]==0 || (ary_ban[x-1-1][y-1] >= 31 && ary_ban[x-1-1][y-1] <=36)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]>=11 && ary_ban[x-1+1][y-1] <=18) || ary_ban[x-1+1][y-1]==0 || (ary_ban[x-1+1][y-1] >= 31 && ary_ban[x-1+1][y-1] <=36)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1-1]>=11 && ary_ban[x-1][y-1-1] <=18) || ary_ban[x-1][y-1-1]==0 || (ary_ban[x-1][y-1-1] >= 31 && ary_ban[x-1][y-1-1] <=36)){c_koma[5] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          break;
                        case 25:
                          label1.setText("("+x+","+y+"）の駒は成桂(青)"); flag_koma=25; 
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[1] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma[2] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1-1][y-1]>=11 && ary_ban[x-1-1][y-1] <=18) || ary_ban[x-1-1][y-1]==0 || (ary_ban[x-1-1][y-1] >= 31 && ary_ban[x-1-1][y-1] <=36)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]>=11 && ary_ban[x-1+1][y-1] <=18) || ary_ban[x-1+1][y-1]==0 || (ary_ban[x-1+1][y-1] >= 31 && ary_ban[x-1+1][y-1] <=36)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1-1]>=11 && ary_ban[x-1][y-1-1] <=18) || ary_ban[x-1][y-1-1]==0 || (ary_ban[x-1][y-1-1] >= 31 && ary_ban[x-1][y-1-1] <=36)){c_koma[5] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          break;
                        case 26:
                          label1.setText("("+x+","+y+"）の駒は成銀(青)"); flag_koma=26; 
                          if((ary_ban[x-1-1][y-1+1]>=11 && ary_ban[x-1-1][y-1+1] <=18) || ary_ban[x-1-1][y-1+1]==0 || (ary_ban[x-1-1][y-1+1] >= 31 && ary_ban[x-1-1][y-1+1] <=36)){c_koma[0] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]>=11 && ary_ban[x-1][y-1+1] <=18) || ary_ban[x-1][y-1+1]==0 || (ary_ban[x-1][y-1+1] >= 31 && ary_ban[x-1][y-1+1] <=36)){c_koma[1] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]>=11 && ary_ban[x-1+1][y-1+1] <=18) || ary_ban[x-1+1][y-1+1]==0 || (ary_ban[x-1+1][y-1+1] >= 31 && ary_ban[x-1+1][y-1+1] <=36)){c_koma[2] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1-1][y-1]>=11 && ary_ban[x-1-1][y-1] <=18) || ary_ban[x-1-1][y-1]==0 || (ary_ban[x-1-1][y-1] >= 31 && ary_ban[x-1-1][y-1] <=36)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]>=11 && ary_ban[x-1+1][y-1] <=18) || ary_ban[x-1+1][y-1]==0 || (ary_ban[x-1+1][y-1] >= 31 && ary_ban[x-1+1][y-1] <=36)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1-1]>=11 && ary_ban[x-1][y-1-1] <=18) || ary_ban[x-1][y-1-1]==0 || (ary_ban[x-1][y-1-1] >= 31 && ary_ban[x-1][y-1-1] <=36)){c_koma[5] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          break;
                    }
                }else if((ary_ban[x-1][y-1] >= 11 && ary_ban[x-1][y-1] <=18 && flag_turn == 2) || (ary_ban[x-1][y-1] >= 31 && ary_ban[x-1][y-1] <=36 && flag_turn == 2)){
                    temp_x = x;
                    temp_y = y;
                    flag_move = 1;
                    switch(ary_ban[x-1][y-1]){
                        case 11:
                          label1.setText("("+x+","+y+"）の駒は歩(赤)"); flag_koma=11; 
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          break;
                        case 12:
                          label1.setText("("+x+","+y+"）の駒は角(赤)"); flag_koma=12;
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_kaku = 0;    
                          /*左上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(x-1-i>-i){
                              if(ary_ban[x-1-i][y-1-i]==0){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                              } else if((ary_ban[x-1-i][y-1-i]<=8 && ary_ban[x-1-i][y-1-i]>=1) || (ary_ban[x-1-i][y-1-i]>=21 && ary_ban[x-1-i][y-1-i]<=28)){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1-i][y-1-i]>=11 && ary_ban[x-1-i][y-1-i]<=18) || (ary_ban[x-1-i][y-1-i]>=31 && ary_ban[x-1-i][y-1-i]<=38)) {flag_kaku++;}
                              if(flag_kaku == 1){i = 9;} 
                              count_masu++;
                            }
                            
                          }
                          if(flag_kaku != 1){flag_kaku = 1;}

                          /*左下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(x-1-i>-1){
                              if(ary_ban[x-1-i][y-1+i]==0){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                              } else if((ary_ban[x-1-i][y-1+i]<=8 && ary_ban[x-1-i][y-1+i]>=1) || (ary_ban[x-1-i][y-1+i]>=21 && ary_ban[x-1-i][y-1+i]<=28)){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1-i][y-1+i]>=11 && ary_ban[x-1-i][y-1+i]<=18) || (ary_ban[x-1-i][y-1+i]>=31 && ary_ban[x-1-i][y-1+i]<=38)) {flag_kaku++;}
                              if(flag_kaku == 2){i = 9;} 
                              count_masu2++;
                            }
                            
                          }
                          if(flag_kaku != 2){flag_kaku = 2;}

                          /*右上方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1-i>-1){
                              if(ary_ban[x-1+i][y-1-i]==0){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                              } else if((ary_ban[x-1+i][y-1-i]<=8 && ary_ban[x-1+i][y-1-i]>=1) || (ary_ban[x-1+i][y-1-i]>=21 && ary_ban[x-1+i][y-1-i]<=28)){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1+i][y-1-i]>=11 && ary_ban[x-1+i][y-1-i]<=18) || (ary_ban[x-1+i][y-1-i]>=31 && ary_ban[x-1+i][y-1-i]<=38)) {flag_kaku++;}
                              if(flag_kaku == 3){i = 9;} 
                              count_masu3++;
                            }
                            
                          }
                          if(flag_kaku != 3){flag_kaku = 3;}

                          /*右上方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1+i<9){
                              if(ary_ban[x-1+i][y-1+i]==0){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                              } else if((ary_ban[x-1+i][y-1+i]<=8 && ary_ban[x-1+i][y-1+i]>=1) || (ary_ban[x-1+i][y-1+i]>=21 && ary_ban[x-1+i][y-1+i]<=28)){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                                flag_kaku++;
                              } else if((ary_ban[x-1+i][y-1+i]>=11 && ary_ban[x-1+i][y-1+i]<=18) || (ary_ban[x-1+i][y-1+i]>=31 && ary_ban[x-1+i][y-1+i]<=38)) {flag_kaku++;}
                              if(flag_kaku == 4){i = 9;} 
                              count_masu4++;
                            }
                            
                          } 
                          break;
                        case 13:
                          label1.setText("("+x+","+y+"）の駒は飛車(赤)"); flag_koma=13; 
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_h = 0;    
                          /*上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(ary_ban[x-1][y-1-i]==0){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                            } else if((ary_ban[x-1][y-1-i]<=8 && ary_ban[x-1][y-1-i]>=1) || (ary_ban[x-1][y-1-i]>=21 && ary_ban[x-1][y-1-i]<=28)){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1][y-1-i]>=11 && ary_ban[x-1][y-1-i]<=18) || (ary_ban[x-1][y-1-i]>=31 && ary_ban[x-1][y-1-i]<=38)) {flag_h++;}
                            if(flag_h == 1){i = 9;} 
                            count_masu++;
                          }
                          if(flag_h != 1){flag_h = 1;}

                          /*下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(ary_ban[x-1][y-1+i]==0){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                            } else if((ary_ban[x-1][y-1+i]<=8 && ary_ban[x-1][y-1+i]>=1) || (ary_ban[x-1][y-1+i]>=21 && ary_ban[x-1][y-1+i]<=28)){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1][y-1+i]>=11 && ary_ban[x-1][y-1+i]<=18) || (ary_ban[x-1][y-1+i]>=31 && ary_ban[x-1][y-1+i]<=38)) {flag_h++;}
                            if(flag_h == 2){i = 9;} 
                            count_masu2++;
                          }
                          if(flag_h != 2){flag_h = 2;}

                          /*右方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(ary_ban[x-1+i][y-1]==0){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                            } else if((ary_ban[x-1+i][y-1]<=8 && ary_ban[x-1+i][y-1]>=1) || (ary_ban[x-1+i][y-1]>=21 && ary_ban[x-1+i][y-1]<=28)){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1+i][y-1]>=11 && ary_ban[x-1+i][y-1]<=18) || (ary_ban[x-1+i][y-1]>=31 && ary_ban[x-1+i][y-1]<=38)) {flag_h++;}
                            if(flag_h == 3){i = 9;} 
                            count_masu3++;
                          }
                          if(flag_h != 3){flag_h = 3;}

                          /*左方向*/
                          for( int i=1; x-1-i>-1; i++){
                            if(ary_ban[x-1-i][y-1]==0){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                            } else if((ary_ban[x-1-i][y-1]<=8 && ary_ban[x-1-i][y-1]>=1) || (ary_ban[x-1-i][y-1]>=21 && ary_ban[x-1-i][y-1]<=28)){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                              flag_h++;
                            } else if((ary_ban[x-1-i][y-1]>=11 && ary_ban[x-1-i][y-1]<=18) || (ary_ban[x-1-i][y-1]>=31 && ary_ban[x-1-i][y-1]<=38)) {flag_h++;}
                            if(flag_h == 4){i = 9;} 
                            count_masu4++;
                          }

                          break;
                        case 14:
                          label1.setText("("+x+","+y+"）の駒は香車(赤)"); flag_koma=14;
                          count_masu = 0;
                          int flag_k = 0;
                          for( int i=1; y-1-i>-1; i++){
                            if(ary_ban[x-1][y-1-i]==0){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                            }else if((ary_ban[x-1][y-1-i]<=8 && ary_ban[x-1][y-1-i]>=1) || (ary_ban[x-1][y-1-i]>=21 && ary_ban[x-1][y-1-i]<=28)){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                              flag_k++;
                            } else if((ary_ban[x-1][y-1-i]>=11 && ary_ban[x-1][y-1-i]<=18) || (ary_ban[x-1][y-1-i]>=31 && ary_ban[x-1][y-1-i]<=38)) {flag_k++;}
                            if(flag_k == 1){i = 9;} 
                            count_masu++;
                          }
                          break;
                        case 15:
                          label1.setText("("+x+","+y+"）の駒は桂馬(赤)"); flag_koma=15;
                          if((ary_ban[x-1-1][y-1-2]<=8 && ary_ban[x-1-1][y-1-2]>=0) || (ary_ban[x-1-1][y-1-2]>=21 && ary_ban[x-1-1][y-1-2]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-2]; ary_banc[x-1-1][y-1-2] = 9;}
                          if((ary_ban[x-1+1][y-1-2]<=8 && ary_ban[x-1+1][y-1-2]>=0) || (ary_ban[x-1+1][y-1-2]>=21 && ary_ban[x-1+1][y-1-2]<=28)){c_koma[1] = ary_ban[x-1+1][y-1-2]; ary_banc[x-1+1][y-1-2] = 9;}
                          break;
                        case 16:
                          label1.setText("("+x+","+y+"）の駒は銀(赤)"); flag_koma=16;
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[1] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma[2] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1-1][y-1+1]<=8 && ary_ban[x-1-1][y-1+1]>=0) || (ary_ban[x-1-1][y-1+1]>=21 && ary_ban[x-1-1][y-1+1]<=28)){c_koma[3] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]<=8 && ary_ban[x-1+1][y-1+1]>=0) || (ary_ban[x-1+1][y-1+1]>=21 && ary_ban[x-1+1][y-1+1]<=28)){c_koma[4] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;} 
                          break;
                        case 17:
                          label1.setText("("+x+","+y+"）の駒は金(赤)"); flag_koma=17; 
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[1] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma[2] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1-1][y-1]<=8 && ary_ban[x-1-1][y-1]>=0) || (ary_ban[x-1-1][y-1]>=21 && ary_ban[x-1-1][y-1]<=28)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]<=8 && ary_ban[x-1+1][y-1]>=0) || (ary_ban[x-1+1][y-1]>=21 && ary_ban[x-1+1][y-1]<=28)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1+1]<=8 && ary_ban[x-1][y-1+1]>=0) || (ary_ban[x-1][y-1+1]>=21 && ary_ban[x-1][y-1+1]<=28)){c_koma[5] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          break;
                        case 18:
                          label1.setText("("+x+","+y+"）の駒は王(赤)"); flag_koma=18; 
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[1] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma[2] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1-1][y-1]<=8 && ary_ban[x-1-1][y-1]>=0) || (ary_ban[x-1-1][y-1]>=21 && ary_ban[x-1-1][y-1]<=28)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]<=8 && ary_ban[x-1+1][y-1]>=0) || (ary_ban[x-1+1][y-1]>=21 && ary_ban[x-1+1][y-1]<=28)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1-1][y-1+1]<=8 && ary_ban[x-1-1][y-1+1]>=0) || (ary_ban[x-1-1][y-1+1]>=21 && ary_ban[x-1-1][y-1+1]<=28)){c_koma[5] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}
                          if((ary_ban[x-1+1][y-1+1]<=8 && ary_ban[x-1+1][y-1+1]>=0) || (ary_ban[x-1+1][y-1+1]>=21 && ary_ban[x-1+1][y-1+1]<=28)){c_koma[6] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}
                          if((ary_ban[x-1][y-1+1]<=8 && ary_ban[x-1][y-1+1]>=0) || (ary_ban[x-1][y-1+1]>=21 && ary_ban[x-1][y-1+1]<=28)){c_koma[7] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          break;
                        case 31:
                          label1.setText("("+x+","+y+"）の駒はと金(赤)"); flag_koma=31; 
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[1] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma[2] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1-1][y-1]<=8 && ary_ban[x-1-1][y-1]>=0) || (ary_ban[x-1-1][y-1]>=21 && ary_ban[x-1-1][y-1]<=28)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]<=8 && ary_ban[x-1+1][y-1]>=0) || (ary_ban[x-1+1][y-1]>=21 && ary_ban[x-1+1][y-1]<=28)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1+1]<=8 && ary_ban[x-1][y-1+1]>=0) || (ary_ban[x-1][y-1+1]>=21 && ary_ban[x-1][y-1+1]<=28)){c_koma[5] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          break;
                        case 32:
                          label1.setText("("+x+","+y+"）の駒は馬(赤)"); flag_koma=32;
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_kaku2 = 0;    
                          /*左上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(x-1-i>-i){
                              if(ary_ban[x-1-i][y-1-i]==0){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                              } else if((ary_ban[x-1-i][y-1-i]<=8 && ary_ban[x-1-i][y-1-i]>=1) || (ary_ban[x-1-i][y-1-i]>=21 && ary_ban[x-1-i][y-1-i]<=28)){
                                c_koma[i-1] = ary_ban[x-1-i][y-1-i]; ary_banc[x-1-i][y-1-i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1-i][y-1-i]>=11 && ary_ban[x-1-i][y-1-i]<=18) || (ary_ban[x-1-i][y-1-i]>=31 && ary_ban[x-1-i][y-1-i]<=38)) {flag_kaku2++;}
                              if(flag_kaku2 == 1){i = 9;} 
                              count_masu++;
                            }
                            
                          }
                          if(flag_kaku2 != 1){flag_kaku2 = 1;}

                          /*左下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(x-1-i>-1){
                              if(ary_ban[x-1-i][y-1+i]==0){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                              } else if((ary_ban[x-1-i][y-1+i]<=8 && ary_ban[x-1-i][y-1+i]>=1) || (ary_ban[x-1-i][y-1+i]>=21 && ary_ban[x-1-i][y-1+i]<=28)){
                                c_koma2[i-1] = ary_ban[x-1-i][y-1+i]; ary_banc[x-1-i][y-1+i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1-i][y-1+i]>=11 && ary_ban[x-1-i][y-1+i]<=18) || (ary_ban[x-1-i][y-1+i]>=31 && ary_ban[x-1-i][y-1+i]<=38)) {flag_kaku2++;}
                              if(flag_kaku2 == 2){i = 9;} 
                              count_masu2++;
                            }
                            
                          }
                          if(flag_kaku2 != 2){flag_kaku2 = 2;}

                          /*右上方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1-i>-1){
                              if(ary_ban[x-1+i][y-1-i]==0){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                              } else if((ary_ban[x-1+i][y-1-i]<=8 && ary_ban[x-1+i][y-1-i]>=1) || (ary_ban[x-1+i][y-1-i]>=21 && ary_ban[x-1+i][y-1-i]<=28)){
                                c_koma3[i-1] = ary_ban[x-1+i][y-1-i]; ary_banc[x-1+i][y-1-i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1+i][y-1-i]>=11 && ary_ban[x-1+i][y-1-i]<=18) || (ary_ban[x-1+i][y-1-i]>=31 && ary_ban[x-1+i][y-1-i]<=38)) {flag_kaku2++;}
                              if(flag_kaku2 == 3){i = 9;} 
                              count_masu3++;
                            }
                            
                          }
                          if(flag_kaku2 != 3){flag_kaku2 = 3;}

                          /*右上方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(y-1+i<9){
                              if(ary_ban[x-1+i][y-1+i]==0){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                              } else if((ary_ban[x-1+i][y-1+i]<=8 && ary_ban[x-1+i][y-1+i]>=1) || (ary_ban[x-1+i][y-1+i]>=21 && ary_ban[x-1+i][y-1+i]<=28)){
                                c_koma4[i-1] = ary_ban[x-1+i][y-1+i]; ary_banc[x-1+i][y-1+i] = 9;
                                flag_kaku2++;
                              } else if((ary_ban[x-1+i][y-1+i]>=11 && ary_ban[x-1+i][y-1+i]<=18) || (ary_ban[x-1+i][y-1+i]>=31 && ary_ban[x-1+i][y-1+i]<=38)) {flag_kaku2++;}
                              if(flag_kaku2 == 4){i = 9;} 
                              count_masu4++;
                            }
                            
                          } 
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma5[0] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}/*上*/
                          if((ary_ban[x-1-1][y-1]<=8 && ary_ban[x-1-1][y-1]>=0) || (ary_ban[x-1-1][y-1]>=21 && ary_ban[x-1-1][y-1]<=28)){c_koma5[1] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}/*左*/
                          if((ary_ban[x-1+1][y-1]<=8 && ary_ban[x-1+1][y-1]>=0) || (ary_ban[x-1+1][y-1]>=21 && ary_ban[x-1+1][y-1]<=28)){c_koma5[2] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}/*右*/
                          if((ary_ban[x-1][y-1+1]<=8 && ary_ban[x-1][y-1+1]>=0) || (ary_ban[x-1][y-1+1]>=21 && ary_ban[x-1][y-1+1]<=28)){c_koma5[3] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}/*下*/
                          break;
                        case 33:
                          label1.setText("("+x+","+y+"）の駒は龍(赤)"); flag_koma=33; 
                          count_masu = 0;
                          count_masu2 = 0;
                          count_masu3 = 0;
                          count_masu4 = 0; 
                          int flag_h2 = 0;    
                          /*上方向*/
                          for( int i=1; y-1-i>-1; i++){
                            if(ary_ban[x-1][y-1-i]==0){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                            } else if((ary_ban[x-1][y-1-i]<=8 && ary_ban[x-1][y-1-i]>=1) || (ary_ban[x-1][y-1-i]>=21 && ary_ban[x-1][y-1-i]<=28)){
                              c_koma[i-1] = ary_ban[x-1][y-1-i]; ary_banc[x-1][y-1-i] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1][y-1-i]>=11 && ary_ban[x-1][y-1-i]<=18) || (ary_ban[x-1][y-1-i]>=31 && ary_ban[x-1][y-1-i]<=38)) {flag_h2++;}
                            if(flag_h2 == 1){i = 9;} 
                            count_masu++;
                          }
                          if(flag_h2 != 1){flag_h2 = 1;}

                          /*下方向*/
                          for( int i=1; y-1+i<9; i++){
                            if(ary_ban[x-1][y-1+i]==0){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                            } else if((ary_ban[x-1][y-1+i]<=8 && ary_ban[x-1][y-1+i]>=1) || (ary_ban[x-1][y-1+i]>=21 && ary_ban[x-1][y-1+i]<=28)){
                              c_koma2[i-1] = ary_ban[x-1][y-1+i]; ary_banc[x-1][y-1+i] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1][y-1+i]>=11 && ary_ban[x-1][y-1+i]<=18) || (ary_ban[x-1][y-1+i]>=31 && ary_ban[x-1][y-1+i]<=38)) {flag_h2++;}
                            if(flag_h2 == 2){i = 9;} 
                            count_masu2++;
                          }
                          if(flag_h2 != 2){flag_h2 = 2;}

                          /*右方向*/
                          for( int i=1; x-1+i<9; i++){
                            if(ary_ban[x-1+i][y-1]==0){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                            } else if((ary_ban[x-1+i][y-1]<=8 && ary_ban[x-1+i][y-1]>=1) || (ary_ban[x-1+i][y-1]>=21 && ary_ban[x-1+i][y-1]<=28)){
                              c_koma3[i-1] = ary_ban[x-1+i][y-1]; ary_banc[x-1+i][y-1] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1+i][y-1]>=11 && ary_ban[x-1+i][y-1]<=18) || (ary_ban[x-1+i][y-1]>=31 && ary_ban[x-1+i][y-1]<=38)) {flag_h2++;}
                            if(flag_h2 == 3){i = 9;} 
                            count_masu3++;
                          }
                          if(flag_h2 != 3){flag_h2 = 3;}

                          /*左方向*/
                          for( int i=1; x-1-i>-1; i++){
                            if(ary_ban[x-1-i][y-1]==0){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                            } else if((ary_ban[x-1-i][y-1]<=8 && ary_ban[x-1-i][y-1]>=1) || (ary_ban[x-1-i][y-1]>=21 && ary_ban[x-1-i][y-1]<=28)){
                              c_koma4[i-1] = ary_ban[x-1-i][y-1]; ary_banc[x-1-i][y-1] = 9;
                              flag_h2++;
                            } else if((ary_ban[x-1-i][y-1]>=11 && ary_ban[x-1-i][y-1]<=18) || (ary_ban[x-1-i][y-1]>=31 && ary_ban[x-1-i][y-1]<=38)) {flag_h2++;}
                            if(flag_h2 == 4){i = 9;} 
                            count_masu4++;
                          }
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma5[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}/*左上*/
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma5[1] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}/*右上*/
                          if((ary_ban[x-1-1][y-1+1]<=8 && ary_ban[x-1-1][y-1+1]>=0) || (ary_ban[x-1-1][y-1+1]>=21 && ary_ban[x-1-1][y-1+1]<=28)){c_koma5[2] = ary_ban[x-1-1][y-1+1]; ary_banc[x-1-1][y-1+1] = 9;}/*左下*/
                          if((ary_ban[x-1+1][y-1+1]<=8 && ary_ban[x-1+1][y-1+1]>=0) || (ary_ban[x-1+1][y-1+1]>=21 && ary_ban[x-1+1][y-1+1]<=28)){c_koma5[3] = ary_ban[x-1+1][y-1+1]; ary_banc[x-1+1][y-1+1] = 9;}/*右下*/
                          break;
                        case 34:
                          label1.setText("("+x+","+y+"）の駒は成香(赤)"); flag_koma=34; 
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[1] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma[2] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1-1][y-1]<=8 && ary_ban[x-1-1][y-1]>=0) || (ary_ban[x-1-1][y-1]>=21 && ary_ban[x-1-1][y-1]<=28)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]<=8 && ary_ban[x-1+1][y-1]>=0) || (ary_ban[x-1+1][y-1]>=21 && ary_ban[x-1+1][y-1]<=28)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1+1]<=8 && ary_ban[x-1][y-1+1]>=0) || (ary_ban[x-1][y-1+1]>=21 && ary_ban[x-1][y-1+1]<=28)){c_koma[5] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          break;
                        case 35:
                          label1.setText("("+x+","+y+"）の駒は成桂(赤)"); flag_koma=35; 
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[1] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma[2] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1-1][y-1]<=8 && ary_ban[x-1-1][y-1]>=0) || (ary_ban[x-1-1][y-1]>=21 && ary_ban[x-1-1][y-1]<=28)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]<=8 && ary_ban[x-1+1][y-1]>=0) || (ary_ban[x-1+1][y-1]>=21 && ary_ban[x-1+1][y-1]<=28)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1+1]<=8 && ary_ban[x-1][y-1+1]>=0) || (ary_ban[x-1][y-1+1]>=21 && ary_ban[x-1][y-1+1]<=28)){c_koma[5] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          break;
                        case 36:
                          label1.setText("("+x+","+y+"）の駒は成銀(赤)"); flag_koma=36; 
                          if((ary_ban[x-1-1][y-1-1]<=8 && ary_ban[x-1-1][y-1-1]>=0) || (ary_ban[x-1-1][y-1-1]>=21 && ary_ban[x-1-1][y-1-1]<=28)){c_koma[0] = ary_ban[x-1-1][y-1-1]; ary_banc[x-1-1][y-1-1] = 9;}
                          if((ary_ban[x-1][y-1-1]<=8 && ary_ban[x-1][y-1-1]>=0) || (ary_ban[x-1][y-1-1]>=21 && ary_ban[x-1][y-1-1]<=28)){c_koma[1] = ary_ban[x-1][y-1-1]; ary_banc[x-1][y-1-1] = 9;}
                          if((ary_ban[x-1+1][y-1-1]<=8 && ary_ban[x-1+1][y-1-1]>=0) || (ary_ban[x-1+1][y-1-1]>=21 && ary_ban[x-1+1][y-1-1]<=28)){c_koma[2] = ary_ban[x-1+1][y-1-1]; ary_banc[x-1+1][y-1-1] = 9;}
                          if((ary_ban[x-1-1][y-1]<=8 && ary_ban[x-1-1][y-1]>=0) || (ary_ban[x-1-1][y-1]>=21 && ary_ban[x-1-1][y-1]<=28)){c_koma[3] = ary_ban[x-1-1][y-1]; ary_banc[x-1-1][y-1] = 9;}
                          if((ary_ban[x-1+1][y-1]<=8 && ary_ban[x-1+1][y-1]>=0) || (ary_ban[x-1+1][y-1]>=21 && ary_ban[x-1+1][y-1]<=28)){c_koma[4] = ary_ban[x-1+1][y-1]; ary_banc[x-1+1][y-1] = 9;}
                          if((ary_ban[x-1][y-1+1]<=8 && ary_ban[x-1][y-1+1]>=0) || (ary_ban[x-1][y-1+1]>=21 && ary_ban[x-1][y-1+1]<=28)){c_koma[5] = ary_ban[x-1][y-1+1]; ary_banc[x-1][y-1+1] = 9;}
                          break;
                    }
                }
            }else if(flag_move == 1 && ary_banc[x-1][y-1]==9){

                 
                    label1.setText("("+x+","+y+"）に駒を移動した");
                    gc.clearRect(x*50-30+2,y*50-30+2,46,46);
                    gc.clearRect(temp_x*50-30+2,temp_y*50-30+2,46,46);

                    ary_ban[temp_x-1][temp_y-1] = 0;
                    flag_move = 0;

                    if(flag_turn == 2){
                      
                      switch(ary_ban[temp_x-1][temp_y-1]){
                        case 1:
                          label1.setText("("+x+","+y+"）の駒は歩(青)");
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[0]; 
                          break;
                        case 2:
                          label1.setText("("+x+","+y+"）の駒は角(青)"); 
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1-i][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1-i][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1-i] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu4; i++){
                            ary_ban[temp_x-1+i][temp_y+i] = c_koma4[i-1]; 
                          }
                          break;
                        case 3:
                          label1.setText("("+x+","+y+"）の駒は飛車(青)"); 
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1-i<count_masu4; i++){
                            ary_ban[temp_x-1-i][temp_y-1] = c_koma4[i-1]; 
                          }
                          break;
                        case 4:
                          label1.setText("("+x+","+y+"）の駒は香車(青)"); 
                          for( int i=1; temp_y-1+i<count_masu; i++){
                            ary_ban[temp_x-1][temp_y-1+i] = c_koma[i-1]; 
                          }
                          break;
                        case 5:
                          label1.setText("("+x+","+y+"）の駒は桂馬(青)");
                          ary_ban[temp_x-1-1][temp_y-1+2] = c_koma[0];
                          ary_ban[temp_x-1+1][temp_y-1+2] = c_koma[1];
                          break;
                          
                        case 6:
                          label1.setText("("+x+","+y+"）の駒は銀(青)");
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[4]; 
                          break;
                        case 7:
                          label1.setText("("+x+","+y+"）の駒は金(青)"); 
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[5];
                          break;
                        case 8:
                          label1.setText("("+x+","+y+"）の駒は王(青)"); 
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[5];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[6];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[7];
                          break;
                        case 21:
                          label1.setText("("+x+","+y+"）の駒はと金(青)"); 
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[5];
                          break;
                        case 22:
                          label1.setText("("+x+","+y+"）の駒は馬(青)"); 
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1-i][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1-i][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1-i] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu4; i++){
                            ary_ban[temp_x-1+i][temp_y+i] = c_koma4[i-1]; 
                          }
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma5[0];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma5[1];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma5[2];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma5[3];
                          break;
                        case 23:
                          label1.setText("("+x+","+y+"）の駒は龍(青)"); 
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1-i<count_masu4; i++){
                            ary_ban[temp_x-1-i][temp_y-1] = c_koma4[i-1]; 
                          }
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma5[0];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma5[1];
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma5[2];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma5[3]; 
                          break;
                        case 24:
                          label1.setText("("+x+","+y+"）の駒は成香(青)"); 
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[5];
                          break;
                        case 25:
                          label1.setText("("+x+","+y+"）の駒は成桂(青)"); 
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[5];
                          break;
                        case 26:
                          label1.setText("("+x+","+y+"）の駒は成銀(青)"); 
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[5];
                          break;
                    }

                        getkoma(label1,efu,ekaku,ehisya,ekyou,ekei,egin,ekin,
                                mfu,mkaku,mhisya,mkyou,mkei,mgin,mkin);
                        ary_ban[x-1][y-1] = flag_koma;
                        naru(label1);
                        koma.setFill(Color.RED);
                        koma.fillOval(x*50-30+2,y*50-30+2,46,46);
                        flag_turn = 1;
                    }else if(flag_turn == 1){

                      switch(ary_ban[temp_x-1][temp_y-1]){
                        case 11:
                          label1.setText("("+x+","+y+"）の駒は歩(赤)");
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[0];
                          break;
                        case 12:
                          label1.setText("("+x+","+y+"）の駒は角(赤)"); 
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1-i][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1-i][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1-i] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu4; i++){
                            ary_ban[temp_x-1+i][temp_y+i] = c_koma4[i-1]; 
                          }
                          break;
                        case 13:
                          label1.setText("("+x+","+y+"）の駒は飛車(赤)");
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1-i<count_masu4; i++){
                            ary_ban[temp_x-1-i][temp_y-1] = c_koma4[i-1]; 
                          }
                          break;
                        case 14:
                          label1.setText("("+x+","+y+"）の駒は香車(赤)"); 
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1][temp_y-1-i] = c_koma[i-1]; 
                          }
                          break;
                        case 15:
                          label1.setText("("+x+","+y+"）の駒は桂馬(赤)");
                          ary_ban[temp_x-1-1][temp_y-1-2] = c_koma[0];
                          ary_ban[temp_x-1+1][temp_y-1-2] = c_koma[1];
                          break;
                        case 16:
                          label1.setText("("+x+","+y+"）の駒は銀(赤)");
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[4]; 
                          break;
                        case 17:
                          label1.setText("("+x+","+y+"）の駒は金(赤)");
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[5];
                          break;
                        case 18:
                          label1.setText("("+x+","+y+"）の駒は王(赤)"); 
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma[5];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma[6];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[7];
                          break;
                        case 31:
                          label1.setText("("+x+","+y+"）の駒はと金(赤)");
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[5];
                          break;
                        case 32:
                          label1.setText("("+x+","+y+"）の駒は馬(赤)"); 
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1-i][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1-i][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1-i] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu4; i++){
                            ary_ban[temp_x-1+i][temp_y+i] = c_koma4[i-1]; 
                          }
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma5[0];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma5[1];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma5[2];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma5[3];
                          break;
                        case 33:
                          label1.setText("("+x+","+y+"）の駒は龍(赤)");
                          for( int i=1; temp_y-1-i<count_masu; i++){
                            ary_ban[temp_x-1][temp_y-1-i] = c_koma[i-1]; 
                          }
                          for( int i=1; temp_y-1+i<count_masu2; i++){
                            ary_ban[temp_x-1][temp_y-1+i] = c_koma2[i-1]; 
                          }
                          for( int i=1; temp_x-1+i<count_masu3; i++){
                            ary_ban[temp_x-1+i][temp_y-1] = c_koma3[i-1]; 
                          }
                          for( int i=1; temp_x-1-i<count_masu4; i++){
                            ary_ban[temp_x-1-i][temp_y-1] = c_koma4[i-1]; 
                          }
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma5[0];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma5[1];
                          ary_ban[temp_x-1-1][temp_y-1+1] = c_koma5[2];
                          ary_ban[temp_x-1+1][temp_y-1+1] = c_koma5[3]; 
                          break;
                        case 34:
                          label1.setText("("+x+","+y+"）の駒は成香(赤)");
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[5];
                          break;
                        case 35:
                          label1.setText("("+x+","+y+"）の駒は成桂(赤)");
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[5];
                          break;
                        case 36:
                          label1.setText("("+x+","+y+"）の駒は成銀(赤)");
                          ary_ban[temp_x-1-1][temp_y-1-1] = c_koma[0];
                          ary_ban[temp_x-1][temp_y-1-1] = c_koma[1];
                          ary_ban[temp_x-1+1][temp_y-1-1] = c_koma[2];
                          ary_ban[temp_x-1-1][temp_y-1] = c_koma[3];
                          ary_ban[temp_x-1+1][temp_y-1] = c_koma[4];
                          ary_ban[temp_x-1][temp_y-1+1] = c_koma[5];
                          break;
                    }
                        getkoma(label1,efu,ekaku,ehisya,ekyou,ekei,egin,ekin,
                                mfu,mkaku,mhisya,mkyou,mkei,mgin,mkin);
                        ary_ban[x-1][y-1] = flag_koma;
                        naru(label1);
                        koma.setFill(Color.BLUE);
                        koma.fillOval(x*50-30+2,y*50-30+2,46,46);
                        flag_turn = 2;
                    }

                    for(int i = 0; i<ary_banc.length; i++){
                      for(int j = 0; j<ary_banc[i].length; j++){
                          if(ary_banc[i][j]==9){
                            ary_banc[i][j] = 0;
                          }
                     }
                     }

                    for(int i = 0; i<8; i++){
                      c_koma[i] = 0;
                 }
                    for(int i = 0; i<8; i++){
                      c_koma2[i] = 0;
                }
                    for(int i = 0; i<8; i++){
                      c_koma3[i] = 0;
                }
                    for(int i = 0; i<8; i++){
                      c_koma4[i] = 0;
                }
                    for(int i = 0; i<8; i++){
                      c_koma5[i] = 0;
                }



            }else if(flag_move == 1 && ary_ban[x-1][y-1]!=9){
                label1.setText("("+x+","+y+"）に駒を移動出来ない");
                flag_move = 0;

                for(int i = 0; i<ary_banc.length; i++){
                    for(int j = 0; j<ary_banc[i].length; j++){
                        if(ary_banc[i][j]==9){
                          ary_banc[i][j] = 0;
                        }
                   }
                   }
            }


            /*盤面の様子確認用 */
            for(int i = 0; i<ary_ban.length; i++){
              for(int j = 0; j<ary_ban[i].length; j++){
                  
                    System.out.print(ary_ban[i][j]+" " );
                 
             }
             System.out.println();
             }
             System.out.println();
             for(int i = 0; i<ary_banc.length; i++){
              for(int j = 0; j<ary_banc[i].length; j++){
                  
                    System.out.print(ary_banc[i][j]+" " );
                 
             }
             System.out.println();
             }
        });
        
        


        VBox vbox = new VBox(); vbox.getChildren().addAll(label1,canvas);
        
        VBox ekoma = new VBox(); ekoma.getChildren().addAll(ekin,egin,ehisya,ekaku,ekei,ekyou,efu);
        VBox mkoma = new VBox(); mkoma.getChildren().addAll(mkin,mgin,mhisya,mkaku,mkei,mkyou,mfu);
        HBox window = new HBox();window.getChildren().addAll(ekoma,vbox,mkoma);


        stage.setScene(new Scene(window));
        stage.show();
    }
}
