package common;

public class LearningJava {
    public static void main(String[] args) {
        Dog dog = new Dog("Original");
        foo(dog);
        System.out.println(dog.name);
    }

    static void foo(Dog dog) {
        dog.name = "Max";
        dog = new Dog("Lol");
        dog.name = "Why";
    }

    static class Dog {
        String name;

        public Dog(String name) {
            this.name = name;
        }
    }
}
