package helper;

public class Trampoline<T>
{
    public T get() { return null; }
    public Trampoline<T>  run() { return null; }

    T execute() {
        Trampoline<T>  trampoline = this;

        while (trampoline.get() == null) {
            trampoline = trampoline.run();
        }

        return trampoline.get();
    }
}