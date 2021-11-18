package helper;


//This is the most basic implementation of the Trampoline pattern I could find out there.
public class Trampoline<T> {
    public T get() {
        return null;
    }
    public Trampoline<T>  run() {
        return null;
    }

    public T execute() {
        Trampoline<T>  trampoline = this;

        while (trampoline.get() == null) {
            trampoline = trampoline.run();
        }

        return trampoline.get();
    }
}