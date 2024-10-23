package dev.lpa;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {

        //Define the Consumer that reads the content of the buffer into a byte array then converts it to a string for printing
        Consumer<ByteBuffer> printBuffer = (buffer) -> {
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            System.out.printf("\"%s\" ", new String(data, StandardCharsets.UTF_8));
        };

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        doOperation("Print: ", buffer, (b)-> System.out.print(b + " "));
        doOperation("Write: ", buffer, (b)->b.put("123456789".getBytes()));
        doOperation("Flip(from Write to Read): ", buffer, (b)->b.flip());
        doOperation("Read and Print Value: ", buffer, printBuffer);
        doOperation("Flip(from Read to Write): ", buffer, (b)->b.flip());
        //doOperation("1.Move POSITION to the end of the text", buffer, b->b.position(b.limit()));
        //doOperation("2.Change the LIMIT to the CAPACITY", buffer, b->b.limit(b.capacity()));
        doOperation("Compact: ", buffer, b->buffer.compact());
        doOperation("Append: ", buffer, (b)->b.put(" this exceedes the limit without changing the position and the limit".getBytes()));
        //doOperation("Flip(from Write to Read): ", buffer, (b)->b.flip());
        //doOperation("Read and Print Value: ", buffer, printBuffer);
        doOperation("Read and Print Value: ", buffer.slice(0, buffer.position()), printBuffer);
        doOperation("Append: ", buffer, (b)->b.put("**********".getBytes()));
        doOperation("Read and Print Value: ", buffer.slice(0, buffer.position()), printBuffer);
    }

    //PURPOSE: observe and log the state of a ByteBuffer
    //String op: description of operation
    //Consumer: operation on a ByteBuffer input w/o returning a value
    private static void doOperation(String op, ByteBuffer buffer, Consumer<ByteBuffer> c){
        System.out.printf("%-30s", op);
        c.accept(buffer);
        System.out.printf("Capacity= %d, Limit= %d, Position= %d, Remaining= %d%n",
                buffer.capacity(), buffer.limit(), buffer.position(), buffer.remaining());
    }
}
