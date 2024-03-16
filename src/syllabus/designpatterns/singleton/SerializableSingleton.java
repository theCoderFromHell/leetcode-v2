package syllabus.designpatterns.singleton;

public class SerializableSingleton {
    private static SerializableSingleton instance = null;

    private SerializableSingleton() {}

    public static SerializableSingleton getInstance() {
        if (instance == null) {
            synchronized (SerializableSingleton.class) {
                instance = new SerializableSingleton();
            }
        }
        return instance;
    }

    protected Object readResolve() {
        return instance;
    }

}
