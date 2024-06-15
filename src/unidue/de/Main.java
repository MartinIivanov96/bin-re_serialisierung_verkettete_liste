package unidue.de;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
        //filling the list
        for (int i = 0; i < 1500; i++) {
            list.append("Satz" + i);
        }

        //serialization of the list
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("list.ser"))) {
            oos.writeObject(list);
            System.out.println("Liste ist serialisiert.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //deserialization of the list
        SimpleLinkedList<String> deserealizedList = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("list.ser"))){
            deserealizedList = (SimpleLinkedList<String>) ois.readObject();
            System.out.println("Liste ist deserialisiert.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //compare both lists;
        if (list.equals(deserealizedList)) {
            System.out.println("Die original Liste und die deserialisierte Liste sind gleich.");
        } else {
            System.out.println("Die original Liste und die deserialisierte Liste sind nicht gleich.");
        }
    }
}
