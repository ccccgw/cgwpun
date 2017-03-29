package com.demo.test.thread;

/**
 * TODO
 *
 * @author Cui.GaoWei
 * @date 2017/3/22
 */
public class MyThread {
    static int i=0;
    public static void main(String[]args){
        //建立3个子线程，以i，i,n,m作为子线程是否结束的判断
        //当所有子线程运行完才开始主线程
        System.out.println("主线程开始");
        Thread t1=new Thread(){
            public void run(){
                System.out.println("子线程1");
                MyThread.i+=1;
            }
        };
        //开启线程1
        t1.start();
        Thread t2=new Thread(){
            public void run(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子线程2");
                MyThread.i+=1;
            }
        };
        //开启线程2
        t2.start();
        Thread t3=new Thread(){
            public void run(){
                System.out.println("子线程3");
                MyThread.i+=1;
            }
        };
        //开启线程3
        t3.start();
        boolean res=true;
        while(res){
            //这里判断子线程是否运行完了
            if(MyThread.i==3){
                System.out.println("主线程结束");
                res=false;
            }
        }
    }
}
