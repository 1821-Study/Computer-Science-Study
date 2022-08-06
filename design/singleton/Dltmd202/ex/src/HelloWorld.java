class Singleton{
    private static class SingleInstanceHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {}

    public static Singleton getInstance(){
        return SingleInstanceHolder.INSTANCE;
    }
}

class SingletonCustom{
    private static SingletonCustom singleton = null;

    private SingletonCustom() {};

    public static SingletonCustom getInstance(){
        if(singleton == null){
            singleton = new SingletonCustom();
        }
        return singleton;
    }
}

class RunnableSingleton implements Runnable{

    private static int seq = 0;
    private int id;
    Singleton singleton;


    @Override
    public void run() {
        id = seq++;
        singleton = Singleton.getInstance();
        sleep();
        for (int i = 0; i < 5; i++) {
            System.out.println(singleton);
            sleep();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("이 객체의 id는 %d입니당\n", id);
    }
}

public class HelloWorld {
    public static void main(String[] args) {

        Runnable singleton = new RunnableSingleton();
        new Thread(singleton).start();
        new Thread(singleton).start();
    }

    public static void test1(){
        Singleton a = Singleton.getInstance();
        Singleton b = Singleton.getInstance();
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(a == b);
    }
}
