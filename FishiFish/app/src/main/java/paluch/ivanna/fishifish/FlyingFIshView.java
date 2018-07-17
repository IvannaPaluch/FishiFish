package paluch.ivanna.fishifish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Iv on 17.07.2018.
 */

public class FlyingFIshView extends View {
    private Bitmap fish[] = new Bitmap[2];

    private int fishX =10;
    private int fishY;
    private int fishSpeed;
    private boolean touch =false;
    private int canvasWidht, canvasHeight;

    private int yellowX, yellowY, yellowSpeed=16;
    private Paint yellowPaint = new Paint();

    private int greenX, greenY, greenSpeed=20;
    private Paint greenPaint = new Paint();

    private int blackX, blackY, blackSpeed=35;
    private Paint blackPaint=new Paint();

    private int redX, redY, redSpeed=35;
    private Paint redPaint=new Paint();

    //extra life
  //  private int cyanX, cyanY, cyanSpeed=55;
  //  private Paint cyanPaint=new Paint();

    private Bitmap backgroundImage;

    private Paint scorePaint = new Paint();
    private Bitmap life[] = new Bitmap[2];

    private int score, lifeCounterOfFish;

    public FlyingFIshView(Context context){
        super(context);
        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);
        backgroundImage =BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        blackPaint.setColor(Color.BLACK);
        blackPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

        //extra life
     //   cyanPaint.setColor(Color.CYAN);
   //     cyanPaint.setAntiAlias(false);

        scorePaint.setColor(Color.YELLOW);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);
        fishY=550;
        score=0;
        lifeCounterOfFish=5;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidht = canvas.getWidth();
        canvasHeight = canvas.getHeight();


        canvas.drawBitmap(backgroundImage, 0 ,0,null);

        int minFishY = fish[0].getHeight();
        int maxFishY= canvasHeight-fish[0].getHeight()*3;
        fishY =fishY+fishSpeed;
        if(fishY<minFishY){
            fishY=minFishY;
        }
        if(fishY>maxFishY){
            fishY=maxFishY;
        }

        fishSpeed=fishSpeed+2;

        if(touch){
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        }else{
            canvas.drawBitmap(fish[0], fishX,fishY, null);
        }

        yellowX=yellowX-yellowSpeed;
        greenX=greenX-greenSpeed;
        blackX=blackX-blackSpeed;
        redX=redX-redSpeed;
        //extra life
      //  cyanX=cyanX-cyanSpeed;


        if(hitBallChecker(yellowX, yellowY)){
            score=score+10;
            yellowX= -100;
        }
        if(hitBallChecker(greenX, greenY)){
            score=score+30;
            greenX= -100;
        }
        if(hitBallChecker(blackX, blackY)){
            score=score-10;
            blackX= -100;
        }
        if(hitBallChecker(redX, redY)){
            lifeCounterOfFish--;
            redY= -100;
            if(lifeCounterOfFish==0){
                Toast.makeText(getContext(), "Game over!", Toast.LENGTH_SHORT).show();
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);

                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameOverIntent);
            }
        }
        //extra life
       /* if(hitBallChecker(cyanX, cyanY)){
            lifeCounterOfFish++;
            blackX= -100;
        }*/
        if (yellowX<0){
            yellowX=canvasWidht+21;
            yellowY= (int) Math.floor(Math.random() *(maxFishY-minFishY))+minFishY;
        }
        if (greenX<0){
            greenX=canvasWidht+21;
            greenY= (int) Math.floor(Math.random() *(maxFishY-minFishY))+minFishY;
        }
        if (blackX<0){
            blackX=canvasWidht+21;
            blackY= (int) Math.floor(Math.random() *(maxFishY-minFishY))+minFishY;
        }
        if (redX<0){
            redX=canvasWidht+21;
            redY= (int) Math.floor(Math.random() *(maxFishY-minFishY))+minFishY;
        }
       /* //extra life
        if (cyanX<0){
            cyanX=canvasWidht+21;
            cyanY= (int) Math.floor(Math.random() *(maxFishY-minFishY))+minFishY;
        }*/

        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);
        canvas.drawCircle(greenX, greenY, 10, greenPaint);
        canvas.drawCircle(blackX, blackY, 40, blackPaint);
        canvas.drawCircle(redX, redY, 55, redPaint);
        //canvas.drawCircle(cyanX, cyanY, 15, cyanPaint);

        canvas.drawText("Score : "+score, 20,90,scorePaint);

        for(int i=0; i<5; i++){
            int x= (int) (480 + life[0].getWidth() *1.5 * i);
            int y =30;
            if(i<lifeCounterOfFish){
                canvas.drawBitmap(life[0], x, y,null);
            }else{
                canvas.drawBitmap(life[1], x, y,null);
            }
        }




    }

    public boolean hitBallChecker(int x, int y){
        if(fishX < x && x < (fishX+fish[0].getWidth()) && fishY < y && y <(fishY+fish[0].getHeight())){
            return true;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            touch=true;
            fishSpeed=-22;
        }
        return true;
    }
}